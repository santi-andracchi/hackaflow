package com.example.hackaflow.validationCode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hackaflow.R
import kotlinx.android.synthetic.main.fragment_validation.*
import org.koin.android.viewmodel.ext.android.viewModel


class ValidationCodeFragment: Fragment() {

    private val viewModel: ValidationCodeViewModel by viewModel()
    private var code: CharSequence = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_validation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners(){
        pinCodeEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                code = cs
            }
            override fun beforeTextChanged(s: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(arg0: Editable) {
            }
        })

        scanCodeButton.setOnClickListener {
            if(code.length == 5) viewModel.validateCode(code)
            else Toast.makeText(requireContext(), "Complete todos los casilleros", Toast.LENGTH_SHORT).show()
        }
    }
}