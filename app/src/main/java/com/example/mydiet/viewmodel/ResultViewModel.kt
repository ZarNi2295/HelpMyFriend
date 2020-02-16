package com.example.mydiet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydiet.model.AllResult

class ResultViewModel : ViewModel() {

    private var selectedResult: MutableLiveData<AllResult> = MutableLiveData()

    fun getCalculateResult(
        weight: Int, height: Double,
        valFeet: Int,
        valInches: Double,
        valAge: Int, isMale: Boolean
    ): LiveData<AllResult> =
        loadResult(weight, height, valFeet, valInches, valAge, isMale)


    private fun loadResult(
        weight: Int, height: Double,
        valFeet: Int,
        valInches: Double,
        valAge: Int, isMale: Boolean
    ): LiveData<AllResult> {


        var bmr: Double;
        if (isMale) {
            bmr =
                66.47F + (6.24F * weight) + (12.7F * (((valFeet) * 12) + valInches)) - (6.755F * valAge)
        } else {
            bmr =
                65.51F + (4.35F * weight) + (4.7F * (((valFeet) * 12) + valInches)) - (4.7F * valAge)
        }
        bmr = String.format("%.1f", bmr).toDouble()
        val bmrResultOne: String
        val bmrOne: Double
        bmrOne = bmr * 1.2F
        bmrResultOne = "Little or no exercise = " + String.format(
            "%.2f",
            bmrOne
        ).toDouble() + " calories per day"


        val bmrResultTwo: String
        val bmrTwo: Double
        bmrTwo = bmr * 1.375F
        bmrResultTwo = "Light exercise or sports 1-3 day/week = " + String.format(
            "%.2f",
            bmrTwo
        ).toDouble() + " calories per day"

        val bmrResultThree: String
        val bmrThree: Double
        bmrThree = bmr * 1.55F
        bmrResultThree = "Moderate exercise 3-5 day/week = " + String.format(
            "%.2f",
            bmrThree
        ).toDouble() + " calories per day"

        val bmrResultFour: String
        val bmrFour: Double
        bmrFour = bmr * 1.725F
        bmrResultFour = "Hard exercise 6-7 day/week = " + String.format(
            "%.2f",
            bmrFour
        ).toDouble() + " calories per day"

        val bmrResultFive: String
        val bmrFive: Double
        bmrFive = bmr * 1.9F
        bmrResultFive = "Very hard exercise and a physical job =  " + String.format(
            "%.2f",
            bmrFive
        ).toDouble() + " calories per day"


        val result = AllResult(
            calculateBMI(weight, height),
            bmr,
            bmrResultOne,
            bmrResultTwo,
            bmrResultThree,
            bmrResultFour,
            bmrResultFive
        )

        selectedResult.value = result
        return selectedResult;
    }


    private fun calculateBMI(weight: Int, height: Double): Double {
        val inchesHeight = height * 12;  //convert to feet to inches
        val bmi =
            weight * 703 / (inchesHeight * inchesHeight)   //Formula: 703 x weight (lbs) / [height (in)]2
        return String.format("%.1f", bmi).toDouble(); //round 1 decimal

    }

}

