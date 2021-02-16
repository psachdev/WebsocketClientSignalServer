package com.psachdev.websocket

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class SignalWebSocketClient(serverURI: URI,
                            draft: Draft,
                            headers: Map<String, String>,
                            connectTimeout: Int): WebSocketClient(serverURI, draft, headers, connectTimeout) {
    private val tag: String = SignalWebSocketClient::class.simpleName?:"DefaultTag"

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d(tag, "onOpen ${handshakedata?.httpStatus} / ${handshakedata?.httpStatusMessage}")
    }

    override fun onMessage(message: String?) {
        Log.d(tag, "onMessage $message")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d(tag, "onClose $code, $reason, $remote")
    }

    override fun onError(ex: Exception?) {
        Log.d(tag, "onError ${ex?.localizedMessage}")
    }
}