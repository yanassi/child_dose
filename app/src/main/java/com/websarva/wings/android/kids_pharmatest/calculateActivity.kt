package com.websarva.wings.android.kids_pharmatest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import kotlin.math.pow

class calculateActivity : AppCompatActivity() {
    var maxdose_result :Float = 0f
    var mindose_result :Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)

        window.statusBarColor = Color.parseColor("#0000cd")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //遷移元からデータを取り出す
        //年齢(nullの可能性あり)
        val age_text = intent.getStringExtra("age")
        val age = age_text?.toIntOrNull()
        //身長(nullの可能性あり)
        val height_text = intent.getStringExtra("height")
        val height = height_text?.toFloatOrNull()
        //体重(nullの可能性あり)
        val weight_text = intent.getStringExtra("weight")
        val weight = weight_text?.toFloatOrNull()
        //身長体重知ってるか知らないか
        val checker_text = intent.getStringExtra("checker")


        //大人の最大量取得
        val adult_maxbox = findViewById<EditText>(R.id.adult_max)

        //大人の最小量取得
        val adult_minbox = findViewById<EditText>(R.id.adult_min)

        //小児量計算へ
        dose_calculate(age, height, weight, checker_text, adult_maxbox, adult_minbox)

    }

    //小児量最大量計算
    private fun dose_calculate(age: Int?, height: Float?, weight: Float?, checker :String? ,adult_maxbox: EditText, adult_minbox: EditText) {
        //大人最大量のEditTextを変えた時
        adult_maxbox.doOnTextChanged {_, _, _, _ ->

            //大人最大量の取得
            val adult_maxtext:String? = adult_maxbox.text?.toString()
            //nullの可能性あるので…
            val adult_maxdose = adult_maxtext?.toFloatOrNull()

            //大人最大量が入力されていない時(或いは全部消されちゃった時)
            if(adult_maxtext == "") {
                //大人最大量=0とする
                maxdose_result = 0F
                //小児量として挿入へ
                max_editor(maxdose_result)
            //大人最大量が入力されている時
            }else {
                //計算へ
                maxdose_result = dose_formula(adult_maxdose, age, height, weight, checker)
                //計算結果を小児量として挿入へ
                max_editor(maxdose_result)
            }
        }

        //大人最小量のEditTextを変えた時
        adult_minbox.doOnTextChanged { _, _, _, _ ->

            //大人最小量の取得
            val adult_mintext: String? = adult_minbox.text?.toString()
            //nullの可能性があるので…
            val adult_mindose = adult_mintext?.toFloatOrNull()

            //大人最小量が入力されていない場合(或いは全部消されちゃった場合)
            if(adult_mintext == "") {
                //大人最小量=0とする
                mindose_result = 0F
                //小児量として挿入へ
                min_editor(mindose_result)
            //大人最小量が入力されている時
            }else {
                //計算へ
                mindose_result = dose_formula(adult_mindose, age, height, weight, checker)
                //計算結果を小児量として挿入へ
                min_editor(mindose_result)
            }
        }
    }

    //小児量の計算式
    private fun dose_formula(dose: Float?, age: Int?, height: Float?, weight: Float?, checker: String?) :Float{
        val result: Float =
            //身長体重不明の場合
            if(checker == "check") {
                //DuBoisの式で計算
                dose!!*(age!! * 4 + 20) /100
            //身長体重判明済みの場合
            } else {
                //Crawfordの式で計算
                dose!!*(height!!.pow(0.725F) * weight!!.pow(0.425F) * 0.007184F ) / 1.73F
            }
        //計算結果を返す
        return result
    }


    //小児最大量をTextViewへ表示
    private fun max_editor(maxdose_result: Float) {
        val maxdose = maxdose_result.toInt()
        //挿入するTextViewを取得
        val kids_maxbox = findViewById<TextView>(R.id.kids_max)
        //小児最大量が0の時
        if (maxdose_result == 0F){
            //初期値と同じ「最大量」を入れる
            kids_maxbox.text = getString(R.string.max)
            kids_maxbox.setTextColor(Color.parseColor("#AAABAE"))
        //小児最大量が0以外の時
        } else {
            //小児最大量をそのまま挿入
            kids_maxbox.text = maxdose.toString()
            kids_maxbox.setTextColor(Color.parseColor("#2196F3"))
        }
    }

    //TextViewへ表示
    private fun min_editor(mindose_result: Float) {
        val mindose = mindose_result.toInt()
        //挿入するTextViewを取得
        val kids_minbox = findViewById<TextView>(R.id.kids_min)
        //小児最大量が0の時
        if (mindose_result == 0F){
            //初期値と同じ「最大量」を入れる
            kids_minbox.text = getString(R.string.min)
            kids_minbox.setTextColor(Color.parseColor("#AAABAE"))
        //小児最大量が0以外の時
        } else {
            //小児最大量をそのまま挿入
            kids_minbox.text = mindose.toString()
            kids_minbox.setTextColor(Color.parseColor("#2196F3"))
        }
    }

    //戻るボタンの実装
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var returnVal = true
        if(item.itemId == android.R.id.home) {
            finish()
        } else {
            returnVal = super.onOptionsItemSelected(item)
        }
        return returnVal
    }

}