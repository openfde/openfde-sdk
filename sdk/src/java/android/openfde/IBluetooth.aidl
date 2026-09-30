/*
**
** Copyright (C) 2021 The OpenFDE Project
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/

package android.openfde;
interface IBluetooth {
    boolean registerCallback(IBinder callback);
    boolean unregisterCallback(IBinder callback);
    boolean init();
    void cleanup();
    boolean enable();
    boolean disable();
    boolean getAdapterProperties();
    boolean getAdapterProperty(int type);
    boolean setAdapterProperty(int type, String val);
    boolean createBond(String address, int addressType, int transport);
    boolean removeBond(String address);
    boolean cancelBond(String address);
    boolean pairingIsBusy();
    int getConnectionState(String address);
    boolean startDiscovery();
    boolean cancelDiscovery();
    boolean connect(String address);
    boolean disconnect(String address);
    boolean setDeviceProperty(String address, int type, String val);
    boolean isEnabled();
    boolean sspReply(String address, int type, boolean accept, int passkey);
    boolean pinReply(String address, boolean accept, String pin);
    String getAdapterName();
}
