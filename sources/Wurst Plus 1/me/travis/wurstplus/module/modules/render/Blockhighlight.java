package me.travis.wurstplus.module.modules.render;

import java.awt.Color;

import me.travis.wurstplus.event.events.RenderEvent;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.wurstplusTessellator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;

@Module.Info(name="Block Highlight", category=Module.Category.RENDER)

public class Blockhighlight extends Module {

    private Setting<Float> width = this.register(Settings.floatBuilder("Width").withMinimum(0.0f).withValue(2.5f).build());
    private Setting<Boolean> rainbow = this.register(Settings.b("RainbowMode", false));
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(255).withVisibility(o -> !rainbow.getValue()).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(255).withVisibility(o -> !rainbow.getValue()).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).withVisibility(o -> !rainbow.getValue()).build());
    private Setting<Float> satuation = this.register(Settings.floatBuilder("Saturation").withRange(0.0f, 1.0f).withValue(0.6f).withVisibility(o -> rainbow.getValue()).build());
    private Setting<Float> brightness = this.register(Settings.floatBuilder("Brightness").withRange(0.0f, 1.0f).withValue(0.6f).withVisibility(o -> rainbow.getValue()).build());
    private Setting<Integer> speed = this.register(Settings.integerBuilder("Speed").withRange(0, 10).withValue(2).withVisibility(o -> rainbow.getValue()).build());
    private Setting<Integer> alpha = this.register(Settings.integerBuilder("Transparency").withRange(0, 255).withValue(70).build());
    private Setting<RenderMode> renderMode = this.register(Settings.e("Render Mode", RenderMode.SOLID));
    
    private BlockPos renderBlock;
    private float hue;
    private Color rgbc;

    @Override
    public void onWorldRender(RenderEvent event) {
        if (this.renderBlock != null && !(mc.world.getBlockState(this.renderBlock).getBlock() instanceof BlockAir) && !(mc.world.getBlockState(this.renderBlock).getBlock() instanceof BlockLiquid) ) {
            if (rainbow.getValue()) {
                this.rgbc = Color.getHSBColor(this.hue, this.satuation.getValue(), this.brightness.getValue());
                this.drawBlock(this.renderBlock, rgbc.getRed(), rgbc.getGreen(), rgbc.getBlue());
                if (this.hue + ( (float) speed.getValue()/200) > 1) {
                    this.hue = 0;   
                } else {
                    this.hue += ( (float) speed.getValue()/200);
                }
            } else {
                this.drawBlock(this.renderBlock, this.red.getValue(), this.green.getValue(), this.blue.getValue());
            }
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
            IBlockState iBlockState2 = mc.world.getBlockState(this.renderBlock);
            Vec3d interp2 = interpolateEntity((Entity)mc.player, mc.getRenderPartialTicks());
            wurstplusTessellator.drawBoundingBox(iBlockState2.getSelectedBoundingBox((World)mc.world, this.renderBlock).offset(-interp2.x, -interp2.y, -interp2.z), width.getValue(), r, g, b, this.alpha.getValue());
        } else {
            IBlockState iBlockState3 = mc.world.getBlockState(this.renderBlock);
            Vec3d interp3 = interpolateEntity((Entity)mc.player, mc.getRenderPartialTicks());
            wurstplusTessellator.drawFullBox(iBlockState3.getSelectedBoundingBox((World)mc.world, this.renderBlock).offset(-interp3.x, -interp3.y, -interp3.z), this.renderBlock, width.getValue(), r, g, b, this.alpha.getValue());
        }
        wurstplusTessellator.release();
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || this.isDisabled()) {
            return;
        }
        try {
            this.renderBlock = new BlockPos((double)mc.objectMouseOver.getBlockPos().getX(), (double)mc.objectMouseOver.getBlockPos().getY(), (double)mc.objectMouseOver.getBlockPos().getZ());
        } catch (Exception e) {
            return;
        }
    }

    private static enum RenderMode {
        SOLID,
        OUTLINE,
        UP,
        FULL;   
    }

    public static Vec3d interpolateEntity(Entity entity, float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)time);
    }

    @Override
    public void onEnable() {
        this.hue = 0f;
    }

    @Override
    public void onDisable() {
        this.renderBlock = null;
    }
}