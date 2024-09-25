package none.utils.render;

public class Anima {
	private float anima, min, max;
	boolean extended = true;
	public boolean starting = false;
	private float lenth;
	public type mode = type.Non_Stop;
	
	public void setType(type type) {
		this.mode = type;
	}
	
	public void Start() {
		starting = true;
	}
	
	public void Stop() {
		starting = false;
		anima = min;
	}
	
	public float getMin() {
		return min;
	}
	
	public float getMax() {
		return max;
	}
	
	public float getAnima() {
		return anima;
	}
	
	public void setAnima(float anima) {
		this.anima = anima;
	}
	
	public void setMin(float min) {
		this.min = min;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public void setLenth(float lenth) {
		this.lenth = lenth;
	}

	public Anima() {
		this.anima = 0;
	}
	
	public void doAnima() {
		if (starting && mode == type.Non_Stop) {
			if (anima <= max && extended) {
	        	setAnima(getAnima() + lenth);
	        }
	        
	        if (anima >= min && !extended) {
	        	setAnima(getAnima() - lenth);
	        }
	        
	        if (anima < min && !extended) {
	        	setAnima(min);
	        	extended = !extended;
	        }
	        
	        if (anima > max && extended) {
	        	setAnima(max);
	        	extended = !extended;
	        }
		}else if (starting && mode == type.Stop) {
			if (anima <= max) {
	        	setAnima(getAnima() + lenth);
	        }
	        
	        if (anima > max) {
	        	setAnima(max);
	        }
		}
	}
	
	public enum type {
		Non_Stop,
		Stop
	}
}
