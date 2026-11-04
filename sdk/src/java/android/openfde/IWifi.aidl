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

interface IWifi {
    boolean registerCallback(IBinder callback);
    boolean unregisterCallback(IBinder callback);
    boolean init();
    boolean isEnabled();
    boolean setEnabled(boolean on);
    boolean startScan();
    String getConnectionInfo();
    String getScanResults();
    boolean connect(String ssid, int security, String passphrase);
    boolean connectSaved(String name);
    boolean disconnect();
    String getSavedNetworks();
    boolean saveNetwork(String ssid, int security, String passphrase);
    boolean forgetNetwork(String name);
    String getFactoryMacAddresses();
    String getDhcpInfo();
}
