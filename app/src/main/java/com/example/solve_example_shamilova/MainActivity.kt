package com.example.solve_example_shamilova

import android.content.res.ColorStateList
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

    private var number_1 = 0
    private var number_2 = 0
    private var operation = ' '
    private var countRightAnswer = 0
    private var countWrongAnswer = 0
    private var count = 0
    private var percent = 0.0
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
            editValue.setBackgroundColor(Color.GREEN)
            countRightAnswer++
            binding.txtNumberRight.text = countRightAnswer.toString()
            button_edit_enabled_false()
        } else {
            editValue.setBackgroundColor(Color.RED)
            countWrongAnswer++
            binding.txtNumberWrong.text = countWrongAnswer.toString()
            button_edit_enabled_false()
        }
    }

    private fun button_edit_enabled_false() {
        editValue.isEnabled = false
        buttonCheck.isEnabled = false
        buttonStart.isEnabled = true
        count++
        binding.txtAllExamples.text = count.toString()
        percent = countRightAnswer * 100 / count.toDouble()
        binding.txtPercentageCorrectAnswers.text = String.format("%.2f%%", percent)
    }
    private fun onButtonStartPressed(): Triple<Int, Int, Char> {
        editValue.setText("")
        editValue.setBackgroundColor(Color.WHITE)
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