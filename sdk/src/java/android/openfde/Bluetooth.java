/**
 * Copyright (C) 2021 The OpenFDE Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.openfde;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.lang.reflect.Method;

public class Bluetooth {
    private static final String TAG = "fdebluetooth";
    public static final String SERVICE_NAME = "openfdebluetooth";

    /**
     * Unable to determine status, an error occured
     */
    public static final int ERROR_UNDEFINED = -1;

    private static IBluetooth sService;
    private static Bluetooth sInstance;
    private EventListener eventListener;
    private IBluetoothCallback callback;
    private Context mContext;
    public enum CallBackEvents {
        BT_STATE_ON,
        BT_STATE_OFF,
        BT_DISCOVERY_STARTED,
        BT_DISCOVERY_STOPPED,
        ADAPTER_PROPERTY_CHANGED,
        DEVICE_FOUND,
        DEVICE_PROPERTY_CHANGED,
        BOND_STATE_CHANGE,
        FROFILE_CONNECTION_STATE_CHANGED,
        PIN_REQUEST,
        SSP_REQUEST;

        public static CallBackEvents fromOrdinal(int ordinal) {
            for (CallBackEvents e : values()) {
                if (e.ordinal() == ordinal) {
                    return e;
                }
            }
            return null;
        }
    }
    public interface EventListener {
        void onEvent(int what, String data);
    }

    private Bluetooth(Context context) {
        mContext = context == null ? null : context.getApplicationContext();
        sService = getService();
    }

    /**
     * Get or create an instance of the {@link android.openfde.Bluetooth}
     *
     * @param context Used to get the service
     * @return {@link Bluetooth}
     */
    public static Bluetooth getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Bluetooth(context);
        }
        return sInstance;
    }

    /** @hide **/
    public static IBluetooth getService() {
        if (sService != null) {
            return sService;
        }
        try {
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = serviceManager.getDeclaredMethod("getService", String.class);
            IBinder b = (IBinder) getServiceMethod.invoke(null, SERVICE_NAME);
            if (b == null) {
                Log.e(TAG, SERVICE_NAME + " null SAD!");
                return null;
            }
            sService = IBluetooth.Stub.asInterface(b);
        } catch (Exception e) {
            Log.e(TAG, "Error getting service via reflection", e);
            return null;
        }
        return sService;
    }

	public boolean registerCallback(EventListener listener) {
        IBluetooth service = getService();
        if (service == null || listener == null) {
            return false;
        }
        eventListener = listener;
        try {
            callback = new IBluetoothCallback.Stub() {
                @Override
                public void onEvent(int what, String data) throws RemoteException {
                        eventListener.onEvent(what, data);
                }
            };
            return service.registerCallback(callback.asBinder());
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

	public boolean unregisterCallback() {
        IBluetooth service = getService();
        if (service == null || callback == null) {
            return false;
        }
        boolean ret = false;
        try {
            ret = service.unregisterCallback(callback.asBinder());
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        if (ret) {
            eventListener = null;
            callback = null;
        }
        return ret;
    }

    public boolean init() {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.init();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public void cleanup() {
        IBluetooth service = getService();
        if (service == null) {
            return;
        }
        try {
            service.cleanup();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
    }

    public boolean enable() {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.enable();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean disable() {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.disable();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean setAdapterProperty(int type, String val) {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.setAdapterProperty(type, val);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean getAdapterProperties() {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.getAdapterProperties();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean getAdapterProperty(int type) {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.getAdapterProperty(type);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean createBond(String address, int addressType, int transport) {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.createBond(address, addressType, transport);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }
 
    public boolean removeBond(String address) {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.removeBond(address);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean cancelBond(String address) {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.cancelBond(address);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean pairingIsBusy() {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.pairingIsBusy();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public int getConnectionState(String address) {
        IBluetooth service = getService();
        if (service == null) {
            return ERROR_UNDEFINED;
        }
        try {
            return service.getConnectionState(address);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return ERROR_UNDEFINED;
    }

    public boolean startDiscovery() {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.startDiscovery();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean cancelDiscovery() {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.cancelDiscovery();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean connect(String address) {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.connect(address);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean disconnect(String address) {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.disconnect(address);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean setDeviceProperty(String address, int type, String val) {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.setDeviceProperty(address, type, val);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean isEnabled() {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.isEnabled();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean sspReply(String address, int type, boolean accept, int passkey) {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.sspReply(address, type, accept, passkey);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean pinReply(String address, boolean accept, String pin) {
        IBluetooth service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.pinReply(address, accept, pin);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public String getAdapterName() {
        IBluetooth service = getService();
        if (service == null) {
            return null;
        }
        try {
            return service.getAdapterName();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return null;
    }
}
