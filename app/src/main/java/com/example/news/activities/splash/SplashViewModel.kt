package com.example.news.activities.splash

import androidx.lifecycle.ViewModel

class SplashViewModel: ViewModel() {

     var delegate: OnSplashNavigations?=null
    fun navigateToHomeActivity(){
        if (delegate!=null) {
            delegate?.navigatoToHomeActivity()
        }
    }

}

fun interface OnSplashNavigations{
    fun navigatoToHomeActivity()

}
