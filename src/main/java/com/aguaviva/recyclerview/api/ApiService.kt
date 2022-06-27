package com.aguaviva.recyclerview.api

import com.aguaviva.recyclerview.model.Categoria
import com.aguaviva.recyclerview.model.Tarefa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @GET("categoria")
    suspend fun listCategoria(): Response<List<Categoria>>

    //adcionar nova tarefa
    @POST("tarefa")
    suspend fun addTarefa(
        @Body tarefa: Tarefa
    ) : Response<Tarefa>

    //Adcionar tarefas
    @GET ("tarefa")
    suspend fun listTarefas(): Response<List<Tarefa>>

    @PUT("tarefa")
    suspend fun updateTarefa(
        @Body tarefa: Tarefa
    ): Response<Tarefa>


}