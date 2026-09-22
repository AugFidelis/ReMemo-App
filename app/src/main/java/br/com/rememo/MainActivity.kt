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
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.rememo.ui.screens.alarms.AlarmListScreen
import br.com.rememo.ui.screens.reminders.ReminderListScreen
import br.com.rememo.ui.components.ReMemoBottomBar
import br.com.rememo.ui.components.ReMemoTopBar
import br.com.rememo.ui.screens.alarms.AlarmViewModel
import br.com.rememo.ui.screens.alarms.EditAlarmScreen
import java.time.LocalTime

class MainActivity : ComponentActivity() {

    private val alarmViewModel: AlarmViewModel by viewModels()

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
//                    topBar = {
//                        if(currentRoute == "alarms" || currentRoute == "reminders"){
//                            ReMemoTopBar(
//                                actions = {
//                                    IconButton(
//                                        onClick = {
//
//                                        }
//                                    ) {
//                                        Icon(
//                                            imageVector = Icons.Default.MoreVert,
//                                            contentDescription = "Mais opções"
//                                        )
//                                    }
//                                }
//                            )
//                        }
//                    },
                    bottomBar = {
                        AnimatedVisibility(
                            visible = (currentRoute == "alarms" || currentRoute == "reminders"),
                            enter = slideInVertically(initialOffsetY = {it}) + fadeIn(),
                            exit = slideOutVertically(targetOffsetY = {it}) + fadeOut()
                        ) {
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
                        modifier = Modifier.padding(innerPadding),
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = tween(300)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = tween(300)
                            )
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = tween(300)
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = tween(300)
                            )
                        }
                    ){
                        composable("alarms"){
                            AlarmListScreen(
                                viewModel = alarmViewModel,
                                onAddAlarmClick = {
                                    navController.navigate("editAlarm")
                                },

                                onAlarmClick = { alarm ->
                                    navController.navigate("editAlarm?alarmId=${alarm.id}")
                                }
                            )
                        }

                        composable("reminders"){
                            ReminderListScreen()
                        }

                        composable(
                            route = "editAlarm?alarmId={alarmId}",
                            arguments = listOf(
                                navArgument("alarmId"){
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }
                            )
                        ){ backStackEntry ->
                            val alarmId = backStackEntry.arguments?.getString("alarmId")

                            EditAlarmScreen(
                                alarmId = alarmId,
                                viewModel = alarmViewModel,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}