package Model.OutputChat;

import java.util.ArrayList;
import java.util.Collection;

public class PublisherOutput {
    Collection<ListenerOutput> listenerOutputs;
    
    public PublisherOutput(){
        listenerOutputs = new ArrayList<ListenerOutput>();
    }

    public void addListener(ListenerOutput listener){
        listenerOutputs.add(listener);
    }

    public void removeListener(ListenerOutput listener){
        listenerOutputs.remove(listener);
    }

    public void updateWaring(Exception e){
        listenerOutputs.forEach((listener)->{
            listener.isWaringOutput(e);
        });
    }
}
