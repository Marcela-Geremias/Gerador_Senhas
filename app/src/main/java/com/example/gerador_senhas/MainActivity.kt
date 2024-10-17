package com.example.gerador_senhas

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvGeneratedPassword: TextView = findViewById(R.id.tvGeneratedPassword)
        val sbLength: SeekBar = findViewById(R.id.sbLength)
        val cbUpperCase: CheckBox = findViewById(R.id.cbUpperCase)
        val cbLowerCase: CheckBox = findViewById(R.id.cbLowerCase)
        val cbNumbers: CheckBox = findViewById(R.id.cbNumbers)
        val cbExcludeSimilar: CheckBox = findViewById(R.id.cbExcludeSimilar)
        val btnGenerate: Button = findViewById(R.id.btnGenerate)
        val btnCopy: Button = findViewById(R.id.btnCopy)

        btnGenerate.setOnClickListener {
            val length = sbLength.progress
            val includeUpperCase = cbUpperCase.isChecked
            val includeLowerCase = cbLowerCase.isChecked
            val includeNumbers = cbNumbers.isChecked
            val excludeSimilar = cbExcludeSimilar.isChecked

            val password = generatePassword(length, includeUpperCase, includeLowerCase, includeNumbers, excludeSimilar)
            tvGeneratedPassword.text = password
        }

        btnCopy.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Generated Password", tvGeneratedPassword.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Senha copiada para a área de transferência", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generatePassword(length: Int, includeUpperCase: Boolean, includeLowerCase: Boolean, includeNumbers: Boolean, excludeSimilar: Boolean): String {
        val upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowerCaseChars = "abcdefghijklmnopqrstuvwxyz"
        val numbers = "0123456789"
        val similarChars = "il1Lo0O"

        var allowedChars = ""

        if (includeUpperCase) allowedChars += upperCaseChars
        if (includeLowerCase) allowedChars += lowerCaseChars
        if (includeNumbers) allowedChars += numbers

        if (excludeSimilar) {
            allowedChars = allowedChars.filterNot { it in similarChars }
        }

        if (allowedChars.isEmpty()) {
            return "Configurações inválidas"
        }

        return (1..length)
            .map { allowedChars[Random.nextInt(allowedChars.length)] }
            .joinToString("")
    }
}