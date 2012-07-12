package square1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

/**
 * Class to implement a Block
 * @author Kenneth Evans, Jr.
 */
public class Block implements Constants {
  private Canvas canvas=null;
  private int type=0;
  private Color topColor=Color.GRAY;
  private Color sideColor=Color.GRAY;
  private Block adjacent=null;
  double x1,y1,x2,y2;
  double xe1,ye1,xe2,ye2;
  private boolean centerAligned=true;
  private int id = 0;

  /**
   * Block constructor
   * @param canvas
   * @param type
   * @param topColor
   * @param sideColor
   * @param id
   */
  public Block(Canvas canvas, int type, Color topColor, Color sideColor,
    int id) {
    this.canvas=canvas;
    this.type=type;
    this.topColor=topColor;
    this.sideColor=sideColor;
    this.id = id;
    
    onResize();    
 }
  
  /**
   * Determines if two blocks are equal (have the same id)
   * @param block
   * @return
   */
  public boolean equals(Block block) {
    return block.id == id;
  }
  
  /**
   * Draw routine used for top and bottom
   * @param g
   * @param face
   * @param position
   */
  public void draw(Graphics g, Face face, int position) {
    if(canvas == null) return;
    
    // Set the Graphics
    Graphics2D g2=(Graphics2D)g;
        
    // Determine theta
    double theta;
    if(position == 12) position=0;
    if(type == 1) theta=(30.*position+15.)*deg2rad;
    else if(type == 2) theta=(30.*position-15.)*deg2rad;
    else theta=(30.*position)*deg2rad;
/*    
    System.out.println("type=" + type + " position=" + position + " theta=" + theta);
    System.out.println("  x1=" + x1 + " y1=" + y1 + " x2=" + x2 + " y2=" + y2);
*/
    // Rotate
    double xcen=face.getCenterX();
    double ycen=face.getCenterY();
    double costheta=Math.cos(theta);
    double sintheta=Math.sin(theta);
    double xx0=xcen;
    double yy0=ycen;
    double xx1=x1*costheta - y1*sintheta + xcen;
    double yy1=x1*sintheta + y1*costheta + ycen;
    double xx2=x2*costheta - y2*sintheta + xcen;
    double yy2=x2*sintheta + y2*costheta + ycen;
    double xxe1=xe1*costheta - ye1*sintheta + xcen;
    double yye1=xe1*sintheta + ye1*costheta + ycen;
    double xxe2=xe2*costheta - ye2*sintheta + xcen;
    double yye2=xe2*sintheta + ye2*costheta + ycen;

    GeneralPath polygon;
    
    // Top
    float xTop[]={(float)xx0,(float)xx1,(float)xx2,(float)xx0};
    float yTop[]={(float)yy0,(float)yy1,(float)yy2,(float)yy0};
    polygon=new GeneralPath(GeneralPath.WIND_EVEN_ODD,
     xTop.length);
    polygon.moveTo(xTop[0], yTop[0]);
    for(int index=1; index < xTop.length; index++ ) {
      polygon.lineTo(xTop[index],yTop[index]);
    }
    polygon.closePath();
    g2.setPaint(topColor);
    g2.fill(polygon);

    // Side
    float xSide[]={(float)xx1,(float)xxe1,(float)xxe2,(float)xx2,(float)xx1};
    float ySide[]={(float)yy1,(float)yye1,(float)yye2,(float)yy2,(float)yy1};
    polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
     xSide.length);
    polygon.moveTo(xSide[0], ySide[0]);
    for(int index=1; index < xSide.length; index++ ) {
      polygon.lineTo(xSide[index],ySide[index]);
    }
    polygon.closePath();
    g2.setPaint(sideColor);
    g2.fill(polygon);

  // Draw the lines
    g2.setStroke(canvas.getWideStroke());
    g2.setColor(Color.BLACK);
    g2.draw(polygon);
    
