package br.com.rememo.ui.screens.reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rememo.R
import br.com.rememo.ui.components.ReMemoTopBar

data class Reminder(
    val title: String,
    val dateTime: String,
    val repeatInterval: String,
    val alarms: List<String>,
    val completed: Boolean
)

@Composable
fun ReminderListScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Botão de adicionar lembrete"
                    )
                }
        }
    )
    { innerPadding ->
        var selectedTab by remember { mutableIntStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .height(50.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedTab = 0
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Text("Ativos")

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(
                                if(selectedTab == 0){
                                    Color.White
                                }
                                else{
                                    Color.Transparent
                                }
                            )
                    )

                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable{
                            selectedTab = 1
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Concluídos"
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(
                                if(selectedTab == 1){
                                    Color.White
                                }
                                else{
                                    Color.Transparent
                                }
                            )
                    )
                }
            }

//            ---------------------------------------------------------------------------

            val reminders = remember {
                mutableListOf(
                    Reminder(
                        title = "Lavar roupas",
                        dateTime = "Seg., 17 de ago., 16:00",
                        repeatInterval = "30 min",
                        alarms = listOf(
                            "Seg., 17 de ago., 15:00",
                            "Seg., 17 de ago., 15:30",
                            "Seg., 17 de ago., 15:45"
                        ),
                        completed = false
                    ),

                    Reminder(
                        title = "Estudar Kotlin",
                        dateTime = "Ter., 18 de ago., 19:00",
                        repeatInterval = "1 hora",
                        alarms = listOf(
                            "Ter., 18 de ago., 18:00",
                            "Ter., 18 de ago., 18:30"
                        ),
                        completed = false
                    ),

                    Reminder(
                        title = "Comprar presente",
                        dateTime = "Qua., 19 de ago., 14:00",
                        repeatInterval = "30 min",
                        alarms = listOf(
                            "Qua., 19 de ago., 13:00"
                        ),
                        completed = true
                    ),

                    Reminder(
                        title = "Estudar Kotlin",
                        dateTime = "Ter., 18 de ago., 19:00",
                        repeatInterval = "1 hora",
                        alarms = listOf(
                            "Ter., 18 de ago., 18:00",
                            "Ter., 18 de ago., 18:30"
                        ),
                        completed = false
                    ),

                    Reminder(
                        title = "Estudar Kotlin",
                        dateTime = "Ter., 18 de ago., 19:00",
                        repeatInterval = "1 hora",
                        alarms = listOf(
                            "Ter., 18 de ago., 18:00",
                            "Ter., 18 de ago., 18:30"
                        ),
                        completed = false
                    ),

                    Reminder(
                        title = "Estudar Kotlin",
                        dateTime = "Ter., 18 de ago., 19:00",
                        repeatInterval = "1 hora",
                        alarms = listOf(
                            "Ter., 18 de ago., 18:00",
                            "Ter., 18 de ago., 18:30"
                        ),
                        completed = false
                    ),
                )
            }

            val filteredReminders = reminders.filter {
                it.completed == (selectedTab == 1)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 90.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ){
                    items(filteredReminders){ reminder ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {

                            }
                        ) {
                            Row(
//                            modifier = Modifier.padding(16.dp),
//                            verticalAlignment = Alignment.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .clickable{

                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .border(
                                                width = 2.dp,
                                                color = Color.White,
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (reminder.completed) {
                                            Box(
                                                modifier = Modifier
                                                    .size(16.dp)
                                                    .background(
                                                        color = Color.White,
                                                        shape = CircleShape
                                                    )
                                            )
                                        }
                                    }
                                }

                                Column(
                                    modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
                                ) {
                                    Text(
                                        text = reminder.title,
                                        fontSize = 20.sp
                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(top = 4.dp)
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(50.dp),
                                            color = MaterialTheme.colorScheme.primaryContainer
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(
                                                    horizontal = 8.dp,
                                                    vertical = 2.dp
                                                ),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.calendar_check_icon),
                                                    contentDescription = "Ícone de lembrete",
                                                    modifier = Modifier
                                                        .size(16.dp)
                                                )

                                                Text(
                                                    text = reminder.dateTime,
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.padding(start = 4.dp)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Icon(
                                            painter = painterResource((R.drawable.reminder_repeat)),
                                            contentDescription = "Ícone de repetir lembrete",
                                            modifier = Modifier.size(16.dp)
                                        )


                                        Text(
                                            text = reminder.repeatInterval,
                                            fontSize = 12.sp,
                                            modifier = Modifier.padding(start = 2.dp)
                                        )
                                    }

                                    reminder.alarms.forEach { alarm ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(start = 8.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(R.drawable.alarm_icon),
                                                contentDescription = "Ícone de alarme",
                                                modifier = Modifier
                                                    .size(16.dp)
                                            )

                                            Text(
                                                text = alarm,
                                                fontSize = 12.sp,
                                                modifier = Modifier.padding(start = 4.dp)
                                            )
                                        }

                                        //Spacer(modifier = Modifier.height(4.dp))
                                    }
                                }
                            }
                        }

                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.background.copy(alpha = 0f),
                                    MaterialTheme.colorScheme.background.copy(alpha = 0f),
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                )
            }
        }
    }
}