package com.example.intentserviceapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.CATEGORY_DEFAULT
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intentserviceapp.MainIntentService.Companion.FIRST_INTEGER
import com.example.intentserviceapp.MainIntentService.Companion.RESULT_OF_EVALUATING
import com.example.intentserviceapp.MainIntentService.Companion.SECOND_INTEGER
import com.example.intentserviceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val intentService by lazy { Intent(this, MainIntentService::class.java) }
    private val broadcastReceiver = MainBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerMainBR()
        bindViews()
    }

    private fun registerMainBR() {
        val intentFilter = IntentFilter(MainIntentService.EVALUATING_ACTION).apply {
            addCategory(CATEGORY_DEFAULT)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun bindViews() {
        binding.buttonEvaluate.setOnClickListener {
            try {
                val firstInt = binding.firstEt.text.toString().toInt()
                val secondInt = binding.secondEt.text.toString().toInt()
                sendEvaluateIntent(firstInt, secondInt)
            } catch (exception: Exception) {
                Toast.makeText(applicationContext, "Введите числа", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun sendEvaluateIntent(firstInt: Int, secondInt: Int) {
        startService(
            intentService
                .putExtra(FIRST_INTEGER, firstInt)
                .putExtra(SECOND_INTEGER, secondInt)
        )
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    inner class MainBroadcastReceiver() : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val result = intent?.getIntExtra(RESULT_OF_EVALUATING, 0)
            result?.let { binding.tv.text = it.toString() }
        }

    }

}