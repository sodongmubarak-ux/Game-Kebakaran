package com.fireland.borneorescue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.fireland.borneorescue.data.LocalSaveManager
import com.fireland.borneorescue.game.GameScreen
import com.fireland.borneorescue.ui.MainMenu

enum class AppScreen {
    MENU,
    BASE_CAMP,
    MISSION_01
}

class MainActivity : ComponentActivity() {
    private lateinit var saveManager: LocalSaveManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        saveManager = LocalSaveManager(this)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var currentScreen by remember { mutableStateOf(AppScreen.MENU) }
                    var hasProgress by remember { mutableStateOf(saveManager.hasProgress()) }

                    when (currentScreen) {
                        AppScreen.MENU -> {
                            MainMenu(
                                hasProgress = hasProgress,
                                onStartNewGame = {
                                    saveManager.setHasStarted(true)
                                    hasProgress = true
                                    currentScreen = AppScreen.BASE_CAMP
                                },
                                onContinueGame = {
                                    currentScreen = AppScreen.BASE_CAMP
                                },
                                onLanguageChange = { lang ->
                                    saveManager.setLanguage(lang)
                                },
                                onResetData = {
                                    saveManager.resetAll()
                                    hasProgress = false
                                }
                            )
                        }
                        AppScreen.BASE_CAMP -> {
                            GameScreen(
                                isMission = false,
                                saveManager = saveManager,
                                onOpenMissionMap = {
                                    currentScreen = AppScreen.MISSION_01
                                },
                                onBackToMenu = {
                                    currentScreen = AppScreen.MENU
                                }
                            )
                        }
                        AppScreen.MISSION_01 -> {
                            GameScreen(
                                isMission = true,
                                saveManager = saveManager,
                                onOpenMissionMap = {
                                    currentScreen = AppScreen.BASE_CAMP
                                },
                                onBackToMenu = {
                                    currentScreen = AppScreen.BASE_CAMP
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
