package com.aguaviva.recyclerview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aguaviva.recyclerview.adapter.TarefaAdapter
import com.aguaviva.recyclerview.adapter.TaskClickListener
import com.aguaviva.recyclerview.databinding.ListFragmentBinding
import com.aguaviva.recyclerview.model.Tarefa
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment(), TaskClickListener {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var binding: ListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel.listTarefas()
        // Inflate the layout for this fragment
        binding = ListFragmentBinding.inflate(layoutInflater, container, false)


        //Configuração do RecyclerView
        val  adapter = TarefaAdapter(this, mainViewModel)
        binding.recyclerTarefa.layoutManager = LinearLayoutManager(context)
        binding.recyclerTarefa.adapter = adapter
        binding.recyclerTarefa.setHasFixedSize(true)

        binding.floatingAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_formFragment)
        }

        mainViewModel.myTarefaResponse.observe(viewLifecycleOwner,{
            response -> if (response != null){
                TarefaAdapter.setList(response.body()!!)
        }
        })

        return binding.root
    }

    override fun onTaskClickListener(tarefa: Tarefa) {
        mainViewModel.tarefaSelecionada = tarefa
        findNavController().navigate(R.id.action_listFragment_to_formFragment)
    }
}