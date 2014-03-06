package admin;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class AdminDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Admin> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Admin> list = new ArrayList<Admin>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from admin";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instaciar objeto */
                Admin reg = new Admin();
                /* obtener resultset */
                reg.setIdAdmin(result.getInt("id_admin"));
                reg.setUsername(result.getString("username"));
                reg.setEmail(result.getString("email"));
                reg.setTypeAdmin(result.getInt("type_admin"));
                reg.setCreateTime(result.getString("create_time"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en AdminDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en AdminDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en AdminDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en AdminDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en AdminDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en AdminDAO, getAll() : " + ex);
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

    public Admin findById(int id) {

        Statement sentence = null;
        ResultSet result = null;

        Admin reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from admin where id_admin = " + id + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                reg = new Admin();
                /* obtener resultset */
                reg.setIdAdmin(result.getInt("id_admin"));
                reg.setUsername(result.getString("username"));
                reg.setEmail(result.getString("email"));
                reg.setTypeAdmin(result.getInt("type_admin"));
                reg.setCreateTime(result.getString("create_time"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en AdminDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en AdminDAO, findById() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en AdminDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en AdminDAO, findById() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en AdminDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en AdminDAO, findById() : " + ex);
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

    public boolean validateDuplicateUsername(Admin reg) {

        Statement sentence = null;
        ResultSet result = null;

        boolean find = false;

        try {

            sentence = conexion.createStatement();
            String sql = "select * from admin where id_admin <> " + reg.getIdAdmin() + " and username = '" + reg.getUsername() + "'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                find = true;
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en AdminDAO, validateDuplicateUsername() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en AdminDAO, validateDuplicateUsername() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en AdminDAO, validateDuplicateUsername() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en AdminDAO, validateDuplicateUsername() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en AdminDAO, validateDuplicateUsername() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en AdminDAO, validateDuplicateUsername() : " + ex);
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

    public boolean validateDuplicateEmail(Admin reg) {

        Statement sentence = null;
        ResultSet result = null;

        boolean find = false;

        try {

            sentence = conexion.createStatement();
            String sql = "select * from admin where id_admin <> " + reg.getIdAdmin() + " and email = '" + reg.getEmail() + "'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                find = true;
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en AdminDAO, validateDuplicateEmail() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en AdminDAO, validateDuplicateEmail() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en AdminDAO, validateDuplicateEmail() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en AdminDAO, validateDuplicateEmail() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en AdminDAO, validateDuplicateEmail() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en AdminDAO, validateDuplicateEmail() : " + ex);
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

    public void delete(int id) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from admin where id_admin = ? and username <> 'patricio.castro' ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en AdminDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en AdminDAO,delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en AdminDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en AdminDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en AdminDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en AdminDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void insert(Admin admin) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into admin (username, email, password, type_admin) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, admin.getUsername());
            sentence.setString(2, admin.getEmail());
            sentence.setString(3, admin.getPassword());
            sentence.setInt(4, admin.getTypeAdmin());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en AdminDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en AdminDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en AdminDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en AdminDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en AdminDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en AdminDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(Admin admin) {

        PreparedStatement sentence = null;

        try {
            String sql = "update admin set username = ?, email = ? where id_admin = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, admin.getUsername());
            sentence.setString(2, admin.getEmail());
            sentence.setInt(3, admin.getIdAdmin());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en AdminDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en AdminDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en AdminDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en AdminDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en AdminDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en AdminDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void updatePassword(Admin admin) {

        PreparedStatement sentence = null;

        try {
            String sql = "update admin set password = ? where id_admin = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, admin.getPassword());
            sentence.setInt(2, admin.getIdAdmin());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en AdminDAO, updatePassword() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en AdminDAO, updatePassword() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en AdminDAO, updatePassword() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en AdminDAO, updatePassword() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en AdminDAO, updatePassword() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en AdminDAO, updatePassword() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public Admin findByUserPass(String username, String pwdCrypted) {

        Statement sentence = null;
        ResultSet result = null;

        Admin reg = null;

        try {

            sentence = conexion.createStatement();
            String sql = "select * from admin where username = '" + username + "' and password = '" + pwdCrypted + "'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                reg = new Admin();
                /* obtener resultset */
                reg.setIdAdmin(result.getInt("id_admin"));
                reg.setUsername(result.getString("username"));
                reg.setEmail(result.getString("email"));
                reg.setCreateTime(result.getString("create_time"));
                reg.setTypeAdmin(result.getInt("type_admin"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en AdminDAO, findByUserPass() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en AdminDAO, findByUserPass() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en AdminDAO, findByUserPass() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en AdminDAO, findByUserPass() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en AdminDAO, findByUserPass() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en AdminDAO, findByUserPass() : " + ex);
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
}
