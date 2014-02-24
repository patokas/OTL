/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

import Helpers.Format;
import card.Card;
import card.CardDAO;
import event.Event;
import event.EventDAO;
import guest.Guest;
import guest.GuestDAO;
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
import userCard.UserCard;
import userCard.UserCardDAO;

/**
 *
 * @author patricio alberto
 */
@WebServlet(name = "ListAddServlet", urlPatterns = {"/ListAddServlet"})
public class ListAddServlet extends HttpServlet {

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
            /////////////////////////////////////////

            conexion = ds.getConnection();

            ListDAO listDAO = new ListDAO();
            listDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            EventDAO eventDAO = new EventDAO();
            eventDAO.setConexion(conexion);

            UserCardDAO usercardDAO = new UserCardDAO();
            usercardDAO.setConexion(conexion);

            CardDAO cardDAO = new CardDAO();
            cardDAO.setConexion(conexion);

            GuestDAO guestDAO = new GuestDAO();
            guestDAO.setConexion(conexion);

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

                    //////////////////////////////////////////
                    // DECLARAR VARIABLES DE INSTANCIA
                    /////////////////////////////////////////                

                    List entry = new List();

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        ////////////////////////////////////////   

                        String btnAdd = request.getParameter("add");
                        String sidPlace = request.getParameter("idPlace");
                        String sidEvent = request.getParameter("idEvent");
                        String sBarCode = request.getParameter("barCode");


                        boolean error = false;

                        if (btnAdd != null) {

                            /* comprobar id place */
                            if (sidPlace == null || sidPlace.trim().equals("")) {
                                request.setAttribute("msgErroridPlace", "Error al recibir id de Plaza. ");
                                error = true;
                            } else {
                                try {
                                    entry.setIdPlace(Integer.parseInt(sidPlace));
                                } catch (NumberFormatException n) {
                                    error = true;
                                    request.setAttribute("msgErroridPlace", "Error: El id de Plaza deber ser numerico. ");
                                }
                            }

                            /* comprobar bar code*/
                            if (sBarCode == null || sBarCode.trim().equals("")) {
                                error = true;
                            } else {
                                try {
                                    entry.setBarCode(Integer.parseInt(sBarCode));
                                    if (entry.getBarCode() < 10000000) {
                                        error = true;
                                        request.setAttribute("msgErrorBarCode", "Error: El Código de Barras debe contener 8 dígitos. ");
                                    }
                                } catch (NumberFormatException n) {
                                    error = true;
                                    request.setAttribute("msgErrorBarCode", "Error: El Código de Barras debe ser numérico. ");
                                }
                            }
                            ////////////////////////////////////
                            // EJECUTAR LÓGICA DE NEGOCIO
                            ////////////////////////////////////
                            if (!error) {
                                /* comprobar existencia tarjeta */
                                Card card = cardDAO.findOneByBarCode(entry.getBarCode());

                                if (card == null) {
                                    request.setAttribute("msgErrorCardNotFound", "ACCESSO PROHIBIDO: Tarjeta inválida, verifique código nuevamente o comuniquese con soporte técnico.");
                                    System.out.println("error: tarjeta no existente");
                                } else {
                                    UserCard usercard = null;
                                    /* comprobar vigencia tarjeta */
                                    if (Format.currentDate().compareTo(card.getDateEndCard()) >= 0) {
                                        request.setAttribute("msgErrorExp", "Acceso Restringido: Tarjeta caducada.");
                                        System.out.println("tarjeta caducada");
                                    } else {
                                        System.out.println("tarjeta correcta");
                                        /* obtener eventos a partir de la fecha actual */
                                        Collection<Event> listEvent = eventDAO.findbyRangeDatePlace(Format.currentDate(), entry.getIdPlace());

                                        /* recorrer collection para obtener eventos */
                                        for (Event aux : listEvent) {                                            
                                            Guest guest = new Guest();
                                            guest.setIdPlace(entry.getIdPlace());
                                            guest.setRut(card.getRut());
                                            guest.setIdEvent(aux.getIdEvent());

                                            /* consultar si existe invitacion */
                                            Guest guestReg = guestDAO.findOneByGuest(guest);
                                            if (guestReg != null) {
                                                System.out.println("encuentra invitacion");
                                                /* si existe invitacion, buscar datos del cliente */
                                                entry.setIdEvent(aux.getIdEvent());
                                                entry.setRut(card.getRut());
                                                entry.setDv(card.getDv());
                                                usercard = usercardDAO.findOneByRut(card.getRut());
                                                break;
                                            }
                                        }
                                        /* mostrar datos si el cliente ha sido invitado */
                                        if (usercard != null) {
                                            System.out.println("obtiene datos del cliente");
                                            listDAO.insert(entry);
                                            request.setAttribute("msgOk", "Bienvenido: " + usercard.getFirstName() + " " + usercard.getLastName());
                                        } else {
                                            request.setAttribute("msgErrorGuestNotFound", "Acceso Restringido: El cliente no posee invitaciones en este recinto.");
                                            System.out.println("no existe invitaciones vigentes");
                                        }
                                    }
                                }
                            }
                        }
                        //////////////////////////////////////////
                        // ESTABLECER ATRIBUTOS AL REQUEST
                        /////////////////////////////////////////

                        Collection<Place> listPlace = placeDAO.getAll();
                        request.setAttribute("listPlace", listPlace);
                        request.setAttribute("reg", entry);

                    } catch (Exception parameterException) {
                        System.out.println("Primera vez del formulario o bien, error de parámetros");
                    } finally {
                        request.getRequestDispatcher("/list/listAdd.jsp").forward(request, response);
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
