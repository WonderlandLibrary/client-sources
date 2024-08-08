package me.xatzdevelopments.xatz.gui;

import java.awt.Color;

import me.xatzdevelopments.xatz.client.modules.KillAura;
import me.xatzdevelopments.xatz.client.modules.Killaura69420;
import me.xatzdevelopments.xatz.gui.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.MathHelper;

public class TargetHUDMaker extends GuiScreen{
	private static final Color COLOR = new Color(0, 0, 0, 180);
	   public final Stopwatch animationStopwatch = new Stopwatch();
	   private EntityOtherPlayerMP target;
	   public double healthBarWidth;
	   public double hudHeight;
	   float width = 140.0F;
       float height = 40.0F;
       float xOffset = 40.0F;
       public float x = width / 2.0F + 75f;
       public float x2 = width / 2.0F + 37f;
       public float y = height / 2.0F + 120.0F;
       double hpPercentage = 1;//= Killaura69420.health2 / Killaura69420.maxHealth;
       double hpPercentage2 = MathHelper.clamp_double(hpPercentage, 0.0D, 1.0D);
       public double hpWidth = 92.0D * hpPercentage2;
       public int healthColor = 16719410;	
	/*public void makeTargetHUD() {
		
        //String KillAura.health = String.valueOf((float)((int)this.target.getHealth()) / 2.0F);
        if (this.animationStopwatch.elapsed(15L)) {
            this.healthBarWidth = AnimationUtils.animate(hpWidth, this.healthBarWidth, 0.3529999852180481D);
            this.hudHeight = AnimationUtils.animate(40.0D, this.hudHeight, 0.10000000149011612D);
            this.animationStopwatch.reset();
         }
        GL11.glEnable(3089);
        RenderUtils.prepareScissorBox(x, y, x + 140.0F, (float)((double)y + this.hudHeight));
        Gui.drawRect((double)x, (double)y, (double)(x + 140.0F), (double)(y + 40.0F), COLOR.getRGB());
        Gui.drawRect((double)(x + 40.0F), (double)(y + 15.0F), (double)(x + 40.0F) + this.healthBarWidth, (double)(y + 25.0F), healthColor);
        //mc.fontRendererObj.drawString(KillAura.health, x + 40.0F + 46.0F - (float)mc.fontRendererObj.getStringWidth(KillAura.health) / 2.0F, y + 16.0F, -1);
        //mc.fontRendererObj.drawStringwithShadow(this.target.getName(), x + 40.0F, y + 2.0F, -1);
        //GuiInventory.drawEntityOnScreen((int)(x + 13.333333F), (int)(y + 40.0F), 20, this.target.rotationYaw, this.target.rotationPitch, this.target);
        GL11.glDisable(3089);
	}*/
}
