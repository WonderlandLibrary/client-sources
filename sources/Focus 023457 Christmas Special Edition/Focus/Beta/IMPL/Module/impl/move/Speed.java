package Focus.Beta.IMPL.Module.impl.move;

import Focus.Beta.IMPL.Module.impl.focus.FocusBot;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.UTILS.helper.Helper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;

import java.util.List;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventMove;
import Focus.Beta.API.events.world.EventPacketReceive;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.UTILS.world.ACType;
import Focus.Beta.UTILS.world.FrictionUtil;
import Focus.Beta.UTILS.world.MovementUtil;
import Focus.Beta.UTILS.world.PacketUtil;
import Focus.Beta.UTILS.world.Timer;
import net.minecraft.util.BlockPos;

public class Speed extends Module {

    double motion = 0;
    private double stage3;
    private double moveSpeed, lastDist;
	public boolean reset, doSlow, spoofGround;
    public int stage,ticks;
    double mineplex;
    public double movementSpeed;
    public double motionXZ;
    Timer timer = new Timer();
    double dist;
    private int verusStage;
    private double ViperMC = 0.0D;
    public Focus.Beta.IMPL.set.Mode<Enum> modes = new Focus.Beta.IMPL.set.Mode("Mode", "Mode", Mode.values(), Mode.Vanilla);
    public Focus.Beta.IMPL.set.Mode<Enum> verusModes = new Focus.Beta.IMPL.set.Mode("Verus Mode", "Verus Mode", VerusMode.values(), VerusMode.Hop);
    public Focus.Beta.IMPL.set.Numbers<Double> speed = new Focus.Beta.IMPL.set.Numbers<Double>("Speed", "Speed", 0.30, 0.1, 3.0, 0.1);
	private float rotationYaw;
    private int spoofSlot;

    public Speed(){
        super("Speed", new String[0], Type.MOVE, "Allows to move faster");
        this.addValues(modes, verusModes, speed);
    }

    public void onEnable(){
        lastDist = 0;
        spoofSlot = mc.thePlayer.inventory.currentItem;
        verusStage = 0;
        mineplex = 0;
        dist = 0;
    }

