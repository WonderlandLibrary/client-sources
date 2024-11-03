package net.augustus.modules.combat;

import net.augustus.Augustus;
import net.augustus.events.EventJump;
import net.augustus.events.EventMove;
import net.augustus.events.EventSilentMove;
import net.augustus.events.EventUpdate;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.utils.RotationUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class AutoProjectile extends Module {
	public AutoProjectile(){
        super("AutoProjectile", Color.GREEN, Categorys.COMBAT);	
    }
    private final BooleanValue AutoThrowthing = new BooleanValue(112199,"AutoThrow",this,false);
    private final BooleanValue AutoRod = new BooleanValue(112200,"AutoRod",this,false);
    private final RotationUtil rotationUtil = new RotationUtil();

    private BlockPos water;
    private int tick = 0;
    private int ticks =0;
    private int d = 0;
    private int dd = 0;
    private int delay = 0;
    private final TimeHelper timeHelper = new TimeHelper();
    private boolean rod = true;
    private float[] rots = new float[2];
    private float[] lastRots = new float[2];
    @EventTarget
    public void onUpdate(EventUpdate event){
        this.AutoThrowthing.setVisible(true);
        this.AutoRod.setVisible(true);
        
        //throwable-things
        if (mm.killAura.isToggled() && mc.thePlayer.getDistanceToEntity(KillAura.target) > mm.killAura.rangeSetting.getValue() && AutoThrowthing.getBoolean() && this.throwablethings() != -1){
            mc.thePlayer.inventory.currentItem = throwablethings();

            d ++;

            if ((mc.thePlayer.inventoryContainer.getSlot(throwablethings() + 36).getStack().getItem().equals(Item.getItemById(344))) || (mc.thePlayer.inventoryContainer.getSlot(throwablethings() + 36).getStack().getItem().equals(Item.getItemById(332)))){
                if (d == 10 || KillAura.target.hurtTime == 9) {
                    rod= false;
                    mc.rightClickMouse();
                    d= 0;
                }
            }else {
                rod = true;
            }
        }
        if (mm.killAura.targets == null){
            d = 0;
        }
        //rod
        if (mm.killAura.isToggled() && mc.thePlayer.getDistanceToEntity(KillAura.target) > mm.killAura.rangeSetting.getValue() && AutoRod.getBoolean() && this.rodfindmoment() != -1 && rod) {
            mc.thePlayer.inventory.currentItem = rodfindmoment();
            if (mc.thePlayer.inventoryContainer.getSlot(rodfindmoment() + 36).getStack().getItem().equals(Item.getItemById(346))) {
                dd++;
                if (dd == 0) {
                    mc.rightClickMouse();
                }
                if (dd == delay){
                    dd = -1;
                }
                if(mc.thePlayer.getDistanceToEntity(KillAura.target) <= 8) {
                    delay = 15;
                }else if(mc.thePlayer.getDistanceToEntity(KillAura.target) <= 8) {
                    delay = 12;
                }else if(mc.thePlayer.getDistanceToEntity(KillAura.target) <= 7) {
                    delay = 10;
                }else if(mc.thePlayer.getDistanceToEntity(KillAura.target) <= 6) {
                    delay = 8;
                }else if(mc.thePlayer.getDistanceToEntity(KillAura.target) <= 5) {
                    delay = 5;
                }
            }
        }
    }
    @EventTarget
    public void onEventMove(EventMove eventMove) {
        if (this.water != null) {
            eventMove.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
        }
    }

    @EventTarget
    public void onEventJump(EventJump eventJump) {
        if (this.water != null) {
            eventJump.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
        }
    }

    @EventTarget
    public void onEventSilentMove(EventSilentMove eventSilentMove) {
        if (this.water != null) {
            eventSilentMove.setSilent(true);
        }
    }
    private void placeWater(BlockPos pos){
        ItemStack heldItem = mc.thePlayer.inventory.getCurrentItem();
        if (heldItem != null) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, heldItem);
            mc.entityRenderer.itemRenderer.resetEquippedProgress2();
        }
        mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), pos, EnumFacing.UP, new Vec3((double)pos.getX() + 0.5, (double)pos.getY() +1, (double)pos.getZ() + 0.5));

    }
    private int getSlotWaterBucket() {
        for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i){
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if(itemStack != null && itemStack.getItem() == Item.getItemById(326)){
                return i - 36;
            }
        }
        return -1;
    }
    private int getSlotBucket(){
        for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i){
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if(itemStack != null && itemStack.getItem() == Item.getItemById(325)){
                return i - 36;
            }
        }
        return -1;
    }
    private int rodfindmoment(){
        for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i){
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if(itemStack != null && itemStack.getItem() == Item.getItemById(346)){
                return i - 36;
            }
        }
        return -1;
    }

    private int throwablethings(){
        for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i){
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if(itemStack != null && (itemStack.getItem() == Item.getItemById(332) || itemStack.getItem() == Item.getItemById(344))){
                return i - 36;
            }
        }
        return -1;
    }

    private void setRotation() {
        if (mc.currentScreen == null && (!mm.killAura.isToggled() || KillAura.target == null)) {
            mc.thePlayer.rotationYaw = this.rots[0];
            mc.thePlayer.rotationPitch = this.rots[1];
            mc.thePlayer.prevRotationYaw = this.lastRots[0];
            mc.thePlayer.prevRotationPitch = this.lastRots[1];
        }
    }
    private void setPitch() {
        if (mc.currentScreen == null && (!mm.killAura.isToggled() || KillAura.target == null)) {
            mc.thePlayer.rotationPitch = this.rots[1];
            mc.thePlayer.prevRotationPitch = this.lastRots[1];
        }
    }
}
