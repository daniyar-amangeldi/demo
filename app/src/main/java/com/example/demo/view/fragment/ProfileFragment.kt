package com.example.demo.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.demo.R
import com.example.demo.databinding.FragmentProfielBinding
import com.example.demo.view.util.UserData

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfielBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfielBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userData = UserData(requireContext())
        println("isAuthorized: ${userData.isAuthorized()}")

        with(binding) {
            if (userData.isAuthorized()) {
                welcomeText.isVisible = true
                actionButton.isVisible = false
            } else {
                welcomeText.isVisible = false
                actionButton.isVisible = true
                actionButton.setOnClickListener {
                    userData.setAuthorizationState(true)
                }
            }
        }
    }
}