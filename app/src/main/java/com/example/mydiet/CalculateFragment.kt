package com.example.mydiet


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.mydiet.viewmodel.ResultViewModel

/**
 * A simple [Fragment] subclass.
 */
class CalculateFragment : Fragment() {

    private lateinit var etFeet: EditText
    private lateinit var etInches: EditText
    private lateinit var etWeight: EditText
    private lateinit var etAge: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var rdMale: RadioGroup
    private lateinit var age: EditText

    private lateinit var calculate: Button
    lateinit var resultViewModel: ResultViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root = inflater.inflate(R.layout.fragment_calculate, container, false)
        etFeet = root.findViewById(R.id.edit_ft)
        etInches = root.findViewById(R.id.edit_inches)
        etWeight = root.findViewById(R.id.edit_weight)
        calculate = root.findViewById(R.id.btncalculate)
        radioGroup = root.findViewById(R.id.radioGroup)
        etAge = root.findViewById(R.id.edit_age)
        calculate = root.findViewById(R.id.btncalculate)



        calculate.setOnClickListener {
            //root.findNavController().navigate(R.id.action_calculateFragment2_to_resultFragment)
            //calculateBMIFunction()
            //calculateBMR()

            if (validateForm()) {
                val weight = Integer.parseInt(etWeight.text.toString())
                val radioButton: RadioButton =
                    root.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                val gender = radioButton.text.toString().equals("Male")
                val height =
                    (Integer.parseInt(etFeet.text.toString()) + (Integer.parseInt(etInches.text.toString()) / 12)).toDouble()
                val intent = ResultActivity.newIntent(
                    requireContext(),
                    weight,
                    height,
                    gender,
                    Integer.parseInt(etAge.text.toString())
                )
                startActivity(intent)
            } else {
                Toast.makeText(context, getString(R.string.lbl_check_error), Toast.LENGTH_LONG)
                    .show()
            }

        }
        return root

    }

    /**
     * Validate Input Form Data
     */
    private fun validateForm(): Boolean {
        var isValid = true
        if (TextUtils.isEmpty(etFeet.text)) {
            etFeet.error = getString(R.string.lbl_fill_data)
            isValid = false
        }
        if (TextUtils.isEmpty(etWeight.text)) {
            etWeight.error = getString(R.string.lbl_fill_data)
            isValid = false
        }
        if (TextUtils.isEmpty(etInches.text)) {
            etInches.error = getString(R.string.lbl_fill_data)
            isValid = false
        }
        if (TextUtils.isEmpty(etAge.text)) {
            etAge.error = getString(R.string.lbl_fill_data)
            isValid = false
        }
        return isValid
    }
}