package com.fireland.borneorescue.game

class WaterSystem(initialWater: Float = 100f) {
    var currentWater: Float = initialWater
        private set
    val maxWater: Float = 100f

    fun useWater(amount: Float): Boolean {
        if (currentWater <= 0f) return false
        currentWater = (currentWater - amount).coerceAtLeast(0f)
        return true
    }

    fun refill() {
        currentWater = maxWater
    }
}
