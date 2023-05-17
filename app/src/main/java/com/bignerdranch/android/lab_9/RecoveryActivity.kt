package com.bignerdranch.android.lab_9

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "RecoveryActivity"

class RecoveryActivity : AppCompatActivity() {

    private lateinit var email: AutoCompleteTextView
    private lateinit var recoveryButton: androidx.appcompat.widget.AppCompatButton
    private lateinit var emailError: TextView

    private var somethingWrong = false
    private var emailCheck = arrayOf("gmail.com", "rambler.ru", "mail.ru", "yandex.ru", "yahoo.com",)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)

        email = findViewById(R.id.email_text)
        recoveryButton = findViewById(R.id.recovery_button)
        emailError = findViewById(R.id.email_recovery_error)

        var adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, emailCheck)
        email.setAdapter(adapter)

        recoveryButton.setOnClickListener{
            somethingWrong = false

            if(email.text.toString().isEmpty()){
                emailError.text = "Email field is empty"
                somethingWrong = true
            } else {
                for (end in emailCheck) {
                    if (email.text.toString().endsWith("@$end")) {
                        somethingWrong = false
                        emailError.text = ""
                        break
                    } else{
                        emailError.setText("Your email address is incorrect")
                        somethingWrong = true
                    }
                }
            }


            if (!somethingWrong){
                Toast.makeText(this@RecoveryActivity, "If you written a correct email, you'll get a letter", Toast.LENGTH_SHORT).show()
            }
        }

        email.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().contains("@")) {
                    val emailList = mutableListOf<String>()
                    val domainText = s.toString().substring(s.toString().indexOf("@") + 1)
                    val temp = emailCheck.filter { it.startsWith(domainText) }
                    for (end in temp) {
                        val email = s.toString().replaceAfter("@", end)
                        emailList.add(email)
                    }
                    adapter = ArrayAdapter(this@RecoveryActivity, android.R.layout.simple_dropdown_item_1line, emailList)
                    email.setAdapter(adapter)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //nothing
            }

            override fun afterTextChanged(s: Editable?) {
                //nothing
            }

        })

    }

    companion object {
        fun newIntent(packageContext: Context) : Intent {
            return Intent(packageContext, RecoveryActivity::class.java)
        }
    }

}