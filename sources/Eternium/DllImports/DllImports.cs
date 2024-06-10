using System;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Text;

namespace Eternium_mcpe_client.DllImports
{
	// Token: 0x02000014 RID: 20
	public static class DllImports
	{
		// Token: 0x06000063 RID: 99
		[DllImport("user32.dll")]
		private static extern bool RegisterHotKey(IntPtr hWnd, int id, uint fsModifiers, uint vk);

		// Token: 0x06000064 RID: 100
		[DllImport("user32.dll")]
		private static extern bool UnregisterHotKey(IntPtr hWnd, int id);

		// Token: 0x06000065 RID: 101
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

		// Token: 0x06000066 RID: 102
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool AddConsoleAlias(string Source, string Target, string ExeName);

		// Token: 0x06000067 RID: 103
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool AllocConsole();

		// Token: 0x06000068 RID: 104
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool AttachConsole(uint dwProcessId);

		// Token: 0x06000069 RID: 105
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr CreateConsoleScreenBuffer(uint dwDesiredAccess, uint dwShareMode, IntPtr lpSecurityAttributes, uint dwFlags, IntPtr lpScreenBufferData);

		// Token: 0x0600006A RID: 106
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool FillConsoleOutputAttribute(IntPtr hConsoleOutput, ushort wAttribute, uint nLength, DllImports.COORD dwWriteCoord, out uint lpNumberOfAttrsWritten);

		// Token: 0x0600006B RID: 107
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool FillConsoleOutputCharacter(IntPtr hConsoleOutput, char cCharacter, uint nLength, DllImports.COORD dwWriteCoord, out uint lpNumberOfCharsWritten);

		// Token: 0x0600006C RID: 108
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool FlushConsoleInputBuffer(IntPtr hConsoleInput);

		// Token: 0x0600006D RID: 109
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		public static extern bool FreeConsole();

		// Token: 0x0600006E RID: 110
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GenerateConsoleCtrlEvent(uint dwCtrlEvent, uint dwProcessGroupId);

		// Token: 0x0600006F RID: 111
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool GetConsoleAlias(string Source, out StringBuilder TargetBuffer, uint TargetBufferLength, string ExeName);

		// Token: 0x06000070 RID: 112
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleAliases(StringBuilder[] lpTargetBuffer, uint targetBufferLength, string lpExeName);

		// Token: 0x06000071 RID: 113
		[DllImport("kernel32", SetLastError = true)]
		public static extern uint GetConsoleAliasesLength(string ExeName);

		// Token: 0x06000072 RID: 114
		[DllImport("kernel32", SetLastError = true)]
		public static extern uint GetConsoleAliasExes(out StringBuilder ExeNameBuffer, uint ExeNameBufferLength);

		// Token: 0x06000073 RID: 115
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleAliasExesLength();

		// Token: 0x06000074 RID: 116
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleCP();

		// Token: 0x06000075 RID: 117
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleCursorInfo(IntPtr hConsoleOutput, out DllImports.CONSOLE_CURSOR_INFO lpConsoleCursorInfo);

		// Token: 0x06000076 RID: 118
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleDisplayMode(out uint ModeFlags);

		// Token: 0x06000077 RID: 119
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern DllImports.COORD GetConsoleFontSize(IntPtr hConsoleOutput, int nFont);

		// Token: 0x06000078 RID: 120
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleHistoryInfo(out DllImports.CONSOLE_HISTORY_INFO ConsoleHistoryInfo);

		// Token: 0x06000079 RID: 121
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleMode(IntPtr hConsoleHandle, out uint lpMode);

		// Token: 0x0600007A RID: 122
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleMode(IntPtr hConsoleHandle, out int lpMode);

		// Token: 0x0600007B RID: 123
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleOriginalTitle(out StringBuilder ConsoleTitle, uint Size);

		// Token: 0x0600007C RID: 124
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleOutputCP();

		// Token: 0x0600007D RID: 125
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleProcessList(out uint[] ProcessList, uint ProcessCount);

		// Token: 0x0600007E RID: 126
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleScreenBufferInfo(IntPtr hConsoleOutput, out DllImports.CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);

		// Token: 0x0600007F RID: 127
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleScreenBufferInfoEx(IntPtr hConsoleOutput, ref DllImports.CONSOLE_SCREEN_BUFFER_INFO_EX ConsoleScreenBufferInfo);

		// Token: 0x06000080 RID: 128
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleSelectionInfo(DllImports.CONSOLE_SELECTION_INFO ConsoleSelectionInfo);

		// Token: 0x06000081 RID: 129
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleTitle([Out] StringBuilder lpConsoleTitle, uint nSize);

		// Token: 0x06000082 RID: 130
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr GetConsoleWindow();

		// Token: 0x06000083 RID: 131
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetCurrentConsoleFont(IntPtr hConsoleOutput, bool bMaximumWindow, out DllImports.CONSOLE_FONT_INFO lpConsoleCurrentFont);

		// Token: 0x06000084 RID: 132
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetCurrentConsoleFontEx(IntPtr ConsoleOutput, bool MaximumWindow, out DllImports.CONSOLE_FONT_INFO_EX ConsoleCurrentFont);

		// Token: 0x06000085 RID: 133
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern DllImports.COORD GetLargestConsoleWindowSize(IntPtr hConsoleOutput);

		// Token: 0x06000086 RID: 134
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetNumberOfConsoleInputEvents(IntPtr hConsoleInput, out uint lpcNumberOfEvents);

		// Token: 0x06000087 RID: 135
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetNumberOfConsoleMouseButtons(ref uint lpNumberOfMouseButtons);

		// Token: 0x06000088 RID: 136
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr GetStdHandle(int nStdHandle);

		// Token: 0x06000089 RID: 137
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool PeekConsoleInput(IntPtr hConsoleInput, [Out] DllImports.INPUT_RECORD[] lpBuffer, uint nLength, out uint lpNumberOfEventsRead);

		// Token: 0x0600008A RID: 138
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsole(IntPtr hConsoleInput, [Out] StringBuilder lpBuffer, uint nNumberOfCharsToRead, out uint lpNumberOfCharsRead, IntPtr lpReserved);

		// Token: 0x0600008B RID: 139
		[DllImport("kernel32.dll", CharSet = CharSet.Unicode, EntryPoint = "ReadConsoleInputW")]
		public static extern bool ReadConsoleInput(IntPtr hConsoleInput, [Out] DllImports.INPUT_RECORD[] lpBuffer, uint nLength, out uint lpNumberOfEventsRead);

		// Token: 0x0600008C RID: 140
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsoleOutput(IntPtr hConsoleOutput, [Out] DllImports.CHAR_INFO[] lpBuffer, DllImports.COORD dwBufferSize, DllImports.COORD dwBufferCoord, ref DllImports.SMALL_RECT lpReadRegion);

		// Token: 0x0600008D RID: 141
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsoleOutputAttribute(IntPtr hConsoleOutput, [Out] ushort[] lpAttribute, uint nLength, DllImports.COORD dwReadCoord, out uint lpNumberOfAttrsRead);

		// Token: 0x0600008E RID: 142
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsoleOutputCharacter(IntPtr hConsoleOutput, [Out] StringBuilder lpCharacter, uint nLength, DllImports.COORD dwReadCoord, out uint lpNumberOfCharsRead);

		// Token: 0x0600008F RID: 143
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ScrollConsoleScreenBuffer(IntPtr hConsoleOutput, [In] ref DllImports.SMALL_RECT lpScrollRectangle, IntPtr lpClipRectangle, DllImports.COORD dwDestinationOrigin, [In] ref DllImports.CHAR_INFO lpFill);

		// Token: 0x06000090 RID: 144
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleActiveScreenBuffer(IntPtr hConsoleOutput);

		// Token: 0x06000091 RID: 145
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCP(uint wCodePageID);

		// Token: 0x06000092 RID: 146
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCtrlHandler(DllImports.ConsoleCtrlDelegate HandlerRoutine, bool Add);

		// Token: 0x06000093 RID: 147
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCursorInfo(IntPtr hConsoleOutput, [In] ref DllImports.CONSOLE_CURSOR_INFO lpConsoleCursorInfo);

		// Token: 0x06000094 RID: 148
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCursorPosition(IntPtr hConsoleOutput, DllImports.COORD dwCursorPosition);

		// Token: 0x06000095 RID: 149
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleDisplayMode(IntPtr ConsoleOutput, uint Flags, out DllImports.COORD NewScreenBufferDimensions);

		// Token: 0x06000096 RID: 150
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleHistoryInfo(DllImports.CONSOLE_HISTORY_INFO ConsoleHistoryInfo);

		// Token: 0x06000097 RID: 151
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleMode(IntPtr hConsoleHandle, uint dwMode);

		// Token: 0x06000098 RID: 152
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleMode(IntPtr hConsoleHandle, int dwMode);

		// Token: 0x06000099 RID: 153
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleOutputCP(uint wCodePageID);

		// Token: 0x0600009A RID: 154
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleScreenBufferInfoEx(IntPtr ConsoleOutput, DllImports.CONSOLE_SCREEN_BUFFER_INFO_EX ConsoleScreenBufferInfoEx);

		// Token: 0x0600009B RID: 155
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleScreenBufferSize(IntPtr hConsoleOutput, DllImports.COORD dwSize);

		// Token: 0x0600009C RID: 156
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleTextAttribute(IntPtr hConsoleOutput, ushort wAttributes);

		// Token: 0x0600009D RID: 157
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleTitle(string lpConsoleTitle);

		// Token: 0x0600009E RID: 158
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleWindowInfo(IntPtr hConsoleOutput, bool bAbsolute, [In] ref DllImports.SMALL_RECT lpConsoleWindow);

		// Token: 0x0600009F RID: 159
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetCurrentConsoleFontEx(IntPtr ConsoleOutput, bool MaximumWindow, ref DllImports.CONSOLE_FONT_INFO_EX ConsoleCurrentFontEx);

		// Token: 0x060000A0 RID: 160
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetCurrentConsoleFontEx(IntPtr ConsoleOutput, bool MaximumWindow, DllImports.CONSOLE_FONT_INFO_EX ConsoleCurrentFontEx);

		// Token: 0x060000A1 RID: 161
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetStdHandle(uint nStdHandle, IntPtr hHandle);

		// Token: 0x060000A2 RID: 162
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsole(IntPtr hConsoleOutput, string lpBuffer, uint nNumberOfCharsToWrite, out uint lpNumberOfCharsWritten, IntPtr lpReserved);

		// Token: 0x060000A3 RID: 163
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleInput(IntPtr hConsoleInput, DllImports.INPUT_RECORD[] lpBuffer, uint nLength, out uint lpNumberOfEventsWritten);

