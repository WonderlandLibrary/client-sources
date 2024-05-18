package wwe.modules.combat;

import net.minecraft.client.network.*;
import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import java.awt.*;
import net.minecraft.client.*;
import com.mojang.authlib.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.player.*;
import wwe.modules.render.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import wwe.*;
import net.minecraft.client.gui.*;
import com.google.common.collect.*;

public class LogOutSpot extends qMIe
{
    static ArrayList<NetworkPlayerInfo> yFHA;
    static int XcYd;
    boolean KSGk;
    public static ArrayList<String> vMez;
    public static ArrayList<String> rmoi;
    private static ArrayList hKId;
    static String[] czIo;
    public static List<YEmw> zEsy;
    public ModeValue mode;
    public BooleanValue chat;
    public BooleanValue esp;
    public BooleanValue nameTag;
    public NumberValue nameTagSize;
    public BooleanValue nameTagHealth;
    public BooleanValue nameTagHead;
    
    public LogOutSpot() {
        super("Log Out Spot", "Types in chat where a player logouts", 0, Category.COMBAT);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Cache");
        list.add("Tab");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Cache", list);
        this.chat = new BooleanValue("Chat", this, "Send a chat message when some one logs out", true);
        this.esp = new BooleanValue("ESP", this, "Render a esp when some one logged out", true);
        this.nameTag = new BooleanValue("Name Tag", this, "Render a name tag when some one logged out", true);
        this.nameTagSize = new NumberValue("Name Tag Size", this, "Size of the name tag", 0.5, 0.1, 1.0);
        this.nameTagHealth = new BooleanValue("Name Tag Health", this, "Render a name tag when some one logged out", true);
        this.nameTagHead = new BooleanValue("Name Tag Head", this, "Render a name tag when some one logged out", true);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Cache")) {
            this.owgW("Log Out Spot" + TextFormatting.DARK_GRAY + " Cache");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Tab")) {
            this.owgW("Log Out Spot" + TextFormatting.DARK_GRAY + " Tab");
        }
        if (Wrapper.mc.player != null) {
            if (Wrapper.mc.player.ticksExisted % 10 == 0) {
                if (this.mode.OnGi().equalsIgnoreCase("Cache")) {
                    this.leLj();
                }
                this.xbry();
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
            }
            else if (Wrapper.mc.isSingleplayer()) {
                this.GDVp();
            }
        }
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        if (this.esp.OnGi()) {
            for (final YEmw yEmw : LogOutSpot.zEsy) {
                this.YdDJ(yEmw);
                final double n = yEmw.Yhqv - 0.25;
                final double eqev = yEmw.EQEV;
                final double n2 = yEmw.YdCe - 0.25;
                final double n3 = yEmw.Yhqv + 0.25;
                final double n4 = yEmw.EQEV + 2.0;
                final double n5 = yEmw.YdCe + 0.25;
                Color color;
                if (vlwv.Zqnd().UsVJ(yEmw.AAtC)) {
                    color = JIfG.JzFV(0xCC95F7606F75DD1BL ^ 0xCC95F7606F75DD1AL, 1.0f);
                    if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                        throw null;
                    }
                }
                else {
                    color = Color.red;
                }
                BIIV.yvCF(n, eqev, n2, n3, n4, n5, color);
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
            }
        }
    }
    
    public void leLj() {
        final ArrayList<Object> list = new ArrayList<Object>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
        if (list.size() != LogOutSpot.XcYd) {
            final ArrayList list2 = (ArrayList)list.clone();
            list2.removeAll(LogOutSpot.yFHA);
            if (list2.size() > 5) {
                LogOutSpot.XcYd = LogOutSpot.yFHA.size();
                this.guhH();
                return;
            }
            final ArrayList list3 = (ArrayList)LogOutSpot.yFHA.clone();
            list3.removeAll(list);
            final Iterator<NetworkPlayerInfo> iterator = list3.iterator();
            while (iterator.hasNext()) {
                this.ZhER(iterator.next().getGameProfile());
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
            }
            final Iterator<NetworkPlayerInfo> iterator2 = list2.iterator();
            while (iterator2.hasNext()) {
                this.zNDT(iterator2.next().getGameProfile());
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
            }
            LogOutSpot.XcYd = LogOutSpot.yFHA.size();
            this.guhH();
        }
    }
    
    private void guhH() {
        LogOutSpot.yFHA = new ArrayList<NetworkPlayerInfo>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
        LogOutSpot.XcYd = LogOutSpot.yFHA.size();
        this.KSGk = true;
    }
    
    public void zNDT(final GameProfile gameProfile) {
        int i = 0;
        while (i < LogOutSpot.zEsy.size()) {
            if ((LogOutSpot.zEsy.get(i).AAtC.equalsIgnoreCase(gameProfile.getName()) || LogOutSpot.zEsy.get(i).TZqL.equals(gameProfile.getId())) && !LogOutSpot.zEsy.get(i).AAtC.equals(Wrapper.mc.player.getName())) {
                if (this.chat.OnGi()) {
                    HAxG.TuzD(LogOutSpot.zEsy.get(i).AAtC + " has logged back in, removing log out spot");
                }
                LogOutSpot.zEsy.remove(i);
                --i;
            }
            ++i;
            if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                throw null;
            }
        }
    }
    
    public void ZhER(final GameProfile gameProfile) {
        for (final EntityPlayer entityPlayer : LogOutSpot.hKId) {
            if ((entityPlayer.getName().equalsIgnoreCase(gameProfile.getName()) || entityPlayer.getGameProfile().getId().equals(gameProfile.getId())) && !entityPlayer.getName().equals(Wrapper.mc.player.getName())) {
                if (this.chat.OnGi()) {
                    final StringBuilder sb = new StringBuilder();
                    String s;
                    if (gameProfile.getName() == null) {
                        s = entityPlayer.getName();
                        if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                            throw null;
                        }
                    }
                    else {
                        s = gameProfile.getName();
                    }
                    HAxG.TuzD(sb.append(s).append(" has logged out at, X: ").append(TextFormatting.DARK_RED).append(entityPlayer.getPosition().getX()).append(TextFormatting.DARK_RED).append(" Y: ").append(TextFormatting.DARK_RED).append(entityPlayer.getPosition().getY()).append(TextFormatting.DARK_RED).append(" Z: ").append(TextFormatting.DARK_RED).append(entityPlayer.getPosition().getZ()).toString());
                }
                LogOutSpot.zEsy.add(new YEmw(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, entityPlayer.getName(), entityPlayer.getGameProfile().getId(), entityPlayer, new Date()));
            }
            if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                throw null;
            }
        }
    }
    
    public void xbry() {
        LogOutSpot.hKId.clear();
        final Iterator<EntityPlayer> iterator = Wrapper.mc.world.playerEntities.iterator();
        while (iterator.hasNext()) {
            LogOutSpot.hKId.add(iterator.next());
            if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                throw null;
            }
        }
    }
    
    @oqrr
    public void DwMG(final LqJY lqJY) {
        if (this.mode.OnGi().equalsIgnoreCase("Tab") && lqJY.MYlK() instanceof SPacketPlayerListItem) {
            final SPacketPlayerListItem sPacketPlayerListItem = (SPacketPlayerListItem)lqJY.MYlK();
            for (final SPacketPlayerListItem.AddPlayerData addPlayerData : sPacketPlayerListItem.getEntries()) {
                if (sPacketPlayerListItem.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                    this.zNDT(addPlayerData.getProfile());
                }
                if (sPacketPlayerListItem.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                    this.ZhER(addPlayerData.getProfile());
                }
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        if (Wrapper.mc.world != null && Wrapper.mc.player != null && Wrapper.mc.getConnection() != null) {
            this.guhH();
            this.xbry();
        }
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        this.xbry();
        super.qBmy();
    }
    
    public void YdDJ(final YEmw yEmw) {
        GL11.glPushMatrix();
        if (Wrapper.mc.player.getDistance(yEmw.Yhqv, yEmw.EQEV, yEmw.YdCe) / 5.0 <= 2.0) {
            if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                throw null;
            }
        }
        final double n = 0.016666668f * this.ErHT(yEmw);
        GL11.glTranslated(yEmw.Yhqv - yYGD.onRX(), yEmw.EQEV - yYGD.UuJK() + 2.5, yEmw.YdCe - yYGD.LkVR());
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-yYGD.BqWL(), 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(yYGD.uDFs(), 1.0f, 0.0f, 0.0f);
        GL11.glScaled(-n, -n, n);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GL11.glDisable(2929);
        final int n2 = Wrapper.fontRenderer.getStringWidth(yEmw.AAtC) / 2;
        final int n3 = Wrapper.fontRenderer.getStringWidth(yEmw.AAtC) / 4;
        if (yEmw.egIB != null && this.nameTagHealth.OnGi()) {
            this.wZRC(yEmw.egIB, -40, 0);
        }
        if (yEmw.egIB != null && this.nameTagHead.OnGi()) {
            try {
                Wrapper.mc.getTextureManager().bindTexture(((AbstractClientPlayer)yEmw.egIB).getLocationSkin());
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final GuiIngame ingameGUI = Wrapper.mc.ingameGUI;
                GuiIngame.drawScaledCustomSizeModalRect(-73, -10, 8.0f, 8.0f, 8, 8, 30, 30, 64.0f, 64.0f);
                if (((AbstractClientPlayer)yEmw.egIB).isWearing(EnumPlayerModelParts.HAT)) {
                    final GuiIngame ingameGUI2 = Wrapper.mc.ingameGUI;
                    GuiIngame.drawScaledCustomSizeModalRect(-73, -10, 40.0f, 8.0f, 8, 8, 30, 30, 64.0f, 64.0f);
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        NameTags.rrxl(-n2 - 2, 10, n2 + 1, 20, 0.2f, 1610612736, 1610612736);
        final FontRenderer fontRenderer = Wrapper.fontRenderer;
        final String aAtC = yEmw.AAtC;
        final float n4 = (float)(-n2);
        final float n5 = 11.0f;
        int n6;
        if (vlwv.Zqnd().UsVJ(yEmw.AAtC)) {
            n6 = -16711681;
            if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                throw null;
            }
        }
        else {
            n6 = -1;
        }
        fontRenderer.drawStringWithShadow(aAtC, n4, n5, n6);
        if (yEmw.egIB != null && vlwv.xXAB(yEmw.egIB.getName())) {
            Wrapper.mc.renderEngine.bindTexture(new ResourceLocation("textures/wwe/wwelogo.png"));
            GlStateManager.pushMatrix();
            GL11.glScalef(0.04f, 0.04f, 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Wrapper.mc.ingameGUI.drawTexturedModalRect((-n2 - 13) * 25, 250, 0, 0, 256, 256);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
        if (yEmw.egIB != null) {
            int n7 = 0;
            final Iterator iterator = yEmw.egIB.inventory.armorInventory.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() != null) {
                    n7 -= 8;
                }
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
            }
            if (yEmw.egIB.getHeldItemMainhand() != null) {
                n7 -= 8;
                final ItemStack copy = yEmw.egIB.getHeldItemMainhand().copy();
                if (copy.hasEffect() && (copy.getItem() instanceof ItemTool || copy.getItem() instanceof ItemArmor)) {
                    copy.setCount(1);
                }
                this.IwxL(yEmw.egIB, copy, n7, this.HHoe(yEmw.egIB));
                n7 += 16;
            }
            int i = 3;
            while (i >= 0) {
                final ItemStack itemStack = (ItemStack)yEmw.egIB.inventory.armorInventory.get(i);
                if (itemStack != null) {
                    final ItemStack copy2 = itemStack.copy();
                    if (copy2 == null) {
                        if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                            throw null;
                        }
                    }
                    else {
                        this.IwxL(yEmw.egIB, copy2, n7, this.HHoe(yEmw.egIB));
                        n7 += 16;
                    }
                }
                --i;
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
            }
            if (yEmw.egIB.getHeldItemOffhand() != null) {
                n7 += 0;
                this.IwxL(yEmw.egIB, yEmw.egIB.getHeldItemOffhand().copy(), n7, this.HHoe(yEmw.egIB));
                n7 += 8;
            }
        }
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public float ErHT(final YEmw yEmw) {
        float floatValue;
        if (Wrapper.mc.player.getDistance(yEmw.Yhqv, yEmw.EQEV, yEmw.YdCe) <= 2.0) {
            floatValue = this.nameTagSize.OnGi().floatValue();
            if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                throw null;
            }
        }
        else {
            floatValue = (float)(Wrapper.mc.player.getDistance(yEmw.Yhqv, yEmw.EQEV, yEmw.YdCe) / 4.0) * this.nameTagSize.OnGi().floatValue();
        }
        return floatValue;
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
        Wrapper.mc.getRenderItem().renderItemOverlays(Wrapper.fontRenderer, itemStack, n, n2 / 2 - 12);
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
        if (itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool) {
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
                        if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
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
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
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
                    if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
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
                this.dOfv(n9, n10, 16 + n7 * 9, 9 * n11, 9, 9);
                if (n4 > 0) {
                    if (n4 == ceil && ceil % 2 == 1) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        this.dOfv(n9, n10, n6 + 153, 9 * n11, 9, 9);
                        --n4;
                        if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                            throw null;
                        }
                    }
                    else {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        this.dOfv(n9, n10, n6 + 144, 9 * n11, 9, 9);
                        n4 -= 2;
                        if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                            throw null;
                        }
                    }
                }
                else {
                    if (i * 2 + 1 < ceil2) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        this.dOfv(n9, n10, n6 + 36, 9 * n11, 9, 9);
                    }
                    if (i * 2 + 1 == ceil2) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        this.dOfv(n9, n10, n6 + 45, 9 * n11, 9, 9);
                    }
                }
                --i;
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
            }
        }
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
    
    public void dOfv(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)(n + 0), (double)(n2 + n6), 0.0).tex((double)((n3 + 0) * 0.00390625f), (double)((n4 + n6) * 0.00390625f)).endVertex();
        buffer.pos((double)(n + n5), (double)(n2 + n6), 0.0).tex((double)((n3 + n5) * 0.00390625f), (double)((n4 + n6) * 0.00390625f)).endVertex();
        buffer.pos((double)(n + n5), (double)(n2 + 0), 0.0).tex((double)((n3 + n5) * 0.00390625f), (double)((n4 + 0) * 0.00390625f)).endVertex();
        buffer.pos((double)(n + 0), (double)(n2 + 0), 0.0).tex((double)((n3 + 0) * 0.00390625f), (double)((n4 + 0) * 0.00390625f)).endVertex();
        instance.draw();
    }
    
    @oqrr
    public void Ewag(final CTFs.wwe/BnvE wwe/BnvE) {
        for (final YEmw yEmw : LogOutSpot.zEsy) {
            final BlockPos blockPos = new BlockPos(yEmw.Yhqv, yEmw.EQEV, yEmw.YdCe);
            if (wwe/BnvE.phME.getX() == blockPos.getX() && wwe/BnvE.phME.getY() == blockPos.getY() - 1 && wwe/BnvE.phME.getZ() == blockPos.getZ()) {
                Wrapper.mc.displayGuiScreen((GuiScreen)new sSoG(yEmw));
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
                break;
            }
            else {
                if (LogOutSpot.\u200b\u2000\u200f\u2001\u2004\u2005\u2007\u2004) {
                    throw null;
                }
                continue;
            }
        }
    }
    
    static {
        LogOutSpot.yFHA = new ArrayList<NetworkPlayerInfo>();
        LogOutSpot.vMez = new ArrayList<String>();
        LogOutSpot.rmoi = new ArrayList<String>();
        LogOutSpot.hKId = new ArrayList();
        LogOutSpot.zEsy = (List<YEmw>)Lists.newArrayList();
    }
}
