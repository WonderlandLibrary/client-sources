package org.newdawn.slick.font.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;








public class EffectUtil
{
  private static BufferedImage scratchImage = new BufferedImage(256, 256, 
    2);
  

  public EffectUtil() {}
  

  public static BufferedImage getScratchImage()
  {
    Graphics2D g = (Graphics2D)scratchImage.getGraphics();
    g.setComposite(AlphaComposite.Clear);
    g.fillRect(0, 0, 256, 256);
    g.setComposite(AlphaComposite.SrcOver);
    g.setColor(Color.white);
    return scratchImage;
  }
  






  public static ConfigurableEffect.Value colorValue(String name, Color currentValue)
  {
    new DefaultValue(name, toString(currentValue)) {
      public void showDialog() {
        Color newColor = JColorChooser.showDialog(null, "Choose a color", EffectUtil.fromString(value));
        if (newColor != null) value = EffectUtil.toString(newColor);
      }
      
      public Object getObject() {
        return EffectUtil.fromString(value);
      }
    };
  }
  







  public static ConfigurableEffect.Value intValue(String name, final int currentValue, final String description)
  {
    new DefaultValue(name, String.valueOf(currentValue)) {
      public void showDialog() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(currentValue, 32768, 32767, 1));
        if (showValueDialog(spinner, description)) value = String.valueOf(spinner.getValue());
      }
      
      public Object getObject() {
        return Integer.valueOf(value);
      }
    };
  }
  










  public static ConfigurableEffect.Value floatValue(String name, final float currentValue, final float min, final float max, final String description)
  {
    new DefaultValue(name, String.valueOf(currentValue)) {
      public void showDialog() {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(currentValue, min, max, 0.10000000149011612D));
        if (showValueDialog(spinner, description)) value = String.valueOf(((Double)spinner.getValue()).floatValue());
      }
      
      public Object getObject() {
        return Float.valueOf(value);
      }
    };
  }
  







  public static ConfigurableEffect.Value booleanValue(String name, final boolean currentValue, final String description)
  {
    new DefaultValue(name, String.valueOf(currentValue)) {
      public void showDialog() {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(currentValue);
        if (showValueDialog(checkBox, description)) value = String.valueOf(checkBox.isSelected());
      }
      
      public Object getObject() {
        return Boolean.valueOf(value);
      }
    };
  }
  












  public static ConfigurableEffect.Value optionValue(String name, final String currentValue, final String[][] options, final String description)
  {
    new DefaultValue(name, currentValue.toString()) {
      public void showDialog() {
        int selectedIndex = -1;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (int i = 0; i < options.length; i++) {
          model.addElement(options[i][0]);
          if (getValue(i).equals(currentValue)) selectedIndex = i;
        }
        JComboBox comboBox = new JComboBox(model);
        comboBox.setSelectedIndex(selectedIndex);
        if (showValueDialog(comboBox, description)) value = getValue(comboBox.getSelectedIndex());
      }
      
      private String getValue(int i) {
        if (options[i].length == 1) return options[i][0];
        return options[i][1];
      }
      
      public String toString() {
        for (int i = 0; i < options.length; i++)
          if (getValue(i).equals(value)) return options[i][0].toString();
        return "";
      }
      
      public Object getObject() {
        return value;
      }
    };
  }
  





  public static String toString(Color color)
  {
    if (color == null) throw new IllegalArgumentException("color cannot be null.");
    String r = Integer.toHexString(color.getRed());
    if (r.length() == 1) r = "0" + r;
    String g = Integer.toHexString(color.getGreen());
    if (g.length() == 1) g = "0" + g;
    String b = Integer.toHexString(color.getBlue());
    if (b.length() == 1) b = "0" + b;
    return r + g + b;
  }
  





  public static Color fromString(String rgb)
  {
    if ((rgb == null) || (rgb.length() != 6)) return Color.white;
    return new Color(Integer.parseInt(rgb.substring(0, 2), 16), Integer.parseInt(rgb.substring(2, 4), 16), Integer.parseInt(rgb
      .substring(4, 6), 16));
  }
  



  private static abstract class DefaultValue
    implements ConfigurableEffect.Value
  {
    String value;
    


    String name;
    


    public DefaultValue(String name, String value)
    {
      this.value = value;
      this.name = name;
    }
    


    public void setString(String value)
    {
      this.value = value;
    }
    


    public String getString()
    {
      return value;
    }
    


    public String getName()
    {
      return name;
    }
    


    public String toString()
    {
      if (value == null) {
        return "";
      }
      return value.toString();
    }
    






    public boolean showValueDialog(final JComponent component, String description)
    {
      EffectUtil.ValueDialog dialog = new EffectUtil.ValueDialog(component, name, description);
      dialog.setTitle(name);
      dialog.setLocationRelativeTo(null);
      EventQueue.invokeLater(new Runnable() {
        public void run() {
          JComponent focusComponent = component;
          if ((focusComponent instanceof JSpinner))
            focusComponent = ((JSpinner.DefaultEditor)((JSpinner)component).getEditor()).getTextField();
          focusComponent.requestFocusInWindow();
        }
      });
      dialog.setVisible(true);
      return okPressed;
    }
  }
  


  private static class ValueDialog
    extends JDialog
  {
    public boolean okPressed = false;
    






    public ValueDialog(JComponent component, String name, String description)
    {
      setDefaultCloseOperation(2);
      setLayout(new GridBagLayout());
      setModal(true);
      
      if ((component instanceof JSpinner)) {
        ((JSpinner.DefaultEditor)((JSpinner)component).getEditor()).getTextField().setColumns(4);
      }
      JPanel descriptionPanel = new JPanel();
      descriptionPanel.setLayout(new GridBagLayout());
      getContentPane().add(
        descriptionPanel, 
        new GridBagConstraints(0, 0, 2, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 
        0), 0, 0));
      descriptionPanel.setBackground(Color.white);
      descriptionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      
      JTextArea descriptionText = new JTextArea(description);
      descriptionPanel.add(descriptionText, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 10, 
        1, new Insets(5, 5, 5, 5), 0, 0));
      descriptionText.setWrapStyleWord(true);
      descriptionText.setLineWrap(true);
      descriptionText.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      descriptionText.setEditable(false);
      

      JPanel panel = new JPanel();
      getContentPane().add(
        panel, 
        new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 0, new Insets(5, 5, 0, 
        5), 0, 0));
      panel.add(new JLabel(name + ":"));
      panel.add(component);
      
      JPanel buttonPanel = new JPanel();
      getContentPane().add(
        buttonPanel, 
        new GridBagConstraints(0, 2, 2, 1, 0.0D, 0.0D, 13, 0, 
        new Insets(0, 0, 0, 0), 0, 0));
      
      JButton okButton = new JButton("OK");
      buttonPanel.add(okButton);
      okButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          okPressed = true;
          setVisible(false);
        }
        

      });
      JButton cancelButton = new JButton("Cancel");
      buttonPanel.add(cancelButton);
      cancelButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          setVisible(false);
        }
        

      });
      setSize(new Dimension(320, 175));
    }
  }
}
