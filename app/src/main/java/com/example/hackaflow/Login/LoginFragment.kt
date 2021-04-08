package com.example.hackaflow.Login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.hackaflow.R
import com.example.hackaflow.data.UIState
import com.example.hackaflow.extensions.hideKeyboard
import com.example.hackaflow.extensions.toast
import kotlinx.android.synthetic.main.fragment_login.*
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
    }

    private fun setupListeners(){
        login.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()

            if(username.isNotEmpty() && password.isNotEmpty()){
                viewModel.login(username, password)
                requireActivity().hideKeyboard()
            }
            else {
                toast(resources.getString(R.string.error_fields), Toast.LENGTH_LONG)
            }

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
}