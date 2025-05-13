package View;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Controll.Controll;


public class ViewServer extends View{

    private HashMap<Integer, JButton> btnsRemoveUsers;

    public ViewServer(){
        btnsRemoveUsers = new HashMap<Integer, JButton>();
    }



    @Override
    public void addUser(JLabel userLabel, int id){
        super.addUser(userLabel, id);
        JButton btn = new JButton("x");
        btn.setSize(20,20);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controll.removeUser(id);
                userArea.remove(usersLabel.remove(id));
                setVisible(true);
                userArea.remove(btnsRemoveUsers.remove(id));
            }
        });
        btnsRemoveUsers.put(id, btn);
        userArea.add(btnsRemoveUsers.get(id));
        setVisible(true);
    }



    @Override
    public void setControll(Controll controll) {
        this.controll = controll;
        controll.setName(String.valueOf(JOptionPane.showInputDialog(null, "Digite seu nome no chat : ", "Name:", JOptionPane.QUESTION_MESSAGE)));
        controll.setHost("",Integer.valueOf(JOptionPane.showInputDialog(null, "Digite a porta do servidor : ", "Name:", JOptionPane.QUESTION_MESSAGE)));
    }

}
