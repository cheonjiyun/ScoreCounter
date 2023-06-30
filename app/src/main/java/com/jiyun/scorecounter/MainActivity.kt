package com.jiyun.scorecounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var reset_button = findViewById<Button>(R.id.reset_button)

        var question_minus_button = findViewById<Button>(R.id.question_minus_button)
        var question_textview = findViewById<TextView>(R.id.question_textview)
        var question_plus_button = findViewById<Button>(R.id.question_plus_button)

        var score_textview = findViewById<TextView>(R.id.score_textview)

        var plus_circle_button = findViewById<Button>(R.id.plus_circle_button) // + 버튼
        var minus_button = findViewById<Button>(R.id.minus_button) // - 버튼

        // 초기 값 세팅
        var question_number = 60 // 문제 개수
        question_textview.setText(question_number.toString())

        var count = 0 //카운트 값
        plus_circle_button.setText(count.toString())

        var score = 0 //점수
        score_textview.setText(score.toString())


        // 리셋 버튼 클릭시
        reset_button.setOnClickListener {
            //카운트 값 0
            count = 0

            //카운트 값 표시 바꾸기
            changeCount(plus_circle_button, count)

            //점수 재 계산
            score = calulateScore(count, question_number, score_textview)
        }

        // 문제 개수 - 버튼
        question_minus_button.setOnClickListener {
            if(question_number > count && question_number > 1){
                question_number--

                //문제 개수 표시 값 바꾸기
                changeQuestionNumber(question_textview, question_number)

                //점수 다시 계산
                score = calulateScore(count, question_number, score_textview)
            }

        }

        // 문제 개수 + 버튼
        question_plus_button.setOnClickListener {
            if(question_number < 9999){
                question_number++

                //문제 개수 표시 값 바꾸기
                changeQuestionNumber(question_textview, question_number)

                //점수 다시 계산
                score = calulateScore(count, question_number, score_textview)
            }

        }



        // 원형 버튼 클릭시
        plus_circle_button.setOnClickListener {
            if(count < question_number){
                //카운트 값 +1
                count++

                //카운트 값 표시 바꾸기
                changeCount(plus_circle_button, count)

                //점수 계산
                score = calulateScore(count, question_number, score_textview)
            }


        }

        // - 버튼 클릭시
        minus_button.setOnClickListener {
            if(count > 0){
                //카운트 값 -1
                count--

                //카운트 값 표시 바꾸기
                changeCount(plus_circle_button, count)

                //점수 계산
                score = calulateScore(count, question_number, score_textview)
            }

        }


    }

    fun changeQuestionNumber(question_textview: TextView, question_number: Int){
        question_textview.setText(question_number.toString())
    }

    fun changeCount(circle_button: Button, count: Int){
        circle_button.setText(count.toString())
    }

    fun calulateScore(count:Int, question_number:Int, score_textview: TextView): Int {
        var score = (count.toDouble()/question_number.toDouble() * 100).roundToInt()
        score_textview.setText(score.toString())
        return score
    }
}
