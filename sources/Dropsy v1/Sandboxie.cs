using System;
using System.Diagnostics;

// Token: 0x02000003 RID: 3
internal static class Sandboxie
{
	// Token: 0x0600000A RID: 10 RVA: 0x00002470 File Offset: 0x00000870
	private static IntPtr GetModuleHandle(string libName)
	{
		foreach (object obj in Process.GetCurrentProcess().Modules)
		{
			ProcessModule processModule = (ProcessModule)obj;
			if (processModule.ModuleName.ToLower().Contains(libName.ToLower()))
			{
				return processModule.BaseAddress;
			}
		}
		return IntPtr.Zero;
	}

	// Token: 0x0600000B RID: 11 RVA: 0x000024F0 File Offset: 0x000008F0
	internal static bool IsSandboxie()
	{
		return Sandboxie.GetModuleHandle("SbieDll.dll") != IntPtr.Zero;
	}
}
