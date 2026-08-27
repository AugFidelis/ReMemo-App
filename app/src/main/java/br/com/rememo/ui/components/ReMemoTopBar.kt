package br.com.rememo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.rememo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReMemoTopBar(
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null
    // @Composable pois é um elemento da interface, não só um valor
){
    CenterAlignedTopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Image(
                painter = painterResource(R.drawable.rememo_topbaricon),
                contentDescription = "Ícone do app ReMemo",
                modifier = Modifier.size(40.dp)
            )
        },
        navigationIcon = {
            navigationIcon?.invoke()
        },
        actions = {
            actions?.invoke()
        }
    )
}
