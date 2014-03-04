/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

/**
 *
 * @author patricio alberto
 */
public class Event implements java.io.Serializable {

    public int idEvent;
    public int idPlace;
    public String namePlace;
    public String tittle;
    public String details;
    public String dateBegin;
    public String dateEnd;
    public String urlImage;
    public int points;
    public int request;
    public int idDressCode;
    public String nameDressCode;

    public Event() {
    }

    public int getIdDressCode() {
        return idDressCode;
    }

    public void setIdDressCode(int idDressCode) {
        this.idDressCode = idDressCode;
    }

    public String getNameDressCode() {
        return nameDressCode;
    }

    public void setNameDressCode(String nameDressCode) {
        this.nameDressCode = nameDressCode;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
