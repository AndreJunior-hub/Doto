package com.aguaviva.recyclerview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aguaviva.recyclerview.api.Repository
import com.aguaviva.recyclerview.model.Categoria
import com.aguaviva.recyclerview.model.Tarefa
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val repository: Repository
        ): ViewModel() {

    var tarefaSelecionada: Tarefa? = null

    private val _myCategoryResponse =
        MutableLiveData<Response<List<Categoria>>>()

    val myCategoriaResponse: LiveData<Response<List<Categoria>>> =
        _myCategoryResponse

    val dataSelecionada = MutableLiveData<LocalDate>()

    init {
        //listCategoria()
    }

    fun listCategoria(){
        viewModelScope.launch (Dispatchers.IO){
            try{
                val response = repository.listCategoria()
                _myCategoryResponse.value = response
            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }

    fun addTarefa(tarefa : Tarefa){
        viewModelScope.launch{
            try {
                repository.addTarefa(tarefa)
            }catch (e: Exception){
                Log.d("Erro", e.message.toString())
            }
        }
    }
    
    fun listTarefas(){
        viewModelScope.launch { 
            try {
                
            }catch (e: Exception){
                Log.e("Developer","Error", e)
            }
        }
    }

    fun updateTarefa(tarefa: Tarefa){
        viewModelScope.launch {
            try {
                repository.updateTarefa(tarefa)
                listTarefas()
            }catch (e: Exception){
                Log.e("Developer","Error", e)
            }
        }
    }
}