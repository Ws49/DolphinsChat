package View;

import javax.swing.JOptionPane;

import Controll.Controll;

public class ViewClient extends View{
    @Override
    public void setControll(Controll controll) {
        this.controll = controll;
    }

    public String setName(){
        return String.valueOf(JOptionPane.showInputDialog(null, "Digite seu nome no chat : ", "Name:", JOptionPane.QUESTION_MESSAGE));
    }

    public String setHost(){
       return String.valueOf(JOptionPane.showInputDialog(null, "Digite o host do servidor : ", "Name:", JOptionPane.QUESTION_MESSAGE));
    }

    public int setPort(){
       return Integer.valueOf(JOptionPane.showInputDialog(null, "Digite a porta do servidor : ", "Name:", JOptionPane.QUESTION_MESSAGE));
    }

}
