package me.kaimson.melonclient.mixins.client.gui;

import java.util.*;
import me.kaimson.melonclient.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.utils.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ aya.class })
public class MixinGuiMainMenu extends axu implements awx
{
    @ModifyArg(method = { "initGui" }, at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0))
    private Object moveOptionsButton(final Object buttonIn) {
        final avs avs;
        final avs button = avs = (avs)buttonIn;
        avs.h += 23;
        button.a(154);
        return button;
    }
    
    @Redirect(method = { "initGui" }, at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1))
    private boolean moveQuitButton(final List<avs> list, final Object e) {
        final avs quit = (avs)e;
        return list.add(new GuiButtonIcon(quit.k, quit.h + 78, quit.i, 20, 20, "close.png"));
    }
    
    @ModifyArg(method = { "initGui" }, at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2))
    private Object moveLanguageButton(final Object buttonIn) {
        final avs avs;
        final avs button = avs = (avs)buttonIn;
        avs.h += 24;
        return button;
    }
    
    @Overwrite
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        bfl.E();
        bfl.l();
        bfl.a(770, 771, 1, 0);
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        this.j.P().a(Client.BACKGROUND);
        GuiUtils.a(0, 0, (float)this.l, (float)this.m, this.l, this.m, (float)this.l, (float)this.m);
        this.j.P().a(Client.LOGO);
        GuiUtils.a(this.l / 2 - 118 + 2, 50, 0.0f, 0.0f, 113, 36, 242.0f, 36.0f);
        GuiUtils.setGlColor(Client.getMainColor(255));
        GuiUtils.a(this.l / 2, 50, 112.0f, 0.0f, 124, 36, 237.0f, 36.0f);
        GuiUtils.setGlColor(Client.getMainColor(255));
        bfl.F();
        this.c(this.q, "Melon Client 1.8.9", 2, this.m - 10, -1);
        super.a(mouseX, mouseY, partialTicks);
    }
}
