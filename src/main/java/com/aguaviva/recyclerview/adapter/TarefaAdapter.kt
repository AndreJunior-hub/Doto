package com.aguaviva.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aguaviva.recyclerview.MainViewModel
import com.aguaviva.recyclerview.databinding.CardLayoutBinding
import com.aguaviva.recyclerview.model.Tarefa

class TarefaAdapter (
    val taskClickListener: TaskClickListener,
    val mainViewModel: MainViewModel
        ): RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    private var listTarefa = emptyList<Tarefa>()

    class TarefaViewHolder (val binding: CardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        return TarefaViewHolder(CardLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        var tarefa = listTarefa[position]

        holder.binding.textNome.text = tarefa.nome
        holder.binding.textNome.text = tarefa.descricao
        holder.binding.textNome.text = tarefa.responsavel
        holder.binding.textNome.text = tarefa.data
        holder.binding.switchAtivo.isChecked = tarefa.status
        holder.binding.textCategoria.text = tarefa.categoria.descricao

        holder.itemView.setOnClickListener{
            taskClickListener.onTaskClickListener(tarefa)
        }

    }

    override fun getItemCount(): Int {
        return listTarefa.size
    }

    fun setList(list: List<Tarefa>){
        listTarefa = list.sortedByDescending { it.id }
        notifyDataSetChanged()

    }

}