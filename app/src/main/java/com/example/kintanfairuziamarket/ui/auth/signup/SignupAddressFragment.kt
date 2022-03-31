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
 * Use the [SignupAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupAddressFragment : Fragment(), SignupContract.View {
    private lateinit var data:RegisterRequest
    lateinit var presenter: SignupPresenter
    var progressDialog:Dialog?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_address, container,
            false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SignupPresenter(this)
        data = arguments?.getParcelable<RegisterRequest>("data")!!
        initDummy()
        initListener()
        initView()
    }
    private fun initListener() {
        btnSignUpNow.setOnClickListener {
            var phone = etPhoneNumber.text.toString()
            var address = etAddress.text.toString()
            var houseNumber = etHouseNumber.text.toString()
            var city = etCity.text.toString()
            data.let {
                it.address = address
                it.city = city
                it.houseNumber = houseNumber
                it.phoneNumber = phone
            }
            if (phone.isNullOrEmpty()) {
                etPhoneNumber.error = "Silahkan masukkan nomor phone"
                etPhoneNumber.requestFocus()
            } else if (address.isNullOrEmpty()) {
                etAddress.error = "Silahkan masukkan address"
                etAddress.requestFocus()
            } else if (houseNumber.isNullOrEmpty()) {
                etHouseNumber.error = "Silahkan masukkan house number"
                etHouseNumber.requestFocus()
            } else if (city.isNullOrEmpty()) {
                etCity.error = "Silahkan masukkan city"
                etCity.requestFocus()
            } else {
                presenter.submitRegister(data, it)
            }
        }
    }
    private fun initDummy(){
        etPhoneNumber.setText("085758145632")
        etAddress.setText("Jalan Jendelan Gajah")
        etHouseNumber.setText("155")
        etCity.setText("Depok")
    }
    override fun onRegisterSuccess(loginResponse: LoginResponse, view: View) {
        FoodMarket.getApp().setToken(loginResponse.access_token)
        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        FoodMarket.getApp().setUser(json)
        if (data.filePath == null) {
            Navigation.findNavController(view)
                .navigate(R.id.action_signup_success, null)
            (activity as AuthActivity).toolbarSignUpSuccess()
        } else {
            Log.v("tamvan", FoodMarket.getApp().getToken())
            presenter.submitPhotoRegister(data.filePath!!, view)
        }
    }
    override fun onRegisterPhotoSuccess(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_signup_success, null)
        (activity as AuthActivity).toolbarSignUpSuccess()
    }
    override fun onRegisterFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
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
