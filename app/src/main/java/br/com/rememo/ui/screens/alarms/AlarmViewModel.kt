package br.com.rememo.ui.screens.alarms

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.time.DayOfWeek
import java.time.LocalTime

class AlarmViewModel : ViewModel(){

    val alarms = mutableStateListOf<Alarm>(
        Alarm(
            time = LocalTime.of(5, 30),
            days = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.FRIDAY),
            name = "Trabalho",
            confirmationText = "Estou ciente que é hora do trabalho",
            enabled = true
        ),
        Alarm(
            time = LocalTime.of(6, 30),
            days = setOf(
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
            ),
            name = "Treino",
            confirmationText = "Texto de confirmação",
            enabled = true
        )
    )

    fun getAlarmById(id: String?): Alarm? {
        if(id == null){
            return null
        }
        else{
            return alarms.find{ it.id == id }
        }
    }

    fun saveAlarm(
        id: String?,
        time: LocalTime,
        days: Set<DayOfWeek>,
        name: String,
        confirmationText: String
    ){
        if(id == null){
            val newAlarm = Alarm(
                time = time,
                days = days,
                name = name,
                confirmationText = confirmationText,
                enabled = true
            )
            alarms.add(newAlarm)
        }
        else{
            val index = alarms.indexOfFirst { it.id == id }

            if(index != -1){
                alarms[index] = alarms[index].copy(
                    time = time,
                    days = days,
                    name = name,
                    confirmationText = confirmationText
                )
            }
        }
    }

    fun deleteAlarm(id: String){
        alarms.removeAll { it.id == id }
    }

    fun toggleAlarm(id: String, enabled: Boolean){
        val index = alarms.indexOfFirst { it.id == id }
        if(index != -1){
            alarms[index] = alarms[index].copy(enabled = enabled)
        }
    }
}