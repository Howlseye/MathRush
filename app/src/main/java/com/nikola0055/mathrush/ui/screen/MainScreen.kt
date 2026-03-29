package com.nikola0055.mathrush.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikola0055.mathrush.R
import com.nikola0055.mathrush.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold { innerPadding ->
        ScreenContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    modifier: Modifier = Modifier
) {
    var difficultyExpanded by remember { mutableStateOf(false) }
    val difficultyOptions = listOf(
        stringResource(id = R.string.easy),
        stringResource(id = R.string.medium),
        stringResource(id = R.string.hard)
    )
    var difficulty by remember { mutableStateOf("")}

    var timeExpanded by remember { mutableStateOf(false) }
    val timeOptions = listOf(
        stringResource(id = R.string.seconds, 30),
        stringResource(id = R.string.minute, 1),
        stringResource(id = R.string.minute, 2),
        stringResource(id = R.string.minute, 3),
    )
    var time by remember { mutableStateOf("") }



    Column(
        modifier = modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.padding(bottom = 24.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_name)
        )

        CustomDropdown(
            label = stringResource(id = R.string.difficulty),
            placeholder = stringResource(id = R.string.difficulty_placeholder),
            options = difficultyOptions,
            value = difficulty,
            expanded = difficultyExpanded,
            onExpandedChange = { difficultyExpanded = !difficultyExpanded },
            onValueChange = {
                difficulty = it
            }
        )

        CustomDropdown(
            label = stringResource(id = R.string.time),
            placeholder = stringResource(id = R.string.time_placeholder),
            options = timeOptions,
            value = time,
            expanded = timeExpanded,
            onExpandedChange = { timeExpanded = !timeExpanded },
            onValueChange = {
                time = it
            }
        )

        Button(
            onClick = {  },
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.start),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(
    label: String,
    placeholder: String,
    options: List<String>,
    value: String,
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { onExpandedChange() }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                value = value,
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                placeholder = { Text(placeholder) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange() }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onValueChange(selectionOption)
                            onExpandedChange()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}