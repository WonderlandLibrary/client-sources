package Focus.Beta.IMPL.Module.impl.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.scoreboard.Team;
import java.awt.*;
import java.util.ArrayList;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPacketReceive;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.UTILS.helper.Helper;
import Focus.Beta.UTILS.world.Timer;


public class AntiBot extends Module {

    public static Mode<Enum> mode = new Mode("Mode", "Mode", AntiBotMODE.values(), AntiBotMODE.Hypixel);
    public static ArrayList<EntityPlayer> bots = new ArrayList<>();
	public static ArrayList<Entity> bad = new ArrayList();
	public static ArrayList<Entity> botList = new ArrayList();
	public static ArrayList<Integer> invalidID = new ArrayList();
	Entity currentEntity;
	public Timer timer = new Timer();
	private final String[] strings = new String[]{"1st Killer - ", "1st Place - ", "You died! Want to play again? Click here!", " - Damage Dealt - ", "1st - ", "Winning Team - ", "Winners: ", "Winner: ", "Winning Team: ", " win the game!", "1st Place: ", "Last team standing!", "Winner #1 (", "Top Survivors", "Winners - "};

    public AntiBot() {
        super("AntiBot", new String[]{"nobot", "botkiller"}, Type.COMBAT, "Removes anti-cheat bot");
        this.setColor(new Color(217, 149, 251).getRGB());
        addValues(mode);
    }

  
    @Override
	public void onEnable() {
		super.onEnable();
		bots.clear();
		invalidID.clear();
		botList.clear();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		bots.clear();
		invalidID.clear();
		botList.clear();
	}
    @EventHandler
    public void e(EventPacketReceive e) {

    	if(mode.getValue() == AntiBotMODE.Hypixel) {
    		if (bots.isEmpty())
				return;
			if (e.getPacket() instanceof S02PacketChat) {
				for (String string : strings) {
					if (((S02PacketChat) e.getPacket()).getChatComponent().getUnformattedText().contains(string))
						this.bots.clear();
				}
			}
    	}
    	if(mode.getValue() == AntiBotMODE.Mineplex) {
    		if (e.getPacket() instanceof S0CPacketSpawnPlayer) {
				S0CPacketSpawnPlayer packetSpawnPlayer = (S0CPacketSpawnPlayer) e.getPacket();
				if (packetSpawnPlayer.func_148944_c().size() < 10) {
					invalidID.add(packetSpawnPlayer.getEntityID());
				}
			} else if (e.getPacket() instanceof S01PacketJoinGame) {
				invalidID.clear();
			}
    	}
    }


    @EventHandler
    private void onUpdate(EventPreUpdate event) {
setSuffix(mode
		.getModeAsString());
        if (mc.theWorld == null) return;

        switch(mode.getModeAsString()) {
        case"Hypixel":
        	if(mc.thePlayer.ticksExisted <= 500) {
        		for (EntityPlayer entity : mc.theWorld.playerEntities) {
					if (entity.getDistanceToEntity(mc.thePlayer) <= 17)
						if (Math.abs(mc.thePlayer.posY - entity.posY) > 2)
							if ( entity != mc.thePlayer && !bots.contains(entity) && entity.ticksExisted != 0 && entity.ticksExisted <= 10) {
								bots.add(entity);
								System.out.println("Added bot: " + entity.getGameProfile().getName() + ", Distance: " + entity.getDistanceToEntity(mc.thePlayer) + ", Ticks Existed: " + entity.ticksExisted);
							}
				}
        	}
        	break;
        case "Mineplex":
        	mc.theWorld.playerEntities.stream()
			.filter(entity -> entity != mc.thePlayer)
			.filter(entity -> invalidID.contains(entity.getEntityId()))
			.forEach(entityPlayer -> bots.add(entityPlayer));
        	break;
        case "Matrix":
        	for(Entity entity : mc.theWorld.loadedEntityList) {
        		if(entity instanceof EntityLivingBase)
					currentEntity = entity;
			}
        	
        	if(currentEntity != mc.thePlayer && mc.thePlayer.getDistanceToEntity(currentEntity) < 10.0F) {
        		mc.theWorld.removeEntity(currentEntity);
        		Helper.sendMessage("Removed Bot " + currentEntity.getName());
        	}
        	
        	break;
        }
    }

   

    private enum AntiBotMODE {
        Hypixel, Mineplex, Matrix
    }

}