import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class TCPServer {

    public final static int FILE_SIZE = 19022386;
    public final static String FILE_TO_RECEIVED = "assets/source-downloaded.jpg";

    public static void main (String args[]){

        try {
            ServerSocket server = new ServerSocket(3000);

            System.out.println("Porta = "+server.getLocalPort());

            while(true) {
                System.out.println("Esperando...");

                Socket cliente = server.accept();
                System.out.println("Client " + cliente.getInetAddress().getHostAddress() + " connected on " + server.getLocalPort());

                byte [] mybytearray  = new byte [FILE_SIZE];
                InputStream in = cliente.getInputStream();
                FileOutputStream fos = new FileOutputStream(FILE_TO_RECEIVED);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                int bytesRead = in.read(mybytearray,0,mybytearray.length);
                int current = bytesRead;

                do {
                    bytesRead =
                            in.read(mybytearray, current, (mybytearray.length-current));
                    if(bytesRead >= 0) current += bytesRead;
                } while(bytesRead > -1);

                bos.write(mybytearray, 0 , current);
                bos.flush();

                System.out.println("File " + FILE_TO_RECEIVED
                        + " downloaded (" + current + " bytes read)");



                fos.close();
                bos.close();
                cliente.close();
                System.out.println("Conexão Encerrada");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

