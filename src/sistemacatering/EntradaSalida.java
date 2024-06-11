package sistemacatering;

import java.util.Scanner;

public class EntradaSalida {

    private static Scanner input = new Scanner(System.in);

    private EntradaSalida() {
    }

    public static void escribir(Object x) {
        System.out.println(x);
    }

    public static void escribirSinSaltar(Object x) {
        System.out.print(x);
    }

    public static void escribirLineas(int cant) {
        for (int i = 0; i < cant; i++) {
            EntradaSalida.escribirSinSaltar("-");
        }
        EntradaSalida.escribir("");
    }

    public static String leer() {
        return input.nextLine();
    }

    public static String leer(String msj) {
        EntradaSalida.escribir(msj);
        return input.nextLine();
    }

    public static int leerEntero() {
        return Integer.parseInt(EntradaSalida.leer());
    }

    public static int leerEntero(String msj) {
        return Integer.parseInt(EntradaSalida.leer(msj));
    }

    public static double leerDouble() {
        return Double.parseDouble(EntradaSalida.leer());
    }

    public static double leerDouble(String msj) {
        return Double.parseDouble(EntradaSalida.leer(msj));
    }

    public static char leerCaracter() {
        return EntradaSalida.leer().charAt(0);
    }

    public static char leerCaracter(String msj) {
        return EntradaSalida.leer(msj).charAt(0);
    }

    public static boolean leerBoolean(String msj) {
        String r = EntradaSalida.leer(msj);
        boolean salida = true;
        boolean respuestaCorrecta = false;
        while (!respuestaCorrecta) {
            if (r.equals("1")) {
                salida = true;
                respuestaCorrecta = true;
            } else if (r.equals("2")) {
                salida = false;
                respuestaCorrecta = true;
            } else {
                EntradaSalida.escribir("\t\t***ERROR: la opcion ingresada no es valida***");
                EntradaSalida.escribir("");
                r = EntradaSalida.leer(msj);
            }
        }
        return salida;
    }
}
