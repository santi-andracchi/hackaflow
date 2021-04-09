package com.example.hackaflow.validationQr

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hackaflow.R
import com.example.hackaflow.utils.ViewUtils
import kotlinx.android.synthetic.main.fragment_validation_qr.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ValidationQRFragment : Fragment() {

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

    override fun onResume() {
        super.onResume()

        if (!checkCameraPermission()) {
            askForCameraPermission()
        } else {
            setupControls()
        }
    }

    override fun onPause() {
        super.onPause()
        zxscan.stopCamera()
    }

    private fun setupControls() {

        zxscan.setResultHandler(ZXingScannerView.ResultHandler { rawResult ->
            navigateToResult()
        })

        zxscan.startCamera()

        context?.apply {
            ViewUtils.expandTouchArea(
                this,
                containerView,
                imageViewFlashLight,
                15,
                15,
                15,
                15
            )
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
            navigateToSerialNumber()
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
                    } else {
                        navigateToSerialNumber()
                    }
                }
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1001
    }

    private fun navigateToSerialNumber() {
        val action = ValidationQRFragmentDirections.actionNavigationValidationQrToValidationCode()
        findNavController().navigate(action)
    }

    private fun navigateToResult() {
        val action = ValidationQRFragmentDirections.actionValidationQRFragmentToResultFragment()
        findNavController().navigate(action)
    }
}


