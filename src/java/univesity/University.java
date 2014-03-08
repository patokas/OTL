/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univesity;

/**
 *
 * @author patricio
 */
public class University implements java.io.Serializable {

    public int idUniversity;
    public String nameUniversity;

    public University() {
    }

    public int getIdUniversity() {
        return idUniversity;
    }

    public void setIdUniversity(int idUniversity) {
        this.idUniversity = idUniversity;
    }

    public String getNameUniversity() {
        return nameUniversity;
    }

    public void setNameUniversity(String nameUniversity) {
        this.nameUniversity = nameUniversity;
    }
}
