using System;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Text;

namespace Eternium_mcpe_client.DllImports
{
	// Token: 0x02000015 RID: 21
	public static class Imports
	{
		// Token: 0x060000BF RID: 191
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

		// Token: 0x060000C0 RID: 192
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool AddConsoleAlias(string Source, string Target, string ExeName);

		// Token: 0x060000C1 RID: 193
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool AllocConsole();

		// Token: 0x060000C2 RID: 194
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool AttachConsole(uint dwProcessId);

		// Token: 0x060000C3 RID: 195
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr CreateConsoleScreenBuffer(uint dwDesiredAccess, uint dwShareMode, IntPtr lpSecurityAttributes, uint dwFlags, IntPtr lpScreenBufferData);

		// Token: 0x060000C4 RID: 196
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool FillConsoleOutputAttribute(IntPtr hConsoleOutput, ushort wAttribute, uint nLength, Imports.COORD dwWriteCoord, out uint lpNumberOfAttrsWritten);

		// Token: 0x060000C5 RID: 197
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool FillConsoleOutputCharacter(IntPtr hConsoleOutput, char cCharacter, uint nLength, Imports.COORD dwWriteCoord, out uint lpNumberOfCharsWritten);

		// Token: 0x060000C6 RID: 198
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool FlushConsoleInputBuffer(IntPtr hConsoleInput);

		// Token: 0x060000C7 RID: 199
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		public static extern bool FreeConsole();

		// Token: 0x060000C8 RID: 200
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GenerateConsoleCtrlEvent(uint dwCtrlEvent, uint dwProcessGroupId);

		// Token: 0x060000C9 RID: 201
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool GetConsoleAlias(string Source, out StringBuilder TargetBuffer, uint TargetBufferLength, string ExeName);

		// Token: 0x060000CA RID: 202
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleAliases(StringBuilder[] lpTargetBuffer, uint targetBufferLength, string lpExeName);

		// Token: 0x060000CB RID: 203
		[DllImport("kernel32", SetLastError = true)]
		public static extern uint GetConsoleAliasesLength(string ExeName);

		// Token: 0x060000CC RID: 204
		[DllImport("kernel32", SetLastError = true)]
		public static extern uint GetConsoleAliasExes(out StringBuilder ExeNameBuffer, uint ExeNameBufferLength);

		// Token: 0x060000CD RID: 205
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleAliasExesLength();

		// Token: 0x060000CE RID: 206
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleCP();

		// Token: 0x060000CF RID: 207
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleCursorInfo(IntPtr hConsoleOutput, out Imports.CONSOLE_CURSOR_INFO lpConsoleCursorInfo);

		// Token: 0x060000D0 RID: 208
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleDisplayMode(out uint ModeFlags);

		// Token: 0x060000D1 RID: 209
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern Imports.COORD GetConsoleFontSize(IntPtr hConsoleOutput, int nFont);

		// Token: 0x060000D2 RID: 210
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleHistoryInfo(out Imports.CONSOLE_HISTORY_INFO ConsoleHistoryInfo);

		// Token: 0x060000D3 RID: 211
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleMode(IntPtr hConsoleHandle, out uint lpMode);

		// Token: 0x060000D4 RID: 212
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleMode(IntPtr hConsoleHandle, out int lpMode);

		// Token: 0x060000D5 RID: 213
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleOriginalTitle(out StringBuilder ConsoleTitle, uint Size);

		// Token: 0x060000D6 RID: 214
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleOutputCP();

		// Token: 0x060000D7 RID: 215
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleProcessList(out uint[] ProcessList, uint ProcessCount);

		// Token: 0x060000D8 RID: 216
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleScreenBufferInfo(IntPtr hConsoleOutput, out Imports.CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);

		// Token: 0x060000D9 RID: 217
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleScreenBufferInfoEx(IntPtr hConsoleOutput, ref Imports.CONSOLE_SCREEN_BUFFER_INFO_EX ConsoleScreenBufferInfo);

		// Token: 0x060000DA RID: 218
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleSelectionInfo(Imports.CONSOLE_SELECTION_INFO ConsoleSelectionInfo);

		// Token: 0x060000DB RID: 219
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleTitle([Out] StringBuilder lpConsoleTitle, uint nSize);

		// Token: 0x060000DC RID: 220
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr GetConsoleWindow();

		// Token: 0x060000DD RID: 221
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetCurrentConsoleFont(IntPtr hConsoleOutput, bool bMaximumWindow, out Imports.CONSOLE_FONT_INFO lpConsoleCurrentFont);

		// Token: 0x060000DE RID: 222
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetCurrentConsoleFontEx(IntPtr ConsoleOutput, bool MaximumWindow, out Imports.CONSOLE_FONT_INFO_EX ConsoleCurrentFont);

		// Token: 0x060000DF RID: 223
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern Imports.COORD GetLargestConsoleWindowSize(IntPtr hConsoleOutput);

		// Token: 0x060000E0 RID: 224
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetNumberOfConsoleInputEvents(IntPtr hConsoleInput, out uint lpcNumberOfEvents);

		// Token: 0x060000E1 RID: 225
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetNumberOfConsoleMouseButtons(ref uint lpNumberOfMouseButtons);

		// Token: 0x060000E2 RID: 226
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr GetStdHandle(int nStdHandle);

		// Token: 0x060000E3 RID: 227
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool PeekConsoleInput(IntPtr hConsoleInput, [Out] Imports.INPUT_RECORD[] lpBuffer, uint nLength, out uint lpNumberOfEventsRead);

		// Token: 0x060000E4 RID: 228
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsole(IntPtr hConsoleInput, [Out] StringBuilder lpBuffer, uint nNumberOfCharsToRead, out uint lpNumberOfCharsRead, IntPtr lpReserved);

		// Token: 0x060000E5 RID: 229
		[DllImport("kernel32.dll", CharSet = CharSet.Unicode, EntryPoint = "ReadConsoleInputW")]
		public static extern bool ReadConsoleInput(IntPtr hConsoleInput, [Out] Imports.INPUT_RECORD[] lpBuffer, uint nLength, out uint lpNumberOfEventsRead);

		// Token: 0x060000E6 RID: 230
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsoleOutput(IntPtr hConsoleOutput, [Out] Imports.CHAR_INFO[] lpBuffer, Imports.COORD dwBufferSize, Imports.COORD dwBufferCoord, ref Imports.SMALL_RECT lpReadRegion);

		// Token: 0x060000E7 RID: 231
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsoleOutputAttribute(IntPtr hConsoleOutput, [Out] ushort[] lpAttribute, uint nLength, Imports.COORD dwReadCoord, out uint lpNumberOfAttrsRead);

		// Token: 0x060000E8 RID: 232
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsoleOutputCharacter(IntPtr hConsoleOutput, [Out] StringBuilder lpCharacter, uint nLength, Imports.COORD dwReadCoord, out uint lpNumberOfCharsRead);

		// Token: 0x060000E9 RID: 233
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ScrollConsoleScreenBuffer(IntPtr hConsoleOutput, [In] ref Imports.SMALL_RECT lpScrollRectangle, IntPtr lpClipRectangle, Imports.COORD dwDestinationOrigin, [In] ref Imports.CHAR_INFO lpFill);

		// Token: 0x060000EA RID: 234
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleActiveScreenBuffer(IntPtr hConsoleOutput);

		// Token: 0x060000EB RID: 235
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCP(uint wCodePageID);

		// Token: 0x060000EC RID: 236
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCtrlHandler(Imports.ConsoleCtrlDelegate HandlerRoutine, bool Add);

		// Token: 0x060000ED RID: 237
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCursorInfo(IntPtr hConsoleOutput, [In] ref Imports.CONSOLE_CURSOR_INFO lpConsoleCursorInfo);

		// Token: 0x060000EE RID: 238
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCursorPosition(IntPtr hConsoleOutput, Imports.COORD dwCursorPosition);

		// Token: 0x060000EF RID: 239
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleDisplayMode(IntPtr ConsoleOutput, uint Flags, out Imports.COORD NewScreenBufferDimensions);

		// Token: 0x060000F0 RID: 240
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleHistoryInfo(Imports.CONSOLE_HISTORY_INFO ConsoleHistoryInfo);

