/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userCard;

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
                /* instanciar objeto */
                UserCard reg = new UserCard();
                /* obtener resultSet */
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setEmail(result.getString("email"));
                reg.setPassword(result.getString("password"));
                reg.setTelephone(result.getInt("tel"));
                reg.setGenre(result.getInt("genre"));
                /* agregar a la lista */
                list.add(reg);
            }
        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UserCardDAO, FindByRut() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UserCardDAO, FindByRut() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UserCardDAO, FindByRut() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UserCardDAO, FindByRut() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UserCardDAO, FindByRut() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UserCardDAO, FindByRut() : " + ex);
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
                /* definir objeto */
                reg = new UserCard();
                /* obtener resultSet */
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setEmail(result.getString("email"));
                reg.setTelephone(result.getInt("tel"));
                reg.setGenre(result.getInt("genre"));
            }
        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UserCardDAO, FindOneByRut() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UserCardDAO, FindOneByRut() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UserCardDAO, FindOneByRut() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UserCardDAO, FindOneByRut() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UserCardDAO, FindOneByRut() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UserCardDAO, FindOneByRut() : " + ex);
        } finally {
            /* liberar recursos */
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
                /* instanciar objeto */
                UserCard reg = new UserCard();
                /* obtener resultSet */
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setEmail(result.getString("email"));
                reg.setPassword(result.getString("password"));
                reg.setTelephone(result.getInt("tel"));
                reg.setGenre(result.getInt("genre"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UserCardDAO, FindByEmail() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UserCardDAO, FindByEmail() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UserCardDAO, FindByEmail() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UserCardDAO, FindByEmail() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UserCardDAO, FindByEmail() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UserCardDAO, FindByEmail() : " + ex);
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

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UserCardDAO, FindByEmailRepeat() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UserCardDAO, FindByEmailRepeat() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UserCardDAO, FindByEmailRepeat() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UserCardDAO, FindByEmailRepeat() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UserCardDAO, FindByEmailRepeat() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UserCardDAO, FindByEmailRepeat() : " + ex);
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

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UserCardDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UserCardDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UserCardDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UserCardDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UserCardDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UserCardDAO, insert() : " + ex);
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
                /* instanciar objeto */
                UserCard reg = new UserCard();
                /* obtener resultSet */
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setEmail(result.getString("email"));
                reg.setPassword(result.getString("password"));
                reg.setTelephone(result.getInt("tel"));
                reg.setGenre(result.getInt("genre"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UserCardDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UserCardDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UserCardDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UserCardDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UserCardDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UserCardDAO, getAll() : " + ex);
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

    public void delete(int rut) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from user_card where rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, rut);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UserCardDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UserCardDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UserCardDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UserCardDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UserCardDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UserCardDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
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

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UserCardDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UserCardDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UserCardDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UserCardDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UserCardDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UserCardDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
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

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UserCardDAO, updatePassword() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UserCardDAO, updatePassword() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UserCardDAO, updatePassword() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UserCardDAO, updatePassword() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UserCardDAO, updatePassword() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UserCardDAO, updatePassword() : " + ex);
        } finally {
            /* liberar recursos */
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
                /* instanciar objeto */
                UserCard reg = new UserCard();
                /* obtener resultSet */
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setIdCity(result.getInt("id_city"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setEmail(result.getString("email"));
                reg.setPassword(result.getString("password"));
                reg.setTelephone(result.getInt("tel"));
                reg.setGenre(result.getInt("genre"));
                /* agregar a la lista */
                list.add(reg);
            }
        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UserCardDAO, findByGender() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UserCardDAO, findByGender() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UserCardDAO, findByGender() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UserCardDAO, findByGender() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UserCardDAO, findByGender() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UserCardDAO, findByGender() : " + ex);
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

    public Collection<UserCard> findByBirthDay() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
