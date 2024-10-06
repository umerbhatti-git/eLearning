package com.example.elearning.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper(context: Context) {

    companion object {
        private const val PREF_NAME = "com.example.elearn.PREFERENCE_FILE"
        private const val KEY_ENROLL_STATUS = "enroll_status"
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setEnrollmentStatus(isEnrolled: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(KEY_ENROLL_STATUS, isEnrolled)
        editor.apply()
    }

    fun getEnrollmentStatus(): Boolean {
        return preferences.getBoolean(KEY_ENROLL_STATUS, false)
    }

    fun clearEnrollmentStatus() {
        val editor = preferences.edit()
        editor.remove(KEY_ENROLL_STATUS)
        editor.apply()
    }
}
