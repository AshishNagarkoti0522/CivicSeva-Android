package com.example.civicseva.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class BottomNavItem(
    val label: String,
    val route: Any,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val imageUrl: String? = null // Displays the image if it is not null.
)

@Composable
fun FloatingBottomNav(
    items: List<BottomNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    // Sets the image border color (defaults to white/surface color).
    selectedImageBorderColor: Color = MaterialTheme.colorScheme.surface
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .background(color = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onItemSelected(index) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Determines whether to display an image or an icon.
                    if (item.imageUrl != null) {
                        AppAsyncImage(
                            model = item.imageUrl,
                            contentDescription = item.label,
                            modifier = Modifier
                                .size(32.dp)
                                .then(
                                    if (isSelected) {
                                        Modifier
                                            .border(2.dp, selectedImageBorderColor, CircleShape)
                                            .padding(2.dp)
                                    } else {
                                        Modifier
                                    }
                                )
                                .clip(CircleShape)
                        )
                    } else {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = if (isSelected) item.selectedIcon!! else item.unselectedIcon!!,
                            contentDescription = item.label,
                            tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.inversePrimary
                        )
                    }
                }
            }
        }
    }
}

// Demo for visualization
@Preview(showBackground = true, name = "Floating Bottom Nav")
@Composable
fun FloatingBottomNavDemo() {
    var selectedIndex by remember { mutableIntStateOf(1) }

    val navItems = listOf(
        // 1. Normal Vector Item
        BottomNavItem(
            label = "Home",
            route = "home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        // 2. AsyncImage Item
        BottomNavItem(
            label = "Profile",
            route = "profile",
            imageUrl = "https://i.pravatar.cc/150?img=11" // Dummy image
        )
    )

    FloatingBottomNav(
        items = navItems,
        selectedIndex = selectedIndex,
        onItemSelected = { selectedIndex = it },
        selectedImageBorderColor = MaterialTheme.colorScheme.onPrimary
    )
}