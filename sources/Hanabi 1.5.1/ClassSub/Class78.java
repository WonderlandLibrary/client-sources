package ClassSub;

import cn.Hanabi.modules.*;
import java.util.*;

class Class78 extends Class204
{
    final Mod val$selectedMod;
    final Class77 this$0;
    
    
    Class78(final Class77 this$0, final String s, final boolean b, final Mod val$selectedMod) {
        this.this$0 = this$0;
        this.val$selectedMod = val$selectedMod;
        super(s, b);
    }
    
    @Override
    public void onPress() {
        this.val$selectedMod.set(!this.val$selectedMod.isEnabled());
        for (final Class281 class281 : this.this$0.mods) {
            if (class281.mod == this.val$selectedMod) {
                class281.button.toggle();
                break;
            }
        }
        super.onPress();
    }
}
