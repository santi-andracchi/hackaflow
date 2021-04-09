package com.example.hackaflow.Login

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.hackaflow.R
import com.example.hackaflow.data.UIState
import com.example.hackaflow.extensions.hideKeyboard
import com.example.hackaflow.extensions.showKeyboard
import com.example.hackaflow.extensions.toast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_validation.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject


@ExperimentalCoroutinesApi
class LoginFragment : Fragment() {

    private val viewModel by inject<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLogin()
        setupListeners()
        setValidations()
    }

    private fun setupListeners(){
        login.setOnClickListener {
            val username = txtEmail.text.toString()
            val password = txtPassword.text.toString()
            viewModel.login(username, password)
            requireActivity().hideKeyboard()
        }
    }

    private fun observeLogin() {
        viewModel.loginState.observe(viewLifecycleOwner, {
            when (it) {
                is UIState.Loading -> {
                    loading.visibility = View.VISIBLE
                    login.visibility = View.GONE
                }
                is UIState.Success -> {
                    val action = LoginFragmentDirections.navigationLoginToValidation()
                    requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
                }
                is UIState.Error -> {
                    loading.visibility = View.GONE
                    login.visibility = View.VISIBLE
                    toast(resources.getString(R.string.connection_error), Toast.LENGTH_LONG)
                }
            }
        })
    }


    private fun setValidations() {

        val email = txtEmail
        val password = txtPassword
        val button = login

        email.setOnFocusChangeListener { _, hasFocus ->

            requireActivity().showKeyboard()
            val emailText = email.text.toString()
            val validEmail = !TextUtils.isEmpty(emailText)
                    && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()

            if (emailText.isEmpty()) {
                txtEmailLayout.isErrorEnabled = false
                return@setOnFocusChangeListener
            }
            if (!hasFocus && !validEmail) {
                txtEmailLayout.error = getString(R.string.verify_email_format)
            } else {
                txtEmailLayout.isErrorEnabled = false
            }
        }

        password.setOnFocusChangeListener { _, hasFocus ->
            requireActivity().showKeyboard()

            val passwordText = password.text.toString()
            val validLength = passwordText.length > 5

            if (passwordText.isEmpty() || !validLength) {
                txtPasswordlLayout.isErrorEnabled = false
                //return@setOnFocusChangeListener
            }
            if (!hasFocus && !validLength && passwordText.isNotEmpty()) {
                txtPasswordlLayout.isErrorEnabled = true
                txtPasswordlLayout.error = getString(R.string.login_wrong_password)
            } else {
                txtPasswordlLayout.isErrorEnabled = false
                txtPasswordlLayout.helperText = " "
            }
        }

        val watcher = (object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val emailText = email.text.toString()
                val validEmail = !TextUtils.isEmpty(emailText)
                        && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()

                val passwordText = password.text.toString()
                val validLength = passwordText.length > 5 && !passwordText.isEmpty()

                button.isEnabled = validEmail && validLength
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        email.addTextChangedListener(watcher)
        password.addTextChangedListener(watcher)
    }
}