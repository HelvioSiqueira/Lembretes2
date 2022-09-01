package com.example.lembretes2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lembretes2.lista.ListLembretesFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showListFragment()
    }

    private fun showListFragment(){
        val fragment = ListLembretesFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layout, fragment, ListLembretesFragment.TAG_LISTA)
            .commit()
    }
}