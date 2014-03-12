/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientNews;

import Helpers.Format;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author alexander
 */
@WebServlet(name = "ClientNewsGetServlet", urlPatterns = {"/ClientNewsGetServlet"})
public class ClientNewsGetServlet extends HttpServlet {

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
            //////////////////////////////////////////
            // ESTABLECER CONEXION
            //////////////////////////////////////////

            conexion = ds.getConnection();

            ClientNewsDAO cnewsDAO = new ClientNewsDAO();
            cnewsDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String username = (String) session.getAttribute("username");

                /* comprobar permisos de usuario */
                if (access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {

                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", username);
                    request.setAttribute("access", access);
                    request.setAttribute("su", 777); //superuser 


                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        /////////////////////////////////////////

                        String sidClientNews = request.getParameter("idClientNews");

                        boolean error = false;
                        ClientNews cnews = new ClientNews();
                        
                        /* comprobar id client place */
                        if (sidClientNews == null || sidClientNews.trim().equals("")) {
                            request.setAttribute("msgErrorIdClientNews", "Error al recibir id cliente noticias.");
                            error = true;
                        } else {
                            try {
                                cnews.setIdClientNews(Integer.parseInt(sidClientNews));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorIdClientNews", "Error: El id de cliente noticias no es numérico.");
                            }
                        }

                        if (!error) {
                            /* buscar noticia cliente */
                            Collection<ClientNews> list = cnewsDAO.findById(cnews.getIdClientNews());
                            if (list.size() > 0) {
                                for (ClientNews reg : list) {
                                    if (reg.getIdClientNews() > 0) {
                                        reg.setDateBegin(Format.dateYYYYMMDD(reg.getDateBegin()));
                                        reg.setDateEnd(Format.dateYYYYMMDD(reg.getDateEnd()));
                                        request.setAttribute("cnews", reg);
                                        request.setAttribute("msgOk", "Se encontró el registro!");
                                    }
                                }
                            } else {
                                request.setAttribute("msgErrorFound", "Error: No se encontró el registro.");
                            }
                        }


                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/clientNews/clientNewsUpdate.jsp").forward(request, response);
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
