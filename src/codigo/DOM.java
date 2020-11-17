/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author DAM
 */
public class DOM {

    //Objeto Document que almacena el DOM del XML seleccionado.
    Document doc;

    public int abrir_XML_DOM(File _fichero) {

        //doc representará el árbol DOM.
        doc = null;

        try {
            //Se crea un objeto DocumentBuilderFactory.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            //Atributos del documento.
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);

            //Se crea un objeto DocumentBuilder para cargar en él
            //la estructura de árbol DOM de un fichero seleccionado
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Interpreta este fichero XML, lo mapea y guarda en memoria y
            //da el apuntador a la raíz.
            doc = builder.parse(_fichero);
            //Ahorastá listo para ser recorrido.

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String recorrer_DOM_y_mostrar() {

        String salida = "";
        Node node;
        String datos_nodo[] = null;

        //Obtiene el primer nodo del DOM (primer hijo).
        Node raiz = doc.getFirstChild();

        //Obtiene una lista de nodos con todos los nodos hijo del raiz
        NodeList nodeList = raiz.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Es un nodo libro

                datos_nodo = procesarLibro(node);
                salida += "\r\n " + "Publicado en: " + datos_nodo[0];
                salida += "\r\n " + "El título es: " + datos_nodo[1];
                salida += "\r\n " + "El autor es: " + datos_nodo[2];
                salida += "\r\n -------------------------";
            }
        }

        return salida;
    }

    private String[] procesarLibro(Node _n) {
        String datos[] = new String[3];
        Node ntemp = null;
        int contador = 1;

        //Obtiene el valor del primer atributo del nodo
        datos[0] = _n.getAttributes().item(0).getNodeValue();

        NodeList nodos = _n.getChildNodes();

        for (int i = 0; i < nodos.getLength(); i++) {
            ntemp = nodos.item(i);

            if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                //Para acceder al texto con el título y autor 
                //accedo al nodo text hijo de ntemp y se saca su valor.
                datos[contador] = ntemp.getFirstChild().getNodeValue();
                contador++;
            }
        }

        return datos;
    }
    
    public int annadirDom(String _titulo, String _autor, String _anno) {

        try {
            //Elemento título con nodo de texto
            Node ntitulo = doc.createElement("Titulo");
            Node ntitulo_text = doc.createTextNode(_titulo);
            ntitulo.appendChild(ntitulo_text);

            //Elemento autor con nodo de texto
            Node nautor = doc.createElement("Autor");
            Node nautor_text = doc.createTextNode(_autor);
            nautor.appendChild(nautor_text);

            //Se crea un nodo de tipo elemento (<libro>)
            Node nlibro = doc.createElement("Libro");
            ((Element) nlibro).setAttribute("publicado_en", _anno);

            //Se añade a libro el nodo autor y título creados antes.
            nlibro.appendChild(ntitulo);
            nlibro.appendChild(nautor);

            //Se obtiene el primer nodo del documento y a él se le añade como
            //hijo al nodo libro que ya tiene colgando todos sus hijos y atributos creados antes.
            Node raiz = doc.getFirstChild();
            raiz.appendChild(nlibro);

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public int guardar_DOM_como_fichero(File _fichero) {

        try {
            File archivo_xml = new File(_fichero.getAbsolutePath());
            //Especifico el formato de salida.
            OutputFormat format = new OutputFormat(doc);
            //Especifica que la salida esté indentada
            format.setIndenting(true);

            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(archivo_xml), format);
            serializer.serialize(doc);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    
    public int modificar_DOM(String _tituloAntiguo, String _tituloNuevo) {
        //getNodeName
        try{
            //Node nodoHijoLibro;
            //nodo libros
            Node raiz = doc.getFirstChild();
            
            //System.out.println(raiz.getNodeType() + " " + raiz.getNodeName());
            
            //todos los nodos libro en una lista
            NodeList nodeListLibro = raiz.getChildNodes();
            Node nodeLibro;
            
            //Lo utilizo para guardar el texto nuevo en un nodo
            Node ntituloNuevo = doc.createElement("Titulo"); //
            Node ntituloNuevo_text = doc.createTextNode(_tituloNuevo);
            ntituloNuevo.appendChild(ntituloNuevo_text);
            
            
            //recorre la lista de nodos libro
            for(int i=0; i<nodeListLibro.getLength(); i++){
                    //Cada uno de los libros (nodo libro)
                    nodeLibro = nodeListLibro.item(i);
                    
                    NodeList nodeListHijoLibro = nodeLibro.getChildNodes();
                    
                    for(int j=0; j<nodeListHijoLibro.getLength(); j++){
                        Node ntemp = nodeListHijoLibro.item(j);
                        
                        if(ntemp.getNodeType() == Node.ELEMENT_NODE && ntemp.getNodeName() == "Titulo"){
                            
                            if (ntemp.getChildNodes().item(0).getNodeValue().equals(_tituloAntiguo)){
                                    System.out.println(ntemp.getChildNodes().item(0).getNodeValue());
                                    nodeLibro.replaceChild(ntituloNuevo, nodeListHijoLibro.item(j));
                                }
                        }
                    }
            }
            
            
            
            return 0;
        } catch(Exception e) {
            return -1;
        }
    }
}
