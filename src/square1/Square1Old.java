package square1;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Square1Old
 * Class to provide the main routine for Square-1
 * @author Kenneth Evans, Jr.
 */
public class Square1Old  {
  public Square1Old() {
    JFrame frame = new Square1();
    
    // Set window decorations
    JFrame.setDefaultLookAndFeelDecorated(true);
    
    // Make it exit when the window manager close button is clicked
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    // Locate it on the screen
//    frame.setLocationRelativeTo(null);
    frame.setLocation(25, 25);
    
    // Display the Square1
    //frame.pack();
    frame.setVisible(true);
  }

  /**
   * Main routine
   * @param args
   */
  public static void main(String[] args) {
    try {
      // Set the native loook and feel
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      
      // Make the job run in the AWT thread
      SwingUtilities.invokeLater(new Runnable () {
        public void run() {
          // Start a Square1Old, which will start a Square1
          new Square1Old();
       }});
    } catch(Throwable t) {
      t.printStackTrace();
    }
  }
}
