package ClassSub;

import javax.swing.*;

final class Class106 extends Class116.Class354
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
