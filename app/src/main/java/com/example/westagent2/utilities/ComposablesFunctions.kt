package com.example.westagent2.utilities

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FullWidthButton(text: String, onClick: () -> Unit, isPrimary: Boolean = true, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPrimary) Color(0xFF1980E6) else Color(0xFFF0F2F4),
            contentColor = if (isPrimary) Color.White else Color(0xFF111418)
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = CircleShape
    ) {
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}