
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jairo Martínez
 */
public class ThreadServer extends Thread {
    private PrintWriter enviar = null;
    private BufferedReader recibir = null;
    private Server servidor;
    private Socket cliente;

    /**
     * HiloServidor builder of thread type.
     * 
     * Constructor de la clase HiloServidor de tipo Thread.
     * 
     * @param cliente Parametro con el que indicamos el Socket al que nos vamos a conectar
     * @param servidor Parametro que utilizamos para recoger el objeto Servidor.
     */
    public ThreadServer(Socket cliente, Server servidor){
        this.cliente = cliente;
        this.servidor = servidor;
        
        try {
            this.enviar = new PrintWriter(this.cliente.getOutputStream(),true);
            this.recibir = new BufferedReader (new InputStreamReader(this.cliente.getInputStream()));
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Run method that execute the thread's functionalities.
     * 
     * Método run que ejecuta las funcionalidades del hilo
     */
    @Override
    public void run(){
        try {
            
            String comandoRecibido = this.recibir.readLine();
            this.comandoEjecutar(Integer.parseInt(comandoRecibido));
            
        } catch (IOException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Manage the received orders from the server and execute the corresponding actions.
     * 
     * Gestiona las ordenes recibidas desde el servidor y ejecuta las acciones necesarias.
     * 
     * @param comando Parametro que recoge un valor int que representa una orden del servidor.
     */
    private void comandoEjecutar (int comando){
        try{
        String orden;
        if(comando == 0){
            orden = String.format("Stock de chirimoyas  es %d",this.servidor.getStockChirimoyas());
            this.enviar.println(orden);
        }else{
            if(this.servidor.modificarStock(comando)){
                orden = String.format("Stock modificado: %d", this.servidor.getStockChirimoyas());
                this.servidor.getStockChirimoyas();
            }else{
                orden = String.format("No se puede realizar la modificacion, la cantidad de stock es: %d",
                        this.servidor.getStockChirimoyas());
            }
        }   this.enviar.println(orden);
        }catch(Exception e){
            System.out.println("ERROR: No se ha enviado el mensaje");
            System.out.println(e.getMessage());
        }
    }
    /**
     * Return the corresponding client to the connection.
     * 
     * Devolvemos el cliente que corresponde a la conexion.
     * @return 
     */
    public Socket getCliente(){
        return this.cliente;
    }
}
