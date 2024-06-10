using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000019 RID: 25
	public static class AirJump
	{
		// Token: 0x06000062 RID: 98 RVA: 0x00003293 File Offset: 0x00001493
		public static void Tick10()
		{
			Memory0.mem.WriteMemory(AirJump.OnGround, 16777473);
		}

		// Token: 0x0400003E RID: 62
		public static bool ToggleState;

		// Token: 0x0400003F RID: 63
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
