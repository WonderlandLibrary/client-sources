package Focus.Beta.IMPL.Module.impl.misc;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPostUpdate;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.UTILS.world.MovementUtil;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
public class NoSlow extends Module {

    private static final C07PacketPlayerDigging PLAYER_DIGGING = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
    private static final C08PacketPlayerBlockPlacement BLOCK_PLACEMENT = new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f);
    private static final Mode<Enum> mode = new Mode("Modes", "Modes", Modes.values(), Modes.Vanilla);
    public NoSlow(){
        super("NoSlow", new String[0], Type.MISC, "Allow's to run while blocking");
        this.addValues(mode);
    }

    @EventHandler
    public void a(EventPreUpdate e){
        setSuffix(mode.getModeAsString());
        if(MovementUtil.isMoving() && mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && mc.thePlayer.isBlocking()) {
            switch(mode.getModeAsString()) {
            case "NCP":
                mc.getNetHandler().getNetworkManager().sendPacket(PLAYER_DIGGING);
            	break;
            }
        }
    }


    @EventHandler
    public void a(EventPostUpdate e){
        if(MovementUtil.isMoving() && mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && mc.thePlayer.isBlocking()) {
        	  switch(mode.getModeAsString()) {
              case "NCP":
                mc.getNetHandler().getNetworkManager().sendPacket(BLOCK_PLACEMENT);
                break;
              case "AAC5":
            	  mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1,-1,-1), 255, mc.thePlayer.inventory.getCurrentItem(), 0f, 0f, 0f));
            	  break;
            }
        }
    }
    
    enum Modes{
    	Vanilla,
    	NCP,
    	AAC5
    }


}
