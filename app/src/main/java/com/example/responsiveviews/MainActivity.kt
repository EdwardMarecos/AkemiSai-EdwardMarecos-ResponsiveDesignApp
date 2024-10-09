package com.example.responsiveviews

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Responsive Views based on device configuration and screen size and orientation (portrait/landscape)
@Composable
fun Response(modifier: Modifier = Modifier) {
    // Sharing state (rounds) between components
    val rounds = remember { mutableStateListOf<String>() }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val configuration = LocalConfiguration.current
        val orientation = configuration.orientation

        // Use the maxWidth from BoxWithConstraints for device size checks
        when {
            // Tablet in landscape mode
            orientation == Configuration.ORIENTATION_LANDSCAPE && maxWidth > 600.dp -> {
                TabletLandscapePanel(rounds)
            }
            // Tablet in portrait mode
            orientation == Configuration.ORIENTATION_PORTRAIT && maxWidth > 600.dp -> {
                TabletPortraitContent(rounds)
            }
            // Phone in landscape mode
            orientation == Configuration.ORIENTATION_LANDSCAPE && maxWidth <= 600.dp -> {
                PhoneLandscapeContent(rounds)
            }
            // Phone in portrait mode
            orientation == Configuration.ORIENTATION_PORTRAIT && maxWidth <= 600.dp -> {
                PhonePortraitContent(rounds)
            }
            else -> {
                PhonePortraitContent(rounds) // Default to phone portrait if other checks fail
            }
        }
    }
}

// Tablet content in portrait mode (larger device)
@Composable
fun TabletPortraitContent(rounds: MutableList<String>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray) // Add background color
    ) {
        Text(text = "Tablet Portrait Layout", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Cookie Clicker App
        CookieClickerApp(rounds)
        Spacer(modifier = Modifier.height(16.dp))

        // Dynamic scoreboard
        DynamicScoreboard(rounds)
    }
}

// Tablet content in landscape mode (larger device) with side panel and dynamic scoreboard
@Composable
fun TabletLandscapePanel(rounds: MutableList<String>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan) // Add background color for landscape mode
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(16.dp)
            .background(Color.Yellow)) {
            Text(text = "Tablet Landscape Layout", style = MaterialTheme.typography.headlineMedium)
        }

        // Dynamic scoreboard in the middle
        DynamicScoreboard(rounds)

        // Cookie clicker on the right
        Column(modifier = Modifier
            .weight(2f)
            .padding(16.dp)
            .background(Color.Green)) {
            CookieClickerApp(rounds)
        }
    }
}

// Phone content in portrait mode (small device)
@Composable
fun PhonePortraitContent(rounds: MutableList<String>) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.LightGray) // Add background color for portrait mode
    ) {
        Text(text = "Phone Portrait Layout", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        CookieClickerApp(rounds)
    }
}

// Phone content in landscape mode (small device) with dynamic scoreboard
@Composable
fun PhoneLandscapeContent(rounds: MutableList<String>) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Magenta) // Add background color for landscape mode
    ) {
        // Dynamic scoreboard in the middle
        DynamicScoreboard(rounds)

        // Counter and Buttons on the right
        Column(modifier = Modifier
            .weight(2f)
            .padding(16.dp)
            .background(Color.Red)) {
            CookieClickerApp(rounds)
        }
    }
}

// Dynamic scoreboard panel (separated from CookieClickerApp)
@Composable
fun DynamicScoreboard(rounds: MutableList<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // This LazyColumn will display the dynamic scoreboard
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(rounds.size) { index ->
                Text("Round ${index + 1}: ${rounds[index]} clicks")
            }
        }
    }
}

// A simple cookie clicker app (without scoreboard, handled separately)
@Composable
fun CookieClickerApp(rounds: MutableList<String>) {
    var count by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display current score
        ScorePanel(count)

        Spacer(modifier = Modifier.height(16.dp))

        // Action buttons
        ActionPanel { action ->
            when (action) {
                "Increment" -> count++
                "Reset" -> {
                    rounds.add(count.toString())  // Log the number of clicks
                    count = 0  // Reset the counter
                }
            }
        }
    }
}

// Action panel for buttons
@Composable
fun ActionPanel(onAction: (String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Button(onClick = { onAction("Increment") }) { Text("Increment") }
        Button(onClick = { onAction("Reset") }) { Text("Reset") }
    }
}

// Panel for displaying the current score
@Composable
fun ScorePanel(count: Int) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text("Current Score: $count", style = MaterialTheme.typography.headlineMedium)
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
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

// Preview on a phone in landscape mode
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=411dp,height=891dp,orientation=landscape"
)
@Composable
fun ResponsiveLandscapePhonePreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

// Preview on a tablet in portrait mode
@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait"
)
@Composable
fun ResponsivePortraitTabletPreview() {
    ResponsiveViewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Response(
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
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
