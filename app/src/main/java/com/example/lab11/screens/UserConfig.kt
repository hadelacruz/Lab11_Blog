package com.example.lab11.screens


import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lab11.data.UserDataStore
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun UserConfigScreen(userDataStore: UserDataStore) {
    val coroutineScope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }

    // State variables for form inputs
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }



    // Load data if exists
    LaunchedEffect(Unit) {
        userDataStore.userData.collect { userData ->
            firstName = userData.firstName
            lastName = userData.lastName
            email = userData.email
            birthDate = userData.birthDate
            age = if (userData.age != 0) userData.age.toString() else ""
        }
    }

    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        DatePickerField(birthDate) { selectedDate ->
            birthDate = selectedDate
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            coroutineScope.launch {
                userDataStore.saveUserData(
                    firstName,
                    lastName,
                    email,
                    birthDate,
                    age.toIntOrNull() ?: 0
                )
            }

            showDialog = true

        }, colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.White
        )) {
            Text("Save")
        }
    }

    // Dialog to indicate success
    if (showDialog) {
        ConfirmationDialog(
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun DatePickerField(value: String, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, selectedYear, selectedMonth, selectedDay ->
            val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(date)
        }, year, month, day
    )

    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text("Birth Date") },
        enabled = false,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
            }
        }
    )
}

@Composable
fun ConfirmationDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        },
        title = {
            Text(text = "Success")
        },
        text = {
            Text("Your data has been saved successfully.")
        }
    )
}