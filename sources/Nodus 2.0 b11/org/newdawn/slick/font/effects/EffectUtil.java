/*   1:    */ package org.newdawn.slick.font.effects;
/*   2:    */ 
/*   3:    */ import java.awt.AlphaComposite;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.EventQueue;
/*   8:    */ import java.awt.Graphics2D;
/*   9:    */ import java.awt.GridBagConstraints;
/*  10:    */ import java.awt.GridBagLayout;
/*  11:    */ import java.awt.Insets;
/*  12:    */ import java.awt.event.ActionEvent;
/*  13:    */ import java.awt.event.ActionListener;
/*  14:    */ import java.awt.image.BufferedImage;
/*  15:    */ import javax.swing.BorderFactory;
/*  16:    */ import javax.swing.DefaultComboBoxModel;
/*  17:    */ import javax.swing.JButton;
/*  18:    */ import javax.swing.JCheckBox;
/*  19:    */ import javax.swing.JColorChooser;
/*  20:    */ import javax.swing.JComboBox;
/*  21:    */ import javax.swing.JComponent;
/*  22:    */ import javax.swing.JDialog;
/*  23:    */ import javax.swing.JFormattedTextField;
/*  24:    */ import javax.swing.JLabel;
/*  25:    */ import javax.swing.JPanel;
/*  26:    */ import javax.swing.JSpinner;
/*  27:    */ import javax.swing.JSpinner.DefaultEditor;
/*  28:    */ import javax.swing.JTextArea;
/*  29:    */ import javax.swing.SpinnerNumberModel;
/*  30:    */ 
/*  31:    */ public class EffectUtil
/*  32:    */ {
/*  33: 40 */   private static BufferedImage scratchImage = new BufferedImage(256, 256, 
/*  34: 41 */     2);
/*  35:    */   
/*  36:    */   public static BufferedImage getScratchImage()
/*  37:    */   {
/*  38: 49 */     Graphics2D g = (Graphics2D)scratchImage.getGraphics();
/*  39: 50 */     g.setComposite(AlphaComposite.Clear);
/*  40: 51 */     g.fillRect(0, 0, 256, 256);
/*  41: 52 */     g.setComposite(AlphaComposite.SrcOver);
/*  42: 53 */     g.setColor(Color.white);
/*  43: 54 */     return scratchImage;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static ConfigurableEffect.Value colorValue(String name, Color currentValue)
/*  47:    */   {
/*  48: 65 */     new DefaultValue(name, toString(currentValue))
/*  49:    */     {
/*  50:    */       public void showDialog()
/*  51:    */       {
/*  52: 67 */         Color newColor = JColorChooser.showDialog(null, "Choose a color", EffectUtil.fromString(this.value));
/*  53: 68 */         if (newColor != null) {
/*  54: 68 */           this.value = EffectUtil.toString(newColor);
/*  55:    */         }
/*  56:    */       }
/*  57:    */       
/*  58:    */       public Object getObject()
/*  59:    */       {
/*  60: 72 */         return EffectUtil.fromString(this.value);
/*  61:    */       }
/*  62:    */     };
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static ConfigurableEffect.Value intValue(String name, final int currentValue, final String description)
/*  66:    */   {
/*  67: 86 */     new DefaultValue(name, String.valueOf(currentValue))
/*  68:    */     {
/*  69:    */       public void showDialog()
/*  70:    */       {
/*  71: 88 */         JSpinner spinner = new JSpinner(new SpinnerNumberModel(currentValue, -32768, 32767, 1));
/*  72: 89 */         if (showValueDialog(spinner, description)) {
/*  73: 89 */           this.value = String.valueOf(spinner.getValue());
/*  74:    */         }
/*  75:    */       }
/*  76:    */       
/*  77:    */       public Object getObject()
/*  78:    */       {
/*  79: 93 */         return Integer.valueOf(this.value);
/*  80:    */       }
/*  81:    */     };
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static ConfigurableEffect.Value floatValue(String name, final float currentValue, final float min, final float max, final String description)
/*  85:    */   {
/*  86:110 */     new DefaultValue(name, String.valueOf(currentValue))
/*  87:    */     {
/*  88:    */       public void showDialog()
/*  89:    */       {
/*  90:112 */         JSpinner spinner = new JSpinner(new SpinnerNumberModel(currentValue, min, max, 0.1000000014901161D));
/*  91:113 */         if (showValueDialog(spinner, description)) {
/*  92:113 */           this.value = String.valueOf(((Double)spinner.getValue()).floatValue());
/*  93:    */         }
/*  94:    */       }
/*  95:    */       
/*  96:    */       public Object getObject()
/*  97:    */       {
/*  98:117 */         return Float.valueOf(this.value);
/*  99:    */       }
/* 100:    */     };
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static ConfigurableEffect.Value booleanValue(String name, final boolean currentValue, final String description)
/* 104:    */   {
/* 105:131 */     new DefaultValue(name, String.valueOf(currentValue))
/* 106:    */     {
/* 107:    */       public void showDialog()
/* 108:    */       {
/* 109:133 */         JCheckBox checkBox = new JCheckBox();
/* 110:134 */         checkBox.setSelected(currentValue);
/* 111:135 */         if (showValueDialog(checkBox, description)) {
/* 112:135 */           this.value = String.valueOf(checkBox.isSelected());
/* 113:    */         }
/* 114:    */       }
/* 115:    */       
/* 116:    */       public Object getObject()
/* 117:    */       {
/* 118:139 */         return Boolean.valueOf(this.value);
/* 119:    */       }
/* 120:    */     };
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static ConfigurableEffect.Value optionValue(String name, final String currentValue, final String[][] options, final String description)
/* 124:    */   {
/* 125:158 */     new DefaultValue(name, currentValue.toString())
/* 126:    */     {
/* 127:    */       public void showDialog()
/* 128:    */       {
/* 129:160 */         int selectedIndex = -1;
/* 130:161 */         DefaultComboBoxModel model = new DefaultComboBoxModel();
/* 131:162 */         for (int i = 0; i < options.length; i++)
/* 132:    */         {
/* 133:163 */           model.addElement(options[i][0]);
/* 134:164 */           if (getValue(i).equals(currentValue)) {
/* 135:164 */             selectedIndex = i;
/* 136:    */           }
/* 137:    */         }
/* 138:166 */         JComboBox comboBox = new JComboBox(model);
/* 139:167 */         comboBox.setSelectedIndex(selectedIndex);
/* 140:168 */         if (showValueDialog(comboBox, description)) {
/* 141:168 */           this.value = getValue(comboBox.getSelectedIndex());
/* 142:    */         }
/* 143:    */       }
/* 144:    */       
/* 145:    */       private String getValue(int i)
/* 146:    */       {
/* 147:172 */         if (options[i].length == 1) {
/* 148:172 */           return options[i][0];
/* 149:    */         }
/* 150:173 */         return options[i][1];
/* 151:    */       }
/* 152:    */       
/* 153:    */       public String toString()
/* 154:    */       {
/* 155:177 */         for (int i = 0; i < options.length; i++) {
/* 156:178 */           if (getValue(i).equals(this.value)) {
/* 157:178 */             return options[i][0].toString();
/* 158:    */           }
/* 159:    */         }
/* 160:179 */         return "";
/* 161:    */       }
/* 162:    */       
/* 163:    */       public Object getObject()
/* 164:    */       {
/* 165:183 */         return this.value;
/* 166:    */       }
/* 167:    */     };
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static String toString(Color color)
/* 171:    */   {
/* 172:195 */     if (color == null) {
/* 173:195 */       throw new IllegalArgumentException("color cannot be null.");
/* 174:    */     }
/* 175:196 */     String r = Integer.toHexString(color.getRed());
/* 176:197 */     if (r.length() == 1) {
/* 177:197 */       r = "0" + r;
/* 178:    */     }
/* 179:198 */     String g = Integer.toHexString(color.getGreen());
/* 180:199 */     if (g.length() == 1) {
/* 181:199 */       g = "0" + g;
/* 182:    */     }
/* 183:200 */     String b = Integer.toHexString(color.getBlue());
/* 184:201 */     if (b.length() == 1) {
/* 185:201 */       b = "0" + b;
/* 186:    */     }
/* 187:202 */     return r + g + b;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static Color fromString(String rgb)
/* 191:    */   {
/* 192:212 */     if ((rgb == null) || (rgb.length() != 6)) {
/* 193:212 */       return Color.white;
/* 194:    */     }
/* 195:213 */     return new Color(Integer.parseInt(rgb.substring(0, 2), 16), Integer.parseInt(rgb.substring(2, 4), 16), Integer.parseInt(rgb
/* 196:214 */       .substring(4, 6), 16));
/* 197:    */   }
/* 198:    */   
/* 199:    */   private static abstract class DefaultValue
/* 200:    */     implements ConfigurableEffect.Value
/* 201:    */   {
/* 202:    */     String value;
/* 203:    */     String name;
/* 204:    */     
/* 205:    */     public DefaultValue(String name, String value)
/* 206:    */     {
/* 207:233 */       this.value = value;
/* 208:234 */       this.name = name;
/* 209:    */     }
/* 210:    */     
/* 211:    */     public void setString(String value)
/* 212:    */     {
/* 213:241 */       this.value = value;
/* 214:    */     }
/* 215:    */     
/* 216:    */     public String getString()
/* 217:    */     {
/* 218:248 */       return this.value;
/* 219:    */     }
/* 220:    */     
/* 221:    */     public String getName()
/* 222:    */     {
/* 223:255 */       return this.name;
/* 224:    */     }
/* 225:    */     
/* 226:    */     public String toString()
/* 227:    */     {
/* 228:262 */       if (this.value == null) {
/* 229:263 */         return "";
/* 230:    */       }
/* 231:265 */       return this.value.toString();
/* 232:    */     }
/* 233:    */     
/* 234:    */     public boolean showValueDialog(final JComponent component, String description)
/* 235:    */     {
/* 236:276 */       EffectUtil.ValueDialog dialog = new EffectUtil.ValueDialog(component, this.name, description);
/* 237:277 */       dialog.setTitle(this.name);
/* 238:278 */       dialog.setLocationRelativeTo(null);
/* 239:279 */       EventQueue.invokeLater(new Runnable()
/* 240:    */       {
/* 241:    */         public void run()
/* 242:    */         {
/* 243:281 */           JComponent focusComponent = component;
/* 244:282 */           if ((focusComponent instanceof JSpinner)) {
/* 245:283 */             focusComponent = ((JSpinner.DefaultEditor)((JSpinner)component).getEditor()).getTextField();
/* 246:    */           }
/* 247:284 */           focusComponent.requestFocusInWindow();
/* 248:    */         }
/* 249:286 */       });
/* 250:287 */       dialog.setVisible(true);
/* 251:288 */       return dialog.okPressed;
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   private static class ValueDialog
/* 256:    */     extends JDialog
/* 257:    */   {
/* 258:297 */     public boolean okPressed = false;
/* 259:    */     
/* 260:    */     public ValueDialog(JComponent component, String name, String description)
/* 261:    */     {
/* 262:307 */       setDefaultCloseOperation(2);
/* 263:308 */       setLayout(new GridBagLayout());
/* 264:309 */       setModal(true);
/* 265:311 */       if ((component instanceof JSpinner)) {
/* 266:312 */         ((JSpinner.DefaultEditor)((JSpinner)component).getEditor()).getTextField().setColumns(4);
/* 267:    */       }
/* 268:314 */       JPanel descriptionPanel = new JPanel();
/* 269:315 */       descriptionPanel.setLayout(new GridBagLayout());
/* 270:316 */       getContentPane().add(
/* 271:317 */         descriptionPanel, 
/* 272:318 */         new GridBagConstraints(0, 0, 2, 1, 1.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 
/* 273:319 */         0), 0, 0));
/* 274:320 */       descriptionPanel.setBackground(Color.white);
/* 275:321 */       descriptionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
/* 276:    */       
/* 277:323 */       JTextArea descriptionText = new JTextArea(description);
/* 278:324 */       descriptionPanel.add(descriptionText, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 10, 
/* 279:325 */         1, new Insets(5, 5, 5, 5), 0, 0));
/* 280:326 */       descriptionText.setWrapStyleWord(true);
/* 281:327 */       descriptionText.setLineWrap(true);
/* 282:328 */       descriptionText.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
/* 283:329 */       descriptionText.setEditable(false);
/* 284:    */       
/* 285:    */ 
/* 286:332 */       JPanel panel = new JPanel();
/* 287:333 */       getContentPane().add(
/* 288:334 */         panel, 
/* 289:335 */         new GridBagConstraints(0, 1, 1, 1, 1.0D, 1.0D, 10, 0, new Insets(5, 5, 0, 
/* 290:336 */         5), 0, 0));
/* 291:337 */       panel.add(new JLabel(name + ":"));
/* 292:338 */       panel.add(component);
/* 293:    */       
/* 294:340 */       JPanel buttonPanel = new JPanel();
/* 295:341 */       getContentPane().add(
/* 296:342 */         buttonPanel, 
/* 297:343 */         new GridBagConstraints(0, 2, 2, 1, 0.0D, 0.0D, 13, 0, 
/* 298:344 */         new Insets(0, 0, 0, 0), 0, 0));
/* 299:    */       
/* 300:346 */       JButton okButton = new JButton("OK");
/* 301:347 */       buttonPanel.add(okButton);
/* 302:348 */       okButton.addActionListener(new ActionListener()
/* 303:    */       {
/* 304:    */         public void actionPerformed(ActionEvent evt)
/* 305:    */         {
/* 306:350 */           EffectUtil.ValueDialog.this.okPressed = true;
/* 307:351 */           EffectUtil.ValueDialog.this.setVisible(false);
/* 308:    */         }
/* 309:355 */       });
/* 310:356 */       JButton cancelButton = new JButton("Cancel");
/* 311:357 */       buttonPanel.add(cancelButton);
/* 312:358 */       cancelButton.addActionListener(new ActionListener()
/* 313:    */       {
/* 314:    */         public void actionPerformed(ActionEvent evt)
/* 315:    */         {
/* 316:360 */           EffectUtil.ValueDialog.this.setVisible(false);
/* 317:    */         }
/* 318:364 */       });
/* 319:365 */       setSize(new Dimension(320, 175));
/* 320:    */     }
/* 321:    */   }
/* 322:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.effects.EffectUtil
 * JD-Core Version:    0.7.0.1
 */