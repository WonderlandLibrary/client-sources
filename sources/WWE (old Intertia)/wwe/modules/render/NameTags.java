package wwe.modules.render;

import java.text.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.client.renderer.color.*;
import org.lwjgl.opengl.*;
import java.util.*;
import wwe.modules.exploits.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.storage.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.text.*;
import net.minecraft.nbt.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import wwe.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.potion.*;
import net.minecraft.client.gui.*;

public class NameTags extends qMIe
{
    private DecimalFormat PxbF;
    private RenderItem HrbU;
    public BooleanValue health;
    public BooleanValue head;
    public BooleanValue potions;
    public BooleanValue ping;
    public BooleanValue durability;
    public BooleanValue stackSize;
    public NumberValue size;
    
    public NameTags() {
        super("Name Tags", "Makes better name tags for players", 0, Category.RENDER);
        this.PxbF = new DecimalFormat("#.#");
        this.HrbU = null;
    }
    
    @Override
    public void HrDr() {
        this.health = new BooleanValue("Health", this, "Renders the player's health", true);
        this.head = new BooleanValue("Head", this, "Renders the player's head", true);
        this.potions = new BooleanValue("Potions", this, "Renders the player's active potions", true);
        this.ping = new BooleanValue("Ping", this, "Renders the player's ping", false);
        this.durability = new BooleanValue("Durability", this, "Shows durability of an item", true);
        this.stackSize = new BooleanValue("Stack Size", this, "Shows the stack size of an item", false);
        this.size = new NumberValue("Size", this, "Size of the name tags", 0.5, 0.1, 1.0);
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        if (Wrapper.mc.world != null && this.HrbU == null) {
            try {
                this.HrbU = new RenderItem(Wrapper.mc.renderEngine, yYGD.Cisv(), (ItemColors)null);
                if (NameTags.\u2001\u200b) {
                    throw null;
                }
            }
            catch (NullPointerException ex) {}
        }
        for (final EntityPlayer entityPlayer : Wrapper.mc.world.playerEntities) {
            if (entityPlayer != Wrapper.mc.getRenderViewEntity() && entityPlayer.isEntityAlive()) {
                final double n = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * yYGD.XgOL().renderPartialTicks - yYGD.onRX();
                final double n2 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * yYGD.XgOL().renderPartialTicks - yYGD.UuJK();
                final double n3 = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * yYGD.XgOL().renderPartialTicks - yYGD.LkVR();
                if (!entityPlayer.getName().startsWith("Body #")) {
                    this.gzqM(entityPlayer, n, n2, n3);
                }
            }
            if (NameTags.\u2001\u200b) {
                throw null;
            }
        }
    }
    
