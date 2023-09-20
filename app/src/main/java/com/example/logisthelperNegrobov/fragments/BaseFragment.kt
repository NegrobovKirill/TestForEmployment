package com.example.logisthelperNegrobov.fragments

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    fun showKeyboard(view: View){
       val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
       imm.showSoftInput(view, 0)
    }
}