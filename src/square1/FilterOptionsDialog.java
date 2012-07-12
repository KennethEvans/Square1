/*
 * Class to implement a Filter Options dialog
 * Created on Jan 1, 2006S
 * By Kewnneth Evans, Jr.
 */

package square1;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * FilterOptionsDialog
 * @author Kenneth Evans, Jr.
 */
public class FilterOptionsDialog extends JDialog
{
  private FilterOptionsDialog dialog = this;
  private Square1 mainFrame;
  private boolean isModified = false;
  private static final long serialVersionUID = 1L;
  private JPanel jContentPane = null;
  private JPanel mainPanel = null;
  private JCheckBox useFiltersCheckBox = null;
  private JCheckBox useFilter1CheckBox = null;
  private JCheckBox useFilter2CheckBox = null;
  private JPanel useFilterNPanel = null;
  private JCheckBox useFilterCustomCheckBox = null;
  private JTextField customTextField = null;
  private JCheckBox useFilterNCheckBox = null;
  private JTextField filterNTextField = null;
  private JPanel customPanel = null;
  private JPanel buttonPanel = null;
  private JButton okButton = null;
  private JButton cancelButton = null;
  private JButton applyButton = null;

  /**
   * Constructor
   */
  public FilterOptionsDialog(Square1 mainFrame) {
    super();
    this.mainFrame = mainFrame;
    initialize();
    // Locate it on the screen
    this.setLocation(425, 490);
  }
  
  /**
   * Resets the internal state from the Square1
   */
  public void reset() {
    useFiltersCheckBox.setSelected(mainFrame.isUseFilters());
    useFilter1CheckBox.setSelected(mainFrame.isUseFilter1());
    useFilter2CheckBox.setSelected(mainFrame.isUseFilter2());
    useFilterCustomCheckBox.setSelected(mainFrame.isUseFilterCustom());
    useFilterNCheckBox.setSelected(mainFrame.isUseFilterN());
    customTextField.setText(mainFrame.getCustomFilter());
    filterNTextField.setText(new Integer(mainFrame.getNFilter()).toString());
    isModified = false;
  }
  
  /**
   * Resets the Square1 state from the internal state
   */
  public void resetMain() {
    mainFrame.setUseFilters(useFiltersCheckBox.isSelected());
    mainFrame.setUseFilter1(useFilter1CheckBox.isSelected());
    mainFrame.setUseFilter2(useFilter2CheckBox.isSelected());
    mainFrame.setUseFilterCustom(useFilterCustomCheckBox.isSelected());
    mainFrame.setUseFilterN(useFilterNCheckBox.isSelected());
    mainFrame.setCustomFilter(customTextField.getText());
    int n = 0;
    String numberString = filterNTextField.getText();
    try {
      n = Integer.parseInt(numberString);
    } catch(NumberFormatException ex) {
      Utilities.errMsg("Invalid number for Specify N");
      n = 0;
    }
    mainFrame.setNFilter(n);
    isModified = false;
  }
  
  /**
   * This method initializes this dialog
   * 
   * @return void
   */
  private void initialize() {
    isModified = false;
    this.setName("filterOptionsdDialog");
    this.setModal(false);
    this.setTitle("Filter Options");
    this.setContentPane(getJContentPane());
    pack();
  }

  /**
   * This method returns the jContentPane, creating it the first time.
   * 
   * @return javax.swing.JPanel
   */
  private JPanel getJContentPane() {
    if(jContentPane == null) {
      jContentPane = new JPanel();
      jContentPane.setLayout(new BorderLayout());
      jContentPane.add(getMainPanel(), java.awt.BorderLayout.CENTER);
    }
    return jContentPane;
  }

