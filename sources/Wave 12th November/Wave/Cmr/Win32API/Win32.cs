using System;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Text;

namespace Wave.Cmr.Win32API
{
	// Token: 0x02000022 RID: 34
	public class Win32
	{
		// Token: 0x060000B5 RID: 181
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

		// Token: 0x060000B6 RID: 182
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool AddConsoleAlias(string Source, string Target, string ExeName);

		// Token: 0x060000B7 RID: 183
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool AllocConsole();

		// Token: 0x060000B8 RID: 184
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool AttachConsole(uint dwProcessId);

		// Token: 0x060000B9 RID: 185
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr CreateConsoleScreenBuffer(uint dwDesiredAccess, uint dwShareMode, IntPtr lpSecurityAttributes, uint dwFlags, IntPtr lpScreenBufferData);

		// Token: 0x060000BA RID: 186
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool FillConsoleOutputAttribute(IntPtr hConsoleOutput, ushort wAttribute, uint nLength, Win32.COORD dwWriteCoord, out uint lpNumberOfAttrsWritten);

		// Token: 0x060000BB RID: 187
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool FillConsoleOutputCharacter(IntPtr hConsoleOutput, char cCharacter, uint nLength, Win32.COORD dwWriteCoord, out uint lpNumberOfCharsWritten);

		// Token: 0x060000BC RID: 188
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool FlushConsoleInputBuffer(IntPtr hConsoleInput);

		// Token: 0x060000BD RID: 189
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		public static extern bool FreeConsole();

		// Token: 0x060000BE RID: 190
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GenerateConsoleCtrlEvent(uint dwCtrlEvent, uint dwProcessGroupId);

		// Token: 0x060000BF RID: 191
		[DllImport("kernel32", SetLastError = true)]
		public static extern bool GetConsoleAlias(string Source, out StringBuilder TargetBuffer, uint TargetBufferLength, string ExeName);

		// Token: 0x060000C0 RID: 192
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleAliases(StringBuilder[] lpTargetBuffer, uint targetBufferLength, string lpExeName);

		// Token: 0x060000C1 RID: 193
		[DllImport("kernel32", SetLastError = true)]
		public static extern uint GetConsoleAliasesLength(string ExeName);

		// Token: 0x060000C2 RID: 194
		[DllImport("kernel32", SetLastError = true)]
		public static extern uint GetConsoleAliasExes(out StringBuilder ExeNameBuffer, uint ExeNameBufferLength);

		// Token: 0x060000C3 RID: 195
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleAliasExesLength();

		// Token: 0x060000C4 RID: 196
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleCP();

		// Token: 0x060000C5 RID: 197
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleCursorInfo(IntPtr hConsoleOutput, out Win32.CONSOLE_CURSOR_INFO lpConsoleCursorInfo);

		// Token: 0x060000C6 RID: 198
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleDisplayMode(out uint ModeFlags);

		// Token: 0x060000C7 RID: 199
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern Win32.COORD GetConsoleFontSize(IntPtr hConsoleOutput, int nFont);

		// Token: 0x060000C8 RID: 200
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleHistoryInfo(out Win32.CONSOLE_HISTORY_INFO ConsoleHistoryInfo);

		// Token: 0x060000C9 RID: 201
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleMode(IntPtr hConsoleHandle, out uint lpMode);

		// Token: 0x060000CA RID: 202
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleMode(IntPtr hConsoleHandle, out int lpMode);

		// Token: 0x060000CB RID: 203
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleOriginalTitle(out StringBuilder ConsoleTitle, uint Size);

		// Token: 0x060000CC RID: 204
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleOutputCP();

		// Token: 0x060000CD RID: 205
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleProcessList(out uint[] ProcessList, uint ProcessCount);

		// Token: 0x060000CE RID: 206
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleScreenBufferInfo(IntPtr hConsoleOutput, out Win32.CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);

		// Token: 0x060000CF RID: 207
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleScreenBufferInfoEx(IntPtr hConsoleOutput, ref Win32.CONSOLE_SCREEN_BUFFER_INFO_EX ConsoleScreenBufferInfo);

		// Token: 0x060000D0 RID: 208
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetConsoleSelectionInfo(Win32.CONSOLE_SELECTION_INFO ConsoleSelectionInfo);

		// Token: 0x060000D1 RID: 209
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetConsoleTitle([Out] StringBuilder lpConsoleTitle, uint nSize);

		// Token: 0x060000D2 RID: 210
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr GetConsoleWindow();

		// Token: 0x060000D3 RID: 211
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetCurrentConsoleFont(IntPtr hConsoleOutput, bool bMaximumWindow, out Win32.CONSOLE_FONT_INFO lpConsoleCurrentFont);

		// Token: 0x060000D4 RID: 212
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetCurrentConsoleFontEx(IntPtr ConsoleOutput, bool MaximumWindow, out Win32.CONSOLE_FONT_INFO_EX ConsoleCurrentFont);

		// Token: 0x060000D5 RID: 213
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern Win32.COORD GetLargestConsoleWindowSize(IntPtr hConsoleOutput);

		// Token: 0x060000D6 RID: 214
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetNumberOfConsoleInputEvents(IntPtr hConsoleInput, out uint lpcNumberOfEvents);

		// Token: 0x060000D7 RID: 215
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool GetNumberOfConsoleMouseButtons(ref uint lpNumberOfMouseButtons);

		// Token: 0x060000D8 RID: 216
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern IntPtr GetStdHandle(int nStdHandle);

		// Token: 0x060000D9 RID: 217
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool PeekConsoleInput(IntPtr hConsoleInput, [Out] Win32.INPUT_RECORD[] lpBuffer, uint nLength, out uint lpNumberOfEventsRead);

		// Token: 0x060000DA RID: 218
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsole(IntPtr hConsoleInput, [Out] StringBuilder lpBuffer, uint nNumberOfCharsToRead, out uint lpNumberOfCharsRead, IntPtr lpReserved);

		// Token: 0x060000DB RID: 219
		[DllImport("kernel32.dll", CharSet = CharSet.Unicode, EntryPoint = "ReadConsoleInputW")]
		public static extern bool ReadConsoleInput(IntPtr hConsoleInput, [Out] Win32.INPUT_RECORD[] lpBuffer, uint nLength, out uint lpNumberOfEventsRead);

		// Token: 0x060000DC RID: 220
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsoleOutput(IntPtr hConsoleOutput, [Out] Win32.CHAR_INFO[] lpBuffer, Win32.COORD dwBufferSize, Win32.COORD dwBufferCoord, ref Win32.SMALL_RECT lpReadRegion);

		// Token: 0x060000DD RID: 221
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsoleOutputAttribute(IntPtr hConsoleOutput, [Out] ushort[] lpAttribute, uint nLength, Win32.COORD dwReadCoord, out uint lpNumberOfAttrsRead);

		// Token: 0x060000DE RID: 222
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ReadConsoleOutputCharacter(IntPtr hConsoleOutput, [Out] StringBuilder lpCharacter, uint nLength, Win32.COORD dwReadCoord, out uint lpNumberOfCharsRead);

		// Token: 0x060000DF RID: 223
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool ScrollConsoleScreenBuffer(IntPtr hConsoleOutput, [In] ref Win32.SMALL_RECT lpScrollRectangle, IntPtr lpClipRectangle, Win32.COORD dwDestinationOrigin, [In] ref Win32.CHAR_INFO lpFill);

		// Token: 0x060000E0 RID: 224
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleActiveScreenBuffer(IntPtr hConsoleOutput);

		// Token: 0x060000E1 RID: 225
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCP(uint wCodePageID);

		// Token: 0x060000E2 RID: 226
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCtrlHandler(Win32.ConsoleCtrlDelegate HandlerRoutine, bool Add);

		// Token: 0x060000E3 RID: 227
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCursorInfo(IntPtr hConsoleOutput, [In] ref Win32.CONSOLE_CURSOR_INFO lpConsoleCursorInfo);

		// Token: 0x060000E4 RID: 228
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleCursorPosition(IntPtr hConsoleOutput, Win32.COORD dwCursorPosition);

		// Token: 0x060000E5 RID: 229
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleDisplayMode(IntPtr ConsoleOutput, uint Flags, out Win32.COORD NewScreenBufferDimensions);

		// Token: 0x060000E6 RID: 230
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleHistoryInfo(Win32.CONSOLE_HISTORY_INFO ConsoleHistoryInfo);

		// Token: 0x060000E7 RID: 231
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleMode(IntPtr hConsoleHandle, uint dwMode);

		// Token: 0x060000E8 RID: 232
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleMode(IntPtr hConsoleHandle, int dwMode);

		// Token: 0x060000E9 RID: 233
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleOutputCP(uint wCodePageID);

		// Token: 0x060000EA RID: 234
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleScreenBufferInfoEx(IntPtr ConsoleOutput, Win32.CONSOLE_SCREEN_BUFFER_INFO_EX ConsoleScreenBufferInfoEx);

		// Token: 0x060000EB RID: 235
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleScreenBufferSize(IntPtr hConsoleOutput, Win32.COORD dwSize);

		// Token: 0x060000EC RID: 236
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleTextAttribute(IntPtr hConsoleOutput, ushort wAttributes);

		// Token: 0x060000ED RID: 237
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleTitle(string lpConsoleTitle);

		// Token: 0x060000EE RID: 238
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetConsoleWindowInfo(IntPtr hConsoleOutput, bool bAbsolute, [In] ref Win32.SMALL_RECT lpConsoleWindow);

		// Token: 0x060000EF RID: 239
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetCurrentConsoleFontEx(IntPtr ConsoleOutput, bool MaximumWindow, ref Win32.CONSOLE_FONT_INFO_EX ConsoleCurrentFontEx);

		// Token: 0x060000F0 RID: 240
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetCurrentConsoleFontEx(IntPtr ConsoleOutput, bool MaximumWindow, Win32.CONSOLE_FONT_INFO_EX ConsoleCurrentFontEx);

		// Token: 0x060000F1 RID: 241
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetStdHandle(uint nStdHandle, IntPtr hHandle);

		// Token: 0x060000F2 RID: 242
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsole(IntPtr hConsoleOutput, string lpBuffer, uint nNumberOfCharsToWrite, out uint lpNumberOfCharsWritten, IntPtr lpReserved);

		// Token: 0x060000F3 RID: 243
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleInput(IntPtr hConsoleInput, Win32.INPUT_RECORD[] lpBuffer, uint nLength, out uint lpNumberOfEventsWritten);

