package com.websarva.wings.android.kids_pharmatest

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = Color.parseColor("#0000cd")

        //入力欄取得
        val age_box = findViewById<EditText>(R.id.age_editor)
        //身長取得
        val height_box = findViewById<EditText>(R.id.height_editor)
        //体重取得
        val weight_box = findViewById<EditText>(R.id.weight_editor)

        //身長体重チェック取得
        val formula_checkbox = findViewById<CheckBox>(R.id.formula_changer)
        //チェックボックスの処理
        formula_checker(age_box, height_box, weight_box, formula_checkbox)


        //ボタンクリックされたら
        val calculate_button = findViewById<Button>(R.id.calculate)
        val ca_listener = calculate_listener(age_box, height_box,weight_box,formula_checkbox)
        calculate_button.setOnClickListener(ca_listener)

    }

    //チェックボックスの処理
    private fun formula_checker(a_box :EditText, h_box :EditText, w_box: EditText, f_box: CheckBox){
        f_box.setOnCheckedChangeListener {_,isChecked ->
            //チェックされた時、入力欄を使用不能に
            val a_text :String? = a_box.text?.toString()
            val h_text :String? = h_box.text?.toString()
            val w_text :String? = w_box.text?.toString()

            if (isChecked) {
                a_box.isEnabled = true
                a_box.setTextColor(Color.parseColor("#2196F3"))
                if(a_text == getString(R.string.unnecessary)){
                    a_box.setText("")
                }

                h_box.isEnabled = false
                h_box.setTextColor(Color.parseColor("#AAABAE"))
                if(h_text == "") {
                    h_box.setText(getString(R.string.unnecessary))
                }


                w_box.isEnabled = false
                w_box.setTextColor(Color.parseColor("#AAABAE"))
                if(w_text == "") {
                    w_box.setText(getString(R.string.unnecessary))
                }

                //チェックされていない時入力欄を使用可能に
            }else {
                a_box.isEnabled = false
                a_box.setTextColor(Color.parseColor("#AAABAE"))
                if(a_text == "") {
                    a_box.setText("ー")
                }

                h_box.isEnabled = true
                h_box.setTextColor(Color.parseColor("#2196F3"))
                if(h_text == getString(R.string.unnecessary)){
                    h_box.setText("")
                }

                w_box.isEnabled = true
                w_box.setTextColor(Color.parseColor("#2196F3"))
                if(w_text == getString(R.string.unnecessary)){
                    w_box.setText("")
                }
            }
        }
    }


    //計算ボタン押された時のリスな
    private inner class calculate_listener(age_box: EditText, height_box: EditText, weight_box: EditText, formula_checkbox: CheckBox) : View.OnClickListener{
        val age = age_box
        val height = height_box
        val weight = weight_box
        val f_box = formula_checkbox

        override fun onClick(view: View) {
            //年齢取得
            var age_text :String? = age.text?.toString()
            //身長取得
            var height_text :String? = height.text?.toString()
            //体重取得
            var weight_text :String? = weight.text?.toString()



                val checker: String




                //チェックボックスにチェックされている場合
                if (f_box.isChecked) {
                    if (age_text == "") {
                        //エラーで弾く
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(R.string.dialog_title)
                            .setMessage(R.string.dialog2_msg)
                            .setPositiveButton(R.string.dialog_btn_ok){dialog, which ->}
                            .show()
                        //年齢が書かれている場合
                    }else {
                        //画面遷移へ
                        checker = "check"
                        height_text =""
                        weight_text =""
                        switch(age_text, height_text, weight_text, checker)
                    }
                    //チェックボックスにチェックされていない場合
                } else {
                    //身長と体重のいずれか或いは両方書かれていない場合
                    if (height_text == "" || weight_text == "") {
                        //エラーで弾く
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle(R.string.dialog_title)
                            .setMessage(R.string.dialog_msg)
                            .setPositiveButton(R.string.dialog_btn_ok) { dialog, which -> }
                            .show()
                        //身長体重両方書かれている場合
                    } else {
                        //画面遷移へ
                        checker = "uncheck"
                        age_text = ""
                        switch(age_text, height_text, weight_text,  checker)
                    }
                }
        }
    }


    //画面遷移
    private fun switch(age:String?, height:String?, weight:String?, checker:String) {
        val intentCalculation = Intent(this@MainActivity, calculateActivity::class.java)
        //年齢を渡す
        intentCalculation.putExtra("age", age)
        //身長
        intentCalculation.putExtra("height", height)
        //体重
        intentCalculation.putExtra("weight", weight)
        //身長体重チェック
        intentCalculation.putExtra("checker", checker)
        startActivity(intentCalculation)
    }
}