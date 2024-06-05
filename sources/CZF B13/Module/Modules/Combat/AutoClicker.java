package gq.vapu.czfclient.Module.Modules.Combat;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Blatant.Killaura;
import gq.vapu.czfclient.Util.PlayerUtil;
import gq.vapu.czfclient.Util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.Random;


public class AutoClicker
        extends Module {

    public static Numbers<Double> cpsmin = new Numbers("CPSMin", "CPSMin", 8.0, 2.0, 20.0, 1.0);
    public static Numbers<Double> cpsmax = new Numbers("CPSMax", "CPSMax", 8.0, 2.0, 20.0, 1.0);
    public static int Click;
    public static boolean Clicked;
    private final long nextDelay = 0L;
    private final TimeHelper time2 = new TimeHelper();
    private final TimeHelper time3 = new TimeHelper();
    public TimeHelper time = new TimeHelper();
    public Option<Boolean> ab = new Option<Boolean>("BlockHit", "BlockHit", false);
    public Option<Boolean> BreakBlock = new Option<Boolean>("BreakBlock", "BreakBlock", true);
    public Option<Boolean> InvClicker = new Option<Boolean>("Inventory", "Inventory", false);
    protected Random r = new Random();
    protected long lastMS = -1L;
    long lastMSInventory = -1L;
    private double delay = 0.0;

    public AutoClicker() {
        super("AutoClicker", new String[]{"AutoClicker"}, ModuleType.Combat);
        this.setColor(new Color(208, 30, 142).getRGB());
        super.addValues(cpsmin, cpsmax, BreakBlock);
    }

    private void delay() {
        float minCps = cpsmin.getValue().floatValue();
        float maxCps = cpsmax.getValue().floatValue();
        float minDelay = 1000.0f / minCps;
        float maxDelay = 1000.0f / maxCps;
        this.delay = (double) maxDelay + this.r.nextDouble() * (double) (minDelay - maxDelay);
    }


    @EventHandler
    private void onUpdate(EventPreUpdate event) {
        boolean isblock;
        BlockPos bp = mc.thePlayer.rayTrace(6.0, 0.0f).getBlockPos();
        boolean bl = isblock = mc.theWorld.getBlockState(bp).getBlock() != Blocks.air && mc.objectMouseOver.typeOfHit != MovingObjectType.ENTITY;
        if (!BreakBlock.getValue()) isblock = false;
        if (this.time2.delay(this.delay) && !this.time2.delay(this.delay + this.delay / 2)) Clicked = true;
        if (this.time2.delay(this.delay + this.delay - 1.0)) {
            Clicked = false;
            time2.reset();
        }

        if (!ModuleManager.getModuleByClass(Killaura.class).isEnabled() && Mouse.isButtonDown(0) && this.time.delay(this.delay) && Minecraft.currentScreen == null && !isblock) {
            PlayerUtil.blockHit(mc.objectMouseOver.entityHit, this.ab.getValue());
            mc.leftClickCounter = 0;
            mc.clickMouse();
            this.delay();
            this.time.reset();
        }
    }

//    @EventHandler
//    private void invClicks(EventPreUpdate event) {
//        if (!Keyboard.isKeyDown((int)42)) {
//            return;
//        }
//        if (mc.currentScreen instanceof GuiContainer && ((Boolean)this.InvClicker.getValue()).booleanValue()) {
//            float invClickDelay = 1000.0f / ((Double)cpsmax.getValue()).floatValue() + (float)this.r.nextInt(50);
//            if (Mouse.isButtonDown((int)0) && this.time3.delay((double)invClickDelay)) {
//                try {
//                    mc.currentScreen.InventoryClicks();
//                    this.time3.reset();
//                }
//                catch (Exception exception) {
//                    // empty catch block
//                }
//            }
//        }
//    }
}