		// Token: 0x060000F4 RID: 244
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleOutput(IntPtr hConsoleOutput, Win32.CHAR_INFO[] lpBuffer, Win32.COORD dwBufferSize, Win32.COORD dwBufferCoord, ref Win32.SMALL_RECT lpWriteRegion);

		// Token: 0x060000F5 RID: 245
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleOutputAttribute(IntPtr hConsoleOutput, ushort[] lpAttribute, uint nLength, Win32.COORD dwWriteCoord, out uint lpNumberOfAttrsWritten);

		// Token: 0x060000F6 RID: 246
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool WriteConsoleOutputCharacter(IntPtr hConsoleOutput, string lpCharacter, uint nLength, Win32.COORD dwWriteCoord, out uint lpNumberOfCharsWritten);

		// Token: 0x060000F7 RID: 247
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool GetWindowRect(IntPtr hwnd, out Win32.RECT lpRect);

		// Token: 0x060000F8 RID: 248
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool GetWindowRect(IntPtr hwnd, out Win32.SimpleRECT lpRect);

		// Token: 0x060000F9 RID: 249
		[DllImport("user32.dll")]
		public static extern int GetSystemMetrics(Win32.SystemMetric smIndex);

		// Token: 0x060000FA RID: 250
		[DllImport("user32.dll", SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int x, int y, int cx, int cy, uint uFlags);

		// Token: 0x060000FB RID: 251
		[DllImport("user32")]
		public static extern bool GetMonitorInfo(IntPtr hMonitor, Win32.MONITORINFO lpmi);

		// Token: 0x060000FC RID: 252
		[DllImport("User32")]
		public static extern IntPtr MonitorFromWindow(IntPtr handle, int flags);

		// Token: 0x060000FD RID: 253
		[DllImport("User32.dll", SetLastError = true)]
		public static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

		// Token: 0x060000FE RID: 254
		[DllImport("user32.dll", SetLastError = true)]
		public static extern bool MoveWindow(IntPtr hWnd, int X, int Y, int Width, int Height, bool Repaint);

		// Token: 0x060000FF RID: 255
		[DllImport("user32.dll", SetLastError = true)]
		public static extern short GetAsyncKeyState(int key);

		// Token: 0x06000100 RID: 256
		[DllImport("user32.dll", SetLastError = true)]
		public static extern short GetAsyncKeyState(short key);

		// Token: 0x06000101 RID: 257
		[DllImport("user32.dll")]
		public static extern short GetAsyncKeyState(Win32.VirtualKeys vKey);

		// Token: 0x06000102 RID: 258
		[DllImport("user32.dll")]
		public static extern short GetAsyncKeyState(Win32.VK vKey);

		// Token: 0x06000103 RID: 259
		[DllImport("user32.dll", SetLastError = true)]
		public static extern int GetWindowLong(IntPtr hWnd, int nIndex);

		// Token: 0x06000104 RID: 260
		[DllImport("user32.dll")]
		public static extern int SetWindowLong(IntPtr hWnd, int nIndex, uint dwNewLong);

		// Token: 0x06000105 RID: 261
		[DllImport("user32.dll")]
		public static extern int SetWindowLong(IntPtr hWnd, int nIndex, int dwNewLong);

		// Token: 0x06000106 RID: 262
		[DllImport("user32.dll")]
		public static extern bool SetLayeredWindowAttributes(IntPtr hwnd, uint crKey, byte bAlpha, uint dwFlags);

		// Token: 0x06000107 RID: 263
		[DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
		public static extern IntPtr GetForegroundWindow();

		// Token: 0x06000108 RID: 264
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern int GetWindowThreadProcessId(IntPtr handle, out int processId);

		// Token: 0x06000109 RID: 265
		[DllImport("user32.dll")]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool SetForegroundWindow(IntPtr hWnd);

		// Token: 0x0600010A RID: 266
		[DllImport("user32.dll")]
		public static extern uint SendInput(uint nInputs, [MarshalAs(UnmanagedType.LPArray)] [In] Win32.INPUT[] pInputs, int cbSize);

		// Token: 0x0600010B RID: 267
		[DllImport("user32.dll")]
		public static extern void mouse_event(uint dwFlags, int dx, int dy, uint dwData, int dwExtraInfo);

		// Token: 0x0600010C RID: 268
		[DllImport("user32.dll")]
		public static extern void mouse_event(Win32.MouseEventFlags dwFlags, int dx, int dy, uint dwData, int dwExtraInfo);

		// Token: 0x0600010D RID: 269
		[DllImport("user32")]
		public static extern int SetCursorPos(int x, int y);

		// Token: 0x04000071 RID: 113
		public const int SWP_ASYNCWINDOWPOS = 16384;

		// Token: 0x04000072 RID: 114
		public const int SWP_DEFERERASE = 8192;

		// Token: 0x04000073 RID: 115
		public const int SWP_DRAWFRAME = 32;

		// Token: 0x04000074 RID: 116
		public const int SWP_FRAMECHANGED = 32;

		// Token: 0x04000075 RID: 117
		public const int SWP_HIDEWINDOW = 128;

		// Token: 0x04000076 RID: 118
		public const int SWP_NOACTIVATE = 16;

		// Token: 0x04000077 RID: 119
		public const int SWP_NOCOPYBITS = 256;

		// Token: 0x04000078 RID: 120
		public const int SWP_NOMOVE = 2;

		// Token: 0x04000079 RID: 121
		public const int SWP_NOOWNERZORDER = 512;

		// Token: 0x0400007A RID: 122
		public const int SWP_NOREDRAW = 8;

		// Token: 0x0400007B RID: 123
		public const int SWP_NOREPOSITION = 512;

		// Token: 0x0400007C RID: 124
		public const int SWP_NOSENDCHANGING = 1024;

		// Token: 0x0400007D RID: 125
		public const int SWP_NOSIZE = 1;

		// Token: 0x0400007E RID: 126
		public const int SWP_NOZORDER = 4;

		// Token: 0x0400007F RID: 127
		public const int SWP_SHOWWINDOW = 64;

		// Token: 0x04000080 RID: 128
		public const int HWND_TOP = 0;

		// Token: 0x04000081 RID: 129
		public const int HWND_BOTTOM = 1;

		// Token: 0x04000082 RID: 130
		public const int HWND_TOPMOST = -1;

		// Token: 0x04000083 RID: 131
		public const int HWND_NOTOPMOST = -2;

		// Token: 0x04000084 RID: 132
		public static IntPtr INVALID_HANDLE_VALUE = new IntPtr(-1);

		// Token: 0x04000085 RID: 133
		public static readonly int TMPF_TRUETYPE = 4;

		// Token: 0x04000086 RID: 134
		public static readonly int STD_OUTPUT_HANDLE = -11;

		// Token: 0x02000031 RID: 49
		// (Invoke) Token: 0x0600013F RID: 319
		public delegate bool ConsoleCtrlDelegate(Win32.CtrlTypes CtrlType);

		// Token: 0x02000032 RID: 50
		public struct COORD
		{
			// Token: 0x06000142 RID: 322 RVA: 0x00004E93 File Offset: 0x00003093
			internal COORD(short x, short y)
			{
				this.X = x;
				this.Y = y;
			}

			// Token: 0x040000C4 RID: 196
			internal short X;

			// Token: 0x040000C5 RID: 197
			internal short Y;
		}

		// Token: 0x02000033 RID: 51
		public struct SMALL_RECT
		{
			// Token: 0x040000C6 RID: 198
			public short Left;

			// Token: 0x040000C7 RID: 199
			public short Top;

			// Token: 0x040000C8 RID: 200
			public short Right;

			// Token: 0x040000C9 RID: 201
			public short Bottom;
		}

		// Token: 0x02000034 RID: 52
		public struct CONSOLE_SCREEN_BUFFER_INFO
		{
			// Token: 0x040000CA RID: 202
			public Win32.COORD dwSize;

			// Token: 0x040000CB RID: 203
			public Win32.COORD dwCursorPosition;

			// Token: 0x040000CC RID: 204
			public short wAttributes;

			// Token: 0x040000CD RID: 205
			public Win32.SMALL_RECT srWindow;

			// Token: 0x040000CE RID: 206
			public Win32.COORD dwMaximumWindowSize;
		}

		// Token: 0x02000035 RID: 53
		public struct CONSOLE_SCREEN_BUFFER_INFO_EX
		{
			// Token: 0x06000143 RID: 323 RVA: 0x00004EA4 File Offset: 0x000030A4
			public static Win32.CONSOLE_SCREEN_BUFFER_INFO_EX Create()
			{
				return new Win32.CONSOLE_SCREEN_BUFFER_INFO_EX
				{
					cbSize = 96U
				};
			}

			// Token: 0x040000CF RID: 207
			public uint cbSize;

			// Token: 0x040000D0 RID: 208
			public Win32.COORD dwSize;

			// Token: 0x040000D1 RID: 209
			public Win32.COORD dwCursorPosition;

			// Token: 0x040000D2 RID: 210
			public short wAttributes;

			// Token: 0x040000D3 RID: 211
			public Win32.SMALL_RECT srWindow;

			// Token: 0x040000D4 RID: 212
			public Win32.COORD dwMaximumWindowSize;

			// Token: 0x040000D5 RID: 213
			public ushort wPopupAttributes;

			// Token: 0x040000D6 RID: 214
			public bool bFullscreenSupported;

			// Token: 0x040000D7 RID: 215
			[MarshalAs(UnmanagedType.ByValArray, SizeConst = 16)]
			public Win32.COLORREF[] ColorTable;
		}

		// Token: 0x02000036 RID: 54
		public struct COLORREF
		{
			// Token: 0x06000144 RID: 324 RVA: 0x00004EC3 File Offset: 0x000030C3
			public COLORREF(Color color)
			{
				this.ColorDWORD = (uint)((int)color.R + ((int)color.G << 8) + ((int)color.B << 16));
			}

			// Token: 0x06000145 RID: 325 RVA: 0x00004EE7 File Offset: 0x000030E7
			public Color GetColor()
			{
				return Color.FromArgb((int)(255U & this.ColorDWORD), (int)(65280U & this.ColorDWORD) >> 8, (int)(16711680U & this.ColorDWORD) >> 16);
			}

			// Token: 0x06000146 RID: 326 RVA: 0x00004F17 File Offset: 0x00003117
			public void SetColor(Color color)
			{
				this.ColorDWORD = (uint)((int)color.R + ((int)color.G << 8) + ((int)color.B << 16));
			}

			// Token: 0x040000D8 RID: 216
			public uint ColorDWORD;
		}

		// Token: 0x02000037 RID: 55
		public struct CONSOLE_FONT_INFO
		{
			// Token: 0x040000D9 RID: 217
			public int nFont;

			// Token: 0x040000DA RID: 218
			public Win32.COORD dwFontSize;
		}

		// Token: 0x02000038 RID: 56
		[StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)]
		public struct CONSOLE_FONT_INFO_EX
		{
			// Token: 0x040000DB RID: 219
			internal uint cbSize;

			// Token: 0x040000DC RID: 220
			internal uint nFont;

			// Token: 0x040000DD RID: 221
			internal Win32.COORD dwFontSize;

			// Token: 0x040000DE RID: 222
			internal int FontFamily;

			// Token: 0x040000DF RID: 223
			internal int FontWeight;

			// Token: 0x040000E0 RID: 224
			[FixedBuffer(typeof(char), 32)]
			internal Win32.CONSOLE_FONT_INFO_EX.<FaceName>e__FixedBuffer FaceName;

			// Token: 0x0200005E RID: 94
			[CompilerGenerated]
			[UnsafeValueType]
			[StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode, Size = 64)]
			public struct <FaceName>e__FixedBuffer
			{
				// Token: 0x040003F4 RID: 1012
				public char FixedElementField;
			}
		}

