package com.example.intentserviceapp

import android.app.IntentService
import android.content.Intent

class MainIntentService : IntentService("Main intent service") {
    override fun onHandleIntent(intent: Intent?) {
        val firstInt = intent?.getIntExtra(FIRST_INTEGER, 0) ?: 0
        val secondInt = intent?.getIntExtra(SECOND_INTEGER, 0) ?: 0
        val resultIntent = Intent().apply {
            action = EVALUATING_ACTION
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra(RESULT_OF_EVALUATING, firstInt * secondInt)
        }
        sendBroadcast(resultIntent)
    }

    companion object {
        const val FIRST_INTEGER = "FIRST_INTEGER"
        const val SECOND_INTEGER = "SECOND_INTEGER"
        const val RESULT_OF_EVALUATING = "RESULT_OF_EVALUATING"
        const val EVALUATING_ACTION = "EVALUATING_ACTION"
    }
}