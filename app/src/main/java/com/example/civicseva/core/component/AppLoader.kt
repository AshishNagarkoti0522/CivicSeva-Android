package com.example.civicseva.core.component

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

@Composable
fun AppLoader(
    modifier: Modifier = Modifier,
    isHorizontal: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surface,
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp
) {
    if (isHorizontal) {
        // Horizontal (Linear) Loader
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(strokeWidth),
            color = color,
            trackColor = trackColor
        )
    } else {
        // Circular Loader
        CircularProgressIndicator(
            modifier = modifier.size(size),
            color = color,
            trackColor = trackColor,
            strokeWidth = strokeWidth
        )
    }
}

// Demo for visualization
@Preview(showBackground = true, name = "App Loader Variations")
@Composable
fun AppLoaderDemo() {
    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Default Circular Loader
        AppLoader()

        // 2. Small Circular Loader
        AppLoader(size = 24.dp, strokeWidth = 2.dp)

        // 3. Horizontal Loader
        AppLoader(isHorizontal = true)

        // 4. Thick Horizontal Loader
        AppLoader(
            isHorizontal = true,
            strokeWidth = 8.dp
        )
    }
}