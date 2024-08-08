// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.player;

import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import me.perry.mcdonalds.util.InventoryUtil;
import net.minecraft.item.ItemEnderPearl;
import org.lwjgl.input.Mouse;
import me.perry.mcdonalds.features.Feature;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class MCP extends Module
{
    private Setting<Mode> mode;
    private Setting<Boolean> stopRotation;
    private Setting<Integer> rotation;
    private boolean clicked;
    
    public MCP() {
        super("MCP", "Throws a pearl", Category.PLAYER, false, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.MIDDLECLICK));
        this.stopRotation = (Setting<Boolean>)this.register(new Setting("Rotation", (T)true));
        this.rotation = (Setting<Integer>)this.register(new Setting("Delay", (T)10, (T)0, (T)100, v -> this.stopRotation.getValue()));
        this.clicked = false;
    }
    
    @Override
    public void onEnable() {
        if (!Feature.fullNullCheck() && this.mode.getValue() == Mode.TOGGLE) {
            this.throwPearl();
            this.disable();
        }
    }
    
    @Override
    public void onTick() {
        if (this.mode.getValue() == Mode.MIDDLECLICK) {
            if (Mouse.isButtonDown(2)) {
                if (!this.clicked) {
                    this.throwPearl();
                }
                this.clicked = true;
            }
            else {
                this.clicked = false;
            }
        }
    }
    
    private void throwPearl() {
        final int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
        final boolean offhand = MCP.mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL;
        if (pearlSlot != -1 || offhand) {
            final int oldslot = MCP.mc.player.inventory.currentItem;
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(pearlSlot, false);
            }
            MCP.mc.playerController.processRightClick((EntityPlayer)MCP.mc.player, (World)MCP.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
        }
    }
    
    public enum Mode
    {
        TOGGLE, 
        MIDDLECLICK;
    }
}
