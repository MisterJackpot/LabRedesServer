import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

    public final static int FILE_SIZE = 6022386;
    public final static String FILE_TO_RECEIVED = "assets/source-downloaded.jpg";

    public static void main(String args[])  throws Exception
    {
        // cria socket do servidor com a porta 3000
        DatagramSocket serverSocket = new DatagramSocket(3000);


        byte [] mybytearray  = new byte [FILE_SIZE];
        byte[] bytes = new byte [10];

        while(true)
        {
            System.out.println("Esperando...");

            //Recebe Numero de Pacotes
            DatagramPacket receiveSize = new DatagramPacket(bytes, bytes.length);
            serverSocket.receive(receiveSize);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
            DataInputStream dataIn = new DataInputStream(byteIn);
            int size = dataIn.readInt();


            FileOutputStream fos = new FileOutputStream(FILE_TO_RECEIVED);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            //Recebe Pacotes
            for (int i = 0; i<size; i++){
                DatagramPacket receivePacket = new DatagramPacket(mybytearray, mybytearray.length);
                serverSocket.receive(receivePacket);
                mybytearray = receivePacket.getData();
                bos.write(mybytearray, i , receivePacket.getLength());
                System.out.println(i + " File " + FILE_TO_RECEIVED
                        + " downloaded (" + receivePacket.getLength() + " bytes read)");
            }

            bos.flush();

            fos.close();
            bos.close();
        }
    }
}
