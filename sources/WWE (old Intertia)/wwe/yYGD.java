package wwe;

import java.lang.reflect.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.fml.common.*;
import wwe.utils.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.inventory.*;
import net.minecraft.network.datasync.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;

public class yYGD
{
    public static Field AiTO;
    public static Field UUbY;
    public static Field ojZx;
    public static Field uPXs;
    public static Field JRdT;
    public static Field TkEx;
    public static Field xxOK;
    public static Field WoEU;
    public static Field ldBn;
    public static Field EXAg;
    public static Field dkBj;
    public static Field Fxwb;
    public static Field VBeC;
    public static Field GCRA;
    public static Field YQVA;
    public static Field MxtI;
    private static Field TvMi;
    public static Method uwun;
    public static Field PqBx;
    public static Field swXu;
    public static Field CfFD;
    public static Field gIyZ;
    public static Field aSIs;
    public static Field QzaI;
    public static Field mRhX;
    public static Field TRgG;
    public static Field tDsP;
    public static Field nbcg;
    public static Field iouz;
    public static Field gEci;
    public static Field ehGs;
    public static Field KsJA;
    public static Field jOQD;
    public static Field zoaL;
    public static Field DqJB;
    public static Field eNKp;
    public static Field sbSC;
    public static Field GqcO;
    public static Field OByD;
    public static Field ZCsv;
    public static Field SguM;
    public static Field chLR;
    public static Field leJD;
    
    public yYGD() {
        super();
    }
    
