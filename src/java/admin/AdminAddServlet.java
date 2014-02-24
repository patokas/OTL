/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Helpers.StringMD;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
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
@WebServlet(name = "AdminAddServlet", urlPatterns = {"/AdminAddServlet"})
public class AdminAddServlet extends HttpServlet {

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
            /////////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////////

            conexion = ds.getConnection();

            AdminDAO adminDAO = new AdminDAO();
            adminDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            //////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String user = (String) session.getAttribute("username");

                /* comprobar permisos de usuario */
                if (access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {
                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", user); // username
                    request.setAttribute("access", access); // nivel de acceso

                    ////////////////////////////////////////
                    // DECLARAR VARIABLES DE INSTANCIA
                    ///////////////////////////////////////

                    Admin admin = new Admin();

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        //////////////////////////////////////// 

                        String btnAdd = request.getParameter("add");
                        String username = request.getParameter("username");
                        String email = request.getParameter("email");
                        String type = request.getParameter("type_admin");
                        String pwd1 = request.getParameter("pwd1");
                        String pwd2 = request.getParameter("pwd2");

                        boolean error = false;

                        /* comprobar boton agregar */
                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese un nuevo Administrador.");
                        } else {
                            /* comprobar username */
                            if (username == null || username.trim().equals("")) {
                                request.setAttribute("msgErrorUsername", "Error al recibir username.");
                                error = true;
                            } else {
                                /* comprobar username duplicado */
                                admin.setUsername(username);
                                Collection<Admin> list = adminDAO.findByUsername(admin.getUsername());
                                for (Admin adm : list) {
                                    if (adm.getIdAdmin() > 0) {
                                        request.setAttribute("msgErrorUsername", "Error: ya existe un administrador con ese username. ");
                                        error = true;
                                    }
                                }
                            }
                            /* comprobar email */
                            if (email == null || email.trim().equals("")) {
                                request.setAttribute("msgErrorEmail", "Error al recibir email.");
                                error = true;
                            } else {
                                /* comprobar email duplicado */
                                admin.setEmail(email);
                                Collection<Admin> list = adminDAO.findByEmail(admin.getEmail());
                                for (Admin adm : list) {
                                    if (adm.getIdAdmin() > 0) {
                                        request.setAttribute("msgErrorEmail", "Error: ya existe un administrador con ese email. ");
                                        error = true;
                                    }
                                }
                            }

                            /* comprobar type */
                            if (type == null || type.trim().equals("")) {
                                request.setAttribute("msgErrorType", "Error al recibir tipo.");
                                error = true;
                            } else {
                                try {
                                    admin.setTypeAdmin(Integer.parseInt(type));
                                } catch (NumberFormatException n) {
                                    request.setAttribute("msgErrorType", "Error: El tipo deber ser numérico.");
                                    error = true;
                                }
                            }
                            /* comprobar pwd1 */
                            if (pwd1 == null || pwd1.trim().equals("")) {
                                request.setAttribute("msgErrorPwd1", "Error al recibir password.");
                                error = true;
                            } else {
                                admin.setPwd1(pwd1);
                                /* comprobar pwd2 */
                                if (pwd2 == null || pwd2.trim().equals("")) {
                                    request.setAttribute("msgErrorPwd1", "Error al recibir password.");
                                    error = true;
                                } else {
                                    admin.setPwd2(pwd2);
                                    /* comprobar coincidencias */
                                    if (!pwd1.equals(pwd2)) {
                                        request.setAttribute("msgErrorPwd1", "Error: Las password's no coinciden.");
                                        error = true;
                                    }
                                    /* comprobar largo de caracteres */
                                    if (pwd1.length() < 6 || pwd2.length() < 6) {
                                        request.setAttribute("msgErrorPwd2", "Error: La password debe poseer al menos 6 caracteres.");
                                        error = true;
                                    }
                                    /*encriptar password en hash MD5 */
                                    if (!error) {
                                        admin.setPassword(StringMD.getStringMessageDigest(pwd1, StringMD.MD5));
                                    }
                                }
                            }
                            /////////////////////////////////////////
                            // INSERTAR REGISTRO
                            ////////////////////////////////////////
                            if (!error) {
                                try {
                                    adminDAO.insert(admin);
                                    request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                } catch (Exception duplicateException) {
                                    request.setAttribute("msgErrorDup", "Error: ya existe este registro. ");
                                }
                            }
                        }
                    } catch (Exception parameterException) {
                    } finally {
                        /* despachar a la vista adminAdd */
                        request.setAttribute("admin", admin);
                        request.getRequestDispatcher("/admin/adminAdd.jsp").forward(request, response);
                    }
                }
            } catch (Exception sesionException) {
                /* enviar a la vista de login */
                System.err.println("no ha iniciado session");
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
            }
        } catch (Exception connectionException) {
            connectionException.printStackTrace();
        } finally {
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
