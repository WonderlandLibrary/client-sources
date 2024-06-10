using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000012 RID: 18
	public static class Fly
	{
		// Token: 0x06000070 RID: 112 RVA: 0x0000384B File Offset: 0x00001A4B
		public static void Tick10()
		{
			Memory0.mem.WriteMemory(Fly.FlyToggle, 1);
		}

		// Token: 0x06000071 RID: 113 RVA: 0x00003862 File Offset: 0x00001A62
		public static void Disable()
		{
			Memory0.mem.WriteMemory(Fly.FlyToggle, 0);
		}

		// Token: 0x04000053 RID: 83
		public static bool ToggleState;

		// Token: 0x04000054 RID: 84
		private static Pointer FlyToggle = new Pointer("Minecraft.Windows", 57279112, new int[]
		{
			0,
			8,
			32,
			184,
			2232
		});
	}
}
