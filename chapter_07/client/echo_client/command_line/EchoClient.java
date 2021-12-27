import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import transmission_object.TransmissionObjectOuterClass.TransmissionObject;
public class EchoClient {
  public static void main(String args[]){
      System.out.println("Spinng up the Echo Client in Java...");
      try{
          final Socket socket = new Socket("localhost", 1234);

          final BufferedReader cliInput  = new BufferedReader(new InputStreamReader(
              System.in
          ));
          System.out.println("Waiting on input from the user...");
          String inputFromUser = cliInput.readLine();
          if(inputFromUser != null){
              System.out.println("Received by Java: " + inputFromUser);
              TransmissionObject transmissionObject =  TransmissionObject.newBuilder()
                .setMessage(inputFromUser)
                .setValue(3.145f)
                .build();

              transmissionObject.writeTo(socket.getOutputStream());
              TransmissionObject receivedObject = TransmissionObject.parseFrom(socket.getInputStream());
              System.out.println("Received Message from server: ");
              System.out.println(receivedObject);
          }
          socket.close();

      }catch(Exception ex){
          System.err.println("Error: " + ex);
      }
  }
}