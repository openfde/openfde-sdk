/**
 * Copyright (C) 2021 The OpenFDE Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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

public class Wifi {
    private static final String TAG = "fdewifi";
    public static final String SERVICE_NAME = "openfdewifi";

    public static final int ERROR_UNDEFINED = -1;

    public static final int SECURITY_OPEN = 0;
    public static final int SECURITY_WPA2_PSK = 1;
    public static final int SECURITY_SAE = 2;

    private static IWifi sService;
    private static Wifi sInstance;
    private EventListener eventListener;
    private IWifiCallback callback;
    private Context mContext;

    public enum CallBackEvents {
        WIFI_STATE_CHANGED,
        SCAN_RESULTS_AVAILABLE,
        CONNECTION_CHANGED,
        RSSI_CHANGED;

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

    private Wifi(Context context) {
        mContext = context == null ? null : context.getApplicationContext();
        sService = getService();
    }

    public static Wifi getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Wifi(context);
        }
        return sInstance;
    }

    /** @hide */
    public static IWifi getService() {
        if (sService != null) {
            if (!sService.asBinder().pingBinder()) {
                Log.w(TAG, SERVICE_NAME + " binder dead, re-resolving");
                sService = null;
            } else {
                return sService;
            }
        }
        try {
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = serviceManager.getDeclaredMethod("getService", String.class);
            IBinder b = (IBinder) getServiceMethod.invoke(null, SERVICE_NAME);
            if (b == null) {
                Log.e(TAG, SERVICE_NAME + " null SAD!");
                return null;
            }
            sService = IWifi.Stub.asInterface(b);
        } catch (Exception e) {
            Log.e(TAG, "Error getting service via reflection", e);
            return null;
        }
        return sService;
    }

    public boolean registerCallback(EventListener listener) {
        IWifi service = getService();
        if (service == null || listener == null) {
            return false;
        }
        eventListener = listener;
        try {
            callback = new IWifiCallback.Stub() {
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
        IWifi service = getService();
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
        IWifi service = getService();
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

    public boolean isEnabled() {
        IWifi service = getService();
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

    public boolean setEnabled(boolean on) {
        IWifi service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.setEnabled(on);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean startScan() {
        IWifi service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.startScan();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public String getConnectionInfo() {
        IWifi service = getService();
        if (service == null) {
            return "";
        }
        try {
            return service.getConnectionInfo();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return "";
    }

    public String getScanResults() {
        IWifi service = getService();
        if (service == null) {
            return "";
        }
        try {
            return service.getScanResults();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return "";
    }

    public boolean connect(String ssid, int security, String passphrase) {
        IWifi service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.connect(ssid, security, passphrase);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean connectSaved(String name) {
        IWifi service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.connectSaved(name);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean disconnect() {
        IWifi service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.disconnect();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public String getSavedNetworks() {
        IWifi service = getService();
        if (service == null) {
            return "";
        }
        try {
            return service.getSavedNetworks();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return "";
    }

    public boolean saveNetwork(String ssid, int security, String passphrase) {
        IWifi service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.saveNetwork(ssid, security, passphrase);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean forgetNetwork(String name) {
        IWifi service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.forgetNetwork(name);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public String getFactoryMacAddresses() {
        IWifi service = getService();
        if (service == null) {
            return "{}";
        }
        try {
            return service.getFactoryMacAddresses();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return "{}";
    }

    public String getDhcpInfo() {
        IWifi service = getService();
        if (service == null) {
            return "{}";
        }
        try {
            return service.getDhcpInfo();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return "{}";
    }
}
