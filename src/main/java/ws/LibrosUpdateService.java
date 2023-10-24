package ws;

import umg.edu.gt.DTO.LibroDTO;
import umg.edu.gt.DTO.AutoresDTO;
import umg.edu.gt.DTO.TiposDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@Path("/actualizarLibros")
public class LibrosUpdateService {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
 @PUT
@Path("/actualizar/{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response actualizarLibro(@PathParam("id") int id, LibroDTO libroActualizado) {
    try (Session session = sessionFactory.openSession()) {
        Transaction tx = session.beginTransaction();

        // Buscar el libro existente
        LibroDTO libroExistente = session.get(LibroDTO.class, id);
        if (libroExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("El libro con el ID proporcionado no existe en la base de datos.")
                           .build();
        }

        // Verificar si el idAutor existe
        if (session.get(AutoresDTO.class, libroActualizado.getIdAutor()) == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("El idAutor proporcionado no existe en la base de datos.")
                           .build();
        }

        // Verificar si el idTipo existe
        if (session.get(TiposDTO.class, libroActualizado.getIdTipo()) == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("El idTipo proporcionado no existe en la base de datos.")
                           .build();
        }

        // Actualizar los datos
        libroExistente.setNombre(libroActualizado.getNombre());
        libroExistente.setNumeroPaginas(libroActualizado.getNumeroPaginas());
        libroExistente.setIsbn(libroActualizado.getIsbn());
        libroExistente.setFechaPublicacion(libroActualizado.getFechaPublicacion());
        // La fecha de ingreso no se modifica, por lo que omitimos esa actualización
        libroExistente.setObservaciones(libroActualizado.getObservaciones());
        libroExistente.setIdAutor(libroActualizado.getIdAutor());
        libroExistente.setIdTipo(libroActualizado.getIdTipo());

        session.update(libroExistente);
        tx.commit();

        return Response.ok().entity(libroExistente).build();

    } catch (HibernateException he) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("Error de Hibernate: " + he.getMessage())
                       .build();
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                       .entity("Error general: " + e.getMessage())
                       .build();
    }
}

}
