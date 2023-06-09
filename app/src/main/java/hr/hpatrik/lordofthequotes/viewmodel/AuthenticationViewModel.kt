package hr.hpatrik.lordofthequotes.viewmodel;

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import hr.hpatrik.lordofthequotes.repository.AuthenticationRepository
import hr.hpatrik.lordofthequotes.view.auth.AuthenticationState
import isValidPassword

public class AuthenticationViewModel : ViewModel() {

    private val _authenticationState = mutableStateOf(
        AuthenticationState()
    )

    val authenticationState: State<AuthenticationState>
        get() = _authenticationState

    fun onEmailChanged(email: String) {
        _authenticationState.value = _authenticationState.value.copy(
            email = email,
            isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        )
    }

    fun onPasswordChanged(password: String) {
        _authenticationState.value = _authenticationState.value.copy(
            password = password,
            isPasswordValid = password.isValidPassword()
        )
    }

    fun logIn(onSuccess: () -> Unit, onFail: () -> Unit) {
        AuthenticationRepository.logIn(
            _authenticationState.value.email,
            _authenticationState.value.password,
            onSuccess = onSuccess,
            onFail = onFail
        )
    }

    fun logInAnon(onSuccess: () -> Unit, onFail: () -> Unit) {
        AuthenticationRepository.logInAnon(
            onSuccess = onSuccess,
            onFail = onFail
        )
    }

    fun register(onSuccess: () -> Unit, onFail: () -> Unit) {
        AuthenticationRepository.register(
            _authenticationState.value.email,
            _authenticationState.value.password,
            onSuccess = onSuccess,
            onFail = onFail
        )
    }
}
