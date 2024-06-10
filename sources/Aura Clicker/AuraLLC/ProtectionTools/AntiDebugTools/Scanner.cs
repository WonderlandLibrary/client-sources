using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows.Forms;

namespace AuraLLC.ProtectionTools.AntiDebugTools
{
	// Token: 0x02000012 RID: 18
	internal class Scanner
	{
		// Token: 0x06000034 RID: 52 RVA: 0x000022E9 File Offset: 0x000004E9
		public static void ScanAndKill()
		{
			if (Scanner.Scan(true) != 0)
			{
				MessageBox.Show("Debuggers/Scripts found. Unload them from your memory and try again.");
			}
		}

		// Token: 0x06000035 RID: 53 RVA: 0x0000398C File Offset: 0x00001B8C
		private static int Scan(bool bool_0)
		{
			int result = 0;
			if (Scanner.BadProcessnameList.Count == 0 && Scanner.BadWindowTextList.Count == 0)
			{
				Scanner.Init();
			}
			foreach (Process process in Process.GetProcesses())
			{
				if (Scanner.BadProcessnameList.Contains(process.ProcessName) || Scanner.BadWindowTextList.Contains(process.MainWindowTitle))
				{
					MessageBox.Show("Bad process found: " + process.ProcessName);
					result = 1;
					if (bool_0)
					{
						try
						{
							process.Kill();
						}
						catch (Win32Exception ex)
						{
							MessageBox.Show("Win32Exception: " + ex.Message);
						}
						catch (NotSupportedException ex2)
						{
							MessageBox.Show("NotSupportedException: " + ex2.Message);
						}
						catch (InvalidOperationException ex3)
						{
							MessageBox.Show("InvalidOperationException: " + ex3.Message);
						}
					}
					return result;
				}
			}
			return result;
		}

		// Token: 0x06000036 RID: 54 RVA: 0x00003AAC File Offset: 0x00001CAC
		private static int Init()
		{
			int result;
			if (Scanner.BadProcessnameList.Count > 0 && Scanner.BadWindowTextList.Count > 0)
			{
				result = 1;
			}
			else
			{
				Scanner.BadProcessnameList.Add("ollydbg");
				Scanner.BadProcessnameList.Add("ida");
				Scanner.BadProcessnameList.Add("ida64");
				Scanner.BadProcessnameList.Add("idag");
				Scanner.BadProcessnameList.Add("idag64");
				Scanner.BadProcessnameList.Add("idaw");
				Scanner.BadProcessnameList.Add("idaw64");
				Scanner.BadProcessnameList.Add("idaq");
				Scanner.BadProcessnameList.Add("idaq64");
				Scanner.BadProcessnameList.Add("idau");
				Scanner.BadProcessnameList.Add("idau64");
				Scanner.BadProcessnameList.Add("scylla");
				Scanner.BadProcessnameList.Add("scylla_x64");
				Scanner.BadProcessnameList.Add("scylla_x86");
				Scanner.BadProcessnameList.Add("protection_id");
				Scanner.BadProcessnameList.Add("x64dbg");
				Scanner.BadProcessnameList.Add("x32dbg");
				Scanner.BadProcessnameList.Add("windbg");
				Scanner.BadProcessnameList.Add("reshacker");
				Scanner.BadProcessnameList.Add("ImportREC");
				Scanner.BadProcessnameList.Add("IMMUNITYDEBUGGER");
				Scanner.BadProcessnameList.Add("MegaDumper");
				Scanner.BadWindowTextList.Add("LordPE");
				Scanner.BadWindowTextList.Add("tcpview");
				Scanner.BadWindowTextList.Add("teamviewer");
				Scanner.BadWindowTextList.Add("joeboxcontrol");
				Scanner.BadWindowTextList.Add("dumpcap");
				Scanner.BadWindowTextList.Add("OLLYDBG");
				Scanner.BadWindowTextList.Add("regmon");
				Scanner.BadWindowTextList.Add("procmon");
				Scanner.BadWindowTextList.Add("ImportREC");
				Scanner.BadWindowTextList.Add("HookExplorer");
				Scanner.BadWindowTextList.Add("procexp");
				Scanner.BadWindowTextList.Add("ida");
				Scanner.BadWindowTextList.Add("disassembly");
				Scanner.BadWindowTextList.Add("scylla");
				Scanner.BadWindowTextList.Add("Debug");
				Scanner.BadWindowTextList.Add("[CPU");
				Scanner.BadWindowTextList.Add("Immunity");
				Scanner.BadWindowTextList.Add("WinDbg");
				Scanner.BadWindowTextList.Add("x32dbg");
				Scanner.BadWindowTextList.Add("x64dbg");
				Scanner.BadWindowTextList.Add("Import reconstructor");
				Scanner.BadWindowTextList.Add("MegaDumper");
				Scanner.BadWindowTextList.Add("MegaDumper 1.0 by CodeCracker / SnD");
				result = 0;
			}
			return result;
		}

		// Token: 0x04000267 RID: 615
		private static HashSet<string> BadProcessnameList = new HashSet<string>();

		// Token: 0x04000268 RID: 616
		private static HashSet<string> BadWindowTextList = new HashSet<string>();
	}
}
