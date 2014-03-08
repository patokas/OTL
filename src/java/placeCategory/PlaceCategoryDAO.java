/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package placeCategory;

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
                /* instanciar objeto */
                PlaceCategory reg = new PlaceCategory();
                /* obtener resultset */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdCategory(result.getInt("id_category"));
                reg.setNameCategory(result.getString("name_category"));
                reg.setNameCity(result.getString("name_city"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceCategoryDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceCategoryDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceCategoryDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceCategoryDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceCategoryDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceCategoryDAO, getAll() : " + ex);
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
            String sql = "select * from place_category pc, place pl, category ca, city ci where pc.id_place = pl.id_place and pc.id_category = ca.id_category and pc.id_place = " + idPlace + " and pc.id_category = " + idCategory + " and pl.id_city = ci.id_city";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                reg = new PlaceCategory();
                /* obtener resultset */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdCategory(result.getInt("id_category"));
                reg.setNameCategory(result.getString("name_category"));
                reg.setNameCity(result.getString("name_city"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceCategoryDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceCategoryDAO, findById() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceCategoryDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceCategoryDAO, findById() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceCategoryDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceCategoryDAO, findById() : " + ex);
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

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceCategoryDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceCategoryDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceCategoryDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceCategoryDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceCategoryDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceCategoryDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
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

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceCategoryDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceCategoryDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceCategoryDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceCategoryDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceCategoryDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceCategoryDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
