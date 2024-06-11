package sistemacatering;

import java.io.IOException;

public class SistemaControl {

    public void iniciar() {

        SistemaDatos sistD = new SistemaDatos();

        boolean seguir;
        try {
            sistD = sistD.deSerializar("Catering.txt");
            seguir = EntradaSalida.leerBoolean("\t\t-SISTEMA DE CATERING-\n"
                    + "Desea ingresar?\n"
                    + "<1>SI\n"
                    + "<2>NO");
        } catch (Exception e) {
            String usuario = EntradaSalida.leer("\t\t-ARRANQUE INCIAL-\n"
                    + "Administrador/a, ingrese su usuario:");
            while (usuario.equals("")) {
                usuario = EntradaSalida.leer("\t\t***ERROR: Su usuario no puede ser nulo***\n"
                        + "Ingrese su usuario:");
            }
            String password = EntradaSalida.leer("Ingrese su password:");
            while (password.equals("")) {
                password = EntradaSalida.leer("\t\t***ERROR: Su password no puede ser nula***\n"
                        + "Ingrese su password:");
            }
            sistD.agregarUsuario(new Administrador(usuario, password));
            try {
                sistD.serializar("Catering.txt");
                EntradaSalida.escribir("\t\t-ARRANQUE INICIAL EXITOSO-\n"
                        + "\t\t   Reinicie el sistema.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            seguir = false;
        }

        while (seguir) {
            EntradaSalida.escribir("");
            boolean inicioRegistro = EntradaSalida.leerBoolean("\t\t-Bienvenido-\n"
                    + "<1>Continuar\n"
                    + "<2>Registrarse como cliente");
            if (inicioRegistro) {
                EntradaSalida.escribir("");
                String usuario = EntradaSalida.leer("Usuario:");
                String password = EntradaSalida.leer("Password:");
                
                Usuario u = sistD.buscarUsuario(usuario + ":" + password);

                if (u == null) {
                    EntradaSalida.escribir("\t\t***ERROR: La combinacion usuario/password ingresada no existe***");
                } else {
                    seguir = u.inicioSesion(sistD);
                }
            } else {
                sistD.agregarCliente(sistD);
                inicioRegistro = true;
            }
        }
    }
}
