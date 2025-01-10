package br.edu.ifsp.dmo.login_datastore.ui.logged

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo.login_datastore.databinding.ActivityLoggedBinding
import br.edu.ifsp.dmo.login_datastore.ui.main.MainActivity

class LoggedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoggedBinding
    private lateinit var viewModel: LoggedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoggedViewModel::class.java)
        binding.textMessage.text = "Bem-Vindo"

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.loggedOut.observe(this, Observer {
            if(it) {
                navigateToMainActivity()
            }
        })
    }

    private fun navigateToMainActivity() {
        val mIntent = Intent(this, MainActivity::class.java)
        startActivity(mIntent)
        finish()
    }

    private fun setupListeners() {
        binding.buttonLogout.setOnClickListener {
            handleLogout()
        }
    }

    private fun handleLogout() {
        viewModel.logout()
    }
}