		// Token: 0x060000F1 RID: 241
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleMode(IntPtr hConsoleHandle, uint dwMode);

		// Token: 0x060000F2 RID: 242
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleMode(IntPtr hConsoleHandle, int dwMode);

		// Token: 0x060000F3 RID: 243
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleOutputCP(uint wCodePageID);

		// Token: 0x060000F4 RID: 244
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleScreenBufferInfoEx(IntPtr ConsoleOutput, Imports.CONSOLE_SCREEN_BUFFER_INFO_EX ConsoleScreenBufferInfoEx);

		// Token: 0x060000F5 RID: 245
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleScreenBufferSize(IntPtr hConsoleOutput, Imports.COORD dwSize);

		// Token: 0x060000F6 RID: 246
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleTextAttribute(IntPtr hConsoleOutput, ushort wAttributes);

		// Token: 0x060000F7 RID: 247
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleTitle(string lpConsoleTitle);

		// Token: 0x060000F8 RID: 248
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleWindowInfo(IntPtr hConsoleOutput, bool bAbsolute, [In] ref Imports.SMALL_RECT lpConsoleWindow);

		// Token: 0x060000F9 RID: 249
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetCurrentConsoleFontEx(IntPtr ConsoleOutput, bool MaximumWindow, ref Imports.CONSOLE_FONT_INFO_EX ConsoleCurrentFontEx);

		// Token: 0x060000FA RID: 250
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetCurrentConsoleFontEx(IntPtr ConsoleOutput, bool MaximumWindow, Imports.CONSOLE_FONT_INFO_EX ConsoleCurrentFontEx);

		// Token: 0x060000FB RID: 251
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetStdHandle(uint nStdHandle, IntPtr hHandle);

		// Token: 0x060000FC RID: 252
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsole(IntPtr hConsoleOutput, string lpBuffer, uint nNumberOfCharsToWrite, out uint lpNumberOfCharsWritten, IntPtr lpReserved);

		// Token: 0x060000FD RID: 253
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleInput(IntPtr hConsoleInput, Imports.INPUT_RECORD[] lpBuffer, uint nLength, out uint lpNumberOfEventsWritten);

		// Token: 0x060000FE RID: 254
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleOutput(IntPtr hConsoleOutput, Imports.CHAR_INFO[] lpBuffer, Imports.COORD dwBufferSize, Imports.COORD dwBufferCoord, ref Imports.SMALL_RECT lpWriteRegion);

		// Token: 0x060000FF RID: 255
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleOutputAttribute(IntPtr hConsoleOutput, ushort[] lpAttribute, uint nLength, Imports.COORD dwWriteCoord, out uint lpNumberOfAttrsWritten);

		// Token: 0x06000100 RID: 256
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleOutputCharacter(IntPtr hConsoleOutput, string lpCharacter, uint nLength, Imports.COORD dwWriteCoord, out uint lpNumberOfCharsWritten);

		// Token: 0x06000101 RID: 257
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool GetWindowRect(IntPtr hwnd, out Imports.RECT lpRect);

		// Token: 0x06000102 RID: 258
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool GetWindowRect(IntPtr hwnd, out Imports.SimpleRECT lpRect);

		// Token: 0x06000103 RID: 259
		[DllImport("user32.dll")]
		public static extern int GetSystemMetrics(Imports.SystemMetric smIndex);

		// Token: 0x06000104 RID: 260
		[DllImport("user32.dll", SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int x, int y, int cx, int cy, uint uFlags);

		// Token: 0x06000105 RID: 261
		[DllImport("user32")]
		public static extern bool GetMonitorInfo(IntPtr hMonitor, Imports.MONITORINFO lpmi);

		// Token: 0x06000106 RID: 262
		[DllImport("User32")]
		public static extern IntPtr MonitorFromWindow(IntPtr handle, int flags);

		// Token: 0x06000107 RID: 263
		[DllImport("User32.dll", SetLastError = true)]
		public static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

		// Token: 0x06000108 RID: 264
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool MoveWindow(IntPtr hWnd, int X, int Y, int Width, int Height, bool Repaint);

		// Token: 0x06000109 RID: 265
		[DllImport("user32.dll", SetLastError = true)]
		public static extern short GetAsyncKeyState(int key);

		// Token: 0x0600010A RID: 266
		[DllImport("user32.dll")]
		public static extern short GetAsyncKeyState(Imports.VirtualKeys vKey);

		// Token: 0x0600010B RID: 267
		[DllImport("user32.dll")]
		public static extern short GetAsyncKeyState(Imports.VK vKey);

		// Token: 0x0600010C RID: 268
		[DllImport("user32.dll", SetLastError = true)]
		public static extern int GetWindowLong(IntPtr hWnd, int nIndex);

		// Token: 0x0600010D RID: 269
		[DllImport("user32.dll")]
		public static extern int SetWindowLong(IntPtr hWnd, int nIndex, uint dwNewLong);

		// Token: 0x0600010E RID: 270
		[DllImport("user32.dll")]
		public static extern int SetWindowLong(IntPtr hWnd, int nIndex, int dwNewLong);

		// Token: 0x0600010F RID: 271
		[DllImport("user32.dll")]
		public static extern bool SetLayeredWindowAttributes(IntPtr hwnd, uint crKey, byte bAlpha, uint dwFlags);

		// Token: 0x06000110 RID: 272
		[DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
		public static extern IntPtr GetForegroundWindow();

		// Token: 0x06000111 RID: 273
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern int GetWindowThreadProcessId(IntPtr handle, out int processId);

		// Token: 0x06000112 RID: 274
		[DllImport("user32.dll")]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool SetForegroundWindow(IntPtr hWnd);

		// Token: 0x04000063 RID: 99
		public const int SWP_ASYNCWINDOWPOS = 16384;

		// Token: 0x04000064 RID: 100
		public const int SWP_DEFERERASE = 8192;

		// Token: 0x04000065 RID: 101
		public const int SWP_DRAWFRAME = 32;

		// Token: 0x04000066 RID: 102
		public const int SWP_FRAMECHANGED = 32;

		// Token: 0x04000067 RID: 103
		public const int SWP_HIDEWINDOW = 128;

		// Token: 0x04000068 RID: 104
		public const int SWP_NOACTIVATE = 16;

		// Token: 0x04000069 RID: 105
		public const int SWP_NOCOPYBITS = 256;

		// Token: 0x0400006A RID: 106
		public const int SWP_NOMOVE = 2;

		// Token: 0x0400006B RID: 107
		public const int SWP_NOOWNERZORDER = 512;

		// Token: 0x0400006C RID: 108
		public const int SWP_NOREDRAW = 8;

		// Token: 0x0400006D RID: 109
		public const int SWP_NOREPOSITION = 512;

		// Token: 0x0400006E RID: 110
		public const int SWP_NOSENDCHANGING = 1024;

		// Token: 0x0400006F RID: 111
		public const int SWP_NOSIZE = 1;

		// Token: 0x04000070 RID: 112
		public const int SWP_NOZORDER = 4;

		// Token: 0x04000071 RID: 113
		public const int SWP_SHOWWINDOW = 64;

		// Token: 0x04000072 RID: 114
		public const int HWND_TOP = 0;

		// Token: 0x04000073 RID: 115
		public const int HWND_BOTTOM = 1;

		// Token: 0x04000074 RID: 116
		public const int HWND_TOPMOST = -1;

		// Token: 0x04000075 RID: 117
		public const int HWND_NOTOPMOST = -2;

		// Token: 0x04000076 RID: 118
		public static IntPtr INVALID_HANDLE_VALUE = new IntPtr(-1);

		// Token: 0x04000077 RID: 119
		public static readonly int TMPF_TRUETYPE = 4;

		// Token: 0x04000078 RID: 120
		public static readonly int STD_OUTPUT_HANDLE = -11;

		// Token: 0x02000047 RID: 71
		// (Invoke) Token: 0x06000142 RID: 322
		public delegate bool ConsoleCtrlDelegate(Imports.CtrlTypes CtrlType);

		// Token: 0x02000048 RID: 72
		public struct COORD
		{
			// Token: 0x06000145 RID: 325 RVA: 0x000066AA File Offset: 0x000048AA
			internal COORD(short x, short y)
			{
				this.X = x;
				this.Y = y;
			}

			// Token: 0x040003B6 RID: 950
			internal short X;

			// Token: 0x040003B7 RID: 951
			internal short Y;
		}

		// Token: 0x02000049 RID: 73
		public struct SMALL_RECT
		{
			// Token: 0x040003B8 RID: 952
			public short Left;

			// Token: 0x040003B9 RID: 953
			public short Top;

			// Token: 0x040003BA RID: 954
			public short Right;

			// Token: 0x040003BB RID: 955
			public short Bottom;
		}

		// Token: 0x0200004A RID: 74
		public struct CONSOLE_SCREEN_BUFFER_INFO
		{
			// Token: 0x040003BC RID: 956
			public Imports.COORD dwSize;

			// Token: 0x040003BD RID: 957
			public Imports.COORD dwCursorPosition;

			// Token: 0x040003BE RID: 958
			public short wAttributes;

			// Token: 0x040003BF RID: 959
			public Imports.SMALL_RECT srWindow;

			// Token: 0x040003C0 RID: 960
			public Imports.COORD dwMaximumWindowSize;
		}

		// Token: 0x0200004B RID: 75
		public struct CONSOLE_SCREEN_BUFFER_INFO_EX
		{
			// Token: 0x06000146 RID: 326 RVA: 0x000066BC File Offset: 0x000048BC
			public static Imports.CONSOLE_SCREEN_BUFFER_INFO_EX Create()
			{
				return new Imports.CONSOLE_SCREEN_BUFFER_INFO_EX
				{
					cbSize = 96U
				};
			}

			// Token: 0x040003C1 RID: 961
			public uint cbSize;

			// Token: 0x040003C2 RID: 962
			public Imports.COORD dwSize;

			// Token: 0x040003C3 RID: 963
			public Imports.COORD dwCursorPosition;

			// Token: 0x040003C4 RID: 964
			public short wAttributes;

			// Token: 0x040003C5 RID: 965
			public Imports.SMALL_RECT srWindow;

			// Token: 0x040003C6 RID: 966
			public Imports.COORD dwMaximumWindowSize;

			// Token: 0x040003C7 RID: 967
			public ushort wPopupAttributes;

			// Token: 0x040003C8 RID: 968
			public bool bFullscreenSupported;

			// Token: 0x040003C9 RID: 969
			[MarshalAs(UnmanagedType.ByValArray, SizeConst = 16)]
			public DllImports.COLORREF[] ColorTable;
		}

		// Token: 0x0200004C RID: 76
		public struct CONSOLE_FONT_INFO
		{
			// Token: 0x040003CA RID: 970
			public int nFont;

			// Token: 0x040003CB RID: 971
			public Imports.COORD dwFontSize;
		}

		// Token: 0x0200004D RID: 77
		[StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)]
		public struct CONSOLE_FONT_INFO_EX
		{
			// Token: 0x040003CC RID: 972
			internal uint cbSize;

			// Token: 0x040003CD RID: 973
			internal uint nFont;

			// Token: 0x040003CE RID: 974
			internal Imports.COORD dwFontSize;

			// Token: 0x040003CF RID: 975
			internal int FontFamily;

			// Token: 0x040003D0 RID: 976
			internal int FontWeight;

			// Token: 0x040003D1 RID: 977
			[FixedBuffer(typeof(char), 32)]
			internal Imports.CONSOLE_FONT_INFO_EX.<FaceName>e__FixedBuffer FaceName;

			// Token: 0x02000068 RID: 104
			[CompilerGenerated]
			[UnsafeValueType]
			[StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode, Size = 64)]
			public struct <FaceName>e__FixedBuffer
			{
				// Token: 0x040005EA RID: 1514
				public char FixedElementField;
			}
		}

