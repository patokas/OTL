package admin;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public Collection<Admin> findById(int id) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<Admin> list = new ArrayList<Admin>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from admin where id_admin = " + id + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Admin reg = new Admin();
                reg.setIdAdmin(result.getInt("id_admin"));
                reg.setUsername(result.getString("username"));
                reg.setEmail(result.getString("email"));
                reg.setTypeAdmin(result.getInt("type_admin"));
                reg.setCreateTime(result.getString("create_time"));

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

    public Collection<Admin> findByUsername(String username) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<Admin> list = new ArrayList<Admin>();

        try {

            sentence = conexion.createStatement();
            String sql = "select * from admin where username like '" + username + "%'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Admin reg = new Admin();
                reg.setIdAdmin(result.getInt("id_admin"));
                reg.setUsername(result.getString("username"));
                reg.setEmail(result.getString("email"));
                reg.setTypeAdmin(result.getInt("type_admin"));
                reg.setCreateTime(result.getString("create_time"));

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

    public Collection<Admin> findByEmail(String email) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<Admin> list = new ArrayList<Admin>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from admin where email like '" + email + "%'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Admin reg = new Admin();
                reg.setIdAdmin(result.getInt("id_admin"));
                reg.setUsername(result.getString("username"));
                reg.setEmail(result.getString("email"));
                reg.setTypeAdmin(result.getInt("type_admin"));
                reg.setCreateTime(result.getString("create_time"));
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

    public Collection<Admin> getAll() {
        Collection<Admin> list = new ArrayList<Admin>();
        Statement sentence = null;
        ResultSet result = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from admin";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Admin reg = new Admin();
                reg.setIdAdmin(result.getInt("id_admin"));
                reg.setUsername(result.getString("username"));
                reg.setEmail(result.getString("email"));
                reg.setTypeAdmin(result.getInt("type_admin"));
                reg.setCreateTime(result.getString("create_time"));

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
            String sql = "delete from admin where id_admin = ? and username <> 'patricio.castro' ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("ADminDAO.eliminar: " + id, ex);
        } finally {
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

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("AdminDAO.add: " + admin, ex);
        } finally {
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

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("AdminDAO.update: " + admin, ex);
        } finally {
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

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("AdminDAO.updatePassword: " + admin, ex);
        } finally {
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
                reg = new Admin();
                reg.setIdAdmin(result.getInt("id_admin"));
                reg.setUsername(result.getString("username"));
                reg.setEmail(result.getString("email"));
                reg.setCreateTime(result.getString("create_time"));
                reg.setTypeAdmin(result.getInt("type_admin"));
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
        return reg;

    }
}
