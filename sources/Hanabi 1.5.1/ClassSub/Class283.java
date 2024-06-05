package ClassSub;

import javax.swing.*;

class Class283 implements Runnable
{
    final JComponent val$component;
    final Class116.Class354 this$0;
    
    
    Class283(final Class116.Class354 this$0, final JComponent val$component) {
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
