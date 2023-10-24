package umg.edu.gt.DTO;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "libros")
public class LibroDTO {

 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numero_paginas")
    private int numeroPaginas;

    @Column(name = "isbn")
    private String isbn;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_publicacion")
    private Date fechaPublicacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "id_autor")
    private int idAutor;

    @Column(name = "id_tipo")
    private int idTipo;
    
     public LibroDTO() {
    }

    public LibroDTO(String nombre, int numeroPaginas, String isbn, Date fechaPublicacion, Date fechaIngreso, String observaciones, int idAutor, int idTipo) {
        this.nombre = nombre;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaIngreso = fechaIngreso;
        this.observaciones = observaciones;
        this.idAutor = idAutor;
        this.idTipo = idTipo;
    }
    
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }
    
}
