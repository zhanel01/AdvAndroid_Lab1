package com.example.lab1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lab1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnShareStory.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToShareStoryFragment()
            )
        }
        binding.btnMusicService.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMusicServiceFragment()
            )
        }
        binding.btnAirplaneMode.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAirplaneModeFragment()
            )
        }
        binding.btnCalendarEvents.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToCalendarEventsFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}