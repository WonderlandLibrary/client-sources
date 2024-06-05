package ClassSub;

import javax.swing.*;
import java.awt.*;

final class Class235 extends Class116.Class354
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
