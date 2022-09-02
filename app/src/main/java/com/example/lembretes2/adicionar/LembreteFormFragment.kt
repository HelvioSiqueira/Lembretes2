package com.example.lembretes2.adicionar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.lembretes2.R

class LembreteFormFragment: DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lembrete_form, container, false)
    }

    fun open(fm: FragmentManager){
        if(fm.findFragmentByTag(LEMBRETE_FORM_TAG) == null){
            show(fm, LEMBRETE_FORM_TAG)
        }
    }

    companion object{
        const val LEMBRETE_FORM_TAG = "lembreteTag"

        fun newInstance() = LembreteFormFragment()
    }
}