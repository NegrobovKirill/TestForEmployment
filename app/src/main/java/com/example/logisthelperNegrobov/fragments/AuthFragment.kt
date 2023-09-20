package com.example.logisthelperNegrobov.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.logisthelperNegrobov.R
import com.example.logisthelperNegrobov.databinding.FragmentAuthBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class AuthFragment : BaseFragment()  {



    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

   private lateinit var auth: FirebaseAuth
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var stateVerificationId: String

    private val authFragmentViewModel: AuthFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentAuthBinding
    private var text: String = ""
    private var boolean: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()


        var currentUser = auth.currentUser

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("GFG", "onCodeSent: $verificationId")
                stateVerificationId = verificationId
                resendToken = token
            }

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("GFG" , "onVerificationCompleted Success")
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("GFG" , "onVerificationFailed  $e")
            }


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        openFrag(EnterNumberFragment.newInstance(), R.id.placeHolder)
        authFragmentViewModel.currentAuthFragment.value = "EnterNumberFragment"


        authFragmentViewModel.fullPhoneNumber.observe(activity as LifecycleOwner) {
            setButtonColors(it)
        }

        binding.bAuth.setOnClickListener {
            if (authFragmentViewModel.currentAuthFragment.value == "EnterKeyFragment"){
//                val code = authFragmentViewModel.enteredKey.value
//                val credentialAuth = PhoneAuthProvider.getCredential(stateVerificationId, code!!)
//                signInWithPhoneAuthCredential(credentialAuth)
                setButtonColors(false)
                fromAuthToMain()
            }else {

                openFrag(EnterKeyFragment.newInstance(), R.id.placeHolder)
                authFragmentViewModel.currentAuthFragment.value = "EnterKeyFragment"
                setButtonColors(false)
                //sendVerificationCode()
            }


        }



        authFragmentViewModel.enteredKey.observe(activity as LifecycleOwner) {
            if (authFragmentViewModel.enterFullKey.value!!){
                setButtonColors(true)
            }else
            {
                setButtonColors(false)
            }
        }
    }

    private fun sendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(authFragmentViewModel.currentPhoneNumber.value!!) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")

                    val user = task.result?.user

                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }

                }
            }

    }

    private fun fromAuthToMain(){
        findNavController().navigate(R.id.action_authFragment_to_mainFragment)
    }

    private fun setButtonColors(boolean: Boolean) {

        if (boolean) {
            binding.bAuth.apply {
                backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.black)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                isClickable = true
            }

        } else
            binding.bAuth.apply {
                backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.gray)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.middle_gray_blue))
                isClickable = false
            }

    }

    private fun openFrag(f: Fragment, idHolder: Int){

        parentFragmentManager.beginTransaction().replace(idHolder,f).commit()

    }

    companion object {

    }
}