		// Token: 0x02000039 RID: 57
		[StructLayout(LayoutKind.Explicit)]
		public struct INPUT_RECORD
		{
			// Token: 0x040000E1 RID: 225
			[FieldOffset(0)]
			public ushort EventType;

			// Token: 0x040000E2 RID: 226
			[FieldOffset(4)]
			public Win32.KEY_EVENT_RECORD KeyEvent;

			// Token: 0x040000E3 RID: 227
			[FieldOffset(4)]
			public Win32.MOUSE_EVENT_RECORD MouseEvent;

			// Token: 0x040000E4 RID: 228
			[FieldOffset(4)]
			public Win32.WINDOW_BUFFER_SIZE_RECORD WindowBufferSizeEvent;

			// Token: 0x040000E5 RID: 229
			[FieldOffset(4)]
			public Win32.MENU_EVENT_RECORD MenuEvent;

			// Token: 0x040000E6 RID: 230
			[FieldOffset(4)]
			public Win32.FOCUS_EVENT_RECORD FocusEvent;
		}

		// Token: 0x0200003A RID: 58
		[StructLayout(LayoutKind.Explicit, CharSet = CharSet.Unicode)]
		public struct KEY_EVENT_RECORD
		{
			// Token: 0x040000E7 RID: 231
			[FieldOffset(0)]
			[MarshalAs(UnmanagedType.Bool)]
			public bool bKeyDown;

			// Token: 0x040000E8 RID: 232
			[FieldOffset(4)]
			[MarshalAs(UnmanagedType.U2)]
			public ushort wRepeatCount;

			// Token: 0x040000E9 RID: 233
			[FieldOffset(6)]
			[MarshalAs(UnmanagedType.U2)]
			public ushort wVirtualKeyCode;

			// Token: 0x040000EA RID: 234
			[FieldOffset(8)]
			[MarshalAs(UnmanagedType.U2)]
			public ushort wVirtualScanCode;

			// Token: 0x040000EB RID: 235
			[FieldOffset(10)]
			public char UnicodeChar;

			// Token: 0x040000EC RID: 236
			[FieldOffset(12)]
			[MarshalAs(UnmanagedType.U4)]
			public uint dwControlKeyState;
		}

		// Token: 0x0200003B RID: 59
		public struct MOUSE_EVENT_RECORD
		{
			// Token: 0x040000ED RID: 237
			public Win32.COORD dwMousePosition;

			// Token: 0x040000EE RID: 238
			public uint dwButtonState;

			// Token: 0x040000EF RID: 239
			public uint dwControlKeyState;

			// Token: 0x040000F0 RID: 240
			public uint dwEventFlags;
		}

		// Token: 0x0200003C RID: 60
		public struct WINDOW_BUFFER_SIZE_RECORD
		{
			// Token: 0x06000147 RID: 327 RVA: 0x00004F3B File Offset: 0x0000313B
			public WINDOW_BUFFER_SIZE_RECORD(short x, short y)
			{
				this.dwSize = default(Win32.COORD);
				this.dwSize.X = x;
				this.dwSize.Y = y;
			}

			// Token: 0x040000F1 RID: 241
			public Win32.COORD dwSize;
		}

		// Token: 0x0200003D RID: 61
		public struct MENU_EVENT_RECORD
		{
			// Token: 0x040000F2 RID: 242
			public uint dwCommandId;
		}

		// Token: 0x0200003E RID: 62
		public struct FOCUS_EVENT_RECORD
		{
			// Token: 0x040000F3 RID: 243
			public uint bSetFocus;
		}

		// Token: 0x0200003F RID: 63
		[StructLayout(LayoutKind.Explicit)]
		public struct CHAR_INFO
		{
			// Token: 0x040000F4 RID: 244
			[FieldOffset(0)]
			private char UnicodeChar;

			// Token: 0x040000F5 RID: 245
			[FieldOffset(0)]
			private char AsciiChar;

			// Token: 0x040000F6 RID: 246
			[FieldOffset(2)]
			private ushort Attributes;
		}

		// Token: 0x02000040 RID: 64
		public struct CONSOLE_CURSOR_INFO
		{
			// Token: 0x040000F7 RID: 247
			private uint Size;

			// Token: 0x040000F8 RID: 248
			private bool Visible;
		}

		// Token: 0x02000041 RID: 65
		public struct CONSOLE_HISTORY_INFO
		{
			// Token: 0x040000F9 RID: 249
			private ushort cbSize;

			// Token: 0x040000FA RID: 250
			private ushort HistoryBufferSize;

			// Token: 0x040000FB RID: 251
			private ushort NumberOfHistoryBuffers;

			// Token: 0x040000FC RID: 252
			private uint dwFlags;
		}

		// Token: 0x02000042 RID: 66
		public struct CONSOLE_SELECTION_INFO
		{
			// Token: 0x040000FD RID: 253
			private uint Flags;

			// Token: 0x040000FE RID: 254
			private Win32.COORD SelectionAnchor;

			// Token: 0x040000FF RID: 255
			private Win32.SMALL_RECT Selection;

			// Token: 0x04000100 RID: 256
			private const uint CONSOLE_MOUSE_DOWN = 8U;

			// Token: 0x04000101 RID: 257
			private const uint CONSOLE_MOUSE_SELECTION = 4U;

			// Token: 0x04000102 RID: 258
			private const uint CONSOLE_NO_SELECTION = 0U;

			// Token: 0x04000103 RID: 259
			private const uint CONSOLE_SELECTION_IN_PROGRESS = 1U;

			// Token: 0x04000104 RID: 260
			private const uint CONSOLE_SELECTION_NOT_EMPTY = 2U;
		}

		// Token: 0x02000043 RID: 67
		public enum CtrlTypes : uint
		{
			// Token: 0x04000106 RID: 262
			CTRL_C_EVENT,
			// Token: 0x04000107 RID: 263
			CTRL_BREAK_EVENT,
			// Token: 0x04000108 RID: 264
			CTRL_CLOSE_EVENT,
			// Token: 0x04000109 RID: 265
			CTRL_LOGOFF_EVENT = 5U,
			// Token: 0x0400010A RID: 266
			CTRL_SHUTDOWN_EVENT
		}

		// Token: 0x02000044 RID: 68
		[Flags]
		public enum MouseEventFlags : uint
		{
			// Token: 0x0400010C RID: 268
			LEFTDOWN = 2U,
			// Token: 0x0400010D RID: 269
			LEFTUP = 4U,
			// Token: 0x0400010E RID: 270
			MIDDLEDOWN = 32U,
			// Token: 0x0400010F RID: 271
			MIDDLEUP = 64U,
			// Token: 0x04000110 RID: 272
			MOVE = 1U,
			// Token: 0x04000111 RID: 273
			ABSOLUTE = 32768U,
			// Token: 0x04000112 RID: 274
			RIGHTDOWN = 8U,
			// Token: 0x04000113 RID: 275
			RIGHTUP = 16U,
			// Token: 0x04000114 RID: 276
			WHEEL = 2048U,
			// Token: 0x04000115 RID: 277
			XDOWN = 128U,
			// Token: 0x04000116 RID: 278
			XUP = 256U
		}

		// Token: 0x02000045 RID: 69
		public enum MouseEventDataXButtons : uint
		{
			// Token: 0x04000118 RID: 280
			XBUTTON1 = 1U,
			// Token: 0x04000119 RID: 281
			XBUTTON2
		}

		// Token: 0x02000046 RID: 70
		public struct INPUT
		{
			// Token: 0x17000010 RID: 16
			// (get) Token: 0x06000148 RID: 328 RVA: 0x00004F61 File Offset: 0x00003161
			internal static int Size
			{
				get
				{
					return Marshal.SizeOf(typeof(Win32.INPUT));
				}
			}

			// Token: 0x0400011A RID: 282
			internal uint Type;

			// Token: 0x0400011B RID: 283
			internal Win32.InputUnion U;
		}

		// Token: 0x02000047 RID: 71
		[StructLayout(LayoutKind.Explicit)]
		public struct InputUnion
		{
			// Token: 0x0400011C RID: 284
			[FieldOffset(0)]
			internal Win32.MOUSEINPUT MouseInput;

			// Token: 0x0400011D RID: 285
			[FieldOffset(0)]
			internal Win32.KEYBDINPUT KeyboardInput;

			// Token: 0x0400011E RID: 286
			[FieldOffset(0)]
			internal Win32.HARDWAREINPUT HardwareInput;
		}

		// Token: 0x02000048 RID: 72
		internal struct HARDWAREINPUT
		{
			// Token: 0x0400011F RID: 287
			internal int uMsg;

			// Token: 0x04000120 RID: 288
			internal short wParamL;

			// Token: 0x04000121 RID: 289
			internal short wParamH;
		}

		// Token: 0x02000049 RID: 73
		public struct MOUSEINPUT
		{
			// Token: 0x04000122 RID: 290
			internal int dx;

			// Token: 0x04000123 RID: 291
			internal int dy;

			// Token: 0x04000124 RID: 292
			internal int mouseData;

			// Token: 0x04000125 RID: 293
			internal Win32.MOUSEEVENTF dwFlags;

