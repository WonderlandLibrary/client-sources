package ClassSub;

import com.google.common.base.*;
import net.minecraft.scoreboard.*;

class Class312 implements Predicate<Score>
{
    final Class287 this$0;
    
    
    Class312(final Class287 this$0) {
        this.this$0 = this$0;
    }
    
    public boolean apply(final Score score) {
        return score.getPlayerName() != null && !score.getPlayerName().startsWith("#");
    }
    
    public boolean apply(final Object o) {
        return this.apply((Score)o);
    }
}
