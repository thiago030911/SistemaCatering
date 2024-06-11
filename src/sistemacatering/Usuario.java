package sistemacatering;

import java.io.Serializable;

public abstract class Usuario implements Serializable{
    private String usuario;
    private String password;
    
    public Usuario(String usuario, String password){
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public abstract boolean inicioSesion(SistemaDatos sistD);
    
    public abstract void mostrarDatosUsuario();
}
