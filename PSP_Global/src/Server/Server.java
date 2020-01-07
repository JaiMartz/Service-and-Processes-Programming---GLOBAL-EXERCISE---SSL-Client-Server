/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.util.ArrayList;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;


/**
 *
 * @author Jairo Martínez
 */
public class Server {
    private int stockChirimoyas = 100;
    public ArrayList<ThreadServer> conexionesClientes = new ArrayList<>();
    
  /**
   * Servidor builder, that create the secure sockets.
   * 
   * Contructor de la clase Servidor que crea los sockets seguros para servidor
   */
  public Server(){
      try{
          SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
          SSLServerSocket servidor = (SSLServerSocket) ssf.createServerSocket(9091);
          //Nos mantenemos a la escucha para gestionar más conexiones a traves de hilos
          while(true){
            SSLSocket cliente =(SSLSocket) servidor.accept();
            this.conexionesClientes.add(new ThreadServer(cliente, this));
              System.out.println("Nuevo cliente creado.");
          }
      }catch(IOException ex){
          System.out.println(ex.getMessage());
      }
  }
  
  /**
   * Get the fruits stock.
   * 
   * Recuperamos el stock de chirimoyas.
   * @return Devuelve la cantidad de chirimoyas.
   */
    public int getStockChirimoyas(){
      return this.stockChirimoyas;
    }
    
  /**Method that add fruits to the current stock and evade a negative stock.
   * 
   * Método para añadir chirimoyas al stock evitando que sea negativo.
   * @param cantidad cantidad que introducimos para añadir al stock.
   * @return valor booleano que nos indica si se ah realizado la insercion de stock.
   */
    public synchronized boolean modificarStock(int cantidad){
        boolean añadido = false;
        if((cantidad < 0 && Math.abs(cantidad) < this.stockChirimoyas) || cantidad > 0){
          this.stockChirimoyas += cantidad;
          añadido = true;
        }
        return añadido;
    }
}
