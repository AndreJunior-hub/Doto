package com.aguaviva.recyclerview.api

import com.aguaviva.recyclerview.model.Categoria
import com.aguaviva.recyclerview.model.Tarefa
import retrofit2.Response

class Repository {

    suspend fun listCategoria():Response<List<Categoria>>{
        return  RetrofitInstance.api.listCategoria()
    }

    suspend fun addTarefa(tarefa : Tarefa): Response<Tarefa>{
        return RetrofitInstance.api.addTarefa(tarefa)
    }

    suspend fun listTarefa(tarefa : Tarefa) : Response<List<Tarefa>>{
        return RetrofitInstance.api.listTarefas()
    }

    suspend fun updateTarefa(tarefa: Tarefa): Response<Tarefa>{
        return RetrofitInstance.api.updateTarefa(tarefa)
    }
}