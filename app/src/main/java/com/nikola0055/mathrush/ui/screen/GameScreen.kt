package com.nikola0055.mathrush.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nikola0055.mathrush.R
import com.nikola0055.mathrush.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    navController: NavController,
    difficulty: String,
    time: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                actions = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = stringResource(id = R.string.pause),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(
            modifier = Modifier.padding(innerPadding),
            difficulty = difficulty,
            time = time
        )
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier,
    difficulty: String,
    time: String
) {
    var score by rememberSaveable { mutableIntStateOf(0) }
    var targetAnswer by rememberSaveable { mutableIntStateOf(0) }
    var paused by rememberSaveable { mutableStateOf(false) }
    var finished by rememberSaveable { mutableStateOf(false) }

    val seconds = when (time) {
        stringResource(R.string.minute, 1) -> 60
        stringResource(R.string.minute, 2) -> 120
        stringResource(R.string.minute, 3) -> 180
        else -> 30
    }
    var timeLeft by rememberSaveable { mutableIntStateOf(seconds) }

    val totalInput = when (difficulty) {
        stringResource(R.string.hard) -> 4
        stringResource(R.string.medium) -> 3
        else -> 2
    }

    val numbers = rememberSaveable { mutableStateListOf<Int>().apply { repeat(totalInput) { add(0) } } }
    val operators = rememberSaveable { mutableStateListOf<Char>().apply { repeat(totalInput - 1) { add('+') } } }

    LaunchedEffect(Unit) {
        targetAnswer = generateNewQuestion(totalInput, operators)
    }

    LaunchedEffect(key1 = timeLeft, key2 = paused) {
        if (!paused && timeLeft > 0) {
            delay(1000)
            timeLeft -= 1
        } else if (timeLeft == 0) {

        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DifficultyLabel(difficulty)

            Text(
                text = formatTime(timeLeft),
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "SCORE: $score",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = if (targetAnswer == 0) "..." else targetAnswer.toString(),
            style = MaterialTheme.typography.displayLarge.copy(fontSize = 90.sp),
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 0 until totalInput) {
                if (i > 0) {
                    Text(
                        text = operators[i - 1].toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
                CustomTextField(
                    value = numbers[i],
                    onValueChange = { input ->
                        if (input.isEmpty()) {
                            numbers[i] = 0
                        } else if (input.length <= 2 && input.all { it.isDigit() }) {
                            numbers[i] = input.toInt()

                            if (numbers.all { it > 0 }) {
                                if (calculateResult(numbers, operators) == targetAnswer) {
                                    score += 10
                                    numbers.indices.forEach { numbers[it] = 0 }
                                    targetAnswer = generateNewQuestion(totalInput, operators)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

// ------------- UI -------------
@Composable
fun DifficultyLabel(difficulty: String) {
    val isDark = isSystemInDarkTheme()
    val color = when (difficulty) {
        stringResource(R.string.easy) -> if (isDark) EasyDark else EasyLight
        stringResource(R.string.medium) -> if (isDark) MediumDark else MediumLight
        stringResource(R.string.hard) -> if (isDark) HardDark else HardLight
        else -> MaterialTheme.colorScheme.primary
    }

    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = difficulty.uppercase(),
            color = color,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun CustomTextField(value: Int, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.size(width = 75.dp, height = 75.dp).padding(4.dp),
        value = if (value == 0) "" else value.toString(),
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        shape = RoundedCornerShape(8.dp)
    )
}

// ------------- LOGIC -------------
private fun generateNewQuestion(
    numCount: Int,
    operatorsList: MutableList<Char>
): Int {
    val availableOps = listOf('+', '-', '×', '÷')
    val numList = List(numCount) { (1..10).random() }

    operatorsList.clear()
    var runningResult = numList[0]

    for (i in 1 until numCount) {
        val nextNum = numList[i]
        var op = availableOps.random()

        if (op == '-' && (runningResult - nextNum <= 0)) op = '+'
        if (op == '÷' && runningResult % nextNum != 0) {
            op = '+'
        }
        operatorsList.add(op)

        runningResult = when (op) {
            '+' -> runningResult + nextNum
            '-' -> runningResult - nextNum
            '×' -> runningResult * nextNum
            '÷' -> runningResult / nextNum
            else -> runningResult
        }
    }

    return runningResult
}

private fun calculateResult(nums: List<Int>, ops: List<Char>): Int {
    if (nums.isEmpty()) return 0
    var res = nums[0]
    for (i in 0 until ops.size) {
        if (i + 1 >= nums.size) break
        val nextNum = nums[i + 1]
        res = when (ops[i]) {
            '+' -> res + nextNum
            '-' -> res - nextNum
            '×' -> res * nextNum
            '÷' -> if (nextNum != 0) res / nextNum else res
            else -> res
        }
    }
    return res
}

private fun formatTime(seconds: Int): String {
    return "%02d:%02d".format(seconds / 60, seconds % 60)
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GameScreenPreview() {
    AppTheme {
        GameScreen(
            navController = rememberNavController(),
            difficulty = stringResource(id = R.string.hard),
            time = stringResource(R.string.minute, 3)
        )
    }
}