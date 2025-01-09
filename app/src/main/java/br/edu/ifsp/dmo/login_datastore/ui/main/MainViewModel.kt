package br.edu.ifsp.dmo.login_datastore.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import br.edu.ifsp.dmo.login_datastore.data.DataStoreRepository
import br.edu.ifsp.dmo.login_datastore.data.User

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataStoreRepository(application)

    val loginPreferences: LiveData<Pair<Boolean, Boolean>> = repository.loginPreferences.asLiveData()
    val dataPreferences: LiveData<Pair<String, Long>> = repository.dataPreferences.asLiveData()

    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> = _loggedIn

    fun login(email: String, passwd: Long, saveLogin: Boolean, stayLoggedIn: Boolean) {
        if(User.autenticate(email, passwd)) {
            _loggedIn.value = true
            if(saveLogin || stayLoggedIn)
                savePreferences(email, passwd, saveLogin, stayLoggedIn)
            else
                savePreferences("", 0L, saveLogin, stayLoggedIn)
        } else {
            _loggedIn.value = false
        }
    }

    private fun savePreferences(email: String, passwd: Long, saveLogin: Boolean, stayLoggedIn: Boolean) {

    }
}