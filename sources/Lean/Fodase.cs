using System;
using System.Diagnostics;

// Token: 0x02000002 RID: 2
internal class Fodase
{
	// Token: 0x06000002 RID: 2 RVA: 0x00002058 File Offset: 0x00000258
	public static void RestartExplorer()
	{
		string strB = string.Format("{0}\\{1}", Environment.GetEnvironmentVariable("WINDIR"), "explorer.exe");
		foreach (Process process in Process.GetProcesses())
		{
			try
			{
				bool flag = string.Compare(process.MainModule.FileName, strB, StringComparison.OrdinalIgnoreCase) == 0;
				if (flag)
				{
					process.Kill();
				}
			}
			catch
			{
			}
		}
		Process.Start("explorer.exe");
	}
}
