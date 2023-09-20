package com.example.logisthelperNegrobov.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.logisthelperNegrobov.databinding.FragmentEnterNumberBinding
import com.google.android.material.internal.ViewUtils.showKeyboard
import com.redmadrobot.inputmask.MaskedTextChangedListener


class EnterNumberFragment : BaseFragment()  {

    lateinit var binding: FragmentEnterNumberBinding



    private val authFragmentViewModel: AuthFragmentViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEnterNumberBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoginMask()

        showKeyboard(binding.loginEdt)
    }

    private fun initLoginMask(){
        MaskedTextChangedListener.installOn(
            binding.loginEdt,
            EnterNumberFragment.PHONE_MASK,
            object : MaskedTextChangedListener.ValueListener {

                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                    tailPlaceholder: String
                ) {

                    isNumberFull(maskFilled)
                    setNumber(formattedValue)
                    setCurrentNumber(formattedValue)
                }
            }
        )

    }
    private fun setCurrentNumber(string: String){
        val newString = string.replace("(","").replace(")","").replace("-","").replace(" ","")
        authFragmentViewModel.currentPhoneNumber.value = newString
    }

    private fun setNumber(string: String){
        val newString = string.replace("(","").replace(")","")
        authFragmentViewModel.enteredPhoneNumberForTextView.value = newString
    }

    private fun isNumberFull(boolean: Boolean){
        authFragmentViewModel.fullPhoneNumber.value = boolean
    }



    companion object {

        const val PHONE_MASK = "+7 ([000]) [000] - [00] - [00]"
        @JvmStatic
        fun newInstance() =
            EnterNumberFragment()
    }


}