    if(type == 0) {
      polygon=new GeneralPath(GeneralPath.WIND_EVEN_ODD,
       xTop.length);
      polygon.moveTo(xTop[0], yTop[0]);
      for(int index=1; index < xTop.length; index++ ) {
        polygon.lineTo(xTop[index],yTop[index]);
      }
    } else if(type == 1) {
      xTop=new float[] {(float)xx1,(float)xx2,(float)xx0};
      yTop=new float[] {(float)yy1,(float)yy2,(float)yy0};
      polygon=new GeneralPath(GeneralPath.WIND_EVEN_ODD,
       xTop.length);
      polygon.moveTo(xTop[0], yTop[0]);
      for(int index=1; index < xTop.length; index++ ) {
        polygon.lineTo(xTop[index],yTop[index]);
      }
    } else if(type == 2) {
      xTop=new float[] {(float)xx0,(float)xx1,(float)xx2};
      yTop=new float[] {(float)yy0,(float)yy1,(float)yy2};
      polygon=new GeneralPath(GeneralPath.WIND_EVEN_ODD,
       xTop.length);
      polygon.moveTo(xTop[0], yTop[0]);
      for(int index=1; index < xTop.length; index++ ) {
        polygon.lineTo(xTop[index],yTop[index]);
      }
    }
    g2.draw(polygon);
  }

  /**
   * Draw routine used for Center
   * @param g
   */
  public void draw(Graphics g) {
    if(canvas == null) return;
    
    // Set the graphics
    Graphics2D g2=(Graphics2D)g;
    
    double x3;
    double longSide=canvas.getUnitLength()*sin30/cos45;
    double shortSide=2.*canvas.getUnitLength()*sin15;
    if(centerAligned) {
      x3=x2+shortSide+longSide;
      topColor=orange;
    } else {
      x3=x2+longSide;
      topColor=Color.RED;
    }

    GeneralPath polygon;
    float x[];
    float y[];

    x=new float[] {(float)x1,(float)x2,(float)x2,(float)x1,(float)x1};
    y=new float[] {(float)y1,(float)y1,(float)y2,(float)y2,(float)y1};
    polygon=new GeneralPath(GeneralPath.WIND_EVEN_ODD,
     x.length);
    polygon.moveTo(x[0],y[0]);
    for(int index=1; index < x.length; index++ ) {
      polygon.lineTo(x[index],y[index]);
    }
    polygon.closePath();
    g2.setPaint(orange);
    g2.fill(polygon);
    g2.setStroke(canvas.getWideStroke());
    g2.setPaint(Color.BLACK);
    g2.draw(polygon);

    x=new float[] {(float)x2,(float)x3,(float)x3,(float)x2,(float)x2};
    y=new float[] {(float)y1,(float)y1,(float)y2,(float)y2,(float)y1};
    polygon=new GeneralPath(GeneralPath.WIND_EVEN_ODD,
     x.length);
    polygon.moveTo(x[0],y[0]);
    for(int index=1; index < x.length; index++ ) {
      polygon.lineTo(x[index],y[index]);
    }
    polygon.closePath();
    g2.setPaint(topColor);
    g2.fill(polygon);
    g2.setStroke(canvas.getWideStroke());
    g2.setPaint(Color.BLACK);
    g2.draw(polygon);
  }
  
  /**
   * Resize routine
   */
  public void onResize() {
    if(canvas == null) return;
    
    if(type == 0) {
      x1=canvas.getUnitLength()*sin15;
      y1=-canvas.getUnitLength()*cos15;
      x2=-x1;
      y2=y1;
      xe1=x1;
      ye1=y1-canvas.getSideWidth();
      xe2=x2;
      ye2=ye1;
    } else if(type == 1) {
      x1=0;
      y1=-canvas.getUnitLength()*(cos30+sin30/tan45);
      x2=-canvas.getUnitLength()*sin30;
      y2=-canvas.getUnitLength()*cos30;
      xe1=x1-canvas.getSideWidth()*cos45;
      ye1=y1-canvas.getSideWidth()*cos45;
      xe2=x2-canvas.getSideWidth()*cos45;
      ye2=y2-canvas.getSideWidth()*cos45;
    } else if(type == 2) {
      x1=canvas.getUnitLength()*sin30;
      y1=-canvas.getUnitLength()*cos30;
      x2=0;
      y2=-canvas.getUnitLength()*(cos30+sin30/tan45);
      xe1=x1+canvas.getSideWidth()*cos45;
      ye1=y1-canvas.getSideWidth()*cos45;
      xe2=x2+canvas.getSideWidth()*cos45;
      ye2=y2-canvas.getSideWidth()*cos45;
    } else {
      double longSide=canvas.getUnitLength()*sin30/cos45;
      double shortSide=2.*canvas.getUnitLength()*sin15;
      double sideWidth=canvas.getSideWidth();
      x1=canvas.getCenterX()-(2.*longSide+shortSide)/2.;
      x2=x1+longSide;
      y1=canvas.getCenterY()+sideWidth/2.;
      y2=canvas.getCenterY()-sideWidth/2.;
    }
   }

  // Accessors
  public int getType() {
    return type;
  }

  public Color getTopColor() {
    return topColor;
  }

  public Color getSideColor() {
    return sideColor;
  }

  public void setAdjacent(Block adjacent) {
    this.adjacent = adjacent;
  }

  public Block getAdjacent() {
    return adjacent;
  }

  public void setCenterAligned(boolean centerAligned) {
    this.centerAligned = centerAligned;
  }

  public void toggleCenterAligned() {
    centerAligned = !centerAligned;
  }

  public boolean isCenterAligned() {
    return centerAligned;
  }

  /**
   * @return Returns the canvas.
   */
  public Canvas getCanvas() {
    return canvas;
  }

  /**
   * @param canvas The canvas to set.
   */
  public void setCanvas(Canvas canvas) {
    this.canvas = canvas;
  }

  /**
   * @return Returns the id.
   */
  public int getId() {
    return id;
  }

  /**
   * @param id The id to set.
   */
  public void setId(int id) {
    this.id = id;
  }

}
