package com.bignerdranch.android.lab_9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

private const val correctEmail = "Admin@gmail.com"
private const val correctPassword = "root"

class MainActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: androidx.appcompat.widget.AppCompatButton
    private lateinit var recoveryText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.login_text)
        password = findViewById(R.id.password_text)
        loginButton = findViewById(R.id.login_button)
        recoveryText = findViewById(R.id.login_forgot)

        loginButton.setOnClickListener{

            if(email.text.toString() == correctEmail && password.text.toString() == correctPassword){
                Toast.makeText(this, "You are successfully logged in", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_SHORT).show()
            }

        }

        recoveryText.setOnClickListener {
            val intent = RecoveryActivity.newIntent(this@MainActivity)
            startActivity(intent)
        }

    }




}