		// Token: 0x060000A4 RID: 164
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleOutput(IntPtr hConsoleOutput, DllImports.CHAR_INFO[] lpBuffer, DllImports.COORD dwBufferSize, DllImports.COORD dwBufferCoord, ref DllImports.SMALL_RECT lpWriteRegion);

		// Token: 0x060000A5 RID: 165
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleOutputAttribute(IntPtr hConsoleOutput, ushort[] lpAttribute, uint nLength, DllImports.COORD dwWriteCoord, out uint lpNumberOfAttrsWritten);

		// Token: 0x060000A6 RID: 166
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleOutputCharacter(IntPtr hConsoleOutput, string lpCharacter, uint nLength, DllImports.COORD dwWriteCoord, out uint lpNumberOfCharsWritten);

		// Token: 0x060000A7 RID: 167
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool GetWindowRect(IntPtr hwnd, out DllImports.RECT lpRect);

		// Token: 0x060000A8 RID: 168
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool GetWindowRect(IntPtr hwnd, out DllImports.SimpleRECT lpRect);

		// Token: 0x060000A9 RID: 169
		[DllImport("user32.dll")]
		public static extern int GetSystemMetrics(DllImports.SystemMetric smIndex);

		// Token: 0x060000AA RID: 170
		[DllImport("user32.dll", SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int x, int y, int cx, int cy, uint uFlags);

		// Token: 0x060000AB RID: 171
		[DllImport("user32")]
		public static extern bool GetMonitorInfo(IntPtr hMonitor, DllImports.MONITORINFO lpmi);

		// Token: 0x060000AC RID: 172
		[DllImport("User32")]
		public static extern IntPtr MonitorFromWindow(IntPtr handle, int flags);

		// Token: 0x060000AD RID: 173
		[DllImport("User32.dll", SetLastError = true)]
		public static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

		// Token: 0x060000AE RID: 174
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool MoveWindow(IntPtr hWnd, int X, int Y, int Width, int Height, bool Repaint);

		// Token: 0x060000AF RID: 175
		[DllImport("user32.dll", SetLastError = true)]
		public static extern short GetAsyncKeyState(int key);

		// Token: 0x060000B0 RID: 176
		[DllImport("user32.dll", SetLastError = true)]
		public static extern short GetAsyncKeyState(short key);

		// Token: 0x060000B1 RID: 177
		[DllImport("user32.dll")]
		public static extern short GetAsyncKeyState(DllImports.VirtualKeys vKey);

		// Token: 0x060000B2 RID: 178
		[DllImport("user32.dll")]
		public static extern short GetAsyncKeyState(DllImports.VK vKey);

		// Token: 0x060000B3 RID: 179
		[DllImport("user32.dll", SetLastError = true)]
		public static extern int GetWindowLong(IntPtr hWnd, int nIndex);

		// Token: 0x060000B4 RID: 180
		[DllImport("user32.dll")]
		public static extern int SetWindowLong(IntPtr hWnd, int nIndex, uint dwNewLong);

		// Token: 0x060000B5 RID: 181
		[DllImport("user32.dll")]
		public static extern int SetWindowLong(IntPtr hWnd, int nIndex, int dwNewLong);

		// Token: 0x060000B6 RID: 182
		[DllImport("user32.dll")]
		public static extern bool SetLayeredWindowAttributes(IntPtr hwnd, uint crKey, byte bAlpha, uint dwFlags);

		// Token: 0x060000B7 RID: 183
		[DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
		public static extern IntPtr GetForegroundWindow();

		// Token: 0x060000B8 RID: 184
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern int GetWindowThreadProcessId(IntPtr handle, out int processId);

		// Token: 0x060000B9 RID: 185
		[DllImport("user32.dll")]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool SetForegroundWindow(IntPtr hWnd);

		// Token: 0x060000BA RID: 186
		[DllImport("user32.dll")]
		public static extern uint SendInput(uint nInputs, [MarshalAs(UnmanagedType.LPArray)] [In] DllImports.INPUT[] pInputs, int cbSize);

		// Token: 0x060000BB RID: 187
		[DllImport("user32.dll")]
		public static extern void mouse_event(uint dwFlags, int dx, int dy, uint dwData, int dwExtraInfo);

		// Token: 0x060000BC RID: 188
		[DllImport("user32.dll")]
		public static extern void mouse_event(DllImports.MouseEventFlags dwFlags, int dx, int dy, uint dwData, int dwExtraInfo);

		// Token: 0x060000BD RID: 189
		[DllImport("user32")]
		public static extern int SetCursorPos(int x, int y);

		// Token: 0x0400004D RID: 77
		public const int SWP_ASYNCWINDOWPOS = 16384;

		// Token: 0x0400004E RID: 78
		public const int SWP_DEFERERASE = 8192;

		// Token: 0x0400004F RID: 79
		public const int SWP_DRAWFRAME = 32;

		// Token: 0x04000050 RID: 80
		public const int SWP_FRAMECHANGED = 32;

		// Token: 0x04000051 RID: 81
		public const int SWP_HIDEWINDOW = 128;

		// Token: 0x04000052 RID: 82
		public const int SWP_NOACTIVATE = 16;

		// Token: 0x04000053 RID: 83
		public const int SWP_NOCOPYBITS = 256;

		// Token: 0x04000054 RID: 84
		public const int SWP_NOMOVE = 2;

		// Token: 0x04000055 RID: 85
		public const int SWP_NOOWNERZORDER = 512;

		// Token: 0x04000056 RID: 86
		public const int SWP_NOREDRAW = 8;

		// Token: 0x04000057 RID: 87
		public const int SWP_NOREPOSITION = 512;

		// Token: 0x04000058 RID: 88
		public const int SWP_NOSENDCHANGING = 1024;

		// Token: 0x04000059 RID: 89
		public const int SWP_NOSIZE = 1;

		// Token: 0x0400005A RID: 90
		public const int SWP_NOZORDER = 4;

		// Token: 0x0400005B RID: 91
		public const int SWP_SHOWWINDOW = 64;

		// Token: 0x0400005C RID: 92
		public const int HWND_TOP = 0;

		// Token: 0x0400005D RID: 93
		public const int HWND_BOTTOM = 1;

		// Token: 0x0400005E RID: 94
		public const int HWND_TOPMOST = -1;

		// Token: 0x0400005F RID: 95
		public const int HWND_NOTOPMOST = -2;

		// Token: 0x04000060 RID: 96
		public static IntPtr INVALID_HANDLE_VALUE = new IntPtr(-1);

		// Token: 0x04000061 RID: 97
		public static readonly int TMPF_TRUETYPE = 4;

		// Token: 0x04000062 RID: 98
		public static readonly int STD_OUTPUT_HANDLE = -11;

		// Token: 0x02000022 RID: 34
		// (Invoke) Token: 0x06000121 RID: 289
		public delegate bool ConsoleCtrlDelegate(DllImports.CtrlTypes CtrlType);

		// Token: 0x02000023 RID: 35
		public struct COORD
		{
			// Token: 0x06000124 RID: 292 RVA: 0x0000625F File Offset: 0x0000445F
			internal COORD(short x, short y)
			{
				this.X = x;
				this.Y = y;
			}

			// Token: 0x040000A7 RID: 167
			internal short X;

			// Token: 0x040000A8 RID: 168
			internal short Y;
		}

		// Token: 0x02000024 RID: 36
		public struct SMALL_RECT
		{
			// Token: 0x040000A9 RID: 169
			public short Left;

			// Token: 0x040000AA RID: 170
			public short Top;

			// Token: 0x040000AB RID: 171
			public short Right;

			// Token: 0x040000AC RID: 172
			public short Bottom;
		}

		// Token: 0x02000025 RID: 37
		public struct CONSOLE_SCREEN_BUFFER_INFO
		{
			// Token: 0x040000AD RID: 173
			public DllImports.COORD dwSize;

			// Token: 0x040000AE RID: 174
			public DllImports.COORD dwCursorPosition;

			// Token: 0x040000AF RID: 175
			public short wAttributes;

			// Token: 0x040000B0 RID: 176
			public DllImports.SMALL_RECT srWindow;

			// Token: 0x040000B1 RID: 177
			public DllImports.COORD dwMaximumWindowSize;
		}

		// Token: 0x02000026 RID: 38
		public struct CONSOLE_SCREEN_BUFFER_INFO_EX
		{
			// Token: 0x06000125 RID: 293 RVA: 0x00006270 File Offset: 0x00004470
			public static DllImports.CONSOLE_SCREEN_BUFFER_INFO_EX Create()
			{
				return new DllImports.CONSOLE_SCREEN_BUFFER_INFO_EX
				{
					cbSize = 96U
				};
			}

			// Token: 0x040000B2 RID: 178
			public uint cbSize;

			// Token: 0x040000B3 RID: 179
			public DllImports.COORD dwSize;

			// Token: 0x040000B4 RID: 180
			public DllImports.COORD dwCursorPosition;

			// Token: 0x040000B5 RID: 181
			public short wAttributes;

			// Token: 0x040000B6 RID: 182
			public DllImports.SMALL_RECT srWindow;

			// Token: 0x040000B7 RID: 183
			public DllImports.COORD dwMaximumWindowSize;

			// Token: 0x040000B8 RID: 184
			public ushort wPopupAttributes;

			// Token: 0x040000B9 RID: 185
			public bool bFullscreenSupported;

			// Token: 0x040000BA RID: 186
			[MarshalAs(UnmanagedType.ByValArray, SizeConst = 16)]
			public DllImports.COLORREF[] ColorTable;
		}

		// Token: 0x02000027 RID: 39
		public struct COLORREF
		{
			// Token: 0x06000126 RID: 294 RVA: 0x00006294 File Offset: 0x00004494
			public COLORREF(Color color)
			{
				this.ColorDWORD = (uint)((int)color.R + ((int)color.G << 8) + ((int)color.B << 16));
			}

			// Token: 0x06000127 RID: 295 RVA: 0x000062BC File Offset: 0x000044BC
			public Color GetColor()
			{
				return Color.FromArgb((int)(255U & this.ColorDWORD), (int)(65280U & this.ColorDWORD) >> 8, (int)(16711680U & this.ColorDWORD) >> 16);
			}

			// Token: 0x06000128 RID: 296 RVA: 0x000062FC File Offset: 0x000044FC
			public void SetColor(Color color)
			{
				this.ColorDWORD = (uint)((int)color.R + ((int)color.G << 8) + ((int)color.B << 16));
			}

			// Token: 0x040000BB RID: 187
			public uint ColorDWORD;
		}

		// Token: 0x02000028 RID: 40
		public struct CONSOLE_FONT_INFO
		{
			// Token: 0x040000BC RID: 188
			public int nFont;

			// Token: 0x040000BD RID: 189
			public DllImports.COORD dwFontSize;
		}

		// Token: 0x02000029 RID: 41
		[StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)]
		public struct CONSOLE_FONT_INFO_EX
		{
			// Token: 0x040000BE RID: 190
			internal uint cbSize;

			// Token: 0x040000BF RID: 191
			internal uint nFont;

			// Token: 0x040000C0 RID: 192
			internal DllImports.COORD dwFontSize;

			// Token: 0x040000C1 RID: 193
			internal int FontFamily;

			// Token: 0x040000C2 RID: 194
			internal int FontWeight;

			// Token: 0x040000C3 RID: 195
			[FixedBuffer(typeof(char), 32)]
			internal DllImports.CONSOLE_FONT_INFO_EX.<FaceName>e__FixedBuffer FaceName;

			// Token: 0x02000067 RID: 103
			[CompilerGenerated]
			[UnsafeValueType]
			[StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode, Size = 64)]
			public struct <FaceName>e__FixedBuffer
			{
				// Token: 0x040005E9 RID: 1513
				public char FixedElementField;
			}
		}