		// Token: 0x0200004E RID: 78
		[StructLayout(LayoutKind.Explicit)]
		public struct INPUT_RECORD
		{
			// Token: 0x040003D2 RID: 978
			[FieldOffset(0)]
			public ushort EventType;

			// Token: 0x040003D3 RID: 979
			[FieldOffset(4)]
			public Imports.KEY_EVENT_RECORD KeyEvent;

			// Token: 0x040003D4 RID: 980
			[FieldOffset(4)]
			public Imports.MOUSE_EVENT_RECORD MouseEvent;

			// Token: 0x040003D5 RID: 981
			[FieldOffset(4)]
			public Imports.WINDOW_BUFFER_SIZE_RECORD WindowBufferSizeEvent;

			// Token: 0x040003D6 RID: 982
			[FieldOffset(4)]
			public Imports.MENU_EVENT_RECORD MenuEvent;

			// Token: 0x040003D7 RID: 983
			[FieldOffset(4)]
			public Imports.FOCUS_EVENT_RECORD FocusEvent;
		}

		// Token: 0x0200004F RID: 79
		[StructLayout(LayoutKind.Explicit, CharSet = CharSet.Unicode)]
		public struct KEY_EVENT_RECORD
		{
			// Token: 0x040003D8 RID: 984
			[FieldOffset(0)]
			[MarshalAs(UnmanagedType.Bool)]
			public bool bKeyDown;

			// Token: 0x040003D9 RID: 985
			[FieldOffset(4)]
			[MarshalAs(UnmanagedType.U2)]
			public ushort wRepeatCount;

			// Token: 0x040003DA RID: 986
			[FieldOffset(6)]
			[MarshalAs(UnmanagedType.U2)]
			public ushort wVirtualKeyCode;

			// Token: 0x040003DB RID: 987
			[FieldOffset(8)]
			[MarshalAs(UnmanagedType.U2)]
			public ushort wVirtualScanCode;

			// Token: 0x040003DC RID: 988
			[FieldOffset(10)]
			public char UnicodeChar;

			// Token: 0x040003DD RID: 989
			[FieldOffset(12)]
			[MarshalAs(UnmanagedType.U4)]
			public uint dwControlKeyState;
		}

		// Token: 0x02000050 RID: 80
		public struct MOUSE_EVENT_RECORD
		{
			// Token: 0x040003DE RID: 990
			public Imports.COORD dwMousePosition;

			// Token: 0x040003DF RID: 991
			public uint dwButtonState;

			// Token: 0x040003E0 RID: 992
			public uint dwControlKeyState;

			// Token: 0x040003E1 RID: 993
			public uint dwEventFlags;
		}

		// Token: 0x02000051 RID: 81
		public struct WINDOW_BUFFER_SIZE_RECORD
		{
			// Token: 0x06000147 RID: 327 RVA: 0x000066E0 File Offset: 0x000048E0
			public WINDOW_BUFFER_SIZE_RECORD(short x, short y)
			{
				this.dwSize = default(Imports.COORD);
				this.dwSize.X = x;
				this.dwSize.Y = y;
			}

			// Token: 0x040003E2 RID: 994
			public Imports.COORD dwSize;
		}

		// Token: 0x02000052 RID: 82
		public struct MENU_EVENT_RECORD
		{
			// Token: 0x040003E3 RID: 995
			public uint dwCommandId;
		}

		// Token: 0x02000053 RID: 83
		public struct FOCUS_EVENT_RECORD
		{
			// Token: 0x040003E4 RID: 996
			public uint bSetFocus;
		}

		// Token: 0x02000054 RID: 84
		[StructLayout(LayoutKind.Explicit)]
		public struct CHAR_INFO
		{
			// Token: 0x040003E5 RID: 997
			[FieldOffset(0)]
			private char UnicodeChar;

			// Token: 0x040003E6 RID: 998
			[FieldOffset(0)]
			private char AsciiChar;

			// Token: 0x040003E7 RID: 999
			[FieldOffset(2)]
			private ushort Attributes;
		}

		// Token: 0x02000055 RID: 85
		public struct CONSOLE_CURSOR_INFO
		{
			// Token: 0x040003E8 RID: 1000
			private uint Size;

			// Token: 0x040003E9 RID: 1001
			private bool Visible;
		}

		// Token: 0x02000056 RID: 86
		public struct CONSOLE_HISTORY_INFO
		{
			// Token: 0x040003EA RID: 1002
			private ushort cbSize;

