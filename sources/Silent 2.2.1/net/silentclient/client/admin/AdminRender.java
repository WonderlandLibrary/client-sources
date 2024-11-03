package net.silentclient.client.admin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.AnimatedResourceLocation;
import net.silentclient.client.cosmetics.ShieldData;
import net.silentclient.client.cosmetics.StaticResourceLocation;
import net.silentclient.client.cosmetics.gui.CosmeticsGui;
import net.silentclient.client.gui.elements.StaticButton;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.mods.CustomFontRenderer.RenderMode;
import net.silentclient.client.utils.FileUtils;
import net.silentclient.client.utils.Players;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.DecimalFormat;

public class AdminRender extends GuiScreen {
	public static File adminRenderPath = new File(Client.getInstance().configDir, "admin-render");
	
	// Settings
	private boolean showSettings = false;
	private static boolean savingPreview = false;
	private float scale = 1.0F;
	private int rotate = 0;
	private int redBackground = 0;
	private int greenBackground = 0;
	private int blueBackground = 0;
	
	// Information
	private int frames = 1;
	private int currentFrame = 0;
	
	public AdminRender() {
		if(Client.getInstance().getAccount().isStaff()) {
			if(!adminRenderPath.exists()) {
				adminRenderPath.mkdirs();
			}
		}
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.showSettings = false;
		AdminRender.savingPreview = false;
		this.scale = 1.0F;
		this.redBackground = 0;
		this.greenBackground = 0;
		this.blueBackground = 0;
		this.rotate = 0;
		loadTextureCosmetic("none");
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		super.drawDefaultBackground();
		
		((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$setShoulders(true);
		((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$setCapeType("Rectangle");
		if(((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getCape() != null) {
			((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getCape().setCurrentFrame(this.currentFrame);
		}
		if(((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getBandana() != null) {
			((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getBandana().setCurrentFrame(this.currentFrame);
		}
		if(((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getShield() != null && ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getShield().getTexture() != null) {
			((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getShield().getTexture().setCurrentFrame(this.currentFrame);
		}
		
		CustomFontRenderer font = new CustomFontRenderer();
        font.setRenderMode(RenderMode.CUSTOM);
		ScaledResolution sr = new ScaledResolution(mc);
		RenderUtils.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(redBackground, greenBackground, blueBackground).getRGB());
		if(!AdminRender.savingPreview) {
			StaticButton.render(2, 2, 50, 12, this.showSettings ? "Close" : "Settings");
			if(this.showSettings) {
				int settingY = 20;
				int sliderLeft = 50;
				
				// Scale
				font.drawString("Scale:", 2, settingY, -1, true);
				RenderUtils.drawRect(sliderLeft, settingY, 90, 9, Color.black.getRGB());
                RenderUtils.drawRect(sliderLeft, settingY, 90F * (float) (scale / 5.0D), 9, -1);
                boolean scaleDrag = MouseUtils.isInside(mouseX, mouseY, sliderLeft, settingY - 1, 90, 9) && Mouse.isButtonDown(0);
                if (scaleDrag) {
                    double diff = 5.0D - 1.0F;
                    double mouse = MathHelper.clamp_double((mouseX - sliderLeft) / 90D, 0, 1);
                    double newVal = 1.0F + mouse * diff;
                    this.scale = (float) newVal;
                }
                font.drawString(new DecimalFormat("0.00").format(scale), sliderLeft + 95, settingY, -1, true);
                settingY += 15;
                
                // Rotate
				font.drawString("Rotate:", 2, settingY, -1, true);
				RenderUtils.drawRect(sliderLeft, settingY, 90, 9, Color.black.getRGB());
                RenderUtils.drawRect(sliderLeft, settingY, 90F * (float) (rotate / 360.0D), 9, -1);
                boolean rotateDrag = MouseUtils.isInside(mouseX, mouseY, sliderLeft, settingY - 1, 90, 9) && Mouse.isButtonDown(0);
                if (rotateDrag) {
                    double diff = 360D - 0F;
                    double mouse = MathHelper.clamp_double((mouseX - sliderLeft) / 90D, 0, 1);
                    double newVal = 0 + mouse * diff;
                    this.rotate = (int) newVal;
                }
                font.drawString(new DecimalFormat("0.00").format(rotate), sliderLeft + 95, settingY, -1, true);
                settingY += 15;
                
                // Background Red
				font.drawString("BG Red:", 2, settingY, -1, true);
				RenderUtils.drawRect(sliderLeft, settingY, 90, 9, Color.black.getRGB());
                RenderUtils.drawRect(sliderLeft, settingY, 90F * (float) (redBackground / 255.0D), 9, -1);
                boolean bgRedDrag = MouseUtils.isInside(mouseX, mouseY, sliderLeft, settingY - 1, 90, 9) && Mouse.isButtonDown(0);
                if (bgRedDrag) {
                    double diff = 255D - 0F;
                    double mouse = MathHelper.clamp_double((mouseX - sliderLeft) / 90D, 0, 1);
                    double newVal = 0 + mouse * diff;
                    this.redBackground = (int) newVal;
                }
                font.drawString(new DecimalFormat("0.00").format(redBackground), sliderLeft + 95, settingY, -1, true);
                settingY += 15;
                
                // Background Green
				font.drawString("BG Green:", 2, settingY, -1, true);
				RenderUtils.drawRect(sliderLeft, settingY, 90, 9, Color.black.getRGB());
                RenderUtils.drawRect(sliderLeft, settingY, 90F * (float) (greenBackground / 255.0D), 9, -1);
                boolean bgGreenDrag = MouseUtils.isInside(mouseX, mouseY, sliderLeft, settingY - 1, 90, 9) && Mouse.isButtonDown(0);
                if (bgGreenDrag) {
                    double diff = 255D - 0F;
                    double mouse = MathHelper.clamp_double((mouseX - sliderLeft) / 90D, 0, 1);
                    double newVal = 0 + mouse * diff;
                    this.greenBackground = (int) newVal;
                }
                font.drawString(new DecimalFormat("0.00").format(greenBackground), sliderLeft + 95, settingY, -1, true);
                settingY += 15;
                
                // Background Blue
				font.drawString("BG Blue:", 2, settingY, -1, true);
				RenderUtils.drawRect(sliderLeft, settingY, 90, 9, Color.black.getRGB());
                RenderUtils.drawRect(sliderLeft, settingY, 90F * (float) (blueBackground / 255.0D), 9, -1);
                boolean bgBlueDrag = MouseUtils.isInside(mouseX, mouseY, sliderLeft, settingY - 1, 90, 9) && Mouse.isButtonDown(0);
                if (bgBlueDrag) {
                    double diff = 255D - 0F;
                    double mouse = MathHelper.clamp_double((mouseX - sliderLeft) / 90D, 0, 1);
                    double newVal = 0 + mouse * diff;
                    this.blueBackground = (int) newVal;
                }
                font.drawString(new DecimalFormat("0.00").format(blueBackground), sliderLeft + 95, settingY, -1, true);
                settingY += 15;
                
                // Presets
                boolean twoColumn = false;
                StaticButton.render(2 + (twoColumn ? 63 : 0), settingY, 60, 12, "Store Preset");
                settingY += 15;
                
                // Loads
                StaticButton.render(2 + (twoColumn ? 63 : 0), settingY, 60, 12, "Load Cape");
                if(twoColumn) {
                	twoColumn = !twoColumn;
                	settingY += 15;
                } else {
                	twoColumn = !twoColumn;
                }
                StaticButton.render(2 + (twoColumn ? 63 : 0), settingY, 60, 12, "Load Wings");
                if(twoColumn) {
                	twoColumn = !twoColumn;
                	settingY += 15;
                } else {
                	twoColumn = !twoColumn;
                }
                StaticButton.render(2 + (twoColumn ? 63 : 0), settingY, 60, 12, "Load Bandana");
                if(twoColumn) {
                	twoColumn = !twoColumn;
                	settingY += 15;
                } else {
                	twoColumn = !twoColumn;
                }
                StaticButton.render(2 + (twoColumn ? 63 : 0), settingY, 60, 12, "Load Shield");
                if(twoColumn) {
                	twoColumn = !twoColumn;
                	settingY += 15;
                } else {
                	twoColumn = !twoColumn;
                }
                StaticButton.render(2 + (twoColumn ? 65 : 0), settingY, 60, 12, "Load Round Shield");
                
                if(twoColumn) {
                	twoColumn = !twoColumn;
                	settingY += 15;
                } else {
                	twoColumn = !twoColumn;
                }
                StaticButton.render(2 + (twoColumn ? 65 : 0), settingY, 60, 12, "Load Hexagon Shield");
                
                twoColumn = false;
                
                if(((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getWings() == null) {
                	settingY += 20;
                	font.drawString("Frames: " + this.frames, 2, settingY, -1, true);
                    settingY += 10;
                    font.drawString("Current Frame: " + (this.currentFrame + 1), 2, settingY, -1, true);
                    settingY += 15;
                    StaticButton.render(2 + (twoColumn ? 63 : 0), settingY, 60, 12, "Prev Frame");
                    if(twoColumn) {
                    	twoColumn = !twoColumn;
                    	settingY += 15;
                    } else {
                    	twoColumn = !twoColumn;
                    }
                    StaticButton.render(2 + (twoColumn ? 63 : 0), settingY, 60, 12, "Next Frame");
                    if(twoColumn) {
                    	twoColumn = !twoColumn;
                    	settingY += 15;
                    } else {
                    	twoColumn = !twoColumn;
                    }
                }
			}
			StaticButton.render(2, sr.getScaledHeight() - 14, 50, 12, "Open Textures Folder");
			StaticButton.render(sr.getScaledWidth() - 52, 2, 50, 12, "Hide UI");
		}
		GlUtils.startScale(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 + ((int) (50 * scale)), scale);
		CosmeticsGui.drawEntityOnScreen(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 + ((int) (50 * scale)), 45, 1, 1, mc.thePlayer, rotate);
		GlUtils.stopScale();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		ScaledResolution sr = new ScaledResolution(mc);
		if(!AdminRender.savingPreview) {
			if(StaticButton.isHovered(mouseX, mouseY, 2, 2, 50, 12)) {
				this.showSettings = !this.showSettings;
			}
			
			if(StaticButton.isHovered(mouseX, mouseY, sr.getScaledWidth() - 52, 2, 50, 12)) {
				AdminRender.savingPreview = true;
			}
			
			if(StaticButton.isHovered(mouseX, mouseY, 2, sr.getScaledHeight() - 14, 50, 12)) {
				File file1 = adminRenderPath;
                String s = file1.getAbsolutePath();

                if (Util.getOSType() == Util.EnumOS.OSX)
                {
                    try
                    {
                        Client.logger.info(s);
                        Runtime.getRuntime().exec(new String[] {"/usr/bin/open", s});
                        return;
                    }
                    catch (IOException ioexception1)
                    {
                    	Client.logger.error((String)"Couldn\'t open file", (Throwable)ioexception1);
                    }
                }
                else if (Util.getOSType() == Util.EnumOS.WINDOWS)
                {
                    String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] {s});

                    try
                    {
                        Runtime.getRuntime().exec(s1);
                        return;
                    }
                    catch (IOException ioexception)
                    {
                    	Client.logger.error((String)"Couldn\'t open file", (Throwable)ioexception);
                    }
                }

                boolean flag = false;

                try
                {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {file1.toURI()});
                }
                catch (Throwable throwable)
                {
                	Client.logger.error("Couldn\'t open link", throwable);
                    flag = true;
                }

                if (flag)
                {
                	Client.logger.info("Opening via system class!");
                    Sys.openURL("file://" + s);
                }
			}
			
			if(this.showSettings) {
				int settingY = 95;
				boolean twoColumn = false;
				
				if(StaticButton.isHovered(mouseX, mouseY, 2, settingY, 60, 12)) {
					this.scale = 3.20F;
					this.rotate = 4;
					this.redBackground = 19;
					this.blueBackground = 19;
					this.greenBackground = 19;
				}
				settingY += 15;
				if(StaticButton.isHovered(mouseX, mouseY, 2 + (twoColumn ? 63 : 0), settingY, 60, 12)) {
					loadTextureCosmetic("cape");
				}
				if(twoColumn) {
                	twoColumn = !twoColumn;
                	settingY += 15;
                } else {
                	twoColumn = !twoColumn;
                }
				if(StaticButton.isHovered(mouseX, mouseY, 2 + (twoColumn ? 63 : 0), settingY, 60, 12)) {
					loadTextureCosmetic("wings");
				}
				if(twoColumn) {
                	twoColumn = !twoColumn;
                	settingY += 15;
                } else {
                	twoColumn = !twoColumn;
                }
				if(StaticButton.isHovered(mouseX, mouseY, 2 + (twoColumn ? 63 : 0), settingY, 60, 12)) {
					loadTextureCosmetic("bandana");
				}
				if(twoColumn) {
                	twoColumn = !twoColumn;
                	settingY += 15;
                } else {
                	twoColumn = !twoColumn;
                }
				if(StaticButton.isHovered(mouseX, mouseY, 2 + (twoColumn ? 63 : 0), settingY, 60, 12)) {
					loadTextureCosmetic("shield", "shield");
				}
				if(twoColumn) {
                	twoColumn = !twoColumn;
                	settingY += 15;
                } else {
                	twoColumn = !twoColumn;
                }
				if(StaticButton.isHovered(mouseX, mouseY, 2 + (twoColumn ? 63 : 0), settingY, 60, 12)) {
					loadTextureCosmetic("shield", "roundshield");
				}
				if(twoColumn) {
                	twoColumn = !twoColumn;
                	settingY += 15;
                } else {
                	twoColumn = !twoColumn;
                }
				if(StaticButton.isHovered(mouseX, mouseY, 2 + (twoColumn ? 63 : 0), settingY, 60, 12)) {
					loadTextureCosmetic("shield", "hexagon_shield");
				}
				twoColumn = false;
				if(((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getWings() == null) {
					settingY += 45;
					if(StaticButton.isHovered(mouseX, mouseY, 2 + (twoColumn ? 63 : 0), settingY, 60, 12)) {
						this.currentFrame = (this.currentFrame - 1 >= 0 ? this.currentFrame - 1 : this.frames - 1);
					}
					if(twoColumn) {
	                	twoColumn = !twoColumn;
	                	settingY += 15;
	                } else {
	                	twoColumn = !twoColumn;
	                }
					if(StaticButton.isHovered(mouseX, mouseY, 2 + (twoColumn ? 63 : 0), settingY, 60, 12)) {
						this.currentFrame = (this.currentFrame + 1 <= (this.frames - 1) ? this.currentFrame + 1 : 0);
					}
				}
			}
		} else {
			AdminRender.savingPreview = false;
		}
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Players.reload();
	}
	
	private void loadTextureCosmetic(String type) {
		this.loadTextureCosmetic(type, "");
	}
	
	private void loadTextureCosmetic(String type, String model) {
		((AbstractClientPlayerExt) mc.thePlayer).silent$setCape(null);
		((AbstractClientPlayerExt) mc.thePlayer).silent$setCapeShoulders(null);
		((AbstractClientPlayerExt) mc.thePlayer).silent$setBandana(null);
		((AbstractClientPlayerExt) mc.thePlayer).silent$setHat(null);
		((AbstractClientPlayerExt) mc.thePlayer).silent$setShield(null);
		((AbstractClientPlayerExt) mc.thePlayer).silent$setWings(null);
		this.frames = 0;
		this.currentFrame = 0;

		File [] pngFiles = adminRenderPath.listFiles(file -> file.isFile() && file.getName().toLowerCase().endsWith(".png"));

		switch(type) {
		case "cape":
			((AbstractClientPlayerExt) mc.thePlayer).silent$setCape(new TestAnimatedResourceLocation(pngFiles.length - 1, 150));
			((AbstractClientPlayerExt) mc.thePlayer).silent$setCapeShoulders(new StaticResourceLocation(FileUtils.fileToResourceLocation(new File(adminRenderPath, "0.png")).getResourcePath()));
			this.frames = ((AbstractClientPlayerExt) mc.thePlayer).silent$getCape().getFrames();
			this.currentFrame = 0;
			break;
		case "wings":
			((AbstractClientPlayerExt) mc.thePlayer).silent$setWings(new TestAnimatedResourceLocation(pngFiles.length, 150));
			this.frames = 1;
			this.currentFrame = 0;
			break;
		case "bandana":
			((AbstractClientPlayerExt) mc.thePlayer).silent$setBandana(new TestAnimatedResourceLocation(pngFiles.length, 150));
			this.frames = ((AbstractClientPlayerExt) mc.thePlayer).silent$getBandana().getFrames();
			this.currentFrame = 0;
			break;
		case "shield":
			((AbstractClientPlayerExt) mc.thePlayer).silent$setShield(new ShieldData(new TestAnimatedResourceLocation(pngFiles.length, 150), model));
			this.frames = ((AbstractClientPlayerExt) mc.thePlayer).silent$getShield().getTexture().getFrames();
			this.currentFrame = 0;
			break;
		}
	}
	
	private class TestAnimatedResourceLocation extends AnimatedResourceLocation {

		public TestAnimatedResourceLocation(int frames, int fpt) {
			super("", frames, fpt, true);
			textures = new ResourceLocation[frames];

	        for(int i = 0; i < frames; i++) {
				Client.logger.info(new File(adminRenderPath, i + ".png").toString());
	            textures[i] = FileUtils.fileToResourceLocation(new File(adminRenderPath, i + ".png"));
	        }
		}
		
		@Override
		public void update(float deltaTick) {
			// Nothing =)
		}
		
	}
}
