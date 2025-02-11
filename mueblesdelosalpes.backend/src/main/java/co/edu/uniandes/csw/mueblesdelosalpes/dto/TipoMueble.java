/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$ TipoMueble.java
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 * Licenciado bajo el esquema Academic Free License version 3.0
 *
 * Ejercicio: Muebles de los Alpes
 * Autor: Juan Sebastián Urrego
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package co.edu.uniandes.csw.mueblesdelosalpes.dto;

/**
 * Clase de enumeración con los tipos de muebles en el sistema
 * @author Juan Sebastián Urrego
 */
public enum TipoMueble
{
    Interior, Exterior;

    public boolean equalsIgnoreCase(String tipo) {
        return this.name().equalsIgnoreCase(tipo);
    }
}

    
