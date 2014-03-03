/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exchangeable;

/**
 *
 * @author patricio alberto
 */
public class Exchangeable implements java.io.Serializable {

    /* atributos */
    public int idPlace;
    public int idExchangeable;
    public String tittle;
    public String namePlace;
    public String urlImage;
    public int points;
    public int request;

    public Exchangeable() {
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public int getIdExchangeable() {
        return idExchangeable;
    }

    public void setIdExchangeable(int idExchangeable) {
        this.idExchangeable = idExchangeable;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
