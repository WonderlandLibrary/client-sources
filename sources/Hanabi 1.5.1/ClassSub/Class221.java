package ClassSub;

import java.security.*;

class Class221 implements PrivilegedAction
{
    final Class225 this$0;
    
    
    Class221(final Class225 this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public Object run() {
        try {
            Class225.DEFAULT_FONT = new Class182("org/newdawn/slick/data/defaultfont.fnt", "org/newdawn/slick/data/defaultfont.png");
        }
        catch (Class341 class341) {
            Class301.error(class341);
        }
        return null;
    }
}
