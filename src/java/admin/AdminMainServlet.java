/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

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
@WebServlet(name = "AdminMainServlet", urlPatterns = {"/AdminMainServlet"})
public class AdminMainServlet extends HttpServlet {

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
                String sidUser = (String) session.getAttribute("idUser");
                int idUser = Integer.parseInt(sidUser);

                /* comprobar permisos de usuario */
                if (access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {
                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", user);
                    request.setAttribute("access", access);
                    request.setAttribute("idUser", idUser);

                    try {
                        //////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        //////////////////////////////////////////                      

                        String btnDelRow = request.getParameter("btnDelRow");
                        String btnDelCol = request.getParameter("btnDelCol");

                        //////////////////////////////////////////
                        // ELIMINAR POR REGISTRO
                        //////////////////////////////////////////
                        if (btnDelRow != null) {
                            /* recibir parametros */
                            int id = Integer.parseInt(request.getParameter("id"));

                            /* comprobar auto eliminacion */
                            if (id != idUser) {
                                adminDAO.delete(id);
                                request.setAttribute("msgDel", "Un Administrador ha sido eliminado.");
                            } else {
                                request.setAttribute("msgErrorNoDel", "Error: El administrador no puede eliminarse a sí mismo.");
                            }
                        }

                        //////////////////////////////////////////
                        // ELIMINAR VARIOS REGISTROS
                        //////////////////////////////////////////
                        if (btnDelCol != null) {
                            try {
                                /* recibir parametros */
                                String[] outerArray = request.getParameterValues("chk");
                                int i = 0;
                                while (outerArray[i] != null) {
                                    adminDAO.delete(Integer.parseInt(outerArray[i]));
                                    i++;
                                    if (i == 1) {
                                        request.setAttribute("msgDel", "Un registro ha sido eliminado.");
                                    } else if (i > 1) {
                                        request.setAttribute("msgDel", i + " registros han sido eliminados.");
                                    }
                                }
                            } catch (Exception ex) {
                            }
                        }

                        //////////////////////////////////////////
                        // OBTENER TOTAL DE REGISTROS
                        //////////////////////////////////////////
                        Collection< Admin> list = adminDAO.getAll();

                        if (list.size() == 1) {
                            request.setAttribute("msg", "1 registro encontrado en la base de datos.");
                        } else if (list.size() > 1) {
                            request.setAttribute("msg", list.size() + " registros encontrados en la base de datos.");
                        } else if (list.isEmpty()) {
                            request.setAttribute("msg", "No hay registros encontrado en la base de datos.");
                        }
                        request.setAttribute("list", list);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/admin/admin.jsp").forward(request, response);
                    }
                }
            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                System.out.println("no ha iniciado session");
                /*enviar al login*/
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
