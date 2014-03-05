/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import userCard.UserCard;

/**
 *
 * @author patricio alberto
 */
public class EventDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Event> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Event> list = new ArrayList<Event>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from event e, place p, dress_code dc where e.id_dress_code = dc.id_dress_code and e.id_place = p.id_place order by e.id_event desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Event reg = new Event();

                reg.setIdPlace(result.getInt("id_place"));
                reg.setIdEvent(result.getInt("id_event"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setUrlImage(result.getString("url_image"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));
                reg.setNameDressCode(result.getString("name_dress_code"));
                reg.setIdDressCode(result.getInt("id_dress_code"));

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

    public Collection<Event> findByTittle(Event event) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Event> list = new ArrayList<Event>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from event e, place pl where tittle like '" + event.getTittle() + "%' and e.id_place = pl.id_place ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Event reg = new Event();
                reg.setIdEvent(result.getInt("id_event"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));

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

    public void insert(Event event) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into event (id_place, tittle, details, date_begin, date_end, url_image, points, request, id_dress_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, event.getIdPlace());
            sentence.setString(2, event.getTittle());
            sentence.setString(3, event.getDetails());
            sentence.setString(4, event.getDateBegin());
            sentence.setString(5, event.getDateEnd());
            sentence.setString(6, event.getUrlImage());
            sentence.setInt(7, event.getPoints());
            sentence.setInt(8, event.getRequest());
            sentence.setInt(9, event.getIdDressCode());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.add: " + event, ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }

    }

    public void update(Event event) {

        PreparedStatement sentence = null;

        try {
            String sql = "update event set tittle = ?, details = ?, date_begin = ?, date_end = ?, url_image = ?, points = ?, request = ?, id_dress_code = ? where id_place = ? and id_event = ? ";
            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, event.getTittle());
            sentence.setString(2, event.getDetails());
            sentence.setString(3, event.getDateBegin());
            sentence.setString(4, event.getDateEnd());
            sentence.setString(5, event.getUrlImage());
            sentence.setInt(6, event.getPoints());
            sentence.setInt(7, event.getRequest());
            sentence.setInt(8, event.getIdDressCode());
            sentence.setInt(9, event.getIdPlace());
            sentence.setInt(10, event.getIdEvent());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("RegDAO.update: " + event, ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void delete(Event event) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from event where id_place = ? and id_event = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, event.getIdPlace());
            sentence.setInt(2, event.getIdEvent());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.eliminar: " + event, ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public Collection<Event> findByPlaceEvent(Event event) {

        Statement sentence = null;
        ResultSet result = null;

        Event reg = new Event();

        Collection<Event> list = new ArrayList<Event>();
        try {
            sentence = conexion.createStatement();
            String sql = "select * from event e, place p where e.id_place = " + event.getIdPlace() + " and e.id_event = " + event.getIdEvent() + " and e.id_place = p.id_place ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg.setIdPlace(result.getInt("id_place"));
                reg.setIdEvent(result.getInt("id_event"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setUrlImage(result.getString("e.url_image"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));

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

    public Collection<Event> findByPlace(Event event) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Event> list = new ArrayList<Event>();
        try {
            sentence = conexion.createStatement();
            String sql = "select * from event e, place p, dress_code dc where e.id_dress_code = dc.id_dress_code and e.id_place = " + event.getIdPlace() + " and e.id_place = p.id_place ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Event reg = new Event();
                reg.setIdPlace(result.getInt("id_place"));
                reg.setIdEvent(result.getInt("id_event"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setUrlImage(result.getString("url_image"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));
                reg.setNameDressCode(result.getString("name_dress_code"));
                reg.setIdDressCode(result.getInt("id_dress_code"));

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

    public Collection<Event> findbyRangeDatePlace(String date1, int idPlace) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Event> list = new ArrayList<Event>();
        try {
            sentence = conexion.createStatement();
            String sql = "select * from event e, place p, dress_code dc where e.id_dress_code = dc.id_dress_code and e.date_begin <= '" + date1 + "' and e.date_end >= '" + date1 + "' and e.id_place = " + idPlace + " and e.id_place = p.id_place ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Event reg = new Event();
                reg.setIdPlace(result.getInt("id_place"));
                reg.setIdEvent(result.getInt("id_event"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setUrlImage(result.getString("url_image"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));
                reg.setNameDressCode(result.getString("name_dress_code"));
                reg.setIdDressCode(result.getInt("id_dress_code"));

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

    public Collection<Event> findByPlaceEvent(int idPlace, int idEvent) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Event> list = new ArrayList<Event>();
        try {
            sentence = conexion.createStatement();
            String sql = "select * from event e, place pl, dress_code dc where e.id_dress_code = dc.id_dress_code and e.id_place = " + idPlace + " and e.id_place = pl.id_place and e.id_event = " + idEvent + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Event reg = new Event();
                reg.setIdPlace(result.getInt("id_place"));
                reg.setIdEvent(result.getInt("id_event"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setUrlImage(result.getString("url_image"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));
                reg.setNameDressCode(result.getString("name_dress_code"));
                reg.setIdDressCode(result.getInt("id_dress_code"));

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
}
