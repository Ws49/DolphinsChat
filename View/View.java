package View;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Controll.Controll;

public abstract class View extends JFrame implements KeyListener{

    protected JButton button;
    protected JTextField inputField;
    protected JPanel userArea;
    protected JScrollPane scrollUsers;
    protected JLabel labelUsers; 
    protected JPanel messageArea;
    protected JScrollPane scrollMessages;
    protected Controll controll;
    protected HashMap<Integer, JLabel> usersLabel;


    public View(){

        setSize(1024,720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setTitle("Dolphins_Chat");
        
        button = new JButton("Send");
        button.setBounds(800,600,200,50);
        
        inputField = new JTextField(20);
        inputField.setBounds(5,600,780,50);
        
        userArea = new JPanel();
        userArea.setLayout(new BoxLayout(userArea, BoxLayout.Y_AXIS));
        userArea.setBounds(800,10,200,580);
        
        scrollUsers = new JScrollPane(userArea);
        scrollUsers.setBounds(800,50,200,500);

        labelUsers = new JLabel("Users");
        labelUsers.setBounds(880,0,200,60);
        
        messageArea = new JPanel();
        messageArea.setLayout(new BoxLayout(messageArea, BoxLayout.Y_AXIS));
        messageArea.setBounds(5,10,700,580);
        
        scrollMessages = new JScrollPane(messageArea);
        scrollMessages.setBounds(5,50,700,500);
        

        inputField.addKeyListener(this);
        usersLabel = new HashMap<Integer,JLabel>();

        add(button);
        add(inputField);
        add(scrollUsers);
        add(scrollMessages);
        add(labelUsers);
        setVisible(true);
   
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInputField();
            }
        });
        
    }

    
    public abstract void setControll(Controll controll);
    
    public void addMessage(JLabel message){
        messageArea.add(message);
        setVisible(true);
    }

    public void addUser(JLabel userLabel, int id){
        usersLabel.put(id, userLabel);
        userArea.add(usersLabel.get(id));
        setVisible(true);
    }

    public void updateInputField(){
        if(inputField.getText() != null || inputField.getText().equals("") == false){
            controll.sendMessage(inputField.getText());
            addMessage(new JLabel(controll.getName() + " : " + inputField.getText()));
            inputField.setText("");
        }else{
            inputField.setText("null Text!");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_ENTER){
        updateInputField();
       }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }

    public void waring(Exception e){
        JOptionPane.showMessageDialog(null,"Error: " + e.getMessage() ,"ERROR",JOptionPane.INFORMATION_MESSAGE);
    }
    
}
