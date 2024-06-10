using System;
using System.Collections.Generic;
using System.Diagnostics;

namespace New_CandyClient.ProtectionTools
{
	// Token: 0x0200002E RID: 46
	internal static class DebuggerAcl
	{
		// Token: 0x0600016B RID: 363 RVA: 0x00012A50 File Offset: 0x00010E50
		internal static bool Run()
		{
			bool result = false;
			if (Debugger.IsAttached || Debugger.IsLogging())
			{
				result = true;
			}
			else
			{
				string[] array = new string[]
				{
					"codecracker",
					"x32dbg",
					"x64dbg",
					"ollydbg",
					"ida",
					"charles",
					"dnspy",
					"simpleassembly",
					"peek",
					"httpanalyzer",
					"httpdebug",
					"fiddler",
					"wireshark",
					"dbx",
					"mdbg",
					"gdb",
					"windbg",
					"dbgclr",
					"kdb",
					"kgdb",
					"mdb",
					"processhacker",
					"scylla_x86",
					"scylla_x64",
					"scylla",
					"idau64",
					"idau",
					"idaq",
					"idaq64",
					"idaw",
					"idaw64",
					"idag",
					"idag64",
					"ida64",
					"ida",
					"ImportREC",
					"IMMUNITYDEBUGGER",
					"MegaDumper",
					"CodeBrowser",
					"reshacker",
					"cheat engine"
				};
				foreach (Process process in Process.GetProcesses())
				{
					if (process != Process.GetCurrentProcess())
					{
						for (int j = 0; j < array.Length; j++)
						{
							if (process.ProcessName.ToLower().Contains(array[j]))
							{
								result = true;
							}
							if (process.MainWindowTitle.ToLower().Contains(array[j]))
							{
								result = true;
							}
						}
					}
				}
			}
			return result;
		}

		// Token: 0x0600016C RID: 364 RVA: 0x00012C4C File Offset: 0x0001104C
		private static string ReturnProcessLists()
		{
			Process[] processes = Process.GetProcesses();
			List<string> list = new List<string>();
			foreach (Process process in processes)
			{
				list.Add(process.ProcessName);
			}
			return string.Join("|", list.ToArray());
		}
	}
}
