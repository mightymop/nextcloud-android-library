/*
 *
 * Nextcloud Android client application
 *
 * @author Tobias Kaminsky
 * Copyright (C) 2023 Tobias Kaminsky
 * Copyright (C) 2023 Nextcloud GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.owncloud.android.lib.resources.users

import com.owncloud.android.AbstractIT
import com.owncloud.android.lib.common.OwnCloudClientManagerFactory
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class GetPublicKeyRemoteOperationIT : AbstractIT() {
    @Before
    fun init() {
        // E2E server app checks for official NC client with >=3.13.0, 
        // and blocks all other clients, e.g. 3rd party apps using this lib
        OwnCloudClientManagerFactory.setUserAgent("Mozilla/5.0 (Android) Nextcloud-android/3.13.0")
    }

    @Test
    fun getOwnKey() {
        val result = GetPublicKeyRemoteOperation().execute(nextcloudClient)

        assertTrue(result.isSuccess)
        assertNotNull(result.resultData)
    }

    @Test
    fun getAnotherPublicKey() {
        val result = GetPublicKeyRemoteOperation("enc2").execute(nextcloudClient)

        assertTrue(result.isSuccess)
        assertNotNull(result.resultData)
    }
}
