using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000017 RID: 23
	public static class NoFall
	{
		// Token: 0x0600007B RID: 123 RVA: 0x00003979 File Offset: 0x00001B79
		public static void Tick10()
		{
			Memory0.mem.WriteMemory(NoFall.nofallptr, 0);
		}

		// Token: 0x0400005B RID: 91
		public static bool ToggleState;

		// Token: 0x0400005C RID: 92
		private static Pointer nofallptr = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			8,
			24,
			128,
			1456,
			176,
			248,
			412
		});
	}
}
