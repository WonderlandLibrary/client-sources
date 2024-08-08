package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import me.xatzdevelopments.xatz.utils.Wrapper;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class SigmaDeleter extends Module {

	public SigmaDeleter() {
		super("SigmaDeleter", Keyboard.KEY_NONE, Category.MISC, "Delete Omikrons shitty pasted Monero miner client. (Deletes prelauncher too unlike cedos shitty one)");
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {

		super.onEnable();
	}

	@Override
	public void onUpdate() {
        File sigmaDataDir = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/sigma");
        File sigmaVerDir = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/Sigma");
        File sigmaNewVerDir = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/versions/Sigma5");
        File jelloprelauncherthingy = new File(FileUtils.getUserDirectoryPath() + "/AppData/Roaming/.minecraft/SigmaJelloPrelauncher.jar");
        try {
            Wrapper.tellPlayer("Sigma Deleter - 1/4");
            FileUtils.deleteDirectory(sigmaDataDir);
            Wrapper.tellPlayer("Sigma Deleter - 2/4");
            FileUtils.deleteDirectory(sigmaVerDir);
            Wrapper.tellPlayer("Sigma Deleter - 3/4");
            FileUtils.deleteDirectory(sigmaNewVerDir);
            Wrapper.tellPlayer("Sigma Deleter - 4/4");
            FileUtils.deleteQuietly(jelloprelauncherthingy);
            Wrapper.tellPlayer("Done! Sigma is gone.");
            Wrapper.tellPlayer("Sigma is gone!, your GPU and CPU will thank you.");
            Xatz.getModuleByName("SigmaDeleter").toggle();;
        } catch (Exception ex) {
            ex.printStackTrace();
            Wrapper.tellPlayer("Sigma Deleter: Failed!");
            Xatz.getModuleByName("SigmaDeleter").toggle();;
        }

		super.onUpdate();
	}

}