			// Token: 0x04000126 RID: 294
			internal uint time;

			// Token: 0x04000127 RID: 295
			internal UIntPtr dwExtraInfo;
		}

		// Token: 0x0200004A RID: 74
		internal struct KEYBDINPUT
		{
			// Token: 0x04000128 RID: 296
			internal Win32.VirtualKeys wVk;

			// Token: 0x04000129 RID: 297
			internal Win32.ScanCodeShort wScan;

			// Token: 0x0400012A RID: 298
			internal Win32.KEYEVENTF dwFlags;

			// Token: 0x0400012B RID: 299
			internal int time;

			// Token: 0x0400012C RID: 300
			internal UIntPtr dwExtraInfo;
		}

		// Token: 0x0200004B RID: 75
		public enum ScanCodeShort : short
		{
			// Token: 0x0400012E RID: 302
			LBUTTON,
			// Token: 0x0400012F RID: 303
			RBUTTON = 0,
			// Token: 0x04000130 RID: 304
			CANCEL = 70,
			// Token: 0x04000131 RID: 305
			MBUTTON = 0,
			// Token: 0x04000132 RID: 306
			XBUTTON1 = 0,
			// Token: 0x04000133 RID: 307
			XBUTTON2 = 0,
			// Token: 0x04000134 RID: 308
			BACK = 14,
			// Token: 0x04000135 RID: 309
			TAB,
			// Token: 0x04000136 RID: 310
			CLEAR = 76,
			// Token: 0x04000137 RID: 311
			RETURN = 28,
			// Token: 0x04000138 RID: 312
			SHIFT = 42,
			// Token: 0x04000139 RID: 313
			CONTROL = 29,
			// Token: 0x0400013A RID: 314
			MENU = 56,
			// Token: 0x0400013B RID: 315
			PAUSE = 0,
			// Token: 0x0400013C RID: 316
			CAPITAL = 58,
			// Token: 0x0400013D RID: 317
			KANA = 0,
			// Token: 0x0400013E RID: 318
			HANGUL = 0,
			// Token: 0x0400013F RID: 319
			JUNJA = 0,
			// Token: 0x04000140 RID: 320
			FINAL = 0,
			// Token: 0x04000141 RID: 321
			HANJA = 0,
			// Token: 0x04000142 RID: 322
			KANJI = 0,
			// Token: 0x04000143 RID: 323
			ESCAPE,
			// Token: 0x04000144 RID: 324
			CONVERT = 0,
			// Token: 0x04000145 RID: 325
			NONCONVERT = 0,
			// Token: 0x04000146 RID: 326
			ACCEPT = 0,
			// Token: 0x04000147 RID: 327
			MODECHANGE = 0,
			// Token: 0x04000148 RID: 328
			SPACE = 57,
			// Token: 0x04000149 RID: 329
			PRIOR = 73,
			// Token: 0x0400014A RID: 330
			NEXT = 81,
			// Token: 0x0400014B RID: 331
			END = 79,
			// Token: 0x0400014C RID: 332
			HOME = 71,
			// Token: 0x0400014D RID: 333
			LEFT = 75,
			// Token: 0x0400014E RID: 334
			UP = 72,
			// Token: 0x0400014F RID: 335
			RIGHT = 77,
			// Token: 0x04000150 RID: 336
			DOWN = 80,
			// Token: 0x04000151 RID: 337
			SELECT = 0,
			// Token: 0x04000152 RID: 338
			PRINT = 0,
			// Token: 0x04000153 RID: 339
			EXECUTE = 0,
			// Token: 0x04000154 RID: 340
			SNAPSHOT = 84,
			// Token: 0x04000155 RID: 341
			INSERT = 82,
			// Token: 0x04000156 RID: 342
			DELETE,
			// Token: 0x04000157 RID: 343
			HELP = 99,
			// Token: 0x04000158 RID: 344
			KEY_0 = 11,
			// Token: 0x04000159 RID: 345
			KEY_1 = 2,
			// Token: 0x0400015A RID: 346
			KEY_2,
			// Token: 0x0400015B RID: 347
			KEY_3,
			// Token: 0x0400015C RID: 348
			KEY_4,
			// Token: 0x0400015D RID: 349
			KEY_5,
			// Token: 0x0400015E RID: 350
			KEY_6,
			// Token: 0x0400015F RID: 351
			KEY_7,
			// Token: 0x04000160 RID: 352
			KEY_8,
			// Token: 0x04000161 RID: 353
			KEY_9,
			// Token: 0x04000162 RID: 354
			KEY_A = 30,
			// Token: 0x04000163 RID: 355
			KEY_B = 48,
			// Token: 0x04000164 RID: 356
			KEY_C = 46,
			// Token: 0x04000165 RID: 357
			KEY_D = 32,
			// Token: 0x04000166 RID: 358
			KEY_E = 18,
			// Token: 0x04000167 RID: 359
			KEY_F = 33,
			// Token: 0x04000168 RID: 360
			KEY_G,
			// Token: 0x04000169 RID: 361
			KEY_H,
			// Token: 0x0400016A RID: 362
			KEY_I = 23,
			// Token: 0x0400016B RID: 363
			KEY_J = 36,
			// Token: 0x0400016C RID: 364
			KEY_K,
			// Token: 0x0400016D RID: 365
			KEY_L,
			// Token: 0x0400016E RID: 366
			KEY_M = 50,
			// Token: 0x0400016F RID: 367
			KEY_N = 49,
			// Token: 0x04000170 RID: 368
			KEY_O = 24,
			// Token: 0x04000171 RID: 369
			KEY_P,
			// Token: 0x04000172 RID: 370
			KEY_Q = 16,
			// Token: 0x04000173 RID: 371
			KEY_R = 19,
			// Token: 0x04000174 RID: 372
			KEY_S = 31,
			// Token: 0x04000175 RID: 373
			KEY_T = 20,
			// Token: 0x04000176 RID: 374
			KEY_U = 22,
			// Token: 0x04000177 RID: 375
			KEY_V = 47,
			// Token: 0x04000178 RID: 376
			KEY_W = 17,
			// Token: 0x04000179 RID: 377
			KEY_X = 45,
			// Token: 0x0400017A RID: 378
			KEY_Y = 21,
			// Token: 0x0400017B RID: 379
			KEY_Z = 44,
			// Token: 0x0400017C RID: 380
			LWIN = 91,
			// Token: 0x0400017D RID: 381
			RWIN,
			// Token: 0x0400017E RID: 382
			APPS,
			// Token: 0x0400017F RID: 383
			SLEEP = 95,
			// Token: 0x04000180 RID: 384
			NUMPAD0 = 82,
			// Token: 0x04000181 RID: 385
			NUMPAD1 = 79,
			// Token: 0x04000182 RID: 386
			NUMPAD2,
			// Token: 0x04000183 RID: 387
			NUMPAD3,
			// Token: 0x04000184 RID: 388
			NUMPAD4 = 75,
			// Token: 0x04000185 RID: 389
			NUMPAD5,
			// Token: 0x04000186 RID: 390
			NUMPAD6,
			// Token: 0x04000187 RID: 391
			NUMPAD7 = 71,
			// Token: 0x04000188 RID: 392
			NUMPAD8,
			// Token: 0x04000189 RID: 393
			NUMPAD9,
			// Token: 0x0400018A RID: 394
			MULTIPLY = 55,
			// Token: 0x0400018B RID: 395
			ADD = 78,
			// Token: 0x0400018C RID: 396
			SEPARATOR = 0,
			// Token: 0x0400018D RID: 397
			SUBTRACT = 74,
			// Token: 0x0400018E RID: 398
			DECIMAL = 83,
			// Token: 0x0400018F RID: 399
			DIVIDE = 53,
			// Token: 0x04000190 RID: 400
			F1 = 59,
			// Token: 0x04000191 RID: 401
			F2,
			// Token: 0x04000192 RID: 402
			F3,
			// Token: 0x04000193 RID: 403
			F4,
			// Token: 0x04000194 RID: 404
			F5,
			// Token: 0x04000195 RID: 405
			F6,
			// Token: 0x04000196 RID: 406
			F7,
			// Token: 0x04000197 RID: 407
			F8,
			// Token: 0x04000198 RID: 408
			F9,
			// Token: 0x04000199 RID: 409
			F10,
			// Token: 0x0400019A RID: 410
			F11 = 87,
			// Token: 0x0400019B RID: 411
			F12,
			// Token: 0x0400019C RID: 412
			F13 = 100,
			// Token: 0x0400019D RID: 413
			F14,
			// Token: 0x0400019E RID: 414
			F15,
			// Token: 0x0400019F RID: 415
			F16,
			// Token: 0x040001A0 RID: 416
			F17,
			// Token: 0x040001A1 RID: 417
			F18,
			// Token: 0x040001A2 RID: 418
			F19,
			// Token: 0x040001A3 RID: 419
			F20,
			// Token: 0x040001A4 RID: 420
			F21,
			// Token: 0x040001A5 RID: 421
			F22,
			// Token: 0x040001A6 RID: 422
			F23,
			// Token: 0x040001A7 RID: 423
			F24 = 118,
			// Token: 0x040001A8 RID: 424
			NUMLOCK = 69,
			// Token: 0x040001A9 RID: 425
			SCROLL,
			// Token: 0x040001AA RID: 426
			LSHIFT = 42,
			// Token: 0x040001AB RID: 427
			RSHIFT = 54,
			// Token: 0x040001AC RID: 428
			LCONTROL = 29,
			// Token: 0x040001AD RID: 429
			RCONTROL = 29,
			// Token: 0x040001AE RID: 430
			LMENU = 56,
			// Token: 0x040001AF RID: 431
			RMENU = 56,
			// Token: 0x040001B0 RID: 432
			BROWSER_BACK = 106,
			// Token: 0x040001B1 RID: 433
			BROWSER_FORWARD = 105,
			// Token: 0x040001B2 RID: 434
			BROWSER_REFRESH = 103,
			// Token: 0x040001B3 RID: 435
			BROWSER_STOP,
			// Token: 0x040001B4 RID: 436
			BROWSER_SEARCH = 101,
			// Token: 0x040001B5 RID: 437
			BROWSER_FAVORITES,
			// Token: 0x040001B6 RID: 438
			BROWSER_HOME = 50,
			// Token: 0x040001B7 RID: 439
			VOLUME_MUTE = 32,
			// Token: 0x040001B8 RID: 440
			VOLUME_DOWN = 46,
			// Token: 0x040001B9 RID: 441
			VOLUME_UP = 48,
			// Token: 0x040001BA RID: 442
			MEDIA_NEXT_TRACK = 25,
			// Token: 0x040001BB RID: 443
			MEDIA_PREV_TRACK = 16,
			// Token: 0x040001BC RID: 444
			MEDIA_STOP = 36,
			// Token: 0x040001BD RID: 445
			MEDIA_PLAY_PAUSE = 34,
			// Token: 0x040001BE RID: 446
			LAUNCH_MAIL = 108,
			// Token: 0x040001BF RID: 447
			LAUNCH_MEDIA_SELECT,
			// Token: 0x040001C0 RID: 448
			LAUNCH_APP1 = 107,
			// Token: 0x040001C1 RID: 449
			LAUNCH_APP2 = 33,
			// Token: 0x040001C2 RID: 450
			OEM_1 = 39,
			// Token: 0x040001C3 RID: 451
			OEM_PLUS = 13,
			// Token: 0x040001C4 RID: 452
			OEM_COMMA = 51,
			// Token: 0x040001C5 RID: 453
			OEM_MINUS = 12,
			// Token: 0x040001C6 RID: 454
			OEM_PERIOD = 52,
			// Token: 0x040001C7 RID: 455
			OEM_2,
			// Token: 0x040001C8 RID: 456
			OEM_3 = 41,
			// Token: 0x040001C9 RID: 457
			OEM_4 = 26,
			// Token: 0x040001CA RID: 458
			OEM_5 = 43,
			// Token: 0x040001CB RID: 459
			OEM_6 = 27,
			// Token: 0x040001CC RID: 460
			OEM_7 = 40,
			// Token: 0x040001CD RID: 461
			OEM_8 = 0,
			// Token: 0x040001CE RID: 462
			OEM_102 = 86,
			// Token: 0x040001CF RID: 463
			PROCESSKEY = 0,
			// Token: 0x040001D0 RID: 464
			PACKET = 0,
			// Token: 0x040001D1 RID: 465
			ATTN = 0,
			// Token: 0x040001D2 RID: 466
			CRSEL = 0,
			// Token: 0x040001D3 RID: 467
			EXSEL = 0,
			// Token: 0x040001D4 RID: 468
			EREOF = 93,
			// Token: 0x040001D5 RID: 469
			PLAY = 0,
			// Token: 0x040001D6 RID: 470
			ZOOM = 98,
			// Token: 0x040001D7 RID: 471
			NONAME = 0,
			// Token: 0x040001D8 RID: 472
			PA1 = 0,
			// Token: 0x040001D9 RID: 473
			OEM_CLEAR = 0
		}

