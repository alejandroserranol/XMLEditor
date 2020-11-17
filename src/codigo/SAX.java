/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author DAM
 */
public class SAX {

    SAXParser parser;
    ManejadorSAX sh;
    File ficheroXML;

    public int abrir_XML_SAX(File fichero) {

        try {
            //Se crea un objeto SAXParser para interpretar el documentoXML.
            SAXParserFactory farctory = SAXParserFactory.newInstance();
            parser = farctory.newSAXParser();

            //Se crea una instancia del manejador que será el que recorra
            //el documento XML secuencialmente
            sh = new ManejadorSAX();
            ficheroXML = fichero;

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String recorrerSAX() {
        try {
            sh.cadena_resultado = "";
            parser.parse(ficheroXML, sh);
            return sh.cadena_resultado;

        } catch (SAXException ex) {
            return "Error al parsear con SAX";
        } catch (IOException ex) {
            return "Error al parsear con SAX";
        }
    }
}

class ManejadorSAX extends DefaultHandler {

    String cadena_resultado = "";
    int numLibro = 1;

    //Este método se ejecuta cuando se encuentra un elemento de apertura
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equals("Libros")) {
            cadena_resultado += "Se van a mostrar los libros de este documento\n************************************************\n";
        } else if (qName.equals("Libro")) {
            cadena_resultado += "Libro " + numLibro + "\n----------------------------------\n";
            numLibro++;
            cadena_resultado += "Publicado en: "
                    + attributes.getValue(attributes.getQName(0).trim());
        } else if (qName.equals("Titulo")) {
            cadena_resultado += "El título es: ".trim();
        } else if (qName.equals("Autor")) {
            cadena_resultado += "El autor es: ".trim();
        }

    }

    //Cuando se detecta una cadena de texto guarda ese texto en la variable de salida.
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        for (int i = start; i < length + start; i++) {
            cadena_resultado += ch[i];
        }
        cadena_resultado += "\n";
    }

    //Cuando se detecta el final de un elemento <libro>,
    //se pone una línea discontinua en la salida
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("Libro")) {
            cadena_resultado += "----------------------------------\n";
        }
    }
}
