package com.bogdash.homework_1

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
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
    }

    private fun getSecretKey() {
        val uri = Uri.parse("content://dev.surf.android.provider/text")
        val contentResolver = contentResolver
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex("text")
                if (columnIndex != -1) {
                    val text = it.getString(columnIndex)
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Секретный ключ не найден", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Данные не найдены", Toast.LENGTH_SHORT).show()
            }
        }
    }
}