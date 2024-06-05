package ClassSub;

import javax.swing.*;

final class Class323 extends Class116.Class354
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