    public static void vDDx() {
        try {
            yYGD.AiTO = BwxQ(RenderManager.class, "renderPosX", "field_78725_b");
            yYGD.UUbY = BwxQ(RenderManager.class, "renderPosY", "field_78726_c");
            yYGD.ojZx = BwxQ(RenderManager.class, "renderPosZ", "field_78723_d");
            yYGD.uPXs = BwxQ(RenderManager.class, "playerViewX", "field_78732_j");
            yYGD.JRdT = BwxQ(RenderManager.class, "playerViewY", "field_78735_i");
            yYGD.TkEx = BwxQ(Minecraft.class, "timer", "field_71428_T");
            yYGD.xxOK = BwxQ(Minecraft.class, "modelManager", "field_175617_aL");
            yYGD.uwun = WOfx(Minecraft.class, new String[] { "rightClickMouse", "func_147121_ag" }, (Class<?>[])new Class[0]);
            yYGD.WoEU = BwxQ(KeyBinding.class, "pressed", "field_74513_e");
            yYGD.ldBn = BwxQ(CPacketPlayer.class, "yaw", "field_149476_e");
            yYGD.EXAg = BwxQ(CPacketPlayer.class, "pitch", "field_149473_f");
            yYGD.dkBj = BwxQ(SPacketPlayerPosLook.class, "yaw", "field_148936_d");
            yYGD.Fxwb = BwxQ(SPacketPlayerPosLook.class, "pitch", "field_148937_e");
            yYGD.VBeC = BwxQ(TextureManager.class, "mapTextureObjects", "field_110585_a");
            yYGD.GCRA = BwxQ(CPacketPlayer.class, "onGround", "field_149474_g");
            yYGD.YQVA = BwxQ(Minecraft.class, "rightClickDelayTimer", "field_71467_ac");
            yYGD.MxtI = BwxQ(EntityPlayerSP.class, "horseJumpPower", "field_110321_bQ");
            yYGD.PqBx = BwxQ(PlayerControllerMP.class, "curBlockDamageMP", "field_78770_f");
            yYGD.swXu = BwxQ(PlayerControllerMP.class, "blockHitDelay", "field_78781_i");
            yYGD.CfFD = BwxQ(Minecraft.class, "debugFPS", "field_71470_ab");
            yYGD.gIyZ = BwxQ(GuiChest.class, "lowerChestInventory", "field_147015_w");
            yYGD.aSIs = BwxQ(GuiShulkerBox.class, "inventory", "field_190779_v");
            yYGD.QzaI = BwxQ(SPacketExplosion.class, "motionX", "field_149152_f");
            yYGD.mRhX = BwxQ(SPacketExplosion.class, "motionY", "field_149153_g");
            yYGD.TRgG = BwxQ(SPacketExplosion.class, "motionZ", "field_149159_h");
            yYGD.tDsP = BwxQ(CPacketPlayer.class, "y", "field_149477_b");
            yYGD.nbcg = BwxQ(CPacketVehicleMove.class, "y", "field_187008_b");
            yYGD.iouz = BwxQ(Minecraft.class, "session", "field_71449_j");
            yYGD.gEci = BwxQ(EntityPlayer.class, "PLAYER_MODEL_FLAG", "field_184827_bp");
            yYGD.ehGs = BwxQ(GuiButton.class, "hovered", "field_146123_n");
            yYGD.KsJA = BwxQ(Entity.class, "ridingEntity", "field_184239_as");
            yYGD.jOQD = BwxQ(EntityPlayer.class, "sleeping", "field_71083_bS");
            yYGD.zoaL = BwxQ(EntityPlayer.class, "sleepTimer", "field_71076_b");
            yYGD.DqJB = BwxQ(CPacketUpdateSign.class, "lines", "field_149590_d");
            yYGD.eNKp = BwxQ(CPacketChatMessage.class, "message", "field_149440_a");
            yYGD.sbSC = BwxQ(GuiDisconnected.class, "parentScreen", "field_146307_h");
            yYGD.GqcO = BwxQ(GuiScreenServerList.class, "serverData", "field_146301_f");
            yYGD.OByD = BwxQ(AbstractHorse.class, "jumpPower", "field_110277_bt");
            yYGD.ZCsv = BwxQ(Entity.class, "FLAGS", "field_184240_ax");
            yYGD.SguM = BwxQ(BlockNote.class, "INSTRUMENTS", "field_176434_a");
            yYGD.chLR = BwxQ(GuiScreenHorseInventory.class, "horseInventory", "field_147029_w");
            yYGD.leJD = BwxQ(GuiScreenHorseInventory.class, "horseEntity", "field_147034_x");
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Field BwxQ(final Class clazz, final String... array) {
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final String s = array[i];
            try {
                final Field declaredField = clazz.getDeclaredField(s);
                declaredField.setAccessible(true);
                yYGD.TvMi.setInt(declaredField, declaredField.getModifiers() & 0xFFFFFFEF);
                return declaredField;
            }
            catch (NoSuchFieldException ex) {
                FMLLog.log.info("unable to find field: " + s);
                if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                    throw null;
                }
            }
            catch (IllegalAccessException ex2) {
                FMLLog.log.info("unable to make field changeable!");
            }
            ++i;
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        throw new IllegalStateException("Field with names: " + array + " not found!");
    }
    
    public static Method WOfx(final Class clazz, final String[] array, final Class<?>... array2) {
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final String s = array[i];
            try {
                final Method declaredMethod = clazz.getDeclaredMethod(s, (Class[])array2);
                declaredMethod.setAccessible(true);
                return declaredMethod;
            }
            catch (NoSuchMethodException ex) {
                FMLLog.log.info("unable to find method: " + s);
                ++i;
                if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                    throw null;
                }
                continue;
            }
            break;
        }
        throw new IllegalStateException("Method with names: " + array + " not found!");
    }
    
    public static double onRX() {
        try {
            return (double)yYGD.AiTO.get(Wrapper.mc.getRenderManager());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static double UuJK() {
        try {
            return (double)yYGD.UUbY.get(Wrapper.mc.getRenderManager());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static double LkVR() {
        try {
            return (double)yYGD.ojZx.get(Wrapper.mc.getRenderManager());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static float BqWL() {
        try {
            return (float)yYGD.JRdT.get(Wrapper.mc.getRenderManager());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static float uDFs() {
        try {
            return (float)yYGD.uPXs.get(Wrapper.mc.getRenderManager());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static Timer XgOL() {
        try {
            return (Timer)yYGD.TkEx.get(Wrapper.mc);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static ModelManager Cisv() {
        try {
            return (ModelManager)yYGD.xxOK.get(Wrapper.mc);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void AXbo() {
        try {
            yYGD.uwun.invoke(Wrapper.mc, new Object[0]);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static boolean dgWY(final KeyBinding keyBinding) {
        try {
            return (boolean)yYGD.WoEU.get(keyBinding);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void rvRL(final KeyBinding keyBinding, final boolean b) {
        try {
            yYGD.WoEU.set(keyBinding, b);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void gcRV(final CPacketPlayer cPacketPlayer, final float n) {
        try {
            yYGD.ldBn.set(cPacketPlayer, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void ZNGm(final CPacketPlayer cPacketPlayer, final float n) {
        try {
            yYGD.EXAg.set(cPacketPlayer, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void eyrN(final SPacketPlayerPosLook sPacketPlayerPosLook, final float n) {
        try {
            yYGD.dkBj.set(sPacketPlayerPosLook, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void YsmX(final SPacketPlayerPosLook sPacketPlayerPosLook, final float n) {
        try {
            yYGD.Fxwb.set(sPacketPlayerPosLook, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static Map<ResourceLocation, ITextureObject> PZBQ() {
        try {
            return (Map<ResourceLocation, ITextureObject>)yYGD.VBeC.get(Wrapper.mc.getTextureManager());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void YyXM(final CPacketPlayer cPacketPlayer, final boolean b) {
        try {
            yYGD.GCRA.set(cPacketPlayer, b);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void wtIT(final int n) {
        try {
            yYGD.YQVA.set(Wrapper.mc, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void olBF(final float n) {
        try {
            yYGD.MxtI.set(Wrapper.mc.player, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static float jdvA() {
        try {
            return (float)yYGD.PqBx.get(Wrapper.mc.playerController);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void wcsa(final float n) {
        try {
            yYGD.PqBx.set(Wrapper.mc.playerController, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static int OcMF() {
        try {
            return (int)yYGD.swXu.get(Wrapper.mc.playerController);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void hSxp(final int n) {
        try {
            yYGD.swXu.set(Wrapper.mc.playerController, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static int nvRH() {
        try {
            return (int)yYGD.CfFD.get(null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static IInventory kpCj(final GuiChest guiChest) {
        try {
            return (IInventory)yYGD.gIyZ.get(guiChest);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static IInventory xZaV(final GuiShulkerBox guiShulkerBox) {
        try {
            return (IInventory)yYGD.aSIs.get(guiShulkerBox);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void Sgwm(final SPacketExplosion sPacketExplosion, final float n) {
        try {
            yYGD.QzaI.set(sPacketExplosion, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void UPvs(final SPacketExplosion sPacketExplosion, final float n) {
        try {
            yYGD.mRhX.set(sPacketExplosion, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void yMfH(final SPacketExplosion sPacketExplosion, final float n) {
        try {
            yYGD.TRgG.set(sPacketExplosion, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static double Oixl(final CPacketPlayer cPacketPlayer) {
        try {
            return (double)yYGD.tDsP.get(cPacketPlayer);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void NXja(final CPacketPlayer cPacketPlayer, final double n) {
        try {
            yYGD.tDsP.set(cPacketPlayer, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static double hAHT(final CPacketVehicleMove cPacketVehicleMove) {
        try {
            return (double)yYGD.nbcg.get(cPacketVehicleMove);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void sXuu(final CPacketVehicleMove cPacketVehicleMove, final double n) {
        try {
            yYGD.nbcg.set(cPacketVehicleMove, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void VHzJ(final Session session) {
        try {
            yYGD.iouz.set(Wrapper.mc, session);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static DataParameter<Byte> clXd() {
        try {
            return (DataParameter<Byte>)yYGD.gEci.get(null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static boolean aVSG(final GuiButton guiButton) {
        try {
            return (boolean)yYGD.ehGs.get(guiButton);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void MNva(final GuiButton guiButton, final boolean b) {
        try {
            yYGD.ehGs.set(guiButton, b);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static Entity AXSL(final Entity entity) {
        try {
            return (Entity)yYGD.KsJA.get(entity);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void gyHE(final boolean b) {
        try {
            yYGD.jOQD.set(Wrapper.mc.player, b);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void MEtD(final int n) {
        try {
            yYGD.zoaL.set(Wrapper.mc.player, n);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void WRdN(final CPacketUpdateSign cPacketUpdateSign, final String[] array) {
        try {
            yYGD.DqJB.set(cPacketUpdateSign, array);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static void NyBC(final CPacketChatMessage cPacketChatMessage, final String s) {
        try {
            yYGD.eNKp.set(cPacketChatMessage, s);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static GuiScreen qgYJ(final GuiDisconnected guiDisconnected) {
        try {
            return (GuiScreen)yYGD.sbSC.get(guiDisconnected);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static ServerData gpTU(final GuiScreenServerList list) {
        try {
            return (ServerData)yYGD.GqcO.get(list);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static float yChz(final AbstractHorse abstractHorse) {
        try {
            return (float)yYGD.OByD.get(abstractHorse);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static DataParameter<Byte> jrMb(final Entity entity) {
        try {
            return (DataParameter<Byte>)yYGD.ZCsv.get(entity);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static List<SoundEvent> VVcl(final Block block) {
        try {
            return (List<SoundEvent>)yYGD.SguM.get(block);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static IInventory Qdhw(final GuiScreenHorseInventory guiScreenHorseInventory) {
        try {
            return (IInventory)yYGD.chLR.get(guiScreenHorseInventory);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    public static AbstractHorse sKEZ(final GuiScreenHorseInventory guiScreenHorseInventory) {
        try {
            return (AbstractHorse)yYGD.leJD.get(guiScreenHorseInventory);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }
    
    static {
        try {
            (yYGD.TvMi = Field.class.getDeclaredField("modifiers")).setAccessible(true);
            if (yYGD.\u2009\u2008\u2005\u200a\u2007) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
}
