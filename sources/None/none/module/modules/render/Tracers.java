package none.module.modules.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event3D;
import none.friend.FriendManager;
import none.module.Category;
import none.module.Module;
import none.module.modules.combat.Antibot;
import none.module.modules.combat.AuraTeams;
import none.module.modules.world.Murder;
import none.utils.BlockUtil;
import none.utils.RenderingUtil;
import none.utils.Utils;
import none.utils.render.Colors;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;

public class Tracers extends Module{

	public Tracers() {
		super("Tracers", "Tracers", Category.RENDER, Keyboard.KEY_NONE);
	}
	
	private static String[] colors = {"Rainbow", "Team", "Health", "Custom"};
	public static ModeValue colormode = new ModeValue("Color", "Rainbow", colors);
	
	private BooleanValue player = new BooleanValue("Player", true);
	private BooleanValue chest = new BooleanValue("Chest", false);
	private BooleanValue spawner = new BooleanValue("Spawner", false);
	private BooleanValue sign = new BooleanValue("Sign", false);

	@Override
	@RegisterEvent(events = Event3D.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + colormode.getSelected());
		
		String colormode = this.colormode.getSelected();
		
		if (event instanceof Event3D) {
			Event3D er = (Event3D) event;
			for (Object o : mc.theWorld.getLoadedEntityList()) {
	            if (o instanceof EntityPlayer && player.getObject()) {
	                EntityPlayer ent = (EntityPlayer) o;
	                if (ent != mc.thePlayer && !ent.isInvisible()) {
	                	
	                	int renderColor = ClientColor.getColor();
	                	switch(colormode){
	                	case"Rainbow":
	                		final int color = ClientColor.rainbow(100);
	            			renderColor = color;
	                		break;
	                	case"Team":
	                		String text = ent.getDisplayName().getFormattedText();
		                	if(Character.toLowerCase(text.charAt(0)) == '§'){
		                    	char oneMore = Character.toLowerCase(text.charAt(1));
		                    	int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
		                    	if (colorCode < 16) {
		                            try {
		                                int newColor = mc.fontRendererObj.colorCode[colorCode];   
		                                 renderColor = Colors.getColor((newColor >> 16), (newColor >> 8 & 0xFF), (newColor & 0xFF), 255);
		                            } catch (ArrayIndexOutOfBoundsException ignored){}
		                        }
		                	}else{
		                		renderColor = Colors.getColor(255, 255, 255, 255);
		                	}
	                		break;
	                	case"Health":{
	                    	float health = ent.getHealth();
	                        if (health > 20) {
	                            health = 20;
	                        }
	                        float[] fractions = new float[]{0f, 0.5f, 1f};
	                        Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
	                        float progress = (health * 5) * 0.01f;
	                        Color customColor = Esp.blendColors(fractions, colors, progress).brighter();
	                        renderColor = customColor.getRGB();
	                    }
	                    break;
	                	}
	                	if (Antibot.getInvalid().contains(ent)) {
	                		renderColor = Color.RED.getRGB();
	                	}else if (FriendManager.isFriend(ent.getName())) {
	                		renderColor = Color.BLUE.getRGB();
	                	}
	                	
	                	float yaw = Utils.getNeededRotationsForAAC(ent)[0];
	                    float posX = (float) ((float) (ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * er.getPartialTicks()) - RenderManager.renderPosX);
	                    float posY = (float) ((float) (ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * er.getPartialTicks()) - RenderManager.renderPosY);
	                    float posZ = (float) ((float) (ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * er.getPartialTicks()) - RenderManager.renderPosZ);
	                    final boolean bobbing = mc.gameSettings.viewBobbing;
	                    mc.gameSettings.viewBobbing = false;
	                    if (Client.nameList.contains(ent.getName())) {
	                    	
	                    }else {
		                    if (Client.instance.moduleManager.murder.isEnabled()) {
		                    	if (ent.isMurderer) {
			                    	RenderingUtil.draw3DLine2(posX, posY, posZ, Color.RED.getRGB());
			                	}
		                    }else {
		                    	if (Client.instance.moduleManager.auraTeams.isEnabled()) {
		                    		if (AuraTeams.player.contains(ent)) {
		                    			renderColor = Color.GREEN.getRGB();
		                    		}
		                    	}
			                    if (Antibot.getInvalid().contains(ent) || mc.thePlayer.getDistanceToEntity(ent) <= 15) {
			                    	RenderingUtil.draw3DLine2(posX, posY, posZ, renderColor);
			                	}else {
			                		RenderingUtil.draw3DLine(posX, posY, posZ, renderColor);
			                	}
		                    }
		                    mc.gameSettings.viewBobbing = bobbing;
	                    }
	                }
	            }
	        }
			
			for (Object o : mc.theWorld.loadedTileEntityList) {
	            int color = -1;
	            if (o instanceof TileEntityChest && chest.getObject()) {
	                TileEntityChest ent = (TileEntityChest) o;
	                color = Colors.getColor(114, 0, 187);
	                float posX = (float) ((float) ent.getPos().getX() - RenderManager.renderPosX);
	                float posY = (float) ((float) ent.getPos().getY() - RenderManager.renderPosY);
	                float posZ = (float) ((float) ent.getPos().getZ() - RenderManager.renderPosZ);
	                float yaw = BlockUtil.getRotationNeededForBlock(mc.thePlayer, new BlockPos(posX, posY, posZ))[0];
	                final boolean bobbing = mc.gameSettings.viewBobbing;
	                mc.gameSettings.viewBobbing = false;
	                RenderingUtil.draw3DLine(posX, posY, posZ, color);
	                mc.gameSettings.viewBobbing = bobbing;
	            }

	            if (o instanceof TileEntityMobSpawner && spawner.getObject()) {
	                TileEntityMobSpawner ent = (TileEntityMobSpawner) o;
	                color = Colors.getColor(255, 156, 0);
	                float posX = (float) ((float) ent.getPos().getX() - RenderManager.renderPosX);
	                float posY = (float) ((float) ent.getPos().getY() - RenderManager.renderPosY);
	                float posZ = (float) ((float) ent.getPos().getZ() - RenderManager.renderPosZ);
	                final boolean bobbing = mc.gameSettings.viewBobbing;
	                mc.gameSettings.viewBobbing = false;
	                RenderingUtil.draw3DLine(posX, posY, posZ, color);
	                mc.gameSettings.viewBobbing = bobbing;
	            }

	            if (o instanceof TileEntitySign && sign.getObject()) {
	                TileEntitySign ent = (TileEntitySign) o;
	                color = Colors.getColor(130, 162, 0);
	                float posX = (float) ((float) ent.getPos().getX() - RenderManager.renderPosX);
	                float posY = (float) ((float) ent.getPos().getY() - RenderManager.renderPosY);
	                float posZ = (float) ((float) ent.getPos().getZ() - RenderManager.renderPosZ);
	                float yaw = BlockUtil.getRotationNeededForBlock(mc.thePlayer, new BlockPos(posX, posY, posZ))[0];
	                float pitch = BlockUtil.getRotationNeededForBlock(mc.thePlayer, new BlockPos(posX, posY, posZ))[1];
	                final boolean bobbing = mc.gameSettings.viewBobbing;
	                mc.gameSettings.viewBobbing = false;
	                RenderingUtil.draw3DLine(posX, posY, posZ, color);
	                mc.gameSettings.viewBobbing = bobbing;
	            }

	        }
		}
	}
}
