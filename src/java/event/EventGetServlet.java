/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

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
import place.Place;
import place.PlaceDAO;

/**
 *
 * @author patricio alberto
 */
@WebServlet(name = "EventGetServlet", urlPatterns = {"/EventGetServlet"})
public class EventGetServlet extends HttpServlet {

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

            EventDAO eventDAO = new EventDAO();
            eventDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

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
                    // DECLARAR VARIABLES DE INSTANCIA
                    /////////////////////////////////////////

                    Event reg = null;
                    
                    Collection<Place> listPlace = null;

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        /////////////////////////////////////////

                        String sidPlace = request.getParameter("idPlace");
                        String sidEvent = request.getParameter("idEvent");

                        boolean error = false;

                        Event event = new Event();

                        /* comprobar id place */
                        if (sidPlace == null || sidPlace.trim().equals("")) {
                            request.setAttribute("msgErrorIdPlace", "Error al recibir id Plaza.");
                            error = true;
                        } else {
                            try {
                                event.setIdPlace(Integer.parseInt(sidPlace));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorIdPlace", "Error: El id de plaza no es numérico.");
                            }
                        }

                        /* comprobar id event */
                        if (sidEvent == null || sidEvent.trim().equals("")) {
                            request.setAttribute("msgErrorIdEvent", "Error al recibir id Evento.");
                            error = true;
                        } else {
                            try {
                                event.setIdEvent(Integer.parseInt(sidEvent));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorIdEvent", "Error: El id de Evento no es numérico.");
                            }
                        }

                        if (!error) {
                            listPlace = placeDAO.getAll();
                            Collection<Event> listEvent = eventDAO.findByPlaceEvent(event);

                            if (listEvent.size() > 0) {
                                for (Event aux : listEvent) {
                                    if (aux.getIdPlace() > 0) {
                                        reg = aux;
                                        reg.setDateBegin(Format.dateYYYYMMDD(reg.getDateBegin()));
                                        reg.setDateEnd(Format.dateYYYYMMDD(reg.getDateEnd()));
                                        request.setAttribute("msgOk", "Se encontró el registro!");
                                    }
                                }
                            } else {
                                request.setAttribute("msgErrorFound", "Error: No se encontró el Evento.");
                            }
                        }
                    } catch (Exception parameterException) {
                    } finally {
                        request.setAttribute("event", reg);
                        request.setAttribute("listPlace", listPlace);
                        request.getRequestDispatcher("/event/eventUpdate.jsp").forward(request, response);
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
