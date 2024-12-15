package com.example.tugasakhirmobile.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.tugasakhirmobile.R
import com.example.tugasakhirmobile.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding tidak boleh null")

    private lateinit var prefManager: PrefManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentUserProfileBinding.bind(view)

        prefManager = PrefManager.getInstance(requireContext())

        with(binding) {
            val username = prefManager.getUsername()
            val email = prefManager.getEmail()
            val password = prefManager.getPassword()
            val role = prefManager.getRole()

            usernameText.text = username
            emailText.text = "Email: $email"
            passwordText.text = "Password: $password"
            roleText.text = "Role: $role"

            btnLogout.setOnClickListener {
                prefManager.clear()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
