/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientPromo;

/**
 *
 * @author patricio alberto
 */
public class ClientPromo {

    public int idPlace;
    public String namePlace;
    public int idPromo;
    public String tittlePromo;
    public int rut;
    public String dv;
    public int status;    
    public int points;

    public ClientPromo() {
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getTittlePromo() {
        return tittlePromo;
    }

    public void setTittlePromo(String tittlePromo) {
        this.tittlePromo = tittlePromo;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public int getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(int idPromo) {
        this.idPromo = idPromo;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
