package com.example.lembretes2.lista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lembretes2.BaseFragment
import com.example.lembretes2.adapter.LembreteAdapter
import com.example.lembretes2.databinding.ListLembretesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListLembretesFragment : BaseFragment<ListLembretesFragmentBinding>() {

    private val viewModel: ListLembretesViewModel by viewModel()
    private val lembreteAdapter by lazy { LembreteAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()

        viewModel.buscar("").observe(viewLifecycleOwner, Observer { lembretes ->
            lembreteAdapter.lembretes = lembretes
        })

    }

    private fun setupRecycleView() = with(binding) {
        rvLembretes.apply {
            adapter = lembreteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ListLembretesFragmentBinding =
        ListLembretesFragmentBinding.inflate(inflater, container, false)


    companion object {
        const val TAG_LISTA = "tagLista"

        fun newInstance() = ListLembretesFragment()
    }
}

