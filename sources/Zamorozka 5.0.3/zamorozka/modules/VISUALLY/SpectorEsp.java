package zamorozka.modules.VISUALLY;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class SpectorEsp extends Module
{
	  public SpectorEsp()
	  {
	    super("SpectorEsp", 0, Category.VISUALLY);
	  }
	  public void onUpdate()
	  {
	    if (getState()) {

            mc.player.moveEntity(MoverType.SELF,  mc.player.motionX,  mc.player.motionY,  mc.player.motionZ);
            mc.player.motionX *= 0.5D;
            mc.player.motionY *= 0.5D;
            mc.player.motionZ *= 0.5D;
		 

	    }
	  }

	}
