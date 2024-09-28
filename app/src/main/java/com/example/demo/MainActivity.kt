package com.example.demo

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.title.text = "Registration form"

        var educationLevel: String = ""
        val disciplines = mutableListOf<Discipline>()

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            educationLevel = binding.radioGroup.findViewById<RadioButton>(checkedId).text.toString()
        }

        binding.checkBox1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                disciplines.add(Discipline.ANDROID)
            } else {
                disciplines.remove(Discipline.ANDROID)
            }
        }

        binding.checkBox2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                disciplines.add(Discipline.IOS)
            } else {
                disciplines.remove(Discipline.IOS)
            }
        }

        binding.submitButton.setOnClickListener {
            val firstName = binding.firstNameInput.text.toString()
            val lastName = binding.lastNameInput.text.toString()

            val person = Person(
                name = firstName,
                surname = lastName,
                educationLevel = EducationLevel.create(educationLevel),
                disciplines = disciplines
            )

            println("Submit Button Click: $person")
        }

        println("Activity: onCreate")
    }

    override fun onStart() {
        super.onStart()

        println("Activity: onStart")
    }

    override fun onResume() {
        super.onResume()

        println("Activity: onResume")
    }

    override fun onPause() {
        super.onPause()

        println("Activity: onPause")
    }

    override fun onStop() {
        super.onStop()

        println("Activity: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        println("Activity: onDestroy")
    }
}