import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class EchoClient {
  public static void main(String args[]){
      System.out.println("Spinng up the Echo Client in Java...");
      try{
          final Socket socket = new Socket("localhost", 1234);
          final BufferedReader inputFromServer = new BufferedReader(
              new InputStreamReader(socket.getInputStream())
          );
          final BufferedReader cliInput  = new BufferedReader(new InputStreamReader(
              System.in
          ));
          System.out.println("Waiting on input from the user...");
          String inputFromUser = cliInput.readLine();
          if(inputFromUser != null){
              System.out.println("Received by Java: " + inputFromUser);
              TransmissionObject transmissionObject = new TransmissionObject();
              transmissionObject.message = inputFromUser;
              transmissionObject.value = 3.145f;
              GsonBuilder builder = new GsonBuilder();
              Gson gson = builder.create();

              PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
              writer.println(gson.toJson(transmissionObject));
              System.out.println(inputFromServer.readLine());
          }
          socket.close();

      }catch(Exception ex){
          System.err.println("Error: " + ex);
      }
  }
}