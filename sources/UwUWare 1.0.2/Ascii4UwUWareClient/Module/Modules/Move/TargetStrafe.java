package Ascii4UwUWareClient.Module.Modules.Move;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender3D;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Combat.Killaura;
import Ascii4UwUWareClient.Util.Render.RenderUtil;
import Ascii4UwUWareClient.Util.player.MovementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.util.ArrayList;

public class TargetStrafe extends Module {
    public Vec3 indexPos;
    public int index, arraySize;
    private boolean set, changeDir;
    public static EntityLivingBase target;
    private ArrayList<EntityLivingBase> targets = new ArrayList<>();
    public static Numbers<Double> distance = new Numbers<Double>("Range", "Range", 3.0, 0.0, 4.0, 0.1);
    private static Option<Boolean> space = new Option<Boolean>("Hold Space", "Hold Space", true);
    private Option<Boolean> esp = new Option<Boolean>("Draw Circle", "Draw Circle", true);


    private float spin = 0.0F;


    public TargetStrafe() {
        super("TargetStrafe", new String[]{"strafe"}, ModuleType.Move);
        this.addValues(distance, space, esp);
        this.setColor(new Color(104, 255, 0).getRGB());


    }

    public int strafeDirection = 1;


    @Override
    public void onEnable() {
        set = false;

    }

    @Override
    public void onDisable() {
        if (mc.thePlayer == null) return;
        mc.timer.timerSpeed = 1f;
        set = false;

    }

    @EventHandler
    public void onRender3DEvent(EventRender3D event) {
        Killaura Killaura = (Killaura) Client.instance.getModuleManager().getModuleByClass(Killaura.class);
        if (Killaura.target != null) {
            RenderUtil.drawLinesAroundPlayer(Killaura.target,
                    distance.getValue(),
                    event.getPartialTicks(),
                    100,
                    1,
                    Color.ORANGE.getRGB());
        }
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e) {
        setSuffix("Dynamic");
        boolean changeDirectionInFight = false;
        boolean thevoid = !MovementUtil.isBlockUnder() && mc.thePlayer.ticksExisted % 3 == 0;

        Killaura aura = (Killaura) Client.instance.getModuleManager().getModuleByClass(Killaura.class);
        if (mc.thePlayer.ticksExisted % 25 == 0 && Killaura.target != null && Minecraft.thePlayer.canEntityBeSeen(Killaura.target)) {
            if (mc.thePlayer.getDistanceToEntity(Killaura.target) <= aura.range.getValue())
                changeDirectionInFight = true;
        }
        if (mc.thePlayer.isCollidedHorizontally || thevoid || !thevoid && changeDirectionInFight && !mc.thePlayer.isCollidedHorizontally) {
            strafeDirection = -strafeDirection;
        }
        if (canStrafe()) mc.thePlayer.movementInput.setForward(0);
    }


    public static boolean canStrafe() {
        return space.getValue() ? Client.instance.getModuleManager().getModuleByClass(Killaura.class).isEnabled() && Killaura.target != null && MovementUtil.isMoving() && Client.instance.getModuleManager().getModuleByClass(TargetStrafe.class).isEnabled() && Minecraft.getMinecraft().gameSettings.keyBindJump.pressed : Client.instance.getModuleManager().getModuleByClass(Killaura.class).isEnabled() && Killaura.target != null && MovementUtil.isMoving() && Client.instance.getModuleManager().getModuleByClass(TargetStrafe.class).isEnabled();
    }

}