			// Token: 0x040003EB RID: 1003
			private ushort HistoryBufferSize;

			// Token: 0x040003EC RID: 1004
			private ushort NumberOfHistoryBuffers;

			// Token: 0x040003ED RID: 1005
			private uint dwFlags;
		}

		// Token: 0x02000057 RID: 87
		public struct CONSOLE_SELECTION_INFO
		{
			// Token: 0x040003EE RID: 1006
			private uint Flags;

			// Token: 0x040003EF RID: 1007
			private Imports.COORD SelectionAnchor;

			// Token: 0x040003F0 RID: 1008
			private Imports.SMALL_RECT Selection;

			// Token: 0x040003F1 RID: 1009
			private const uint CONSOLE_MOUSE_DOWN = 8U;

			// Token: 0x040003F2 RID: 1010
			private const uint CONSOLE_MOUSE_SELECTION = 4U;

			// Token: 0x040003F3 RID: 1011
			private const uint CONSOLE_NO_SELECTION = 0U;

			// Token: 0x040003F4 RID: 1012
			private const uint CONSOLE_SELECTION_IN_PROGRESS = 1U;

			// Token: 0x040003F5 RID: 1013
			private const uint CONSOLE_SELECTION_NOT_EMPTY = 2U;
		}

		// Token: 0x02000058 RID: 88
		public enum CtrlTypes : uint
		{
			// Token: 0x040003F7 RID: 1015
			CTRL_C_EVENT,
			// Token: 0x040003F8 RID: 1016
			CTRL_BREAK_EVENT,
			// Token: 0x040003F9 RID: 1017
			CTRL_CLOSE_EVENT,
			// Token: 0x040003FA RID: 1018
			CTRL_LOGOFF_EVENT = 5U,
			// Token: 0x040003FB RID: 1019
			CTRL_SHUTDOWN_EVENT
		}

