using System;
using System.Text;
using System.Windows.Forms;

namespace AutoClicker
{
	// Token: 0x0200000A RID: 10
	public class KeysConverter
	{
		// Token: 0x0600005A RID: 90 RVA: 0x0000390B File Offset: 0x00001B0B
		public static string Convert(Keys keys)
		{
			return KeysConverter.ConvertModifiers(keys) + " " + KeysConverter.ConvertKeyPart(keys);
		}

		// Token: 0x0600005B RID: 91 RVA: 0x00003924 File Offset: 0x00001B24
		public static string ConvertModifiers(Keys keys)
		{
			Keys keys2 = keys & Keys.Modifiers;
			StringBuilder stringBuilder = new StringBuilder();
			if ((keys2 & Keys.Shift) != Keys.None)
			{
				stringBuilder.Append("<Shift> ");
			}
			if ((keys2 & Keys.Control) != Keys.None)
			{
				stringBuilder.Append("<Control> ");
			}
			if ((keys2 & Keys.Alt) != Keys.None)
			{
				stringBuilder.Append("<Alt> ");
			}
			string text = stringBuilder.ToString();
			if (text.Length > 0)
			{
				return text.Substring(0, text.Length - 1);
			}
			return "";
		}

		// Token: 0x0600005C RID: 92 RVA: 0x000039A4 File Offset: 0x00001BA4
		public static string ConvertKeyPart(Keys keys)
		{
			Keys keys2 = keys & Keys.KeyCode;
			switch (keys2)
			{
			case Keys.LButton:
				return "Left Click";
			case Keys.RButton:
				return "Right Click";
			case Keys.Cancel:
			case Keys.MButton:
			case Keys.XButton1:
			case Keys.XButton2:
			case Keys.LButton | Keys.RButton | Keys.MButton:
			case Keys.LineFeed:
			case Keys.LButton | Keys.RButton | Keys.Back:
			case Keys.Clear:
			case Keys.RButton | Keys.MButton | Keys.Back:
			case Keys.LButton | Keys.RButton | Keys.MButton | Keys.Back:
			case Keys.ShiftKey:
			case Keys.ControlKey:
			case Keys.Menu:
			case Keys.KanaMode:
			case Keys.RButton | Keys.MButton | Keys.ShiftKey:
			case Keys.JunjaMode:
			case Keys.FinalMode:
			case Keys.HanjaMode:
			case Keys.RButton | Keys.Back | Keys.ShiftKey:
			case Keys.IMEConvert:
			case Keys.IMENonconvert:
			case Keys.IMEAccept:
			case Keys.IMEModeChange:
			case Keys.Select:
			case Keys.Print:
			case Keys.Execute:
			case Keys.Help:
			case Keys.RButton | Keys.Back | Keys.ShiftKey | Keys.Space:
			case Keys.LButton | Keys.RButton | Keys.Back | Keys.ShiftKey | Keys.Space:
			case Keys.MButton | Keys.Back | Keys.ShiftKey | Keys.Space:
			case Keys.LButton | Keys.MButton | Keys.Back | Keys.ShiftKey | Keys.Space:
			case Keys.RButton | Keys.MButton | Keys.Back | Keys.ShiftKey | Keys.Space:
			case Keys.LButton | Keys.RButton | Keys.MButton | Keys.Back | Keys.ShiftKey | Keys.Space:
			case (Keys)64:
			case Keys.LWin:
			case Keys.RWin:
			case Keys.Apps:
			case (Keys)94:
			case Keys.Sleep:
			case Keys.Separator:
			case Keys.Back | Keys.F17:
			case Keys.LButton | Keys.Back | Keys.F17:
			case Keys.RButton | Keys.Back | Keys.F17:
			case Keys.LButton | Keys.RButton | Keys.Back | Keys.F17:
			case Keys.MButton | Keys.Back | Keys.F17:
			case Keys.LButton | Keys.MButton | Keys.Back | Keys.F17:
			case Keys.RButton | Keys.MButton | Keys.Back | Keys.F17:
			case Keys.LButton | Keys.RButton | Keys.MButton | Keys.Back | Keys.F17:
			case Keys.RButton | Keys.ShiftKey | Keys.F17:
			case Keys.LButton | Keys.RButton | Keys.ShiftKey | Keys.F17:
			case Keys.MButton | Keys.ShiftKey | Keys.F17:
			case Keys.LButton | Keys.MButton | Keys.ShiftKey | Keys.F17:
			case Keys.RButton | Keys.MButton | Keys.ShiftKey | Keys.F17:
			case Keys.LButton | Keys.RButton | Keys.MButton | Keys.ShiftKey | Keys.F17:
			case Keys.Back | Keys.ShiftKey | Keys.F17:
			case Keys.LButton | Keys.Back | Keys.ShiftKey | Keys.F17:
			case Keys.RButton | Keys.Back | Keys.ShiftKey | Keys.F17:
			case Keys.LButton | Keys.RButton | Keys.Back | Keys.ShiftKey | Keys.F17:
			case Keys.MButton | Keys.Back | Keys.ShiftKey | Keys.F17:
			case Keys.LButton | Keys.MButton | Keys.Back | Keys.ShiftKey | Keys.F17:
			case Keys.RButton | Keys.MButton | Keys.Back | Keys.ShiftKey | Keys.F17:
			case Keys.LButton | Keys.RButton | Keys.MButton | Keys.Back | Keys.ShiftKey | Keys.F17:
			case Keys.LShiftKey:
			case Keys.RShiftKey:
			case Keys.LControlKey:
			case Keys.RControlKey:
			case Keys.LMenu:
			case Keys.RMenu:
			case Keys.BrowserBack:
			case Keys.BrowserForward:
			case Keys.BrowserRefresh:
			case Keys.BrowserStop:
			case Keys.BrowserSearch:
			case Keys.BrowserFavorites:
			case Keys.BrowserHome:
			case Keys.VolumeMute:
			case Keys.VolumeDown:
			case Keys.VolumeUp:
			case Keys.MediaNextTrack:
			case Keys.MediaPreviousTrack:
			case Keys.MediaStop:
			case Keys.MediaPlayPause:
			case Keys.LaunchMail:
			case Keys.SelectMedia:
			case Keys.LaunchApplication1:
			case Keys.LaunchApplication2:
			case Keys.Back | Keys.ShiftKey | Keys.Space | Keys.F17:
			case Keys.LButton | Keys.Back | Keys.ShiftKey | Keys.Space | Keys.F17:
				break;
			case Keys.Back:
				return "Backspace";
			case Keys.Tab:
				return "Tab";
			case Keys.Return:
				return "Enter";
			case Keys.Pause:
				return "Pause";
			case Keys.Capital:
				return "Caps Lock";
			case Keys.Escape:
				return "Escape";
			case Keys.Space:
				return "Space";
			case Keys.Prior:
				return "Page Up";
			case Keys.Next:
				return "Page Down";
			case Keys.End:
				return "End";
			case Keys.Home:
				return "Home";
			case Keys.Left:
				return "Left";
			case Keys.Up:
				return "Up";
			case Keys.Right:
				return "Right";
			case Keys.Down:
				return "Down";
			case Keys.Snapshot:
				return "Print Screen";
			case Keys.Insert:
				return "Insert";
			case Keys.Delete:
				return "Delete";
			case Keys.D0:
				return "0";
			case Keys.D1:
				return "1";
			case Keys.D2:
				return "2";
			case Keys.D3:
				return "3";
			case Keys.D4:
				return "4";
			case Keys.D5:
				return "5";
			case Keys.D6:
				return "6";
			case Keys.D7:
				return "7";
			case Keys.D8:
				return "8";
			case Keys.D9:
				return "9";
			case Keys.A:
				return "A";
			case Keys.B:
				return "B";
			case Keys.C:
				return "C";
			case Keys.D:
				return "D";
			case Keys.E:
				return "E";
			case Keys.F:
				return "F";
			case Keys.G:
				return "G";
			case Keys.H:
				return "H";
			case Keys.I:
				return "I";
			case Keys.J:
				return "J";
			case Keys.K:
				return "K";
			case Keys.L:
				return "L";
			case Keys.M:
				return "M";
			case Keys.N:
				return "N";
			case Keys.O:
				return "O";
			case Keys.P:
				return "P";
			case Keys.Q:
				return "Q";
			case Keys.R:
				return "R";
			case Keys.S:
				return "S";
			case Keys.T:
				return "T";
			case Keys.U:
				return "U";
			case Keys.V:
				return "V";
			case Keys.W:
				return "W";
			case Keys.X:
				return "X";
			case Keys.Y:
				return "Y";
			case Keys.Z:
				return "Z";
			case Keys.NumPad0:
				return "Num 0";
			case Keys.NumPad1:
				return "Num 1";
			case Keys.NumPad2:
				return "Num 2";
			case Keys.NumPad3:
				return "Num 3";
			case Keys.NumPad4:
				return "Num 4";
			case Keys.NumPad5:
				return "Num 5";
			case Keys.NumPad6:
				return "Num 6";
			case Keys.NumPad7:
				return "Num 7";
			case Keys.NumPad8:
				return "Num 8";
			case Keys.NumPad9:
				return "Num 9";
			case Keys.Multiply:
				return "Num *";
			case Keys.Add:
				return "Num +";
			case Keys.Subtract:
				return "Num -";
			case Keys.Decimal:
				return "Num .";
			case Keys.Divide:
				return "Num /";
			case Keys.F1:
				return "F1";
			case Keys.F2:
				return "F2";
			case Keys.F3:
				return "F3";
			case Keys.F4:
				return "F4";
			case Keys.F5:
				return "F5";
			case Keys.F6:
				return "F6";
			case Keys.F7:
				return "F7";
			case Keys.F8:
				return "F8";
			case Keys.F9:
				return "F9";
			case Keys.F10:
				return "F10";
			case Keys.F11:
				return "F11";
			case Keys.F12:
				return "F12";
			case Keys.F13:
				return "F13";
			case Keys.F14:
				return "F14";
			case Keys.F15:
				return "F15";
			case Keys.F16:
				return "F16";
			case Keys.F17:
				return "F17";
			case Keys.F18:
				return "F18";
			case Keys.F19:
				return "F19";
			case Keys.F20:
				return "F20";
			case Keys.F21:
				return "F21";
			case Keys.F22:
				return "F22";
			case Keys.F23:
				return "F23";
			case Keys.F24:
				return "F24";
			case Keys.NumLock:
				return "Num Lock";
			case Keys.Scroll:
				return "Scroll Lock";
			case Keys.OemSemicolon:
				return ";";
			case Keys.Oemplus:
				return "+";
			case Keys.Oemcomma:
				return ",";
			case Keys.OemMinus:
				return "-";
			case Keys.OemPeriod:
				return ".";
			case Keys.OemQuestion:
				return "?";
			case Keys.Oemtilde:
				return "`";
			default:
				switch (keys2)
				{
				case Keys.OemOpenBrackets:
					return "[";
				case Keys.OemPipe:
					return "|";
				case Keys.OemCloseBrackets:
					return "]";
				case Keys.OemQuotes:
					return "'";
				case Keys.OemBackslash:
					return "\\";
				}
				break;
			}
			return "Dunno";
		}
	}
}
