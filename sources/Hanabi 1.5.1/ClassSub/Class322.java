package ClassSub;

import cn.Hanabi.value.*;

class Class322 extends Class204
{
    final Value val$value;
    final Class77 this$0;
    
    
    Class322(final Class77 this$0, final String s, final boolean b, final Value val$value) {
        this.this$0 = this$0;
        this.val$value = val$value;
        super(s, b);
    }
    
    @Override
    public void onPress() {
        if (!this.parent.equals(Class281.selectedMod.getName())) {
            return;
        }
        this.val$value.setValueState(!this.val$value.getValueState());
        super.onPress();
    }
}
