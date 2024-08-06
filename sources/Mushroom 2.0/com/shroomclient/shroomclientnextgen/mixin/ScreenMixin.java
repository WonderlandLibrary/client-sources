package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.RenderScreenEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.InventoryMove;
import com.shroomclient.shroomclientnextgen.modules.impl.player.ChestStealer;
import com.shroomclient.shroomclientnextgen.util.C;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Screen.class, priority = 1)
public abstract class ScreenMixin {

    @Shadow
    @Final
    private List<Drawable> drawables;

    @Shadow
    public abstract void renderBackground(
        DrawContext context,
        int mouseX,
        int mouseY,
        float delta
    );

    /**
     * @author scoliosis
     * @reason gui event + seethrough guis
     */
    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(
        DrawContext context,
        int mouseX,
        int mouseY,
        float delta,
        CallbackInfo ci
    ) {
        ci.cancel();

        if (ModuleManager.isEnabled(InventoryMove.class)) {
            if (
                C.mc.currentScreen instanceof GenericContainerScreen s &&
                ((s.getTitle().withoutStyle().get(0).getLiteralString() !=
                            null &&
                        s
                            .getTitle()
                            .withoutStyle()
                            .get(0)
                            .getLiteralString()
                            .toLowerCase()
                            .contains("chest")) ||
                    !ChestStealer.nameCheck)
            ) if (InventoryMove.hideGUI) {
                context.getMatrices().translate(10000, 10000, 10000);
            }

            if (C.mc.currentScreen instanceof InventoryScreen) if (
                InventoryMove.hideInventory
            ) {
                context.getMatrices().translate(10000, 10000, 10000);
            }
        }

        if (C.isInGame()) Bus.post(
            new RenderScreenEvent(context, mouseX, mouseY, delta)
        );

        this.renderBackground(context, mouseX, mouseY, delta);

        Iterator var5 = this.drawables.iterator();

        while (var5.hasNext()) {
            Drawable drawable = (Drawable) var5.next();
            drawable.render(context, mouseX, mouseY, delta);
        }
    }
}
