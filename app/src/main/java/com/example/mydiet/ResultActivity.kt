package com.example.mydiet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mydiet.model.AllResult
import com.example.mydiet.viewmodel.ResultViewModel


class ResultActivity : AppCompatActivity() {


    private lateinit var etBmiResult: EditText
    private lateinit var etBmrResult: EditText
    private lateinit var tvBmrText: TextView
    private lateinit var btnOk: Button
    private lateinit var tvResult: TextView

    companion object {
        private val INTENT_WEIGHT = "intentWeight"
        private val INTENT_HEIGHT = "intentHeight"
        private val INTENT_GENDER = "intentGender"
        private val INTENT_AGE = "intentAge"
        private val INTENT_FEET = "intentFeet"
        private val INTENT_INCHES = "intentInches"
        fun newIntent(
            context: Context,
            weight: Int,
            height: Double,
            gender: Boolean,
            age: Int
        ): Intent {
            var intent = Intent(context, ResultActivity::class.java)
            intent.putExtra(INTENT_WEIGHT, weight);
            intent.putExtra(INTENT_HEIGHT, height);
            intent.putExtra(INTENT_GENDER, gender);
            intent.putExtra(INTENT_AGE, age);
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        etBmiResult = findViewById(R.id.et_bmi_result)
        etBmrResult = findViewById(R.id.et_bmr_result)
        tvBmrText = findViewById(R.id.tv_bmi_text)
        btnOk = findViewById(R.id.btn_confirm)
        tvResult = findViewById(R.id.tv_result)
        val resultViewModel: ResultViewModel =
            ViewModelProviders.of(this).get(ResultViewModel::class.java)
        val bundle = intent.extras
        if (bundle != null) {
            var weight = bundle.getInt(INTENT_WEIGHT)
            var height =
                bundle.getDouble(INTENT_HEIGHT)
            var gender =
                bundle.getBoolean(INTENT_GENDER)
            var age =
                bundle.getInt(INTENT_AGE)
            var valFeet =
                bundle.getInt(INTENT_FEET)
            var valInches =
                bundle.getDouble(INTENT_INCHES)

            resultViewModel.getCalculateResult(weight, height, valFeet, valInches, age, gender).observe(
                this, Observer<AllResult> { result ->
                    updateUI(result)
                }
            )
        }
        btnOk.setOnClickListener {
            finish()  //close this activity
        }

    }

    private fun getBMIText(bmi: Double): String {
        val bmiLevel: String = when {
            bmi < 16 -> "Severely UnderWeight"
            bmi < 18.25 -> "UnderWeight"
            bmi < 25 -> "Normal"
            bmi < 30 -> "OverWeight"
            else -> "Obese"
        }

        return bmiLevel;
    }

    private fun updateUI(result: AllResult) {
        etBmiResult.setText(
            result.bmiResult.toString() + " BMI "
        );
        tvBmrText.setText(
            "You are " + getBMIText(
                result.bmiResult
            )
        )

        etBmrResult.setText(result.bmrResult.toString() + " BMR")

        tvResult.setText(
            result.bmrOne + "\n\n" +
                    result.bmrTwo + "\n\n" +
                    result.bmrThree + "\n\n" +
                    result.bmrFour + "\n\n" +
                    result.bmrFive + "\n"
        )

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
