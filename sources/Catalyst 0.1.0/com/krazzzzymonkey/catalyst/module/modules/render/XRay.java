package com.krazzzzymonkey.catalyst.module.modules.render;

import java.util.Iterator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EntityLivingBase;
import com.krazzzzymonkey.catalyst.utils.BlockUtils;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.krazzzzymonkey.catalyst.xray.XRayData;
import com.krazzzzymonkey.catalyst.managers.XRayManager;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.krazzzzymonkey.catalyst.utils.TimerUtils;
import com.krazzzzymonkey.catalyst.xray.XRayBlock;
import java.util.LinkedList;
import com.krazzzzymonkey.catalyst.value.NumberValue;
import com.krazzzzymonkey.catalyst.module.Modules;

public class XRay extends Modules
{

    public NumberValue distance;
    LinkedList<XRayBlock> blocks;
    public TimerUtils timer;
    public NumberValue delay;

    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        RenderUtils.drawXRayBlocks(this.blocks, event.getPartialTicks());
        super.onRenderWorldLast(event);
    }
    
    private static boolean lIIlIllIlI(final int lIllllIlIlllllI, final int lIllllIlIllllIl) {
        return lIllllIlIlllllI < lIllllIlIllllIl;
    }
    
    public XRay() {
        super("XRay", ModuleCategory.RENDER);
        this.blocks = new LinkedList<XRayBlock>();
        this.distance = new NumberValue("Distance", 50.0, 4.0, 100.0);
        this.delay = new NumberValue("UpdateDelay", 100.0, 0.0, 300.0);
        this.timer = new TimerUtils();
        final Value[] value = new Value[2];
        value[0] = this.distance;
        value[1] = this.delay;
        this.addValue(value);
    }

    public void onEnable() {
        this.blocks.clear();
    }

    private static boolean lIIlIllIII(final int lIllllIlIlllIll) {
        return lIllllIlIlllIll != 0;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        final int lIllllIllllIlII = this.distance.getValue().intValue();
        if (!(this.timer.isDelay(this.delay.getValue().intValue() * 10))) {
            return;
        }
        this.blocks.clear();
        final short list = (short)XRayManager.xrayList.iterator();
        while (((Iterator)list).hasNext()) {
            final XRayData data = ((Iterator<XRayData>)list).next();
            final Exception exception = (Exception)BlockUtils.findBlocksNearEntity((EntityLivingBase)Wrapper.INSTANCE.player(), data.getId(), data.getMeta(), lIllllIllllIlII).iterator();
            while (((Iterator)exception).hasNext()) {
                final BlockPos blockPos = ((Iterator<BlockPos>)exception).next();
                final XRayBlock block = new XRayBlock(blockPos, data);
                this.blocks.add(block);
            }
        }
        this.timer.setLastMS();
        super.onClientTick(event);
    }
}
