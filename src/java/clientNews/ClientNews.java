/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientNews;

/**
 *
 * @author alexander
 */
public class ClientNews {

    public int idClientNews;
    public int rut;
    public String dv;
    public String firstName;
    public String lastName;
    public String tittle;
    public int typeNews;
    public String dateBegin;
    public String dateEnd;

    public ClientNews() {
    }

    public int getIdClientNews() {
        return idClientNews;
    }

    public void setIdClientNews(int idClientNews) {
        this.idClientNews = idClientNews;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public int getTypeNews() {
        return typeNews;
    }

    public void setTypeNews(int typeNews) {
        this.typeNews = typeNews;
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
