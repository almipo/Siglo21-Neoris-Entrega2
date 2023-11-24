import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class CargaBiblioteca {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        List<Usuario> usuarios = new ArrayList<>();

        String opcion;
        int iopcion;

        opcion = JOptionPane.showInputDialog("Ingrese la opcion correspondiente \n 0-terminar carga \n 1-Cargar libros \n 2-Gestion usuarios\n");
        while (opcion == null) {
            opcion = JOptionPane.showInputDialog("Ingrese la opcion correspondiente \n 0-terminar carga \n 1-Cargar libros \n 2-Gestion usuarios\n");
        }
        iopcion = Integer.parseInt(opcion);
        while (iopcion != 0) {
            switch (iopcion) {
                case 1 -> cargarLibros(biblioteca);
                case 2 -> gestionarUsuarios(biblioteca, usuarios);
                case 3 -> eliminarLibroPorISBN(biblioteca);
                case 4 -> mostrarLibros(biblioteca);
                case 5 -> buscarLibros(biblioteca);
                default -> {
                    opcion = JOptionPane.showInputDialog("Ingrese la opcion correspondiente \n 0-terminar carga \n 1-Cargar libros \n 2-Gestion usuarios\n");
                    while (opcion == null) {
                        opcion = JOptionPane.showInputDialog("Ingrese la opcion correspondiente \n 0-terminar carga \n 1-Cargar libros \n 2-Gestion usuarios\n");
                    }
                    iopcion = Integer.parseInt(opcion);
                }
            }

            opcion = JOptionPane.showInputDialog("Ingrese la opcion correspondiente \n 0-terminar carga \n 1-Cargar libros \n 2-Gestion usuarios \n 3-Eliminar libro \n 4-Mostrar libros \n 5-Buscar libros\n");
            while (opcion == null) {
                opcion = JOptionPane.showInputDialog("Ingrese la opcion correspondiente \n 0-terminar carga \n 1-Cargar libros \n 2-Gestion usuarios \n 3-Eliminar libro \n 4-Mostrar libros \n 5-Buscar libros\n");
            }
            iopcion = Integer.parseInt(opcion);
        }
    }

    private static void cargarLibros(Biblioteca biblioteca) {
        String opcion;
        do {
            String titulo = JOptionPane.showInputDialog("Ingrese el título del libro:");
            String autor = JOptionPane.showInputDialog("Ingrese el autor del libro:");
            String isbn = JOptionPane.showInputDialog("Ingrese el ISBN del libro:");
            String genero = JOptionPane.showInputDialog("Ingrese el género del libro:");

            Libro libro = new Libro(titulo, autor, isbn, genero);
            biblioteca.agregarLibro(libro);

            opcion = JOptionPane.showInputDialog("¿Desea agregar otro libro? (S/N)");
        } while (opcion != null && opcion.equalsIgnoreCase("S"));
    }

    private static void gestionarUsuarios(Biblioteca biblioteca, List<Usuario> usuarios) {
        String opcion;
        int iopcion;

        opcion = JOptionPane.showInputDialog("Ingrese la opcion correspondiente \n 1-Registrar usuario \n 2-Alquilar libro \n 3-Devolver libro \n 4-Mostrar libros alquilados");
        while (opcion == null || (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3"))) {
            opcion = JOptionPane.showInputDialog("Ingrese la opcion correspondiente \n 1-Registrar usuario \n 2-Alquilar libro \n 3-Devolver libro \n 4-Mostrar libros alquilados");
        }
        iopcion = Integer.parseInt(opcion);

        switch (iopcion) {
            case 1 -> registrarUsuario(usuarios);
            case 2 -> alquilarLibro(biblioteca, usuarios);
            case 3 -> devolverLibro(biblioteca, usuarios);
            case 5-> mostrarLibrosAlquilados(usuarios);
        }
    }
    
    
    
    

    private static void registrarUsuario(List<Usuario> usuarios) {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");
        String id = JOptionPane.showInputDialog("Ingrese el ID del usuario:");
        Usuario usuario = new Usuario(nombre, id);
        usuarios.add(usuario);
    }

    private static void alquilarLibro(Biblioteca biblioteca, List<Usuario> usuarios) {
        String idUsuario = JOptionPane.showInputDialog("Ingrese el ID del usuario que quiere alquilar un libro:");
        Usuario usuario = buscarUsuarioPorID(usuarios, idUsuario);

        if (usuario != null) {
            String isbn = JOptionPane.showInputDialog("Ingrese el ISBN del libro que desea alquilar:");
            Libro libro = biblioteca.buscarLibroPorISBN(isbn);

            if (libro != null) {
                usuario.alquilarLibro(libro);
                JOptionPane.showMessageDialog(null, "Libro alquilado correctamente", "Alquiler Exitoso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Libro no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void devolverLibro(Biblioteca biblioteca, List<Usuario> usuarios) {
        String idUsuario = JOptionPane.showInputDialog("Ingrese el ID del usuario que quiere devolver un libro:");
        Usuario usuario = buscarUsuarioPorID(usuarios, idUsuario);

        if (usuario != null) {
            String isbn = JOptionPane.showInputDialog("Ingrese el ISBN del libro que desea devolver:");
            Libro libro = biblioteca.buscarLibroPorISBN(isbn);

            if (libro != null) {
                usuario.devolverLibro(libro);
                JOptionPane.showMessageDialog(null, "Libro devuelto correctamente", "Devolución Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Libro no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static Usuario buscarUsuarioPorID(List<Usuario> usuarios, String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    private static void mostrarLibrosAlquilados(List<Usuario> usuarios) {
    String idUsuario = JOptionPane.showInputDialog("Ingrese el ID del usuario para ver los libros alquilados:");
    Usuario usuario = buscarUsuarioPorID(usuarios, idUsuario);

    if (usuario != null) {
        List<Libro> librosAlquilados = usuario.getLibrosAlquilados();
        if (librosAlquilados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El usuario no tiene libros alquilados", "Libros Alquilados", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder mensaje = new StringBuilder("Libros alquilados por " + usuario.getNombre() + ":\n\n");
            for (Libro libro : librosAlquilados) {
                mensaje.append("Título: ").append(libro.getTitulo())
                        .append(", Autor: ").append(libro.getAutor())
                        .append(", ISBN: ").append(libro.getIsbn())
                        .append(", Género: ").append(libro.getGenero())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, mensaje.toString(), "Libros Alquilados", JOptionPane.INFORMATION_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    
    
    private static void eliminarLibroPorISBN(Biblioteca biblioteca) {
        String isbn = JOptionPane.showInputDialog("Ingrese el ISBN del libro que desea eliminar:");
        biblioteca.eliminarLibroPorISBN(isbn);
    }

    private static void mostrarLibros(Biblioteca biblioteca) {
        StringBuilder mensaje = new StringBuilder("Libros en la biblioteca:\n\n");
        for (Libro libro : biblioteca.getListaLibros()) {
            mensaje.append("Título: ").append(libro.getTitulo())
                    .append(", Autor: ").append(libro.getAutor())
                    .append(", ISBN: ").append(libro.getIsbn())
                    .append(", Género: ").append(libro.getGenero())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, mensaje.toString(), "Libros en la Biblioteca", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void buscarLibros(Biblioteca biblioteca) {
        String opcion = JOptionPane.showInputDialog("Ingrese el criterio de búsqueda:\n 1-Título\n 2-Autor\n 3-Género");
        while (opcion == null || opcion.length() == 0) {
            opcion = JOptionPane.showInputDialog("Ingrese el criterio de búsqueda:\n 1-Título\n 2-Autor\n 3-Género");
        }
        int iopcion = Integer.parseInt(opcion);

        String busqueda = JOptionPane.showInputDialog("Ingrese el término de búsqueda:");

        List<Libro> resultados = new ArrayList<>();

        switch (iopcion) {
            case 1 -> resultados = biblioteca.buscarLibrosPorTitulo(busqueda);
            case 2 -> resultados = biblioteca.buscarLibrosPorAutor(busqueda);
            case 3 -> resultados = biblioteca.buscarLibrosPorGenero(busqueda);
            default -> {
                break;
            }
        }

        mostrarResultadosBusqueda(resultados);
    }

    private static void mostrarResultadosBusqueda(List<Libro> resultados) {
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron resultados", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder mensaje = new StringBuilder("Resultados de la búsqueda:\n\n");
            for (Libro libro : resultados) {
                mensaje.append("Título: ").append(libro.getTitulo())
                        .append(", Autor: ").append(libro.getAutor())
                        .append(", ISBN: ").append(libro.getIsbn())
                        .append(", Género: ").append(libro.getGenero())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, mensaje.toString(), "Resultados de la Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

class Libro {
    private String titulo;
    private String autor;
    private String isbn;
    private String genero;

    // Constructor
    public Libro(String titulo, String autor, String isbn, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.genero = genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}

class Usuario {
    private String nombre;
    private String id;
    private List<Libro> librosAlquilados;

    // Constructor
    public Usuario(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
        this.librosAlquilados = new ArrayList<>();
    }

    // Métodos
    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public List<Libro> getLibrosAlquilados() {
        return librosAlquilados;
    }

    public void alquilarLibro(Libro libro) {
        librosAlquilados.add(libro);
    }

    public void devolverLibro(Libro libro) {
        librosAlquilados.remove(libro);
    }
}







class Biblioteca {
    private final List<Libro> listaLibros;

    // Constructor
    public Biblioteca() {
        this.listaLibros = new ArrayList<>();
    }

    // Métodos
    public void agregarLibro(Libro libro) {
        listaLibros.add(libro);
    }

    public void eliminarLibroPorISBN(String isbn) {
        // Buscar el libro por ISBN y eliminarlo
        for (Libro libro : listaLibros) {
            if (libro.getIsbn().equals(isbn)) {
                listaLibros.remove(libro);
                JOptionPane.showMessageDialog(null, "Libro eliminado correctamente", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        // Si no se encuentra el libro con el ISBN proporcionado
        JOptionPane.showMessageDialog(null, "Libro no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public List<Libro> getListaLibros() {
        return listaLibros;
    }

    public Libro buscarLibroPorISBN(String isbn) {
        // Buscar el libro por ISBN
        for (Libro libro : listaLibros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }

        // Si no se encuentra el libro con el ISBN proporcionado
        return null;
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro libro : listaLibros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                resultados.add(libro);
            }
        }
        return resultados;
    }

    public List<Libro> buscarLibrosPorAutor(String autor) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro libro : listaLibros) {
            if (libro.getAutor().equalsIgnoreCase(autor)) {
                resultados.add(libro);
            }
        }
        return resultados;
    }

    public List<Libro> buscarLibrosPorGenero(String genero) {
        List<Libro> resultados = new ArrayList<>();
        for (Libro libro : listaLibros) {
            if (libro.getGenero().equalsIgnoreCase(genero)) {
                resultados.add(libro);
            }
        }
        return resultados;
    }
}

