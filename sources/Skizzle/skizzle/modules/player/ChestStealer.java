/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package skizzle.modules.player;

import java.util.ArrayDeque;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;
import skizzle.events.Event;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class ChestStealer
extends Module {
    public Timer timer = new Timer();
    public boolean hasAdded = false;
    public ArrayDeque<Integer> queue;
    public Timer closeDelay;
    public ModeSetting close;
    public boolean hasClosed = false;
    public NumberSetting delay = new NumberSetting(Qprot0.0("\ub039\u71ce\u8b9d\ua7e5\u9a2b"), 200.0, 0.0, 1000.0, 10.0);

    @Override
    public void onEvent(Event Nigga) {
        ContainerChest Nigga2;
        ChestStealer Nigga3;
        Nigga3.setSuffix("" + Nigga3.delay.getValue());
        if (Nigga3.mc.thePlayer != null && Nigga3.isChestInventory() && (Nigga2 = (ContainerChest)Nigga3.mc.thePlayer.openContainer) != null) {
            if (Nigga2.getLowerChestInventory().getName().equals(Qprot0.0("\ub03e\u71c3\u8b94\ue27e\u24b1")) && !Nigga3.isChestEmpty(Nigga2)) {
                if (!Nigga3.close.getMode().equals(Qprot0.0("\ub039\u71c2\u8b82\ue26c\u24a7\u35ab\u8c2a\udcac")) && Nigga3.close.getMode().equals(Qprot0.0("\ub02e\u71db\u8b9e\ue262\u24a3")) && !Mouse.isGrabbed()) {
                    Nigga3.mc.inGameHasFocus = true;
                    Nigga3.mc.mouseHelper.grabMouseCursor();
                    try {
                        Nigga3.mc.displayGuiScreen(null);
                        Nigga3.mc.currentScreen = null;
                    }
                    catch (Exception exception) {}
                }
                for (int Nigga4 = 0; Nigga4 < Nigga2.getLowerChestInventory().getSizeInventory(); ++Nigga4) {
                    if (Nigga2.getLowerChestInventory().getStackInSlot(Nigga4) == null || !Nigga3.timer.hasTimeElapsed((long)Nigga3.delay.getValue(), true) || !Nigga3.checkItem(Nigga2.getLowerChestInventory().getStackInSlot(Nigga4))) continue;
                    Nigga3.mc.playerController.windowClick(Nigga2.windowId, Nigga4, 0, 1, Nigga3.mc.thePlayer);
                    Nigga3.timer.reset();
                }
            }
            if (Nigga3.isChestEmpty(Nigga2) && Nigga3.timer.hasTimeElapsed((long)1943136835 ^ 0x73D1EA46L, true)) {
                Nigga3.mc.thePlayer.closeScreen();
            }
        }
    }

    public boolean checkItem(ItemStack Nigga) {
        return !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub00e\u71df\u8b83\ub5a4\uec6b\u35a0")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub00e\u71ce\u8b94\ub5a9\uec76")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub009\u71c5\u8b85")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub00d\u71c2\u8b82\ub5b9\uec6a\u35a9")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub00e\u71c5\u8b9e\ub5ba")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub01b\u71ce\u8b83\ub5a3")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub00e\u71ca\u8b81\ub5a1\uec6c\u35a9\u8c28")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub009\u71de\u8b9d\ub5a4\uec75")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub00d\u71ca\u8b9f\ub5a8")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub01e\u71ca\u8b83\ub5bd\uec60\u35b3")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub01f\u71ca\u8b9f\ub5a3\uec60\u35b5")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub015\u71ce\u8b90\ub5a9")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub01f\u71c4\u8b9f\ub5a8")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub010\u71de\u8b82\ub5a4\uec66")) && !Nigga.getUnlocalizedName().contains(Qprot0.0("\ub00e\u71df\u8b83\ub5a4\uec6b\u35a0"));
    }

    public ChestStealer() {
        super(Qprot0.0("\ub03e\u71c3\u8b94\ua7f7\u9a26\u3594\u8c3b\udcad\u5703\u3174\ubf8f\uaf1e"), 0, Module.Category.PLAYER);
        ChestStealer Nigga;
        Nigga.close = new ModeSetting(Qprot0.0("\ub03e\u71c7\u8b9e\ua7f7\u9a37"), Qprot0.0("\ub02e\u71db\u8b9e\ua7eb\u9a34"), Qprot0.0("\ub02e\u71db\u8b9e\ua7eb\u9a34"), Qprot0.0("\ub038\u71c5\u8b90\ua7e6\u9a3e\u35a2\u8c2b"), Qprot0.0("\ub039\u71c2\u8b82\ua7e5\u9a30\u35ab\u8c2a\udcac"));
        Nigga.closeDelay = new Timer();
        Nigga.queue = new ArrayDeque();
        Nigga.addSettings(Nigga.close, Nigga.delay);
    }

    public boolean getFreeSlot() {
        for (int Nigga = 26; Nigga > 0; --Nigga) {
            ChestStealer Nigga2;
            ItemStack Nigga3 = Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga);
            if (Nigga3 != null) continue;
            return true;
        }
        return false;
    }

    public static {
        throw throwable;
    }

    public boolean isChestEmpty(ContainerChest Nigga) {
        for (int Nigga2 = 0; Nigga2 < Nigga.getLowerChestInventory().getSizeInventory(); ++Nigga2) {
            if (Nigga.getLowerChestInventory().getStackInSlot(Nigga2) == null) continue;
            return false;
        }
        return true;
    }

    public boolean isChestInventory() {
        ChestStealer Nigga;
        return Nigga.mc.thePlayer.openContainer != null && Nigga.mc.thePlayer.openContainer instanceof ContainerChest;
    }
}

