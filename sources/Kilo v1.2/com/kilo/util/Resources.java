package com.kilo.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import net.minecraft.util.ResourceLocation;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.kilo.Kilo;
import com.kilo.manager.TextureManager;
import com.kilo.render.TextureImage;


public class Resources {

	public static File soundNotification = new File("./KiLO/audio/notification.mp3");
	
	public static Texture backgroundLight;
	public static Texture backgroundDim;
	public static Texture branding;
	public static Texture brandingSmall;
	public static Texture createWorld;
	public static Texture wizard;
	
	public static ResourceLocation textureBlank = new ResourceLocation("textures/blank.png");
	
	public static Texture[] iconAccept = new Texture[6];
	public static Texture[] iconAdd = new Texture[6];
	public static Texture[] iconAdventure = new Texture[6];
	public static Texture[] iconArrowDown = new Texture[6];
	public static Texture[] iconArrowUp = new Texture[6];
	public static Texture[] iconBack = new Texture[6];
	public static Texture[] iconCharts = new Texture[6];
	public static Texture[] iconClose = new Texture[6];
	public static Texture[] iconCreative = new Texture[6];
	public static Texture[] iconDecline = new Texture[6];
	public static Texture[] iconDelete = new Texture[6];
	public static Texture[] iconEdit = new Texture[6];
	public static Texture[] iconExit = new Texture[6];
	public static Texture[] iconGoto = new Texture[6];
	public static Texture[] iconHardcore = new Texture[6];
	public static Texture[] iconHome = new Texture[6];
	public static Texture[] iconKey = new Texture[6];
	public static Texture[] iconMultiplayer = new Texture[6];
	public static Texture[] iconMusic = new Texture[6];
	public static Texture[] iconNext = new Texture[6];
	public static Texture[] iconPause = new Texture[6];
	public static Texture[] iconPlay = new Texture[6];
	public static Texture[] iconPlus = new Texture[6];
	public static Texture[] iconPrev = new Texture[6];
	public static Texture[] iconRefresh = new Texture[6];
	public static Texture[] iconReturn = new Texture[6];
	public static Texture[] iconSearch = new Texture[6];
	public static Texture[] iconSend = new Texture[6];
	public static Texture[] iconSettings = new Texture[6];
	public static Texture[] iconShield = new Texture[6];
	public static Texture[] iconSingleplayer = new Texture[6];
	public static Texture[] iconSpectator = new Texture[6];
	public static Texture[] iconStar = new Texture[6];
	public static Texture[] iconStatistics = new Texture[6];
	public static Texture[] iconSubmit = new Texture[6];
	public static Texture[] iconSurvival = new Texture[6];
	public static Texture[] iconTick = new Texture[6];
	public static Texture[] iconUser = new Texture[6];
	public static Texture[][] iconVolume = new Texture[3][6];
	public static Texture[] iconWifi = new Texture[6];
	
	public static Texture iconSeparator;
	public static Texture[] iconHacks = new Texture[7];
	
	private static final String dom = "assets/kilo/";
	private static final String tex = dom+"textures/";
	private static final String fnt = dom+"fonts/";

	public static void loadTextures() throws Exception {
		backgroundLight = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/background-light.png"));
		backgroundDim = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/background-dim.png"));
		branding = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/branding.png"));
		brandingSmall = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/branding-small.png"));
		createWorld = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/create-world.png"));
		wizard = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/wizard.png"));
		
		iconSeparator = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/hacks/separator.png"));
		iconHacks[0] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/hacks/all.png"));
		iconHacks[1] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/hacks/build.png"));
		iconHacks[2] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/hacks/combat.png"));
		iconHacks[3] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/hacks/display.png"));
		iconHacks[4] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/hacks/misc.png"));
		iconHacks[5] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/hacks/movement.png"));
		iconHacks[6] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/hacks/player.png"));
		
		int[] sizes = new int[] {8, 16, 24, 32, 48, 64};
		for(int i = 0; i < 6; i++) {
			int j = sizes[i];
			iconAccept[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/accept.png"));
			iconAdd[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/add.png"));
			iconAdventure[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/adventure.png"));
			iconArrowDown[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/arrow_down.png"));
			iconArrowUp[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/arrow_up.png"));
			iconBack[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/back.png"));
			iconCharts[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/charts.png"));
			iconClose[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/close.png"));
			iconCreative[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/creative.png"));
			iconDecline[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/decline.png"));
			iconDelete[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/delete.png"));
			iconEdit[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/edit.png"));
			iconExit[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/exit.png"));
			iconGoto[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/goto.png"));
			iconHardcore[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/hardcore.png"));
			iconHome[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/home.png"));
			iconKey[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/key.png"));
			iconMultiplayer[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/multiplayer.png"));
			iconMusic[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/music.png"));
			iconNext[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/next.png"));
			iconPause[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/pause.png"));
			iconPlay[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/play.png"));
			iconPlus[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/plus.png"));
			iconPrev[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/prev.png"));
			iconRefresh[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/refresh.png"));
			iconReturn[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/return.png"));
			iconSearch[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/search.png"));
			iconSend[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/send.png"));
			iconSettings[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/settings.png"));
			iconShield[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/shield.png"));
			iconSingleplayer[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/singleplayer.png"));
			iconSpectator[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/spectate.png"));
			iconStar[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/star.png"));
			iconStatistics[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/statistics.png"));
			iconSubmit[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/submit.png"));
			iconSurvival[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/survival.png"));
			iconTick[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/tick.png"));
			iconUser[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/user.png"));
			iconVolume[0][i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/volume.png"));
			iconVolume[1][i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/volume1.png"));
			iconVolume[2][i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/volume2.png"));
			iconWifi[i] = TextureLoader.getTexture("PNG", Kilo.kilo().getClass().getClassLoader().getResourceAsStream(tex+"gui/icons/"+String.valueOf(j)+"/wifi.png"));
		}
	}
	
	public static TextureImage downloadTexture(final String imageURL) {
		if (TextureManager.exists(imageURL)) {
			if (TextureManager.get(imageURL) != null) {
				return TextureManager.get(imageURL);
			}
		}
		TextureImage textureImage = new TextureImage();
		textureImage.location = imageURL;
		final TextureImage ti = textureImage;
		TextureManager.cache.add(ti);
		new Thread() {
			@Override
			public void run() {
				try {
				    URL url = new URL(imageURL);
				    InputStream in = new BufferedInputStream(url.openStream());
				    ByteArrayOutputStream out = new ByteArrayOutputStream();
				    byte[] buf = new byte[1024];
				    int n = 0;
				    while (-1!=(n=in.read(buf)))
				    {
				       out.write(buf, 0, n);
				    }
				    out.close();
				    in.close();
				    ti.pixels = out.toByteArray();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		}.start();
		return ti;
	}
}
