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

public class P2p {
    private static final String TAG = "fdep2p";
    public static final String SERVICE_NAME = "openfdep2p";

    private static IP2p sService;
    private static P2p sInstance;
    private final Context mContext;

    private P2p(Context context) {
        mContext = context == null ? null : context.getApplicationContext();
        sService = getService();
    }

    public static P2p getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new P2p(context);
        }
        return sInstance;
    }

    public static IP2p getService() {
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
            sService = IP2p.Stub.asInterface(b);
        } catch (Exception e) {
            Log.e(TAG, "Error getting service via reflection", e);
            return null;
        }
        return sService;
    }

    public boolean addBonjourService(byte[] query, byte[] response) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.addBonjourService(query, response);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean addGroup(boolean persistent, int persistentNetworkId) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.addGroup(persistent, persistentNetworkId);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean cancelConnect() {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.cancelConnect();
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pStopFind() {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_stop_find();
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pAspProvision(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_asp_provision(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pAspProvisionResp(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_asp_provision_resp(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pConnect(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_connect(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pListen(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_listen(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pGroupRemove(String ifname) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_group_remove(ifname);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pGroupMember(String ifname) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_group_member(ifname);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pProvDisc(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_prov_disc(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public String p2pGetPassphrase() {
        IP2p service = getService();
        if (service == null) {
            return "";
        }
        try {
            return service.p2p_get_passphrase();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return "";
    }

    public String p2pServDiscReq(String args) {
        IP2p service = getService();
        if (service == null) {
            return "";
        }
        try {
            return service.p2p_serv_disc_req(args);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return "";
    }

    public boolean p2pServDiscCancel(String identifier) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_serv_disc_cancel(identifier);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pServDiscResp(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_serv_disc_resp(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pServiceUpdate() {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_service_update();
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pServDiscExternal(String value) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_serv_disc_external(value);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pServiceFlush() {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_service_flush();
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pServiceRep(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_service_rep(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pServiceDel(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_service_del(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pReject(String peer) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_reject(peer);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pInvite(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_invite(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public String p2pPeers() {
        IP2p service = getService();
        if (service == null) {
            return "";
        }
        try {
            return service.p2p_peers();
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return "";
    }

    public String p2pPeer(String peer) {
        IP2p service = getService();
        if (service == null) {
            return "";
        }
        try {
            return service.p2p_peer(peer);
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return "";
    }

    public boolean p2pSet(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_set(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pFlush() {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_flush();
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pUnauthorize(String peer) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_unauthorize(peer);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pPresenceReq(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_presence_req(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pExtListen(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_ext_listen(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pRemoveClient(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_remove_client(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean p2pFind(String args) {
        IP2p service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.p2p_find(args);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return false;
    }
}

