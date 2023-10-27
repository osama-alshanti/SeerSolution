package com.oshanti.seersolution.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.oshanti.seersolution.R
import com.oshanti.seersolution.base.UiState
import com.oshanti.seersolution.data.model.User
import com.oshanti.seersolution.databinding.ActivityLoginBinding
import com.oshanti.seersolution.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val loginVM: LoginViewModel by viewModels()

    private var userId: Int? = null
    private var password: String? = null

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
        setObserver()

    }

    private fun setUp() {
        binding.igvLogo.setImageResource(R.drawable.seer_logo_ic)

        binding.button.setOnClickListener {
            insertUser()
        }

    }

    private fun insertUser() {
        password = binding.editPassword.text.toString()

        if (binding.editUserId.text.isNullOrBlank()) {
            binding.editUserId.error = "userId is Required!"
            return
        }
        userId = binding.editUserId.text.toString().toInt()

        if (password.isNullOrBlank()) {
            binding.editPassword.error = "password is Required!"
            return
        }

        val user = User(userId = userId!!, password = password!!)
        loginVM.insertUser(user)

    }

    private fun setObserver() {
        loginVM.dbInsertState.observe(this) {
            when (it) {
                is UiState.Success -> {
                    val state = it.data.toInt()
                    if (state == -1) {
                        Toast.makeText(this, "Failed registered, try again", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        loginSuccess()
                    }
                }
                is UiState.Error -> {
                    Log.d(TAG, "setObserver: ${it.msg}")
                }
                is UiState.Loading -> {

                }
            }
        }

        loginVM.getUser.observe(this) {
            when (it) {
                is UiState.Success -> {
                    val userDb = it.data
                    if (password == userDb.password && userId == userDb.userId) {
                        loginSuccess()
                    } else if (password != userDb.password) {
                        binding.button.text = "login"
                        Toast.makeText(this, "Password incorrect!", Toast.LENGTH_SHORT).show()
                    }
                }
                is UiState.Error -> {
                    Log.d(TAG, "setObserver: ${it.msg}")
                }
                is UiState.Loading -> {

                }
            }
        }
    }

    private fun loginSuccess() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}