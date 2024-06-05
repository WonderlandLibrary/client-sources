package me.superskidder.lune.modules.movement;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventMove;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Num;

/**
 * @description: 虚空跳
 * @author: QianXia
 * @create: 2020/9/26 17-22
 **/
public class VoidJump extends Mod {
    public static Num<Double> dis = new Num<Double>("Distance", 5.0, 3.0, 10.0);
    public static Num<Double> height = new Num<Double>("Height", 1.15, 0.1, 5.0);

    public Bool blink = new Bool("Blink",true);

    private boolean needJump = false;
    private double lastY = 0;

    public VoidJump(){
        super("VoidJump", ModCategory.Movement,"You can jump on fall void");
        this.addValues(dis,blink,height);
    }

    @EventTarget
    public void onMove(EventMove event){
        if (mc.thePlayer.fallDistance >= dis.getValue() && mc.thePlayer.motionY <= 0
                && (!needJump || mc.thePlayer.posY <= lastY)
                && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0, 0, 0).expand(0, 0, 0)).isEmpty()
                && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0, -10002.25, 0).expand(0, -10003.75, 0)).isEmpty()){
            mc.thePlayer.motionY = height.getValue();
            event.setX(0);
            event.setZ(0);
            lastY = mc.thePlayer.posY;
            if((boolean)blink.value) {
                event.setMoveSpeed(5);
            }
            needJump = true;
        }

        if (mc.thePlayer.onGround) {
            //event.setMoveSpeed(MoveUtils.getBaseMoveSpeed());
            lastY = 0;
            needJump = false;
        }
    }
}
