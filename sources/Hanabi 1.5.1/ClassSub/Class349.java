package ClassSub;

import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;

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
