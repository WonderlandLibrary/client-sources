package ClassSub;

import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import java.awt.image.*;

class Class105 implements IImageBuffer
{
    final Class296 val$track;
    final ResourceLocation val$rl2;
    final Class286 this$0;
    
    
    Class105(final Class286 this$0, final Class296 val$track, final ResourceLocation val$rl2) {
        this.this$0 = this$0;
        this.val$track = val$track;
        this.val$rl2 = val$rl2;
    }
    
    public BufferedImage parseUserSkin(final BufferedImage bufferedImage) {
        return bufferedImage;
    }
    
    public void skinAvailable() {
        this.this$0.circleLocations.put(this.val$track.getId(), this.val$rl2);
    }
}
