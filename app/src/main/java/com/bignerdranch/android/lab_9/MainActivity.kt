package com.bignerdranch.android.lab_9

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import java.net.URL

private const val TAG = "MainActivity"

private const val correctEmail = "Admin@gmail.com"
private const val correctPassword = "root123"

class MainActivity : AppCompatActivity() {

    private lateinit var email: AutoCompleteTextView
    private lateinit var password: EditText
    private lateinit var loginButton: androidx.appcompat.widget.AppCompatButton
    private lateinit var recoveryText: TextView
    private lateinit var passwordError: TextView
    private lateinit var emailError: TextView

    private var somethingWrong = false
    private var emailCheck = arrayOf("gmail.com", "rambler.ru", "mail.ru", "yandex.ru", "yahoo.com",)

    class CheckDomainTask(private val listener: OnDomainCheckedListener):
        AsyncTask<String, Void, Boolean>(){

            interface OnDomainCheckedListener {
                fun onDomainChecked(isValid: Boolean)
            }

        override fun doInBackground(vararg params: String): Boolean {
            val domain = params[0]
            val url = "https://$domain"
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .head()
                .build()

            try{
                val response: Response = client.newCall(request).execute()
                return response.isSuccessful
            } catch (e: Exception){
                e.printStackTrace()
            }

            return false
        }

        override fun onPostExecute(result: Boolean) {
            listener.onDomainChecked(result)
        }

        }


    private fun isEmailValid (email: String): Boolean {
        val domain = email.substringAfter("@")
        var url = "http://$domain"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .head()
            .build()

        val response = client.newCall(request).execute()
        return response.isSuccessful
    }

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
                val task = CheckDomainTask(object: CheckDomainTask.OnDomainCheckedListener{
                    override fun onDomainChecked(isValid: Boolean) {
                        if(isValid){
                            somethingWrong = false
                            emailError.text = ""
                        } else {
                            emailError.text = "Your domain is incorrect"
                            somethingWrong = true
                        }
                    }
                })
                task.execute(email.text.toString())
                Thread.sleep(3000)
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
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
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