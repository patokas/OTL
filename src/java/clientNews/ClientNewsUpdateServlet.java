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
@WebServlet(name = "ClientNewsUpdateServlet", urlPatterns = {"/ClientNewsUpdateServlet"})
public class ClientNewsUpdateServlet extends HttpServlet {

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

            ClientNewsDAO cnewsDAO = new ClientNewsDAO();
            cnewsDAO.setConexion(conexion);

            /////////////////////////////////////////
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
                }

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", username);
                request.setAttribute("access", access);

                try {
                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    /////////////////////////////////////////

                    String sidClientNews = request.getParameter("idClientNews");
                    String stittle = request.getParameter("tittle");
                    String stypeNews = request.getParameter("typeNews");
                    String srut = request.getParameter("rut");
                    String sdv = request.getParameter("dv");
                    String sfirstName = request.getParameter("firstName");
                    String slastName = request.getParameter("lastName");
                    String sdateBegin = request.getParameter("dateBegin");
                    String sdateEnd = request.getParameter("dateEnd");

                    ClientNews cnews = new ClientNews();

                    boolean error = false;

                    /* comprobar id news */
                    if (sidClientNews == null || sidClientNews.trim().equals("")) {
                        request.setAttribute("msgErrorIdClientNews", "Error al recibir id cliente noticicas. ");
                        error = true;
                    } else {
                        try {
                            cnews.setIdClientNews(Integer.parseInt(sidClientNews));
                        } catch (NumberFormatException n) {
                            request.setAttribute("msgErrorIdClientNews", "Error al recibir id cliente noticias. ");
                            error = true;
                        }
                    }

                    /* comprobar tittle */
                    if (stittle == null || stittle.trim().equals("")) {
                        request.setAttribute("msgErrorTittle", "Error: Debe ingresar un titulo para la noticia. ");
                        error = true;
                    } else {
                        cnews.setTittle(stittle);
                    }

                    /* comprobar type news */
                    if (stypeNews == null || stypeNews.trim().equals("")) {
                        request.setAttribute("msgErrorTypeNews", "Error al recibir tipo de noticia.");
                        error = true;
                    } else {
                        try {
                            cnews.setTypeNews(Integer.parseInt(stypeNews));
                        } catch (NumberFormatException n) {
                            request.setAttribute("msgErrorTypeNews", "Error: Debe recibir un valor numérico en tipo de noticias.");
                            error = true;
                        }
                    }

                   /* comprobar rut */
                        if (srut == null || srut.trim().equals("") || srut.length() < 2) {
                            request.setAttribute("msgErrorRut", "Error al recibir RUT.");
                            error = true;
                        } else {
                            try {
                                cnews.setRut(Integer.parseInt(srut));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorRut", "Error: RUT inválido.");
                                error = true;
                            }
                        }
                        
                        /* comprobar dv */
                        if(sdv == null || sdv.trim().equals("")) {
                            request.setAttribute("msgErrorRut", "Error al recibir RUT.");
                            error = true;
                        } else {
                            cnews.setDv(sdv);
                        }
                        
                        /* comprobar firstname */
                    if(sfirstName == null || sfirstName.trim().equals("")) {
                        request.setAttribute("msgErrorFirstName", "Error al recibir nombre.");
                        error = true;
                    } else {
                        cnews.setFirstName(sfirstName);
                    }
                    
                    /* comprobar lastname */
                    if(slastName == null || slastName.trim().equals("")) {
                        request.setAttribute("msgErrorLastName", "Error al recibir apellido.");
                        error = true;
                    } else {
                        cnews.setLastName(slastName);
                    }
                        

                    /* comprobar date begin */
                    if (sdateBegin == null || sdateBegin.trim().equals("")) {
                        request.setAttribute("msgErrorDate", "Error al recibir fecha de inicio.");
                        error = true;
                    } else {
                        cnews.setDateBegin(sdateBegin);
                        /* comprobar date end */
                        if (sdateEnd == null || sdateEnd.trim().equals("")) {
                            request.setAttribute("msgErrorDate", "Error al recibir fecha de término.");
                            error = true;
                        } else {
                            /* comparar fechas */
                            cnews.setDateBegin(sdateBegin);
                            cnews.setDateEnd(sdateEnd);
                            /* comparar con fecha actual */
                            if (cnews.getDateBegin().compareTo(Format.currentDate()) < 0) {
                                request.setAttribute("msgErrorDate", "Error: La noticia no puede poseer una fecha de inicio anterior a la fecha actual.");
                                error = true;
                            } else {
                                if (cnews.getDateBegin().compareTo(cnews.getDateEnd()) >= 0) {
                                    request.setAttribute("msgErrorDate", "Error: La fecha de término deber ser mayor que la fecha de inicio.");
                                    error = true;
                                }
                            }
                        }
                    }

                    if (!error) {
                        Collection<ClientNews> list = cnewsDAO.findByTittleDateId(cnews.getTittle(), cnews.getDateEnd(), cnews.getIdClientNews());
                        if (list.size() > 0) {
                            error = true;
                            request.setAttribute("msgErrorDup", "Error: Ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas.");
                        }
                    }

                    if (!error) {
                        /* comprobar existencia */
                        Collection list = cnewsDAO.findById(cnews.getIdClientNews());
                        if (list.size() > 0) {
                            cnewsDAO.update(cnews);
                            request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                        } else {
                            request.setAttribute("msgErrorFound", "Error: No existe la noticia o ha sido eliminada mientras se actualizaba. ");
                        }
                    }

                    /////////////////////////////////////////
                    // ESTABLECER ATRIBUTOS AL REQUEST
                    /////////////////////////////////////////

                    request.setAttribute("cnews", cnews);

                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/clientNews/clientNewsUpdate.jsp").forward(request, response);
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
