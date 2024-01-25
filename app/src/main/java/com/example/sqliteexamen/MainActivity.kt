package com.example.sqliteexamen

import Articulo
import Mochila
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lista = arrayListOf(
            Articulo(Articulo.TipoArticulo.ARMA, Articulo.Nombre.BASTON, 1, 1),
            Articulo(Articulo.TipoArticulo.OBJETO, Articulo.Nombre.POCION, 1, 1),
            Articulo(Articulo.TipoArticulo.PROTECCION, Articulo.Nombre.ARMADURA, 1, 1)
        )
        val mochila = Mochila(this)
        lista.forEach { mochila.addArticulo(it); Log.i("hola", "articulo añadido") }

        val texto: TextView = findViewById(R.id.textView)

        val vista: Button = findViewById(R.id.visualizarBtn)
        vista.setOnClickListener {
            val articulos = mochila.mostrarContenido()
            texto.text = articulos
            Log.i("hola", articulos)
        }

        val aniadirBtn : Button = findViewById(R.id.aniadirBtn)
        aniadirBtn.setOnClickListener {
            val articulo = Articulo(Articulo.TipoArticulo.entries.random(), Articulo.Nombre.entries.random(), (1..10).random(), (1..10).random())
        }
    }
}