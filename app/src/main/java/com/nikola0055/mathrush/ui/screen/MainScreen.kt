package com.nikola0055.mathrush.ui.screen

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nikola0055.mathrush.R
import com.nikola0055.mathrush.ui.theme.AppTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController
) {
    Scaffold { innerPadding ->
        ScreenContent(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val currentAppLocales = AppCompatDelegate.getApplicationLocales()
    var language = if (currentAppLocales.isEmpty) Locale.getDefault().language else currentAppLocales.toLanguageTags()

    var isPlay by rememberSaveable { mutableStateOf(false) }

    var difficultyExpanded by rememberSaveable { mutableStateOf(false) }
    val difficultyOptions = listOf(
        stringResource(id = R.string.easy),
        stringResource(id = R.string.medium),
        stringResource(id = R.string.hard)
    )
    var difficulty by rememberSaveable { mutableStateOf("")}
    var difficultyError by rememberSaveable { mutableStateOf(false) }

    var timeExpanded by rememberSaveable { mutableStateOf(false) }
    val timeOptions = listOf(
        stringResource(id = R.string.seconds, 30),
        stringResource(id = R.string.minute, 1),
        stringResource(id = R.string.minute, 2),
        stringResource(id = R.string.minute, 3),
    )
    var time by rememberSaveable { mutableStateOf("") }
    var timeError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.scale(1.5f),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_name),
        )

        Spacer(Modifier.height(48.dp))

        if (isPlay) {
            PlayDisplay(
                difficultyOptions = difficultyOptions,
                difficulty = difficulty,
                onDifficultyValueChange = { difficulty = it },
                difficultyError = difficultyError,
                onDifficultyErrorChange = { difficultyError = !difficultyError },
                difficultyExpanded = difficultyExpanded,
                onDifficultyExpandedChange = { difficultyExpanded = !difficultyExpanded },
                timeOptions = timeOptions,
                time = time,
                onTimeValueChange = { time = it },
                timeError = timeError,
                onTimeErrorChange = { timeError = !timeError },
                timeExpanded = timeExpanded,
                onTimeExpandedChange = { timeExpanded = !timeExpanded },
                onPlayClicked = {
                    isPlay = false
                    navController.navigate("gameScreen/$difficulty/$time")
                },
                onBackClicked = { isPlay = false }
            )
        } else {
            Button(
                onClick = { isPlay = true},
                modifier = Modifier.fillMaxWidth(0.85f)
                    .padding(16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.play),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
            }
            Button(
                onClick = { navController.navigate("aboutScreen") },
                modifier = Modifier.fillMaxWidth(0.85f)
                    .padding(16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.about),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
            }
            Button(
                onClick = {
                    val newLanguage = if (language == "en") "id" else "en"
                    changeLanguage(newLanguage.lowercase())
                    language = newLanguage
                },
                modifier = Modifier.fillMaxWidth(0.85f)
                    .padding(16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.language, language.uppercase()),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun PlayDisplay(
    difficultyOptions: List<String>,
    difficulty: String,
    onDifficultyValueChange: (String) -> Unit,
    difficultyError: Boolean,
    onDifficultyErrorChange: () -> Unit,
    difficultyExpanded: Boolean,
    onDifficultyExpandedChange: () -> Unit,
    timeOptions: List<String>,
    time: String,
    onTimeValueChange: (String) -> Unit,
    timeError: Boolean,
    onTimeErrorChange: () -> Unit,
    timeExpanded: Boolean,
    onTimeExpandedChange: () -> Unit,
    onPlayClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    CustomDropdown(
        label = stringResource(id = R.string.difficulty),
        placeholder = stringResource(id = R.string.difficulty_placeholder),
        options = difficultyOptions,
        value = difficulty,
        isError = difficultyError,
        expanded = difficultyExpanded,
        onExpandedChange = { onDifficultyExpandedChange() },
        onValueChange = {
            onDifficultyValueChange(it)
        }
    )

    CustomDropdown(
        label = stringResource(id = R.string.time),
        placeholder = stringResource(id = R.string.time_placeholder),
        options = timeOptions,
        value = time,
        isError = timeError,
        expanded = timeExpanded,
        onExpandedChange = { onTimeExpandedChange() },
        onValueChange = {
            onTimeValueChange(it)
        }
    )

    Button(
        onClick = {
            if (difficulty.isNotEmpty() && time.isNotEmpty()) {
                onDifficultyErrorChange()
                onTimeErrorChange()
                onPlayClicked()
            } else {
                onDifficultyErrorChange()
                onTimeErrorChange()
            }
        },
        modifier = Modifier.fillMaxWidth(0.85f).padding(16.dp, 8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.start),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
    OutlinedButton(
        onClick = { onBackClicked() },
        modifier = Modifier.fillMaxWidth(0.85f).padding(16.dp, 0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ) {
        Text(
            text = stringResource(id = R.string.back),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(
    label: String,
    placeholder: String,
    options: List<String>,
    value: String,
    isError: Boolean,
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
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryEditable,
                        enabled = true
                    ),
                readOnly = true,
                value = value,
                onValueChange = {},
                isError = isError,
                supportingText = { if (isError) Text(stringResource(id = R.string.error_text), color = MaterialTheme.colorScheme.error) },
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

fun changeLanguage(languageCode: String) {
    val appLocales: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
    AppCompatDelegate.setApplicationLocales(appLocales)
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen(rememberNavController())
    }
}