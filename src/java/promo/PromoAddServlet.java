package promo;

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
import userCard.UserCard;
import userCard.UserCardDAO;

/**
 *
 * @author patricio alberto
 */
@WebServlet(name = "PromoAddServlet", urlPatterns = {"/PromoAddServlet"})
public class PromoAddServlet extends HttpServlet {

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

            PromoDAO promoDAO = new PromoDAO();
            promoDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            UserCardDAO usercardDAO = new UserCardDAO();
            usercardDAO.setConexion(conexion);

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
                        String sendTo = request.getParameter("sendTo");
                        String sidPlace = request.getParameter("idPlace");
                        String tittle = request.getParameter("tittle");
                        String details = request.getParameter("details");
                        String urlImage = request.getParameter("urlImage");
                        String date1 = request.getParameter("dateBegin");
                        String date2 = request.getParameter("dateEnd");
                        String spoints = request.getParameter("points");

                        Promo promo = new Promo();

                        boolean error = false;

                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese una promoción.");
                        } else {
                            /* comprobar send to */
                            int sent = 9; // 9 == null
                            if (sendTo == null || sendTo.trim().equals("")) {
                                error = true;
                            } else {
                                try {
                                    sent = Integer.parseInt(sendTo);
                                    request.setAttribute("sendTo", sent);
                                } catch (NumberFormatException n) {
                                    error = true;
                                }
                            }

                            /* comprobar id place */
                            if (sidPlace == null || sidPlace.trim().equals("")) {
                                error = true;
                            } else {
                                try {
                                    promo.setIdPlace(Integer.parseInt(sidPlace));
                                } catch (NumberFormatException n) {
                                    error = true;
                                }
                            }

                            /* comprobar tittle */
                            if (tittle == null || tittle.trim().equals("")) {
                                request.setAttribute("msgErrorTittle", "Error: Debe ingresar un título a la promoción o regalo.");
                                error = true;
                            } else {
                                promo.setTittle(tittle);
                            }

                            /* comprobar details */
                            if (details == null || details.trim().equals("")) {
                                request.setAttribute("msgErrorDetails", "Error: Debe ingresar detalle de la promoción o regalo");
                                error = true;
                            } else {
                                promo.setDetails(details);
                            }

                            /* comprobar url image */
                            if (urlImage == null || urlImage.trim().equals("")) {
                                request.setAttribute("msgErrorUrlImage", "Error: Debe ingresar url de la imagen.");
                                error = true;
                            } else {
                                promo.setUrlImage(urlImage);
                            }

                            /* comprobar date begin */
                            if (date1 == null || date1.trim().equals("")) {
                                request.setAttribute("msgErrorDate", "Error al recibir fecha de inicio.");
                                error = true;
                            } else {
                                promo.setDateBegin(date1);
                                /* comprobar date end */
                                if (date2 == null || date2.trim().equals("")) {
                                    request.setAttribute("msgErrorDate", "Error al recibir fecha de término.");
                                    error = true;
                                } else {
                                    /* comparar fechas */
                                    promo.setDateBegin(date1);
                                    promo.setDateEnd(date2);
                                    /* comparar con fecha actual */
                                    if (promo.getDateBegin().compareTo(Format.currentDate()) < 0) {
                                        request.setAttribute("msgErrorDate", "Error: La promo o regalo no puede poseer una fecha de inicio anterior a la fecha actual.");
                                        error = true;
                                    } else {
                                        if (promo.getDateBegin().compareTo(promo.getDateEnd()) >= 0) {
                                            request.setAttribute("msgErrorDate", "Error: La fecha de término deber ser mayor que la fecha de inicio.");
                                            error = true;
                                        }
                                    }
                                }
                            }

                            /* comprobar points */
                            if (spoints == null || spoints.trim().equals("")) {
                                request.setAttribute("msgErrorPoints", "Error: Debe ingresar puntos que acumula esta promoción.");
                                error = true;
                            } else {
                                try {
                                    promo.setPoints(Integer.parseInt(spoints));
                                } catch (NumberFormatException n) {
                                    request.setAttribute("msgErrorPoints", "Error: Los puntos deben ser numéricos.");
                                    error = true;
                                }
                            }

                            //////////////////////////////////
                            //EJECUTAR lOGICA DE NEGOCIO
                            //////////////////////////////////

                            if (!error) {
                                /* comprobar registros duplicados */
                                boolean find = promoDAO.validateDuplicate(promo);
                                if (find) {
                                    request.setAttribute("msgErrorDup", "Error: ya existe esta promoción. Compruebe utilizando otro título u otro rango de fechas.");
                                } else {
                                    Collection<UserCard> listUC;
                                    int gender = sent - 2;

                                    switch (sent) {
                                        case 1:
                                            /* obtener todos */
                                            listUC = usercardDAO.getAll();
                                            try {
                                                promoDAO.insert(promo, listUC);
                                                request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                            } catch (Exception insertException) {
                                                System.out.println("error no se pudo insertar promocion");
                                            }
                                            break;
                                        case 2:
                                            /* obtener sólo hombres */
                                            listUC = usercardDAO.findByGender(gender);
                                            try {
                                                promoDAO.insert(promo, listUC);
                                                request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                            } catch (Exception insertException) {
                                                System.out.println("error no se pudo insertar promocion");
                                            }
                                            break;
                                        case 3:
                                            /* obtener sólo mujeres */
                                            listUC = usercardDAO.findByGender(gender);
                                            try {
                                                promoDAO.insert(promo, listUC);
                                                request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                            } catch (Exception insertException) {
                                                System.out.println("error no se pudo insertar promocion");
                                            }
                                            break;
                                        case 4:
                                            /* obtener sólo cumpleañeros */
                                            listUC = usercardDAO.findByBirthDay();
                                            try {
                                                promoDAO.insert(promo, listUC);
                                                request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                            } catch (Exception insertException) {
                                                System.out.println("error no se pudo insertar promocion");
                                            }
                                            break;
                                        case 5:
                                            /* registrar sólo evento */
                                            try {
                                                promoDAO.insert(promo);
                                                request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                            } catch (Exception insertException) {
                                                System.out.println("error no se pudo insertar promocion");
                                            }
                                            break;
                                    }
                                }
                            }
                        }

                        /////////////////////////////////////////
                        // ESTABLECER ATRIBUTOS AL REQUEST
                        /////////////////////////////////////////

                        /* obtener lista de lugares */
                        try {
                            Collection<Place> listPlace = placeDAO.getAll();
                            request.setAttribute("listPlace", listPlace);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        request.setAttribute("promo", promo);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/promo/promoAdd.jsp").forward(request, response);
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