		// Token: 0x02000059 RID: 89
		public enum VirtualKeys : ushort
		{
			// Token: 0x040003FD RID: 1021
			LeftButton = 1,
			// Token: 0x040003FE RID: 1022
			RightButton,
			// Token: 0x040003FF RID: 1023
			Cancel,
			// Token: 0x04000400 RID: 1024
			MiddleButton,
			// Token: 0x04000401 RID: 1025
			ExtraButton1,
			// Token: 0x04000402 RID: 1026
			ExtraButton2,
			// Token: 0x04000403 RID: 1027
			Back = 8,
			// Token: 0x04000404 RID: 1028
			Tab,
			// Token: 0x04000405 RID: 1029
			Clear = 12,
			// Token: 0x04000406 RID: 1030
			Return,
			// Token: 0x04000407 RID: 1031
			Shift = 16,
			// Token: 0x04000408 RID: 1032
			Control,
			// Token: 0x04000409 RID: 1033
			Menu,
			// Token: 0x0400040A RID: 1034
			Pause,
			// Token: 0x0400040B RID: 1035
			CapsLock,
			// Token: 0x0400040C RID: 1036
			Kana,
			// Token: 0x0400040D RID: 1037
			Hangeul = 21,
			// Token: 0x0400040E RID: 1038
			Hangul = 21,
			// Token: 0x0400040F RID: 1039
			Junja = 23,
			// Token: 0x04000410 RID: 1040
			Final,
			// Token: 0x04000411 RID: 1041
			Hanja,
			// Token: 0x04000412 RID: 1042
			Kanji = 25,
			// Token: 0x04000413 RID: 1043
			Escape = 27,
			// Token: 0x04000414 RID: 1044
			Convert,
			// Token: 0x04000415 RID: 1045
			NonConvert,
			// Token: 0x04000416 RID: 1046
			Accept,
			// Token: 0x04000417 RID: 1047
			ModeChange,
			// Token: 0x04000418 RID: 1048
			Space,
			// Token: 0x04000419 RID: 1049
			Prior,
			// Token: 0x0400041A RID: 1050
			Next,
			// Token: 0x0400041B RID: 1051
			End,
			// Token: 0x0400041C RID: 1052
			Home,
			// Token: 0x0400041D RID: 1053
			Left,
			// Token: 0x0400041E RID: 1054
			Up,
			// Token: 0x0400041F RID: 1055
			Right,
			// Token: 0x04000420 RID: 1056
			Down,
			// Token: 0x04000421 RID: 1057
			Select,
			// Token: 0x04000422 RID: 1058
			Print,
			// Token: 0x04000423 RID: 1059
			Execute,
			// Token: 0x04000424 RID: 1060
			Snapshot,
			// Token: 0x04000425 RID: 1061
			Insert,
			// Token: 0x04000426 RID: 1062
			Delete,
			// Token: 0x04000427 RID: 1063
			Help,
			// Token: 0x04000428 RID: 1064
			N0,
			// Token: 0x04000429 RID: 1065
			N1,
			// Token: 0x0400042A RID: 1066
			N2,
			// Token: 0x0400042B RID: 1067
			N3,
			// Token: 0x0400042C RID: 1068
			N4,
			// Token: 0x0400042D RID: 1069
			N5,
			// Token: 0x0400042E RID: 1070
			N6,
			// Token: 0x0400042F RID: 1071
			N7,
			// Token: 0x04000430 RID: 1072
			N8,
			// Token: 0x04000431 RID: 1073
			N9,
			// Token: 0x04000432 RID: 1074
			A = 65,
			// Token: 0x04000433 RID: 1075
			B,
			// Token: 0x04000434 RID: 1076
			C,
			// Token: 0x04000435 RID: 1077
			D,
			// Token: 0x04000436 RID: 1078
			E,
			// Token: 0x04000437 RID: 1079
			F,
			// Token: 0x04000438 RID: 1080
			G,
			// Token: 0x04000439 RID: 1081
			H,
			// Token: 0x0400043A RID: 1082
			I,
			// Token: 0x0400043B RID: 1083
			J,
			// Token: 0x0400043C RID: 1084
			K,
			// Token: 0x0400043D RID: 1085
			L,
			// Token: 0x0400043E RID: 1086
			M,
			// Token: 0x0400043F RID: 1087
			N,
			// Token: 0x04000440 RID: 1088
			O,
			// Token: 0x04000441 RID: 1089
			P,
			// Token: 0x04000442 RID: 1090
			Q,
			// Token: 0x04000443 RID: 1091
			R,
			// Token: 0x04000444 RID: 1092
			S,
			// Token: 0x04000445 RID: 1093
			T,
			// Token: 0x04000446 RID: 1094
			U,
			// Token: 0x04000447 RID: 1095
			V,
			// Token: 0x04000448 RID: 1096
			W,
			// Token: 0x04000449 RID: 1097
			X,
			// Token: 0x0400044A RID: 1098
			Y,
			// Token: 0x0400044B RID: 1099
			Z,
			// Token: 0x0400044C RID: 1100
			LeftWindows,
			// Token: 0x0400044D RID: 1101
			RightWindows,
			// Token: 0x0400044E RID: 1102
			Application,
			// Token: 0x0400044F RID: 1103
			Sleep = 95,
			// Token: 0x04000450 RID: 1104
			Numpad0,
			// Token: 0x04000451 RID: 1105
			Numpad1,
			// Token: 0x04000452 RID: 1106
			Numpad2,
			// Token: 0x04000453 RID: 1107
			Numpad3,
			// Token: 0x04000454 RID: 1108
			Numpad4,
			// Token: 0x04000455 RID: 1109
			Numpad5,
			// Token: 0x04000456 RID: 1110
			Numpad6,
			// Token: 0x04000457 RID: 1111
			Numpad7,
			// Token: 0x04000458 RID: 1112
			Numpad8,
			// Token: 0x04000459 RID: 1113
			Numpad9,
			// Token: 0x0400045A RID: 1114
			Multiply,
			// Token: 0x0400045B RID: 1115
			Add,
			// Token: 0x0400045C RID: 1116
			Separator,
			// Token: 0x0400045D RID: 1117
			Subtract,
			// Token: 0x0400045E RID: 1118
			Decimal,
			// Token: 0x0400045F RID: 1119
			Divide,
			// Token: 0x04000460 RID: 1120
			F1,
			// Token: 0x04000461 RID: 1121
			F2,
			// Token: 0x04000462 RID: 1122
			F3,
			// Token: 0x04000463 RID: 1123
			F4,
			// Token: 0x04000464 RID: 1124
			F5,
			// Token: 0x04000465 RID: 1125
			F6,
			// Token: 0x04000466 RID: 1126
			F7,
			// Token: 0x04000467 RID: 1127
			F8,
			// Token: 0x04000468 RID: 1128
			F9,
			// Token: 0x04000469 RID: 1129
			F10,
			// Token: 0x0400046A RID: 1130
			F11,
			// Token: 0x0400046B RID: 1131
			F12,
			// Token: 0x0400046C RID: 1132
			F13,
			// Token: 0x0400046D RID: 1133
			F14,
			// Token: 0x0400046E RID: 1134
			F15,
			// Token: 0x0400046F RID: 1135
			F16,
			// Token: 0x04000470 RID: 1136
			F17,
			// Token: 0x04000471 RID: 1137
			F18,
			// Token: 0x04000472 RID: 1138
			F19,
			// Token: 0x04000473 RID: 1139
			F20,
			// Token: 0x04000474 RID: 1140
			F21,
			// Token: 0x04000475 RID: 1141
			F22,
			// Token: 0x04000476 RID: 1142
			F23,
			// Token: 0x04000477 RID: 1143
			F24,
			// Token: 0x04000478 RID: 1144
			NumLock = 144,
			// Token: 0x04000479 RID: 1145
			ScrollLock,
			// Token: 0x0400047A RID: 1146
			NEC_Equal,
			// Token: 0x0400047B RID: 1147
			Fujitsu_Jisho = 146,
			// Token: 0x0400047C RID: 1148
			Fujitsu_Masshou,
			// Token: 0x0400047D RID: 1149
			Fujitsu_Touroku,
			// Token: 0x0400047E RID: 1150
			Fujitsu_Loya,
			// Token: 0x0400047F RID: 1151
			Fujitsu_Roya,
			// Token: 0x04000480 RID: 1152
			LeftShift = 160,
			// Token: 0x04000481 RID: 1153
			RightShift,
			// Token: 0x04000482 RID: 1154
			LeftControl,
			// Token: 0x04000483 RID: 1155
			RightControl,
			// Token: 0x04000484 RID: 1156
			LeftMenu,
			// Token: 0x04000485 RID: 1157
			RightMenu,
			// Token: 0x04000486 RID: 1158
			BrowserBack,
			// Token: 0x04000487 RID: 1159
			BrowserForward,
			// Token: 0x04000488 RID: 1160
			BrowserRefresh,
			// Token: 0x04000489 RID: 1161
			BrowserStop,
			// Token: 0x0400048A RID: 1162
			BrowserSearch,
			// Token: 0x0400048B RID: 1163
			BrowserFavorites,
			// Token: 0x0400048C RID: 1164
			BrowserHome,
			// Token: 0x0400048D RID: 1165
			VolumeMute,
			// Token: 0x0400048E RID: 1166
			VolumeDown,
			// Token: 0x0400048F RID: 1167
			VolumeUp,
			// Token: 0x04000490 RID: 1168
			MediaNextTrack,
			// Token: 0x04000491 RID: 1169
			MediaPrevTrack,
			// Token: 0x04000492 RID: 1170
			MediaStop,
			// Token: 0x04000493 RID: 1171
			MediaPlayPause,
			// Token: 0x04000494 RID: 1172
			LaunchMail,
			// Token: 0x04000495 RID: 1173
			LaunchMediaSelect,
			// Token: 0x04000496 RID: 1174
			LaunchApplication1,
			// Token: 0x04000497 RID: 1175
			LaunchApplication2,
			// Token: 0x04000498 RID: 1176
			OEM1 = 186,
			// Token: 0x04000499 RID: 1177
			OEMPlus,
			// Token: 0x0400049A RID: 1178
			OEMComma,
			// Token: 0x0400049B RID: 1179
			OEMMinus,
			// Token: 0x0400049C RID: 1180
			OEMPeriod,
			// Token: 0x0400049D RID: 1181
			OEM2,
			// Token: 0x0400049E RID: 1182
			OEM3,
			// Token: 0x0400049F RID: 1183
			OEM4 = 219,
			// Token: 0x040004A0 RID: 1184
			OEM5,
			// Token: 0x040004A1 RID: 1185
			OEM6,
			// Token: 0x040004A2 RID: 1186
			OEM7,
			// Token: 0x040004A3 RID: 1187
			OEM8,
			// Token: 0x040004A4 RID: 1188
			OEMAX = 225,
			// Token: 0x040004A5 RID: 1189
			OEM102,
			// Token: 0x040004A6 RID: 1190
			ICOHelp,
			// Token: 0x040004A7 RID: 1191
			ICO00,
			// Token: 0x040004A8 RID: 1192
			ProcessKey,
			// Token: 0x040004A9 RID: 1193
			ICOClear,
			// Token: 0x040004AA RID: 1194
			Packet,
			// Token: 0x040004AB RID: 1195
			OEMReset = 233,
			// Token: 0x040004AC RID: 1196
			OEMJump,
			// Token: 0x040004AD RID: 1197
			OEMPA1,
			// Token: 0x040004AE RID: 1198
			OEMPA2,
			// Token: 0x040004AF RID: 1199
			OEMPA3,
			// Token: 0x040004B0 RID: 1200
			OEMWSCtrl,
			// Token: 0x040004B1 RID: 1201
			OEMCUSel,
			// Token: 0x040004B2 RID: 1202
			OEMATTN,
			// Token: 0x040004B3 RID: 1203
			OEMFinish,
			// Token: 0x040004B4 RID: 1204
			OEMCopy,
			// Token: 0x040004B5 RID: 1205
			OEMAuto,
			// Token: 0x040004B6 RID: 1206
			OEMENLW,
			// Token: 0x040004B7 RID: 1207
			OEMBackTab,
			// Token: 0x040004B8 RID: 1208
			ATTN,
			// Token: 0x040004B9 RID: 1209
			CRSel,
			// Token: 0x040004BA RID: 1210
			EXSel,
			// Token: 0x040004BB RID: 1211
			EREOF,
			// Token: 0x040004BC RID: 1212
			Play,
			// Token: 0x040004BD RID: 1213
			Zoom,
			// Token: 0x040004BE RID: 1214
			Noname,
			// Token: 0x040004BF RID: 1215
			PA1,
			// Token: 0x040004C0 RID: 1216
			OEMClear
		}

