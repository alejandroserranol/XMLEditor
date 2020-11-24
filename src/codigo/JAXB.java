/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.io.File;
import java.util.List;
import javalibros.Libros;
import javalibros.Libros.Libro;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author aleja
 */
public class JAXB {
    
    Libros misLibros;

    int abrir_XML_JAXB(File fichero) {
        
        try{
            //Crea una instancia JAXB.
            JAXBContext contexto = JAXBContext.newInstance(Libros.class);
            //Crea un objeto Unmarshaller.
            Unmarshaller u = contexto.createUnmarshaller();
            //Deserializa (unmarshal el fichero).
            misLibros = (Libros) u.unmarshal(fichero);
            
            return 0;
        } catch(Exception e){
            return -1;
        }
    }

    public String recorrerJAXB() {
        
        String cadena_resultado = "";
        cadena_resultado =  cadena_resultado + "Se van a mostrar los libros de este documento\n************************************************\n";
        
        List<Libros.Libro> lLibros = misLibros.getLibro();
        for(int i=0; i<lLibros.size(); i++){
            int numLibro = i+1;
            Libro libroTemporal = lLibros.get(i);
            cadena_resultado =  cadena_resultado + "Libro " + numLibro + "\n----------------------------------";
            cadena_resultado = cadena_resultado + "\nTitulo en: " + libroTemporal.getTitulo();
            cadena_resultado = cadena_resultado + "\nAutor en: " + libroTemporal.getAutor();
            cadena_resultado = cadena_resultado + "\nPublicado en: " + libroTemporal.getPublicadoEn();
            cadena_resultado = cadena_resultado + "\nEditorial en: " + libroTemporal.getEditorial();
            cadena_resultado = cadena_resultado + "\n----------------------------------\n";
        }
        
        return cadena_resultado;
    }
    
}
