package sistemacatering;

import java.io.Serializable;
import java.util.ArrayList;

public class Cliente extends Usuario implements Serializable {

    private String nombre;
    private String direccionEntrega;
    private String mail;
    private String tel;
    private int cod;

    public Cliente(String usuario, String password) {
        super(usuario, password);
    }

    public Cliente(String nombre, String mail, String tel, String direccionEntrega, int cod, String usuario, String password) {
        super(usuario, password);
        this.nombre = nombre;
        this.mail = mail;
        this.tel = tel;
        this.direccionEntrega = direccionEntrega;
        this.cod = cod;
    }

    @Override
    public boolean inicioSesion(SistemaDatos sistD) {
        int op;
        boolean seguir = true;

        do {
            op = EntradaSalida.leerEntero("\t\t-INICIO CLIENTE-\n"
                    + "<1>Consultar reservas\n"
                    + "<2>Mostrar datos del usuario\n"
                    + "<3>Volver al inicio\n"
                    + "<4>Salir del sistema");
            switch (op) {
                case 1:
                    consultarReservas(sistD);
                    break;
                case 2:
                    mostrarDatosUsuario();
                    op = 100;
                    break;
                case 3:
                    seguir = true;
                    break;
                case 4:
                    seguir = false;
                    break;
            }
        } while (op < 1 || op > 4);

        return seguir;
    }

    @Override
    public void mostrarDatosUsuario() {
        EntradaSalida.escribirLineas(40);
        EntradaSalida.escribir("-Cliente-\n"
                + "USUARIO: " + this.getUsuario() + "\n"
                + "PASSWORD: " + this.getPassword() + "\n"
                + "NOMBRE: " + this.getNombre() + "\n"
                + "MAIL: " + this.getMail() + "\n"
                + "TEL: " + this.getTel() + "\n"
                + "DIRECCION DE ENTREGA: " + this.getDireccionEntrega());
        EntradaSalida.escribirLineas(40);
    }

    public void consultarReservas(SistemaDatos sistD) {
        ArrayList<Reserva> reservasCliente = new ArrayList<>();

        for (Reserva reserva : sistD.getReservas()) {
            if (reserva.getCliente().equals(this)) {
                reservasCliente.add(reserva);
            }
        }

        if (reservasCliente.isEmpty()) {
            EntradaSalida.escribir("\t\t-Todavia no realizaste ninguna reserva-");
        } else {
            EntradaSalida.escribirLineas(40);
            EntradaSalida.escribir("\t\t-Reservas de " + this.getUsuario() + "-");
            for (Reserva r : reservasCliente) {
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
            EntradaSalida.escribirLineas(40);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

}
