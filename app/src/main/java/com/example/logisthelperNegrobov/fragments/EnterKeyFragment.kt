package com.example.logisthelperNegrobov.fragments

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.logisthelperNegrobov.R
import com.example.logisthelperNegrobov.databinding.FragmentEnterKeyBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener


class EnterKeyFragment : BaseFragment() {

    private lateinit var binding: FragmentEnterKeyBinding
    private val authFragmentViewModel: AuthFragmentViewModel by activityViewModels()
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterKeyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoginMask()
        showKeyboard(binding.keyEdt)

        KeyMessege()


        binding.tVPhoneNumber.text = authFragmentViewModel.enteredPhoneNumberForTextView.value




        binding.bBackToEnterPhone.setOnClickListener {
            authFragmentViewModel.currentAuthFragment.value = "EnterNumberFragment"
            parentFragmentManager.beginTransaction().replace(R.id.placeHolder,EnterNumberFragment.newInstance()).commit()
        }


        authFragmentViewModel.newAuthLink.observe(activity as LifecycleOwner){
            if (it){
                binding.tVSentNewCode.setOnClickListener {
                    if (binding.tVSentNewCode.isClickable){
                        KeyMessege()
                    }
                }
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



    private fun initLoginMask(){


        MaskedTextChangedListener.installOn(

            binding.keyEdt,
            EnterKeyFragment.KEY_MASK,
            object : MaskedTextChangedListener.ValueListener {

                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                    tailPlaceholder: String
                ) {
                    authFragmentViewModel.enterFullKey.value = maskFilled
                    authFragmentViewModel.enteredKey.value = extractedValue
                }

            }


        )


    }

    companion object {

        const val KEY_MASK = "[0] [0] [0] [0] [0] [0]"

        @JvmStatic
        fun newInstance() =
            EnterKeyFragment()
    }
}