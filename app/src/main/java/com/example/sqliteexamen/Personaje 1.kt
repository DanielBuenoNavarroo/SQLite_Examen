import android.content.Context
import android.util.Log
import com.example.sqliteexamen.DatabaseHelper

/***********************************************************************************************************************
 *  CLASE: Mochila
 *  CONSTRUCTOR:
 *      pesoMochila      - > Peso máximo que puede soportar la mochila (Int)
 *
 *  METODOS
 *      getPesoMochila()        - > Devuelve el peso máximo como Int
 *      addArticulo()           - > Añade un artículo (clase Articulo) a la mochila, comprobando que el peso del
 *                                  artículo sumado al peso total del resto de artículos de la Mochila no supere el
 *                                  límite (pesoMochila)
 *      getContenido()          - > Devuelve el ArrayList de artículos que contiene la mochila
 *      findObjeto(nombre)      - > Devuelve la posición del artículo que cuyo nombre (Articulo.Nombre) pasamos como
 *                                  entrada o -1 si no lo encuentra
 *
 **********************************************************************************************************************/
class Mochila(private var context: Context){
    private var pesoMochila = 50

    // sustituir el método getPesoMochila() por un método getNumeroArticulos()
    fun getNumeroArticulos() : Int{
        val dbHelper = DatabaseHelper(context)
        return dbHelper.getArticulos().size
    }

    fun addArticulo(articulo: Articulo) {
        val dbHelper = DatabaseHelper(context)
        if (articulo.getPeso() <= pesoMochila) {

            when (articulo.getTipoArticulo()) {
                Articulo.TipoArticulo.ARMA -> {
                    Log.i("hola", "entra aqui");
                    when (articulo.getNombre()) {
                        Articulo.Nombre.BASTON, Articulo.Nombre.ESPADA, Articulo.Nombre.DAGA,
                        Articulo.Nombre.MARTILLO, Articulo.Nombre.GARRAS -> {
                            dbHelper.insertarArticulo(articulo)
                            this.pesoMochila -= articulo.getPeso()
                            Log.i("hola", "${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> Log.i("hola", "Nombre del artículo no válido para el tipo ARMA.")
                    }
                }
                Articulo.TipoArticulo.OBJETO -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.POCION, Articulo.Nombre.IRA -> {
                            dbHelper.insertarArticulo(articulo)
                            this.pesoMochila -= articulo.getPeso()
                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo OBJETO.")
                    }
                }
                Articulo.TipoArticulo.PROTECCION -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                            dbHelper.insertarArticulo(articulo)
                            this.pesoMochila -= articulo.getPeso()
                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                    }
                }
            }
        } else {
            Log.i("hola", "El peso del artículo excede el límite de la mochila.")
        }
    }

    fun getContenido(): ArrayList<Articulo> {
        val dbHelper = DatabaseHelper(context)
        return dbHelper.getArticulos()
    }

    fun findObjeto(nombre: Articulo.Nombre): Int {
        val dbHelper = DatabaseHelper(context)
        val contenido = dbHelper.getArticulos()
        return contenido.indexOfFirst { it.getNombre() == nombre }
    }

    fun mostrarContenido() : String{
        val contenido = getContenido()
        return if (contenido.isEmpty()) {
            "Mochila vacía"
        } else {
            "Artículos en la mochila: ${contenido.joinToString("\n")}"
        }
    }

    override fun toString(): String {
        return "Artículos en la mochila:"
    }
}
/***********************************************************************************************************************
 *  CLASE: Articulo
 *  CONSTRUCTOR:
 *      tipoArticulo  - > Enumeración con valores ARMA, OBJETO, PROTECCION
 *      nombre        - > Enumeración con valores BASTON, ESPADA, DAGA, MARTILLO, GARRAS, POCION, IRA, ESCUDO, ARMADURA
 *      peso          - > Peso del artículo
 *
 *  METODOS
 *      getPeso()           - > Devuelve el peso como Int
 *      getNombre()         - > Devuelve el nombre del artículo
 *      getTipoArticulo()   - > Devuelve el tipo del artículo
 *      toString()          - > Sobreescribimos el método toString para darle formato a la visualización de los
 *                              artículos
 *      getAumentoAtaque()  - > Devuelve el aumento de ataque según el nombre del arma o si el objeto es IRA
 *      getAumentoDefensa() - > Devuelve el aumento de defensa según el nombre de la proteccion
 *      getAumentoVida()    - > Devuelve el aumento de vida si el objeto es POCION
 *
 *
 **********************************************************************************************************************/

class Articulo(private var tipoArticulo: TipoArticulo, private var nombre: Nombre, private var peso: Int, private var precio : Int) {

    enum class TipoArticulo { ARMA, OBJETO, PROTECCION }
    enum class Nombre { BASTON, ESPADA, DAGA, MARTILLO, GARRAS, POCION, IRA, ESCUDO, ARMADURA }

    fun getPrecio():Int{
        return precio
    }

    fun getPeso(): Int {
        return peso
    }
    fun getNombre(): Nombre {
        return nombre
    }
    fun getTipoArticulo(): TipoArticulo {
        return tipoArticulo
    }
    fun getAumentoAtaque(): Int {
        return when (nombre) {
            Nombre.BASTON -> 10
            Nombre.ESPADA -> 20
            Nombre.DAGA -> 15
            Nombre.MARTILLO -> 25
            Nombre.GARRAS -> 30
            Nombre.IRA -> 80
            else -> 0 // Para otros tipos de armas no especificados
        }
    }
    fun getAumentoDefensa(): Int {
        return when (nombre) {
            Nombre.ESCUDO -> 10
            Nombre.ARMADURA -> 20
            else -> 0 // Para otros tipos de protecciones no especificados
        }
    }
    fun getAumentoVida(): Int {
        return when (nombre) {
            Nombre.POCION -> 100
            else -> 0 // Para otros tipos de objetos no especificados
        }
    }
    override fun toString(): String {
        return "[Tipo Artículo:$tipoArticulo, Nombre:$nombre, Peso:$peso]"
    }
}
