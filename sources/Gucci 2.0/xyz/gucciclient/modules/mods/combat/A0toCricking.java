package xyz.gucciclient.modules.mods.combat;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import java.util.*;
import java.lang.reflect.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.settings.*;
import xyz.gucciclient.utils.*;
import xyz.gucciclient.utils.Timer;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class A0toCricking extends Module
{
    private NumberValue maxcps;
    private NumberValue mincps;
    private NumberValue jitterstren;
    private BooleanValue jitter;
    private BooleanValue weapon;
    private BooleanValue inv;
    private long nextLeftUp;
    private long nextLeftDown;
    private long nextRightUp;
    private long nextRightDown;
    private long nextDrop;
    private long nextExhaust;
    private Timer timer;
    private Random random;
    private double dr0pR4t3;
    private boolean dr0pp1ng;
    private Method guiScreenMethod;
    
    public A0toCricking() {
        super(Modules.Clicker.name(), 0, Category.COMBAT);
        this.maxcps = new NumberValue("MaxCPS", 12.0, 1.0, 20.0);
        this.mincps = new NumberValue("MinCPS", 6.0, 1.0, 20.0);
        this.jitterstren = new NumberValue("Jitter Strength", 0.5, 0.1, 2.0);
        this.jitter = new BooleanValue("Jitter", false);
        this.weapon = new BooleanValue("Weapon", false);
        this.inv = new BooleanValue("Inventory fill", false);
        this.timer = new Timer();
        this.random = new Random();
        this.addValue(this.maxcps);
        this.addValue(this.mincps);
        this.addValue(this.jitterstren);
        this.addBoolean(this.jitter);
        this.addBoolean(this.weapon);
        this.addBoolean(this.inv);
        try {
            this.guiScreenMethod = GuiScreen.class.getDeclaredMethod("func_73864_a", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    public boolean check(final EntityPlayerSP playerSP) {
        return !this.weapon.getState() || (playerSP.getCurrentEquippedItem() != null && (playerSP.getCurrentEquippedItem().getItem() instanceof ItemSword || playerSP.getCurrentEquippedItem().getItem() instanceof ItemAxe));
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent ev3nt) throws Exception {
        if (this.mc.currentScreen == null && this.check(this.mc.thePlayer)) {
            Mouse.poll();
            if (Mouse.isButtonDown(0)) {
                if (this.jitter.getState() && this.random.nextDouble() > 0.65) {
                    final float jitterstrenght = (float)(this.jitterstren.getValue() * 0.5);
                    if (this.random.nextBoolean()) {
                        final EntityPlayerSP thePlayer = this.mc.thePlayer;
                        thePlayer.rotationYaw += this.random.nextFloat() * jitterstrenght;
                    }
                    else {
                        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                        thePlayer2.rotationYaw -= this.random.nextFloat() * jitterstrenght;
                    }
                    if (this.random.nextBoolean()) {
                        final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                        thePlayer3.rotationPitch += (float)(this.random.nextFloat() * (jitterstrenght * 0.75));
                    }
                    else {
                        final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                        thePlayer4.rotationPitch -= (float)(this.random.nextFloat() * (jitterstrenght * 0.75));
                    }
                }
                if (this.nextLeftDown > 0L && this.nextLeftUp > 0L) {
                    if (System.currentTimeMillis() > this.nextLeftDown) {
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindAttack.getKeyCode(), true);
                        KeyBinding.onTick(this.mc.gameSettings.keyBindAttack.getKeyCode());
                        MouseUtil.sendClick(0, true);
                        this.generateLeftDelay();
                    }
                    else if (System.currentTimeMillis() > this.nextLeftUp) {
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindAttack.getKeyCode(), false);
                        MouseUtil.sendClick(0, false);
                    }
                }
                else {
                    this.generateLeftDelay();
                }
                if (!Mouse.isButtonDown(1)) {
                    final long n = 0L;
                    this.nextRightUp = 0L;
                    this.nextRightDown = 0L;
                }
            }
            else {
                final long n2 = 0L;
                this.nextRightUp = 0L;
                this.nextRightDown = 0L;
                this.nextLeftUp = 0L;
                this.nextLeftDown = 0L;
            }
        }
        else if (this.mc.currentScreen instanceof GuiInventory) {
            if (Mouse.isButtonDown(0) && (Keyboard.isKeyDown(54) || Keyboard.isKeyDown(42))) {
                if (!this.inv.getState()) {
                    return;
                }
                if (this.nextLeftUp == 0L || this.nextLeftDown == 0L) {
                    this.generateLeftDelay();
                    return;
                }
                if (System.currentTimeMillis() > this.nextLeftDown) {
                    this.generateLeftDelay();
                    this.clickInventory(this.mc.currentScreen);
                }
            }
            else {
                final long n3 = 0L;
                this.nextRightUp = 0L;
                this.nextRightDown = 0L;
                this.nextLeftUp = 0L;
                this.nextLeftDown = 0L;
            }
        }
    }
    
    private void generateLeftDelay() {
        if (this.mincps.getValue() > this.maxcps.getValue()) {
            return;
        }
        long d3l4y = (int)Math.round(900.0 / this.mincps.getValue() + this.random.nextDouble() * (this.maxcps.getValue() - this.mincps.getValue()));
        if (System.currentTimeMillis() > this.nextDrop) {
            if (!this.dr0pp1ng && this.random.nextInt(100) >= 85) {
                this.dr0pp1ng = true;
                this.dr0pR4t3 = 1.1 + this.random.nextDouble() * 0.15;
            }
            else {
                this.dr0pp1ng = false;
            }
            this.nextDrop = System.currentTimeMillis() + 500L + this.random.nextInt(1500);
        }
        if (this.dr0pp1ng) {
            d3l4y *= (long)this.dr0pR4t3;
        }
        if (System.currentTimeMillis() > this.nextExhaust) {
            if (this.random.nextInt(100) >= 80) {
                d3l4y += 50L + this.random.nextInt(150);
            }
            this.nextExhaust = System.currentTimeMillis() + 500L + this.random.nextInt(1500);
        }
        this.nextLeftDown = System.currentTimeMillis() + d3l4y;
        this.nextLeftUp = System.currentTimeMillis() + d3l4y / 2L - this.random.nextInt(10);
    }
    
    private void clickInventory(final GuiScreen screen) {
        final int v4r1 = Mouse.getX() * screen.width / this.mc.displayWidth;
        final int v4r2 = screen.height - Mouse.getY() * screen.height / this.mc.displayHeight - 1;
        final int v4r3 = 0;
        try {
            this.guiScreenMethod.setAccessible(true);
            this.guiScreenMethod.invoke(screen, v4r1, v4r2, 0);
            this.guiScreenMethod.setAccessible(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
