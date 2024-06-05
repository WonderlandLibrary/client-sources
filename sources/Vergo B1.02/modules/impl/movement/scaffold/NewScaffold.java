package xyz.vergoclient.modules.impl.movement.scaffold;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.*;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.modules.impl.combat.KillAura;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.settings.NumberSetting;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.util.Gl.BlurUtil;
import xyz.vergoclient.util.animations.Animation;
import xyz.vergoclient.util.animations.Direction;
import xyz.vergoclient.util.animations.impl.EaseBackIn;
import xyz.vergoclient.util.main.ChatUtils;
import xyz.vergoclient.util.main.MovementUtils;
import xyz.vergoclient.util.main.RenderUtils;
import xyz.vergoclient.util.main.RotationUtils;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Arrays;

import static xyz.vergoclient.modules.impl.visual.TargetHud.getColor;

public class NewScaffold extends Module implements OnEventInterface {

    public NewScaffold() {
        super("Scaffold", Category.MOVEMENT);
    }

    // Variables
    private float rotations[];
    private ScaffoldUtils.BlockCache blockCache, lastBlockCache;

    public static transient int lastSlot = -1;

    public static int slot;

    public static Animation openingAnimation;

    @Override
    public void onEnable() {

        mc.timer.timerSpeed = 1f;

        openingAnimation = new EaseBackIn(400, .4f, 2f);

        if(openingAnimation.getDirection() == Direction.BACKWARDS)
            openingAnimation.setDirection(Direction.FORWARDS);
    }

    @Override
    public void onDisable() {

        if (lastSlot != mc.thePlayer.inventory.currentItem) {
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        }
        lastSlot = -1;

        openingAnimation.setDirection(Direction.BACKWARDS);

    }

    @Override
    public void onEvent(Event e) {

        if (e instanceof EventRenderGUI) {
            ScaledResolution sr = new ScaledResolution(mc);
            RenderUtils.scale(sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f, (float) openingAnimation.getOutput() + .6f, () -> {

                GlStateManager.pushMatrix();
                int blocksLeft = 0;

                for (short g = 0; g < 9; g++) {

                    if (mc.thePlayer.inventoryContainer.getSlot(g + 36).getHasStack()
                            && mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().getItem() instanceof ItemBlock
                            && mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().stackSize != 0
                            && !((ItemBlock) mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().getItem()).getBlock()
                            .getLocalizedName().toLowerCase().contains("chest")
                            && !((ItemBlock) mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().getItem()).getBlock()
                            .getLocalizedName().toLowerCase().contains("table")) {
                        blocksLeft += mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().stackSize;
                    }

                }

                DecimalFormat decimalFormat = new DecimalFormat("###");
                String left = decimalFormat.format(blocksLeft);

                JelloFontRenderer jfr = FontUtil.comfortaaSmall;


                if (blocksLeft > 0) {

                    BlurUtil.blurAreaRounded(GuiScreen.width / 2 - 12f, GuiScreen.height / 2 + 18, 25, 30, 3f);
                    RenderUtils.drawAlphaRoundedRect(GuiScreen.width / 2 - 12f, GuiScreen.height / 2 + 18, 25, 30, 3f, getColor(10, 10, 10, (int) 100));
                    if (blocksLeft <= 99 && blocksLeft > 9) {
                        jfr.drawString("0" + left, GuiScreen.width / 2 - 5, GuiScreen.height / 2 + 40, getColor(255, 255, 255, (int) 255));
                    } else if (blocksLeft <= 9) {
                        jfr.drawString("00" + left, GuiScreen.width / 2 - 5, GuiScreen.height / 2 + 40, getColor(255, 255, 255, (int) 255));
                    } else {
                        jfr.drawString(left, GuiScreen.width / 2 - 5, GuiScreen.height / 2 + 40, getColor(255, 255, 255, (int) 255));
                    }
                    GlStateManager.resetColor();
                    RenderHelper.enableGUIStandardItemLighting();
                    GlStateManager.color(1, 1, 1, 255);
                    mc.getRenderItem().renderItemAndEffectIntoGUI(setStackToPlace(), GuiScreen.width / 2 - 7.5f, GuiScreen.height / 2 + 20);

                } else {
                    BlurUtil.blurAreaRounded(GuiScreen.width / 2 - 12f, GuiScreen.height / 2 + 18, 25, 30, 3f);
                    RenderUtils.drawAlphaRoundedRect(GuiScreen.width / 2 - 12f, GuiScreen.height / 2 + 18, 25, 30, 3f, new Color(10, 10, 10, 100));
                    GlStateManager.resetColor();
                    RenderHelper.enableGUIStandardItemLighting();
                    GlStateManager.color(1, 1, 1, 255);
                    jfr.drawString("000", GuiScreen.width / 2 - 5, GuiScreen.height / 2 + 40, getColor(191, 9, 29, (int) 255));
                }

                GlStateManager.popMatrix();
            });
        }

        if (e instanceof EventReceivePacket && e.isPre()) {

            if (((EventReceivePacket) e).packet instanceof S2FPacketSetSlot) {
                lastSlot = ((S2FPacketSetSlot) ((EventReceivePacket) e).packet).slot;
            }

        }

        if (e instanceof EventSendPacket & e.isPre()) {
            if (((EventSendPacket) e).packet instanceof C09PacketHeldItemChange) {
                lastSlot = ((C09PacketHeldItemChange) ((EventSendPacket) e).packet).getSlotId();
            }

        }

        if (e instanceof EventUpdate) {
            if (mc.thePlayer.isSprinting()) {
                mc.thePlayer.setSprinting(false);
            }

        }

        if(e instanceof EventUpdate) {
            EventUpdate event = (EventUpdate) e;
            rotationInformation(event);
        }

        if(e instanceof EventMove && e.isPost()) {

                // Setting Block Cache
                blockCache = ScaffoldUtils.grab();
                if (blockCache != null) {
                    lastBlockCache = ScaffoldUtils.grab();
                } else {
                    return;
                }

                int slot = ScaffoldUtils.grabBlockSlot();
                if (slot == -1) return;

                // Setting Slot
                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));


