using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace ac.ac
{
	// Token: 0x02000007 RID: 7
	public class KeyboardHook
	{
		// Token: 0x14000001 RID: 1
		// (add) Token: 0x06000017 RID: 23 RVA: 0x00002790 File Offset: 0x00000990
		// (remove) Token: 0x06000018 RID: 24 RVA: 0x000027C8 File Offset: 0x000009C8
		public event KeyboardHook.KeyboardHookCallback KeyDown;

		// Token: 0x14000002 RID: 2
		// (add) Token: 0x06000019 RID: 25 RVA: 0x00002800 File Offset: 0x00000A00
		// (remove) Token: 0x0600001A RID: 26 RVA: 0x00002838 File Offset: 0x00000A38
		public event KeyboardHook.KeyboardHookCallback KeyUp;

		// Token: 0x0600001B RID: 27 RVA: 0x0000286D File Offset: 0x00000A6D
		public void Install()
		{
			this.hookHandler = new KeyboardHook.KeyboardHookHandler(this.HookFunc);
			this.hookID = this.SetHook(this.hookHandler);
		}

		// Token: 0x0600001C RID: 28 RVA: 0x00002893 File Offset: 0x00000A93
		public void Uninstall()
		{
			KeyboardHook.UnhookWindowsHookEx(this.hookID);
		}

		// Token: 0x0600001D RID: 29 RVA: 0x000028A4 File Offset: 0x00000AA4
		private IntPtr SetHook(KeyboardHook.KeyboardHookHandler proc)
		{
			IntPtr result;
			using (ProcessModule mainModule = Process.GetCurrentProcess().MainModule)
			{
				result = KeyboardHook.SetWindowsHookEx(13, proc, KeyboardHook.GetModuleHandle(mainModule.ModuleName), 0u);
			}
			return result;
		}

		// Token: 0x0600001E RID: 30 RVA: 0x000028F0 File Offset: 0x00000AF0
		private IntPtr HookFunc(int nCode, IntPtr wParam, IntPtr lParam)
		{
			if (nCode >= 0)
			{
				int num = wParam.ToInt32();
				if ((num == 256 || num == 260) && this.KeyDown != null)
				{
					this.KeyDown((KeyboardHook.VKeys)Marshal.ReadInt32(lParam));
				}
				if ((num == 257 || num == 261) && this.KeyUp != null)
				{
					this.KeyUp((KeyboardHook.VKeys)Marshal.ReadInt32(lParam));
				}
			}
			return KeyboardHook.CallNextHookEx(this.hookID, nCode, wParam, lParam);
		}

		// Token: 0x0600001F RID: 31 RVA: 0x0000296C File Offset: 0x00000B6C
		~KeyboardHook()
		{
			this.Uninstall();
		}

		// Token: 0x06000020 RID: 32
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr SetWindowsHookEx(int idHook, KeyboardHook.KeyboardHookHandler lpfn, IntPtr hMod, uint dwThreadId);

		// Token: 0x06000021 RID: 33
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		private static extern bool UnhookWindowsHookEx(IntPtr hhk);

		// Token: 0x06000022 RID: 34
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode, IntPtr wParam, IntPtr lParam);

		// Token: 0x06000023 RID: 35
		[DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr GetModuleHandle(string lpModuleName);

		// Token: 0x04000013 RID: 19
		private KeyboardHook.KeyboardHookHandler hookHandler;

		// Token: 0x04000016 RID: 22
		private IntPtr hookID = IntPtr.Zero;

		// Token: 0x04000017 RID: 23
		private const int WM_KEYDOWN = 256;

		// Token: 0x04000018 RID: 24
		private const int WM_SYSKEYDOWN = 260;

		// Token: 0x04000019 RID: 25
		private const int WM_KEYUP = 257;

		// Token: 0x0400001A RID: 26
		private const int WM_SYSKEYUP = 261;

		// Token: 0x0200000A RID: 10
		public enum VKeys
		{
			// Token: 0x04000035 RID: 53
			LBUTTON = 1,
			// Token: 0x04000036 RID: 54
			RBUTTON,
            // Token: 0x04000037 RID: 55
            CANCEL,
			// Token: 0x04000038 RID: 56
			MBUTTON,
			// Token: 0x04000039 RID: 57
			XBUTTON1,
			// Token: 0x0400003A RID: 58
			XBUTTON2,
			// Token: 0x0400003B RID: 59
			BACK = 8,
			// Token: 0x0400003C RID: 60
			TAB,
			// Token: 0x0400003D RID: 61
			CLEAR = 12,
			// Token: 0x0400003E RID: 62
			RETURN,
			// Token: 0x0400003F RID: 63
			SHIFT = 16,
			// Token: 0x04000040 RID: 64
			CONTROL,
			// Token: 0x04000041 RID: 65
			MENU,
			// Token: 0x04000042 RID: 66
			PAUSE,
			// Token: 0x04000043 RID: 67
			CAPITAL,
			// Token: 0x04000044 RID: 68
			KANA,
			// Token: 0x04000045 RID: 69
			HANGUL = 21,
			// Token: 0x04000046 RID: 70
			JUNJA = 23,
			// Token: 0x04000047 RID: 71
			FINAL,
			// Token: 0x04000048 RID: 72
			HANJA,
			// Token: 0x04000049 RID: 73
			KANJI = 25,
			// Token: 0x0400004A RID: 74
			ESCAPE = 27,
			// Token: 0x0400004B RID: 75
			CONVERT,
			// Token: 0x0400004C RID: 76
			NONCONVERT,
			// Token: 0x0400004D RID: 77
			ACCEPT,
			// Token: 0x0400004E RID: 78
			MODECHANGE,
			// Token: 0x0400004F RID: 79
			SPACE,
			// Token: 0x04000050 RID: 80
			PRIOR,
			// Token: 0x04000051 RID: 81
			NEXT,
			// Token: 0x04000052 RID: 82
			END,
			// Token: 0x04000053 RID: 83
			HOME,
			// Token: 0x04000054 RID: 84
			LEFT,
			// Token: 0x04000055 RID: 85
			UP,
			// Token: 0x04000056 RID: 86
			RIGHT,
			// Token: 0x04000057 RID: 87
			DOWN,
			// Token: 0x04000058 RID: 88
			SELECT,
			// Token: 0x04000059 RID: 89
			PRINT,
			// Token: 0x0400005A RID: 90
			EXECUTE,
			// Token: 0x0400005B RID: 91
			SNAPSHOT,
			// Token: 0x0400005C RID: 92
			INSERT,
			// Token: 0x0400005D RID: 93
			DELETE,
			// Token: 0x0400005E RID: 94
			HELP,
			// Token: 0x0400005F RID: 95
			KEY_0,
			// Token: 0x04000060 RID: 96
			KEY_1,
			// Token: 0x04000061 RID: 97
			KEY_2,
			// Token: 0x04000062 RID: 98
			KEY_3,
			// Token: 0x04000063 RID: 99
			KEY_4,
			// Token: 0x04000064 RID: 100
			KEY_5,
			// Token: 0x04000065 RID: 101
			KEY_6,
			// Token: 0x04000066 RID: 102
			KEY_7,
			// Token: 0x04000067 RID: 103
			KEY_8,
			// Token: 0x04000068 RID: 104
			KEY_9,
			// Token: 0x04000069 RID: 105
			KEY_A = 65,
			// Token: 0x0400006A RID: 106
			KEY_B,
			// Token: 0x0400006B RID: 107
			KEY_C,
			// Token: 0x0400006C RID: 108
			KEY_D,
			// Token: 0x0400006D RID: 109
			KEY_E,
			// Token: 0x0400006E RID: 110
			KEY_F,
			// Token: 0x0400006F RID: 111
			KEY_G,
			// Token: 0x04000070 RID: 112
			KEY_H,
			// Token: 0x04000071 RID: 113
			KEY_I,
			// Token: 0x04000072 RID: 114
			KEY_J,
			// Token: 0x04000073 RID: 115
			KEY_K,
			// Token: 0x04000074 RID: 116
			KEY_L,
			// Token: 0x04000075 RID: 117
			KEY_M,
			// Token: 0x04000076 RID: 118
			KEY_N,
			// Token: 0x04000077 RID: 119
			KEY_O,
			// Token: 0x04000078 RID: 120
			KEY_P,
			// Token: 0x04000079 RID: 121
			KEY_Q,
			// Token: 0x0400007A RID: 122
			KEY_R,
			// Token: 0x0400007B RID: 123
			KEY_S,
			// Token: 0x0400007C RID: 124
			KEY_T,
			// Token: 0x0400007D RID: 125
			KEY_U,
			// Token: 0x0400007E RID: 126
			KEY_V,
			// Token: 0x0400007F RID: 127
			KEY_W,
			// Token: 0x04000080 RID: 128
			KEY_X,
			// Token: 0x04000081 RID: 129
			KEY_Y,
			// Token: 0x04000082 RID: 130
			KEY_Z,
			// Token: 0x04000083 RID: 131
			LWIN,
			// Token: 0x04000084 RID: 132
			RWIN,
			// Token: 0x04000085 RID: 133
			APPS,
			// Token: 0x04000086 RID: 134
			SLEEP = 95,
			// Token: 0x04000087 RID: 135
			NUMPAD0,
			// Token: 0x04000088 RID: 136
			NUMPAD1,
			// Token: 0x04000089 RID: 137
			NUMPAD2,
			// Token: 0x0400008A RID: 138
			NUMPAD3,
			// Token: 0x0400008B RID: 139
			NUMPAD4,
			// Token: 0x0400008C RID: 140
			NUMPAD5,
			// Token: 0x0400008D RID: 141
			NUMPAD6,
			// Token: 0x0400008E RID: 142
			NUMPAD7,
			// Token: 0x0400008F RID: 143
			NUMPAD8,
			// Token: 0x04000090 RID: 144
			NUMPAD9,
			// Token: 0x04000091 RID: 145
			MULTIPLY,
			// Token: 0x04000092 RID: 146
			ADD,
			// Token: 0x04000093 RID: 147
			SEPARATOR,
			// Token: 0x04000094 RID: 148
			SUBTRACT,
			// Token: 0x04000095 RID: 149
			DECIMAL,
			// Token: 0x04000096 RID: 150
			DIVIDE,
			// Token: 0x04000097 RID: 151
			F1,
			// Token: 0x04000098 RID: 152
			F2,
			// Token: 0x04000099 RID: 153
			F3,
			// Token: 0x0400009A RID: 154
			F4,
			// Token: 0x0400009B RID: 155
			F5,
			// Token: 0x0400009C RID: 156
			F6,
			// Token: 0x0400009D RID: 157
			F7,
			// Token: 0x0400009E RID: 158
			F8,
			// Token: 0x0400009F RID: 159
			F9,
			// Token: 0x040000A0 RID: 160
			F10,
			// Token: 0x040000A1 RID: 161
			F11,
			// Token: 0x040000A2 RID: 162
			F12,
			// Token: 0x040000A3 RID: 163
			F13,
			// Token: 0x040000A4 RID: 164
			F14,
			// Token: 0x040000A5 RID: 165
			F15,
			// Token: 0x040000A6 RID: 166
			F16,
			// Token: 0x040000A7 RID: 167
			F17,
			// Token: 0x040000A8 RID: 168
			F18,
			// Token: 0x040000A9 RID: 169
			F19,
			// Token: 0x040000AA RID: 170
			F20,
			// Token: 0x040000AB RID: 171
			F21,
			// Token: 0x040000AC RID: 172
			F22,
			// Token: 0x040000AD RID: 173
			F23,
			// Token: 0x040000AE RID: 174
			F24,
			// Token: 0x040000AF RID: 175
			NUMLOCK = 144,
			// Token: 0x040000B0 RID: 176
			SCROLL,
			// Token: 0x040000B1 RID: 177
			LSHIFT = 160,
			// Token: 0x040000B2 RID: 178
			RSHIFT,
			// Token: 0x040000B3 RID: 179
			LCONTROL,
			// Token: 0x040000B4 RID: 180
			RCONTROL,
			// Token: 0x040000B5 RID: 181
			LMENU,
			// Token: 0x040000B6 RID: 182
			RMENU,
			// Token: 0x040000B7 RID: 183
			BROWSER_BACK,
			// Token: 0x040000B8 RID: 184
			BROWSER_FORWARD,
			// Token: 0x040000B9 RID: 185
			BROWSER_REFRESH,
			// Token: 0x040000BA RID: 186
			BROWSER_STOP,
			// Token: 0x040000BB RID: 187
			BROWSER_SEARCH,
			// Token: 0x040000BC RID: 188
			BROWSER_FAVORITES,
			// Token: 0x040000BD RID: 189
			BROWSER_HOME,
			// Token: 0x040000BE RID: 190
			VOLUME_MUTE,
			// Token: 0x040000BF RID: 191
			VOLUME_DOWN,
			// Token: 0x040000C0 RID: 192
			VOLUME_UP,
			// Token: 0x040000C1 RID: 193
			MEDIA_NEXT_TRACK,
			// Token: 0x040000C2 RID: 194
			MEDIA_PREV_TRACK,
			// Token: 0x040000C3 RID: 195
			MEDIA_STOP,
			// Token: 0x040000C4 RID: 196
			MEDIA_PLAY_PAUSE,
			// Token: 0x040000C5 RID: 197
			LAUNCH_MAIL,
			// Token: 0x040000C6 RID: 198
			LAUNCH_MEDIA_SELECT,
			// Token: 0x040000C7 RID: 199
			LAUNCH_APP1,
			// Token: 0x040000C8 RID: 200
			LAUNCH_APP2,
			// Token: 0x040000C9 RID: 201
			OEM_1 = 186,
			// Token: 0x040000CA RID: 202
			OEM_PLUS,
			// Token: 0x040000CB RID: 203
			OEM_COMMA,
			// Token: 0x040000CC RID: 204
			OEM_MINUS,
			// Token: 0x040000CD RID: 205
			OEM_PERIOD,
			// Token: 0x040000CE RID: 206
			OEM_2,
			// Token: 0x040000CF RID: 207
			OEM_3,
			// Token: 0x040000D0 RID: 208
			OEM_4 = 219,
			// Token: 0x040000D1 RID: 209
			OEM_5,
			// Token: 0x040000D2 RID: 210
			OEM_6,
			// Token: 0x040000D3 RID: 211
			OEM_7,
			// Token: 0x040000D4 RID: 212
			OEM_8,
			// Token: 0x040000D5 RID: 213
			OEM_102 = 226,
			// Token: 0x040000D6 RID: 214
			PROCESSKEY = 229,
			// Token: 0x040000D7 RID: 215
			PACKET = 231,
			// Token: 0x040000D8 RID: 216
			ATTN = 246,
			// Token: 0x040000D9 RID: 217
			CRSEL,
			// Token: 0x040000DA RID: 218
			EXSEL,
			// Token: 0x040000DB RID: 219
			EREOF,
			// Token: 0x040000DC RID: 220
			PLAY,
			// Token: 0x040000DD RID: 221
			ZOOM,
			// Token: 0x040000DE RID: 222
			NONAME,
			// Token: 0x040000DF RID: 223
			PA1,
			// Token: 0x040000E0 RID: 224
			OEM_CLEAR
		}

		// Token: 0x0200000B RID: 11
		// (Invoke) Token: 0x0600004E RID: 78
		private delegate IntPtr KeyboardHookHandler(int nCode, IntPtr wParam, IntPtr lParam);

		// Token: 0x0200000C RID: 12
		// (Invoke) Token: 0x06000052 RID: 82
		public delegate void KeyboardHookCallback(KeyboardHook.VKeys key);
	}
}
