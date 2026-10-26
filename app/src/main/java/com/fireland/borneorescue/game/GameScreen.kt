package com.fireland.borneorescue.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fireland.borneorescue.data.LocalSaveManager
import kotlinx.coroutines.delay
import kotlin.math.hypot

@Composable
fun GameScreen(
    isMission: Boolean,
    saveManager: LocalSaveManager,
    onOpenMissionMap: () -> Unit,
    onBackToMenu: () -> Unit
) {
    val renderer = remember { GameRenderer() }
    val player = remember { Player(x = 180f, y = 350f) }
    val fire = remember { if (isMission) Fire(x = 550f, y = 350f) else null }
    val waterSystem = remember { WaterSystem(saveManager.getWater()) }

    var waterDisplay by remember { mutableStateOf(waterSystem.currentWater) }
    var isNearFire by remember { mutableStateOf(false) }
    var isNearWater by remember { mutableStateOf(false) }
    var victoryMessage by remember { mutableStateOf<String?>(null) }

    // Game loop running with coroutines
    LaunchedEffect(Unit) {
        var lastTime = System.currentTimeMillis()
        while (true) {
            val now = System.currentTimeMillis()
            val dt = ((now - lastTime) / 1000f).coerceIn(0.01f, 0.05f)
            lastTime = now

            player.update(dt, 900f, 800f)

            // Extinguish update
            if (player.isSpraying && fire != null && !fire.isExtinguished) {
                if (waterSystem.useWater(25f * dt)) {
                    fire.extinguish(30f * dt)
                    waterDisplay = waterSystem.currentWater
                    if (fire.isExtinguished) {
                        player.isSpraying = false
                        saveManager.setMissionCompleted("01")
                        victoryMessage = "Misi Berhasil! Api berhasil dipadamkan!"
                    }
                } else {
                    player.isSpraying = false
                }
            }

            // Distances
            if (fire != null && !fire.isExtinguished) {
                val dist = hypot(player.x - fire.x, player.y - fire.y)
                isNearFire = dist < 240f
            } else {
                isNearFire = false
            }

            isNearWater = if (!isMission) {
                hypot(player.x - 320f, player.y - 220f) < 120f
            } else {
                hypot(player.x - 450f, player.y - 650f) < 140f
            }

            delay(16)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F172A))) {
        // 2D Canvas Game Render
        Canvas(modifier = Modifier.fillMaxSize()) {
            renderer.render(this, player, fire, isMission)
        }

        // Top Status HUD
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xCC0F172A))) {
                Text(
                    text = "💧 Air: ${waterDisplay.toInt()} / 100 L",
                    color = Color(0xFF38BDF8),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 13.sp
                )
            }

            Button(
                onClick = onBackToMenu,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xCC334155))
            ) {
                Text(if (isMission) "Markas" else "Menu", fontSize = 12.sp)
            }
        }

        // Controls Bottom Layer
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            // Virtual Joystick
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0x991E293B), CircleShape)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                player.vx = 0f
                                player.vy = 0f
                            },
                            onDragCancel = {
                                player.vx = 0f
                                player.vy = 0f
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                val len = hypot(dragAmount.x, dragAmount.y)
                                if (len > 0f) {
                                    player.vx = (dragAmount.x / len) * player.speed
                                    player.vy = (dragAmount.y / len) * player.speed
                                }
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(50.dp).background(Color(0xFF64748B), CircleShape))
            }

            // Action Buttons
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (!isMission) {
                    Button(
                        onClick = onOpenMissionMap,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A))
                    ) {
                        Text("PETA MISI")
                    }
                }

                if (isNearWater) {
                    Button(
                        onClick = {
                            waterSystem.refill()
                            waterDisplay = waterSystem.currentWater
                            saveManager.setWater(100f)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                    ) {
                        Text("ISI AIR")
                    }
                }

                Button(
                    onClick = {
                        if (isNearFire && waterDisplay > 0f) {
                            player.isSpraying = !player.isSpraying
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isNearFire) Color(0xFFEA580C) else Color(0xFF475569)
                    ),
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape
                ) {
                    Text("PADAM", fontSize = 11.sp)
                }
            }
        }

        if (victoryMessage != null) {
            AlertDialog(
                onDismissRequest = { victoryMessage = null },
                confirmButton = {
                    TextButton(onClick = {
                        victoryMessage = null
                        onBackToMenu()
                    }) {
                        Text("KEMBALI KE MARKAS")
                    }
                },
                title = { Text("MISI SELESAI") },
                text = { Text(victoryMessage ?: "") }
            )
        }
    }
}
