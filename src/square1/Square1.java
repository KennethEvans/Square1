package square1;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Square1
 * @author Kenneth Evans, Jr.
 */
public class Square1 extends JFrame implements Constants
{
  /**
   * Added to prevent compiler warnings
   */
  private static final long serialVersionUID = 1L;
  private String defaultPath = "C:\\Scratch\\Square-1";
  
  private boolean useFilters = true;
  private boolean useFilter1 = false;
  private static final String filter1Pattern =
    "@4-.[2468]-.[2468]-.[2468]-.[2468]\\s";
  private static final Pattern pattern1 = Pattern.compile(filter1Pattern);
  private boolean useFilter2 = true;
  private static final String filter2Pattern =
    "@4-b[1357]-b[1357]-t[1357]-t[1357]\\s";
  private static final Pattern pattern2 = Pattern.compile(filter2Pattern);
  private boolean useFilterCustom = false;
  private String customFilter = "@4-b[2468]-b[2468]-t[2468]-t[2468]\\s";
  private Pattern patternCustom = Pattern.compile(customFilter);
  private boolean useFilterN = false;
  private int nFilter = 4;
  
  private Block top[];
  private Block center;
  private Block bottom[];
  private String history = "";
  private int nHistory = 0;

  private JMenuItem menuHelpAbout = new JMenuItem();
  private JMenu menuHelp = new JMenu();
  private SavedTransformsDialog savedTransformsDialog =
    new SavedTransformsDialog(this);
  private FilterOptionsDialog filterOptionsDialog =
    new FilterOptionsDialog(this);
  private JMenuItem menuFileOpen = new JMenuItem();
  private JMenuItem menuFilterOptions = new JMenuItem();
  private JMenuItem menuFileSave = new JMenuItem();
  private JMenuItem menuFileSavedTransforms = new JMenuItem();
  private JMenuItem menuFileCopyHistory = new JMenuItem();
  private JMenuItem menuFileClearHistory = new JMenuItem();
  private JMenuItem menuFileExit = new JMenuItem();
  private JMenu menuFile = new JMenu();
  private JMenuBar menuBar = new JMenuBar();
  private Canvas canvas = null;
  // private Box vBox = Box.createVerticalBox();
  private JTextField transformTextField = null;
  private JFileChooser chooser = null;

