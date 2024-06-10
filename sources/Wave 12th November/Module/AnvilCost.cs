using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x0200000D RID: 13
	public static class AnvilCost
	{
		// Token: 0x06000065 RID: 101 RVA: 0x000036DF File Offset: 0x000018DF
		public static void Tick100()
		{
			Memory0.mem.WriteMemory(AnvilCost.anvilcostptr, 0);
		}

		// Token: 0x04000049 RID: 73
		public static bool ToggleState;

		// Token: 0x0400004A RID: 74
		private static Pointer anvilcostptr = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			0,
			24,
			128,
			1448,
			80,
			32,
			168
		});
	}
}
