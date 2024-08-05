package studio.dreamys.entityculling.access;

public interface Cullable {
	boolean isForcedVisible();
	
	void setCulled(boolean value);

	boolean isCulled();
}
