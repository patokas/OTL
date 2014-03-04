/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dressCode;

import Helpers.Format;
import city.City;
import city.CityDAO;
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
 * @author patricio
 */
@WebServlet(name = "DressCodeAddServlet", urlPatterns = {"/DressCodeAddServlet"})
public class DressCodeAddServlet extends HttpServlet {

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

            DressCodeDAO dressCodeDAO = new DressCodeDAO();
            dressCodeDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener los valores de session */
                String userJsp = (String) session.getAttribute("username");
                String sAccess = (String) session.getAttribute("access");
                int access = Integer.parseInt(sAccess);

                /* asignar valores a la jsp */
                request.setAttribute("userJsp", userJsp);
                request.setAttribute("access", access);

                /////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////////
                try {

                    String btnAdd = request.getParameter("add");
                    String nameDressCode = request.getParameter("nameDressCode");
                    String menDetails = request.getParameter("menDetails");
                    String womenDetails = request.getParameter("womenDetails");
                    String urlImage = request.getParameter("urlImage");

                    DressCode dressCode = new DressCode();

                    boolean error = false;

                    if (btnAdd == null) {
                        request.setAttribute("msg", "Ingrese un nuevo código de vestir.");
                    } else {
                        /* comprobar men details */
                        if (menDetails == null || menDetails.trim().equals("")) {
                            request.setAttribute("msgErrorMenDetails", "Error: Debe ingresar detalles para hombres.");
                            error = true;
                        } else {
                            request.setAttribute("menDetails", menDetails);
                            dressCode.setMenDetails(menDetails);
                        }

                        /* comprobar women details */
                        if (womenDetails == null || womenDetails.trim().equals("")) {
                            request.setAttribute("msgErrorWomenDetails", "Error: Debe ingresar detalles para mujeres.");
                            error = true;
                        } else {
                            request.setAttribute("womenDetails", womenDetails);
                            dressCode.setWomenDetails(womenDetails);
                        }

                        /* comprobar url image */
                        if (urlImage == null || urlImage.trim().equals("")) {
                            request.setAttribute("msgErrorUrlImage", "Error: Debe ingresar url de la imagen.");
                            error = true;
                        } else {
                            request.setAttribute("urlImage", urlImage);
                            dressCode.setUrlImage(urlImage);
                        }

                        /* comprobar tittle */
                        if (nameDressCode == null || nameDressCode.trim().equals("")) {
                            request.setAttribute("msgErrorTittle", "Error: Debe ingresar título de código de vestir.");
                            error = true;
                        } else {
                            request.setAttribute("nameDressCode", nameDressCode);
                            /* capitalizar título */
                            dressCode.setNameDressCode(Format.capital(nameDressCode));
                        }

                        ////////////////////////////////////
                        // EJECUTAR LOGICA DE NEGOCIO
                        ///////////////////////////////////
                        if (!error) {
                            /* buscar duplicaciones de tittle */
                            boolean find = dressCodeDAO.findByName(dressCode.getNameDressCode());
                            if (find) {
                                request.setAttribute("msgErrorDup", "Error: Ya existe un código de vestir con el mismo título.");
                            } else {
                                /* insertar nuevo registro */
                                try {
                                    dressCodeDAO.insert(dressCode);
                                    request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                } catch (Exception ex) {
                                }
                            }
                        }
                    }
                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/dressCode/dressCodeAdd.jsp").forward(request, response);
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
