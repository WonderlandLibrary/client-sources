using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x02000014 RID: 20
	public static class AutoSprint
	{
		// Token: 0x06000057 RID: 87 RVA: 0x0000316B File Offset: 0x0000136B
		public static void Tick10()
		{
			Memory0.mem.WriteMemory(AutoSprint.autosprintPointer, 0);
		}

		// Token: 0x06000058 RID: 88 RVA: 0x00003182 File Offset: 0x00001382
		public static void Disabled()
		{
		}

		// Token: 0x04000036 RID: 54
		public static bool ToggleState;

		// Token: 0x04000037 RID: 55
		private static Pointer autosprintPointer = new Pointer("Minecraft.Windows.exe", 56973088, new int[]
		{
			168,
			16,
			1992,
			0,
			296,
			8,
			128,
			12
		});

		// Token: 0x04000038 RID: 56
		private static float sprintvalue = 1f;
	}
}
