/*
 
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
*/

package co.edu.uniandes.csw.mueblesdelosalpes.servicios;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Mueble;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.AutenticacionException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCarritoMockRemote;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCatalogoMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioSeguridadMockLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/Seguridad")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SeguridadService {
    
    @EJB
    private IServicioSeguridadMockLocal SeguridadEjb;
    
    
    @GET
    @Path("usuarios/")
    public List<Usuario> getTodosLosUsuarios() {
        return SeguridadEjb.darUsuario();
 
    }

    @POST
    @Path("/ingresar")
    public Response ingresar(@FormParam("login") String login,
                             @FormParam("contraseña") String contraseña) {

        if (login == null || contraseña == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Los campos de nombre de usuario y contraseña son obligatorios.")
                           .build();
        }

        try {
            if (SeguridadEjb.autenticarUsuario(login, contraseña)) {
                return Response.ok("Autenticación Exitosa").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                               .entity("La autenticación ha fallado. Verifica tus credenciales.")
                               .build();
            }
        } catch (NullPointerException e) {
            // Esto maneja el caso en el que autenticarUsuario lance una NullPointerException
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("La autenticación ha fallado. Verifica tus credenciales.")
                           .build();
        }
    }

    @DELETE
    @Path("/eliminarusuario/{usuario}/{contraseña}/")
    public Response eliminarUsuario(@PathParam("usuario") String usuario, 
                                    @PathParam("contraseña") String contraseña){
        boolean eliminacionExitosa = SeguridadEjb.eliminarUsuarioBoolean(usuario, contraseña);
        try {
            if (eliminacionExitosa) {
                return Response.ok("Credenciales Correctas!! Eliminación Exitosa!!").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Credenciales Incorrectas, Eliminación fallida!! El Usuario " + usuario + " no se encontró.")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar el usuario!!").build();
        }
    }  

    
    @PUT
    @Path("/actualizarcontraseña/{usuario}/{contrasena}")
    public Response actualizarContraseña(@PathParam("usuario") String usuario,
                                         @PathParam("contrasena") String contraseña,
                                         String nuevaContraseña) {
        // Llamar al método para actualizar la contraseña
        boolean actualizacionExitosa = SeguridadEjb.actualizarContraseña(usuario, contraseña, nuevaContraseña);
        
        if (actualizacionExitosa) {
            return Response.ok("La contraseña se actualizó correctamente.").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("No se pudo actualizar la contraseña. Verifica tus credenciales.")
                           .build();
        }
    }
       
    
    
}

     

