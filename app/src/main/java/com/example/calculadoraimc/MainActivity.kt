package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IMCScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun IMCScreen(modifier: Modifier = Modifier) {

    var altura by remember { mutableStateOf("") }

    var peso by remember { mutableStateOf("") }


    var imc by remember {mutableStateOf(0.0)}

    var textinho by remember { mutableStateOf("RESULTADO") }

    var corFundo by remember {
        mutableStateOf(Color(239, 247, 207))
    }


    Column(modifier = modifier
        .fillMaxSize()
    ) {
        //Header
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(color = colorResource(R.color.cor_app)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.bmi),
                contentDescription = "BMI",
                modifier = Modifier
                    .size(80.dp)
                    .padding(vertical = 16.dp)
            )

            Text(text = "Calculadora IMC",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        //Form
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .offset(y = (-30).dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F6F6)),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(modifier = Modifier.padding(vertical = 32.dp),
                        text = "Seus dados",
                        fontSize = 24.sp,
                        color = colorResource(R.color.cor_app),
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(modifier = Modifier,
                        value = altura,
                        onValueChange = { altura = it},
                        label = {Text(text = "Altura")},
                        placeholder = {Text(text = "Digite sua altura")},
                        shape = RoundedCornerShape(size = 12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.cor_app),
                            unfocusedBorderColor = colorResource(R.color.cor_app)
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(modifier = Modifier,
                        value = peso,
                        onValueChange = { peso = it},
                        label = {Text(text = "Peso")},
                        placeholder = {Text(text = "Digite seu peso")},
                        shape = RoundedCornerShape(size = 12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(R.color.cor_app),
                            unfocusedBorderColor = colorResource(R.color.cor_app)
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )

                    )

                    Button(
                        onClick = {
                            if (peso.toDouble() > 0.0 && altura.toDouble() > 0.0){
                                imc = CalculoImc(altura.toDouble(),peso.toDouble())
                                textinho = Classificacao(imc)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.cor_app)
                        )

                    ) {

                        Text(text = "Calcular")
                    }
                }
            }
        }

        var imcFormat = String.format("%.2f",imc)
        //Card resultado
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .height(65.dp)
            .offset(y = (30).dp),
            colors = CardDefaults.cardColors(containerColor = Color(87, 155, 111)),
            elevation = CardDefaults.cardElevation(4.dp),

        ) {

            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
                ) {
                Text(
                    text = "${imcFormat}",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${textinho}",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        peso = ""
        altura = ""
    }
}


fun CalculoImc(altura: Double, peso: Double): Double {
    var imc = peso/(altura*altura)
    return imc
}

fun Classificacao(classificacao: Double): String{

    var resultado = ""

    if (classificacao < 18.5){
        resultado = "Abaixo do peso"
    }else if(classificacao > 18.5 && classificacao < 25){
        resultado = "Peso ideal"
    }else if(classificacao >= 25 && classificacao < 30){
        resultado = "Levemete acima do peso"
    }else if(classificacao >= 30 && classificacao < 35){
        resultado = "Obesidade 1"
    }else if(classificacao >= 35 && classificacao < 40){
        resultado = "Obesidade 2"
    }else{
        resultado = "Obesidade 3"
    }

    return resultado
}

fun Cor(classificacao: Double): Color{

    return if (classificacao < 18.5){
        Color.Gray
    }else if(classificacao > 18.5 && classificacao < 25){
        Color.Green
    }else if(classificacao >= 25 && classificacao < 30){
        Color.Yellow
    }else{
        Color.Red
    }
}
