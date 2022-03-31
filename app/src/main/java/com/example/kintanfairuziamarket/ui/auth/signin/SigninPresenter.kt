package com.example.kintanfairuziamarket.ui.auth.signin

class SigninPresenter (private val view:SigninContract.View) :
    SigninContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }
    override fun subimtLogin(email: String, password: String) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.login(email,
            password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onLoginSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onLoginFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onLoginFailed(it.message.toString())
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
