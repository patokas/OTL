/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package news;

import Helpers.Format;
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
 * @author alexander
 */
@WebServlet(name = "NewsUpdateServlet", urlPatterns = {"/NewsUpdateServlet"})
public class NewsUpdateServlet extends HttpServlet {

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

            NewsDAO newsDAO = new NewsDAO();
            newsDAO.setConexion(conexion);
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

                    String sidNews = request.getParameter("idNews");
                    String stittle = request.getParameter("tittle");
                    String sdetails = request.getParameter("details");
                    String stypeNews = request.getParameter("typeNews");
                    String surlImage = request.getParameter("urlImage");
                    String sdateBegin = request.getParameter("dateBegin");
                    String sdateEnd = request.getParameter("dateEnd");

                    News news = new News();

                    boolean error = false;

                    /* comprobar id news */
                    if (sidNews == null || sidNews.trim().equals("")) {
                        request.setAttribute("msgErrorIdNews", "Error al recibir id noticias. ");
                        error = true;
                    } else {
                        try {
                            news.setIdNews(Integer.parseInt(sidNews));
                        } catch (NumberFormatException n) {
                            request.setAttribute("msgErrorIdNews", "Error al recibir id noticias. ");
                            error = true;
                        }
                    }

                    /* comprobar tittle */
                    if (stittle == null || stittle.trim().equals("")) {
                        request.setAttribute("msgErrorTittle", "Error: Debe ingresar un titulo para la noticia. ");
                        error = true;
                    } else {
                        news.setTittle(stittle);
                    }

                    /* comprobar details */
                    if (sdetails == null || sdetails.trim().equals("")) {
                        request.setAttribute("msgErrorDetails", "Error: Debe ingresar detalle de la noticia");
                        error = true;
                    } else {
                        news.setDetails(sdetails);
                    }

                    /* comprobar type news */
                    if (stypeNews == null || stypeNews.trim().equals("")) {
                        request.setAttribute("msgErrorTypeNews", "Error al recibir tipo de noticia.");
                        error = true;
                    } else {
                        try {
                            news.setTypeNews(Integer.parseInt(stypeNews));
                        } catch (NumberFormatException n) {
                            request.setAttribute("msgErrorTypeNews", "Error: Debe recibir un valor numérico en tipo de noticias.");
                            error = true;
                        }
                    }

                    /* comprobar url image */
                    if (surlImage == null || surlImage.trim().equals("")) {
                        request.setAttribute("msgErrorUrlImage", "Error: Debe ingresar la url de la imagen.");
                        error = true;
                    } else {
                        news.setUrlImage(surlImage);
                    }

                    /* comprobar dateBegin */
                    if (sdateBegin == null || sdateBegin.trim().equals("")) {
                        request.setAttribute("msgErrorDate", "Error al recibir feha de inicio.");
                        error = true;
                    } else {
                        /* comprobar dateEnd */
                        if (sdateEnd == null || sdateEnd.trim().equals("")) {
                            request.setAttribute("msgErrorDate", "Error al recibir feha de término.");
                            error = true;
                        } else {
                            /* comparar fechas */
                            news.setDateBegin(sdateBegin);
                            news.setDateEnd(sdateEnd);
                            //System.out.println("Comparar fecha 1 y fecha 2: " + event.getDateBegin().compareTo(event.getDateEnd()));
                            if (news.getDateBegin().compareTo(news.getDateEnd()) >= 0) {
                                request.setAttribute("msgErrorDate", "Error: La fecha de término debe ser mayor que la fecha de inicio.");
                                error = true;
                            }
                        }
                    }

                    if (!error) {
                        /* comprobar registros duplicados */
                        boolean find = newsDAO.validateDuplicate(news);
                        if (find) {
                            request.setAttribute("msgErrorDup", "Error: ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas.");
                        } else {
                            News aux = newsDAO.findByNews(news);
                            if (aux != null) {
                                newsDAO.update(news);
                                request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                            } else {
                                request.setAttribute("msgErrorFound", "Error: El registro no existe o ha sido mientras se actualizaba.");
                            }
                        }
                    }

                    /////////////////////////////////////////
                    // ESTABLECER ATRIBUTOS AL REQUEST
                    /////////////////////////////////////////

                    request.setAttribute("news", news);
                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/news/newsUpdate.jsp").forward(request, response);
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
