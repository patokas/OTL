/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guest;

import Helpers.Format;
import Helpers.ValidationRut;
import event.Event;
import event.EventDAO;
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
@WebServlet(name = "GuestAddServlet", urlPatterns = {"/GuestAddServlet"})
public class GuestAddServlet extends HttpServlet {

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

            GuestDAO guestDAO = new GuestDAO();
            guestDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            EventDAO eventDAO = new EventDAO();
            eventDAO.setConexion(conexion);

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
                if (access != 777 && access != 555) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {
                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", user); // username
                    request.setAttribute("access", access); // nivel de acceso

                    ////////////////////////////////////////
                    // DECLARAR VARIABLES DE INSTANCIA
                    ///////////////////////////////////////

                    Guest guest = new Guest();

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        //////////////////////////////////////// 

                        String btnAdd = request.getParameter("add");
                        String sidPlace = request.getParameter("idPlace");
                        String sidEvent = request.getParameter("idEvent");
                        String srut = request.getParameter("rut");

                        boolean error = false;

                        /* comprobar boton agregar */
                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese un nuevo invitado.");
                        } else {
                            /* comprobar id place */
                            if (sidPlace == null || sidPlace.trim().equals("")) {
                                error = true;
                            } else {
                                try {
                                    guest.setIdPlace(Integer.parseInt(sidPlace));
                                } catch (NumberFormatException n) {
                                    error = true;
                                }
                            }

                            /* comprobar id event */
                            if (sidEvent == null || sidEvent.trim().equals("")) {
                                error = true;
                                request.setAttribute("msgErrorIdEvent", "Error: Debe ingresar Id Evento.");
                            } else {
                                try {
                                    guest.setIdEvent(Integer.parseInt(sidEvent));
                                } catch (NumberFormatException n) {
                                    error = true;
                                    request.setAttribute("msgErrorIdEvent", "Error: El id Evento deber ser numérico.");
                                }
                            }

                            /* verificar si existe evento */
                            if (!error) {
                                Event event = new Event();
                                event.setIdEvent(guest.getIdEvent());
                                event.setIdPlace(guest.getIdPlace());
                                Collection<Event> listEvent = eventDAO.findByPlaceEvent(event);
                                if (listEvent.isEmpty()) {
                                    error = true;
                                    request.setAttribute("msgErrorIdEvent", "Error: No existe el evento ingresado.");
                                }
                            }

                            /* comprobar rut */
                            if (srut == null || srut.trim().equals("")) {
                                error = true;
                                request.setAttribute("msgErrorRut", "Error: Debe ingresar Rut.");
                            } else {
                                if (ValidationRut.validateRut(srut)) {
                                    guest.setRut(Format.getRut(srut));
                                    guest.setDv(Format.getDv(srut));
                                } else {
                                    error = true;
                                    request.setAttribute("msgErrorRut", "Error: Rut inválido.");
                                }

                            }

                            /////////////////////////////////////////
                            // INSERTAR REGISTRO
                            ////////////////////////////////////////

                            if (!error) {
                                try {
                                    guestDAO.insert(guest);
                                    request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                } catch (Exception dupException) {
                                    request.setAttribute("msgErrorDup", "Error: ya existe esta invitación.");
                                }
                            }
                        }

                        Collection<Place> listPlace = placeDAO.getAll();
                        request.setAttribute("listPlace", listPlace);
                        request.setAttribute("guest", guest);
                        
                    } catch (Exception parameterException) {
                    } finally {
                        /* despachar a la vista adminAdd */
                        request.getRequestDispatcher("/guest/guestAdd.jsp").forward(request, response);
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
