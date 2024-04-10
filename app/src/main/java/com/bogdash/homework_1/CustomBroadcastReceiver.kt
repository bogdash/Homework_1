package com.bogdash.homework_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings.Global
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomBroadcastReceiver : BroadcastReceiver() {
    var message: String? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "ru.shalkoff.vsu_lesson2_2024.SURF_ACTION" && context != null) {
            message = intent.getStringExtra("message") ?: "null message"
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}