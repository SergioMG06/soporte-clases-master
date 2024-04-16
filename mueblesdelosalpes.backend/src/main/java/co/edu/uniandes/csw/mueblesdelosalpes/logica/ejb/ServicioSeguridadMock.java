/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$ ServicioSeguridadMock.java
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
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.AutenticacionException;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioSeguridadMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioPersistenciaMockLocal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;


/**
 * Clase que se encarga de la autenticación de un usuario en el sistema
 * @author Juan Sebastián Urrego
 */
@Stateless
public class ServicioSeguridadMock implements IServicioSeguridadMockLocal
{

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    /**
     * Interface con referencia al servicio de persistencia en el sistema
     */
    private IServicioPersistenciaMockLocal persistencia;

    //-----------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------

    /**
     * Constructor sin argumentos de la clase
     */
    public ServicioSeguridadMock()
    {
        persistencia=new ServicioPersistenciaMock();
    }

    //-----------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------

    /**
     * Registra el ingreso de un usuario al sistema.
     * @param nombre Login del usuario que quiere ingresar al sistema.
     * @param contraseña Contraseña del usuario que quiere ingresar al sistema.
     * @return usuario Retorna el objeto que contiene la información del usuario que ingreso al sistema.
     */
 @Override
    public Usuario ingresar(String nombre, String contraseña) throws AutenticacionException
    {
   
        Usuario u = (Usuario) persistencia.findById(Usuario.class, nombre);

        if (u != null)
        {
            if (u.getLogin().equals(nombre) && u.getContraseña().equals(contraseña))
            {
                return u;
            } 
            else
            {
                throw new AutenticacionException("La contraseña no es válida. Por favor, asegúrate de que el bloqueo de mayúsculas no está activado e inténtalo de nuevo.");
            }
        } 
        else
        {
            throw new AutenticacionException("El nombre de usuario proporcionado no pertenece a ninguna cuenta.");
        }
    }
    

    public boolean autenticarUsuario(String login, String contraseña) {
        Usuario u = (Usuario) persistencia.findById(Usuario.class, login);

        if (u != null && u.getLogin() != null && u.getContraseña() != null) {
            if (u.getLogin().equals(login) && u.getContraseña().equals(contraseña)) {
               return true;
            }
        }
        return false;
    }

    public List<Usuario> darUsuario()
    {
        return persistencia.findAll(Usuario.class);
    }
    

    public boolean eliminarUsuarioBoolean(String usuario, String contraseña) {
        // Busca el usuario por su nombre
        Usuario u = (Usuario) persistencia.findById(Usuario.class, usuario);

        // Verifica si se encontró un usuario con el nombre dado
        if (u != null) {
            // Obtiene la contraseña del usuario
            String contraseñaUsuario = u.getContraseña();

            // Verifica si la contraseña del usuario coincide con la contraseña proporcionada y no es nula
            if (contraseñaUsuario != null && contraseñaUsuario.equals(contraseña)) {
                try {
                    // Elimina el usuario
                    persistencia.delete(u);
                    return true; // Retorna true si se elimina correctamente
                } catch (OperacionInvalidaException ex) {
                    Logger.getLogger(ServicioSeguridadMock.class.getName()).log(Level.SEVERE, null, ex);
                    return false; // Retorna false si ocurre una excepción al eliminar
                }
            }
        }

        return false; // Retorna false si el usuario no se encuentra o la contraseña no coincide
    }
    
    public boolean actualizarContraseña(String login, String contraseña, String nuevaContraseña) {
        // Buscar el usuario por su nombre de usuario en la persistencia
        Usuario usuario = (Usuario) persistencia.findById(Usuario.class, login);

        // Verificar si se encontró un usuario y si las credenciales coinciden
        if (usuario != null && usuario.getLogin().equals(login) && usuario.getContraseña().equals(contraseña)) {
            // Actualizar la contraseña del usuario
            usuario.setContraseña(nuevaContraseña);
            // Actualizar el usuario en la persistencia
            persistencia.update(usuario);
            return true; // Indica que la contraseña se actualizó correctamente
        } else {
            // Indicar que las credenciales no son válidas o que el usuario no existe
            return false;
        }
    }

    
}
