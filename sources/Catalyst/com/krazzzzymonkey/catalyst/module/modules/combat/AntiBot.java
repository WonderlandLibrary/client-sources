package com.krazzzzymonkey.catalyst.module.modules.combat;

import java.util.Iterator;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import com.krazzzzymonkey.catalyst.utils.system.Connection;
import net.minecraft.client.network.NetworkPlayerInfo;
import com.krazzzzymonkey.catalyst.utils.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.value.NumberValue;
import com.krazzzzymonkey.catalyst.utils.EntityBot;
import java.util.ArrayList;
import com.krazzzzymonkey.catalyst.value.BooleanValue;
import com.krazzzzymonkey.catalyst.module.Modules;

public class AntiBot extends Modules
{
    public /* synthetic */ BooleanValue ifEntityId;
    public /* synthetic */ BooleanValue remove;
    public static /* synthetic */ ArrayList<EntityBot> bots;
    public /* synthetic */ BooleanValue ifInAir;
    public /* synthetic */ BooleanValue ifPing;
    public /* synthetic */ NumberValue level;
    public /* synthetic */ BooleanValue ifTabName;
    private static final /* synthetic */ String[] llIIllI;
    public /* synthetic */ BooleanValue ifInvisible;
    public /* synthetic */ NumberValue tick;
    public /* synthetic */ BooleanValue ifZeroHealth;
    public /* synthetic */ BooleanValue gwen;
    private static final /* synthetic */ int[] llIlllI;
    public /* synthetic */ BooleanValue ifGround;
    
