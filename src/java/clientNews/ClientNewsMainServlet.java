/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientNews;

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
import news.News;


/**
 *
 * @author alexander
 */
@WebServlet(name = "ClientNewsMainServlet", urlPatterns = {"/ClientNewsMainServlet"})
public class ClientNewsMainServlet extends HttpServlet {

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

        /////////////////////////////////////////
        // ESTABLECER CONEXION
        /////////////////////////////////////////
        try {
            conexion = ds.getConnection();

            ClientNewsDAO cnewsDAO = new ClientNewsDAO();
            cnewsDAO.setConexion(conexion);



            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener nivel de acceso acceso */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                /* obtener nombre de usuario */
                String username = (String) session.getAttribute("username");

                /* comprobar permisos de usuario */
                if (access != 555 && access != 777) {
                    /* ACCESO PROHIBIDO */
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {
                    /* ACCESO PERMITIDO */

                    /* asginar nombre de usuario */
                    request.setAttribute("userJsp", username);
                    /* asignar nivel de acceso */
                    request.setAttribute("access", access);

                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETOS
                    /////////////////////////////////////////
                    try {
                        /* obtener parametro para eliminar única fila */
                        String btnDelRow = request.getParameter("btnDelRow");

                        /* obtener parametro para eliminar varias filas */
                        String btnDelCol = request.getParameter("btnDelCol");

                        /* instanciar point */
                        ClientNews cnews = new ClientNews();

                        //////////////////////////////////////////
                        // ELIMINAR POR REGISTRO
                        //////////////////////////////////////////
                        if (btnDelRow != null) {
                            /* recibir parametros */
                            cnews.setIdClientNews(Integer.parseInt(request.getParameter("idClientNews")));
                            try {
                                cnewsDAO.delete(cnews.getIdClientNews());
                                request.setAttribute("msgDel", "Un registro ha sido eliminado.");
                            } catch (Exception referenceException) {
                                referenceException.printStackTrace();
                                request.setAttribute("msgErrorReference", "Error: No puede eliminar el registro, existen referencias asociadas.");
                            }
                        }

                        //////////////////////////////////////////
                        // ELIMINAR VARIOS REGISTOS
                        //////////////////////////////////////////
                        if (btnDelCol != null) {
                            try {
                                /* recibir parametros del array */
                                String[] outerArray = request.getParameterValues("chk");

                                int cont = 0; //contador de registros eliminados
                                int i = 0; //puntero del array

                                while (outerArray[i] != null) {
                                    try {
                                        cnewsDAO.delete(Integer.parseInt(outerArray[i]));
                                        cont++;
                                        if (cont == 1) {
                                            request.setAttribute("msgDel", "Un registro ha sido eliminado.");
                                        } else if (cont > 1) {
                                            request.setAttribute("msgDel", cont + " registros han sido eliminados");
                                        }
                                    } catch (Exception referenceException) {
                                        request.setAttribute("msgErrorReference", "Error: No puede eliminar, existen registros asociados.");
                                    }
                                    i++;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        ////////////////////////////////////////
                        // ASIGNAR REGISTROS A LA VISTA
                        ////////////////////////////////////////

                        /* obtener lista de client noticias */
                        Collection<ClientNews> cnewsList = cnewsDAO.getAll();

                        request.setAttribute("list", cnewsList);
                        /* obtener en numero de registros encontrados */
                        if (cnewsList.size() == 1) {
                            request.setAttribute("msg", "1 registro encontrado en la base de datos.");
                        } else if (cnewsList.size() > 1) {
                            request.setAttribute("msg", cnewsList.size() + " registros encontrados en la base de datos.");
                        } else if (cnewsList.isEmpty()) {
                            request.setAttribute("msg", "No hay registros encontrado en la base de datos.");
                        }

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/clientNews/clientNews.jsp").forward(request, response);
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
