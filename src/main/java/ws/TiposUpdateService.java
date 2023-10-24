package ws;

import umg.edu.gt.DTO.TiposDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@Path("/actualizarTipos")
public class TiposUpdateService {

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
    public Response actualizarTipo(@PathParam("id") int id, TiposDTO tipoActualizado) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Buscar el tipo existente
            TiposDTO tipoExistente = session.get(TiposDTO.class, id);
            if (tipoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("El tipo con el ID proporcionado no existe en la base de datos.")
                               .build();
            }

            // Actualizar los datos
            tipoExistente.setNombre(tipoActualizado.getNombre());

            session.update(tipoExistente);

            tx.commit();

            return Response.ok().entity(tipoExistente).build();
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
