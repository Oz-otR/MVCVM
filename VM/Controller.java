package groupAssignment2;

public class Controller {
	static private MyConfigDialog Config;
    static private MyControlDialog Control;
    static private int x, y;
    
    public static void main(String[] args) {
        View theView = new View("VM Simulation");
        MyWindowListener aWindowListener = new MyWindowListener();
        theView.addWindowListener(aWindowListener);
        
        //M theModel = new M();

        //C thisController = new C(theView, theModel);

        //theView.setSize(WIDTH,HEIGHT);
        theView.pack();
        theView.setLocationRelativeTo(null);

        // Creates Dialog on the side
        //Dialog = new MyDialog(theModel);
        Config = new MyConfigDialog();
        Control = new MyControlDialog();
        
        Config.setDefaultCloseOperation(MyConfigDialog.DO_NOTHING_ON_CLOSE);
        Control.setDefaultCloseOperation(MyControlDialog.DO_NOTHING_ON_CLOSE);

        Config.setSize(450, 150);
        Control.setSize(180, 175);
        x = theView.getX() + theView.getWidth();
        y = theView.getHeight() - Config.getHeight();

        Config.setLocation(x, y);
        Config.setUndecorated(true);
        Config.setVisible(true);
        Control.setLocation(x, y - 420);
        Control.setUndecorated(true);
        Control.setVisible(true);

        theView.setVisible(true);

    }
}
