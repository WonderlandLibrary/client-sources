using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace FastPot
{
	// Token: 0x02000003 RID: 3
	public class KeyboardHook
	{
		// Token: 0x14000001 RID: 1
		// (add) Token: 0x0600000D RID: 13 RVA: 0x00003060 File Offset: 0x00001260
		// (remove) Token: 0x0600000E RID: 14 RVA: 0x00003098 File Offset: 0x00001298
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		public event KeyboardHook.KeyboardHookCallback KeyDown;

		// Token: 0x14000002 RID: 2
		// (add) Token: 0x0600000F RID: 15 RVA: 0x000030D0 File Offset: 0x000012D0
		// (remove) Token: 0x06000010 RID: 16 RVA: 0x00003108 File Offset: 0x00001308
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		public event KeyboardHook.KeyboardHookCallback KeyUp;

		// Token: 0x06000011 RID: 17 RVA: 0x0000313D File Offset: 0x0000133D
		public void Install()
		{
			this.hookHandler = new KeyboardHook.KeyboardHookHandler(this.HookFunc);
			this.hookID = this.SetHook(this.hookHandler);
		}

		// Token: 0x06000012 RID: 18 RVA: 0x00003164 File Offset: 0x00001364
		public void Uninstall()
		{
			KeyboardHook.UnhookWindowsHookEx(this.hookID);
		}

		// Token: 0x06000013 RID: 19 RVA: 0x00003174 File Offset: 0x00001374
		private IntPtr SetHook(KeyboardHook.KeyboardHookHandler proc)
		{
			IntPtr result;
			using (ProcessModule mainModule = Process.GetCurrentProcess().MainModule)
			{
				result = KeyboardHook.SetWindowsHookEx(13, proc, KeyboardHook.GetModuleHandle(mainModule.ModuleName), 0U);
			}
			return result;
		}

		// Token: 0x06000014 RID: 20 RVA: 0x000031C8 File Offset: 0x000013C8
		private IntPtr HookFunc(int nCode, IntPtr wParam, IntPtr lParam)
		{
			bool flag = nCode >= 0;
			if (flag)
			{
				int num = wParam.ToInt32();
				bool flag2 = (num == 256 || num == 260) && this.KeyDown != null;
				if (flag2)
				{
					this.KeyDown((KeyboardHook.VKeys)Marshal.ReadInt32(lParam));
				}
				bool flag3 = (num == 257 || num == 261) && this.KeyUp != null;
				if (flag3)
				{
					this.KeyUp((KeyboardHook.VKeys)Marshal.ReadInt32(lParam));
				}
			}
			return KeyboardHook.CallNextHookEx(this.hookID, nCode, wParam, lParam);
		}

		// Token: 0x06000015 RID: 21 RVA: 0x00003268 File Offset: 0x00001468
		~KeyboardHook()
		{
			this.Uninstall();
		}

		// Token: 0x06000016 RID: 22
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr SetWindowsHookEx(int idHook, KeyboardHook.KeyboardHookHandler lpfn, IntPtr hMod, uint dwThreadId);

		// Token: 0x06000017 RID: 23
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		private static extern bool UnhookWindowsHookEx(IntPtr hhk);

		// Token: 0x06000018 RID: 24
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode, IntPtr wParam, IntPtr lParam);

		// Token: 0x06000019 RID: 25
		[DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr GetModuleHandle(string lpModuleName);

		// Token: 0x04000010 RID: 16
		private KeyboardHook.KeyboardHookHandler hookHandler;

		// Token: 0x04000011 RID: 17
		private IntPtr hookID = IntPtr.Zero;

		// Token: 0x04000012 RID: 18
		private const int WM_KEYDOWN = 256;

		// Token: 0x04000013 RID: 19
		private const int WM_SYSKEYDOWN = 260;

		// Token: 0x04000014 RID: 20
		private const int WM_KEYUP = 257;

		// Token: 0x04000015 RID: 21
		private const int WM_SYSKEYUP = 261;

		// Token: 0x02000009 RID: 9
		public enum VKeys
		{
			// Token: 0x04000029 RID: 41
			NOT_A_KEY,
			// Token: 0x0400002A RID: 42
			LBUTTON,
			// Token: 0x0400002B RID: 43
			RBUTTON,
			// Token: 0x0400002C RID: 44
			CANCEL,
			// Token: 0x0400002D RID: 45
			MBUTTON,
			// Token: 0x0400002E RID: 46
			XBUTTON1,
			// Token: 0x0400002F RID: 47
			XBUTTON2,
			// Token: 0x04000030 RID: 48
			BACK = 8,
			// Token: 0x04000031 RID: 49
			TAB,
			// Token: 0x04000032 RID: 50
			CLEAR = 12,
			// Token: 0x04000033 RID: 51
			RETURN,
			// Token: 0x04000034 RID: 52
			SHIFT = 16,
			// Token: 0x04000035 RID: 53
			CONTROL,
			// Token: 0x04000036 RID: 54
			MENU,
			// Token: 0x04000037 RID: 55
			PAUSE,
			// Token: 0x04000038 RID: 56
			CAPITAL,
			// Token: 0x04000039 RID: 57
			KANA,
			// Token: 0x0400003A RID: 58
			HANGUL = 21,
			// Token: 0x0400003B RID: 59
			JUNJA = 23,
			// Token: 0x0400003C RID: 60
			FINAL,
			// Token: 0x0400003D RID: 61
			HANJA,
			// Token: 0x0400003E RID: 62
			KANJI = 25,
			// Token: 0x0400003F RID: 63
			ESCAPE = 27,
			// Token: 0x04000040 RID: 64
			CONVERT,
			// Token: 0x04000041 RID: 65
			NONCONVERT,
			// Token: 0x04000042 RID: 66
			ACCEPT,
			// Token: 0x04000043 RID: 67
			MODECHANGE,
			// Token: 0x04000044 RID: 68
			SPACE,
			// Token: 0x04000045 RID: 69
			PRIOR,
			// Token: 0x04000046 RID: 70
			NEXT,
			// Token: 0x04000047 RID: 71
			END,
			// Token: 0x04000048 RID: 72
			HOME,
			// Token: 0x04000049 RID: 73
			LEFT,
			// Token: 0x0400004A RID: 74
			UP,
			// Token: 0x0400004B RID: 75
			RIGHT,
			// Token: 0x0400004C RID: 76
			DOWN,
			// Token: 0x0400004D RID: 77
			SELECT,
			// Token: 0x0400004E RID: 78
			PRINT,
			// Token: 0x0400004F RID: 79
			EXECUTE,
			// Token: 0x04000050 RID: 80
			SNAPSHOT,
			// Token: 0x04000051 RID: 81
			INSERT,
			// Token: 0x04000052 RID: 82
			DELETE,
			// Token: 0x04000053 RID: 83
			HELP,
			// Token: 0x04000054 RID: 84
			KEY_0,
			// Token: 0x04000055 RID: 85
			KEY_1,
			// Token: 0x04000056 RID: 86
			KEY_2,
			// Token: 0x04000057 RID: 87
			KEY_3,
			// Token: 0x04000058 RID: 88
			KEY_4,
			// Token: 0x04000059 RID: 89
			KEY_5,
			// Token: 0x0400005A RID: 90
			KEY_6,
			// Token: 0x0400005B RID: 91
			KEY_7,
			// Token: 0x0400005C RID: 92
			KEY_8,
			// Token: 0x0400005D RID: 93
			KEY_9,
			// Token: 0x0400005E RID: 94
			KEY_A = 65,
			// Token: 0x0400005F RID: 95
			KEY_B,
			// Token: 0x04000060 RID: 96
			KEY_C,
			// Token: 0x04000061 RID: 97
			KEY_D,
			// Token: 0x04000062 RID: 98
			KEY_E,
			// Token: 0x04000063 RID: 99
			KEY_F,
			// Token: 0x04000064 RID: 100
			KEY_G,
			// Token: 0x04000065 RID: 101
			KEY_H,
			// Token: 0x04000066 RID: 102
			KEY_I,
			// Token: 0x04000067 RID: 103
			KEY_J,
			// Token: 0x04000068 RID: 104
			KEY_K,
			// Token: 0x04000069 RID: 105
			KEY_L,
			// Token: 0x0400006A RID: 106
			KEY_M,
			// Token: 0x0400006B RID: 107
			KEY_N,
			// Token: 0x0400006C RID: 108
			KEY_O,
			// Token: 0x0400006D RID: 109
			KEY_P,
			// Token: 0x0400006E RID: 110
			KEY_Q,
			// Token: 0x0400006F RID: 111
			KEY_R,
			// Token: 0x04000070 RID: 112
			KEY_S,
			// Token: 0x04000071 RID: 113
			KEY_T,
			// Token: 0x04000072 RID: 114
			KEY_U,
			// Token: 0x04000073 RID: 115
			KEY_V,
			// Token: 0x04000074 RID: 116
			KEY_W,
			// Token: 0x04000075 RID: 117
			KEY_X,
			// Token: 0x04000076 RID: 118
			KEY_Y,
			// Token: 0x04000077 RID: 119
			KEY_Z,
			// Token: 0x04000078 RID: 120
			LWIN,
			// Token: 0x04000079 RID: 121
			RWIN,
			// Token: 0x0400007A RID: 122
			APPS,
			// Token: 0x0400007B RID: 123
			SLEEP = 95,
			// Token: 0x0400007C RID: 124
			NUMPAD0,
			// Token: 0x0400007D RID: 125
			NUMPAD1,
			// Token: 0x0400007E RID: 126
			NUMPAD2,
			// Token: 0x0400007F RID: 127
			NUMPAD3,
			// Token: 0x04000080 RID: 128
			NUMPAD4,
			// Token: 0x04000081 RID: 129
			NUMPAD5,
			// Token: 0x04000082 RID: 130
			NUMPAD6,
			// Token: 0x04000083 RID: 131
			NUMPAD7,
			// Token: 0x04000084 RID: 132
			NUMPAD8,
			// Token: 0x04000085 RID: 133
			NUMPAD9,
			// Token: 0x04000086 RID: 134
			MULTIPLY,
			// Token: 0x04000087 RID: 135
			ADD,
			// Token: 0x04000088 RID: 136
			SEPARATOR,
			// Token: 0x04000089 RID: 137
			SUBTRACT,
			// Token: 0x0400008A RID: 138
			DECIMAL,
			// Token: 0x0400008B RID: 139
			DIVIDE,
			// Token: 0x0400008C RID: 140
			F1,
			// Token: 0x0400008D RID: 141
			F2,
			// Token: 0x0400008E RID: 142
			F3,
			// Token: 0x0400008F RID: 143
			F4,
			// Token: 0x04000090 RID: 144
			F5,
			// Token: 0x04000091 RID: 145
			F6,
			// Token: 0x04000092 RID: 146
			F7,
			// Token: 0x04000093 RID: 147
			F8,
			// Token: 0x04000094 RID: 148
			F9,
			// Token: 0x04000095 RID: 149
			F10,
			// Token: 0x04000096 RID: 150
			F11,
			// Token: 0x04000097 RID: 151
			F12,
			// Token: 0x04000098 RID: 152
			F13,
			// Token: 0x04000099 RID: 153
			F14,
			// Token: 0x0400009A RID: 154
			F15,
			// Token: 0x0400009B RID: 155
			F16,
			// Token: 0x0400009C RID: 156
			F17,
			// Token: 0x0400009D RID: 157
			F18,
			// Token: 0x0400009E RID: 158
			F19,
			// Token: 0x0400009F RID: 159
			F20,
			// Token: 0x040000A0 RID: 160
			F21,
			// Token: 0x040000A1 RID: 161
			F22,
			// Token: 0x040000A2 RID: 162
			F23,
			// Token: 0x040000A3 RID: 163
			F24,
			// Token: 0x040000A4 RID: 164
			NUMLOCK = 144,
			// Token: 0x040000A5 RID: 165
			SCROLL,
			// Token: 0x040000A6 RID: 166
			LSHIFT = 160,
			// Token: 0x040000A7 RID: 167
			RSHIFT,
			// Token: 0x040000A8 RID: 168
			LCONTROL,
			// Token: 0x040000A9 RID: 169
			RCONTROL,
			// Token: 0x040000AA RID: 170
			LMENU,
			// Token: 0x040000AB RID: 171
			RMENU,
			// Token: 0x040000AC RID: 172
			BROWSER_BACK,
			// Token: 0x040000AD RID: 173
			BROWSER_FORWARD,
			// Token: 0x040000AE RID: 174
			BROWSER_REFRESH,
			// Token: 0x040000AF RID: 175
			BROWSER_STOP,
			// Token: 0x040000B0 RID: 176
			BROWSER_SEARCH,
			// Token: 0x040000B1 RID: 177
			BROWSER_FAVORITES,
			// Token: 0x040000B2 RID: 178
			BROWSER_HOME,
			// Token: 0x040000B3 RID: 179
			VOLUME_MUTE,
			// Token: 0x040000B4 RID: 180
			VOLUME_DOWN,
			// Token: 0x040000B5 RID: 181
			VOLUME_UP,
			// Token: 0x040000B6 RID: 182
			MEDIA_NEXT_TRACK,
			// Token: 0x040000B7 RID: 183
			MEDIA_PREV_TRACK,
			// Token: 0x040000B8 RID: 184
			MEDIA_STOP,
			// Token: 0x040000B9 RID: 185
			MEDIA_PLAY_PAUSE,
			// Token: 0x040000BA RID: 186
			LAUNCH_MAIL,
			// Token: 0x040000BB RID: 187
			LAUNCH_MEDIA_SELECT,
			// Token: 0x040000BC RID: 188
			LAUNCH_APP1,
			// Token: 0x040000BD RID: 189
			LAUNCH_APP2,
			// Token: 0x040000BE RID: 190
			OEM_1 = 186,
			// Token: 0x040000BF RID: 191
			OEM_PLUS,
			// Token: 0x040000C0 RID: 192
			OEM_COMMA,
			// Token: 0x040000C1 RID: 193
			OEM_MINUS,
			// Token: 0x040000C2 RID: 194
			OEM_PERIOD,
			// Token: 0x040000C3 RID: 195
			OEM_2,
			// Token: 0x040000C4 RID: 196
			OEM_3,
			// Token: 0x040000C5 RID: 197
			OEM_4 = 219,
			// Token: 0x040000C6 RID: 198
			OEM_5,
			// Token: 0x040000C7 RID: 199
			OEM_6,
			// Token: 0x040000C8 RID: 200
			OEM_7,
			// Token: 0x040000C9 RID: 201
			OEM_8,
			// Token: 0x040000CA RID: 202
			OEM_102 = 226,
			// Token: 0x040000CB RID: 203
			PROCESSKEY = 229,
			// Token: 0x040000CC RID: 204
			PACKET = 231,
			// Token: 0x040000CD RID: 205
			ATTN = 246,
			// Token: 0x040000CE RID: 206
			CRSEL,
			// Token: 0x040000CF RID: 207
			EXSEL,
			// Token: 0x040000D0 RID: 208
			EREOF,
			// Token: 0x040000D1 RID: 209
			PLAY,
			// Token: 0x040000D2 RID: 210
			ZOOM,
			// Token: 0x040000D3 RID: 211
			NONAME,
			// Token: 0x040000D4 RID: 212
			PA1,
			// Token: 0x040000D5 RID: 213
			OEM_CLEAR
		}

		// Token: 0x0200000A RID: 10
		// (Invoke) Token: 0x0600003F RID: 63
		private delegate IntPtr KeyboardHookHandler(int nCode, IntPtr wParam, IntPtr lParam);

		// Token: 0x0200000B RID: 11
		// (Invoke) Token: 0x06000043 RID: 67
		public delegate void KeyboardHookCallback(KeyboardHook.VKeys key);
	}
}
