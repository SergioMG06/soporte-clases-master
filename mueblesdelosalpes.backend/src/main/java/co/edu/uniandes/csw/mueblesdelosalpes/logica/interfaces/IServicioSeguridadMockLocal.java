/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$ IServicioSeguridadLocal.java
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 * Licenciado bajo el esquema Academic Free License version 3.0
 *
 * Ejercicio: Muebles de los Alpes
 * Autor: Juan Sebastián Urrego
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.AutenticacionException;
import java.util.List;
import javax.ejb.Local;



/**
 * Contrato funcional de los servicios de autenticación en el sistema
 * @author Juan Sebastián Urrego
 */
@Local
public interface IServicioSeguridadMockLocal
{
    
    /**
     * Registra el ingreso de un usuario al sistema.
     * @param nombre Login del usuario que quiere ingresar al sistema.
     * @param contraseña Contraseña del usuario que quiere ingresar al sistema.
     * @return usuario Retorna el objeto que contiene la información del usuario que ingreso al sistema.
     */
   public Usuario ingresar(String nombre, String contraseña)throws AutenticacionException;
   
   public boolean autenticarUsuario(String login, String contraseña);
   public List<Usuario> darUsuario();
   public boolean eliminarUsuarioBoolean(String usuario, String contraseña);
   public boolean actualizarContraseña(String usuario, String contraseña, String nuevaContraseña);

}


