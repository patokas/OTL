/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import Helpers.Format;
import dressCode.DressCode;
import dressCode.DressCodeDAO;
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
@WebServlet(name = "EventAddServlet", urlPatterns = {"/EventAddServlet"})
public class EventAddServlet extends HttpServlet {

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

            DressCodeDAO dressCodeDAO = new DressCodeDAO();
            dressCodeDAO.setConexion(conexion);

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
                    request.setAttribute("su", 777); //superuser

                    ///////////////////////////////////////
                    // DECLARAR VARIABLES DE INSTANCIA
                    ///////////////////////////////////////                   

                    Event event = new Event();

                    try {
                        /////////////////////////////////////////
                        // RECIBIR PARAMETROS
                        //////////////////////////////////////// 

                        String btnAdd = request.getParameter("add");
                        String sidPlace = request.getParameter("idPlace");
                        String tittle = request.getParameter("tittle");
                        String details = request.getParameter("details");
                        String dateBegin = request.getParameter("dateBegin");
                        String dateEnd = request.getParameter("dateEnd");
                        String urlImage = request.getParameter("url");
                        String spoints = request.getParameter("points");                        
                        String sidDressCode = request.getParameter("idDressCode");

                        boolean error = false;

                        /* comprobar si recibe formulario */
                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese un evento.");
                        } else {

                            /* comprobar id place */
                            if (sidPlace == null || sidPlace.trim().equals("")) {
                                request.setAttribute("msgErrorIdPlace", "Error al recibir id Plaza.");
                                error = true;
                            } else {
                                event.setIdPlace(Integer.parseInt(sidPlace));
                            }

                            /* comprobar tittle */
                            if (tittle == null || tittle.trim().equals("")) {
                                request.setAttribute("msgErrorTittle", "Error: Debe ingresar un título para el evento.");
                                error = true;
                            } else {
                                event.setTittle(tittle);
                            }

                            /* comprobar details */
                            if (details == null || details.trim().equals("")) {
                            } else {
                                event.setDetails(details);
                            }

                            /* comprobar points */
                            if (spoints == null || spoints.trim().equals("")) {
                                request.setAttribute("msgErrorPoints", "Error: Debe ingresar puntos acumulables por asistencia.");
                                error = true;
                            } else {
                                try {
                                    event.setPoints(Integer.parseInt(spoints));
                                    if (event.getPoints() < 0) {
                                        request.setAttribute("msgErrorPoints", "Error: El valor de puntos no puede ser negativo.");
                                        error = true;
                                    }
                                } catch (NumberFormatException n) {
                                    request.setAttribute("msgErrorPoints", "Error: Debe ingresar un valor numérico para puntos.");
                                    error = true;
                                }
                            }

                            /* comprobar url image */
                            if (urlImage == null || urlImage.trim().equals("")) {
                                error = true;
                            } else {
                                event.setUrlImage(urlImage);
                            }

                            /* comprobar fecha de inicio */
                            if (dateBegin == null || dateBegin.trim().equals("")) {
                                request.setAttribute("msgErrorDateBegin", "Error al recibir fecha de inicio.");
                                error = true;
                            } else {
                                /* comprobar fecha de termino */
                                if (dateEnd == null || dateEnd.trim().equals("")) {
                                    request.setAttribute("msgErrorDateEnd", "Error al recibir fecha de término.");
                                    error = true;
                                } else {
                                    event.setDateBegin(dateBegin);
                                    event.setDateEnd(dateEnd);
                                    /* comparar con fecha actual */
                                    if (event.getDateBegin().compareTo(Format.currentDate()) < 0) {
                                        request.setAttribute("msgErrorDateBegin", "Error: El evento no puede poseer una fecha de inicio anterior a la fecha actual.");
                                        error = true;
                                    } else {
                                        /* comparar fechas */
                                        if (event.getDateBegin().compareTo(event.getDateEnd()) >= 0) {
                                            request.setAttribute("msgErrorDate", "Error: La fecha de inicio no puede ser mayor que la de término.");
                                            error = true;
                                        }
                                    }
                                }
                            }

                            /* comprobar id dress code */
                            if (sidDressCode == null || sidDressCode.trim().equals("")) {
                                error = true;
                            } else {
                                try {
                                    event.setIdDressCode(Integer.parseInt(sidDressCode));
                                } catch (NumberFormatException n) {
                                    error = true;
                                }
                            }

                            ////////////////////////////////////////////
                            // EJECUTAR LÓGICA DE NEGOCIO
                            ////////////////////////////////////////////
                            
                            if (!error) {
                                /* comprobar registros duplicados */
                                boolean find = eventDAO.findDuplicate(event);
                                if (find) {
                                    request.setAttribute("msgErrorEvent", "Error: ya existe este evento. Compruebe utilizando otro título u otro rango de fechas.");
                                } else {
                                    /* insertar nuevo evento */
                                    try {
                                        eventDAO.insert(event);
                                        request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                    } catch (Exception ex) {
                                        request.setAttribute("msgErrorEvent", "Error al insertar el evento, verifique coincidencias.");
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }

                        /* obtener lista de lugares */
                        try {
                            Collection<Place> listPlace = placeDAO.getAll();
                            request.setAttribute("listPlace", listPlace);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        /* obtener lista de codigos de vestir */
                        try {
                            Collection<DressCode> listDressCode = dressCodeDAO.getAll();
                            request.setAttribute("listDressCode", listDressCode);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        request.setAttribute("event", event);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/event/eventAdd.jsp").forward(request, response);
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
