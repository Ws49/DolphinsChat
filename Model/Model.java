package Model;

import Controll.Controll;
import Model.InputChat.ListenerInput;
import Model.InputChat.PublisherInput;
import Model.OutputChat.ListenerOutput;
import Model.OutputChat.PublisherOutput;

public abstract class Model implements ListenerInput, ListenerOutput{
    PublisherInput publisherInput;
    PublisherOutput publisherOutput;
    String name;

    public Model(String name){
        publisherInput = new PublisherInput();
        publisherOutput = new PublisherOutput();
        this.name = name;
    }

    public abstract void setControll(Controll controll);

    public abstract String getName();

    public abstract  void setName(String name);

    public abstract void sendAll(String message);

    public abstract void run() throws Exception;
}
