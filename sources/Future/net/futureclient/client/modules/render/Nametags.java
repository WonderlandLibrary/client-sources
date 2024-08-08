package net.futureclient.client.modules.render;

import net.minecraft.util.StringUtils;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.RenderHelper;
import net.futureclient.client.xG;
import net.minecraft.util.ResourceLocation;
import java.awt.Color;
import net.futureclient.client.dH;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import net.minecraft.init.Items;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.client.gui.FontRenderer;
import net.futureclient.client.KH;
import net.futureclient.client.pg;
import net.futureclient.client.gD;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.futureclient.client.modules.render.nametags.Listener2;
import net.futureclient.client.modules.render.nametags.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Nametags extends Ea
{
    private Value<Boolean> armor;
    private Value<Boolean> durability;
    private Value<Boolean> entityID;
    private Value<Boolean> futureCheck;
    private Value<Boolean> gameMode;
    private Value<Boolean> invisibles;
    public NumberValue scaling;
    private Value<Boolean> itemName;
    private Value<Boolean> ping;
    private Value<Boolean> health;
    
    public Nametags() {
        super("Nametags", new String[] { "Nametags", "anematags", "tags", "tag", "nt" }, true, -10333473, Category.RENDER);
        this.armor = new Value<Boolean>(true, new String[] { "Armor", "a" });
        this.durability = new Value<Boolean>(true, new String[] { "Durability", "Dura", "Durabil", "Dur" });
        this.itemName = new Value<Boolean>(true, new String[] { "ItemName", "ItemNames", "Items", "Itemname", "ItemID", "ItemIDs", "Ietm", "Names" });
        this.health = new Value<Boolean>(true, new String[] { "Health", "h" });
        this.invisibles = new Value<Boolean>(false, new String[] { "Invisibles", "Invis", "invisible", "Inv" });
        this.entityID = new Value<Boolean>(false, new String[] { "EntityID", "EntityID", "EID", "ID" });
        this.gameMode = new Value<Boolean>(false, new String[] { "GameMode", "Game", "Mode", "GM", "Creative", "Survival", "Spectator" });
        this.futureCheck = new Value<Boolean>(false, new String[] { "FutureCheck", "OnTheClient", "Fcheck" });
        this.ping = new Value<Boolean>(false, new String[] { "Ping", "Response", "ResponseTime", "Peng" });
        this.scaling = new NumberValue(0.003f, 0.001f, 0.01f, 1.748524532E-314, new String[] { "Scaling", "scale", "s" });
        this.M(new Value[] { this.armor, this.durability, this.itemName, this.health, this.scaling, this.invisibles, this.entityID, this.gameMode, this.futureCheck, this.ping });
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    private void b(final ItemStack itemStack, final int n, final int n2) {
        final float n3 = 0.5f;
        final float n4 = 0.5f;
        GlStateManager.scale(n4, n3, n4);
        GlStateManager.disableDepth();
        final String displayName = itemStack.getDisplayName();
        final gD gd;
        if ((gd = (gD)pg.M().M().M((Class)gD.class)) != null && (boolean)gd.E.M()) {
            GlStateManager.enableBlend();
            final gD gd2 = gd;
            final KH p3 = gd2.p;
            final KH p4 = gd2.p;
            final String s = displayName;
            p3.M(s, -p4.M(s) / 2, (double)n2, -1);
            GlStateManager.disableBlend();
        }
        else {
            final FontRenderer fontRenderer = Nametags.D.fontRenderer;
            final FontRenderer fontRenderer2 = Nametags.D.fontRenderer;
            final String s2 = displayName;
            fontRenderer.drawStringWithShadow(s2, (float)(-fontRenderer2.getStringWidth(s2) / 2), (float)n2, -1);
        }
        GlStateManager.enableDepth();
        final float n5 = 2.0f;
        final int n6 = 2;
        GlStateManager.scale((float)n6, n5, (float)n6);
    }
    
    private void e(final ItemStack itemStack, final int n, int n2) {
        n2 = n2;
        final int n3 = -1;
        final gD gd = (gD)pg.M().M().M((Class)gD.class);
        final Iterator<Enchantment> iterator2;
        Iterator<Enchantment> iterator = iterator2 = EnchantmentHelper.getEnchantments(itemStack).keySet().iterator();
        while (iterator.hasNext()) {
            final Enchantment enchantment;
            if ((enchantment = iterator2.next()) == null) {
                iterator = iterator2;
            }
            else {
                if (gd.E.M()) {
                    GlStateManager.enableBlend();
                    final KH p3 = gd.p;
                    final Enchantment enchantment2 = enchantment;
                    p3.M(this.M(enchantment2, EnchantmentHelper.getEnchantmentLevel(enchantment2, itemStack)), n * 2, (double)n2, n3);
                    GlStateManager.disableBlend();
                }
                else {
                    final FontRenderer fontRenderer = Nametags.D.fontRenderer;
                    final Enchantment enchantment3 = enchantment;
                    fontRenderer.drawStringWithShadow(this.M(enchantment3, EnchantmentHelper.getEnchantmentLevel(enchantment3, itemStack)), (float)(n * 2), (float)n2, n3);
                }
                n2 += 8;
                iterator = iterator2;
            }
        }
        if (itemStack.getItem().equals(Items.GOLDEN_APPLE) && itemStack.hasEffect()) {
            if (gd.E.M()) {
                GlStateManager.enableBlend();
                gd.p.M("God", n * 2, (double)n2, -3977919);
                GlStateManager.disableBlend();
                return;
            }
            Nametags.D.fontRenderer.drawStringWithShadow("God", (float)(n * 2), (float)n2, -3977919);
        }
    }
    
    public static void M(final Nametags nametags, final EntityPlayer entityPlayer, final double n, final double n2, final double n3) {
        nametags.M(entityPlayer, n, n2, n3);
    }
    
    private void M(final ItemStack itemStack, final int n, final int n2) {
        final gD gd = (gD)pg.M().M().M((Class)gD.class);
        final int maxDamage = itemStack.getMaxDamage();
        final float n3 = (maxDamage - itemStack.getItemDamage()) / (float)maxDamage;
        final Color e = new dH(n3 * 120.0f, 100.0f, 50.0f, 1.0f).e();
        final float n4 = 0.5f;
        final float n5 = 0.5f;
        GlStateManager.scale(n5, n4, n5);
        GlStateManager.disableDepth();
        if (gd.E.M()) {
            GlStateManager.enableBlend();
            gd.p.M(new StringBuilder().insert(0, String.valueOf((int)(n3 * 100.0f))).append('%').toString(), n * 2, (double)n2, e.getRGB());
            GlStateManager.disableBlend();
        }
        else {
            Nametags.D.fontRenderer.drawStringWithShadow(new StringBuilder().insert(0, String.valueOf((int)(n3 * 100.0f))).append('%').toString(), (float)(n * 2), (float)n2, e.getRGB());
        }
        GlStateManager.enableDepth();
        final float n6 = 2.0f;
        final int n7 = 2;
        GlStateManager.scale((float)n7, n6, (float)n7);
    }
    
    private String M(final Enchantment enchantment, final int n) {
        final ResourceLocation resourceLocation;
        String substring = ((resourceLocation = (ResourceLocation)Enchantment.REGISTRY.getNameForObject((Object)enchantment)) == null) ? enchantment.getName() : resourceLocation.toString();
        final int n2 = (n > 1) ? 12 : 13;
        if (substring.length() > n2) {
            substring = substring.substring(10, n2);
        }
        final StringBuilder sb = new StringBuilder();
        final String s = substring;
        final int n3 = 0;
        String s2 = sb.insert(n3, s.substring(n3, 1).toUpperCase()).append(substring.substring(1)).toString();
        if (n > 1) {
            s2 = new StringBuilder().insert(0, s2).append(n).toString();
        }
        return s2;
    }
    
    private void M(final EntityPlayer entityPlayer, final double n, double distance, final double n2) {
        final double n3 = distance + (entityPlayer.isSneaking() ? 0.0 : 8.48798316E-315);
        final Entity entity2;
        final Entity entity = entity2 = (Entity)((Nametags.D.getRenderViewEntity() == null) ? Nametags.D.player : Nametags.D.getRenderViewEntity());
        final double posX = entity2.posX;
        final double posY = entity2.posY;
        final double posZ = entity2.posZ;
        final Vec3d m;
        entity2.posX = (m = xG.M(entity2)).x;
        entity2.posY = m.y;
        entity2.posZ = m.z;
        final gD gd2;
        final gD gd = gd2 = (gD)pg.M().M().M((Class)gD.class);
        distance = entity.getDistance(n, distance, n2);
        final int n4 = Nametags.D.fontRenderer.getStringWidth(this.M(entityPlayer)) / 2;
        final int n5 = gd.p.M(this.M(entityPlayer)) / 2;
        double n6 = 6.00949208E-315 + this.scaling.B().floatValue() * distance;
        if (distance <= 0.0) {
            n6 = 3.56495293E-315;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)n, (float)n3 + 1.4f, (float)n2);
        final float n7 = -Nametags.D.getRenderManager().playerViewY;
        final float n8 = 1.0f;
        final float n9 = 0.0f;
        GlStateManager.rotate(n7, n9, n8, n9);
        GlStateManager.rotate(Nametags.D.getRenderManager().playerViewX, (Nametags.D.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, (float)0);
        GlStateManager.scale(-n6, -n6, n6);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        EntityPlayer entityPlayer2;
        if (gd2.E.M()) {
            GlStateManager.enableBlend();
            xG.M((float)(-n5 - 1), (float)(-gd2.p.M()), (float)(n5 + 2), 1.0f, 1.8f, 1426064384, 855638016);
            GlStateManager.disableBlend();
            final KH p4 = gd2.p;
            entityPlayer2 = entityPlayer;
            p4.M(this.M(entityPlayer), -n5, (double)(-(gd2.p.M() - 1)), this.M(entityPlayer));
        }
        else {
            GlStateManager.enableBlend();
            xG.M((float)(-n4 - 1), (float)(-Nametags.D.fontRenderer.FONT_HEIGHT), (float)(n4 + 2), 1.0f, 1.8f, 1426064384, 855638016);
            GlStateManager.disableBlend();
            Nametags.D.fontRenderer.drawStringWithShadow(this.M(entityPlayer), (float)(-n4), (float)(-(Nametags.D.fontRenderer.FONT_HEIGHT - 1)), this.M(entityPlayer));
            entityPlayer2 = entityPlayer;
        }
        final ItemStack heldItemMainhand = entityPlayer2.getHeldItemMainhand();
        final ItemStack heldItemOffhand = entityPlayer.getHeldItemOffhand();
        int n10 = 0;
        int n11 = 0;
        boolean b = false;
        GlStateManager.pushMatrix();
        int i = 3;
        int n12 = 3;
        while (i >= 0) {
            final ItemStack itemStack;
            if (!(itemStack = (ItemStack)entityPlayer.inventory.armorInventory.get(n12)).isEmpty()) {
                final Boolean j = this.durability.M();
                n10 -= 8;
                if (j) {
                    b = true;
                }
                final int size;
                if (this.armor.M() && (size = EnchantmentHelper.getEnchantments(itemStack).size()) > n11) {
                    n11 = size;
                }
            }
            i = --n12;
        }
        if (!heldItemOffhand.isEmpty() && (this.armor.M() || (this.durability.M() && heldItemOffhand.isItemStackDamageable()))) {
            n10 -= 8;
            if (this.durability.M() && heldItemOffhand.isItemStackDamageable()) {
                b = true;
            }
            final int size2;
            if (this.armor.M() && (size2 = EnchantmentHelper.getEnchantments(heldItemOffhand).size()) > n11) {
                n11 = size2;
            }
        }
        if (!heldItemMainhand.isEmpty()) {
            final int size3;
            if (this.armor.M() && (size3 = EnchantmentHelper.getEnchantments(heldItemMainhand).size()) > n11) {
                n11 = size3;
            }
            int k = this.M(n11);
            if (this.armor.M() || (this.durability.M() && heldItemMainhand.isItemStackDamageable())) {
                n10 -= 8;
            }
            if (this.armor.M()) {
                final ItemStack itemStack2 = heldItemMainhand;
                final int n13 = n10;
                final int n14 = k;
                k -= 32;
                this.M(itemStack2, n13, n14, n11);
            }
            Nametags nametags;
            if (this.durability.M() && heldItemMainhand.isItemStackDamageable()) {
                final int n15 = k;
                this.M(heldItemMainhand, n10, k);
                k = n15 - (gd2.E.M() ? gd2.p.M() : Nametags.D.fontRenderer.FONT_HEIGHT);
                nametags = this;
            }
            else {
                if (b) {
                    k -= (gd2.E.M() ? gd2.p.M() : Nametags.D.fontRenderer.FONT_HEIGHT);
                }
                nametags = this;
            }
            if (nametags.itemName.M()) {
                this.b(heldItemMainhand, n10, k);
            }
            if (this.armor.M() || (this.durability.M() && heldItemMainhand.isItemStackDamageable())) {
                n10 += 16;
            }
        }
        int l = 3;
        int n16 = 3;
        while (l >= 0) {
            final ItemStack itemStack3;
            if (!(itemStack3 = (ItemStack)entityPlayer.inventory.armorInventory.get(n16)).isEmpty()) {
                int m2 = this.M(n11);
                if (this.armor.M()) {
                    final ItemStack itemStack4 = itemStack3;
                    final int n17 = n10;
                    final int n18 = m2;
                    m2 -= 32;
                    this.M(itemStack4, n17, n18, n11);
                }
                if (this.durability.M() && itemStack3.isItemStackDamageable()) {
                    this.M(itemStack3, n10, m2);
                }
                n10 += 16;
            }
            l = --n16;
        }
        if (!heldItemOffhand.isEmpty()) {
            int m3 = this.M(n11);
            if (this.armor.M()) {
                final ItemStack itemStack5 = heldItemOffhand;
                final int n19 = n10;
                final int n20 = m3;
                m3 -= 32;
                this.M(itemStack5, n19, n20, n11);
            }
            if (this.durability.M() && heldItemOffhand.isItemStackDamageable()) {
                this.M(heldItemOffhand, n10, m3);
            }
            n10 += 16;
        }
        GlStateManager.popMatrix();
        final float n21 = 1.0f;
        final double posZ2 = posZ;
        final Entity entity3 = entity;
        final double posY2 = posY;
        entity.posX = posX;
        entity3.posY = posY2;
        entity3.posZ = posZ2;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(n21, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    private void M(final ItemStack itemStack, final int n, final int n2, final int n3) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        Nametags.D.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        final int n4 = (n3 > 4) ? ((n3 - 4) * 8 / 2) : 0;
        Nametags.D.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2 + n4);
        Nametags.D.getRenderItem().renderItemOverlays(Nametags.D.fontRenderer, itemStack, n, n2 + n4);
        Nametags.D.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        final float n5 = 0.5f;
        final float n6 = 0.5f;
        GlStateManager.scale(n6, n5, n6);
        GlStateManager.disableDepth();
        this.e(itemStack, n, n2 - 24);
        GlStateManager.enableDepth();
        final float n7 = 2.0f;
        final int n8 = 2;
        GlStateManager.scale((float)n8, n7, (float)n8);
        GlStateManager.popMatrix();
    }
    
    private int M(final EntityPlayer entityPlayer) {
        int n = -1;
        if (pg.M().M().M(entityPlayer.getName())) {
            return -10027009;
        }
        if (entityPlayer.isInvisible()) {
            return -56064;
        }
        if (Nametags.D.getConnection() != null && Nametags.D.getConnection().getPlayerInfo(entityPlayer.getUniqueID()) == null) {
            return -1113785;
        }
        if (entityPlayer.isSneaking()) {
            n = 16750848;
        }
        return n;
    }
    
    public static Value M(final Nametags nametags) {
        return nametags.invisibles;
    }
    
    private String M(final EntityPlayer entityPlayer) {
        String s = entityPlayer.getDisplayName().getFormattedText();
        if (pg.M().M().M(entityPlayer.getName())) {
            s = pg.M().M().M(entityPlayer.getName()).e();
        }
        if (this.entityID.M()) {
            s = new StringBuilder().insert(0, s).append(" ID: ").append(entityPlayer.getEntityId()).toString();
        }
        Nametags nametags = null;
        Label_0195: {
            if (this.gameMode.M()) {
                if (entityPlayer.isCreative()) {
                    s = new StringBuilder().insert(0, s).append(" [C]").toString();
                    nametags = this;
                    break Label_0195;
                }
                if (entityPlayer.isSpectator()) {
                    s = new StringBuilder().insert(0, s).append(" [I]").toString();
                    nametags = this;
                    break Label_0195;
                }
                s = new StringBuilder().insert(0, s).append(" [S]").toString();
            }
            nametags = this;
        }
        if (nametags.futureCheck.M() && pg.M().M().M(StringUtils.stripControlCodes(entityPlayer.getName())) && !entityPlayer.getName().equalsIgnoreCase(Nametags.D.player.getName())) {
            s = new StringBuilder().insert(0, s).append(" [F]").toString();
        }
        if (this.ping.M() && Nametags.D.getConnection() != null && Nametags.D.getConnection().getPlayerInfo(entityPlayer.getUniqueID()) != null) {
            s = new StringBuilder().insert(0, s).append(" ").append(Nametags.D.getConnection().getPlayerInfo(entityPlayer.getUniqueID()).getResponseTime()).append("ms").toString();
        }
        if (!this.health.M()) {
            return s;
        }
        final double ceil;
        String s2;
        if ((ceil = Math.ceil(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount())) > 0.0) {
            s2 = "§a";
        }
        else if (ceil > 0.0) {
            s2 = "§2";
        }
        else if (ceil > 0.0) {
            s2 = "§e";
        }
        else if (ceil > 0.0) {
            s2 = "§6";
        }
        else if (ceil > 0.0) {
            s2 = "§c";
        }
        else {
            s2 = "§4";
        }
        return new StringBuilder().insert(0, s).append(s2).append(" ").append((ceil > 0.0) ? Integer.valueOf((int)ceil) : "0").toString();
    }
    
    private int M(final int n) {
        int n2 = this.armor.M() ? -26 : -27;
        if (n > 4) {
            n2 -= (n - 4) * 8;
        }
        return n2;
    }
}