    public void gzqM(final EntityPlayer entityPlayer, final double n, final double n2, final double n3) {
        GL11.glPushMatrix();
        final String wzxj = this.wzxj((EntityLivingBase)entityPlayer);
        if (Wrapper.mc.player.getDistance((Entity)entityPlayer) / 5.0 <= 2.0) {
            if (NameTags.\u2001\u200b) {
                throw null;
            }
        }
        final double n4 = 0.01666666753590107 * this.ACyO((EntityLivingBase)entityPlayer);
        GL11.glTranslated(n, n2 + 2.5, n3);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotated((double)(-yYGD.BqWL()), 0.0, 1.0, 0.0);
        GL11.glRotated((double)yYGD.uDFs(), 1.0, 0.0, 0.0);
        GL11.glScaled(-n4, -n4, n4);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GL11.glDisable(2929);
        final int n5 = Wrapper.fontRenderer.getStringWidth(wzxj) / 2;
        final int n6 = Wrapper.fontRenderer.getStringWidth(wzxj) / 4;
        if (this.health.OnGi()) {
            this.wZRC(entityPlayer, -40, 0);
        }
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        if (!entityPlayer.getHeldItemMainhand().isEmpty()) {
            list.add(entityPlayer.getHeldItemMainhand());
        }
        if (!entityPlayer.getHeldItemOffhand().isEmpty()) {
            list.add(entityPlayer.getHeldItemOffhand());
        }
        if (!((ItemStack)entityPlayer.inventory.armorInventory.get(0)).isEmpty()) {
            list.add((ItemStack)entityPlayer.inventory.armorInventory.get(0));
        }
        if (!((ItemStack)entityPlayer.inventory.armorInventory.get(1)).isEmpty()) {
            list.add((ItemStack)entityPlayer.inventory.armorInventory.get(1));
        }
        if (!((ItemStack)entityPlayer.inventory.armorInventory.get(2)).isEmpty()) {
            list.add((ItemStack)entityPlayer.inventory.armorInventory.get(2));
        }
        if (!((ItemStack)entityPlayer.inventory.armorInventory.get(3)).isEmpty()) {
            list.add((ItemStack)entityPlayer.inventory.armorInventory.get(3));
        }
        int n7 = 0;
        for (final ItemStack itemStack : list) {
            if (itemStack.getEnchantmentTagList() != null) {
                final int tagCount = itemStack.getEnchantmentTagList().tagCount();
                if (tagCount > n7) {
                    n7 = tagCount;
                }
            }
            if (NameTags.\u2001\u200b) {
                throw null;
            }
        }
        if (this.potions.OnGi()) {
            final int n8 = -13;
            final int hHoe = this.HHoe(entityPlayer);
            int n9;
            if (list.isEmpty()) {
                n9 = 20;
                if (NameTags.\u2001\u200b) {
                    throw null;
                }
            }
            else {
                n9 = -20 - n7 * 8;
            }
            this.lywF(entityPlayer, n8, hHoe + n9);
        }
        if (vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).IuiN() && vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).shulker.OnGi() && entityPlayer.getHeldItemMainhand().getItem() instanceof ItemShulkerBox) {
            GL11.glPushMatrix();
            GlStateManager.scale(0.5f, 0.5f, 0.0f);
            final ItemStack heldItemMainhand = entityPlayer.getHeldItemMainhand();
            final WorldClient world = Wrapper.mc.world;
            final int n10 = -254;
            int n11;
            if (this.head.OnGi()) {
                n11 = -30;
                if (NameTags.\u2001\u200b) {
                    throw null;
                }
            }
            else {
                n11 = 0;
            }
            FIwW.SitR(heldItemMainhand, (World)world, n10, n11 + this.HHoe(entityPlayer) - 50, false);
            GL11.glPopMatrix();
        }
        if (vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).IuiN() && vlwv.hNxW.qHPF((Class<Peek>)aKVA.class).map.OnGi() && entityPlayer.getHeldItemMainhand().getItem() instanceof ItemMap) {
            final MapData mapData = ((ItemMap)entityPlayer.getHeldItemMainhand().getItem()).getMapData(entityPlayer.getHeldItemMainhand(), (World)Wrapper.mc.world);
            final int n12 = -94;
            int n13;
            if (this.head.OnGi()) {
                n13 = -20;
                if (NameTags.\u2001\u200b) {
                    throw null;
                }
            }
            else {
                n13 = 0;
            }
            FIwW.rRds(mapData, n12, n13 + this.HHoe(entityPlayer) - 5, false);
        }
        if (this.head.OnGi()) {
            try {
                Wrapper.mc.getTextureManager().bindTexture(((AbstractClientPlayer)entityPlayer).getLocationSkin());
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final GuiIngame ingameGUI = Wrapper.mc.ingameGUI;
                GuiIngame.drawScaledCustomSizeModalRect(-73, -10, 8.0f, 8.0f, 8, 8, 30, 30, 64.0f, 64.0f);
                if (((AbstractClientPlayer)entityPlayer).isWearing(EnumPlayerModelParts.HAT)) {
                    final GuiIngame ingameGUI2 = Wrapper.mc.ingameGUI;
                    GuiIngame.drawScaledCustomSizeModalRect(-73, -10, 40.0f, 8.0f, 8, 8, 30, 30, 64.0f, 64.0f);
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                if (NameTags.\u2001\u200b) {
                    throw null;
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        rrxl(-n5 - 2, 10, n5 + 1, 20, 0.2f, 1610612736, 1610612736);
        Wrapper.fontRenderer.drawStringWithShadow(wzxj, (float)(-n5), 11.0f, this.wdUs((EntityLivingBase)entityPlayer));
        if (vlwv.xXAB(entityPlayer.getName())) {
            Wrapper.mc.renderEngine.bindTexture(new ResourceLocation("textures/wwe/wwelogo.png"));
            GlStateManager.pushMatrix();
            GL11.glScalef(0.04f, 0.04f, 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Wrapper.mc.ingameGUI.drawTexturedModalRect((-n5 - 13) * 25, 250, 0, 0, 256, 256);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
        int n14 = 0;
        final Iterator iterator2 = entityPlayer.inventory.armorInventory.iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next() != null) {
                n14 -= 8;
            }
            if (NameTags.\u2001\u200b) {
                throw null;
            }
        }
        if (entityPlayer.getHeldItemMainhand() != null) {
            n14 -= 8;
            final ItemStack copy = entityPlayer.getHeldItemMainhand().copy();
            if (copy.hasEffect() && (copy.getItem() instanceof ItemTool || copy.getItem() instanceof ItemArmor)) {
                copy.setCount(1);
            }
            this.IwxL(entityPlayer, copy, n14, this.HHoe(entityPlayer));
            n14 += 16;
        }
        int i = 3;
        while (i >= 0) {
            final ItemStack itemStack2 = (ItemStack)entityPlayer.inventory.armorInventory.get(i);
            if (itemStack2 != null) {
                final ItemStack copy2 = itemStack2.copy();
                if (copy2 == null) {
                    if (NameTags.\u2001\u200b) {
                        throw null;
                    }
                }
                else {
                    if (copy2.hasEffect() && (copy2.getItem() instanceof ItemTool || copy2.getItem() instanceof ItemArmor)) {
                        if (this.stackSize.OnGi()) {
                            if (NameTags.\u2001\u200b) {
                                throw null;
                            }
                        }
                        else {
                            copy2.setCount(1);
                        }
                    }
                    this.IwxL(entityPlayer, copy2, n14, this.HHoe(entityPlayer));
                    n14 += 16;
                }
            }
            --i;
            if (NameTags.\u2001\u200b) {
                throw null;
            }
        }
        if (entityPlayer.getHeldItemOffhand() != null) {
            n14 += 0;
            final ItemStack copy3 = entityPlayer.getHeldItemOffhand().copy();
            if (copy3.hasEffect() && (copy3.getItem() instanceof ItemTool || copy3.getItem() instanceof ItemArmor)) {
                if (this.stackSize.OnGi()) {
                    if (NameTags.\u2001\u200b) {
                        throw null;
                    }
                }
                else {
                    copy3.setCount(1);
                }
            }
            this.IwxL(entityPlayer, copy3, n14, this.HHoe(entityPlayer));
            n14 += 8;
        }
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public String BGGB(final int n) {
        if (n > 10) {
            return "a";
        }
        if (n > 5) {
            return "4";
        }
        return "4";
    }
    
    public int wdUs(final EntityLivingBase entityLivingBase) {
        if (vlwv.Zqnd().UsVJ(entityLivingBase.getName())) {
            return -16711681;
        }
        if (entityLivingBase.isInvisible()) {
            return -22016;
        }
        if (entityLivingBase.isSneaking()) {
            return -49088;
        }
        return -1;
    }
    
    public String wzxj(final EntityLivingBase entityLivingBase) {
        int responseTime;
        if (entityLivingBase.getUniqueID() == null) {
            responseTime = 0;
            if (NameTags.\u2001\u200b) {
                throw null;
            }
        }
        else if (Wrapper.mc.player.connection.getPlayerInfo(entityLivingBase.getUniqueID()) == null) {
            responseTime = 0;
            if (NameTags.\u2001\u200b) {
                throw null;
            }
        }
        else {
            responseTime = Wrapper.mc.player.connection.getPlayerInfo(entityLivingBase.getUniqueID()).getResponseTime();
        }
        final int n = responseTime;
        final StringBuilder append = new StringBuilder().append(entityLivingBase.getName());
        String string;
        if (this.ping.OnGi()) {
            string = " " + n + "ms";
            if (NameTags.\u2001\u200b) {
                throw null;
            }
        }
        else {
            string = "";
        }
        return append.append(string).toString();
    }
    
    public double ACyO(final EntityLivingBase entityLivingBase) {
        double doubleValue;
        if (Wrapper.mc.player.getDistance((Entity)entityLivingBase) <= 2.0f) {
            doubleValue = this.size.OnGi();
            if (NameTags.\u2001\u200b) {
                throw null;
            }
        }
        else {
            doubleValue = Wrapper.mc.player.getDistance((Entity)entityLivingBase) / 4.0f * this.size.OnGi();
        }
        return doubleValue;
    }
    
    public void IwxL(final EntityPlayer entityPlayer, final ItemStack itemStack, final int n, final int n2) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        Wrapper.mc.getRenderItem().zLevel = -100.0f;
        GlStateManager.scale(1.0f, 1.0f, 0.0f);
        Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2 / 2 - 12);
        if (this.durability.OnGi()) {
            Wrapper.mc.getRenderItem().renderItemOverlays(Wrapper.fontRenderer, itemStack, n, n2 / 2 - 12);
        }
        Wrapper.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        this.rwjt(entityPlayer, itemStack, n, n2 - 18);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    public void rwjt(final EntityPlayer entityPlayer, final ItemStack itemStack, final int n, final int n2) {
        int n3 = n2 - 24;
        int n4 = n3 + 5;
        if ((itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool) && this.durability.OnGi()) {
            Wrapper.fontRenderer.drawStringWithShadow(itemStack.getMaxDamage() - itemStack.getItemDamage() + "", (float)(n * 2 + 8), (float)(n2 + 26), -1);
        }
        final NBTTagList enchantmentTagList = itemStack.getEnchantmentTagList();
        if (enchantmentTagList != null) {
            int i = 0;
            while (i < enchantmentTagList.tagCount()) {
                final short short1 = enchantmentTagList.getCompoundTagAt(i).getShort("id");
                final short short2 = enchantmentTagList.getCompoundTagAt(i).getShort("lvl");
                final Enchantment enchantmentByID = Enchantment.getEnchantmentByID((int)short1);
                if (enchantmentByID != null) {
                    String s;
                    if (enchantmentByID.isCurse()) {
                        s = TextFormatting.RED + enchantmentByID.getTranslatedName((int)short2).substring(11).substring(0, 1).toLowerCase();
                        if (NameTags.\u2001\u200b) {
                            throw null;
                        }
                    }
                    else {
                        s = enchantmentByID.getTranslatedName((int)short2).substring(0, 1).toLowerCase();
                    }
                    final String string = s + short2;
                    GL11.glPushMatrix();
                    GL11.glScalef(0.9f, 0.9f, 0.0f);
                    Wrapper.fontRenderer.drawStringWithShadow(string, (float)(n * 2 + 13), (float)n4, -1);
                    GL11.glScalef(1.0f, 1.0f, 1.0f);
                    GL11.glPopMatrix();
                    n3 += 8;
                    n4 -= 10;
                }
                ++i;
                if (NameTags.\u2001\u200b) {
                    throw null;
                }
            }
        }
    }
    
    public void wZRC(final EntityPlayer entityPlayer, final int n, final int n2) {
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        Wrapper.mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/icons.png"));
        if (!(entityPlayer instanceof EntityPlayerSP)) {
            final int ceil = MathHelper.ceil(entityPlayer.getAbsorptionAmount());
            final float n3 = (float)entityPlayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
            final int max = Math.max(10 - (MathHelper.ceil((n3 + ceil) / 2.0f / 10.0f) - 2), 3);
            final int ceil2 = MathHelper.ceil(entityPlayer.getHealth());
            int n4 = ceil;
            final int n5 = -1;
            int i = MathHelper.ceil((n3 + ceil) / 2.0f) - 1;
            while (i >= 0) {
                int n6 = 16;
                if (entityPlayer.isPotionActive(MobEffects.POISON)) {
                    n6 += 36;
                    if (NameTags.\u2001\u200b) {
                        throw null;
                    }
                }
                else if (entityPlayer.isPotionActive(MobEffects.WITHER)) {
                    n6 += 72;
                }
                final int n7 = 0;
                final int n8 = MathHelper.ceil((i + 1) / 10.0f) - 1;
                final int n9 = n + i % 10 * 8;
                int n10 = n2 - n8 * max;
                if (n4 <= 0 && i == n5) {
                    n10 -= 2;
                }
                int n11 = 0;
                if (Wrapper.mc.world.getWorldInfo().isHardcoreModeEnabled()) {
                    n11 = 5;
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                wAtb(n9, n10, 16 + n7 * 9, 9 * n11, 9, 9);
                if (n4 > 0) {
                    if (n4 == ceil && ceil % 2 == 1) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        wAtb(n9, n10, n6 + 153, 9 * n11, 9, 9);
                        --n4;
                        if (NameTags.\u2001\u200b) {
                            throw null;
                        }
                    }
                    else {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        wAtb(n9, n10, n6 + 144, 9 * n11, 9, 9);
                        n4 -= 2;
                        if (NameTags.\u2001\u200b) {
                            throw null;
                        }
                    }
                }
                else {
                    if (i * 2 + 1 < ceil2) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        wAtb(n9, n10, n6 + 36, 9 * n11, 9, 9);
                    }
                    if (i * 2 + 1 == ceil2) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        wAtb(n9, n10, n6 + 45, 9 * n11, 9, 9);
                    }
                }
                --i;
                if (NameTags.\u2001\u200b) {
                    throw null;
                }
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
    
    public static void wAtb(final double n, final double n2, final int n3, final int n4, final int n5, final int n6) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(n + 0.0, n2 + n6, 0.0).tex((double)((n3 + 0) * 0.00390625f), (double)((n4 + n6) * 0.00390625f)).endVertex();
        buffer.pos(n + n5, n2 + n6, 0.0).tex((double)((n3 + n5) * 0.00390625f), (double)((n4 + n6) * 0.00390625f)).endVertex();
        buffer.pos(n + n5, n2 + 0.0, 0.0).tex((double)((n3 + n5) * 0.00390625f), (double)((n4 + 0) * 0.00390625f)).endVertex();
        buffer.pos(n + 0.0, n2 + 0.0, 0.0).tex((double)((n3 + 0) * 0.00390625f), (double)((n4 + 0) * 0.00390625f)).endVertex();
        instance.draw();
    }
    
    public int HHoe(final EntityPlayer entityPlayer) {
        final int n = -40;
        if (entityPlayer.getAbsorptionAmount() >= 1.0f && entityPlayer.getAbsorptionAmount() <= 20.0f) {
            return n - 15 + 20;
        }
        if (entityPlayer.getAbsorptionAmount() >= 20.0f && entityPlayer.getAbsorptionAmount() <= 40.0f) {
            return n - 15 - 15 + 20;
        }
        if (entityPlayer.getAbsorptionAmount() >= 40.0f && entityPlayer.getAbsorptionAmount() <= 60.0f) {
            return n - 15 - 15 - 15 + 20;
        }
        return n + 20;
    }
    
    public void lywF(final EntityPlayer entityPlayer, final int n, final int n2) {
        final ResourceLocation resourceLocation = new ResourceLocation("textures/gui/container/inventory.png");
        int n3 = 0;
        for (final PotionEffect potionEffect : entityPlayer.getActivePotionEffects()) {
            ++n3;
            final Potion potion = potionEffect.getPotion();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            Wrapper.mc.renderEngine.bindTexture(resourceLocation);
            final String potionDurationString = Potion.getPotionDurationString(potionEffect, 1.0f);
            if (potion.hasStatusIcon()) {
                final int statusIconIndex = potion.getStatusIconIndex();
                GlStateManager.pushMatrix();
                GL11.glScalef(0.5f, 0.5f, 0.0f);
                wAtb(n - 10, n2 - 10 - n3 * 20, 0 + statusIconIndex % 8 * 18, 198 + statusIconIndex / 8 * 18, 18, 18);
                Wrapper.fontRenderer.drawStringWithShadow(potionDurationString, (float)(n + 12), (float)(n2 - 4 - n3 * 20), potion.getLiquidColor());
                GL11.glPopMatrix();
                if (NameTags.\u2001\u200b) {
                    throw null;
                }
            }
            else {
                GlStateManager.pushMatrix();
                GL11.glScalef(0.5f, 0.5f, 0.0f);
                Wrapper.fontRenderer.drawStringWithShadow(potion.getName(), (float)(n - 20 - Wrapper.fontRenderer.getStringWidth(potion.getName()) / 2), (float)(n2 - 4 - n3 * 20), potion.getLiquidColor());
                Wrapper.fontRenderer.drawStringWithShadow(potionDurationString, (float)(n + 12), (float)(n2 - 4 - n3 * 20), potion.getLiquidColor());
                GL11.glPopMatrix();
            }
            if (NameTags.\u2001\u200b) {
                throw null;
            }
        }
    }
    
    public static void rrxl(final int n, final int n2, final int n3, final int n4, final float n5, final int n6, final int n7) {
        XbGa();
        Gui.drawRect(n + (int)n5, n2 + (int)n5, n3 - (int)n5, n4 - (int)n5, n6);
        Gui.drawRect(n + (int)n5, n2, n3 - (int)n5, n2 + (int)n5, n7);
        Gui.drawRect(n, n2, n + (int)n5, n4, n7);
        Gui.drawRect(n3 - (int)n5, n2, n3, n4, n7);
        Gui.drawRect(n + (int)n5, n4 - (int)n5, n3 - (int)n5, n4, n7);
        KAeW();
    }
    
    public static void XbGa() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }
    
    public static void KAeW() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }
}
