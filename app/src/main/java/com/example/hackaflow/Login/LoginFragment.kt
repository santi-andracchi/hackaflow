package com.example.hackaflow.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hackaflow.R
import com.example.hackaflow.data.UIState
import com.example.hackaflow.extensions.toast
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private val viewModel by inject<LoginViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLogin()

    }

    private fun observeLogin() {
        viewModel.loginState.observe(viewLifecycleOwner, {
            when (it) {
                is UIState.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
                is UIState.Success -> {
                    //findNavController().navigate(R.id.)
                }
                is UIState.Error -> {
//                    binding.progressBar.visibility = View.INVISIBLE
                    toast(
                        resources.getString(R.string.connection_error),
                        Toast.LENGTH_LONG
                    )
                }
            }
        })
    }
}