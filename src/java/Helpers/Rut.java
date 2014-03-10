package Helpers;

/**
 *
 * @author patricio, alexander
 */
public class Rut {

    public static boolean validateRut(String rut) {

        rut.toLowerCase();

        boolean correcto = false;

        if (rut.substring(rut.length() - 2, rut.length() - 1).equals("-")) {
            //String numerico = rut;
            String[] parts = rut.split("-");
            String numerica, nuevo = parts[0];
            String dv = parts[1];
            //System.out.println(numerica);
            numerica = nuevo.replace(".", "");

            int factor = 2;
            int suma = 0;
            for (int i = numerica.length(); i > 0; i--) {
                if (factor > 7) {
                    factor = 2;
                }
                String num;
                if (i == numerica.length()) {
                    num = numerica.substring(i - 1);
                } else {
                    num = numerica.substring(i - 1, i);
                }
                int numero = Integer.parseInt(num);
                suma = suma + (numero * factor);
                factor++;
            }
            int dv2 = 11 - (suma % 11);
            int dv1 = 0;
            if (dv.equals("0")) {
                dv1 = 11;
            }
            if (dv.equals("k")) {
                dv1 = 10;
            }
            if ((!(dv.equals("0"))) && (!(dv.equals("k")))) {
                dv1 = Integer.parseInt(dv);
            }

            if (dv1 == dv2) {
                correcto = true;
            }
        }
        return correcto;
    }

    public static int getRut(String rut) {

        /* quitar los puntos */
        String srut = rut.replace(".", "");
        /* quitar los guiones */
        srut = srut.replace("-", "");
        /* quitar espacios */
        srut = srut.trim();

        /* obtener parte numerica */
        srut = srut.substring(0, srut.length() - 1);

        /* parsear a int */
        int nrut = Integer.parseInt(srut);

        return nrut;
    }

    public static String getDv(String rut) {

        /* quitar los guiones */
        String srut = rut.replace("-", "");

        /* obtener dv */
        String dv = srut.substring(srut.length() - 1, srut.length());

        return dv;
    }
}
