package category;

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
public class CategoryDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Category> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Category> list = new ArrayList<Category>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from category";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Category reg = new Category();
                reg.setIdCategory(result.getInt("id_category"));
                reg.setNameCategory(result.getString("name_category"));
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

    public boolean findByName(int id, String name) {

        Statement sentence = null;
        ResultSet result = null;

        boolean find = false;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from category where id_category <> " + id + " and name_category = '" + name + "' ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                find = true;
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
        return find;
    }

    public void insert(Category reg) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into category (name_category) values (?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getNameCategory());

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

    public void update(Category reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "update category set name_category = ? where id_category = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getNameCategory());
            sentence.setInt(2, reg.getIdCategory());

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

    public void delete(int idCategory) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from category where id_category = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, idCategory);

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.eliminar: " + idCategory, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public Category findById(int idCategory) {

        Statement sentence = null;
        ResultSet result = null;

        Category reg = new Category();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from category where id_category = " + idCategory + "";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg.setIdCategory(result.getInt("id_category"));
                reg.setNameCategory(result.getString("name_category"));
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
}
