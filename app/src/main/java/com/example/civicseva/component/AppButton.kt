package com.example.civicseva.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    isLoading: Boolean = false, // 🔥 NAYA: Loader control karne ke liye
    colors: ButtonColors = ButtonDefaults.buttonColors()
) {
    // 1. Interaction source to detect press state
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // 2. 🔥 PREMIUM ANIMATION: Press karne par thoda shrink hoga (Scale Animation)
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f, // 5% shrink on press
        label = "buttonScaleAnim"
    )

    Button(
        // Agar loading chal rahi hai, toh click disable kar do
        onClick = { if (!isLoading) onClick() },
        modifier = modifier
            .height(50.dp) // 🔥 Thoda height badha diya premium look ke liye
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        enabled = enabled,
        shape = RoundedCornerShape(12.dp), // 🔥 Fixed decent curve
        colors = colors,
        interactionSource = interactionSource
    ) {
        if (isLoading) {
            // 🔥 LOADER STATE
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp), // Button ke hisaab se chota loader
                color = MaterialTheme.colorScheme.onPrimary, // Text ke color jaisa
                strokeWidth = 2.5.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
            AppText(
                text = "Please wait...", // Ya tum isko as parameter le sakte ho
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        } else {
            // 🔥 NORMAL STATE
            AppText(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Demo/Boilerplate for visualization
@Preview(showBackground = true, name = "Premium App Button Demo")
@Composable
fun AppButtonDemo() {
    // Loader test karne ke liye state
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Normal Premium Button
        AppButton(
            text = "Login",
            onClick = { /* Do something */ }
        )

        // 2. Button with Loader (Click karne par loader on hoga)
        AppButton(
            text = "Submit",
            isLoading = isLoading,
            onClick = {
                isLoading = !isLoading // Click karke toggle check kar sakte ho
            }
        )

        // 3. Disabled State
        AppButton(
            text = "Disabled Button",
            onClick = { },
            enabled = false
        )

        // 4. Wrap Content Usage
        AppButton(
            text = "Small",
            onClick = { },
            modifier = Modifier // Overrides default fillMaxWidth
        )
    }
}