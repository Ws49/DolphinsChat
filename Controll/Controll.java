package Controll;

import java.awt.Color;

import javax.swing.JLabel;

import Model.Client;
import Model.Model;
import Model.Server;
import View.View;

public class Controll {
    Model model;
    View view;

    public Controll(View view, Model model){
        this.model = model;
        this.view = view;
        this.model.setControll(this);
        this.view.setControll(this);

        try {
            this.model.run(); 
        } catch (Exception e) {
            waring(e);
        }
    }

    public void setHost(String host, int port){
        if(model instanceof Server){
            try {
                ((Server)model).setServer(port);
            } catch (Exception e) {
                waring(e);
            }
        }else{
            try {
               ((Client)model).setSocket(host, port);
            } catch (Exception e) {
                waring(e);
            }
        }
    }

    public void setName(String name){
        this.model.setName(name);
    }

    public String getName(){
        return this.model.getName();
    }

    public void setMessage(String message, Color color){
        if(message != null){
            JLabel messageLabel = new JLabel(message);
            messageLabel.setForeground(color);
            view.addMessage(messageLabel);
        }else{
            waring(new Exception("Message null"));
        }
    }

    public void addUser(String name, Color color,int id){
        JLabel userLabel = new JLabel(name);
        userLabel.setForeground(color);
        view.addUser(userLabel,id);
    }

    public void removeUser(int id){
        if(model instanceof Server){
            ((Server)model).removeClient(id);
        }
            
    }

    public void sendMessage(String message){
        if(message != null){
            model.sendAll(message);
        }else{
            waring(new Exception("Message null"));
        }

    }

    public void waring(Exception e){
        System.out.println(e.getMessage());
    }
}