  /**
   * Square1 constructor
   */
  public Square1() {
    try {
      menuInit();
      jbInit();
      canvasInit();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initializes the menus.
   * @throws Exception
   */
  private void menuInit() throws Exception {
    this.setJMenuBar(menuBar);

    menuFile.setText("File");
    menuFilterOptions.setText("Filter Options...");
    menuFilterOptions.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        filterOptionsOnActionPerformed(ae);
      }
    });
    menuFileOpen.setText("Open...");
    menuFileOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        fileOpenOnActionPerformed(ae);
      }
    });
    menuFileSave.setText("Save...");
    menuFileSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        fileSaveOnActionPerformed(ae);
      }
    });
    menuFileSavedTransforms.setText("Saved Transforms...");
    menuFileSavedTransforms.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        fileSavedTransformsOnActionPerformed(ae);
      }
    });
    menuFileCopyHistory.setText("Copy History");
    menuFileCopyHistory.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        fileCopyHistoryOnActionPerformed(ae);
      }
    });
    menuFileClearHistory.setText("Clear History");
    menuFileClearHistory.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        fileClearHistoryOnActionPerformed(ae);
      }
    });
    menuFileExit.setText("Exit");
    menuFileExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        fileExitOnActionPerformed(ae);
      }
    });
    menuHelp.setText("Help");
    menuHelpAbout.setText("About");
    menuHelpAbout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        helpAboutOnActionPerformed(ae);
      }
    });
    menuFile.add(menuFileOpen);
    menuFile.add(menuFileSave);
    menuFile.addSeparator();
    menuFile.add(menuFilterOptions);
    menuFile.add(menuFileSavedTransforms);
    menuFile.addSeparator();
    menuFile.add(menuFileCopyHistory);
    menuFile.add(menuFileClearHistory);
    menuFile.addSeparator();
    menuFile.add(menuFileExit);
    menuBar.add(menuFile);
    menuHelp.add(menuHelpAbout);
    menuBar.add(menuHelp);
  }

  /**
   * Initializes the panels.
   * @throws Exception
   */
  private void jbInit() throws Exception {
    Container contentPane = this.getContentPane();
    contentPane.setLayout(new GridBagLayout());

    GridBagConstraints gbcDefault = new GridBagConstraints();
    gbcDefault.insets = new Insets(2, 2, 2, 2);
    gbcDefault.gridx = 0;
    GridBagConstraints gbc = null;

    // Upper text field
    transformTextField = new JTextField();
    transformTextField.setColumns(25);
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridy = 0;
    gbc.weightx = 100;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    contentPane.add(transformTextField, gbc);
    
    // Upper panel
    JPanel panel = new JPanel();
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridy = 1;
    gbc.weightx = 100;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    contentPane.add(panel, gbc);
    
    JButton button = new JButton();
    Insets buttonInsets = button.getInsets();
    buttonInsets.left = buttonInsets.right = 5;
    button.setText("Clear");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clearButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("Trim");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        trimButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("Cut");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cutButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("Copy");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        copyButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("Paste");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pasteButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    // Center canvas
    canvas = new Canvas(this);
    canvas.setPreferredSize(new Dimension(250, 475));
    canvas.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        canvasOnComponentResized(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridy = 2;
    gbc.weightx = 100;
    gbc.weighty = 100;
    gbc.fill = GridBagConstraints.BOTH;
    contentPane.add(canvas, gbc);

    // Lower panel 1
    panel = new JPanel();
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridy = 3;
    gbc.weightx = 100;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    contentPane.add(panel, gbc);

    button = new JButton();
    button.setText("T+3");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tp3ButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("T+1");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tp1ButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("R");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        r1ButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("T-1");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tm1ButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("T-3");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tm3ButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    // Lower panel 2
    panel = new JPanel();
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridy = 4;
    gbc.weightx = 100;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    contentPane.add(panel, gbc);

    button = new JButton();
    button.setText("B+3");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bp3ButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("B+1");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bp1ButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("R");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        r2ButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("B-1");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bm1ButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("B-3");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bm3ButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    // Lower panel 3
    panel = new JPanel();
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridy = 5;
    gbc.weightx = 100;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    contentPane.add(panel, gbc);

    button = new JButton();
    button.setText("Transform");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        transformButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("Reverse");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        reverseButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    button = new JButton();
    button.setText("Restore");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        restoreButtonOnActionPerformed(e);
      }
    });
    panel.add(button, null);

    this.setTitle("Square-1");
  }

  void canvasInit() {
    restore();
  }

  /**
   * Resets everything back to the begnning
   */
  public void restore() {
    Configuration config = Configuration.defaultConfiguration(canvas);
    top = config.getTop();
    center = config.getCenter();
    bottom = config.getBottom();
    clearHistory();
    canvas.repaint();
  }

  /**
   * Clears the history
   */
  public void clearHistory() {
    history = "";
    nHistory = 0;
  }

  /**
   * Does the GUI part of the transform, calling Configuration.transform
   * for the geometrical part
   * @param string
   */
  public void transform(String string) {
    transform(string, false);
  }
  
  /**
   * Does the GUI part of the transform, calling Configuration.transform
   * for the geometrical part
   * @param transform
   * @param reverse Reverses the transformation
   */
  public void transform(String transform, boolean reverse) {
    // Split the configuration and check it
    String[] tlist = Configuration.splitTransform(transform);
    int tlen=tlist.length;
    if(tlen <= 0) {
      Utilities.errMsg("Empty transformation");
    }
    // Do the transform
    Configuration config = new Configuration(top, center, bottom);
    Configuration newConfig;
    try {
      newConfig = config.transform(transform, reverse);
    } catch(InvalidOperationException ex) {
      Utilities.errMsg(ex.getMessage());
      return;
    }
    // Set the new configuration
    top = newConfig.getTop();
    center = newConfig.getCenter();
    bottom = newConfig.getBottom();
    // Reset the history
    nHistory++;
    if(nHistory != 1) history += " ";
    history += "@" + nHistory;
    for(int i = 0; i < tlen; i++) {
      history += " " + tlist[i];
    }
    canvas.repaint();
  }

  /**
   * Trims the transformation by calling Configuration.packTransform
   * @param string
   * @return
   */
  public static String trimTransform(String string) {
    String newString = Configuration.packTransform(string);
    return newString;
  }
  
  // Filters
  
  /**
   * Checks for pattern1 in line
   * @param line
   * @return true if line matches the pattern
   */
  private boolean filter1(String line) {
    boolean retVal = false;
    Matcher matcher = pattern1.matcher(line);
    if(matcher.lookingAt()) retVal = true;
    return retVal;
  }
  
  /**
   * Checks for pattern2 in line
   * @param line
   * @return true if line matches the pattern
   */
  private boolean filter2(String line) {
    boolean retVal = false;
    Matcher matcher = pattern2.matcher(line);
    if(matcher.lookingAt()) retVal = true;
    return retVal;
  }
  
  /**
   * Checks for custom pattern in line
   * @param line
   * @return fslse (keep line) if line matches the pattern
   */
  private boolean filterCustom(String line) {
    boolean retVal = true;
    Matcher matcher = patternCustom.matcher(line);
    if(matcher.lookingAt()) retVal = false;
    return retVal;
  }
  
  /**
   * Checks if n in line matches the current filter number
   * @param line
   * @return false (keep line) if N matches the current filter number
   */
  private boolean filterN(String line) {
    boolean retVal = true;
    try {
      int start = line.indexOf('@');
      if(++start < 0) return retVal;
      int end = line.indexOf('-');
      if(end < 0) return retVal;
      String numberString=line.substring(start, end);
      int n = Integer.parseInt(numberString);
      if(n == nFilter) retVal = false;
    } catch(NumberFormatException ex) {
      // Do nothing
    }
    return retVal;
  }
   
  // Getters and setters

  /**
   * @return top array
   */
  public Block[] getTop() {
    return top;
  }

  /**
   * @return center
   */
  public Block getCenter() {
    return center;
  }

  /**
   * @return bottom array
   */
  public Block[] getBottom() {
    return bottom;
  }

  /**
   * @return the history
   */
  public String getHistory() {
    return history;
  }

  /**
   * @return the value of the transform TextField
   */
  public String getTransform() {
    return transformTextField.getText();
  }

  /**
   * Sets the 
   * @param transform value of the transform TextField
   */
  public void setTransform(String transform) {
    transformTextField.setText(transform);
  }
  
  /**
   * @return Returns the customFilter.
   */
  public String getCustomFilter() {
    return customFilter;
  }

  /**
   * @param customFilter The customFilter to set.
   */
  public void setCustomFilter(String customFilter) {
    this.customFilter = customFilter;
    patternCustom = Pattern.compile(customFilter);
  }

  /**
   * @return Returns the nFilter.
   */
  public int getNFilter() {
    return nFilter;
  }

  /**
   * @param filter The nFilter to set.
   */
  public void setNFilter(int nFilter) {
    this.nFilter = nFilter;
  }

  /**
   * @return Returns the useFilterN.
   */
  public boolean isUseFilterN() {
    return useFilterN;
  }

  /**
   * @param useFilterN The useFilterN to set.
   */
  public void setUseFilterN(boolean useFilterN) {
    this.useFilterN = useFilterN;
  }

  /**
   * @return Returns the useFilter1.
   */
  public boolean isUseFilter1() {
    return useFilter1;
  }

  /**
   * @param useFilter1 The useFilter1 to set.
   */
  public void setUseFilter1(boolean useFilter1) {
    this.useFilter1 = useFilter1;
  }

  /**
   * @return Returns the useFilter2.
   */
  public boolean isUseFilter2() {
    return useFilter2;
  }

  /**
   * @param useFilter2 The useFilter2 to set.
   */
  public void setUseFilter2(boolean useFilter2) {
    this.useFilter2 = useFilter2;
  }

  /**
   * @return Returns the useFilterCustom.
   */
  public boolean isUseFilterCustom() {
    return useFilterCustom;
  }

  /**
   * @param useFilterCustom The useFilterCustom to set.
   */
  public void setUseFilterCustom(boolean useFilterCustom) {
    this.useFilterCustom = useFilterCustom;
  }

  /**
   * @return Returns the useFilters.
   */
  public boolean isUseFilters() {
    return useFilters;
  }

  /**
   * @param useFilters The useFilters to set.
   */
  public void setUseFilters(boolean useFilters) {
    this.useFilters = useFilters;
  }

  // Event handlers

  void filterOptionsOnActionPerformed(ActionEvent e) {
    filterOptionsDialog.reset();
    filterOptionsDialog.setVisible(true);
    /*
     * JOptionPane.showMessageDialog(this, new SavedTransformsPanel(), "Saved
     * Transforms", JOptionPane.PLAIN_MESSAGE);
     */
  }

  void fileOpenOnActionPerformed(ActionEvent e) {
    if(filterOptionsDialog.isModified()) {
      int selection = JOptionPane.showConfirmDialog(this,
        "Filter options have been modified but not saved\nOK to continue?",
        "Warning",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
      if(selection != JOptionPane.OK_OPTION) return;
    }
    chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(defaultPath));
    int result = chooser.showOpenDialog(this);
    if(result == JFileChooser.APPROVE_OPTION) {
      // Save the selected path for next time
      defaultPath = chooser.getSelectedFile().getParentFile().getPath();
      // Complie the regular expression for the start of the line
      Pattern pattern = Pattern.compile("[@TBR][-0-9 ]");
      // Process the file
      String fileName = chooser.getSelectedFile().getPath();
      JList transformList = savedTransformsDialog.getTransformList();
      DefaultListModel listModel = savedTransformsDialog.getListModel();
      int last = listModel.size();
      long lineNum = 0;
      try {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line;
        while((line = in.readLine()) != null) {
          lineNum++;
          if(line.length() == 0) continue;
          // Use only lines starting with @, T, B, R
//          char ch = line.charAt(0);
//          if(ch != '@' && ch != 'T' && ch != 'B' && ch != 'R') continue;
          Matcher matcher = pattern.matcher(line);
          if(!matcher.lookingAt()) continue;
          if(!useFilters) {
            listModel.addElement(line);
          } else {
            // Has to pass all the specified filters
            if((!useFilter1 || (useFilter1 && !filter1(line))) &&
              (!useFilter2 || (useFilter2 && !filter2(line))) &&
              (!useFilterCustom || (useFilterCustom && !filterCustom(line))) &&
              (!useFilterN || (useFilterN && !filterN(line)))) {
              listModel.addElement(line);
            }
          }
        }
        in.close();
        int newLast = listModel.size();
        if(newLast > last) {
          transformList.setSelectedIndex(last);
          transformList.ensureIndexIsVisible(last);
        }
        savedTransformsDialog.setVisible(true);
      } catch(Exception ex) {
        Utilities.errMsg("Error reading " + fileName + "\nat line " + lineNum);
      }
    }
  }

  void fileSaveOnActionPerformed(ActionEvent e) {
    chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(defaultPath));
    int result = chooser.showSaveDialog(this);
    if(result == JFileChooser.APPROVE_OPTION) {
      // Save the selected path for next time
      defaultPath = chooser.getSelectedFile().getParentFile().getPath();
      // Process the file
      String fileName = chooser.getSelectedFile().getPath();
      File file = new File(fileName);
      if(file.exists()) {
        int selection = JOptionPane.showConfirmDialog(this,
          "File already exists:\n" + fileName + "\nOK to replace?",
          "Warning",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if(selection != JOptionPane.OK_OPTION) return;
      }
      DefaultListModel listModel = savedTransformsDialog.getListModel();
      int last = listModel.size();
      long lineNum = 0;
      try {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));
        for(int i=0; i < last; i++) {
          lineNum++;
          String line = (String)listModel.getElementAt(i);
          out.println(line);
        }
        out.close();
      } catch(Exception ex) {
        Utilities.errMsg("Error writing " + fileName + "\nat line " + lineNum);
      }
    }
  }

  void fileSavedTransformsOnActionPerformed(ActionEvent e) {
    savedTransformsDialog.setVisible(true);
    /*
     * JOptionPane.showMessageDialog(this, new SavedTransformsPanel(), "Saved
     * Transforms", JOptionPane.PLAIN_MESSAGE);
     */
  }

  void fileCopyHistoryOnActionPerformed(ActionEvent e) {
    transformTextField.setText(history);
  }

  void fileClearHistoryOnActionPerformed(ActionEvent e) {
    clearHistory();
  }

  void fileExitOnActionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void helpAboutOnActionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(this, new AboutBoxPanel(), "Square-1",
      JOptionPane.PLAIN_MESSAGE);
  }

  private void tp3ButtonOnActionPerformed(ActionEvent e) {
    transform("T3");
    // Utilities.errMsg("tp3Button Clicked");
  }

  private void tp1ButtonOnActionPerformed(ActionEvent e) {
    transform("T1");
    // Utilities.errMsg("tp1Button Clicked");
  }

  private void r1ButtonOnActionPerformed(ActionEvent e) {
    transform("R");
    // Utilities.errMsg("r1Button Clicked");
  }

  private void tm1ButtonOnActionPerformed(ActionEvent e) {
    transform("T-1");
    // Utilities.errMsg("tm1Button Clicked");
  }

  private void tm3ButtonOnActionPerformed(ActionEvent e) {
    transform("T-3");
    // Utilities.errMsg("tm3Button Clicked");
  }

  private void bp3ButtonOnActionPerformed(ActionEvent e) {
    transform("B3");
    // Utilities.errMsg("bp3Button Clicked");
  }

  private void bp1ButtonOnActionPerformed(ActionEvent e) {
    transform("B1");
    // Utilities.errMsg("bp1Button Clicked");
  }

  private void r2ButtonOnActionPerformed(ActionEvent e) {
    transform("R");
    // Utilities.errMsg("r2Button Clicked");
  }

  private void bm1ButtonOnActionPerformed(ActionEvent e) {
    transform("B-1");
    // Utilities.errMsg("bm1Button Clicked");
  }

  private void bm3ButtonOnActionPerformed(ActionEvent e) {
    transform("B-3");
    // Utilities.errMsg("bm3Button Clicked");
  }

  private void transformButtonOnActionPerformed(ActionEvent e) {
    String text = transformTextField.getText();
    transform(text);
    // Utilities.errMsg("transformButton Clicked");
  }

  private void reverseButtonOnActionPerformed(ActionEvent e) {
    String text = transformTextField.getText();
    transform(text, true);
    // Utilities.errMsg("reverseButton Clicked");
  }

  private void restoreButtonOnActionPerformed(ActionEvent e) {
    restore();
    // Utilities.errMsg("restoreButton Clicked");
  }

  private void clearButtonOnActionPerformed(ActionEvent e) {
    transformTextField.setText("");
  }

  private void trimButtonOnActionPerformed(ActionEvent e) {
    String text = transformTextField.getText();
    text = Configuration.packTransform(text);
    transformTextField.setText(text);
  }

  private void cutButtonOnActionPerformed(ActionEvent e) {
    copyButtonOnActionPerformed(e);
    transformTextField.setText("");
  }

   private void copyButtonOnActionPerformed(ActionEvent e) {
     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
     String text = transformTextField.getSelectedText();
     if(text == null) text = transformTextField.getText();
     StringSelection selection = new StringSelection(text);
     clipboard.setContents(selection, null);
  }

   private void pasteButtonOnActionPerformed(ActionEvent e) {
     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
     DataFlavor flavor = DataFlavor.stringFlavor;
     Transferable contents = clipboard.getContents(null);
     if(contents != null && contents.isDataFlavorSupported(flavor)) {
       try {
         String text = (String)contents.getTransferData(flavor);
         if(transformTextField.getSelectedText() != null) {
           transformTextField.replaceSelection(text);
         } else {
           transformTextField.setText(text);
         }
       } catch(UnsupportedFlavorException ex) {
         Utilities.errMsg(ex.getMessage());
         Utilities.errMsg(ex.getMessage());
       } catch(IOException ex) {
         Utilities.errMsg(ex.getMessage());
       }
     }
   }
   
  private void canvasOnComponentResized(ComponentEvent e) {
    canvas.onResize();
    for(int i = 0; i < 12; i++) {
      top[i].onResize();
      bottom[i].onResize();
    }
    center.onResize();
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
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          JFrame frame = new Square1();
          JFrame.setDefaultLookAndFeelDecorated(true);
          frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
          frame.pack();
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        }
      });
    } catch(Throwable t) {
      t.printStackTrace();
    }
  }
}
