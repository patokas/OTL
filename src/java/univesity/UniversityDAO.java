/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univesity;

import city.City;
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
 * @author patricio
 */
public class UniversityDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<University> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<University> list = new ArrayList<University>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from university";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                University reg = new University();
                /* obtener resultset */
                reg.setIdUniversity(result.getInt("id_university"));
                reg.setNameUniversity(result.getString("name_university"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UniversityDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UniversityDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UniversityDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UniversityDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UniversityDAO, CityDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UniversityDAO, getAll() : " + ex);
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

    public University findById(int id) {

        Statement sentence = null;
        ResultSet result = null;

        University reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from university where id_university = " + id + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                reg = new University();
                /* obtener resultset */
                reg.setIdUniversity(result.getInt("id_university"));
                reg.setNameUniversity(result.getString("name_university"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UniversityDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UniversityDAO, findById() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UniversityDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UniversityDAO, findById() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UniversityDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UniversityDAO, findById() : " + ex);
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

    public boolean validateDuplicate(University reg) {

        Statement sentence = null;
        ResultSet result = null;

        boolean find = false;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from university where id_university <> " + reg.getIdUniversity() + " and name_university = '" + reg.getNameUniversity() + "'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                find = true;
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UniversityDAO, validateDuplicate() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UniversityDAO, validateDuplicate() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UniversityDAO, validateDuplicate() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UniversityDAO, validateDuplicate() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UniversityDAO, validateDuplicate() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UniversityDAO, validateDuplicate() : " + ex);
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

    public void insert(University reg) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into university (name_university) values (?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getNameUniversity());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UniversityDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UniversityDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UniversityDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UniversityDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UniversityDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UniversityDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(University reg) {

        PreparedStatement sentence = null;

        try {

            String sql = "update university set name_university = ? where id_university = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getNameUniversity());
            sentence.setInt(2, reg.getIdUniversity());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UniversityDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UniversityDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UniversityDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UniversityDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UniversityDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UniversityDAO, update() : " + ex);
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

            String sql = "delete from university where id_university = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en UniversityDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en UniversityDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en UniversityDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en UniversityDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en UniversityDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en UniversityDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