		// Token: 0x0200005A RID: 90
		public enum VK
		{
			// Token: 0x040004C2 RID: 1218
			LBUTTON = 1,
			// Token: 0x040004C3 RID: 1219
			RBUTTON,
			// Token: 0x040004C4 RID: 1220
			CANCEL,
			// Token: 0x040004C5 RID: 1221
			MBUTTON,
			// Token: 0x040004C6 RID: 1222
			XBUTTON1,
			// Token: 0x040004C7 RID: 1223
			XBUTTON2,
			// Token: 0x040004C8 RID: 1224
			BACK = 8,
			// Token: 0x040004C9 RID: 1225
			TAB,
			// Token: 0x040004CA RID: 1226
			CLEAR = 12,
			// Token: 0x040004CB RID: 1227
			RETURN,
			// Token: 0x040004CC RID: 1228
			SHIFT = 16,
			// Token: 0x040004CD RID: 1229
			CONTROL,
			// Token: 0x040004CE RID: 1230
			MENU,
			// Token: 0x040004CF RID: 1231
			PAUSE,
			// Token: 0x040004D0 RID: 1232
			CAPITAL,
			// Token: 0x040004D1 RID: 1233
			KANA,
			// Token: 0x040004D2 RID: 1234
			HANGUL = 21,
			// Token: 0x040004D3 RID: 1235
			JUNJA = 23,
			// Token: 0x040004D4 RID: 1236
			FINAL,
			// Token: 0x040004D5 RID: 1237
			HANJA,
			// Token: 0x040004D6 RID: 1238
			KANJI = 25,
			// Token: 0x040004D7 RID: 1239
			ESCAPE = 27,
			// Token: 0x040004D8 RID: 1240
			CONVERT,
			// Token: 0x040004D9 RID: 1241
			NONCONVERT,
			// Token: 0x040004DA RID: 1242
			ACCEPT,
			// Token: 0x040004DB RID: 1243
			MODECHANGE,
			// Token: 0x040004DC RID: 1244
			SPACE,
			// Token: 0x040004DD RID: 1245
			PRIOR,
			// Token: 0x040004DE RID: 1246
			NEXT,
			// Token: 0x040004DF RID: 1247
			END,
			// Token: 0x040004E0 RID: 1248
			HOME,
			// Token: 0x040004E1 RID: 1249
			LEFT,
			// Token: 0x040004E2 RID: 1250
			UP,
			// Token: 0x040004E3 RID: 1251
			RIGHT,
			// Token: 0x040004E4 RID: 1252
			DOWN,
			// Token: 0x040004E5 RID: 1253
			SELECT,
			// Token: 0x040004E6 RID: 1254
			PRINT,
			// Token: 0x040004E7 RID: 1255
			EXECUTE,
			// Token: 0x040004E8 RID: 1256
			SNAPSHOT,
			// Token: 0x040004E9 RID: 1257
			INSERT,
			// Token: 0x040004EA RID: 1258
			DELETE,
			// Token: 0x040004EB RID: 1259
			HELP,
			// Token: 0x040004EC RID: 1260
			KEY_0,
			// Token: 0x040004ED RID: 1261
			KEY_1,
			// Token: 0x040004EE RID: 1262
			KEY_2,
			// Token: 0x040004EF RID: 1263
			KEY_3,
			// Token: 0x040004F0 RID: 1264
			KEY_4,
			// Token: 0x040004F1 RID: 1265
			KEY_5,
			// Token: 0x040004F2 RID: 1266
			KEY_6,
			// Token: 0x040004F3 RID: 1267
			KEY_7,
			// Token: 0x040004F4 RID: 1268
			KEY_8,
			// Token: 0x040004F5 RID: 1269
			KEY_9,
			// Token: 0x040004F6 RID: 1270
			KEY_A = 65,
			// Token: 0x040004F7 RID: 1271
			KEY_B,
			// Token: 0x040004F8 RID: 1272
			KEY_C,
			// Token: 0x040004F9 RID: 1273
			KEY_D,
			// Token: 0x040004FA RID: 1274
			KEY_E,
			// Token: 0x040004FB RID: 1275
			KEY_F,
			// Token: 0x040004FC RID: 1276
			KEY_G,
			// Token: 0x040004FD RID: 1277
			KEY_H,
			// Token: 0x040004FE RID: 1278
			KEY_I,
			// Token: 0x040004FF RID: 1279
			KEY_J,
			// Token: 0x04000500 RID: 1280
			KEY_K,
			// Token: 0x04000501 RID: 1281
			KEY_L,
			// Token: 0x04000502 RID: 1282
			KEY_M,
			// Token: 0x04000503 RID: 1283
			KEY_N,
			// Token: 0x04000504 RID: 1284
			KEY_O,
			// Token: 0x04000505 RID: 1285
			KEY_P,
			// Token: 0x04000506 RID: 1286
			KEY_Q,
			// Token: 0x04000507 RID: 1287
			KEY_R,
			// Token: 0x04000508 RID: 1288
			KEY_S,
			// Token: 0x04000509 RID: 1289
			KEY_T,
			// Token: 0x0400050A RID: 1290
			KEY_U,
			// Token: 0x0400050B RID: 1291
			KEY_V,
			// Token: 0x0400050C RID: 1292
			KEY_W,
			// Token: 0x0400050D RID: 1293
			KEY_X,
			// Token: 0x0400050E RID: 1294
			KEY_Y,
			// Token: 0x0400050F RID: 1295
			KEY_Z,
			// Token: 0x04000510 RID: 1296
			LWIN,
			// Token: 0x04000511 RID: 1297
			RWIN,
			// Token: 0x04000512 RID: 1298
			APPS,
			// Token: 0x04000513 RID: 1299
			SLEEP = 95,
			// Token: 0x04000514 RID: 1300
			NUMPAD0,
			// Token: 0x04000515 RID: 1301
			NUMPAD1,
			// Token: 0x04000516 RID: 1302
			NUMPAD2,
			// Token: 0x04000517 RID: 1303
			NUMPAD3,
			// Token: 0x04000518 RID: 1304
			NUMPAD4,
			// Token: 0x04000519 RID: 1305
			NUMPAD5,
			// Token: 0x0400051A RID: 1306
			NUMPAD6,
			// Token: 0x0400051B RID: 1307
			NUMPAD7,
			// Token: 0x0400051C RID: 1308
			NUMPAD8,
			// Token: 0x0400051D RID: 1309
			NUMPAD9,
			// Token: 0x0400051E RID: 1310
			MULTIPLY,
			// Token: 0x0400051F RID: 1311
			ADD,
			// Token: 0x04000520 RID: 1312
			SEPARATOR,
			// Token: 0x04000521 RID: 1313
			SUBTRACT,
			// Token: 0x04000522 RID: 1314
			DECIMAL,
			// Token: 0x04000523 RID: 1315
			DIVIDE,
			// Token: 0x04000524 RID: 1316
			F1,
			// Token: 0x04000525 RID: 1317
			F2,
			// Token: 0x04000526 RID: 1318
			F3,
			// Token: 0x04000527 RID: 1319
			F4,
			// Token: 0x04000528 RID: 1320
			F5,
			// Token: 0x04000529 RID: 1321
			F6,
			// Token: 0x0400052A RID: 1322
			F7,
			// Token: 0x0400052B RID: 1323
			F8,
			// Token: 0x0400052C RID: 1324
			F9,
			// Token: 0x0400052D RID: 1325
			F10,
			// Token: 0x0400052E RID: 1326
			F11,
			// Token: 0x0400052F RID: 1327
			F12,
			// Token: 0x04000530 RID: 1328
			F13,
			// Token: 0x04000531 RID: 1329
			F14,
			// Token: 0x04000532 RID: 1330
			F15,
			// Token: 0x04000533 RID: 1331
			F16,
			// Token: 0x04000534 RID: 1332
			F17,
			// Token: 0x04000535 RID: 1333
			F18,
			// Token: 0x04000536 RID: 1334
			F19,
			// Token: 0x04000537 RID: 1335
			F20,
			// Token: 0x04000538 RID: 1336
			F21,
			// Token: 0x04000539 RID: 1337
			F22,
			// Token: 0x0400053A RID: 1338
			F23,
			// Token: 0x0400053B RID: 1339
			F24,
			// Token: 0x0400053C RID: 1340
			NUMLOCK = 144,
			// Token: 0x0400053D RID: 1341
			SCROLL,
			// Token: 0x0400053E RID: 1342
			LSHIFT = 160,
			// Token: 0x0400053F RID: 1343
			RSHIFT,
			// Token: 0x04000540 RID: 1344
			LCONTROL,
			// Token: 0x04000541 RID: 1345
			RCONTROL,
			// Token: 0x04000542 RID: 1346
			LMENU,
			// Token: 0x04000543 RID: 1347
			RMENU,
			// Token: 0x04000544 RID: 1348
			BROWSER_BACK,
			// Token: 0x04000545 RID: 1349
			BROWSER_FORWARD,
			// Token: 0x04000546 RID: 1350
			BROWSER_REFRESH,
			// Token: 0x04000547 RID: 1351
			BROWSER_STOP,
			// Token: 0x04000548 RID: 1352
			BROWSER_SEARCH,
			// Token: 0x04000549 RID: 1353
			BROWSER_FAVORITES,
			// Token: 0x0400054A RID: 1354
			BROWSER_HOME,
			// Token: 0x0400054B RID: 1355
			VOLUME_MUTE,
			// Token: 0x0400054C RID: 1356
			VOLUME_DOWN,
			// Token: 0x0400054D RID: 1357
			VOLUME_UP,
			// Token: 0x0400054E RID: 1358
			MEDIA_NEXT_TRACK,
			// Token: 0x0400054F RID: 1359
			MEDIA_PREV_TRACK,
			// Token: 0x04000550 RID: 1360
			MEDIA_STOP,
			// Token: 0x04000551 RID: 1361
			MEDIA_PLAY_PAUSE,
			// Token: 0x04000552 RID: 1362
			LAUNCH_MAIL,
			// Token: 0x04000553 RID: 1363
			LAUNCH_MEDIA_SELECT,
			// Token: 0x04000554 RID: 1364
			LAUNCH_APP1,
			// Token: 0x04000555 RID: 1365
			LAUNCH_APP2,
			// Token: 0x04000556 RID: 1366
			OEM_1 = 186,
			// Token: 0x04000557 RID: 1367
			OEM_PLUS,
			// Token: 0x04000558 RID: 1368
			OEM_COMMA,
			// Token: 0x04000559 RID: 1369
			OEM_MINUS,
			// Token: 0x0400055A RID: 1370
			OEM_PERIOD,
			// Token: 0x0400055B RID: 1371
			OEM_2,
			// Token: 0x0400055C RID: 1372
			OEM_3,
			// Token: 0x0400055D RID: 1373
			OEM_4 = 219,
			// Token: 0x0400055E RID: 1374
			OEM_5,
			// Token: 0x0400055F RID: 1375
			OEM_6,
			// Token: 0x04000560 RID: 1376
			OEM_7,
			// Token: 0x04000561 RID: 1377
			OEM_8,
			// Token: 0x04000562 RID: 1378
			OEM_102 = 226,
			// Token: 0x04000563 RID: 1379
			PROCESSKEY = 229,
			// Token: 0x04000564 RID: 1380
			PACKET = 231,
			// Token: 0x04000565 RID: 1381
			ATTN = 246,
			// Token: 0x04000566 RID: 1382
			CRSEL,
			// Token: 0x04000567 RID: 1383
			EXSEL,
			// Token: 0x04000568 RID: 1384
			EREOF,
			// Token: 0x04000569 RID: 1385
			PLAY,
			// Token: 0x0400056A RID: 1386
			ZOOM,
			// Token: 0x0400056B RID: 1387
			NONAME,
			// Token: 0x0400056C RID: 1388
			PA1,
			// Token: 0x0400056D RID: 1389
			OEM_CLEAR
		}

