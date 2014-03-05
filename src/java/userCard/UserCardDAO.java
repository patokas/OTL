/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author patricio Alberto
 */
public class UserCardDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<UserCard> findByRut(int rut) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<UserCard> list = new ArrayList<UserCard>();
        try {
            sentence = conexion.createStatement();
            String sql = "select * from user_card where rut = " + rut + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                UserCard reg = new UserCard();
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setEmail(result.getString("email"));
                reg.setPassword(result.getString("password"));
                reg.setTelephone(result.getInt("tel"));
                reg.setGenre(result.getInt("genre"));
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

    public UserCard findOneByRut(int rut) {

        Statement sentence = null;
        ResultSet result = null;

        UserCard reg = null;
        try {
            sentence = conexion.createStatement();
            String sql = "select * from user_card where rut = " + rut + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg = new UserCard();
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setEmail(result.getString("email"));
                reg.setTelephone(result.getInt("tel"));
                reg.setGenre(result.getInt("genre"));
            }
        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
        }
        return reg;
    }

    public Collection<UserCard> findByEmail(String email) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<UserCard> list = new ArrayList<UserCard>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from user_card where email like '" + email + "%'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                UserCard reg = new UserCard();
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setEmail(result.getString("email"));
                reg.setPassword(result.getString("password"));
                reg.setTelephone(result.getInt("tel"));
                reg.setGenre(result.getInt("genre"));

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

    public Collection<UserCard> findByEmailRepeat(String email) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<UserCard> list = new ArrayList<UserCard>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from user_card where email = '" + email + "' ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                UserCard reg = new UserCard();
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
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

    public void insert(UserCard reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into user_card (rut, dv, first_name, last_name, password, tel, genre, email, id_city) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getRut());
            sentence.setString(2, reg.getDv());
            sentence.setString(3, reg.getFirstName());
            sentence.setString(4, reg.getLastName());
            sentence.setString(5, reg.getPassword());
            sentence.setInt(6, reg.getTelephone());
            sentence.setInt(7, reg.getGenre());
            sentence.setString(8, reg.getEmail());
            sentence.setInt(9, reg.getIdCity());
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

    public Collection<UserCard> getAll() {
        Collection<UserCard> list = new ArrayList<UserCard>();
        Statement sentence = null;
        ResultSet result = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from user_card";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                UserCard reg = new UserCard();
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setEmail(result.getString("email"));
                reg.setPassword(result.getString("password"));
                reg.setTelephone(result.getInt("tel"));
                reg.setGenre(result.getInt("genre"));
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

    public void delete(int rut) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from user_card where rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, rut);

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("UserCardDAO.eliminar: " + rut, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(UserCard user) {

        PreparedStatement sentence = null;

        try {
            String sql = "update user_card set id_city = ?, first_name = ?, last_name = ?, genre = ?, tel = ?, email = ? where rut = ? ";

            sentence = conexion.prepareStatement(sql);
            sentence.setInt(1, user.getIdCity());
            sentence.setString(2, user.getFirstName());
            sentence.setString(3, user.getLastName());
            sentence.setInt(4, user.getGenre());
            sentence.setInt(5, user.getTelephone());
            sentence.setString(6, user.getEmail());
            sentence.setInt(7, user.getRut());
            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("UserCardDAO.update: " + user, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void updatePassword(UserCard user) {

        PreparedStatement sentence = null;

        try {
            String sql = "update user_card set password = ? where rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, user.getPassword());
            sentence.setInt(2, user.getRut());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("UserCardDAO.updatePassword: " + user, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public Collection<UserCard> findByGender(int gender) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<UserCard> list = new ArrayList<UserCard>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from user_card where genre = " + gender + "";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                UserCard reg = new UserCard();
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setEmail(result.getString("email"));
                reg.setPassword(result.getString("password"));
                reg.setTelephone(result.getInt("tel"));
                reg.setGenre(result.getInt("genre"));
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

    public Collection<UserCard> findByBirthDay() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
