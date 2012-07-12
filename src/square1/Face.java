package square1;

/**
 * Class to manage a single Face
 * @author Kenneeth Evans, Jr.
 */
public class Face {
  Canvas canvas=null;
  int centerX;
  int centerY;
  
  /**
   * Face constructor
   * @param canvas
   * @param centerX
   * @param centerY
   */
  public Face(Canvas canvas, int centerX, int centerY) {
    this.canvas=canvas;
    this.centerX=centerX;
    this.centerY=centerY;
  }

  // Accessors
  public Canvas getCanvas() {
    return canvas;
  }

  public void setCenterX(int centerX) {
    this.centerX = centerX;
  }

  public int getCenterX() {
    return centerX;
  }

  public void setCenterY(int centerY) {
    this.centerY = centerY;
  }

  public int getCenterY() {
    return centerY;
  }

}
