package Model;
import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import Controll.Controll;
import Model.InputChat.InputChat;
import Model.InputChat.ListenerInput;
import Model.InputChat.PublisherInput;
import Model.OutputChat.OutputChat;
import Model.OutputChat.PublisherOutput;

class ClientServer implements Runnable, ListenerInput {
    private OutputChat out;
    private Thread tOutput;
    private InputChat input;
    private Thread tInput;
    private int id;
    private String name;
    private Color color;
    private Socket socket;
    PublisherInput publisherInput;
    public ClientServer(int id, Socket socket, PublisherInput publisherInput, PublisherOutput publisherOutput)throws IOException {
        this.id = id;
        this.socket = socket;
        this.publisherInput = publisherInput;
        publisherInput.addListener(this);
        input = new InputChat(id, socket, publisherInput);
        tInput = new Thread(input);
        out = new OutputChat(socket, publisherOutput);
        tOutput = new Thread(out);
        color = new Color(new Random().nextInt(10,100),new Random().nextInt(10,255),new Random().nextInt(10,255));
    }

    @Override
    public void run() {
        tInput.start();
        tOutput.start();
    }


    public Color getColor() {
        return color;
    }

    @Override
    public void setInputMessage(String message, int id) {
        if (id != this.id) {
                try {
                    getOut().setOutputMessage(message);
                } catch (Exception e) {

                }
        }
    }

    public int getId() {
        return id;
    }

    public OutputChat getOut() {
        return out;
    }

    public void setName(String name){
        this.name = name;
    }

    public String  getName() {
        return name;
    }

    @Override
    public void isWaringInput(Exception e) {
    }

    public void closeConnection() throws IOException{
        tInput.interrupt();
        tOutput.interrupt();
        this.publisherInput.removeListener(this);
        this.socket.close();
    }

}

public class Server extends Model{
    private ServerSocket server;
    private ArrayList<ClientServer> clientServers;
    private ClientServer clientRemove;


    private Controll controll;

    public Server(String name, int port) throws IOException {
        super(name);
        server = new ServerSocket(port);
        publisherInput.addListener(this);
        publisherOutput.addListener(this);
        clientServers = new ArrayList<ClientServer>();
    }

    public void setControll(Controll controll) {
        this.controll = controll;
    }
    
    public void setServer(int port) throws IOException{
        this.server = new ServerSocket(port);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void sendAll(String message) {
        clientServers.forEach((client) -> {
            try {
                client.getOut().setOutputMessage(this.name + ": " + message);
            } catch (Exception e) {
                isWaringOutput(e);
            }
        });
    }

    public void notifyUsers(ClientServer client){

        clientServers.forEach(clientServ->{
            try {
               if(client.getId() != clientServ.getId()){
                client.getOut().setOutputMessage("DELIMITER"+clientServ.getName()+"DELIMITER");
               }
           } catch (Exception e) {
                isWaringOutput(e);
            }
        });

    }

    public void run() throws Exception {
        
        int identifiers = 0;

        while (true) {
            ClientServer client = new ClientServer(identifiers, server.accept(), publisherInput, publisherOutput);
            clientServers.add(client);
            notifyUsers(client);
            Thread clientThread = new Thread(client);
            clientThread.start();
            identifiers++;
        }
    }

    public void removeClient(int id){
        clientServers.forEach((client)->{
            if(client.getId() == id){
                clientRemove = client;
            }
        });
        
        try {
            clientRemove.closeConnection();
        } catch (Exception e) {
           controll.waring(e);
        }
        
    }

    @Override
    public void setInputMessage(String message, int id) {
        if (!message.startsWith("DELIMITER")) {
            controll.setMessage(message,clientServers.get(id).getColor());
        } else {
            clientServers.get(id).setName(message.split("DELIMITER")[1]);
            controll.setMessage("New Client: " + clientServers.get(id).getName(),clientServers.get(id).getColor());
            controll.addUser(clientServers.get(id).getName(), clientServers.get(id).getColor(),id);
        }
    }

    @Override
    public void isWaringInput(Exception e) {
        controll.waring(e);
    }

    @Override
    public void isWaringOutput(Exception e) {
        controll.waring(e);
    }

}
