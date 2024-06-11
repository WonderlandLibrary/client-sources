package Hydro.command;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public interface CommandExecutor {
	
	public Minecraft mc = Minecraft.getMinecraft();

    void execute(EntityPlayerSP sender, List<String> args);
}
