package com.example.kintanfairuziamarket.ui.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kintanfairuziamarket.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment() {
    var filePath:Uri ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDummy()
        initListener()
    }
    private fun initListener() {
        ivProfil.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .start()
        }
        btnContinue.setOnClickListener {
            var fullname = etFullname.text.toString()
            var email = etEmail.text.toString()
            var pass = etPassword.text.toString()
            if (fullname.isNullOrEmpty()) {
                etFullname.error = "Silahkan masukkan fullname"
                etFullname.requestFocus()
            } else if (email.isNullOrEmpty()) {
                etEmail.error = "Silahkan masukkan email"
                etEmail.requestFocus()
            } else if (pass.isNullOrEmpty()) {
                etPassword.error = "Silahkan masukkan pass"
                etPassword.requestFocus()
            } else {
                var data = RegisterRequest(
                    fullname,
                    email,
                    pass,
                    pass,
                    "", "","","",
                    filePath
                )
                var bundle = Bundle()
                bundle.putParcelable("data", data)
                Navigation.findNavController(it)
                    .navigate(R.id.action_signup_address, bundle)
                (activity as AuthActivity).toolbarSignUpAddress()
            }
        }
    }
    private fun initDummy() {
        etFullname.setText("Jennie Kim White")
        etEmail.setText("jennie.kim@blackpink.com")
        etPassword.setText("12345678")
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:
    Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            filePath = data?.data
            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfil)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(context, ImagePicker.getError(data),
                Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}

