package ws;
import umg.edu.gt.DTO.AutoresDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@Path("/actualizarAutores")
public class AutoresUpdateService {

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
    public Response actualizarAutor(@PathParam("id") int id, AutoresDTO autorActualizado) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Buscar el autor existente
            AutoresDTO autorExistente = session.get(AutoresDTO.class, id);
            if (autorExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("El autor con el ID proporcionado no existe en la base de datos.")
                               .build();
            }

            // Actualizar los datos
            autorExistente.setNombres(autorActualizado.getNombres());
            autorExistente.setApellidos(autorActualizado.getApellidos());
            autorExistente.setNacionalidad(autorActualizado.getNacionalidad());
            autorExistente.setAlias(autorActualizado.getAlias());
            autorExistente.setFechaNacimiento(autorActualizado.getFechaNacimiento());
            autorExistente.setObservaciones(autorActualizado.getObservaciones());

            session.update(autorExistente);

            tx.commit();

            return Response.ok().entity(autorExistente).build();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error de Hibernate: " + he.getMessage())
                           .build();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error general: " + e.getMessage())
                           .build();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