		// Token: 0x0200002A RID: 42
		[StructLayout(LayoutKind.Explicit)]
		public struct INPUT_RECORD
		{
			// Token: 0x040000C4 RID: 196
			[FieldOffset(0)]
			public ushort EventType;

			// Token: 0x040000C5 RID: 197
			[FieldOffset(4)]
			public DllImports.KEY_EVENT_RECORD KeyEvent;

			// Token: 0x040000C6 RID: 198
			[FieldOffset(4)]
			public DllImports.MOUSE_EVENT_RECORD MouseEvent;

			// Token: 0x040000C7 RID: 199
			[FieldOffset(4)]
			public DllImports.WINDOW_BUFFER_SIZE_RECORD WindowBufferSizeEvent;

			// Token: 0x040000C8 RID: 200
			[FieldOffset(4)]
			public DllImports.MENU_EVENT_RECORD MenuEvent;

			// Token: 0x040000C9 RID: 201
			[FieldOffset(4)]
			public DllImports.FOCUS_EVENT_RECORD FocusEvent;
		}

		// Token: 0x0200002B RID: 43
		[StructLayout(LayoutKind.Explicit, CharSet = CharSet.Unicode)]
		public struct KEY_EVENT_RECORD
		{
			// Token: 0x040000CA RID: 202
			[FieldOffset(0)]
			[MarshalAs(UnmanagedType.Bool)]
			public bool bKeyDown;

			// Token: 0x040000CB RID: 203
			[FieldOffset(4)]
			[MarshalAs(UnmanagedType.U2)]
			public ushort wRepeatCount;

			// Token: 0x040000CC RID: 204
			[FieldOffset(6)]
			[MarshalAs(UnmanagedType.U2)]
			public ushort wVirtualKeyCode;

			// Token: 0x040000CD RID: 205
			[FieldOffset(8)]
			[MarshalAs(UnmanagedType.U2)]
			public ushort wVirtualScanCode;

			// Token: 0x040000CE RID: 206
			[FieldOffset(10)]
			public char UnicodeChar;

			// Token: 0x040000CF RID: 207
			[FieldOffset(12)]
			[MarshalAs(UnmanagedType.U4)]
			public uint dwControlKeyState;
		}

		// Token: 0x0200002C RID: 44
		public struct MOUSE_EVENT_RECORD
		{
			// Token: 0x040000D0 RID: 208
			public DllImports.COORD dwMousePosition;

			// Token: 0x040000D1 RID: 209
			public uint dwButtonState;

			// Token: 0x040000D2 RID: 210
			public uint dwControlKeyState;

			// Token: 0x040000D3 RID: 211
			public uint dwEventFlags;
		}

		// Token: 0x0200002D RID: 45
		public struct WINDOW_BUFFER_SIZE_RECORD
		{
			// Token: 0x06000129 RID: 297 RVA: 0x00006321 File Offset: 0x00004521
			public WINDOW_BUFFER_SIZE_RECORD(short x, short y)
			{
				this.dwSize = default(DllImports.COORD);
				this.dwSize.X = x;
				this.dwSize.Y = y;
			}

			// Token: 0x040000D4 RID: 212
			public DllImports.COORD dwSize;
		}

		// Token: 0x0200002E RID: 46
		public struct MENU_EVENT_RECORD
		{
			// Token: 0x040000D5 RID: 213
			public uint dwCommandId;
		}

		// Token: 0x0200002F RID: 47
		public struct FOCUS_EVENT_RECORD
		{
			// Token: 0x040000D6 RID: 214
			public uint bSetFocus;
		}

		// Token: 0x02000030 RID: 48
		[StructLayout(LayoutKind.Explicit)]
		public struct CHAR_INFO
		{
			// Token: 0x040000D7 RID: 215
			[FieldOffset(0)]
			private char UnicodeChar;

			// Token: 0x040000D8 RID: 216
			[FieldOffset(0)]
			private char AsciiChar;

			// Token: 0x040000D9 RID: 217
			[FieldOffset(2)]
			private ushort Attributes;
		}

		// Token: 0x02000031 RID: 49
		public struct CONSOLE_CURSOR_INFO
		{
			// Token: 0x040000DA RID: 218
			private uint Size;

			// Token: 0x040000DB RID: 219
			private bool Visible;
		}

		// Token: 0x02000032 RID: 50
		public struct CONSOLE_HISTORY_INFO
		{
			// Token: 0x040000DC RID: 220
			private ushort cbSize;

			// Token: 0x040000DD RID: 221
			private ushort HistoryBufferSize;

			// Token: 0x040000DE RID: 222
			private ushort NumberOfHistoryBuffers;

			// Token: 0x040000DF RID: 223
			private uint dwFlags;
		}

		// Token: 0x02000033 RID: 51
		public struct CONSOLE_SELECTION_INFO
		{
			// Token: 0x040000E0 RID: 224
			private uint Flags;

			// Token: 0x040000E1 RID: 225
			private DllImports.COORD SelectionAnchor;

			// Token: 0x040000E2 RID: 226
			private DllImports.SMALL_RECT Selection;

			// Token: 0x040000E3 RID: 227
			private const uint CONSOLE_MOUSE_DOWN = 8U;

			// Token: 0x040000E4 RID: 228
			private const uint CONSOLE_MOUSE_SELECTION = 4U;

			// Token: 0x040000E5 RID: 229
			private const uint CONSOLE_NO_SELECTION = 0U;

			// Token: 0x040000E6 RID: 230
			private const uint CONSOLE_SELECTION_IN_PROGRESS = 1U;

			// Token: 0x040000E7 RID: 231
			private const uint CONSOLE_SELECTION_NOT_EMPTY = 2U;
		}

		// Token: 0x02000034 RID: 52
		public enum CtrlTypes : uint
		{
			// Token: 0x040000E9 RID: 233
			CTRL_C_EVENT,
			// Token: 0x040000EA RID: 234
			CTRL_BREAK_EVENT,
			// Token: 0x040000EB RID: 235
			CTRL_CLOSE_EVENT,
			// Token: 0x040000EC RID: 236
			CTRL_LOGOFF_EVENT = 5U,
			// Token: 0x040000ED RID: 237
			CTRL_SHUTDOWN_EVENT
		}

		// Token: 0x02000035 RID: 53
		[Flags]
		public enum MouseEventFlags : uint
		{
			// Token: 0x040000EF RID: 239
			LEFTDOWN = 2U,
			// Token: 0x040000F0 RID: 240
			LEFTUP = 4U,
			// Token: 0x040000F1 RID: 241
			MIDDLEDOWN = 32U,
			// Token: 0x040000F2 RID: 242
			MIDDLEUP = 64U,
			// Token: 0x040000F3 RID: 243
			MOVE = 1U,
			// Token: 0x040000F4 RID: 244
			ABSOLUTE = 32768U,
			// Token: 0x040000F5 RID: 245
			RIGHTDOWN = 8U,
			// Token: 0x040000F6 RID: 246
			RIGHTUP = 16U,
			// Token: 0x040000F7 RID: 247
			WHEEL = 2048U,
			// Token: 0x040000F8 RID: 248
			XDOWN = 128U,
			// Token: 0x040000F9 RID: 249
			XUP = 256U
		}

		// Token: 0x02000036 RID: 54
		public enum MouseEventDataXButtons : uint
		{
			// Token: 0x040000FB RID: 251
			XBUTTON1 = 1U,
			// Token: 0x040000FC RID: 252
			XBUTTON2
		}

		// Token: 0x02000037 RID: 55
		public struct INPUT
		{
			// Token: 0x17000005 RID: 5
			// (get) Token: 0x0600012A RID: 298 RVA: 0x00006348 File Offset: 0x00004548
			internal static int Size
			{
				get
				{
					return Marshal.SizeOf(typeof(DllImports.INPUT));
				}
			}

			// Token: 0x040000FD RID: 253
			internal uint Type;

			// Token: 0x040000FE RID: 254
			internal DllImports.InputUnion U;
		}

		// Token: 0x02000038 RID: 56
		[StructLayout(LayoutKind.Explicit)]
		public struct InputUnion
		{
			// Token: 0x040000FF RID: 255
			[FieldOffset(0)]
			internal DllImports.MOUSEINPUT MouseInput;

			// Token: 0x04000100 RID: 256
			[FieldOffset(0)]
			internal DllImports.KEYBDINPUT KeyboardInput;

			// Token: 0x04000101 RID: 257
			[FieldOffset(0)]
			internal DllImports.HARDWAREINPUT HardwareInput;
		}

		// Token: 0x02000039 RID: 57
		internal struct HARDWAREINPUT
		{
			// Token: 0x04000102 RID: 258
			internal int uMsg;

			// Token: 0x04000103 RID: 259
			internal short wParamL;

			// Token: 0x04000104 RID: 260
			internal short wParamH;
		}

		// Token: 0x0200003A RID: 58
		public struct MOUSEINPUT
		{
			// Token: 0x04000105 RID: 261
			internal int dx;

			// Token: 0x04000106 RID: 262
			internal int dy;

			// Token: 0x04000107 RID: 263
			internal int mouseData;

			// Token: 0x04000108 RID: 264
			internal DllImports.MOUSEEVENTF dwFlags;

			// Token: 0x04000109 RID: 265
			internal uint time;

			// Token: 0x0400010A RID: 266
			internal UIntPtr dwExtraInfo;
		}

