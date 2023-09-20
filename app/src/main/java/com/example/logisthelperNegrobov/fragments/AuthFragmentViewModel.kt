package com.example.logisthelperNegrobov.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


open class AuthFragmentViewModel: ViewModel(){
    val currentPhoneNumber: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val fullPhoneNumber: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val enteredPhoneNumberForTextView: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val enterFullKey: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val enteredKey: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val newAuthLink: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val currentAuthFragment: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}