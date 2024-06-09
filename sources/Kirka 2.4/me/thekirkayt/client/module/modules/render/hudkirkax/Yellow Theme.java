/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render.hudkirkax;

import java.text.SimpleDateFormat;
import java.util.Date;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.client.module.modules.render.hudkirkax.Theme;
import me.thekirkayt.event.events.Render2DEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Yellow Theme
extends Theme {
    public Yellow Theme(String string, boolean bl, Module module) {
        super(string, bl, module);
    }

    @Override
    public boolean onRender(Render2DEvent render2DEvent) {
        if (super.onRender(render2DEvent)) {
            String string = new SimpleDateFormat("\u00a77hh:mm a").format(new Date());
            if (string.startsWith("0")) {
                string = string.replaceFirst("0", "");
            }
            ClientUtils.mc().getRenderItem().func_180450_b(new ItemStack(Item.getByNameOrId("diamond_pickaxe")), 52, -2);
            ClientUtils.clientFont().drawStringWithShadow(String.format("\u00a7e\u00a7lK\u00a77\u00a7lirka \u00a7e\u00a7lb\u00a77\u00a7l11", string), 2.0, 2.0, 16777045);
            ClientUtils.clientFont().drawStringWithShadow(String.format("", Math.round(ClientUtils.x()), Math.round(ClientUtils.y()), Math.round(ClientUtils.z())), 2.0, 14.0, 16777045);
            int n = 2;
            for (Module module : ModuleManager.getModulesForRender()) {
                if (!module.drawDisplayName(render2DEvent.getWidth() - ClientUtils.clientFont().getStringWidth(String.format("%s" + (module.getSuffix().length() > 0 ? " [%s]" : ""), module.getDisplayName(), module.getSuffix())) - 2, n, 16777045)) continue;
                n += 10;
            }
        }
        return super.onRender(render2DEvent);
    }
}

