package me.travis.wurstplus.module.modules.combat;

import java.awt.Color;

import me.travis.wurstplus.event.events.RenderEvent;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.EntityUtil;
import me.travis.wurstplus.util.wurstplusTessellator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.world.World;
import me.travis.wurstplus.util.Friends;

@Module.Info(name="Fucked Detector", category=Module.Category.COMBAT)
public class FuckedDetect extends Module {

    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(255).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(255).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
    private Setting<Integer> alpha = this.register(Settings.integerBuilder("Transparency").withRange(0, 255).withValue(70).build());
    private Setting<Integer> distance = this.register(Settings.integerBuilder("Draw Distance").withRange(0, 30).withValue(20).build());
    private Setting<Boolean> drawFriends = this.register(Settings.b("Draw Friends", false));
    private Setting<Boolean> drawOwn = this.register(Settings.b("Draw Own", false));
    private Setting<RenderMode> renderMode = this.register(Settings.e("Render Mode", RenderMode.SOLID));

    public Set<EntityPlayer> fuckedPlayers;

    @Override
    public void onEnable() {
        fuckedPlayers = new HashSet<EntityPlayer>();
    }

    @Override
    public void onUpdate() {
        if (mc.player.isDead || mc.player == null || this.isDisabled()) {
            return;
        }
        getList();
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        for (EntityPlayer e : this.fuckedPlayers) {
            this.drawBlock(new BlockPos(e.posX,e.posY,e.posZ), this.red.getValue(), this.green.getValue(), this.blue.getValue());
        }
    }

    private void drawBlock(final BlockPos blockPos, final int r, final int g, final int b) {
        final Color color = new Color(r, g, b, this.alpha.getValue());
        wurstplusTessellator.prepare(7);
        if (this.renderMode.getValue().equals(RenderMode.UP)) {
            wurstplusTessellator.drawBox(blockPos, color.getRGB(), 2);
        } else if (this.renderMode.getValue().equals(RenderMode.SOLID)) {
            wurstplusTessellator.drawBox(blockPos, color.getRGB(), 63);
        } else if (this.renderMode.getValue().equals(RenderMode.OUTLINE)) {
            IBlockState iBlockState2 = CrystalAura.mc.world.getBlockState(blockPos);
            Vec3d interp2 = interpolateEntity((Entity)CrystalAura.mc.player, mc.getRenderPartialTicks());
            wurstplusTessellator.drawBoundingBox(iBlockState2.getSelectedBoundingBox((World)CrystalAura.mc.world, blockPos).offset(-interp2.x, -interp2.y, -interp2.z), 1.5f, r, g, b, this.alpha.getValue());
        } else {
            IBlockState iBlockState3 = CrystalAura.mc.world.getBlockState(blockPos);
            Vec3d interp3 = interpolateEntity((Entity)CrystalAura.mc.player, mc.getRenderPartialTicks());
            wurstplusTessellator.drawFullBox(iBlockState3.getSelectedBoundingBox((World)CrystalAura.mc.world, blockPos).offset(-interp3.x, -interp3.y, -interp3.z), blockPos, 1.5f, r, g, b, this.alpha.getValue());
        }
        wurstplusTessellator.release();
    }

    public static Vec3d interpolateEntity(Entity entity, float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)time);
    }

    private boolean canPlaceCrystal(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        return (CrystalAura.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || CrystalAura.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && CrystalAura.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && CrystalAura.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && CrystalAura.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty() && CrystalAura.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }

    public Boolean checkHole(EntityPlayer ent) {
        BlockPos pos = new BlockPos(ent.posX, ent.posY-1, ent.posZ);
        if (mc.world.getBlockState(pos).getBlock() != Blocks.AIR) {
            if (canPlaceCrystal(pos.south()) || (canPlaceCrystal(pos.south().south()) && mc.world.getBlockState(pos.add(0, 1, 1)).getBlock() == Blocks.AIR)) {
                return true;
            } else if (canPlaceCrystal(pos.east()) || (canPlaceCrystal(pos.east().east()) && mc.world.getBlockState(pos.add(1, 1, 0)).getBlock() == Blocks.AIR)) {
                return true;
            } else if (canPlaceCrystal(pos.west()) || (canPlaceCrystal(pos.west().west()) && mc.world.getBlockState(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR)) {
                return true;
            } else if (canPlaceCrystal(pos.north()) || (canPlaceCrystal(pos.north().north()) && mc.world.getBlockState(pos.add(0, 1, -1)).getBlock() == Blocks.AIR)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public Set<EntityPlayer> getList() {
        this.fuckedPlayers.clear();
        for (EntityPlayer ent : mc.world.playerEntities) {
            if (!EntityUtil.isLiving(ent) ||((EntityLivingBase)ent).getHealth() <= 0.0f) continue;
            if (checkHole(ent)) {
                if (!this.drawOwn.getValue() && ent.getName() == mc.player.getName()) continue;
                if (!this.drawFriends.getValue() && Friends.isFriend(ent.getName())) continue;
                if (mc.player.getDistance(ent) > this.distance.getValue()) continue;

                this.fuckedPlayers.add(ent);
            }
        }
        return this.fuckedPlayers;
    }

    private static enum RenderMode {
        SOLID,
        OUTLINE,
        UP,
        FULL;   
    }
}

