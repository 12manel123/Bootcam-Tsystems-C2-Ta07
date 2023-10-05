import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Ta07ArraylistYHastable {
	static ArrayList<Double> notasAlumnos = new ArrayList<>();//Lista para almacenar notas de alumnos
    static ArrayList<String> nombresAlumnos = new ArrayList<>();//Lista para almacenar nombres de alumnos
    static Hashtable<String, ArrayList<Double>> diccionarioNotas = new Hashtable<>();//Diccionario que relaciona nombres de alumnos con sus notas
    static Hashtable<String, Double> stockProductos = new Hashtable<>();//Almacena nombres de productos y sus precios
    
	public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            String opcion = mostrarMenu();

            switch (opcion) {
                case "1":
                    ejercicio1();
                    break;
                case "2":
                    mostrarMediasAlumnos();
                    break;
                case "3":
                    ejercicio2();
                    break;
                case "4":
                	agregarProducto();
                    break;
                case "5":
                	listarProductos(stockProductos);
                    break;
                case "6":
                	consultarPrecio(stockProductos);
                    break;
                case "0":
                	JOptionPane.showMessageDialog(null, "Adiós!");
                    salir = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Introduce un número del 0 al 6.");
            }
        }
	}
	
	public static String mostrarMenu() {
        return JOptionPane.showInputDialog(
                "Elige un ejercicio (0 para salir):\n" +
                "1.- Ex1 Calcular nota media de alumno\n" +
                "2.- Ex1 Mostrar medias de alumnos\n" +
                "3.- Ex2 Compra de supermercado\n" +
                "4.- Ex3 Añadir 10 productos\n" +
                "5.- Ex3 Listado de productos\n" +
                "6.- Ex3 Buscar producto"
        );
    }
	
	//1) Crea una aplicación que calcule la nota media de los alumnos pertenecientes al curso de programación. Una vez calculada la nota media se guardara esta información en un diccionario de datos que almacene la nota media de cada alumno. Todos estos datos se han de proporcionar por pantalla.
	public static void ejercicio1() {
        String nombreAlumno = JOptionPane.showInputDialog("Nombre del alumno:");
        ArrayList<Double> notasAlumno = new ArrayList<>();

        while (true) {
            String notaStr = JOptionPane.showInputDialog("Nota de "+nombreAlumno+" (1-10) (0 para terminar): ");
            double nota;
            try {
                nota = Double.parseDouble(notaStr);
            } 
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Formato de nota no válido. Introduce un número válido.");
                continue;
            }
            if (nota == 0) {
                break;
            }
            if (nota >= 1 && nota <= 10) {
                notasAlumno.add(nota);
            } 
            else {
                JOptionPane.showMessageDialog(null, "La nota debe estar entre 1 y 10.");
            }
        }
        if (!notasAlumno.isEmpty()) {
            double suma = 0;
            for (double nota : notasAlumno) {
                suma += nota;
            }

            double media = suma / notasAlumno.size();

            JOptionPane.showMessageDialog(null, "Nota media de " + nombreAlumno + ": " + media);

            diccionarioNotas.put(nombreAlumno, notasAlumno);
            nombresAlumnos.add(nombreAlumno);
            notasAlumnos.addAll(notasAlumno);
        } 
        else {
            JOptionPane.showMessageDialog(null, "No hay notas para " + nombreAlumno);
        }
    }
	
	public static void mostrarMediasAlumnos() {
	    StringBuilder mensaje = new StringBuilder("Medias de notas de los alumnos:\n");
	    for (String nombre : nombresAlumnos) {
	        ArrayList<Double> notas = diccionarioNotas.get(nombre);
	        double suma = 0;
	        for (double nota : notas) {
	            suma += nota;
	        }
	        double media = suma / notas.size();
	        mensaje.append(nombre).append(": ").append(media).append("\n");
	    }
	    JOptionPane.showMessageDialog(null, mensaje.toString());
	}

    /*2) Crea una aplicación que gestione el flujo de ventas de una caja de supermercado. El programa guardara la cantidades del carrito de compra dentro de una lista. Mostrará por pantalla la siguiente informacion:
	Mostrará por pantalla la siguiente informacion:
	• IVA aplicado (21% o 4%)
	• Precio total bruto y precio mas IVA. 
	• Numero de artículos comprados.
	• Cantidad pagada.
	• Cambio a devolver al cliente.
	*/
	public static void ejercicio2() {
        double totalVenta = 0;
        boolean continuarComprando = true;
        while (continuarComprando) {
            listarProductos(stockProductos);
            String productoCompra = JOptionPane.showInputDialog("Nombre del producto a comprar (0 para pagar): ");
            if (productoCompra.equals("0")) {
                continuarComprando = false;
            } else if (stockProductos.containsKey(productoCompra)) {
                double precioProducto = stockProductos.get(productoCompra);
                String cantidadStr = JOptionPane.showInputDialog("Dime la cantidad de " + productoCompra + " a comprar: ");
                double cantidad = Double.parseDouble(cantidadStr);
                double subtotal = cantidad * precioProducto;
                totalVenta += subtotal;
                JOptionPane.showMessageDialog(null, "Subtotal de " + productoCompra + ": " + subtotal + "€");
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
            }
        }
        double iva = totalVenta * 0.21;
        double totalConIVA = totalVenta + iva;
        JOptionPane.showMessageDialog(null, "IVA aplicado (21%): " + iva);
        JOptionPane.showMessageDialog(null, "Precio total bruto: " + totalVenta);
        JOptionPane.showMessageDialog(null, "Precio total con IVA: " + totalConIVA);
        double cantidadPagada = 0;
        while (cantidadPagada < totalConIVA) {
            String cantidadPagadaStr = JOptionPane.showInputDialog("Introduce la cantidad pagada (debe ser igual o superior a " + totalConIVA + "): ");
            cantidadPagada = Double.parseDouble(cantidadPagadaStr);
            if (cantidadPagada < totalConIVA) {
                JOptionPane.showMessageDialog(null, "La cantidad pagada es insuficiente. Debe pagar al menos " + totalConIVA + ".");
            }
        }
        double cambio = cantidadPagada - totalConIVA;
        JOptionPane.showMessageDialog(null, "Cantidad pagada: " + cantidadPagada);
        JOptionPane.showMessageDialog(null, "Cambio a devolver al cliente: " + cambio);
    }

	//3) Crea una base de datos de 10 artículos para controlar el stock de productos de una tienda por medio de un diccionario de datos (articulo:precio). El usuario podrá añadir, por medio de interfaz visual artículos nuevos y cantidades de estos. El usario podrá consultar la información almacenada en el diccionario referente a un articulo concreto e incluso listar toda la información en la terminal del programa.
	public static void agregarProducto() {	    
	    for (int i = 0; i < 10; i++) {
	        String producto = JOptionPane.showInputDialog("Nombre del producto: ");
	        String precioStr = JOptionPane.showInputDialog("Dime el precio de "+producto+":");
	        double precio = Double.parseDouble(precioStr);
	        stockProductos.put(producto, precio);
	    }
	}
	
	public static void listarProductos(Hashtable<String, Double> stockProductos) {
        StringBuilder lista = new StringBuilder("Listado de productos:\n");//Y el string builder: https://www.aprenderaprogramar.com/index.php?option=com_content&view=article&id=961:stringbuffer-stringbuilder-java-ejemplo-diferencias-entre-clases-criterios-para-elegir-metodos-cu00914c&catid=58&Itemid=180
        Set<String> productos = stockProductos.keySet();//La funcion de keySet la encontre aquí: http://www.tutorialspoint.com/java/util/hashmap_keyset.htm
        for (String producto : productos) {// Con el : es como decir toda la lista de productos recorre producto por porducto
            double precio = stockProductos.get(producto);
            lista.append(producto).append(": ").append(precio).append("\n");//Con esto muestro todos los productos y añado el precio para asi ir enseñando toda la lista estando en el bucle
        }
        JOptionPane.showMessageDialog(null, lista.toString());
    }
	
	public static void consultarPrecio(Hashtable<String, Double> stockProductos) {
        String productoConsulta = JOptionPane.showInputDialog("Dime el nombre del producto a consultar: ");
        if (stockProductos.containsKey(productoConsulta)) {
            double precioProducto = stockProductos.get(productoConsulta);
            JOptionPane.showMessageDialog(null, "Precio de " + productoConsulta + ": " + precioProducto);
        } 
        else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado en el stock.");
        }
    }
}
