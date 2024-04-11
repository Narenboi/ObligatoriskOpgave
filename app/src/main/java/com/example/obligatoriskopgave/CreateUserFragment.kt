// CreateUserFragment.kt

package com.example.obligatoriskopgave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.obligatoriskopgave.databinding.CreateUserFragmentBinding
import com.example.obligatoriskopgave.models.BeerViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CreateUserFragment : Fragment() {

    private var _binding: CreateUserFragmentBinding? = null

    private val binding get() = _binding!!
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = CreateUserFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createUser.setOnClickListener {
            val email = binding.emailCreateUser.text.toString()
            val password = binding.passwordCreateUser.text.toString()
            if (email.isEmpty()) {
                binding.emailCreateUser.error = "Please enter email"
                binding.emailCreateUser.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordCreateUser.error = "Please enter password"
                binding.passwordCreateUser.requestFocus()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        println("User created!")
                        // Access the instance of BeerViewModel
                        val beerViewModel = BeerViewModel()
                        beerViewModel.reload()
                        findNavController().navigate(R.id.action_CreateUserFragment_to_BeerFragment)
                    } else {
                        binding.loginFailedCreateUser.text = "Create user failed: ${task.exception?.message} "
                    }
                }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
