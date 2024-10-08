package com.example.responsiveviews

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            // Round screen (e.g., smartwatch)
            configuration.isScreenRound -> {
                RoundScreenContent(name)
            }
            else -> {
                PhonePortraitContent(name) // Default to phone portrait if other checks fail
            }
        }
    }
}


// Tablet content on portrait (larger device)
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

// Tablet content on landscape (larger device)
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

// Phone in portrait mode (small device)
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

// Phone in landscape mode (small device)
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

// Round screen content (watch / smaller device)
@Composable
fun RoundScreenContent(name: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Hello $name!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        CookieClickerApp() // Cookie Clicker App
    }
}
@Composable
fun CookieClickerApp() {
    var count by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cookie Clicker Button
        Button(onClick = { count++ }) {
            Text("Click the Cookie")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Counter Display
        Text(text = "Clicks: $count", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Reset Button
        Button(onClick = { count = 0 }) {
            Text("Reset")
        }
    }
}


// Preview on a phone in portrait mode with system UI and background enabled
// The device is set to typical phone dimensions (width: 411dp, height: 891dp) in portrait orientation.
// The system UI is shown to help visualize how the layout interacts with the status bar and other elements.
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
fun ResponsivePortraitPhonePreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
                name = "Phone (Portrait)",
                modifier = Modifier.padding(innerPadding) // Accounts for padding around system UI elements
            )
        }
    }
}

// Preview on a phone in landscape mode with system UI and background enabled
// The same device is previewed, but in landscape orientation (width: 891dp, height: 411dp).
// This helps test how the layout adapts to the wider screen.
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=891dp,height=411dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun ResponsiveLandscapePhonePreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
                name = "Phone (Landscape)",
                modifier = Modifier.padding(innerPadding) // Adapts to landscape mode, adjusting for system UI
            )
        }
    }
}

// Preview on a tablet in portrait mode with system UI and background enabled
// A tablet device is simulated with larger dimensions (width: 800dp, height: 1280dp).
// This demonstrates how the layout adjusts on a bigger screen in portrait orientation.
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240,isRound=false,chinSize=0dp,orientation=portrait"
)
@Composable
fun ResponsivePortraitTabletPreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
                name = "Tablet (Portrait)",
                modifier = Modifier.padding(innerPadding) // Ensures responsiveness on larger tablet screens
            )
        }
    }
}

// Preview on a tablet in landscape mode with system UI and background enabled
// The tablet is previewed in landscape mode (width: 1280dp, height: 800dp), demonstrating responsive behavior.
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun ResponsiveLandscapeTabletPreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
                name = "Tablet (Landscape)",
                modifier = Modifier.padding(innerPadding) // Adapts layout for landscape on larger tablet screens
            )
        }
    }
}

// Preview on a smartwatch with a round screen
// The dimensions are set to typical watch size (width and height: 320dp), round with system UI included.
// This tests how the layout works on a small round screen.
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=320dp,height=320dp,dpi=280,isRound=true,chinSize=0dp,orientation=portrait"
)
@Composable
fun ResponsiveWatchPreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
                name = "Watch (Round)",
                modifier = Modifier.padding(innerPadding) // Adjusts layout to fit within the small round screen
            )
        }
    }
}
