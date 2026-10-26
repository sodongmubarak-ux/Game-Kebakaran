package com.fireland.borneorescue.game

data class Player(
    var x: Float,
    var y: Float,
    var vx: Float = 0f,
    var vy: Float = 0f,
    val speed: Float = 220f,
    var isSpraying: Boolean = false
) {
    fun update(dt: Float, mapW: Float, mapH: Float) {
        x = (x + vx * dt).coerceIn(40f, mapW - 40f)
        y = (y + vy * dt).coerceIn(40f, mapH - 40f)
    }
}
