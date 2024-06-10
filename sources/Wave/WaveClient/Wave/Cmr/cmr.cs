using System;
using Wave.Cmr.Win32API;

namespace Wave.Cmr
{
	// Token: 0x0200001E RID: 30
	public class cmr
	{
		// Token: 0x0600006A RID: 106 RVA: 0x000033B8 File Offset: 0x000015B8
		public static void EnableVirtualTerminalProcessing()
		{
			IntPtr stdHandle = Win32.GetStdHandle(-11);
			uint num;
			Win32.GetConsoleMode(stdHandle, out num);
			Win32.SetConsoleMode(stdHandle, num | 4U);
		}

		// Token: 0x0600006B RID: 107 RVA: 0x000033DE File Offset: 0x000015DE
		public static void cout()
		{
		}

		// Token: 0x0600006C RID: 108 RVA: 0x000033E0 File Offset: 0x000015E0
		public static string cin(bool ClearImput = true)
		{
			return "";
		}

		// Token: 0x0600006D RID: 109 RVA: 0x000033E7 File Offset: 0x000015E7
		public static void cli()
		{
			while (Console.KeyAvailable)
			{
				Console.ReadKey(true);
			}
		}

		// Token: 0x0600006E RID: 110 RVA: 0x000033FC File Offset: 0x000015FC
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

		// Token: 0x0600006F RID: 111 RVA: 0x00003464 File Offset: 0x00001664
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

		// Token: 0x06000070 RID: 112 RVA: 0x000034CC File Offset: 0x000016CC
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

		// Token: 0x06000071 RID: 113 RVA: 0x00003524 File Offset: 0x00001724
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

		// Token: 0x06000072 RID: 114 RVA: 0x0000357C File Offset: 0x0000177C
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

		// Token: 0x06000073 RID: 115 RVA: 0x000035D4 File Offset: 0x000017D4
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
		// (get) Token: 0x06000074 RID: 116 RVA: 0x0000362A File Offset: 0x0000182A
		// (set) Token: 0x06000075 RID: 117 RVA: 0x0000363B File Offset: 0x0000183B
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
		// (get) Token: 0x06000076 RID: 118 RVA: 0x0000363D File Offset: 0x0000183D
		// (set) Token: 0x06000077 RID: 119 RVA: 0x00003644 File Offset: 0x00001844
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
		// (get) Token: 0x06000078 RID: 120 RVA: 0x00003646 File Offset: 0x00001846
		// (set) Token: 0x06000079 RID: 121 RVA: 0x0000364D File Offset: 0x0000184D
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
		// (get) Token: 0x0600007A RID: 122 RVA: 0x0000364F File Offset: 0x0000184F
		// (set) Token: 0x0600007B RID: 123 RVA: 0x00003656 File Offset: 0x00001856
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
		// (get) Token: 0x0600007C RID: 124 RVA: 0x00003658 File Offset: 0x00001858
		// (set) Token: 0x0600007D RID: 125 RVA: 0x0000365F File Offset: 0x0000185F
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
		// (get) Token: 0x0600007E RID: 126 RVA: 0x00003661 File Offset: 0x00001861
		// (set) Token: 0x0600007F RID: 127 RVA: 0x00003668 File Offset: 0x00001868
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
		// (get) Token: 0x06000080 RID: 128 RVA: 0x0000366A File Offset: 0x0000186A
		// (set) Token: 0x06000081 RID: 129 RVA: 0x00003671 File Offset: 0x00001871
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

		// Token: 0x06000082 RID: 130 RVA: 0x00003674 File Offset: 0x00001874
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

		// Token: 0x06000083 RID: 131 RVA: 0x000036D4 File Offset: 0x000018D4
		public static void MaximizeConsole()
		{
			Win32.ShowWindow(Win32.GetConsoleWindow(), 3);
		}

		// Token: 0x06000084 RID: 132 RVA: 0x000036E2 File Offset: 0x000018E2
		public static void MinimizeConsole()
		{
			Win32.ShowWindow(Win32.GetConsoleWindow(), 6);
		}

		// Token: 0x06000085 RID: 133 RVA: 0x000036F0 File Offset: 0x000018F0
		public static void HideConsole()
		{
			Win32.ShowWindow(Win32.GetConsoleWindow(), 0);
		}

		// Token: 0x06000086 RID: 134 RVA: 0x000036FE File Offset: 0x000018FE
		public static void RestoreConsole()
		{
			Win32.ShowWindow(Win32.GetConsoleWindow(), 9);
		}

		// Token: 0x06000087 RID: 135 RVA: 0x00003710 File Offset: 0x00001910
		public static void FullscreenConsole()
		{
			Win32.COORD coord = new Win32.COORD(100, 100);
			Win32.SetConsoleDisplayMode(Win32.GetStdHandle(Win32.STD_OUTPUT_HANDLE), 1U, out coord);
			IntPtr stdHandle = Win32.GetStdHandle(-11);
			Win32.COORD coord2 = new Win32.COORD(100, 100);
			Win32.SetConsoleDisplayMode(stdHandle, 2U, out coord2);
		}

		// Token: 0x06000088 RID: 136 RVA: 0x00003756 File Offset: 0x00001956
		public static void FullscreenWindowedConsole()
		{
		}

		// Token: 0x06000089 RID: 137 RVA: 0x00003758 File Offset: 0x00001958
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

		// Token: 0x0600008A RID: 138 RVA: 0x00003788 File Offset: 0x00001988
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

		// Token: 0x0600008B RID: 139 RVA: 0x000037B8 File Offset: 0x000019B8
		public static bool DownloadFileFromURL()
		{
			return false;
		}

		// Token: 0x0600008C RID: 140 RVA: 0x000037BC File Offset: 0x000019BC
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

		// Token: 0x0600008D RID: 141 RVA: 0x0000387F File Offset: 0x00001A7F
		public static void ExitApplication()
		{
			Console.WriteLine(cmr.cr + "\n" + cmr.cf(255, 140, 46) + "Exiting... \n");
			Environment.Exit(0);
		}

		// Token: 0x04000044 RID: 68
		public static readonly string esc = "\u001b[";
	}
}
