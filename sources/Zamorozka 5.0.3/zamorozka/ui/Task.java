package zamorozka.ui;

import net.minecraft.client.Minecraft;
import zamorozka.module.Module;

public interface Task {

	Minecraft mc = Minecraft.getMinecraft();

    /**
     * Invoked when the task has been cancelled (or replaced by another task)
     * */
	void onTaskCancelled();

    /**
     * Invoked before updating the player positions/rotations.
     * */
	void onPreUpdate();

    /**
     * Invoked after updating the player positions/rotations.
     * */
	void onPostUpdate();

	boolean isRunning();
	
	void setRunning(boolean running);
	
	Module getMod();

}
