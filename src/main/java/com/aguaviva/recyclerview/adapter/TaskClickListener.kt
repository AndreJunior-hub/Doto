package com.aguaviva.recyclerview.adapter

import com.aguaviva.recyclerview.model.Tarefa

interface TaskClickListener {
    fun onTaskClickListener(tarefa: Tarefa)
}