		// Token: 0x0200003B RID: 59
		internal struct KEYBDINPUT
		{
			// Token: 0x0400010B RID: 267
			internal DllImports.VirtualKeys wVk;

			// Token: 0x0400010C RID: 268
			internal DllImports.ScanCodeShort wScan;

			// Token: 0x0400010D RID: 269
			internal DllImports.KEYEVENTF dwFlags;

			// Token: 0x0400010E RID: 270
			internal int time;

			// Token: 0x0400010F RID: 271
			internal UIntPtr dwExtraInfo;
		}

		// Token: 0x0200003C RID: 60
		public enum ScanCodeShort : short
		{
			// Token: 0x04000111 RID: 273
			LBUTTON,
			// Token: 0x04000112 RID: 274
			RBUTTON = 0,
			// Token: 0x04000113 RID: 275
			CANCEL = 70,
			// Token: 0x04000114 RID: 276
			MBUTTON = 0,
			// Token: 0x04000115 RID: 277
			XBUTTON1 = 0,
			// Token: 0x04000116 RID: 278
			XBUTTON2 = 0,
			// Token: 0x04000117 RID: 279
			BACK = 14,
			// Token: 0x04000118 RID: 280
			TAB,
			// Token: 0x04000119 RID: 281
			CLEAR = 76,
			// Token: 0x0400011A RID: 282
			RETURN = 28,
			// Token: 0x0400011B RID: 283
			SHIFT = 42,
			// Token: 0x0400011C RID: 284
			CONTROL = 29,
			// Token: 0x0400011D RID: 285
			MENU = 56,
			// Token: 0x0400011E RID: 286
			PAUSE = 0,
			// Token: 0x0400011F RID: 287
			CAPITAL = 58,
			// Token: 0x04000120 RID: 288
			KANA = 0,
			// Token: 0x04000121 RID: 289
			HANGUL = 0,
			// Token: 0x04000122 RID: 290
			JUNJA = 0,
			// Token: 0x04000123 RID: 291
			FINAL = 0,
			// Token: 0x04000124 RID: 292
			HANJA = 0,
			// Token: 0x04000125 RID: 293
			KANJI = 0,
			// Token: 0x04000126 RID: 294
			ESCAPE,
			// Token: 0x04000127 RID: 295
			CONVERT = 0,
			// Token: 0x04000128 RID: 296
			NONCONVERT = 0,
			// Token: 0x04000129 RID: 297
			ACCEPT = 0,
			// Token: 0x0400012A RID: 298
			MODECHANGE = 0,
			// Token: 0x0400012B RID: 299
			SPACE = 57,
			// Token: 0x0400012C RID: 300
			PRIOR = 73,
			// Token: 0x0400012D RID: 301
			NEXT = 81,
			// Token: 0x0400012E RID: 302
			END = 79,
			// Token: 0x0400012F RID: 303
			HOME = 71,
			// Token: 0x04000130 RID: 304
			LEFT = 75,
			// Token: 0x04000131 RID: 305
			UP = 72,
			// Token: 0x04000132 RID: 306
			RIGHT = 77,
			// Token: 0x04000133 RID: 307
			DOWN = 80,
			// Token: 0x04000134 RID: 308
			SELECT = 0,
			// Token: 0x04000135 RID: 309
			PRINT = 0,
			// Token: 0x04000136 RID: 310
			EXECUTE = 0,
			// Token: 0x04000137 RID: 311
			SNAPSHOT = 84,
			// Token: 0x04000138 RID: 312
			INSERT = 82,
			// Token: 0x04000139 RID: 313
			DELETE,
			// Token: 0x0400013A RID: 314
			HELP = 99,
			// Token: 0x0400013B RID: 315
			KEY_0 = 11,
			// Token: 0x0400013C RID: 316
			KEY_1 = 2,
			// Token: 0x0400013D RID: 317
			KEY_2,
			// Token: 0x0400013E RID: 318
			KEY_3,
			// Token: 0x0400013F RID: 319
			KEY_4,
			// Token: 0x04000140 RID: 320
			KEY_5,
			// Token: 0x04000141 RID: 321
			KEY_6,
			// Token: 0x04000142 RID: 322
			KEY_7,
			// Token: 0x04000143 RID: 323
			KEY_8,
			// Token: 0x04000144 RID: 324
			KEY_9,
			// Token: 0x04000145 RID: 325
			KEY_A = 30,
			// Token: 0x04000146 RID: 326
			KEY_B = 48,
			// Token: 0x04000147 RID: 327
			KEY_C = 46,
			// Token: 0x04000148 RID: 328
			KEY_D = 32,
			// Token: 0x04000149 RID: 329
			KEY_E = 18,
			// Token: 0x0400014A RID: 330
			KEY_F = 33,
			// Token: 0x0400014B RID: 331
			KEY_G,
			// Token: 0x0400014C RID: 332
			KEY_H,
			// Token: 0x0400014D RID: 333
			KEY_I = 23,
			// Token: 0x0400014E RID: 334
			KEY_J = 36,
			// Token: 0x0400014F RID: 335
			KEY_K,
			// Token: 0x04000150 RID: 336
			KEY_L,
			// Token: 0x04000151 RID: 337
			KEY_M = 50,
			// Token: 0x04000152 RID: 338
			KEY_N = 49,
			// Token: 0x04000153 RID: 339
			KEY_O = 24,
			// Token: 0x04000154 RID: 340
			KEY_P,
			// Token: 0x04000155 RID: 341
			KEY_Q = 16,
			// Token: 0x04000156 RID: 342
			KEY_R = 19,
			// Token: 0x04000157 RID: 343
			KEY_S = 31,
			// Token: 0x04000158 RID: 344
			KEY_T = 20,
			// Token: 0x04000159 RID: 345
			KEY_U = 22,
			// Token: 0x0400015A RID: 346
			KEY_V = 47,
			// Token: 0x0400015B RID: 347
			KEY_W = 17,
			// Token: 0x0400015C RID: 348
			KEY_X = 45,
			// Token: 0x0400015D RID: 349
			KEY_Y = 21,
			// Token: 0x0400015E RID: 350
			KEY_Z = 44,
			// Token: 0x0400015F RID: 351
			LWIN = 91,
			// Token: 0x04000160 RID: 352
			RWIN,
			// Token: 0x04000161 RID: 353
			APPS,
			// Token: 0x04000162 RID: 354
			SLEEP = 95,
			// Token: 0x04000163 RID: 355
			NUMPAD0 = 82,
			// Token: 0x04000164 RID: 356
			NUMPAD1 = 79,
			// Token: 0x04000165 RID: 357
			NUMPAD2,
			// Token: 0x04000166 RID: 358
			NUMPAD3,
			// Token: 0x04000167 RID: 359
			NUMPAD4 = 75,
			// Token: 0x04000168 RID: 360
			NUMPAD5,
			// Token: 0x04000169 RID: 361
			NUMPAD6,
			// Token: 0x0400016A RID: 362
			NUMPAD7 = 71,
			// Token: 0x0400016B RID: 363
			NUMPAD8,
			// Token: 0x0400016C RID: 364
			NUMPAD9,
			// Token: 0x0400016D RID: 365
			MULTIPLY = 55,
			// Token: 0x0400016E RID: 366
			ADD = 78,
			// Token: 0x0400016F RID: 367
			SEPARATOR = 0,
			// Token: 0x04000170 RID: 368
			SUBTRACT = 74,
			// Token: 0x04000171 RID: 369
			DECIMAL = 83,
			// Token: 0x04000172 RID: 370
			DIVIDE = 53,
			// Token: 0x04000173 RID: 371
			F1 = 59,
			// Token: 0x04000174 RID: 372
			F2,
			// Token: 0x04000175 RID: 373
			F3,
			// Token: 0x04000176 RID: 374
			F4,
			// Token: 0x04000177 RID: 375
			F5,
			// Token: 0x04000178 RID: 376
			F6,
			// Token: 0x04000179 RID: 377
			F7,
			// Token: 0x0400017A RID: 378
			F8,
			// Token: 0x0400017B RID: 379
			F9,
			// Token: 0x0400017C RID: 380
			F10,
			// Token: 0x0400017D RID: 381
			F11 = 87,
			// Token: 0x0400017E RID: 382
			F12,
			// Token: 0x0400017F RID: 383
			F13 = 100,
			// Token: 0x04000180 RID: 384
			F14,
			// Token: 0x04000181 RID: 385
			F15,
			// Token: 0x04000182 RID: 386
			F16,
			// Token: 0x04000183 RID: 387
			F17,
			// Token: 0x04000184 RID: 388
			F18,
			// Token: 0x04000185 RID: 389
			F19,
			// Token: 0x04000186 RID: 390
			F20,
			// Token: 0x04000187 RID: 391
			F21,
			// Token: 0x04000188 RID: 392
			F22,
			// Token: 0x04000189 RID: 393
			F23,
			// Token: 0x0400018A RID: 394
			F24 = 118,
			// Token: 0x0400018B RID: 395
			NUMLOCK = 69,
			// Token: 0x0400018C RID: 396
			SCROLL,
			// Token: 0x0400018D RID: 397
			LSHIFT = 42,
			// Token: 0x0400018E RID: 398
			RSHIFT = 54,
			// Token: 0x0400018F RID: 399
			LCONTROL = 29,
			// Token: 0x04000190 RID: 400
			RCONTROL = 29,
			// Token: 0x04000191 RID: 401
			LMENU = 56,
			// Token: 0x04000192 RID: 402
			RMENU = 56,
			// Token: 0x04000193 RID: 403
			BROWSER_BACK = 106,
			// Token: 0x04000194 RID: 404
			BROWSER_FORWARD = 105,
			// Token: 0x04000195 RID: 405
			BROWSER_REFRESH = 103,
			// Token: 0x04000196 RID: 406
			BROWSER_STOP,
			// Token: 0x04000197 RID: 407
			BROWSER_SEARCH = 101,
			// Token: 0x04000198 RID: 408
			BROWSER_FAVORITES,
			// Token: 0x04000199 RID: 409
			BROWSER_HOME = 50,
			// Token: 0x0400019A RID: 410
			VOLUME_MUTE = 32,
			// Token: 0x0400019B RID: 411
			VOLUME_DOWN = 46,
			// Token: 0x0400019C RID: 412
			VOLUME_UP = 48,
			// Token: 0x0400019D RID: 413
			MEDIA_NEXT_TRACK = 25,
			// Token: 0x0400019E RID: 414
			MEDIA_PREV_TRACK = 16,
			// Token: 0x0400019F RID: 415
			MEDIA_STOP = 36,
			// Token: 0x040001A0 RID: 416
			MEDIA_PLAY_PAUSE = 34,
			// Token: 0x040001A1 RID: 417
			LAUNCH_MAIL = 108,
			// Token: 0x040001A2 RID: 418
			LAUNCH_MEDIA_SELECT,
			// Token: 0x040001A3 RID: 419
			LAUNCH_APP1 = 107,
			// Token: 0x040001A4 RID: 420
			LAUNCH_APP2 = 33,
			// Token: 0x040001A5 RID: 421
			OEM_1 = 39,
			// Token: 0x040001A6 RID: 422
			OEM_PLUS = 13,
			// Token: 0x040001A7 RID: 423
			OEM_COMMA = 51,
			// Token: 0x040001A8 RID: 424
			OEM_MINUS = 12,
			// Token: 0x040001A9 RID: 425
			OEM_PERIOD = 52,
			// Token: 0x040001AA RID: 426
			OEM_2,
			// Token: 0x040001AB RID: 427
			OEM_3 = 41,
			// Token: 0x040001AC RID: 428
			OEM_4 = 26,
			// Token: 0x040001AD RID: 429
			OEM_5 = 43,
			// Token: 0x040001AE RID: 430
			OEM_6 = 27,
			// Token: 0x040001AF RID: 431
			OEM_7 = 40,
			// Token: 0x040001B0 RID: 432
			OEM_8 = 0,
			// Token: 0x040001B1 RID: 433
			OEM_102 = 86,
			// Token: 0x040001B2 RID: 434
			PROCESSKEY = 0,
			// Token: 0x040001B3 RID: 435
			PACKET = 0,
			// Token: 0x040001B4 RID: 436
			ATTN = 0,
			// Token: 0x040001B5 RID: 437
			CRSEL = 0,
			// Token: 0x040001B6 RID: 438
			EXSEL = 0,
			// Token: 0x040001B7 RID: 439
			EREOF = 93,
			// Token: 0x040001B8 RID: 440
			PLAY = 0,
			// Token: 0x040001B9 RID: 441
			ZOOM = 98,
			// Token: 0x040001BA RID: 442
			NONAME = 0,
			// Token: 0x040001BB RID: 443
			PA1 = 0,
			// Token: 0x040001BC RID: 444
			OEM_CLEAR = 0
		}

