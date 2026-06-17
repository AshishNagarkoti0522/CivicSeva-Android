package com.example.civicseva.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Your main reusable "All-in-One" Loader component
@Composable
fun AppLoader(
    modifier: Modifier = Modifier,
    isHorizontal: Boolean = false, // 🔥 Jaadu ki chadi
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp
) {
    if (isHorizontal) {
        // Horizontal (Linear) Loader
        LinearProgressIndicator(
            // 🔥 Dekho ab modifier kitna clean lag raha hai
            modifier = modifier
                .fillMaxWidth()
                .height(strokeWidth),
            color = color,
            trackColor = trackColor
        )
    } else {
        // Circular Loader
        CircularProgressIndicator(
            // 🔥 Yahan se bhi lamba path hata diya
            modifier = modifier.size(size),
            color = color,
            trackColor = trackColor,
            strokeWidth = strokeWidth
        )
    }
}

// Demo/Boilerplate for visualization
@Preview(showBackground = true, name = "App Loader Variations")
@Composable
fun AppLoaderDemo() {
    Column(
        modifier = Modifier.padding(24.dp), // Padding import bhi add kar diya upar
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Default Circular Loader
        AppLoader()

        // 2. Chota Circular Loader
        AppLoader(size = 24.dp, strokeWidth = 2.dp)

        // 3. Horizontal Loader
        AppLoader(isHorizontal = true)

        // 4. Mota Horizontal Loader
        AppLoader(
            isHorizontal = true,
            strokeWidth = 8.dp
        )
    }
}