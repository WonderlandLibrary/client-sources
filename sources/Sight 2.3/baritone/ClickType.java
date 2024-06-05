package baritone;

public enum ClickType {

	SWAP(1);
	
	private int type;
	
	ClickType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
}
