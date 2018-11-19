package com.smartguygoescrazy.starwars.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.smartguygoescrazy.starwars.R

class Sobre : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }
}
