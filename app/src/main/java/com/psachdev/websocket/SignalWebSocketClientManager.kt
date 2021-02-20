package com.psachdev.websocket

import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.exceptions.WebsocketNotConnectedException
import java.net.URI
import java.util.concurrent.TimeUnit

class SignalWebSocketClientManager {
    companion object {
        private const val signalServerUrl = BuildConfig.BACKEND_DEBUG_WS_HOST_PORT + "/v1/signal_server"
        private const val timeOut = 30 * 1000
        private const val connectTimeout = 30 * 1000
        const val sampleBearerToken =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6InByYXRlZWtfd2VicnRjX3NpZ25hbF9zZXJ2ZXIiLCJuYW1lIjoiYWxpY2UiLCJpZCI6ImExIiwiZXhwIjoxNjEzNTQ4Mzc2fQ.jLbr3EO00pBCgHZqeuM8jACc4BK06I0hdS3qXjdW8t2ufwH09GTjSsvDURTnwQ5_d31A-xIqlgvAqQKJYy4YMQ"
    }

    private lateinit var webSocketClient: WebSocketClient

    fun initSignalClient(bearerToken: String): Boolean {
        val signalServerUri = URI(signalServerUrl)
        return try {
            createWebSocketClient(signalServerUri, "Bearer $bearerToken")
        }catch (e: InterruptedException){
            print(e.message)
            false
        }
    }

    fun sendMessage(message: String): Boolean{
        if (::webSocketClient.isInitialized && webSocketClient.isOpen) {
            return try {
                webSocketClient.send(message)
                true
            }catch(e: WebsocketNotConnectedException){
                print("WebsocketNotConnectedException")
                false
            }
        }else{
            print("Websocket client is not open")
            return false
        }
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
    ): Boolean {
        val httpHeader = HashMap<String, String>()
        httpHeader["Authorization"] = bearerToken
        webSocketClient = SignalWebSocketClient(signalServerUri, Draft_6455(),  httpHeader, connectTimeout)
        return webSocketClient.connectBlocking(timeOut.toLong(), TimeUnit.SECONDS)
    }
}