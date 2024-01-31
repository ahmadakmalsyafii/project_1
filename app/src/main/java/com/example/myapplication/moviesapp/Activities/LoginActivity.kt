package com.example.myapplication.moviesapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.moviesapp.R

class LoginActivity : AppCompatActivity() {
    private lateinit var userEdit: EditText
    private lateinit var passEdit: EditText
    private lateinit var logBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }
    //login code
    private fun initView() {
        userEdit = findViewById(R.id.editTextText)
        passEdit = findViewById(R.id.editTextPassword)
        logBtn = findViewById(R.id.loginBtn)

        logBtn.setOnClickListener { v ->
            if (userEdit.text.toString().isEmpty() || passEdit.text.toString().isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Please Fill your user and password",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (userEdit.text.toString() == "test" && passEdit.text.toString() == "test") {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Your user and password is not correct",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}