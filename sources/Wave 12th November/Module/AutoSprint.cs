using System;
using Wave.Cmr.MemoryManagement;
using WaveClient.ModuleManagment;

namespace WaveClient.Module
{
	// Token: 0x0200000E RID: 14
	public static class AutoSprint
	{
		// Token: 0x06000067 RID: 103 RVA: 0x0000371D File Offset: 0x0000191D
		public static void Tick10()
		{
			Memory0.mem.WriteMemory(AutoSprint.autosprintPointer, 0);
		}

		// Token: 0x06000068 RID: 104 RVA: 0x00003734 File Offset: 0x00001934
		public static void Disabled()
		{
		}

		// Token: 0x0400004B RID: 75
		public static bool ToggleState;

		// Token: 0x0400004C RID: 76
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
	}
}
