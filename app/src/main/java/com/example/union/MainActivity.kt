package com.example.union

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.gson.JsonParser
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val TAG = "MyActivity"

    data class PostData(val email: String, val password: String)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button
        val btn_findPassword = findViewById<Button>(R.id.btn_findPassword)
        val btn_signIn = findViewById<Button>(R.id.btn_signIn)
        val btn_login = findViewById<Button>(R.id.btn_logIn)
        val btn_test = findViewById<Button>(R.id.btn_test)

        // editText
        val edt_email = findViewById<EditText>(R.id.edt_email)
        val edt_password = findViewById<EditText>(R.id.edt_password)

        btn_findPassword.setOnClickListener{
            startActivity(Intent(this, findPassword::class.java))
        }

        btn_signIn.setOnClickListener{
            startActivity(Intent(this, signIn::class.java))
        }

        btn_login.setOnClickListener{

            val email = edt_email.text.toString()
            val password = edt_password.text.toString()

            connection(email, password)
        }

        btn_test.setOnClickListener{
            startActivity(Intent(this, buttonTest::class.java))
        }
    }

    fun connection(email: String, password: String) {

        var resultCode: String = ""

        thread(start = true) {
            val url = URL("http://211.204.106.46/union/api/user/login") // Replace with your API endpoint URL

            val postData = "{\"email\":\"$email\",\"password\":\"$password\"}" // JSON data to send

            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                connection.doOutput = true

                val outputStream: OutputStream = connection.outputStream
                val writer = OutputStreamWriter(outputStream)
                writer.write(postData)
                writer.flush()

                val responseCode = connection.responseCode

                val response = connection.inputStream.bufferedReader().use { it.readLine() }

                val jsonParser = JsonParser()
                val jsonObject = jsonParser.parse(response).asJsonObject

                resultCode = jsonObject.get("resultCode").toString()

                Log.d(TAG, jsonObject.get("resultCode").toString())

                connection.disconnect()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        loginCheck(resultCode)
    }

    fun loginCheck(resultCode: String) {
        Log.d(TAG, resultCode)
    }
}