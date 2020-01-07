
package psp_global;

import Client.Client;
import Server.Server;
import java.util.Scanner;

/**
 *
 * @author Jairo Martínez
 */
public class PSP_Global {


    public static void main(String[] args) {
        
        //Certificados seguros del cliente
        System.setProperty("javax.net.ssl.trustStore","clientTrustedCerts.jks");
        System.setProperty("java.net.ssl.trustStorePassword", "claveClienteSecreta");
        
        //Certificados seguros del servidor.
        System.setProperty("javax.net.ssl.keyStore","serverKey.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "claveSecreta");
        
        //Iniciamos el programa
        PSP_Global programa = new PSP_Global();
        programa.iniciar();
    }
    
    /**
     * Lanzamos la ejecución del programa principal que
     * nos permitirá elegir entre cliente y servidor.
     */
    public void iniciar(){
    
        this.datosAlumno();
        System.out.println("Seleccione programa a ejecutar: ");
        System.out.println("1 - Cliente");
        System.out.println("2 - Servidor");
        System.out.println("Introduzca número asociado: ");
        Scanner entrada = new Scanner (System.in);
        String opcion = entrada.nextLine();
        
        switch(opcion){
            case "1":
                new Client();
                break;
            case "2":
                new Server();
                break;
            default:
                System.out.println("Ha introducido una opción no válida.");
                break;
        }
        
    }
    /**
     * Show the alumns and task information.
     * Mostramos la información del alumno y la tarea.
     */
    public void datosAlumno(){
        System.out.println("********************************************************\n" +
                           "\n" +
                           "* PSP - Tarea Global - Almacén multiusuario             *\n" +
                           "\n" +
                           "********************************************************\n" +
                           "\n" +
                           "* Jairo Martínez Garrido                                *\n" +
                           "\n" +
                           "********************************************************\n" +
                           "\n" +
                           "* GLOBAL EXERCISE                                      *\n" +
                           "\n" +
                           "******************************************************** \n");
                                }
    
}
