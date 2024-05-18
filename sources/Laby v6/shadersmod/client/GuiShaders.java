package shadersmod.client;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.src.Config;
import org.lwjgl.Sys;

public class GuiShaders extends GuiScreen
{
    protected GuiScreen parentGui;
    private int updateTimer = -1;
    private GuiSlotShaders shaderList;
    private static final String ANTIALIASING = "Antialiasing: ";
    private static final String NORMAL_MAP = "Normap Map: ";
    private static final String SPECULAR_MAP = "Specular Map: ";
    private static final String RENDER_RES_MUL = "Render Quality: ";
    private static final String SHADOW_RES_MUL = "Shadow Quality: ";
    private static final String HAND_DEPTH = "Hand Depth: ";
    private static final String CLOUD_SHADOW = "Cloud Shadow: ";
    private static final String OLD_LIGHTING = "Classic Lighting: ";
    private static float[] QUALITY_MULTIPLIERS = new float[] {0.5F, 0.70710677F, 1.0F, 1.4142135F, 2.0F};
    private static String[] QUALITY_MULTIPLIER_NAMES = new String[] {"0.5x", "0.7x", "1x", "1.5x", "2x"};
    private static float[] HAND_DEPTH_VALUES = new float[] {0.0625F, 0.125F, 0.25F, 0.5F, 1.0F};
    private static String[] HAND_DEPTH_NAMES = new String[] {"0.5x", "1x", "2x", "4x", "8x"};
    public static final int EnumOS_UNKNOWN = 0;
    public static final int EnumOS_WINDOWS = 1;
    public static final int EnumOS_OSX = 2;
    public static final int EnumOS_SOLARIS = 3;
    public static final int EnumOS_LINUX = 4;

    public GuiShaders(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        this.parentGui = par1GuiScreen;
    }

    private static String toStringOnOff(boolean value)
    {
        String s = "ON";
        String s1 = "OFF";
        return value ? s : s1;
    }

    private static String toStringAa(int value)
    {
        return value == 2 ? "FXAA 2x" : (value == 4 ? "FXAA 4x" : "OFF");
    }

    private static String toStringValue(float val, float[] values, String[] names)
    {
        int i = getValueIndex(val, values);
        return names[i];
    }

    private static int getValueIndex(float val, float[] values)
    {
        for (int i = 0; i < values.length; ++i)
        {
            float f = values[i];

            if (f >= val)
            {
                return i;
            }
        }

        return values.length - 1;
    }

