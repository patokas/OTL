/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package news;

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
@WebServlet(name = "NewsAddServlet", urlPatterns = {"/NewsAddServlet"})
public class NewsAddServlet extends HttpServlet {

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
                if (access != 555 && access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {
                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", username);
                    request.setAttribute("access", access);

                    /////////////////////////////////////////
                    // RECIBIR PARAMETROS
                    //////////////////////////////////////// 
                    try {

                        String btnAdd = request.getParameter("add");
                        String stittle = request.getParameter("tittle");
                        String sdetails = request.getParameter("details");
                        String stypeNews = request.getParameter("typeNews");
                        String surlImage = request.getParameter("urlImage");
                        String sdateBegin = request.getParameter("dateBegin");
                        String sdateEnd = request.getParameter("dateEnd");

                        News news = new News();

                        boolean error = false;

                        /* comprobar si recibe formulario */
                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese Noticia.");
                        } else {

                            /* comprobar tittle */
                            if (stittle == null || stittle.trim().equals("")) {
                                request.setAttribute("msgErrorTittle", "Error al recibir titulo.");
                                error = true;
                            } else {
                                news.setTittle(stittle);
                            }

                            /* comprobar details */
                            if (sdetails == null || sdetails.trim().equals("")) {
                                request.setAttribute("msgErrorDetails", "Error al recibir detalle.");
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

                            /* comprobar url_image */
                            if (surlImage == null || surlImage.trim().equals("")) {
                                request.setAttribute("msgErrorUrlImage", "Error al recibir url de la imagen.");
                                error = true;
                            } else {
                                news.setUrlImage(surlImage);
                            }

                            /* comprobar date begin */
                            if (sdateBegin == null || sdateBegin.trim().equals("")) {
                                request.setAttribute("msgErrorDate", "Error al recibir fecha de inicio.");
                                error = true;
                            } else {
                                news.setDateBegin(sdateBegin);
                                /* comprobar date end */
                                if (sdateEnd == null || sdateEnd.trim().equals("")) {
                                    request.setAttribute("msgErrorDate", "Error al recibir fecha de término.");
                                    error = true;
                                } else {
                                    /* comparar fechas */
                                    news.setDateBegin(sdateBegin);
                                    news.setDateEnd(sdateEnd);
                                    /* comparar con fecha actual */
                                    if (news.getDateBegin().compareTo(Format.currentDate()) < 0) {
                                        request.setAttribute("msgErrorDate", "Error: La noticia no puede poseer una fecha de inicio anterior a la fecha actual.");
                                        error = true;
                                    } else {
                                        if (news.getDateBegin().compareTo(news.getDateEnd()) >= 0) {
                                            request.setAttribute("msgErrorDate", "Error: La fecha de término deber ser mayor que la fecha de inicio.");
                                            error = true;
                                        }
                                    }
                                }
                            }

                            //////////////////////////////////
                            //EJECUTAR lOGICA DE NEGOCIO
                            //////////////////////////////////

                            if (!error) {
                                /* comprobar registros duplicados */
                                boolean find = newsDAO.validateDuplicate(news);
                                if (find) {
                                    error = true;
                                    request.setAttribute("msgErrorDup", "Error: ya existe esta noticia. Compruebe utilizando otro título u otro rango de fechas.");
                                } else {
                                    /* insertar nueva noticia */
                                    try {
                                        newsDAO.insert(news);
                                        request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                    } catch (Exception insertException) {
                                        //InsertException no existe                                  
                                        System.out.println("error no se pudo insertar news");
                                    }
                                }
                            }
                        }

                        /////////////////////////////////////////
                        // ESTABLECER ATRIBUTOS AL REQUEST
                        /////////////////////////////////////////

                        request.setAttribute("news", news);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/news/newsAdd.jsp").forward(request, response);
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
