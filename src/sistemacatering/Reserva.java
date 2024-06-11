package sistemacatering;

import java.io.Serializable;

public class Reserva implements Serializable {

    private Cliente cliente;
    private String fechaHoraInicio;
    private String fechaHoraFin;
    private String detallesMenuPlatos;
    private String restriccionesDiet;
    private String preferenciasCliente;
    private String tipoServicio;
    private String menu;
    private String tipoReserva;
    private float precioServicio;
    private boolean entregado;

    public Reserva(Cliente cliente, String tipoReserva, String tipoServicio, String menu, String detallesMenuPlatos,float precioServicio,
            String fechaHoraInicio, String fechaHoraFin, String restriccionesDiet,
            String preferenciasCliente, boolean entregado) {
        this.cliente = cliente;
        this.tipoServicio = tipoServicio;
        this.menu = menu;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.restriccionesDiet = restriccionesDiet;
        this.preferenciasCliente = preferenciasCliente;
        this.tipoReserva = tipoReserva;
        this.entregado = entregado;
        this.detallesMenuPlatos = detallesMenuPlatos;
        this.precioServicio = precioServicio;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(String fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getDetallesMenuPlatos() {
        return detallesMenuPlatos;
    }

    public void setDetallesMenuPlatos(String detallesMenuPlatos) {
        this.detallesMenuPlatos = detallesMenuPlatos;
    }

    public String getRestriccionesDiet() {
        return restriccionesDiet;
    }

    public void setRestriccionesDiet(String restriccionesDiet) {
        this.restriccionesDiet = restriccionesDiet;
    }

    public String getPreferenciasCliente() {
        return preferenciasCliente;
    }

    public void setPreferenciasCliente(String preferenciasCliente) {
        this.preferenciasCliente = preferenciasCliente;
    }

    public float getPrecioServicio() {
        return precioServicio;
    }

    public void setPrecioServicio(float precioServicio) {
        this.precioServicio = precioServicio;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipoReserva() {
        return tipoReserva;
    }

    public void setTipoReserva(String tipoReserva) {
        this.tipoReserva = tipoReserva;
    }
    
}