		// Token: 0x0200004C RID: 76
		[Flags]
		public enum KEYEVENTF : uint
		{
			// Token: 0x040001DB RID: 475
			EXTENDEDKEY = 1U,
			// Token: 0x040001DC RID: 476
			KEYUP = 2U,
			// Token: 0x040001DD RID: 477
			SCANCODE = 8U,
			// Token: 0x040001DE RID: 478
			UNICODE = 4U
		}

		// Token: 0x0200004D RID: 77
		[Flags]
		public enum MOUSEEVENTF : uint
		{
			// Token: 0x040001E0 RID: 480
			ABSOLUTE = 32768U,
			// Token: 0x040001E1 RID: 481
			HWHEEL = 4096U,
			// Token: 0x040001E2 RID: 482
			MOVE = 1U,
			// Token: 0x040001E3 RID: 483
			MOVE_NOCOALESCE = 8192U,
			// Token: 0x040001E4 RID: 484
			LEFTDOWN = 2U,
			// Token: 0x040001E5 RID: 485
			LEFTUP = 4U,
			// Token: 0x040001E6 RID: 486
			RIGHTDOWN = 8U,
			// Token: 0x040001E7 RID: 487
			RIGHTUP = 16U,
			// Token: 0x040001E8 RID: 488
			MIDDLEDOWN = 32U,
			// Token: 0x040001E9 RID: 489
			MIDDLEUP = 64U,
			// Token: 0x040001EA RID: 490
			VIRTUALDESK = 16384U,
			// Token: 0x040001EB RID: 491
			WHEEL = 2048U,
			// Token: 0x040001EC RID: 492
			XDOWN = 128U,
			// Token: 0x040001ED RID: 493
			XUP = 256U
		}

