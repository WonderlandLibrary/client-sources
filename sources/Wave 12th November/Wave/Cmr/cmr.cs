using System;
using Wave.Cmr.Win32API;

namespace Wave.Cmr
{
	// Token: 0x02000020 RID: 32
	public class cmr
	{
		// Token: 0x0600008B RID: 139 RVA: 0x00003CC4 File Offset: 0x00001EC4
		public static void EnableVirtualTerminalProcessing()
		{
			IntPtr stdHandle = Win32.GetStdHandle(-11);
			uint num;
			Win32.GetConsoleMode(stdHandle, out num);
			Win32.SetConsoleMode(stdHandle, num | 4U);
		}

		// Token: 0x0600008C RID: 140 RVA: 0x00003CEA File Offset: 0x00001EEA
		public static void cout()
		{
		}

		// Token: 0x0600008D RID: 141 RVA: 0x00003CEC File Offset: 0x00001EEC
		public static string cin(bool ClearImput = true)
		{
			return "";
		}

		// Token: 0x0600008E RID: 142 RVA: 0x00003CF3 File Offset: 0x00001EF3
		public static void cli()
		{
			while (Console.KeyAvailable)
			{
				Console.ReadKey(true);
			}
		}

		// Token: 0x0600008F RID: 143 RVA: 0x00003D08 File Offset: 0x00001F08
		public static void clog(string name, string print)
		{
			Console.Write(string.Concat(new string[]
			{
				cmr.cf(120, 120, 120),
				"[",
				cmr.cf(200, 200, 200),
				name,
				cmr.cf(120, 120, 120),
				"] ",
				print
			}));
		}

		// Token: 0x06000090 RID: 144 RVA: 0x00003D70 File Offset: 0x00001F70
		public static void clogl(string name, string print)
		{
			Console.WriteLine(string.Concat(new string[]
			{
				cmr.cf(120, 120, 120),
				"[",
				cmr.cf(200, 200, 200),
				name,
				cmr.cf(120, 120, 120),
				"] ",
				print
			}));
		}

		// Token: 0x06000091 RID: 145 RVA: 0x00003DD8 File Offset: 0x00001FD8
		public static string cf(uint r, uint g, uint b)
		{
			return string.Concat(new string[]
			{
				"\u001b[38;2;",
				r.ToString(),
				";",
				g.ToString(),
				";",
				b.ToString(),
				"m"
			});
		}

		// Token: 0x06000092 RID: 146 RVA: 0x00003E30 File Offset: 0x00002030
		public static string cf(int r, int g, int b)
		{
			return string.Concat(new string[]
			{
				"\u001b[38;2;",
				r.ToString(),
				";",
				g.ToString(),
				";",
				b.ToString(),
				"m"
			});
		}

		// Token: 0x06000093 RID: 147 RVA: 0x00003E88 File Offset: 0x00002088
		public static string cb(uint r, uint g, uint b)
		{
			return string.Concat(new string[]
			{
				"\u001b[48;2;",
				r.ToString(),
				";",
				g.ToString(),
				";",
				b.ToString(),
				"m"
			});
		}

		// Token: 0x06000094 RID: 148 RVA: 0x00003EE0 File Offset: 0x000020E0
		public static string cb(int r, int g, int b)
		{
			return string.Concat(new string[]
			{
				"\u001b[48;2;",
				r.ToString(),
				";",
				g.ToString(),
				";",
				b.ToString(),
				"m"
			});
		}

		// Token: 0x17000008 RID: 8
		// (get) Token: 0x06000095 RID: 149 RVA: 0x00003F36 File Offset: 0x00002136
		// (set) Token: 0x06000096 RID: 150 RVA: 0x00003F47 File Offset: 0x00002147
		public static string cr
		{
			get
			{
				return cmr.cfr + cmr.cbr;
			}
			set
			{
			}
		}

		// Token: 0x17000009 RID: 9
		// (get) Token: 0x06000097 RID: 151 RVA: 0x00003F49 File Offset: 0x00002149
		// (set) Token: 0x06000098 RID: 152 RVA: 0x00003F50 File Offset: 0x00002150
		public static string cfr
		{
			get
			{
				return "\u001b[39m";
			}
			set
			{
			}
		}

		// Token: 0x1700000A RID: 10
		// (get) Token: 0x06000099 RID: 153 RVA: 0x00003F52 File Offset: 0x00002152
		// (set) Token: 0x0600009A RID: 154 RVA: 0x00003F59 File Offset: 0x00002159
		public static string cbr
		{
			get
			{
				return "\u001b[49m";
			}
			set
			{
			}
		}

		// Token: 0x1700000B RID: 11
		// (get) Token: 0x0600009B RID: 155 RVA: 0x00003F5B File Offset: 0x0000215B
		// (set) Token: 0x0600009C RID: 156 RVA: 0x00003F62 File Offset: 0x00002162
		public static string tb
		{
			get
			{
				return "\u001b[1m";
			}
			set
			{
			}
		}

		// Token: 0x1700000C RID: 12
		// (get) Token: 0x0600009D RID: 157 RVA: 0x00003F64 File Offset: 0x00002164
		// (set) Token: 0x0600009E RID: 158 RVA: 0x00003F6B File Offset: 0x0000216B
		public static string tu
		{
			get
			{
				return "\u001b[4m";
			}
			set
			{
			}
		}

