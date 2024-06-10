using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000018 RID: 24
	public static class AirJump
	{
		// Token: 0x0600007D RID: 125 RVA: 0x000039B7 File Offset: 0x00001BB7
		public static void Tick10()
		{
			Memory0.mem.WriteMemory(AirJump.OnGround, 16777473);
		}

		// Token: 0x0400005D RID: 93
		public static bool ToggleState;

		// Token: 0x0400005E RID: 94
		private static Pointer OnGround = new Pointer("Minecraft.Windows.exe", 57279096, new int[]
		{
			0,
			32,
			144,
			1456,
			216,
			24,
			416
		});
	}
}
