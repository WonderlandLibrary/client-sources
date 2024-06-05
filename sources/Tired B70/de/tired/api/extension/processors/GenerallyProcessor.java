package de.tired.api.extension.processors;

import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GenerallyProcessor {

	public final RenderProcessor renderProcessor;

	public GenerallyProcessor INSTANCE;

	public GenerallyProcessor() {
		INSTANCE = this;
		renderProcessor = new RenderProcessor();

	}

}