		// Token: 0x0200003D RID: 61
		[Flags]
		public enum KEYEVENTF : uint
		{
			// Token: 0x040001BE RID: 446
			EXTENDEDKEY = 1U,
			// Token: 0x040001BF RID: 447
			KEYUP = 2U,
			// Token: 0x040001C0 RID: 448
			SCANCODE = 8U,
			// Token: 0x040001C1 RID: 449
			UNICODE = 4U
		}

		// Token: 0x0200003E RID: 62
		[Flags]
		public enum MOUSEEVENTF : uint
		{
			// Token: 0x040001C3 RID: 451
			ABSOLUTE = 32768U,
			// Token: 0x040001C4 RID: 452
			HWHEEL = 4096U,
			// Token: 0x040001C5 RID: 453
			MOVE = 1U,
			// Token: 0x040001C6 RID: 454
			MOVE_NOCOALESCE = 8192U,
			// Token: 0x040001C7 RID: 455
			LEFTDOWN = 2U,
			// Token: 0x040001C8 RID: 456
			LEFTUP = 4U,
			// Token: 0x040001C9 RID: 457
			RIGHTDOWN = 8U,
			// Token: 0x040001CA RID: 458
			RIGHTUP = 16U,
			// Token: 0x040001CB RID: 459
			MIDDLEDOWN = 32U,
			// Token: 0x040001CC RID: 460
			MIDDLEUP = 64U,
			// Token: 0x040001CD RID: 461
			VIRTUALDESK = 16384U,
			// Token: 0x040001CE RID: 462
			WHEEL = 2048U,
			// Token: 0x040001CF RID: 463
			XDOWN = 128U,
			// Token: 0x040001D0 RID: 464
			XUP = 256U
		}

		// Token: 0x0200003F RID: 63
		public enum VirtualKeys : ushort
		{
			// Token: 0x040001D2 RID: 466
			LeftButton = 1,
			// Token: 0x040001D3 RID: 467
			RightButton,
			// Token: 0x040001D4 RID: 468
			Cancel,
			// Token: 0x040001D5 RID: 469
			MiddleButton,
			// Token: 0x040001D6 RID: 470
			ExtraButton1,
			// Token: 0x040001D7 RID: 471
			ExtraButton2,
			// Token: 0x040001D8 RID: 472
			Back = 8,
			// Token: 0x040001D9 RID: 473
			Tab,
			// Token: 0x040001DA RID: 474
			Clear = 12,
			// Token: 0x040001DB RID: 475
			Return,
			// Token: 0x040001DC RID: 476
			Shift = 16,
			// Token: 0x040001DD RID: 477
			Control,
			// Token: 0x040001DE RID: 478
			Menu,
			// Token: 0x040001DF RID: 479
			Pause,
			// Token: 0x040001E0 RID: 480
			CapsLock,
			// Token: 0x040001E1 RID: 481
			Kana,
			// Token: 0x040001E2 RID: 482
			Hangeul = 21,
			// Token: 0x040001E3 RID: 483
			Hangul = 21,
			// Token: 0x040001E4 RID: 484
			Junja = 23,
			// Token: 0x040001E5 RID: 485
			Final,
			// Token: 0x040001E6 RID: 486
			Hanja,
			// Token: 0x040001E7 RID: 487
			Kanji = 25,
			// Token: 0x040001E8 RID: 488
			Escape = 27,
			// Token: 0x040001E9 RID: 489
			Convert,
			// Token: 0x040001EA RID: 490
			NonConvert,
			// Token: 0x040001EB RID: 491
			Accept,
			// Token: 0x040001EC RID: 492
			ModeChange,
			// Token: 0x040001ED RID: 493
			Space,
			// Token: 0x040001EE RID: 494
			Prior,
			// Token: 0x040001EF RID: 495
			Next,
			// Token: 0x040001F0 RID: 496
			End,
			// Token: 0x040001F1 RID: 497
			Home,
			// Token: 0x040001F2 RID: 498
			Left,
			// Token: 0x040001F3 RID: 499
			Up,
			// Token: 0x040001F4 RID: 500
			Right,
			// Token: 0x040001F5 RID: 501
			Down,
			// Token: 0x040001F6 RID: 502
			Select,
			// Token: 0x040001F7 RID: 503
			Print,
			// Token: 0x040001F8 RID: 504
			Execute,
			// Token: 0x040001F9 RID: 505
			Snapshot,
			// Token: 0x040001FA RID: 506
			Insert,
			// Token: 0x040001FB RID: 507
			Delete,
			// Token: 0x040001FC RID: 508
			Help,
			// Token: 0x040001FD RID: 509
			N0,
			// Token: 0x040001FE RID: 510
			N1,
			// Token: 0x040001FF RID: 511
			N2,
			// Token: 0x04000200 RID: 512
			N3,
			// Token: 0x04000201 RID: 513
			N4,
			// Token: 0x04000202 RID: 514
			N5,
			// Token: 0x04000203 RID: 515
			N6,
			// Token: 0x04000204 RID: 516
			N7,
			// Token: 0x04000205 RID: 517
			N8,
			// Token: 0x04000206 RID: 518
			N9,
			// Token: 0x04000207 RID: 519
			A = 65,
			// Token: 0x04000208 RID: 520
			B,
			// Token: 0x04000209 RID: 521
			C,
			// Token: 0x0400020A RID: 522
			D,
			// Token: 0x0400020B RID: 523
			E,
			// Token: 0x0400020C RID: 524
			F,
			// Token: 0x0400020D RID: 525
			G,
			// Token: 0x0400020E RID: 526
			H,
			// Token: 0x0400020F RID: 527
			I,
			// Token: 0x04000210 RID: 528
			J,
			// Token: 0x04000211 RID: 529
			K,
			// Token: 0x04000212 RID: 530
			L,
			// Token: 0x04000213 RID: 531
			M,
			// Token: 0x04000214 RID: 532
			N,
			// Token: 0x04000215 RID: 533
			O,
			// Token: 0x04000216 RID: 534
			P,
			// Token: 0x04000217 RID: 535
			Q,
			// Token: 0x04000218 RID: 536
			R,
			// Token: 0x04000219 RID: 537
			S,
			// Token: 0x0400021A RID: 538
			T,
			// Token: 0x0400021B RID: 539
			U,
			// Token: 0x0400021C RID: 540
			V,
			// Token: 0x0400021D RID: 541
			W,
			// Token: 0x0400021E RID: 542
			X,
			// Token: 0x0400021F RID: 543
			Y,
			// Token: 0x04000220 RID: 544
			Z,
			// Token: 0x04000221 RID: 545
			LeftWindows,
			// Token: 0x04000222 RID: 546
			RightWindows,
			// Token: 0x04000223 RID: 547
			Application,
			// Token: 0x04000224 RID: 548
			Sleep = 95,
			// Token: 0x04000225 RID: 549
			Numpad0,
			// Token: 0x04000226 RID: 550
			Numpad1,
			// Token: 0x04000227 RID: 551
			Numpad2,
			// Token: 0x04000228 RID: 552
			Numpad3,
			// Token: 0x04000229 RID: 553
			Numpad4,
			// Token: 0x0400022A RID: 554
			Numpad5,
			// Token: 0x0400022B RID: 555
			Numpad6,
			// Token: 0x0400022C RID: 556
			Numpad7,
			// Token: 0x0400022D RID: 557
			Numpad8,
			// Token: 0x0400022E RID: 558
			Numpad9,
			// Token: 0x0400022F RID: 559
			Multiply,
			// Token: 0x04000230 RID: 560
			Add,
			// Token: 0x04000231 RID: 561
			Separator,
			// Token: 0x04000232 RID: 562
			Subtract,
			// Token: 0x04000233 RID: 563
			Decimal,
			// Token: 0x04000234 RID: 564
			Divide,
			// Token: 0x04000235 RID: 565
			F1,
			// Token: 0x04000236 RID: 566
			F2,
			// Token: 0x04000237 RID: 567
			F3,
			// Token: 0x04000238 RID: 568
			F4,
			// Token: 0x04000239 RID: 569
			F5,
			// Token: 0x0400023A RID: 570
			F6,
			// Token: 0x0400023B RID: 571
			F7,
			// Token: 0x0400023C RID: 572
			F8,
			// Token: 0x0400023D RID: 573
			F9,
			// Token: 0x0400023E RID: 574
			F10,
			// Token: 0x0400023F RID: 575
			F11,
			// Token: 0x04000240 RID: 576
			F12,
			// Token: 0x04000241 RID: 577
			F13,
			// Token: 0x04000242 RID: 578
			F14,
			// Token: 0x04000243 RID: 579
			F15,
			// Token: 0x04000244 RID: 580
			F16,
			// Token: 0x04000245 RID: 581
			F17,
			// Token: 0x04000246 RID: 582
			F18,
			// Token: 0x04000247 RID: 583
			F19,
			// Token: 0x04000248 RID: 584
			F20,
			// Token: 0x04000249 RID: 585
			F21,
			// Token: 0x0400024A RID: 586
			F22,
			// Token: 0x0400024B RID: 587
			F23,
			// Token: 0x0400024C RID: 588
			F24,
			// Token: 0x0400024D RID: 589
			NumLock = 144,
			// Token: 0x0400024E RID: 590
			ScrollLock,
			// Token: 0x0400024F RID: 591
			NEC_Equal,
			// Token: 0x04000250 RID: 592
			Fujitsu_Jisho = 146,
			// Token: 0x04000251 RID: 593
			Fujitsu_Masshou,
			// Token: 0x04000252 RID: 594
			Fujitsu_Touroku,
			// Token: 0x04000253 RID: 595
			Fujitsu_Loya,
			// Token: 0x04000254 RID: 596
			Fujitsu_Roya,
			// Token: 0x04000255 RID: 597
			LeftShift = 160,
			// Token: 0x04000256 RID: 598
			RightShift,
			// Token: 0x04000257 RID: 599
			LeftControl,
			// Token: 0x04000258 RID: 600
			RightControl,
			// Token: 0x04000259 RID: 601
			LeftMenu,
			// Token: 0x0400025A RID: 602
			RightMenu,
			// Token: 0x0400025B RID: 603
			BrowserBack,
			// Token: 0x0400025C RID: 604
			BrowserForward,
			// Token: 0x0400025D RID: 605
			BrowserRefresh,
			// Token: 0x0400025E RID: 606
			BrowserStop,
			// Token: 0x0400025F RID: 607
			BrowserSearch,
			// Token: 0x04000260 RID: 608
			BrowserFavorites,
			// Token: 0x04000261 RID: 609
			BrowserHome,
			// Token: 0x04000262 RID: 610
			VolumeMute,
			// Token: 0x04000263 RID: 611
			VolumeDown,
			// Token: 0x04000264 RID: 612
			VolumeUp,
			// Token: 0x04000265 RID: 613
			MediaNextTrack,
			// Token: 0x04000266 RID: 614
			MediaPrevTrack,
			// Token: 0x04000267 RID: 615
			MediaStop,
			// Token: 0x04000268 RID: 616
			MediaPlayPause,
			// Token: 0x04000269 RID: 617
			LaunchMail,
			// Token: 0x0400026A RID: 618
			LaunchMediaSelect,
			// Token: 0x0400026B RID: 619
			LaunchApplication1,
			// Token: 0x0400026C RID: 620
			LaunchApplication2,
			// Token: 0x0400026D RID: 621
			OEM1 = 186,
			// Token: 0x0400026E RID: 622
			OEMPlus,
			// Token: 0x0400026F RID: 623
			OEMComma,
			// Token: 0x04000270 RID: 624
			OEMMinus,
			// Token: 0x04000271 RID: 625
			OEMPeriod,
			// Token: 0x04000272 RID: 626
			OEM2,
			// Token: 0x04000273 RID: 627
			OEM3,
			// Token: 0x04000274 RID: 628
			OEM4 = 219,
			// Token: 0x04000275 RID: 629
			OEM5,
			// Token: 0x04000276 RID: 630
			OEM6,
			// Token: 0x04000277 RID: 631
			OEM7,
			// Token: 0x04000278 RID: 632
			OEM8,
			// Token: 0x04000279 RID: 633
			OEMAX = 225,
			// Token: 0x0400027A RID: 634
			OEM102,
			// Token: 0x0400027B RID: 635
			ICOHelp,
			// Token: 0x0400027C RID: 636
			ICO00,
			// Token: 0x0400027D RID: 637
			ProcessKey,
			// Token: 0x0400027E RID: 638
			ICOClear,
			// Token: 0x0400027F RID: 639
			Packet,
			// Token: 0x04000280 RID: 640
			OEMReset = 233,
			// Token: 0x04000281 RID: 641
			OEMJump,
			// Token: 0x04000282 RID: 642
			OEMPA1,
			// Token: 0x04000283 RID: 643
			OEMPA2,
			// Token: 0x04000284 RID: 644
			OEMPA3,
			// Token: 0x04000285 RID: 645
			OEMWSCtrl,
			// Token: 0x04000286 RID: 646
			OEMCUSel,
			// Token: 0x04000287 RID: 647
			OEMATTN,
			// Token: 0x04000288 RID: 648
			OEMFinish,
			// Token: 0x04000289 RID: 649
			OEMCopy,
			// Token: 0x0400028A RID: 650
			OEMAuto,
			// Token: 0x0400028B RID: 651
			OEMENLW,
			// Token: 0x0400028C RID: 652
			OEMBackTab,
			// Token: 0x0400028D RID: 653
			ATTN,
			// Token: 0x0400028E RID: 654
			CRSel,
			// Token: 0x0400028F RID: 655
			EXSel,
			// Token: 0x04000290 RID: 656
			EREOF,
			// Token: 0x04000291 RID: 657
			Play,
			// Token: 0x04000292 RID: 658
			Zoom,
			// Token: 0x04000293 RID: 659
			Noname,
			// Token: 0x04000294 RID: 660
			PA1,
			// Token: 0x04000295 RID: 661
			OEMClear
		}

