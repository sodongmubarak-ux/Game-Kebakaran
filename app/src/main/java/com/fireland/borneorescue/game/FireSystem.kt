package com.fireland.borneorescue.game

data class Fire(
    val x: Float,
    val y: Float,
    val radius: Float = 90f,
    var intensity: Float = 100f
) {
    val isExtinguished: Boolean get() = intensity <= 0f

    fun extinguish(amount: Float) {
        intensity = (intensity - amount).coerceAtLeast(0f)
    }
}
