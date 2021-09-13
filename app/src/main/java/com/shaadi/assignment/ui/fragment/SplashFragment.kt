package com.shaadi.assignment.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shaadi.assignment.R
import kotlinx.coroutines.delay

class SplashFragment : Fragment() {

    companion object{
        private const val SPLASH_DELAY = 3000L
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            delay(SPLASH_DELAY)
            navigateToDashboard()
        }
    }

    private fun navigateToDashboard(){
        findNavController().navigate(R.id.action_splashFragment_to_dashboardFragment)
    }

}