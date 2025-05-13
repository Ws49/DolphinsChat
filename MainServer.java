import Controll.Controll;
import Model.Server;
import View.ViewServer;

public class MainServer {
    public static void main(String[] args) {
        try {
            new Controll(new ViewServer(), new Server("Server", 444));    
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
}
