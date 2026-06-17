package com.example.avaliaws_08.ui.components

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun Welcome(){

    var totalCompetidores by remember() { mutableIntStateOf(0) }

Scaffold() { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "AvaliaWS 08",
            style = Typography().headlineMedium)
        Text(text = "Motor de Regras para Avaliação de Competidores",
            style = Typography().bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Total de Competidores: $totalCompetidores")
    }
}
}

@Composable
@Preview
fun WelcomePreview(){
    Welcome()
}