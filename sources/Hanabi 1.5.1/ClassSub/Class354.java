package ClassSub;

import javax.swing.*;
import java.awt.*;

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
