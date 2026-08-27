package br.com.rememo.ui.screens.reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.rememo.ui.components.ReMemoTopBar

@Composable
fun ReminderListScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp),
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
                    
                    if(selectedTab == 0){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color(0xFFFFFFFF))
                        )
                    }
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

                    if(selectedTab == 1){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color(0xFFFFFFFF))
                        )
                    }
                }


//                TextButton(
//                    onClick = {
//                        selectedTab = 0
//                    },
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text("Ativos")
//                }
//
//                TextButton(
//                    onClick = {
//                        selectedTab = 1
//                    },
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text("Concluídos")
//                }
            }

            if(selectedTab == 0){
                Text("Ativos selecionado")
            }
            else if(selectedTab == 1){
                Text("Concluídos selecionado")
            }
        }
    }
}