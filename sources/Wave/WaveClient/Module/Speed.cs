using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x0200001C RID: 28
	public static class Speed
	{
		// Token: 0x06000068 RID: 104 RVA: 0x00003375 File Offset: 0x00001575
		public static void Tick100()
		{
			Memory0.mem.WriteMemory(Speed.SpeedPointer, 1f);
		}

		// Token: 0x04000042 RID: 66
		public static bool ToggleState;

		// Token: 0x04000043 RID: 67
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
