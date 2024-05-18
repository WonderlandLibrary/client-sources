package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import net.minecraft.client.*;
import me.darkmagician6.morbid.*;
import java.util.*;

public final class AutoPotion extends ModBase
{
    private byte slot;
    
    public AutoPotion() {
        super("AutoPotion", "0", true, ".t potion");
        this.slot = -1;
        this.setDescription("Vedi attraverso i blocchi");
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.slot = -1;
    }
    
    @Override
    public void onRenderHand() {
        this.onHealthUpdate();
        if (Minecraft.x().s == null) {
            for (byte b = 36; b <= 44; ++b) {
                if (Minecraft.x().g.bL.a(b).c() == null) {
                    if (this.getPot()) {
                        break;
                    }
                }
                else if (Minecraft.x().g.bL.a(b).c().b() instanceof uv) {
                    final int curSlot = Minecraft.x().g.bK.c;
                    Minecraft.x().g.bK.c = b - 36;
                    Minecraft.x().b.j();
                    Minecraft.x().g.a(true);
                    Minecraft.x().g.bK.c = curSlot;
                    Minecraft.x().b.j();
                }
            }
        }
    }
    
    private void onHealthUpdate() {
        if (MorbidWrapper.getPlayer().aX() <= 10.0f) {
            for (byte b = 36; b <= 44; ++b) {
                final wm stack = Minecraft.x().g.bL.a(b).c();
                if (stack != null) {
                    final wk item = stack.b();
                    if (item instanceof ww) {
                        final ww pot = (ww)item;
                        if (ww.f(stack.k())) {
                            final Object localObject1 = null;
                            for (final Object o1 : pot.g(stack)) {
                                final ml e1 = (ml)o1;
                                if (e1.a() == mk.h.H) {
                                    this.slot = b;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void onPreUpdate() {
        if (this.slot != -1) {
            Minecraft.x().g.B = 90.0f;
        }
    }
    
    private void onPostUpdate() {
        if (this.slot != -1) {
            final int curSlot = Minecraft.x().g.bK.c;
            Minecraft.x().g.bK.c = this.slot - 36;
            Minecraft.x().b.j();
            Minecraft.x().g.a.c(new fr(-1, -1, -1, 255, Minecraft.x().g.bK.h(), 0.0f, 0.0f, 0.0f));
            Minecraft.x().g.bK.c = curSlot;
            Minecraft.x().b.j();
            this.slot = -1;
        }
    }
    
    private boolean getPot() {
        for (byte b = 9; b <= 35; ++b) {
            final wm stack = Minecraft.x().g.bL.a(b).c();
            if (stack != null) {
                final wk item = stack.b();
                if (item instanceof ww) {
                    clickSlot(b, 0, true);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void clickSlot(final int slot, final int mouseButton, final boolean shiftClick) {
        Minecraft.x().b.a(Minecraft.x().g.bL.d, slot, mouseButton, shiftClick ? 1 : 0, Minecraft.x().g);
    }
}
