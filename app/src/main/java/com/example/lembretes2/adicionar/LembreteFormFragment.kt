package com.example.lembretes2.adicionar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.lembretes2.databinding.FragmentLembreteFormBinding
import android.view.inputmethod.EditorInfo
import com.example.lembretes2.Lembrete
import org.koin.androidx.viewmodel.ext.android.viewModel

class LembreteFormFragment : DialogFragment() {
    private lateinit var binding: FragmentLembreteFormBinding

    private val viewModel: LembreteFormViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLembreteFormBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinner()

        binding.edtText.setOnEditorActionListener { _, i, _ ->
            handleKeyboadEvent(i)
        }
    }

    private fun initSpinner() {
        val prioridades = arrayOf("Urgente", "Importante", "Flexivel", "Fixo")

        val adapterSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, prioridades)

        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spnPrioridades.adapter = adapterSpinner
    }

    private fun handleKeyboadEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            val lembrete = dadosLembrete()

            viewModel.salvar(lembrete)
            dialog?.dismiss()

            return true
        }
        return false
    }

    private fun dadosLembrete(): Lembrete {

        val lembrete = Lembrete()

        lembrete.titulo = binding.edtTitle.text.toString()
        lembrete.texto = binding.edtText.text.toString()
        lembrete.prioridade = binding.spnPrioridades.selectedItem.toString()

        return lembrete
    }

    fun open(fm: FragmentManager) {
        if (fm.findFragmentByTag(LEMBRETE_FORM_TAG) == null) {
            show(fm, LEMBRETE_FORM_TAG)
        }
    }

    companion object {
        const val LEMBRETE_FORM_TAG = "lembreteTag"

        fun newInstance() = LembreteFormFragment()
    }
}