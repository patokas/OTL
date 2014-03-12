/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import Helpers.StringMD;
import admin.Admin;
import admin.AdminDAO;
import java.io.IOException;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author patricio alberto
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Resource(name = "jdbc/OTL")
    private DataSource ds;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Connection conexion = null;

        try {
            //////////////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////////////

            conexion = ds.getConnection();

            AdminDAO adminDAO = new AdminDAO();
            adminDAO.setConexion(conexion);

            //////////////////////////////////////////////
            // RECIBIR Y COMPROBAR PARAMETROS
            /////////////////////////////////////////////

            String btnLogin = request.getParameter("btnLogin");

            if (btnLogin == null) {
                /* mostrar login por 1° vez */
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
            } else {
                /* recibir parametros desde formulario login */
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                boolean error = false;

                /* comprobar username */
                if (username == null || username.trim().equals("")) {
                    error = true;
                }

                /* comprobar password */
                if (password == null || password.trim().equals("")) {
                    error = true;
                }


                /* encriptar passwordd */
                String pwdCrypted = StringMD.getStringMessageDigest(password, StringMD.MD5);
              
                /* comprobar si existe */
                try {
                    Admin admin = adminDAO.findByUserPass(username,pwdCrypted);

                    if (admin != null) {
                        System.out.println("iniciar sesion de admin");

                        /* crear session */
                        HttpSession session = request.getSession(true);

                        /* asignar 10000 ms de expiracion ante inactividad */
                        session.setMaxInactiveInterval(10000);

                        /* asignar valores a session */
                        session.setAttribute("username", username);

                        String access = "" + admin.getTypeAdmin();
                        session.setAttribute("access", access);
                        session.setAttribute("idUser", "" + admin.getIdAdmin());

                        request.getRequestDispatcher("/AdminMainServlet").forward(request, response);
                    } else {
                        System.out.println("error username o password");
                        request.setAttribute("msgErrorLogin", "ERROR AL INGRESAR USERNAME O PASSWORD.");
                        request.getRequestDispatcher("/login/login.jsp").forward(request, response);
                    }
                } catch (Exception ex) {
                    request.setAttribute("msgErrorLogin", "ERROR DE CONEXION.");
                    request.getRequestDispatcher("/login/login.jsp").forward(request, response);
                }
            }
        } catch (Exception connectionException) {
            connectionException.printStackTrace();
        } finally {
            /* cerrar conexion */
            try {
                conexion.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