		// Token: 0x02000040 RID: 64
		public enum VK
		{
			// Token: 0x04000297 RID: 663
			LBUTTON = 1,
			// Token: 0x04000298 RID: 664
			RBUTTON,
			// Token: 0x04000299 RID: 665
			CANCEL,
			// Token: 0x0400029A RID: 666
			MBUTTON,
			// Token: 0x0400029B RID: 667
			XBUTTON1,
			// Token: 0x0400029C RID: 668
			XBUTTON2,
			// Token: 0x0400029D RID: 669
			BACK = 8,
			// Token: 0x0400029E RID: 670
			TAB,
			// Token: 0x0400029F RID: 671
			CLEAR = 12,
			// Token: 0x040002A0 RID: 672
			RETURN,
			// Token: 0x040002A1 RID: 673
			SHIFT = 16,
			// Token: 0x040002A2 RID: 674
			CONTROL,
			// Token: 0x040002A3 RID: 675
			MENU,
			// Token: 0x040002A4 RID: 676
			PAUSE,
			// Token: 0x040002A5 RID: 677
			CAPITAL,
			// Token: 0x040002A6 RID: 678
			KANA,
			// Token: 0x040002A7 RID: 679
			HANGUL = 21,
			// Token: 0x040002A8 RID: 680
			JUNJA = 23,
			// Token: 0x040002A9 RID: 681
			FINAL,
			// Token: 0x040002AA RID: 682
			HANJA,
			// Token: 0x040002AB RID: 683
			KANJI = 25,
			// Token: 0x040002AC RID: 684
			ESCAPE = 27,
			// Token: 0x040002AD RID: 685
			CONVERT,
			// Token: 0x040002AE RID: 686
			NONCONVERT,
			// Token: 0x040002AF RID: 687
			ACCEPT,
			// Token: 0x040002B0 RID: 688
			MODECHANGE,
			// Token: 0x040002B1 RID: 689
			SPACE,
			// Token: 0x040002B2 RID: 690
			PRIOR,
			// Token: 0x040002B3 RID: 691
			NEXT,
			// Token: 0x040002B4 RID: 692
			END,
			// Token: 0x040002B5 RID: 693
			HOME,
			// Token: 0x040002B6 RID: 694
			LEFT,
			// Token: 0x040002B7 RID: 695
			UP,
			// Token: 0x040002B8 RID: 696
			RIGHT,
			// Token: 0x040002B9 RID: 697
			DOWN,
			// Token: 0x040002BA RID: 698
			SELECT,
			// Token: 0x040002BB RID: 699
			PRINT,
			// Token: 0x040002BC RID: 700
			EXECUTE,
			// Token: 0x040002BD RID: 701
			SNAPSHOT,
			// Token: 0x040002BE RID: 702
			INSERT,
			// Token: 0x040002BF RID: 703
			DELETE,
			// Token: 0x040002C0 RID: 704
			HELP,
			// Token: 0x040002C1 RID: 705
			KEY_0,
			// Token: 0x040002C2 RID: 706
			KEY_1,
			// Token: 0x040002C3 RID: 707
			KEY_2,
			// Token: 0x040002C4 RID: 708
			KEY_3,
			// Token: 0x040002C5 RID: 709
			KEY_4,
			// Token: 0x040002C6 RID: 710
			KEY_5,
			// Token: 0x040002C7 RID: 711
			KEY_6,
			// Token: 0x040002C8 RID: 712
			KEY_7,
			// Token: 0x040002C9 RID: 713
			KEY_8,
			// Token: 0x040002CA RID: 714
			KEY_9,
			// Token: 0x040002CB RID: 715
			KEY_A = 65,
			// Token: 0x040002CC RID: 716
			KEY_B,
			// Token: 0x040002CD RID: 717
			KEY_C,
			// Token: 0x040002CE RID: 718
			KEY_D,
			// Token: 0x040002CF RID: 719
			KEY_E,
			// Token: 0x040002D0 RID: 720
			KEY_F,
			// Token: 0x040002D1 RID: 721
			KEY_G,
			// Token: 0x040002D2 RID: 722
			KEY_H,
			// Token: 0x040002D3 RID: 723
			KEY_I,
			// Token: 0x040002D4 RID: 724
			KEY_J,
			// Token: 0x040002D5 RID: 725
			KEY_K,
			// Token: 0x040002D6 RID: 726
			KEY_L,
			// Token: 0x040002D7 RID: 727
			KEY_M,
			// Token: 0x040002D8 RID: 728
			KEY_N,
			// Token: 0x040002D9 RID: 729
			KEY_O,
			// Token: 0x040002DA RID: 730
			KEY_P,
			// Token: 0x040002DB RID: 731
			KEY_Q,
			// Token: 0x040002DC RID: 732
			KEY_R,
			// Token: 0x040002DD RID: 733
			KEY_S,
			// Token: 0x040002DE RID: 734
			KEY_T,
			// Token: 0x040002DF RID: 735
			KEY_U,
			// Token: 0x040002E0 RID: 736
			KEY_V,
			// Token: 0x040002E1 RID: 737
			KEY_W,
			// Token: 0x040002E2 RID: 738
			KEY_X,
			// Token: 0x040002E3 RID: 739
			KEY_Y,
			// Token: 0x040002E4 RID: 740
			KEY_Z,
			// Token: 0x040002E5 RID: 741
			LWIN,
			// Token: 0x040002E6 RID: 742
			RWIN,
			// Token: 0x040002E7 RID: 743
			APPS,
			// Token: 0x040002E8 RID: 744
			SLEEP = 95,
			// Token: 0x040002E9 RID: 745
			NUMPAD0,
			// Token: 0x040002EA RID: 746
			NUMPAD1,
			// Token: 0x040002EB RID: 747
			NUMPAD2,
			// Token: 0x040002EC RID: 748
			NUMPAD3,
			// Token: 0x040002ED RID: 749
			NUMPAD4,
			// Token: 0x040002EE RID: 750
			NUMPAD5,
			// Token: 0x040002EF RID: 751
			NUMPAD6,
			// Token: 0x040002F0 RID: 752
			NUMPAD7,
			// Token: 0x040002F1 RID: 753
			NUMPAD8,
			// Token: 0x040002F2 RID: 754
			NUMPAD9,
			// Token: 0x040002F3 RID: 755
			MULTIPLY,
			// Token: 0x040002F4 RID: 756
			ADD,
			// Token: 0x040002F5 RID: 757
			SEPARATOR,
			// Token: 0x040002F6 RID: 758
			SUBTRACT,
			// Token: 0x040002F7 RID: 759
			DECIMAL,
			// Token: 0x040002F8 RID: 760
			DIVIDE,
			// Token: 0x040002F9 RID: 761
			F1,
			// Token: 0x040002FA RID: 762
			F2,
			// Token: 0x040002FB RID: 763
			F3,
			// Token: 0x040002FC RID: 764
			F4,
			// Token: 0x040002FD RID: 765
			F5,
			// Token: 0x040002FE RID: 766
			F6,
			// Token: 0x040002FF RID: 767
			F7,
			// Token: 0x04000300 RID: 768
			F8,
			// Token: 0x04000301 RID: 769
			F9,
			// Token: 0x04000302 RID: 770
			F10,
			// Token: 0x04000303 RID: 771
			F11,
			// Token: 0x04000304 RID: 772
			F12,
			// Token: 0x04000305 RID: 773
			F13,
			// Token: 0x04000306 RID: 774
			F14,
			// Token: 0x04000307 RID: 775
			F15,
			// Token: 0x04000308 RID: 776
			F16,
			// Token: 0x04000309 RID: 777
			F17,
			// Token: 0x0400030A RID: 778
			F18,
			// Token: 0x0400030B RID: 779
			F19,
			// Token: 0x0400030C RID: 780
			F20,
			// Token: 0x0400030D RID: 781
			F21,
			// Token: 0x0400030E RID: 782
			F22,
			// Token: 0x0400030F RID: 783
			F23,
			// Token: 0x04000310 RID: 784
			F24,
			// Token: 0x04000311 RID: 785
			NUMLOCK = 144,
			// Token: 0x04000312 RID: 786
			SCROLL,
			// Token: 0x04000313 RID: 787
			LSHIFT = 160,
			// Token: 0x04000314 RID: 788
			RSHIFT,
			// Token: 0x04000315 RID: 789
			LCONTROL,
			// Token: 0x04000316 RID: 790
			RCONTROL,
			// Token: 0x04000317 RID: 791
			LMENU,
			// Token: 0x04000318 RID: 792
			RMENU,
			// Token: 0x04000319 RID: 793
			BROWSER_BACK,
			// Token: 0x0400031A RID: 794
			BROWSER_FORWARD,
			// Token: 0x0400031B RID: 795
			BROWSER_REFRESH,
			// Token: 0x0400031C RID: 796
			BROWSER_STOP,
			// Token: 0x0400031D RID: 797
			BROWSER_SEARCH,
			// Token: 0x0400031E RID: 798
			BROWSER_FAVORITES,
			// Token: 0x0400031F RID: 799
			BROWSER_HOME,
			// Token: 0x04000320 RID: 800
			VOLUME_MUTE,
			// Token: 0x04000321 RID: 801
			VOLUME_DOWN,
			// Token: 0x04000322 RID: 802
			VOLUME_UP,
			// Token: 0x04000323 RID: 803
			MEDIA_NEXT_TRACK,
			// Token: 0x04000324 RID: 804
			MEDIA_PREV_TRACK,
			// Token: 0x04000325 RID: 805
			MEDIA_STOP,
			// Token: 0x04000326 RID: 806
			MEDIA_PLAY_PAUSE,
			// Token: 0x04000327 RID: 807
			LAUNCH_MAIL,
			// Token: 0x04000328 RID: 808
			LAUNCH_MEDIA_SELECT,
			// Token: 0x04000329 RID: 809
			LAUNCH_APP1,
			// Token: 0x0400032A RID: 810
			LAUNCH_APP2,
			// Token: 0x0400032B RID: 811
			OEM_1 = 186,
			// Token: 0x0400032C RID: 812
			OEM_PLUS,
			// Token: 0x0400032D RID: 813
			OEM_COMMA,
			// Token: 0x0400032E RID: 814
			OEM_MINUS,
			// Token: 0x0400032F RID: 815
			OEM_PERIOD,
			// Token: 0x04000330 RID: 816
			OEM_2,
			// Token: 0x04000331 RID: 817
			OEM_3,
			// Token: 0x04000332 RID: 818
			OEM_4 = 219,
			// Token: 0x04000333 RID: 819
			OEM_5,
			// Token: 0x04000334 RID: 820
			OEM_6,
			// Token: 0x04000335 RID: 821
			OEM_7,
			// Token: 0x04000336 RID: 822
			OEM_8,
			// Token: 0x04000337 RID: 823
			OEM_102 = 226,
			// Token: 0x04000338 RID: 824
			PROCESSKEY = 229,
			// Token: 0x04000339 RID: 825
			PACKET = 231,
			// Token: 0x0400033A RID: 826
			ATTN = 246,
			// Token: 0x0400033B RID: 827
			CRSEL,
			// Token: 0x0400033C RID: 828
			EXSEL,
			// Token: 0x0400033D RID: 829
			EREOF,
			// Token: 0x0400033E RID: 830
			PLAY,
			// Token: 0x0400033F RID: 831
			ZOOM,
			// Token: 0x04000340 RID: 832
			NONAME,
			// Token: 0x04000341 RID: 833
			PA1,
			// Token: 0x04000342 RID: 834
			OEM_CLEAR
		}

