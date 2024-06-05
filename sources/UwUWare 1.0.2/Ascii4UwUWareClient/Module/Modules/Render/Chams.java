package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventPostRenderPlayer;
import Ascii4UwUWareClient.API.Events.Render.EventPreRenderPlayer;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.nio.FloatBuffer;

public class Chams extends Module {
	public Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[]) ChamsMode.values(), (Enum) ChamsMode.Normal);

	public Chams() {
		super("Chams", new String[] { "seethru", "cham" }, ModuleType.Render);
		this.addValues(this.mode);
		this.setColor(new Color(159, 190, 192).getRGB());
	}

	@EventHandler
	private void preRenderPlayer(EventPreRenderPlayer e) {
		setSuffix ( mode.getModeAsString () );
		switch (mode.getModeAsString ()) {
			case"Normal":
				GL11.glEnable((int) 32823);
				GL11.glPolygonOffset((float) 1.0f, (float) -1100000.0f);
				break;
			case"Colored":
			GL11.glPushMatrix ();
			GL11.glEnable ( GL11.GL_POLYGON_OFFSET_FILL );
			GL11.glPolygonOffset ( 1.0F, -2000000F );
			GL11.glDisable ( GL11.GL_TEXTURE_2D );
			Color color = Color.magenta;
			GlStateManager.enableLighting ();
			FloatBuffer floatBuffer = GLAllocation.createDirectFloatBuffer ( 16 );
			floatBuffer.put ( 1.0f ).put ( 1.0f ).put ( 1.0f ).put ( 1.0f );
			floatBuffer.flip ();
			GL11.glLight ( GL11.GL_LIGHT7, GL11.GL_SPECULAR, floatBuffer );
			floatBuffer.clear ();
			FloatBuffer pointer = GLAllocation.createDirectFloatBuffer ( 16 );
			pointer.put ( color.getRed () / 332.0f ).put ( color.getGreen () / 255.0f ).put ( color.getBlue () / 255.0f ).put ( 1.0F );
			pointer.flip ();
			GL11.glColorPointer ( 11, GL11.GL_COLOR_ARRAY_STRIDE, pointer );
			pointer.clear ();
			GlStateManager.disableLighting ();
				break;
		}
	}
	@EventHandler
	private void postRenderPlayer(EventPostRenderPlayer e) {
		setSuffix ( mode.getModeAsString () );
		switch (mode.getModeAsString ()) {
			case"Normal":
				GL11.glDisable((int) 32823);
				GL11.glPolygonOffset((float) 1.0f, (float) 1100000.0f);
				break;
			case "Colored":
				GL11.glEnable ( GL11.GL_TEXTURE_2D );
				GL11.glPolygonOffset ( 1.0F, 2000000F );
				GL11.glDisable ( GL11.GL_POLYGON_OFFSET_FILL );
				GL11.glPopMatrix ();
				break;
		}
	}

	public static enum ChamsMode {
		Normal, Colored
	}

		}

