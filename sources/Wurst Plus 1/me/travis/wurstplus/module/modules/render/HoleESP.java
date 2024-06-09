package me.travis.wurstplus.module.modules.render;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.entity.Entity;
import me.travis.wurstplus.event.events.RenderEvent;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.modules.combat.CrystalAura;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.BlockInteractionHelper;
import me.travis.wurstplus.util.wurstplusTessellator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

// made, with love and care, by travis

@Module.Info(name="HoleESP", category=Module.Category.RENDER, description="Show safe holes")
public class HoleESP extends Module {
    private final BlockPos[] surroundOffset = new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
    private Setting<HoleType> holeType = this.register(Settings.e("HoleType", HoleType.BOTH));
    private Setting<Boolean> hideOwn = this.register(Settings.b("HideOwn", false));
    private Setting<Double> renderDistance = this.register(Settings.doubleBuilder("RenderDistance").withMinimum(1.0).withValue(8.0).withMaximum(32.0).build());
    private Setting<RenderMode> renderMode = this.register(Settings.e("RenderMode", RenderMode.DOWN));
    private Setting<Float> width = this.register(Settings.floatBuilder("Line Width").withMinimum(1.0f).withValue(3.0f).withMaximum(10.0f).withVisibility(o -> this.renderMode.getValue().equals((Object)RenderMode.OUTLINE)).build());
    private Setting<Integer> renderAlpha = this.register(Settings.integerBuilder("RenderAlpha").withMinimum(0).withValue(42).withMaximum(255).build());
    private ConcurrentHashMap<BlockPos, Boolean> safeHoles;

    @Override
    public void onUpdate() {
        if (this.safeHoles == null) {
            this.safeHoles = new ConcurrentHashMap();
        } else {
            this.safeHoles.clear();
        }
        int range = (int)Math.ceil(this.renderDistance.getValue());
        List<BlockPos> blockPosList = BlockInteractionHelper.getSphere(CrystalAura.getPlayerPos(), range, range, false, true, 0);
        for (BlockPos pos : blockPosList) {
            if (!HoleESP.mc.world.getBlockState(pos).getBlock().equals((Object)Blocks.AIR) || !HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals((Object)Blocks.AIR) || !HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals((Object)Blocks.AIR) || this.hideOwn.getValue().booleanValue() && pos.equals((Object)new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ))) continue;
            boolean isSafe = true;
            boolean isBedrock = true;
            for (BlockPos offset : this.surroundOffset) {
                Block block = HoleESP.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                if (block != Blocks.BEDROCK) {
                    isBedrock = false;
                }
                if (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN || block == Blocks.ENDER_CHEST || block == Blocks.ANVIL) continue;
                isSafe = false;
                break;
            }
            if (!isSafe) continue;
            this.safeHoles.put(pos, isBedrock);
        }
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (HoleESP.mc.player == null || this.safeHoles == null) {
            return;
        }
        if (this.safeHoles.isEmpty()) {
            return;
        }
        wurstplusTessellator.prepare(7);
        this.safeHoles.forEach((blockPos, isBedrock) -> {
            if (isBedrock.booleanValue()) {
                if (this.holeType.getValue().equals((Object)HoleType.BOTH) || this.holeType.getValue().equals((Object)HoleType.BROCK)) {
                    this.drawBlock((BlockPos)blockPos, 208, 118, 15);
                }
            } else if (this.holeType.getValue().equals((Object)HoleType.BOTH) || this.holeType.getValue().equals((Object)HoleType.OBI)) {
                this.drawBlock((BlockPos)blockPos, 103, 156, 255);
            }
        });
        wurstplusTessellator.release();
    }

    private void drawBlock(BlockPos blockPos, int r, int g, int b) {
        Color color = new Color(r, g, b, this.renderAlpha.getValue());
        if (this.renderMode.getValue().equals((Object)RenderMode.BLOCK)) {
            wurstplusTessellator.drawBox(blockPos, color.getRGB(), 63);
        } else if (this.renderMode.getValue().equals((Object)RenderMode.OUTLINE)) {
            IBlockState iBlockState2 = CrystalAura.mc.world.getBlockState(blockPos);
            Vec3d interp2 = interpolateEntity((Entity)CrystalAura.mc.player, mc.getRenderPartialTicks());
            wurstplusTessellator.drawBoundingBoxBottom(iBlockState2.getSelectedBoundingBox((World)CrystalAura.mc.world, blockPos).offset(-interp2.x, -interp2.y, -interp2.z), this.width.getValue(), r, g, b, this.renderAlpha.getValue());
        } else {
            wurstplusTessellator.drawBox(blockPos, color.getRGB(), 1);
        }
    }

    public static Vec3d interpolateEntity(Entity entity, float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)time);
    }

    private static enum HoleType {
        BROCK,
        OBI,
        BOTH;      
    }

    public static enum RenderMode {
        DOWN,
        OUTLINE,
        BLOCK; 
    }

}

