/**
 * Copyright (C) 2021 The WayDroid Project
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
import android.os.ServiceManager;
import android.util.Log;

import android.openfde.OpenfdeContextConstants;

public class Clipboard {
    private static final String TAG = "WayDroidClipboard";

    private static IClipboard sService;
    private static Clipboard sInstance;

    private Context mContext;

    private Clipboard(Context context) {
        Context appContext = context.getApplicationContext();
        mContext = appContext == null ? context : appContext;
        sService = getService();
    }

    /**
     * Get or create an instance of the {@link lineageos.waydroid.Clipboard}
     *
     * @param context Used to get the service
     * @return {@link Clipboard}
     */
    public static Clipboard getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Clipboard(context);
        }
        return sInstance;
    }

    /** @hide **/
    public static IClipboard getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService(OpenfdeContextConstants.OPENFDE_CLIPBOARD_SERVICE);

        if (b == null) {
            Log.e(TAG, "null service. SAD!");
            return null;
        }

        sService = IClipboard.Stub.asInterface(b);
        return sService;
    }

    /** @hide **/
    public void sendClipboardData(String value) {
        IClipboard service = getService();
        if (service == null) {
            return;
        }
        try {
            service.sendClipboardData(value);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return;
    }

    public String getClipboardData() {
        IClipboard service = getService();
        if (service == null) {
            return "";
        }
        try {
            String paste = service.getClipboardData();
            return paste != null ? paste : "";
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return "";
    }

}
