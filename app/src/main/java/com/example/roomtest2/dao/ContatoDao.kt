package com.example.roomtest2.dao

import androidx.room.*
import com.example.roomtest2.model.Contato

@Dao
interface ContatoDao {

    @Insert
    fun salvar(contato: Contato)

    @Update
    fun atualizar(contato: Contato)

    @Delete
    fun excluir(contato: Contato)

    @Query("SELECT * FROM contato ORDER BY id ASC")
    fun listarTodos(): List<Contato>

    @Query("SELECT * FROM contato WHERE id = :id")
    fun listarPorId(id: Int): Contato



}