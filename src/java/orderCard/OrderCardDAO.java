/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orderCard;

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
                /* instanciar registro de orderCard */
                OrderCard reg = new OrderCard();
                /* establecer valores */
                reg.setIdOrder(result.getInt("id_order"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setTypeCard(result.getInt("type_card"));
                reg.setRequest(result.getInt("request"));
                reg.setOrderDate(result.getString("order_date"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
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

                reg.setIdOrder(result.getInt("id_order"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setTypeCard(result.getInt("type_card"));
                reg.setRequest(result.getInt("request"));
                reg.setOrderDate(result.getString("order_date"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
            }

        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
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
            String sql = "insert into order_card (rut, dv, type_card, request) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getRut());
            sentence.setString(2, reg.getDv());
            sentence.setInt(3, reg.getTypeCard());
            sentence.setInt(4, reg.getRequest());            

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.add: " + reg, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(OrderCard reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "update order_card set type_card = ?, request = ? where id_order = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getTypeCard());
            sentence.setInt(2, reg.getRequest());
            sentence.setInt(3, reg.getIdOrder());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("RegDAO.update: " + reg, ex);
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

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.eliminar: " + id, ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