		// Token: 0x0200004E RID: 78
		public enum VirtualKeys : ushort
		{
			// Token: 0x040001EF RID: 495
			LeftButton = 1,
			// Token: 0x040001F0 RID: 496
			RightButton,
			// Token: 0x040001F1 RID: 497
			Cancel,
			// Token: 0x040001F2 RID: 498
			MiddleButton,
			// Token: 0x040001F3 RID: 499
			ExtraButton1,
			// Token: 0x040001F4 RID: 500
			ExtraButton2,
			// Token: 0x040001F5 RID: 501
			Back = 8,
			// Token: 0x040001F6 RID: 502
			Tab,
			// Token: 0x040001F7 RID: 503
			Clear = 12,
			// Token: 0x040001F8 RID: 504
			Return,
			// Token: 0x040001F9 RID: 505
			Shift = 16,
			// Token: 0x040001FA RID: 506
			Control,
			// Token: 0x040001FB RID: 507
			Menu,
			// Token: 0x040001FC RID: 508
			Pause,
			// Token: 0x040001FD RID: 509
			CapsLock,
			// Token: 0x040001FE RID: 510
			Kana,
			// Token: 0x040001FF RID: 511
			Hangeul = 21,
			// Token: 0x04000200 RID: 512
			Hangul = 21,
			// Token: 0x04000201 RID: 513
			Junja = 23,
			// Token: 0x04000202 RID: 514
			Final,
			// Token: 0x04000203 RID: 515
			Hanja,
			// Token: 0x04000204 RID: 516
			Kanji = 25,
			// Token: 0x04000205 RID: 517
			Escape = 27,
			// Token: 0x04000206 RID: 518
			Convert,
			// Token: 0x04000207 RID: 519
			NonConvert,
			// Token: 0x04000208 RID: 520
			Accept,
			// Token: 0x04000209 RID: 521
			ModeChange,
			// Token: 0x0400020A RID: 522
			Space,
			// Token: 0x0400020B RID: 523
			Prior,
			// Token: 0x0400020C RID: 524
			Next,
			// Token: 0x0400020D RID: 525
			End,
			// Token: 0x0400020E RID: 526
			Home,
			// Token: 0x0400020F RID: 527
			Left,
			// Token: 0x04000210 RID: 528
			Up,
			// Token: 0x04000211 RID: 529
			Right,
			// Token: 0x04000212 RID: 530
			Down,
			// Token: 0x04000213 RID: 531
			Select,
			// Token: 0x04000214 RID: 532
			Print,
			// Token: 0x04000215 RID: 533
			Execute,
			// Token: 0x04000216 RID: 534
			Snapshot,
			// Token: 0x04000217 RID: 535
			Insert,
			// Token: 0x04000218 RID: 536
			Delete,
			// Token: 0x04000219 RID: 537
			Help,
			// Token: 0x0400021A RID: 538
			N0,
			// Token: 0x0400021B RID: 539
			N1,
			// Token: 0x0400021C RID: 540
			N2,
			// Token: 0x0400021D RID: 541
			N3,
			// Token: 0x0400021E RID: 542
			N4,
			// Token: 0x0400021F RID: 543
			N5,
			// Token: 0x04000220 RID: 544
			N6,
			// Token: 0x04000221 RID: 545
			N7,
			// Token: 0x04000222 RID: 546
			N8,
			// Token: 0x04000223 RID: 547
			N9,
			// Token: 0x04000224 RID: 548
			A = 65,
			// Token: 0x04000225 RID: 549
			B,
			// Token: 0x04000226 RID: 550
			C,
			// Token: 0x04000227 RID: 551
			D,
			// Token: 0x04000228 RID: 552
			E,
			// Token: 0x04000229 RID: 553
			F,
			// Token: 0x0400022A RID: 554
			G,
			// Token: 0x0400022B RID: 555
			H,
			// Token: 0x0400022C RID: 556
			I,
			// Token: 0x0400022D RID: 557
			J,
			// Token: 0x0400022E RID: 558
			K,
			// Token: 0x0400022F RID: 559
			L,
			// Token: 0x04000230 RID: 560
			M,
			// Token: 0x04000231 RID: 561
			N,
			// Token: 0x04000232 RID: 562
			O,
			// Token: 0x04000233 RID: 563
			P,
			// Token: 0x04000234 RID: 564
			Q,
			// Token: 0x04000235 RID: 565
			R,
			// Token: 0x04000236 RID: 566
			S,
			// Token: 0x04000237 RID: 567
			T,
			// Token: 0x04000238 RID: 568
			U,
			// Token: 0x04000239 RID: 569
			V,
			// Token: 0x0400023A RID: 570
			W,
			// Token: 0x0400023B RID: 571
			X,
			// Token: 0x0400023C RID: 572
			Y,
			// Token: 0x0400023D RID: 573
			Z,
			// Token: 0x0400023E RID: 574
			LeftWindows,
			// Token: 0x0400023F RID: 575
			RightWindows,
			// Token: 0x04000240 RID: 576
			Application,
			// Token: 0x04000241 RID: 577
			Sleep = 95,
			// Token: 0x04000242 RID: 578
			Numpad0,
			// Token: 0x04000243 RID: 579
			Numpad1,
			// Token: 0x04000244 RID: 580
			Numpad2,
			// Token: 0x04000245 RID: 581
			Numpad3,
			// Token: 0x04000246 RID: 582
			Numpad4,
			// Token: 0x04000247 RID: 583
			Numpad5,
			// Token: 0x04000248 RID: 584
			Numpad6,
			// Token: 0x04000249 RID: 585
			Numpad7,
			// Token: 0x0400024A RID: 586
			Numpad8,
			// Token: 0x0400024B RID: 587
			Numpad9,
			// Token: 0x0400024C RID: 588
			Multiply,
			// Token: 0x0400024D RID: 589
			Add,
			// Token: 0x0400024E RID: 590
			Separator,
			// Token: 0x0400024F RID: 591
			Subtract,
			// Token: 0x04000250 RID: 592
			Decimal,
			// Token: 0x04000251 RID: 593
			Divide,
			// Token: 0x04000252 RID: 594
			F1,
			// Token: 0x04000253 RID: 595
			F2,
			// Token: 0x04000254 RID: 596
			F3,
			// Token: 0x04000255 RID: 597
			F4,
			// Token: 0x04000256 RID: 598
			F5,
			// Token: 0x04000257 RID: 599
			F6,
			// Token: 0x04000258 RID: 600
			F7,
			// Token: 0x04000259 RID: 601
			F8,
			// Token: 0x0400025A RID: 602
			F9,
			// Token: 0x0400025B RID: 603
			F10,
			// Token: 0x0400025C RID: 604
			F11,
			// Token: 0x0400025D RID: 605
			F12,
			// Token: 0x0400025E RID: 606
			F13,
			// Token: 0x0400025F RID: 607
			F14,
			// Token: 0x04000260 RID: 608
			F15,
			// Token: 0x04000261 RID: 609
			F16,
			// Token: 0x04000262 RID: 610
			F17,
			// Token: 0x04000263 RID: 611
			F18,
			// Token: 0x04000264 RID: 612
			F19,
			// Token: 0x04000265 RID: 613
			F20,
			// Token: 0x04000266 RID: 614
			F21,
			// Token: 0x04000267 RID: 615
			F22,
			// Token: 0x04000268 RID: 616
			F23,
			// Token: 0x04000269 RID: 617
			F24,
			// Token: 0x0400026A RID: 618
			NumLock = 144,
			// Token: 0x0400026B RID: 619
			ScrollLock,
			// Token: 0x0400026C RID: 620
			NEC_Equal,
			// Token: 0x0400026D RID: 621
			Fujitsu_Jisho = 146,
			// Token: 0x0400026E RID: 622
			Fujitsu_Masshou,
			// Token: 0x0400026F RID: 623
			Fujitsu_Touroku,
			// Token: 0x04000270 RID: 624
			Fujitsu_Loya,
			// Token: 0x04000271 RID: 625
			Fujitsu_Roya,
			// Token: 0x04000272 RID: 626
			LeftShift = 160,
			// Token: 0x04000273 RID: 627
			RightShift,
			// Token: 0x04000274 RID: 628
			LeftControl,
			// Token: 0x04000275 RID: 629
			RightControl,
			// Token: 0x04000276 RID: 630
			LeftMenu,
			// Token: 0x04000277 RID: 631
			RightMenu,
			// Token: 0x04000278 RID: 632
			BrowserBack,
			// Token: 0x04000279 RID: 633
			BrowserForward,
			// Token: 0x0400027A RID: 634
			BrowserRefresh,
			// Token: 0x0400027B RID: 635
			BrowserStop,
			// Token: 0x0400027C RID: 636
			BrowserSearch,
			// Token: 0x0400027D RID: 637
			BrowserFavorites,
			// Token: 0x0400027E RID: 638
			BrowserHome,
			// Token: 0x0400027F RID: 639
			VolumeMute,
			// Token: 0x04000280 RID: 640
			VolumeDown,
			// Token: 0x04000281 RID: 641
			VolumeUp,
			// Token: 0x04000282 RID: 642
			MediaNextTrack,
			// Token: 0x04000283 RID: 643
			MediaPrevTrack,
			// Token: 0x04000284 RID: 644
			MediaStop,
			// Token: 0x04000285 RID: 645
			MediaPlayPause,
			// Token: 0x04000286 RID: 646
			LaunchMail,
			// Token: 0x04000287 RID: 647
			LaunchMediaSelect,
			// Token: 0x04000288 RID: 648
			LaunchApplication1,
			// Token: 0x04000289 RID: 649
			LaunchApplication2,
			// Token: 0x0400028A RID: 650
			OEM1 = 186,
			// Token: 0x0400028B RID: 651
			OEMPlus,
			// Token: 0x0400028C RID: 652
			OEMComma,
			// Token: 0x0400028D RID: 653
			OEMMinus,
			// Token: 0x0400028E RID: 654
			OEMPeriod,
			// Token: 0x0400028F RID: 655
			OEM2,
			// Token: 0x04000290 RID: 656
			OEM3,
			// Token: 0x04000291 RID: 657
			OEM4 = 219,
			// Token: 0x04000292 RID: 658
			OEM5,
			// Token: 0x04000293 RID: 659
			OEM6,
			// Token: 0x04000294 RID: 660
			OEM7,
			// Token: 0x04000295 RID: 661
			OEM8,
			// Token: 0x04000296 RID: 662
			OEMAX = 225,
			// Token: 0x04000297 RID: 663
			OEM102,
			// Token: 0x04000298 RID: 664
			ICOHelp,
			// Token: 0x04000299 RID: 665
			ICO00,
			// Token: 0x0400029A RID: 666
			ProcessKey,
			// Token: 0x0400029B RID: 667
			ICOClear,
			// Token: 0x0400029C RID: 668
			Packet,
			// Token: 0x0400029D RID: 669
			OEMReset = 233,
			// Token: 0x0400029E RID: 670
			OEMJump,
			// Token: 0x0400029F RID: 671
			OEMPA1,
			// Token: 0x040002A0 RID: 672
			OEMPA2,
			// Token: 0x040002A1 RID: 673
			OEMPA3,
			// Token: 0x040002A2 RID: 674
			OEMWSCtrl,
			// Token: 0x040002A3 RID: 675
			OEMCUSel,
			// Token: 0x040002A4 RID: 676
			OEMATTN,
			// Token: 0x040002A5 RID: 677
			OEMFinish,
			// Token: 0x040002A6 RID: 678
			OEMCopy,
			// Token: 0x040002A7 RID: 679
			OEMAuto,
			// Token: 0x040002A8 RID: 680
			OEMENLW,
			// Token: 0x040002A9 RID: 681
			OEMBackTab,
			// Token: 0x040002AA RID: 682
			ATTN,
			// Token: 0x040002AB RID: 683
			CRSel,
			// Token: 0x040002AC RID: 684
			EXSel,
			// Token: 0x040002AD RID: 685
			EREOF,
			// Token: 0x040002AE RID: 686
			Play,
			// Token: 0x040002AF RID: 687
			Zoom,
			// Token: 0x040002B0 RID: 688
			Noname,
			// Token: 0x040002B1 RID: 689
			PA1,
			// Token: 0x040002B2 RID: 690
			OEMClear
		}

