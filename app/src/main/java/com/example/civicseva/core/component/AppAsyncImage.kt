package com.example.civicseva.core.component

/*
 SETUP INSTRUCTIONS (Read Before Use):
 1. Add this dependency in your app/build.gradle (or build.gradle.kts):
    implementation("io.coil-kt:coil-compose:2.6.0")
    (Check for the latest 2.x version if needed)

 2. Add Internet Permission in AndroidManifest.xml (above <application> tag):
    <uses-permission android:name="android.permission.INTERNET" />
*/

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
// 🔥 Note: Replace 'R' with your actual app's R class import if needed for placeholder testing
// import com.example.appname.R

@Composable
fun AppAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderResId: Int? = null,
    errorResId: Int? = null,       // Custom error image fallback (if image fails to load)
    fallbackResId: Int? = null
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        // Optional Placeholders logic
        placeholder = placeholderResId?.let { painterResource(id = it) },
        error = errorResId?.let { painterResource(id = it) },
        fallback = fallbackResId?.let { painterResource(id = it) }
    )
}

// DEMO & USE CASES
@Preview(showBackground = true, name = "App Async Image Demo")
@Composable
fun AppAsyncImageDemo() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // 1. STANDARD USE CASE (E.g., A Banner or Post Image)
        AppAsyncImage(
            model = "https://images.unsplash.com/photo-1542291026-7eec264c27ff",
            contentDescription = "Post Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 2. CIRCULAR PROFILE PICTURE
            AppAsyncImage(
                model = "https://i.pravatar.cc/150?img=11",
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            // 3. ERROR / FALLBACK HANDLING
            // Displays a placeholder if the image fails to load.
            // (Note: In previews, pass an R.drawable resource. The examples below are commented out.)

            /*
            AppAsyncImage(
                model = "https://invalid-url.com/image.jpg",
                contentDescription = "Failed Image",
                placeholderResId = R.drawable.ic_loading_placeholder,
                errorResId = R.drawable.ic_error_avatar,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            */
        }
    }
}