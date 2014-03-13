/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package news;

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
 * @author alexander
 */
public class NewsDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public News findByNews(News news) {

        Statement sentence = null;
        ResultSet result = null;

        News reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from news where id_news = " + news.getIdNews() + "";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                reg = new News();
                /* obtener resultSet */
                reg.setIdNews(result.getInt("id_news"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setTypeNews(result.getInt("type_news"));
                reg.setUrlImage(result.getString("url_image"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));

            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en NewsDAO, findbyNews() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en NewsDAO, findbyNews() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en NewsDAO, findbyNews() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PromoNews, findbyNews() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en NewsDAO, findbyNews() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en NewsDAO, findbyNews() : " + ex);
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

    public Collection<News> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<News> list = new ArrayList<News>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from news";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                News reg = new News();
                /* obtener resultset */
                reg.setIdNews(result.getInt("id_news"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setTypeNews(result.getInt("type_news"));
                reg.setUrlImage(result.getString("url_image"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en NewsDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en NewsDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en NewsDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en NewsDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en NewsDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en NewsDAO, getAll() : " + ex);
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

    public boolean validateDuplicate(News reg) {

        Statement sentence = null;
        ResultSet result = null;

        boolean find = false;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from news where id_news <> " + reg.getIdNews() + " and tittle = '" + reg.getTittle() + "' and date_end > '" + reg.getDateBegin() + "' ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* obtener resultSet */
                find = true;
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en NewsDAO, validateDuplicate : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en NewsDAO, validateDuplicate : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en NewsDAO, validateDuplicate : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en NewsDAO, validateDuplicate : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en NewsDAO, validateDuplicate : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en NewsDAO, validateDuplicate : " + ex);
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

    public void insert(News reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into news (tittle, details, type_news, url_image, date_begin, date_end) values (?, ?, ?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getTittle());
            sentence.setString(2, reg.getDetails());
            sentence.setInt(3, reg.getTypeNews());
            sentence.setString(4, reg.getUrlImage());
            sentence.setString(5, reg.getDateBegin());
            sentence.setString(6, reg.getDateEnd());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en NewsDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en NewsDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en NewsDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en NewsDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en NewsDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en NewsDAO, insert() : " + ex);
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
            String sql = "delete from news where id_news = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en NewsDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en NewsDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en NewsDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en NewsDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en NewsDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en NewsDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(News reg) {
        PreparedStatement sentence = null;

        try {
            String sql = "update news set tittle = ?, details = ?, type_news = ?, url_image = ?, date_begin = ?, date_end = ? where id_news = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getTittle());
            sentence.setString(2, reg.getDetails());
            sentence.setInt(3, reg.getTypeNews());
            sentence.setString(4, reg.getUrlImage());
            sentence.setString(5, reg.getDateBegin());
            sentence.setString(6, reg.getDateEnd());
            sentence.setInt(7, reg.getIdNews());


            sentence.executeUpdate();

       } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en NewsDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en NewsDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en NewsDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en NewsDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en NewsDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en NewsDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}