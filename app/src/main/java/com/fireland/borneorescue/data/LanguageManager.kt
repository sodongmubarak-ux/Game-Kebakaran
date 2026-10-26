package com.fireland.borneorescue.data

object LanguageManager {
    fun getString(key: String, lang: String): String {
        return when (lang) {
            "en" -> when (key) {
                "water" -> "WATER TANK"
                "extinguish" -> "EXTINGUISH"
                "refill" -> "REFILL WATER"
                "base" -> "Volunteer Base Camp"
                "mission" -> "Forest Edge Wildfire"
                else -> key
            }
            else -> when (key) {
                "water" -> "TANGKI AIR"
                "extinguish" -> "PADAMKAN"
                "refill" -> "ISI AIR"
                "base" -> "Markas Relawan"
                "mission" -> "Kebakaran Tepi Hutan"
                else -> key
            }
        }
    }
}
