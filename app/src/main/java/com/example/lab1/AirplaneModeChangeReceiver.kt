package com.example.lab1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneModeChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeOn = intent.getBooleanExtra("state", false)
            Toast.makeText(
                context, "Airplane mode changed to ${if (isAirplaneModeOn) "ON" else "OFF"}",
                Toast.LENGTH_SHORT).show()
        }
    }
}