    private static String lIIllllll(String llIlIlIlIIIllll, final String llIlIlIlIIIlllI) {
        llIlIlIlIIIllll = new String(Base64.getDecoder().decode(llIlIlIlIIIllll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIlIlIlIIlIIlI = new StringBuilder();
        final char[] llIlIlIlIIlIIIl = llIlIlIlIIIlllI.toCharArray();
        int llIlIlIlIIlIIII = AntiBot.llIlllI[0];
        final long llIlIlIlIIIlIlI = (Object)llIlIlIlIIIllll.toCharArray();
        final short llIlIlIlIIIlIIl = (short)llIlIlIlIIIlIlI.length;
        short llIlIlIlIIIlIII = (short)AntiBot.llIlllI[0];
        while (lIllIIIIl(llIlIlIlIIIlIII, llIlIlIlIIIlIIl)) {
            final char llIlIlIlIIlIlIl = llIlIlIlIIIlIlI[llIlIlIlIIIlIII];
            llIlIlIlIIlIIlI.append((char)(llIlIlIlIIlIlIl ^ llIlIlIlIIlIIIl[llIlIlIlIIlIIII % llIlIlIlIIlIIIl.length]));
            "".length();
            ++llIlIlIlIIlIIII;
            ++llIlIlIlIIIlIII;
            "".length();
            if ("   ".length() <= 0) {
                return null;
            }
        }
        return String.valueOf(llIlIlIlIIlIIlI);
    }
    
    private static boolean lIllIIIIl(final int llIlIlIIllIIllI, final int llIlIlIIllIIlIl) {
        return llIlIlIIllIIllI < llIlIlIIllIIlIl;
    }
    
    private static boolean lIlIlllIl(final int llIlIlIIlIIlllI, final int llIlIlIIlIIllIl) {
        return llIlIlIIlIIlllI != llIlIlIIlIIllIl;
    }
    
    private static boolean lIlIllIIl(final int llIlIlIIlIlIIIl) {
        return llIlIlIIlIlIIIl > 0;
    }
    
    private static boolean lIlIlIllI(final int llIlIlIIlIlIlll) {
        return llIlIlIIlIlIlll != 0;
    }
    
    private static boolean lIlIlllII(final int llIlIlIIllIIIlI, final int llIlIlIIllIIIIl) {
        return llIlIlIIllIIIlI > llIlIlIIllIIIIl;
    }
    
    private static int lIlIlIlIl(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    private static void lIlIIlIIl() {
        (llIIllI = new String[AntiBot.llIlllI[15]])[AntiBot.llIlllI[0]] = lIIllllII("J2916GKJwOY=", "Lluco");
        AntiBot.llIIllI[AntiBot.llIlllI[1]] = lIIllllIl("je4gE64oaVs=", "eLOTq");
        AntiBot.llIIllI[AntiBot.llIlllI[2]] = lIIllllII("k80byX25Ey+MXazPR86uCA==", "FdwXP");
        AntiBot.llIIllI[AntiBot.llIlllI[3]] = lIIllllII("nr1YTVjpYTk3KFI0BDH+/g==", "WIWGH");
        AntiBot.llIIllI[AntiBot.llIlllI[4]] = lIIllllll("IC8YExs=", "iAYzi");
        AntiBot.llIIllI[AntiBot.llIlllI[5]] = lIIllllII("izimwmAzLpDpdfdgNqlHlA==", "sZvGl");
        AntiBot.llIIllI[AntiBot.llIlllI[6]] = lIIllllII("etVuovVGmm2tAwwygxK+jA==", "ATipy");
        AntiBot.llIIllI[AntiBot.llIlllI[7]] = lIIllllll("MDY3PR8MESc=", "uXCTk");
        AntiBot.llIIllI[AntiBot.llIlllI[8]] = lIIllllII("pHHZ08hTb0+O8c53wmou8g==", "epOII");
        AntiBot.llIIllI[AntiBot.llIlllI[9]] = lIIllllll("EzMLNDQrPwY4", "CZeSw");
        AntiBot.llIIllI[AntiBot.llIlllI[10]] = lIIllllll("EyE3IiMkBjU5Jg==", "ADZMU");
        AntiBot.llIIllI[AntiBot.llIlllI[11]] = lIIllllIl("66rZrlJu+tE=", "pSvfz");
        AntiBot.llIIllI[AntiBot.llIlllI[13]] = lIIllllII("Bnu3yPcbQiE=", "auKdO");
        AntiBot.llIIllI[AntiBot.llIlllI[14]] = lIIllllll("LB8n", "bOdFW");
    }
    
    private static boolean lIllIIIlI(final int llIlIlIIllIlIlI, final int llIlIlIIllIlIIl) {
        return llIlIlIIllIlIlI >= llIlIlIIllIlIIl;
    }
    
    private static boolean lIlIllIll(final Object llIlIlIIlIllllI, final Object llIlIlIIlIlllIl) {
        return llIlIlIIlIllllI != llIlIlIIlIlllIl;
    }
    
    private static int lIllIIlIl(final float n, final float n2) {
        return fcmpg(n, n2);
    }
    
    @Override
    public void onEnable() {
        AntiBot.bots.clear();
        super.onEnable();
    }
    
    int botPercentage(final EntityPlayer llIlIlIlIllIIll) {
        int llIlIlIlIllIIlI = AntiBot.llIlllI[0];
        if (lIlIllIIl(lIllIIlII(this.tick.getValue().intValue(), 0.0)) && lIllIIIIl(llIlIlIlIllIIll.ticksExisted, this.tick.getValue().intValue())) {
            ++llIlIlIlIllIIlI;
        }
        if (lIlIlIllI(((boolean)this.ifInAir.getValue()) ? 1 : 0) && lIlIlIllI(llIlIlIlIllIIll.isInvisible() ? 1 : 0) && lIlIllIIl(lIllIIlII(llIlIlIlIllIIll.posY, Wrapper.INSTANCE.player().posY + 1.0)) && lIlIlIllI(Utils.isBlockMaterial(new BlockPos((Entity)llIlIlIlIllIIll).down(), Blocks.AIR) ? 1 : 0)) {
            ++llIlIlIlIllIIlI;
        }
        if (lIlIlIllI(((boolean)this.ifGround.getValue()) ? 1 : 0) && lIlIllIlI(lIllIIlII(llIlIlIlIllIIll.motionY, 0.0)) && lIlIllIlI(llIlIlIlIllIIll.collidedVertically ? 1 : 0) && lIlIlIllI(llIlIlIlIllIIll.onGround ? 1 : 0) && lIlIlIllI(lIllIIlII(llIlIlIlIllIIll.posY % 1.0, 0.0)) && lIlIlIllI(lIllIIlII(llIlIlIlIllIIll.posY % 0.5, 0.0))) {
            ++llIlIlIlIllIIlI;
        }
        if (lIlIlIllI(((boolean)this.ifZeroHealth.getValue()) ? 1 : 0) && lIlIlIlll(lIllIIlIl(llIlIlIlIllIIll.getHealth(), 0.0f))) {
            ++llIlIlIlIllIIlI;
        }
        if (lIlIlIllI(((boolean)this.ifInvisible.getValue()) ? 1 : 0) && lIlIlIllI(llIlIlIlIllIIll.isInvisible() ? 1 : 0)) {
            ++llIlIlIlIllIIlI;
        }
        if (lIlIlIllI(((boolean)this.ifEntityId.getValue()) ? 1 : 0) && lIllIIIlI(llIlIlIlIllIIll.getEntityId(), AntiBot.llIlllI[12])) {
            ++llIlIlIlIllIIlI;
        }
        if (lIlIlIllI(((boolean)this.ifTabName.getValue()) ? 1 : 0)) {
            boolean llIlIlIlIllIlIl = AntiBot.llIlllI[0] != 0;
            final float llIlIlIlIlIllIl = (float)Wrapper.INSTANCE.mc().getConnection().getPlayerInfoMap().iterator();
            while (lIlIlIllI(((Iterator)llIlIlIlIlIllIl).hasNext() ? 1 : 0)) {
                final NetworkPlayerInfo llIlIlIlIllIllI = ((Iterator<NetworkPlayerInfo>)llIlIlIlIlIllIl).next();
                if (lIllIIIll(llIlIlIlIllIllI.getGameProfile()) && lIlIlIllI(llIlIlIlIllIllI.getGameProfile().getName().contains(llIlIlIlIllIIll.getName()) ? 1 : 0)) {
                    llIlIlIlIllIlIl = (AntiBot.llIlllI[1] != 0);
                }
                "".length();
                if (null != null) {
                    return (0x8 ^ 0x37) & ~(0xB0 ^ 0x8F);
                }
            }
            if (lIlIllIlI(llIlIlIlIllIlIl ? 1 : 0)) {
                ++llIlIlIlIllIIlI;
            }
        }
        return llIlIlIlIllIIlI;
    }
    
    void addBot(final EntityPlayer llIlIlIllIlIlll) {
        if (lIlIllIlI(isBot(llIlIlIllIlIlll) ? 1 : 0)) {
            AntiBot.bots.add(new EntityBot(llIlIlIllIlIlll));
            "".length();
        }
    }
    
    private static int lIlIlIIll(final double n, final double n2) {
        return dcmpg(n, n2);
    }
    
    private static boolean lIllIIllI(final Object llIlIlIIlIllIIl) {
        return llIlIlIIlIllIIl == null;
    }
    
    @Override
    public boolean onPacket(final Object llIlIlIlllllllI, final Connection.Side llIlIlIllllllIl) {
        if (lIlIlIllI(((boolean)this.gwen.getValue()) ? 1 : 0)) {
            final byte llIlIlIlllllIII = (byte)Wrapper.INSTANCE.world().loadedEntityList.iterator();
            while (lIlIlIllI(((Iterator)llIlIlIlllllIII).hasNext() ? 1 : 0)) {
                final Object llIlIllIIIIIIII = ((Iterator<Object>)llIlIlIlllllIII).next();
                if (lIlIlIllI((llIlIlIlllllllI instanceof SPacketSpawnPlayer) ? 1 : 0)) {
                    final SPacketSpawnPlayer llIlIllIIIIlIII = (SPacketSpawnPlayer)llIlIlIlllllllI;
                    final double llIlIllIIIIIlll = llIlIllIIIIlIII.getX() / 32.0;
                    final double llIlIllIIIIIllI = llIlIllIIIIlIII.getY() / 32.0;
                    final double llIlIllIIIIIlIl = llIlIllIIIIlIII.getZ() / 32.0;
                    final double llIlIllIIIIIlII = Wrapper.INSTANCE.player().posX - llIlIllIIIIIlll;
                    final double llIlIllIIIIIIll = Wrapper.INSTANCE.player().posY - llIlIllIIIIIllI;
                    final double llIlIllIIIIIIlI = Wrapper.INSTANCE.player().posZ - llIlIllIIIIIlIl;
                    final double llIlIllIIIIIIIl = Math.sqrt(llIlIllIIIIIlII * llIlIllIIIIIlII + llIlIllIIIIIIll * llIlIllIIIIIIll + llIlIllIIIIIIlI * llIlIllIIIIIIlI);
                    if (lIlIlIlll(lIlIlIIll(llIlIllIIIIIIIl, 17.0)) && lIlIlIllI(lIlIlIlIl(llIlIllIIIIIlll, Wrapper.INSTANCE.player().posX)) && lIlIlIllI(lIlIlIlIl(llIlIllIIIIIllI, Wrapper.INSTANCE.player().posY)) && lIlIlIllI(lIlIlIlIl(llIlIllIIIIIlIl, Wrapper.INSTANCE.player().posZ))) {
                        return AntiBot.llIlllI[0] != 0;
                    }
                }
                "".length();
                if (((0x11 ^ 0x47) & ~(0x2B ^ 0x7D)) < 0) {
                    return ((0xBB ^ 0x8E) & ~(0x9C ^ 0xA9)) != 0x0;
                }
            }
        }
        return AntiBot.llIlllI[1] != 0;
    }
    
    private static boolean lIlIllIlI(final int llIlIlIIlIlIlIl) {
        return llIlIlIIlIlIlIl == 0;
    }
    
    private static boolean lIlIlIlll(final int llIlIlIIlIlIIll) {
        return llIlIlIIlIlIIll <= 0;
    }
    
    boolean isBotBase(final EntityPlayer llIlIlIlIlIIIlI) {
        if (lIlIlIllI(isBot(llIlIlIlIlIIIlI) ? 1 : 0)) {
            return AntiBot.llIlllI[1] != 0;
        }
        if (lIllIIllI(llIlIlIlIlIIIlI.getGameProfile())) {
            return AntiBot.llIlllI[1] != 0;
        }
        final GameProfile llIlIlIlIlIIlIl = llIlIlIlIlIIIlI.getGameProfile();
        if (lIllIIllI(llIlIlIlIlIIIlI.getUniqueID())) {
            return AntiBot.llIlllI[1] != 0;
        }
        final UUID llIlIlIlIlIIlII = llIlIlIlIlIIIlI.getUniqueID();
        if (lIllIIllI(llIlIlIlIlIIlIl.getName())) {
            return AntiBot.llIlllI[1] != 0;
        }
        final String llIlIlIlIlIIIll = llIlIlIlIlIIlIl.getName();
        if (!lIlIllIlI(llIlIlIlIlIIIll.contains(AntiBot.llIIllI[AntiBot.llIlllI[13]]) ? 1 : 0) || !lIlIllIlI(llIlIlIlIlIIIll.contains(AntiBot.llIIllI[AntiBot.llIlllI[14]]) ? 1 : 0) || lIlIlIllI(llIlIlIlIlIIIll.equalsIgnoreCase(Utils.getEntityNameColor((EntityLivingBase)llIlIlIlIlIIIlI)) ? 1 : 0)) {
            return AntiBot.llIlllI[1] != 0;
        }
        return AntiBot.llIlllI[0] != 0;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent llIlIlIlllIIIII) {
        if (lIlIllIIl(lIlIllIII(this.tick.getValue().intValue(), 0.0))) {
            AntiBot.bots.clear();
        }
        final char llIlIlIllIlllll = (char)Wrapper.INSTANCE.world().loadedEntityList.iterator();
        while (lIlIlIllI(((Iterator)llIlIlIllIlllll).hasNext() ? 1 : 0)) {
            final Object llIlIlIlllIIlII = ((Iterator<Object>)llIlIlIllIlllll).next();
            if (lIlIlIllI((llIlIlIlllIIlII instanceof EntityLivingBase) ? 1 : 0)) {
                final EntityLivingBase llIlIlIlllIIlIl = (EntityLivingBase)llIlIlIlllIIlII;
                if (lIlIllIlI((llIlIlIlllIIlIl instanceof EntityPlayerSP) ? 1 : 0) && lIlIlIllI((llIlIlIlllIIlIl instanceof EntityPlayer) ? 1 : 0) && lIlIllIlI((llIlIlIlllIIlIl instanceof EntityArmorStand) ? 1 : 0) && lIlIllIll(llIlIlIlllIIlIl, Wrapper.INSTANCE.player())) {
                    final EntityPlayer llIlIlIlllIIllI = (EntityPlayer)llIlIlIlllIIlIl;
                    if (lIlIllIlI(this.isBotBase(llIlIlIlllIIllI) ? 1 : 0)) {
                        final int llIlIlIlllIlIII = this.level.getValue().intValue();
                        int n;
                        if (lIlIllIIl(lIlIllIII(llIlIlIlllIlIII, 0.0))) {
                            n = AntiBot.llIlllI[1];
                            "".length();
                            if ("  ".length() <= 0) {
                                return;
                            }
                        }
                        else {
                            n = AntiBot.llIlllI[0];
                        }
                        final boolean llIlIlIlllIIlll = n != 0;
                        if (lIlIlIllI(llIlIlIlllIIlll ? 1 : 0) && lIlIlllII(this.botPercentage(llIlIlIlllIIllI), llIlIlIlllIlIII)) {
                            this.addBot(llIlIlIlllIIllI);
                            "".length();
                            if (((0x7F ^ 0x69) & ~(0x9D ^ 0x8B)) != 0x0) {
                                return;
                            }
                        }
                        else if (lIlIllIlI(llIlIlIlllIIlll ? 1 : 0) && lIlIlIllI(this.botCondition(llIlIlIlllIIllI) ? 1 : 0)) {
                            this.addBot(llIlIlIlllIIllI);
                        }
                        "".length();
                        if ((0x4E ^ 0x4A) < 0) {
                            return;
                        }
                    }
                    else {
                        this.addBot(llIlIlIlllIIllI);
                        if (lIlIlIllI(((boolean)this.remove.getValue()) ? 1 : 0)) {
                            Wrapper.INSTANCE.world().removeEntity((Entity)llIlIlIlllIIllI);
                        }
                    }
                }
            }
            "".length();
            if ("   ".length() == -" ".length()) {
                return;
            }
        }
        super.onClientTick(llIlIlIlllIIIII);
    }
    
    static {
        lIlIlIIlI();
        lIlIIlIIl();
        AntiBot.bots = new ArrayList<EntityBot>();
    }
    
    private static int lIlIllIII(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    private static void lIlIlIIlI() {
        (llIlllI = new int[16])[0] = ((0x93 ^ 0xA4 ^ (0xAB ^ 0x8A)) & (0x20 ^ 0x18 ^ (0x46 ^ 0x68) ^ -" ".length()));
        AntiBot.llIlllI[1] = " ".length();
        AntiBot.llIlllI[2] = "  ".length();
        AntiBot.llIlllI[3] = "   ".length();
        AntiBot.llIlllI[4] = (0x96 ^ 0x92);
        AntiBot.llIlllI[5] = (0x2F ^ 0x2A);
        AntiBot.llIlllI[6] = (60 + 80 - 113 + 141 ^ 102 + 36 - 0 + 36);
        AntiBot.llIlllI[7] = (0xA ^ 0xD);
        AntiBot.llIlllI[8] = (0x69 ^ 0x67 ^ (0x49 ^ 0x4F));
        AntiBot.llIlllI[9] = (0xD7 ^ 0xA3 ^ (0xC ^ 0x71));
        AntiBot.llIlllI[10] = (0x6C ^ 0x66);
        AntiBot.llIlllI[11] = (0xB6 ^ 0xC0 ^ (0xDB ^ 0xA6));
        AntiBot.llIlllI[12] = (-(0xFFFFB4FF & 0x6FF2) & (0xFFFFFEF3 & 0x3B9AEFFD));
        AntiBot.llIlllI[13] = (0x4E ^ 0x49 ^ (0xA6 ^ 0xAD));
        AntiBot.llIlllI[14] = (0xBA ^ 0x9C ^ (0x68 ^ 0x43));
        AntiBot.llIlllI[15] = (0x73 ^ 0x7D);
    }
    
    public AntiBot() {
        super(AntiBot.llIIllI[AntiBot.llIlllI[0]], ModuleCategory.COMBAT);
        this.level = new NumberValue(AntiBot.llIIllI[AntiBot.llIlllI[1]], 0.0, 0.0, 6.0);
        this.tick = new NumberValue(AntiBot.llIIllI[AntiBot.llIlllI[2]], 0.0, 0.0, 999.0);
        this.ifInvisible = new BooleanValue(AntiBot.llIIllI[AntiBot.llIlllI[3]], (boolean)(AntiBot.llIlllI[0] != 0));
        this.ifInAir = new BooleanValue(AntiBot.llIIllI[AntiBot.llIlllI[4]], (boolean)(AntiBot.llIlllI[0] != 0));
        this.ifGround = new BooleanValue(AntiBot.llIIllI[AntiBot.llIlllI[5]], (boolean)(AntiBot.llIlllI[0] != 0));
        this.ifZeroHealth = new BooleanValue(AntiBot.llIIllI[AntiBot.llIlllI[6]], (boolean)(AntiBot.llIlllI[0] != 0));
        this.ifEntityId = new BooleanValue(AntiBot.llIIllI[AntiBot.llIlllI[7]], (boolean)(AntiBot.llIlllI[0] != 0));
        this.ifTabName = new BooleanValue(AntiBot.llIIllI[AntiBot.llIlllI[8]], (boolean)(AntiBot.llIlllI[0] != 0));
        this.ifPing = new BooleanValue(AntiBot.llIIllI[AntiBot.llIlllI[9]], (boolean)(AntiBot.llIlllI[0] != 0));
        this.remove = new BooleanValue(AntiBot.llIIllI[AntiBot.llIlllI[10]], (boolean)(AntiBot.llIlllI[0] != 0));
        this.gwen = new BooleanValue(AntiBot.llIIllI[AntiBot.llIlllI[11]], (boolean)(AntiBot.llIlllI[0] != 0));
        final Value[] lllIIIIlIIlllIl = new Value[AntiBot.llIlllI[11]];
        lllIIIIlIIlllIl[AntiBot.llIlllI[0]] = this.level;
        lllIIIIlIIlllIl[AntiBot.llIlllI[1]] = this.tick;
        lllIIIIlIIlllIl[AntiBot.llIlllI[2]] = this.remove;
        lllIIIIlIIlllIl[AntiBot.llIlllI[3]] = this.gwen;
        lllIIIIlIIlllIl[AntiBot.llIlllI[4]] = this.ifInvisible;
        lllIIIIlIIlllIl[AntiBot.llIlllI[5]] = this.ifInAir;
        lllIIIIlIIlllIl[AntiBot.llIlllI[6]] = this.ifGround;
        lllIIIIlIIlllIl[AntiBot.llIlllI[7]] = this.ifZeroHealth;
        lllIIIIlIIlllIl[AntiBot.llIlllI[8]] = this.ifEntityId;
        lllIIIIlIIlllIl[AntiBot.llIlllI[9]] = this.ifTabName;
        lllIIIIlIIlllIl[AntiBot.llIlllI[10]] = this.ifPing;
        this.addValue(lllIIIIlIIlllIl);
    }
    
    private static int lIllIIIII(final float n, final float n2) {
        return fcmpg(n, n2);
    }
    
    private static boolean lIllIIIll(final Object llIlIlIIlIllIll) {
        return llIlIlIIlIllIll != null;
    }
    
    private static int lIlIlllll(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    private static String lIIllllIl(final String llIlIlIIlllIIlI, final String llIlIlIIllIllll) {
        try {
            final SecretKeySpec llIlIlIIlllIlIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlIlIIllIllll.getBytes(StandardCharsets.UTF_8)), AntiBot.llIlllI[8]), "DES");
            final Cipher llIlIlIIlllIlII = Cipher.getInstance("DES");
            llIlIlIIlllIlII.init(AntiBot.llIlllI[2], llIlIlIIlllIlIl);
            return new String(llIlIlIIlllIlII.doFinal(Base64.getDecoder().decode(llIlIlIIlllIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIlIIlllIIll) {
            llIlIlIIlllIIll.printStackTrace();
            return null;
        }
    }
    
    private static int lIllIIlII(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    public static boolean isBot(final EntityPlayer llIlIlIllIlIIIl) {
        final String llIlIlIllIIllll = (String)AntiBot.bots.iterator();
        while (lIlIlIllI(((Iterator)llIlIlIllIIllll).hasNext() ? 1 : 0)) {
            final EntityBot llIlIlIllIlIIlI = ((Iterator<EntityBot>)llIlIlIllIIllll).next();
            if (lIlIlIllI(llIlIlIllIlIIlI.getName().equals(llIlIlIllIlIIIl.getName()) ? 1 : 0)) {
                if (lIlIlllIl(llIlIlIllIlIIIl.isInvisible() ? 1 : 0, llIlIlIllIlIIlI.isInvisible() ? 1 : 0)) {
                    return llIlIlIllIlIIIl.isInvisible();
                }
                return AntiBot.llIlllI[1] != 0;
            }
            else {
                if (!lIlIlllIl(llIlIlIllIlIIlI.getId(), llIlIlIllIlIIIl.getEntityId()) || lIlIlIllI(llIlIlIllIlIIlI.getUuid().equals(llIlIlIllIlIIIl.getGameProfile().getId()) ? 1 : 0)) {
                    return AntiBot.llIlllI[1] != 0;
                }
                "".length();
                if (-"   ".length() >= 0) {
                    return ((0x17 ^ 0x26) & ~(0xBF ^ 0x8E)) != 0x0;
                }
                continue;
            }
        }
        return AntiBot.llIlllI[0] != 0;
    }
    
    private static String lIIllllII(final String llIlIlIIlllllIl, final String llIlIlIIllllllI) {
        try {
            final SecretKeySpec llIlIlIlIIIIIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIlIIllllllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIlIlIlIIIIIIl = Cipher.getInstance("Blowfish");
            llIlIlIlIIIIIIl.init(AntiBot.llIlllI[2], llIlIlIlIIIIIlI);
            return new String(llIlIlIlIIIIIIl.doFinal(Base64.getDecoder().decode(llIlIlIIlllllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIlIlIIIIIII) {
            llIlIlIlIIIIIII.printStackTrace();
            return null;
        }
    }
    
    boolean botCondition(final EntityPlayer llIlIlIllIIIIIl) {
        final int llIlIlIllIIIIll = AntiBot.llIlllI[0];
        if (lIlIllIIl(lIlIlllll(this.tick.getValue().intValue(), 0.0)) && lIllIIIIl(llIlIlIllIIIIIl.ticksExisted, this.tick.getValue().intValue())) {
            return AntiBot.llIlllI[1] != 0;
        }
        if (lIlIlIllI(((boolean)this.ifInAir.getValue()) ? 1 : 0) && lIlIlIllI(llIlIlIllIIIIIl.isInvisible() ? 1 : 0) && lIlIllIlI(lIlIlllll(llIlIlIllIIIIIl.motionY, 0.0)) && lIlIllIIl(lIlIlllll(llIlIlIllIIIIIl.posY, Wrapper.INSTANCE.player().posY + 1.0)) && lIlIlIllI(Utils.isBlockMaterial(new BlockPos((Entity)llIlIlIllIIIIIl).down(), Blocks.AIR) ? 1 : 0)) {
            return AntiBot.llIlllI[1] != 0;
        }
        if (lIlIlIllI(((boolean)this.ifGround.getValue()) ? 1 : 0) && lIlIllIlI(lIlIlllll(llIlIlIllIIIIIl.motionY, 0.0)) && lIlIllIlI(llIlIlIllIIIIIl.collidedVertically ? 1 : 0) && lIlIlIllI(llIlIlIllIIIIIl.onGround ? 1 : 0) && lIlIlIllI(lIlIlllll(llIlIlIllIIIIIl.posY % 1.0, 0.0)) && lIlIlIllI(lIlIlllll(llIlIlIllIIIIIl.posY % 0.5, 0.0))) {
            return AntiBot.llIlllI[1] != 0;
        }
        if (lIlIlIllI(((boolean)this.ifZeroHealth.getValue()) ? 1 : 0) && lIlIlIlll(lIllIIIII(llIlIlIllIIIIIl.getHealth(), 0.0f))) {
            return AntiBot.llIlllI[1] != 0;
        }
        if (lIlIlIllI(((boolean)this.ifInvisible.getValue()) ? 1 : 0) && lIlIlIllI(llIlIlIllIIIIIl.isInvisible() ? 1 : 0)) {
            return AntiBot.llIlllI[1] != 0;
        }
        if (lIlIlIllI(((boolean)this.ifEntityId.getValue()) ? 1 : 0) && lIllIIIlI(llIlIlIllIIIIIl.getEntityId(), AntiBot.llIlllI[12])) {
            return AntiBot.llIlllI[1] != 0;
        }
        if (lIlIlIllI(((boolean)this.ifTabName.getValue()) ? 1 : 0)) {
            boolean llIlIlIllIIIllI = AntiBot.llIlllI[0] != 0;
            final short llIlIlIlIlllllI = (short)Wrapper.INSTANCE.mc().getConnection().getPlayerInfoMap().iterator();
            while (lIlIlIllI(((Iterator)llIlIlIlIlllllI).hasNext() ? 1 : 0)) {
                final NetworkPlayerInfo llIlIlIllIIIlll = ((Iterator<NetworkPlayerInfo>)llIlIlIlIlllllI).next();
                if (lIllIIIll(llIlIlIllIIIlll.getGameProfile()) && lIlIlIllI(llIlIlIllIIIlll.getGameProfile().getName().contains(llIlIlIllIIIIIl.getName()) ? 1 : 0)) {
                    llIlIlIllIIIllI = (AntiBot.llIlllI[1] != 0);
                }
                "".length();
                if ("  ".length() < 0) {
                    return ((0x49 ^ 0x59) & ~(0xF ^ 0x1F)) != 0x0;
                }
            }
            if (lIlIllIlI(llIlIlIllIIIllI ? 1 : 0)) {
                return AntiBot.llIlllI[1] != 0;
            }
        }
        return AntiBot.llIlllI[0] != 0;
    }
}
