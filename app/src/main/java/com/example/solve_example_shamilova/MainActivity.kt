package com.example.solve_example_shamilova

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.solve_example_shamilova.databinding.ActivityMainBinding
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var editTextValue: TextView
    lateinit var state: State

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        editTextValue = binding.editValue

        binding.btnStart.setOnClickListener { onButtonStartPressed() }
        binding.btnCheck.setOnClickListener { onButtonCheckPressed() }

        state = savedInstanceState?.getParcelable(KEY_STATE) ?: State(
            number_1 = 0,
            number_2 = 0,
            operation = '+',
            countRightAnswer = 0,
            countWrongAnswer = 0,
            countSolvedExamples = 0,
            percent = 0.00,
            editTextBackgroundColor = ContextCompat.getColor(this, R.color.white),
            buttonStartEnable = true,
            buttonCheckEnable = false,
            editTextEnable = false
        )
        setState()
    }

    private fun setState() = with(binding) {
        txtFirstOperand.setText(state.number_1.toString())
        txtSecondOperand.setText(state.number_2.toString())
        txtOperation.setText(state.operation.toString())
        txtNumberRight.setText(state.countRightAnswer.toString())
        txtNumberWrong.setText(state.countWrongAnswer.toString())
        txtAllExamples.setText(state.countSolvedExamples.toString())
        txtPercentageCorrectAnswers.setText(String.format("%.2f%%", state.percent))
        editValue.setBackgroundColor(state.editTextBackgroundColor)
        btnStart.isEnabled = if (state.buttonStartEnable) true else false
        btnCheck.isEnabled = if (state.buttonCheckEnable) true else false
        editValue.isEnabled = if (state.editTextEnable) true else false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, state)
    }

    @Parcelize
    class State(
        var number_1: Int,
        var number_2: Int,
        var operation: Char,
        var countRightAnswer: Int,
        var countWrongAnswer: Int,
        var countSolvedExamples: Int,
        var percent: Double,
        var editTextBackgroundColor: Int,
        var buttonStartEnable: Boolean,
        var buttonCheckEnable: Boolean,
        var editTextEnable: Boolean,
    ) : Parcelable

    companion object {
        @JvmStatic private val KEY_STATE = "STATE"
    }

    private fun onButtonCheckPressed() {
        if (editTextValue.text.isEmpty()) {
            Toast.makeText(applicationContext, "Введите ответ!", Toast.LENGTH_SHORT).show()
        } else if ((state.operation == '+' && ((state.number_1 + state.number_2) == editTextValue.text.toString().toInt())) ||
            (state.operation == '-' && ((state.number_1 - state.number_2) == editTextValue.text.toString().toInt())) ||
            (state.operation == '*' && ((state.number_1 * state.number_2) == editTextValue.text.toString().toInt())) ||
            (state.operation == '/' && ((state.number_1 / state.number_2) == editTextValue.text.toString().toInt()))
        ) {
            state.editTextBackgroundColor = Color.GREEN
            state.countRightAnswer++
            button_edit_enabled_false()
        } else {
            state.editTextBackgroundColor = Color.RED
            state.countWrongAnswer++
            button_edit_enabled_false()
        }
        setState()
    }

    private fun button_edit_enabled_false() {
        state.editTextEnable = !state.editTextEnable
        state.buttonCheckEnable = !state.buttonCheckEnable
        state.buttonStartEnable = !state.buttonStartEnable

        state.countSolvedExamples++
        state.percent = state.countRightAnswer * 100 / state.countSolvedExamples.toDouble()
        setState()
    }

    private fun onButtonStartPressed() {
        editTextValue.setText("")
        state.editTextBackgroundColor = Color.WHITE
        editTextValue.isEnabled = true
        binding.editValue.requestFocus()
        state.operation = listOf('*', '/', '-', '+').random()

        do {
            state.number_1 = Random.nextInt(10, 100)
            state.number_2 = Random.nextInt(10, 100)
            if (state.operation == '/') {
                if (state.number_1 % state.number_2 == 0) {
                    break
                }
            } else
                break
        } while (true)

        state.buttonStartEnable = !state.buttonStartEnable
        state.buttonCheckEnable = !state.buttonCheckEnable
        state.editTextEnable = !state.editTextEnable

        setState()
    }
}