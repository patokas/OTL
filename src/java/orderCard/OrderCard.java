/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orderCard;

/**
 *
 * @author patricio
 */
public class OrderCard implements java.io.Serializable {

    public int idOrder;
    public int rut;
    public String dv;
    public int typeCard;
    public int request;
    public String orderDate;
    public String firstName;
    public String lastName;

    public OrderCard() {
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
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

    public int getTypeCard() {
        return typeCard;
    }

    public void setTypeCard(int typeCard) {
        this.typeCard = typeCard;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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
}
