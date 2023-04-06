package com.bunty.notesappusingmvvmhilt.views.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bunty.notesappusingmvvmhilt.R
import com.bunty.notesappusingmvvmhilt.databinding.FragmentSplashBinding
import com.bunty.notesappusingmvvmhilt.utils.Constants.TAG
import com.bunty.notesappusingmvvmhilt.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        Log.d(TAG, "token: ${tokenManager.getToken()}")
        if (tokenManager.getToken() != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
            }, 3000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_registerFragment)
            }, 3000)
        }

        return binding.root
    }
}