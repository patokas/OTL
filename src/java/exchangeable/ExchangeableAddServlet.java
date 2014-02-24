/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exchangeable;

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
@WebServlet(name = "ExchangeableAddServlet", urlPatterns = {"/ExchangeableAddServlet"})
public class ExchangeableAddServlet extends HttpServlet {

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

            ExchangeableDAO exDAO = new ExchangeableDAO();
            exDAO.setConexion(conexion);

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

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        /////////////////////////////////////////

                        String btnAdd = request.getParameter("add");
                        String sidPlace = request.getParameter("idPlace");
                        String tittle = request.getParameter("tittle");
                        String urlImage = request.getParameter("urlImage");
                        String spoints = request.getParameter("points");

                        Exchangeable exchange = new Exchangeable();

                        boolean error = false;

                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese una promo o regalo.");
                        } else {

                            /* comprobar id place */
                            if (sidPlace == null || sidPlace.trim().equals("")) {
                                error = true;
                            } else {
                                try {
                                    exchange.setIdPlace(Integer.parseInt(sidPlace));
                                } catch (NumberFormatException n) {
                                    error = true;
                                }
                            }

                            /* comprobar tittle */
                            if (tittle == null || tittle.trim().equals("")) {
                                request.setAttribute("msgErrorTittle", "Error: Debe ingresar un título a la promoción o regalo.");
                                error = true;
                            } else {
                                exchange.setTittle(tittle);
                            }

                            /* comprobar url image */
                            if (urlImage == null || urlImage.trim().equals("")) {
                                request.setAttribute("msgErrorUrlImage", "Error: Debe ingresar url de la imagen.");
                                error = true;
                            } else {
                                exchange.setUrlImage(urlImage);
                            }

                            /* comprobar points */
                            if (spoints == null || spoints.trim().equals("")) {
                                request.setAttribute("msgErrorPoints", "Error: Debe ingresar puntos que acumula esta promoción.");
                                error = true;
                            } else {
                                try {
                                    exchange.setPoints(Integer.parseInt(spoints));
                                    if (exchange.getPoints() < 0) {
                                        request.setAttribute("msgErrorPoints", "Error: Los puntos deben ser numéricos.");
                                        error = true;
                                    }
                                } catch (NumberFormatException n) {
                                    request.setAttribute("msgErrorPoints", "Error: Los puntos deben ser numéricos.");
                                    error = true;
                                }
                            }

                            /////////////////////////////////////////
                            // EJECUTAR LOGICA DE NEGOCIO
                            /////////////////////////////////////////

                            /* verificar duplicaciones */
                            if (!error) {
                                Collection<Exchangeable> list = exDAO.findByTittle(exchange);
                                for (Exchangeable aux : list) {
                                    /* si pertenecen al mismo place, error */
                                    if (aux.getIdPlace() == exchange.getIdPlace()) {
                                        error = true;
                                        request.setAttribute("msgErrorDup", "Error: Ya existe este producto. Compruebe utilizando otro título.");
                                    }
                                }
                            }
                            /* insertar producto canjeable */
                            if (!error) {
                                try {
                                    exDAO.insert(exchange);
                                    request.setAttribute("msgOk", "El registro se ha ingresado exitosamente.");                                    
                                } catch (Exception ex) {
                                    request.setAttribute("msgErrorInsert", "Error al insertar nuevo producto canjeable.");
                                }
                            }
                        }

                        /////////////////////////////////////////
                        // ESTABLECER ATRIBUTOS AL REQUEST
                        /////////////////////////////////////////

                        /* obtener lista de lugares */
                        Collection<Place> listPlace = placeDAO.getAll();
                        request.setAttribute("listPlace", listPlace);

                        request.setAttribute("exchange", exchange);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/exchangeable/exchangeableAdd.jsp").forward(request, response);
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
