package com.fireland.borneorescue.data

import android.content.Context
import android.content.SharedPreferences

class LocalSaveManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("fireland_save", Context.MODE_PRIVATE)

    fun hasProgress(): Boolean = prefs.getBoolean("has_started", false)

    fun setHasStarted(started: Boolean) {
        prefs.edit().putBoolean("has_started", started).apply()
    }

    fun getCoins(): Int = prefs.getInt("coins", 0)

    fun addCoins(amount: Int) {
        prefs.edit().putInt("coins", getCoins() + amount).apply()
    }

    fun getWater(): Float = prefs.getFloat("water", 100f)

    fun setWater(amount: Float) {
        prefs.edit().putFloat("water", amount.coerceIn(0f, 100f)).apply()
    }

    fun isMissionCompleted(id: String): Boolean = prefs.getBoolean("mission_$id", false)

    fun setMissionCompleted(id: String) {
        prefs.edit().putBoolean("mission_$id", true).apply()
        addCoins(50)
    }

    fun getLanguage(): String = prefs.getString("lang", "id") ?: "id"

    fun setLanguage(lang: String) {
        prefs.edit().putString("lang", lang).apply()
    }

    fun resetAll() {
        prefs.edit().clear().apply()
    }
}