		// Token: 0x1700000D RID: 13
		// (get) Token: 0x0600009F RID: 159 RVA: 0x00003F6D File Offset: 0x0000216D
		// (set) Token: 0x060000A0 RID: 160 RVA: 0x00003F74 File Offset: 0x00002174
		public static string tn
		{
			get
			{
				return "\u001b[7m";
			}
			set
			{
			}
		}

		// Token: 0x1700000E RID: 14
		// (get) Token: 0x060000A1 RID: 161 RVA: 0x00003F76 File Offset: 0x00002176
		// (set) Token: 0x060000A2 RID: 162 RVA: 0x00003F7D File Offset: 0x0000217D
		public static string tr
		{
			get
			{
				return "\u001b[0m";
			}
			set
			{
			}
		}

		// Token: 0x060000A3 RID: 163 RVA: 0x00003F80 File Offset: 0x00002180
		public static void CenterConsole()
		{
			IntPtr consoleWindow = Win32.GetConsoleWindow();
			Win32.RECT rect;
			Win32.GetWindowRect(consoleWindow, out rect);
			int systemMetrics = Win32.GetSystemMetrics(Win32.SystemMetric.SM_CXSCREEN);
			int systemMetrics2 = Win32.GetSystemMetrics(Win32.SystemMetric.SM_CYSCREEN);
			int num = rect.right - rect.left;
			int num2 = rect.bottom - rect.top;
			Win32.SetWindowPos(consoleWindow, (IntPtr)0, (systemMetrics - num) / 2, (systemMetrics2 - num2) / 2, 0, 0, 1U);
		}

		// Token: 0x060000A4 RID: 164 RVA: 0x00003FE0 File Offset: 0x000021E0
		public static void MaximizeConsole()
		{
			Win32.ShowWindow(Win32.GetConsoleWindow(), 3);
		}

		// Token: 0x060000A5 RID: 165 RVA: 0x00003FEE File Offset: 0x000021EE
		public static void MinimizeConsole()
		{
			Win32.ShowWindow(Win32.GetConsoleWindow(), 6);
		}

		// Token: 0x060000A6 RID: 166 RVA: 0x00003FFC File Offset: 0x000021FC
		public static void HideConsole()
		{
			Win32.ShowWindow(Win32.GetConsoleWindow(), 0);
		}

		// Token: 0x060000A7 RID: 167 RVA: 0x0000400A File Offset: 0x0000220A
		public static void RestoreConsole()
		{
			Win32.ShowWindow(Win32.GetConsoleWindow(), 9);
		}

		// Token: 0x060000A8 RID: 168 RVA: 0x0000401C File Offset: 0x0000221C
		public static void FullscreenConsole()
		{
			Win32.COORD coord = new Win32.COORD(100, 100);
			Win32.SetConsoleDisplayMode(Win32.GetStdHandle(Win32.STD_OUTPUT_HANDLE), 1U, out coord);
			IntPtr stdHandle = Win32.GetStdHandle(-11);
			Win32.COORD coord2 = new Win32.COORD(100, 100);
			Win32.SetConsoleDisplayMode(stdHandle, 2U, out coord2);
		}

		// Token: 0x060000A9 RID: 169 RVA: 0x00004062 File Offset: 0x00002262
		public static void FullscreenWindowedConsole()
		{
		}

		// Token: 0x060000AA RID: 170 RVA: 0x00004064 File Offset: 0x00002264
		public static void DisableQuickEdit()
		{
			IntPtr stdHandle = Win32.GetStdHandle(-10);
			int num;
			if (!Win32.GetConsoleMode(stdHandle, out num))
			{
				return;
			}
			num &= -193;
			Win32.SetConsoleMode(stdHandle, num);
		}

		// Token: 0x060000AB RID: 171 RVA: 0x00004094 File Offset: 0x00002294
		public static void EnableQuickEdit()
		{
			IntPtr stdHandle = Win32.GetStdHandle(-10);
			int num;
			if (!Win32.GetConsoleMode(stdHandle, out num))
			{
				return;
			}
			num |= 192;
			Win32.SetConsoleMode(stdHandle, num);
		}

		// Token: 0x060000AC RID: 172 RVA: 0x000040C4 File Offset: 0x000022C4
		public static bool DownloadFileFromURL()
		{
			return false;
		}

		// Token: 0x060000AD RID: 173 RVA: 0x000040C8 File Offset: 0x000022C8
		public static void ExitApplicationPrint()
		{
			Console.WriteLine(string.Concat(new string[]
			{
				cmr.cr,
				"\n",
				cmr.cf(255, 207, 105),
				"Press any key to ",
				cmr.cf(255, 207, 105),
				"exit",
				cmr.cf(220, 220, 220),
				"...",
				cmr.cr,
				"\n"
			}));
			Console.ReadKey(true);
			Console.WriteLine(cmr.cf(255, 140, 46) + "Exiting... ");
			Environment.Exit(0);
		}

		// Token: 0x060000AE RID: 174 RVA: 0x0000418B File Offset: 0x0000238B
		public static void ExitApplication()
		{
			Console.WriteLine(cmr.cr + "\n" + cmr.cf(255, 140, 46) + "Exiting... \n");
			Environment.Exit(0);
		}

		// Token: 0x04000070 RID: 112
		public static readonly string esc = "\u001b[";
	}
}
