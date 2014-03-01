/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package place;

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
            String sql = "select * from place pl, city ci, category ca, dress_code dc where id_place = " + id + " and pl.id_dress_code = dc.id_dress_code and pl.id_city = ci.id_city and pl.id_category = ca.id_category ";
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
                reg.setIdCategory(result.getInt("id_category"));
                reg.setNameCategory(result.getString("name_category"));
                reg.setIdDressCode(result.getInt("id_dress_code"));
                reg.setTittle(result.getString("tittle"));

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

    public Collection<Place> findByName(String name) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<Place> list = new ArrayList<Place>();

        try {

            sentence = conexion.createStatement();
            String sql = "select * from place pl, city ci where name_place like '" + name + "%' and pl.id_city = ci.id_city ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Place reg = new Place();
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setAddress(result.getString("address"));
                reg.setStatus(result.getInt("status"));
                reg.setContact(result.getInt("contact"));
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

    public Collection<Place> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Place> list = new ArrayList<Place>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from place pl, city ci, category ca, dress_code dc where pl.id_dress_code = dc.id_dress_code and pl.id_city = ci.id_city and pl.id_category = ca.id_category";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Place reg = new Place();
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setAddress(result.getString("address"));
                reg.setStatus(result.getInt("status"));
                reg.setContact(result.getInt("contact"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setNameCity(result.getString("name_city"));
                reg.setIdCategory(result.getInt("id_category"));
                reg.setNameCategory(result.getString("name_category"));
                reg.setIdDressCode(result.getInt("id_dress_code"));
                reg.setTittle(result.getString("tittle"));

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

    public void delete(int id) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from place where id_place = ? ";

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

    public void insert(Place reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into place (name_place, id_city, address, status, contact, description, url_image, id_category, url_logo, id_dress_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getNamePlace());
            sentence.setInt(2, reg.getIdCity());
            sentence.setString(3, reg.getAddress());
            sentence.setInt(4, reg.getStatus());
            sentence.setInt(5, reg.getContact());
            sentence.setString(6, reg.getDescription());
            sentence.setString(7, reg.getUrlImage());
            sentence.setInt(8, reg.getIdCategory());
            sentence.setString(9, reg.getUrlLogo());
            sentence.setInt(10, reg.getIdDressCode());

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

    public void update(Place reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "update place set name_place = ?, address = ?, status = ?, contact = ?, id_city = ?, description = ?, url_image = ?, url_logo = ?, id_category = ?, id_dress_code = ? where id_place = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getNamePlace());
            sentence.setString(2, reg.getAddress());
            sentence.setInt(3, reg.getStatus());
            sentence.setInt(4, reg.getContact());
            sentence.setInt(5, reg.getIdCity());
            sentence.setString(6, reg.getDescription());
            sentence.setString(7, reg.getUrlImage());
            sentence.setString(8, reg.getUrlLogo());
            sentence.setInt(9, reg.getIdCategory());
            sentence.setInt(10, reg.getIdDressCode());
            sentence.setInt(11, reg.getIdPlace());


            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("RegDAO.update: " + reg, ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
