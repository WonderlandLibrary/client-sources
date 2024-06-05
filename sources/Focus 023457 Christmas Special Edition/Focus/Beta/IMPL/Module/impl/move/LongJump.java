package Focus.Beta.IMPL.Module.impl.move;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventMove;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.UTILS.helper.Helper;
import Focus.Beta.UTILS.world.MovementUtil;
import Focus.Beta.UTILS.world.PacketUtil;
import Focus.Beta.UTILS.world.Timer;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

public class LongJump extends Module {
    public Mode<Enum> mode = new Mode<>("Mode", "Mode", Modes.values(), Modes.Hypixel);
    private double moveSpeed;
    private boolean canDisable;
    private boolean velocitywasEnabled;
    private double z;
    private double O;
    private boolean fuck = false;
    Timer timer = new Timer();
    boolean canFly = false;
    private boolean J;
    public LongJump(){
        super("LongJump", new String[0], Type.MOVE, "No");
        this.addValues(mode);
    }

    @Override
    public void onEnable(){
        this.moveSpeed = this.z = this.mc.thePlayer.a(true, 0.2D);
        if(mode.getModeAsString().equalsIgnoreCase("Hypixel")) {
            if (this.mc.thePlayer.onGround) {
                this.mc.thePlayer.jump();
            }
        }

        switch (mode.getModeAsString()){
            case "":

                break;
        }

        timer.reset();
    }
    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        canFly = false;
        fuck = false;
    }

    @EventHandler
    public void onUpdate(EventPreUpdate e){
        setSuffix(mode.getModeAsString());
        switch (mode.getModeAsString()){
            case "Hypixel":
                mc.timer.timerSpeed = 0.9F;
                break;
            case "SurvivalDub":

                mc.thePlayer.setSprinting(false);
                if(mc.thePlayer.hurtTime > 0){
                    canFly = true;
                }else if(!canFly){
                    ModuleManager.getModuleByName("Velocity").setEnabled(false);
                    e.setPitch(-89F);
                    mc.thePlayer.motionX = 0.0;
                    mc.thePlayer.motionZ = 0.0;
                    if(!((mc.thePlayer.getHeldItem().getItem()) instanceof ItemBow)){
                        Helper.sendMessage("Move Bow into your hand");
                        timer.reset();
                    }else{
                        if(!timer.hasElapsed(340, false)){
                            mc.gameSettings.keyBindUseItem.pressed = true;
                        }else{

                            mc.gameSettings.keyBindUseItem.pressed = false;
                            if(timer.hasElapsed(540, false)) {
                                e.setPitch(mc.thePlayer.rotationPitch);
                            }
                        }
                    }
                }

                if(canFly){
                    mc.gameSettings.keyBindForward.pressed = true;
                    mc.thePlayer.motionY = 0;
                    MovementUtil.setMotion(0.4D);
                }
                break;
            case "TAKA":
                if(mc.thePlayer.hurtTime > 0){
                    fuck = true;
;                }else if(!fuck){
                    e.setPitch(-89F);
                    if(!((mc.thePlayer.getHeldItem().getItem()) instanceof ItemBow && mc.thePlayer.getHeldItem().getItem() != null)){
                        Helper.sendMessage("Move Bow into your hand");
                        timer.reset();
                    }else{
                        if(!timer.hasElapsed(340, false)){
                            mc.gameSettings.keyBindUseItem.pressed = true;
                        }else{

                            mc.gameSettings.keyBindUseItem.pressed = false;
                        }
                    }
                }
                if(fuck){

                    float direction = mc.thePlayer.rotationYaw + ((mc.thePlayer.moveForward < 0) ? 100 : 0) +  ((mc.thePlayer.moveStrafing > 0) ? (-90F * ((mc.thePlayer.moveForward < 0) ? -.5F
                            : ((mc.thePlayer.moveForward > 0) ? .4F : 1F))) : 0);
                    float x = (float) Math.cos((direction + 90F) * Math.PI / 100);
                    float z = (float) Math.sin((direction + 90F) * Math.PI / 100);

                    float speed = 1.30F;

                    if(this.timer.hasElapsed(10L, true)){
                        speed += Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionY + 0.14F) + speed;
                    }

                    if(mc.thePlayer.hurtTime > 1 && mc.thePlayer.fallDistance < 1){
                        Helper.sendMessage("Test");
                        mc.thePlayer.motionX = x * speed;
                        mc.thePlayer.motionZ = z * speed;
                    }
                }
                break;
            case "AGC":


                if(mc.thePlayer.hurtTime > 0){
                    mc.gameSettings.keyBindForward.pressed = true;
                    mc.timer.timerSpeed = 0.1F;
                    if(!mc.thePlayer.onGround && mc.gameSettings.keyBindForward.isKeyDown()){
                        MovementUtil.setMotion(4.6);
                    }
                }else{
                    e.setPitch(-89F);
                    if(!((mc.thePlayer.getHeldItem().getItem()) instanceof ItemBow)){
                        Helper.sendMessage("Move Bow into your hand");
                        timer.reset();
                    }else{
                        if(!timer.hasElapsed(340, false)){
                            mc.gameSettings.keyBindUseItem.pressed = true;
                        }else{

                            mc.gameSettings.keyBindUseItem.pressed = false;
                        }
                    }
                }


                break;
        }
    }

    @EventHandler
    public void onMove(EventMove e){
        switch (mode.getModeAsString()){
            case "Hypixel":
                if(this.mc.thePlayer.onGround){
                    if(this.J){
                        e.setY(this.mc.thePlayer.motionY = this.mc.thePlayer.getBaseMotionY());
                        this.moveSpeed *= 2.139999980926514D;
                    }

                    this.moveSpeed = this.mc.thePlayer.getBySprinting() * 2.0D;
                }
                if(this.J) {
                    this.moveSpeed = this.O - 0.66D * (this.O - this.z);
                }
                this.moveSpeed -= this.O / 24.0D;
                if(!this.mc.thePlayer.isPotionActive(Potion.jump) && this.mc.thePlayer.motionY < 0.0D) {
                    this.moveSpeed = this.z;
                    if(this.mc.thePlayer.ticksExisted % 2 == 0 && (double)this.mc.thePlayer.fallDistance < 0.45D) {
                        this.moveSpeed = this.z * 1.2D;
                        this.mc.thePlayer.motionY = 0.0D;
                    }
                }


                this.moveSpeed = Math.max(this.moveSpeed, this.z);
                e.setSpeed(e, this.moveSpeed);
                break;
        }
    }

    enum Modes{
        Hypixel, AGC, SurvivalDub, TAKA
    }
}
