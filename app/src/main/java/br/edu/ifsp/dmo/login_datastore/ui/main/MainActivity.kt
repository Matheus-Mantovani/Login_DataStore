package br.edu.ifsp.dmo.login_datastore.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.login_datastore.R
import br.edu.ifsp.dmo.login_datastore.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.login_datastore.ui.logged.LoggedActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.loggedIn.observe(this, Observer {
            if(it) {
                navigateToLoggedActivity()
            } else {
                Toast.makeText(this, getString(R.string.erro_ao_fazer_login), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loginPreferences.observe(this, Observer {
            val (saveLogin, stayLoggedIn) = it
            if(stayLoggedIn) {
                navigateToLoggedActivity()
            }
            binding.checkboxSaveLogin.isChecked = saveLogin
            binding.checkboxStayLoggedin.isChecked = stayLoggedIn
        })

        viewModel.dataPreferences.observe(this, Observer {
            val (email, password) = it
            binding.textEmail.setText(email)
            if(email.isNotEmpty())
                binding.textPassword.setText(password.toString())
        })
    }

    private fun navigateToLoggedActivity() {
        if(!flag) {
            flag = true
            val mIntent = Intent(this, LoggedActivity::class.java)
            startActivity(mIntent)
            finish()
        }
    }

    private fun setupListeners() {
        binding.buttonLogin.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val email = binding.textEmail.text.toString()
        //nao sei se isso q eu fiz é algum tipo de gambiarra, mas tive q fazer pois quando eu deslogava, e clicava em logar novamente sem preencher nenhum dado, dava erro pq tentava conveter uma string vazia em Long
        //val passwd = binding.textPassword.text.toString().toLong() //estava dessa maneira anteriormente
        val passwd = binding.textPassword.text.toString().toLongOrNull() ?: 0L
        val saveLogin = binding.checkboxSaveLogin.isChecked
        val stayLoggedIn = binding.checkboxStayLoggedin.isChecked

        binding.textEmail.setText("")
        binding.textPassword.setText("")
        binding.checkboxSaveLogin.isChecked = false
        binding.checkboxStayLoggedin.isChecked = false

        if(email.isNotEmpty()) {
            viewModel.login(email, passwd, saveLogin, stayLoggedIn)
        } else {
            Toast.makeText(this, getString(R.string.preencha_todos_os_dados), Toast.LENGTH_SHORT).show()
        }
    }


}