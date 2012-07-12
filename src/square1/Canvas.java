package square1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import javax.swing.JPanel;

/**
 * Class to implement the Canvas
 * 
 * @author Kenneth Evans, Jr.
 */
public class Canvas extends JPanel implements Constants
{
  /**
   * Added to prevent compiler warnings
   */
  private static final long serialVersionUID = 1L;
  static final float lineWidth = 3;
  static final BasicStroke stroke = new BasicStroke(1.0f);
  static final BasicStroke wideStroke = new BasicStroke(1.0f * lineWidth);
  private static final double rotMult = 1.65;
  private int topCenterX;
  private int topCenterY;
  private int centerX;
  private int centerY;
  private int bottomCenterX;
  private int bottomCenterY;
  private int unitLength;
  private int sideWidth;
  private Face topFace = null;
  private Face centerFace = null;
  private Face bottomFace = null;

  private Square1 mainFrame;

  /**
   * Canvas constructor
   * 
   * @param mainFrame
   */
  public Canvas(Square1 mainFrame) {
    this.mainFrame = mainFrame;
    topFace = new Face(this, getTopCenterX(), getTopCenterY());
    centerFace = new Face(this, getCenterX(), getCenterY());
    bottomFace = new Face(this, getBottomCenterX(), getBottomCenterY());
    onResize();
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Graphics2D g2=(Graphics2D)g;

    // drawTest(g2);

    Block top[] = mainFrame.getTop();
    Block bottom[] = mainFrame.getBottom();
    if(top == null || bottom == null) return;

    for(int i = 0; i < 12; i++) {
      top[i].draw(g, getTopFace(), i + 1);
      bottom[i].draw(g, getBottomFace(), i + 1);
    }

    // Do center
    Block center = mainFrame.getCenter();
    center.draw(g);

    // Do rotation axes
    drawRotationAxes(g);
  }

  /**
   * Draws the rotation axes
   * 
   * @param g
   */
  public void drawRotationAxes(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    g2.setPaint(Color.BLACK);

    for(int i = 0; i < 2; i++) {
      Face face;
      if(i == 0)
        face = getTopFace();
      else
        face = getBottomFace();
      double multx = rotMult * (1 - 2 * i);
      double multy = rotMult;
      double x1 = face.getCenterX() + multx * getUnitLength() * sin15;
      double y1 = face.getCenterY() - multy * getUnitLength() * cos15;
      double x2 = face.getCenterX() - multx * getUnitLength() * sin15;
      double y2 = face.getCenterY() + multy * getUnitLength() * cos15;

      float xAxis[] = {(float)x1, (float)x2};
      float yAxis[] = {(float)y1, (float)y2};
      GeneralPath line = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
        xAxis.length);
      line.moveTo(xAxis[0], yAxis[0]);
      for(int index = 1; index < xAxis.length; index++) {
        line.lineTo(xAxis[index], yAxis[index]);
      }
      g2.setStroke(getStroke());
      g2.draw(line);
    }
  }

  /**
   * Test routine
   * 
   * @param g2
   */
  public void drawTest(Graphics2D g2) {
    Dimension d = getSize();
    int gridWidth = d.width;
    int gridHeight = d.height;

    int x = 5;
    int y = 7;
    int rectWidth = gridWidth - 2 * x;
    int rectHeight = gridHeight - 2 * y;

    // GeneralPath (polygon)
    int x1Points[] = {x, x + rectWidth, (x + rectWidth) / 2, x};
    int y1Points[] = {y, y, y + rectHeight, y + rectHeight};
    GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
      x1Points.length);
    polygon.moveTo(x1Points[0], y1Points[0]);
    for(int index = 1; index < x1Points.length; index++) {
      polygon.lineTo(x1Points[index], y1Points[index]);
    }
    polygon.closePath();

    g2.setPaint(Color.RED);
    g2.fill(polygon);
    g2.setPaint(Color.BLACK);
    g2.draw(polygon);
  }

  /**
   * Resize routine
   */
  public void onResize() {
    int width = getWidth();
    int height = getHeight();
    topCenterX = width / 2;
    topCenterY = height / 4;
    centerX = width / 2;
    centerY = height / 2;
    bottomCenterX = width / 2;
    bottomCenterY = height - height / 4;
    unitLength = width / 4;
    sideWidth = unitLength / 5;

    // Reset faces
    topFace.setCenterX(topCenterX);
    topFace.setCenterY(topCenterY);
    centerFace.setCenterX(centerX);
    centerFace.setCenterY(centerY);
    bottomFace.setCenterX(bottomCenterX);
    bottomFace.setCenterY(bottomCenterY);
    
    repaint();
  }

  // Getters and setters

  public Face getTopFace() {
    return topFace;
  }

  public Face getBottomFace() {
    return bottomFace;
  }

  public int getTopCenterX() {
    return topCenterX;
  }

  public int getTopCenterY() {
    return topCenterY;
  }

  public int getCenterX() {
    return centerX;
  }

  public int getCenterY() {
    return centerY;
  }

  public int getBottomCenterX() {
    return bottomCenterX;
  }

  public int getBottomCenterY() {
    return bottomCenterY;
  }

  public double getUnitLength() {
    return unitLength;
  }

  public double getSideWidth() {
    return sideWidth;
  }

  public double getLineWidth() {
    return lineWidth;
  }

  public Stroke getStroke() {
    return stroke;
  }

  public Stroke getWideStroke() {
    return wideStroke;
  }

}
