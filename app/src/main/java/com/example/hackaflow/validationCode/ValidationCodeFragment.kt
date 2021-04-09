package com.example.hackaflow.validationCode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.hackaflow.R
import com.example.hackaflow.data.UIState
import com.example.hackaflow.extensions.hideKeyboard
import com.example.hackaflow.extensions.toast
import kotlinx.android.synthetic.main.fragment_validation.*
import org.koin.android.viewmodel.ext.android.viewModel

class ValidationCodeFragment: Fragment() {

    private val viewModel: ValidationCodeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_validation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObserver()
    }

    private fun setListeners() {
        pinCodeEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                if(cs.length == 5){
                    viewModel.validateCode(cs)
                    requireActivity().hideKeyboard()
                } else setNormalColors()
            }
            override fun beforeTextChanged(s: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(arg0: Editable) {
            }
        })

        scanCodeButton.setOnClickListener {
            findNavController().navigate(ValidationCodeFragmentDirections.actionNavigationValidationCodeToValidationQr())
        }
    }

    private fun setObserver() {

        viewModel.validationState.observe(viewLifecycleOwner, {
            when (it) {
                is UIState.ErrorMessage ->{
                    toast(it.data, Toast.LENGTH_LONG)
                    setErrorColors()
                }

                is UIState.Success -> {
                    val action = ValidationCodeFragmentDirections.actionValidationCodeFragmentToResultFragment(isSuccess = true)
                    requireActivity().findNavController(R.id.nav_host_fragment).navigate(action)
                }
                is UIState.Error -> {
                    toast(resources.getString(R.string.connection_error), Toast.LENGTH_LONG)
                    setErrorColors()
                }
            }
        })
    }

    private fun setErrorColors(){
        pinCodeEditText.setLineColor(ContextCompat.getColor(requireContext(), R.color.error))
        pinCodeEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
    }

    private fun setNormalColors(){
        pinCodeEditText.setLineColor(ContextCompat.getColor(requireContext(), R.color.white))
        pinCodeEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }
}