		// Token: 0x0200005B RID: 91
		public enum SystemMetric
		{
			// Token: 0x0400056F RID: 1391
			SM_CXSCREEN,
			// Token: 0x04000570 RID: 1392
			SM_CYSCREEN,
			// Token: 0x04000571 RID: 1393
			SM_CXVSCROLL,
			// Token: 0x04000572 RID: 1394
			SM_CYHSCROLL,
			// Token: 0x04000573 RID: 1395
			SM_CYCAPTION,
			// Token: 0x04000574 RID: 1396
			SM_CXBORDER,
			// Token: 0x04000575 RID: 1397
			SM_CYBORDER,
			// Token: 0x04000576 RID: 1398
			SM_CXDLGFRAME,
			// Token: 0x04000577 RID: 1399
			SM_CXFIXEDFRAME = 7,
			// Token: 0x04000578 RID: 1400
			SM_CYDLGFRAME,
			// Token: 0x04000579 RID: 1401
			SM_CYFIXEDFRAME = 8,
			// Token: 0x0400057A RID: 1402
			SM_CYVTHUMB,
			// Token: 0x0400057B RID: 1403
			SM_CXHTHUMB,
			// Token: 0x0400057C RID: 1404
			SM_CXICON,
			// Token: 0x0400057D RID: 1405
			SM_CYICON,
			// Token: 0x0400057E RID: 1406
			SM_CXCURSOR,
			// Token: 0x0400057F RID: 1407
			SM_CYCURSOR,
			// Token: 0x04000580 RID: 1408
			SM_CYMENU,
			// Token: 0x04000581 RID: 1409
			SM_CXFULLSCREEN,
			// Token: 0x04000582 RID: 1410
			SM_CYFULLSCREEN,
			// Token: 0x04000583 RID: 1411
			SM_CYKANJIWINDOW,
			// Token: 0x04000584 RID: 1412
			SM_MOUSEPRESENT,
			// Token: 0x04000585 RID: 1413
			SM_CYVSCROLL,
			// Token: 0x04000586 RID: 1414
			SM_CXHSCROLL,
			// Token: 0x04000587 RID: 1415
			SM_DEBUG,
			// Token: 0x04000588 RID: 1416
			SM_SWAPBUTTON,
			// Token: 0x04000589 RID: 1417
			SM_CXMIN = 28,
			// Token: 0x0400058A RID: 1418
			SM_CYMIN,
			// Token: 0x0400058B RID: 1419
			SM_CXSIZE,
			// Token: 0x0400058C RID: 1420
			SM_CYSIZE,
			// Token: 0x0400058D RID: 1421
			SM_CXSIZEFRAME,
			// Token: 0x0400058E RID: 1422
			SM_CXFRAME = 32,
			// Token: 0x0400058F RID: 1423
			SM_CYSIZEFRAME,
			// Token: 0x04000590 RID: 1424
			SM_CYFRAME = 33,
			// Token: 0x04000591 RID: 1425
			SM_CXMINTRACK,
			// Token: 0x04000592 RID: 1426
			SM_CYMINTRACK,
			// Token: 0x04000593 RID: 1427
			SM_CXDOUBLECLK,
			// Token: 0x04000594 RID: 1428
			SM_CYDOUBLECLK,
			// Token: 0x04000595 RID: 1429
			SM_CXICONSPACING,
			// Token: 0x04000596 RID: 1430
			SM_CYICONSPACING,
			// Token: 0x04000597 RID: 1431
			SM_MENUDROPALIGNMENT,
			// Token: 0x04000598 RID: 1432
			SM_PENWINDOWS,
			// Token: 0x04000599 RID: 1433
			SM_DBCSENABLED,
			// Token: 0x0400059A RID: 1434
			SM_CMOUSEBUTTONS,
			// Token: 0x0400059B RID: 1435
			SM_SECURE,
			// Token: 0x0400059C RID: 1436
			SM_CXEDGE,
			// Token: 0x0400059D RID: 1437
			SM_CYEDGE,
			// Token: 0x0400059E RID: 1438
			SM_CXMINSPACING,
			// Token: 0x0400059F RID: 1439
			SM_CYMINSPACING,
			// Token: 0x040005A0 RID: 1440
			SM_CXSMICON,
			// Token: 0x040005A1 RID: 1441
			SM_CYSMICON,
			// Token: 0x040005A2 RID: 1442
			SM_CYSMCAPTION,
			// Token: 0x040005A3 RID: 1443
			SM_CXSMSIZE,
			// Token: 0x040005A4 RID: 1444
			SM_CYSMSIZE,
			// Token: 0x040005A5 RID: 1445
			SM_CXMENUSIZE,
			// Token: 0x040005A6 RID: 1446
			SM_CYMENUSIZE,
			// Token: 0x040005A7 RID: 1447
			SM_ARRANGE,
			// Token: 0x040005A8 RID: 1448
			SM_CXMINIMIZED,
			// Token: 0x040005A9 RID: 1449
			SM_CYMINIMIZED,
			// Token: 0x040005AA RID: 1450
			SM_CXMAXTRACK,
			// Token: 0x040005AB RID: 1451
			SM_CYMAXTRACK,
			// Token: 0x040005AC RID: 1452
			SM_CXMAXIMIZED,
			// Token: 0x040005AD RID: 1453
			SM_CYMAXIMIZED,
			// Token: 0x040005AE RID: 1454
			SM_NETWORK,
			// Token: 0x040005AF RID: 1455
			SM_CLEANBOOT = 67,
			// Token: 0x040005B0 RID: 1456
			SM_CXDRAG,
			// Token: 0x040005B1 RID: 1457
			SM_CYDRAG,
			// Token: 0x040005B2 RID: 1458
			SM_SHOWSOUNDS,
			// Token: 0x040005B3 RID: 1459
			SM_CXMENUCHECK,
			// Token: 0x040005B4 RID: 1460
			SM_CYMENUCHECK,
			// Token: 0x040005B5 RID: 1461
			SM_SLOWMACHINE,
			// Token: 0x040005B6 RID: 1462
			SM_MIDEASTENABLED,
			// Token: 0x040005B7 RID: 1463
			SM_MOUSEWHEELPRESENT,
			// Token: 0x040005B8 RID: 1464
			SM_XVIRTUALSCREEN,
			// Token: 0x040005B9 RID: 1465
			SM_YVIRTUALSCREEN,
			// Token: 0x040005BA RID: 1466
			SM_CXVIRTUALSCREEN,
			// Token: 0x040005BB RID: 1467
			SM_CYVIRTUALSCREEN,
			// Token: 0x040005BC RID: 1468
			SM_CMONITORS,
			// Token: 0x040005BD RID: 1469
			SM_SAMEDISPLAYFORMAT,
			// Token: 0x040005BE RID: 1470
			SM_IMMENABLED,
			// Token: 0x040005BF RID: 1471
			SM_CXFOCUSBORDER,
			// Token: 0x040005C0 RID: 1472
			SM_CYFOCUSBORDER,
			// Token: 0x040005C1 RID: 1473
			SM_TABLETPC = 86,
			// Token: 0x040005C2 RID: 1474
			SM_MEDIACENTER,
			// Token: 0x040005C3 RID: 1475
			SM_STARTER,
			// Token: 0x040005C4 RID: 1476
			SM_SERVERR2,
			// Token: 0x040005C5 RID: 1477
			SM_MOUSEHORIZONTALWHEELPRESENT = 91,
			// Token: 0x040005C6 RID: 1478
			SM_CXPADDEDBORDER,
			// Token: 0x040005C7 RID: 1479
			SM_DIGITIZER = 94,
			// Token: 0x040005C8 RID: 1480
			SM_MAXIMUMTOUCHES,
			// Token: 0x040005C9 RID: 1481
			SM_REMOTESESSION = 4096,
			// Token: 0x040005CA RID: 1482
			SM_SHUTTINGDOWN = 8192,
			// Token: 0x040005CB RID: 1483
			SM_REMOTECONTROL,
			// Token: 0x040005CC RID: 1484
			SM_CONVERTIBLESLATEMODE = 8195,
			// Token: 0x040005CD RID: 1485
			SM_SYSTEMDOCKED
		}

