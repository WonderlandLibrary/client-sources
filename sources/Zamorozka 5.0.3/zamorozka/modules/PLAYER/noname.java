package zamorozka.modules.PLAYER;

import java.util.Iterator;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.Session;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;

public class noname extends Module
{
public noname()
{
  super("NameProtect", 0, Category.PLAYER);
}
}