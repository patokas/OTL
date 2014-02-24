/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package card;

/**
 *
 * @author alexander
 */
public class Card implements java.io.Serializable {

    /* atributos */
    public int barCode;
    public int rut;
    public String dv;
    public int cardType;
    public String dateBeginCard;
    public String dateEndCard;
    public String firstName;
    public String lastName;
    /* constantes */
    public int basic = 4;
    public int silver = 8;
    public int gold = 12;

    public Card() {
    }

    public int getBasic() {
        return basic;
    }

    public int getSilver() {
        return silver;
    }

    public int getGold() {
        return gold;
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

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
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

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getDateBeginCard() {
        return dateBeginCard;
    }

    public void setDateBeginCard(String dateBeginCard) {
        this.dateBeginCard = dateBeginCard;
    }

    public String getDateEndCard() {
        return dateEndCard;
    }

    public void setDateEndCard(String dateEndCard) {
        this.dateEndCard = dateEndCard;
    }
}
