import Controll.Controll;
import Model.Client;
import View.ViewClient;

public class MainClient {
    public static void main(String[] args) {
        try {
            ViewClient view  = new ViewClient();
            new Controll(view, new Client(view.setName(), view.setHost(),view.setPort()));
        } catch (Exception e) {
          e.printStackTrace();
        }
    }

}
