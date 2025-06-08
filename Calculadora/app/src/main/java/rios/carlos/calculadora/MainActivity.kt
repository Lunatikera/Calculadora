package rios.carlos.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var pantalla: TextView
    private var expresion = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pantalla = findViewById(R.id.tvPantalla)
        val btnBorrar:Button = findViewById(R.id.btnBorrar)
        val btnRetroceso:Button = findViewById(R.id.btnRetroceso)
        val btnIgual:Button = findViewById(R.id.btnIgual)



        setBoton(R.id.btnCero, "0")
        setBoton(R.id.btnUno, "1")
        setBoton(R.id.btnDos, "2")
        setBoton(R.id.btnTres, "3")
        setBoton(R.id.btnCuatro, "4")
        setBoton(R.id.btnCinco, "5")
        setBoton(R.id.btnSeis, "6")
        setBoton(R.id.btnSiete, "7")
        setBoton(R.id.btnOcho, "8")
        setBoton(R.id.btnNueve, "9")
        setBoton(R.id.btnPunto, ".")

        setBoton(R.id.btnSumar, "+")
        setBoton(R.id.btnRestar, "-")
        setBoton(R.id.btnMultiplicar, "*")
        setBoton(R.id.btnDividir, "/")
        setBoton(R.id.btnPorcentaje, "%")

        btnBorrar.setOnClickListener {
            expresion = ""
            pantalla.text = "0"
        }

        btnRetroceso.setOnClickListener {
            if (expresion.isNotEmpty()) {
                expresion = expresion.dropLast(1)
                pantalla.text = expresion.ifEmpty { "0" }
            }
        }

        btnIgual.setOnClickListener {
            val resultado = evaluarExpresion(expresion)
            pantalla.text = resultado
            expresion = if (resultado != "Error") resultado else ""
        }
    }

    private fun setBoton(id: Int, valor: String) {
        findViewById<Button>(id).setOnClickListener {
            expresion += valor
            pantalla.text = expresion
        }
    }

    private fun evaluarExpresion(exp: String): String {
        return try {
            val tokens = mutableListOf<String>()
            var numero = ""

            for (c in exp) {
                if (c.isDigit() || c == '.') {
                    numero += c
                } else {
                    if (numero.isNotEmpty()) {
                        tokens.add(numero)
                        numero = ""
                    }
                    tokens.add(c.toString())
                }
            }
            if (numero.isNotEmpty()) tokens.add(numero)

            var resultado = tokens[0].toDouble()
            var i = 1
            while (i < tokens.size) {
                val operador = tokens[i]
                val siguiente = tokens[i + 1].toDouble()

                resultado = when (operador) {
                    "+" -> resultado + siguiente
                    "-" -> resultado - siguiente
                    "*" -> resultado * siguiente
                    "/" -> if (siguiente != 0.0) resultado / siguiente else return "Error"
                    "%" -> resultado % siguiente
                    else -> return "Error"
                }
                i += 2
            }
            resultado.toString()
        } catch (e: Exception) {
            "Error"
        }
    }
}
