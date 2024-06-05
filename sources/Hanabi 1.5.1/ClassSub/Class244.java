package ClassSub;

import javax.swing.*;

final class Class244 extends Class116.Class354
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
