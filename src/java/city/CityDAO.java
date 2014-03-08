/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package city;

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
public class CityDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<City> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<City> list = new ArrayList<City>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from city";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                City reg = new City();
                /* obtener resultset */
                reg.setIdCity(result.getInt("id_city"));
                reg.setNameCity(result.getString("name_city"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CityDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CityDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CityDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CityDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en CityDAO, CityDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CityDAO, getAll() : " + ex);
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
        return list;
    }

    public boolean validateDuplicateName(City reg) {

        Statement sentence = null;
        ResultSet result = null;

        boolean find = false;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from city where id_city <> " + reg.getIdCity() + " and name_city = '" + reg.getNameCity() + "'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                find = true;
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CityDAO, validateDuplicateName() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CityDAO, validateDuplicateName() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CityDAO, validateDuplicateName() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CityDAO, validateDuplicateName() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoDAO, CityDAO, validateDuplicateName() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CityDAO, validateDuplicateName() : " + ex);
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
        return find;
    }

    public City findbyIdCity(City city) {

        Statement sentence = null;
        ResultSet result = null;

        City reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from city where id_city = " + city.getIdCity() + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                reg = new City();
                /* obtener resultset */
                reg.setIdCity(result.getInt("id_city"));
                reg.setNameCity(result.getString("name_city"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CityDAO, findbyIdCity() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CityDAO, findbyIdCity() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CityDAO, findbyIdCity() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CityDAO, findbyIdCity() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en CityDAO, findbyIdCity() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CityDAO, findbyIdCity() : " + ex);
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

    public void insert(City city) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into city (name_city) values (?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, city.getNameCity());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CityDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CityDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CityDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CityDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoDAO, CityDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CityDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(City city) {

        PreparedStatement sentence = null;

        try {
            String sql = "update city set name_city = ? where id_city = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, city.getNameCity());
            sentence.setInt(2, city.getIdCity());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CityDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CityDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CityDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CityDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoDAO, CityDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CityDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void delete(int id) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from city where id_city = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CityDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CityDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CityDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CityDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoDAO, CityDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CityDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
