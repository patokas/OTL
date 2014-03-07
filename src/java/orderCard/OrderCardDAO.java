/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orderCard;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author patricio
 */
public class OrderCardDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<OrderCard> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<OrderCard> list = new ArrayList<OrderCard>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from order_card oc, user_card uc where oc.rut = uc.rut order by oc.id_order desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objetos */
                OrderCard reg = new OrderCard();
                /* obtener resultset */
                reg.setIdOrder(result.getInt("id_order"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCardType(result.getInt("card_type"));
                reg.setRequest(result.getInt("request"));
                reg.setOrderDate(result.getString("order_date"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en OrderCardDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en OrderCardDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en OrderCardDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en OrderCardDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en OrderCardDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en OrderCardDAO, getAll() : " + ex);
        } finally {
            /* liberar los recursos */
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return list;
    }

    public OrderCard findById(int id) {

        Statement sentence = null;
        ResultSet result = null;

        OrderCard reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from order_card oc, user_card uc where oc.id_order = " + id + " and oc.rut = uc.rut ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* definir objeto */
                reg = new OrderCard();
                /* obtener resultset */
                reg.setIdOrder(result.getInt("id_order"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCardType(result.getInt("card_type"));
                reg.setRequest(result.getInt("request"));
                reg.setOrderDate(result.getString("order_date"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en OrderCardDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en OrderCardDAO, findById() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en OrderCardDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en OrderCardDAO, findById() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en OrderCardDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en OrderCardDAO, findById() : " + ex);
        } finally {
            /* liberar los recursos */
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return reg;
    }

    public void insert(OrderCard reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into order_card (rut, dv, card_type, request) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getRut());
            sentence.setString(2, reg.getDv());
            sentence.setInt(3, reg.getCardType());
            sentence.setInt(4, reg.getRequest());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en OrderCardDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en OrderCardDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en OrderCardDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en OrderCardDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en OrderCardDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en OrderCardDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(OrderCard reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "update order_card set card_type = ?, request = ? where id_order = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getCardType());
            sentence.setInt(2, reg.getRequest());
            sentence.setInt(3, reg.getIdOrder());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en OrderCardDAO,update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en OrderCardDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en OrderCardDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en OrderCardDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en OrderCardDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en OrderCardDAO, update() : " + ex);
        } finally {
            /* liberar los recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void delete(int id) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from order_card where id_order = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en OrderCardDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en OrderCardDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en OrderCardDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en OrderCardDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en OrderCardDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en OrderCardDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
