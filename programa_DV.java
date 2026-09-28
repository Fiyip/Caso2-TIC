import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileWriter;

public class programa_DV {
    public static void main(String[] args) {
        int filas = Integer.parseInt(args[0]);
        int columnas = Integer.parseInt(args[1]);
        int tamVector = Integer.parseInt(args[2]);
        int tamPagina = Integer.parseInt(args[3]);
        int numPasadas = Integer.parseInt(args[4]);
        String archivoSalida = (args[5]);
        generarReferencias(filas, columnas, tamVector, tamPagina,numPasadas, archivoSalida);
    }

    public static void generarReferencias(int filas, int columnas, int tamVector, int tamPagina, int numPasadas, String archivoSalida) {
        long tamMatriz = (long) filas * columnas;
        long baseVector = tamMatriz;
        long tamTotal = tamMatriz + tamVector;

        int numPaginas = (int) ((tamTotal + tamPagina - 1) / tamPagina); //numero de paginas que se necesitan para almacenar la matriz y el vector

        long numAccesos = 3L * 2 * filas * columnas * numPasadas; //numero de referencias que se van a generar
        
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(archivoSalida)))) {

            out.print("TP=" + tamPagina + "\r\n");
            out.print("NF=" + filas + "\r\n");
            out.print("NC=" + columnas + "\r\n");
            out.print("Tamaño vector clave=" + tamVector + "\r\n");
            out.print("numPasadas=" + numPasadas + "\r\n");
            out.print("NR=" + numAccesos + "\r\n");
            out.print("NP=" + numPaginas + "\r\n");

            for (int p = 0; p < numPasadas; p++) {
                for (int f = 0; f < filas; f++) {
                    for (int c = 0; c < columnas; c++) {
                        long dirMatriz = (long) f * columnas + c;
                        long dirVector = baseVector + (c % tamVector);
                        escribir(out, "mat1", f, c, dirMatriz, tamPagina);
                        escribir(out, "v", 0, c% tamVector, dirVector, tamPagina);
                        escribir(out, "mat1", f, c, dirMatriz, tamPagina);
                    }
                }
            for (int c=0; c<columnas; c++ ){
                for (int f=0; f< filas; f++){
                    long dirMatriz= (long) f*columnas + c;
                    long dirVector = baseVector + (f % tamVector);
                    escribir(out, "mat1", f, c, dirMatriz, tamPagina);
                    escribir(out, "v", 0, f%tamVector, dirVector, tamPagina);
                    escribir(out, "mat1", f, c, dirMatriz, tamPagina);
                }
            }
                
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        }


    private static void escribir(PrintWriter out, String nombre, int f, int c, long dir, int tamPagina) {
        long pagina = dir / tamPagina;
        long desplazamiento = dir % tamPagina;
        out.print("[" + nombre + "-" + f + "-" + c + "]," + pagina + "," + desplazamiento + "\r\n");
    }
}