		// Token: 0x0200005C RID: 92
		public struct RECT
		{
			// Token: 0x06000148 RID: 328 RVA: 0x00006707 File Offset: 0x00004907
			public RECT(int Left, int Top, int Right, int Bottom)
			{
				this.left = Left;
				this.top = Top;
				this.right = Right;
				this.bottom = Bottom;
			}

			// Token: 0x06000149 RID: 329 RVA: 0x00006727 File Offset: 0x00004927
			public RECT(Rectangle r)
			{
				this = new Imports.RECT(r.Left, r.Top, r.Right, r.Bottom);
			}

			// Token: 0x1700000C RID: 12
			// (get) Token: 0x0600014A RID: 330 RVA: 0x00006750 File Offset: 0x00004950
			// (set) Token: 0x0600014B RID: 331 RVA: 0x00006768 File Offset: 0x00004968
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

			// Token: 0x1700000D RID: 13
			// (get) Token: 0x0600014C RID: 332 RVA: 0x00006788 File Offset: 0x00004988
			// (set) Token: 0x0600014D RID: 333 RVA: 0x000067A0 File Offset: 0x000049A0
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

			// Token: 0x1700000E RID: 14
			// (get) Token: 0x0600014E RID: 334 RVA: 0x000067C0 File Offset: 0x000049C0
			// (set) Token: 0x0600014F RID: 335 RVA: 0x000067DF File Offset: 0x000049DF
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

			// Token: 0x1700000F RID: 15
			// (get) Token: 0x06000150 RID: 336 RVA: 0x000067F0 File Offset: 0x000049F0
			// (set) Token: 0x06000151 RID: 337 RVA: 0x0000680F File Offset: 0x00004A0F
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

			// Token: 0x17000010 RID: 16
			// (get) Token: 0x06000152 RID: 338 RVA: 0x00006820 File Offset: 0x00004A20
			// (set) Token: 0x06000153 RID: 339 RVA: 0x00006843 File Offset: 0x00004A43
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

			// Token: 0x17000011 RID: 17
			// (get) Token: 0x06000154 RID: 340 RVA: 0x00006864 File Offset: 0x00004A64
			// (set) Token: 0x06000155 RID: 341 RVA: 0x00006887 File Offset: 0x00004A87
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

			// Token: 0x06000156 RID: 342 RVA: 0x000068A8 File Offset: 0x00004AA8
			public static implicit operator Rectangle(Imports.RECT r)
			{
				return new Rectangle(r.left, r.top, r.Width, r.Height);
			}

			// Token: 0x06000157 RID: 343 RVA: 0x000068DC File Offset: 0x00004ADC
			public static implicit operator Imports.RECT(Rectangle r)
			{
				return new Imports.RECT(r);
			}

			// Token: 0x06000158 RID: 344 RVA: 0x000068F4 File Offset: 0x00004AF4
			public static bool operator ==(Imports.RECT r1, Imports.RECT r2)
			{
				return r1.Equals(r2);
			}

			// Token: 0x06000159 RID: 345 RVA: 0x00006910 File Offset: 0x00004B10
			public static bool operator !=(Imports.RECT r1, Imports.RECT r2)
			{
				return !r1.Equals(r2);
			}

			// Token: 0x0600015A RID: 346 RVA: 0x00006930 File Offset: 0x00004B30
			public bool Equals(Imports.RECT r)
			{
				return r.left == this.left && r.top == this.top && r.right == this.right && r.bottom == this.bottom;
			}

			// Token: 0x0600015B RID: 347 RVA: 0x00006980 File Offset: 0x00004B80
			public override bool Equals(object obj)
			{
				bool flag = obj is Imports.RECT;
				bool result;
				if (flag)
				{
					result = this.Equals((Imports.RECT)obj);
				}
				else
				{
					bool flag2 = obj is Rectangle;
					result = (flag2 && this.Equals(new Imports.RECT((Rectangle)obj)));
				}
				return result;
			}

			// Token: 0x0600015C RID: 348 RVA: 0x000069D0 File Offset: 0x00004BD0
			public override int GetHashCode()
			{
				return this.GetHashCode();
			}

			// Token: 0x040005CE RID: 1486
			public int left;

			// Token: 0x040005CF RID: 1487
			public int top;

			// Token: 0x040005D0 RID: 1488
			public int right;

			// Token: 0x040005D1 RID: 1489
			public int bottom;
		}

		// Token: 0x0200005D RID: 93
		public struct SimpleRECT
		{
			// Token: 0x040005D2 RID: 1490
			public int left;

			// Token: 0x040005D3 RID: 1491
			public int top;

			// Token: 0x040005D4 RID: 1492
			public int right;

			// Token: 0x040005D5 RID: 1493
			public int bottom;
		}

		// Token: 0x0200005E RID: 94
		public struct POINT
		{
			// Token: 0x040005D6 RID: 1494
			public int x;

			// Token: 0x040005D7 RID: 1495
			public int y;
		}

		// Token: 0x0200005F RID: 95
		public struct MINMAXINFO
		{
			// Token: 0x040005D8 RID: 1496
			public Imports.POINT ptReserved;

			// Token: 0x040005D9 RID: 1497
			public Imports.POINT ptMaxSize;

			// Token: 0x040005DA RID: 1498
			public Imports.POINT ptMaxPosition;

			// Token: 0x040005DB RID: 1499
			public Imports.POINT ptMinTrackSize;

			// Token: 0x040005DC RID: 1500
			public Imports.POINT ptMaxTrackSize;
		}

		// Token: 0x02000060 RID: 96
		[StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)]
		public class MONITORINFO
		{
			// Token: 0x040005DD RID: 1501
			public int cbSize = Marshal.SizeOf(typeof(Imports.MONITORINFO));

			// Token: 0x040005DE RID: 1502
			public Imports.RECT rcMonitor = default(Imports.RECT);

			// Token: 0x040005DF RID: 1503
			public Imports.RECT rcWork = default(Imports.RECT);

			// Token: 0x040005E0 RID: 1504
			public int dwFlags = 0;
		}
	}
}
