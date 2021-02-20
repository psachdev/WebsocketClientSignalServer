package com.psachdev.websocket

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignalClientTest {
    @Test
    fun testConnectDisconnect() {
        val signalClient = SignalWebSocketClientManager()
        val loginStatus = signalClient.initSignalClient(SignalWebSocketClientManager.sampleBearerToken)
        Assert.assertTrue(loginStatus)
        Assert.assertTrue(signalClient.isOpen())
        Assert.assertFalse(signalClient.isClosed())

        signalClient.destroySignalClient()
        Assert.assertFalse(signalClient.isOpen())
        Assert.assertTrue(signalClient.isClosed())
    }
}