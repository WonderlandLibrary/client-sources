package mods.batty.main;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

public class BattyUI extends Gui
{
    public final Minecraft mc;
    int showCoords = 1;
    boolean shadedCoords = true;
    boolean hideTimer = false;
    boolean shadedTimer = true;
    public boolean timerRunning = false;
    public boolean toggleTimer = false;
    public boolean resetTimer = false;
    boolean shadedFPS = true;
    boolean hideFPS = false;
    boolean coordsCopyTPFormat = false;
    int myTitleText = 16746496;
    int myPosCoordText = 5636095;
    int myNegCoordText = 13434879;
    int myPosChunkText = 16777215;
    int myNegChunkText = 11184810;
    int myCompassText = 16746496;
    int myChevronText = 5636095;
    int myBiomeText = 11184810;
    int myRectColour = 1431655765;
    int myTimerStopText = 16746496;
    int myTimerRunText = 5636095;
    int myFPSText = 5636095;
    int myPosX;
    int myPosY;
    int myPosZ;
    boolean myXminus;
    boolean myZminus;
    int myAngle;
    int myDir;
    int myMoveX;
    int myMoveZ;
    int myFind;
    protected static final ResourceLocation batUIResourceLocation = new ResourceLocation("battymod/batheart_icon.png");
    static float batLogoScaler = 0.036F;
    static int batLogoU = 0;
    static int batLogoV = 0;
    static int batLogoX = (int)(256.0F * batLogoScaler);
    static int batLogoY = (int)(256.0D * (double)batLogoScaler);
    int coordLocation = 0;
    int myXLine;
    int myYLine;
    int myZLine;
    int myBiomeLine;
    int myBaseOffset;
    int myCoord0Offset;
    int myCoord1Offset;
    int myCoord2Offset;
    int myRHSlocation;
    int coordBoxW;
    int coordBoxH;
    int coordBoxL;
    int coordBoxR;
    int coordBoxTop;
    int coordBoxBase;
    int timerLocation = 2;
    int clockBoxW;
    int clockBoxH;
    int clockBoxL;
    int clockBoxR;
    int clockBoxTop;
    int clockBoxBase;
    int myTimerLine;
    int myTimerOffset;
    int fpsLocation = 1;
    int fpsBoxW;
    int fpsBoxH;
    int fpsBoxL;
    int fpsBoxR;
    int fpsBoxTop;
    int fpsBoxBase;
    int myFPSLine;
    int myFPSOffset;
    public String myChevronUp = "+";
    public String myChevronDown = "-";
    public static final String[] myCardinalPoint = new String[] {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
    public static final String[] myColourList = new String[] {"black", "darkblue", "darkgreen", "darkaqua", "darkred", "purple", "brown", "grey", "darkgrey", "blue", "green", "aqua", "sage", "violet", "orange", "lime", "silver", "coolblue", "red", "gold", "oldgold", "lightpurple", "pink", "yellow", "white"};
    public static final int[] myColourCodes = new int[] {0, 170, 43520, 43690, 11141120, 11141290, 11162880, 11184810, 5592405, 5592575, 5635925, 5636095, 8956416, 8934860, 13391104, 13434624, 13421772, 13434879, 16733525, 16755200, 16746496, 16733695, 16755370, 16777045, 16777215};
    public int myMoon;
    public File optionsFile;
    public File runtimeFile;
    public int secondCounter = 0;
    public int minuteCounter = 0;
    public int hourCounter = 0;
    public int tickCounter = 0;
    Properties propts = new Properties();
    Properties proprt = new Properties();
    public static KeyBinding hideunhideCoordskey = new KeyBinding("Hide / Unhide Coords", 75, "Batty\'s Coordinates");
    public static KeyBinding moveCoordScreenPos = new KeyBinding("Change Coords Screen Position", 79, "Batty\'s Coordinates");
    public static KeyBinding copyCoordsClipboard = new KeyBinding("Copy Coords to Clipboard", 71, "Batty\'s Coordinates");
    public static KeyBinding hideunhideTimerkey = new KeyBinding("Hide / Unhide Timer", 76, "Batty\'s Timer");
    public static KeyBinding startstopTimerkey = new KeyBinding("Start / Stop Timer", 83, "Batty\'s Timer");
    public static KeyBinding resetTimerkey = new KeyBinding("Reset Timer to Zero", 82, "Batty\'s Timer");
    public static KeyBinding moveTimerScreenPos = new KeyBinding("Change Timer Screen Position", 80, "Batty\'s Timer");
    public static KeyBinding hideunhideFPSkey = new KeyBinding("Hide / Unhide FPS", 77, "Batty\'s FPS");
    public static KeyBinding moveFPSScreenPos = new KeyBinding("Change FPS Screen Position", 81, "Batty\'s FPS");

    public BattyUI(Minecraft par1Minecraft)
    {
        BattyMod.getInstance().setBatheartgui(this);
        this.mc = par1Minecraft;
        this.optionsFile = new File(this.mc.mcDataDir, "BatMod.properties");
        this.runtimeFile = new File(this.mc.mcDataDir, "BatMod.runtime");
        BattyConfig.retrieveOptions();
        BattyConfig.retrieveRuntimeOptions();
        List<KeyBinding> list = Arrays.<KeyBinding>asList(new KeyBinding[] {hideunhideCoordskey, moveCoordScreenPos, copyCoordsClipboard, hideunhideTimerkey, resetTimerkey, moveTimerScreenPos, startstopTimerkey, hideunhideFPSkey, moveFPSScreenPos});

        try
        {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(new File(this.mc.mcDataDir, "options.txt")));
            String s = "";

            while ((s = bufferedreader.readLine()) != null)
            {
                for (KeyBinding keybinding : list)
                {
                    String[] astring = s.split(":");

                    if (astring[0].equals("key_" + keybinding.getKeyDescription()))
                    {
                        keybinding.setKeyCode(Integer.parseInt(astring[1]));
                    }
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        for (KeyBinding keybinding1 : list)
        {
            this.mc.gameSettings.keyBindings = (KeyBinding[])((KeyBinding[])ArrayUtils.add(this.mc.gameSettings.keyBindings, keybinding1));
        }
    }

    public void drawTexture(int x, int y, int u, int v, int width, int height, ResourceLocation resourceLocation, float scaler)
    {
        x = (int)((float)x / scaler);
        y = (int)((float)y / scaler);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glScalef(scaler, scaler, scaler);
        this.mc.getTextureManager().bindTexture(resourceLocation);
        this.drawTexturedModalRect(x, y, u, v, width, height);
        GL11.glPopMatrix();
    }

    protected void drawLogoTexture(int x, int y)
    {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(255.0F, 255.0F, 255.0F, 255.0F);
        this.drawTexture(x, y, batLogoU, batLogoV, (int)((float)batLogoX / batLogoScaler), (int)((float)batLogoY / batLogoScaler), batUIResourceLocation, batLogoScaler);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void renderPlayerCoords()
    {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        FontRenderer fontrenderer = this.mc.fontRendererObj;
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        BlockPos blockpos = new BlockPos(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);
        this.myPosX = MathHelper.floor_double(this.mc.thePlayer.posX);
        this.myXminus = this.mc.thePlayer.posX < 0.0D;
        this.myPosY = MathHelper.floor_double(this.mc.thePlayer.getEntityBoundingBox().minY);
        this.myPosZ = MathHelper.floor_double(this.mc.thePlayer.posZ);
        this.myZminus = this.mc.thePlayer.posZ < 0.0D;
        this.myAngle = BattyUtils.getCardinalPoint(this.mc.thePlayer.rotationYaw);
        this.myDir = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int i = this.mc.fontRendererObj.getStringWidth(myCardinalPoint[7]);

        if (this.showCoords > 2)
        {
            this.coordBoxW = 104;
            this.coordBoxH = 40;
        }
        else
        {
            this.coordBoxW = 80;
            this.coordBoxH = 30;
        }

        switch (this.coordLocation)
        {
            case 0:
                this.coordBoxR = this.coordBoxW + 1;
                this.coordBoxBase = this.coordBoxH + 1;
                break;

            case 1:
                this.coordBoxR = scaledresolution.getScaledWidth() - 1;
                this.coordBoxBase = this.coordBoxH + 1;
                break;

            case 2:
                this.coordBoxR = scaledresolution.getScaledWidth() - 1;
                this.coordBoxBase = scaledresolution.getScaledHeight() - 1;
                break;

            case 3:
                this.coordBoxR = this.coordBoxW + 1;
                this.coordBoxBase = scaledresolution.getScaledHeight() - 1;
        }

        this.coordBoxL = this.coordBoxR - this.coordBoxW;
        this.coordBoxTop = this.coordBoxBase - this.coordBoxH;
        this.myXLine = this.coordBoxTop + 1;
        this.myYLine = this.myXLine + 10;
        this.myZLine = this.myYLine + 10;
        this.myBiomeLine = this.myZLine + 10;
        this.myBaseOffset = this.coordBoxL + 1;
        this.myCoord0Offset = this.myBaseOffset + 10;
        this.myCoord1Offset = this.myBaseOffset + 16;
        this.myCoord2Offset = this.myBaseOffset + 39;

        if (this.showCoords == 3)
        {
            this.myRHSlocation = this.coordBoxR - i - 14;
        }
        else if (this.showCoords > 2)
        {
            this.myRHSlocation = this.coordBoxR - i - 1;
        }
        else
        {
            this.myRHSlocation = this.myBaseOffset + 64;
        }

        if (this.shadedCoords)
        {
            drawRect(this.coordBoxL, this.coordBoxTop, this.coordBoxR, this.coordBoxBase, this.myRectColour);
        }

        fontrenderer.drawStringWithShadow(String.format("x: ", new Object[0]), (float)this.myBaseOffset, (float)this.myXLine, this.myTitleText);
        fontrenderer.drawStringWithShadow(String.format("y: ", new Object[0]), (float)this.myBaseOffset, (float)this.myYLine, this.myTitleText);
        fontrenderer.drawStringWithShadow(String.format("z: ", new Object[0]), (float)this.myBaseOffset, (float)this.myZLine, this.myTitleText);

        if (this.showCoords < 4)
        {
            if (!this.myXminus)
            {
                fontrenderer.drawStringWithShadow(String.format("%d", new Object[] {Integer.valueOf(this.myPosX)}), (float)this.myCoord1Offset, (float)this.myXLine, this.myPosCoordText);
            }
            else
            {
                fontrenderer.drawStringWithShadow("-", (float)this.myCoord0Offset, (float)this.myXLine, this.myNegCoordText);
                fontrenderer.drawStringWithShadow(String.format("%d", new Object[] {Integer.valueOf(Math.abs(this.myPosX))}), (float)this.myCoord1Offset, (float)this.myXLine, this.myNegCoordText);
            }

            fontrenderer.drawStringWithShadow(String.format("%d", new Object[] {Integer.valueOf(this.myPosY)}), (float)this.myCoord1Offset, (float)this.myYLine, this.myPosCoordText);

            if (!this.myZminus)
            {
                fontrenderer.drawStringWithShadow(String.format("%d", new Object[] {Integer.valueOf(this.myPosZ)}), (float)this.myCoord1Offset, (float)this.myZLine, this.myPosCoordText);
            }
            else
            {
                fontrenderer.drawStringWithShadow("-", (float)this.myCoord0Offset, (float)this.myZLine, this.myNegCoordText);
                fontrenderer.drawStringWithShadow(String.format("%d", new Object[] {Integer.valueOf(Math.abs(this.myPosZ))}), (float)this.myCoord1Offset, (float)this.myZLine, this.myNegCoordText);
            }
        }
        else
        {
            if (this.myPosX >= 0)
            {
                fontrenderer.drawStringWithShadow(String.format("c%d ", new Object[] {Integer.valueOf(this.myPosX >> 4)}), (float)this.myCoord2Offset, (float)this.myXLine, this.myPosChunkText);
                fontrenderer.drawStringWithShadow(String.format("b%d", new Object[] {Integer.valueOf(this.myPosX & 15)}), (float)this.myCoord1Offset, (float)this.myXLine, this.myPosChunkText);
            }
            else
            {
                fontrenderer.drawStringWithShadow(String.format("c%d ", new Object[] {Integer.valueOf(this.myPosX >> 4)}), (float)this.myCoord2Offset, (float)this.myXLine, this.myNegChunkText);
                fontrenderer.drawStringWithShadow(String.format("b%d", new Object[] {Integer.valueOf(this.myPosX & 15)}), (float)this.myCoord1Offset, (float)this.myXLine, this.myPosChunkText);
            }

            fontrenderer.drawStringWithShadow(String.format("%d", new Object[] {Integer.valueOf(this.myPosY)}), (float)this.myCoord1Offset, (float)this.myYLine, this.myPosCoordText);

            if (this.myPosZ >= 0)
            {
                fontrenderer.drawStringWithShadow(String.format("c%d ", new Object[] {Integer.valueOf(this.myPosZ >> 4)}), (float)this.myCoord2Offset, (float)this.myZLine, this.myPosChunkText);
                fontrenderer.drawStringWithShadow(String.format("b%d", new Object[] {Integer.valueOf(this.myPosZ & 15)}), (float)this.myCoord1Offset, (float)this.myZLine, this.myPosChunkText);
            }
            else
            {
                fontrenderer.drawStringWithShadow(String.format("c%d ", new Object[] {Integer.valueOf(this.myPosZ >> 4)}), (float)this.myCoord2Offset, (float)this.myZLine, this.myNegChunkText);
                fontrenderer.drawStringWithShadow(String.format("b%d", new Object[] {Integer.valueOf(this.myPosZ & 15)}), (float)this.myCoord1Offset, (float)this.myZLine, this.myPosChunkText);
            }
        }

        this.drawLogoTexture(this.myRHSlocation - 12, this.myYLine - 1);
        fontrenderer.drawStringWithShadow(myCardinalPoint[this.myAngle], (float)this.myRHSlocation, (float)this.myYLine, this.myCompassText);

        if (this.showCoords > 1)
        {
            switch (this.myAngle)
            {
                case 0:
                    fontrenderer.drawStringWithShadow(this.myChevronDown + this.myChevronDown, (float)this.myRHSlocation, (float)this.myZLine, this.myNegCoordText);
                    break;

                case 1:
                    fontrenderer.drawStringWithShadow(this.myChevronDown, (float)this.myRHSlocation, (float)this.myZLine, this.myNegCoordText);
                    fontrenderer.drawStringWithShadow(this.myChevronUp, (float)this.myRHSlocation, (float)this.myXLine, this.myPosCoordText);
                    break;

                case 2:
                    fontrenderer.drawStringWithShadow(this.myChevronUp + this.myChevronUp, (float)this.myRHSlocation, (float)this.myXLine, this.myPosCoordText);
                    break;

                case 3:
                    fontrenderer.drawStringWithShadow(this.myChevronUp, (float)this.myRHSlocation, (float)this.myXLine, this.myPosCoordText);
                    fontrenderer.drawStringWithShadow(this.myChevronUp, (float)this.myRHSlocation, (float)this.myZLine, this.myPosCoordText);
                    break;

                case 4:
                    fontrenderer.drawStringWithShadow(this.myChevronUp + this.myChevronUp, (float)this.myRHSlocation, (float)this.myZLine, this.myPosCoordText);
                    break;

                case 5:
                    fontrenderer.drawStringWithShadow(this.myChevronUp, (float)this.myRHSlocation, (float)this.myZLine, this.myPosCoordText);
                    fontrenderer.drawStringWithShadow(this.myChevronDown, (float)this.myRHSlocation, (float)this.myXLine, this.myNegCoordText);
                    break;

                case 6:
                    fontrenderer.drawStringWithShadow(this.myChevronDown + this.myChevronDown, (float)this.myRHSlocation, (float)this.myXLine, this.myNegCoordText);
                    break;

                case 7:
                    fontrenderer.drawStringWithShadow(this.myChevronDown, (float)this.myRHSlocation, (float)this.myXLine, this.myNegCoordText);
                    fontrenderer.drawStringWithShadow(this.myChevronDown, (float)this.myRHSlocation, (float)this.myZLine, this.myNegCoordText);
            }
        }

        if (this.showCoords > 2 && this.mc.theWorld != null && this.mc.theWorld.isBlockLoaded(blockpos))
        {
            Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(blockpos);
            fontrenderer.drawStringWithShadow(chunk.getBiome(blockpos, this.mc.theWorld.getWorldChunkManager()).biomeName, (float)this.myBaseOffset, (float)this.myBiomeLine, this.myBiomeText);
        }
    }

    public void renderPlayerTimer()
    {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        String s = BattyUtils.constructTimeString();
        int i = this.mc.fontRendererObj.getStringWidth(s);
        this.clockBoxW = 12 + i;
        this.clockBoxH = 10;

        switch (this.timerLocation)
        {
            case 0:
                this.clockBoxR = this.clockBoxW + 1;
                this.clockBoxBase = this.clockBoxH + 1;

                if (this.coordLocation == 0)
                {
                    this.clockBoxBase += this.coordBoxH + 1;
                }

                break;

            case 1:
                this.clockBoxR = scaledresolution.getScaledWidth() / 2 + this.clockBoxW / 2;
                this.clockBoxBase = this.clockBoxH + 1;
                break;

            case 2:
                this.clockBoxR = scaledresolution.getScaledWidth() - 1;
                this.clockBoxBase = this.clockBoxH + 1;

                if (this.coordLocation == 1)
                {
                    this.clockBoxBase += this.coordBoxH + 1;
                }

                break;

            case 3:
                this.clockBoxR = scaledresolution.getScaledWidth() - 1;
                this.clockBoxBase = scaledresolution.getScaledHeight() - 1;

                if (this.coordLocation == 2)
                {
                    this.clockBoxBase -= this.coordBoxH + 1;
                }

                break;

            case 4:
                this.clockBoxR = this.clockBoxW + 1;
                this.clockBoxBase = scaledresolution.getScaledHeight() - 15;

                if (this.coordLocation == 3)
                {
                    this.clockBoxBase -= this.coordBoxH + 1;
                }
        }

        this.clockBoxL = this.clockBoxR - this.clockBoxW;
        this.clockBoxTop = this.clockBoxBase - this.clockBoxH;
        this.myTimerLine = this.clockBoxTop + 1;
        this.myTimerOffset = this.clockBoxL + 6;

        if (this.shadedTimer)
        {
            drawRect(this.clockBoxL, this.clockBoxTop, this.clockBoxR, this.clockBoxBase, this.myRectColour);
        }

        if (this.timerRunning)
        {
            this.mc.fontRendererObj.drawStringWithShadow(s, (float)this.myTimerOffset, (float)this.myTimerLine, this.myTimerRunText);
        }
        else
        {
            this.mc.fontRendererObj.drawStringWithShadow(s, (float)this.myTimerOffset, (float)this.myTimerLine, this.myTimerStopText);
        }
    }

    public void renderPlayerFPS()
    {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        String s = this.mc.debug.split(" ")[0] + " FPS";
        int i = this.mc.fontRendererObj.getStringWidth(s);
        this.fpsBoxW = 12 + i;
        this.fpsBoxH = 10;

        switch (this.fpsLocation)
        {
            case 0:
                this.fpsBoxR = this.fpsBoxW + 1;
                this.fpsBoxBase = this.fpsBoxH + 1;

                if (this.timerLocation == 0)
                {
                    this.fpsBoxBase += this.clockBoxH + 1;
                }

                if (this.coordLocation == 0)
                {
                    this.fpsBoxBase += this.coordBoxH + 1;
                }

                break;

            case 1:
                this.fpsBoxR = scaledresolution.getScaledWidth() / 2 + this.fpsBoxW / 2;
                this.fpsBoxBase = this.fpsBoxH + 1;

                if (this.timerLocation == 1)
                {
                    this.fpsBoxBase += this.clockBoxH + 1;
                }

                break;

            case 2:
                this.fpsBoxR = scaledresolution.getScaledWidth() - 1;
                this.fpsBoxBase = this.fpsBoxH + 1;

                if (this.timerLocation == 2)
                {
                    this.fpsBoxBase += this.clockBoxH + 1;
                }

                if (this.coordLocation == 1)
                {
                    this.fpsBoxBase += this.coordBoxH + 1;
                }

                break;

            case 3:
                this.fpsBoxR = scaledresolution.getScaledWidth() - 1;
                this.fpsBoxBase = scaledresolution.getScaledHeight() - 1;

                if (this.timerLocation == 3)
                {
                    this.fpsBoxBase -= this.clockBoxH + 1;
                }

                if (this.coordLocation == 2)
                {
                    this.fpsBoxBase -= this.coordBoxH + 1;
                }

                break;

            case 4:
                this.fpsBoxR = this.fpsBoxW + 1;
                this.fpsBoxBase = scaledresolution.getScaledHeight() - 15;

                if (this.timerLocation == 4)
                {
                    this.fpsBoxBase -= this.clockBoxH + 1;
                }

                if (this.coordLocation == 3)
                {
                    this.fpsBoxBase -= this.coordBoxH + 1;
                }
        }

        this.fpsBoxL = this.fpsBoxR - this.fpsBoxW;
        this.fpsBoxTop = this.fpsBoxBase - this.fpsBoxH;
        this.myFPSLine = this.fpsBoxTop + 1;
        this.myFPSOffset = this.fpsBoxL + 6;

        if (this.shadedFPS)
        {
            drawRect(this.fpsBoxL, this.fpsBoxTop, this.fpsBoxR, this.fpsBoxBase, this.myRectColour);
        }

        this.mc.fontRendererObj.drawStringWithShadow(s, (float)this.myFPSOffset, (float)this.myFPSLine, this.myFPSText);
    }

    public void hideUnhideCoords()
    {
        ++this.showCoords;

        if (this.showCoords > 4)
        {
            this.showCoords = 0;
        }

        BattyConfig.storeRuntimeOptions();
    }

    public void rotateScreenCoords()
    {
        ++this.coordLocation;

        if (this.coordLocation > 2)
        {
            this.coordLocation = 0;
        }

        BattyConfig.storeRuntimeOptions();
    }

    public void copyScreenCoords()
    {
        StringSelection stringselection;

        if (this.coordsCopyTPFormat)
        {
            stringselection = new StringSelection(this.myPosX + " " + this.myPosY + " " + this.myPosZ);
        }
        else
        {
            stringselection = new StringSelection("x:" + this.myPosX + " y:" + this.myPosY + " z:" + this.myPosZ);
        }

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, (ClipboardOwner)null);
    }

    public void hideUnhideStopWatch()
    {
        this.hideTimer = !this.hideTimer;
        BattyConfig.storeRuntimeOptions();
    }

    public void rotateScreenTimer()
    {
        ++this.timerLocation;

        if (this.timerLocation > 4)
        {
            this.timerLocation = 0;
        }

        BattyConfig.storeRuntimeOptions();
    }

    public void hideUnhideFPS()
    {
        this.hideFPS = !this.hideFPS;
        BattyConfig.storeRuntimeOptions();
    }

    public void rotateScreenFPS()
    {
        ++this.fpsLocation;

        if (this.fpsLocation > 3)
        {
            this.fpsLocation = 0;
        }

        BattyConfig.storeRuntimeOptions();
    }

    public void renderPlayerInfo()
    {
        if (!this.mc.gameSettings.showDebugInfo)
        {
            if (this.showCoords > 0)
            {
                this.renderPlayerCoords();
            }
            else
            {
                this.coordBoxH = 0;
            }

            if (this.hideTimer)
            {
                this.clockBoxH = 0;
            }
            else
            {
                this.renderPlayerTimer();
            }

            if (!this.hideFPS)
            {
                this.renderPlayerFPS();
            }
        }
    }
}
