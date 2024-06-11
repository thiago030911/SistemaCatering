package sistemacatering;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SistemaDatos implements Serializable {

    private ArrayList<Usuario> usuarios;
    private ArrayList<Reserva> reservas;

    public SistemaDatos() {
        usuarios = new ArrayList<Usuario>();
        reservas = new ArrayList<Reserva>();
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }
    
    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void agregarReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }
    
    public void eliminarReserva(Reserva reserva){
        this.reservas.remove(reserva);  
    }
    
    public SistemaDatos deSerializar(String archivo) throws IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(archivo);
        ObjectInputStream o = new ObjectInputStream(f);
        SistemaDatos sistD = (SistemaDatos) o.readObject();
        o.close();
        f.close();
        return sistD;
    }

    public void serializar(String archivo) throws IOException {
        FileOutputStream f = new FileOutputStream(archivo);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(this);
        o.close();
        f.close();
    }

    public Usuario buscarUsuario(String datos) {
        int i = 0;
        boolean flag = false;
        Usuario u = null;

        while (i < usuarios.size() && !flag) {
            u = usuarios.get(i);
            if (datos.equals(u.getUsuario() + ":" + u.getPassword())) {
                flag = true;
            } else {
                i++;
            }
        }
        if (!flag) {
            return null;
        } else {
            return u;
        }
    }
    
    public void agregarCliente(SistemaDatos sistD) {
        EntradaSalida.escribir("");
        String usuario = EntradaSalida.leer("Ingrese el nombre de usuario:");
        String password = EntradaSalida.leer("Ingrese la password:");
        Usuario u = sistD.buscarUsuario(usuario + ":" + password);
        if (u == null) {
            String nombre = EntradaSalida.leer("\t\t-INGRESO DE DATOS PERSONALES-\n"
                    + "Igrese su nombre:");
            String mail = EntradaSalida.leer("Ingrese su mail:");
            String tel = EntradaSalida.leer("Ingrese su telefono:");
            String direcEntrega = EntradaSalida.leer("Ingrese la direccion de entrega:");
            int codCliente = 0;

            codCliente = ultCodCliente(sistD);

            Cliente cl = new Cliente(nombre, mail, tel, direcEntrega, codCliente, usuario, password);

            sistD.agregarUsuario(cl);
            try {
                sistD.serializar("Catering.txt");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            EntradaSalida.escribir("\t\t-REGISTRO EXITOSO-");
        } else {
            EntradaSalida.escribir("\t\t***ERROR: El usuario y la password ya corresponden a otro usuario***");
            EntradaSalida.escribir("");
        }
    }

    public int ultCodCliente(SistemaDatos sistD) {
        int max = 0;
        for (Usuario user : sistD.getUsuarios()) {
            if (user instanceof Cliente) {
                int codCliente = ((Cliente) user).getCod();
                if (codCliente > max) {
                    max = codCliente;
                }
            }
        }
        return max + 1;
    }
}
