/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.RayTraceResult;
import ru.govno.client.module.Module;

public class PushAttack
extends Module {
    public static Module get;

    public PushAttack() {
        super("PushAttack", 0, Module.Category.COMBAT);
        get = this;
    }

    @Override
    public void onUpdate() {
        if (PushAttack.mc.gameSettings.keyBindAttack.isKeyDown()) {
            if (Minecraft.player.isHandActive() && (PushAttack.mc.objectMouseOver == null || PushAttack.mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK)) {
                mc.clickMouse();
                PushAttack.mc.gameSettings.keyBindAttack.pressed = false;
            }
        }
    }
}

