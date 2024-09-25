/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import net.minecraft.client.Minecraft;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;

public class NoHurtCam
extends Module {
    public Minecraft mc = Minecraft.getMinecraft();
    public NumberSetting scale = new NumberSetting(Qprot0.0("\uf7cb\u71c6\ucc91\ua7f1\ud937\u7244"), 100.0, 0.0, 100.0, 5.0);

    public static {
        throw throwable;
    }

    public NoHurtCam() {
        super(Qprot0.0("\uf7c4\u71c4\uccb6\ua7f1\ud92b\u7244\u8c0c\u9ba6\u570f"), 0, Module.Category.RENDER);
        NoHurtCam Nigga;
        Nigga.addSettings(Nigga.scale);
    }
}

