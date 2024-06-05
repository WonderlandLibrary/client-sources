// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.combat.KillauraModule;
import org.lwjgl.input.Mouse;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "AutoTool", renderName = "Auto Tool", description = "Automatically uses the best tool.", aliases = { "tool" }, category = ModuleCategory.PLAYER)
public final class AutoToolModule extends Module
{
    private final BooleanProperty toolsProperty;
    private final BooleanProperty swordsProperty;
    private final EventListener<EventUpdate> updateEventListener;
    
    public AutoToolModule() {
        this.toolsProperty = new BooleanProperty("Tools", true);
        this.swordsProperty = new BooleanProperty("Swords", true);
        this.updateEventListener = (event -> {
            if (event.isPre()) {
                if (this.toolsProperty.getValue() && this.mc.currentScreen == null && Mouse.isButtonDown(0) && this.mc.objectMouseOver != null) {
                    final BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
                    if (blockPos != null) {
                        final Block block = this.mc.theWorld.getBlockState(blockPos).getBlock();
                        float strength = 1.0f;
                        int bestToolSlot = -1;
                        for (int i = 0; i < 9; ++i) {
                            final ItemStack itemStack = this.mc.thePlayer.inventory.getStackInSlot(i);
                            if (itemStack != null && itemStack.getStrVsBlock(block) > strength) {
                                strength = itemStack.getStrVsBlock(block);
                                bestToolSlot = i;
                            }
                        }
                        if (bestToolSlot != -1) {
                            this.mc.thePlayer.inventory.currentItem = bestToolSlot;
                        }
                    }
                }
                if (ModuleManager.getInstance(KillauraModule.class).getTarget() != null && this.swordsProperty.getValue()) {
                    double damage = 1.0;
                    int bestSwordSlot = -1;
                    for (int j = 0; j < 9; ++j) {
                        final ItemStack itemStack2 = this.mc.thePlayer.inventory.getStackInSlot(j);
                        if (itemStack2 != null) {
                            final double damageLevel = PlayerUtils.getItemDamage(itemStack2);
                            if (damageLevel > damage) {
                                damage = damageLevel;
                                bestSwordSlot = j;
                            }
                        }
                    }
                    if (bestSwordSlot != -1) {
                        this.mc.thePlayer.inventory.currentItem = bestSwordSlot;
                    }
                }
            }
        });
    }
}
