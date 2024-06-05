package club.mega.module.impl.player;

import club.mega.Mega;
import club.mega.event.impl.*;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.*;
import club.mega.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "Scaffold", description = "Places blocks below you", category = Category.PLAYER)
public class Scaffold extends Module {

    private Vec3 pos;
    private BlockPos blockPos;
    private ScaffoldUtil.BlockData blockData;
    private float[] prevRotations = new float[1];
    private float[] rotations = new float[1];
    private int slot;
    private double y;
    private final TimeUtil timer = new TimeUtil();

    public final ListSetting placeTiming = new ListSetting("PlaceTiming", this, new String[] {"Tick", "Pre", "Post"});
    public final NumberSetting placeDelay = new NumberSetting("Place Delay", this, 0, 1000, 0, 1);
    public final ListSetting rotationMode = new ListSetting("Rotations", this, new String[]{"Normal", "Legit"});
    public final BooleanSetting legitPlace = new BooleanSetting("Legit place", this, false);
    public final BooleanSetting sprint = new BooleanSetting("Sprint", this, true);
    public final BooleanSetting silent = new BooleanSetting("Silent", this, true);
    public final BooleanSetting keepY = new BooleanSetting("KeepY", this, false);
    public final BooleanSetting showCount = new BooleanSetting("RenderBlockCount", this, true, false);

    @Handler
    public final void eventRender2D(final EventRender2D event) {
        if (!showCount.get()) return;
        Mega.INSTANCE.getFontManager().getFont("Arial 20").drawString("Blocks:", event.getScaledResolution().getScaledWidth() / 2D - Mega.INSTANCE.getFontManager().getFont("Arial 20").getWidth("Blocks:") + 40, event.getScaledResolution().getScaledHeight() / 2D - 5, -1);
        Mega.INSTANCE.getFontManager().getFont("Roboto bold 20").drawString(String.valueOf(ScaffoldUtil.getBlockCount()), event.getScaledResolution().getScaledWidth() / 2D + 40, event.getScaledResolution().getScaledHeight() / 2D - 5, ColorUtil.getMainColor());

    }

    @Handler
    public final void tick(final EventTick event) {
        if (blockData == null || ScaffoldUtil.getBlockSlot() == -1) {
            prevRotations = rotations;
            rotations = new float[] {MC.thePlayer.rotationYaw, MC.thePlayer.rotationPitch};
            return;
        }

        MC.gameSettings.keyBindSprint.pressed = sprint.get();
        MC.thePlayer.setSprinting(sprint.get());

        final double playerYaw = Math.toDegrees(MC.thePlayer.rotationYaw);
        final double random = RandomUtil.getRandomNumber(0.02, 0.03);
        final BlockPos bp = new BlockPos(MC.thePlayer.posX + random * -Math.cos(playerYaw), MC.thePlayer.posY - 0.9D, MC.thePlayer.posZ + random * Math.sin(playerYaw));
        MC.gameSettings.keyBindSneak.pressed = WorldUtil.getBlock(bp).getMaterial() == Material.air;

        prevRotations = rotations;
        if (!rotationMode.is("legit"))
            rotations = RotationUtil.getScaffoldRotations(blockData, false);
        else if (WorldUtil.getBlock(bp).getMaterial() == Material.air)
            rotations = RotationUtil.getScaffoldRotations(blockData, true);


        if (placeTiming.is("tick"))
            doPlacement();
    }

    @Handler
    public final void preTick(final EventPreTick event) {
        if (MC.thePlayer.posY < y || (!MC.thePlayer.onGround && !MovementUtil.isMoving()) || MC.thePlayer.posY - y > 6 || !keepY.get()) y = MC.thePlayer.posY - 0.9D;

        pos = new Vec3(MC.thePlayer.posX, y , MC.thePlayer.posZ);
        blockPos = new BlockPos(pos.xCoord, pos.yCoord, pos.zCoord);

        blockData = ScaffoldUtil.getBlockData(blockPos);

        if (blockData == null)
            blockData = ScaffoldUtil.getBlockData(blockPos.offsetDown());

        if (blockData == null || ScaffoldUtil.getBlockSlot() == -1)
            return;

        MC.thePlayer.rotationYawHead = rotations[0];
        MC.thePlayer.renderYawOffset = rotations[0];
        event.setYaw(rotations[0]);
        event.setPitch(rotations[1]);

        if (placeTiming.is("pre"))
            doPlacement();
    }

    @Handler
    public final void postTick(final EventPostTick event) {
        if (blockData == null || ScaffoldUtil.getBlockSlot() == -1)
            return;

        if (placeTiming.is("post"))
            doPlacement();
    }

    @Handler
    public final void renderPitch(final EventRenderPitch event) {
        if (blockData == null || ScaffoldUtil.getBlockSlot() == -1 || rotations == null)
            return;

        event.setPitch(rotations[1]);
    }

    private void doPlacement() {
        slot = MC.thePlayer.inventory.currentItem;
        MC.thePlayer.inventory.currentItem = ScaffoldUtil.getBlockSlot();
        if (!legitPlace.get()) {
            if (WorldUtil.getBlock(this.blockPos).getMaterial() == Material.air && timer.hasTimePassed(placeDelay.getAsInt())) {
                MC.getNetHandler().addToSendQueue(new C0APacketAnimation());
                MC.playerController.onPlayerRightClick(MC.thePlayer, MC.theWorld, MC.thePlayer.getHeldItem(), blockData.position, blockData.face, PlayerUtil.getVectorForRotation(rotations[0], rotations[1]));
                timer.reset();
            }
        } else {
            final BlockPos blockpos = MC.objectMouseOver.getBlockPos();
            if (blockpos.equals(blockData.position) && MC.objectMouseOver.sideHit.equals(blockData.face)) {
                if (WorldUtil.getBlock(blockpos).getMaterial() != Material.air) {
                    if (MC.playerController.onPlayerRightClick(MC.thePlayer, MC.theWorld, MC.thePlayer.inventoryContainer.getSlot(36 + slot).getStack(), blockpos, MC.objectMouseOver.sideHit, MC.objectMouseOver.hitVec))
                        MC.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }
            }
        }
        if (silent.get()) MC.thePlayer.inventory.currentItem = slot;
    }

    @Handler
    public final void look(final EventLook event) {
        if (blockData == null || ScaffoldUtil.getBlockSlot() == -1)
            return;

        event.setRotations(rotations);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        prevRotations = rotations;

        if (MC.thePlayer == null)
            return;

        rotations = new float[]{MC.thePlayer.rotationYaw, MC.thePlayer.rotationPitch};
        y = MC.thePlayer.posY - 0.9D;
        slot = MC.thePlayer.inventory.currentItem;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MC.thePlayer.inventory.currentItem = slot;
        if (legitPlace.get())
        MC.gameSettings.keyBindSneak.pressed = false;
    }

    public final float[] getRotations() {
        return rotations;
    }

    public final float[] getPrevRotations() {
        return prevRotations;
    }

    public static Scaffold getInstance() {
        return Mega.INSTANCE.getModuleManager().getModule(Scaffold.class);
    }
}
