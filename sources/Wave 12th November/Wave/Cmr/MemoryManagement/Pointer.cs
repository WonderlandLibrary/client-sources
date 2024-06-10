using System;

namespace Wave.Cmr.MemoryManagement
{
	// Token: 0x02000024 RID: 36
	public class Pointer
	{
		// Token: 0x0600012E RID: 302 RVA: 0x00004810 File Offset: 0x00002A10
		public Pointer(string ModuleBase, int PointerAddress, int[] Offsets)
		{
			this.ModuleBase = ModuleBase;
			this.PointerAddress = PointerAddress;
			this.Offsets = Offsets;
		}

		// Token: 0x0400008A RID: 138
		public string ModuleBase;

		// Token: 0x0400008B RID: 139
		public int PointerAddress;

		// Token: 0x0400008C RID: 140
		public int[] Offsets;
	}
}
