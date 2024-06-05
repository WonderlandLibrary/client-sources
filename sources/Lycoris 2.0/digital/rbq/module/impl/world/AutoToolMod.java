/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package digital.rbq.module.impl.world;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;
import digital.rbq.annotations.Label;
import digital.rbq.core.Autumn;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.combat.AuraMod;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.utils.InventoryUtils;

@Label(value="Auto Tool")
@Category(value=ModuleCategory.WORLD)
@Aliases(value={"autotool", "autosword"})
public final class AutoToolMod
extends Module {
    private final BoolOption swords = new BoolOption("Swords", true);
    private final BoolOption tools = new BoolOption("Tools", true);
    private AuraMod aura;

    public AutoToolMod() {
        this.addOptions(this.swords, this.tools);
    }

    @Override
    public void onEnabled() {
        if (this.aura == null) {
            this.aura = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(AuraMod.class);
        }
    }

    @Listener(value=MotionUpdateEvent.class)
    public void onEvent(MotionUpdateEvent event) {
        if (event.isPre()) {
            BlockPos blockPos;
            if (this.tools.getValue().booleanValue() && AutoToolMod.mc.currentScreen == null && Mouse.isButtonDown((int)0) && AutoToolMod.mc.objectMouseOver != null && (blockPos = AutoToolMod.mc.objectMouseOver.getBlockPos()) != null) {
                Block block = AutoToolMod.mc.theWorld.getBlockState(blockPos).getBlock();
                float strength = 1.0f;
                int bestToolSlot = -1;
                for (int i = 0; i < 9; ++i) {
                    ItemStack itemStack = AutoToolMod.mc.thePlayer.inventory.getStackInSlot(i);
                    if (itemStack == null || !(itemStack.getStrVsBlock(block) > strength)) continue;
                    strength = itemStack.getStrVsBlock(block);
                    bestToolSlot = i;
                }
                if (bestToolSlot != -1) {
                    AutoToolMod.mc.thePlayer.inventory.currentItem = bestToolSlot;
                }
            }
            if (this.aura.getTarget() != null && this.aura.isEnabled() && this.swords.getValue().booleanValue()) {
                float damage = 1.0f;
                int bestSwordSlot = -1;
                for (int i = 0; i < 9; ++i) {
                    float damageLevel;
                    ItemStack itemStack = AutoToolMod.mc.thePlayer.inventory.getStackInSlot(i);
                    if (itemStack == null || !(itemStack.getItem() instanceof ItemSword) || !((damageLevel = InventoryUtils.getDamageLevel(itemStack)) > damage)) continue;
                    damage = damageLevel;
                    bestSwordSlot = i;
                }
                if (bestSwordSlot != -1) {
                    AutoToolMod.mc.thePlayer.inventory.currentItem = bestSwordSlot;
                }
            }
        }
    }
}

