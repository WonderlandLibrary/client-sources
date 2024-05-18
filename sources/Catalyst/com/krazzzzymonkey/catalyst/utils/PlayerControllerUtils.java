// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import java.lang.reflect.Field;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraft.world.GameType;
import com.krazzzzymonkey.catalyst.utils.system.Mapping;
import net.minecraft.entity.player.EntityPlayer;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;

public class PlayerControllerUtils
{
    private static final /* synthetic */ int[] lIIIIlI;
    
    private static void lllIIIIl() {
        (lIIIIlI = new int[2])[0] = " ".length();
        PlayerControllerUtils.lIIIIlI[1] = ((0x6 ^ 0x78 ^ (0xE ^ 0x37)) & (0x18 ^ 0x78 ^ (0xA6 ^ 0x81) ^ -" ".length()));
    }
    
    static {
        lllIIIIl();
    }
    
    public static void setReach(final Entity lllIIIIllllIIll, final double lllIIIIlllllIII) {
        final Minecraft lllIIIIllllIlll = Wrapper.INSTANCE.mc();
        final EntityPlayer lllIIIIllllIlIl = (EntityPlayer)Wrapper.INSTANCE.player();
        if (lllIIIlI(lllIIIIllllIlIl, lllIIIIllllIIll)) {
            class 1RangePlayerController extends PlayerControllerMP
            {
                private /* synthetic */ float range;
                
                public void setBlockReachDistance(final float llIIIIlllIlIlIl) {
                    this.range = llIIIIlllIlIlIl;
                }
                
                public 1RangePlayerController(final Minecraft llIIIIllllIIIlI, final NetHandlerPlayClient llIIIIllllIIIIl) {
                    super(llIIIIllllIIIlI, llIIIIllllIIIIl);
                    this.range = (float)Wrapper.INSTANCE.player().getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
                }
                
                public float getBlockReachDistance() {
                    return this.range;
                }
            }
            if (lllIIIll((lllIIIIllllIlll.playerController instanceof 1RangePlayerController) ? 1 : 0)) {
                final Class<PlayerControllerMP> clazz = PlayerControllerMP.class;
                final PlayerControllerMP playerController = lllIIIIllllIlll.playerController;
                final String[] array = new String[PlayerControllerUtils.lIIIIlI[0]];
                array[PlayerControllerUtils.lIIIIlI[1]] = Mapping.currentGameType;
                final GameType lllIIIIllllllll = (GameType)ReflectionHelper.getPrivateValue((Class)clazz, (Object)playerController, array);
                final Class<PlayerControllerMP> clazz2 = PlayerControllerMP.class;
                final PlayerControllerMP playerController2 = lllIIIIllllIlll.playerController;
                final String[] array2 = new String[PlayerControllerUtils.lIIIIlI[0]];
                array2[PlayerControllerUtils.lIIIIlI[1]] = Mapping.connection;
                final NetHandlerPlayClient lllIIIIlllllllI = (NetHandlerPlayClient)ReflectionHelper.getPrivateValue((Class)clazz2, (Object)playerController2, array2);
                final 1RangePlayerController lllIIIIllllllIl = new 1RangePlayerController(lllIIIIllllIlll, lllIIIIlllllllI);
                final boolean lllIIIIllllllII = lllIIIIllllIlIl.capabilities.isFlying;
                final boolean lllIIIIlllllIll = lllIIIIllllIlIl.capabilities.allowFlying;
                lllIIIIllllllIl.setGameType(lllIIIIllllllll);
                lllIIIIllllIlIl.capabilities.isFlying = lllIIIIllllllII;
                lllIIIIllllIlIl.capabilities.allowFlying = lllIIIIlllllIll;
                lllIIIIllllIlll.playerController = lllIIIIllllllIl;
            }
            ((1RangePlayerController)lllIIIIllllIlll.playerController).setBlockReachDistance((float)lllIIIIlllllIII);
        }
    }
    
    public static float getCurBlockDamageMP() {
        float lllIIIIllIIlIII = 0.0f;
        try {
            final Field lllIIIIllIIlIlI = PlayerControllerMP.class.getDeclaredField(Mapping.curBlockDamageMP);
            lllIIIIllIIlIlI.setAccessible((boolean)(PlayerControllerUtils.lIIIIlI[0] != 0));
            lllIIIIllIIlIII = lllIIIIllIIlIlI.getFloat(Wrapper.INSTANCE.mc().playerController);
            "".length();
            if (((37 + 80 - 35 + 88 ^ 22 + 48 + 32 + 53) & (15 + 75 + 84 + 1 ^ 25 + 136 - 96 + 93 ^ -" ".length())) != 0x0) {
                return 0.0f;
            }
        }
        catch (Exception ex) {}
        return lllIIIIllIIlIII;
    }
    
    public static void setBlockHitDelay(final int lllIIIIllIlIIII) {
        try {
            final Field lllIIIIllIlIIll = PlayerControllerMP.class.getDeclaredField(Mapping.blockHitDelay);
            lllIIIIllIlIIll.setAccessible((boolean)(PlayerControllerUtils.lIIIIlI[0] != 0));
            lllIIIIllIlIIll.setInt(Wrapper.INSTANCE.mc().playerController, lllIIIIllIlIIII);
            "".length();
            if (-(75 + 73 - 123 + 141 ^ 145 + 58 - 159 + 118) >= 0) {
                return;
            }
        }
        catch (Exception ex) {}
    }
    
    private static boolean lllIIIlI(final Object lllIIIIlIlllllI, final Object lllIIIIlIllllIl) {
        return lllIIIIlIlllllI == lllIIIIlIllllIl;
    }
    
    public static void setIsHittingBlock(final boolean lllIIIIllIllIll) {
        try {
            final Field lllIIIIllIlllIl = PlayerControllerMP.class.getDeclaredField(Mapping.isHittingBlock);
            lllIIIIllIlllIl.setAccessible((boolean)(PlayerControllerUtils.lIIIIlI[0] != 0));
            lllIIIIllIlllIl.setBoolean(Wrapper.INSTANCE.mc().playerController, lllIIIIllIllIll);
            "".length();
            if (-" ".length() >= 0) {
                return;
            }
        }
        catch (Exception ex) {}
    }
    
    private static boolean lllIIIll(final int lllIIIIlIlllIIl) {
        return lllIIIIlIlllIIl == 0;
    }
}
