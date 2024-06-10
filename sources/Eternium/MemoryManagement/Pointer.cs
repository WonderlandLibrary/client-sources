using System;

namespace Eternium_mcpe_client.MemoryManagement
{
	// Token: 0x0200000F RID: 15
	public class Pointer
	{
		// Token: 0x06000051 RID: 81 RVA: 0x00005BD4 File Offset: 0x00003DD4
		public Pointer(string ModuleBase, int PointerAddress, int[] Offsets)
		{
			this.ModuleBase = ModuleBase;
			this.PointerAddress = PointerAddress;
			this.Offsets = Offsets;
		}

		// Token: 0x04000040 RID: 64
		public string ModuleBase;

		// Token: 0x04000041 RID: 65
		public int PointerAddress;

		// Token: 0x04000042 RID: 66
		public int[] Offsets;
	}
}