    @EventHandler
    public void packetReceive(EventPacketReceive e){
    }
    @EventHandler
    public void onEventPre(EventPreUpdate e) {
        this.setSuffix(modes.getModeAsString());

        if(modes.getModeAsString().equalsIgnoreCase("NCPlw")){
            mc.thePlayer.setSprinting(false);
            if(MovementUtil.isMoving()){
                mc.timer.timerSpeed = 1.05F;
                if(mc.thePlayer.onGround){
                    mc.thePlayer.jump();
                    mc.thePlayer.motionY = 0.4F;
                    if(!mc.thePlayer.isPotionActive(Potion.moveSpeed)){
                        MovementUtil.setMotion(0.39);
                    }else{
                        switch (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()){
                            case 0:
                                MovementUtil.setMotion(0.49);
                                break;
                            case 1:
                                MovementUtil.setMotion(0.575);
                                break;
                            case 2:
                                MovementUtil.setMotion(0.73);
                                break;
                        }
                    }
                }
                else
                {
                    mc.timer.timerSpeed = 1.05F;
                    MovementUtil.setMotion(MovementUtil.getSpeed()*1.0);
                    mc.thePlayer.motionY = -0.4f;
                }
            }else{
                MovementUtil.setMotion(0);
            }
        }
        if(modes.getModeAsString().equalsIgnoreCase("Hypixel")){
            if(MovementUtil.isMoving()){
                if(MovementUtil.isOnGround()) stage = 0;
                switch (stage){
                    case 0:
                        mc.thePlayer.jump();
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.13992E-199 * Math.random(), mc.thePlayer.posZ);
                        moveSpeed = MovementUtil.getBaseMoveSpeed() * 2.139992;
                        break;
                    case 1:
                        moveSpeed *= 0.57;
                        break;
                    case 4:
                        mc.thePlayer.motionY -= 0.0789992;
                        break;
                    default:
                        moveSpeed *= 0.96572;
                        break;
                }
                stage++;
                MovementUtil.setMotion(moveSpeed);
            }
        }
        if (modes.getModeAsString().equalsIgnoreCase("Vanilla")) {
            MovementUtil.setMotion(speed.getValue());
            if (mc.thePlayer.onGround && MovementUtil.isMoving()) {
                mc.thePlayer.jump();
            }
        }
        if (modes.getModeAsString().equalsIgnoreCase("Verus")) {

            if(verusModes.getModeAsString().equalsIgnoreCase("Hop")){
                if(this.mc.thePlayer.onGround && MovementUtil.isMoving()){
                    this.mc.thePlayer.jump();
                }
                else if(!MovementUtil.isMoving()){
                    MovementUtil.setMotion(0.0);
                }
                MovementUtil.setMotion(0.32);
            }

        }
    if(modes.getModeAsString().equalsIgnoreCase("Matrix")){
        if(mc.thePlayer.onGround) {
            mc.thePlayer.motionY = 0.25;
            mc.thePlayer.motionX *= 1.215f;
            mc.thePlayer.motionZ *= 1.215f;
        }
    }
        if(modes.getModeAsString().equalsIgnoreCase("Spartan")){
        if(MovementUtil.isMoving() && mc.thePlayer.onGround){
           mc.thePlayer.jump();
        }

        MovementUtil.setMotion(0.46f);
    }
    }
    @EventHandler
    public void movementUpdate(EventMove e) {
        switch (modes.getModeAsString()){
            case "NCPlw":
                mc.thePlayer.setSprinting(false);
                if(MovementUtil.isMoving()){
                    mc.timer.timerSpeed = 1.05F;
                    if(mc.thePlayer.onGround){
                        mc.thePlayer.jump();
                        mc.thePlayer.motionY = 0.4F;
                        if(!mc.thePlayer.isPotionActive(Potion.moveSpeed)){
                            MovementUtil.setMotion(0.39);
                        }else{
                            switch (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()){
                                case 0:
                                    MovementUtil.setMotion(0.49);
                                    break;
                                case 1:
                                    MovementUtil.setMotion(0.575);
                                    break;
                                case 2:
                                    MovementUtil.setMotion(0.73);
                                    break;
                    }
                }
                    }
                    else
                    {
                        mc.timer.timerSpeed = 1.05F;
                        MovementUtil.setMotion(MovementUtil.getSpeed()*1.0);
                        mc.thePlayer.motionY = -0.4f;
                    }
                }
                break;
            case "Verus":
                    if(verusModes.getModeAsString().equalsIgnoreCase("Port")) {
                        if (!mc.thePlayer.isInLava() && !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder() && mc.thePlayer.ridingEntity == null) {
                            if (MovementUtil.isMoving()) {
                                if (mc.thePlayer.onGround && !mc.thePlayer.isCollidedHorizontally) {
                                    mc.thePlayer.jump();
                                    mc.thePlayer.motionY = 0.0;
                                    MovementUtil.doStrafe(0.65f);
                                    e.setY(0.41999998688698);
                                }else if(mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround){
                                    mc.gameSettings.keyBindJump.pressed = true;
                                }else{

                                    mc.gameSettings.keyBindJump.pressed = false;
                                }
                                MovementUtil.doStrafe();
                            }
                        }
                    }
                break;
            case "Mineplex":
                final Entity player = mc.thePlayer;
                final BlockPos pos = new BlockPos(player.posX, player.posY - 1.0, player.posZ);
                final Block block = mc.theWorld.getBlockState(pos).getBlock();
                mc.timer.timerSpeed = 1.0F;
                if(MovementUtil.isMoving() && MovementUtil.isOnGround()){
                    e.setY(mc.thePlayer.motionY = 0.359);
                    this.doSlow = true;
                    this.dist = this.mineplex;
                    this.mineplex = 0.0;
                }else{
                    mc.timer.timerSpeed = 1.0F;
                    if(this.doSlow){
                        this.mineplex = this.dist + 0.5600000023841858;
                        this.doSlow = false;
                    }else{
                        this.mineplex = this.lastDist * ((this.mineplex > 2.2) ? 0.975 : ((this.moveSpeed >= 1.5) ? 0.98 : 0.985));
                    }
                    e.setY(e.getY() - 1.0E-4);
                }
                final double max = 5.0;
                MovementUtil.doStrafe(Math.max(Math.min(this.mineplex, max), this.doSlow ? 0.0 : 0.455));
                break;
        }
    }

    private boolean invCheck() {
        for (int i = 36; i < 45; i++){
            if(!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onDisable() {
    	mc.timer.timerSpeed = 1.0F;
    	super.onDisable();
    }



    public enum Mode{
        Hypixel,
        Vanilla,
        Spartan,
        Matrix,
        NCPlw,
        Mineplex,
        Verus

    }

    public enum VerusMode{
        Port,
        Hop,
    }
}
