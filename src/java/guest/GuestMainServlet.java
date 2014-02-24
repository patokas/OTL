/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guest;

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
 * @author patricio alberto
 */
@WebServlet(name = "GuestMainServlet", urlPatterns = {"/GuestMainServlet"})
public class GuestMainServlet extends HttpServlet {

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
                        // RECIBIR Y COMPROBAR PARAMETOS
                        /////////////////////////////////////////

                        String btnDelRow = request.getParameter("btnDelRow");
                        String btnDelCol = request.getParameter("btnDelCol");

                        Guest guest = new Guest();

                        //////////////////////////////////////////
                        // ELIMINAR POR REGISTRO
                        //////////////////////////////////////////
                        if (btnDelRow != null) {
                            try {
                                /* recibir parametros*/
                                String sidPlace = request.getParameter("idPlace");
                                String sidEvent = request.getParameter("idEvent");
                                String srut = request.getParameter("rut");

                                boolean error = false;

                                /* comprobar id place */
                                if (sidPlace == null || sidPlace.trim().equals("")) {
                                    error = true;
                                    System.err.println("Error al recibir parametro");
                                } else {
                                    try {
                                        guest.setIdPlace(Integer.parseInt(sidPlace));
                                    } catch (NumberFormatException n) {
                                        error = true;
                                        System.err.println("Error id place no numerico");
                                    }
                                }

                                /* comprobar id event */
                                if (sidEvent == null || sidEvent.trim().equals("")) {
                                    error = true;
                                    System.err.println("Error al recibir parametro");
                                } else {
                                    try {
                                        guest.setIdEvent(Integer.parseInt(sidEvent));
                                    } catch (NumberFormatException n) {
                                        error = true;
                                        System.err.println("Error id event no numerico");
                                    }
                                }

                                /* comprobar rut */
                                if (srut == null || srut.trim().equals("")) {
                                    error = true;
                                    System.err.println("Error al recibir parametro");
                                } else {
                                    try {
                                        guest.setRut(Integer.parseInt(srut));
                                    } catch (NumberFormatException n) {
                                        error = true;
                                        System.err.println("Error rut no numerico");
                                    }
                                }

                                if (!error) {
                                    try {
                                        guestDAO.delete(guest);
                                        request.setAttribute("msgDel", "Un registro ha sido eliminado.");
                                    } catch (Exception SQLException) {
                                        request.setAttribute("msgErrorNoDel", "Error: No se pudo eliminar el registro.");
                                    }
                                }
                            } catch (Exception parameterException) {
                            }
                        }

                        //////////////////////////////////////////
                        // ELIMINAR VARIOS REGISTROS
                        //////////////////////////////////////////
                        if (btnDelCol != null) {
                            try {
                                /* recibir parametros */
                                String[] outerArray = request.getParameterValues("chk");
                                int cont = 0;
                                int i = 0;
                                while (outerArray[i] != null) {
                                    String string = outerArray[i];
                                    String[] parts = string.split("-");
                                    guest.setIdPlace(Integer.parseInt(parts[0]));
                                    guest.setIdEvent(Integer.parseInt(parts[1]));
                                    guest.setRut(Integer.parseInt(parts[2]));
                                    try {
                                        guestDAO.delete(guest);
                                        cont++;
                                        if (cont == 1) {
                                            request.setAttribute("msgDel", "Un registro ha sido eliminado.");
                                        } else if (cont > 1) {
                                            request.setAttribute("msgDel", cont + " registros han sido eliminados.");
                                        }
                                    } catch (Exception referenceException) {
                                        request.setAttribute("msgErrorReference", "Error: Hubo un problema y no se pudo eliminar una invitación.");
                                    }
                                    i++;
                                }
                            } catch (Exception parameterException) {
                            }
                        }

                        //////////////////////////////////////////
                        // OBTENER TOTAL DE REGISTROS
                        //////////////////////////////////////////
                        Collection<Guest> listGuest = guestDAO.getAll();

                        if (listGuest.size() > 1) {
                            request.setAttribute("msg", listGuest.size() + " registros encontrados en la base de datos.");
                        } else if (listGuest.isEmpty()) {
                            request.setAttribute("msg", "No hay registros encontrado en la base de datos.");
                        }
                        request.setAttribute("listGuest", listGuest);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/guest/guest.jsp").forward(request, response);
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
