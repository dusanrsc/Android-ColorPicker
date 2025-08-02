package com.infinitysoftware.colorpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.infinitysoftware.colorpicker.ui.theme.ColorPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ColorPickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorPickerApp()
                }
            }
        }
    }
}

@Composable
fun ColorPickerApp() {
    var showPicker by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color.White) }

    // Color Picker Controller.
    val controller = rememberColorPickerController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(selectedColor),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { showPicker = true }) {
            Text("Pick Color")
        }

        if (showPicker) {
            AlertDialog(
                onDismissRequest = { showPicker = false },
                confirmButton = {
                    Button(onClick = { showPicker = false }) {
                        Text("OK")
                    }
                },
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(16.dp),
                            controller = controller,
                            onColorChanged = { colorEnvelope ->
                                selectedColor = colorEnvelope.color
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "HEX: ${controller.selectedColor.value.toArgb().toUInt().toString(16)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            )
        }
    }
}