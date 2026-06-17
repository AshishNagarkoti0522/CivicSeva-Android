package com.example.civicseva.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

// Your main reusable component
@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

// Demo/Boilerplate for visualization
@Preview(showBackground = true, name = "App Text Variations")
@Composable
fun AppTextDemo() {
    Column(modifier = Modifier.padding(16.dp)) {

        // 1. Normal Usage
        AppText(text = "Hello World")

        // 2. Custom Style & Bold Weight
        AppText(
            text = "This is a Heading",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // 3. Center Aligned Text
        AppText(
            modifier = Modifier.fillMaxWidth(),
            text = "Centered Text",
            textAlign = TextAlign.Center
        )

        // 4. Truncated Text (Adds ... after 1 line)
        AppText(
            text = "This is a very long sentence that will not fit completely on the screen, so we are truncating it.",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}