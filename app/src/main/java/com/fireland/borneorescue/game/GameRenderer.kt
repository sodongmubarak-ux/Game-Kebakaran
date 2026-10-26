package com.fireland.borneorescue.game

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

class GameRenderer {
    fun render(
        drawScope: DrawScope,
        player: Player,
        fire: Fire?,
        isMission: Boolean
    ) {
        // Draw Peat Forest Ground
        drawScope.drawRect(
            color = Color(0xFF2D5A27),
            size = drawScope.size
        )

        // Draw Trails
        drawScope.drawRect(
            color = Color(0xFF8D6E63),
            topLeft = Offset(40f, drawScope.size.height * 0.45f),
            size = Size(drawScope.size.width - 80f, 60f)
        )

        // Draw Buildings / Trees
        if (!isMission) {
            // Player House (Rumah Panggung)
            drawScope.drawRect(
                color = Color(0xFF92400E),
                topLeft = Offset(100f, 150f),
                size = Size(160f, 120f)
            )
            // Water Tank (Tandon Air)
            drawScope.drawCircle(
                color = Color(0xFF0284C7),
                radius = 45f,
                center = Offset(320f, 220f)
            )
        } else {
            // Settler Cottage
            drawScope.drawRect(
                color = Color(0xFF92400E),
                topLeft = Offset(80f, 120f),
                size = Size(130f, 100f)
            )
            // Peat River
            drawScope.drawOval(
                color = Color(0xFF0284C7),
                topLeft = Offset(drawScope.size.width * 0.3f, drawScope.size.height * 0.75f),
                size = Size(240f, 90f)
            )
            // Wildfire
            if (fire != null && !fire.isExtinguished) {
                // Scorched ground
                drawScope.drawCircle(
                    color = Color(0xFF1C1917),
                    radius = fire.radius,
                    center = Offset(fire.x, fire.y)
                )
                // Fire flame
                val scale = fire.intensity / 100f
                drawScope.drawCircle(
                    color = Color(0xFFDC2626),
                    radius = fire.radius * 0.8f * scale,
                    center = Offset(fire.x, fire.y)
                )
                drawScope.drawCircle(
                    color = Color(0xFFF97316),
                    radius = fire.radius * 0.5f * scale,
                    center = Offset(fire.x, fire.y)
                )
            }
        }

        // Draw Player Character
        drawScope.drawCircle(
            color = Color(0xFFEA580C), // Orange vest
            radius = 24f,
            center = Offset(player.x, player.y)
        )
        drawScope.drawCircle(
            color = Color(0xFF166534), // Forest green hat
            radius = 16f,
            center = Offset(player.x, player.y - 12f)
        )

        // Draw Water Spray stream
        if (player.isSpraying && fire != null) {
            drawScope.drawLine(
                color = Color(0xFF38BDF8),
                start = Offset(player.x, player.y),
                end = Offset(fire.x, fire.y),
                strokeWidth = 8f
            )
        }
    }
}
