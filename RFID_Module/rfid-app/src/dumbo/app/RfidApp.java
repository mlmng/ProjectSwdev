package dumbo.app;

import java.io.IOException;


public class RfidApp{

    public static void main(String[] args) throws IOException{
        new RfidApp().start();
       
    }
    public void start() throws IOException{

    	
    	Controller controller = new Controller();
		View view = new View(controller);
		view.show();
		controller.begin();
		
		System.out.println(controller.getRfid());
    }
    
}