                // Placing Blocks, Without jumping!
                if (blockCache == null) return;

                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(slot), lastBlockCache.position, lastBlockCache.facing, ScaffoldUtils.getHypixelVec3(lastBlockCache));

                mc.thePlayer.swingItem();

                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());

                blockCache = null;

                // Slowdown if speed pot is active.
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed.id)) {
                    mc.thePlayer.motionX *= 0.66;
                    mc.thePlayer.motionZ *= 0.66;
                }
            }

    }

    private ItemStack setStackToPlace() {

        ItemStack block = mc.thePlayer.getCurrentEquippedItem();

        if (block != null && block.getItem() != null && !(block.getItem() instanceof ItemBlock)) {
            block = null;
        }

        int slot = mc.thePlayer.inventory.currentItem;

        for (short g = 0; g < 9; g++) {

            if (mc.thePlayer.inventoryContainer.getSlot(g + 36).getHasStack()
                    && mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().getItem() instanceof ItemBlock
                    && mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().stackSize != 0
                    && !((ItemBlock) mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().getItem()).getBlock()
                    .getLocalizedName().toLowerCase().contains("chest")
                    && !((ItemBlock) mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().getItem()).getBlock()
                    .getLocalizedName().toLowerCase().contains("table")
                    && (block == null
                    || (block.getItem() instanceof ItemBlock && mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().stackSize >= block.stackSize))) {

                slot = g;
                block = mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack();

            }

        }
        if (lastSlot + (lastSlot >= 36 ? -36 : 0) != slot) {
            mc.getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(slot));
            lastSlot = slot;
        }
        return block;
    }

    private void rotationInformation(EventUpdate e) {
        if (lastBlockCache != null) {
            rotations = ScaffoldUtils.getFacingRotations2(lastBlockCache.getPosition().getX(), lastBlockCache.getPosition().getY(), lastBlockCache.getPosition().getZ());

            e.setYaw(rotations[0]);
            e.setPitch(81);

            RenderUtils.setCustomYaw(rotations[0]);
            RenderUtils.setCustomPitch(81);
        } else {
            e.setPitch(81);
            e.setYaw(mc.thePlayer.rotationYaw + 180);

            RenderUtils.setCustomPitch(81);
            RenderUtils.setCustomYaw(mc.thePlayer.rotationYaw + 180);
        }
    }

    private float[] doOtherRotations(BlockPos blockPos, EnumFacing enumFacing) {
        if (blockPos == null && enumFacing == null) {
            return null;
        }
        Vec3 positionEyes = mc.thePlayer.getPositionEyes(2.0f);
        Vec3 add = new Vec3((double)blockPos.getX() + 1, (double)blockPos.getY() + 0.1, (double)blockPos.getZ() + 1);
        double n = add.xCoord - positionEyes.xCoord;
        double n2 = add.yCoord - positionEyes.yCoord;
        double n3 = add.zCoord - positionEyes.zCoord;
        return new float[]{(float)(Math.atan2(n3, n) * 180.0 / Math.PI - 90), -((float)(Math.atan2(n2, (float)Math.hypot(n, n3)) * 180.0 / Math.PI))};
    }

}