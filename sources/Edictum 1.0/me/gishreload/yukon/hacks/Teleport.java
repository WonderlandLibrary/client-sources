package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.gishreload.yukon.*;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class Teleport extends Module{
	
	public Teleport() {
		super("Teleport", 0, Category.PLAYER);
	}
	
    private double tX;
    private double tY;
    private double tZ;
    private int counter;
    private boolean aac2b1;
    private int aac2i1;
    private double aac2x;
    private double aac2y;
    private double aac2z;
	
	public void onEnable(){
		Meanings.coordinates = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Yukon\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eTeleport \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	public void onDisable(){
		Meanings.coordinates = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Yukon\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eTeleport \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
		super.onDisable();
	}
	
	public void onUpdate(){
		if(this.isToggled()){
			if(!Meanings.aac){
				Meanings.noaac = true;
				}
				if(Meanings.noaac){
			if(Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed){
				EntityPlayerSP player = mc.thePlayer;
				if (player.isSneaking() && player.onGround) {
				mc.thePlayer.setPosition((double)mc.inputNumber1, (double)mc.inputNumber2, (double)mc.var65);
				Edictum.addChatMessage("\u00a74Teleported XYZ: " + mc.inputNumber1 + " / " + mc.inputNumber2 + " / " + mc.var65);
				Minecraft.getMinecraft().rightClickMouse();
			}
			}
				}
				if(Meanings.aac){
					Meanings.noaac = false;
			if(Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed){
			 EntityPlayerSP player = mc.thePlayer;
		        if (player.isSneaking() && player.onGround) {
		            double dX = player.posX - this.tX;
		            double dZ = player.posZ - this.tZ;
		            double dist = Math.sqrt(dX * dX + dZ * dZ);
		            if (dist > 5.0D) {
		                int runs = (int) (dist / 5.0D);
		                double cX = player.posX;
		                double cZ = player.posZ;
		 
		                for (int i = 0; i < runs; ++i) {
		                    cX -= dX / (double) runs;
		                    cZ -= dZ / (double) runs;
		                    player.connection.sendPacket(new CPacketPlayer.Position(cX, player.posY, cZ, false));
		                }
		            }
		 
		            player.connection.sendPacket(new CPacketPlayer.Position(this.tX, player.posY, this.tZ, false));
		            player.connection.sendPacket(new CPacketPlayer.Position(this.tX, player.posY + 2.0D, this.tZ, false));
		            Edictum.addChatMessage("TP-Location: " + this.tX + " " + this.tY + " " + this.tZ);
					Minecraft.getMinecraft().rightClickMouse();
		        }
			}
			}
				}
			}
public void tp1(int ticks) {
    EntityPlayerSP player = mc.thePlayer;
    MovementInput org = player.movementInput;
    player.movementInput = new MovementInput();

    for (int i = 0; i < ticks; ++i) {
        player.onUpdate();
    }

    player.movementInput = org;
}
}

