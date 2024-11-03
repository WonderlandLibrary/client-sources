package net.silentclient.client.gui.font;

import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import org.apache.commons.io.Charsets;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class SilentFontRenderer {
	private static final ResourceLocation silentFontHeader = new ResourceLocation("silentclient/fonts/silent-font-0.png");
    private static final ResourceLocation silentFontTitle = new ResourceLocation("silentclient/fonts/silent-font-1.png");
    private static final ResourceLocation silentFontText = new ResourceLocation("silentclient/fonts/silent-font-2.png");
    private static final ResourceLocation silentFontDataHeader = new ResourceLocation("silentclient/fonts/silent-font-data-0.json");
    private static final ResourceLocation silentFontDataTitle = new ResourceLocation("silentclient/fonts/silent-font-data-1.json");
    private static final ResourceLocation silentFontDataText = new ResourceLocation("silentclient/fonts/silent-font-data-2.json");
    private Map<Integer, Integer> fontDataHeader;
    private Map<Integer, Integer> fontDataTitle;
    private Map<Integer, Integer> fontDataText;
    private int[] colorCode = new int[32];

    @SuppressWarnings("unchecked")
	public SilentFontRenderer() throws Throwable
    {
        try
        {
            InputStreamReader inputstreamreader = new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(silentFontDataHeader).getInputStream(), Charsets.UTF_8);
            Throwable var6 = null;

            try
            {
                this.fontDataHeader = (Map<Integer, Integer>)Client.getInstance().getGson().fromJson((Reader)inputstreamreader, (new TypeToken<Map<Integer, Integer>>()
                {
                }).getType());
            }
            catch (Throwable throwable6)
            {
                var6 = throwable6;
                throw throwable6;
            }
            finally
            {
                if (inputstreamreader != null)
                {
                    if (var6 != null)
                    {
                        try
                        {
                            inputstreamreader.close();
                        }
                        catch (Throwable throwable5)
                        {
                            var6.addSuppressed(throwable5);
                        }
                    }
                    else
                    {
                        inputstreamreader.close();
                    }
                }
            }
        }
        catch (IOException ioexception2)
        {
            ioexception2.printStackTrace();
        }

        try
        {
            InputStreamReader inputstreamreader1 = new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(silentFontDataTitle).getInputStream(), Charsets.UTF_8);
            Throwable throwable7 = null;

            try
            {
                this.fontDataTitle = (Map<Integer, Integer>)Client.getInstance().getGson().fromJson((Reader)inputstreamreader1, (new TypeToken<Map<Integer, Integer>>()
                {
                }).getType());
            }
            catch (Throwable throwable4)
            {
                throwable7 = throwable4;
                throw throwable4;
            }
            finally
            {
                if (inputstreamreader1 != null)
                {
                    if (throwable7 != null)
                    {
                        try
                        {
                            inputstreamreader1.close();
                        }
                        catch (Throwable throwable3)
                        {
                            throwable7.addSuppressed(throwable3);
                        }
                    }
                    else
                    {
                        inputstreamreader1.close();
                    }
                }
            }
        }
        catch (IOException ioexception1)
        {
            ioexception1.printStackTrace();
        }

        try
        {
            InputStreamReader inputstreamreader2 = new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(silentFontDataText).getInputStream(), Charsets.UTF_8);
            Throwable throwable8 = null;

            try
            {
                this.fontDataText = (Map<Integer, Integer>)Client.getInstance().getGson().fromJson((Reader)inputstreamreader2, (new TypeToken<Map<Integer, Integer>>()
                {
                }).getType());
            }
            catch (Throwable throwable2)
            {
                throwable8 = throwable2;
                throw throwable2;
            }
            finally
            {
                if (inputstreamreader2 != null)
                {
                    if (throwable8 != null)
                    {
                        try
                        {
                            inputstreamreader2.close();
                        }
                        catch (Throwable throwable1)
                        {
                            throwable8.addSuppressed(throwable1);
                        }
                    }
                    else
                    {
                        inputstreamreader2.close();
                    }
                }
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        for (int l = 0; l < 32; ++l)
        {
            int i1 = (l >> 3 & 1) * 85;
            int i = (l >> 2 & 1) * 170 + i1;
            int j = (l >> 1 & 1) * 170 + i1;
            int k = (l >> 0 & 1) * 170 + i1;

            if (l == 6)
            {
                i += 85;
            }

            if (l >= 16)
            {
                i /= 4;
                j /= 4;
                k /= 4;
            }

            this.colorCode[l] = (i & 255) << 16 | (j & 255) << 8 | k & 255;
        }
    }

    private Map<Integer, Integer> bindFontTexture(FontType fontType, boolean mipmapped)
    {
        switch (fontType)
        {
            case HEADER:
                if (mipmapped)
                {
                    Client.getInstance().getTextureManager().bindTextureMipmapped(silentFontHeader);
                }
                else
                {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(silentFontHeader);
                }

                return this.fontDataHeader;

            case TITLE:
                if (mipmapped)
                {
                    Client.getInstance().getTextureManager().bindTextureMipmapped(silentFontTitle);
                }
                else
                {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(silentFontTitle);
                }

                return this.fontDataTitle;

            case TEXT:
                if (mipmapped)
                {
                    Client.getInstance().getTextureManager().bindTextureMipmapped(silentFontText);
                }
                else
                {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(silentFontText);
                }

                return this.fontDataText;

            default:
                return null;
        }
    }

    private void renderString(float x, float y, String string, int scale, FontType fontType, boolean mipmapped)
    {
        this.renderString(x, y, string, scale, fontType, mipmapped, 0);
    }

    private void renderString(float x, float y, String string, int scale, FontType fontType, boolean mipmapped, int italicsWeight)
    {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        Map<Integer, Integer> map = this.bindFontTexture(fontType, mipmapped);

        if (map != null)
        {
            int i = 0;
            boolean flag = false;
            GlStateManager.enableAlpha();
            GL11.glEnable(GL11.GL_BLEND);

            for (char c0 : string.toCharArray())
            {
                if (flag)
                {
                    ++i;
                    flag = false;
                }
                else if (c0 == 167 && i + 1 < string.length())
                {
                    int j = "0123456789abcdefklmnor".indexOf(string.toLowerCase().charAt(i + 1));

                    if (j < 16)
                    {
                        if (j < 0 || j > 15)
                        {
                            j = 15;
                        }

                        int l1 = this.colorCode[j];
                        GL11.glColor4f((float)(l1 >> 16) / 255.0F, (float)(l1 >> 8 & 255) / 255.0F, (float)(l1 & 255) / 255.0F, 1.0F);
                    }

                    ++i;
                    flag = true;
                }
                else if (map.containsKey(Integer.valueOf(c0)))
                {
                    int k = ((Integer)map.get(Integer.valueOf(c0))).intValue();
                    int l = (c0 - 32) * 128 % 2048;
                    int i1 = (c0 - 32 >> 4) * 128;
                    int j1 = k / scale;
                    this.getClass();
                    int k1 = 128 / scale;
                    float f = 4.8828125E-4F;
                    float f1 = 4.8828125E-4F;
                    Tessellator tessellator = Tessellator.getInstance();
                    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                    worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                    worldrenderer.pos((double)x, (double)(y + k1), 0.0D).tex((double)((float)l * f), (double)((float)(i1 + 128) * f1)).endVertex();
                    worldrenderer.pos((double)(x + j1), (double)(y + k1), 0.0D).tex((double)((float)(l + k) * f), (double)((float)(i1 + 128) * f1)).endVertex();
                    worldrenderer.pos((double)(x + j1 + italicsWeight), (double)y, 0.0D).tex((double)((float)(l + k) * f), (double)((float)i1 * f1)).endVertex();
                    worldrenderer.pos((double)(x + italicsWeight), (double)y, 0.0D).tex((double)((float)l * f), (double)((float)i1 * f1)).endVertex();
                    tessellator.draw();
                    x += j1;
                    ++i;
                }
            }

            GL11.glDisable(GL11.GL_BLEND);
        }
    }
    public void drawString(String string, float x, float y, int fontHeight, FontType fontType)
    {
        this.getClass();
        this.renderString(x, y, string, 128 / fontHeight, fontType, true);
    }

    public void drawString(String string, float x, float y, int fontHeight, FontType fontType, int maxWidth)
    {
        this.getClass();
        if(this.getStringWidth(string, fontHeight, fontType) > maxWidth) {
            int difference = -(maxWidth - this.getStringWidth(string, fontHeight, fontType));
            int oneSymbolWidth = this.getStringWidth("a", fontHeight, fontType);
            int symbolsCount = difference / oneSymbolWidth;

            string = string.substring(0, string.length() - symbolsCount - 3).trim();
            string += "...";
        }
        this.renderString(x, y, string, 128 / fontHeight, fontType, true);
    }

    public void drawString(int x, int y, String string, int fontHeight, FontType fontType)
    {
        this.getClass();
        this.renderString(x, y, string, 128 / fontHeight, fontType, true);
    }

    public void drawString(float x, float y, String string, int fontHeight, FontType fontType, boolean mipmapped)
    {
        this.getClass();
        this.renderString(x, y, string, 128 / fontHeight, fontType, mipmapped);
    }

    public void drawItalicizedString(int x, int y, String string, int fontHeight, FontType fontType)
    {
        this.getClass();
        this.renderString(x, y, string, 128 / fontHeight, fontType, true, 4);
    }

    public void drawItalicizedString(int x, int y, String string, int fontHeight, FontType fontType, boolean mipmapped)
    {
        this.getClass();
        this.renderString(x, y, string, 128 / fontHeight, fontType, mipmapped, 4);
    }

    public int getStringWidth(String string, FontType fontType)
    {
        Map<Integer, Integer> map = this.getFontDataMap(fontType);

        if (map != null)
        {
            int i = 0;

            for (char c0 : string.toCharArray())
            {
                if (map.containsKey(Integer.valueOf(c0)))
                {
                    int j = ((Integer)map.get(Integer.valueOf(c0))).intValue();
                    i += j;
                }
            }

            return i;
        }
        else
        {
            return 0;
        }
    }

    public int getCharWidth(char c, FontType fontType)
    {
        Map<Integer, Integer> map = this.getFontDataMap(fontType);
        return map.containsKey(Integer.valueOf(c)) ? ((Integer)map.get(Integer.valueOf(c))).intValue() : 0;
    }

    public int getStringWidth(String string, int fontHeight, FontType fontType)
    {
        Map<Integer, Integer> map = this.getFontDataMap(fontType);
        int i = 0;
        int j = 0;
        boolean flag = false;

        for (char c0 : string.toCharArray())
        {
            if (flag)
            {
                ++j;
                flag = false;
            }
            else if (c0 == 167 && j + 1 < string.length())
            {
                ++j;
                flag = true;
            }
            else if (map.containsKey(Integer.valueOf(c0)))
            {
                int l = ((Integer)map.get(Integer.valueOf(c0))).intValue();
                this.getClass();
                int k = l / (128 / fontHeight);
                i += k;
            }
        }

        return i;
    }

    public int getCharWidth(char c, int fontHeight, FontType fontType)
    {
        Map<Integer, Integer> map = this.getFontDataMap(fontType);

        if (map != null && map.containsKey(Integer.valueOf(c)))
        {
            int i = ((Integer)map.get(Integer.valueOf(c))).intValue();
            this.getClass();
            return i / (128 / fontHeight);
        }
        else
        {
            return 0;
        }
    }

    private Map<Integer, Integer> getFontDataMap(FontType fontType)
    {
        switch (fontType)
        {
            case HEADER:
                return this.fontDataHeader;

            case TITLE:
                return this.fontDataTitle;

            case TEXT:
                return this.fontDataText;

            default:
                return null;
        }
    }

    public static enum FontType
    {
        HEADER,
        TITLE,
        TEXT;
    }

    public void drawCenteredString(String string, int x, int y, int fontHeight, FontType fontType, int maxWidth) {
        this.getClass();
        if(this.getStringWidth(string, fontHeight, fontType) > maxWidth) {
            int difference = -(maxWidth - this.getStringWidth(string, fontHeight, fontType));
            int oneSymbolWidth = this.getStringWidth("a", fontHeight, fontType);
            int symbolsCount = difference / oneSymbolWidth;

            string = string.substring(0, string.length() - symbolsCount - 3);
            string += "...";
        }
        this.renderString(x - (this.getStringWidth(string, fontHeight, fontType) / 2), y, string, 128 / fontHeight, fontType, true);
    }

	public void drawCenteredString(String string, int x, int y, int fontHeight, FontType fontType) {
		this.getClass();
        this.renderString(x - (this.getStringWidth(string, fontHeight, fontType) / 2), y, string, 128 / fontHeight, fontType, true);
	}
}
