package com.psachdev.websocket

class NetworkManager(val signalHttpClient: SignalHttpClient = SignalHttpClient(),
                     val signalWebSocketClient: SignalWebSocketClientManager = SignalWebSocketClientManager()) {

    fun login(username: String, id: String): Boolean{
        val jwtToken = signalHttpClient.loginClient(username = username, id = id)
        return if(jwtToken.isNotEmpty() && jwtToken != SignalHttpClient.LoginError){
            return signalWebSocketClient.initSignalClient(jwtToken)
        }else{
            false
        }
    }

    fun sendMessage(message: String): Boolean{
        return signalWebSocketClient.sendMessage(message)
    }

    fun close(){
        signalWebSocketClient.destroySignalClient()
    }
}