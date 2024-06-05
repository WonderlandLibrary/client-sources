package de.tired.api.extension;

import de.tired.api.extension.processors.GenerallyProcessor;
import de.tired.api.extension.processors.BlatantProcessor;
import de.tired.api.extension.processors.LegitProcessor;
import lombok.Getter;

public enum Extension {

	EXTENSION;

	@Getter
	private GenerallyProcessor generallyProcessor;

	@Getter
	private LegitProcessor legitProcessor;

	@Getter
	private BlatantProcessor blatantProcessor;

	public void setupExtension() {
		this.blatantProcessor = new BlatantProcessor();
		this.generallyProcessor = new GenerallyProcessor();
		this.legitProcessor = new LegitProcessor();
	}

}
