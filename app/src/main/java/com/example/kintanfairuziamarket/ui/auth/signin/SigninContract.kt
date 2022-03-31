package com.example.kintanfairuziamarket.ui.auth.signin

interface SigninContract {
    interface View: BaseView {
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onLoginFailed(message:String)
    }
    interface Presenter : SigninContract, BasePresenter {
        fun subimtLogin(email:String, password:String)
    }
}