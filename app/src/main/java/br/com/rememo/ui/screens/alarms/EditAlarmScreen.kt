package br.com.rememo.ui.screens.alarms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalTime
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.input.pointer.pointerInput

fun normalizeTimePart(
    value: String,
    maximum: Int
):String {
    val number = value.toIntOrNull()?: 0
    val normalized = number.coerceIn(0, maximum)

    return normalized.toString().padStart(2, '0')
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditAlarmScreen(){
    val currentTime = remember{
        LocalTime.now()
    }

    var hour by remember{
        mutableStateOf(currentTime.hour.toString().padStart(2, '0'))
    }

    var minute by remember{
        mutableStateOf(currentTime.minute.toString().padStart(2, '0'))
    }

    var previousHour by remember {
        mutableStateOf(hour)
    }

    var previousMinute by remember {
        mutableStateOf(minute)
    }

    val minuteFocusRequester = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current
    val isKeyboardVisible = WindowInsets.isImeVisible

    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible) {
            focusManager.clearFocus()
        }
    }

    Box(
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = hour,
                onValueChange = { newValue ->
                    if (newValue.length <= 2 && newValue.all { it.isDigit() }) {
                        hour = newValue
                    }
                },
                modifier = Modifier
                    .size(
                        width = 130.dp,
                        height = 130.dp
                    )
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            previousHour = hour
                            hour = ""
                        } else {
                            hour = if (hour.isBlank()) previousHour else normalizeTimePart(hour, 23)
                        }
                    },
                textStyle = TextStyle(
                    fontSize = 64.sp,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),

                keyboardActions = KeyboardActions(
                    onNext = {
                        minuteFocusRequester.requestFocus()
                    }
                )
            )

            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 130.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = ":",
                    fontSize = 64.sp
                )
            }

            OutlinedTextField(
                value = minute,
                onValueChange = { newValue ->
                    if (newValue.length <= 2 && newValue.all { it.isDigit() }) {
                        minute = newValue
                    }
                },
                modifier = Modifier
                    .size(
                        width = 130.dp,
                        height = 130.dp
                    )
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            previousMinute = minute
                            minute = ""
                        } else {
                            minute = if (minute.isBlank()) previousMinute else normalizeTimePart(minute, 59)
                        }
                    }
                    .focusRequester(minuteFocusRequester),
                textStyle = TextStyle(
                    fontSize = 64.sp,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        minute = if (minute.isBlank()) {
                            previousMinute
                        } else {
                            normalizeTimePart(minute, 59)
                        }

                        focusManager.clearFocus()
                    }
                )
            )
        }
    }
}
