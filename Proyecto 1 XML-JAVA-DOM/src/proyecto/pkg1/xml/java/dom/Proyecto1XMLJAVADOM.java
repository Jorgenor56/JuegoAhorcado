package proyecto.pkg1.xml.java.dom;

import java.io.IOException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Scanner;
import org.xml.sax.SAXException;

public class Proyecto1XMLJAVADOM {

    public static void main(String[] args) throws SAXException, IOException {
        try {
            // Parsear el archivo XML, es decir, crea un archivo Document con una API
            //La primera linea crea una instancia de la clase DocumentBuilderFactor y un objeto DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Esta línea utiliza la instancia que creó la anterior para crear otra llamada DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Aquí utiliza el objeto de la línea de arriba para analizar el xml catalog.xml y crea un objeto document
            Document doc = builder.parse("catalog.xml");

            //Con un printl muestro el contenido actual del XML y llamo al método mostrarContenidoXML
            System.out.println("Contenido actual del XML:");
            mostrarContenidoXML(doc);

            //Aquí establezco que el valor de ultimaId depende de el método obtenerUltimaId
            String ultimaId = obtenerUltimaId(doc);

            //Crea la nueva id incrementando la última id en  uno
            int nuevaId = Integer.parseInt(ultimaId) + 1;

            //Pedir al usuario los datos para el nuevo nodo, creando un scanner llamado scanner para leer los datos
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el autor del libro: ");
            String autor = scanner.nextLine();
            System.out.print("Ingrese el título del libro: ");
            String titulo = scanner.nextLine();
            System.out.print("Ingrese el género del libro: ");
            String genero = scanner.nextLine();
            System.out.print("Ingrese el precio del libro: ");
            String precio = scanner.nextLine();
            System.out.print("Ingrese la fecha de publicación del libro: ");
            String fechaPublicacion = scanner.nextLine();
            System.out.print("Ingrese la descripción del libro: ");
            String descripcion = scanner.nextLine();

            //Crear un nuevo libro como elemento, y en la segunda linea le pongo el atributo id
            Element nuevoLibro = doc.createElement("book");
            nuevoLibro.setAttribute("id", String.valueOf(nuevaId));

            //Crear nodos para los elementos del libro, uno para cada dato
            Element autorElement = doc.createElement("author");
            autorElement.appendChild(doc.createTextNode(autor));
            Element tituloElement = doc.createElement("title");
            tituloElement.appendChild(doc.createTextNode(titulo));
            Element generoElement = doc.createElement("genre");
            generoElement.appendChild(doc.createTextNode(genero));
            Element precioElement = doc.createElement("price");
            precioElement.appendChild(doc.createTextNode(precio));
            Element fechaElement = doc.createElement("publish_date");
            fechaElement.appendChild(doc.createTextNode(fechaPublicacion));
            Element descripcionElement = doc.createElement("description");
            descripcionElement.appendChild(doc.createTextNode(descripcion));

            //Creo todos los atributos y se los añado al elemento libro
            nuevoLibro.appendChild(autorElement);
            nuevoLibro.appendChild(tituloElement);
            nuevoLibro.appendChild(generoElement);
            nuevoLibro.appendChild(precioElement);
            nuevoLibro.appendChild(fechaElement);
            nuevoLibro.appendChild(descripcionElement);

            //Obtengo el elemento raíz del documento xml representado por el objeto doc 
            //y en la segundo el nuevo libro pasa a ser hijo del elemento raíz 
            Node raiz = doc.getDocumentElement();
            raiz.appendChild(nuevoLibro);

            //Guardar el documento XML modificado en un nuevo archivo
            //Creo un objeto Transformer
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            //Configuro una salida para el transformador para que formatee el XML
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //Creo un objeto DOM, que indica al transformador que tome el documento XML llamado doc
            DOMSource source = new DOMSource(doc);
            //Creo un objeto StreamResult que dice el destino de la salida catalog_modificado.xml
            StreamResult result = new StreamResult("catalog_modificado.xml");
            //Ejecutamos la transformación del documento XML utilizando el transformador de antes
            transformer.transform(source, result);
            //Imprimo por pantalla un mensaje
            System.out.println("El nuevo libro ha sido agregado al XML.");

            //Aquí trato las posibles excepciones con varios catch
        } catch (ParserConfigurationException e) {
            //Captura una excepción de tipo ParserConfigurationException
            System.err.println("Error de configuración del parser XML: " + e.getMessage());
            e.printStackTrace();
            //Se produce cuando hay problemas en la configuración del parser XML, como configuraciones inválidas

        } catch (SAXException | IOException e) {
            //Captura excepciones de tipo SAXException o IOException
            System.err.println("Error al procesar el archivo XML: " + e.getMessage());
            e.printStackTrace();
            //SAXException se produce durante el análisis del archivo XML
            //IOException se produce si hay problemas al leer o escribir el archivo

        } catch (TransformerException e) {
            //Captura una excepción de tipo TransformerException
            System.err.println("Error al transformar el documento XML: " + e.getMessage());
            e.printStackTrace();
            //Se produce si hay problemas durante la transformación de XML, como errores en el formato de salida

        } catch (DOMException e) {
            //Captura una excepción de tipo DOMException
            System.err.println("Error del modelo de objeto de documento (DOM): " + e.getMessage());
            e.printStackTrace();
            //DOMException se produce en casos de problemas con el modelo de objeto de documento XML

        } catch (Exception e) {
            //Captura cualquier otra excepción no manejada
            System.err.println("Error no manejado: " + e.getMessage());
            e.printStackTrace();
        }

    }

