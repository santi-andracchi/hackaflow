package com.example.hackaflow.validationQr

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hackaflow.R
import com.example.hackaflow.data.UIState
import com.example.hackaflow.extensions.toast
import com.example.hackaflow.utils.ViewUtils
import com.example.hackaflow.validationCode.ValidationCodeViewModel
import kotlinx.android.synthetic.main.fragment_validation_qr.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.koin.android.viewmodel.ext.android.viewModel

class ValidationQRFragment : Fragment() {

    private val viewModel: ValidationCodeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_validation_qr, container, false)
    }

    private val containerView: ConstraintLayout by lazy { fragment_add_qr_device_container }

    private var torchState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun setObserver() {

        viewModel.validationState.observe(viewLifecycleOwner, {
            when (it) {
                is UIState.Loading -> {
                    loading.visibility = View.VISIBLE
                }
                is UIState.ErrorMessage -> {
                    loading.visibility = View.GONE
                    toast(it.data, Toast.LENGTH_LONG)
                    initCamera()
                }
                is UIState.Success -> {
                    navigateToResult()
                }
                is UIState.Error -> {
                    loading.visibility = View.GONE
                    toast(resources.getString(R.string.connection_error), Toast.LENGTH_LONG)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        if (!checkCameraPermission()) {
            askForCameraPermission()
        } else {
            setupControls()
            setObserver()
        }
    }

    override fun onPause() {
        super.onPause()
        zxscan.stopCamera()
    }


    private fun initCamera() {
        zxscan.startCamera()
        zxscan.setResultHandler(ZXingScannerView.ResultHandler { rawResult ->
            viewModel.validateCode(rawResult.toString())
        })
    }

    private fun setupControls() {

        lifecycleScope.launch {
            delay(400)
            initCamera()
        }
        context?.apply {
            ViewUtils.expandTouchArea(
                this,
                containerView,
                imageViewFlashLight,
                15,
                15,
                15,
                15)
        }

        imageViewFlashLight.setOnClickListener {
            torchState = !torchState
            zxscan.toggleFlash()

            if (torchState) {
                imageViewFlashLight.setImageResource(R.drawable.ic_torch_on)
            } else {
                imageViewFlashLight.setImageResource(R.drawable.ic_torch_off)
            }
        }
        buttonManualEntry.setOnClickListener {
            navigateToCodeFragment()
        }
    }

    private fun checkCameraPermission(): Boolean {
        return (activity?.applicationContext?.let {
            ContextCompat.checkSelfPermission(
                it,
                android.Manifest.permission.CAMERA
            )
        } == PackageManager.PERMISSION_GRANTED)
    }

    private fun askForCameraPermission() {
        requestPermissions(
            arrayOf(android.Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        setupControls()
                        setObserver()
                    } else {
                        navigateToCodeFragment()
                    }
                }
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1001
    }

    private fun navigateToCodeFragment() {
        val action = ValidationQRFragmentDirections.actionNavigationValidationQrToValidationCode()
        findNavController().navigate(action)
    }

    private fun navigateToResult() {
        val action = ValidationQRFragmentDirections.actionValidationQRFragmentToResultFragment()
        findNavController().navigate(action)
    }
}


