package br.com.rememo.ui.components

import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import br.com.rememo.R

@Composable
fun ReMemoBottomBar(
    currentRoute: String?,
    onAlarmsClick: () -> Unit,
    onRemindersClick: () -> Unit
){
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        NavigationBarItem(
            selected = currentRoute == "alarms",
            onClick = onAlarmsClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.alarm_icon),
                    contentDescription = "Alarmes"
                )
            },
            label = {
                Text("Alarmes")
            }
        )

        NavigationBarItem(
            selected = currentRoute == "reminders",
            onClick = onRemindersClick,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.calendar_check_icon),
                    contentDescription = "Lembretes"
                )
            },
            label = {
                Text("Lembretes")
            }
        )
    }
}