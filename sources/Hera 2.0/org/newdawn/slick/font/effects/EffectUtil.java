/*     */ package org.newdawn.slick.font.effects;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.SpinnerNumberModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EffectUtil
/*     */ {
/*  40 */   private static BufferedImage scratchImage = new BufferedImage(256, 256, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage getScratchImage() {
/*  49 */     Graphics2D g = (Graphics2D)scratchImage.getGraphics();
/*  50 */     g.setComposite(AlphaComposite.Clear);
/*  51 */     g.fillRect(0, 0, 256, 256);
/*  52 */     g.setComposite(AlphaComposite.SrcOver);
/*  53 */     g.setColor(Color.white);
/*  54 */     return scratchImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEffect.Value colorValue(String name, Color currentValue) {
/*  65 */     return new DefaultValue(name, toString(currentValue)) {
/*     */         public void showDialog() {
/*  67 */           Color newColor = JColorChooser.showDialog(null, "Choose a color", EffectUtil.fromString(this.value));
/*  68 */           if (newColor != null) this.value = EffectUtil.toString(newColor); 
/*     */         }
/*     */         
/*     */         public Object getObject() {
/*  72 */           return EffectUtil.fromString(this.value);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEffect.Value intValue(String name, final int currentValue, final String description) {
/*  86 */     return new DefaultValue(name, String.valueOf(currentValue)) {
/*     */         public void showDialog() {
/*  88 */           JSpinner spinner = new JSpinner(new SpinnerNumberModel(currentValue, -32768, 32767, 1));
/*  89 */           if (showValueDialog(spinner, description)) this.value = String.valueOf(spinner.getValue()); 
/*     */         }
/*     */         
/*     */         public Object getObject() {
/*  93 */           return Integer.valueOf(this.value);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEffect.Value floatValue(String name, final float currentValue, final float min, final float max, final String description) {
/* 110 */     return new DefaultValue(name, String.valueOf(currentValue)) {
/*     */         public void showDialog() {
/* 112 */           JSpinner spinner = new JSpinner(new SpinnerNumberModel(currentValue, min, max, 0.10000000149011612D));
/* 113 */           if (showValueDialog(spinner, description)) this.value = String.valueOf(((Double)spinner.getValue()).floatValue()); 
/*     */         }
/*     */         
/*     */         public Object getObject() {
/* 117 */           return Float.valueOf(this.value);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEffect.Value booleanValue(String name, final boolean currentValue, final String description) {
/* 131 */     return new DefaultValue(name, String.valueOf(currentValue)) {
/*     */         public void showDialog() {
/* 133 */           JCheckBox checkBox = new JCheckBox();
/* 134 */           checkBox.setSelected(currentValue);
/* 135 */           if (showValueDialog(checkBox, description)) this.value = String.valueOf(checkBox.isSelected()); 
/*     */         }
/*     */         
/*     */         public Object getObject() {
/* 139 */           return Boolean.valueOf(this.value);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurableEffect.Value optionValue(String name, final String currentValue, final String[][] options, final String description) {
/* 158 */     return new DefaultValue(name, currentValue.toString()) {
/*     */         public void showDialog() {
/* 160 */           int selectedIndex = -1;
/* 161 */           DefaultComboBoxModel<String> model = new DefaultComboBoxModel();
/* 162 */           for (int i = 0; i < options.length; i++) {
/* 163 */             model.addElement(options[i][0]);
/* 164 */             if (getValue(i).equals(currentValue)) selectedIndex = i; 
/*     */           } 
/* 166 */           JComboBox<String> comboBox = new JComboBox<String>(model);
/* 167 */           comboBox.setSelectedIndex(selectedIndex);
/* 168 */           if (showValueDialog(comboBox, description)) this.value = getValue(comboBox.getSelectedIndex()); 
/*     */         }
/*     */         
/*     */         private String getValue(int i) {
/* 172 */           if ((options[i]).length == 1) return options[i][0]; 
/* 173 */           return options[i][1];
/*     */         }
/*     */         
/*     */         public String toString() {
/* 177 */           for (int i = 0; i < options.length; i++) {
/* 178 */             if (getValue(i).equals(this.value)) return options[i][0].toString(); 
/* 179 */           }  return "";
/*     */         }
/*     */         
/*     */         public Object getObject() {
/* 183 */           return this.value;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(Color color) {
/* 195 */     if (color == null) throw new IllegalArgumentException("color cannot be null."); 
/* 196 */     String r = Integer.toHexString(color.getRed());
/* 197 */     if (r.length() == 1) r = "0" + r; 
/* 198 */     String g = Integer.toHexString(color.getGreen());
/* 199 */     if (g.length() == 1) g = "0" + g; 
/* 200 */     String b = Integer.toHexString(color.getBlue());
/* 201 */     if (b.length() == 1) b = "0" + b; 
/* 202 */     return r + g + b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color fromString(String rgb) {
/* 212 */     if (rgb == null || rgb.length() != 6) return Color.white; 
/* 213 */     return new Color(Integer.parseInt(rgb.substring(0, 2), 16), Integer.parseInt(rgb.substring(2, 4), 16), Integer.parseInt(rgb.substring(4, 6), 16));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class DefaultValue
/*     */     implements ConfigurableEffect.Value
/*     */   {
/*     */     String value;
/*     */ 
/*     */ 
/*     */     
/*     */     String name;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DefaultValue(String name, String value) {
/* 233 */       this.value = value;
/* 234 */       this.name = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setString(String value) {
/* 241 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getString() {
/* 248 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 255 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 262 */       if (this.value == null) {
/* 263 */         return "";
/*     */       }
/* 265 */       return this.value.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean showValueDialog(final JComponent component, String description) {
/* 276 */       EffectUtil.ValueDialog dialog = new EffectUtil.ValueDialog(component, this.name, description);
/* 277 */       dialog.setTitle(this.name);
/* 278 */       dialog.setLocationRelativeTo(null);
/* 279 */       EventQueue.invokeLater(new Runnable() {
/*     */             public void run() {
/* 281 */               JComponent focusComponent = component;
/* 282 */               if (focusComponent instanceof JSpinner)
/* 283 */                 focusComponent = ((JSpinner.DefaultEditor)((JSpinner)component).getEditor()).getTextField(); 
/* 284 */               focusComponent.requestFocusInWindow();
/*     */             }
/*     */           });
/* 287 */       dialog.setVisible(true);
/* 288 */       return dialog.okPressed;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ValueDialog
/*     */     extends JDialog
/*     */   {
/*     */     public boolean okPressed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ValueDialog(JComponent component, String name, String description) {
/* 307 */       setDefaultCloseOperation(2);
/* 308 */       setLayout(new GridBagLayout());
/* 309 */       setModal(true);
/*     */       
/* 311 */       if (component instanceof JSpinner) {
/* 312 */         ((JSpinner.DefaultEditor)((JSpinner)component).getEditor()).getTextField().setColumns(4);
/*     */       }
/* 314 */       JPanel descriptionPanel = new JPanel();
/* 315 */       descriptionPanel.setLayout(new GridBagLayout());
/* 316 */       getContentPane().add(descriptionPanel, new GridBagConstraints(0, 0, 2, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*     */ 
/*     */       
/* 320 */       descriptionPanel.setBackground(Color.white);
/* 321 */       descriptionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
/*     */       
/* 323 */       JTextArea descriptionText = new JTextArea(description);
/* 324 */       descriptionPanel.add(descriptionText, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
/*     */       
/* 326 */       descriptionText.setWrapStyleWord(true);
/* 327 */       descriptionText.setLineWrap(true);
/* 328 */       descriptionText.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 329 */       descriptionText.setEditable(false);
/*     */ 
/*     */       
/* 332 */       JPanel panel = new JPanel();
/* 333 */       getContentPane().add(panel, new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 0, new Insets(5, 5, 0, 5), 0, 0));
/*     */ 
/*     */ 
/*     */       
/* 337 */       panel.add(new JLabel(name + ":"));
/* 338 */       panel.add(component);
/*     */       
/* 340 */       JPanel buttonPanel = new JPanel();
/* 341 */       getContentPane().add(buttonPanel, new GridBagConstraints(0, 2, 2, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 346 */       JButton okButton = new JButton("OK");
/* 347 */       buttonPanel.add(okButton);
/* 348 */       okButton.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent evt) {
/* 350 */               EffectUtil.ValueDialog.this.okPressed = true;
/* 351 */               EffectUtil.ValueDialog.this.setVisible(false);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 356 */       JButton cancelButton = new JButton("Cancel");
/* 357 */       buttonPanel.add(cancelButton);
/* 358 */       cancelButton.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent evt) {
/* 360 */               EffectUtil.ValueDialog.this.setVisible(false);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 365 */       setSize(new Dimension(320, 175));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\font\effects\EffectUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */