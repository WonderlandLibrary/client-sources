package com.kilo.ui;

import java.util.Random;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import com.kilo.Kilo;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.CheckBox;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.TextBoxAlt;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Timer;
import com.kilo.util.Util;


public class UICreateWorld extends UI {

	private float formOffset;
	private boolean invalid;
	
	private String invalidMessage = "";
	
	private Timer invalidTimer = new Timer();

	private float fX, fY, fW, fH;

	private String worldFileName = "", worldName = "", worldSeed = "", gamemode = "survival";
    private boolean generateStructures, cheats;
	private WorldType worldType = WorldType.DEFAULT;
    private static final String[] blackList = new String[] {"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};
	
	public UICreateWorld(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Create New World";

		formOffset = 0;
		invalid = false;

		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2)+44;
		fW = Display.getWidth()-40;
		fH = Display.getHeight()-124;

		inters.clear();
		inters.add(new IconButton(this, 32, 32, 32, 32, Colors.WHITE.c, Resources.iconReturn[3]));
		int i = 0;
		inters.add(new IconButton(this, fX+(fW/2)-24-32-(48*(i++)), fY-(fH/2)+16, 32, 32, Colors.WHITE.c, Resources.iconHardcore[3]));
		inters.add(new IconButton(this, fX+(fW/2)-24-32-(48*(i++)), fY-(fH/2)+16, 32, 32, Colors.WHITE.c, Resources.iconSurvival[3]));
		inters.add(new IconButton(this, fX+(fW/2)-24-32-(48*(i++)), fY-(fH/2)+16, 32, 32, Colors.WHITE.c, Resources.iconCreative[3]));
		TextBox tb;
		inters.add(tb = new TextBoxAlt(this, "Enter World Name", fX-(fW/2)+24, fY-(fH/2)+8, 350, 52, Fonts.ttfRoundedBold20, Colors.WHITE.c, Colors.WHITE.c, 4f, Align.L, Align.C));
		tb.text = worldName;
		inters.add(new Button(this, "Default", fX-(fW/2)+524, fY-(fH/2)+128+32, ((fW-500-48)/2)-16, ((fH-256)/2)-8, Fonts.ttfRoundedBold25, 0xFF777777, Colors.GREEN.c, Resources.iconTick[2], 24, Align.L, Align.T));
		inters.add(new Button(this, "Superflat", fX-(fW/2)+524+((fW-500-48+16)/2)+8, fY-(fH/2)+128+32, ((fW-500-48)/2)-16, ((fH-256)/2)-8, Fonts.ttfRoundedBold25, 0xFF777777, Colors.GREEN.c, Resources.iconTick[2], 24, Align.L, Align.T));
		inters.add(new Button(this, "Large Biomes", fX-(fW/2)+524, fY-(fH/2)+128+32+((fH-256)/2)+8, ((fW-500-48)/2)-16, ((fH-256)/2)-8, Fonts.ttfRoundedBold25, 0xFF777777, Colors.GREEN.c, Resources.iconTick[2], 24, Align.L, Align.T));
		inters.add(new Button(this, "AMPLIFIED", fX-(fW/2)+524+((fW-500-48+16)/2)+8, fY-(fH/2)+128+32+((fH-256)/2)+8, ((fW-500-48)/2)-16, ((fH-256)/2)-8, Fonts.ttfRoundedBold25, 0xFF777777, Colors.GREEN.c, Resources.iconTick[2], 24, Align.L, Align.T));
		CheckBox cb;
		inters.add(cb = new CheckBox(this, "Allow Cheats", fX-(fW/2)+24, fY+(fH/2)-32-11, Fonts.ttfRoundedBold14, Colors.GREEN.c));
		cb.active = cheats;
		inters.add(new CheckBox(this, "Bonus Chest", fX-(fW/2)+24+22+16+24+Fonts.ttfRoundedBold14.getWidth("Allow Cheats"), fY+(fH/2)-32-11, Fonts.ttfRoundedBold14, Colors.GREEN.c));
		inters.add(cb = new CheckBox(this, "Generate Structures", fX-(fW/2)+24+((22+16+24)*2)+Fonts.ttfRoundedBold14.getWidth("Allow Cheats")+Fonts.ttfRoundedBold14.getWidth("Bonus Chest"), fY+(fH/2)-32-11, Fonts.ttfRoundedBold14, Colors.GREEN.c));
		cb.active = generateStructures;
		inters.add(new Button(this, "Create", fX+(fW/2)-16-128, fY+(fH/2)-48, 128, 32, Fonts.ttfStandard12, Colors.GREEN.c, Resources.iconSubmit[1], 16));
		inters.add(tb = new TextBoxAlt(this, "Enter World Seed", fX+(fW/2)-16-128-32-300, fY+(fH/2)-48, 300, 32, Fonts.ttfRoundedBold20, Colors.GREY.c, Colors.GREY.c, 2f, Align.L, Align.C));
		tb.text = worldSeed;
		
		setNewFileName();
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		if (invalid) {
			formOffset+= ((-Fonts.ttfStandard14.getHeight()*2)-formOffset)/5f;
			if (invalidTimer.isTime(2f)) {
				invalid = false;
			}
		} else {
			invalidTimer.reset();
		}
		if (!invalid) {
			formOffset+= (0-formOffset)/5f;
		}
		
		((IconButton)inters.get(1)).buttonColor = (gamemode == "hardcore"?Colors.WHITE.c:Colors.DARKGREY.c);
		((IconButton)inters.get(2)).buttonColor = (gamemode == "survival"?Colors.WHITE.c:Colors.DARKGREY.c);
		((IconButton)inters.get(3)).buttonColor = (gamemode == "creative"?Colors.WHITE.c:Colors.DARKGREY.c);

		((Button)inters.get(5)).buttonColor = (worldType == WorldType.DEFAULT?0xFF3F3F3F:0xFF777777);
		((Button)inters.get(6)).buttonColor = (worldType == WorldType.FLAT?0xFF3F3F3F:0xFF777777);
		((Button)inters.get(7)).buttonColor = (worldType == WorldType.LARGE_BIOMES?0xFF3F3F3F:0xFF777777);
		((Button)inters.get(8)).buttonColor = (worldType == WorldType.AMPLIFIED?0xFF3F3F3F:0xFF777777);
		
		((Button)inters.get(5)).icon = (worldType == WorldType.DEFAULT?Resources.iconTick[3]:null);
		((Button)inters.get(6)).icon = (worldType == WorldType.FLAT?Resources.iconTick[3]:null);
		((Button)inters.get(7)).icon = (worldType == WorldType.LARGE_BIOMES?Resources.iconTick[3]:null);
		((Button)inters.get(8)).icon = (worldType == WorldType.AMPLIFIED?Resources.iconTick[3]:null);
	}
	
	@Override
	public void interact(Inter i) {
		switch (inters.indexOf(i)) {
		case 0:
			UIHandler.changeUI(parent);
			break;
		case 1:
			gamemode = "hardcore";
			break;
		case 2:
			gamemode = "survival";
			break;
		case 3:
			gamemode = "creative";
			break;
		case 5:
			worldType = WorldType.DEFAULT;
			break;
		case 6:
			worldType = WorldType.FLAT;
			break;
		case 7:
			worldType = WorldType.LARGE_BIOMES;
			break;
		case 8:
			worldType = WorldType.AMPLIFIED;
			break;
		case 12:
			this.mc.displayGuiScreen((GuiScreen)null);

            long var2 = (new Random()).nextLong();
            String var4 = ((TextBox)inters.get(13)).text;

            if (!StringUtils.isEmpty(var4))
            {
                try
                {
                    long var5 = Long.parseLong(var4);

                    if (var5 != 0L)
                    {
                        var2 = var5;
                    }
                }
                catch (NumberFormatException var7)
                {
                    var2 = (long)var4.hashCode();
                }
            }

            WorldSettings.GameType var8 = WorldSettings.GameType.getByName(gamemode);
            WorldSettings var6 = new WorldSettings(var2, var8, ((CheckBox)inters.get(11)).active, gamemode == "hardcore", worldType);
            var6.setWorldName(((TextBox)inters.get(4)).text);

            if (((CheckBox)inters.get(10)).active && gamemode != "hardcore")
            {
                var6.enableBonusChest();
            }

            if (((CheckBox)inters.get(9)).active && gamemode != "hardcore")
            {
                var6.enableCommands();
            }

            this.mc.launchIntegratedServer(worldFileName, ((TextBox)inters.get(4)).text, var6);
			break;
		}
		if (i instanceof CheckBox) {
			i.active = !i.active;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
	}
	
	public void mouseRelease(int mx, int my, int b) {
		super.mouseRelease(mx, my, b);
	}
	
	public void mouseScroll(int s) {
		super.mouseScroll(s);
	}
	
	public void keyboardPress(int key) {
		super.keyboardPress(key);
	}

	public void keyTyped(int key, char keyChar) {
		super.keyTyped(key, keyChar);
		setNewFileName();
	}
	
	private void setNewFileName() {
        worldFileName = ((TextBox)inters.get(4)).text == null?"":((TextBox)inters.get(4)).text;
        char[] var1 = ChatAllowedCharacters.allowedCharactersArray;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            char var4 = var1[var3];
            worldFileName = worldFileName.replace(var4, '_');
        }

        if (StringUtils.isEmpty(worldFileName))
        {
            worldFileName = "New World";
        }

        worldFileName = func_146317_a(mc.getSaveLoader(), worldFileName);
    }

    public static String func_146317_a(ISaveFormat p_146317_0_, String p_146317_1_)
    {
        p_146317_1_ = p_146317_1_.replaceAll("[\\./\"]", "_");
        String[] var2 = blackList;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            String var5 = var2[var4];

            if (p_146317_1_.equalsIgnoreCase(var5))
            {
                p_146317_1_ = "_" + p_146317_1_ + "_";
            }
        }

        while (p_146317_0_.getWorldInfo(p_146317_1_) != null)
        {
            p_146317_1_ = p_146317_1_ + "-";
        }

        return p_146317_1_;
    }
    
    public void set(WorldInfo p_146318_1_)
    {
        worldName = I18n.format("selectWorld.newWorld.copyOf", new Object[] {p_146318_1_.getWorldName()});
        worldSeed = p_146318_1_.getSeed() + "";
        worldType = p_146318_1_.getTerrainType();
        //this.field_146334_a = p_146318_1_.getGeneratorOptions();
        generateStructures = p_146318_1_.isMapFeaturesEnabled();
        cheats = p_146318_1_.areCommandsAllowed();

        if (p_146318_1_.isHardcoreModeEnabled())
        {
            gamemode = "hardcore";
        }
        else if (p_146318_1_.getGameType().isSurvivalOrAdventure())
        {
        	gamemode = "survival";
        }
        else if (p_146318_1_.getGameType().isCreative())
        {
        	gamemode = "creative";
        }
    }
	
	public void render(float opacity) {
		drawDarkerBackground(false, opacity);
		
		Draw.string(Fonts.ttfRoundedBold40, Display.getWidth()/2, fY-(fH/2)-(Fonts.ttfRoundedBold40.getHeight(title)/2)-10, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+64, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		Draw.rect(fX-(fW/2), fY-(fH/2)+64, fX+(fW/2), fY-(fH/2)+128, Util.reAlpha(0xFF111111, 1f*opacity));
		Draw.rect(fX-(fW/2), fY-(fH/2)+128, fX+(fW/2), fY+(fH/2)-64, Util.reAlpha(Colors.BLUE.c, 1f*opacity));
		Draw.rect(fX-(fW/2), fY+(fH/2)-64, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF111111, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold14, fX-(fW/2)+48+(inters.get(4).width), fY-(fH/2)+32, "Will be saved in:", Util.reAlpha(Colors.DARKGREY.c, 1f*opacity), Align.L, Align.B); 
		Draw.string(Fonts.ttfRoundedBold14, fX-(fW/2)+48+(inters.get(4).width), fY-(fH/2)+32, worldFileName, Util.reAlpha(Colors.DARKGREY.c, 1f*opacity), Align.L, Align.T);
		
		Draw.string(Fonts.ttfRoundedBold20, fX+(fW/2)-24-16-(48*3), fY-(fH/2)+32, "Game Mode:", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.R, Align.C);
		
		String mode = gamemode.substring(0, 1).toUpperCase()+gamemode.substring(1, gamemode.length());
		int color = gamemode == "creative"?Colors.BLUE.c:gamemode == "survival"?Colors.RED.c:Colors.ORANGE.c;
		Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+24, fY-(fH/2)+64+32, mode+" Mode", Util.reAlpha(color, 1f*opacity), Align.L, Align.C);
		Draw.string(Fonts.ttfRoundedBold14, fX+(fW/2)-24, fY-(fH/2)+64+32, "Search for resources, crafting, gain levels, health and hunger", Util.reAlpha(Colors.GREEN.c, 1f*opacity), Align.R, Align.C);
		
		Draw.rectTexture(fX-(fW/2)+250-(Resources.createWorld.getImageWidth()/2), fY-(Resources.createWorld.getImageHeight()/2)+24, Resources.createWorld.getImageWidth(), Resources.createWorld.getImageHeight(), Resources.createWorld, 1f*opacity);

		//Message
		Draw.startClip(fX-(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)-40+(Fonts.ttfStandard14.getHeight()/2), fX+(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)-40+(Fonts.ttfStandard14.getHeight()*1.5f));
		Draw.string(Fonts.ttfStandard14, fX, fY+(fH/2)-40-(Fonts.ttfStandard14.getHeight(invalidMessage))-formOffset, invalidMessage, Util.reAlpha(0xFFFF5555, 1f*opacity), Align.C, Align.C);
		Draw.endClip();
		
		super.render(opacity);
	}
}
