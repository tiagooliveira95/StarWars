package com.smartguygoescrazy.starwars.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.smartguygoescrazy.starwars.R
import kotlinx.android.synthetic.main.activity_sobre.*
import java.util.*
import java.util.Calendar.*

class Sobre : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Glide.with(this).load(R.drawable.profile).into(circleImageView)


        val c1 : Calendar = getInstance()
        c1.set(DAY_OF_YEAR, 25) // 25
        c1.set(MONTH, 5)        // 06 (Jan = 0)
        c1.set(YEAR, 1995)      // 1995


        val anosText = String.format(Locale.getDefault(),"${getDiffYears(c1)} %s", getString(R.string.about_anos_label))

         anos.text = anosText


    }


    private fun getDiffYears(cal : Calendar): Int {
        val b = Calendar.getInstance()
        var diff = b.get(YEAR) - cal.get(YEAR)
        if (cal.get(MONTH) > b.get(MONTH) || cal.get(MONTH) == b.get(MONTH) && cal.get(DATE) > b.get(DATE)) {
            diff--
        }
        return diff
    }

}
