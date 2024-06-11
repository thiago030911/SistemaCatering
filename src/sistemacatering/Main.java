package sistemacatering;

public class Main {
    public static void main(String[] args) {
        SistemaControl sistReservas = new SistemaControl();
        try{
            sistReservas.iniciar();
        }catch(NullPointerException e){
            EntradaSalida.escribir(e.getMessage());
        }
    }
}
