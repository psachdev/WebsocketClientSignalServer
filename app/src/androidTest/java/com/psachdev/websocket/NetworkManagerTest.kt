package com.psachdev.websocket

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.HashMap

@RunWith(AndroidJUnit4::class)
class NetworkManagerTest {

    @Test
    fun testLogin() {
        val networkManager = NetworkManager()
        val testUserName = "sampleUsername"
        val testId = UUID.randomUUID().toString().replace("-", "")
        val loginResult = networkManager.login(testUserName, testId)
        Assert.assertTrue(loginResult)
        networkManager.close()
    }

    @Test
    fun testSendMessage() {
        val networkManager = NetworkManager()
        val testUserName = "sampleUsername"
        val testId = UUID.randomUUID().toString().replace("-", "")
        val loginResult = networkManager.login(testUserName, testId)
        Assert.assertTrue(loginResult)

        val data = HashMap<String, String>()
        data["userId"] = "login"
        data["messageType"] = "a" + Date().time
        var jsonObject = JSONObject(data as Map<String, String>)

        var messageSent = networkManager.sendMessage(message = jsonObject.toString())
        Assert.assertTrue(messageSent)

        data["messageType"] = "ax" +  Date().time
        jsonObject = JSONObject(data as Map<String, String>)
        messageSent = networkManager.sendMessage(message = jsonObject.toString())
        Assert.assertTrue(messageSent)

        data["messageType"] = "ay" +  Date().time
        jsonObject = JSONObject(data as Map<String, String>)
        messageSent = networkManager.sendMessage(message = jsonObject.toString())
        Assert.assertTrue(messageSent)

        networkManager.close()
    }
}