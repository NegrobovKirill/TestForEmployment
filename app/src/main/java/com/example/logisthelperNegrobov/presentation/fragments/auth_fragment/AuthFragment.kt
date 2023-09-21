package com.example.logisthelperNegrobov.presentation.fragments.auth_fragment

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
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
import com.example.logisthelperNegrobov.presentation.fragments.BaseFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class AuthFragment : BaseFragment()  {


    private var timer: CountDownTimer? = null
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var auth: FirebaseAuth
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    var stateVerificationId: String? = null



    private val authFragmentViewModel: AuthFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentAuthBinding
    private var text: String = ""
    private var boolean: Boolean = true


    private var codeSendMessage: String? = null
    private var authSuccessMessage: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        //var currentUser = auth.currentUser

        openFrag(EnterNumberFragment.newInstance(), R.id.placeHolder)

        authFragmentViewModel.currentAuthFragment.value = "EnterNumberFragment"

        authFragmentViewModel.fullPhoneNumber.observe(activity as LifecycleOwner) {
            setButtonColors(it)
        }

        authFragmentViewModel.currentAuthFragment.observe(activity as LifecycleOwner){
            if(it == "EnterNumberFragment")
            {
                binding.tVSentNewCode.visibility = View.GONE
            }else
            {
                binding.tVSentNewCode.visibility = View.VISIBLE
            }
        }

        binding.bAuth.setOnClickListener {
            if (authFragmentViewModel.currentAuthFragment.value == "EnterKeyFragment") {
                val code = authFragmentViewModel.enteredKey.value
                if (codeSendMessage == "codeSendSuccess") {

                    val credentialAuth = PhoneAuthProvider.getCredential(stateVerificationId!!, code!!)
                    signInWithPhoneAuthCredential(credentialAuth)

//                        setButtonColors(false)
//                        fromAuthToMain()

                }

            }else {

                openFrag(EnterKeyFragment.newInstance(), R.id.placeHolder)
                authFragmentViewModel.currentAuthFragment.value = "EnterKeyFragment"

                binding.tVSentNewCode.visibility = View.VISIBLE

                setButtonColors(false)
                sendVerificationCode()

            }


        }

        authFragmentViewModel.newAuthLink.observe(activity as LifecycleOwner){
            if (it){
                binding.tVSentNewCode.setOnClickListener {
                    if (binding.tVSentNewCode.isClickable){

                        sendVerificationCode()


                    }
                }
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

    private fun KeyMessege() {

        isNewError(false)
        val message: String = getString(R.string.key_message)
        timer?.cancel()
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(p0: Long) {

                val newMessage = if (p0 > 10000){
                    message + " (00:" + p0.toString().substring(0,2) + ")"
                }else if(p0 in 1000..10000){
                    message + " (00:0" + p0.toString().substring(0,1) + ")"
                }else {
                    message + " (00:00)"
                }

                binding.tVSentNewCode.text = newMessage
            }

            override fun onFinish() {
                isNewError(true)
                binding.tVSentNewCode.text = message


            }

        }.start()

    }

    private fun isNewError(boolean: Boolean){
        authFragmentViewModel.newAuthLink.value = boolean
        if (boolean){
            binding.tVSentNewCode.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            binding.tVSentNewCode.setTextColor(Color.parseColor("#F0303F"))
            binding.tVSentNewCode.isClickable = true
        }else
        {
            binding.tVSentNewCode.paintFlags = 0
            binding.tVSentNewCode.setTextColor(Color.parseColor("#B8C4DB"))
            binding.tVSentNewCode.isClickable = false
        }
    }

fun sendVerificationCode() {
        auth = FirebaseAuth.getInstance()
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("GFG", "onCodeSent: $verificationId")
                codeSendMessage = "codeSendSuccess"
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
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(authFragmentViewModel.currentPhoneNumber.value!!) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        KeyMessege()
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    setButtonColors(false)
                    fromAuthToMain()
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