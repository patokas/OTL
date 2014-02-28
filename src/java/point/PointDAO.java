/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package point;

import event.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import list.List;

/**
 *
 * @author patricio alberto
 */
public class PointDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Point> getAll() {
        Statement sentence = null;
        ResultSet result = null;

        Collection<Point> list = new ArrayList<Point>();

        try {
            sentence = conexion.createStatement();

            String sql = "select * from point pt, user_card uc, place pl, city ci where pt.rut = uc.rut and pt.id_place = pl.id_place and pl.id_city = ci.id_city ";

            result = sentence.executeQuery(sql);

            while (result.next()) {
                Point reg = new Point();

                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setNameCity(result.getString("name_city"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setPoints(result.getInt("points"));

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

    public void delete(Point point) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from point where id_place = ? and rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, point.getIdPlace());
            sentence.setInt(2, point.getRut());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.eliminar: " + point, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void insert(Point point) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into point (id_place, rut, dv, points) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, point.getIdPlace());
            sentence.setInt(2, point.getRut());
            sentence.setString(3, point.getDv());
            sentence.setInt(4, point.getPoints());

            sentence.executeUpdate();


        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.add: " + point, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public Point findByPoint(Point point) {

        Statement sentence = null;
        ResultSet result = null;

        Point reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from point pt, place pl where pt.id_place = " + point.getIdPlace() + " and pt.rut = " + point.getRut() + " and pt.id_place = pl.id_place ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg = new Point();

                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setPoints(result.getInt("points"));
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

    public void update(Point point) {

        PreparedStatement sentence = null;

        try {
            String sql = "update point set points = ? where id_place = ? and rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, point.getPoints());
            sentence.setInt(2, point.getIdPlace());
            sentence.setInt(3, point.getRut());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("RegDAO.update: " + point, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void updatePointUp(List reg, int points) {

        PreparedStatement sentence = null;

        try {
            String sql = "update point set points = points + ? where id_place = ? and rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, points);
            sentence.setInt(2, reg.getIdPlace());
            sentence.setInt(3, reg.getRut());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("RegDAO.update: " + reg, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
