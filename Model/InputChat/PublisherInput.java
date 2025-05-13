package Model.InputChat;


import java.util.ArrayList;
import java.util.Collection;

public class PublisherInput{
    Collection<ListenerInput> listeners;

    public PublisherInput(){
        this.listeners = new ArrayList<ListenerInput>();
    }

    public void addListener(ListenerInput listener){
        listeners.add(listener);
    }
    public void removeListener(ListenerInput listener){
        listeners.remove(listener);
    }
    public void updateMessage(String message, int id){
        listeners.forEach((listener)->{
            listener.setInputMessage(message,id);
        });
    }

    public void updateWaring(Exception e){
        listeners.forEach((listener)->{
            listener.isWaringInput(e);
        });
    }
}