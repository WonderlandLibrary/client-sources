package me.rich.helpers.render;

import me.rich.helpers.world.TimerHelper;
import net.minecraft.util.math.Vec3d;

public class BreadcrumbHelper {
	Vec3d vector;
	TimeHelper timer;

	public BreadcrumbHelper(Vec3d vector) {
		timer = new TimeHelper();
		this.vector = vector;
	}

	public TimeHelper getTimer() {
		return timer;
	}

	public Vec3d getVector() {
		return vector;
	}
	
	public class TimeHelper {
	    long ms;

	    public TimeHelper() {
	        reset();
	    }

	    public boolean hasReached(long mils) {
	        return System.currentTimeMillis() - ms >= mils;
	    }

	    public long getMS() {
	        return ms;
	    }

	    public void reset() {
	        ms = System.currentTimeMillis();
	    }
	}

}