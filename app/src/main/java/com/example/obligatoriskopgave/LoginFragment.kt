package com.example.obligatoriskopgave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.obligatoriskopgave.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email.isEmpty()) {
                binding.email.error = "Please enter email"
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.password.error = "Please enter password"
                binding.password.requestFocus()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        println("Login successfull!")
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    } else {
                        binding.loginFailed.text = "Login failed: ${task.exception?.message} "
                    }
                }
        }
        binding.createUser.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}