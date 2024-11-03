package net.silentclient.client.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.mixin.ducks.TextureManagerExt;
import net.silentclient.client.utils.TimerUtils;

import java.util.ArrayList;

public class AnimatedResourceLocation {
		protected final String folder;
	 	protected final int frames;
	    protected final int fpt;

	    private int currentFrame = 0;
	    
	    private TimerUtils timer = new TimerUtils();

	    protected ResourceLocation[] textures;
	    public ArrayList<ResourceLocation> bindedFrames = new ArrayList<ResourceLocation>();
	    private boolean binding;
	    
	    public AnimatedResourceLocation(String folder, int frames, int fpt) {
	        this(folder, frames, fpt, false, false);
	    }
	    
	    public AnimatedResourceLocation(String folder, int frames, int fpt, boolean clear) {
	        this(folder, frames, fpt, clear, false);
	    }
	    
	    public AnimatedResourceLocation(String folder, int frames, int fpt, boolean clear, boolean bind) {
	        this.folder = folder;
	        this.frames = frames;
	        this.fpt = fpt;
	        
	        if(!clear) {
	        	textures = new ResourceLocation[frames];

		        for(int i = 0; i < frames; i++) {
		        	if(bind) {
		        		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(folder + "/" + i + ".png"));
		        	}
		            textures[i] = new ResourceLocation(folder + "/" + i + ".png");
		        }
	        }

	    }
	    
	    public int getCurrentFrame() {
			return currentFrame;
		}
	    
	    public ArrayList<ResourceLocation> getBindedFrames() {
			return bindedFrames;
		}

	    public ResourceLocation getTexture() {
	        return textures[currentFrame];
	    }
	    
	    public int getFrames() {
			return frames;
		}
	    
	    public ResourceLocation[] getTextures() {
			return textures;
		}
	    
	    public void bindTexture() {
			if(currentFrame == 0) {
				binding = false;
				Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTexture());
				return;
			}
	    	binding = ((TextureManagerExt) Minecraft.getMinecraft().getTextureManager()).waitBindTexture(new StaticResourceLocation(this.getTexture().getResourcePath()), new StaticResourceLocation(this.getTextures()[0].getResourcePath()), 1000);
	    }
	    
	    public void setCurrentFrame(int currentFrame) {
			this.currentFrame = currentFrame;
		}

	    public void update(float deltaTick) {
	    	if(textures.length == 1) {
	    		currentFrame = 0;
	    		return;
	    	}
	    	
	    	if(this.binding) {
	    		return;
	    	}
	    	
	        if(timer.delay(this.fpt)) {
	            currentFrame++;
	            if(currentFrame > textures.length - 1) {
	                currentFrame = 0;
	            }
	            timer.reset();
	        }
	    }
}
