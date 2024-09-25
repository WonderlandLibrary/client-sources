package none.module.modules.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.event.events.Event3D;
import none.friend.FriendManager;
import none.module.Category;
import none.module.Module;
import none.module.modules.combat.Antibot;
import none.module.modules.world.Murder;
import none.utils.RenderingUtil;
import none.utils.Targeter;
import none.utils.render.Colors;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class Esp extends Module {
	public static float h = 0;
	
	private static String[] modes = {"WireFrame", "Box", "Fill", "CSGO", "2D"};
	public static ModeValue espmode = new ModeValue("Esp-Mode", "Box", modes);
	
	private static String[] colors = {"Rainbow", "Team", "Health", "Custom"};
	public static ModeValue colormode = new ModeValue("Color", "Rainbow", colors);
	
	public static NumberValue<Float> wireFrameWidth = new NumberValue<>("WireFrame-Width", 2F, 0.5F, 5F);
	
	public Esp() {
		super("ESP", "ESP", Category.RENDER, Keyboard.KEY_Y);
	}
	
	private double gradualFOVModifier;
	public static Map<EntityLivingBase, double[]> entityPositionstop = new HashMap();
    public static Map<EntityLivingBase, double[]> entityPositionsbottom = new HashMap();
	
	private void passive(Entity entity, int color) {
		double x = entity.lastTickPosX
				+ (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;

		double y = entity.lastTickPosY
				+ (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks;

		double z = entity.lastTickPosZ
				+ (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;
		double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX);
		double height = 1;
		Vec3 vec = new Vec3(x - width/2, y + entity.getEyeHeight(), z - width/2);
        Vec3 vec2 = new Vec3(x + width/2, y, z + width/2);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        RenderingUtil.enableGL2D();
        RenderingUtil.pre3D();
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 2);
        RenderingUtil.glColor(color);
        RenderingUtil.drawBoundingBox(new AxisAlignedBB(
        		vec.xCoord - renderManager.renderPosX, vec.yCoord - renderManager.renderPosY, vec.zCoord - renderManager.renderPosZ,
        		vec2.xCoord - renderManager.renderPosX, vec2.yCoord - renderManager.renderPosY, vec2.zCoord - renderManager.renderPosZ));
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        RenderingUtil.post3D();
        RenderingUtil.disableGL2D();
	}

	@Override
	@RegisterEvent(events = {Event3D.class, Event2D.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + espmode.getSelected());
		
		String mode = espmode.getSelected();
		String color = colormode.getSelected();
		
		int renderColor = 0;
		
		if (event instanceof Event3D) {
			if (h > 255) {
				h = 0;
			}
    
			h+= 0.1;
			
			for (Entity e : mc.theWorld.loadedEntityList) {

				double posX = e.lastTickPosX + (e.posX - e.lastTickPosX) * mc.timer.renderPartialTicks;
                double posY = e.lastTickPosY + (e.posY - e.lastTickPosY) * mc.timer.renderPartialTicks;
                double posZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * mc.timer.renderPartialTicks;
				Render entityRender = mc.getRenderManager().getEntityRenderObject(e);
				if (isValid(e)) {
					mc.entityRenderer.disableLightmap();
					switch (color) {
					case "Rainbow":{
						renderColor = ClientColor.rainbow(1000 * 1000);
						}
					break;
						
					case "Team":{
						String text = e.getDisplayName().getFormattedText();
	                	if(Character.toLowerCase(text.charAt(0)) == '§'){
	                		
	                    	char oneMore = Character.toLowerCase(text.charAt(1));
	                    	int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
	                    	
	                    	if (colorCode < 16) {
	                            try {
	                                int newColor = mc.fontRendererObj.colorCode[colorCode];   
	                                 renderColor = Colors.getColor((newColor >> 16), (newColor >> 8 & 0xFF), (newColor & 0xFF), 255);
	                            } catch (ArrayIndexOutOfBoundsException ignored) {
	                            }
	                        }
	                	}else{
	                		renderColor = Colors.getColor(255, 255, 255, 255);
	                	}
					}
					break;
						
					case"Health":{
	                	float health = ((EntityLivingBase)e).getHealth();
	                

	                    if (health > 20) {
	                        health = 20;
	                    }
	                    float[] fractions = new float[]{0f, 0.5f, 1f};
	                    Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
	                    float progress = (health * 5) * 0.01f;
	                    Color customColor = blendColors(fractions, colors, progress).brighter();
	                    renderColor = customColor.getRGB();
	                }
	                break;
	                
					case "Custom" : {
						renderColor = ClientColor.getColor();
					}
					break;
	                
	                
					default:
						break;
					}
					
					if (e instanceof EntityPlayer) {
                    	EntityPlayer entityplayer = (EntityPlayer) e;
                    	if (Client.nameList.contains(entityplayer.getName()) || FriendManager.isFriend(e.getName())) {
                    		renderColor = Color.BLUE.getRGB();
                    	}else if (Client.instance.moduleManager.murder.isEnabled() && entityplayer.isMurderer) {
                    		renderColor = Color.RED.getRGB();
                    	}
                    }
					
					switch (mode) {
					case "Box":{
						passive(e, renderColor);
					}
					break;
					
					case "2D" :
					break;
					
					default:
						break;
					}
				}
			}

			mc.entityRenderer.disableLightmap();
			RenderHelper.disableStandardItemLighting();
			if (mode.equalsIgnoreCase("2D")) {
				try {
        			updatePositions();
        		} catch (Exception ex) {
        			
        		}
			}
		}
		
		if (event instanceof Event2D) {
			Event2D er = (Event2D) event;
			for (Entity e : mc.theWorld.loadedEntityList) {
                if (espmode.getSelected().equalsIgnoreCase("2D")) {
    				GlStateManager.pushMatrix();
                    ScaledResolution scaledRes = new ScaledResolution(mc);
                    double twoDscale = scaledRes.getScaleFactor() / Math.pow(scaledRes.getScaleFactor(), 2.0D);
                    GlStateManager.scale(twoDscale, twoDscale, twoDscale);
                    for (Entity ent : entityPositionstop.keySet()) {
                        double[] renderPositions = entityPositionstop.get(ent);
                        double[] renderPositionsBottom = entityPositionsbottom.get(ent);
                        if ((renderPositions[3] > 0.0D) || (renderPositions[3] <= 1.0D)) {
                            GlStateManager.pushMatrix();
                            if (isValid(e)) {
                            	switch (color) {
                				case "Rainbow":{
                					renderColor = ClientColor.rainbow(1000 * 1000);
                					}
                				break;
                					
                				case "Team":{
                					String text = e.getDisplayName().getFormattedText();
                                	if(Character.toLowerCase(text.charAt(0)) == '§'){
                                		
                                    	char oneMore = Character.toLowerCase(text.charAt(1));
                                    	int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
                                    	
                                    	if (colorCode < 16) {
                                            try {
                                                int newColor = mc.fontRendererObj.colorCode[colorCode];   
                                                 renderColor = Colors.getColor((newColor >> 16), (newColor >> 8 & 0xFF), (newColor & 0xFF), 255);
                                            } catch (ArrayIndexOutOfBoundsException ignored) {
                                            }
                                        }
                                	}else{
                                		renderColor = Colors.getColor(255, 255, 255, 255);
                                	}
                				}
                				break;
                					
                				case"Health":{
                                	float health = ((EntityLivingBase)e).getHealth();
                                

                                    if (health > 20) {
                                        health = 20;
                                    }
                                    float[] fractions = new float[]{0f, 0.5f, 1f};
                                    Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
                                    float progress = (health * 5) * 0.01f;
                                    Color customColor = blendColors(fractions, colors, progress).brighter();
                                    renderColor = customColor.getRGB();
                                }
                                break;
                                
                				case "Custom" : {
                					renderColor = ClientColor.getColor();
                				}
                				break;
                                
                                
                				default:
                					break;
                				}
            					if (e instanceof EntityPlayer) {
                                	EntityPlayer entityplayer = (EntityPlayer) e;
                                	if (entityplayer.getGameProfile().getName().equalsIgnoreCase("MossTK") || FriendManager.isFriend(e.getName())) {
                                		renderColor = Color.BLUE.getRGB();
                                	}
                                }
            					
                                scale(ent);
                                try {
                                    float y = (float) renderPositions[1];
                                    float endy = (float) renderPositionsBottom[1];
                                    float meme = endy - y;
                                    float x = (float) renderPositions[0] - (meme / 4f);
                                    float endx = (float) renderPositionsBottom[0] + (meme / 4f);
                                    if (x > endx) {
                                        endx = x;
                                        x = (float) renderPositionsBottom[0] + (meme / 4f);
                                    }
                                    GlStateManager.pushMatrix();
                                    GlStateManager.scale(2, 2, 2);
                                    GlStateManager.popMatrix();
                                    GL11.glEnable(GL11.GL_BLEND);
                                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                                    RenderingUtil.rectangleBordered(x, y, endx, endy, 2.25, Colors.getColor(0, 0, 0, 0), renderColor);
                                    RenderingUtil.rectangleBordered(x - 0.5, y - 0.5, endx + 0.5, endy + 0.5, 0.9, Colors.getColor(0, 0), Colors.getColor(0));
                                    RenderingUtil.rectangleBordered(x + 2.5, y + 2.5, endx - 2.5, endy - 2.5, 0.9, Colors.getColor(0, 0), Colors.getColor(0));
                                    RenderingUtil.rectangleBordered(x - 5, y - 1, x - 1, endy, 1, Colors.getColor(0, 100), Colors.getColor(0, 255));
                                    GL11.glDisable(GL11.GL_BLEND);
                                    GL11.glEnable(GL11.GL_TEXTURE_2D);
//                                    if(ent instanceof EntityPlayer){
//                                    	GlStateManager.pushMatrix();
//                                        TTFFontRenderer font = Client.fm.getFont("Verdana Bold 16");
//                                        float meme2 = ((endx - x) / 2 - (font.getStringWidth(ent.getName()) / 2f));
//                                        font.drawStringWithShadow(ent.getName() + " " + (int) mc.thePlayer.getDistanceToEntity(ent) + "m", (x + meme2), (y - font.getHeight(ent.getName()) - 5), renderColor);
//                                        GlStateManager.popMatrix();
//                                    }
                                    
                                    float health = ((EntityLivingBase) ent).getHealth();
                                    if(health > 20)
                                    	health = 20;
                                    float[] fractions = new float[]{0f, 0.5f, 1f};
                                    Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
                                    float progress = (health * 5) * 0.01f;
                                    Color customColor = blendColors(fractions, colors, progress).brighter();
                                    double healthLocation = endy + (y - endy) * ((health * 5) * 0.01f);
                                    RenderingUtil.rectangle(x - 4, endy - 1, x - 2, healthLocation, customColor.getRGB());
                                } catch (Exception ez) {
                                }
                            }
                            GlStateManager.popMatrix();
                            GL11.glColor4f(1, 1, 1, 1);
                        }
                    }
                    GL11.glScalef(1, 1, 1);
                    GL11.glColor4f(1, 1, 1, 1);
                    GlStateManager.popMatrix();
    			}
			}
		}
	}
	
	public static int getColor(Entity entity) {
		Minecraft mc = Minecraft.getMinecraft();
		String color = Esp.colormode.getSelected();
		int renderColor = ClientColor.getColor();
		switch (color) {
		case "Rainbow":{
			renderColor = ClientColor.rainbow(1000 * 1000);
			}
		break;
			
		case "Team":{
			String text = entity.getDisplayName().getFormattedText();
        	if(Character.toLowerCase(text.charAt(0)) == '§'){
        		
            	char oneMore = Character.toLowerCase(text.charAt(1));
            	int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
            	
            	if (colorCode < 16) {
                    try {
                        int newColor = mc.fontRendererObj.colorCode[colorCode];   
                         renderColor = Colors.getColor((newColor >> 16), (newColor >> 8 & 0xFF), (newColor & 0xFF), 255);
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
        	}else{
        		renderColor = Colors.getColor(255, 255, 255, 255);
        	}
		}
		break;
			
		case"Health":{
        	float health = ((EntityLivingBase)entity).getHealth();
        	if (health > 20) {
                health = 20;
            }
            float[] fractions = new float[]{0f, 0.5f, 1f};
            Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
            float progress = (health * 5) * 0.01f;
            Color customColor = Esp.blendColors(fractions, colors, progress).brighter();
            renderColor = customColor.getRGB();
        }
        break;
        
		case "Custom" : {
			renderColor = ClientColor.getColor();
		}
		break;
        
        
		default:
			break;
		}
		return renderColor;
	}
	
	private void scale(Entity ent) {
	    float scale = (float) 1;
	    float target = scale * (mc.gameSettings.fovSetting
	            / (mc.gameSettings.fovSetting/*
	     * *
	     * mc.thePlayer.getFovModifier()
	     *//* .func_175156_o() */));
	    if ((this.gradualFOVModifier == 0.0D) || (Double.isNaN(this.gradualFOVModifier))) {
	        this.gradualFOVModifier = target;
	    }
	    this.gradualFOVModifier += (target - this.gradualFOVModifier) / (Minecraft.debugFPS * 0.7D);

	    scale = (float) (scale * this.gradualFOVModifier);

	    GlStateManager.scale(scale, scale, scale);
	}
	
	public static int[] getFractionIndicies(float[] fractions, float progress) {
	    int[] range = new int[2];

	    int startPoint = 0;
	    while (startPoint < fractions.length && fractions[startPoint] <= progress) {
	        startPoint++;
	    }

	    if (startPoint >= fractions.length) {
	        startPoint = fractions.length - 1;
	    }

	    range[0] = startPoint - 1;
	    range[1] = startPoint;

	    return range;
	}
	
	public static Color blendColors(float[] fractions, Color[] colors, float progress) {
	    Color color = null;
	    if (fractions != null) {
	        if (colors != null) {
	            if (fractions.length == colors.length) {
	                int[] indicies = getFractionIndicies(fractions, progress);

	                if (indicies[0] < 0 || indicies[0] >= fractions.length || indicies[1] < 0 || indicies[1] >= fractions.length) {
	                    return colors[0];
	                }
	                float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
	                Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};

	                float max = range[1] - range[0];
	                float value = progress - range[0];
	                float weight = value / max;

	                color = blend(colorRange[0], colorRange[1], 1f - weight);
	            } else {
	                throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
	            }
	        } else {
	            throw new IllegalArgumentException("Colours can't be null");
	        }
	    } else {
	        throw new IllegalArgumentException("Fractions can't be null");
	    }
	    return color;
	}
	
	public static Color blend(Color color1, Color color2, double ratio) {
	    float r = (float) ratio;
	    float ir = (float) 1.0 - r;

	    float rgb1[] = new float[3];
	    float rgb2[] = new float[3];

	    color1.getColorComponents(rgb1);
	    color2.getColorComponents(rgb2);

	    float red = rgb1[0] * r + rgb2[0] * ir;
	    float green = rgb1[1] * r + rgb2[1] * ir;
	    float blue = rgb1[2] * r + rgb2[2] * ir;

	    if (red < 0) {
	        red = 0;
	    } else if (red > 255) {
	        red = 255;
	    }
	    if (green < 0) {
	        green = 0;
	    } else if (green > 255) {
	        green = 255;
	    }
	    if (blue < 0) {
	        blue = 0;
	    } else if (blue > 255) {
	        blue = 255;
	    }

	    Color color = null;
	    try {
	        color = new Color(red, green, blue);
	    } catch (IllegalArgumentException exp) {
	        NumberFormat nf = NumberFormat.getNumberInstance();
	        System.out.println(nf.format(red) + "; " + nf.format(green) + "; " + nf.format(blue));
	        exp.printStackTrace();
	    }
	    return color;
	}
	
	public static boolean isValid(Entity entity){
		if (!(entity instanceof EntityLivingBase)) return false;
        boolean players = Targeter.player;
        boolean invis = Targeter.invisible;
    	boolean others = Targeter.other;
    	boolean valid = entity instanceof EntityMob || entity instanceof EntityIronGolem ||
				entity instanceof EntityAnimal || entity instanceof EntityVillager || entity instanceof EntityPlayer;
    	if(entity.isInvisible() && !invis){
    		return false;
    	}
    	
    	if (entity instanceof EntityPlayer) {
    		entity = (EntityPlayer) entity;
    		if (Antibot.getInvalid().contains(entity) || Client.nameList.contains(entity.getName())) {
    			return false;
    		}
    	}
    	
    	if((players && entity instanceof EntityPlayer) || (others && (entity instanceof EntityMob || entity instanceof EntityAnimal || entity instanceof EntityVillager || entity instanceof EntityIronGolem))){
    		if(entity instanceof EntityPlayerSP){
    			return  Minecraft.getMinecraft().gameSettings.thirdPersonView != 0 && !espmode.getSelected().equalsIgnoreCase("Box") && !espmode.getSelected().equalsIgnoreCase("2D");
    		}else{
    			return true;
    		}
    	}else{
    		return false;
    	}
    }
	
	private double[] convertTo2D(double x, double y, double z) {
	    FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
	    IntBuffer viewport = BufferUtils.createIntBuffer(16);
	    FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
	    FloatBuffer projection = BufferUtils.createFloatBuffer(16);
	    GL11.glGetFloat(2982, modelView);
	    GL11.glGetFloat(2983, projection);
	    GL11.glGetInteger(2978, viewport);
	    boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport, screenCoords);
	    if (result) {
	        return new double[]{screenCoords.get(0), Display.getHeight() - screenCoords.get(1), screenCoords.get(2)};
	    }
	    return null;
	}
	
	private double[] convertTo2D(double x, double y, double z, Entity ent) {
	    return convertTo2D(x, y, z);
	}
	
	private void updatePositions() {
	    entityPositionstop.clear();
	    entityPositionsbottom.clear();
	    float pTicks = mc.timer.renderPartialTicks;
	    for (Object o : mc.theWorld.getLoadedEntityList()) {
	  
	        if (o instanceof EntityPlayer && o != mc.thePlayer) {
	          	EntityLivingBase ent = (EntityLivingBase)o;
	            double x;
	            double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * pTicks - mc.getRenderManager().viewerPosY;
	            double z;
	            x = ent.lastTickPosX + ((ent.posX + 10) - (ent.lastTickPosX + 10)) * pTicks - mc.getRenderManager().viewerPosX;
	            z = ent.lastTickPosZ + ((ent.posZ + 10) - (ent.lastTickPosZ + 10)) * pTicks - mc.getRenderManager().viewerPosZ;
	            y += ent.height + 0.2D;
	            double[] convertedPoints = convertTo2D(x, y, z);
	            double xd = Math.abs(convertTo2D(x, y + 1.0D, z, ent)[1] - convertTo2D(x, y, z, ent)[1]);
	            assert convertedPoints != null;
	            if ((convertedPoints[2] >= 0.0D) && (convertedPoints[2] < 1.0D)) {
	                entityPositionstop.put(ent, new double[]{convertedPoints[0], convertedPoints[1], xd, convertedPoints[2]});
	                y = ent.lastTickPosY + ((ent.posY - 2.2) - (ent.lastTickPosY - 2.2)) * pTicks - mc.getRenderManager().viewerPosY;
	                entityPositionsbottom.put(ent, new double[]{convertTo2D(x, y, z)[0], convertTo2D(x, y, z)[1], xd, convertTo2D(x, y, z)[2]});
	            }
	        }
	    }
	}
}
