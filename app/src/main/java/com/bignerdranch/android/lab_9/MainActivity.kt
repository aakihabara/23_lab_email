package com.bignerdranch.android.lab_9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.widget.doAfterTextChanged

private const val TAG = "MainActivity"

private const val correctEmail = "Admin@gmail.com"
private const val correctPassword = "root"

class MainActivity : AppCompatActivity() {

    private lateinit var email: AutoCompleteTextView
    private lateinit var password: EditText
    private lateinit var loginButton: androidx.appcompat.widget.AppCompatButton
    private lateinit var recoveryText: TextView
    private lateinit var passwordError: TextView
    private lateinit var emailError: TextView

    private var somethingWrong = false
    private var emailCheck = arrayOf("gmail.com", "rambler.ru", "mail.ru", "yandex.ru", "yahoo.com",)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        email = findViewById(R.id.login_text)
        password = findViewById(R.id.password_text)
        loginButton = findViewById(R.id.login_button)
        recoveryText = findViewById(R.id.login_forgot)
        emailError = findViewById(R.id.email_error)
        passwordError = findViewById(R.id.password_error)

        loginButton.setOnClickListener{

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
                    }
                    else{
                        emailError.text = "Your email address is incorrect"
                        somethingWrong = true
                    }
                }
            }



            if(password.text.toString().isEmpty()){
                somethingWrong = true
                passwordError.text = "Password field is empty"
            } else {
                if(password.text.toString().length < 6){
                    passwordError.text = "Password is too small (less than 6 letters)"
                    somethingWrong = true
                } else if (password.text.toString().length > 14){
                    passwordError.text = "Password is too big (more than 14 letters)"
                    somethingWrong = true
                } else {
                    passwordError.text = ""
                }
            }

            if(!somethingWrong){
                if(email.text.toString() == correctEmail && password.text.toString() == correctPassword){
                    Toast.makeText(this, "You are successfully logged in", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }

        recoveryText.setOnClickListener {
            val intent = RecoveryActivity.newIntent(this@MainActivity)
            startActivity(intent)
        }

        var adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, emailCheck)
        email.setAdapter(adapter)

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
                    adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_dropdown_item_1line, emailList)
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




}