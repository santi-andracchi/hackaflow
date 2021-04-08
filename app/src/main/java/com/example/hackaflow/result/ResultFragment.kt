package com.example.hackaflow.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.hackaflow.R
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment: Fragment() {

    private val args: ResultFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResult()
    }

    private fun setResult(){
        if(args.isSuccess){
            resultTitle.text = getString(R.string.success_link)
            checkImage.setBackgroundResource(R.drawable.ic_baseline_check_24)
            redirectTitle.text = getString(R.string.redirect_msg)
        }
    }
}