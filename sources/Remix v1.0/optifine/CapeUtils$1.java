package optifine;

import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import java.awt.image.*;

static final class CapeUtils$1 implements IImageBuffer {
    ImageBufferDownload ibd = new ImageBufferDownload();
    final /* synthetic */ AbstractClientPlayer val$player;
    final /* synthetic */ ResourceLocation val$rl;
    
    @Override
    public BufferedImage parseUserSkin(final BufferedImage var1) {
        return CapeUtils.parseCape(var1);
    }
    
    @Override
    public void func_152634_a() {
        this.val$player.setLocationOfCape(this.val$rl);
    }
}