package dev.star.utils;

import dev.star.utils.font.CustomFont;
import dev.star.utils.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IFontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public interface Utils {
    Minecraft mc = Minecraft.getMinecraft();
    IFontRenderer fr = mc.fontRendererObj;

    Tessellator tessellator = Tessellator.getInstance();
    WorldRenderer worldrenderer = tessellator.getWorldRenderer();

    FontUtil.FontType Starfont = FontUtil.FontType.STAR,
            iconFont = FontUtil.FontType.ICON,
            iconFont1 = FontUtil.FontType.  ICON1,
            neverloseFont = FontUtil.FontType.NEVERLOSE,
            tahomaFont = FontUtil.FontType.TAHOMA,
            verdanaFont = FontUtil.FontType.VERDANA,
             rubikFont = FontUtil.FontType.RUBIK;

    //Regular Fonts
    CustomFont Font12 = Starfont.size(12),
            Font14 = Starfont.size(14),
            Font16 = Starfont.size(16),
            Font18 = Starfont.size(18),
            Font20 = Starfont.size(20),
            Font22 = Starfont.size(22),
            Font24 = Starfont.size(24),
            Font26 = Starfont.size(26),
            Font28 = Starfont.size(28),
            Font32 = Starfont.size(32),
            Font40 = Starfont.size(40),
            Font80 = Starfont.size(80),
            Font10 = Starfont.size(10);


    //Bold Fonts
    CustomFont BoldFont12 = Font12.getBoldFont(),
            BoldFont14 = Font14.getBoldFont(),
            BoldFont16 = Font16.getBoldFont(),
            BoldFont18 = Font18.getBoldFont(),
            BoldFont20 = Font20.getBoldFont(),
            BoldFont22 = Font22.getBoldFont(),
            BoldFont24 = Font24.getBoldFont(),
            BoldFont26 = Font26.getBoldFont(),
            BoldFont28 = Font28.getBoldFont(),
            BoldFont32 = Font32.getBoldFont(),
            BoldFont40 = Font40.getBoldFont(),
            BoldFont80 = Font80.getBoldFont();

    //Icon Fontsor i
    CustomFont iconFont16 = iconFont.size(16),
            iconFont20 = iconFont.size(20),
            iconFont26 = iconFont.size(26),
            iconFont35 = iconFont.size(35),
            iconFont40 = iconFont.size(40);


    CustomFont iconFon1t16 = iconFont1.size(16),
            iconFont120 = iconFont1.size(20),
            iconFont126 = iconFont1.size(26),
            iconFont130 = iconFont1.size(30),

    iconFont135 = iconFont1.size(35),
            iconFont140 = iconFont1.size(40);

    //Enchant Font
    CustomFont enchantFont14 = tahomaFont.size(14),
    enchantFont12 = tahomaFont.size(12);

    CustomFont Verdana = verdanaFont.size(14),
    verdanaFont18 = verdanaFont.size(18),
            verdanaFont20 = verdanaFont.size(20),

    verdanaFont22 = verdanaFont.size(22),

    verdanaFont12 = verdanaFont.size(12);

}
