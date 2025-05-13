package Model.InputChat;

import java.io.ObjectInputStream;
import java.net.Socket;

public class InputChat implements Runnable{
    private ObjectInputStream input;
    private Socket socket;
    private String message;
    private PublisherInput publisher;
    private int id;

    public InputChat(int id, Socket socket, PublisherInput publisherInput){
        this.id = id;
        this.socket = socket;
        this.publisher = publisherInput;
        this.message = null;
    }
    
    @Override
    public void run() {
        try{
            input = new ObjectInputStream(socket.getInputStream());
            while(true){
                String newMessage = (String) input.readObject();
                if(newMessage != message && newMessage != null){
                    publisher.updateMessage(newMessage,id);
                    message = newMessage;
                }
            }
            
        }catch(Exception e){
           publisher.updateWaring(e);
        }

    }

    public Socket getSocket() {
        return socket;
    }

    
}
