package wwe;

import java.text.*;
import net.minecraftforge.client.event.*;
import wwe.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import wwe.modules.*;
import wwe.modules.render.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.resources.*;
import net.minecraft.potion.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import wwe.modules.combat.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;

public class XybC
{
    private static DecimalFormat MvaJ;
    private DecimalFormat yOZV;
    
    public XybC() {
        super();
        this.yOZV = new DecimalFormat("#");
    }
    
    @SubscribeEvent
    public void QccF(final RenderGameOverlayEvent.Post post) {
        if (post.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        final GuiIngame ingameGUI = Wrapper.mc.ingameGUI;
        GuiIngame.drawRect(scaledResolution.getScaledWidth() - 1, scaledResolution.getScaledHeight() - 1, scaledResolution.getScaledWidth() - 2, scaledResolution.getScaledHeight() - 2, 16777215);
        if (!Wrapper.mc.gameSettings.showDebugInfo) {
            this.lOdW();
            this.xrCE();
            this.dihd();
            this.rtlW();
            this.ntBz(scaledResolution);
        }
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).armorHUD.OnGi()) {
            this.JRPa();
        }
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN()) {
            MjBb.EPsz();
        }
    }
    
    public void qGPs(int n) {
        final ArrayList<qMIe> ijZl = vlwv.hNxW.ijZl;
        if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN()) {
            if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Comfortaa")) {
                ijZl.sort(XybC::PzRn);
            }
            if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Roboto")) {
                ijZl.sort(XybC::MSNW);
            }
            if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Cosmic Sans")) {
                ijZl.sort(XybC::RkaO);
            }
            if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Verdana")) {
                ijZl.sort(XybC::IFuU);
            }
            if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Custom") && bozE.Jfgf != null) {
                ijZl.sort(XybC::gvpI);
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
        }
        else {
            ijZl.sort(XybC::cbtR);
        }
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        int n2 = 1;
        final ArrayList<Integer[][]> list = new ArrayList<Integer[][]>();
        final ArrayList<Integer> list2 = new ArrayList<Integer>();
        vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).wkYe = 0;
        for (final qMIe qmIe : vlwv.hNxW.ijZl) {
            qmIe.qHNm();
            if (qmIe.IuiN() && !qmIe.jrFS(Category.GUI) && qmIe.sUHq()) {
                final Hud hud = vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class);
                hud.wkYe += 10;
                final int rgb = nuRU(n2 * (0xE7E4C7BCF422AAA1L ^ 0xE7E4C7BCE9EFCFA1L), 1.0f).getRGB();
                if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).rects.OnGi() || vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).coolerRects.OnGi()) {
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).oLhm()) {
                        gnNa.wQYt(0.0f, (float)(n - 1), (float)(4 + wKEb().getStringWidth(qmIe.Odlv())), (float)(n + 9), -1728053248);
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        gnNa.wQYt((float)(scaledResolution.getScaledWidth() - wKEb().getStringWidth(qmIe.Odlv()) - 4), (float)(n - 1), (float)scaledResolution.getScaledWidth(), (float)(n + 9), -1728053248);
                    }
                }
                if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).rects.OnGi()) {
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).oLhm()) {
                        final double n3 = 0.0;
                        final double n4 = n - 1;
                        final double n5 = 0.0 + vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).rectslength.OnGi();
                        final double n6 = n + 9;
                        int uUxj;
                        if (vlwv.hNxW.qHPF((Class<ColorList>)VWdz.class).IuiN()) {
                            uUxj = rgb;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else {
                            uUxj = qmIe.UUxj();
                        }
                        gnNa.OKXQ(n3, n4, n5, n6, uUxj);
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        final double n7 = scaledResolution.getScaledWidth() - vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).rectslength.OnGi();
                        final double n8 = n - 1;
                        final double n9 = scaledResolution.getScaledWidth();
                        final double n10 = n + 9;
                        int uUxj2;
                        if (vlwv.hNxW.qHPF((Class<ColorList>)VWdz.class).IuiN()) {
                            uUxj2 = rgb;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else {
                            uUxj2 = qmIe.UUxj();
                        }
                        gnNa.OKXQ(n7, n8, n9, n10, uUxj2);
                    }
                }
                if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).oLhm()) {
                    final ArrayList<Integer[][]> list3 = list;
                    final Integer[][] array = new Integer[2][];
                    final int n11 = 0;
                    final Integer[] array2 = { wKEb().getStringWidth(qmIe.Odlv()) + 3, null };
                    final int n12 = 1;
                    final int n13 = n;
                    int n14;
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).zDcj()) {
                        n14 = 0;
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        n14 = 3;
                    }
                    array2[n12] = n13 + n14;
                    array[n11] = array2;
                    final int n15 = 1;
                    final Integer[] array3 = { wKEb().getStringWidth(qmIe.Odlv()) + 5, null };
                    final int n16 = 1;
                    final int n17 = n;
                    int n18;
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).zDcj()) {
                        n18 = 9;
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        n18 = -9;
                    }
                    array3[n16] = n17 + n18;
                    array[n15] = array3;
                    list3.add(array);
                    if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                        throw null;
                    }
                }
                else {
                    final ArrayList<Integer[][]> list4 = list;
                    final Integer[][] array4 = new Integer[2][];
                    final int n19 = 0;
                    final Integer[] array5 = { scaledResolution.getScaledWidth() - wKEb().getStringWidth(qmIe.Odlv()) - 6, null };
                    final int n20 = 1;
                    final int n21 = n;
                    int n22;
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).arrayListMode.OnGi().equalsIgnoreCase("Top Right")) {
                        n22 = 0;
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        n22 = 3;
                    }
                    array5[n20] = n21 + n22;
                    array4[n19] = array5;
                    final int n23 = 1;
                    final Integer[] array6 = { scaledResolution.getScaledWidth() - wKEb().getStringWidth(qmIe.Odlv()) - 4, null };
                    final int n24 = 1;
                    final int n25 = n;
                    int n26;
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).arrayListMode.OnGi().equalsIgnoreCase("Top Right")) {
                        n26 = 9;
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        n26 = -9;
                    }
                    array6[n24] = n25 + n26;
                    array4[n23] = array6;
                    list4.add(array4);
                }
                if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).oLhm()) {
                    final String odlv = qmIe.Odlv();
                    final float n27 = 3.0f;
                    final float n28 = (float)n;
                    int uUxj3;
                    if (vlwv.hNxW.qHPF((Class<ColorList>)VWdz.class).IuiN()) {
                        uUxj3 = rgb;
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        uUxj3 = qmIe.UUxj();
                    }
                    eTim(odlv, n27, n28, uUxj3);
                    if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                        throw null;
                    }
                }
                else {
                    final String odlv2 = qmIe.Odlv();
                    final float n29 = (float)(scaledResolution.getScaledWidth() - wKEb().getStringWidth(qmIe.Odlv()) - 2);
                    final float n30 = (float)n;
                    int uUxj4;
                    if (vlwv.hNxW.qHPF((Class<ColorList>)VWdz.class).IuiN()) {
                        uUxj4 = rgb;
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        uUxj4 = qmIe.UUxj();
                    }
                    eTim(odlv2, n29, n30, uUxj4);
                }
                list2.add(qmIe.UUxj());
                int n31;
                if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).zDcj()) {
                    n31 = n + 10;
                    if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                        throw null;
                    }
                }
                else {
                    n31 = n - 10;
                }
                n = n31;
                ++n2;
            }
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        int scaledWidth = scaledResolution.getScaledWidth();
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).oLhm()) {
            scaledWidth = 0;
        }
        final int n32 = n;
        int n33;
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).zDcj()) {
            n33 = 0;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else {
            n33 = 3;
        }
        final int n34 = n32 + n33;
        list.add(new Integer[][] { { scaledWidth, n34 }, { scaledWidth, n34 } });
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).coolerRects.OnGi()) {
            int n35 = 0;
            int n36 = 0;
            Integer[] array7 = null;
            for (final Integer[][] array8 : list) {
                final int rgb2 = nuRU(n35 * (0xF38B1FE4B52FC11DL ^ 0xF38B1FE4A8E2A41DL), 1.0f).getRGB();
                final int n37 = array8[0][0] + 1;
                final int intValue = array8[0][1];
                int n38;
                if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).zDcj()) {
                    n38 = -1;
                    if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                        throw null;
                    }
                }
                else {
                    n38 = 6;
                }
                final int n39 = intValue + n38;
                final int intValue2 = array8[1][0];
                final int intValue3 = array8[1][1];
                int n40;
                if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).zDcj()) {
                    n40 = 0;
                    if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                        throw null;
                    }
                }
                else {
                    n40 = 7;
                }
                final int n41 = intValue3 + n40;
                int intValue4;
                if (vlwv.hNxW.qHPF((Class<ColorList>)VWdz.class).IuiN()) {
                    intValue4 = rgb2;
                    if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                        throw null;
                    }
                }
                else {
                    intValue4 = list2.get(n35);
                }
                Gui.drawRect(n37, n39, intValue2, n41, intValue4);
                if (array7 != null) {
                    final int max = Math.max(array7[0], array8[1][0]);
                    final int min = Math.min(array7[0], array8[1][0]);
                    ++n36;
                    final int n42 = min - 1;
                    final int intValue5 = array7[1];
                    int n43;
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).zDcj()) {
                        n43 = 0;
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        n43 = 8;
                    }
                    final int n44 = intValue5 + n43;
                    final int n45 = max;
                    final int intValue6 = array8[0][1];
                    int n46;
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).zDcj()) {
                        n46 = 0;
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        n46 = 5;
                    }
                    final int n47 = intValue6 + n46;
                    int n48;
                    if (vlwv.hNxW.qHPF((Class<ColorList>)VWdz.class).IuiN()) {
                        n48 = rgb2;
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else if (n36 == list.size() - 1) {
                        n48 = list2.get(n35);
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    else {
                        n48 = list2.get(n35 - 1);
                    }
                    Gui.drawRect(n42, n44, n45, n47, n48);
                }
                array7 = array8[1];
                if (n35 + 1 != list2.size()) {
                    ++n35;
                }
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
        }
    }
    
    private void JRPa() {
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        GL11.glPushMatrix();
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        boolean b;
        if (Wrapper.mc.player.isEntityAlive() && Wrapper.mc.player.isInsideOfMaterial(Material.WATER)) {
            b = true;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else {
            b = false;
        }
        final boolean b2 = b;
        int n = -3;
        int i = 3;
        while (i >= 0) {
            final ItemStack itemStack = (ItemStack)Wrapper.mc.player.inventory.armorInventory.get(i);
            if (itemStack == null) {
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
            else {
                list.add(itemStack);
            }
            --i;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        if (Wrapper.mc.player.getHeldItemMainhand() != null) {
            list.add(Wrapper.mc.player.getHeldItemMainhand());
        }
        if (Wrapper.mc.player.getHeldItemOffhand() != null) {
            list.add(Wrapper.mc.player.getHeldItemOffhand());
        }
        for (final ItemStack itemStack2 : list) {
            if (Wrapper.mc.world != null) {
                RenderHelper.enableGUIStandardItemLighting();
                n += 16;
            }
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            GlStateManager.enableBlend();
            Wrapper.mc.getRenderItem().zLevel = -150.0f;
            final RenderItem renderItem = Wrapper.mc.getRenderItem();
            final ItemStack itemStack3 = itemStack2;
            final int n2 = n + scaledResolution.getScaledWidth() / 2 - 4;
            final int scaledHeight = scaledResolution.getScaledHeight();
            int n3;
            if (Wrapper.mc.playerController.getCurrentGameType() == GameType.CREATIVE) {
                n3 = 42;
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
            else if (b2 && Wrapper.mc.playerController.getCurrentGameType() != GameType.CREATIVE) {
                n3 = 65;
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
            else {
                n3 = 55;
            }
            renderItem.renderItemAndEffectIntoGUI(itemStack3, n2, scaledHeight - n3);
            final RenderItem renderItem2 = Wrapper.mc.getRenderItem();
            final FontRenderer fontRenderer = Wrapper.fontRenderer;
            final ItemStack itemStack4 = itemStack2;
            final int n4 = n + scaledResolution.getScaledWidth() / 2 - 4;
            final int scaledHeight2 = scaledResolution.getScaledHeight();
            int n5;
            if (Wrapper.mc.playerController.getCurrentGameType() == GameType.CREATIVE) {
                n5 = 42;
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
            else if (b2 && Wrapper.mc.playerController.getCurrentGameType() != GameType.CREATIVE) {
                n5 = 65;
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
            else {
                n5 = 55;
            }
            renderItem2.renderItemOverlays(fontRenderer, itemStack4, n4, scaledHeight2 - n5);
            Wrapper.mc.getRenderItem().zLevel = 0.0f;
            GlStateManager.disableBlend();
            GlStateManager.scale(0.5, 0.5, 0.5);
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.scale(2.0f, 2.0f, 2.0f);
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        GL11.glPopMatrix();
    }
    
    public void eUwe(final int n, final int n2, final int n3) {
        final int n4 = n + n3;
        final int n5 = n2 + n3;
        GL11.glPushMatrix();
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarRotation.OnGi().equalsIgnoreCase("Radar")) {
            GL11.glTranslated((double)n4, (double)n5, 0.0);
            GL11.glRotated((double)(Wrapper.mc.player.rotationYaw + 180.0f), 0.0, 0.0, -1.0);
            GL11.glTranslated((double)(-n4), (double)(-n5), 0.0);
        }
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarLines.OnGi()) {
            gnNa.pABc(n4 - 0.5, n5 - n3 * 0.3, n4 + 0.5, n5 + n3 * 0.3, new Color(0, 0, 0, 175).getRGB());
            gnNa.pABc(n4 - n3 * 0.3, n5 - 0.5, n4 + n3 * 0.3, n5 + 0.5, new Color(0, 0, 0, 175).getRGB());
        }
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarMode.OnGi().equalsIgnoreCase("Circle")) {
            gnNa.QtkP(n4, n5, n3, new Color(0, 0, 0, 75).getRGB(), new Color(189, 0, 0).getRGB());
        }
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarMode.OnGi().equalsIgnoreCase("Square")) {
            gnNa.dYaT((float)(n4 - n3), (float)(n5 - n3), (float)(n4 + n3), (float)(n5 + n3), 1.0f, new Color(0, 0, 0, 75).getRGB(), new Color(189, 0, 0).getRGB());
        }
        eTim("X-", (float)(n + 2), (float)(n2 + n3), -1);
        eTim("X+", (float)(n + n3 + n3 - 10), (float)(n2 + n3), -1);
        eTim("Z-", (float)(n + n3), (float)(n2 + 1), -1);
        eTim("Z+", (float)(n + n3), (float)(n2 + n3 + n3 - 9), -1);
        for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
            Label_1167: {
                if ((entity instanceof EntityLivingBase || entity instanceof EntityEnderCrystal) && entity != Wrapper.mc.player && (!vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarMode.OnGi().equalsIgnoreCase("Circle") || FIwW.WwmM(entity) <= n3 - 1)) {
                    int n6 = n4 + (int)(entity.posX - Wrapper.mc.player.posX);
                    int n7 = n5 + (int)(entity.posZ - Wrapper.mc.player.posZ);
                    if (!vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarMode.OnGi().equalsIgnoreCase("Circle") || FIwW.WwmM(entity) >= n3 - 1) {}
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarMode.OnGi().equalsIgnoreCase("Square")) {
                        if (n6 > n4 + n3) {
                            n6 = n4 + n3;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else if (n6 < n4 - n3) {
                            n6 = n4 - n3;
                        }
                        if (n7 > n5 + n3) {
                            n7 = n5 + n3;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else if (n7 < n5 - n3) {
                            n7 = n5 - n3;
                        }
                    }
                    if (entity instanceof EntityPlayer) {
                        try {
                            Wrapper.mc.getTextureManager().bindTexture(((AbstractClientPlayer)entity).getLocationSkin());
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            GL11.glPushMatrix();
                            if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarRotation.OnGi().equalsIgnoreCase("Radar")) {
                                GL11.glTranslated((double)n6, (double)n7, 0.0);
                                GL11.glRotated((double)(-Wrapper.mc.player.rotationYaw + 180.0f), 0.0, 0.0, -1.0);
                                GL11.glTranslated((double)(-n6), (double)(-n7), 0.0);
                            }
                            final GuiIngame ingameGUI = Wrapper.mc.ingameGUI;
                            GuiIngame.drawScaledCustomSizeModalRect(n6 - 6, n7 - 6, 8.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
                            if (((AbstractClientPlayer)entity).isWearing(EnumPlayerModelParts.HAT)) {
                                final GuiIngame ingameGUI2 = Wrapper.mc.ingameGUI;
                                GuiIngame.drawScaledCustomSizeModalRect(n6 - 6, n7 - 6, 40.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
                            }
                            GL11.glPopMatrix();
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                            break Label_1167;
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                            break Label_1167;
                        }
                    }
                    final int n8 = 2;
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarEntities.OnGi().equalsIgnoreCase("Squares")) {
                        gnNa.wQYt((float)(n6 - n8 / 2), (float)(n7 - n8 / 2), (float)(n6 + n8 / 2), (float)(n7 + n8 / 2), this.JFLL(entity));
                    }
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarEntities.OnGi().equalsIgnoreCase("Circles")) {
                        gnNa.oQBi(n6, n7, n8, this.JFLL(entity));
                    }
                }
            }
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarChests.OnGi()) {
            for (final TileEntity tileEntity : Wrapper.mc.world.loadedTileEntityList) {
                if ((tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest || tileEntity instanceof TileEntityShulkerBox) && (!vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarMode.OnGi().equalsIgnoreCase("Circle") || FIwW.tLFP(tileEntity) <= n3 - 1)) {
                    int n9 = n4 + (int)(tileEntity.getPos().getX() - Wrapper.mc.player.posX);
                    int n10 = n5 + (int)(tileEntity.getPos().getZ() - Wrapper.mc.player.posZ);
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarMode.OnGi().equalsIgnoreCase("Circle")) {}
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarMode.OnGi().equalsIgnoreCase("Square")) {
                        if (n9 > n4 + n3) {
                            n9 = n4 + n3;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else if (n9 < n4 - n3) {
                            n9 = n4 - n3;
                        }
                        if (n10 > n5 + n3) {
                            n10 = n5 + n3;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else if (n10 < n5 - n3) {
                            n10 = n5 - n3;
                        }
                    }
                    final int n11 = 2;
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarEntities.OnGi().equalsIgnoreCase("Squares")) {
                        final float n12 = (float)(n9 - n11 / 2);
                        final float n13 = (float)(n10 - n11 / 2);
                        final float n14 = (float)(n9 + n11 / 2);
                        final float n15 = (float)(n10 + n11 / 2);
                        int n16;
                        if (tileEntity instanceof TileEntityChest) {
                            n16 = -19968;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else if (tileEntity instanceof TileEntityEnderChest) {
                            n16 = -7274241;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else if (tileEntity instanceof TileEntityShulkerBox) {
                            n16 = -65536;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else {
                            n16 = -1;
                        }
                        gnNa.wQYt(n12, n13, n14, n15, n16);
                    }
                    if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarEntities.OnGi().equalsIgnoreCase("Circles")) {
                        final int n17 = n9;
                        final int n18 = n10;
                        final double n19 = n11;
                        int n20;
                        if (tileEntity instanceof TileEntityChest) {
                            n20 = -19968;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else if (tileEntity instanceof TileEntityEnderChest) {
                            n20 = -7274241;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else if (tileEntity instanceof TileEntityShulkerBox) {
                            n20 = -65536;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else {
                            n20 = -1;
                        }
                        gnNa.oQBi(n17, n18, n19, n20);
                    }
                }
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
        }
        GL11.glPopMatrix();
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarIndicator.OnGi().equalsIgnoreCase("Minecraft")) {
            Wrapper.mc.getTextureManager().bindTexture(new ResourceLocation("textures/map/map_icons.png"));
            GL11.glPushMatrix();
            GL11.glScalef(0.2f, 0.2f, 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarRotation.OnGi().equalsIgnoreCase("Indicator")) {
                GL11.glTranslated((double)(n4 * 5), (double)(n5 * 5), 0.0);
                GL11.glRotated((double)(Wrapper.mc.player.rotationYaw + 180.0f), 0.0, 0.0, 1.0);
                GL11.glTranslated((double)(-(n4 * 5)), (double)(-(n5 * 5)), 0.0);
            }
            tdNb.wAtb((double)(n4 * 5 - 35), (double)(n5 * 5 - 30), 0, 0, 70, 70);
            GL11.glPopMatrix();
        }
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarIndicator.OnGi().equalsIgnoreCase("WWE")) {
            GL11.glPushMatrix();
            if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarRotation.OnGi().equalsIgnoreCase("Indicator")) {
                GL11.glTranslated((double)n4, (double)n5, 0.0);
                GL11.glRotated((double)(Wrapper.mc.player.rotationYaw + 180.0f), 0.0, 0.0, 1.0);
                GL11.glTranslated((double)(-n4), (double)(-n5), 0.0);
            }
            gnNa.zmwe(n4, n5, new Color(189, 0, 0).getRGB());
            GL11.glPopMatrix();
        }
    }
    
    public boolean fqlN(final Entity entity, final int n, final int n2, final int n3) {
        final double n4 = n - n3 - (int)(entity.posX - Wrapper.mc.player.posX) - n;
        final double n5 = n2 - n3 - (int)(entity.posZ - Wrapper.mc.player.posZ) - n2;
        final int n6 = n - n3;
        final int n7 = n2 - n3;
        return n4 + n < n6 + n3 && n4 + n > n6 - n3 && n5 + n2 < n7 + n3 && n5 + n2 > n7 - n3;
    }
    
    public boolean lDPQ(final TileEntity tileEntity, final int n, final int n2, final int n3) {
        final double n4 = n - n3 - (int)(tileEntity.getPos().getX() - Wrapper.mc.player.posX) - n;
        final double n5 = n2 - n3 - (int)(tileEntity.getPos().getZ() - Wrapper.mc.player.posZ) - n2;
        final int n6 = n - n3;
        final int n7 = n2 - n3;
        return n4 + n < n6 + n3 && n4 + n > n6 - n3 && n5 + n2 < n7 + n3 && n5 + n2 > n7 - n3;
    }
    
    public int JFLL(final Entity entity) {
        if (entity instanceof EntityPlayer && vlwv.Zqnd().UsVJ(entity.getName())) {
            return KrtY.DnMA(0x370B23EF1584EE36L ^ 0x370B23EF1E6F2C36L, 1.0f).getRGB();
        }
        if (entity instanceof EntityPlayer) {
            return -65536;
        }
        if (entity instanceof EntityAnimal) {
            return new Color(0, 230, 255).getRGB();
        }
        if (entity instanceof EntityMob) {
            return -16777216;
        }
        if (entity instanceof EntityLivingBase) {
            return new Color(255, 255, 0).getRGB();
        }
        if (entity instanceof EntityEnderCrystal) {
            return -65340;
        }
        return -1;
    }
    
    public void yAdi(int n) {
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        for (final PotionEffect potionEffect : Wrapper.mc.player.getActivePotionEffects()) {
            eTim(I18n.format(potionEffect.getEffectName(), new Object[0]) + " " + potionEffect.getAmplifier() + " : " + Potion.getPotionDurationString(potionEffect, 1.0f), (float)(scaledResolution.getScaledWidth() - wKEb().getStringWidth(I18n.format(potionEffect.getEffectName(), new Object[0]) + " " + potionEffect.getAmplifier() + " : " + Potion.getPotionDurationString(potionEffect, 1.0f)) - 2), (float)n, potionEffect.getPotion().getLiquidColor());
            int n2;
            if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).arrayListMode.OnGi().equalsIgnoreCase("Top Right")) {
                n2 = n - 10;
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
            else {
                n2 = n + 10;
            }
            n = n2;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
    }
    
    public void lOdW() {
        class luCt implements pSdr
        {
            final XybC hILD;
            
            luCt(final XybC hild) {
                this.hILD = hild;
                super();
            }
            
            @Override
            public int Gsgl() {
                return 75;
            }
            
            @Override
            public void Hrqs(final int n) {
                vlwv.VFOG.zeZm(3, n);
            }
        }
        class eZOX implements pSdr
        {
            final XybC hILD;
            
            eZOX(final XybC hild) {
                this.hILD = hild;
                super();
            }
            
            @Override
            public int Gsgl() {
                return vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).wkYe + 2;
            }
            
            @Override
            public void Hrqs(final int n) {
                this.hILD.qGPs(n);
            }
        }
        class rJZq implements pSdr
        {
            final XybC hILD;
            
            rJZq(final XybC hild) {
                this.hILD = hild;
                super();
            }
            
            @Override
            public int Gsgl() {
                return (int)(vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarScale.OnGi() * 2.0 + 3.0);
            }
            
            @Override
            public void Hrqs(final int n) {
                this.hILD.eUwe(3, n, vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarScale.OnGi().intValue());
            }
        }
        class SxyY implements pSdr
        {
            final XybC hILD;
            
            SxyY(final XybC hild) {
                this.hILD = hild;
                super();
            }
            
            @Override
            public int Gsgl() {
                return vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).pxFw;
            }
            
            @Override
            public void Hrqs(final int n) {
                this.hILD.rZnU(3, n);
            }
        }
        class CXWa implements pSdr
        {
            final XybC hILD;
            
            CXWa(final XybC hild) {
                this.hILD = hild;
                super();
            }
            
            @Override
            public int Gsgl() {
                return 0;
            }
            
            @Override
            public void Hrqs(final int n) {
                this.hILD.swMA(n + 15);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: new             Lnet/minecraft/client/gui/ScaledResolution;
        //     7: dup            
        //     8: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    11: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;)V
        //    14: astore_1       
        //    15: new             Ljava/util/ArrayList;
        //    18: dup            
        //    19: invokespecial   java/util/ArrayList.<init>:()V
        //    22: astore_2       
        //    23: ldc             1
        //    25: istore_3       
        //    26: aload_0        
        //    27: invokevirtual   wwe/XybC.UNzQ:()Lwwe/Eiby;
        //    30: ifnull          42
        //    33: aload_2        
        //    34: aload_0        
        //    35: invokevirtual   wwe/XybC.UNzQ:()Lwwe/Eiby;
        //    38: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //    41: pop            
        //    42: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    45: ldc             Lwwe/Ijoo;.class
        //    47: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //    50: checkcast       Lwwe/modules/render/Hud;
        //    53: getfield        wwe/modules/render/Hud.information:Lwwe/utils/ModeValue;
        //    56: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //    59: checkcast       Ljava/lang/String;
        //    62: ldc_w           "Top Left"
        //    65: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //    68: ifeq            256
        //    71: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    74: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    77: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //    80: ifeq            97
        //    83: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    86: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    89: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //    92: ldc             1
        //    94: if_icmpne       129
        //    97: aload_0        
        //    98: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   101: ifnull          113
        //   104: aload_2        
        //   105: aload_0        
        //   106: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   109: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   112: pop            
        //   113: aload_0        
        //   114: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   117: ifnull          129
        //   120: aload_2        
        //   121: aload_0        
        //   122: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   125: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   128: pop            
        //   129: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //   132: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   135: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //   138: ldc_w           -1
        //   141: if_icmpne       176
        //   144: aload_0        
        //   145: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   148: ifnull          160
        //   151: aload_2        
        //   152: aload_0        
        //   153: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   156: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   159: pop            
        //   160: aload_0        
        //   161: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   164: ifnull          176
        //   167: aload_2        
        //   168: aload_0        
        //   169: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   172: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   175: pop            
        //   176: aload_0        
        //   177: invokevirtual   wwe/XybC.SDNi:()Lwwe/Eiby;
        //   180: ifnull          192
        //   183: aload_2        
        //   184: aload_0        
        //   185: invokevirtual   wwe/XybC.SDNi:()Lwwe/Eiby;
        //   188: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   191: pop            
        //   192: aload_0        
        //   193: invokevirtual   wwe/XybC.Rlmo:()Lwwe/Eiby;
        //   196: ifnull          208
        //   199: aload_2        
        //   200: aload_0        
        //   201: invokevirtual   wwe/XybC.Rlmo:()Lwwe/Eiby;
        //   204: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   207: pop            
        //   208: aload_0        
        //   209: invokevirtual   wwe/XybC.uocS:()Lwwe/Eiby;
        //   212: ifnull          224
        //   215: aload_2        
        //   216: aload_0        
        //   217: invokevirtual   wwe/XybC.uocS:()Lwwe/Eiby;
        //   220: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   223: pop            
        //   224: aload_0        
        //   225: invokevirtual   wwe/XybC.lHll:()Lwwe/Eiby;
        //   228: ifnull          240
        //   231: aload_2        
        //   232: aload_0        
        //   233: invokevirtual   wwe/XybC.lHll:()Lwwe/Eiby;
        //   236: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   239: pop            
        //   240: aload_0        
        //   241: invokevirtual   wwe/XybC.VeEy:()Lwwe/Eiby;
        //   244: ifnull          256
        //   247: aload_2        
        //   248: aload_0        
        //   249: invokevirtual   wwe/XybC.VeEy:()Lwwe/Eiby;
        //   252: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   255: pop            
        //   256: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   259: ldc             Lwwe/Ijoo;.class
        //   261: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   264: checkcast       Lwwe/modules/render/Hud;
        //   267: invokevirtual   wwe/modules/render/Hud.IuiN:()Z
        //   270: ifeq            327
        //   273: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   276: ldc             Lwwe/Ijoo;.class
        //   278: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   281: checkcast       Lwwe/modules/render/Hud;
        //   284: getfield        wwe/modules/render/Hud.tabGui:Lwwe/utils/BooleanValue;
        //   287: invokevirtual   wwe/utils/BooleanValue.OnGi:()Ljava/lang/Object;
        //   290: checkcast       Ljava/lang/Boolean;
        //   293: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   296: ifeq            327
        //   299: new             Lwwe/luCt;
        //   302: dup            
        //   303: aload_0        
        //   304: invokespecial   wwe/luCt.<init>:(Lwwe/XybC;)V
        //   307: astore          4
        //   309: aload_2        
        //   310: new             Lwwe/Eiby;
        //   313: dup            
        //   314: aconst_null    
        //   315: ldc_w           0
        //   318: aload           4
        //   320: invokespecial   wwe/Eiby.<init>:(Ljava/lang/String;ILwwe/pSdr;)V
        //   323: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   326: pop            
        //   327: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   330: ldc             Lwwe/Ijoo;.class
        //   332: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   335: checkcast       Lwwe/modules/render/Hud;
        //   338: invokevirtual   wwe/modules/render/Hud.IuiN:()Z
        //   341: ifeq            427
        //   344: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   347: ldc             Lwwe/Ijoo;.class
        //   349: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   352: checkcast       Lwwe/modules/render/Hud;
        //   355: getfield        wwe/modules/render/Hud.arrayList:Lwwe/utils/BooleanValue;
        //   358: invokevirtual   wwe/utils/BooleanValue.OnGi:()Ljava/lang/Object;
        //   361: checkcast       Ljava/lang/Boolean;
        //   364: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   367: ifeq            427
        //   370: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   373: ldc             Lwwe/Ijoo;.class
        //   375: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   378: checkcast       Lwwe/modules/render/Hud;
        //   381: getfield        wwe/modules/render/Hud.arrayListMode:Lwwe/utils/ModeValue;
        //   384: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //   387: checkcast       Ljava/lang/String;
        //   390: ldc_w           "Top Left"
        //   393: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   396: ifeq            427
        //   399: new             Lwwe/eZOX;
        //   402: dup            
        //   403: aload_0        
        //   404: invokespecial   wwe/eZOX.<init>:(Lwwe/XybC;)V
        //   407: astore          4
        //   409: aload_2        
        //   410: new             Lwwe/Eiby;
        //   413: dup            
        //   414: aconst_null    
        //   415: ldc_w           0
        //   418: aload           4
        //   420: invokespecial   wwe/Eiby.<init>:(Ljava/lang/String;ILwwe/pSdr;)V
        //   423: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   426: pop            
        //   427: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   430: ldc             Lwwe/Ijoo;.class
        //   432: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   435: checkcast       Lwwe/modules/render/Hud;
        //   438: invokevirtual   wwe/modules/render/Hud.IuiN:()Z
        //   441: ifeq            498
        //   444: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   447: ldc             Lwwe/Ijoo;.class
        //   449: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   452: checkcast       Lwwe/modules/render/Hud;
        //   455: getfield        wwe/modules/render/Hud.radar:Lwwe/utils/BooleanValue;
        //   458: invokevirtual   wwe/utils/BooleanValue.OnGi:()Ljava/lang/Object;
        //   461: checkcast       Ljava/lang/Boolean;
        //   464: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   467: ifeq            498
        //   470: new             Lwwe/rJZq;
        //   473: dup            
        //   474: aload_0        
        //   475: invokespecial   wwe/rJZq.<init>:(Lwwe/XybC;)V
        //   478: astore          4
        //   480: aload_2        
        //   481: new             Lwwe/Eiby;
        //   484: dup            
        //   485: aconst_null    
        //   486: ldc_w           0
        //   489: aload           4
        //   491: invokespecial   wwe/Eiby.<init>:(Ljava/lang/String;ILwwe/pSdr;)V
        //   494: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   497: pop            
        //   498: aload_0        
        //   499: invokevirtual   wwe/XybC.GQOK:()Lwwe/Eiby;
        //   502: ifnull          514
        //   505: aload_2        
        //   506: aload_0        
        //   507: invokevirtual   wwe/XybC.GQOK:()Lwwe/Eiby;
        //   510: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   513: pop            
        //   514: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   517: ldc             Lwwe/Ijoo;.class
        //   519: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   522: checkcast       Lwwe/modules/render/Hud;
        //   525: invokevirtual   wwe/modules/render/Hud.IuiN:()Z
        //   528: ifeq            585
        //   531: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   534: ldc             Lwwe/Ijoo;.class
        //   536: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   539: checkcast       Lwwe/modules/render/Hud;
        //   542: getfield        wwe/modules/render/Hud.entities:Lwwe/utils/BooleanValue;
        //   545: invokevirtual   wwe/utils/BooleanValue.OnGi:()Ljava/lang/Object;
        //   548: checkcast       Ljava/lang/Boolean;
        //   551: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   554: ifeq            585
        //   557: new             Lwwe/SxyY;
        //   560: dup            
        //   561: aload_0        
        //   562: invokespecial   wwe/SxyY.<init>:(Lwwe/XybC;)V
        //   565: astore          4
        //   567: aload_2        
        //   568: new             Lwwe/Eiby;
        //   571: dup            
        //   572: aconst_null    
        //   573: ldc_w           0
        //   576: aload           4
        //   578: invokespecial   wwe/Eiby.<init>:(Ljava/lang/String;ILwwe/pSdr;)V
        //   581: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   584: pop            
        //   585: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   588: ldc_w           Lwwe/fhLu;.class
        //   591: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   594: checkcast       Lwwe/modules/combat/CombatInfo;
        //   597: invokevirtual   wwe/fhLu.IuiN:()Z
        //   600: ifeq            631
        //   603: new             Lwwe/CXWa;
        //   606: dup            
        //   607: aload_0        
        //   608: invokespecial   wwe/CXWa.<init>:(Lwwe/XybC;)V
        //   611: astore          4
        //   613: aload_2        
        //   614: new             Lwwe/Eiby;
        //   617: dup            
        //   618: aconst_null    
        //   619: ldc_w           0
        //   622: aload           4
        //   624: invokespecial   wwe/Eiby.<init>:(Ljava/lang/String;ILwwe/pSdr;)V
        //   627: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   630: pop            
        //   631: aload_2        
        //   632: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   635: astore          4
        //   637: aload           4
        //   639: invokevirtual   java/util/Iterator.hasNext:()Z
        //   642: ifeq            741
        //   645: aload           4
        //   647: invokevirtual   java/util/Iterator.next:()Ljava/lang/Object;
        //   650: checkcast       Lwwe/Eiby;
        //   653: astore          5
        //   655: aload           5
        //   657: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   660: ifnull          685
        //   663: aload           5
        //   665: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   668: iload_3        
        //   669: invokevirtual   wwe/pSdr.Hrqs:(I)V
        //   672: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   675: ifeq            703
        //   678: aconst_null    
        //   679: athrow         
        //   680: nop            
        //   681: nop            
        //   682: nop            
        //   683: nop            
        //   684: athrow         
        //   685: aload           5
        //   687: getfield        wwe/Eiby.EMiZ:Ljava/lang/String;
        //   690: ldc_w           3.0
        //   693: iload_3        
        //   694: i2f            
        //   695: aload           5
        //   697: getfield        wwe/Eiby.wquB:I
        //   700: invokestatic    wwe/XybC.eTim:(Ljava/lang/String;FFI)V
        //   703: iload_3        
        //   704: aload           5
        //   706: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   709: ifnull          728
        //   712: aload           5
        //   714: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   717: invokevirtual   wwe/pSdr.Gsgl:()I
        //   720: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   723: ifeq            731
        //   726: aconst_null    
        //   727: athrow         
        //   728: ldc_w           10
        //   731: iadd           
        //   732: istore_3       
        //   733: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   736: ifeq            637
        //   739: aconst_null    
        //   740: athrow         
        //   741: return         
        //    StackMapTable: 00 1A FF 00 03 00 07 07 00 02 00 00 00 00 00 01 00 00 00 FF 00 25 00 07 07 00 02 07 00 49 07 00 DC 01 00 00 01 00 00 36 0F 0F 1E 0F 0F 0F 0F 0F 0F FB 00 46 FB 00 63 FB 00 46 0F FB 00 46 2D FF 00 05 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 00 01 00 00 FF 00 2A 00 00 00 01 07 00 31 FF 00 04 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 07 03 9A 01 00 00 11 58 01 FF 00 02 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 07 03 9A 01 00 02 01 01 FF 00 09 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 00 01 00 00
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void xrCE() {
        class Oohy implements pSdr
        {
            final XybC hILD;
            
            Oohy(final XybC hild) {
                this.hILD = hild;
                super();
            }
            
            @Override
            public int Gsgl() {
                return Wrapper.mc.player.getActivePotionEffects().size() * 10;
            }
            
            @Override
            public void Hrqs(final int n) {
                this.hILD.yAdi(n);
            }
        }
        class gHEy implements pSdr
        {
            final XybC hILD;
            
            gHEy(final XybC hild) {
                this.hILD = hild;
                super();
            }
            
            @Override
            public int Gsgl() {
                return vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).wkYe + 2;
            }
            
            @Override
            public void Hrqs(final int n) {
                this.hILD.qGPs(n);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: new             Lnet/minecraft/client/gui/ScaledResolution;
        //     7: dup            
        //     8: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    11: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;)V
        //    14: astore_1       
        //    15: new             Ljava/util/ArrayList;
        //    18: dup            
        //    19: invokespecial   java/util/ArrayList.<init>:()V
        //    22: astore_2       
        //    23: ldc             1
        //    25: istore_3       
        //    26: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    29: ldc             Lwwe/Ijoo;.class
        //    31: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //    34: checkcast       Lwwe/modules/render/Hud;
        //    37: invokevirtual   wwe/modules/render/Hud.IuiN:()Z
        //    40: ifeq            126
        //    43: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    46: ldc             Lwwe/Ijoo;.class
        //    48: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //    51: checkcast       Lwwe/modules/render/Hud;
        //    54: getfield        wwe/modules/render/Hud.potions:Lwwe/utils/BooleanValue;
        //    57: invokevirtual   wwe/utils/BooleanValue.OnGi:()Ljava/lang/Object;
        //    60: checkcast       Ljava/lang/Boolean;
        //    63: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    66: ifeq            126
        //    69: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    72: ldc             Lwwe/Ijoo;.class
        //    74: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //    77: checkcast       Lwwe/modules/render/Hud;
        //    80: getfield        wwe/modules/render/Hud.arrayListMode:Lwwe/utils/ModeValue;
        //    83: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //    86: checkcast       Ljava/lang/String;
        //    89: ldc_w           "Top Right"
        //    92: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //    95: ifne            126
        //    98: new             Lwwe/Oohy;
        //   101: dup            
        //   102: aload_0        
        //   103: invokespecial   wwe/Oohy.<init>:(Lwwe/XybC;)V
        //   106: astore          4
        //   108: aload_2        
        //   109: new             Lwwe/Eiby;
        //   112: dup            
        //   113: aconst_null    
        //   114: ldc_w           0
        //   117: aload           4
        //   119: invokespecial   wwe/Eiby.<init>:(Ljava/lang/String;ILwwe/pSdr;)V
        //   122: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   125: pop            
        //   126: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   129: ldc             Lwwe/Ijoo;.class
        //   131: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   134: checkcast       Lwwe/modules/render/Hud;
        //   137: getfield        wwe/modules/render/Hud.information:Lwwe/utils/ModeValue;
        //   140: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //   143: checkcast       Ljava/lang/String;
        //   146: ldc_w           "Top Right"
        //   149: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   152: ifeq            340
        //   155: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //   158: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   161: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //   164: ifeq            181
        //   167: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //   170: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   173: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //   176: ldc             1
        //   178: if_icmpne       213
        //   181: aload_0        
        //   182: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   185: ifnull          197
        //   188: aload_2        
        //   189: aload_0        
        //   190: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   193: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   196: pop            
        //   197: aload_0        
        //   198: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   201: ifnull          213
        //   204: aload_2        
        //   205: aload_0        
        //   206: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   209: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   212: pop            
        //   213: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //   216: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   219: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //   222: ldc_w           -1
        //   225: if_icmpne       260
        //   228: aload_0        
        //   229: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   232: ifnull          244
        //   235: aload_2        
        //   236: aload_0        
        //   237: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   240: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   243: pop            
        //   244: aload_0        
        //   245: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   248: ifnull          260
        //   251: aload_2        
        //   252: aload_0        
        //   253: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   256: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   259: pop            
        //   260: aload_0        
        //   261: invokevirtual   wwe/XybC.SDNi:()Lwwe/Eiby;
        //   264: ifnull          276
        //   267: aload_2        
        //   268: aload_0        
        //   269: invokevirtual   wwe/XybC.SDNi:()Lwwe/Eiby;
        //   272: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   275: pop            
        //   276: aload_0        
        //   277: invokevirtual   wwe/XybC.Rlmo:()Lwwe/Eiby;
        //   280: ifnull          292
        //   283: aload_2        
        //   284: aload_0        
        //   285: invokevirtual   wwe/XybC.Rlmo:()Lwwe/Eiby;
        //   288: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   291: pop            
        //   292: aload_0        
        //   293: invokevirtual   wwe/XybC.uocS:()Lwwe/Eiby;
        //   296: ifnull          308
        //   299: aload_2        
        //   300: aload_0        
        //   301: invokevirtual   wwe/XybC.uocS:()Lwwe/Eiby;
        //   304: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   307: pop            
        //   308: aload_0        
        //   309: invokevirtual   wwe/XybC.lHll:()Lwwe/Eiby;
        //   312: ifnull          324
        //   315: aload_2        
        //   316: aload_0        
        //   317: invokevirtual   wwe/XybC.lHll:()Lwwe/Eiby;
        //   320: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   323: pop            
        //   324: aload_0        
        //   325: invokevirtual   wwe/XybC.VeEy:()Lwwe/Eiby;
        //   328: ifnull          340
        //   331: aload_2        
        //   332: aload_0        
        //   333: invokevirtual   wwe/XybC.VeEy:()Lwwe/Eiby;
        //   336: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   339: pop            
        //   340: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   343: ldc             Lwwe/Ijoo;.class
        //   345: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   348: checkcast       Lwwe/modules/render/Hud;
        //   351: invokevirtual   wwe/modules/render/Hud.IuiN:()Z
        //   354: ifeq            440
        //   357: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   360: ldc             Lwwe/Ijoo;.class
        //   362: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   365: checkcast       Lwwe/modules/render/Hud;
        //   368: getfield        wwe/modules/render/Hud.arrayList:Lwwe/utils/BooleanValue;
        //   371: invokevirtual   wwe/utils/BooleanValue.OnGi:()Ljava/lang/Object;
        //   374: checkcast       Ljava/lang/Boolean;
        //   377: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   380: ifeq            440
        //   383: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   386: ldc             Lwwe/Ijoo;.class
        //   388: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   391: checkcast       Lwwe/modules/render/Hud;
        //   394: getfield        wwe/modules/render/Hud.arrayListMode:Lwwe/utils/ModeValue;
        //   397: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //   400: checkcast       Ljava/lang/String;
        //   403: ldc_w           "Top Right"
        //   406: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   409: ifeq            440
        //   412: new             Lwwe/gHEy;
        //   415: dup            
        //   416: aload_0        
        //   417: invokespecial   wwe/gHEy.<init>:(Lwwe/XybC;)V
        //   420: astore          4
        //   422: aload_2        
        //   423: new             Lwwe/Eiby;
        //   426: dup            
        //   427: aconst_null    
        //   428: ldc_w           0
        //   431: aload           4
        //   433: invokespecial   wwe/Eiby.<init>:(Ljava/lang/String;ILwwe/pSdr;)V
        //   436: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   439: pop            
        //   440: aload_2        
        //   441: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   444: astore          4
        //   446: aload           4
        //   448: invokevirtual   java/util/Iterator.hasNext:()Z
        //   451: ifeq            567
        //   454: aload           4
        //   456: invokevirtual   java/util/Iterator.next:()Ljava/lang/Object;
        //   459: checkcast       Lwwe/Eiby;
        //   462: astore          5
        //   464: aload           5
        //   466: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   469: ifnull          494
        //   472: aload           5
        //   474: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   477: iload_3        
        //   478: invokevirtual   wwe/pSdr.Hrqs:(I)V
        //   481: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   484: ifeq            529
        //   487: aconst_null    
        //   488: athrow         
        //   489: nop            
        //   490: nop            
        //   491: nop            
        //   492: nop            
        //   493: athrow         
        //   494: aload           5
        //   496: getfield        wwe/Eiby.EMiZ:Ljava/lang/String;
        //   499: aload_1        
        //   500: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   503: invokestatic    wwe/XybC.wKEb:()Lnet/minecraft/client/gui/FontRenderer;
        //   506: aload           5
        //   508: getfield        wwe/Eiby.EMiZ:Ljava/lang/String;
        //   511: invokevirtual   net/minecraft/client/gui/FontRenderer.getStringWidth:(Ljava/lang/String;)I
        //   514: isub           
        //   515: ldc             2
        //   517: isub           
        //   518: i2f            
        //   519: iload_3        
        //   520: i2f            
        //   521: aload           5
        //   523: getfield        wwe/Eiby.wquB:I
        //   526: invokestatic    wwe/XybC.eTim:(Ljava/lang/String;FFI)V
        //   529: iload_3        
        //   530: aload           5
        //   532: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   535: ifnull          554
        //   538: aload           5
        //   540: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   543: invokevirtual   wwe/pSdr.Gsgl:()I
        //   546: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   549: ifeq            557
        //   552: aconst_null    
        //   553: athrow         
        //   554: ldc_w           10
        //   557: iadd           
        //   558: istore_3       
        //   559: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   562: ifeq            446
        //   565: aconst_null    
        //   566: athrow         
        //   567: return         
        //    StackMapTable: 00 15 FF 00 03 00 07 07 00 02 00 00 00 00 00 01 00 00 00 FF 00 79 00 07 07 00 02 07 00 49 07 00 DC 01 00 00 01 00 00 36 0F 0F 1E 0F 0F 0F 0F 0F 0F FB 00 63 FF 00 05 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 00 01 00 00 FF 00 2A 00 00 00 01 07 00 31 FF 00 04 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 07 03 9A 01 00 00 22 58 01 FF 00 02 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 07 03 9A 01 00 02 01 01 FF 00 09 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 00 01 00 00
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void dihd() {
        class pnDm implements pSdr
        {
            final XybC hILD;
            
            pnDm(final XybC hild) {
                this.hILD = hild;
                super();
            }
            
            @Override
            public int Gsgl() {
                return vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).wkYe + 2;
            }
            
            @Override
            public void Hrqs(final int n) {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     3: return         
                //     4: aload_0        
                //     5: getfield        wwe/pnDm.hILD:Lwwe/XybC;
                //     8: iload_1        
                //     9: ldc             1
                //    11: iadd           
                //    12: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
                //    15: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
                //    18: instanceof      Lnet/minecraft/client/gui/GuiChat;
                //    21: ifeq            34
                //    24: ldc             1
                //    26: getstatic       wwe/pnDm.\u2006\u200e\u2007:Z
                //    29: ifeq            36
                //    32: aconst_null    
                //    33: athrow         
                //    34: ldc             0
                //    36: isub           
                //    37: invokevirtual   wwe/XybC.qGPs:(I)V
                //    40: return         
                //    StackMapTable: 00 04 FC 00 03 01 00 FF 00 1D 00 03 07 00 02 01 01 00 02 07 00 08 01 FF 00 01 00 03 07 00 02 01 01 00 03 07 00 08 01 01
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: new             Lnet/minecraft/client/gui/ScaledResolution;
        //     7: dup            
        //     8: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    11: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;)V
        //    14: astore_1       
        //    15: new             Ljava/util/ArrayList;
        //    18: dup            
        //    19: invokespecial   java/util/ArrayList.<init>:()V
        //    22: astore_2       
        //    23: aload_1        
        //    24: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledHeight:()I
        //    27: ldc_w           10
        //    30: isub           
        //    31: istore_3       
        //    32: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    35: ldc             Lwwe/Ijoo;.class
        //    37: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //    40: checkcast       Lwwe/modules/render/Hud;
        //    43: getfield        wwe/modules/render/Hud.information:Lwwe/utils/ModeValue;
        //    46: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //    49: checkcast       Ljava/lang/String;
        //    52: ldc_w           "Bottom Left"
        //    55: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //    58: ifeq            246
        //    61: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    64: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    67: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //    70: ifeq            87
        //    73: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    76: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    79: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //    82: ldc             1
        //    84: if_icmpne       119
        //    87: aload_0        
        //    88: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //    91: ifnull          103
        //    94: aload_2        
        //    95: aload_0        
        //    96: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //    99: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   102: pop            
        //   103: aload_0        
        //   104: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   107: ifnull          119
        //   110: aload_2        
        //   111: aload_0        
        //   112: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   115: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   118: pop            
        //   119: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //   122: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   125: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //   128: ldc_w           -1
        //   131: if_icmpne       166
        //   134: aload_0        
        //   135: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   138: ifnull          150
        //   141: aload_2        
        //   142: aload_0        
        //   143: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   146: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   149: pop            
        //   150: aload_0        
        //   151: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   154: ifnull          166
        //   157: aload_2        
        //   158: aload_0        
        //   159: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   162: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   165: pop            
        //   166: aload_0        
        //   167: invokevirtual   wwe/XybC.SDNi:()Lwwe/Eiby;
        //   170: ifnull          182
        //   173: aload_2        
        //   174: aload_0        
        //   175: invokevirtual   wwe/XybC.SDNi:()Lwwe/Eiby;
        //   178: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   181: pop            
        //   182: aload_0        
        //   183: invokevirtual   wwe/XybC.Rlmo:()Lwwe/Eiby;
        //   186: ifnull          198
        //   189: aload_2        
        //   190: aload_0        
        //   191: invokevirtual   wwe/XybC.Rlmo:()Lwwe/Eiby;
        //   194: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   197: pop            
        //   198: aload_0        
        //   199: invokevirtual   wwe/XybC.uocS:()Lwwe/Eiby;
        //   202: ifnull          214
        //   205: aload_2        
        //   206: aload_0        
        //   207: invokevirtual   wwe/XybC.uocS:()Lwwe/Eiby;
        //   210: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   213: pop            
        //   214: aload_0        
        //   215: invokevirtual   wwe/XybC.lHll:()Lwwe/Eiby;
        //   218: ifnull          230
        //   221: aload_2        
        //   222: aload_0        
        //   223: invokevirtual   wwe/XybC.lHll:()Lwwe/Eiby;
        //   226: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   229: pop            
        //   230: aload_0        
        //   231: invokevirtual   wwe/XybC.VeEy:()Lwwe/Eiby;
        //   234: ifnull          246
        //   237: aload_2        
        //   238: aload_0        
        //   239: invokevirtual   wwe/XybC.VeEy:()Lwwe/Eiby;
        //   242: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   245: pop            
        //   246: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   249: ldc             Lwwe/Ijoo;.class
        //   251: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   254: checkcast       Lwwe/modules/render/Hud;
        //   257: invokevirtual   wwe/modules/render/Hud.IuiN:()Z
        //   260: ifeq            346
        //   263: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   266: ldc             Lwwe/Ijoo;.class
        //   268: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   271: checkcast       Lwwe/modules/render/Hud;
        //   274: getfield        wwe/modules/render/Hud.arrayList:Lwwe/utils/BooleanValue;
        //   277: invokevirtual   wwe/utils/BooleanValue.OnGi:()Ljava/lang/Object;
        //   280: checkcast       Ljava/lang/Boolean;
        //   283: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   286: ifeq            346
        //   289: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   292: ldc             Lwwe/Ijoo;.class
        //   294: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   297: checkcast       Lwwe/modules/render/Hud;
        //   300: getfield        wwe/modules/render/Hud.arrayListMode:Lwwe/utils/ModeValue;
        //   303: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //   306: checkcast       Ljava/lang/String;
        //   309: ldc_w           "Bottom Left"
        //   312: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   315: ifeq            346
        //   318: new             Lwwe/pnDm;
        //   321: dup            
        //   322: aload_0        
        //   323: invokespecial   wwe/pnDm.<init>:(Lwwe/XybC;)V
        //   326: astore          4
        //   328: aload_2        
        //   329: new             Lwwe/Eiby;
        //   332: dup            
        //   333: aconst_null    
        //   334: ldc_w           0
        //   337: aload           4
        //   339: invokespecial   wwe/Eiby.<init>:(Ljava/lang/String;ILwwe/pSdr;)V
        //   342: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   345: pop            
        //   346: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //   349: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //   352: instanceof      Lnet/minecraft/client/gui/GuiChat;
        //   355: ifeq            361
        //   358: iinc            3, -13
        //   361: aload_2        
        //   362: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   365: astore          4
        //   367: aload           4
        //   369: invokevirtual   java/util/Iterator.hasNext:()Z
        //   372: ifeq            471
        //   375: aload           4
        //   377: invokevirtual   java/util/Iterator.next:()Ljava/lang/Object;
        //   380: checkcast       Lwwe/Eiby;
        //   383: astore          5
        //   385: aload           5
        //   387: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   390: ifnull          415
        //   393: aload           5
        //   395: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   398: iload_3        
        //   399: invokevirtual   wwe/pSdr.Hrqs:(I)V
        //   402: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   405: ifeq            433
        //   408: aconst_null    
        //   409: athrow         
        //   410: nop            
        //   411: nop            
        //   412: nop            
        //   413: nop            
        //   414: athrow         
        //   415: aload           5
        //   417: getfield        wwe/Eiby.EMiZ:Ljava/lang/String;
        //   420: ldc_w           3.0
        //   423: iload_3        
        //   424: i2f            
        //   425: aload           5
        //   427: getfield        wwe/Eiby.wquB:I
        //   430: invokestatic    wwe/XybC.eTim:(Ljava/lang/String;FFI)V
        //   433: iload_3        
        //   434: aload           5
        //   436: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   439: ifnull          458
        //   442: aload           5
        //   444: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   447: invokevirtual   wwe/pSdr.Gsgl:()I
        //   450: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   453: ifeq            461
        //   456: aconst_null    
        //   457: athrow         
        //   458: ldc_w           10
        //   461: isub           
        //   462: istore_3       
        //   463: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   466: ifeq            367
        //   469: aconst_null    
        //   470: athrow         
        //   471: return         
        //    StackMapTable: 00 15 FF 00 03 00 07 07 00 02 00 00 00 00 00 01 00 00 00 FF 00 52 00 07 07 00 02 07 00 49 07 00 DC 01 00 00 01 00 00 0F 0F 1E 0F 0F 0F 0F 0F 0F FB 00 63 0E FF 00 05 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 00 01 00 00 FF 00 2A 00 00 00 01 07 00 31 FF 00 04 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 07 03 9A 01 00 00 11 58 01 FF 00 02 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 07 03 9A 01 00 02 01 01 FF 00 09 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 00 01 00 00
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void rtlW() {
        class DvIq implements pSdr
        {
            final XybC hILD;
            
            DvIq(final XybC hild) {
                this.hILD = hild;
                super();
            }
            
            @Override
            public int Gsgl() {
                return Wrapper.mc.player.getActivePotionEffects().size() * 10;
            }
            
            @Override
            public void Hrqs(final int n) {
                this.hILD.yAdi(n);
            }
        }
        class CDOW implements pSdr
        {
            final XybC hILD;
            
            CDOW(final XybC hild) {
                this.hILD = hild;
                super();
            }
            
            @Override
            public int Gsgl() {
                return vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).wkYe + 2;
            }
            
            @Override
            public void Hrqs(final int n) {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     3: return         
                //     4: aload_0        
                //     5: getfield        wwe/CDOW.hILD:Lwwe/XybC;
                //     8: iload_1        
                //     9: ldc             1
                //    11: iadd           
                //    12: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
                //    15: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
                //    18: instanceof      Lnet/minecraft/client/gui/GuiChat;
                //    21: ifeq            34
                //    24: ldc             1
                //    26: getstatic       wwe/CDOW.\u2007\u2002\u2008\u2001\u2003\u200d\u2009\u2008:Z
                //    29: ifeq            36
                //    32: aconst_null    
                //    33: athrow         
                //    34: ldc             0
                //    36: isub           
                //    37: invokevirtual   wwe/XybC.qGPs:(I)V
                //    40: return         
                //    StackMapTable: 00 04 FC 00 03 01 00 FF 00 1D 00 03 07 00 02 01 01 00 02 07 00 08 01 FF 00 01 00 03 07 00 02 01 01 00 03 07 00 08 01 01
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: new             Lnet/minecraft/client/gui/ScaledResolution;
        //     7: dup            
        //     8: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    11: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;)V
        //    14: astore_1       
        //    15: new             Ljava/util/ArrayList;
        //    18: dup            
        //    19: invokespecial   java/util/ArrayList.<init>:()V
        //    22: astore_2       
        //    23: aload_1        
        //    24: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledHeight:()I
        //    27: ldc_w           10
        //    30: isub           
        //    31: istore_3       
        //    32: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    35: getfield        net/minecraft/client/Minecraft.currentScreen:Lnet/minecraft/client/gui/GuiScreen;
        //    38: instanceof      Lnet/minecraft/client/gui/GuiChat;
        //    41: ifeq            47
        //    44: iinc            3, -13
        //    47: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    50: ldc             Lwwe/Ijoo;.class
        //    52: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //    55: checkcast       Lwwe/modules/render/Hud;
        //    58: invokevirtual   wwe/modules/render/Hud.IuiN:()Z
        //    61: ifeq            147
        //    64: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    67: ldc             Lwwe/Ijoo;.class
        //    69: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //    72: checkcast       Lwwe/modules/render/Hud;
        //    75: getfield        wwe/modules/render/Hud.potions:Lwwe/utils/BooleanValue;
        //    78: invokevirtual   wwe/utils/BooleanValue.OnGi:()Ljava/lang/Object;
        //    81: checkcast       Ljava/lang/Boolean;
        //    84: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    87: ifeq            147
        //    90: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    93: ldc             Lwwe/Ijoo;.class
        //    95: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //    98: checkcast       Lwwe/modules/render/Hud;
        //   101: getfield        wwe/modules/render/Hud.arrayListMode:Lwwe/utils/ModeValue;
        //   104: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //   107: checkcast       Ljava/lang/String;
        //   110: ldc_w           "Top Right"
        //   113: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   116: ifeq            147
        //   119: new             Lwwe/DvIq;
        //   122: dup            
        //   123: aload_0        
        //   124: invokespecial   wwe/DvIq.<init>:(Lwwe/XybC;)V
        //   127: astore          4
        //   129: aload_2        
        //   130: new             Lwwe/Eiby;
        //   133: dup            
        //   134: aconst_null    
        //   135: ldc_w           0
        //   138: aload           4
        //   140: invokespecial   wwe/Eiby.<init>:(Ljava/lang/String;ILwwe/pSdr;)V
        //   143: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   146: pop            
        //   147: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   150: ldc             Lwwe/Ijoo;.class
        //   152: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   155: checkcast       Lwwe/modules/render/Hud;
        //   158: getfield        wwe/modules/render/Hud.information:Lwwe/utils/ModeValue;
        //   161: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //   164: checkcast       Ljava/lang/String;
        //   167: ldc_w           "Bottom Right"
        //   170: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   173: ifeq            361
        //   176: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //   179: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   182: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //   185: ifeq            202
        //   188: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //   191: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   194: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //   197: ldc             1
        //   199: if_icmpne       234
        //   202: aload_0        
        //   203: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   206: ifnull          218
        //   209: aload_2        
        //   210: aload_0        
        //   211: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   214: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   217: pop            
        //   218: aload_0        
        //   219: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   222: ifnull          234
        //   225: aload_2        
        //   226: aload_0        
        //   227: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   230: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   233: pop            
        //   234: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //   237: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   240: getfield        net/minecraft/client/entity/EntityPlayerSP.dimension:I
        //   243: ldc_w           -1
        //   246: if_icmpne       281
        //   249: aload_0        
        //   250: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   253: ifnull          265
        //   256: aload_2        
        //   257: aload_0        
        //   258: invokevirtual   wwe/XybC.Cvit:()Lwwe/Eiby;
        //   261: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   264: pop            
        //   265: aload_0        
        //   266: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   269: ifnull          281
        //   272: aload_2        
        //   273: aload_0        
        //   274: invokevirtual   wwe/XybC.pejA:()Lwwe/Eiby;
        //   277: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   280: pop            
        //   281: aload_0        
        //   282: invokevirtual   wwe/XybC.SDNi:()Lwwe/Eiby;
        //   285: ifnull          297
        //   288: aload_2        
        //   289: aload_0        
        //   290: invokevirtual   wwe/XybC.SDNi:()Lwwe/Eiby;
        //   293: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   296: pop            
        //   297: aload_0        
        //   298: invokevirtual   wwe/XybC.Rlmo:()Lwwe/Eiby;
        //   301: ifnull          313
        //   304: aload_2        
        //   305: aload_0        
        //   306: invokevirtual   wwe/XybC.Rlmo:()Lwwe/Eiby;
        //   309: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   312: pop            
        //   313: aload_0        
        //   314: invokevirtual   wwe/XybC.uocS:()Lwwe/Eiby;
        //   317: ifnull          329
        //   320: aload_2        
        //   321: aload_0        
        //   322: invokevirtual   wwe/XybC.uocS:()Lwwe/Eiby;
        //   325: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   328: pop            
        //   329: aload_0        
        //   330: invokevirtual   wwe/XybC.lHll:()Lwwe/Eiby;
        //   333: ifnull          345
        //   336: aload_2        
        //   337: aload_0        
        //   338: invokevirtual   wwe/XybC.lHll:()Lwwe/Eiby;
        //   341: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   344: pop            
        //   345: aload_0        
        //   346: invokevirtual   wwe/XybC.VeEy:()Lwwe/Eiby;
        //   349: ifnull          361
        //   352: aload_2        
        //   353: aload_0        
        //   354: invokevirtual   wwe/XybC.VeEy:()Lwwe/Eiby;
        //   357: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   360: pop            
        //   361: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   364: ldc             Lwwe/Ijoo;.class
        //   366: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   369: checkcast       Lwwe/modules/render/Hud;
        //   372: invokevirtual   wwe/modules/render/Hud.IuiN:()Z
        //   375: ifeq            461
        //   378: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   381: ldc             Lwwe/Ijoo;.class
        //   383: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   386: checkcast       Lwwe/modules/render/Hud;
        //   389: getfield        wwe/modules/render/Hud.arrayList:Lwwe/utils/BooleanValue;
        //   392: invokevirtual   wwe/utils/BooleanValue.OnGi:()Ljava/lang/Object;
        //   395: checkcast       Ljava/lang/Boolean;
        //   398: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   401: ifeq            461
        //   404: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   407: ldc             Lwwe/Ijoo;.class
        //   409: invokevirtual   wwe/eOnJ.qHPF:(Ljava/lang/Class;)Lwwe/qMIe;
        //   412: checkcast       Lwwe/modules/render/Hud;
        //   415: getfield        wwe/modules/render/Hud.arrayListMode:Lwwe/utils/ModeValue;
        //   418: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //   421: checkcast       Ljava/lang/String;
        //   424: ldc_w           "Bottom Right"
        //   427: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   430: ifeq            461
        //   433: new             Lwwe/CDOW;
        //   436: dup            
        //   437: aload_0        
        //   438: invokespecial   wwe/CDOW.<init>:(Lwwe/XybC;)V
        //   441: astore          4
        //   443: aload_2        
        //   444: new             Lwwe/Eiby;
        //   447: dup            
        //   448: aconst_null    
        //   449: ldc_w           0
        //   452: aload           4
        //   454: invokespecial   wwe/Eiby.<init>:(Ljava/lang/String;ILwwe/pSdr;)V
        //   457: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   460: pop            
        //   461: aload_2        
        //   462: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   465: astore          4
        //   467: aload           4
        //   469: invokevirtual   java/util/Iterator.hasNext:()Z
        //   472: ifeq            588
        //   475: aload           4
        //   477: invokevirtual   java/util/Iterator.next:()Ljava/lang/Object;
        //   480: checkcast       Lwwe/Eiby;
        //   483: astore          5
        //   485: aload           5
        //   487: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   490: ifnull          515
        //   493: aload           5
        //   495: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   498: iload_3        
        //   499: invokevirtual   wwe/pSdr.Hrqs:(I)V
        //   502: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   505: ifeq            550
        //   508: aconst_null    
        //   509: athrow         
        //   510: nop            
        //   511: nop            
        //   512: nop            
        //   513: nop            
        //   514: athrow         
        //   515: aload           5
        //   517: getfield        wwe/Eiby.EMiZ:Ljava/lang/String;
        //   520: aload_1        
        //   521: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaledWidth:()I
        //   524: invokestatic    wwe/XybC.wKEb:()Lnet/minecraft/client/gui/FontRenderer;
        //   527: aload           5
        //   529: getfield        wwe/Eiby.EMiZ:Ljava/lang/String;
        //   532: invokevirtual   net/minecraft/client/gui/FontRenderer.getStringWidth:(Ljava/lang/String;)I
        //   535: isub           
        //   536: ldc             2
        //   538: isub           
        //   539: i2f            
        //   540: iload_3        
        //   541: i2f            
        //   542: aload           5
        //   544: getfield        wwe/Eiby.wquB:I
        //   547: invokestatic    wwe/XybC.eTim:(Ljava/lang/String;FFI)V
        //   550: iload_3        
        //   551: aload           5
        //   553: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   556: ifnull          575
        //   559: aload           5
        //   561: getfield        wwe/Eiby.gWym:Lwwe/pSdr;
        //   564: invokevirtual   wwe/pSdr.Gsgl:()I
        //   567: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   570: ifeq            578
        //   573: aconst_null    
        //   574: athrow         
        //   575: ldc_w           10
        //   578: isub           
        //   579: istore_3       
        //   580: getstatic       wwe/XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009:Z
        //   583: ifeq            467
        //   586: aconst_null    
        //   587: athrow         
        //   588: return         
        //    StackMapTable: 00 16 FF 00 03 00 07 07 00 02 00 00 00 00 00 01 00 00 00 FF 00 2A 00 07 07 00 02 07 00 49 07 00 DC 01 00 00 01 00 00 FB 00 63 36 0F 0F 1E 0F 0F 0F 0F 0F 0F FB 00 63 FF 00 05 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 00 01 00 00 FF 00 2A 00 00 00 01 07 00 31 FF 00 04 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 07 03 9A 01 00 00 22 58 01 FF 00 02 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 07 03 9A 01 00 02 01 01 FF 00 09 00 07 07 00 02 07 00 49 07 00 DC 01 07 01 16 00 01 00 00
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void ntBz(final ScaledResolution scaledResolution) {
        final ArrayList<Eiby> list = new ArrayList<Eiby>();
        if (this.TdhW() != null) {
            list.add(this.TdhW());
        }
        if (this.tvFf() != null) {
            list.add(this.tvFf());
        }
        int n = 50;
        for (final Eiby eiby : list) {
            if (eiby == null) {
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
                continue;
            }
            else {
                if (eiby.gWym != null) {
                    eiby.gWym.Hrqs(n);
                    if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                        throw null;
                    }
                }
                else {
                    eTim(eiby.EMiZ, (float)(scaledResolution.getScaledWidth() / 2 - wKEb().getStringWidth(eiby.EMiZ) / 2), (float)n, eiby.wquB);
                }
                final int n2 = n;
                int gsgl;
                if (eiby.gWym != null) {
                    gsgl = eiby.gWym.Gsgl();
                    if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                        throw null;
                    }
                }
                else {
                    gsgl = 10;
                }
                n = n2 + gsgl;
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
                continue;
            }
        }
    }
    
    public Eiby UNzQ() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).waterMark.OnGi()) {
            final String s = "WWE Version 2.7.2";
            int n;
            if (vlwv.hNxW.qHPF((Class<ColorList>)VWdz.class).IuiN()) {
                n = nuRU(0x441E3A091259B80DL ^ 0x441E3A090F94DD0DL, 1.0f).getRGB();
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
            else {
                n = vlwv.fomn.LvCB.JqMr().getRGB();
            }
            return new Eiby(s, n, null);
        }
        return null;
    }
    
    public Eiby SDNi() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).fps.OnGi()) {
            final StringBuilder append = new StringBuilder().append("FPS: ");
            final Minecraft mc = Wrapper.mc;
            return new Eiby(append.append(Minecraft.getDebugFPS()).toString(), vlwv.fomn.bNiH.JqMr().getRGB(), null);
        }
        return null;
    }
    
    public Eiby Cvit() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).overworldCoords.OnGi()) {
            if (Wrapper.mc.player.dimension == 0 || Wrapper.mc.player.dimension == 1) {
                return new Eiby("X:Y:Z: " + this.yOZV.format(Wrapper.mc.player.posX) + " " + this.yOZV.format(Wrapper.mc.player.posY) + " " + this.yOZV.format(Wrapper.mc.player.posZ), vlwv.fomn.VQLP.JqMr().getRGB(), null);
            }
            if (Wrapper.mc.player.dimension == -1) {
                return new Eiby("X:Y:Z: " + this.yOZV.format(Wrapper.mc.player.posX * 8.0) + " " + this.yOZV.format(Wrapper.mc.player.posY) + " " + this.yOZV.format(Wrapper.mc.player.posZ * 8.0), vlwv.fomn.VQLP.JqMr().getRGB(), null);
            }
        }
        return null;
    }
    
    public Eiby pejA() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).netherCoords.OnGi()) {
            if (Wrapper.mc.player.dimension == -1) {
                return new Eiby("X:Y:Z: " + this.yOZV.format(Wrapper.mc.player.posX) + " " + this.yOZV.format(Wrapper.mc.player.posY) + " " + this.yOZV.format(Wrapper.mc.player.posZ), vlwv.fomn.agTY.JqMr().getRGB(), null);
            }
            if (Wrapper.mc.player.dimension == 0 || Wrapper.mc.player.dimension == 1) {
                return new Eiby("X:Y:Z: " + this.yOZV.format(Wrapper.mc.player.posX / 8.0) + " " + this.yOZV.format(Wrapper.mc.player.posY) + " " + this.yOZV.format(Wrapper.mc.player.posZ / 8.0), vlwv.fomn.agTY.JqMr().getRGB(), null);
            }
        }
        return null;
    }
    
    public Eiby Rlmo() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).tps.OnGi()) {
            return new Eiby("TPS: " + PJxY.Npqe, this.WRhv(), null);
        }
        return null;
    }
    
    public int WRhv() {
        if (PJxY.Npqe > 15.0f) {
            return vlwv.fomn.JOgm.JqMr().getRGB();
        }
        if (PJxY.Npqe > 10.0f) {
            return vlwv.fomn.kGoP.JqMr().getRGB();
        }
        if (PJxY.Npqe > 5.0f) {
            return vlwv.fomn.TAnK.JqMr().getRGB();
        }
        if (PJxY.Npqe >= 0.0f) {
            return vlwv.fomn.SfWP.JqMr().getRGB();
        }
        return -1;
    }
    
    public Eiby uocS() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).bps.OnGi()) {
            return new Eiby("BPS: " + XybC.MvaJ.format(Hud.CwjD()), vlwv.fomn.qpge.JqMr().getRGB(), null);
        }
        return null;
    }
    
    public Eiby TdhW() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).lagMeter.OnGi()) {
            vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).Xijd.gCgh();
            if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).Xijd.QOXG(1000.0)) {
                return new Eiby("Server has been lagging for " + vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).Xijd.KBDH() / (0x3F360B846FEC2057L ^ 0x3F360B846FEC23BFL) + " seconds", -1, null);
            }
        }
        else {
            vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).Xijd.BBwj();
        }
        return null;
    }
    
    public int GvWQ(final String s, final ScaledResolution scaledResolution) {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).information.OnGi().equalsIgnoreCase("Top Left") || vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).information.OnGi().equalsIgnoreCase("Bottom Left")) {
            return 3;
        }
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).information.OnGi().equalsIgnoreCase("Top Right") || vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).information.OnGi().equalsIgnoreCase("Bottom Right")) {
            return scaledResolution.getScaledWidth() - wKEb().getStringWidth(s);
        }
        return 3;
    }
    
    public void swMA(final int n) {
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        final ResourceLocation resourceLocation = new ResourceLocation("textures/gui/icons.png");
        Wrapper.mc.renderEngine.bindTexture(resourceLocation);
        final int n2 = 50;
        int n3 = n;
        for (final EntityPlayer next : Wrapper.mc.world.loadedEntityList) {
            if (next instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = next;
                if (Wrapper.mc.player.getDistance((Entity)entityPlayer) < vlwv.hNxW.qHPF((Class<CombatInfo>)fhLu.class).range.OnGi() && !entityPlayer.getName().equals(Wrapper.mc.player.getName())) {
                    final int ceil = MathHelper.ceil(entityPlayer.getAbsorptionAmount());
                    final float n4 = (float)entityPlayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
                    final int max = Math.max(10 - (MathHelper.ceil((n4 + ceil) / 2.0f / 10.0f) - 2), 3);
                    final int ceil2 = MathHelper.ceil(entityPlayer.getHealth());
                    int n5 = ceil;
                    final int n6 = -1;
                    int i = MathHelper.ceil((n4 + ceil) / 2.0f) - 1;
                    while (i >= 0) {
                        int n7 = 16;
                        if (entityPlayer.isPotionActive(MobEffects.POISON)) {
                            n7 += 36;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else if (entityPlayer.isPotionActive(MobEffects.WITHER)) {
                            n7 += 72;
                        }
                        final int n8 = 0;
                        final int n9 = MathHelper.ceil((i + 1) / 10.0f) - 1;
                        final int n10 = n2 + i % 10 * 8;
                        int n11 = n3 - n9 * max + 8;
                        if (n5 <= 0 && i == n6) {
                            n11 -= 2;
                        }
                        int n12 = 0;
                        if (Wrapper.mc.world.getWorldInfo().isHardcoreModeEnabled()) {
                            n12 = 5;
                        }
                        Wrapper.mc.ingameGUI.drawTexturedModalRect(n10, n11, 16 + n8 * 9, 9 * n12, 9, 9);
                        if (n5 > 0) {
                            if (n5 == ceil && ceil % 2 == 1) {
                                Wrapper.mc.ingameGUI.drawTexturedModalRect(n10, n11, n7 + 153, 9 * n12, 9, 9);
                                --n5;
                                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                    throw null;
                                }
                            }
                            else {
                                Wrapper.mc.ingameGUI.drawTexturedModalRect(n10, n11, n7 + 144, 9 * n12, 9, 9);
                                n5 -= 2;
                                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            if (i * 2 + 1 < ceil2) {
                                Wrapper.mc.ingameGUI.drawTexturedModalRect(n10, n11, n7 + 36, 9 * n12, 9, 9);
                            }
                            if (i * 2 + 1 == ceil2) {
                                Wrapper.mc.ingameGUI.drawTexturedModalRect(n10, n11, n7 + 45, 9 * n12, 9, 9);
                            }
                        }
                        --i;
                        if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                            throw null;
                        }
                    }
                    if (vlwv.hNxW.qHPF((Class<CombatInfo>)fhLu.class).names.OnGi()) {
                        int n13;
                        if (vlwv.hNxW.qHPF((Class<CombatInfo>)fhLu.class).coords.OnGi()) {
                            n13 = 10;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else {
                            n13 = 0;
                        }
                        final int n14 = n13;
                        int n15;
                        if (entityPlayer.getAbsorptionAmount() >= 1.0f) {
                            n15 = 10;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else {
                            n15 = 0;
                        }
                        eTim(entityPlayer.getName(), 55.0f, (float)(n3 - n14 - n15), -1);
                    }
                    if (vlwv.hNxW.qHPF((Class<CombatInfo>)fhLu.class).coords.OnGi()) {
                        int n16;
                        if (entityPlayer.getAbsorptionAmount() >= 1.0f) {
                            n16 = 10;
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        else {
                            n16 = 0;
                        }
                        eTim("X:Y:Z: " + this.yOZV.format(entityPlayer.posX) + " " + this.yOZV.format(entityPlayer.posY) + " " + this.yOZV.format(entityPlayer.posZ), 55.0f, (float)(n3 - n16), -1);
                    }
                    n3 += 50;
                    if (vlwv.hNxW.qHPF((Class<CombatInfo>)fhLu.class).heads.OnGi()) {
                        try {
                            Wrapper.mc.getTextureManager().bindTexture(((AbstractClientPlayer)entityPlayer).getLocationSkin());
                            final GuiIngame ingameGUI = Wrapper.mc.ingameGUI;
                            GuiIngame.drawScaledCustomSizeModalRect(5, n3 - 63, 8.0f, 8.0f, 8, 8, 30, 30, 64.0f, 64.0f);
                            if (((AbstractClientPlayer)entityPlayer).isWearing(EnumPlayerModelParts.HAT)) {
                                final GuiIngame ingameGUI2 = Wrapper.mc.ingameGUI;
                                GuiIngame.drawScaledCustomSizeModalRect(5, n3 - 63, 40.0f, 8.0f, 8, 8, 30, 30, 64.0f, 64.0f);
                            }
                            Wrapper.mc.getTextureManager().bindTexture(resourceLocation);
                            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                                throw null;
                            }
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
    }
    
    public void rZnU(final int n, int n2) {
        final HashMap<Object, Integer> hashMap = new HashMap<Object, Integer>();
        for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
            if (entity instanceof EntityPlayerSP) {
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
                continue;
            }
            else {
                String name = entity.getName();
                int count = 1;
                if (entity instanceof EntityItem) {
                    name = "Item";
                    count = ((EntityItem)entity).getItem().getCount();
                }
                if (entity instanceof EntityEnderCrystal) {
                    name = "Ender Crystal";
                }
                if (entity instanceof EntityItemFrame) {
                    name = "Item Frame";
                }
                if (entity instanceof EntityPlayer) {
                    name = "Player";
                }
                int intValue;
                if (hashMap.containsKey(name)) {
                    intValue = hashMap.get(name);
                    if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                        throw null;
                    }
                }
                else {
                    intValue = 0;
                }
                hashMap.put(name, intValue + count);
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
                continue;
            }
        }
        vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).pxFw = hashMap.size() * 10;
        for (final Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            eTim(entry.getKey() + ":" + entry.getValue(), (float)n, (float)n2, vlwv.fomn.JQor.JqMr().getRGB());
            n2 += 10;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
    }
    
    public Eiby lHll() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).ping.OnGi()) {
            final StringBuilder append = new StringBuilder().append("Ping: ");
            long pingToServer;
            if (Wrapper.mc.isSingleplayer()) {
                pingToServer = (0xF70D3960F60CD94FL ^ 0xF70D3960F60CD94FL);
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
            else {
                pingToServer = Wrapper.mc.getCurrentServerData().pingToServer;
            }
            return new Eiby(append.append(pingToServer).toString(), vlwv.fomn.LsTY.JqMr().getRGB(), null);
        }
        return null;
    }
    
    public Eiby GQOK() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).itemDurability.OnGi() && Wrapper.mc.player.getHeldItemMainhand().isItemStackDamageable()) {
            return new Eiby("Item Durability: " + (Wrapper.mc.player.getHeldItemMainhand().getMaxDamage() - Wrapper.mc.player.getHeldItemMainhand().getItemDamage()) + " / " + Wrapper.mc.player.getHeldItemMainhand().getMaxDamage(), vlwv.fomn.EDSc.JqMr().getRGB(), null);
        }
        return null;
    }
    
    public Eiby tvFf() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).lavaWarning.OnGi()) {
            int i = 0;
            while (i < 11) {
                if (Wrapper.mc.world.getBlockState(new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ).add(0, -i, 0)).getBlock() == Blocks.LAVA || Wrapper.mc.world.getBlockState(new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ).add(0, -i, 0)).getBlock() == Blocks.FLOWING_LAVA) {
                    return new Eiby("Lava Warning: Your in lava/:", -558050, null);
                }
                ++i;
                if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                    throw null;
                }
            }
        }
        return null;
    }
    
    public Eiby VeEy() {
        if (vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).IuiN() && vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).biome.OnGi()) {
            return new Eiby("Biome: " + Wrapper.mc.world.getBiome(new BlockPos((Entity)Wrapper.mc.player)).getBiomeName(), vlwv.fomn.CZsW.JqMr().getRGB(), null);
        }
        return null;
    }
    
    public static Color nuRU(final long n, final float n2) {
        final float n3 = (System.nanoTime() + n) / 1.0E10f % 1.0f;
        float n4;
        if (vlwv.hNxW.qHPF((Class<ColorList>)VWdz.class).mode.OnGi().equalsIgnoreCase("Up")) {
            n4 = (System.nanoTime() - n) / 1.0E10f % 1.0f;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else {
            n4 = (System.nanoTime() + n) / 1.0E10f % 1.0f;
        }
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB(n4, 1.0f, 1.0f)), 16));
        return new Color(color.getRed() / 255.0f * n2, color.getGreen() / 255.0f * n2, color.getBlue() / 255.0f * n2, color.getAlpha() / 255.0f);
    }
    
    public FontRenderer cZcq(final boolean b, final boolean b2, final boolean b3, final boolean b4, final boolean b5) {
        FontRenderer fontRenderer;
        if (b) {
            fontRenderer = bozE.dRQq;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (b2) {
            fontRenderer = bozE.SpUn;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (b3) {
            fontRenderer = bozE.VuaG;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (b4) {
            fontRenderer = bozE.gHdT;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (b5) {
            fontRenderer = bozE.Jfgf;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else {
            fontRenderer = Wrapper.fontRenderer;
        }
        return fontRenderer;
    }
    
    public static FontRenderer wKEb() {
        if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN()) {
            if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Comfortaa")) {
                return bozE.dRQq;
            }
            if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Roboto")) {
                return bozE.SpUn;
            }
            if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Cosmic Sans")) {
                return bozE.VuaG;
            }
            if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Verdana")) {
                return bozE.gHdT;
            }
            if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Custom")) {
                if (bozE.Jfgf != null) {
                    return bozE.Jfgf;
                }
                HAxG.TuzD("It Appears you dident set a custom font yet, do .customfont NameOfFont");
                vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.NdCc("Comfortaa");
            }
        }
        return Wrapper.fontRenderer;
    }
    
    public static void eTim(final String s, final float n, final float n2, final int n3) {
        final FontRenderer wkEb = wKEb();
        float n4;
        if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN() && vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Comfortaa")) {
            n4 = 1.0f;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN() && vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Roboto")) {
            n4 = 2.0f;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN() && vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Cosmic Sans")) {
            n4 = 3.0f;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN() && vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Verdana")) {
            n4 = 2.5f;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN() && vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Custom")) {
            n4 = (float)vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).height.OnGi().intValue();
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else {
            n4 = 0.0f;
        }
        wkEb.drawString(s, n, n2 - n4, n3, (boolean)vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).textShadow.OnGi());
    }
    
    public static void dWXO(final String s, final float n, final float n2, final int n3) {
        final FontRenderer wkEb = wKEb();
        float n4;
        if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN() && vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Comfortaa")) {
            n4 = 1.0f;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN() && vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Roboto")) {
            n4 = 2.0f;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN() && vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Cosmic Sans")) {
            n4 = 3.0f;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN() && vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Verdana")) {
            n4 = 2.5f;
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else if (vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).IuiN() && vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).font.OnGi().equalsIgnoreCase("Custom")) {
            n4 = (float)vlwv.hNxW.qHPF((Class<CustomFont>)krYX.class).height.OnGi().intValue();
            if (XybC.\u2008\u200e\u200d\u2003\u2004\u2000\u200e\u2009) {
                throw null;
            }
        }
        else {
            n4 = 0.0f;
        }
        wkEb.drawString(s, n, n2 - n4, n3, false);
    }
    
    private static int cbtR(final qMIe qmIe, final qMIe qmIe2) {
        return Wrapper.fontRenderer.getStringWidth(qmIe2.Odlv()) - Wrapper.fontRenderer.getStringWidth(qmIe.Odlv());
    }
    
    private static int gvpI(final qMIe qmIe, final qMIe qmIe2) {
        return bozE.Jfgf.getStringWidth(qmIe2.Odlv()) - bozE.Jfgf.getStringWidth(qmIe.Odlv());
    }
    
    private static int IFuU(final qMIe qmIe, final qMIe qmIe2) {
        return bozE.gHdT.getStringWidth(qmIe2.Odlv()) - bozE.gHdT.getStringWidth(qmIe.Odlv());
    }
    
    private static int RkaO(final qMIe qmIe, final qMIe qmIe2) {
        return bozE.VuaG.getStringWidth(qmIe2.Odlv()) - bozE.VuaG.getStringWidth(qmIe.Odlv());
    }
    
    private static int MSNW(final qMIe qmIe, final qMIe qmIe2) {
        return bozE.SpUn.getStringWidth(qmIe2.Odlv()) - bozE.SpUn.getStringWidth(qmIe.Odlv());
    }
    
    private static int PzRn(final qMIe qmIe, final qMIe qmIe2) {
        return bozE.dRQq.getStringWidth(qmIe2.Odlv()) - bozE.dRQq.getStringWidth(qmIe.Odlv());
    }
    
    static {
        XybC.MvaJ = new DecimalFormat("#.#");
    }
}