  /**
   * This method returns the  mainPanel, creating it the first time.
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getMainPanel() {
    if(mainPanel == null) {
      mainPanel = new JPanel();
      mainPanel.setLayout(new BoxLayout(getMainPanel(), BoxLayout.Y_AXIS));
      mainPanel.add(getUseFiltersCheckBox(), null);
      mainPanel.add(getUseFilter1CheckBox(), null);
      mainPanel.add(getUseFilter2CheckBox(), null);
      mainPanel.add(getCustomPanel(), null);
      mainPanel.add(getuseFilterNPanel(), null);
      mainPanel.add(getButtonPanel(), null);
    }
    return mainPanel;
  }

  /**
   * This method initializes useFiltersCheckBox, creating it the first time.	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getUseFiltersCheckBox() {
    if(useFiltersCheckBox == null) {
      useFiltersCheckBox = new JCheckBox();
      useFiltersCheckBox.setText("Use filters");
      useFiltersCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
      useFiltersCheckBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          isModified = true;
        }
      });
    }
    return useFiltersCheckBox;
  }

  /**
   * This method initializes useFilter1CheckBox, creating it the first time.	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getUseFilter1CheckBox() {
    if(useFilter1CheckBox == null) {
      useFilter1CheckBox = new JCheckBox();
      useFilter1CheckBox.setText("Filter out 4 edge blocks");
      useFilter1CheckBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          isModified = true;
        }
      });
    }
    return useFilter1CheckBox;
  }

  /**
   * This method initializes useFilter2CheckBox, creating it the first time.	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getUseFilter2CheckBox() {
    if(useFilter2CheckBox == null) {
      useFilter2CheckBox = new JCheckBox();
      useFilter2CheckBox.setText("Filter out 2 bottom and 2 top corner blocks");
      useFilter2CheckBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          isModified = true;
        }
      });
    }
    return useFilter2CheckBox;
  }

  /**
   * This method returns the useFilterNPanel, creating it the first time.	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getuseFilterNPanel() {
    if(useFilterNPanel == null) {
      useFilterNPanel = new JPanel();
      useFilterNPanel.setLayout(new FlowLayout());
      useFilterNPanel.add(getuseFilterNCheckBox(), null);
      useFilterNPanel.add(getFilterNTextField(), null);
    }
    return useFilterNPanel;
  }

  /**
   * This method returns the useFilterCustomCheckBox, creating it the first time.	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getUseFilterCustomCheckBox() {
    if(useFilterCustomCheckBox == null) {
      useFilterCustomCheckBox = new JCheckBox();
      useFilterCustomCheckBox.setText("Filter in Custom");
      useFilterCustomCheckBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          isModified = true;
        }
      });
    }
    return useFilterCustomCheckBox;
  }

  /**
   * This method returns the customTextField, creating it the first time.	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getCustomTextField() {
    if(customTextField == null) {
      customTextField = new JTextField();
      customTextField.setColumns(30);
      customTextField.setPreferredSize(new java.awt.Dimension(50,20));
      customTextField.setHorizontalAlignment(javax.swing.JTextField.LEADING);
    }
    customTextField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        isModified = true;
     }
    });
    return customTextField;
  }

  /**
   * This method returns the useFilterNCheckBox, creating it the first time.	
   * 	
   * @return javax.swing.JCheckBox	
   */
  private JCheckBox getuseFilterNCheckBox() {
    if(useFilterNCheckBox == null) {
      useFilterNCheckBox = new JCheckBox();
      useFilterNCheckBox.setText("Specify a single N to use");
      useFilterNCheckBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          isModified = true;
       }
      });
    }
    return useFilterNCheckBox;
  }

  /**
   * This method returns the filterNTextField, creating it the first time.	
   * 	
   * @return javax.swing.JTextField	
   */
  private JTextField getFilterNTextField() {
    if(filterNTextField == null) {
      filterNTextField = new JTextField();
      filterNTextField.setColumns(3);
    }
    filterNTextField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        isModified = true;
     }
    });
    return filterNTextField;
  }

  /**
   * This method returns the customPanel, creating it the first time.	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getCustomPanel() {
    if(customPanel == null) {
      customPanel = new JPanel();
      customPanel.setLayout(new FlowLayout());
      customPanel.add(getUseFilterCustomCheckBox(), null);
      customPanel.add(getCustomTextField(), null);
    }
    return customPanel;
  }

  /**
   * This method returns the buttonPanel, creating it the first time.	
   * 	
   * @return javax.swing.JPanel	
   */
  private JPanel getButtonPanel() {
    if(buttonPanel == null) {
      buttonPanel = new JPanel();
      buttonPanel.add(getOkButton(), null);
      buttonPanel.add(getApplyButton(), null);
      buttonPanel.add(getCancelButton(), null);
    }
    return buttonPanel;
  }

  /**
   * This method returns the okButton, creating it the first time.	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getOkButton() {
    if(okButton == null) {
      okButton = new JButton();
      okButton.setText("OK");
      okButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          resetMain();
          dialog.setVisible(false);
        }
      });
    }
    return okButton;
  }

  /**
   * This method returns the cancelButton, creating it the first time.	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getCancelButton() {
    if(cancelButton == null) {
      cancelButton = new JButton();
      cancelButton.setText("Cancel");
      cancelButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          reset();
          dialog.setVisible(false);
        }
      });
    }
    return cancelButton;
  }

  /**
   * This method returns the applyButton, creating it the first time.	
   * 	
   * @return javax.swing.JButton	
   */
  private JButton getApplyButton() {
    if(applyButton == null) {
      applyButton = new JButton();
      applyButton.setText("Apply");
      applyButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          resetMain();
        }
      });
    }
    return applyButton;
  }

  /**
   * @return Returns whether the internal state is modified
   */
  public boolean isModified() {
    return isModified;
  }

  /**
   * * Sets the internal state to modified or not
   * @param isModified The isModified to set.
   */
  public void setModified(boolean isModified) {
    this.isModified = isModified;
  }
  

}  //  @jve:decl-index=0:visual-constraint="11,10"
