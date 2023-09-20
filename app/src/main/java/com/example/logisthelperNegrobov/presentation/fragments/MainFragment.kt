package com.example.logisthelperNegrobov.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.example.logisthelperNegrobov.R
import com.example.logisthelperNegrobov.databinding.FragmentMainBinding
import com.example.logisthelperNegrobov.presentation.fragments.bottom_menu_fragments.ChatFragment
import com.example.logisthelperNegrobov.presentation.fragments.bottom_menu_fragments.GraficsFragment
import com.example.logisthelperNegrobov.presentation.fragments.bottom_menu_fragments.ProfileFragment
import com.example.logisthelperNegrobov.presentation.fragments.bottom_menu_fragments.task_fragment.TaskFragment


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bNavigator.setOnItemSelectedListener {
                menuItem ->
            when(menuItem.itemId) {
                R.id.taskFragment -> {
                    replaceFragment(TaskFragment())
                    true
                }
                R.id.graficsFragment -> {
                    replaceFragment(GraficsFragment())
                    true
                }
                R.id.chatFragment -> {
                    replaceFragment(ChatFragment())
                    true
                }
                R.id.profileFragment -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(TaskFragment())


    }

    private fun replaceFragment(fragment: Fragment){
        parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }
}