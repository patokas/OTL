/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dressCode;

/**
 *
 * @author patricio
 */
public class DressCode implements java.io.Serializable {

    public int idDressCode;
    public String nameDressCode;
    public String menDetails;
    public String womenDetails;
    public String urlImage;

    public DressCode() {
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

    public String getMenDetails() {
        return menDetails;
    }

    public void setMenDetails(String menDetails) {
        this.menDetails = menDetails;
    }

    public String getWomenDetails() {
        return womenDetails;
    }

    public void setWomenDetails(String womenDetails) {
        this.womenDetails = womenDetails;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
