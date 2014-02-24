/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package city;

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
                City reg = new City();

                reg.setIdCity(result.getInt("id_city"));
                reg.setNameCity(result.getString("name_city"));

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

    public Collection<City> findByName(City city) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<City> list = new ArrayList<City>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from city where name_city like '" + city.getNameCity() + "%'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                City reg = new City();

                reg.setIdCity(result.getInt("id_city"));
                reg.setNameCity(result.getString("name_city"));

                list.add(reg);
            }

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

    public City findbyIdCity(City city) {

        Statement sentence = null;
        ResultSet result = null;

        City reg = new City();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from city where id_city = " + city.getIdCity() + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg.setIdCity(result.getInt("id_city"));
                reg.setNameCity(result.getString("name_city"));
            }

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
        return reg;
    }

    public void insert(City city) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into city (name_city) values (?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, city.getNameCity());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.add: " + city, ex);
        } finally {
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

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("RegDAO.update: " + city, ex);
        } finally {
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

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.eliminar: " + id, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
