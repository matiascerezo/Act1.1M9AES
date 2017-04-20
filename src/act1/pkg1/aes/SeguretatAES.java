package act1.pkg1.aes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Matias
 */
public class SeguretatAES {

    private SecretKey skey;

    /**
     * Este método le llega un tamaño que tiene que ser igual a 128/192/256 y
     * mediante el MessageDigest obtenemos la intancia del algoritmo 'SHA-256'.
     * pasamos la contraseña en array de bytes con el digest, copiamos el array
     * pasandolo a 16-24-32 depende del tamaño que hayamos establecido anteriormente.
     * Mostramos por pantalla los array de bytes y generamos la clave secreta con
     * AES. En caso de no poder realizar la operación anteriormente explicada, nos
     * aparecerá la excepción indicando que hay un error al crear la clave.
     * @param tamanyo
     * @param contrasenya
     * @return 
     */
    public SecretKey generarClau(int tamanyo, String contrasenya) {
        //Controla la llargaria de la clau
        if ((tamanyo == 128) || (tamanyo == 192) || (tamanyo == 256)) {
            try {
                byte[] data = contrasenya.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, tamanyo / 8);
                System.out.println(Arrays.toString(data));
                System.out.println(Arrays.toString(hash));
                System.out.println(Arrays.toString(key));
                skey = new SecretKeySpec(key, "AES");
            } catch (Exception ex) {
                System.err.println("Error generant la clau: " + ex);
            }
        }
        return skey;
    }

    /**
     * Este método descifra un fichero. Le llega por parametro el ficheroCifrado, la SecretKey,
     * y el nombre del ficheroDesencriptado (El nombre que tendrá el nuevo fichero). Creamos el
     * FileInputStream con el ficheroEncriptado para leer y el FileOutputStream para escribir en
     * el nuevo fichero que creamos. Usaremos el Cipher usando el algoritmo y le decimos a
     * c.init que esté en modo DECRYPT, clave que usará para desencriptar. Dspués haremos un bucle
     * que recorra el fichero encriptado para finalmente escribir el contenido desencriptado en
     * el nuevo fichero creado.
     * Si no establecemos los bytesLeidos, a la hora de descifrar el fichero no nos aparecerá el contenido.
     * @param fitxerEncriptat
     * @param clave
     * @param fitxerDesencriptat 
     */
    public void desxifrarFixer(String fitxerEncriptat, SecretKey clave, String fitxerDesencriptat) {
        try {
            FileInputStream fis = new FileInputStream(fitxerEncriptat);
            FileOutputStream fos = new FileOutputStream(new File(fitxerDesencriptat));
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, clave);
            byte[] buffer = new byte[1024];
            int bytesLeidos;
            while ((bytesLeidos = fis.read(buffer, 0, buffer.length)) != -1) {
                c.update(buffer, 0, bytesLeidos);
            }
            fos.write(c.doFinal());
        } catch (Exception e) {
        }
    }

    /**
     * Este método cifra un fichero con una SecretKey. Creamos como en el método anterior un
     * FileInputStream con el fichero que le llega por parametro y un FileOutputStream que cree
     * un nuevo fichero, que será el ficheroCifrado usando el algoritmo y diciendole a c.init que
     * use esta vez el modo ENCRYPT. Después haremos lo mismo que en el método anterior, que lea el fichero
     * y que escriba en el otro.
     * @param fitxer
     * @param clau
     * @throws FileNotFoundException 
     */
    public void xifrarFitxer(String fitxer, SecretKey clau) throws FileNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(fitxer);
            FileOutputStream fos = new FileOutputStream(new File("ficheroCifrado.txt"));
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, clau);
            byte[] buffer = new byte[1024];
            int bytesLeidos;
            while ((bytesLeidos = fis.read(buffer, 0, buffer.length)) != -1) {
                c.update(buffer, 0 , bytesLeidos);
            }
            fos.write(c.doFinal());
        } catch (Exception e) {
        }
    }
}
