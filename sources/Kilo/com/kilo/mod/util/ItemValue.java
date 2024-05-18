package com.kilo.mod.util;

public class ItemValue {

	public static enum tool {
		WOOD(50),
		GOLD(50),
		STONE(100),
		IRON(200),
		EMERALD(300);
		
		int value;
		
		tool(int v) {
			this.value = v;
		}
		
		public int val() {
			return this.value;
		}
	}
	
	public static enum armor {
		LEATHER(50),
		GOLD(100),
		CHAIN(200),
		IRON(300),
		DIAMOND(400);
		
		public int value;
		
		armor(int v) {
			this.value = v;
		}
		
		public int val() {
			return this.value;
		}
	}
	
	public static enum enchant {
		UNBREAKING(34, 25),
		
		EFFICIENCY(32, 50),
		SILKTOUCH(33, 25),
		FORTUNE(35, 25),
		
		PROTECTION(0, 100),
		FIREPROTECTION(1, 50),
		BLASTPROTECTION(3, 30),
		PROJECTILEPROTECTION(4, 30),
		
		RESPIRATION(5, 20),
		AQUAAFFINITY(6, 30),

		FEATHERFALLING(2, 50),
		
		THORNS(7, 40),
		
		DEPTHSTRIDER(8, 30),
		
		SHARPNESS(16, 100),
		SMITE(17, 50),
		BANEOFARTHROPODS(18, 20),
		KNOCKBACK(19, 75),
		FIREASPECT(20, 80),
		LOOTING(21, 40);
		
		int id;
		int value;
		
		enchant(int i, int v) {
			this.id = i;
			this.value = v;
		}
		
		public static int val(int eid) {
			for(enchant e : enchant.values()) {
				if (e.id == eid) {
					return e.value;
				}
			}
			return 0;
		}
	}
}
