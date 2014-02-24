/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

/**
 *
 * @author alexander
 */
public class Rut {

    public int getRut(String rut) {
        String[] parts = rut.split("-");
        String numerica, nuevo = parts[0];
        numerica = nuevo.replace(".", "");
        int inumerica = Integer.parseInt(numerica);
        return inumerica;

    }

    public String getDv(String rut) {
        String[] parts = rut.split("-");
        String numerica, nuevo = parts[0];
        String dv = parts[1];
        numerica = nuevo.replace(".", "");
        int inumerica = Integer.parseInt(numerica);
        return dv;
    }
}
