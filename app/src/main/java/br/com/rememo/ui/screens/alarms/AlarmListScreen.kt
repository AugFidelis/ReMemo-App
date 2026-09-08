package br.com.rememo.ui.screens.alarms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rememo.R
import br.com.rememo.ui.components.ReMemoTopBar
import br.com.rememo.ui.theme.ReMemoTheme
import java.time.DayOfWeek
import java.time.LocalTime

data class Alarm(
    val time: LocalTime,
    val days: Set<DayOfWeek>,
    val name: String,
    val confirmationText: String,
    val enabled: Boolean
)

fun formatAlarmDays(days: Set<DayOfWeek>): String{

    if (days.isEmpty()) {
        return "Uma vez"
    }

    if(days.size == 7){
        return "Todos os dias"
    }

    val sortedDays = days.sortedBy { it.value }

    val shortenedDays = sortedDays.map{ dayAbbreviation(it)}
    return shortenedDays.joinToString(" ")
}

fun dayAbbreviation(day: DayOfWeek): String{
    return when (day) {
        DayOfWeek.MONDAY -> "Seg."
        DayOfWeek.TUESDAY -> "Ter."
        DayOfWeek.WEDNESDAY -> "Qua."
        DayOfWeek.THURSDAY -> "Qui."
        DayOfWeek.FRIDAY -> "Sex."
        DayOfWeek.SATURDAY -> "Sáb."
        DayOfWeek.SUNDAY -> "Dom."
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmListScreen(
    modifier: Modifier = Modifier,
    onAddAlarmClick: () -> Unit
) {
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
                val alarms = remember {
                    mutableListOf(
                        Alarm(
                            time = LocalTime.of(5, 30),
                            days = setOf(
                                DayOfWeek.MONDAY,
                                DayOfWeek.TUESDAY,
                                DayOfWeek.FRIDAY,
                            ),
                            name = "",
                            confirmationText = "Texto de confirmação",
                            enabled = true
                        ),

                        Alarm(
                            time = LocalTime.of(6, 30),
                            days = setOf(
                                DayOfWeek.MONDAY,
                                DayOfWeek.TUESDAY,
                                DayOfWeek.WEDNESDAY,
                                DayOfWeek.THURSDAY,
                                DayOfWeek.FRIDAY,
                                DayOfWeek.SATURDAY,
                                DayOfWeek.SUNDAY,
                            ),
                            name = "Alarme 1",
                            confirmationText = "Texto de confirmação",
                            enabled = true
                        ),

                        Alarm(
                            time = LocalTime.of(7, 30),
                            days = emptySet(),
                            name = "Alarme 1",
                            confirmationText = "Texto de confirmação",
                            enabled = true
                        ),

                        Alarm(
                            time = LocalTime.of(8, 30),
                            days = setOf(
                                DayOfWeek.MONDAY,
                                DayOfWeek.TUESDAY,
                                DayOfWeek.WEDNESDAY,
                                DayOfWeek.THURSDAY,
                                DayOfWeek.FRIDAY,
                            ),
                            name = "FJKGHBDSHGBDJGBDKJGBKDJHGBHGBDHGFBKHJSBJBGGKJHSBGJSDBJDSGKJHBFKJGBJHDBGKDHSGBFDG",
                            confirmationText = "Texto de confirmação",
                            enabled = true
                        ),

                        Alarm(
                            time = LocalTime.of(9, 30),
                            days = setOf(
                                DayOfWeek.MONDAY,
                                DayOfWeek.TUESDAY,
                                DayOfWeek.WEDNESDAY,
                                DayOfWeek.THURSDAY,
                                DayOfWeek.FRIDAY,
                            ),
                            name = "Alarme 1",
                            confirmationText = "Texto de confirmação",
                            enabled = true
                        ),

                        Alarm(
                            time = LocalTime.of(10, 30),
                            days = setOf(
                                DayOfWeek.MONDAY,
                                DayOfWeek.TUESDAY,
                                DayOfWeek.WEDNESDAY,
                                DayOfWeek.THURSDAY,
                                DayOfWeek.FRIDAY,
                            ),
                            name = "Alarme 1",
                            confirmationText = "Texto de confirmação",
                            enabled = true
                        ),
                    )
                }

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

                            .clickable{

                            }
                        ) {
                            Row(modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = alarm.time.toString(), fontSize = 32.sp)

                                    Text(text = formatAlarmDays(alarm.days),
                                        fontSize = 14.sp
                                    )

                                    if(alarm.name.isNotEmpty()){
                                        Text(text = alarm.name,
                                            fontSize = 14.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

//                                Spacer(modifier = Modifier.weight(1f))

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

                    FloatingActionButton(onClick = onAddAlarmClick) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar alarme")
                    }
                }
            }
        }
    }
}

