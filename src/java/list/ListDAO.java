/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author patricio alberto
 */
public class ListDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<List> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<List> list = new ArrayList<List>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from list l, user_card uc, place p, event e, card c where l.rut = uc.rut and l.rut = c.rut and l.id_place = p.id_place and l.id_place = e.id_place and l.id_event = e.id_event group by l.id_place, l.id_event, l.rut order by l.create_time desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                List reg = new List();
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdEvent(result.getInt("id_event"));
                reg.setNameEvent(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setBarCode(result.getInt("bar_code"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setDateBegin(result.getString("date_begin_card"));
                reg.setDateEnd(result.getString("date_end_card"));
                reg.setCreateTime(result.getString("create_time"));
                list.add(reg);
            }

            //5 let free resources        
        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
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

    public void insert(List reg) {
        PreparedStatement sentence = null;

        try {
            String sql = "insert into list (id_event, id_place, rut, dv, bar_code) values (?, ?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdEvent());
            sentence.setInt(2, reg.getIdPlace());
            sentence.setInt(3, reg.getRut());
            sentence.setString(4, reg.getDv());
            sentence.setInt(5, reg.getBarCode());

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

    public void delete(List reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from list where id_place = ? and id_event = ? and rut = ? and bar_code = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdPlace());
            sentence.setInt(2, reg.getIdEvent());
            sentence.setInt(3, reg.getRut());
            sentence.setInt(4, reg.getBarCode());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("DAO.eliminar: " + reg, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
