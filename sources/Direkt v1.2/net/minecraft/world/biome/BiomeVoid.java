package net.minecraft.world.biome;

public class BiomeVoid extends Biome {
	public BiomeVoid(Biome.BiomeProperties properties) {
		super(properties);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.theBiomeDecorator = new BiomeVoidDecorator();
	}

	@Override
	public boolean ignorePlayerSpawnSuitability() {
		return true;
	}
}
