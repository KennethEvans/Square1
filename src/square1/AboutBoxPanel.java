package square1;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Class to implement the About dialog
 * @author Kenneth Evans, Jr.
 */
public class AboutBoxPanel extends JPanel  {
  /**
   * Added to prevent compiler warnings
   */
  private static final long serialVersionUID=1L;
  private Border border = BorderFactory.createEtchedBorder();
  private GridBagLayout layoutMain = new GridBagLayout();
  private JLabel labelCompany = new JLabel();
  private JLabel labelCopyright = new JLabel();
  private JLabel labelAuthor = new JLabel();
  private JLabel labelTitle = new JLabel();

  /**
   * AboutBoxPanel constructor
   */
  public AboutBoxPanel() {
    try {
      jbInit();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initializes the Swing part
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(layoutMain);
    this.setBorder(border);
    labelTitle.setText("Square-1");
    labelAuthor.setText("Kenneth Evans, Jr.");
    labelCopyright.setText("Copyright 2005-2006 Kenneth Evans, Jr.");
    labelCompany.setText("");
    this.add(labelTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
     GridBagConstraints.WEST, GridBagConstraints.NONE,
     new Insets(5, 15, 0, 15), 0, 0));
    this.add(labelAuthor, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
     GridBagConstraints.WEST, GridBagConstraints.NONE,
     new Insets(0, 15, 0, 15), 0, 0));
    this.add(labelCopyright, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
     GridBagConstraints.WEST, GridBagConstraints.NONE,
     new Insets(0, 15, 0, 15), 0, 0));
    this.add(labelCompany, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
     GridBagConstraints.WEST, GridBagConstraints.NONE,
     new Insets(0, 15, 5, 15), 0, 0));
  }
}
