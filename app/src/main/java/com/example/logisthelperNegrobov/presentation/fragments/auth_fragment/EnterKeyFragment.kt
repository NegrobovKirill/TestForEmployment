package com.example.logisthelperNegrobov.presentation.fragments.auth_fragment

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.logisthelperNegrobov.R
import com.example.logisthelperNegrobov.databinding.FragmentEnterKeyBinding
import com.example.logisthelperNegrobov.presentation.fragments.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.redmadrobot.inputmask.MaskedTextChangedListener


class EnterKeyFragment : BaseFragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentEnterKeyBinding
    private val authFragmentViewModel: AuthFragmentViewModel by activityViewModels()


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

        binding.tVPhoneNumber.text = authFragmentViewModel.enteredPhoneNumberForTextView.value

        binding.bBackToEnterPhone.setOnClickListener {
            authFragmentViewModel.currentAuthFragment.value = "EnterNumberFragment"
            parentFragmentManager.beginTransaction().replace(R.id.placeHolder,
                EnterNumberFragment.newInstance()
            ).commit()
        }
    }


    private fun initLoginMask(){

        MaskedTextChangedListener.installOn(

            binding.keyEdt,
            KEY_MASK,
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