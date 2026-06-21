package com.example.avaliaws_08

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.avaliaws_08.ui.navigation.AppNavHost
import com.example.avaliaws_08.ui.theme.AvaliaWS08Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AvaliaWS08Theme {
                AppNavHost()
            }
        }
    }
}