    //Método para mostrar el contenido del XML
    public static void mostrarContenidoXML(Document doc) {
        //Obtiene una lista de elementos "book" del documento XML
        NodeList listaLibros = doc.getElementsByTagName("book");

        //Busca en la lista de elementos book
        for (int i = 0; i < listaLibros.getLength(); i++) {
            //Obtiene un elemento book en la posición actual de la búsqueda
            Element libro = (Element) listaLibros.item(i);

            //Obtiene el valor del atributo id de book
            String id = libro.getAttribute("id");

            //Obtiene el contenido del elemento author dentro de book, y lo mismo en las demás lineas
            String autor = libro.getElementsByTagName("author").item(0).getTextContent();
            String titulo = libro.getElementsByTagName("title").item(0).getTextContent();
            String genero = libro.getElementsByTagName("genre").item(0).getTextContent();
            String precio = libro.getElementsByTagName("price").item(0).getTextContent();
            String fechaPublicacion = libro.getElementsByTagName("publish_date").item(0).getTextContent();
            String descripcion = libro.getElementsByTagName("description").item(0).getTextContent();

            //Imprime la información del libro en la consola
            System.out.println("ID: " + id);
            System.out.println("Autor: " + autor);
            System.out.println("Título: " + titulo);
            System.out.println("Género: " + genero);
            System.out.println("Precio: " + precio);
            System.out.println("Fecha de Publicación: " + fechaPublicacion);
            System.out.println("Descripción: " + descripcion);
            System.out.println();
        }
    }

    //Método para obtener la última ID del archivo XML
    private static String obtenerUltimaId(Document doc) {
        String ultimaId = "0";
    //busca el último elemento "book" en un documento XML y devuelve el valor del atributo "id" de ese elemento 
    //Si no se encuentra ningún elemento "book", devuelve "0" como valor predeterminado
        NodeList listaLibros = doc.getElementsByTagName("book");
        //Este if se ejecuta si encuentra al menos un elemento en la lista de libros
        if (listaLibros.getLength() > 0) {
            //Estas líneas comprueban cual es el ultimo libro y su id
            Element ultimoLibro = (Element) listaLibros.item(listaLibros.getLength() - 1);
            ultimaId = ultimoLibro.getAttribute("id");
        }

        return ultimaId;
    }

    //Método para obtener la entrada del usuario
    private static String obtenerEntradaUsuario(Scanner scanner, String mensaje) {
        //Espera a que el usuario escriba una línea de texto en la consola y presione enter"
        //luego devuelve el texto ingresado por el usuario como una cadena de caracteres String
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
