package br.com.rememo.ui.screens.alarms

import android.widget.Space
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalTime
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerSelectionMode
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import br.com.rememo.ui.components.ReMemoTopBar
import java.time.DayOfWeek
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState

fun dayFullName(day: DayOfWeek): String {
    return when (day) {
        DayOfWeek.MONDAY -> "Segunda-feira"
        DayOfWeek.TUESDAY -> "Terça-feira"
        DayOfWeek.WEDNESDAY -> "Quarta-feira"
        DayOfWeek.THURSDAY -> "Quinta-feira"
        DayOfWeek.FRIDAY -> "Sexta-feira"
        DayOfWeek.SATURDAY -> "Sábado"
        DayOfWeek.SUNDAY -> "Domingo"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerCard(
    hour: Int,
    minute: Int,
    onTimeSelected: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
){
    var showDialog by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
        is24Hour = true
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 25.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(width = 125.dp, height = 125.dp)
                .clip(RoundedCornerShape(18.dp))
                .border(
                    BorderStroke(2.dp, Color.White),
                    shape = RoundedCornerShape(18.dp)
                )
                .clickable {
                    showDialog = true
                    timePickerState.hour = hour
                    timePickerState.minute = minute
                    timePickerState.selection = TimePickerSelectionMode.Hour
                },
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "%02d".format(hour),
                fontSize = 65.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }

        Text(
            text = ":",
            fontSize = 52.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Box(
            modifier = Modifier
                .size(width = 125.dp, height = 125.dp)
                .clip(RoundedCornerShape(18.dp))
                .border(
                    BorderStroke(2.dp, Color.White),
                    shape = RoundedCornerShape(18.dp)
                )
                .clickable {
                    showDialog = true
                    timePickerState.hour = hour
                    timePickerState.minute = minute
                    timePickerState.selection = TimePickerSelectionMode.Minute
                },
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "%02d".format(minute),
                fontSize = 65.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }

        if(showDialog){
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onTimeSelected(timePickerState.hour, timePickerState.minute)
                            showDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                },
                text = {
                    TimePicker(state = timePickerState)
                }
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditAlarmScreen(
    alarmId: String? = null,
    viewModel: AlarmViewModel,
    onNavigateBack: () -> Unit
){
    val existingAlarm = viewModel.getAlarmById(alarmId)
    val isEditing = (existingAlarm != null)

    val mondayToFriday = setOf(
        DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
    )

    var selectedHour by remember { mutableIntStateOf(existingAlarm?.time?.hour ?: LocalTime.now().hour) }
    var selectedMinute by remember { mutableIntStateOf(existingAlarm?.time?.minute ?: LocalTime.now().minute) }
    var selectedRepeatOption by remember {
        mutableStateOf(when {
            existingAlarm == null -> "Uma vez"
            existingAlarm.days.isEmpty() -> "Uma vez"
            existingAlarm.days.size == 7 -> "Diariamente"
            existingAlarm.days == mondayToFriday -> "Segunda a Sexta"
            else -> "Personalizado"
        })
    }
    var selectedCustomDays by remember {
        mutableStateOf<Set<DayOfWeek>>(
            if (existingAlarm != null &&
                existingAlarm.days.isNotEmpty() &&
                existingAlarm.days.size != 7 &&
                existingAlarm.days != mondayToFriday)
                existingAlarm.days
            else
                emptySet()
        )
    }
    var alarmName by remember { mutableStateOf(existingAlarm?.name ?: "") }
    var confirmationText by remember { mutableStateOf(existingAlarm?.confirmationText ?: "") }

    var isRepeatExpanded by remember { mutableStateOf(false) }
    val repeatOptions = listOf("Uma vez", "Diariamente", "Segunda a Sexta", "Personalizado")

    val nameFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val confirmationFocusRequester = remember { FocusRequester() }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showCustomDaysSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    Scaffold(
        topBar = {
            ReMemoTopBar(
                actions = {
                    IconButton(
                        onClick = {
                            val days = when(selectedRepeatOption){
                                "Diariamente" -> DayOfWeek.entries.toSet()
                                "Segunda a Sexta" -> setOf(
                                    DayOfWeek.MONDAY,  DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                                    DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
                                )
                                "Personalizado" -> selectedCustomDays
                                else -> emptySet()
                            }

                            viewModel.saveAlarm(
                                id = alarmId,
                                time = LocalTime.of(selectedHour, selectedMinute),
                                days = days,
                                name = alarmName,
                                confirmationText = confirmationText
                            )

                            onNavigateBack()
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
                            onNavigateBack()
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit){
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePickerCard(
                hour = selectedHour,
                minute = selectedMinute,
                onTimeSelected = { newHour, newMinute ->
                    selectedHour = newHour
                    selectedMinute = newMinute
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{
                                isRepeatExpanded = !isRepeatExpanded
                            }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Repetir")

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedRepeatOption,
                                fontSize = 12.sp
                            )

                            Icon(
                                imageVector = if (!isRepeatExpanded) {
                                    Icons.Default.KeyboardArrowDown
                                } else {
                                    Icons.AutoMirrored.Filled.KeyboardArrowLeft
                                },
                                contentDescription = if (isRepeatExpanded) "Recolher" else "Expandir"
                            )
                        }
                    }

                    if(isRepeatExpanded){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1E1E24))
                        ) {
                            repeatOptions.forEach { option ->
                                val isSelected = (option == selectedRepeatOption)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(if(isSelected) Color(0xFF2E2D38) else Color.Transparent)
                                        .clickable {
                                            selectedRepeatOption = option
                                            if (option == "Personalizado") {
                                                showCustomDaysSheet = true
                                            }
                                        }
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                    Text(text = option)

                                    if (option == "Personalizado") {
                                        Spacer(modifier = Modifier.weight(1f))
                                        if (selectedCustomDays.isNotEmpty()) {
                                            Text(
                                                text = formatAlarmDays(selectedCustomDays),
                                                fontSize = 10.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                        }
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = "Personalizar dias",
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //---------------------------------------------------------------------------------------

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                onClick = {
                    nameFocusRequester.requestFocus()
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Nome")

                    BasicTextField(
                        value = alarmName,
                        onValueChange = {alarmName = it},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            textAlign = TextAlign.End
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                            .focusRequester(nameFocusRequester),
                        decorationBox = { innerTextField ->
                            Box(contentAlignment = Alignment.CenterEnd){
                                if(alarmName.isEmpty()){
                                    Text(
                                        text = "Inserir nome",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                        fontSize = 12.sp
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            }

            //---------------------------------------------------------------------------------------

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                onClick = {
                    confirmationFocusRequester.requestFocus()
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Texto de confirmação")

                    BasicTextField(
                        value = confirmationText,
                        onValueChange = {confirmationText = it},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            textAlign = TextAlign.End
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                            .focusRequester(confirmationFocusRequester),
                        decorationBox = { innerTextField ->
                            Box(contentAlignment = Alignment.CenterEnd){
                                if(confirmationText.isEmpty()){
                                    Text(
                                        text = "Nenhum",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                        fontSize = 12.sp
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            }

            //---------------------------------------------------------------------------------------

            if(isEditing){
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    onClick = {
                        showDeleteDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF3C0000)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Excluir alarme",
                            color = Color(0xFFFFCBCB)
                        )
                    }
                }
            }

            if(showDeleteDialog){
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Excluir alarme?") },
                    text = { Text("Tem certeza de que deseja apagar este alarme? \nEle será excluído permanentemente.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDeleteDialog = false

                                if(alarmId != null){
                                    viewModel.deleteAlarm(alarmId)
                                }

                                onNavigateBack()
                            }
                        ) {
                            Text(
                                text = "Excluir",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDeleteDialog = false
                            }
                         ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }

    //------------------------------------------------------------------------------------------

    fun closeCustomDaysSheet() {
        val newRepeatOption = when {
            selectedCustomDays.isEmpty() -> "Uma vez"
            selectedCustomDays.size == 7 -> "Diariamente"
            selectedCustomDays == mondayToFriday -> "Segunda a Sexta"
            else -> "Personalizado"
        }

        selectedRepeatOption = newRepeatOption

        if (newRepeatOption != "Personalizado") {
            selectedCustomDays = emptySet()
        }

        showCustomDaysSheet = false
    }

    if (showCustomDaysSheet) {
        ModalBottomSheet(
            onDismissRequest = { closeCustomDaysSheet() },
            sheetState = sheetState
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { closeCustomDaysSheet() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Voltar"
                    )
                }
                Text(
                    text = "Personalizar",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 48.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp)
            ) {
                DayOfWeek.entries.forEach { day ->
                    val isChecked = day in selectedCustomDays
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCustomDays = if (isChecked) {
                                    selectedCustomDays - day
                                } else {
                                    selectedCustomDays + day
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = dayFullName(day))

                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color.White,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isChecked) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .background(
                                            color = Color.White,
                                            shape = CircleShape
                                        )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(210.dp))
        }
    }
}
