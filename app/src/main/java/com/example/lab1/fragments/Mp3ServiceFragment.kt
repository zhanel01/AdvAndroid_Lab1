package com.example.lab1.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.lab1.databinding.FragmentMp3ServiceBinding
import com.example.lab1.Mp3ForegroundService

class Mp3ServiceFragment : Fragment() {

    private var _binding: FragmentMp3ServiceBinding? = null
    private val binding get() = _binding!!
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Разрешение на уведомления получено", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Разрешение на уведомления ОТКАЗАНО", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMp3ServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireContext().checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {

            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }


        binding.btnStart.setOnClickListener {
            val intent = Intent(requireContext(), Mp3ForegroundService::class.java)
            intent.action = Mp3ForegroundService.ACTION_START
            requireContext().startService(intent)
        }

        binding.btnPause.setOnClickListener {
            val intent = Intent(requireContext(), Mp3ForegroundService::class.java)
            intent.action = Mp3ForegroundService.ACTION_PAUSE
            requireContext().startService(intent)
        }

        binding.btnStop.setOnClickListener {
            val intent = Intent(requireContext(), Mp3ForegroundService::class.java)
            intent.action = Mp3ForegroundService.ACTION_STOP
            requireContext().startService(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}