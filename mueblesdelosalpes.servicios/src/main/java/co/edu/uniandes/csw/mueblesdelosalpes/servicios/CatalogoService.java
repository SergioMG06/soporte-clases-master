/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.mueblesdelosalpes.servicios;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Mueble;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.TipoMueble;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCarritoMockRemote;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCatalogoMockLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/Catalogo")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CatalogoService {
 
    /**
     * Referencia al Ejb del catalogo encargada de realizar las operaciones del mismo.
     */
    @EJB
    private IServicioCatalogoMockLocal catalogoEjb;
   
 
    /**
     * Servicio que ofrece una lista JSON con el catálogo de Muebles de los alpes (Los muebles disponibles para la venta).
     * @return la lista JSON con los muebles de MDLA.
  
     */
    @GET
    @Path("muebles/")
    public List<Mueble> getTodosLosMuebles() {
        return catalogoEjb.darMuebles();
 
    }
    

    @GET
    @Path("/muebles/{id}")
    public Response obtenerMueble(@PathParam("id") long id) {
        Mueble mueble = catalogoEjb.buscarMueble(id);
        if (mueble != null) {
            return Response.ok(mueble).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(" El mueble " + id + " no se encontró en el catálogo.")
                    .build();
        }
    }
    
    
  
    @DELETE
    @Path("/eliminar/{id}")
    public Response eliminarMueble(@PathParam("id") long id) {
        boolean eliminacionExitosa = catalogoEjb.RemoverMueble(id);
        try {
            if (eliminacionExitosa) {
                return Response.ok("El mueble " + id + " ha sido eliminado del catalogo").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("El mueble " + id + " no se encontró en el catálogo.")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar el mueble!!").build();
        }
    }
    
    

    @POST
    @Path("/agregar/")
    public Response agregarMueble(Mueble nuevoMueble) {
        catalogoEjb.agregarMueble(nuevoMueble);
        return Response.status(Response.Status.CREATED)
                .entity("Mueble agregado al catalogo.")
                .build();
    }
    
    @PUT
    @Path("/muebles/{tipo}")
    public Response filtrarMueblesPorTipo(@PathParam("tipo") TipoMueble tipo) {
        List<Mueble> muebles = catalogoEjb.filtrarMueblesPorTipo(tipo);
        if (!muebles.isEmpty()) {
            try {
                // Construye el mensaje que incluye los nombres de los muebles encontrados
                StringBuilder message = new StringBuilder();
                message.append("Estos son los muebles de ").append(tipo).append(" que manejamos:\n");
                for (Mueble mueble : muebles) {
                    message.append("- ").append(mueble.getNombre()).append("\n");
                }
                return Response.ok(message.toString()).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al visualizar el tipo de mueble!!").build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No manejamos este tipo de mueble en el momento !!!").build();
        }
    }


    @PUT
    @Path("/muebles/{id}/aumento")
    public Response AumentoMueble(@PathParam("id") long id) {
        Mueble mueble = catalogoEjb.buscarMueble(id);
        if (mueble != null) {
            try {
                catalogoEjb.AumentoMueble(id);
                return Response.ok("Se le ha aplicado un aumento del 20% al Mueble " + id ).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al aumentar el precio del mueble!!").build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No se le pudo aplicar el aumento al mueble " + id + ", no se encontró en el catálogo.").build();
        }
    }
     
    @PUT
    @Path("/muebles/{id}/descuento")
    public Response DescuentoMueble(@PathParam("id") long id) {
        Mueble mueble = catalogoEjb.buscarMueble(id);
        if (mueble != null) {
            try {
                catalogoEjb.DescuentoMueble(id);
                return Response.ok("Se le ha aplicado un descuento del 30% al Mueble " + id ).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al disminuir el precio del mueble!!").build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No se le pudo aplicar el descuento al mueble " + id + ", no se encontró en el catálogo.").build();
        }
    }
    
    
} 


    
    