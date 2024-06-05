package alphentus.utils;

import net.minecraft.util.ResourceLocation;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class AnimatedResourceLocation {

    public int currentTick, currentFrame;
    private final int ftp;

    private final ResourceLocation[] resourceLocations;

    /*
     * File Location of the Folder
     */

    /*
     * fileEnding = .png / .jpg
     */

    public AnimatedResourceLocation(String location, String fileEnding, int frames, int ftp) {
        this.ftp = ftp;
        this.resourceLocations = new ResourceLocation[frames];
        for (int i = 0; i < frames; i++) {
            this.resourceLocations[i] = new ResourceLocation(location + "/" + i + fileEnding);
        }
    }

    public void updateResourceLocation() {
        if (currentTick > ftp) {
            currentTick = 0;
            this.currentFrame++;
            if (this.currentFrame > resourceLocations.length - 1)
                currentFrame = 0;
        }
        currentTick++;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocations[currentFrame];
    }

}
