package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.utils.*;
import wwe.modules.exploits.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import wwe.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { GuiContainer.class }, priority = 9999)
public class MixinGuiContainer
{
    @Shadow
    private Slot field_147006_u;
    
    public MixinGuiContainer() {
        super();
    }
    
    @Inject(method = { "renderHoveredToolTip" }, at = { @At("HEAD") }, cancellable = true)
    public void renderHoveredToolTip(final int n, final int n2, final CallbackInfo callbackInfo) {
        if (Wrapper.mc.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
            if (vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).IuiN() && vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).shulker.OnGi() && this.hoveredSlot.getStack().getItem() instanceof ItemShulkerBox) {
                FIwW.SitR(this.hoveredSlot.getStack(), (World)Wrapper.mc.world, Mouse.getX() * new ScaledResolution(Wrapper.mc).getScaledWidth() / Wrapper.mc.displayWidth, new ScaledResolution(Wrapper.mc).getScaledHeight() - Mouse.getY() * new ScaledResolution(Wrapper.mc).getScaledHeight() / Wrapper.mc.displayHeight - 90, true);
                callbackInfo.cancel();
            }
            else if (vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).IuiN() && vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).map.OnGi() && this.hoveredSlot.getStack().getItem() instanceof ItemMap) {
                try {
                    FIwW.rRds(((ItemMap)this.hoveredSlot.getStack().getItem()).getMapData(this.hoveredSlot.getStack(), (World)Wrapper.mc.world), Mouse.getX() * new ScaledResolution(Wrapper.mc).getScaledWidth() / Wrapper.mc.displayWidth, new ScaledResolution(Wrapper.mc).getScaledHeight() - Mouse.getY() * new ScaledResolution(Wrapper.mc).getScaledHeight() / Wrapper.mc.displayHeight - 90, true);
                    callbackInfo.cancel();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
