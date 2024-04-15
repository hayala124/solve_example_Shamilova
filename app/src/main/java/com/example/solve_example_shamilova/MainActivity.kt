package com.example.solve_example_shamilova

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
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
    private var percent = 0.00
    private var editBackgroundColor = Color.WHITE
    private var buttonStartEnable = true
    private var buttonCheckEnable = false
    private var editTextEnable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonStart = binding.btnStart
        buttonCheck = binding.btnCheck
        editValue = binding.editValue

        buttonStart.setOnClickListener {
            val (number1, number2, operation) = onButtonStartPressed()
            this.number_1 = number1
            this.number_2 = number2
            this.operation = operation
        }
        buttonCheck.setOnClickListener { onButtonCheckPressed(number_1, number_2, operation) }

        if (savedInstanceState == null) {
            number_1 = 0
            number_2 = 0
            operation = '+'
            countRightAnswer = 0
            countWrongAnswer = 0
            count = 0
            percent = 0.00
            editBackgroundColor = ContextCompat.getColor(editValue.context, R.color.white)
            buttonStart.isEnabled = buttonStartEnable
            buttonCheck.isEnabled = buttonCheckEnable
            editValue.isEnabled = editTextEnable
        }
        else {
            number_1 = savedInstanceState.getInt(FIRST_OPERAND)
            number_2 = savedInstanceState.getInt(SECOND_OPERAND)
            operation = savedInstanceState.getChar(OPERATION)
            countRightAnswer = savedInstanceState.getInt(COUNT_RIGHT_ANSWER)
            countWrongAnswer = savedInstanceState.getInt(COUNT_WRONG_ANSWER)
            count = savedInstanceState.getInt(COUNT)
            percent = savedInstanceState.getDouble(PERCENT)
            editBackgroundColor = savedInstanceState.getInt(BACKGROUND_COLOR)
            buttonStart.isEnabled = savedInstanceState.getBoolean(BUTTON_START_ENABLED)
            buttonCheck.isEnabled = savedInstanceState.getBoolean(BUTTON_CHECK_ENABLED)
            editValue.isEnabled = savedInstanceState.getBoolean(EDIT_TEXT_ENABLED)
        }
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(FIRST_OPERAND, number_1)
        outState.putInt(SECOND_OPERAND, number_2)
        outState.putChar(OPERATION, operation)
        outState.putInt(COUNT_RIGHT_ANSWER, countRightAnswer)
        outState.putInt(COUNT_WRONG_ANSWER, countWrongAnswer)
        outState.putInt(COUNT, count)
        outState.putDouble(PERCENT, percent)
        outState.putInt(BACKGROUND_COLOR, editBackgroundColor)
        outState.putBoolean(BUTTON_START_ENABLED, buttonStart.isEnabled)
        outState.putBoolean(BUTTON_CHECK_ENABLED, buttonCheck.isEnabled)
        outState.putBoolean(EDIT_TEXT_ENABLED, editValue.isEnabled)
    }

    private fun renderState() = with(binding) {
        txtFirstOperand.setText(number_1.toString())
        txtSecondOperand.setText(number_2.toString())
        txtOperation.setText(operation.toString())
        txtNumberRight.setText(countRightAnswer.toString())
        txtNumberWrong.setText(countWrongAnswer.toString())
        txtAllExamples.setText(count.toString())
        txtPercentageCorrectAnswers.setText(String.format("%.2f%%", percent))
        editValue.setBackgroundColor(editBackgroundColor)

        if (btnStart.isEnabled == buttonStartEnable)
            btnStart.isEnabled = buttonStartEnable
        else
            btnStart.isEnabled = !buttonStartEnable

        if (btnCheck.isEnabled == buttonCheckEnable)
            btnCheck.isEnabled = buttonCheckEnable
        else
            btnCheck.isEnabled = !buttonCheckEnable

        if (editValue.isEnabled == editTextEnable)
            editValue.isEnabled = editTextEnable
        else
            editValue.isEnabled = !editTextEnable
    }
    companion object {
        @JvmStatic private val FIRST_OPERAND = "txtFirstOperand"
        @JvmStatic private val SECOND_OPERAND = "txtSecondOperand"
        @JvmStatic private val OPERATION = "sign"
        @JvmStatic private val COUNT_RIGHT_ANSWER = "countRightAnswer"
        @JvmStatic private val COUNT_WRONG_ANSWER = "countWrongAnswer"
        @JvmStatic private val COUNT = "count"
        @JvmStatic private val PERCENT = "percent"
        @JvmStatic private val BACKGROUND_COLOR = "color"
        @JvmStatic private val BUTTON_START_ENABLED = "button_start"
        @JvmStatic private val BUTTON_CHECK_ENABLED = "button_start"
        @JvmStatic private val EDIT_TEXT_ENABLED = "edit_text"
    }
    private fun onButtonCheckPressed(number1: Int, number2: Int, operation: Char) {
        if (editValue.text.isEmpty()) {
            Toast.makeText(applicationContext, "Введите ответ!", Toast.LENGTH_SHORT).show()
        } else if ((operation == '+' && ((number1 + number2).toString() == editValue.text.toString())) ||
            (operation == '-' && ((number1 - number2).toString() == editValue.text.toString())) ||
            (operation == '*' && ((number1 * number2).toString() == editValue.text.toString())) ||
            (operation == '/' && ((number1 / number2).toString() == editValue.text.toString()))
        ) {
            editBackgroundColor = Color.GREEN
            editValue.setBackgroundColor(editBackgroundColor)
            countRightAnswer++
            binding.txtNumberRight.text = countRightAnswer.toString()
            button_edit_enabled_false()
        } else {
            editBackgroundColor = Color.RED
            editValue.setBackgroundColor(editBackgroundColor)
            countWrongAnswer++
            binding.txtNumberWrong.text = countWrongAnswer.toString()
            button_edit_enabled_false()
        }
    }

    private fun button_edit_enabled_false() {
        editValue.isEnabled = false
       // buttonCheckEnable = false
        buttonCheck.isEnabled = false
       // buttonStartEnable = true
        buttonStart.isEnabled = true
        count++
        binding.txtAllExamples.text = count.toString()
        percent = countRightAnswer * 100 / count.toDouble()
        binding.txtPercentageCorrectAnswers.text = String.format("%.2f%%", percent)
    }
    private fun onButtonStartPressed(): Triple<Int, Int, Char> {
        editValue.setText("")
        editBackgroundColor = Color.WHITE
        editValue.setBackgroundColor(editBackgroundColor)
        operation = getRandomOperation()
        binding.txtOperation.text = operation.toString()

        do {
            number_1 = getRandomNumber()
            number_2 = getRandomNumber()
            if (operation == '/') {
                if (number_1 % number_2 == 0) {
                    break
                }
            } else
                break
        } while (true)

        binding.txtFirstOperand.text = number_1.toString()
        binding.txtSecondOperand.text = number_2.toString()
       // buttonStartEnable = false
        buttonStart.isEnabled = false
        editValue.isEnabled = true
       // buttonCheckEnable = true
        buttonCheck.isEnabled = true

        return Triple(number_1, number_2, operation)
    }

    private fun getRandomNumber(): Int {
        return Random.nextInt(10, 100)
    }

    private fun getRandomOperation(): Char {
        return listOf('*', '/', '-', '+').random()
    }
}