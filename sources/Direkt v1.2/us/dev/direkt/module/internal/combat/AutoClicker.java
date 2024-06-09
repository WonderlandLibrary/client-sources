package us.dev.direkt.module.internal.combat;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult.Type;
import us.dev.api.property.Property;
import us.dev.api.timing.Timer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "Auto Clicker", aliases = "clicker", category = ModCategory.COMBAT)
public class AutoClicker extends ToggleableModule {

    @Exposed(description = "Bypass GCheat on Badlion")
    private Property<Boolean> badlionClicker = new Property<>("GCheat", false);

    @Exposed(description = "Target invisible entities")
    private Property<Boolean> invisibles = new Property<>("Invisibles", false);

    @Exposed(description = "Ignore players on your own team")
    private Property<Boolean> teams = new Property<>("Teams", false);

    private Timer timer = new Timer();

	private double clickSpeed = 500;

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
    	double random = Math.random();
    	if(random >= .8) {
    		clickSpeed = 80 + Math.random() * 50;
    	} else {
    		clickSpeed = 100;
    	}
		if (timer.hasReach((int)Math.floor(clickSpeed)) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
	        	if(Wrapper.getMinecraft().objectMouseOver.typeOfHit == Type.BLOCK) {
	        		Wrapper.sendPacketDirect(new CPacketAnimation(EnumHand.MAIN_HAND));
	        	}
            Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND);
        if (Wrapper.getGameSettings().keyBindAttack.isKeyDown()) {
            if (doesQualify(Wrapper.getMouseOver().entityHit)) {
                    if (Wrapper.getPlayer().isActiveItemStackBlocking() && !badlionClicker.getValue())
                        Wrapper.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                        Wrapper.getPlayerController().attackEntity(Wrapper.getPlayer(), Wrapper.getMinecraft().objectMouseOver.entityHit);
                    if (Wrapper.getPlayer().isActiveItemStackBlocking() && !badlionClicker.getValue())
                        Wrapper.sendPacket(new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                    timer.reset();
                }
            }
        }
    });

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
        if (Wrapper.getMinecraft().objectMouseOver.typeOfHit == Type.BLOCK && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && (event.getPacket() instanceof CPacketPlayerDigging || event.getPacket() instanceof CPacketAnimation)) {
        	event.setCancelled(true);
        }
    });
    
    private boolean doesQualify(Entity e) {
        if (e != null && (this.invisibles.getValue() || !e.isInvisible()) && e.isEntityAlive()) {
            return !(e instanceof EntityPlayer) || !(Direkt.getInstance().getFriendManager().isFriend((EntityPlayer) e) || (this.teams.getValue() && e.getTeam().isSameTeam(Wrapper.getPlayer().getTeam())));
        }
        return false;
    }

}
