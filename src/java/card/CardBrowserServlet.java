/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package card;

import Helpers.Format;
import Helpers.ValidationRut;
import city.City;
import city.CityDAO;
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
import userCard.UserCard;

/**
 *
 * @author alexander
 */
@WebServlet(name = "CardBrowserServlet", urlPatterns = {"/CardBrowserServlet"})
public class CardBrowserServlet extends HttpServlet {

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

        ////////////////////////////////////////
        //      ESTABLECER CONEXION
        ////////////////////////////////////////
        try {
            conexion = ds.getConnection();

            CardDAO cardDAO = new CardDAO();
            cardDAO.setConexion(conexion);

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            //////////////////////////////////////////
            //        COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String user = (String) session.getAttribute("username");

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", user);
                request.setAttribute("access", access);

                /////////////////////////////////////////
                //   RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////////
                try {
                    String rut = request.getParameter("rut"); // rut + dv

                    Card card = new Card();

                    boolean error = false;

                    /* comprobar rut */
                    if (rut == null || rut.trim().equals("") || rut.length() < 3) {
                        request.setAttribute("msgErrorRut", "Error: Debe ingresar RUT.");
                        error = true;
                    } else {
                        request.setAttribute("rut", rut);
                        try {
                            if (!ValidationRut.validateRut(rut)) {
                                request.setAttribute("msgErrorRut", "Error: RUT inválido.");
                                error = true;
                            } else {
                                card.setRut(Format.getRut(rut));
                                card.setDv(Format.getDv(rut));
                            }
                        } catch (Exception ex) {
                            request.setAttribute("msgErrorRut", "Error: RUT inválido.");
                            error = true;
                        }
                    }

                    ////////////////////////////////////////
                    //        LOGICA DE NEGOCIO
                    ////////////////////////////////////////

                    if (!error) {
                        /* buscar card de usercard */
                        Card aux = cardDAO.findbyRutJoin(card);

                        if (aux != null) {
                            request.setAttribute("msgAdd", "El usuario está registrado. ");
                            request.setAttribute("reg", aux);
                            request.getRequestDispatcher("/card/cardAdd.jsp").forward(request, response);
                        } else {
                            /* obtener lista de ciudades */
                            try {
                                Collection<City> listCity = cityDAO.getAll();
                                request.setAttribute("listCity", listCity);
                            } catch (Exception ex) {
                            }

                            /* instanciar usercard */
                            UserCard usercard = new UserCard();
                            /* asignar rut */
                            usercard.setRut(card.getRut());
                            usercard.setDv(card.getDv());

                            request.setAttribute("reg", usercard);

                            request.getRequestDispatcher("/userCard/userCardAdd.jsp").forward(request, response);
                        }
                    } else {
                        request.getRequestDispatcher("/card/cardVerify.jsp").forward(request, response);
                    }
                } catch (Exception parameterException) {
                    System.out.println("Error al despachar a la vista");
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
