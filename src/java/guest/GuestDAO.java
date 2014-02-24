/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guest;

import event.Event;
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
public class GuestDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Guest> getAll(String email) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Guest> list = new ArrayList<Guest>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from guest g, event e, place p, city c, user_card u where u.email = '" + email + "'  and g.rut = u.rut  and g.rut = u.rut and g.id_event = e.id_event and g.id_place = e.id_place and e.id_place = p.id_place and p.id_city = c.id_city order by g.id_place desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Guest reg = new Guest();
                reg.setRut(result.getInt("rut"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdEvent(result.getInt("id_event"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setNameCity(result.getString("name_city"));
                reg.setUrlImage(result.getString("url_image"));
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

    public void insert(UserCard usercard, Event event) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into guest (rut, dv, id_place, id_event) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, usercard.getRut());
            sentence.setString(2, usercard.getDv());
            sentence.setInt(3, event.getIdPlace());
            sentence.setInt(4, event.getIdEvent());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.add: " + event, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    Collection<Guest> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Guest> list = new ArrayList<Guest>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from guest g, event e, place p, user_card u where u.rut = g.rut and g.id_place = e.id_place and g.id_event = e.id_event and e.id_place = p.id_place order by g.id_event desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Guest reg = new Guest();
                reg.setIdPlace(result.getInt("id_place"));
                reg.setIdEvent(result.getInt("id_event"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("e.tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));

                list.add(reg);
            }

            // let free resources        
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

    void insert(Guest guest) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into guest (id_place, id_event, rut, dv) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, guest.getIdPlace());
            sentence.setInt(2, guest.getIdEvent());
            sentence.setInt(3, guest.getRut());
            sentence.setString(4, guest.getDv());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("AdminDAO.add: " + guest, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }

    }

    public Guest findByGuest(Guest guest) {

        Statement sentence = null;
        ResultSet result = null;

        Guest reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from guest g, event e, place p, user_card u where u.rut = g.rut and g.id_place = e.id_place and g.id_event = e.id_event and e.id_place = p.id_place order by g.id_event desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg = new Guest();
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdEvent(result.getInt("id_event"));
                reg.setTittle(result.getString("e.tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
            }

            // let free resources        
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

    public Guest findOneByGuest(Guest guest) {

        Statement sentence = null;
        ResultSet result = null;

        Guest reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from guest where rut = " + guest.getRut() +" and id_place = " + guest.getIdPlace() + " and id_event = " + guest.getIdEvent() + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg = new Guest();
                reg.setIdPlace(result.getInt("id_place"));                
                reg.setIdEvent(result.getInt("id_event"));                
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));                                
            }

            // let free resources        
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

    void delete(Guest guest) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from guest where id_place = ? and id_event = ? and rut = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, guest.getIdPlace());
            sentence.setInt(2, guest.getIdEvent());
            sentence.setInt(3, guest.getRut());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("ADminDAO.eliminar: " + guest, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
