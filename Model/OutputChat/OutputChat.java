package Model.OutputChat;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OutputChat implements Runnable{
    private Socket socket;
    private ObjectOutputStream output;
    private String message;
    private PublisherOutput publisher;

    public OutputChat(Socket socket, PublisherOutput publisherOutput) throws IOException {
        this.socket = socket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.publisher = publisherOutput;
        this.message = "";
    }



    public void setOutputMessage(String message)  throws IOException{
        output.writeObject(message);   
        this.message = message;
    }

    @Override
    public void run() {
        try{
            while(message.equals("exit")){
                System.exit(0);
            }
        }catch(Exception e){
            publisher.updateWaring(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }


}