		// Token: 0x0200004F RID: 79
		public enum VK
		{
			// Token: 0x040002B4 RID: 692
			LBUTTON = 1,
			// Token: 0x040002B5 RID: 693
			RBUTTON,
			// Token: 0x040002B6 RID: 694
			CANCEL,
			// Token: 0x040002B7 RID: 695
			MBUTTON,
			// Token: 0x040002B8 RID: 696
			XBUTTON1,
			// Token: 0x040002B9 RID: 697
			XBUTTON2,
			// Token: 0x040002BA RID: 698
			BACK = 8,
			// Token: 0x040002BB RID: 699
			TAB,
			// Token: 0x040002BC RID: 700
			CLEAR = 12,
			// Token: 0x040002BD RID: 701
			RETURN,
			// Token: 0x040002BE RID: 702
			SHIFT = 16,
			// Token: 0x040002BF RID: 703
			CONTROL,
			// Token: 0x040002C0 RID: 704
			MENU,
			// Token: 0x040002C1 RID: 705
			PAUSE,
			// Token: 0x040002C2 RID: 706
			CAPITAL,
			// Token: 0x040002C3 RID: 707
			KANA,
			// Token: 0x040002C4 RID: 708
			HANGUL = 21,
			// Token: 0x040002C5 RID: 709
			JUNJA = 23,
			// Token: 0x040002C6 RID: 710
			FINAL,
			// Token: 0x040002C7 RID: 711
			HANJA,
			// Token: 0x040002C8 RID: 712
			KANJI = 25,
			// Token: 0x040002C9 RID: 713
			ESCAPE = 27,
			// Token: 0x040002CA RID: 714
			CONVERT,
			// Token: 0x040002CB RID: 715
			NONCONVERT,
			// Token: 0x040002CC RID: 716
			ACCEPT,
			// Token: 0x040002CD RID: 717
			MODECHANGE,
			// Token: 0x040002CE RID: 718
			SPACE,
			// Token: 0x040002CF RID: 719
			PRIOR,
			// Token: 0x040002D0 RID: 720
			NEXT,
			// Token: 0x040002D1 RID: 721
			END,
			// Token: 0x040002D2 RID: 722
			HOME,
			// Token: 0x040002D3 RID: 723
			LEFT,
			// Token: 0x040002D4 RID: 724
			UP,
			// Token: 0x040002D5 RID: 725
			RIGHT,
			// Token: 0x040002D6 RID: 726
			DOWN,
			// Token: 0x040002D7 RID: 727
			SELECT,
			// Token: 0x040002D8 RID: 728
			PRINT,
			// Token: 0x040002D9 RID: 729
			EXECUTE,
			// Token: 0x040002DA RID: 730
			SNAPSHOT,
			// Token: 0x040002DB RID: 731
			INSERT,
			// Token: 0x040002DC RID: 732
			DELETE,
			// Token: 0x040002DD RID: 733
			HELP,
			// Token: 0x040002DE RID: 734
			KEY_0,
			// Token: 0x040002DF RID: 735
			KEY_1,
			// Token: 0x040002E0 RID: 736
			KEY_2,
			// Token: 0x040002E1 RID: 737
			KEY_3,
			// Token: 0x040002E2 RID: 738
			KEY_4,
			// Token: 0x040002E3 RID: 739
			KEY_5,
			// Token: 0x040002E4 RID: 740
			KEY_6,
			// Token: 0x040002E5 RID: 741
			KEY_7,
			// Token: 0x040002E6 RID: 742
			KEY_8,
			// Token: 0x040002E7 RID: 743
			KEY_9,
			// Token: 0x040002E8 RID: 744
			KEY_A = 65,
			// Token: 0x040002E9 RID: 745
			KEY_B,
			// Token: 0x040002EA RID: 746
			KEY_C,
			// Token: 0x040002EB RID: 747
			KEY_D,
			// Token: 0x040002EC RID: 748
			KEY_E,
			// Token: 0x040002ED RID: 749
			KEY_F,
			// Token: 0x040002EE RID: 750
			KEY_G,
			// Token: 0x040002EF RID: 751
			KEY_H,
			// Token: 0x040002F0 RID: 752
			KEY_I,
			// Token: 0x040002F1 RID: 753
			KEY_J,
			// Token: 0x040002F2 RID: 754
			KEY_K,
			// Token: 0x040002F3 RID: 755
			KEY_L,
			// Token: 0x040002F4 RID: 756
			KEY_M,
			// Token: 0x040002F5 RID: 757
			KEY_N,
			// Token: 0x040002F6 RID: 758
			KEY_O,
			// Token: 0x040002F7 RID: 759
			KEY_P,
			// Token: 0x040002F8 RID: 760
			KEY_Q,
			// Token: 0x040002F9 RID: 761
			KEY_R,
			// Token: 0x040002FA RID: 762
			KEY_S,
			// Token: 0x040002FB RID: 763
			KEY_T,
			// Token: 0x040002FC RID: 764
			KEY_U,
			// Token: 0x040002FD RID: 765
			KEY_V,
			// Token: 0x040002FE RID: 766
			KEY_W,
			// Token: 0x040002FF RID: 767
			KEY_X,
			// Token: 0x04000300 RID: 768
			KEY_Y,
			// Token: 0x04000301 RID: 769
			KEY_Z,
			// Token: 0x04000302 RID: 770
			LWIN,
			// Token: 0x04000303 RID: 771
			RWIN,
			// Token: 0x04000304 RID: 772
			APPS,
			// Token: 0x04000305 RID: 773
			SLEEP = 95,
			// Token: 0x04000306 RID: 774
			NUMPAD0,
			// Token: 0x04000307 RID: 775
			NUMPAD1,
			// Token: 0x04000308 RID: 776
			NUMPAD2,
			// Token: 0x04000309 RID: 777
			NUMPAD3,
			// Token: 0x0400030A RID: 778
			NUMPAD4,
			// Token: 0x0400030B RID: 779
			NUMPAD5,
			// Token: 0x0400030C RID: 780
			NUMPAD6,
			// Token: 0x0400030D RID: 781
			NUMPAD7,
			// Token: 0x0400030E RID: 782
			NUMPAD8,
			// Token: 0x0400030F RID: 783
			NUMPAD9,
			// Token: 0x04000310 RID: 784
			MULTIPLY,
			// Token: 0x04000311 RID: 785
			ADD,
			// Token: 0x04000312 RID: 786
			SEPARATOR,
			// Token: 0x04000313 RID: 787
			SUBTRACT,
			// Token: 0x04000314 RID: 788
			DECIMAL,
			// Token: 0x04000315 RID: 789
			DIVIDE,
			// Token: 0x04000316 RID: 790
			F1,
			// Token: 0x04000317 RID: 791
			F2,
			// Token: 0x04000318 RID: 792
			F3,
			// Token: 0x04000319 RID: 793
			F4,
			// Token: 0x0400031A RID: 794
			F5,
			// Token: 0x0400031B RID: 795
			F6,
			// Token: 0x0400031C RID: 796
			F7,
			// Token: 0x0400031D RID: 797
			F8,
			// Token: 0x0400031E RID: 798
			F9,
			// Token: 0x0400031F RID: 799
			F10,
			// Token: 0x04000320 RID: 800
			F11,
			// Token: 0x04000321 RID: 801
			F12,
			// Token: 0x04000322 RID: 802
			F13,
			// Token: 0x04000323 RID: 803
			F14,
			// Token: 0x04000324 RID: 804
			F15,
			// Token: 0x04000325 RID: 805
			F16,
			// Token: 0x04000326 RID: 806
			F17,
			// Token: 0x04000327 RID: 807
			F18,
			// Token: 0x04000328 RID: 808
			F19,
			// Token: 0x04000329 RID: 809
			F20,
			// Token: 0x0400032A RID: 810
			F21,
			// Token: 0x0400032B RID: 811
			F22,
			// Token: 0x0400032C RID: 812
			F23,
			// Token: 0x0400032D RID: 813
			F24,
			// Token: 0x0400032E RID: 814
			NUMLOCK = 144,
			// Token: 0x0400032F RID: 815
			SCROLL,
			// Token: 0x04000330 RID: 816
			LSHIFT = 160,
			// Token: 0x04000331 RID: 817
			RSHIFT,
			// Token: 0x04000332 RID: 818
			LCONTROL,
			// Token: 0x04000333 RID: 819
			RCONTROL,
			// Token: 0x04000334 RID: 820
			LMENU,
			// Token: 0x04000335 RID: 821
			RMENU,
			// Token: 0x04000336 RID: 822
			BROWSER_BACK,
			// Token: 0x04000337 RID: 823
			BROWSER_FORWARD,
			// Token: 0x04000338 RID: 824
			BROWSER_REFRESH,
			// Token: 0x04000339 RID: 825
			BROWSER_STOP,
			// Token: 0x0400033A RID: 826
			BROWSER_SEARCH,
			// Token: 0x0400033B RID: 827
			BROWSER_FAVORITES,
			// Token: 0x0400033C RID: 828
			BROWSER_HOME,
			// Token: 0x0400033D RID: 829
			VOLUME_MUTE,
			// Token: 0x0400033E RID: 830
			VOLUME_DOWN,
			// Token: 0x0400033F RID: 831
			VOLUME_UP,
			// Token: 0x04000340 RID: 832
			MEDIA_NEXT_TRACK,
			// Token: 0x04000341 RID: 833
			MEDIA_PREV_TRACK,
			// Token: 0x04000342 RID: 834
			MEDIA_STOP,
			// Token: 0x04000343 RID: 835
			MEDIA_PLAY_PAUSE,
			// Token: 0x04000344 RID: 836
			LAUNCH_MAIL,
			// Token: 0x04000345 RID: 837
			LAUNCH_MEDIA_SELECT,
			// Token: 0x04000346 RID: 838
			LAUNCH_APP1,
			// Token: 0x04000347 RID: 839
			LAUNCH_APP2,
			// Token: 0x04000348 RID: 840
			OEM_1 = 186,
			// Token: 0x04000349 RID: 841
			OEM_PLUS,
			// Token: 0x0400034A RID: 842
			OEM_COMMA,
			// Token: 0x0400034B RID: 843
			OEM_MINUS,
			// Token: 0x0400034C RID: 844
			OEM_PERIOD,
			// Token: 0x0400034D RID: 845
			OEM_2,
			// Token: 0x0400034E RID: 846
			OEM_3,
			// Token: 0x0400034F RID: 847
			OEM_4 = 219,
			// Token: 0x04000350 RID: 848
			OEM_5,
			// Token: 0x04000351 RID: 849
			OEM_6,
			// Token: 0x04000352 RID: 850
			OEM_7,
			// Token: 0x04000353 RID: 851
			OEM_8,
			// Token: 0x04000354 RID: 852
			OEM_102 = 226,
			// Token: 0x04000355 RID: 853
			PROCESSKEY = 229,
			// Token: 0x04000356 RID: 854
			PACKET = 231,
			// Token: 0x04000357 RID: 855
			ATTN = 246,
			// Token: 0x04000358 RID: 856
			CRSEL,
			// Token: 0x04000359 RID: 857
			EXSEL,
			// Token: 0x0400035A RID: 858
			EREOF,
			// Token: 0x0400035B RID: 859
			PLAY,
			// Token: 0x0400035C RID: 860
			ZOOM,
			// Token: 0x0400035D RID: 861
			NONAME,
			// Token: 0x0400035E RID: 862
			PA1,
			// Token: 0x0400035F RID: 863
			OEM_CLEAR
		}

