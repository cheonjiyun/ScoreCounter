package com.jiyun.scorecounter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    var count = 0 //카운트 값
    var score = 0 //점수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resetButton = findViewById<ImageView>(R.id.reset_button)

        val questionEditText = findViewById<TextView>(R.id.question_editText)

        val scoreTextview = findViewById<TextView>(R.id.score_textview)

        val plusButton = findViewById<Button>(R.id.plus_button) // + 버튼
        val correctCount = findViewById<TextView>(R.id.correctCount_textview)
        val minusButton = findViewById<Button>(R.id.minus_button) // - 버튼


        // 초기 값 세팅
        val questionShared: SharedPreferences = applicationContext.getSharedPreferences(
            "questionNum",
            Context.MODE_PRIVATE
        ) //SharedPreferences
        var questionNumber = questionShared.getInt("question_number", 60)   // 문제 개수
        questionEditText.setText(questionNumber.toString()) //문제 개수


        correctCount.setText(count.toString()) //카운트 값

        //점수 계산
        score = calulateScore(count, questionNumber, scoreTextview) //점수


        // 리셋 버튼 클릭시
        resetButton.setOnClickListener {
            //카운트 값 0
            count = 0

            //카운트 값 표시 바꾸기
            changeCount(correctCount, count)

            //점수 재 계산
            score = calulateScore(count, questionNumber, scoreTextview)

            //진동
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(100);
        }
        
        // 문제 개수 입력시
        questionEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("tags", "beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("tags", "onTextChanged")
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("tags", "afterTextChanged")
                //카운트 값 0
                if(questionEditText.text.toString() != ""){
                    questionNumber = questionEditText.text.toString().toInt()

                    score = calulateScore(count, questionNumber, scoreTextview)

                    //문제 개수 SharedPreferences에 저장
                    with(questionShared.edit()) {
                        putInt("question_number", questionNumber)
                        apply()
                    }
                }

            }

        })


        // + 버튼 클릭시
        plusButton.setOnClickListener {
            if (count < 1000000) {
                //카운트 값 +1
                count++

                //카운트 값 표시 바꾸기
                changeCount(correctCount, count)

                //점수 계산
                score = calulateScore(count, questionNumber, scoreTextview)

                //진동
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(100);
            }


        }

        // - 버튼 클릭시
        minusButton.setOnClickListener {
            if (count > 0) {
                //카운트 값 -1
                count--

                //카운트 값 표시 바꾸기
                changeCount(correctCount, count)

                //점수 계산
                score = calulateScore(count, questionNumber, scoreTextview)

                //진동
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(100);
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView = currentFocus
        if(focusView != null && ev != null){
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()

            if(!rect.contains(x, y)){
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    fun changeCount(countTextView: TextView, count: Int) {
        countTextView.setText(count.toString())
    }

    fun calulateScore(count: Int, question_number: Int, scoreTextview: TextView): Int {

        val score = (count.toDouble() / question_number.toDouble() * 100).roundToInt()
        scoreTextview.setText(score.toString())
        return score
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        count = savedInstanceState.getInt("count")
        score = savedInstanceState.getInt("score")

        val correctCount = findViewById<TextView>(R.id.correctCount_textview)
        correctCount.setText(count.toString()) //카운트 값

        val scoreTextview = findViewById<TextView>(R.id.score_textview)
        scoreTextview.setText(score.toString())

        Log.d("회전","회전count $count")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", count)
        outState.putInt("score", score)
        Log.d("count", "count값: $count")
    }
}

