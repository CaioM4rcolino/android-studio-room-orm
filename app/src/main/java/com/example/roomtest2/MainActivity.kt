package com.example.roomtest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room
import com.example.roomtest2.adapter.ContatoAdapter
import com.example.roomtest2.dao.AppDatabase
import com.example.roomtest2.dao.DatabaseBuilder
import com.example.roomtest2.model.Contato

class MainActivity : AppCompatActivity(), OnClickListener{


    private lateinit var buttonNovoContato: ImageButton
    private lateinit var editNome: EditText
    private lateinit var editTelefone: EditText
    private lateinit var buttonSalvar: Button
    private lateinit var buttonCancelar: Button
    private lateinit var dialog: AlertDialog
    private lateinit var recyclerContatos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonNovoContato = findViewById(R.id.button_add_contato)
        buttonNovoContato.setOnClickListener(this)
        recyclerContatos = findViewById(R.id.recycler_view_contatos)

        exibirContatos()

    }

    override fun onClick(v: View) {

        if(v.id === R.id.button_add_contato) {
            abrirCadastroContato()
        }
        else if(v.id === R.id.button_cancelar) {
            dialog.dismiss()
        }
        else if(v.id === R.id.button_salvar) {
            salvarContato()
            dialog.dismiss()

        }
        else{
            exibirContatos()
        }

    }

    private fun abrirCadastroContato() {
        val alert_dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.cadastro_contato_dialogo, null)
        alert_dialog.setView(view)

        editNome = view.findViewById(R.id.edit_nome)
        editTelefone = view.findViewById(R.id.edit_telefone)

        buttonSalvar = view.findViewById(R.id.button_salvar)

        buttonSalvar.setOnClickListener(this)
        buttonCancelar = view.findViewById(R.id.button_cancelar)

        dialog = alert_dialog.create()
        dialog.setCancelable(false)
        dialog.show()

        buttonCancelar.setOnClickListener(this)
    }

    private fun salvarContato(){

        var contato = Contato(0,
                editNome.text.toString(), editTelefone.text.toString())

        val contatoDao =  DatabaseBuilder.getDatabase(this).contatoDao()

        contatoDao.salvar(contato)
    }

    private fun exibirContatos(){

        val contatoDao = DatabaseBuilder.getDatabase(this).contatoDao()

        val contatos = contatoDao.listarTodos()

        val adapter = ContatoAdapter()


        //Definir o layout da RecyclerView
        recyclerContatos.layoutManager = LinearLayoutManager(this)

        //Instanciar o Adapter que será utilizado pela RecyclerView

        adapter.carregarLista(contatos)

        recyclerContatos.adapter = adapter

    }
}