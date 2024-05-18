package com.krazzzzymonkey.catalyst.module.modules.render;

import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.minecraft.util.math.BlockPos;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.util.math.RayTraceResult;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.krazzzzymonkey.catalyst.module.Modules;

public class BlockOverlay extends Modules
{

    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        if (Wrapper.INSTANCE.mc().objectMouseOver == null) {
            return;
        }
        if (Wrapper.INSTANCE.mc().objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            final Block block = Wrapper.INSTANCE.world().getBlockState(Wrapper.INSTANCE.mc().objectMouseOver.getBlockPos()).getBlock();
            final BlockPos blockpos = Wrapper.INSTANCE.mc().objectMouseOver.getBlockPos();
            if (Block.getIdFromBlock(block) == 0) {
                return;
            }
            RenderUtils.drawBlockESP(blockpos, 1.0f, 1.0f, 1.0f);
        }
        super.onRenderWorldLast(event);
    }

    
    public BlockOverlay() {
        super("BlockOverlay", ModuleCategory.RENDER);
    }
    

}
