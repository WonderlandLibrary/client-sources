/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import skizzle.friends.Friend;
import skizzle.friends.FriendManager;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.settings.BooleanSetting;

public class NameProtect
extends Module {
    public BooleanSetting others;
    public Minecraft mc = Minecraft.getMinecraft();
    public CopyOnWriteArrayList<EntityPlayer> list;

    public String replace(String Nigga) {
        NameProtect Nigga2;
        String Nigga3 = Nigga2.mc.thePlayer.getName();
        String Nigga4 = Nigga;
        if (Nigga2.isEnabled()) {
            Nigga4 = Nigga.replace(Nigga3, Qprot0.0("\u8860\u71ca\ub3ee\u1731\u1233\u0d0f"));
        }
        if (ModuleManager.nameProtect.others.isEnabled()) {
            int Nigga5 = 0;
            for (Object Nigga6 : Minecraft.theWorld.playerEntities) {
                EntityPlayer Nigga7 = (EntityPlayer)Nigga6;
                if (ModuleManager.nameProtect.isEnabled()) {
                    Nigga4 = Nigga4.replace(Nigga7.getName(), Qprot0.0("\u8860\u719c\ub3eb\u172e\u1237\u0d04\u8c2a\ue4f0") + Nigga5);
                }
                for (Friend Nigga8 : FriendManager.friends) {
                    Nigga4 = Nigga4.replace(Nigga7.getName(), Qprot0.0("\u8860\u7192") + Nigga8.getNickname());
                }
                ++Nigga5;
            }
        }
        return Nigga4;
    }

    public NameProtect() {
        super(Qprot0.0("\u8889\u71ca\ub3d6\ua7e1\ua244\u0d0f\u8c20\ue4f6\u5707\u093d\u8724"), 0, Module.Category.RENDER);
        NameProtect Nigga;
        Nigga.others = new BooleanSetting(Qprot0.0("\u8888\u71df\ub3d3\ua7e1\ua266\u0d0e"), false);
        Nigga.list = new CopyOnWriteArrayList();
    }

    public static {
        throw throwable;
    }
}

