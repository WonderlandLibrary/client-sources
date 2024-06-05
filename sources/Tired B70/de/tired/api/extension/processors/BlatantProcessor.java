package de.tired.api.extension.processors;

import de.tired.api.extension.processors.extensions.blatant.CPSDrop;
import de.tired.api.extension.processors.extensions.blatant.RotationProcessor;

public class BlatantProcessor {

	public BlatantProcessor INSTANCE;

	public RotationProcessor rotationProcessor;

	public CPSDrop cpsDrop;

	public BlatantProcessor() {
		INSTANCE = this;
		this.rotationProcessor = new RotationProcessor();
		this.cpsDrop = new CPSDrop();
	}

}
