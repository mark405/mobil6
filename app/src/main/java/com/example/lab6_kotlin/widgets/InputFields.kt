package com.example.lab6_kotlin.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.lab6_kotlin.models.InputModel

@Composable
fun InputFields(inputModel: InputModel, onInputChange: (InputModel) -> Unit) {
    var n by remember { mutableStateOf(inputModel.n.toString()) }
    var Pn by remember { mutableStateOf(inputModel.Pn.toString()) }
    var Un by remember { mutableStateOf(inputModel.Un.toString()) }
    var cos by remember { mutableStateOf(inputModel.cos.toString()) }
    var eta by remember { mutableStateOf(inputModel.eta.toString()) }
    var Kv by remember { mutableStateOf(inputModel.Kv.toString()) }
    var tg by remember { mutableStateOf(inputModel.tg.toString()) }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(value = n, onValueChange = {
            n = it
            onInputChange(inputModel.copy(n = it.toInt()))
        }, label = { Text("n") })

        TextField(value = Pn, onValueChange = {
            Pn = it
            onInputChange(inputModel.copy(Pn = it.toDouble()))
        }, label = { Text("Pn") })

        TextField(value = Un, onValueChange = {
            Un = it
            onInputChange(inputModel.copy(Un = it.toDouble()))
        }, label = { Text("Un") })

        TextField(value = cos, onValueChange = {
            cos = it
            onInputChange(inputModel.copy(cos = it.toDouble()))
        }, label = { Text("cos") })

        TextField(value = eta, onValueChange = {
            eta = it
            onInputChange(inputModel.copy(eta = it.toDouble()))
        }, label = { Text("eta") })

        TextField(value = Kv, onValueChange = {
            Kv = it
            onInputChange(inputModel.copy(Kv = it.toDouble()))
        }, label = { Text("Kv") })

        TextField(value = tg, onValueChange = {
            tg = it
            onInputChange(inputModel.copy(tg = it.toDouble()))
        }, label = { Text("tg") })
    }
}