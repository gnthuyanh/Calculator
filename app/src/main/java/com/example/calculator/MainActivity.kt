package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    private lateinit var tvDisplay: TextView
    private var currentInput: String = ""
    private var previousInput: String = ""
    private var operator: String = ""
    private var isNewOperator: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linear_layout)

        tvDisplay = findViewById(R.id.tvDisplay)


        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { numberClicked((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.btnPlus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { operatorClicked("x") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { operatorClicked("/") }

        findViewById<Button>(R.id.btnClearEntry).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnBackspace).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnDot).setOnClickListener { numberClicked(".") }
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener { toggleSign() }
    }

    private fun numberClicked(number: String) {
        if (isNewOperator) {
            currentInput = ""
            isNewOperator = false
        }
        currentInput += number
        updateDisplay(currentInput)
    }

    private fun operatorClicked(op: String) {
        if (currentInput.isNotEmpty()) {
            if (operator.isNotEmpty()) {
                calculateResult()
            }
            operator = op
            previousInput = currentInput
            isNewOperator = true
        }
    }

    private fun calculateResult() {
        if (operator.isNotEmpty() && currentInput.isNotEmpty()) {
            if(operator == "/" && currentInput.toInt() == 0){
                updateDisplay("Error")
                currentInput = ""
                previousInput = ""
                operator = ""
                return
            }

            val result = when (operator) {
                "+" -> previousInput.toDouble() + currentInput.toDouble()
                "-" -> previousInput.toDouble() - currentInput.toDouble()
                "x" -> previousInput.toDouble() * currentInput.toDouble()
                "/" -> previousInput.toDouble() / currentInput.toDouble()
                else -> 0.0
            }
            val df = DecimalFormat("#.##")
            currentInput = df.format(result)
            updateDisplay(currentInput)
            operator = ""
        }
    }

    private fun updateDisplay(value: String) {
        tvDisplay.text = value
    }

    private fun clearAll() {
        currentInput = ""
        previousInput = ""
        operator = ""
        updateDisplay("0")
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            if (currentInput.isEmpty()) {
                updateDisplay("0")
            } else {
                updateDisplay(currentInput)
            }
        }
    }

    private fun clearEntry() {
        currentInput = ""
        updateDisplay("0")
    }

    private fun toggleSign() {
        if (currentInput.isNotEmpty() && currentInput != "0") {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.drop(1)
            } else {
                "-$currentInput"
            }
            updateDisplay(currentInput)
        }
    }
}