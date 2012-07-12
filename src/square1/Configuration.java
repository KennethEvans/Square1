/*
 * Class to manage an entire configuration
 * Created on Dec 25, 2005
 * By Kenneth Evans, Jr.
 */

package square1;

import java.awt.Color;

/**
 * Configuration
 * @author Kenneth Evans, Jr.
 */
public class Configuration implements Constants
{
  private static final String sepChars="[ ,:;.|{}()<>/\\$^&*?_=]";
  private Block top[];
  private Block center;
  private Block bottom[]; 
  
  /**
   * Configuration constructor
   * @param top
   * @param center
   * @param bottom
   */
  public Configuration(Block[] top, Block center, Block[] bottom) {
    reset(top, center, bottom);
  }
  
  /**
   * Resets the Configuration to the new values.
   * @param top
   * @param center
   * @param bottom
   */
  public void reset(Block[] top, Block center, Block[] bottom) {
    this.top = new Block[12];
    System.arraycopy(top, 0, this.top, 0, 12);
    this.center = center;
    this.bottom = new Block[12];
    System.arraycopy(bottom, 0, this.bottom, 0, 12);
  }
  
  /**
   * Makes a default configuration (solved puzzle).
   * @return
   */
  public static Configuration defaultConfiguration() {
    return defaultConfiguration(null);    
  }
    
  /**
   * Makes a default configuration (solved puzzle), specifying a canvas.
   * @param canvas
   * @return
   */
  public static Configuration defaultConfiguration(Canvas canvas) {
    Block t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12;
    Block b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12;

    t1 = new Block(canvas, 1, Color.WHITE, Color.RED, 1);
    t2 = new Block(canvas, 2, Color.WHITE, Color.BLUE, 2);
    t3 = new Block(canvas, 0, Color.WHITE, Color.BLUE, 3);
    t4 = new Block(canvas, 1, Color.WHITE, Color.BLUE, 4);
    t5 = new Block(canvas, 2, Color.WHITE, orange, 5);
    t6 = new Block(canvas, 0, Color.WHITE, orange, 6);
    t7 = new Block(canvas, 1, Color.WHITE, orange, 7);
    t8 = new Block(canvas, 2, Color.WHITE, Color.YELLOW, 8);
    t9 = new Block(canvas, 0, Color.WHITE, Color.YELLOW, 9);
    t10 = new Block(canvas, 1, Color.WHITE, Color.YELLOW, 10);
    t11 = new Block(canvas, 2, Color.WHITE, Color.RED, 11);
    t12 = new Block(canvas, 0, Color.WHITE, Color.RED, 12);

    t1.setAdjacent(t2);
    t2.setAdjacent(t1);
    t3.setAdjacent((Block)null);
    t4.setAdjacent(t5);
    t5.setAdjacent(t4);
    t6.setAdjacent((Block)null);
    t7.setAdjacent(t8);
    t8.setAdjacent(t7);
    t9.setAdjacent((Block)null);
    t10.setAdjacent(t11);
    t11.setAdjacent(t10);
    t12.setAdjacent((Block)null);

    Block[] top = new Block[] {t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12};

    b1 = new Block(canvas, 1, Color.GREEN, orange, -1);
    b2 = new Block(canvas, 2, Color.GREEN, Color.BLUE, -2);
    b3 = new Block(canvas, 0, Color.GREEN, Color.BLUE, -3);
    b4 = new Block(canvas, 1, Color.GREEN, Color.BLUE, -4);
    b5 = new Block(canvas, 2, Color.GREEN, Color.RED, -5);
    b6 = new Block(canvas, 0, Color.GREEN, Color.RED, -6);
    b7 = new Block(canvas, 1, Color.GREEN, Color.RED, -7);
    b8 = new Block(canvas, 2, Color.GREEN, Color.YELLOW, -8);
    b9 = new Block(canvas, 0, Color.GREEN, Color.YELLOW, -9);
    b10 = new Block(canvas, 1, Color.GREEN, Color.YELLOW, -10);
    b11 = new Block(canvas, 2, Color.GREEN, orange, -11);
    b12 = new Block(canvas, 0, Color.GREEN, orange, -12);

    b1.setAdjacent(b2);
    b2.setAdjacent(b1);
    b3.setAdjacent((Block)null);
    b4.setAdjacent(b5);
    b5.setAdjacent(b4);
    b6.setAdjacent((Block)null);
    b7.setAdjacent(b8);
    b8.setAdjacent(b7);
    b9.setAdjacent((Block)null);
    b10.setAdjacent(b11);
    b11.setAdjacent(b10);
    b12.setAdjacent((Block)null);

    Block[] bottom = new Block[] {b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12};

    Block center = new Block(canvas, 3, Color.WHITE, Color.WHITE, 0);
    center.setCenterAligned(true);
    
    Configuration config = new Configuration(top, center, bottom);
    return config;
  }
  
