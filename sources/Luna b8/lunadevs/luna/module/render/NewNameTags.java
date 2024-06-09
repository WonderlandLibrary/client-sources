package lunadevs.luna.module.render;

import java.util.Objects;

import org.lwjgl.opengl.GL11;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventRenderNameTag;
import lunadevs.luna.friend.FriendManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import parallaxadevs.Utils.GLUtil;

public class NewNameTags extends Module{

    public static boolean armor = true;
	public static boolean active;

	public NewNameTags() {
		super("3DNameTags", 0, Category.RENDER, true);
	}
	
	@Override
	public void onRender() {
		if(!this.isEnabled) return;
		for (Object o : this.mc.theWorld.playerEntities)
	    {
	      EntityPlayer p = (EntityPlayer)o;
	      if ((p != this.mc.func_175606_aa()) && (p.isEntityAlive()))
	      {
	        this.mc.getRenderManager();double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * this.mc.timer.renderPartialTicks - RenderManager.renderPosX;
	        this.mc.getRenderManager();double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * this.mc.timer.renderPartialTicks - RenderManager.renderPosY;
	        this.mc.getRenderManager();double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * this.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
	        renderNameTag(p, p.getName(), pX, pY, pZ);
	      }
	    }
	  }
	  private void renderNameTag(EntityPlayer entity, String tag, double x, double y, double z)
	  {
	    y += (entity.isSneaking() ? 0.5D : 0.7D);
	    float var2 = this.mc.thePlayer.getDistanceToEntity(entity) / 4.0F;
	    if (var2 < 1.6F) {
	      var2 = 1.6F;
	    }
	    int colour = 16777215;
	    if (FriendManager.isFriend(entity.getName())) {
	      colour = 5488374;
	    } else if (entity.isInvisible()) {
	      colour = 16756480;
	    } else if (entity.isSneaking()) {
	      colour = 11468800;
	    } else if (!this.mc.thePlayer.canEntityBeSeen(entity)) {
	      colour = 786179;
	    }
	    double health = Math.ceil(entity.getHealth() + entity.getAbsorptionAmount()) / 2.0D;
	    EnumChatFormatting healthColour;
	    EnumChatFormatting healthColour4;
	    if (health > 10.0D)
	    {
	      healthColour = EnumChatFormatting.DARK_GREEN;
	    }
	    else
	    {
	      EnumChatFormatting healthColour3;
	      if (health > 7.5D)
	      {
	        healthColour = EnumChatFormatting.GREEN;
	      }
	      else
	      {
	        EnumChatFormatting healthColour1;
	        if (health > 5.0D)
	        {
	          healthColour = EnumChatFormatting.YELLOW;
	        }
	        else
	        {
	          EnumChatFormatting healthColour2;
	          if (health > 2.5D) {
	            healthColour = EnumChatFormatting.GOLD;
	          } else {
	            healthColour = EnumChatFormatting.RED;
	          }
	        }
	      }
	    }
	    if (Math.floor(health) == health) {
	      tag = tag + " " + healthColour + "â�¤ " + (int)Math.floor(health);
	    } else {
	      tag = tag + " " + healthColour + "â�¤ " + health;
	    }
	    float scale = var2 * 2.0F;
	    scale /= 150.0F;
	    GL11.glPushMatrix();
	    GL11.glTranslatef((float)x, (float)y + 1.4F, (float)z);
	    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(-this.mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(this.mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
	    GL11.glScalef(-scale, -scale, scale);
	    GLUtil.setGLCap(2896, false);
	    GLUtil.setGLCap(2929, false);
	    int width = this.mc.fontRendererObj.getStringWidth(tag) / 2;
	    GLUtil.setGLCap(3042, true);
	    GL11.glBlendFunc(770, 771);
	    RenderUtils.drawBorderedRect(-width - 2, -(this.mc.fontRendererObj.FONT_HEIGHT + 1), width + 2, 2.0F, 1.0F, -16777216, -1728052224);
	    this.mc.fontRendererObj.func_175065_a(tag, -width, -(this.mc.fontRendererObj.FONT_HEIGHT - 1), colour, true);
	    GL11.glPushMatrix();
	    if (((Boolean)this.armor).booleanValue())
	    {
	      int xOffset = 0;
	      ItemStack[] arrayOfItemStack;
	      int j = (arrayOfItemStack = entity.inventory.armorInventory).length;
	      for (int i = 0; i < j; i++)
	      {
	        ItemStack armourStack = arrayOfItemStack[i];
	        if (Objects.nonNull(armourStack)) {
	          xOffset -= 8;
	        }
	      }
	      if (Objects.nonNull(entity.getHeldItem()))
	      {
	        xOffset -= 8;
	        ItemStack renderStack = entity.getHeldItem().copy();
	        if ((renderStack.hasEffect()) && (((renderStack.getItem() instanceof ItemTool)) || ((renderStack.getItem() instanceof ItemArmor)))) {
	          renderStack.stackSize = 1;
	        }
	        renderItemStack(renderStack, xOffset, -26);
	        xOffset += 16;
	      }
	      j = (arrayOfItemStack = entity.inventory.armorInventory).length;
	      for (j = 0; j < j; j++)
	      {
	        ItemStack armourStack = arrayOfItemStack[j];
	        if (Objects.nonNull(armourStack))
	        {
	          ItemStack renderStack2 = armourStack.copy();
	          if ((renderStack2.hasEffect()) && (((renderStack2.getItem() instanceof ItemTool)) || ((renderStack2.getItem() instanceof ItemArmor)))) {
	            renderStack2.stackSize = 1;
	          }
	          renderItemStack(renderStack2, xOffset, -26);
	          xOffset += 16;
	        }
	      }
	    }
	    GL11.glPopMatrix();
	    GLUtil.revertAllCaps();
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glPopMatrix();
	  }
	  
	  private void renderItemStack(ItemStack stack, int x, int y)
	  {
	    GL11.glPushMatrix();
	    GL11.glDepthMask(true);
	    GlStateManager.clear(256);
	    RenderHelper.enableStandardItemLighting();
	    this.mc.getRenderItem().zLevel = -150.0F;
	    GlStateManager.disableLighting();
	    GlStateManager.disableDepth();
	    GlStateManager.disableBlend();
	    GlStateManager.enableLighting();
	    GlStateManager.enableDepth();
	    GlStateManager.disableLighting();
	    GlStateManager.disableDepth();
	    GlStateManager.func_179090_x();
	    GlStateManager.disableAlpha();
	    GlStateManager.disableBlend();
	    GlStateManager.enableBlend();
	    GlStateManager.enableAlpha();
	    GlStateManager.func_179098_w();
	    GlStateManager.enableLighting();
	    GlStateManager.enableDepth();
	    this.mc.getRenderItem().func_180450_b(stack, x, y);
	    this.mc.getRenderItem().func_175030_a(this.mc.fontRendererObj, stack, x, y);
	    this.mc.getRenderItem().zLevel = 0.0F;
	    RenderHelper.disableStandardItemLighting();
	    GlStateManager.disableCull();
	    GlStateManager.enableAlpha();
	    GlStateManager.disableBlend();
	    GlStateManager.disableLighting();
	    GlStateManager.scale(0.5D, 0.5D, 0.5D);
	    GlStateManager.disableDepth();
	    renderEnchantText(stack, x, y);
	    GlStateManager.enableDepth();
	    GlStateManager.scale(2.0F, 2.0F, 2.0F);
	    GL11.glPopMatrix();
	  }
	  
	  private void renderEnchantText(ItemStack stack, int x, int y)
	  {
	    int encY = y - 24;
	    if ((stack.getItem() instanceof ItemArmor))
	    {
	      int protectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack);
	      int thornsLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
	      int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
	      if (protectionLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("p" + protectionLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (thornsLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("t" + thornsLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (unbreakingLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("u" + unbreakingLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	    }
	    if ((stack.getItem() instanceof ItemBow))
	    {
	      int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
	      int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
	      int flameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
	      int unbreakingLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
	      if (powerLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("p" + powerLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (punchLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("k" + punchLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (flameLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("f" + flameLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (unbreakingLevel2 > 0)
	      {
	        this.mc.fontRendererObj.drawString("u" + unbreakingLevel2, x * 2, encY, 16777215);
	        encY += 8;
	      }
	    }
	    if ((stack.getItem() instanceof ItemSword))
	    {
	      int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, stack);
	      int knockbackLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180313_o.effectId, stack);
	      int fireAspectLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
	      int unbreakingLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
	      if (sharpnessLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("s" + sharpnessLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (knockbackLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("k" + knockbackLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (fireAspectLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("f" + fireAspectLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (unbreakingLevel3 > 0) {
	        this.mc.fontRendererObj.drawString("u" + unbreakingLevel3, x * 2, encY, 16777215);
	      }
	    }
	    if ((stack.getItem() instanceof ItemAxe))
	    {
	      int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, stack);
	      int knockbackLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180313_o.effectId, stack);
	      int fireAspectLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
	      int unbreakingLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
	      if (sharpnessLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("s" + sharpnessLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (knockbackLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("k" + knockbackLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (fireAspectLevel > 0)
	      {
	        this.mc.fontRendererObj.drawString("f" + fireAspectLevel, x * 2, encY, 16777215);
	        encY += 8;
	      }
	      if (unbreakingLevel3 > 0) {
	        this.mc.fontRendererObj.drawString("u" + unbreakingLevel3, x * 2, encY, 16777215);
	      }
	    }
	  }
	


	@Override
	public void onEnable() {
        EventRenderNameTag.cancel=true;
		super.onEnable();
	}

	@Override
	public void onDisable() {
        EventRenderNameTag.cancel=false;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return "Beta";
	}

}