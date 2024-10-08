package com.example.responsiveviews

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.responsiveviews.ui.theme.ResponsiveViewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResponsiveViewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Response(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Responsive Views based on device configuration and screen size and orientation (portrait/landscape)
@Composable
fun Response(name: String, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val configuration = LocalConfiguration.current
        val orientation = configuration.orientation

        // Use the maxWidth from BoxWithConstraints for device size checks
        when {
            // Tablet in landscape mode
            orientation == Configuration.ORIENTATION_LANDSCAPE && maxWidth > 600.dp -> {
                TabletLandscapeContent(name)
            }
            // Tablet in portrait mode
            orientation == Configuration.ORIENTATION_PORTRAIT && maxWidth > 600.dp -> {
                TabletPortraitContent(name)
            }
            // Phone in landscape mode
            orientation == Configuration.ORIENTATION_LANDSCAPE && maxWidth <= 600.dp -> {
                PhoneLandscapeContent(name)
            }
            // Phone in portrait mode
            orientation == Configuration.ORIENTATION_PORTRAIT && maxWidth <= 600.dp -> {
                PhonePortraitContent(name)
            }
            else -> {
                PhonePortraitContent(name) // Default to phone portrait if other checks fail
            }
        }
    }
}

// Tablet content in portrait mode (larger device)
@Composable
fun TabletPortraitContent(name: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Tablet Portrait Layout for $name", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        CookieClickerApp() // Cookie Clicker App
    }
}

// Tablet content in landscape mode (larger device)
@Composable
fun TabletLandscapeContent(name: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Tablet Landscape Layout for $name", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.width(16.dp))
        CookieClickerApp() // Cookie Clicker App
    }
}

// Phone content in portrait mode (small device)
@Composable
fun PhonePortraitContent(name: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        Text(text = "Hello $name!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        CookieClickerApp() // Cookie Clicker App
    }
}

// Phone content in landscape mode (small device)
@Composable
fun PhoneLandscapeContent(name: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Phone Landscape Layout for $name", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.width(16.dp))
        CookieClickerApp() // Cookie Clicker App
    }
}

// A simple cookie clicker app
@Composable
fun CookieClickerApp() {
    var count by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { count++ }) {
            Text("Click the Cookie")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Clicks: $count", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { count = 0 }) {
            Text("Reset")
        }
    }
}

// Preview on a phone in portrait mode
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=411dp,height=891dp"
)
@Composable
fun ResponsivePortraitPhonePreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
                name = "Phone (Portrait)",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

// Preview on a phone in landscape mode
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=411dp,height=891dp"
)
@Composable
fun ResponsiveLandscapePhonePreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
                name = "Phone (Landscape)",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

// Preview on a tablet in portrait mode
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
fun ResponsivePortraitTabletPreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
                name = "Tablet (Portrait)",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

// Preview on a tablet in landscape mode
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
fun ResponsiveLandscapeTabletPreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
                name = "Tablet (Landscape)",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
