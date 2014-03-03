/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package placeCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import point.Point;

/**
 *
 * @author patricio
 */
public class PlaceCategoryDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<PlaceCategory> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<PlaceCategory> list = new ArrayList<PlaceCategory>();

        try {
            sentence = conexion.createStatement();

            String sql = "select * from place_category pc, category ca, place pl, city ci where pc.id_category = ca.id_category and pc.id_place = pl.id_place and pl.id_city = ci.id_city ";

            result = sentence.executeQuery(sql);

            while (result.next()) {
                PlaceCategory reg = new PlaceCategory();

                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdCategory(result.getInt("id_category"));
                reg.setNameCategory(result.getString("name_category"));
                reg.setNameCity(result.getString("name_city"));

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

    public PlaceCategory findById(int idPlace, int idCategory) {

        Statement sentence = null;
        ResultSet result = null;

        PlaceCategory reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from place_category pc, place pl, category ca where pc.id_place = pl.id_place and pc.id_category = ca.id_category and pc.id_place = " + idPlace + " and pc.id_category = " + idCategory + " pl.id_city = ci.id_city";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg = new PlaceCategory();

                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdCategory(result.getInt("id_category"));
                reg.setNameCategory(result.getString("name_category"));
                reg.setNameCity(result.getString("name_city"));
            }

        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
            /* liberar recursos */
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

    public void insert(PlaceCategory reg) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into place_category (id_place, id_category) values (?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdPlace());
            sentence.setInt(2, reg.getIdCategory());

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

    public void delete(PlaceCategory reg) {

        PreparedStatement sentence = null;

        try {

            String sql = "delete from place_category where id_place = ? and id_category = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdPlace());
            sentence.setInt(2, reg.getIdCategory());

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
}
