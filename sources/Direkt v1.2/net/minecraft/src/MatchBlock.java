package net.minecraft.src;

import net.minecraft.block.state.BlockStateBase;

public class MatchBlock {
	private int blockId = -1;
	private int[] metadatas = null;

	public MatchBlock(int p_i45_1_) {
		this.blockId = p_i45_1_;
	}

	public MatchBlock(int p_i46_1_, int p_i46_2_) {
		this.blockId = p_i46_1_;

		if ((p_i46_2_ >= 0) && (p_i46_2_ <= 15)) {
			this.metadatas = new int[] { p_i46_2_ };
		}
	}

	public MatchBlock(int p_i47_1_, int[] p_i47_2_) {
		this.blockId = p_i47_1_;
		this.metadatas = p_i47_2_;
	}

	public int getBlockId() {
		return this.blockId;
	}

	public int[] getMetadatas() {
		return this.metadatas;
	}

	public boolean matches(BlockStateBase p_matches_1_) {
		return p_matches_1_.getBlockId() != this.blockId ? false : Matches.metadata(p_matches_1_.getMetadata(), this.metadatas);
	}

	public boolean matches(int p_matches_1_, int p_matches_2_) {
		return p_matches_1_ != this.blockId ? false : Matches.metadata(p_matches_2_, this.metadatas);
	}

	public void addMetadata(int p_addMetadata_1_) {
		if (this.metadatas != null) {
			if ((p_addMetadata_1_ >= 0) && (p_addMetadata_1_ <= 15)) {
				for (int metadata : this.metadatas) {
					if (metadata == p_addMetadata_1_) { return; }
				}

				this.metadatas = Config.addIntToArray(this.metadatas, p_addMetadata_1_);
			}
		}
	}

	@Override
	public String toString() {
		return "" + this.blockId + ":" + Config.arrayToString(this.metadatas);
	}
}
