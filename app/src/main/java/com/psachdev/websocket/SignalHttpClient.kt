package com.psachdev.websocket

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class SignalHttpClient {
    companion object {
        private const val loginUrl = BuildConfig.BACKEND_DEBUG_HTTP_HOST_PORT + "/v1/login"
        const val LoginError = "Login error!"
    }
    private val loginURL = URL(loginUrl)

    fun loginClient(username: String, id: String): String{
        val con = try {
            loginURL.openConnection() as HttpURLConnection
        }catch (e: IOException){
            print(e.message)
            null
        } ?: return LoginError

        var jwtToken = LoginError
        try {
            con.requestMethod = "POST"
            con.setRequestProperty("Content-Type", "application/json; utf-8")
            con.setRequestProperty("Accept", "text/plain")
            con.doOutput = true
            val jsonInputString = "{\"username\": \"$username\", \"id\": \"$id\"}"
            con.outputStream.use { os ->
                val input = jsonInputString.toByteArray(charset("utf-8"))
                os.write(input, 0, input.size)
            }

            BufferedReader(
                InputStreamReader(con.inputStream, "utf-8")
            ).use { br ->
                val response = StringBuilder()
                var responseLine: String? = null
                while (br.readLine().also { responseLine = it } != null) {
                    response.append(responseLine!!.trim { it <= ' ' })
                }
                jwtToken = response.toString()
                print(jwtToken)
            }
        }catch (t: Throwable){
            print(t.message)
        }
        con.disconnect()
        return jwtToken
    }
}