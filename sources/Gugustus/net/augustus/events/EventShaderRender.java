package net.augustus.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// @AllArgsConstructor
@Getter
@Setter
public class EventShaderRender extends Event {
    private boolean bloom;

	public EventShaderRender(boolean bloom) {
		super();
		this.bloom = bloom;
	}

	public boolean isBloom() {
		return bloom;
	}

	public void setBloom(boolean bloom) {
		this.bloom = bloom;
	}
    
}
