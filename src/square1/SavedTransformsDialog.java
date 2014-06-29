package square1;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class SavedTransformsDialog extends JDialog
{
  /**
   * Added to prevent compiler warnings
   */
  private static final long serialVersionUID = 1L;
  private Square1 mainFrame;
  private DefaultListModel<String> listModel = new DefaultListModel<String>();
  private JList<String> transformList = new JList<String>(listModel);
  private JScrollPane transformListScrollPane = new JScrollPane(transformList);

  /**
   * SavedTransformsDialog constructor
   * @param mainFrame
   */
  public SavedTransformsDialog(Square1 mainFrame) {
    this(mainFrame, null, "", false);
  }

  /**
   * 
   * @param parent
   * @param title
   * @param modal
   */
  public SavedTransformsDialog(Square1 mainFrame, Frame parent, String title,
    boolean modal) {
    super(parent, title, modal);
    this.mainFrame = mainFrame;
    try {
      jbInit();
    } catch(Exception e) {
      e.printStackTrace();
    }
    // Locate it on the screen
    this.setLocation(350, 25);
  }

  /**
   * Initializes the Swing part
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setTitle("Saved Transforms");
    Container contentPane = this.getContentPane();
    contentPane.setLayout(new GridBagLayout());

    GridBagConstraints gbcDefault = new GridBagConstraints();
    gbcDefault.insets = new Insets(2, 2, 2, 2);
    gbcDefault.weightx = 100;
    gbcDefault.fill = GridBagConstraints.HORIZONTAL;
    GridBagConstraints gbc = null;

    // Don't set size and bounds for transformList, just transformListScrollPane
    transformListScrollPane.setBounds(new Rectangle(5, 5, 600, 225));
    transformListScrollPane.setPreferredSize(new Dimension(600, 225));
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 8;
    gbc.weightx = 100;
    gbc.weighty = 100;
    gbc.fill = GridBagConstraints.BOTH;
    contentPane.add(transformListScrollPane, gbc);

    JButton button = new JButton();
    Insets buttonInsets = button.getInsets();
    buttonInsets.left = buttonInsets.right = 5;
    button.setText("Delete");
    button.setToolTipText("Delete current");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonDeleteOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 0;
    gbc.gridy = 1;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Delete Sel");
    button.setToolTipText("Delete selected");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonDeleteSelOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 1;
    gbc.gridy = 1;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Delete All");
    button.setToolTipText("Delete all");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonDeleteAllOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 2;
    gbc.gridy = 1;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Trim");
    button.setToolTipText("Remove unneeded characters and combine transforms");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonTrimOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 3;
    gbc.gridy = 1;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Transform");
    button.setToolTipText("Transform using current");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonTransformOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 4;
    gbc.gridy = 1;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Reverse");
    button.setToolTipText("Reverse using current");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonReverseOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 5;
    gbc.gridy = 1;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Restore");
    button.setToolTipText("Restore");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonRestoreOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 6;
    gbc.gridy = 1;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Test");
    button.setToolTipText("Restore then transform");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonTestOnActionPerformed(e, 0);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 7;
    gbc.gridy = 1;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Cut");
    button.setToolTipText("Cut selected");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonCutOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 0;
    gbc.gridy = 2;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Copy");
    button.setToolTipText("Copy selected");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonCopyOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 1;
    gbc.gridy = 2;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Paste");
    button.setToolTipText("Paste after selected or at end if no selection");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonPasteOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 2;
    gbc.gridy = 2;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Append");
    button.setToolTipText("Append to transform");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonAppendOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 3;
    gbc.gridy = 2;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Sort");
    button.setToolTipText("Sort");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonSortOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 4;
    gbc.gridy = 2;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("By Moves");
    button.setToolTipText("Sort by number of (trimmed) moves");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonSortEffOnActionPerformed(e);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 5;
    gbc.gridy = 2;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Test Next");
    button.setToolTipText("Restore then transform with next");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonTestOnActionPerformed(e, 1);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 6;
    gbc.gridy = 2;
    contentPane.add(button, gbc);

    button = new JButton();
    button.setText("Test Prev");
    button.setToolTipText("Restore then transform with previous");
    button.setMargin(buttonInsets);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonTestOnActionPerformed(e, -1);
      }
    });
    gbc = (GridBagConstraints)gbcDefault.clone();
    gbc.gridx = 7;
    gbc.gridy = 2;
    contentPane.add(button, gbc);
    
    pack();
  }

  // Event handlers

  private void buttonCutOnActionPerformed(ActionEvent e) {
    buttonCopyOnActionPerformed(e);
    buttonDeleteOnActionPerformed(e);
  }

  private void buttonCopyOnActionPerformed(ActionEvent e) {
    int index = transformList.getSelectedIndex();
    if(index > -1) {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      String text = (String)listModel.get(index);
      StringSelection selection = new StringSelection(text);
      clipboard.setContents(selection, null);
    }
  }

  private void buttonPasteOnActionPerformed(ActionEvent e) {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    DataFlavor flavor = DataFlavor.stringFlavor;
    Transferable contents = clipboard.getContents(null);
    if(contents != null && contents.isDataFlavorSupported(flavor)) {
      try {
        String text = (String)contents.getTransferData(flavor);
        int index = transformList.getSelectedIndex();
        if(index > -1) {
          // Add after selected
          listModel.add(index + 1, text);
        } else {
          // Add at end
          listModel.addElement(text);
        }
        transformList.setSelectedIndex(index + 1);
        transformList.ensureIndexIsVisible(index + 1);
      } catch(UnsupportedFlavorException ex) {
        Utilities.errMsg(ex.getMessage());
      } catch(IOException ex) {
        Utilities.errMsg(ex.getMessage());
      }
    }
  }

  private void buttonRestoreOnActionPerformed(ActionEvent e) {
    mainFrame.restore();
  }

  private void buttonAppendOnActionPerformed(ActionEvent e) {
    int index = transformList.getSelectedIndex();
    if(index > -1) {
      String newTransform = (String)listModel.getElementAt(index);
      String transform = mainFrame.getTransform();
      if(transform.length() == 0) {
        mainFrame.setTransform(newTransform);
      } else {
        mainFrame.setTransform(transform + " " + newTransform);
      }
    }
  }

  private void buttonDeleteOnActionPerformed(ActionEvent e) {
    int index = transformList.getSelectedIndex();
    if(index > -1) {
      listModel.remove(index);
      int size = listModel.getSize();
      if(size > 0) {
        int newIndex = (index > 1) ? index - 1 : 0;
        transformList.setSelectedIndex(newIndex);
        transformList.ensureIndexIsVisible(newIndex);
      }
    }
  }

  private void buttonDeleteSelOnActionPerformed(ActionEvent e) {
    int size = listModel.getSize();
    int iSave = 0;
    for(int i = size - 1; i >= 0; i--) {
      if(transformList.isSelectedIndex(i)) {
        listModel.remove(i);
        iSave = i;
      }
    }
    size = listModel.getSize();
    if(--iSave < 0) iSave = 0;
    if(size > 0 && iSave < size) transformList.setSelectedIndex(iSave);
    transformList.ensureIndexIsVisible(iSave);
  }

  private void buttonDeleteAllOnActionPerformed(ActionEvent e) {
    listModel.clear();
  }

  private void buttonTrimOnActionPerformed(ActionEvent e) {
    int index = transformList.getSelectedIndex();
    if(index > -1) {
      String transform = Configuration.packTransform((String)listModel
        .get(index));
      if(transform.length() > 0) {
        listModel.add(index + 1, transform);
        int newIndex = index + 1;
        transformList.setSelectedIndex(newIndex);
        transformList.ensureIndexIsVisible(newIndex);
      } else {
        Utilities.errMsg("Trimmed string is empty");
      }
    }
  }

  private void buttonSortOnActionPerformed(ActionEvent e) {
    int size = listModel.getSize();
    if(size < 0) return;
    String[] array = new String[size];
    for(int i = 0; i < size; i++) {
      array[i] = (String)listModel.getElementAt(i);
    }
    Arrays.sort(array);
    listModel.clear();
    for(int i = 0; i < size; i++) {
      listModel.addElement(array[i]);
    }
    transformList.setSelectedIndex(0);
    transformList.ensureIndexIsVisible(0);
  }

  private void buttonSortEffOnActionPerformed(ActionEvent e) {
    int size = listModel.getSize();
    if(size < 0) return;
    String[] array = new String[size];
    for(int i = 0; i < size; i++) {
      array[i] = (String)listModel.getElementAt(i);
    }
    Comparator<String> movesSquaredComparator = new Comparator<String>() {
      public int compare(String f1, String f2) {
        long m1 = Configuration.movesSquared(f1);
        long m2 = Configuration.movesSquared(f2);
        return (m1 > m2) ? 1 : 0;
      }
    };
    Arrays.sort(array, movesSquaredComparator);
    listModel.clear();
    for(int i = 0; i < size; i++) {
      listModel.addElement(array[i]);
    }
    transformList.setSelectedIndex(0);
    transformList.ensureIndexIsVisible(0);
  }

  private void buttonTransformOnActionPerformed(ActionEvent e) {
    int index = transformList.getSelectedIndex();
    if(index > -1) {
      mainFrame.transform((String)listModel.getElementAt(index));
    }
  }

  private void buttonReverseOnActionPerformed(ActionEvent e) {
    int index = transformList.getSelectedIndex();
    if(index > -1) {
      mainFrame.transform((String)listModel.getElementAt(index), true);
    }
  }

  private void buttonTestOnActionPerformed(ActionEvent e, int move) {
    int index = transformList.getSelectedIndex();
    int size = listModel.getSize();
    if(index > -1) {
      index += move;
      if(index < 0 || index >= size) {
        Utilities.errMsg("Cannot move in that direction");
        return;
      }
      mainFrame.restore();
      mainFrame.transform((String)listModel.getElementAt(index), false);
      transformList.setSelectedIndex(index);
      transformList.ensureIndexIsVisible(index);
    }
  }

  // Getters and setters

  /**
   * @return Returns the transformList.
   */
  public JList<String> getTransformList() {
    return transformList;
  }

  /**
   * @return Returns the listModel.
   */
  public DefaultListModel<String> getListModel() {
    return listModel;
  }

}
