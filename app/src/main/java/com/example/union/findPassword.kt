package com.example.union

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.JsonParser
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class findPassword : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_password)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "비밀번호 찾기"

        val edt_tempEmail = findViewById<TextView>(R.id.edt_email)
        val btn_tempPwd = findViewById<Button>(R.id.btn_tempPwd)

        btn_tempPwd.setOnClickListener() {
            val email: String = edt_tempEmail.toString()
            connect(email)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    fun connect (email: String) {

        var resultCode: String = ""

        thread(start = true) {
            val url = URL("http://211.204.106.46/union/api/user/issue/temporary-password") // Replace with your API endpoint URL

            val postData = "{\"email\":\"$email\"}" // JSON data to send

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

                resultCode = jsonObject.get("password").toString()

                connection.disconnect()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}