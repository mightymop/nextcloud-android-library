/* Nextcloud Android Library is available under MIT license
 *
 *   @author Tobias Kaminsky
 *   Copyright (C) 2017 Tobias Kaminsky
 *   Copyright (C) 2017 Nextcloud GmbH
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *   EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *   MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *   BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *   ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *   CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 */

package com.owncloud.android.lib.resources.users;

import com.google.gson.reflect.TypeToken;
import com.nextcloud.common.NextcloudClient;
import com.nextcloud.operations.GetMethod;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.common.utils.Log_OC;
import com.owncloud.android.lib.ocs.ServerResponse;
import com.owncloud.android.lib.ocs.responses.PrivateKey;
import com.owncloud.android.lib.resources.OCSRemoteOperation;

import java.net.HttpURLConnection;

/**
 * Remote operation performing the fetch of the private key for an user
 */

public class GetPrivateKeyOperation extends OCSRemoteOperation<PrivateKey> {

    private static final String TAG = GetPrivateKeyOperation.class.getSimpleName();
    private static final String PUBLIC_KEY_URL = "/ocs/v2.php/apps/end_to_end_encryption/api/v1/private-key";

    /**
     * @param client Client object
     */
    @Override
    public RemoteOperationResult<PrivateKey> run(NextcloudClient client) {
        GetMethod getMethod = null;
        RemoteOperationResult<PrivateKey> result;

        try {
            // remote request
            getMethod = new GetMethod(client.getBaseUri() + PUBLIC_KEY_URL + JSON_FORMAT, true);
            getMethod.addRequestHeader(OCS_API_HEADER, OCS_API_HEADER_VALUE);

            int status = client.execute(getMethod);

            if (status == HttpURLConnection.HTTP_OK) {
                ServerResponse<PrivateKey> serverResponse =
                        getServerResponse(getMethod, new TypeToken<ServerResponse<PrivateKey>>() {
                        });

                result = new RemoteOperationResult<>(true, getMethod);
                result.setResultData(serverResponse.getOcs().data);
            } else {
                result = new RemoteOperationResult<>(false, getMethod);
            }

        } catch (Exception e) {
            result = new RemoteOperationResult<>(e);
            Log_OC.e(TAG, "Fetching of public key failed: " + result.getLogMessage(), result.getException());
        } finally {
            if (getMethod != null)
                getMethod.releaseConnection();
        }
        return result;
    }

}
