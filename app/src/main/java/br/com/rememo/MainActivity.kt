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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.rememo.ui.screens.alarms.AlarmListScreen
import br.com.rememo.ui.screens.reminders.ReminderListScreen
import br.com.rememo.ui.components.ReMemoBottomBar
import br.com.rememo.ui.components.ReMemoTopBar
import br.com.rememo.ui.screens.alarms.EditAlarmScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ReMemoTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    contentWindowInsets = WindowInsets(0.dp),
                    topBar = {
                        if(currentRoute == "alarms" || currentRoute == "reminders"){
                            ReMemoTopBar(
                                actions = {
                                    IconButton(
                                        onClick = {

                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.MoreVert,
                                            contentDescription = "Mais opções"
                                        )
                                    }
                                }
                            )
                        }
                        else{
                            ReMemoTopBar(
                                actions = {
                                    IconButton(
                                        onClick = {

                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Aceitar"
                                        )
                                    }
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {

                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Cancelar"
                                        )
                                    }
                                }
                            )
                        }
                    },
                    bottomBar = {
                        if(currentRoute == "alarms" || currentRoute == "reminders"){
                            ReMemoBottomBar(
                                currentRoute = currentRoute,
                                onAlarmsClick = {
                                    if(currentRoute != "alarms"){
                                        navController.navigate("alarms"){
                                            popUpTo("alarms")
                                            launchSingleTop = true
                                        }
                                    }
                                },
                                onRemindersClick = {
                                    if(currentRoute != "reminders"){
                                        navController.navigate("reminders"){
                                            popUpTo("reminders")
                                            launchSingleTop = true
                                        }
                                    }
                                }
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
                            AlarmListScreen(
                                onAddAlarmClick = {
                                    navController.navigate("editAlarm")
                                }
                            )
                        }

                        composable("reminders"){
                            ReminderListScreen()
                        }

                        composable("editAlarm"){
                            EditAlarmScreen()
                        }
                    }
                }
            }
        }
    }
}