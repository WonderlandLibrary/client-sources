/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import me.imfr0zen.guiapi.components.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import pw.vertexcode.util.module.types.ToggleableModule;

public class ClickListener
implements ActionListener {
    private ToggleableModule module;
    private Button button;

    public ClickListener(ToggleableModule module, Button button) {
        this.module = module;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent actionevent) {
        if (this.module != null) {
            this.module.toggleModule();
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 2.0f, 2.0f);
        }
    }
}

