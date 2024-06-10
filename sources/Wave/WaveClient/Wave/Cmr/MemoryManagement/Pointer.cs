using System;

namespace Wave.Cmr.MemoryManagement
{
	// Token: 0x02000022 RID: 34
	public class Pointer
	{
		// Token: 0x0600010D RID: 269 RVA: 0x00003F04 File Offset: 0x00002104
		public Pointer(string ModuleBase, int PointerAddress, int[] Offsets)
		{
			this.ModuleBase = ModuleBase;
			this.PointerAddress = PointerAddress;
			this.Offsets = Offsets;
		}

		// Token: 0x0400005E RID: 94
		public string ModuleBase;

		// Token: 0x0400005F RID: 95
		public int PointerAddress;

		// Token: 0x04000060 RID: 96
		public int[] Offsets;
	}
}
