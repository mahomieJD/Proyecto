package WS;

import umg.edu.gt.DTO.AutoresDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

@Path("/autores")
public class AutoresService {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
           
            throw new ExceptionInInitializerError(ex);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AutoresDTO> obtenerTodosLosAutores() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        List<AutoresDTO> autoresList = session.createQuery("FROM AutoresDTO", AutoresDTO.class).list();

        session.getTransaction().commit();
        session.close();

        return autoresList;
    }
}
