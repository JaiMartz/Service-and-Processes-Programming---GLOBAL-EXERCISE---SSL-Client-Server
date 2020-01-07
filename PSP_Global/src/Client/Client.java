
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Jairo Martínez
 */
public class Client {
    //Atributos para la conexion
    private static final String IPAddress = "127.0.0.1";
    private static final int Port = 9091;
    //Introduccion de datos
    private String opcion;
    private int orden;
    public Scanner entrada = new Scanner(System.in);
    //Gestion del bucle
    public boolean seguir = true;
    
    /**
     * Client class builder.
     * 
     * Constructor de la clase Client
     */
    public Client(){
    
        while(seguir){
            this.MenuCliente();
            System.out.println("Introduzca la accion a realizar: ");
            opcion = entrada.nextLine();
            try{
            switch(opcion){
                case "1":
                    orden = 0;
                    this.conexionServer(orden, IPAddress, Port);
                    break;
                case "2":
                    System.out.println("Introduzca la cantidad que desea añadir: ");
                    orden = Integer.parseInt(entrada.nextLine());
                    if(orden >0){
                        this.conexionServer(orden, IPAddress, Port);
                    }else{
                        System.out.println("La cantidad a retirar debe ser positiva");
                    }
                    break;
                case "3":
                    System.out.println("Introduce la cantidad que deseas retirar: ");
                    orden = -Integer.parseInt(entrada.nextLine());
                    if(orden < 0){
                        this.conexionServer(orden, IPAddress, Port);
                    }else{
                        System.out.println("La cantidad a retirar debe ser positiva");
                    }
                    break;
                case "4":
                    System.out.println("Saliendo...");
                    seguir = false;
                    break;
                default:
                    System.out.println("Has elegido una opción no valida.");
                }
            }catch(NumberFormatException ex){
                    System.out.println("Los caracteres introducidos deben de ser de tipo numérico.");
                    System.out.println(ex.getStackTrace());
            }
        }
    
    }
    /**
     * Print the client meny by console.
     * 
     * Imprime el menú del cliente por consola
     * 
     */
    public void MenuCliente(){
        System.out.println("\n********************************");
        System.out.println("1 - Consultar stock");
        System.out.println("2 - Añadir stock de chirimoyas");
        System.out.println("3 - Retirar stock de chirimoyas");
        System.out.println("4 - Salir");
        System.out.println("********************************\n");
    }
    /**
     * Realize the connection.
     * 
     * Realiza la conexion.
     * @param orden Parametro que el usuario inserta para elegir su accion.
     * @param IPAddress Direction IP a la que deseamos conectarnos.
     * @param Port Puerto al cual deseamos conectarnos.
     */
    private void conexionServer(int orden, String IPAddress, int Port){
        //Creamos sockets seguros
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket conexion;
        
        BufferedReader recibir = null;
        PrintWriter enviar =null;
        try {
            
            conexion = (SSLSocket) sf.createSocket(IPAddress,Port);
            recibir = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            enviar = new PrintWriter(conexion.getOutputStream(), true);
            
            enviar.println(orden);
            System.out.println(recibir.readLine());
            
            //Cerramos flujos
            conexion.close();
            recibir.close();
            enviar.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Get method that return the order.
     * 
     * Metodo get que nos devuelve la orden.
     * @return 
     */
    public int getOrden(){
        return this.orden;
    }
}
