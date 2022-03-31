package com.example.kintanfairuziamarket.ui.auth.signup

class SignupPresenter (private val view:SignupContract.View) :
    SignupContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }
    override fun submitRegister(registerRequest: RegisterRequest, viewParms:View)
    {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.register(
            registerRequest.name,
            registerRequest.email,
            registerRequest.password,
            registerRequest.password_confirmation,
            registerRequest.address,
            registerRequest.city,
            registerRequest.houseNumber,
            registerRequest.phoneNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onRegisterSuccess(it1,
                            viewParms) }
                    } else {
                        view.onRegisterFailed(it.meta?.message.toString())
                    }
                },
                {
                    view.dismissLoading()
                    view.onRegisterFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }
    override fun submitPhotoRegister(filePath: Uri, viewParms:View) {
        view.showLoading()
        var profileImageFile = File(filePath.path)
        val profileImageRequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), profileImageFile)
        val profileImageParms = MultipartBody.Part.createFormData("file",
            profileImageFile.name, profileImageRequestBody)
        val disposable = HttpClient.getInstance().getApi()!!.registerPhoto(
            profileImageParms)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 ->
                            view.onRegisterPhotoSuccess(viewParms) }
                    } else {
                        view.onRegisterFailed(it.meta?.message.toString())
                    }
                },
                {
                    view.dismissLoading()
                    view.onRegisterFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }
    override fun subscribe() {
    }
    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}