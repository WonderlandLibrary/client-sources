package lunadevs.luna.Connector.ParticleSync.Particles;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.Connector.ParticleSync.Utils.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class Particle {

	public int x;
	public int y;
	public int k;
	public ParticleGenerator pg;
	public boolean reset;
	public float size;
	private Random random = new Random();
	
	public Particle(int x, int y) {
		this.x = x;
		this.y = y;
		this.size = genRandom(0.7F, 0.8F);
	}

	public void draw() {
		//Reset
		if (x == -1){
			x = pg.breite;
			reset = true;
		}
		
		if (y == -1){
			y = pg.höhe;
			reset = true;
		}
		
		this.x -= random.nextInt(2);
		this.y -= random.nextInt(2);
		
		int xx = (int) (MathHelper.cos(0.1F * (this.x + this.k)) * 10.0F);
		zCore.drawBorderedCircle(this.x + xx, this.y, this.size, 0, 0xffFFFFFF);
	}


	public float genRandom(float min, float max) {
		return (float) (min + Math.random() * (max - min + 1.0F));
	}
}