    private static String toStringQuality(float val)
    {
        return toStringValue(val, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
    }

    private static String toStringHandDepth(float val)
    {
        return toStringValue(val, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        if (Shaders.shadersConfig == null)
        {
            Shaders.loadConfig();
        }

        int i = 30;
        int j = 20;
        int k = this.width - 130;
        int l = 120;
        int i1 = 20;
        int j1 = this.width - l - 20;
        this.shaderList = new GuiSlotShaders(this, j1, this.height, i, this.height - 50, 16);
        this.shaderList.registerScrollButtons(7, 8);
        this.buttonList.add(new GuiButton(20, k, 0 * j + i, l, i1, "Antialiasing: " + toStringAa(Shaders.configAntialiasingLevel)));
        this.buttonList.add(new GuiButton(17, k, 1 * j + i, l, i1, "Normap Map: " + toStringOnOff(Shaders.configNormalMap)));
        this.buttonList.add(new GuiButton(18, k, 2 * j + i, l, i1, "Specular Map: " + toStringOnOff(Shaders.configSpecularMap)));
        this.buttonList.add(new GuiButton(15, k, 3 * j + i, l, i1, "Render Quality: " + toStringQuality(Shaders.configRenderResMul)));
        this.buttonList.add(new GuiButton(16, k, 4 * j + i, l, i1, "Shadow Quality: " + toStringQuality(Shaders.configShadowResMul)));
        this.buttonList.add(new GuiButton(10, k, 5 * j + i, l, i1, "Hand Depth: " + toStringHandDepth(Shaders.configHandDepthMul)));
        this.buttonList.add(new GuiButton(9, k, 6 * j + i, l, i1, "Cloud Shadow: " + toStringOnOff(Shaders.configCloudShadow)));
        this.buttonList.add(new GuiButton(19, k, 7 * j + i, l, i1, "Classic Lighting: " + toStringOnOff(Shaders.configOldLighting)));
        this.buttonList.add(new GuiButton(6, k, this.height - 25, l, i1, "Done"));
        int k1 = 160;
        this.buttonList.add(new GuiButton(5, j1 / 2 - k1 / 2, this.height - 25, k1, i1, "Open shader packs folder"));
        this.updateButtons();
    }

    public void updateButtons()
    {
        boolean flag = Config.isShaders();

        for (GuiButton guibutton : this.buttonList)
        {
            if (guibutton.id > 8 && guibutton.id != 20)
            {
                guibutton.enabled = flag;
            }
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.shaderList.handleMouseInput();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
            switch (button.id)
            {
                case 4:
                    Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
                    button.displayString = "tweakBlockDamage: " + toStringOnOff(Shaders.configTweakBlockDamage);
                    break;

                case 5:
                    switch (getOSType())
                    {
                        case 1:
                            String s = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] {Shaders.shaderpacksdir.getAbsolutePath()});

                            try
                            {
                                Runtime.getRuntime().exec(s);
                                return;
                            }
                            catch (IOException ioexception)
                            {
                                ioexception.printStackTrace();
                                break;
                            }

                        case 2:
                            try
                            {
                                Runtime.getRuntime().exec(new String[] {"/usr/bin/open", Shaders.shaderpacksdir.getAbsolutePath()});
                                return;
                            }
                            catch (IOException ioexception1)
                            {
                                ioexception1.printStackTrace();
                            }
                    }

                    boolean flag = false;

                    try
                    {
                        Class oclass = Class.forName("java.awt.Desktop");
                        Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                        oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {(new File(this.mc.mcDataDir, Shaders.shaderpacksdirname)).toURI()});
                    }
                    catch (Throwable throwable)
                    {
                        throwable.printStackTrace();
                        flag = true;
                    }

                    if (flag)
                    {
                        System.out.println("Opening via system class!");
                        Sys.openURL("file://" + Shaders.shaderpacksdir.getAbsolutePath());
                    }

                    break;

                case 6:
                    new File(Shaders.shadersdir, "current.cfg");

                    try
                    {
                        Shaders.storeConfig();
                    }
                    catch (Exception var6)
                    {
                        ;
                    }

                    this.mc.displayGuiScreen(this.parentGui);
                    break;

                case 7:
                case 8:
                default:
                    this.shaderList.actionPerformed(button);
                    break;

                case 9:
                    Shaders.configCloudShadow = !Shaders.configCloudShadow;
                    button.displayString = "Cloud Shadow: " + toStringOnOff(Shaders.configCloudShadow);
                    break;

                case 10:
                    float f2 = Shaders.configHandDepthMul;
                    float[] afloat2 = HAND_DEPTH_VALUES;
                    String[] astring2 = HAND_DEPTH_NAMES;
                    int k = getValueIndex(f2, afloat2);

                    if (isShiftKeyDown())
                    {
                        --k;

                        if (k < 0)
                        {
                            k = afloat2.length - 1;
                        }
                    }
                    else
                    {
                        ++k;

                        if (k >= afloat2.length)
                        {
                            k = 0;
                        }
                    }

                    Shaders.configHandDepthMul = afloat2[k];
                    button.displayString = "Hand Depth: " + astring2[k];
                    break;

                case 11:
                    Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
                    Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
                    button.displayString = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
                    ShadersTex.updateTextureMinMagFilter();
                    break;

                case 12:
                    Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
                    button.displayString = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
                    ShadersTex.updateTextureMinMagFilter();
                    break;

                case 13:
                    Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
                    button.displayString = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
                    ShadersTex.updateTextureMinMagFilter();
                    break;

                case 14:
                    Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
                    button.displayString = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
                    ShadersTex.updateTextureMinMagFilter();
                    break;

                case 15:
                    float f1 = Shaders.configRenderResMul;
                    float[] afloat1 = QUALITY_MULTIPLIERS;
                    String[] astring1 = QUALITY_MULTIPLIER_NAMES;
                    int j = getValueIndex(f1, afloat1);

                    if (isShiftKeyDown())
                    {
                        --j;

                        if (j < 0)
                        {
                            j = afloat1.length - 1;
                        }
                    }
                    else
                    {
                        ++j;

                        if (j >= afloat1.length)
                        {
                            j = 0;
                        }
                    }

                    Shaders.configRenderResMul = afloat1[j];
                    button.displayString = "Render Quality: " + astring1[j];
                    Shaders.scheduleResize();
                    break;

                case 16:
                    float f = Shaders.configShadowResMul;
                    float[] afloat = QUALITY_MULTIPLIERS;
                    String[] astring = QUALITY_MULTIPLIER_NAMES;
                    int i = getValueIndex(f, afloat);

                    if (isShiftKeyDown())
                    {
                        --i;

                        if (i < 0)
                        {
                            i = afloat.length - 1;
                        }
                    }
                    else
                    {
                        ++i;

                        if (i >= afloat.length)
                        {
                            i = 0;
                        }
                    }

                    Shaders.configShadowResMul = afloat[i];
                    button.displayString = "Shadow Quality: " + astring[i];
                    Shaders.scheduleResizeShadow();
                    break;

                case 17:
                    Shaders.configNormalMap = !Shaders.configNormalMap;
                    button.displayString = "Normap Map: " + toStringOnOff(Shaders.configNormalMap);
                    this.mc.scheduleResourcesRefresh();
                    break;

                case 18:
                    Shaders.configSpecularMap = !Shaders.configSpecularMap;
                    button.displayString = "Specular Map: " + toStringOnOff(Shaders.configSpecularMap);
                    this.mc.scheduleResourcesRefresh();
                    break;

                case 19:
                    Shaders.configOldLighting = !Shaders.configOldLighting;
                    button.displayString = "Classic Lighting: " + toStringOnOff(Shaders.configOldLighting);
                    Shaders.updateBlockLightLevel();
                    this.mc.scheduleResourcesRefresh();
                    break;

                case 20:
                    Shaders.nextAntialiasingLevel();
                    button.displayString = "Antialiasing: " + toStringAa(Shaders.configAntialiasingLevel);
                    Shaders.uninit();
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.shaderList.drawScreen(mouseX, mouseY, partialTicks);

        if (this.updateTimer <= 0)
        {
            this.shaderList.updateList();
            this.updateTimer += 20;
        }

        this.drawCenteredString(this.fontRendererObj, "Shaders ", this.width / 2, 15, 16777215);
        String s = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
        int i = this.fontRendererObj.getStringWidth(s);

        if (i < this.width - 5)
        {
            this.drawCenteredString(this.fontRendererObj, s, this.width / 2, this.height - 40, 8421504);
        }
        else
        {
            this.drawString(this.fontRendererObj, s, 5, this.height - 40, 8421504);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        --this.updateTimer;
    }

    public Minecraft getMc()
    {
        return this.mc;
    }

    public void drawCenteredString(String text, int x, int y, int color)
    {
        this.drawCenteredString(this.fontRendererObj, text, x, y, color);
    }

    public static int getOSType()
    {
        String s = System.getProperty("os.name").toLowerCase();
        return s.contains("win") ? 1 : (s.contains("mac") ? 2 : (s.contains("solaris") ? 3 : (s.contains("sunos") ? 3 : (s.contains("linux") ? 4 : (s.contains("unix") ? 4 : 0)))));
    }
}
