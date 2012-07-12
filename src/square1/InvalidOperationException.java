/*
 * Program to define an InvalidOperationException
 * Created on Dec 25, 2005
 * By Kenneth Evans, Jr.
 */

package square1;

/**
 * InvalidOperationException
 * @author Kenneth Evans, Jr.
 */
public class InvalidOperationException extends Exception
{
  private static final long serialVersionUID = 1L;
  private boolean notR = false;
  private boolean topBad = false;
  private int position = 0;

  /**
   * InvalidOperationException constructor
   */
  public InvalidOperationException()
  {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * InvalidOperationException constructor
   * @param message
   */
  public InvalidOperationException(String message)
  {
    super(message);
    // TODO Auto-generated constructor stub
  }

  /**
   * InvalidOperationException constructor
   * @param cause
   */
  public InvalidOperationException(Throwable cause)
  {
    super(cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * InvalidOperationException constructor
   * @param message
   * @param cause
   */
  public InvalidOperationException(String message, Throwable cause)
  {
    super(message,cause);
    // TODO Auto-generated constructor stub
  }

  /**
   * @return Returns the notR.
   */
  public boolean isNotR() {
    return notR;
  }

  /**
   * @param notR The notR to set.
   */
  public void setNotR(boolean notR) {
    this.notR = notR;
  }

  /**
   * @return Returns the position.
   */
  public int getPosition() {
    return position;
  }

  /**
   * @param position The position to set.
   */
  public void setPosition(int position) {
    this.position = position;
  }

  /**
   * @return Returns the topBad.
   */
  public boolean isTopBad() {
    return topBad;
  }

  /**
   * @param topBad The topBad to set.
   */
  public void setTopBad(boolean topBad) {
    this.topBad = topBad;
  }
  
}
