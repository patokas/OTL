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
@WebServlet(name = "AdminUpdateServlet", urlPatterns = {"/AdminUpdateServlet"})
public class AdminUpdateServlet extends HttpServlet {

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
            /////////////////////////////////////////
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
                    request.setAttribute("userJsp", user);
                    request.setAttribute("access", access);

                    ///////////////////////////////////////
                    // DECLARAR VARIABLE DE INSTANCIA
                    //////////////////////////////////////

                    Admin admin = new Admin();
                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        //////////////////////////////////////// 
                        
                        String chk = request.getParameter("chk");
                        String sid = request.getParameter("id");
                        String username = request.getParameter("username");
                        String email = request.getParameter("email");
                        String pwd1 = request.getParameter("pwd1");
                        String pwd2 = request.getParameter("pwd2");

                        boolean error = false;

                        /* comprobar id admin */
                        if (sid == null || sid.trim().equals("")) {
                            request.setAttribute("msgErrorId", "Error al recibir id Admin.");
                            error = true;
                        } else {
                            admin.setIdAdmin(Integer.parseInt(sid));
                        }

                        /* comprobar username */
                        if (username == null || username.trim().equals("")) {
                            request.setAttribute("msgErrorUsername", "Error al recibir username.");
                            error = true;
                        } else {
                            admin.setUsername(username);
                            Collection<Admin> list = adminDAO.findByUsername(admin.getUsername());
                            for (Admin adm : list) {
                                if (adm.getIdAdmin() > 0 && adm.getIdAdmin() != admin.getIdAdmin()) {
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
                            admin.setEmail(email);
                            Collection<Admin> list = adminDAO.findByEmail(admin.getEmail());
                            for (Admin adm : list) {
                                if (adm.getIdAdmin() > 0 && adm.getIdAdmin() != admin.getIdAdmin()) {
                                    request.setAttribute("msgErrorEmail", "Error: ya existe un administrador con ese email. ");
                                    error = true;
                                }
                            }
                        }

                        if (chk != null) {
                            /* comprobar pwd1 */
                            if (pwd1 == null || pwd1.trim().equals("")) {
                                request.setAttribute("msgErrorPwd1", "Error al recibir password.");
                            } else {
                                admin.setPwd1(pwd1);
                                /* comprobar pwd2 */
                                if (pwd2 == null || pwd2.trim().equals("")) {
                                    request.setAttribute("msgErrorPwd1", "Error al recibir password.");
                                } else {
                                    admin.setPwd2(pwd2);
                                    /* comprobar coincidencias */
                                    if (!pwd1.equals(pwd2)) {
                                        request.setAttribute("msgErrorPwd1", "Error: Las password's no coinciden");
                                        error = true;
                                    }
                                    if (pwd1.length() < 6 || pwd2.length() < 6) {
                                        request.setAttribute("msgErrorPwd2", "Error: La password debe poseer al menos 6 caracteres");
                                        error = true;
                                    }

                                    if (!error) {
                                        admin.setPassword(StringMD.getStringMessageDigest(pwd1, StringMD.MD5));
                                        /* buscar admin */
                                        Collection<Admin> list = adminDAO.findById(admin.getIdAdmin());
                                        if (list.size() > 0) {
                                            /* actualizar password */
                                            adminDAO.updatePassword(admin);
                                            request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                                        } else {
                                            request.setAttribute("msgErrorId", "Error: No se encontró el administrador o ha sido eliminado mientras se actualizaba.");
                                        }
                                    }
                                }
                            }
                        } else {
                            if (!error) {
                                /* buscar admin */
                                Collection<Admin> list = adminDAO.findById(admin.getIdAdmin());
                                if (list.size() > 0) {
                                    /* no actualizar password */
                                    adminDAO.update(admin);
                                    request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                                } else {
                                    request.setAttribute("msgErrorId", "Error: No se encontró el administrador o ha sido eliminado mientras se actualizaba.");
                                }
                            }
                        }
                    } catch (Exception parameterException) {
                    } finally {
                        request.setAttribute("admin", admin);
                        request.getRequestDispatcher("/admin/adminUpdate.jsp").forward(request, response);
                    }
                }
            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                System.out.println("no ha iniciado session");
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
