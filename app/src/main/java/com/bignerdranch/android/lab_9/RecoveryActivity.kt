package com.bignerdranch.android.lab_9

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "RecoveryActivity"

class RecoveryActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var recoveryButton: androidx.appcompat.widget.AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)

        email = findViewById(R.id.email_text)

        recoveryButton = findViewById(R.id.recovery_button)

        recoveryButton.setOnClickListener{
            if (email.text.toString() == " "){
                Toast.makeText(this, "You have to enter an email!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "If you written a correct email, you'll get a letter", Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object {
        fun newIntent(packageContext: Context) : Intent {
            return Intent(packageContext, RecoveryActivity::class.java)
        }
    }

}