package com.example.sqliteexamen

import Articulo
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "Articulos.db"
        private const val TABLA_ARTICULOS = "articulos"
        private const val KEY_ID = "_id"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_PESO = "peso"
        private const val COLUMN_PRECIO = "precio"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLA_ARTICULOS ($KEY_ID INTEGER PRIMARY KEY, $COLUMN_TIPO TEXT, $COLUMN_NOMBRE TEXT, $COLUMN_PESO INTEGER, $COLUMN_PRECIO INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLA_ARTICULOS")
        onCreate(db)
    }

    fun insertarArticulo(articulo: Articulo){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TIPO, articulo.getTipoArticulo().toString())
            put(COLUMN_NOMBRE, articulo.getNombre().toString())
            put(COLUMN_PESO, articulo.getPeso())
            put(COLUMN_PRECIO, articulo.getPrecio())
        }
        db.insert(TABLA_ARTICULOS, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getArticulos(): ArrayList<Articulo>{
        val articulos = ArrayList<Articulo>()
        val selectQuery = "SELECT * FROM $TABLA_ARTICULOS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val tipo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO))
                val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
                val nombreA = when(nombre){
                    "BASTON" -> { Articulo.Nombre.BASTON}
                    "ESPADA"-> { Articulo.Nombre.ESPADA}
                    "DAGA"-> {Articulo.Nombre.DAGA}
                    "MARTILLO"-> {Articulo.Nombre.MARTILLO}
                    "GARRAS"-> {Articulo.Nombre.GARRAS}
                    "POCION"-> {Articulo.Nombre.POCION}
                    "IRA"-> {Articulo.Nombre.IRA}
                    "ESCUDO"-> {Articulo.Nombre.ESCUDO}
                    "ARMADURA"-> {Articulo.Nombre.ARMADURA}
                    else -> {Articulo.Nombre.BASTON}
                }
                val tipoA = when(tipo){
                    "ARMA"-> { Articulo.TipoArticulo.ARMA}
                    "OBJETO"-> {Articulo.TipoArticulo.OBJETO}
                    "PROTECCION" -> {Articulo.TipoArticulo.PROTECCION}
                    else -> {Articulo.TipoArticulo.ARMA}
                }
                Log.i("hola", Articulo(tipoA,nombreA, peso, precio).toString())
                articulos.add(Articulo(tipoA,nombreA, peso, precio))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return articulos
    }
}