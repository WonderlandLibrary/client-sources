package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.sound.SoundUtil;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.block.Block;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;

@ModuleInfo(aliases = {"module.render.killeffect.name"}, description = "module.render.killeffect.description", category = Category.RENDER)
public final class KillEffect extends Module {

    private final BooleanValue lightning = new BooleanValue("Lightning", this, true);
    private final BooleanValue blood = new BooleanValue("Blood Explosion", this, true);

    private final BooleanValue explosion = new BooleanValue("Explosion", this, true);

    private EntityLivingBase target;

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (this.target != null && !mc.theWorld.loadedEntityList.contains(this.target)) {
            if (this.lightning.getValue()) {
                final EntityLightningBolt entityLightningBolt = new EntityLightningBolt(mc.theWorld, target.posX, target.posY, target.posZ);
                mc.theWorld.addEntityToWorld((int) (-Math.random() * 100000), entityLightningBolt);

                SoundUtil.playSound("ambient.weather.thunder");
            }

            if (this.explosion.getValue()) {
                for (int i = 0; i <= 8; i++) {
                    mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.FLAME);
                }

                SoundUtil.playSound("item.fireCharge.use");
            }

            if (this.blood.getValue()) {
                double startY = target.posY;
                double endY = target.posY + target.height+.4;
                double step = 0.4;
                for (int i = 0; i < 100; i++)

                    for (double y = startY; y <= endY; y += step) {
                    mc.theWorld.spawnParticle(EnumParticleTypes.BLOCK_CRACK, target.posX, y, target.posZ, 0, 0, 0, Block.getStateId(Blocks.redstone_block.getDefaultState()));

                     }

                for (double y = startY; y <= endY; y += step) {
                    SoundUtil.playSound("dig.stone");
                }


        }

            this.target = null;
        }
    };

    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        final Entity entity = event.getTarget();

        if (entity instanceof EntityLivingBase) {
            target = (EntityLivingBase) entity;
        }
    };
}