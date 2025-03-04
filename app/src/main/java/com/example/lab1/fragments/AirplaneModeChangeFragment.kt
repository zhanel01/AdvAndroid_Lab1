package com.example.lab1.fragments

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lab1.databinding.FragmentAirplaneModeChangeBinding
import com.example.lab1.AirplaneModeChangeReceiver

class AirplaneModeChangeFragment : Fragment() {

    private var _binding: FragmentAirplaneModeChangeBinding? = null
    private val binding get() = _binding!!

    private val airplaneModeChangeReceiver = AirplaneModeChangeReceiver()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAirplaneModeChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        requireActivity().registerReceiver(airplaneModeChangeReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(airplaneModeChangeReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
