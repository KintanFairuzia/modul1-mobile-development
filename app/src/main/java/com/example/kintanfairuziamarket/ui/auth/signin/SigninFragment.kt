package com.example.kintanfairuziamarket.ui.auth.signin

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
 * Use the [SigninFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SigninFragment : Fragment(), SigninContract.View {
    lateinit var presenter: SigninPresenter
    var progressDialog : Dialog? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SigninPresenter(this)
        if (!FoodMarket.getApp().getToken().isNullOrEmpty()) {
            val home = Intent(activity, MainActivity::class.java)
            startActivity(home)
            activity?.finish()
        }
        initDummy()
        initView()
        btnSignup.setOnClickListener {
            val signup = Intent(activity, AuthActivity::class.java)
            signup.putExtra("page_request", 2)
            startActivity(signup)
        }
        btnSignin.setOnClickListener {
            var email = etEmail.text.toString()
            var password = etPassword.text.toString()
            if (email.isNullOrEmpty()) {
                etEmail.error = "Silahkan masukkan email Anda"
                etEmail.requestFocus()
            } else if (password.isNullOrEmpty()) {
                etPassword.error = "Silahkan masukkan password Anda"
                etPassword.requestFocus()
            } else {
                presenter.subimtLogin(email, password)
            }
        }
    }
    override fun onLoginSuccess(loginResponse: LoginResponse) {
        FoodMarket.getApp().setToken(loginResponse.access_token)
        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        FoodMarket.getApp().setUser(json)
        val home = Intent(activity, MainActivity::class.java)
        startActivity(home)
        activity?.finish()
    }
    override fun onLoginFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
    private fun initDummy() {
        etEmail.setText("jennie.kim@blackpink.com")
        etPassword.setText("12345678")
    }
    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)
        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }
    override fun showLoading() {
        progressDialog?.show()
    }
    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}