		// Token: 0x02000050 RID: 80
		public enum SystemMetric
		{
			// Token: 0x04000361 RID: 865
			SM_CXSCREEN,
			// Token: 0x04000362 RID: 866
			SM_CYSCREEN,
			// Token: 0x04000363 RID: 867
			SM_CXVSCROLL,
			// Token: 0x04000364 RID: 868
			SM_CYHSCROLL,
			// Token: 0x04000365 RID: 869
			SM_CYCAPTION,
			// Token: 0x04000366 RID: 870
			SM_CXBORDER,
			// Token: 0x04000367 RID: 871
			SM_CYBORDER,
			// Token: 0x04000368 RID: 872
			SM_CXDLGFRAME,
			// Token: 0x04000369 RID: 873
			SM_CXFIXEDFRAME = 7,
			// Token: 0x0400036A RID: 874
			SM_CYDLGFRAME,
			// Token: 0x0400036B RID: 875
			SM_CYFIXEDFRAME = 8,
			// Token: 0x0400036C RID: 876
			SM_CYVTHUMB,
			// Token: 0x0400036D RID: 877
			SM_CXHTHUMB,
			// Token: 0x0400036E RID: 878
			SM_CXICON,
			// Token: 0x0400036F RID: 879
			SM_CYICON,
			// Token: 0x04000370 RID: 880
			SM_CXCURSOR,
			// Token: 0x04000371 RID: 881
			SM_CYCURSOR,
			// Token: 0x04000372 RID: 882
			SM_CYMENU,
			// Token: 0x04000373 RID: 883
			SM_CXFULLSCREEN,
			// Token: 0x04000374 RID: 884
			SM_CYFULLSCREEN,
			// Token: 0x04000375 RID: 885
			SM_CYKANJIWINDOW,
			// Token: 0x04000376 RID: 886
			SM_MOUSEPRESENT,
			// Token: 0x04000377 RID: 887
			SM_CYVSCROLL,
			// Token: 0x04000378 RID: 888
			SM_CXHSCROLL,
			// Token: 0x04000379 RID: 889
			SM_DEBUG,
			// Token: 0x0400037A RID: 890
			SM_SWAPBUTTON,
			// Token: 0x0400037B RID: 891
			SM_CXMIN = 28,
			// Token: 0x0400037C RID: 892
			SM_CYMIN,
			// Token: 0x0400037D RID: 893
			SM_CXSIZE,
			// Token: 0x0400037E RID: 894
			SM_CYSIZE,
			// Token: 0x0400037F RID: 895
			SM_CXSIZEFRAME,
			// Token: 0x04000380 RID: 896
			SM_CXFRAME = 32,
			// Token: 0x04000381 RID: 897
			SM_CYSIZEFRAME,
			// Token: 0x04000382 RID: 898
			SM_CYFRAME = 33,
			// Token: 0x04000383 RID: 899
			SM_CXMINTRACK,
			// Token: 0x04000384 RID: 900
			SM_CYMINTRACK,
			// Token: 0x04000385 RID: 901
			SM_CXDOUBLECLK,
			// Token: 0x04000386 RID: 902
			SM_CYDOUBLECLK,
			// Token: 0x04000387 RID: 903
			SM_CXICONSPACING,
			// Token: 0x04000388 RID: 904
			SM_CYICONSPACING,
			// Token: 0x04000389 RID: 905
			SM_MENUDROPALIGNMENT,
			// Token: 0x0400038A RID: 906
			SM_PENWINDOWS,
			// Token: 0x0400038B RID: 907
			SM_DBCSENABLED,
			// Token: 0x0400038C RID: 908
			SM_CMOUSEBUTTONS,
			// Token: 0x0400038D RID: 909
			SM_SECURE,
			// Token: 0x0400038E RID: 910
			SM_CXEDGE,
			// Token: 0x0400038F RID: 911
			SM_CYEDGE,
			// Token: 0x04000390 RID: 912
			SM_CXMINSPACING,
			// Token: 0x04000391 RID: 913
			SM_CYMINSPACING,
			// Token: 0x04000392 RID: 914
			SM_CXSMICON,
			// Token: 0x04000393 RID: 915
			SM_CYSMICON,
			// Token: 0x04000394 RID: 916
			SM_CYSMCAPTION,
			// Token: 0x04000395 RID: 917
			SM_CXSMSIZE,
			// Token: 0x04000396 RID: 918
			SM_CYSMSIZE,
			// Token: 0x04000397 RID: 919
			SM_CXMENUSIZE,
			// Token: 0x04000398 RID: 920
			SM_CYMENUSIZE,
			// Token: 0x04000399 RID: 921
			SM_ARRANGE,
			// Token: 0x0400039A RID: 922
			SM_CXMINIMIZED,
			// Token: 0x0400039B RID: 923
			SM_CYMINIMIZED,
			// Token: 0x0400039C RID: 924
			SM_CXMAXTRACK,
			// Token: 0x0400039D RID: 925
			SM_CYMAXTRACK,
			// Token: 0x0400039E RID: 926
			SM_CXMAXIMIZED,
			// Token: 0x0400039F RID: 927
			SM_CYMAXIMIZED,
			// Token: 0x040003A0 RID: 928
			SM_NETWORK,
			// Token: 0x040003A1 RID: 929
			SM_CLEANBOOT = 67,
			// Token: 0x040003A2 RID: 930
			SM_CXDRAG,
			// Token: 0x040003A3 RID: 931
			SM_CYDRAG,
			// Token: 0x040003A4 RID: 932
			SM_SHOWSOUNDS,
			// Token: 0x040003A5 RID: 933
			SM_CXMENUCHECK,
			// Token: 0x040003A6 RID: 934
			SM_CYMENUCHECK,
			// Token: 0x040003A7 RID: 935
			SM_SLOWMACHINE,
			// Token: 0x040003A8 RID: 936
			SM_MIDEASTENABLED,
			// Token: 0x040003A9 RID: 937
			SM_MOUSEWHEELPRESENT,
			// Token: 0x040003AA RID: 938
			SM_XVIRTUALSCREEN,
			// Token: 0x040003AB RID: 939
			SM_YVIRTUALSCREEN,
			// Token: 0x040003AC RID: 940
			SM_CXVIRTUALSCREEN,
			// Token: 0x040003AD RID: 941
			SM_CYVIRTUALSCREEN,
			// Token: 0x040003AE RID: 942
			SM_CMONITORS,
			// Token: 0x040003AF RID: 943
			SM_SAMEDISPLAYFORMAT,
			// Token: 0x040003B0 RID: 944
			SM_IMMENABLED,
			// Token: 0x040003B1 RID: 945
			SM_CXFOCUSBORDER,
			// Token: 0x040003B2 RID: 946
			SM_CYFOCUSBORDER,
			// Token: 0x040003B3 RID: 947
			SM_TABLETPC = 86,
			// Token: 0x040003B4 RID: 948
			SM_MEDIACENTER,
			// Token: 0x040003B5 RID: 949
			SM_STARTER,
			// Token: 0x040003B6 RID: 950
			SM_SERVERR2,
			// Token: 0x040003B7 RID: 951
			SM_MOUSEHORIZONTALWHEELPRESENT = 91,
			// Token: 0x040003B8 RID: 952
			SM_CXPADDEDBORDER,
			// Token: 0x040003B9 RID: 953
			SM_DIGITIZER = 94,
			// Token: 0x040003BA RID: 954
			SM_MAXIMUMTOUCHES,
			// Token: 0x040003BB RID: 955
			SM_REMOTESESSION = 4096,
			// Token: 0x040003BC RID: 956
			SM_SHUTTINGDOWN = 8192,
			// Token: 0x040003BD RID: 957
			SM_REMOTECONTROL,
			// Token: 0x040003BE RID: 958
			SM_CONVERTIBLESLATEMODE = 8195,
			// Token: 0x040003BF RID: 959
			SM_SYSTEMDOCKED
		}

		// Token: 0x02000051 RID: 81
		public struct RECT
		{
			// Token: 0x06000149 RID: 329 RVA: 0x00004F72 File Offset: 0x00003172
			public RECT(int Left, int Top, int Right, int Bottom)
			{
				this.left = Left;
				this.top = Top;
				this.right = Right;
				this.bottom = Bottom;
			}

			// Token: 0x0600014A RID: 330 RVA: 0x00004F91 File Offset: 0x00003191
			public RECT(Rectangle r)
			{
				this = new Win32.RECT(r.Left, r.Top, r.Right, r.Bottom);
			}

			// Token: 0x17000011 RID: 17
			// (get) Token: 0x0600014B RID: 331 RVA: 0x00004FB5 File Offset: 0x000031B5
			// (set) Token: 0x0600014C RID: 332 RVA: 0x00004FBD File Offset: 0x000031BD
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

			// Token: 0x17000012 RID: 18
			// (get) Token: 0x0600014D RID: 333 RVA: 0x00004FDB File Offset: 0x000031DB
			// (set) Token: 0x0600014E RID: 334 RVA: 0x00004FE3 File Offset: 0x000031E3
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

			// Token: 0x17000013 RID: 19
			// (get) Token: 0x0600014F RID: 335 RVA: 0x00005001 File Offset: 0x00003201
			// (set) Token: 0x06000150 RID: 336 RVA: 0x00005010 File Offset: 0x00003210
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

			// Token: 0x17000014 RID: 20
			// (get) Token: 0x06000151 RID: 337 RVA: 0x00005020 File Offset: 0x00003220
			// (set) Token: 0x06000152 RID: 338 RVA: 0x0000502F File Offset: 0x0000322F
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

			// Token: 0x17000015 RID: 21
			// (get) Token: 0x06000153 RID: 339 RVA: 0x0000503F File Offset: 0x0000323F
			// (set) Token: 0x06000154 RID: 340 RVA: 0x00005052 File Offset: 0x00003252
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

			// Token: 0x17000016 RID: 22
			// (get) Token: 0x06000155 RID: 341 RVA: 0x0000506E File Offset: 0x0000326E
			// (set) Token: 0x06000156 RID: 342 RVA: 0x00005081 File Offset: 0x00003281
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

			// Token: 0x06000157 RID: 343 RVA: 0x0000509D File Offset: 0x0000329D
			public static implicit operator Rectangle(Win32.RECT r)
			{
				return new Rectangle(r.left, r.top, r.Width, r.Height);
			}

			// Token: 0x06000158 RID: 344 RVA: 0x000050BE File Offset: 0x000032BE
			public static implicit operator Win32.RECT(Rectangle r)
			{
				return new Win32.RECT(r);
			}

			// Token: 0x06000159 RID: 345 RVA: 0x000050C6 File Offset: 0x000032C6
			public static bool operator ==(Win32.RECT r1, Win32.RECT r2)
			{
				return r1.Equals(r2);
			}

			// Token: 0x0600015A RID: 346 RVA: 0x000050D0 File Offset: 0x000032D0
			public static bool operator !=(Win32.RECT r1, Win32.RECT r2)
			{
				return !r1.Equals(r2);
			}

			// Token: 0x0600015B RID: 347 RVA: 0x000050DD File Offset: 0x000032DD
			public bool Equals(Win32.RECT r)
			{
				return r.left == this.left && r.top == this.top && r.right == this.right && r.bottom == this.bottom;
			}

			// Token: 0x0600015C RID: 348 RVA: 0x00005119 File Offset: 0x00003319
			public override bool Equals(object obj)
			{
				if (obj is Win32.RECT)
				{
					return this.Equals((Win32.RECT)obj);
				}
				return obj is Rectangle && this.Equals(new Win32.RECT((Rectangle)obj));
			}

			// Token: 0x0600015D RID: 349 RVA: 0x0000514C File Offset: 0x0000334C
			public override int GetHashCode()
			{
				return this.GetHashCode();
			}

			// Token: 0x040003C0 RID: 960
			public int left;

			// Token: 0x040003C1 RID: 961
			public int top;

			// Token: 0x040003C2 RID: 962
			public int right;

			// Token: 0x040003C3 RID: 963
			public int bottom;
		}

		// Token: 0x02000052 RID: 82
		public struct SimpleRECT
		{
			// Token: 0x040003C4 RID: 964
			public int left;

			// Token: 0x040003C5 RID: 965
			public int top;

			// Token: 0x040003C6 RID: 966
			public int right;

			// Token: 0x040003C7 RID: 967
			public int bottom;
		}

		// Token: 0x02000053 RID: 83
		public struct POINT
		{
			// Token: 0x040003C8 RID: 968
			public int x;

			// Token: 0x040003C9 RID: 969
			public int y;
		}

		// Token: 0x02000054 RID: 84
		public struct MINMAXINFO
		{
			// Token: 0x040003CA RID: 970
			public Win32.POINT ptReserved;

			// Token: 0x040003CB RID: 971
			public Win32.POINT ptMaxSize;

			// Token: 0x040003CC RID: 972
			public Win32.POINT ptMaxPosition;

			// Token: 0x040003CD RID: 973
			public Win32.POINT ptMinTrackSize;

			// Token: 0x040003CE RID: 974
			public Win32.POINT ptMaxTrackSize;
		}

		// Token: 0x02000055 RID: 85
		[StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)]
		public class MONITORINFO
		{
			// Token: 0x040003CF RID: 975
			public int cbSize = Marshal.SizeOf(typeof(Win32.MONITORINFO));

			// Token: 0x040003D0 RID: 976
			public Win32.RECT rcMonitor = default(Win32.RECT);

			// Token: 0x040003D1 RID: 977
			public Win32.RECT rcWork = default(Win32.RECT);

			// Token: 0x040003D2 RID: 978
			public int dwFlags;
		}
	}
}
