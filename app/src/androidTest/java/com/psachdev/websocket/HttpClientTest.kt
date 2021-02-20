package com.psachdev.websocket

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class HttpClientTest {

    @Test
    fun testGetJwtToken() {
        val testUserName = "sampleUsername"
        val testId = UUID.randomUUID().toString().replace("-", "")
        val jwtToken = SignalHttpClient().loginClient(testUserName, testId)
        Assert.assertTrue(jwtToken.isNotEmpty() && jwtToken!=SignalHttpClient.LoginError)

    }
}