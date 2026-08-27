package br.com.rememo.ui.screens.alarms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rememo.R
import br.com.rememo.ui.components.ReMemoTopBar
import br.com.rememo.ui.theme.ReMemoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmListScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
            ) {
                val alarms = listOf(
                    "5:30" to "Seg. a Sex.",
                    "7:00" to "Seg. a Sáb.",
                    "12:30" to "Todos os dias",
                    "18:00" to "Seg. a Sex.",
                    "12:30" to "Todos os dias",
                    "12:30" to "Todos os dias",
                    "12:30" to "Todos os dias",
                    "12:30" to "Todos os dias",
                    "12:30" to "Todos os dias",
                )

                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 90.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(alarms) { alarm ->
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                        ) {
                            Row(modifier = Modifier
                                .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column() {
                                    Text(text = alarm.first, fontSize = 32.sp)

                                    Text(text = alarm.second, fontSize = 16.sp)
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Switch(checked = true, onCheckedChange = {

                                })
                            }
                        }
                    }
                }

                //Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background.copy(alpha = 0f),
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp)
                        .align(Alignment.BottomCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,

                        ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = 20.dp,
                                vertical = 16.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painter = painterResource(R.drawable.alarm_icon), contentDescription = "Tempo restante até próximo alarme")

                            Text(
                                text = "1d, 24h, 59m, 59s",
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    FloatingActionButton(onClick = {

                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar alarme")
                    }
                }
            }
        }
    }
}

