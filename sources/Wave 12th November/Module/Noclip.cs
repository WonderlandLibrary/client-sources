using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000016 RID: 22
	public static class Noclip
	{
		// Token: 0x06000078 RID: 120 RVA: 0x00003924 File Offset: 0x00001B24
		public static void Tick10()
		{
			Memory0.mem.WriteMemory(Noclip.noclipptr, 1);
		}

		// Token: 0x06000079 RID: 121 RVA: 0x0000393B File Offset: 0x00001B3B
		public static void Disable()
		{
			Memory0.mem.WriteMemory(Noclip.noclipptr, 0);
		}

		// Token: 0x04000059 RID: 89
		public static bool ToggleState;

		// Token: 0x0400005A RID: 90
		private static Pointer noclipptr = new Pointer("Minecraft.Windows.exe", 57279112, new int[]
		{
			8,
			0,
			24,
			376,
			128,
			2552,
			24,
			2328
		});
	}
}