		// Token: 0x02000041 RID: 65
		public enum SystemMetric
		{
			// Token: 0x04000344 RID: 836
			SM_CXSCREEN,
			// Token: 0x04000345 RID: 837
			SM_CYSCREEN,
			// Token: 0x04000346 RID: 838
			SM_CXVSCROLL,
			// Token: 0x04000347 RID: 839
			SM_CYHSCROLL,
			// Token: 0x04000348 RID: 840
			SM_CYCAPTION,
			// Token: 0x04000349 RID: 841
			SM_CXBORDER,
			// Token: 0x0400034A RID: 842
			SM_CYBORDER,
			// Token: 0x0400034B RID: 843
			SM_CXDLGFRAME,
			// Token: 0x0400034C RID: 844
			SM_CXFIXEDFRAME = 7,
			// Token: 0x0400034D RID: 845
			SM_CYDLGFRAME,
			// Token: 0x0400034E RID: 846
			SM_CYFIXEDFRAME = 8,
			// Token: 0x0400034F RID: 847
			SM_CYVTHUMB,
			// Token: 0x04000350 RID: 848
			SM_CXHTHUMB,
			// Token: 0x04000351 RID: 849
			SM_CXICON,
			// Token: 0x04000352 RID: 850
			SM_CYICON,
			// Token: 0x04000353 RID: 851
			SM_CXCURSOR,
			// Token: 0x04000354 RID: 852
			SM_CYCURSOR,
			// Token: 0x04000355 RID: 853
			SM_CYMENU,
			// Token: 0x04000356 RID: 854
			SM_CXFULLSCREEN,
			// Token: 0x04000357 RID: 855
			SM_CYFULLSCREEN,
			// Token: 0x04000358 RID: 856
			SM_CYKANJIWINDOW,
			// Token: 0x04000359 RID: 857
			SM_MOUSEPRESENT,
			// Token: 0x0400035A RID: 858
			SM_CYVSCROLL,
			// Token: 0x0400035B RID: 859
			SM_CXHSCROLL,
			// Token: 0x0400035C RID: 860
			SM_DEBUG,
			// Token: 0x0400035D RID: 861
			SM_SWAPBUTTON,
			// Token: 0x0400035E RID: 862
			SM_CXMIN = 28,
			// Token: 0x0400035F RID: 863
			SM_CYMIN,
			// Token: 0x04000360 RID: 864
			SM_CXSIZE,
			// Token: 0x04000361 RID: 865
			SM_CYSIZE,
			// Token: 0x04000362 RID: 866
			SM_CXSIZEFRAME,
			// Token: 0x04000363 RID: 867
			SM_CXFRAME = 32,
			// Token: 0x04000364 RID: 868
			SM_CYSIZEFRAME,
			// Token: 0x04000365 RID: 869
			SM_CYFRAME = 33,
			// Token: 0x04000366 RID: 870
			SM_CXMINTRACK,
			// Token: 0x04000367 RID: 871
			SM_CYMINTRACK,
			// Token: 0x04000368 RID: 872
			SM_CXDOUBLECLK,
			// Token: 0x04000369 RID: 873
			SM_CYDOUBLECLK,
			// Token: 0x0400036A RID: 874
			SM_CXICONSPACING,
			// Token: 0x0400036B RID: 875
			SM_CYICONSPACING,
			// Token: 0x0400036C RID: 876
			SM_MENUDROPALIGNMENT,
			// Token: 0x0400036D RID: 877
			SM_PENWINDOWS,
			// Token: 0x0400036E RID: 878
			SM_DBCSENABLED,
			// Token: 0x0400036F RID: 879
			SM_CMOUSEBUTTONS,
			// Token: 0x04000370 RID: 880
			SM_SECURE,
			// Token: 0x04000371 RID: 881
			SM_CXEDGE,
			// Token: 0x04000372 RID: 882
			SM_CYEDGE,
			// Token: 0x04000373 RID: 883
			SM_CXMINSPACING,
			// Token: 0x04000374 RID: 884
			SM_CYMINSPACING,
			// Token: 0x04000375 RID: 885
			SM_CXSMICON,
			// Token: 0x04000376 RID: 886
			SM_CYSMICON,
			// Token: 0x04000377 RID: 887
			SM_CYSMCAPTION,
			// Token: 0x04000378 RID: 888
			SM_CXSMSIZE,
			// Token: 0x04000379 RID: 889
			SM_CYSMSIZE,
			// Token: 0x0400037A RID: 890
			SM_CXMENUSIZE,
			// Token: 0x0400037B RID: 891
			SM_CYMENUSIZE,
			// Token: 0x0400037C RID: 892
			SM_ARRANGE,
			// Token: 0x0400037D RID: 893
			SM_CXMINIMIZED,
			// Token: 0x0400037E RID: 894
			SM_CYMINIMIZED,
			// Token: 0x0400037F RID: 895
			SM_CXMAXTRACK,
			// Token: 0x04000380 RID: 896
			SM_CYMAXTRACK,
			// Token: 0x04000381 RID: 897
			SM_CXMAXIMIZED,
			// Token: 0x04000382 RID: 898
			SM_CYMAXIMIZED,
			// Token: 0x04000383 RID: 899
			SM_NETWORK,
			// Token: 0x04000384 RID: 900
			SM_CLEANBOOT = 67,
			// Token: 0x04000385 RID: 901
			SM_CXDRAG,
			// Token: 0x04000386 RID: 902
			SM_CYDRAG,
			// Token: 0x04000387 RID: 903
			SM_SHOWSOUNDS,
			// Token: 0x04000388 RID: 904
			SM_CXMENUCHECK,
			// Token: 0x04000389 RID: 905
			SM_CYMENUCHECK,
			// Token: 0x0400038A RID: 906
			SM_SLOWMACHINE,
			// Token: 0x0400038B RID: 907
			SM_MIDEASTENABLED,
			// Token: 0x0400038C RID: 908
			SM_MOUSEWHEELPRESENT,
			// Token: 0x0400038D RID: 909
			SM_XVIRTUALSCREEN,
			// Token: 0x0400038E RID: 910
			SM_YVIRTUALSCREEN,
			// Token: 0x0400038F RID: 911
			SM_CXVIRTUALSCREEN,
			// Token: 0x04000390 RID: 912
			SM_CYVIRTUALSCREEN,
			// Token: 0x04000391 RID: 913
			SM_CMONITORS,
			// Token: 0x04000392 RID: 914
			SM_SAMEDISPLAYFORMAT,
			// Token: 0x04000393 RID: 915
			SM_IMMENABLED,
			// Token: 0x04000394 RID: 916
			SM_CXFOCUSBORDER,
			// Token: 0x04000395 RID: 917
			SM_CYFOCUSBORDER,
			// Token: 0x04000396 RID: 918
			SM_TABLETPC = 86,
			// Token: 0x04000397 RID: 919
			SM_MEDIACENTER,
			// Token: 0x04000398 RID: 920
			SM_STARTER,
			// Token: 0x04000399 RID: 921
			SM_SERVERR2,
			// Token: 0x0400039A RID: 922
			SM_MOUSEHORIZONTALWHEELPRESENT = 91,
			// Token: 0x0400039B RID: 923
			SM_CXPADDEDBORDER,
			// Token: 0x0400039C RID: 924
			SM_DIGITIZER = 94,
			// Token: 0x0400039D RID: 925
			SM_MAXIMUMTOUCHES,
			// Token: 0x0400039E RID: 926
			SM_REMOTESESSION = 4096,
			// Token: 0x0400039F RID: 927
			SM_SHUTTINGDOWN = 8192,
			// Token: 0x040003A0 RID: 928
			SM_REMOTECONTROL,
			// Token: 0x040003A1 RID: 929
			SM_CONVERTIBLESLATEMODE = 8195,
			// Token: 0x040003A2 RID: 930
			SM_SYSTEMDOCKED
		}

