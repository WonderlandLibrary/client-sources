using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000018 RID: 24
	public static class NoFall
	{
		// Token: 0x06000060 RID: 96 RVA: 0x00003255 File Offset: 0x00001455
		public static void Tick10()
		{
			Memory0.mem.WriteMemory(NoFall.FallingCounter, 0);
		}

		// Token: 0x0400003C RID: 60
		public static bool ToggleState;

		// Token: 0x0400003D RID: 61
		private static Pointer FallingCounter = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
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