  /**
   * Splits a transform into an array of single transforms. 
   * @param transform
   * @return The array of transforms
   */
  public static String[] splitTransform(String transform) {
    String tlist[]=transform.split(sepChars);
    return tlist;
  }

  /**
   * Does a transformation from a single String.
   * @param transform
   * @param reverse
   * @return
   * @throws InvalidOperationException
   */
  public Configuration transform(String transform, boolean reverse)
    throws InvalidOperationException {
    String tlist[]=splitTransform(transform);
    return transform(tlist, reverse);
  }
  
  /**
   * Does a transformation from an array of Strings.
   * @param tlist
   * @param reverse
   * @return
   * @throws InvalidOperationException
   */
  public Configuration transform(String[] tlist, boolean reverse)
    throws InvalidOperationException {
    int tlen = tlist.length;
    if(tlen <= 0) {
      throw new InvalidOperationException("Empty transformation");
    }
    
    // Loop over transformations
    for(int ii=0; ii < tlen; ii++) {
      int i;
      if(reverse) i=tlen-ii-1;
      else i=ii;
      String trans=tlist[i];
      int len=trans.length();
      if(len == 0) continue;
      String type=trans.substring(0,1);
      int number = 0;
      try {
        String numberString=trans.substring(1,len);
        if(len > 1) {
          number=Integer.parseInt(numberString);
        }
        if(reverse) number=-number;
      } catch(NumberFormatException ex) {
        // Do nothing
      }
      if(type.equalsIgnoreCase("t")) {
        if(number < -12 || number > 12) {
          throw new InvalidOperationException("Invalid rotation number (" +
            number + ")");
        }
        Block oldTop[]=new Block[12];
        System.arraycopy(top,0,oldTop,0,12);
        for(int j=0; j < 12; j++) {
          int j1=j+number;
          while(j1 > 11) j1-=12;
          while(j1 < 0) j1+=12;
          top[j]=oldTop[j1];
        }
      } else if(type.equalsIgnoreCase("b")) {
        if(number < -12 || number > 12) {
          throw new InvalidOperationException("Invalid rotation number (" +
            number + ")");
        }
        Block oldBottom[]=new Block[12];
        System.arraycopy(bottom,0,oldBottom,0,12);
        for(int j=0; j < 12; j++) {
          int j1=j+number;
          while(j1 < 0) j1+=12;
          while(j1 > 11) j1-=12;
          bottom[j]=oldBottom[j1];
        }
      } else if(type.equalsIgnoreCase("r")) {
        // Check if cannot be done owing to top
        if(top[11].getAdjacent() == top[0]
          || top[5].getAdjacent() == top[6]) {
          InvalidOperationException ex =
            new InvalidOperationException("Cannot do R "
            + "owing to top in step " + (ii + 1));
          ex.setNotR(true);
          ex.setTopBad(true);
//          ex.setPosition(ii + 1);
          throw ex;
        }
        // Check if cannot be done owing to bottom
        if(bottom[10].getAdjacent() == bottom[11]
          || bottom[4].getAdjacent() == bottom[5]) {
          InvalidOperationException ex =
            new InvalidOperationException("Cannot do R "
            + "owing to bottom in step " + (ii + 1));
          ex.setNotR(true);
          ex.setTopBad(false);
//          ex.setPosition(ii + 1);
          throw ex;
        }
        Block oldTop[]=new Block[12];
        System.arraycopy(top,0,oldTop,0,12);
        Block oldBottom[]=new Block[12];
        System.arraycopy(bottom,0,oldBottom,0,12);
        top[0]=oldBottom[11];
        top[1]=oldBottom[0];
        top[2]=oldBottom[1];
        top[3]=oldBottom[2];
        top[4]=oldBottom[3];
        top[5]=oldBottom[4];
        bottom[11]=oldTop[0];
        bottom[0]=oldTop[1];
        bottom[1]=oldTop[2];
        bottom[2]=oldTop[3];
        bottom[3]=oldTop[4];
        bottom[4]=oldTop[5];
        center.toggleCenterAligned();
      } else if(type.equalsIgnoreCase("") ||
        type.equalsIgnoreCase("!") ||
        type.equalsIgnoreCase("#") ||
        type.equalsIgnoreCase("%") ||
        type.equalsIgnoreCase("@")) {
        // Skip these (do nothing)
      } else {
        throw new InvalidOperationException("Invalid transform ("+
          type + ")\n" + "Must be T, B, or R");
      }
    }
    return new Configuration(top, center, bottom);
  }

