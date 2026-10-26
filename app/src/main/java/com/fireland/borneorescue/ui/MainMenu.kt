package com.fireland.borneorescue.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenu(
    hasProgress: Boolean,
    onStartNewGame: () -> Unit,
    onContinueGame: () -> Unit,
    onLanguageChange: (String) -> Unit,
    onResetData: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Text(
                text = "🔥 FIRELAND",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFF97316)
            )
            Text(
                text = "BORNEO FOREST RESCUE",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFDE047),
                letterSpacing = 2.sp
            )
            Text(
                text = "Simulasi Pemadam Hutan Kalimantan",
                fontSize = 12.sp,
                color = Color(0xFF86EFAC),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onStartNewGame,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("MULAI BERMAIN", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Button(
                onClick = {
                    if (hasProgress) {
                        onContinueGame()
                    } else {
                        dialogText = "Belum ada progres tersimpan!"
                        showDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (hasProgress) Color(0xFFD97706) else Color(0xFF334155)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("LANJUTKAN", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = {
                        dialogText = "FIRELAND: Pelestarian Hutan Kalimantan v1.0.0\nDeveloper: sodongmubarak-ux"
                        showDialog = true
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("TENTANG", fontSize = 12.sp, color = Color.White)
                }

                OutlinedButton(
                    onClick = onResetData,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("RESET DATA", fontSize = 12.sp, color = Color(0xFFEF4444))
                }
            }
        }

        Text(
            text = "FIRELAND Android Prototype 2D",
            fontSize = 11.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            text = { Text(dialogText) }
        )
    }
}
