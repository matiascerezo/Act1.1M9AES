package act1.pkg1.aes;

import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.crypto.SecretKey;

/**
 *
 * @author Matias
 */
public class TestAES {

    public static void main(String[] args) throws FileNotFoundException {
        SeguretatAES saes = new SeguretatAES();
        //Recogemos el tamaño de la contraseña
        System.out.print("Tamaño de la contraseña:");
        int tamanyo = new Scanner(System.in).nextInt();

        //Generamos la clave
        SecretKey key = saes.generarClau(tamanyo, "123456");
        //Ciframos el archivo "fichero.txt" y creamos uno nuevo (FicheroCifrado.txt"
        //saes.xifrarFitxer("fichero.txt", key);

        //Desciframos el fichero cifrado y creamos un ficheroDescifrado.txt.
        saes.desxifrarFixer("ficheroCifrado.txt", key, "ficheroDescifrado.txt");
    }
}
