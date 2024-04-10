package com.bogdash.homework_1

import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val customBroadcastReceiver = CustomBroadcastReceiver()
    private var secretKey: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonGetSecretKey: Button = findViewById(R.id.buttonGetSecretKey)
        buttonGetSecretKey.setOnClickListener {
            getSecretKey()
        }

        initReceiver()
    }

    private fun getSecretKey() {
        val uri = Uri.parse("content://dev.surf.android.provider/text")
        val contentResolver = contentResolver
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex("text")
                if (columnIndex != -1) {
                    secretKey = it.getString(columnIndex)
                    Toast.makeText(this, secretKey, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Секретный ключ не найден", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Данные не найдены", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initReceiver() {
        val intentFilter = IntentFilter("ru.shalkoff.vsu_lesson2_2024.SURF_ACTION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(customBroadcastReceiver, intentFilter, RECEIVER_EXPORTED)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(customBroadcastReceiver)
    }
}