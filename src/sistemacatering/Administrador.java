package sistemacatering;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

public class Administrador extends Usuario implements Serializable {

    public Administrador(String usuario, String password) {
        super(usuario, password);
    }

    @Override
    public boolean inicioSesion(SistemaDatos sistD) {
        int op;
        boolean seguir = true;

        do {
            op = EntradaSalida.leerEntero("\t\t-INICIO ADMINISTRADOR-\n"
                    + "<1>Agregar un coordinador\n"
                    + "<2>Eliminar un coordinador\n"
                    + "<3>Eliminar un cliente\n"
                    + "<4>Mostrar datos del usuario\n"
                    + "<5>Mostrar datos del sistema\n"
                    + "<6>Volver al inicio\n"
                    + "<7>Salir del sistema");
            switch (op) {
                case 1:
                    agregarCoordinador(sistD);
                    op = 100;
                    break;
                case 2:
                    eliminarCoordinador(sistD);
                    op = 100;
                    break;
                case 3:
                    eliminarCliente(sistD);
                    op = 100;
                    break;
                case 4:
                    mostrarDatosUsuario();
                    op = 100;
                    break;
                case 5:
                    mostrarDatosSist(sistD);
                    op = 100;
                    break;
                case 6:
                    seguir = true;
                    try {
                        sistD.serializar("Catering.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 7:
                    seguir = false;
                    try {
                        sistD.serializar("Catering.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
            }
        } while (op < 1 || op > 7);

        return seguir;
    }

    @Override
    public void mostrarDatosUsuario() {
        EntradaSalida.escribirLineas(40);
        EntradaSalida.escribir("-Administrador-\n"
                + "USUARIO: " + this.getUsuario() + "\n"
                + "PASSWORD: " + this.getPassword());
        EntradaSalida.escribirLineas(40);
    }

    public void mostrarDatosSist(SistemaDatos sistD) {
        EntradaSalida.escribirLineas(40);
        EntradaSalida.escribir("\t\t-DATOS DEL SISTEMA-");
        EntradaSalida.escribir("-Administrador-\n"
                + "USUARIO: " + this.getUsuario() + "\n"
                + "PASSWORD: " + this.getPassword());
        EntradaSalida.escribir("");
        EntradaSalida.escribir("-Coordinadores-");
        for (Usuario u : sistD.getUsuarios()) {
            if (u instanceof Coordinador) {
                EntradaSalida.escribir("USUARIO: " + u.getUsuario());
                EntradaSalida.escribir("PASSWORD: " + u.getPassword());
                EntradaSalida.escribir("");
            }
        }
        EntradaSalida.escribir("-Clientes-");
        for (Usuario u : sistD.getUsuarios()) {
            if (u instanceof Cliente) {
                EntradaSalida.escribir("USUARIO: " + u.getUsuario());
                EntradaSalida.escribir("PASSWORD: " + u.getPassword());
                EntradaSalida.escribir("CODIGO: " + ((Cliente) u).getCod());
                EntradaSalida.escribir("NOMBRE: " + ((Cliente) u).getNombre());
                EntradaSalida.escribir("MAIL: " + ((Cliente) u).getMail());
                EntradaSalida.escribir("TEL: " + ((Cliente) u).getTel());
                EntradaSalida.escribir("DIRECCION ENTREGA: " + ((Cliente) u).getDireccionEntrega());
                EntradaSalida.escribir("");
            }
        }
        EntradaSalida.escribir("-Reservas-");
        for (Reserva r : sistD.getReservas()) {
            if (r instanceof Reserva) {
                EntradaSalida.escribir("TIPO DE RESERVA: " + r.getTipoReserva());
                EntradaSalida.escribir("TIPO DE SERVICIO: " + r.getTipoServicio());
                EntradaSalida.escribir("MENU SELECCIONADO: " + r.getMenu());
                EntradaSalida.escribir("DETALLES DEL MENU: \n" + r.getDetallesMenuPlatos());
                EntradaSalida.escribir("PRECIO: $" + r.getPrecioServicio());
                EntradaSalida.escribir("FECHA Y HORA DE INICIO: " + r.getFechaHoraInicio());
                EntradaSalida.escribir("FECHA Y HORA DE FIN: " + r.getFechaHoraFin());
                EntradaSalida.escribir("RESTRICCIONES DIETETICAS: " + r.getRestriccionesDiet());
                EntradaSalida.escribir("PREFERENCIAS: " + r.getPreferenciasCliente());
                EntradaSalida.escribir("ESTADO DE ENTREGA: " + (r.isEntregado() ? "Entregado" : "No se entrego"));
                EntradaSalida.escribir("");
            }
        }
        EntradaSalida.escribirLineas(40);
    }

    public void agregarCoordinador(SistemaDatos sistD) {
        EntradaSalida.escribir("");
        String usuario = EntradaSalida.leer("Ingrese el nombre de usuario:");
        String password = EntradaSalida.leer("Ingrese la password:");
        Usuario u = sistD.buscarUsuario(usuario + ":" + password);
        if (u == null) {
            Coordinador c = new Coordinador(usuario, password);

            sistD.agregarUsuario(c);

            EntradaSalida.escribir("\t\t-COORDINADOR CREADO CON EXITO-");
            EntradaSalida.escribir("");
        } else {
            EntradaSalida.escribir("\t\t***ERROR: El coordinador ya existe***");
            EntradaSalida.escribir("");
        }
    }

    public void eliminarCoordinador(SistemaDatos sistD) {
        EntradaSalida.escribir("");
        String usuario = EntradaSalida.leer("Ingrese el nombre del coordinador:");
        String password = EntradaSalida.leer("Ingrese la password del coordinador:");
        Iterator<Usuario> it = sistD.getUsuarios().iterator();
        while (it.hasNext()) {
            Usuario u = it.next();
            if (u instanceof Coordinador) {
                if (u.getUsuario().equals(usuario) && u.getPassword().equals(password)) {
                    it.remove();
                } else {
                    EntradaSalida.escribir("\t\t***ERROR: no se encontro al coordinador***");
                }
            }
        }
        EntradaSalida.escribir("\t\t-COORDINADOR ELIMINADO CON EXITO-");
        EntradaSalida.escribir("");
    }

    public void eliminarCliente(SistemaDatos sistD) {
        EntradaSalida.escribir("");
        String usuario = EntradaSalida.leer("Ingrese el nombre del Cliente: ");
        String password = EntradaSalida.leer("Ingrese la password del Cliente: ");
        Iterator<Usuario> it = sistD.getUsuarios().iterator();
        while (it.hasNext()) {
            Usuario u = it.next();
            if (u instanceof Cliente) {
                if (u.getUsuario().equals(usuario) && u.getPassword().equals(password)) {
                    it.remove();
                } else {
                    EntradaSalida.escribir("\t\t***ERROR: no se encontro al cliente***");
                }
            }
        }
        EntradaSalida.escribir("\t\t-CLIENTE ELIMINADO CON EXITO-");
        EntradaSalida.escribir("");
    }
}
