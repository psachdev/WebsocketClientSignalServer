package com.psachdev.websocket

import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import java.net.URI
import java.util.concurrent.TimeUnit

class SignalClient {
    companion object {
        private const val signalServerUrl = "ws://192.168.2.6:8080/signal_server"
        private const val timeOut = 30 * 1000
        private const val connectTimeout = 30 * 1000
        const val sampleBearerToken =
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6InByYXRlZWtfd2VicnRjX3NpZ25hbF9zZXJ2ZXIiLCJuYW1lIjoiYWxpY2UiLCJpZCI6ImExIiwiZXhwIjoxNjEzNTQ4Mzc2fQ.jLbr3EO00pBCgHZqeuM8jACc4BK06I0hdS3qXjdW8t2ufwH09GTjSsvDURTnwQ5_d31A-xIqlgvAqQKJYy4YMQ"
    }

    private lateinit var webSocketClient: WebSocketClient

    fun initSignalClient(bearerToken: String) {
        val signalServerUri = URI(signalServerUrl)
        createWebSocketClient(signalServerUri, bearerToken)
    }

    fun destroySignalClient() {
        if (::webSocketClient.isInitialized && webSocketClient.isOpen) {
            webSocketClient.closeBlocking()
        }
    }

    fun isOpen(): Boolean {
        return if (::webSocketClient.isInitialized) {
            webSocketClient.isOpen
        } else {
            false
        }
    }

    fun isClosed(): Boolean{
        return if (::webSocketClient.isInitialized) {
            webSocketClient.isClosed
        } else {
            true
        }
    }

    private fun createWebSocketClient(
        signalServerUri: URI,
        bearerToken: String
    ) {
        val httpHeader = HashMap<String, String>()
        httpHeader["Authorization"] = bearerToken
        webSocketClient = SignalWebSocketClient(signalServerUri, Draft_6455(),  httpHeader, connectTimeout)
        webSocketClient.connectBlocking(timeOut.toLong(), TimeUnit.SECONDS)
    }
}