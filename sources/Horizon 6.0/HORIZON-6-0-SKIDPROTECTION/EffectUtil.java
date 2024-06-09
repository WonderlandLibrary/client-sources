package HORIZON-6-0-SKIDPROTECTION;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import javax.swing.JDialog;
import java.awt.EventQueue;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.SpinnerModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Component;
import javax.swing.JColorChooser;
import java.awt.Color;
import java.awt.Composite;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EffectUtil
{
    private static BufferedImage HorizonCode_Horizon_È;
    
    static {
        EffectUtil.HorizonCode_Horizon_È = new BufferedImage(256, 256, 2);
    }
    
    public static BufferedImage HorizonCode_Horizon_È() {
        final Graphics2D g = (Graphics2D)EffectUtil.HorizonCode_Horizon_È.getGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, 256, 256);
        g.setComposite(AlphaComposite.SrcOver);
        g.setColor(Color.white);
        return EffectUtil.HorizonCode_Horizon_È;
    }
    
    public static ConfigurableEffect.HorizonCode_Horizon_È HorizonCode_Horizon_È(final String name, final Color currentValue) {
        return new HorizonCode_Horizon_È(name, HorizonCode_Horizon_È(currentValue)) {
            @Override
            public void Ø­áŒŠá() {
                final Color newColor = JColorChooser.showDialog(null, "Choose a color", EffectUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È));
                if (newColor != null) {
                    this.HorizonCode_Horizon_È = EffectUtil.HorizonCode_Horizon_È(newColor);
                }
            }
            
            @Override
            public Object Ý() {
                return EffectUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
            }
        };
    }
    
    public static ConfigurableEffect.HorizonCode_Horizon_È HorizonCode_Horizon_È(final String name, final int currentValue, final String description) {
        return new HorizonCode_Horizon_È(name, String.valueOf(currentValue)) {
            @Override
            public void Ø­áŒŠá() {
                final JSpinner spinner = new JSpinner(new SpinnerNumberModel(currentValue, -32768, 32767, 1));
                if (this.HorizonCode_Horizon_È(spinner, description)) {
                    this.HorizonCode_Horizon_È = String.valueOf(spinner.getValue());
                }
            }
            
            @Override
            public Object Ý() {
                return Integer.valueOf(this.HorizonCode_Horizon_È);
            }
        };
    }
    
    public static ConfigurableEffect.HorizonCode_Horizon_È HorizonCode_Horizon_È(final String name, final float currentValue, final float min, final float max, final String description) {
        return new HorizonCode_Horizon_È(name, String.valueOf(currentValue)) {
            @Override
            public void Ø­áŒŠá() {
                final JSpinner spinner = new JSpinner(new SpinnerNumberModel(currentValue, min, max, 0.10000000149011612));
                if (this.HorizonCode_Horizon_È(spinner, description)) {
                    this.HorizonCode_Horizon_È = String.valueOf((float)spinner.getValue());
                }
            }
            
            @Override
            public Object Ý() {
                return Float.valueOf(this.HorizonCode_Horizon_È);
            }
        };
    }
    
    public static ConfigurableEffect.HorizonCode_Horizon_È HorizonCode_Horizon_È(final String name, final boolean currentValue, final String description) {
        return new HorizonCode_Horizon_È(name, String.valueOf(currentValue)) {
            @Override
            public void Ø­áŒŠá() {
                final JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected(currentValue);
                if (this.HorizonCode_Horizon_È(checkBox, description)) {
                    this.HorizonCode_Horizon_È = String.valueOf(checkBox.isSelected());
                }
            }
            
            @Override
            public Object Ý() {
                return Boolean.valueOf(this.HorizonCode_Horizon_È);
            }
        };
    }
    
    public static ConfigurableEffect.HorizonCode_Horizon_È HorizonCode_Horizon_È(final String name, final String currentValue, final String[][] options, final String description) {
        return new HorizonCode_Horizon_È(name, currentValue.toString()) {
            @Override
            public void Ø­áŒŠá() {
                int selectedIndex = -1;
                final DefaultComboBoxModel model = new DefaultComboBoxModel();
                for (int i = 0; i < options.length; ++i) {
                    model.addElement(options[i][0]);
                    if (this.HorizonCode_Horizon_È(i).equals(currentValue)) {
                        selectedIndex = i;
                    }
                }
                final JComboBox comboBox = new JComboBox(model);
                comboBox.setSelectedIndex(selectedIndex);
                if (this.HorizonCode_Horizon_È(comboBox, description)) {
                    this.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È(comboBox.getSelectedIndex());
                }
            }
            
            private String HorizonCode_Horizon_È(final int i) {
                if (options[i].length == 1) {
                    return options[i][0];
                }
                return options[i][1];
            }
            
            @Override
            public String toString() {
                for (int i = 0; i < options.length; ++i) {
                    if (this.HorizonCode_Horizon_È(i).equals(this.HorizonCode_Horizon_È)) {
                        return options[i][0].toString();
                    }
                }
                return "";
            }
            
            @Override
            public Object Ý() {
                return this.HorizonCode_Horizon_È;
            }
        };
    }
    
    public static String HorizonCode_Horizon_È(final Color color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null.");
        }
        String r = Integer.toHexString(color.getRed());
        if (r.length() == 1) {
            r = "0" + r;
        }
        String g = Integer.toHexString(color.getGreen());
        if (g.length() == 1) {
            g = "0" + g;
        }
        String b = Integer.toHexString(color.getBlue());
        if (b.length() == 1) {
            b = "0" + b;
        }
        return String.valueOf(r) + g + b;
    }
    
    public static Color HorizonCode_Horizon_È(final String rgb) {
        if (rgb == null || rgb.length() != 6) {
            return Color.white;
        }
        return new Color(Integer.parseInt(rgb.substring(0, 2), 16), Integer.parseInt(rgb.substring(2, 4), 16), Integer.parseInt(rgb.substring(4, 6), 16));
    }
    
    private abstract static class HorizonCode_Horizon_È implements ConfigurableEffect.HorizonCode_Horizon_È
    {
        String HorizonCode_Horizon_È;
        String Â;
        
        public HorizonCode_Horizon_È(final String name, final String value) {
            this.HorizonCode_Horizon_È = value;
            this.Â = name;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final String value) {
            this.HorizonCode_Horizon_È = value;
        }
        
        @Override
        public String Â() {
            return this.HorizonCode_Horizon_È;
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        @Override
        public String toString() {
            if (this.HorizonCode_Horizon_È == null) {
                return "";
            }
            return this.HorizonCode_Horizon_È.toString();
        }
        
        public boolean HorizonCode_Horizon_È(final JComponent component, final String description) {
            final Â dialog = new Â(component, this.Â, description);
            dialog.setTitle(this.Â);
            dialog.setLocationRelativeTo(null);
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JComponent focusComponent = component;
                    if (focusComponent instanceof JSpinner) {
                        focusComponent = ((JSpinner.DefaultEditor)((JSpinner)component).getEditor()).getTextField();
                    }
                    focusComponent.requestFocusInWindow();
                }
            });
            dialog.setVisible(true);
            return dialog.HorizonCode_Horizon_È;
        }
    }
    
    private static class Â extends JDialog
    {
        public boolean HorizonCode_Horizon_È;
        
        public Â(final JComponent component, final String name, final String description) {
            this.HorizonCode_Horizon_È = false;
            this.setDefaultCloseOperation(2);
            this.setLayout(new GridBagLayout());
            this.setModal(true);
            if (component instanceof JSpinner) {
                ((JSpinner.DefaultEditor)((JSpinner)component).getEditor()).getTextField().setColumns(4);
            }
            final JPanel descriptionPanel = new JPanel();
            descriptionPanel.setLayout(new GridBagLayout());
            this.getContentPane().add(descriptionPanel, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
            descriptionPanel.setBackground(Color.white);
            descriptionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
            final JTextArea descriptionText = new JTextArea(description);
            descriptionPanel.add(descriptionText, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
            descriptionText.setWrapStyleWord(true);
            descriptionText.setLineWrap(true);
            descriptionText.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            descriptionText.setEditable(false);
            final JPanel panel = new JPanel();
            this.getContentPane().add(panel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, 10, 0, new Insets(5, 5, 0, 5), 0, 0));
            panel.add(new JLabel(String.valueOf(name) + ":"));
            panel.add(component);
            final JPanel buttonPanel = new JPanel();
            this.getContentPane().add(buttonPanel, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
            final JButton okButton = new JButton("OK");
            buttonPanel.add(okButton);
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent evt) {
                    Â.this.HorizonCode_Horizon_È = true;
                    Â.this.setVisible(false);
                }
            });
            final JButton cancelButton = new JButton("Cancel");
            buttonPanel.add(cancelButton);
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent evt) {
                    Â.this.setVisible(false);
                }
            });
            this.setSize(new Dimension(320, 175));
        }
    }
}
