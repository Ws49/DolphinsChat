package Model;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

import Controll.Controll;
import Model.InputChat.InputChat;
import Model.OutputChat.OutputChat;



public class Client extends Model{
    String message;
    Socket client;
    InputChat input;
    Thread tInput;
    OutputChat out;
    Thread tOutput;
    Controll controll;
    HashMap<Integer, Color> messagesRecv;

    public Client(String name, String host, int port) throws IOException {
        super(name);
        publisherInput.addListener(this);
        publisherOutput.addListener(this);

        client = new Socket(host, port);
        input = new InputChat(0,client, publisherInput);
        tInput = new Thread(input);
        out =  new OutputChat(client, publisherOutput);
        tOutput = new Thread(out);
        out.setOutputMessage("DELIMITER"+ name+"DELIMITER");

        messagesRecv = new HashMap<Integer, Color>();

    }

    public  void run(){
        try {
            tInput.start();
            tOutput.start();
        } catch (Exception e) {
            controll.waring(e);
        }
    }

    public void setSocket(String host, int port)throws IOException{
        client = new Socket(host, port);
    }

    @Override
    public void setInputMessage(String message, int id) {
        if (!message.startsWith("DELIMITER")) {
            controll.setMessage(message,messagesRecv.get(id));
        } else {
            messagesRecv.put(id,new Color(new Random().nextInt(10,100),new Random().nextInt(10,255),new Random().nextInt(10,255)));
            controll.setMessage("New Client: " + message.split("DELIMITER")[1],messagesRecv.get(id));
            controll.addUser(message.split("DELIMITER")[1], messagesRecv.get(id),id);
        }
    }

    @Override
    public void isWaringInput(Exception e) {
        System.out.println("ERRO INPUT : " + e.getMessage());
    }
    @Override
    public void isWaringOutput(Exception e) {
        System.out.println("ERRO OUTPUT : " + e.getMessage());
    }

    @Override
    public void setControll(Controll controll) {
        this.controll = controll;
    }

    @Override
    public String getName() {
       return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void sendAll(String message) {
        try {
            out.setOutputMessage(name + ": " +message);
        } catch (Exception e) {
           isWaringOutput(e);
        }
     
    }

}