  /**
   * Calculates the sum of the squares of the number part of the transforms
   * A lower number tends to indicate a simplier transform
   * @param string
   * @return Sum of the squares of the number parts
   */
  public static long movesSquared(String string) {
    string.toUpperCase();
    String tlist[] = string.split(sepChars);
    return movesSquared(tlist);
  }
  
  /**
   * Calculates the sum of the squares of the number part of the transforms
   * A lower number tends to indicate a simplier transform
   * @param string
   * @return Sum of the squares of the number parts
   */
  public static long movesSquared(String[] tlist) {
    int tlen = tlist.length;
    if(tlen <= 0) {
      return 0;
    }

    // Loop over transformations
    int sum = 0;
    String type0 = "";
    String type = type0;
    int number = 0;
    for(int i = 0; i < tlen; i++) {
      String trans = tlist[i].toUpperCase();
      int len = trans.length();
      if(len == 0) continue;
      type = trans.substring(0, 1);
      // Skip anything except T and B
      if(!type.equals("T") && !type.equals("B")) continue;
      String numberString = trans.substring(1, len);
      number = 0;
      try {
        number = Integer.parseInt(numberString);
      } catch(NumberFormatException ex) {
        number = 0;
      }
      sum += number*number;
    }
    return sum;
  }
  
  /**
   * Reduces a String transform to a compact form
   * @param string
   * @return Compacted transform
   */
  public static String packTransform(String string) {
    string.toUpperCase();
    String tlist[] = string.split(sepChars);
    return packTransform(tlist);
  }
  
  /**
   * Reduces a String[] transform to a compact form
   * @param tlist
   * @return Compacted transform
   */
  public static String packTransform(String[] tlist) {
    String newString = "";
    int tlen = tlist.length;
    if(tlen <= 0) {
      return newString;
    }

    // Loop over transformations
    String type0 = "";
    String type = type0;
    int number0 = 0;
    int number = number0;
    boolean started = false;
    boolean combine = false;
    for(int i = 0; i <= tlen; i++) {
      if(i < tlen) {
        String trans = tlist[i].toUpperCase();
        int len = trans.length();
        if(len == 0) continue;
        type = trans.substring(0, 1);
        // Skip anything except T, B, and R
        if(!type.equals("T") && !type.equals("B") && !type.equals("R")) {
          continue;
        }
        String numberString = trans.substring(1, len);
        number = 0;
        if(len > 1) {
          number = Integer.parseInt(numberString);
        }
        combine = false;
        if(type.compareTo(type0) == 0) {
          if(type.equals("T") || type.equals("B")) {
            combine = true;
            number0 += number;
          } else if(type.equals("R")) {
            combine = true;
            number0 = 1 - number0;
          }
        }
      } else {
        if(!started) continue;
        number = number0;
        type = type0;
      }
      if(!started) {
        started = true;
        number0 = number;
        type0 = type;
        continue;
      }

      // Write result to string
      if(!combine || i == tlen) {
        // Write the finished one
        if(type0.equals("T") || type0.equals("B")) {
          while(number0 > 6)
            number0 -= 12;
          while(number0 < -6)
            number0 += 12;
          if(number0 != 0) newString += " " + type0 + number0;
        } else if(type0.equals("R")) {
          if(number0 == 0) newString += " R";
        } else {
          // newString+=" " + type0 + number0;
        }
        number0 = number;
      }
      type0 = type;
    }
    return newString.trim();
  }
  
  /**
   * Prints the Id's of the configuration
   * 
   */
  public void printConfiguration() {
//    System.out.println("top=:");
    for(int i=0; i < 12; i++) {
      System.out.print(" " + top[i].getId());
    }
    System.out.println("");
//    System.out.println("bottom=:");
    for(int i=0; i < 12; i++) {
      System.out.print(" " + bottom[i].getId());
    }
    System.out.println("");
  }

  // Accessors
  
  /**
   * @return Returns the bottom.
   */
  public Block[] getBottom() {
    return bottom;
  }

  /**
   * @param bottom The bottom to set.
   */
  public void setBottom(Block[] bottom) {
    this.bottom = bottom;
  }

  /**
   * @return Returns the center.
   */
  public Block getCenter() {
    return center;
  }

  /**
   * @param center The center to set.
   */
  public void setCenter(Block center) {
    this.center = center;
  }

  /**
   * @return Returns the top.
   */
  public Block[] getTop() {
    return top;
  }

  /**
   * @param top The top to set.
   */
  public void setTop(Block[] top) {
    this.top = top;
  }
  
  public void setCanvas(Canvas canvas) {
    for(int i = 0; i < top.length; i++) {
      top[i].setCanvas(canvas);
    }
    for(int i = 0; i < bottom.length; i++) {
      bottom[i].setCanvas(canvas);
    }
    center.setCanvas(canvas);
  }

}
