package br.com.rememo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.rememo.ui.theme.ReMemoTheme
import android.content.Intent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.rememo.ui.screens.alarms.AlarmListScreen
import br.com.rememo.ui.screens.reminders.ReminderListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ReMemoTheme {
                val navController = rememberNavController()

                Scaffold(
                    contentWindowInsets = WindowInsets(0.dp),
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ) {
                            NavigationBarItem(
                                selected = false,
                                onClick = {
                                    navController.navigate("alarms")
                                },
                                icon = { Icon(painter = painterResource(R.drawable.alarm_icon), contentDescription = "Alarmes") },
                                label = { Text("Alarmes") }
                            )

                            NavigationBarItem(
                                selected = false,
                                onClick = {
                                    navController.navigate("reminders")
                                },
                                icon = { Icon(painter = painterResource(R.drawable.calendar_check_icon), contentDescription = "Lembretes") },
                                label = { Text("Lembretes") }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "alarms",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("alarms"){
                            AlarmListScreen()
                        }

                        composable("reminders"){
                            ReminderListScreen()
                        }
                    }
                }
            }
        }
    }
}