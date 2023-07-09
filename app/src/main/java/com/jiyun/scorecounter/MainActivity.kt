package com.jiyun.scorecounter

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val reset_button = findViewById<Button>(R.id.reset_button)

        val question_minus_button = findViewById<Button>(R.id.question_minus_button)
        val question_textview = findViewById<TextView>(R.id.question_textview)
        val question_plus_button = findViewById<Button>(R.id.question_plus_button)

        val score_textview = findViewById<TextView>(R.id.score_textview)

        val plus_circle_button = findViewById<Button>(R.id.plus_circle_button) // + 버튼
        val minus_button = findViewById<Button>(R.id.minus_button) // - 버튼


        // 초기 값 세팅
        val questionShared: SharedPreferences = applicationContext.getSharedPreferences(
            "questionNum",
            Context.MODE_PRIVATE
        ) //SharedPreferences
        var questionNumber = questionShared.getInt("question_number", 60)   // 문제 개수
        question_textview.setText(questionNumber.toString()) //문제 개수

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
            score = calulateScore(count, questionNumber, score_textview)
        }

        // 문제 개수 - 버튼
        question_minus_button.setOnClickListener {
            if (questionNumber > count && questionNumber > 1) {
                questionNumber--

                //문제 개수 표시 값 바꾸기
                changeQuestionNumber(question_textview, questionNumber)

                //점수 다시 계산
                score = calulateScore(count, questionNumber, score_textview)

                //문제 개수 SharedPreferences에 저장
                with(questionShared.edit()) {
                    putInt("question_number", questionNumber)
                    apply()
                }
            }

        }

        // 문제 개수 + 버튼
        question_plus_button.setOnClickListener {
            if (questionNumber < 9999) {
                questionNumber++

                //문제 개수 표시 값 바꾸기
                changeQuestionNumber(question_textview, questionNumber)

                //점수 다시 계산
                score = calulateScore(count, questionNumber, score_textview)

                //문제 개수 SharedPreferences에 저장
                with(questionShared.edit()) {
                    putInt("question_number", questionNumber)
                    apply()
                }
            }

        }


        // 원형 버튼 클릭시
        plus_circle_button.setOnClickListener {
            if (count < questionNumber) {
                //카운트 값 +1
                count++

                //카운트 값 표시 바꾸기
                changeCount(plus_circle_button, count)

                //점수 계산
                score = calulateScore(count, questionNumber, score_textview)
            }


        }

        // - 버튼 클릭시
        minus_button.setOnClickListener {
            if (count > 0) {
                //카운트 값 -1
                count--

                //카운트 값 표시 바꾸기
                changeCount(plus_circle_button, count)

                //점수 계산
                score = calulateScore(count, questionNumber, score_textview)
            }

        }


    }

    fun changeQuestionNumber(question_textview: TextView, question_number: Int) {
        question_textview.setText(question_number.toString())
    }

    fun changeCount(circle_button: Button, count: Int) {
        circle_button.setText(count.toString())
    }

    fun calulateScore(count: Int, question_number: Int, score_textview: TextView): Int {
        val score = (count.toDouble() / question_number.toDouble() * 100).roundToInt()
        score_textview.setText(score.toString())
        return score
    }
}

