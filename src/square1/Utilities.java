package square1;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * Utilities class for Square-1
 * @author Kenneth Evans, Jr.
 */
public class Utilities  {
  public Utilities() {
  }
  
  /**
   * Error message dialog
   * @param err
   */
  public static void errMsg(String err) {
    // Show it in a message box
    JOptionPane.showMessageDialog(null,err,
    "Error", JOptionPane.ERROR_MESSAGE);
  }
  
  /**
   * Generates a timestamp
   * @return String timestamp with the current time
   */
  public static String timeStamp() {
/*
    final SimpleDateFormat defaultFormatter= new SimpleDateFormat(
    "MMM dd, yyyy HH:mm:ss.SSS");
*/    
    final SimpleDateFormat defaultFormatter= new SimpleDateFormat(
    "MMM dd, yyyy HH:mm:ss");
    Date now = new Date();
    return defaultFormatter.format(now);
  }

}
