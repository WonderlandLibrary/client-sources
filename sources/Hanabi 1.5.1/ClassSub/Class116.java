package ClassSub;

import java.awt.image.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;

public class Class116
{
    private static BufferedImage scratchImage;
    
    
    public static BufferedImage getScratchImage() {
        final Graphics2D graphics2D = (Graphics2D)Class116.scratchImage.getGraphics();
        graphics2D.setComposite(AlphaComposite.Clear);
        graphics2D.fillRect(0, 0, 256, 256);
        graphics2D.setComposite(AlphaComposite.SrcOver);
        graphics2D.setColor(Color.white);
        return Class116.scratchImage;
    }
    
    public static Class193.Class111 colorValue(final String s, final Color color) {
        final class Class235 extends Class354
        {
            
            
            Class235(final String s, final String s2) {
                super(s, s2);
            }
            
            @Override
            public void showDialog() {
                final Color showDialog = JColorChooser.showDialog(null, "Choose a color", Class116.fromString(this.value));
                if (showDialog != null) {
                    this.value = Class116.toString(showDialog);
                }
            }
            
            @Override
            public Object getObject() {
                return Class116.fromString(this.value);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: nop            
        //     4: athrow         
        //     5: new             LClassSub/Class235;
        //     8: dup            
        //     9: aload_0        
        //    10: aload_1        
        //    11: invokestatic    ClassSub/Class116.toString:(Ljava/awt/Color;)Ljava/lang/String;
        //    14: invokespecial   ClassSub/Class235.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //    17: areturn        
        //    18: nop            
        //    19: nop            
        //    20: nop            
        //    21: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static Class193.Class111 intValue(final String s, final int n, final String s2) {
        final class Class323 extends Class354
        {
            final int val$currentValue;
            final String val$description;
            
            
            Class323(final String s, final String s2, final int val$currentValue, final String val$description) {
                this.val$currentValue = val$currentValue;
                this.val$description = val$description;
                super(s, s2);
            }
            
            @Override
            public void showDialog() {
                final JSpinner spinner = new JSpinner(new SpinnerNumberModel(this.val$currentValue, -32768, 32767, 1));
                if (this.showValueDialog(spinner, this.val$description)) {
                    this.value = String.valueOf(spinner.getValue());
                }
            }
            
            @Override
            public Object getObject() {
                return Integer.valueOf(this.value);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: nop            
        //     4: athrow         
        //     5: new             LClassSub/Class323;
        //     8: dup            
        //     9: aload_0        
        //    10: iload_1        
        //    11: invokestatic    java/lang/String.valueOf:(I)Ljava/lang/String;
        //    14: iload_1        
        //    15: aload_2        
        //    16: invokespecial   ClassSub/Class323.<init>:(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V
        //    19: areturn        
        //    20: nop            
        //    21: nop            
        //    22: nop            
        //    23: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static Class193.Class111 floatValue(final String s, final float n, final float n2, final float n3, final String s2) {
        final class Class106 extends Class354
        {
            final float val$currentValue;
            final float val$min;
            final float val$max;
            final String val$description;
            
            
            Class106(final String s, final String s2, final float val$currentValue, final float val$min, final float val$max, final String val$description) {
                this.val$currentValue = val$currentValue;
                this.val$min = val$min;
                this.val$max = val$max;
                this.val$description = val$description;
                super(s, s2);
            }
            
            @Override
            public void showDialog() {
                final JSpinner spinner = new JSpinner(new SpinnerNumberModel(this.val$currentValue, this.val$min, this.val$max, 0.10000000149011612));
                if (this.showValueDialog(spinner, this.val$description)) {
                    this.value = String.valueOf((float)spinner.getValue());
                }
            }
            
            @Override
            public Object getObject() {
                return Float.valueOf(this.value);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: nop            
        //     4: athrow         
        //     5: new             LClassSub/Class106;
        //     8: dup            
        //     9: aload_0        
        //    10: fload_1        
        //    11: invokestatic    java/lang/String.valueOf:(F)Ljava/lang/String;
        //    14: fload_1        
        //    15: fload_2        
        //    16: fload_3        
        //    17: aload           4
        //    19: invokespecial   ClassSub/Class106.<init>:(Ljava/lang/String;Ljava/lang/String;FFFLjava/lang/String;)V
        //    22: areturn        
        //    23: nop            
        //    24: nop            
        //    25: nop            
        //    26: nop            
        //    27: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static Class193.Class111 booleanValue(final String s, final boolean b, final String s2) {
        final class Class244 extends Class354
        {
            final boolean val$currentValue;
            final String val$description;
            
            
            Class244(final String s, final String s2, final boolean val$currentValue, final String val$description) {
                this.val$currentValue = val$currentValue;
                this.val$description = val$description;
                super(s, s2);
            }
            
            @Override
            public void showDialog() {
                final JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected(this.val$currentValue);
                if (this.showValueDialog(checkBox, this.val$description)) {
                    this.value = String.valueOf(checkBox.isSelected());
                }
            }
            
            @Override
            public Object getObject() {
                return Boolean.valueOf(this.value);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: nop            
        //     4: athrow         
        //     5: new             LClassSub/Class244;
        //     8: dup            
        //     9: aload_0        
        //    10: iload_1        
        //    11: invokestatic    java/lang/String.valueOf:(Z)Ljava/lang/String;
        //    14: iload_1        
        //    15: aload_2        
        //    16: invokespecial   ClassSub/Class244.<init>:(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;)V
        //    19: areturn        
        //    20: nop            
        //    21: nop            
        //    22: nop            
        //    23: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static Class193.Class111 optionValue(final String s, final String s2, final String[][] array, final String s3) {
        final class Class207 extends Class354
        {
            final String[][] val$options;
            final String val$currentValue;
            final String val$description;
            
            
            Class207(final String s, final String s2, final String[][] val$options, final String val$currentValue, final String val$description) {
                this.val$options = val$options;
                this.val$currentValue = val$currentValue;
                this.val$description = val$description;
                super(s, s2);
            }
            
            @Override
            public void showDialog() {
                int selectedIndex = -1;
                final DefaultComboBoxModel<Object> defaultComboBoxModel = new DefaultComboBoxModel<Object>();
                for (int i = 0; i < this.val$options.length; ++i) {
                    defaultComboBoxModel.addElement(this.val$options[i][0]);
                    if (this.getValue(i).equals(this.val$currentValue)) {
                        selectedIndex = i;
                    }
                }
                final JComboBox comboBox = new JComboBox(defaultComboBoxModel);
                comboBox.setSelectedIndex(selectedIndex);
                if (this.showValueDialog(comboBox, this.val$description)) {
                    this.value = this.getValue(comboBox.getSelectedIndex());
                }
            }
            
            private String getValue(final int n) {
                if (this.val$options[n].length == 1) {
                    return this.val$options[n][0];
                }
                return this.val$options[n][1];
            }
            
            @Override
            public String toString() {
                for (int i = 0; i < this.val$options.length; ++i) {
                    if (this.getValue(i).equals(this.value)) {
                        return this.val$options[i][0].toString();
                    }
                }
                return "";
            }
            
            @Override
            public Object getObject() {
                return this.value;
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: nop            
        //     4: athrow         
        //     5: new             LClassSub/Class207;
        //     8: dup            
        //     9: aload_0        
        //    10: aload_1        
        //    11: invokevirtual   java/lang/String.toString:()Ljava/lang/String;
        //    14: aload_2        
        //    15: aload_1        
        //    16: aload_3        
        //    17: invokespecial   ClassSub/Class207.<init>:(Ljava/lang/String;Ljava/lang/String;[[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
        //    20: areturn        
        //    21: nop            
        //    22: nop            
        //    23: nop            
        //    24: nop            
        //    25: athrow         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static String toString(final Color color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null.");
        }
        String s = Integer.toHexString(color.getRed());
        if (s.length() == 1) {
            s = "0" + s;
        }
        String s2 = Integer.toHexString(color.getGreen());
        if (s2.length() == 1) {
            s2 = "0" + s2;
        }
        String s3 = Integer.toHexString(color.getBlue());
        if (s3.length() == 1) {
            s3 = "0" + s3;
        }
        return s + s2 + s3;
    }
    
    public static Color fromString(final String s) {
        if (s == null || s.length() != 6) {
            return Color.white;
        }
        return new Color(Integer.parseInt(s.substring(0, 2), 16), Integer.parseInt(s.substring(2, 4), 16), Integer.parseInt(s.substring(4, 6), 16));
    }
    
    static {
        Class116.scratchImage = new BufferedImage(256, 256, 2);
    }
    
    private abstract static class Class354 implements Class193.Class111
    {
        String value;
        String name;
        
        
        public Class354(final String name, final String value) {
            this.value = value;
            this.name = name;
        }
        
        @Override
        public void setString(final String value) {
            this.value = value;
        }
        
        @Override
        public String getString() {
            return this.value;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            if (this.value == null) {
                return "";
            }
            return this.value.toString();
        }
        
        public boolean showValueDialog(final JComponent component, final String s) {
            class Class283 implements Runnable
            {
                final JComponent val$component;
                final Class354 this$0;
                
                
                Class283(final Class354 this$0, final JComponent val$component) {
                    this.this$0 = this$0;
                    this.val$component = val$component;
                }
                
                @Override
                public void run() {
                    JComponent component = this.val$component;
                    if (component instanceof JSpinner) {
                        component = ((JSpinner.DefaultEditor)((JSpinner)this.val$component).getEditor()).getTextField();
                    }
                    component.requestFocusInWindow();
                }
            }
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     3: nop            
            //     4: athrow         
            //     5: nop            
            //     6: nop            
            //     7: nop            
            //     8: nop            
            //     9: nop            
            //    10: nop            
            //    11: nop            
            //    12: nop            
            //    13: nop            
            //    14: nop            
            //    15: nop            
            //    16: nop            
            //    17: athrow         
            //    18: new             LClassSub/Class349;
            //    21: dup            
            //    22: aload_1        
            //    23: aload_0        
            //    24: getfield        ClassSub/Class354.name:Ljava/lang/String;
            //    27: aload_2        
            //    28: invokespecial   ClassSub/Class349.<init>:(Ljavax/swing/JComponent;Ljava/lang/String;Ljava/lang/String;)V
            //    31: astore_3       
            //    32: aload_3        
            //    33: aload_0        
            //    34: getfield        ClassSub/Class354.name:Ljava/lang/String;
            //    37: invokevirtual   ClassSub/Class349.setTitle:(Ljava/lang/String;)V
            //    40: aload_3        
            //    41: aconst_null    
            //    42: invokevirtual   ClassSub/Class349.setLocationRelativeTo:(Ljava/awt/Component;)V
            //    45: new             LClassSub/Class283;
            //    48: dup            
            //    49: aload_0        
            //    50: aload_1        
            //    51: invokespecial   ClassSub/Class283.<init>:(LClassSub/Class354;Ljavax/swing/JComponent;)V
            //    54: invokestatic    java/awt/EventQueue.invokeLater:(Ljava/lang/Runnable;)V
            //    57: aload_3        
            //    58: ldc             1
            //    60: invokevirtual   ClassSub/Class349.setVisible:(Z)V
            //    63: aload_3        
            //    64: getfield        ClassSub/Class349.okPressed:Z
            //    67: ireturn        
            //    68: nop            
            //    69: nop            
            //    70: nop            
            //    71: nop            
            //    72: athrow         
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
    }
    
    private static class Class349 extends JDialog
    {
        public boolean okPressed;
        
        
        public Class349(final JComponent component, final String s, final String s2) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     3: athrow         
            //     4: aload_0        
            //     5: invokespecial   javax/swing/JDialog.<init>:()V
            //     8: aload_0        
            //     9: ldc             0
            //    11: putfield        ClassSub/Class349.okPressed:Z
            //    14: aload_0        
            //    15: ldc             2
            //    17: invokevirtual   ClassSub/Class349.setDefaultCloseOperation:(I)V
            //    20: aload_0        
            //    21: new             Ljava/awt/GridBagLayout;
            //    24: dup            
            //    25: invokespecial   java/awt/GridBagLayout.<init>:()V
            //    28: invokevirtual   ClassSub/Class349.setLayout:(Ljava/awt/LayoutManager;)V
            //    31: aload_0        
            //    32: ldc             1
            //    34: invokevirtual   ClassSub/Class349.setModal:(Z)V
            //    37: aload_1        
            //    38: instanceof      Ljavax/swing/JSpinner;
            //    41: ifeq            62
            //    44: aload_1        
            //    45: checkcast       Ljavax/swing/JSpinner;
            //    48: invokevirtual   javax/swing/JSpinner.getEditor:()Ljavax/swing/JComponent;
            //    51: checkcast       Ljavax/swing/JSpinner$DefaultEditor;
            //    54: invokevirtual   javax/swing/JSpinner$DefaultEditor.getTextField:()Ljavax/swing/JFormattedTextField;
            //    57: ldc             4
            //    59: invokevirtual   javax/swing/JFormattedTextField.setColumns:(I)V
            //    62: new             Ljavax/swing/JPanel;
            //    65: dup            
            //    66: invokespecial   javax/swing/JPanel.<init>:()V
            //    69: astore          4
            //    71: aload           4
            //    73: new             Ljava/awt/GridBagLayout;
            //    76: dup            
            //    77: invokespecial   java/awt/GridBagLayout.<init>:()V
            //    80: invokevirtual   javax/swing/JPanel.setLayout:(Ljava/awt/LayoutManager;)V
            //    83: aload_0        
            //    84: invokevirtual   ClassSub/Class349.getContentPane:()Ljava/awt/Container;
            //    87: aload           4
            //    89: new             Ljava/awt/GridBagConstraints;
            //    92: dup            
            //    93: ldc             0
            //    95: ldc             0
            //    97: ldc             2
            //    99: ldc             1
            //   101: dconst_1       
            //   102: dconst_0       
            //   103: ldc             10
            //   105: ldc             1
            //   107: new             Ljava/awt/Insets;
            //   110: dup            
            //   111: ldc             0
            //   113: ldc             0
            //   115: ldc             0
            //   117: ldc             0
            //   119: invokespecial   java/awt/Insets.<init>:(IIII)V
            //   122: ldc             0
            //   124: ldc             0
            //   126: invokespecial   java/awt/GridBagConstraints.<init>:(IIIIDDIILjava/awt/Insets;II)V
            //   129: invokevirtual   java/awt/Container.add:(Ljava/awt/Component;Ljava/lang/Object;)V
            //   132: aload           4
            //   134: getstatic       java/awt/Color.white:Ljava/awt/Color;
            //   137: invokevirtual   javax/swing/JPanel.setBackground:(Ljava/awt/Color;)V
            //   140: aload           4
            //   142: ldc             0
            //   144: ldc             0
            //   146: ldc             1
            //   148: ldc             0
            //   150: getstatic       java/awt/Color.black:Ljava/awt/Color;
            //   153: invokestatic    javax/swing/BorderFactory.createMatteBorder:(IIIILjava/awt/Color;)Ljavax/swing/border/MatteBorder;
            //   156: invokevirtual   javax/swing/JPanel.setBorder:(Ljavax/swing/border/Border;)V
            //   159: new             Ljavax/swing/JTextArea;
            //   162: dup            
            //   163: aload_3        
            //   164: invokespecial   javax/swing/JTextArea.<init>:(Ljava/lang/String;)V
            //   167: astore          5
            //   169: aload           4
            //   171: aload           5
            //   173: new             Ljava/awt/GridBagConstraints;
            //   176: dup            
            //   177: ldc             0
            //   179: ldc             0
            //   181: ldc             1
            //   183: ldc             1
            //   185: dconst_1       
            //   186: dconst_0       
            //   187: ldc             10
            //   189: ldc             1
            //   191: new             Ljava/awt/Insets;
            //   194: dup            
            //   195: ldc             5
            //   197: ldc             5
            //   199: ldc             5
            //   201: ldc             5
            //   203: invokespecial   java/awt/Insets.<init>:(IIII)V
            //   206: ldc             0
            //   208: ldc             0
            //   210: invokespecial   java/awt/GridBagConstraints.<init>:(IIIIDDIILjava/awt/Insets;II)V
            //   213: invokevirtual   javax/swing/JPanel.add:(Ljava/awt/Component;Ljava/lang/Object;)V
            //   216: aload           5
            //   218: ldc             1
            //   220: invokevirtual   javax/swing/JTextArea.setWrapStyleWord:(Z)V
            //   223: aload           5
            //   225: ldc             1
            //   227: invokevirtual   javax/swing/JTextArea.setLineWrap:(Z)V
            //   230: aload           5
            //   232: ldc             0
            //   234: ldc             0
            //   236: ldc             0
            //   238: ldc             0
            //   240: invokestatic    javax/swing/BorderFactory.createEmptyBorder:(IIII)Ljavax/swing/border/Border;
            //   243: invokevirtual   javax/swing/JTextArea.setBorder:(Ljavax/swing/border/Border;)V
            //   246: aload           5
            //   248: ldc             0
            //   250: invokevirtual   javax/swing/JTextArea.setEditable:(Z)V
            //   253: new             Ljavax/swing/JPanel;
            //   256: dup            
            //   257: invokespecial   javax/swing/JPanel.<init>:()V
            //   260: astore          5
            //   262: aload_0        
            //   263: invokevirtual   ClassSub/Class349.getContentPane:()Ljava/awt/Container;
            //   266: aload           5
            //   268: new             Ljava/awt/GridBagConstraints;
            //   271: dup            
            //   272: ldc             0
            //   274: ldc             1
            //   276: ldc             1
            //   278: ldc             1
            //   280: dconst_1       
            //   281: dconst_1       
            //   282: ldc             10
            //   284: ldc             0
            //   286: new             Ljava/awt/Insets;
            //   289: dup            
            //   290: ldc             5
            //   292: ldc             5
            //   294: ldc             0
            //   296: ldc             5
            //   298: invokespecial   java/awt/Insets.<init>:(IIII)V
            //   301: ldc             0
            //   303: ldc             0
            //   305: invokespecial   java/awt/GridBagConstraints.<init>:(IIIIDDIILjava/awt/Insets;II)V
            //   308: invokevirtual   java/awt/Container.add:(Ljava/awt/Component;Ljava/lang/Object;)V
            //   311: aload           5
            //   313: new             Ljavax/swing/JLabel;
            //   316: dup            
            //   317: new             Ljava/lang/StringBuilder;
            //   320: dup            
            //   321: invokespecial   java/lang/StringBuilder.<init>:()V
            //   324: aload_2        
            //   325: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   328: ldc             ":"
            //   330: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   333: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   336: invokespecial   javax/swing/JLabel.<init>:(Ljava/lang/String;)V
            //   339: invokevirtual   javax/swing/JPanel.add:(Ljava/awt/Component;)Ljava/awt/Component;
            //   342: pop            
            //   343: aload           5
            //   345: aload_1        
            //   346: invokevirtual   javax/swing/JPanel.add:(Ljava/awt/Component;)Ljava/awt/Component;
            //   349: pop            
            //   350: new             Ljavax/swing/JPanel;
            //   353: dup            
            //   354: invokespecial   javax/swing/JPanel.<init>:()V
            //   357: astore          6
            //   359: aload_0        
            //   360: invokevirtual   ClassSub/Class349.getContentPane:()Ljava/awt/Container;
            //   363: aload           6
            //   365: new             Ljava/awt/GridBagConstraints;
            //   368: dup            
            //   369: ldc             0
            //   371: ldc             2
            //   373: ldc             2
            //   375: ldc             1
            //   377: dconst_0       
            //   378: dconst_0       
            //   379: ldc             13
            //   381: ldc             0
            //   383: new             Ljava/awt/Insets;
            //   386: dup            
            //   387: ldc             0
            //   389: ldc             0
            //   391: ldc             0
            //   393: ldc             0
            //   395: invokespecial   java/awt/Insets.<init>:(IIII)V
            //   398: ldc             0
            //   400: ldc             0
            //   402: invokespecial   java/awt/GridBagConstraints.<init>:(IIIIDDIILjava/awt/Insets;II)V
            //   405: invokevirtual   java/awt/Container.add:(Ljava/awt/Component;Ljava/lang/Object;)V
            //   408: new             Ljavax/swing/JButton;
            //   411: dup            
            //   412: ldc             "OK"
            //   414: invokespecial   javax/swing/JButton.<init>:(Ljava/lang/String;)V
            //   417: astore          7
            //   419: aload           6
            //   421: aload           7
            //   423: invokevirtual   javax/swing/JPanel.add:(Ljava/awt/Component;)Ljava/awt/Component;
            //   426: pop            
            //   427: aload           7
            //   429: new             LClassSub/Class253;
            //   432: dup            
            //   433: aload_0        
            //   434: invokespecial   ClassSub/Class253.<init>:(LClassSub/Class349;)V
            //   437: invokevirtual   javax/swing/JButton.addActionListener:(Ljava/awt/event/ActionListener;)V
            //   440: new             Ljavax/swing/JButton;
            //   443: dup            
            //   444: ldc             "Cancel"
            //   446: invokespecial   javax/swing/JButton.<init>:(Ljava/lang/String;)V
            //   449: astore          7
            //   451: aload           6
            //   453: aload           7
            //   455: invokevirtual   javax/swing/JPanel.add:(Ljava/awt/Component;)Ljava/awt/Component;
            //   458: pop            
            //   459: aload           7
            //   461: new             LClassSub/Class166;
            //   464: dup            
            //   465: aload_0        
            //   466: invokespecial   ClassSub/Class166.<init>:(LClassSub/Class349;)V
            //   469: invokevirtual   javax/swing/JButton.addActionListener:(Ljava/awt/event/ActionListener;)V
            //   472: aload_0        
            //   473: new             Ljava/awt/Dimension;
            //   476: dup            
            //   477: ldc             320
            //   479: ldc             175
            //   481: invokespecial   java/awt/Dimension.<init>:(II)V
            //   484: invokevirtual   ClassSub/Class349.setSize:(Ljava/awt/Dimension;)V
            //   487: return         
            //   488: nop            
            //   489: nop            
            //   490: nop            
            //   491: nop            
            //   492: athrow         
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
    }
}
