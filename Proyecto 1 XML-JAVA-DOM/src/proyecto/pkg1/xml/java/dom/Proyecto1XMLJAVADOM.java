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
            // Parsear el archivo XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("catalog.xml");

            // Mostrar el contenido actual del XML
            System.out.println("Contenido actual del XML:");
            mostrarContenidoXML(doc);
            
            // Obtener la última ID del archivo XML
            String ultimaId = obtenerUltimaId(doc);

            // Generar la nueva ID incrementando la última ID
            int nuevaId = Integer.parseInt(ultimaId) + 1;

            
            // Pedir al usuario los datos para el nuevo nodo
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

            // Crear un nuevo nodo libro
            Element nuevoLibro = doc.createElement("book");
            nuevoLibro.setAttribute("id", String.valueOf(nuevaId));

            // Crear nodos para los elementos del libro
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

            // Agregar los elementos al nodo libro
            nuevoLibro.appendChild(autorElement);
            nuevoLibro.appendChild(tituloElement);
            nuevoLibro.appendChild(generoElement);
            nuevoLibro.appendChild(precioElement);
            nuevoLibro.appendChild(fechaElement);
            nuevoLibro.appendChild(descripcionElement);

            // Agregar el nuevo nodo libro al elemento raíz catalog
            Node raiz = doc.getDocumentElement();
            raiz.appendChild(nuevoLibro);

            // Guardar el documento XML modificado en un nuevo archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult("catalog_modificado.xml");
            transformer.transform(source, result);

            System.out.println("El nuevo libro ha sido agregado al XML.");

        } catch (ParserConfigurationException e) {
            System.err.println("Error de configuración del parser XML: " + e.getMessage());
            e.printStackTrace();

        } catch (SAXException | IOException e) {
            System.err.println("Error al procesar el archivo XML: " + e.getMessage());
            e.printStackTrace();

        } catch (TransformerException e) {
            System.err.println("Error al transformar el documento XML: " + e.getMessage());
            e.printStackTrace();

        } catch (DOMException e) {
            System.err.println("Error del modelo de objeto de documento (DOM): " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("Error no manejado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para mostrar el contenido del XML
    public static void mostrarContenidoXML(Document doc) {
        NodeList listaLibros = doc.getElementsByTagName("book");
        for (int i = 0; i < listaLibros.getLength(); i++) {
            Element libro = (Element) listaLibros.item(i);
            String id = libro.getAttribute("id");
            String autor = libro.getElementsByTagName("author").item(0).getTextContent();
            String titulo = libro.getElementsByTagName("title").item(0).getTextContent();
            String genero = libro.getElementsByTagName("genre").item(0).getTextContent();
            String precio = libro.getElementsByTagName("price").item(0).getTextContent();
            String fechaPublicacion = libro.getElementsByTagName("publish_date").item(0).getTextContent();
            String descripcion = libro.getElementsByTagName("description").item(0).getTextContent();

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
        // Método para obtener la última ID del archivo XML
    private static String obtenerUltimaId(Document doc) {
        String ultimaId = "0";

        NodeList listaLibros = doc.getElementsByTagName("book");
        if (listaLibros.getLength() > 0) {
            Element ultimoLibro = (Element) listaLibros.item(listaLibros.getLength() - 1);
            ultimaId = ultimoLibro.getAttribute("id");
        }

        return ultimaId;
    }
    // Método para obtener la entrada del usuario
    private static String obtenerEntradaUsuario(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
