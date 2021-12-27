import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import transmission_object.TransmissionObjectOuterClass.TransmissionObject;
import transceiver.TransceiverOuterClass.EchoRequest;
import transceiver.TransceiverOuterClass.EchoResponse;
import transceiver.TransceiverOuterClass.UpperCaseRequest;
import transceiver.TransceiverOuterClass.UpperCaseResponse;
import transceiver.TransceiverGrpc;

//transceiver.TransceiverOuterClass.EchoRequest
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
              ManagedChannel channel = ManagedChannelBuilder
                       .forAddress("localhost",1234)
                       .usePlaintext()
                       .build();
              TransceiverGrpc.TransceiverBlockingStub stub = TransceiverGrpc.newBlockingStub(channel);
              EchoRequest request = EchoRequest.newBuilder()
                                     .setFromClient(
                                        TransmissionObject.newBuilder()
                                             .setMessage(inputFromUser)
                                             .setValue(3.145f)
                                             .build()
                                     )
                                     .build();
              
              EchoResponse response = stub.echo(request);

              System.out.println("Received Message from server: ");
              System.out.println(response);

              UpperCaseRequest upperCaseRequest = UpperCaseRequest.newBuilder()
                                     .setOriginal(inputFromUser).build();
              UpperCaseResponse upperCaseResp = stub.upperCase(upperCaseRequest);
              System.out.println("Received upper cased:");
              System.out.println(upperCaseResp);
              channel.shutdownNow();
          }
          socket.close();

      }catch(Exception ex){
          System.err.println("Error: " + ex);
      }
  }
}