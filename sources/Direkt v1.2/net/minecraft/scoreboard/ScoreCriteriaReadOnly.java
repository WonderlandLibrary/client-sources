package net.minecraft.scoreboard;

public class ScoreCriteriaReadOnly extends ScoreCriteria {
	public ScoreCriteriaReadOnly(String name) {
		super(name);
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}
}