		// Token: 0x02000042 RID: 66
		public struct RECT
		{
			// Token: 0x0600012B RID: 299 RVA: 0x00006369 File Offset: 0x00004569
			public RECT(int Left, int Top, int Right, int Bottom)
			{
				this.left = Left;
				this.top = Top;
				this.right = Right;
				this.bottom = Bottom;
			}

			// Token: 0x0600012C RID: 300 RVA: 0x00006389 File Offset: 0x00004589
			public RECT(Rectangle r)
			{
				this = new DllImports.RECT(r.Left, r.Top, r.Right, r.Bottom);
			}

			// Token: 0x17000006 RID: 6
			// (get) Token: 0x0600012D RID: 301 RVA: 0x000063B0 File Offset: 0x000045B0
			// (set) Token: 0x0600012E RID: 302 RVA: 0x000063C8 File Offset: 0x000045C8
			public int X
			{
				get
				{
					return this.left;
				}
				set
				{
					this.right -= this.left - value;
					this.left = value;
				}
			}

			// Token: 0x17000007 RID: 7
			// (get) Token: 0x0600012F RID: 303 RVA: 0x000063E8 File Offset: 0x000045E8
			// (set) Token: 0x06000130 RID: 304 RVA: 0x00006400 File Offset: 0x00004600
			public int Y
			{
				get
				{
					return this.top;
				}
				set
				{
					this.bottom -= this.top - value;
					this.top = value;
				}
			}

			// Token: 0x17000008 RID: 8
			// (get) Token: 0x06000131 RID: 305 RVA: 0x00006420 File Offset: 0x00004620
			// (set) Token: 0x06000132 RID: 306 RVA: 0x0000643F File Offset: 0x0000463F
			public int Height
			{
				get
				{
					return this.bottom - this.top;
				}
				set
				{
					this.bottom = value + this.top;
				}
			}

			// Token: 0x17000009 RID: 9
			// (get) Token: 0x06000133 RID: 307 RVA: 0x00006450 File Offset: 0x00004650
			// (set) Token: 0x06000134 RID: 308 RVA: 0x0000646F File Offset: 0x0000466F
			public int Width
			{
				get
				{
					return this.right - this.left;
				}
				set
				{
					this.right = value + this.left;
				}
			}

			// Token: 0x1700000A RID: 10
			// (get) Token: 0x06000135 RID: 309 RVA: 0x00006480 File Offset: 0x00004680
			// (set) Token: 0x06000136 RID: 310 RVA: 0x000064A3 File Offset: 0x000046A3
			public Point Location
			{
				get
				{
					return new Point(this.left, this.top);
				}
				set
				{
					this.X = value.X;
					this.Y = value.Y;
				}
			}

			// Token: 0x1700000B RID: 11
			// (get) Token: 0x06000137 RID: 311 RVA: 0x000064C4 File Offset: 0x000046C4
			// (set) Token: 0x06000138 RID: 312 RVA: 0x000064E7 File Offset: 0x000046E7
			public Size Size
			{
				get
				{
					return new Size(this.Width, this.Height);
				}
				set
				{
					this.Width = value.Width;
					this.Height = value.Height;
				}
			}

			// Token: 0x06000139 RID: 313 RVA: 0x00006508 File Offset: 0x00004708
			public static implicit operator Rectangle(DllImports.RECT r)
			{
				return new Rectangle(r.left, r.top, r.Width, r.Height);
			}

			// Token: 0x0600013A RID: 314 RVA: 0x0000653C File Offset: 0x0000473C
			public static implicit operator DllImports.RECT(Rectangle r)
			{
				return new DllImports.RECT(r);
			}

			// Token: 0x0600013B RID: 315 RVA: 0x00006554 File Offset: 0x00004754
			public static bool operator ==(DllImports.RECT r1, DllImports.RECT r2)
			{
				return r1.Equals(r2);
			}

			// Token: 0x0600013C RID: 316 RVA: 0x00006570 File Offset: 0x00004770
			public static bool operator !=(DllImports.RECT r1, DllImports.RECT r2)
			{
				return !r1.Equals(r2);
			}

			// Token: 0x0600013D RID: 317 RVA: 0x00006590 File Offset: 0x00004790
			public bool Equals(DllImports.RECT r)
			{
				return r.left == this.left && r.top == this.top && r.right == this.right && r.bottom == this.bottom;
			}

			// Token: 0x0600013E RID: 318 RVA: 0x000065E0 File Offset: 0x000047E0
			public override bool Equals(object obj)
			{
				bool flag = obj is DllImports.RECT;
				bool result;
				if (flag)
				{
					result = this.Equals((DllImports.RECT)obj);
				}
				else
				{
					bool flag2 = obj is Rectangle;
					result = (flag2 && this.Equals(new DllImports.RECT((Rectangle)obj)));
				}
				return result;
			}

			// Token: 0x0600013F RID: 319 RVA: 0x00006630 File Offset: 0x00004830
			public override int GetHashCode()
			{
				return this.GetHashCode();
			}

			// Token: 0x040003A3 RID: 931
			public int left;

			// Token: 0x040003A4 RID: 932
			public int top;

			// Token: 0x040003A5 RID: 933
			public int right;

			// Token: 0x040003A6 RID: 934
			public int bottom;
		}

		// Token: 0x02000043 RID: 67
		public struct SimpleRECT
		{
			// Token: 0x040003A7 RID: 935
			public int left;

			// Token: 0x040003A8 RID: 936
			public int top;

			// Token: 0x040003A9 RID: 937
			public int right;

			// Token: 0x040003AA RID: 938
			public int bottom;
		}

		// Token: 0x02000044 RID: 68
		public struct POINT
		{
			// Token: 0x040003AB RID: 939
			public int x;

			// Token: 0x040003AC RID: 940
			public int y;
		}

		// Token: 0x02000045 RID: 69
		public struct MINMAXINFO
		{
			// Token: 0x040003AD RID: 941
			public DllImports.POINT ptReserved;

			// Token: 0x040003AE RID: 942
			public DllImports.POINT ptMaxSize;

			// Token: 0x040003AF RID: 943
			public DllImports.POINT ptMaxPosition;

			// Token: 0x040003B0 RID: 944
			public DllImports.POINT ptMinTrackSize;

			// Token: 0x040003B1 RID: 945
			public DllImports.POINT ptMaxTrackSize;
		}

		// Token: 0x02000046 RID: 70
		[StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)]
		public class MONITORINFO
		{
			// Token: 0x040003B2 RID: 946
			public int cbSize = Marshal.SizeOf(typeof(DllImports.MONITORINFO));

			// Token: 0x040003B3 RID: 947
			public DllImports.RECT rcMonitor = default(DllImports.RECT);

			// Token: 0x040003B4 RID: 948
			public DllImports.RECT rcWork = default(DllImports.RECT);

			// Token: 0x040003B5 RID: 949
			public int dwFlags = 0;
		}
	}
}
