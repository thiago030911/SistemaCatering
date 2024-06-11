package sistemacatering;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Coordinador extends Usuario implements Serializable {

    public Coordinador(String usuario, String password) {
        super(usuario, password);
    }

    @Override
    public boolean inicioSesion(SistemaDatos sistD) {
        int op;
        boolean seguir = true;

        do {
            op = EntradaSalida.leerEntero("\t\t-INICIO COORDINADOR-\n"
                    + "<1>Crear una reserva\n"
                    + "<2>Modificar una reserva\n"
                    + "<3>Cancelar una reserva\n"
                    + "<4>Mostrar datos de una reserva\n"
                    + "<5>Modificar datos de un cliente\n"
                    + "<6>Mostrar datos de los clientes\n"
                    + "<7>Mostrar datos del usuario\n"
                    + "<8>Volver al inicio\n"
                    + "<9>Salir del sistema");
            switch (op) {
                case 1:
                    crearReserva(sistD);
                    op = 100;
                    break;
                case 2:
                    modificarReserva(sistD);
                    op = 100;
                    break;
                case 3:
                    cancelarReserva(sistD);
                    op = 100;
                    break;
                case 4:
                    mostrarDatosReserva(sistD);
                    op = 100;
                    break;
                case 5:
                    modificarDatosCliente(sistD);
                    op = 100;
                    break;
                case 6:
                    mostrarDatosClientes(sistD);
                    op = 100;
                    break;
                case 7:
                    mostrarDatosUsuario();
                    op = 100;
                    break;
                case 8:
                    seguir = true;
                    try {
                        sistD.serializar("Catering.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 9:
                    seguir = false;
                    try {
                        sistD.serializar("Catering.txt");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
            }
        } while (op < 1 || op > 9);

        return seguir;
    }

    @Override
    public void mostrarDatosUsuario() {
        EntradaSalida.escribirLineas(40);
        EntradaSalida.escribir("-Coordinador-\n"
                + "USUARIO: " + this.getUsuario() + "\n"
                + "PASSWORD: " + this.getPassword());
        EntradaSalida.escribirLineas(40);
    }

    public void mostrarDatosClientes(SistemaDatos sistD) {
        EntradaSalida.escribirLineas(40);
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
        EntradaSalida.escribirLineas(40);
    }

    public void mostrarDatosReserva(SistemaDatos sistD) {
        String usuario = EntradaSalida.leer("Ingrese el nombre de usuario:");
        String password = EntradaSalida.leer("Ingrese la password:");
        Usuario u = sistD.buscarUsuario(usuario + ":" + password);
        if (u == null || !(u instanceof Cliente)) {
            EntradaSalida.escribir("\t\t***ERROR: el usuario y la password ingresada no corresponden a ningun cliente***");
        } else {
            Cliente cliente = (Cliente) u;
            ArrayList<Reserva> reservasCliente = new ArrayList<>();

            for (Reserva reserva : sistD.getReservas()) {
                if (reserva.getCliente().equals(cliente)) {
                    reservasCliente.add(reserva);
                }
            }

            if (reservasCliente.isEmpty()) {
                EntradaSalida.escribir("\t\t-El cliente no realizo ninguna reserva-");
            } else {
                EntradaSalida.escribirLineas(40);
                EntradaSalida.escribir("-Reservas del cliente-");
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
    }

    public void modificarDatosCliente(SistemaDatos sistD) {
        int op;
        EntradaSalida.escribir("");
        String usuario = EntradaSalida.leer("Ingrese el nombre de usuario:");
        String password = EntradaSalida.leer("Ingrese la password:");
        Usuario u = sistD.buscarUsuario(usuario + ":" + password);
        if (u == null) {
            EntradaSalida.escribir("\t\t***ERROR: el usuario y la password ingresada no corresponden a ningun cliente***");
        } else {
            do {
                op = EntradaSalida.leerEntero("\t\t-Que desea modificar?-\n"
                        + "<1>Nombre de usuario\n"
                        + "<2>Password\n"
                        + "<3>Nombre del cliente\n"
                        + "<4>Mail\n"
                        + "<5>Tel");
                switch (op) {
                    case 1:
                        String nuevoUsuario = EntradaSalida.leer("Ingrese el nuevo nombre de usuario:");
                        for (Usuario user : sistD.getUsuarios()) {
                            if (user instanceof Cliente) {
                                if (user.getUsuario().equals(usuario) && user.getPassword().equals(password)) {
                                    user.setUsuario(nuevoUsuario);
                                }
                            }
                        }
                        EntradaSalida.escribir("\t\t-NOMBRE DE USUARIO ACTUALIZADO CON EXITO-");
                        EntradaSalida.escribir("");
                        break;
                    case 2:
                        String nuevaPassword = EntradaSalida.leer("Ingrese la nueva password:");
                        for (Usuario user : sistD.getUsuarios()) {
                            if (user instanceof Cliente) {
                                if (user.getUsuario().equals(usuario) && user.getPassword().equals(password)) {
                                    user.setPassword(nuevaPassword);
                                }
                            }
                        }
                        EntradaSalida.escribir("\t\t-PASSWORD ACTUALIZADA CON EXITO-");
                        EntradaSalida.escribir("");
                        break;
                    case 3:
                        String nuevoNombre = EntradaSalida.leer("Ingrese el nuevo nombre:");
                        for (Usuario user : sistD.getUsuarios()) {
                            if (user instanceof Cliente) {
                                if (user.getUsuario().equals(usuario) && user.getPassword().equals(password)) {
                                    ((Cliente) user).setNombre(nuevoNombre);
                                }
                            }
                        }
                        EntradaSalida.escribir("\t\t-NOMBRE ACTUALIZADO CON EXITO-");
                        EntradaSalida.escribir("");
                        break;
                    case 4:
                        String nuevoMail = EntradaSalida.leer("Ingrese el nuevo mail:");
                        for (Usuario user : sistD.getUsuarios()) {
                            if (user instanceof Cliente) {
                                if (user.getUsuario().equals(usuario) && user.getPassword().equals(password)) {
                                    ((Cliente) user).setMail(nuevoMail);
                                }
                            }
                        }
                        EntradaSalida.escribir("\t\t-MAIL ACTUALIZADO CON EXITO-");
                        EntradaSalida.escribir("");
                        break;
                    case 5:
                        String nuevoTel = EntradaSalida.leer("Ingrese el nuevo telefono:");
                        for (Usuario user : sistD.getUsuarios()) {
                            if (user instanceof Cliente) {
                                if (user.getUsuario().equals(usuario) && user.getPassword().equals(password)) {
                                    ((Cliente) user).setTel(nuevoTel);
                                }
                            }
                        }
                        EntradaSalida.escribir("\t\t-TEL ACTUALIZADO CON EXITO-");
                        EntradaSalida.escribir("");
                        break;
                }
            } while (op < 1 || op > 5);
        }
    }

    public void crearReserva(SistemaDatos sistD) {
        String tipoS = "";
        String tipoMenu = "";
        String subTipoMenu = "";
        String tipoReserva = "";
        String detallesMenu = "";
        float precio = 0;
        boolean entregado = false;
        int op;
        EntradaSalida.escribir("");
        EntradaSalida.escribir("\t\t-NUEVA RESERVA-\n");

        String usuario = EntradaSalida.leer("-Datos del cliente-\n"
                + "Ingrese el usuario del cliente:");
        String password = EntradaSalida.leer("Ingrese la password del cliente:");

        Usuario u = sistD.buscarUsuario(usuario + ":" + password);

        if (u == null) {
            EntradaSalida.escribir("\t\t***ERROR: el usuario y la password no corresponden a ningun usuario***");
        } else {
            do {
                op = EntradaSalida.leerEntero("\t\t-Que tipo de reserva es?-\n"
                        + "<1>Presencial\n"
                        + "<2>Por telefono\n"
                        + "<3>Por mail");
                switch (op) {
                    case 1:
                        tipoReserva = "Presencial";
                        break;
                    case 2:
                        tipoReserva = "Por telefono";
                        break;
                    case 3:
                        tipoReserva = "Por mail";
                        break;
                }
            } while (op < 1 || op > 3);

            do {
                tipoS = EntradaSalida.leer("-Ingrese el tipo de servicio-\n"
                        + "<1>Finger Food\n"
                        + "<2>Pizza Party\n"
                        + "<3>Lunch");
            } while (Integer.parseInt(tipoS) < 1 || Integer.parseInt(tipoS) > 3);

            if ((Integer.parseInt(tipoS) == 2)) {
                do {
                    tipoMenu = EntradaSalida.leer("-Elija el menu-\n"
                            + "<1>Menu 1\n"
                            + "<2>Menu 2\n"
                            + "<3>Menu 3\n"
                            + "<4>Menu 4");
                    switch (Integer.parseInt(subTipoMenu)) {
                        case 1:
                            detallesMenu = "-ENTRADA-\n"
                                    + "\t2 empanadas por persona\n"
                                    + "\tCarne\n"
                                    + "\tJamón y queso\n"
                                    + "-PIZZA LIBRE-\n"
                                    + "\tMuzzarella\n"
                                    + "\tFugazzeta\n"
                                    + "\tNapolitana\n"
                                    + "\tJamón\n"
                                    + "\tCaprese\n"
                                    + "\tHuevo\n"
                                    + "\tRoquefort\n"
                                    + "\tCalabresa\n"
                                    + "\tJamón y morrón\n"
                                    + "\tJamon crudo y rucula\n"
                                    + "\tCheddar y panceta\n"
                                    + "\tPalmitos\n"
                                    + "\tProvolone";
                            precio = 2600;
                            break;
                        case 2:
                            detallesMenu = "ENTRADA-\n"
                                    + "\tEmpanaditas de copetin de carne\n"
                                    + "\tSandwich de miga\n"
                                    + "\tPinchos de crispy de pollo acompañado de salsa mostaza y miel\n"
                                    + "\tMini hamburguesas bacon\n"
                                    + "-PIZZA LIBRE-\n"
                                    + "\tMuzzarella\n"
                                    + "\tFugazzeta\n"
                                    + "\tNapolitana \n"
                                    + "\tJamón\n"
                                    + "\tCapresse\n"
                                    + "\tHuevo\n"
                                    + "\tRoquefort\n"
                                    + "\tCalabresa\n"
                                    + "\tJamón y morrón \n"
                                    + "\tJamon crudo y rucula\n"
                                    + "\tCheddar y panceta\n"
                                    + "\tPalmitos\n"
                                    + "\tProvolone";
                            precio = 2900;
                            break;
                        case 3:
                            detallesMenu = "-ENTRADA-\n"
                                    + "\tTabla de fiambre:\n"
                                    + "\tJamón Cocido\n"
                                    + "\tJamón Crudo\n"
                                    + "\tQueso Pategras\n"
                                    + "\tBaston\n"
                                    + "\tLomito Ahumado\n"
                                    + "\tAceitunas rellenas\n"
                                    + "\tPanes\n"
                                    + "-PIZZA LIBRE-\n"
                                    + "\tMuzzarella\n"
                                    + "\tFugazzeta\n"
                                    + "\tNapolitana\n"
                                    + "\tJamón\n"
                                    + "\tCaprese\n"
                                    + "\tHuevo\n"
                                    + "\tRoquefort\n"
                                    + "\tCalabresa\n"
                                    + "\tJamón y morrón\n"
                                    + "\tJamon crudo y rucula\n"
                                    + "\tCheddar y panceta\n"
                                    + "\tPalmitos\n"
                                    + "\tProvolone";
                            precio = 2600;
                            break;
                        case 4:
                            detallesMenu = "-ENTRADA-\n"
                                    + "\t2 Tacos por persona\n"
                                    + "\tPollo\n"
                                    + "\tCarne\n"
                                    + "-PIZZA LIBRE-\n"
                                    + "\tMuzzarella\n"
                                    + "\tFugazzeta\n"
                                    + "\tNapolitana\n"
                                    + "\tJamón\n"
                                    + "\tCapresse\n"
                                    + "\tHuevo\n"
                                    + "\tRoquefort\n"
                                    + "\tCalabresa\n"
                                    + "\tJamón y morrón\n"
                                    + "\tJamon crudo y rucula\n"
                                    + "\tCheddar y panceta\n"
                                    + "\tPalmitos\n"
                                    + "\tProvolone";
                            precio = 2900;
                            break;
                    }
                } while ((Integer.parseInt(tipoMenu)) < 1 || (Integer.parseInt(tipoMenu)) > 4);
            } else if ((Integer.parseInt(tipoS) == 3)) {
                do {
                    tipoMenu = EntradaSalida.leer("-Elija un tipo de lunch-\n"
                            + "<1>Lunch Tradicional\n"
                            + "<2>Lunch gourmet\n"
                            + "<3>Lunch express\n"
                            + "<4>Menu pernil de cerdo");
                    switch (Integer.parseInt(tipoMenu)) {
                        case 1:
                            do {
                                subTipoMenu = EntradaSalida.leer("-Elija el menu-\n"
                                        + "<1>Menu para 10 personas\n"
                                        + "<2>Menu para 20 personas\n"
                                        + "<3>Menu para 40 personas\n"
                                        + "<4>Menu para 50 personas");
                                switch (Integer.parseInt(subTipoMenu)) {
                                    case 1:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t20 Sándwiches triples\n"
                                                + "\t10 Pizzetas\n"
                                                + "\t10 Empanaditas de copetín\n"
                                                + "\t10 Morenitos de lomito y cheddar\n"
                                                + "\t10 Chips de pollo con queso crema y ciboulette\n"
                                                + "\t10 Chips de jamón y tomate\n"
                                                + "\t10 Salchichitas encamisadas\n"
                                                + "\t10 Fosforitos\n"
                                                + "\t10 Medialunas de jamón y queso\n"
                                                + "\t1 Tarta dulce";
                                        precio = 25000;
                                        break;
                                    case 2:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t40 Sándwiches triples\n"
                                                + "\t20 Pizzetas\n"
                                                + "\t20 Empanaditas de copetín\n"
                                                + "\t20 Morenitos de lomito y cheddar\n"
                                                + "\t20 Chips de pollo con queso crema y ciboulette\n"
                                                + "\t20 Chips de jamón y tomate\n"
                                                + "\t20 Salchichitas encamisadas\n"
                                                + "\t20 Fosforitos\n"
                                                + "\t20 Medialunas de jamón y queso \n"
                                                + "\t2 Tartas dulces";
                                        precio = 48000;
                                        break;
                                    case 3:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t80 Sándwiches triples\n"
                                                + "\t40 Pizzetas\n"
                                                + "\t40 Empanaditas de copetín\n"
                                                + "\t40 Morenitos de lomito y cheddar\n"
                                                + "\t40 Chips de pollo con queso crema y ciboulette\n"
                                                + "\t40 Chips de jamón y tomate\n"
                                                + "\t40 Salchichitas encamisadas\n"
                                                + "\t40 Fosforitos\n"
                                                + "\t40 Medialunas de jamón y queso\n"
                                                + "\t4 Tartas dulces";
                                        precio = 96000;
                                        break;
                                    case 4:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t100 Sándwiches triples\n"
                                                + "\t50 Pizzetas\n"
                                                + "\t50 Empanaditas de copetín\n"
                                                + "\t50 Morenitos de lomito y cheddar\n"
                                                + "\t50 Chips de pollo con queso crema y ciboulette\n"
                                                + "\t50 Chips de jamón y tomate\n"
                                                + "\t50 Salchichitas encamisadas\n"
                                                + "\t50 Fosforitos\n"
                                                + "\t50 Medialunas de jamón y queso\n"
                                                + "\t5 Tartas dulces";
                                        precio = 120000;
                                        break;
                                }
                            } while ((Integer.parseInt(subTipoMenu)) < 1 || (Integer.parseInt(subTipoMenu)) > 4);
                            break;
                        case 2:
                            do {
                                subTipoMenu = EntradaSalida.leer("-Elija el menu-\n"
                                        + "<1>Menu para 10 personas\n"
                                        + "<2>Menu para 20 personas\n"
                                        + "<3>Menu para 30 personas\n"
                                        + "<4>Menu para 40 personas\n"
                                        + "<5>Menu para 50 personas");
                                switch (Integer.parseInt(subTipoMenu)) {
                                    case 1:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t16 Sandwich de miga especiales\n"
                                                + "\t10 Pinchos de serrano y gruyere\n"
                                                + "\t20 Empanaditas de copetin variadas\n"
                                                + "\t10 Negros de lomito y cheddar\n"
                                                + "\t10 Pinchos crispy de pollo\n"
                                                + "\t10 Salchichitas encamisadas\n"
                                                + "\t10 Sandwich de bondiola en pan saborizado\n"
                                                + "\t10 Tortilla de papa\n"
                                                + "\t10 Mini hamburguesas bacon\n"
                                                + "\t1 Tarta dulce";
                                        precio = 27000;
                                        break;
                                    case 2:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t20 Sandwich de miga especiales\n"
                                                + "\t20 Pinchos de serrano y gruyere\n"
                                                + "\t40 Empanaditas de copetin variadas \n"
                                                + "\t20 Negritos de lomito y cheddar\n"
                                                + "\t20 Pinchos crispy de pollo\n"
                                                + "\t20 Salchichitas encamisadas\n"
                                                + "\t20 Sandwich de bondiola en pan saborizado\n"
                                                + "\t20 Tortilla de papa\n"
                                                + "\t20 Mini hamburguesas bacon\n"
                                                + "\t2 Tartas dulce";
                                        precio = 50000;
                                        break;
                                    case 3:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t30 Sandwich de miga especiales\n"
                                                + "\t30 Pinchos de serrano y gruyere\n"
                                                + "\t60 Empanaditas de copetin variadas \n"
                                                + "\t30 Negritos de lomito y cheddar\n"
                                                + "\t30 Pinchos crispy de pollo\n"
                                                + "\t30 Salchichitas encamisadas\n"
                                                + "\t30 Sandwich de bondiola en pan saborizado\n"
                                                + "\t30 Tortilla de papa\n"
                                                + "\t30 Mini hamburguesas bacon\n"
                                                + "\t3 Tartas dulce";
                                        precio = 75000;
                                        break;
                                    case 4:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t40 Sandwich de miga especiales\n"
                                                + "\t40 Pinchos de serrano y gruyere\n"
                                                + "\t80 Empanaditas de copetin variadas\n"
                                                + "\t40 Negritos de lomito y cheddar\n"
                                                + "\t40 Pinchos crispy de pollo\n"
                                                + "\t40 Salchichitas encamisadas\n"
                                                + "\t40 Sandwich de bondiola en pan saborizado\n"
                                                + "\t40 Tortilla de papa\n"
                                                + "\t40 Mini hamburguesas bacon\n"
                                                + "\t4 Tartas dulce";
                                        precio = 100000;
                                        break;
                                    case 5:
                                        detallesMenu = "CONTIENE-\n"
                                                + "\t50 Sandwich de miga especiales\n"
                                                + "\t50 Pinchos de serrano y gruyere\n"
                                                + "\t100 Empanaditas de copetin variadas \n"
                                                + "\t50 Negritos de lomito y cheddar\n"
                                                + "\t50 Pinchos crispy de pollo\n"
                                                + "\t50 Salchichitas encamisadas\n"
                                                + "\t50 Sandwich de bondiola en pan saborizado\n"
                                                + "\t50 Tortilla de papa\n"
                                                + "\t50 Mini hamburguesas bacon\n"
                                                + "\t5 Tartas dulces";
                                        precio = 120000;
                                        break;
                                }
                            } while ((Integer.parseInt(subTipoMenu)) < 1 || (Integer.parseInt(subTipoMenu)) > 5);
                            break;
                        case 3:
                            do {
                                subTipoMenu = EntradaSalida.leer("-Elija el menu-\n"
                                        + "<1>Menu para 5 personas\n"
                                        + "<2>Menu para 10 personas\n"
                                        + "<3>Menu para 20 personas\n"
                                        + "<4>Menu para 30 personas");
                                switch (Integer.parseInt(subTipoMenu)) {
                                    case 1:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t16 Sandwich triples\n"
                                                + "\t10 Fosforitos\n"
                                                + "\t10 Pizzetas\n"
                                                + "\t10 Empanaditas de copetin";
                                        precio = 10000;
                                        break;
                                    case 2:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t16 Sándwich triples\n"
                                                + "\t15 Pizzetas\n"
                                                + "\t15 Fosforitos\n"
                                                + "\t15 Emapanaditas de copetin\n"
                                                + "\t15 Salchichitas\n"
                                                + "\t15 Medialunas de jamon y queso";
                                        precio = 18500;
                                        break;
                                    case 3:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t32 Sándwich triples\n"
                                                + "\t30 Pizzetas\n"
                                                + "\t30 Fosforitos\n"
                                                + "\t30 Emapanaditas de copetin\n"
                                                + "\t30 Salchichitas\n"
                                                + "\t30 Medialunas de jamon y queso";
                                        precio = 37000;
                                        break;
                                    case 4:
                                        detallesMenu = "CONTIENE-\n"
                                                + "\t48 Sándwich triples\n"
                                                + "\t45 Pizzetas\n"
                                                + "\t45 Fosforitos\n"
                                                + "\t45 Empanaditas de copetin\n"
                                                + "\t45 Salchichitas\n"
                                                + "\t45 Menialuas de jamon y queso";
                                        precio = 55500;
                                        break;
                                }
                            } while ((Integer.parseInt(subTipoMenu)) < 1 || (Integer.parseInt(subTipoMenu)) > 4);
                            break;
                        case 4:
                            do {
                                subTipoMenu = EntradaSalida.leer("-Elija el menu-\n"
                                        + "<1>Menu para 20 personas\n"
                                        + "<2>Menu para 30 personas\n"
                                        + "<3>Menu para 40 personas\n"
                                        + "<4>Menu para 50 personas");
                                switch (Integer.parseInt(subTipoMenu)) {
                                    case 1:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t24 sándwiches de miga triples\n"
                                                + "\t20 empanadas copetín variada \n"
                                                + "\t20 salchichitas encaminadas \n"
                                                + "\t20 medialunas con jamon y queso\n"
                                                + "\tPernil de cerdo 7/8 kg (incluye 80 panes y 3 salsas)";
                                        precio = 37000;
                                        break;
                                    case 2:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t32 sándwiches de miga triples\n"
                                                + "\t30 empanadas copetín variada\n"
                                                + "\t30 salchichitas encamisadas \n"
                                                + "\t30 medialunas de jamon y queso\n"
                                                + "\tPernil de cerdo 8/9 kg (incluye 100 panes y 3 salsas";
                                        precio = 55500;
                                        break;
                                    case 3:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t48 sándwiches de miga triples\n"
                                                + "\t40 empanadas copetín variada \n"
                                                + "\t40 salchichitas encamisadas\n"
                                                + "\t40 medialunas con jamon y queso\n"
                                                + "\tPernil de cerdo 9/10 kg (incluye 120 panes y 3 salsas)";
                                        precio = 74000;
                                        break;
                                    case 4:
                                        detallesMenu = "-CONTIENE-\n"
                                                + "\t50 sándwiches de miga triples\n"
                                                + "\t50 canastitas gustos variados \n"
                                                + "\t50 salchichitas encamisadas\n"
                                                + "\t50 medialunas con jamon y queso\n"
                                                + "\tPernil de cerdo 10/11 kg (incluye 150 panes y 3 salsas)";
                                        precio = 92000;
                                        break;
                                }
                            } while ((Integer.parseInt(subTipoMenu)) < 1 || (Integer.parseInt(subTipoMenu)) > 4);
                            break;
                    }
                } while ((Integer.parseInt(tipoMenu)) < 1 || (Integer.parseInt(tipoMenu)) > 4);
            } else {
                tipoS = "1";
                tipoMenu = "1";
                detallesMenu = "-MENU-\n"
                        + "\tBrusquetas de salmon ahumado y ciboullette\n"
                        + "\tBrusquetas de jamon crudo y rucula\n"
                        + "\tMini sandwich de pollo en pan integral \n"
                        + "\tCazuela de ñoquis\n"
                        + "\tMini hamburguesas con queso cheddar y bacon\n"
                        + "\tMini ensalada caesar\n"
                        + "\tPinchos capresse\n"
                        + "\tVariedad de mini empanaditas \n"
                        + "\tPinchos crispy de pollo\n"
                        + "\tPeceto en panco con pan de brioche\n"
                        + "\tSándwich de miga especiales\n"
                        + "\tPernil de cerdo + Panes + Salsas\n"
                        + "\t\t-MESA DULCE-\n"
                        + "\tLemon Pie\n"
                        + "\tTarta de frutillas\n"
                        + "\tBrownie con dulce de leche y merengue \n"
                        + "\tBrownie, dulce de leche, crema y frutos rojos\n"
                        + "\tCheesecake con frutos rojos\n"
                        + "\tCrumble de manzana\n"
                        + "\tTorta Oreo\n"
                        + "\tTarta de chocolate, dulce de leche y ganache de chocolate\n"
                        + "\tChocotorta";
            }

            tipoMenu = tipoMenu + "-" + subTipoMenu;
            EntradaSalida.escribir("");
            String fechaI = EntradaSalida.leer("Ingrese la fecha de inicio en el siguiente formato --> DD/MM/AA:");
            String horaI = EntradaSalida.leer("Ingrese la hora de inicio en el siguiente formato --> Hs:min:");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
            LocalDateTime inicioEvento = LocalDateTime.parse(fechaI + " " + horaI, formatter);

            int duracionHoras = 0;
            LocalDateTime finEvento;
            if (tipoS.equals("1")) {
                duracionHoras = 5;
                finEvento = inicioEvento.plusHours(duracionHoras);
            } else if (tipoS.equals("2")) {
                duracionHoras = 2;
                finEvento = inicioEvento.plusHours(duracionHoras);
            } else {
                duracionHoras = EntradaSalida.leerEntero("Ingrese la duracion del evento en horas(Hs):");
                finEvento = inicioEvento.plusHours(duracionHoras);
            }

            String fechaHoraI = inicioEvento.format(formatter);
            String fechaHoraF = finEvento.format(formatter);

            String restricD = EntradaSalida.leer("Ingrese las restricciones dieteticas:");
            String preferenciasC = EntradaSalida.leer("Ingrese las preferencias del cliente:");

            sistD.agregarReserva(new Reserva((Cliente) u, tipoReserva, tipoS, tipoMenu, detallesMenu, precio, fechaHoraI, fechaHoraF, restricD, preferenciasC, entregado));

            EntradaSalida.escribir("\t\t-RESERVA CREADA CON EXITO-");
        }
    }

    public void cancelarReserva(SistemaDatos sistD) {
        EntradaSalida.escribir("");
        String usuario = EntradaSalida.leer("Ingrese el nombre del cliente:");
        String password = EntradaSalida.leer("Ingrese la password del cliente:");
        Usuario u = sistD.buscarUsuario(usuario + ":" + password);

        if (u == null || !(u instanceof Cliente)) {
            EntradaSalida.escribir("\t\t***ERROR: el usuario y la password ingresados no corresponden a ningun cliente***");
        } else {
            Cliente cliente = (Cliente) u;
            ArrayList<Reserva> reservasCliente = new ArrayList<>();
            int nroReserva = 0;
            for (Reserva reserva : sistD.getReservas()) {
                if (reserva.getCliente().equals(cliente)) {
                    reservasCliente.add(reserva);
                    nroReserva++;
                }
            }

            if (reservasCliente.isEmpty()) {
                EntradaSalida.escribir("\t\t-El cliente no ha realizado ninguna reserva-");
            } else {
                EntradaSalida.escribir("El cliente " + u.getUsuario()
                        + " realizo " + nroReserva + " reservas");
                EntradaSalida.escribir("");
                for (Reserva reserva : sistD.getReservas()) {
                    if (reserva.getCliente().equals(cliente)) {
                        EntradaSalida.escribir("TIPO DE SERVICIO: " + reserva.getTipoServicio());
                        EntradaSalida.escribir("MENU SELECCIONADO: " + reserva.getMenu());
                        EntradaSalida.escribir("FECHA Y HORA DE INICIO: " + reserva.getFechaHoraInicio());
                        EntradaSalida.escribir("FECHA Y HORA DE FIN: " + reserva.getFechaHoraFin());
                        EntradaSalida.escribir("RESTRICCIONES DIETETICAS: " + reserva.getRestriccionesDiet());
                        EntradaSalida.escribir("PREFERENCIAS: " + reserva.getPreferenciasCliente());
                        EntradaSalida.escribir("");
                    }
                }
                EntradaSalida.escribir("\t\t-Las reservas estan ordenadas de forma ascendente-");
                EntradaSalida.escribir("");
                int posReserva = EntradaSalida.leerEntero("-Cual desea cancelar?-\n"
                        + "Ingrese un nro");
                sistD.eliminarReserva(reservasCliente.get(posReserva - 1));
                EntradaSalida.escribir("\t\t-RESERVA CANCELADA-");
            }
        }
    }

    public void modificarReserva(SistemaDatos sistD) {
        EntradaSalida.escribir("");
        String usuario = EntradaSalida.leer("Ingrese el nombre del cliente:");
        String password = EntradaSalida.leer("Ingrese la password del cliente:");
        String tipoS;
        String tipoMenu = "";
        String subTipoMenu = "";
        Usuario u = sistD.buscarUsuario(usuario + ":" + password);
        int posReserva = 0;
        int op;

        if (u == null || !(u instanceof Cliente)) {
            EntradaSalida.escribir("\t\t***ERROR: el usuario y la password ingresados no corresponden a ningun cliente***");
        } else {
            Cliente cliente = (Cliente) u;
            ArrayList<Reserva> reservasCliente = new ArrayList<>();
            int nroReserva = 0;
            for (Reserva reserva : sistD.getReservas()) {
                if (reserva.getCliente().equals(cliente)) {
                    reservasCliente.add(reserva);
                    nroReserva++;
                }
            }

            if (reservasCliente.isEmpty()) {
                EntradaSalida.escribir("\t\t-El cliente no ha realizado ninguna reserva-");
            } else {
                EntradaSalida.escribir("El cliente " + u.getUsuario()
                        + " realizo " + nroReserva + " reservas");
                EntradaSalida.escribir("");
                for (Reserva reserva : sistD.getReservas()) {
                    if (reserva.getCliente().equals(cliente)) {
                        EntradaSalida.escribir("TIPO DE SERVICIO: " + reserva.getTipoServicio());
                        EntradaSalida.escribir("MENU SELECCIONADO: " + reserva.getMenu());
                        EntradaSalida.escribir("FECHA Y HORA DE INICIO: " + reserva.getFechaHoraInicio());
                        EntradaSalida.escribir("FECHA Y HORA DE FIN: " + reserva.getFechaHoraFin());
                        EntradaSalida.escribir("RESTRICCIONES DIETETICAS: " + reserva.getRestriccionesDiet());
                        EntradaSalida.escribir("PREFERENCIAS: " + reserva.getPreferenciasCliente());
                        EntradaSalida.escribir("");
                    }
                }
                EntradaSalida.escribir("\t\t-Las reservas estan ordenadas de forma ascendente-");
                EntradaSalida.escribir("");
                posReserva = EntradaSalida.leerEntero("-Cual desea modificar?-\n"
                        + "Ingrese un nro");
            }

            do {
                op = EntradaSalida.leerEntero("-Que desea modificar?-\n"
                        + "<1>Tipo reserva\n"
                        + "<2>Tipo de servicio y menu\n"
                        + "<3>Fecha y hora de incio\n"
                        + "<4>Restricciones dieteticas\n"
                        + "<5>Preferencias\n"
                        + "<6>Estado de la entrega");
                switch (op) {
                    case 1:
                        String tipoReserva = "";
                        int op2;
                        do {
                            op2 = EntradaSalida.leerEntero("\t\t-Que tipo de reserva es?-\n"
                                    + "<1>Presencial\n"
                                    + "<2>Por telefono\n"
                                    + "<3>Por mail");
                            switch (op2) {
                                case 1:
                                    tipoReserva = "Presencial";
                                    break;
                                case 2:
                                    tipoReserva = "Por telefono";
                                    break;
                                case 3:
                                    tipoReserva = "Por mail";
                                    break;
                            }
                        } while (op2 < 1 || op2 > 3);

                        reservasCliente.get(posReserva - 1).setTipoReserva(tipoReserva);
                        EntradaSalida.escribir("\t\t-TIPO RESERVA ACTUALIZADA CON EXITO-");
                        break;
                    case 2:
                        do {
                            tipoS = EntradaSalida.leer("-Ingrese el tipo de servicio-\n"
                                    + "<1>Finger Food\n"
                                    + "<2>Pizza Party\n"
                                    + "<3>Lunch");
                        } while (Integer.parseInt(tipoS) < 1 || Integer.parseInt(tipoS) > 3);

                        if ((Integer.parseInt(tipoS) == 2)) {
                            do {
                                tipoMenu = EntradaSalida.leer("-Elija el menu-\n"
                                        + "<1>Menu 1\n"
                                        + "<2>Menu 2\n"
                                        + "<3>Menu 3\n"
                                        + "<4>Menu 4");
                            } while ((Integer.parseInt(tipoMenu)) < 1 || (Integer.parseInt(tipoMenu)) > 4);
                        } else if ((Integer.parseInt(tipoS) == 3)) {
                            do {
                                tipoMenu = EntradaSalida.leer("-Elija un tipo de lunch-\n"
                                        + "<1>Lunch Tradicional\n"
                                        + "<2>Lunch gourmet\n"
                                        + "<3>Lunch express\n"
                                        + "<4>Menu pernil de cerdo");
                                switch (Integer.parseInt(tipoMenu)) {
                                    case 1:
                                        do {
                                            subTipoMenu = EntradaSalida.leer("-Elija el menu-\n"
                                                    + "<1>Menu para 10 personas\n"
                                                    + "<2>Menu para 20 personas\n"
                                                    + "<3>Menu para 40 personas\n"
                                                    + "<4>Menu para 50 personas");
                                        } while ((Integer.parseInt(subTipoMenu)) < 1 || (Integer.parseInt(subTipoMenu)) > 4);
                                        break;
                                    case 2:
                                        do {
                                            subTipoMenu = EntradaSalida.leer("-Elija el menu-\n"
                                                    + "<1>Menu para 10 personas\n"
                                                    + "<2>Menu para 20 personas\n"
                                                    + "<3>Menu para 30 personas\n"
                                                    + "<4>Menu para 40 personas\n"
                                                    + "<5>Menu para 50 personas");
                                        } while ((Integer.parseInt(subTipoMenu)) < 1 || (Integer.parseInt(subTipoMenu)) > 5);
                                        break;
                                    case 3:
                                        do {
                                            subTipoMenu = EntradaSalida.leer("-Elija el menu-\n"
                                                    + "<1>Menu para 5 personas\n"
                                                    + "<2>Menu para 10 personas\n"
                                                    + "<3>Menu para 20 personas\n"
                                                    + "<4>Menu para 30 personas");
                                        } while ((Integer.parseInt(subTipoMenu)) < 1 || (Integer.parseInt(subTipoMenu)) > 4);
                                        break;
                                    case 4:
                                        do {
                                            subTipoMenu = EntradaSalida.leer("-Elija el menu-\n"
                                                    + "<1>Menu para 20 personas\n"
                                                    + "<2>Menu para 30 personas\n"
                                                    + "<3>Menu para 40 personas\n"
                                                    + "<4>Menu para 50 personas");
                                        } while ((Integer.parseInt(subTipoMenu)) < 1 || (Integer.parseInt(subTipoMenu)) > 4);
                                        break;
                                }
                            } while ((Integer.parseInt(tipoMenu)) < 1 || (Integer.parseInt(tipoMenu)) > 4);
                        } else {
                            tipoS = "1";
                            tipoMenu = "1";
                        }
                        tipoMenu = tipoMenu + "-" + subTipoMenu;

                        reservasCliente.get(posReserva - 1).setTipoServicio(tipoS);
                        reservasCliente.get(posReserva - 1).setMenu(tipoMenu);
                        EntradaSalida.escribir("\t\t-TIPO DE SERVICIO Y MENU ACTUALIZADO CON EXITO-");
                        break;
                    case 3:
                        String fechaI = EntradaSalida.leer("Ingrese la fecha de inicio en el siguiente formato --> DD/MM/AA:");
                        String horaI = EntradaSalida.leer("Ingrese la hora de inicio en el siguiente formato --> Hs:min:");

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
                        LocalDateTime inicioEvento = LocalDateTime.parse(fechaI + " " + horaI, formatter);

                        int duracionHoras = EntradaSalida.leerEntero("Ingrese la duracion del evento en horas(Hs):");
                        LocalDateTime finEvento = inicioEvento.plusHours(duracionHoras);

                        String fechaHoraI = inicioEvento.format(formatter);
                        String fechaHoraF = finEvento.format(formatter);

                        reservasCliente.get(posReserva - 1).setFechaHoraInicio(fechaHoraI);
                        reservasCliente.get(posReserva - 1).setFechaHoraFin(fechaHoraF);
                        EntradaSalida.escribir("\t\t-FECHA Y HORA ACTUALIZADA CON EXITO-");
                        break;
                    case 4:
                        reservasCliente.get(posReserva - 1).setRestriccionesDiet(EntradaSalida.leer("Ingrese la(s)"
                                + "nueva(s) restricciones dieteticas: "));
                        EntradaSalida.escribir("\t\t-RESTRICIONES DIETETICAS ACTUALIZADAS CON EXITO-");
                        break;
                    case 5:
                        reservasCliente.get(posReserva - 1).setPreferenciasCliente(EntradaSalida.leer("Ingrese la(s)"
                                + "nueva(s) preferencia: "));
                        EntradaSalida.escribir("\t\t-PREFERENCIA ACTUALIZADA CON EXITO-");
                        break;
                    case 6:
                        boolean entregado = EntradaSalida.leerBoolean("Se entrego el servicio?");
                        reservasCliente.get(posReserva - 1).setEntregado(entregado);
                        EntradaSalida.escribir("\t\t-ESTADO DE LA ENTREGA ACTUALIZADO-");
                        break;
                }
            } while (op < 1 || op > 6);
        }
    }
}
