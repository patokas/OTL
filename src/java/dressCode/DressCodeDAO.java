/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dressCode;

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
                reg.setNameDressCode(result.getString("name_dress_code"));
                reg.setMenDetails(result.getString("men_details"));
                reg.setWomenDetails(result.getString("women_details"));
                reg.setUrlImage(result.getString("url_image"));
                /* agregar a la lista */
                list.add(reg);
            }
        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en DressCodeDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en DressCodeDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en DressCodeDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en DressCodeDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en DressCodeDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en DressCodeDAO, getAll() : " + ex);
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

        DressCode reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from dress_code where id_dress_code = " + id + "";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* definir objeto */
                reg = new DressCode();
                /* obtener resultSet */
                reg.setIdDressCode(result.getInt("id_dress_code"));
                reg.setNameDressCode(result.getString("name_dress_code"));
                reg.setMenDetails(result.getString("men_details"));
                reg.setWomenDetails(result.getString("women_details"));
                reg.setUrlImage(result.getString("url_image"));
            }
        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en DressCodeDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en DressCodeDAO, findById() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en DressCodeDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en DressCodeDAO, findById() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en DressCodeDAO, findById() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en DressCodeDAO, findById() : " + ex);
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
            String sql = "select * from dress_code where name_dress_code = '" + name + "'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                find = true;
            }
        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en DressCodeDAO, findByName() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en DressCodeDAO, findByName() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en DressCodeDAO, findByName() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en DressCodeDAO, findByName() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en DressCodeDAO, findByName() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en DressCodeDAO, findByName() : " + ex);
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

        boolean find = false;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from dress_code where name_dress_code = '" + name + "' and id_dress_code <> " + id + "";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                find = true;
            }
        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en DressCodeDAO, findByIdName() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en DressCodeDAO, findByIdName() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en DressCodeDAO, findByIdName() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en DressCodeDAO, findByIdName() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en DressCodeDAO, findByIdName() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en DressCodeDAO, findByIdName() : " + ex);
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

            String sql = "insert into dress_code (name_dress_code, men_details, women_details, url_image) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getNameDressCode());
            sentence.setString(2, reg.getMenDetails());
            sentence.setString(3, reg.getWomenDetails());
            sentence.setString(4, reg.getUrlImage());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en DressCodeDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en DressCodeDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en DressCodeDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en DressCodeDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en DressCodeDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en DressCodeDAO, insert() : " + ex);
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
            String sql = "update dress_code set name_dress_code = ?, men_details = ?, women_details = ?, url_image = ? where id_dress_code = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getNameDressCode());
            sentence.setString(2, reg.getMenDetails());
            sentence.setString(3, reg.getWomenDetails());
            sentence.setString(4, reg.getUrlImage());
            sentence.setInt(5, reg.getIdDressCode());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en DressCodeDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en DressCodeDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en DressCodeDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en DressCodeDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en DressCodeDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en DressCodeDAO, update() : " + ex);
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

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en DressCodeDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en DressCodeDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en DressCodeDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en DressCodeDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en DressCodeDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en DressCodeDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
