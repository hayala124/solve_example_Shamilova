package com.example.solve_example_shamilova

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.solve_example_shamilova.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonStart: Button
    private lateinit var buttonCheck: Button
    private lateinit var editValue: EditText

    var number_1: Int = 0
    var number_2: Int = 0
    var operation: Char = ' '

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonStart = binding.btnStart
        buttonCheck = binding.btnCheck
        editValue = binding.editValue

        buttonStart.setOnClickListener {
            val (number_1, number_2, operation) = onButtonStartPressed()
            this.number_1 = number_1
            this.number_2 = number_2
            this.operation = operation
        }
        buttonCheck.setOnClickListener { onButtonCheckPressed(number_1, number_2, operation) }

    }

    private fun onButtonCheckPressed(number1: Int, number2: Int, operation: Char) {
        if (editValue.text.isEmpty()) {
            Toast.makeText(applicationContext, "Введите ответ!", Toast.LENGTH_SHORT).show()
        } else if ((operation == '+' && ((number1 + number2).toString() == editValue.text.toString())) ||
            (operation == '-' && ((number1 - number2).toString() == editValue.text.toString())) ||
            (operation == '*' && ((number1 * number2).toString() == editValue.text.toString())) ||
            (operation == '/' && ((number1 / number2).toString() == editValue.text.toString()))
        ) {
            button_edit_enabled_false()
            editValue.setBackgroundColor(Color.GREEN)
        } else {
            button_edit_enabled_false()
            editValue.setBackgroundColor(Color.RED)
        }
    }

    private fun button_edit_enabled_false() {
        editValue.isEnabled = false
        buttonCheck.isEnabled = false
    }
    private fun onButtonStartPressed(): Triple<Int, Int, Char> {
        var number1: Int
        var number2: Int
        var operation = getRandomOperation()
        binding.txtOperation.text = operation.toString()

        do {
            number1 = getRandomNumber()
            number2 = getRandomNumber()
            if (operation == '/') {
                if (number1 % number2 == 0) {
                    break
                }
            } else
                break
        } while (true)

        binding.txtFirstOperand.text = number1.toString()
        binding.txtSecondOperand.text = number2.toString()
        buttonStart.isEnabled = false
        editValue.isEnabled = true
        buttonCheck.isEnabled = true

        return Triple(number1, number2, operation)
    }

    private fun getRandomNumber(): Int {
        return Random.nextInt(10, 100)
    }

    private fun getRandomOperation(): Char {
        return listOf('*', '/', '-', '+').random()
    }
}