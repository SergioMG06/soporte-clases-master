/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$ ServicioCatalogoMock.java
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 * Licenciado bajo el esquema Academic Free License version 3.0
 *
 * Ejercicio: Muebles de los Alpes
 * Autor: Juan Sebastián Urrego
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package co.edu.uniandes.csw.mueblesdelosalpes.logica.ejb;

import co.edu.uniandes.csw.mueblesdelosalpes.persistencia.mock.ServicioPersistenciaMock;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Mueble;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.TipoMueble;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCatalogoMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCatalogoMockRemote;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioPersistenciaMockLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 * Implementacion de los servicios del catálogo de muebles que se le prestan al sistema.
 * @author Juan Sebastián Urrego
 */
@Stateless
public class ServicioCatalogoMock implements IServicioCatalogoMockRemote,IServicioCatalogoMockLocal
{

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    /**
     * Interface con referencia al servicio de persistencia en el sistema
     */
    private IServicioPersistenciaMockLocal persistencia;

    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------

    /**
     * Constructor sin argumentos de la clase
     */
    public ServicioCatalogoMock()
    {
        persistencia=new ServicioPersistenciaMock();
        //Inicializa el arreglo de los muebles
  
    }

    //-----------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------

    /**
     * Agrega un mueble al sistema
     * @param mueble Nuevo mueble
     */
 
    public void agregarMueble(Mueble mueble)
    {
        try
        {
            persistencia.create(mueble);
        }
        catch (OperacionInvalidaException ex)
        {
            Logger.getLogger(ServicioCatalogoMock.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    /**
     * Se elimina un mueble del sistema dado su identificador único
     * @param id Identificador único del mueble
     */
    @Override
    public void eliminarMueble(long id)
    {
        Mueble m=(Mueble) persistencia.findById(Mueble.class, id);
        try
        {
            persistencia.delete(m);
        }
        catch (OperacionInvalidaException ex)
        {
            Logger.getLogger(ServicioCatalogoMock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Remueve un ejemplar del mueble (no el mueble)
     * @param id Identificador único del mueble
     */
    @Override
    public void removerEjemplarMueble(long id)
    {
        ArrayList<Mueble>muebles=(ArrayList<Mueble>) persistencia.findAll(Mueble.class);
        Mueble mueble;
        for (int i = 0; i < muebles.size(); i++)
        {
            mueble = muebles.get(i);
            if(mueble.getReferencia()==id)
            {
                int cantidad=mueble.getCantidad();
                mueble.setCantidad(cantidad-1);
                persistencia.update(mueble);
                break;
            }
        }
    }

    /**
     * Devuelve los muebles del sistema
     * @return muebles Arreglo con todos los muebles del sistema
     */
 
    public List<Mueble> darMuebles()
    {
        return persistencia.findAll(Mueble.class);
    }


    public Mueble buscarMueble(long id) {
        return (Mueble) persistencia.findById(Mueble.class, id);
    }
    
 
    
    public boolean RemoverMueble(long id) {
        Mueble m=(Mueble) persistencia.findById(Mueble.class, id);
        if(m != null){
            try {
                persistencia.delete(m);
            } catch (OperacionInvalidaException ex) {
                Logger.getLogger(ServicioCatalogoMock.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }else{
            return false;
        }
    }

     public List<Mueble> filtrarMueblesPorTipo(TipoMueble tipo) {
        List<Mueble> mueblesFiltrados = new ArrayList<Mueble>(); // Lista para almacenar los muebles filtrados
        List<Mueble> muebles = persistencia.findAll(Mueble.class); 

        // Iterar sobre todos los muebles
        for (Mueble mueble : muebles) {
            // Verificar si el tipo del mueble coincide con el tipo proporcionado
            if (mueble.getTipo().equalsIgnoreCase(tipo.name())) {
                // Si coincide, agregar el mueble a la lista de muebles filtrados
                mueblesFiltrados.add(mueble);
            }
        }

        // Devolver la lista de muebles filtrados
        return mueblesFiltrados;
    }


    public void AumentoMueble(long id) {
        
        ArrayList<Mueble> muebles = (ArrayList<Mueble>) persistencia.findAll(Mueble.class);
        Mueble mueble;
        for (int i = 0; i < muebles.size(); i++) {
            mueble = muebles.get(i);
            if (mueble.getReferencia() == id) {
                double precio = mueble.getPrecio();
                double aumento = precio * 0.3; // 20% de descuento
                double precioNuevo = precio + aumento;
                mueble.setPrecio(precioNuevo);
                persistencia.update(mueble);
                break;
            }
        }
    }
    
     
    public void DescuentoMueble(long id) {
        
        ArrayList<Mueble> muebles = (ArrayList<Mueble>) persistencia.findAll(Mueble.class);
        Mueble mueble;
        for (int i = 0; i < muebles.size(); i++) {
            mueble = muebles.get(i);
            if (mueble.getReferencia() == id) {
                double precio = mueble.getPrecio();
                double descuento = precio * 0.3; // 30% de descuento
                double precioNuevo = precio - descuento;
                mueble.setPrecio(precioNuevo);
                persistencia.update(mueble);
                break;
            }
        }
    }   
    
    
    
    
}

    



