package com.jiyun.scorecounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var question_textview = findViewById<TextView>(R.id.question_textview)
        var score_textview = findViewById<TextView>(R.id.score_textview)
        var plus_button = findViewById<Button>(R.id.plus_button) // + 버튼
        var minus_button = findViewById<Button>(R.id.minus_button) // - 버튼

        // 초기 값 세팅
        var question_number = 30 // 문제 개수
        question_textview.setText(question_number.toString())

        var count = 0 //카운트 값
        plus_button.setText(count.toString())

        var score = 0 //점수

        // + 버튼 클릭시
        plus_button.setOnClickListener {
            if(count < question_number){
                //카운트 값
                count++
                plus_button.setText(count.toString())

                //점수 계산
                score = (count.toDouble()/question_number.toDouble() * 100).toInt()
                score_textview.setText(score.toString())
            }


        }

        // - 버튼 클릭시
        minus_button.setOnClickListener {
            //카운트 값
            if(count > 0){
                count--
                plus_button.setText(count.toString())

                //점수 계산
                score = (count.toDouble()/question_number.toDouble() * 100).toInt()
                score_textview.setText(score.toString())
            }

        }


    }

}