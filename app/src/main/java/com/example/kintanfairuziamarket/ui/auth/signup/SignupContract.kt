package com.example.kintanfairuziamarket.ui.auth.signup

interface SignupContract {
    interface View: BaseView {
        fun onRegisterSuccess(loginResponse: LoginResponse,
                              view:android.view.View)
        fun onRegisterPhotoSuccess(view:android.view.View)
        fun onRegisterFailed(message:String)
    }
    interface Presenter : SignupContract, BasePresenter {
        fun submitRegister(registerRequest: RegisterRequest,
                           view:android.view.View)
        fun submitPhotoRegister(filePath:Uri, view:android.view.View)
    }
}
