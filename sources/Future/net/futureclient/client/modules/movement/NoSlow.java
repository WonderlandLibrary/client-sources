package net.futureclient.client.modules.movement;

import net.futureclient.client.ZG;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiOptions;
import net.futureclient.client.KC;
import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.modules.movement.noslow.Listener7;
import net.futureclient.client.modules.movement.noslow.Listener6;
import net.futureclient.client.modules.movement.noslow.Listener5;
import net.futureclient.client.modules.movement.noslow.Listener4;
import net.futureclient.client.modules.movement.noslow.Listener3;
import net.futureclient.client.modules.movement.noslow.Listener2;
import net.futureclient.client.modules.movement.noslow.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class NoSlow extends Ea
{
    private NumberValue webSpeed;
    public Value<Boolean> inventoryMove;
    public Value<Boolean> soulSand;
    public Value<Boolean> items;
    public Value<Boolean> webs;
    private Timer k;
    
    public static Minecraft getMinecraft() {
        return NoSlow.D;
    }
    
    public NoSlow() {
        super("NoSlow", new String[] { "NoSlow", "noslowdown", "ns" }, false, -14254, Category.MOVEMENT);
        this.soulSand = new Value<Boolean>(true, new String[] { "SoulSand", "ss", "soul-sand", "sand" });
        this.inventoryMove = new Value<Boolean>(true, new String[] { "InventoryMove", "Inventory-Move", "invmove", "inventory" });
        this.webs = new Value<Boolean>(true, new String[] { "Webs", "web", "w" });
        this.items = new Value<Boolean>(true, new String[] { "Items", "item", "i" });
        this.webSpeed = new NumberValue(0.0, 1.273197475E-314, 0.0, 1.273197475E-314, new String[] { "WebSpeed", "wspeed", "web-speed", "webs" });
        final int n = 5;
        this.k = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.inventoryMove;
        array[1] = this.soulSand;
        array[2] = this.webSpeed;
        array[3] = this.webs;
        array[4] = this.items;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this), new Listener5(this), new Listener6(this), new Listener7(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft2() {
        return NoSlow.D;
    }
    
    private void C() {
        if (this.inventoryMove.M()) {
            final KeyBinding[] array = { NoSlow.D.gameSettings.keyBindForward, NoSlow.D.gameSettings.keyBindBack, NoSlow.D.gameSettings.keyBindLeft, NoSlow.D.gameSettings.keyBindRight, NoSlow.D.gameSettings.keyBindJump, NoSlow.D.gameSettings.keyBindSprint };
            if (NoSlow.D.currentScreen instanceof KC || NoSlow.D.currentScreen instanceof GuiOptions || NoSlow.D.currentScreen instanceof GuiVideoSettings || NoSlow.D.currentScreen instanceof GuiScreenOptionsSounds || NoSlow.D.currentScreen instanceof GuiContainer || NoSlow.D.currentScreen instanceof GuiIngameMenu) {
                final KeyBinding[] array2;
                final int length = (array2 = array).length;
                int i = 0;
                int n = 0;
                while (i < length) {
                    final KeyBinding keyBinding;
                    final int keyCode = (keyBinding = array2[n]).getKeyCode();
                    ++n;
                    KeyBinding.setKeyBindState(keyCode, ZG.M(keyBinding));
                    i = n;
                }
            }
            else if (NoSlow.D.currentScreen == null) {
                final KeyBinding[] array3;
                final int length2 = (array3 = array).length;
                int j = 0;
                int n2 = 0;
                while (j < length2) {
                    final KeyBinding keyBinding2;
                    if (!ZG.M(keyBinding2 = array3[n2])) {
                        KeyBinding.setKeyBindState(keyBinding2.getKeyCode(), false);
                    }
                    j = ++n2;
                }
            }
        }
    }
    
    public static Minecraft getMinecraft3() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft4() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft5() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft6() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft7() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft8() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft9() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft10() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft11() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft12() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft13() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft14() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft15() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft16() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft17() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft18() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft19() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft20() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft21() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft22() {
        return NoSlow.D;
    }
    
    public static void M(final NoSlow noSlow) {
        noSlow.C();
    }
    
    public static Minecraft getMinecraft23() {
        return NoSlow.D;
    }
    
    public static Timer M(final NoSlow noSlow) {
        return noSlow.k;
    }
    
    public static NumberValue M(final NoSlow noSlow) {
        return noSlow.webSpeed;
    }
    
    public static Minecraft getMinecraft24() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft25() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft26() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft27() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft28() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft29() {
        return NoSlow.D;
    }
    
    public static Minecraft getMinecraft30() {
        return NoSlow.D;
    }
}
