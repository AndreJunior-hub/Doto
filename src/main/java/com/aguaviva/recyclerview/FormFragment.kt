package com.aguaviva.recyclerview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aguaviva.recyclerview.databinding.FormFragmentBinding
import com.aguaviva.recyclerview.fragment.DatePickerFragment
import com.aguaviva.recyclerview.fragment.TimerPickerListener
import com.aguaviva.recyclerview.model.Categoria
import com.aguaviva.recyclerview.model.Tarefa
import java.time.LocalDate

class FormFragment : Fragment(), TimerPickerListener  {

    private lateinit var binding: FormFragmentBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private var categoriaSelecionada = 0L
    private var tarefaSelecionada: Tarefa? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FormFragmentBinding.inflate(layoutInflater, container, false)

        carregarDados()

        mainViewModel.listCategoria()

        mainViewModel.dataSelecionada.value = LocalDate.now()

        mainViewModel.myCategoriaResponse.observe(viewLifecycleOwner){
            response -> Log.d("Requisicao",response.body().toString())
            spinnerCategoria(response.body())
        }

        mainViewModel.dataSelecionada.observe(viewLifecycleOwner){
            selectedDate -> binding.editData.setText(selectedDate.toString())
        }

        binding.buttonSalvar.setOnClickListener {
            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        }

        binding.editData.setOnClickListener{
            DatePickerFragment(this)
                .show(parentFragmentManager,"DatePicker")
        }

        binding.buttonSalvar.setOnClickListener {
            inserirNoBanco()
        }

        return binding.root
    }

    fun spinnerCategoria(listCategoria: List<Categoria>?){
        if (listCategoria != null){
            binding.spinnerCategoria.adapter =
                ArrayAdapter(
                    requireContext(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    listCategoria
                )

            binding.spinnerCategoria.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                    p0: AdapterView<*>?, p1: View?, p2 : Int, p3 : Long
                    ){
                        val categoriaSelecionadaFun = binding
                            .spinnerCategoria.selectedItem as Categoria

                        categoriaSelecionada = categoriaSelecionadaFun.id
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        }
    }


    fun validarCampos(
        nome: String, desc: String, responsavel: String,
        data: String
    ): Boolean{
        return !(
                (nome == "" || nome.length < 3 || nome.length >20) ||
                (desc == "" || desc.length < 5|| nome.length >200) ||
                (responsavel == "" || nome.length < 3 || nome.length >20) || data == ""
                )
    }

    fun inserirNoBanco(){
        val nome = binding.editNome.text.toString()
        val desc = binding.editDescricao.text.toString()
        val responsavel = binding.editResponsavel.text.toString()
        val data = binding.editData.text.toString()
        val status = binding.switchAtivoCard.isChecked
        val categoria= Categoria(categoriaSelecionada,null,null)

        if (validarCampos(nome, desc, responsavel, data)){
            val salvar: String
            if (tarefaSelecionada != null){
                salvar = "Tarefa atualizada"
                val tarefa = Tarefa(tarefaSelecionada?.id!!,nome, desc, responsavel, data, status, categoria)
                mainViewModel.addTarefa(tarefa)
            }else{
                salvar = "Tarefa criada"
                val tarefa = Tarefa(tarefaSelecionada?.id!!,nome, desc, responsavel, data, status, categoria)
                mainViewModel.addTarefa(tarefa)
            }

            Toast.makeText(
                context,salvar,
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_listFragment_to_formFragment)
        }else{
            Toast.makeText(
                context,"Preencha os campos corretamente!!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun carregarDados(){
        tarefaSelecionada = mainViewModel.tarefaSelecionada
        if(tarefaSelecionada != null){
            binding.editNome.setText(tarefaSelecionada?.nome)
            binding.editDescricao.setText(tarefaSelecionada?.descricao)
            binding.editResponsavel.setText(tarefaSelecionada?.responsavel)
            binding.editData.setText(tarefaSelecionada?.data)
            binding.switchAtivoCard.isChecked = tarefaSelecionada?.status!!
        }
    }

    override fun onDateSelected(date: LocalDate) {
        mainViewModel.dataSelecionada.value = date
    }
}