package com.example.solve_example_shamilova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.solve_example_shamilova.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonStart: Button
    private lateinit var buttonCheck: Button
    private lateinit var editValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonStart = binding.btnStart
        buttonCheck = binding.btnCheck
        editValue = binding.editValue

        buttonStart.setOnClickListener { onButtonStartPressed() }
        buttonCheck.setOnClickListener { onButtonCheckPressed() }

    }

    private fun onButtonCheckPressed() {
        if (editValue.text.isEmpty()) {

        }
    }

    private fun onButtonStartPressed() {
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
            }
            else
                break
        } while (true)

        binding.txtFirstOperand.text = number1.toString()
        binding.txtSecondOperand.text = number2.toString()
        buttonStart.isEnabled = false
        editValue.isEnabled = true
        buttonCheck.isEnabled = true
    }

    private fun getRandomNumber(): Int {
        return Random.nextInt(10, 100)
    }

    private fun getRandomOperation(): Char {
        return listOf('*', '/', '-', '+').random()
    }
}