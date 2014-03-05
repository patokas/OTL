/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package place;

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
 * @author patricio alberto
 */
public class PlaceDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Place> findById(int id) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Place> list = new ArrayList<Place>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from place pl, city ci where id_place = " + id + " and pl.id_city = ci.id_city";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                Place reg = new Place();
                /* obtener resultSet */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setAddress(result.getString("address"));
                reg.setStatus(result.getInt("status"));
                reg.setContact(result.getInt("contact"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setNameCity(result.getString("name_city"));
                reg.setDescription(result.getString("description"));
                reg.setUrlImage(result.getString("url_image"));
                reg.setUrlLogo(result.getString("url_logo"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceDAO, findById() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceDAO, findById() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceDAO, findById(): " + id, ex);
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

    public Collection<Place> findByName(String name) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Place> list = new ArrayList<Place>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from place pl, city ci where name_place like '" + name + "%' and pl.id_city = ci.id_city ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                Place reg = new Place();
                /* obtener resultSet */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setAddress(result.getString("address"));
                reg.setStatus(result.getInt("status"));
                reg.setContact(result.getInt("contact"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setNameCity(result.getString("name_city"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceDAO, findByName() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceDAO, findByName() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceDAO, findByName() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceDAO, findByName() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceDAO, findByName() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceDAO, findByName(): " + name, ex);
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

    public Collection<Place> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Place> list = new ArrayList<Place>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from place pl, city ci where pl.id_city = ci.id_city";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto*/
                Place reg = new Place();
                /* obtener resultSet */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setAddress(result.getString("address"));
                reg.setStatus(result.getInt("status"));
                reg.setContact(result.getInt("contact"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setNameCity(result.getString("name_city"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceDAO, getAll() : " + ex);
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

    public void delete(int id) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from place where id_place = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void insert(Place reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into place (name_place, id_city, address, status, contact, description, url_image, url_logo) values (?, ?, ?, ?, ?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getNamePlace());
            sentence.setInt(2, reg.getIdCity());
            sentence.setString(3, reg.getAddress());
            sentence.setInt(4, reg.getStatus());
            sentence.setInt(5, reg.getContact());
            sentence.setString(6, reg.getDescription());
            sentence.setString(7, reg.getUrlImage());
            sentence.setString(8, reg.getUrlLogo());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceDAO, insert(): " + reg, ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(Place reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "update place set name_place = ?, address = ?, status = ?, contact = ?, id_city = ?, description = ?, url_image = ?, url_logo = ? where id_place = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getNamePlace());
            sentence.setString(2, reg.getAddress());
            sentence.setInt(3, reg.getStatus());
            sentence.setInt(4, reg.getContact());
            sentence.setInt(5, reg.getIdCity());
            sentence.setString(6, reg.getDescription());
            sentence.setString(7, reg.getUrlImage());
            sentence.setString(8, reg.getUrlLogo());
            sentence.setInt(9, reg.getIdPlace());


            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PlaceDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PlaceDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PlaceDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PlaceDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PlaceDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PlaceDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
