/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dressCode;

import city.City;
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
public class DressCodeDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<DressCode> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<DressCode> list = new ArrayList<DressCode>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from dress_code";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                DressCode reg = new DressCode();

                /* obtener resultSet */
                reg.setIdDressCode(result.getInt("id_dress_code"));
                reg.setTittle(result.getString("tittle"));
                reg.setMenDetails(result.getString("men_details"));
                reg.setWomenDetails(result.getString("women_details"));
                reg.setUrlImage(result.getString("url_image"));

                /* agregar a la lista */
                list.add(reg);
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
        return list;
    }

    public DressCode findById(int id) {

        Statement sentence = null;
        ResultSet result = null;

        /* declarar objeto */
        DressCode reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from dress_code";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* definir objeto */
                reg = new DressCode();

                /* obtener resultSet */
                reg.setIdDressCode(result.getInt("id_dress_code"));
                reg.setTittle(result.getString("tittle"));
                reg.setMenDetails(result.getString("men_details"));
                reg.setWomenDetails(result.getString("women_details"));
                reg.setUrlImage(result.getString("url_image"));
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

    public boolean findByName(String name) {

        Statement sentence = null;
        ResultSet result = null;

        /* declarar y definir variables */
        boolean find = false;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from dress_code where tittle = '" + name + "'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                find = true;
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
        return find;
    }

    public boolean findByIdName(int id, String name) {

        Statement sentence = null;
        ResultSet result = null;

        /* declarar y definir variables */
        boolean find = false;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from dress_code where tittle = '" + name + "' and id_dress_code <> " + id + "";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                find = true;
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
        return find;
    }

    public void insert(DressCode reg) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into dress_code (tittle, men_details, women_details, url_image) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getTittle());
            sentence.setString(2, reg.getMenDetails());
            sentence.setString(3, reg.getWomenDetails());
            sentence.setString(4, reg.getUrlImage());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.add: " + reg, ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(DressCode reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "update dress_code set tittle = ?, men_details = ?, women_details = ?, url_image = ? where id_dress_code = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getTittle());
            sentence.setString(2, reg.getMenDetails());
            sentence.setString(3, reg.getWomenDetails());
            sentence.setString(4, reg.getUrlImage());

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

    public void delete(int id) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from dress_code where id_dress_code = ?";

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
