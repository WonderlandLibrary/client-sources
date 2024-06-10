using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x0200001D RID: 29
	public static class Speed
	{
		// Token: 0x06000088 RID: 136 RVA: 0x00003C73 File Offset: 0x00001E73
		public static void Tick10()
		{
			Memory0.mem.WriteMemory(Speed.SpeedPointer, 1f);
		}

		// Token: 0x0400006D RID: 109
		public static bool ToggleState;

		// Token: 0x0400006E RID: 110
		private static Pointer SpeedPointer = new Pointer("Minecraft.Windows.exe", 56973088, new int[]
		{
			168,
			88,
			56,
			16,
			1080,
			24,
			496,
			156
		});
	}
}
