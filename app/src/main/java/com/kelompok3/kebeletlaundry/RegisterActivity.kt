package com.kelompok3.kebeletlaundry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import retrofit2.HttpException
import java.io.IOException

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val api = RetrofitInstance.getInstance().create(TdgApi::class.java)

        findViewById<Button>(R.id.button).setOnClickListener { _ ->
            val email = findViewById<EditText>(R.id.editTextEmailAddress).text.toString()
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString()
            val name = findViewById<EditText>(R.id.editTextName).text.toString()
            val phone = findViewById<EditText>(R.id.editTextPhone).text.toString()
            val address = findViewById<EditText>(R.id.editTextAddress).text.toString()

            lifecycleScope.launchWhenCreated {
                val response = try {
                    api.postRegister(PostRegister(email, password, name, phone, address))
                } catch(e: IOException) {
                    Toast.makeText(this@RegisterActivity, "IOException, you might not have internet connection", Toast.LENGTH_SHORT).show()
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
                    return@launchWhenCreated
                }
                if (response.body() != null) {
                    Toast.makeText(this@RegisterActivity, "User registered \uD83C\uDF89", Toast.LENGTH_SHORT).show()
                    val activityLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(activityLogin)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Request rejected: invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}