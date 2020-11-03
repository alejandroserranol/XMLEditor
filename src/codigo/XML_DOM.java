/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author DAM
 */
public class XML_DOM {
    public static Document doc;
    
    public int abrir_XML_DOM(File fichero){
        doc = null;
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            factory.setIgnoringComments(true);
            
            factory.setIgnoringElementContentWhitespace(true);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            doc = builder.parse(fichero);
            
            return 0;
        } catch (Exception e){
            return -1;
        }
    }
    
    public String recorrer_DOM_y_mostrar(){
        String salida = "";
        Node node;
        String datos_nodo[] = null;
        
        Node raiz = doc.getFirstChild();
        
        NodeList nodeList = raiz.getChildNodes();
        
        for(int i=0; i<nodeList.getLength(); i++){
            node = nodeList.item(i);
            
            if(node.getNodeType() == Node.ELEMENT_NODE){
                datos_nodo = procesarLibro(node);
                salida += "\r\n " + "Publicado en: " + datos_nodo[0];
                salida += "\r\n " + "El título es: " + datos_nodo[1];
                salida += "\r\n " + "El autor es: " + datos_nodo[2];
                salida += "\r\n --------------------";
            }
        }        
        
        return salida;
    }

    private String[] procesarLibro(Node n) {
        String datos[] = new String[3];
        Node ntemp = null;
        int contador = 1;
        
        datos[0] = n.getAttributes().item(0).getNodeValue();
        
        NodeList nodos = n.getChildNodes();
        
        for(int i=0; i<nodos.getLength(); i++){
            ntemp = nodos.item(i);
            
            if(ntemp.getNodeType() == Node.ELEMENT_NODE){
                datos[contador] = ntemp.getFirstChild().getNodeValue();
                contador++;
            }
        }
        
        return datos;
    }
}
