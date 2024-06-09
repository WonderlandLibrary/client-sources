using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using Microsoft.VisualBasic.Devices;
using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Management;
using System.Resources;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using Xh0kO1ZCmA.My;
using Xh0kO1ZCmA.My.Resources;

namespace Xh0kO1ZCmA
{
	[DesignerGenerated]
	public class Form1 : Form
	{
		private FullHook kbHook;

		private DataSession AllData;

		public bool Running;

		private string makel;

		private const int mouse_downclick = 4;

		private const int mouse_upclick = 2;

		private const int KeyDownBit = 32768;

		public bool LightTheme;

		public bool ExitLoop;

		public double FlatToggleColor;

		public double SavedFlatToggleColor;

		public bool FlatToggleFade;

		private string rndname;

		private string hashedhwid;

		private string LauncherPrefetch;

		private string LauncherPrefetch2;

		private string rndnamePrefetch;

		private string rndnamePrefetch2;

		public int ClicksMax;

		public int ClicksMin;

		private bool hotkey;

		private bool hotkey2;

		private int ayeeeeH;

		private int ayeeeeV;

		private int ayeeee2;

		private int iRed;

		private int iGreen;

		private int iBlue;

		private Process p;

		private bool hotkey3;

		private bool hotkey4;

		private bool hotkey5;

		private bool hotkey6;

		private bool BreakBlocks;

		private int? Min2;

		private int? Max2;

		private string customsoundpath;

		private int audiolength;

		private bool ClickSoundJitterToggle;

		private Random r;

		private int SwordSlot2;

		private int SwordSlot;

		private int RodSlot;

		private bool DownloadDone2;

		private bool SelfDestruct;

		private bool InInv;

		private bool InChat;

		private int Previous1;

		private int Previous2;

		private int Previous3;

		private int Min;

		private int Max;

		private int rndint;

		private int rndmin;

		private int rndmax;

		private int rndint2;

		private int RodMin;

		private int RodMax;

		private bool ColorCycle;

		private Dictionary<string, Keys> Hotkeys;

		public static cSettings Settings;

		private IContainer components;

		internal virtual Button Button1
		{
			get
			{
				return this._Button1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Button1_Click);
				Button button = this._Button1;
				if (button != null)
				{
					button.Click -= eventHandler;
				}
				this._Button1 = value;
				button = this._Button1;
				if (button != null)
				{
					button.Click += eventHandler;
				}
			}
		}

		internal virtual Button Button2
		{
			get
			{
				return this._Button2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Button2_Click);
				Button button = this._Button2;
				if (button != null)
				{
					button.Click -= eventHandler;
				}
				this._Button2 = value;
				button = this._Button2;
				if (button != null)
				{
					button.Click += eventHandler;
				}
			}
		}

		internal virtual Button Button3
		{
			get
			{
				return this._Button3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Button3_Click);
				Button button = this._Button3;
				if (button != null)
				{
					button.Click -= eventHandler;
				}
				this._Button3 = value;
				button = this._Button3;
				if (button != null)
				{
					button.Click += eventHandler;
				}
			}
		}

		internal virtual Button Button5
		{
			get
			{
				return this._Button5;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Button5_Click);
				Button button = this._Button5;
				if (button != null)
				{
					button.Click -= eventHandler;
				}
				this._Button5 = value;
				button = this._Button5;
				if (button != null)
				{
					button.Click += eventHandler;
				}
			}
		}

		internal virtual Button Button6
		{
			get
			{
				return this._Button6;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Button6_Click);
				Button button = this._Button6;
				if (button != null)
				{
					button.Click -= eventHandler;
				}
				this._Button6 = value;
				button = this._Button6;
				if (button != null)
				{
					button.Click += eventHandler;
				}
			}
		}

		internal virtual Button Button7
		{
			get
			{
				return this._Button7;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Button7_Click);
				Button button = this._Button7;
				if (button != null)
				{
					button.Click -= eventHandler;
				}
				this._Button7 = value;
				button = this._Button7;
				if (button != null)
				{
					button.Click += eventHandler;
				}
			}
		}

		internal virtual Button clickapply
		{
			get
			{
				return this._clickapply;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.clickapply_Click);
				Button button = this._clickapply;
				if (button != null)
				{
					button.Click -= eventHandler;
				}
				this._clickapply = value;
				button = this._clickapply;
				if (button != null)
				{
					button.Click += eventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual ComboBox ComboBox10
		{
			get
			{
				return this._ComboBox10;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.ComboBox10_SelectedIndexChanged);
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox10_KeyPress);
				ComboBox comboBox = this._ComboBox10;
				if (comboBox != null)
				{
					comboBox.SelectedIndexChanged -= eventHandler;
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox10 = value;
				comboBox = this._ComboBox10;
				if (comboBox != null)
				{
					comboBox.SelectedIndexChanged += eventHandler;
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox11
		{
			get
			{
				return this._ComboBox11;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox11_KeyPress);
				EventHandler eventHandler = new EventHandler(this.ComboBox11_SelectedIndexChanged);
				ComboBox comboBox = this._ComboBox11;
				if (comboBox != null)
				{
					comboBox.KeyPress -= keyPressEventHandler;
					comboBox.SelectedIndexChanged -= eventHandler;
				}
				this._ComboBox11 = value;
				comboBox = this._ComboBox11;
				if (comboBox != null)
				{
					comboBox.KeyPress += keyPressEventHandler;
					comboBox.SelectedIndexChanged += eventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox12
		{
			get
			{
				return this._ComboBox12;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox12_KeyPress);
				ComboBox comboBox = this._ComboBox12;
				if (comboBox != null)
				{
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox12 = value;
				comboBox = this._ComboBox12;
				if (comboBox != null)
				{
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox13
		{
			get
			{
				return this._ComboBox13;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.ComboBox13_SelectedIndexChanged);
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox13_KeyPress);
				ComboBox comboBox = this._ComboBox13;
				if (comboBox != null)
				{
					comboBox.SelectedIndexChanged -= eventHandler;
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox13 = value;
				comboBox = this._ComboBox13;
				if (comboBox != null)
				{
					comboBox.SelectedIndexChanged += eventHandler;
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox14
		{
			get
			{
				return this._ComboBox14;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox14_KeyPress);
				ComboBox comboBox = this._ComboBox14;
				if (comboBox != null)
				{
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox14 = value;
				comboBox = this._ComboBox14;
				if (comboBox != null)
				{
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox15
		{
			get
			{
				return this._ComboBox15;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.ComboBox15_SelectedIndexChanged);
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox15_KeyPress);
				ComboBox comboBox = this._ComboBox15;
				if (comboBox != null)
				{
					comboBox.SelectedIndexChanged -= eventHandler;
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox15 = value;
				comboBox = this._ComboBox15;
				if (comboBox != null)
				{
					comboBox.SelectedIndexChanged += eventHandler;
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox16
		{
			get
			{
				return this._ComboBox16;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox16_KeyPress);
				ComboBox comboBox = this._ComboBox16;
				if (comboBox != null)
				{
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox16 = value;
				comboBox = this._ComboBox16;
				if (comboBox != null)
				{
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox17
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual ComboBox ComboBox2
		{
			get
			{
				return this._ComboBox2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox2_KeyPress);
				ComboBox comboBox = this._ComboBox2;
				if (comboBox != null)
				{
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox2 = value;
				comboBox = this._ComboBox2;
				if (comboBox != null)
				{
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox3
		{
			get
			{
				return this._ComboBox3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox3_KeyPress);
				ComboBox comboBox = this._ComboBox3;
				if (comboBox != null)
				{
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox3 = value;
				comboBox = this._ComboBox3;
				if (comboBox != null)
				{
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox4
		{
			get
			{
				return this._ComboBox4;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.ComboBox4_SelectedIndexChanged);
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox4_KeyPress);
				ComboBox comboBox = this._ComboBox4;
				if (comboBox != null)
				{
					comboBox.SelectedIndexChanged -= eventHandler;
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox4 = value;
				comboBox = this._ComboBox4;
				if (comboBox != null)
				{
					comboBox.SelectedIndexChanged += eventHandler;
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox5
		{
			get
			{
				return this._ComboBox5;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.ComboBox5_SelectedIndexChanged);
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox5_KeyPress);
				ComboBox comboBox = this._ComboBox5;
				if (comboBox != null)
				{
					comboBox.SelectedIndexChanged -= eventHandler;
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox5 = value;
				comboBox = this._ComboBox5;
				if (comboBox != null)
				{
					comboBox.SelectedIndexChanged += eventHandler;
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox6
		{
			get
			{
				return this._ComboBox6;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox6_KeyPress);
				ComboBox comboBox = this._ComboBox6;
				if (comboBox != null)
				{
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox6 = value;
				comboBox = this._ComboBox6;
				if (comboBox != null)
				{
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox7
		{
			get
			{
				return this._ComboBox7;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox7_KeyPress);
				ComboBox comboBox = this._ComboBox7;
				if (comboBox != null)
				{
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox7 = value;
				comboBox = this._ComboBox7;
				if (comboBox != null)
				{
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox8
		{
			get
			{
				return this._ComboBox8;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox8_KeyPress);
				ComboBox comboBox = this._ComboBox8;
				if (comboBox != null)
				{
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox8 = value;
				comboBox = this._ComboBox8;
				if (comboBox != null)
				{
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual ComboBox ComboBox9
		{
			get
			{
				return this._ComboBox9;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(this.ComboBox9_KeyPress);
				ComboBox comboBox = this._ComboBox9;
				if (comboBox != null)
				{
					comboBox.KeyPress -= keyPressEventHandler;
				}
				this._ComboBox9 = value;
				comboBox = this._ComboBox9;
				if (comboBox != null)
				{
					comboBox.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual FlatButton FlatButton1
		{
			get
			{
				return this._FlatButton1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.FlatButton1_Click);
				FlatButton flatButton = this._FlatButton1;
				if (flatButton != null)
				{
					flatButton.Click -= eventHandler;
				}
				this._FlatButton1 = value;
				flatButton = this._FlatButton1;
				if (flatButton != null)
				{
					flatButton.Click += eventHandler;
				}
			}
		}

		internal virtual FlatButton FlatButton2
		{
			get
			{
				return this._FlatButton2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.FlatButton2_Click);
				FlatButton flatButton = this._FlatButton2;
				if (flatButton != null)
				{
					flatButton.Click -= eventHandler;
				}
				this._FlatButton2 = value;
				flatButton = this._FlatButton2;
				if (flatButton != null)
				{
					flatButton.Click += eventHandler;
				}
			}
		}

		internal virtual FlatButton FlatButton3
		{
			get
			{
				return this._FlatButton3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.FlatButton3_Click);
				FlatButton flatButton = this._FlatButton3;
				if (flatButton != null)
				{
					flatButton.Click -= eventHandler;
				}
				this._FlatButton3 = value;
				flatButton = this._FlatButton3;
				if (flatButton != null)
				{
					flatButton.Click += eventHandler;
				}
			}
		}

		internal virtual FlatButton FlatButton4
		{
			get
			{
				return this._FlatButton4;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.FlatButton4_Click);
				FlatButton flatButton = this._FlatButton4;
				if (flatButton != null)
				{
					flatButton.Click -= eventHandler;
				}
				this._FlatButton4 = value;
				flatButton = this._FlatButton4;
				if (flatButton != null)
				{
					flatButton.Click += eventHandler;
				}
			}
		}

		internal virtual FlatButton FlatButton5
		{
			get
			{
				return this._FlatButton5;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.FlatButton5_Click);
				FlatButton flatButton = this._FlatButton5;
				if (flatButton != null)
				{
					flatButton.Click -= eventHandler;
				}
				this._FlatButton5 = value;
				flatButton = this._FlatButton5;
				if (flatButton != null)
				{
					flatButton.Click += eventHandler;
				}
			}
		}

		internal virtual FlatToggle FlatToggle1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle10
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle11
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle12
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle13
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle2
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Xh0kO1ZCmA.FlatToggle2 FlatToggle22
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle3
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Xh0kO1ZCmA.FlatToggle3 FlatToggle31
		{
			get
			{
				return this._FlatToggle31;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				Xh0kO1ZCmA.FlatToggle3.CheckedChangedEventHandler checkedChangedEventHandler = new Xh0kO1ZCmA.FlatToggle3.CheckedChangedEventHandler(this.FlatToggle31_CheckedChanged);
				Xh0kO1ZCmA.FlatToggle3 flatToggle3 = this._FlatToggle31;
				if (flatToggle3 != null)
				{
					flatToggle3.CheckedChanged -= checkedChangedEventHandler;
				}
				this._FlatToggle31 = value;
				flatToggle3 = this._FlatToggle31;
				if (flatToggle3 != null)
				{
					flatToggle3.CheckedChanged += checkedChangedEventHandler;
				}
			}
		}

		internal virtual FlatToggle FlatToggle4
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Xh0kO1ZCmA.FlatToggle4 FlatToggle41
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle5
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle6
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle7
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle8
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatToggle FlatToggle9
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual FlatTrackBar FlatTrackBar1
		{
			get
			{
				return this._FlatTrackBar1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler scrollEventHandler = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar1_Scroll);
				FlatTrackBar flatTrackBar = this._FlatTrackBar1;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= scrollEventHandler;
				}
				this._FlatTrackBar1 = value;
				flatTrackBar = this._FlatTrackBar1;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += scrollEventHandler;
				}
			}
		}

		internal virtual FlatTrackBar FlatTrackBar2
		{
			get
			{
				return this._FlatTrackBar2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler scrollEventHandler = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar2_Scroll);
				FlatTrackBar flatTrackBar = this._FlatTrackBar2;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= scrollEventHandler;
				}
				this._FlatTrackBar2 = value;
				flatTrackBar = this._FlatTrackBar2;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += scrollEventHandler;
				}
			}
		}

		internal virtual FlatTrackBar FlatTrackBar3
		{
			get
			{
				return this._FlatTrackBar3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler scrollEventHandler = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar3_Scroll);
				FlatTrackBar flatTrackBar = this._FlatTrackBar3;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= scrollEventHandler;
				}
				this._FlatTrackBar3 = value;
				flatTrackBar = this._FlatTrackBar3;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += scrollEventHandler;
				}
			}
		}

		internal virtual FlatTrackBar FlatTrackBar4
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual ImageList ImageList1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Label Label1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Label Label15
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Label Label16
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Label Label2
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Label Label3
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Label Label4
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Label Label5
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Label Label6
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual ListBox ListBox1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NotifyIcon NotifyIcon1
		{
			get
			{
				return this._NotifyIcon1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				MouseEventHandler mouseEventHandler = new MouseEventHandler(this.NotifyIcon1_MouseDoubleClick);
				NotifyIcon notifyIcon = this._NotifyIcon1;
				if (notifyIcon != null)
				{
					notifyIcon.MouseDoubleClick -= mouseEventHandler;
				}
				this._NotifyIcon1 = value;
				notifyIcon = this._NotifyIcon1;
				if (notifyIcon != null)
				{
					notifyIcon.MouseDoubleClick += mouseEventHandler;
				}
			}
		}

		internal virtual NSLabel NsLabel1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel10
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel11
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel12
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel13
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel14
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel15
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel16
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel17
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel18
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel19
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel2
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel20
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel21
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel22
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel23
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel24
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel25
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel26
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel27
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel2 NsLabel28
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel29
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel3
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel30
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel3 NsLabel31
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel32
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel33
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel34
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel4
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel5
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel6
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel7
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel8
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual NSLabel NsLabel9
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual OpenFileDialog ofdsound
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Panel Panel1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Panel Panel2
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual PictureBox PictureBox1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual PictureBox PictureBox2
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual TabControlClass TabControlClass1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual TabPage TabPage1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual TabPage TabPage2
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual TabPage TabPage3
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual TabPage TabPage4
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual TabPage TabPage5
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual TabPage TabPage6
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual System.Windows.Forms.Timer Timer1
		{
			get
			{
				return this._Timer1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = (object a0, EventArgs a1) => this.Timer1_Tick(RuntimeHelpers.GetObjectValue(a0), a1);
				System.Windows.Forms.Timer timer = this._Timer1;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer1 = value;
				timer = this._Timer1;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer10
		{
			get
			{
				return this._Timer10;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer10_Tick);
				System.Windows.Forms.Timer timer = this._Timer10;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer10 = value;
				timer = this._Timer10;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer11
		{
			get
			{
				return this._Timer11;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer11_Tick);
				System.Windows.Forms.Timer timer = this._Timer11;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer11 = value;
				timer = this._Timer11;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer12
		{
			get
			{
				return this._Timer12;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer12_Tick);
				System.Windows.Forms.Timer timer = this._Timer12;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer12 = value;
				timer = this._Timer12;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer13
		{
			get
			{
				return this._Timer13;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer13_Tick);
				System.Windows.Forms.Timer timer = this._Timer13;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer13 = value;
				timer = this._Timer13;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer14
		{
			get
			{
				return this._Timer14;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer14_Tick);
				System.Windows.Forms.Timer timer = this._Timer14;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer14 = value;
				timer = this._Timer14;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer15
		{
			get
			{
				return this._Timer15;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer15_TickAsync);
				System.Windows.Forms.Timer timer = this._Timer15;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer15 = value;
				timer = this._Timer15;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer16
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual System.Windows.Forms.Timer Timer17
		{
			get
			{
				return this._Timer17;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer17_Tick);
				System.Windows.Forms.Timer timer = this._Timer17;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer17 = value;
				timer = this._Timer17;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer18
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual System.Windows.Forms.Timer Timer19
		{
			get
			{
				return this._Timer19;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer19_Tick);
				System.Windows.Forms.Timer timer = this._Timer19;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer19 = value;
				timer = this._Timer19;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer2
		{
			get
			{
				return this._Timer2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer2_Tick);
				System.Windows.Forms.Timer timer = this._Timer2;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer2 = value;
				timer = this._Timer2;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer20
		{
			get
			{
				return this._Timer20;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer20_Tick);
				System.Windows.Forms.Timer timer = this._Timer20;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer20 = value;
				timer = this._Timer20;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer21
		{
			get
			{
				return this._Timer21;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer21_Tick);
				System.Windows.Forms.Timer timer = this._Timer21;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer21 = value;
				timer = this._Timer21;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer3
		{
			get
			{
				return this._Timer3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer3_Tick);
				System.Windows.Forms.Timer timer = this._Timer3;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer3 = value;
				timer = this._Timer3;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer4
		{
			get
			{
				return this._Timer4;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer4_Tick);
				System.Windows.Forms.Timer timer = this._Timer4;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer4 = value;
				timer = this._Timer4;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer5
		{
			get
			{
				return this._Timer5;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer5_Tick);
				System.Windows.Forms.Timer timer = this._Timer5;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer5 = value;
				timer = this._Timer5;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer6
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual System.Windows.Forms.Timer Timer7
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual System.Windows.Forms.Timer Timer8
		{
			get
			{
				return this._Timer8;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer8_Tick);
				System.Windows.Forms.Timer timer = this._Timer8;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer8 = value;
				timer = this._Timer8;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual System.Windows.Forms.Timer Timer9
		{
			get
			{
				return this._Timer9;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Timer9_Tick);
				System.Windows.Forms.Timer timer = this._Timer9;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._Timer9 = value;
				timer = this._Timer9;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		internal virtual ToolTip ToolTip1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual TrackBar TrackBar1
		{
			get
			{
				return this._TrackBar1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.TrackBar1_Scroll);
				TrackBar trackBar = this._TrackBar1;
				if (trackBar != null)
				{
					trackBar.Scroll -= eventHandler;
				}
				this._TrackBar1 = value;
				trackBar = this._TrackBar1;
				if (trackBar != null)
				{
					trackBar.Scroll += eventHandler;
				}
			}
		}

		internal virtual TrackBar TrackBar2
		{
			get
			{
				return this._TrackBar2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.TrackBar2_Scroll);
				TrackBar trackBar = this._TrackBar2;
				if (trackBar != null)
				{
					trackBar.Scroll -= eventHandler;
				}
				this._TrackBar2 = value;
				trackBar = this._TrackBar2;
				if (trackBar != null)
				{
					trackBar.Scroll += eventHandler;
				}
			}
		}

		static Form1()
		{
			Form1.Settings = new cSettings();
		}

		public Form1()
		{
			base.Load += new EventHandler(this.Form1_Load);
			base.FormClosing += new FormClosingEventHandler(this.Form1_FormClosing);
			this.Running = false;
			this.ExitLoop = true;
			this.FlatToggleColor = 53;
			this.SavedFlatToggleColor = 53;
			this.FlatToggleFade = false;
			this.audiolength = 60;
			this.r = new Random();
			this.SelfDestruct = false;
			this.InInv = false;
			this.InChat = false;
			this.Hotkeys = new Dictionary<string, Keys>()
			{
				{ "None", (Keys)Conversions.ToInteger(null) },
				{ "A", Keys.A },
				{ "B", Keys.B },
				{ "C", Keys.C },
				{ "D", Keys.D },
				{ "E", Keys.E },
				{ "F", Keys.F },
				{ "G", Keys.G },
				{ "H", Keys.H },
				{ "I", Keys.I },
				{ "J", Keys.J },
				{ "K", Keys.K },
				{ "L", Keys.L },
				{ "M", Keys.M },
				{ "N", Keys.N },
				{ "O", Keys.O },
				{ "P", Keys.P },
				{ "Q", Keys.Q },
				{ "R", Keys.R },
				{ "S", Keys.S },
				{ "T", Keys.T },
				{ "U", Keys.U },
				{ "V", Keys.V },
				{ "W", Keys.W },
				{ "X", Keys.X },
				{ "Y", Keys.Y },
				{ "Z", Keys.Z },
				{ "1", Keys.D1 },
				{ "2", Keys.D2 },
				{ "3", Keys.D3 },
				{ "4", Keys.D4 },
				{ "5", Keys.D5 },
				{ "6", Keys.D6 },
				{ "7", Keys.D7 },
				{ "8", Keys.D8 },
				{ "9", Keys.D9 },
				{ "MB1", Keys.LButton },
				{ "MB2", Keys.RButton },
				{ "MB3", Keys.MButton },
				{ "MB4", Keys.XButton1 },
				{ "MB5", Keys.XButton2 },
				{ "F1", Keys.F1 },
				{ "F2", Keys.F2 },
				{ "F3", Keys.F3 },
				{ "F4", Keys.F4 },
				{ "F5", Keys.F5 },
				{ "F6", Keys.F6 },
				{ "F7", Keys.F7 },
				{ "F8", Keys.F8 },
				{ "F9", Keys.F9 },
				{ "F10", Keys.F10 },
				{ "F11", Keys.F11 },
				{ "F12", Keys.F12 }
			};
			this.InitializeComponent();
		}

		private void Button1_Click(object sender, EventArgs e)
		{
			Form1._Closure$__91-0 variable = null;
			IEnumerator<string> enumerator = null;
			IEnumerator<string> enumerator1 = null;
			IEnumerator<string> enumerator2 = null;
			IEnumerator<string> enumerator3 = null;
			IEnumerator<string> enumerator4 = null;
			IEnumerator<string> enumerator5 = null;
			IEnumerator<string> enumerator6 = null;
			IEnumerator<string> enumerator7 = null;
			IEnumerator<string> enumerator8 = null;
			IEnumerator<string> enumerator9 = null;
			IEnumerator<string> enumerator10 = null;
			Func<string, string> func;
			Func<string, string> func1;
			Func<string, string> func2;
			Func<string, string> func3;
			Func<string, string> func4;
			Func<string, string> func5;
			Func<string, string> func6;
			Func<string, string> func7;
			Func<string, string> func8;
			Func<string, string> func9;
			Func<string, string> func10;
			variable = new Form1._Closure$__91-0(variable);
			this.GUIReset();
			this.Melt2(1);
			if (this.FlatToggle41.Checked)
			{
				this.Melt3(1);
			}
			string str = this.rndname.Substring(6);
			string str1 = string.Concat(this.rndname.Substring(0, 5), ".exe");
			this.rndname.Substring(6);
			string str2 = Path.Combine(new string[] { Environment.GetFolderPath(Environment.SpecialFolder.Recent) });
			variable.$VB$Local_filter = this.rndname.Substring(0, 5);
			IEnumerable<string> files = 
				from fl in Directory.GetFiles(str2)
				where fl.Contains(this.$VB$Local_filter)
				select fl;
			if (Form1._Closure$__.$I91-1 == null)
			{
				func = (string fl) => fl;
				Form1._Closure$__.$I91-1 = func;
			}
			else
			{
				func = Form1._Closure$__.$I91-1;
			}
			using (IEnumerable<string> strs = files.Select<string, string>(func))
			{
				enumerator = strs.GetEnumerator();
				while (enumerator.MoveNext())
				{
					File.Delete(enumerator.Current);
				}
			}
			string str3 = Path.Combine(new string[] { Environment.GetFolderPath(Environment.SpecialFolder.Recent) });
			variable.$VB$Local_filter6 = this.LauncherPrefetch;
			IEnumerable<string> files1 = 
				from fl6 in Directory.GetFiles(str3)
				where fl6.Contains(this.$VB$Local_filter6)
				select fl6;
			if (Form1._Closure$__.$I91-3 == null)
			{
				func1 = (string fl6) => fl6;
				Form1._Closure$__.$I91-3 = func1;
			}
			else
			{
				func1 = Form1._Closure$__.$I91-3;
			}
			using (IEnumerable<string> strs1 = files1.Select<string, string>(func1))
			{
				enumerator1 = strs.GetEnumerator();
				while (enumerator1.MoveNext())
				{
					File.Delete(enumerator1.Current);
				}
			}
			string str4 = Path.Combine(new string[] { "C:\\Windows\\Prefetch" });
			this.rndnamePrefetch = this.rndname.Substring(0, 5);
			this.rndnamePrefetch2 = this.rndnamePrefetch.ToUpper();
			variable.$VB$Local_filter3 = this.rndnamePrefetch2;
			IEnumerable<string> files2 = 
				from fl3 in Directory.GetFiles(str4)
				where fl3.Contains(this.$VB$Local_filter3)
				select fl3;
			if (Form1._Closure$__.$I91-5 == null)
			{
				func2 = (string fl3) => fl3;
				Form1._Closure$__.$I91-5 = func2;
			}
			else
			{
				func2 = Form1._Closure$__.$I91-5;
			}
			using (IEnumerable<string> strs2 = files2.Select<string, string>(func2))
			{
				enumerator2 = strs2.GetEnumerator();
				while (enumerator2.MoveNext())
				{
					File.Delete(enumerator2.Current);
				}
			}
			string str5 = Path.Combine(new string[] { "C:\\Windows\\Prefetch" });
			variable.$VB$Local_filter5 = this.LauncherPrefetch.ToUpper();
			IEnumerable<string> files3 = 
				from fl5 in Directory.GetFiles(str5)
				where fl5.Contains(this.$VB$Local_filter5)
				select fl5;
			if (Form1._Closure$__.$I91-7 == null)
			{
				func3 = (string fl5) => fl5;
				Form1._Closure$__.$I91-7 = func3;
			}
			else
			{
				func3 = Form1._Closure$__.$I91-7;
			}
			using (IEnumerable<string> strs3 = files3.Select<string, string>(func3))
			{
				enumerator3 = strs3.GetEnumerator();
				while (enumerator3.MoveNext())
				{
					File.Delete(enumerator3.Current);
				}
			}
			string[] strArrays = new string[] { "C:\\Windows\\Prefetch" };
			variable.$VB$Local_filter10 = "RUNDLL32";
			IEnumerable<string> files4 = 
				from fl10 in Directory.GetFiles(Path.Combine(strArrays))
				where fl10.Contains(this.$VB$Local_filter10)
				select fl10;
			if (Form1._Closure$__.$I91-9 == null)
			{
				func4 = (string fl10) => fl10;
				Form1._Closure$__.$I91-9 = func4;
			}
			else
			{
				func4 = Form1._Closure$__.$I91-9;
			}
			using (IEnumerable<string> strs4 = files4.Select<string, string>(func4))
			{
				enumerator4 = strs4.GetEnumerator();
				while (enumerator4.MoveNext())
				{
					File.Delete(enumerator4.Current);
				}
			}
			string[] strArrays1 = new string[] { "C:\\Windows\\Prefetch" };
			variable.$VB$Local_filter11 = "REG";
			IEnumerable<string> files5 = 
				from fl11 in Directory.GetFiles(Path.Combine(strArrays1))
				where fl11.Contains(this.$VB$Local_filter11)
				select fl11;
			if (Form1._Closure$__.$I91-11 == null)
			{
				func5 = (string fl11) => fl11;
				Form1._Closure$__.$I91-11 = func5;
			}
			else
			{
				func5 = Form1._Closure$__.$I91-11;
			}
			using (IEnumerable<string> strs5 = files5.Select<string, string>(func5))
			{
				enumerator5 = strs5.GetEnumerator();
				while (enumerator5.MoveNext())
				{
					File.Delete(enumerator5.Current);
				}
			}
			string[] strArrays2 = new string[] { "C:\\Windows\\Prefetch" };
			variable.$VB$Local_filter12 = "CMD";
			IEnumerable<string> files6 = 
				from fl12 in Directory.GetFiles(Path.Combine(strArrays2))
				where fl12.Contains(this.$VB$Local_filter12)
				select fl12;
			if (Form1._Closure$__.$I91-13 == null)
			{
				func6 = (string fl12) => fl12;
				Form1._Closure$__.$I91-13 = func6;
			}
			else
			{
				func6 = Form1._Closure$__.$I91-13;
			}
			using (IEnumerable<string> strs6 = files6.Select<string, string>(func6))
			{
				enumerator6 = strs6.GetEnumerator();
				while (enumerator6.MoveNext())
				{
					File.Delete(enumerator6.Current);
				}
			}
			string[] strArrays3 = new string[] { "C:\\Windows\\Prefetch" };
			variable.$VB$Local_filter13 = "CACLS";
			IEnumerable<string> files7 = 
				from fl13 in Directory.GetFiles(Path.Combine(strArrays3))
				where fl13.Contains(this.$VB$Local_filter13)
				select fl13;
			if (Form1._Closure$__.$I91-15 == null)
			{
				func7 = (string fl13) => fl13;
				Form1._Closure$__.$I91-15 = func7;
			}
			else
			{
				func7 = Form1._Closure$__.$I91-15;
			}
			using (IEnumerable<string> strs7 = files7.Select<string, string>(func7))
			{
				enumerator7 = strs7.GetEnumerator();
				while (enumerator7.MoveNext())
				{
					File.Delete(enumerator7.Current);
				}
			}
			string[] strArrays4 = new string[] { "C:\\Windows\\Prefetch" };
			variable.$VB$Local_filter14 = "WSCRIPT";
			IEnumerable<string> files8 = 
				from fl14 in Directory.GetFiles(Path.Combine(strArrays4))
				where fl14.Contains(this.$VB$Local_filter14)
				select fl14;
			if (Form1._Closure$__.$I91-17 == null)
			{
				func8 = (string fl14) => fl14;
				Form1._Closure$__.$I91-17 = func8;
			}
			else
			{
				func8 = Form1._Closure$__.$I91-17;
			}
			using (IEnumerable<string> strs8 = files8.Select<string, string>(func8))
			{
				enumerator8 = strs8.GetEnumerator();
				while (enumerator8.MoveNext())
				{
					File.Delete(enumerator8.Current);
				}
			}
			string[] strArrays5 = new string[] { string.Concat("C:\\Users\\", Environment.UserName, "\\AppData\\Local\\Temp") };
			variable.$VB$Local_filter8 = "JNativeHook";
			IEnumerable<string> files9 = 
				from fl8 in Directory.GetFiles(Path.Combine(strArrays5))
				where fl8.Contains(this.$VB$Local_filter8)
				select fl8;
			if (Form1._Closure$__.$I91-19 == null)
			{
				func9 = (string fl8) => fl8;
				Form1._Closure$__.$I91-19 = func9;
			}
			else
			{
				func9 = Form1._Closure$__.$I91-19;
			}
			using (IEnumerable<string> strs9 = files9.Select<string, string>(func9))
			{
				enumerator9 = strs9.GetEnumerator();
				while (enumerator9.MoveNext())
				{
					File.Delete(enumerator9.Current);
				}
			}
			string[] strArrays6 = new string[] { string.Concat("C:\\Users\\", Environment.UserName, "\\AppData\\Local\\Temp") };
			variable.$VB$Local_filter9 = "jna";
			IEnumerable<string> files10 = 
				from fl9 in Directory.GetFiles(Path.Combine(strArrays6))
				where fl9.Contains(this.$VB$Local_filter9)
				select fl9;
			if (Form1._Closure$__.$I91-21 == null)
			{
				func10 = (string fl9) => fl9;
				Form1._Closure$__.$I91-21 = func10;
			}
			else
			{
				func10 = Form1._Closure$__.$I91-21;
			}
			using (IEnumerable<string> strs10 = files10.Select<string, string>(func10))
			{
				enumerator10 = strs10.GetEnumerator();
				while (enumerator10.MoveNext())
				{
					File.Delete(enumerator10.Current);
				}
			}
			Interaction.Shell("cmd.exe /c ping 1.1.1.1 -n 1 -w 5000 >NUL", AppWinStyle.NormalFocus, false, -1);
			Interaction.Shell(string.Concat("cmd.exe /c reg delete HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\AppCompatFlags\\Compatibility Assistant\\Store /v ", str, " /f"), AppWinStyle.NormalFocus, false, -1);
			Interaction.Shell(string.Concat(new string[] { "cmd.exe /c reg delete HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\AppCompatFlags\\Compatibility Assistant\\Store /v C:\\Users\\", Environment.UserName, "\\AppData\\Local\\Temp\\", str1, " /f" }), AppWinStyle.NormalFocus, false, -1);
			Interaction.Shell(string.Concat("cmd.exe /c reg delete HKEY_CURRENT_USER\\Software\\Classes\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\MuiCache /v ", str, ".FriendlyAppName"), AppWinStyle.NormalFocus, false, -1);
			Interaction.Shell(string.Concat(new string[] { "cmd.exe /c reg delete HKEY_CURRENT_USER\\Software\\Classes\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\MuiCache /v C:\\Users\\", Environment.UserName, "\\AppData\\Local\\Temp\\", str1, ".FriendlyAppName" }), AppWinStyle.NormalFocus, false, -1);
			base.Close();
		}

		private void Button2_Click(object sender, EventArgs e)
		{
			this.LightTheme = false;
			Color color = Color.FromArgb(255, 53, 53, 53);
			Color color1 = Color.FromArgb(255, 42, 42, 42);
			this.BackColor = color1;
			this.Button1.FlatStyle = FlatStyle.Popup;
			this.Button2.FlatStyle = FlatStyle.Popup;
			this.Button3.FlatStyle = FlatStyle.Popup;
			this.Button5.FlatStyle = FlatStyle.Popup;
			this.Button6.FlatStyle = FlatStyle.Popup;
			this.Button7.FlatStyle = FlatStyle.Popup;
			this.clickapply.FlatStyle = FlatStyle.Popup;
			this.clickapply.BackColor = color1;
			this.Button1.BackColor = color1;
			this.Button2.BackColor = color1;
			this.Button3.BackColor = color1;
			this.Button5.BackColor = color1;
			this.Button6.BackColor = color1;
			this.Button7.BackColor = color1;
			this.clickapply.BackColor = color1;
			this.Panel1.BackColor = color;
			this.Panel2.BackColor = color;
			this.clickapply.ForeColor = Color.White;
			this.Button1.ForeColor = Color.White;
			this.Button2.ForeColor = Color.White;
			this.Button3.ForeColor = Color.White;
			this.Button5.ForeColor = Color.White;
			this.Button6.ForeColor = Color.White;
			this.Button7.ForeColor = Color.White;
			this.Label1.ForeColor = Color.Indigo;
			this.Label2.ForeColor = Color.White;
			this.Label3.ForeColor = Color.White;
			this.Label4.ForeColor = Color.White;
			this.Label5.ForeColor = Color.White;
			this.Label6.ForeColor = Color.White;
			this.Label15.ForeColor = Color.White;
			this.Label16.ForeColor = Color.White;
		}

		private void Button3_Click(object sender, EventArgs e)
		{
			this.LightTheme = true;
			this.BackColor = Color.White;
			this.Button1.FlatStyle = FlatStyle.Standard;
			this.Button2.FlatStyle = FlatStyle.Standard;
			this.Button3.FlatStyle = FlatStyle.Standard;
			this.Button5.FlatStyle = FlatStyle.Standard;
			this.Button6.FlatStyle = FlatStyle.Standard;
			this.Button7.FlatStyle = FlatStyle.Standard;
			this.clickapply.FlatStyle = FlatStyle.Standard;
			this.Button1.BackColor = Color.White;
			this.Button2.BackColor = Color.White;
			this.Button3.BackColor = Color.White;
			this.Button5.BackColor = Color.White;
			this.Button6.BackColor = Color.White;
			this.Button7.BackColor = Color.White;
			this.clickapply.BackColor = Color.White;
			this.clickapply.ForeColor = Color.Indigo;
			this.Button1.ForeColor = Color.Indigo;
			this.Button2.ForeColor = Color.Indigo;
			this.Button3.ForeColor = Color.Indigo;
			this.Button5.ForeColor = Color.Indigo;
			this.Button6.ForeColor = Color.Indigo;
			this.Button7.ForeColor = Color.Indigo;
			this.Label1.ForeColor = Color.Indigo;
			this.Label2.ForeColor = Color.Indigo;
			this.Label3.ForeColor = Color.Indigo;
			this.Label4.ForeColor = Color.Indigo;
			this.Label5.ForeColor = Color.Indigo;
			this.Label6.ForeColor = Color.Indigo;
			this.Label15.ForeColor = Color.Indigo;
			this.Label16.ForeColor = Color.Indigo;
			this.Panel1.BackColor = Color.Transparent;
			this.Panel2.BackColor = Color.Transparent;
		}

		private void Button5_Click(object sender, EventArgs e)
		{
			this.GUIReset();
		}

		private void Button6_Click(object sender, EventArgs e)
		{
			this.GUIReset();
			this.ExitLoop = false;
			long num = (long)255;
			long num1 = (long)0;
			long num2 = (long)0;
			do
			{
				if (num == (long)255 && num2 == (long)255 || this.ExitLoop)
				{
					break;
				}
				long num3 = (long)10;
				this.Label1.ForeColor = Color.FromArgb(checked((int)num), checked((int)num1), checked((int)num2));
				long num4 = (long)1;
				do
				{
					num1 = num4;
					if (this.ExitLoop)
					{
						return;
					}
					this.Label1.ForeColor = Color.FromArgb(checked((int)num), checked((int)num1), checked((int)num2));
					Thread.Sleep(checked((int)num3));
					Application.DoEvents();
					num4 = checked(num4 + (long)1);
				}
				while (num4 <= (long)255);
				num4 = (long)254;
				do
				{
					num = num4;
					if (this.ExitLoop)
					{
						return;
					}
					this.Label1.ForeColor = Color.FromArgb(checked((int)num), checked((int)num1), checked((int)num2));
					Thread.Sleep(checked((int)num3));
					Application.DoEvents();
					num4 = checked(num4 + (long)-1);
				}
				while (num4 >= (long)0);
				num4 = (long)1;
				do
				{
					num2 = num4;
					if (this.ExitLoop)
					{
						return;
					}
					this.Label1.ForeColor = Color.FromArgb(checked((int)num), checked((int)num1), checked((int)num2));
					Thread.Sleep(checked((int)num3));
					Application.DoEvents();
					num4 = checked(num4 + (long)1);
				}
				while (num4 <= (long)255);
				num4 = (long)254;
				do
				{
					num1 = num4;
					if (this.ExitLoop)
					{
						return;
					}
					this.Label1.ForeColor = Color.FromArgb(checked((int)num), checked((int)num1), checked((int)num2));
					Thread.Sleep(checked((int)num3));
					Application.DoEvents();
					num4 = checked(num4 + (long)-1);
				}
				while (num4 >= (long)0);
				num4 = (long)1;
				do
				{
					num = num4;
					if (this.ExitLoop)
					{
						return;
					}
					this.Label1.ForeColor = Color.FromArgb(checked((int)num), checked((int)num1), checked((int)num2));
					Thread.Sleep(checked((int)num3));
					Application.DoEvents();
					num4 = checked(num4 + (long)1);
				}
				while (num4 <= (long)255);
				num4 = (long)254;
				do
				{
					num2 = num4;
					if (this.ExitLoop)
					{
						return;
					}
					this.Label1.ForeColor = Color.FromArgb(checked((int)num), checked((int)num1), checked((int)num2));
					Thread.Sleep(checked((int)num3));
					Application.DoEvents();
					num4 = checked(num4 + (long)-1);
				}
				while (num4 >= (long)0);
			}
			while (!this.ExitLoop);
		}

		private void Button7_Click(object sender, EventArgs e)
		{
			OpenFileDialog openFileDialog = this.ofdsound;
			openFileDialog.FileName = "";
			openFileDialog.Title = "Select A .wav File";
			openFileDialog.Filter = ".wav|*.wav";
			if (openFileDialog.ShowDialog() != System.Windows.Forms.DialogResult.OK)
			{
				this.ComboBox3.Text = "No Sound Selected";
			}
			else
			{
				this.customsoundpath = this.ofdsound.FileName;
				this.ComboBox3.Text = "Custom Sound";
				this.audiolength = (void*)(checked((int)this.GetMediaLength(this.customsoundpath)));
				Thread.Sleep(200);
				this.audiolength = checked(this.audiolength - 65);
			}
		}

		private void clickapply_Click(object sender, EventArgs e)
		{
			this.ayeeeeH = Conversions.ToInteger(this.ComboBox2.Text);
			this.ayeeeeV = Conversions.ToInteger(this.ComboBox12.Text);
			this.ayeeee2 = Conversions.ToInteger(this.ComboBox1.Text);
			if (this.FlatToggle8.Checked && this.FlatToggle9.Checked)
			{
				Interaction.MsgBox("Manual CPS Drops Needs To Be Turned Off For Smart CPS Drops To Work Properly", MsgBoxStyle.Information, this.Text);
				this.FlatToggle8.Checked = false;
				this.FlatToggle9.Checked = false;
			}
			if (!this.FlatToggle3.Checked)
			{
				this.Timer2.Stop();
			}
			else
			{
				this.Timer2.Start();
			}
			if (!this.FlatToggle1.Checked)
			{
				base.KeyPreview = false;
			}
			else
			{
				base.KeyPreview = true;
				this.Timer3.Start();
			}
			if (!this.FlatToggle2.Checked)
			{
				this.Timer4.Stop();
			}
			else
			{
				this.Timer4.Start();
			}
			if (!this.FlatToggle6.Checked)
			{
				this.Timer5.Stop();
			}
			else
			{
				this.Timer5.Start();
			}
			if (!this.FlatToggle4.Checked)
			{
				this.Timer8.Stop();
			}
			else
			{
				this.Timer8.Start();
			}
			if (!this.FlatToggle5.Checked)
			{
				this.BreakBlocks = false;
			}
			else
			{
				this.BreakBlocks = true;
			}
			if (!this.FlatToggle11.Checked)
			{
				this.Timer10.Stop();
			}
			else
			{
				this.Timer10.Start();
			}
			if (!this.FlatToggle10.Checked)
			{
				this.Timer13.Stop();
			}
			else
			{
				this.Timer13.Start();
			}
			if (!this.FlatToggle7.Checked)
			{
				this.Timer14.Stop();
			}
			else
			{
				this.Timer14.Start();
			}
			if (this.FlatToggle12.Checked)
			{
				this.Timer15.Start();
				return;
			}
			this.Timer15.Stop();
		}

		private void ComboBox10_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox10_SelectedIndexChanged(object sender, EventArgs e)
		{
			this.SwordSlot = Conversions.ToInteger(this.ComboBox10.Text);
		}

		private void ComboBox11_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox11_SelectedIndexChanged(object sender, EventArgs e)
		{
			this.SwordSlot2 = Conversions.ToInteger(this.ComboBox11.Text);
		}

		private void ComboBox12_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox13_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox13_SelectedIndexChanged(object sender, EventArgs e)
		{
			if (Operators.CompareString(this.ComboBox13.Text, "1", false) == 0)
			{
				this.Timer4.Interval = 50;
			}
			if (Operators.CompareString(this.ComboBox13.Text, "2", false) == 0)
			{
				this.Timer4.Interval = 45;
			}
			if (Operators.CompareString(this.ComboBox13.Text, "3", false) == 0)
			{
				this.Timer4.Interval = 40;
			}
			if (Operators.CompareString(this.ComboBox13.Text, "4", false) == 0)
			{
				this.Timer4.Interval = 35;
			}
			if (Operators.CompareString(this.ComboBox13.Text, "5", false) == 0)
			{
				this.Timer4.Interval = 30;
			}
			if (Operators.CompareString(this.ComboBox13.Text, "6", false) == 0)
			{
				this.Timer4.Interval = 25;
			}
			if (Operators.CompareString(this.ComboBox13.Text, "7", false) == 0)
			{
				this.Timer4.Interval = 20;
			}
			if (Operators.CompareString(this.ComboBox13.Text, "8", false) == 0)
			{
				this.Timer4.Interval = 15;
			}
			if (Operators.CompareString(this.ComboBox13.Text, "9", false) == 0)
			{
				this.Timer4.Interval = 10;
			}
			if (Operators.CompareString(this.ComboBox13.Text, "10", false) == 0)
			{
				this.Timer4.Interval = 5;
			}
		}

		private void ComboBox14_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox15_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox15_SelectedIndexChanged(object sender, EventArgs e)
		{
			this.RodSlot = Conversions.ToInteger(this.ComboBox15.Text);
		}

		private void ComboBox16_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox2_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox3_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox4_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox4_SelectedIndexChanged(object sender, EventArgs e)
		{
			this.Min2 = new int?(Conversions.ToInteger(this.ComboBox4.Text));
		}

		private void ComboBox5_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox5_SelectedIndexChanged(object sender, EventArgs e)
		{
			this.Max2 = new int?(Conversions.ToInteger(this.ComboBox5.Text));
		}

		private void ComboBox6_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox7_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox8_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private void ComboBox9_KeyPress(object sender, KeyPressEventArgs e)
		{
			e.Handled = true;
		}

		private string CpuId()
		{
			IEnumerator enumerator = null;
			string str = ".";
			object objectValue = RuntimeHelpers.GetObjectValue(NewLateBinding.LateGet(RuntimeHelpers.GetObjectValue(Interaction.GetObject(string.Concat("winmgmts:{impersonationLevel=impersonate}!\\\\", str, "\\root\\cimv2"), null)), null, "ExecQuery", new object[] { "Select * from Win32_Processor" }, null, null, null));
			string str1 = "";
			try
			{
				enumerator = ((IEnumerable)objectValue).GetEnumerator();
				while (enumerator.MoveNext())
				{
					object obj = RuntimeHelpers.GetObjectValue(enumerator.Current);
					str1 = Conversions.ToString(Operators.ConcatenateObject(string.Concat(str1, ", "), NewLateBinding.LateGet(obj, null, "ProcessorId", new object[0], null, null, null)));
				}
			}
			finally
			{
				if (enumerator is IDisposable)
				{
					(enumerator as IDisposable).Dispose();
				}
			}
			if (str1.Length > 0)
			{
				str1 = str1.Substring(2);
			}
			return str1;
		}

		public void Delay(double dblSecs)
		{
			DateAndTime.Now.AddSeconds(1.15740740740741E-05);
			DateTime dateTime = DateAndTime.Now.AddSeconds(1.15740740740741E-05);
			DateTime dateTime1 = dateTime.AddSeconds(dblSecs);
			while (DateTime.Compare(DateAndTime.Now, dateTime1) <= 0)
			{
				Application.DoEvents();
			}
		}

		[DebuggerNonUserCode]
		protected override void Dispose(bool disposing)
		{
			try
			{
				if (disposing && this.components != null)
				{
					this.components.Dispose();
				}
			}
			finally
			{
				base.Dispose(disposing);
			}
		}

		private void FlatButton1_Click(object sender, EventArgs e)
		{
			Form1.Settings.Load();
			if (Operators.CompareString(Form1.Settings.Theme, "Light", false) != 0)
			{
				this.Button2.PerformClick();
			}
			else
			{
				this.Button3.PerformClick();
			}
			this.TrackBar1.Value = Form1.Settings.MinCPS;
			this.TrackBar2.Value = Form1.Settings.MaxCPS;
			this.NsLabel17.Value1 = Conversions.ToString(this.TrackBar1.Value);
			this.NsLabel16.Value1 = Conversions.ToString(this.TrackBar2.Value);
			this.FlatToggle1.Checked = Form1.Settings.Click;
			this.FlatToggle2.Checked = Form1.Settings.Jitter;
			this.FlatToggle3.Checked = Form1.Settings.Randomize;
			this.FlatToggle4.Checked = Form1.Settings.MCOnly;
			this.FlatToggle5.Checked = Form1.Settings.BreakBlocks;
			this.ComboBox8.Text = Form1.Settings.ClickToggle;
			this.ComboBox2.Text = Conversions.ToString(Form1.Settings.JitterVertical);
			this.ComboBox12.Text = Conversions.ToString(Form1.Settings.JitterHorizontal);
			this.ComboBox13.Text = Conversions.ToString(Form1.Settings.JitterSmoothness);
			this.FlatToggle6.Checked = Form1.Settings.ClickSounds;
			this.ComboBox3.Text = Form1.Settings.ClickSoundsType;
			this.FlatToggle9.Checked = Form1.Settings.CPSDrops;
			this.ComboBox1.Text = Conversions.ToString(Form1.Settings.CPSDropsValue);
			this.FlatToggle8.Checked = Form1.Settings.SmartCPSDrops;
			this.FlatToggle7.Checked = Form1.Settings.BlockHit;
			this.FlatToggle22.Checked = Form1.Settings.IgnoreBindsInChat;
			this.ComboBox14.Text = Form1.Settings.ChatKey;
			this.ComboBox16.Text = Form1.Settings.HideFromTaskbarKey;
			this.FlatToggle10.Checked = Form1.Settings.WTap;
			this.FlatTrackBar1.Value = Form1.Settings.DBW;
			this.FlatTrackBar2.Value = Form1.Settings.DBWW;
			this.FlatToggle11.Checked = Form1.Settings.ThrowPot;
			this.ComboBox6.Text = Form1.Settings.ThrowPotToggleKey;
			this.ComboBox7.Text = Form1.Settings.ThrowPotInvKey;
			this.ComboBox4.Text = Conversions.ToString(Form1.Settings.ThrowPotFirstPot);
			this.ComboBox5.Text = Conversions.ToString(Form1.Settings.ThrowPotLastPot);
			this.ComboBox11.Text = Conversions.ToString(Form1.Settings.ThrowPotSwordSlot);
			this.FlatToggle13.Checked = Form1.Settings.DropBowls;
			this.ComboBox17.Text = Form1.Settings.DropBowlsDropKey;
			this.FlatToggle12.Checked = Form1.Settings.RodTrick;
			this.FlatTrackBar3.Value = Form1.Settings.DBSS;
			this.ComboBox9.Text = Form1.Settings.RodTrickToggleKey;
			this.ComboBox15.Text = Conversions.ToString(Form1.Settings.RodTrickRodSlot);
			this.ComboBox10.Text = Conversions.ToString(Form1.Settings.RodTrickSwordSlot);
			this.FlatToggle31.Checked = Form1.Settings.SimulateHumanizedClicks;
			this.FlatTrackBar4.Value = Form1.Settings.MaxDelay;
			this.ClicksMin = Form1.Settings.MinRDelay;
			this.ClicksMax = Form1.Settings.MaxRDelay;
			this.NsLabel30.Value2 = Form1.Settings.AverageCPS;
			this.NsLabel33.Value2 = Form1.Settings.MinimumCPS;
			this.NsLabel32.Value2 = Form1.Settings.MaximumCPS;
			this.clickapply.PerformClick();
			if (Form1.Settings.ColorCycle == Conversions.ToBoolean("True"))
			{
				this.Button6.PerformClick();
				return;
			}
			this.Button5.PerformClick();
		}

		private void FlatButton2_Click(object sender, EventArgs e)
		{
			Form1.Settings.Save();
		}

		private void FlatButton3_Click(object sender, EventArgs e)
		{
			this.NsLabel31.Visible = false;
			this.NsLabel28.Visible = true;
			this.PictureBox2.Visible = true;
			this.kbHook = new FullHook();
			Control control = this;
			this.AllData = new DataSession(true, ref control);
			this.AllData.ActionAdded += new DataSession.ActionAddedEventHandler(this.NewEvent);
			control = this;
			this.kbHook.Start(ref control, this.AllData);
			this.Running = !this.Running;
		}

		private void FlatButton4_Click(object sender, EventArgs e)
		{
			this.NsLabel28.Visible = false;
			this.PictureBox2.Visible = false;
			this.NsLabel31.Visible = true;
			if (this.kbHook != null)
			{
				this.kbHook.Dispose();
			}
		}

		private void FlatButton5_Click(object sender, EventArgs e)
		{
			this.NsLabel28.Visible = false;
			this.PictureBox2.Visible = false;
			this.NsLabel31.Visible = true;
			this.ListBox1.Items.Clear();
			this.ClicksMin = 0;
			this.ClicksMax = 0;
			this.NsLabel30.Value2 = " N/A";
			this.NsLabel33.Value2 = " N/A";
			this.NsLabel32.Value2 = " N/A";
			this.FlatToggle31.Checked = false;
			base.Invalidate();
			if (this.kbHook != null)
			{
				this.kbHook.Dispose();
			}
		}

		private void FlatToggle31_CheckedChanged(object sender)
		{
			if (this.ListBox1.Items.Count <= 0)
			{
				Interaction.MsgBox("No Recorded Clicks Found!", MsgBoxStyle.Critical, this.Text);
				this.FlatToggle31.Checked = false;
			}
		}

		private void FlatTrackBar1_Scroll(object sender)
		{
			if (this.FlatTrackBar1.Value < 1)
			{
				this.FlatTrackBar1.ShowValue = false;
				this.NsLabel23.Visible = true;
				this.Timer13.Interval = 400;
				return;
			}
			this.FlatTrackBar1.ShowValue = true;
			this.NsLabel23.Visible = false;
			this.Timer13.Interval = this.FlatTrackBar1.Value;
		}

		private void FlatTrackBar2_Scroll(object sender)
		{
			if (this.FlatTrackBar2.Value >= 1)
			{
				this.FlatTrackBar2.ShowValue = true;
				this.NsLabel22.Visible = false;
				return;
			}
			this.FlatTrackBar2.ShowValue = false;
			this.NsLabel22.Visible = true;
		}

		private void FlatTrackBar3_Scroll(object sender)
		{
			if (this.FlatTrackBar3.Value >= 1)
			{
				this.FlatTrackBar3.ShowValue = true;
				this.NsLabel24.Visible = false;
				return;
			}
			this.FlatTrackBar3.ShowValue = false;
			this.NsLabel24.Visible = true;
		}

		private void Form1_FormClosing(object sender, FormClosingEventArgs e)
		{
			this.GUIReset();
			if (this.kbHook != null)
			{
				this.kbHook.Dispose();
			}
			e.Cancel = true;
			this.Timer20.Enabled = true;
		}

		private void Form1_Load(object sender, EventArgs e)
		{
			MessageBox.Show("Cracked by Del Camp, get fucked xD");
		}

		[DllImport("user32.dll", CharSet=CharSet.None, ExactSpelling=false)]
		public static extern short GetAsyncKeyState(Keys vKey);

		private string GetCaption()
		{
			StringBuilder stringBuilder = new StringBuilder(256);
			Form1.GetWindowText(Form1.GetForegroundWindow(), stringBuilder, stringBuilder.Capacity);
			return stringBuilder.ToString();
		}

		public long GetFolderSize(string dPath)
		{
			long num;
			try
			{
				long length = (long)0;
				IEnumerator enumerator = (new DirectoryInfo(dPath)).GetFiles("*", SearchOption.AllDirectories).GetEnumerator();
				while ((-enumerator.MoveNext() & -4) != 0)
				{
					length = checked(length + ((FileInfo)enumerator.Current).Length);
				}
				num = length;
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				Interaction.MsgBox(exception.Message, MsgBoxStyle.OkOnly, null);
				num = (long)-1;
				ProjectData.ClearProjectError();
			}
			return num;
		}

		[DllImport("user32", CharSet=CharSet.Ansi, ExactSpelling=true, SetLastError=true)]
		private static extern IntPtr GetForegroundWindow();

		public ulong GetMediaLength(string FileName)
		{
			ulong num;
			ulong num1;
			string str = new string(' ', 50);
			if (!File.Exists(this.customsoundpath))
			{
				throw new FileNotFoundException(string.Format("File {0} was not found.", this.customsoundpath));
			}
			int length = 0;
			Form1.mciSendString(string.Concat("Open ", this.customsoundpath, " alias MediaFile"), string.Empty, ref length, IntPtr.Zero);
			length = 0;
			Form1.mciSendString("Set MediaFile time format milliseconds", string.Empty, ref length, IntPtr.Zero);
			length = str.Length;
			Form1.mciSendString("Status MediaFile length", str, ref length, IntPtr.Zero);
			str = str.Trim();
			if (ulong.TryParse(str, out num1))
			{
				length = 0;
				Form1.mciSendString("Close MediaFile", null, ref length, IntPtr.Zero);
				num = num1;
			}
			else
			{
				Interaction.MsgBox(string.Format("Failed to retrieve the media length for '{0}'.", this.customsoundpath), MsgBoxStyle.OkOnly, null);
				num = (ulong)0;
			}
			return num;
		}

		[DllImport("user32", CharSet=CharSet.Auto, ExactSpelling=false, SetLastError=true)]
		private static extern int GetWindowText(IntPtr hWnd, StringBuilder lpString, int cch);

		private void GUIReset()
		{
			this.ExitLoop = true;
			Color color = Color.FromArgb(255, 42, 42, 42);
			if (this.BackColor == color)
			{
				this.clickapply.ForeColor = Color.White;
				this.Button1.ForeColor = Color.White;
				this.Button2.ForeColor = Color.White;
				this.Button3.ForeColor = Color.White;
				this.Button5.ForeColor = Color.White;
				this.Button6.ForeColor = Color.White;
				this.Button7.ForeColor = Color.White;
				this.Label1.ForeColor = Color.Indigo;
				this.Label2.ForeColor = Color.White;
				this.Label3.ForeColor = Color.White;
				this.Label4.ForeColor = Color.White;
				this.Label5.ForeColor = Color.White;
				this.Label6.ForeColor = Color.White;
				this.Label15.ForeColor = Color.White;
				this.Label16.ForeColor = Color.White;
				return;
			}
			this.clickapply.ForeColor = Color.Indigo;
			this.Button1.ForeColor = Color.Indigo;
			this.Button2.ForeColor = Color.Indigo;
			this.Button3.ForeColor = Color.Indigo;
			this.Button5.ForeColor = Color.Indigo;
			this.Button6.ForeColor = Color.Indigo;
			this.Button7.ForeColor = Color.Indigo;
			this.Label1.ForeColor = Color.Indigo;
			this.Label2.ForeColor = Color.Indigo;
			this.Label3.ForeColor = Color.Indigo;
			this.Label4.ForeColor = Color.Indigo;
			this.Label5.ForeColor = Color.Indigo;
			this.Label6.ForeColor = Color.Indigo;
			this.Label15.ForeColor = Color.Indigo;
			this.Label16.ForeColor = Color.Indigo;
		}

		[DebuggerStepThrough]
		private void InitializeComponent()
		{
			this.components = new System.ComponentModel.Container();
			ComponentResourceManager componentResourceManager = new ComponentResourceManager(typeof(Form1));
			this.Timer1 = new System.Windows.Forms.Timer(this.components);
			this.Timer2 = new System.Windows.Forms.Timer(this.components);
			this.Timer3 = new System.Windows.Forms.Timer(this.components);
			this.Timer4 = new System.Windows.Forms.Timer(this.components);
			this.Timer5 = new System.Windows.Forms.Timer(this.components);
			this.Timer6 = new System.Windows.Forms.Timer(this.components);
			this.clickapply = new Button();
			this.Button2 = new Button();
			this.Button3 = new Button();
			this.Button5 = new Button();
			this.Timer7 = new System.Windows.Forms.Timer(this.components);
			this.Timer8 = new System.Windows.Forms.Timer(this.components);
			this.Panel1 = new Panel();
			this.PictureBox1 = new PictureBox();
			this.Label1 = new Label();
			this.Panel2 = new Panel();
			this.Button6 = new Button();
			this.Timer9 = new System.Windows.Forms.Timer(this.components);
			this.Timer10 = new System.Windows.Forms.Timer(this.components);
			this.ofdsound = new OpenFileDialog();
			this.Timer11 = new System.Windows.Forms.Timer(this.components);
			this.Timer12 = new System.Windows.Forms.Timer(this.components);
			this.Timer13 = new System.Windows.Forms.Timer(this.components);
			this.Timer14 = new System.Windows.Forms.Timer(this.components);
			this.Timer15 = new System.Windows.Forms.Timer(this.components);
			this.Timer16 = new System.Windows.Forms.Timer(this.components);
			this.NotifyIcon1 = new NotifyIcon(this.components);
			this.Timer17 = new System.Windows.Forms.Timer(this.components);
			this.Timer18 = new System.Windows.Forms.Timer(this.components);
			this.TabControlClass1 = new TabControlClass();
			this.TabPage1 = new TabPage();
			this.NsLabel13 = new NSLabel();
			this.NsLabel14 = new NSLabel();
			this.NsLabel21 = new NSLabel();
			this.NsLabel19 = new NSLabel();
			this.NsLabel18 = new NSLabel();
			this.NsLabel17 = new NSLabel();
			this.NsLabel16 = new NSLabel();
			this.ComboBox8 = new ComboBox();
			this.NsLabel15 = new NSLabel();
			this.NsLabel12 = new NSLabel();
			this.ComboBox13 = new ComboBox();
			this.FlatToggle9 = new FlatToggle();
			this.FlatToggle8 = new FlatToggle();
			this.FlatToggle7 = new FlatToggle();
			this.FlatToggle6 = new FlatToggle();
			this.FlatToggle5 = new FlatToggle();
			this.FlatToggle4 = new FlatToggle();
			this.FlatToggle3 = new FlatToggle();
			this.FlatToggle2 = new FlatToggle();
			this.FlatToggle1 = new FlatToggle();
			this.Label16 = new Label();
			this.Label15 = new Label();
			this.ComboBox12 = new ComboBox();
			this.Button7 = new Button();
			this.TrackBar2 = new TrackBar();
			this.Label6 = new Label();
			this.Label4 = new Label();
			this.TrackBar1 = new TrackBar();
			this.ComboBox1 = new ComboBox();
			this.Label2 = new Label();
			this.Label3 = new Label();
			this.Label5 = new Label();
			this.ComboBox3 = new ComboBox();
			this.ComboBox2 = new ComboBox();
			this.TabPage2 = new TabPage();
			this.NsLabel2 = new NSLabel();
			this.NsLabel1 = new NSLabel();
			this.ComboBox14 = new ComboBox();
			this.ComboBox16 = new ComboBox();
			this.FlatToggle22 = new Xh0kO1ZCmA.FlatToggle2();
			this.TabPage3 = new TabPage();
			this.NsLabel23 = new NSLabel();
			this.NsLabel22 = new NSLabel();
			this.NsLabel20 = new NSLabel();
			this.FlatTrackBar2 = new FlatTrackBar();
			this.NsLabel8 = new NSLabel();
			this.FlatTrackBar1 = new FlatTrackBar();
			this.FlatToggle10 = new FlatToggle();
			this.TabPage4 = new TabPage();
			this.NsLabel26 = new NSLabel();
			this.ComboBox17 = new ComboBox();
			this.FlatToggle13 = new FlatToggle();
			this.NsLabel7 = new NSLabel();
			this.ComboBox11 = new ComboBox();
			this.FlatToggle11 = new FlatToggle();
			this.NsLabel5 = new NSLabel();
			this.NsLabel4 = new NSLabel();
			this.NsLabel6 = new NSLabel();
			this.ComboBox7 = new ComboBox();
			this.NsLabel3 = new NSLabel();
			this.ComboBox4 = new ComboBox();
			this.ComboBox5 = new ComboBox();
			this.ComboBox6 = new ComboBox();
			this.TabPage5 = new TabPage();
			this.NsLabel9 = new NSLabel();
			this.NsLabel25 = new NSLabel();
			this.ComboBox10 = new ComboBox();
			this.NsLabel11 = new NSLabel();
			this.NsLabel24 = new NSLabel();
			this.ComboBox15 = new ComboBox();
			this.FlatToggle12 = new FlatToggle();
			this.NsLabel10 = new NSLabel();
			this.ComboBox9 = new ComboBox();
			this.FlatTrackBar3 = new FlatTrackBar();
			this.TabPage6 = new TabPage();
			this.NsLabel33 = new NSLabel();
			this.NsLabel32 = new NSLabel();
			this.NsLabel30 = new NSLabel();
			this.FlatButton5 = new FlatButton();
			this.NsLabel34 = new NSLabel();
			this.FlatTrackBar4 = new FlatTrackBar();
			this.NsLabel31 = new NSLabel3();
			this.NsLabel29 = new NSLabel();
			this.FlatToggle31 = new Xh0kO1ZCmA.FlatToggle3();
			this.FlatButton4 = new FlatButton();
			this.FlatButton3 = new FlatButton();
			this.PictureBox2 = new PictureBox();
			this.NsLabel28 = new NSLabel2();
			this.ListBox1 = new ListBox();
			this.FlatButton2 = new FlatButton();
			this.FlatButton1 = new FlatButton();
			this.NsLabel27 = new NSLabel();
			this.ImageList1 = new ImageList(this.components);
			this.Timer19 = new System.Windows.Forms.Timer(this.components);
			this.Timer20 = new System.Windows.Forms.Timer(this.components);
			this.Timer21 = new System.Windows.Forms.Timer(this.components);
			this.ToolTip1 = new ToolTip(this.components);
			this.FlatToggle41 = new Xh0kO1ZCmA.FlatToggle4();
			this.Button1 = new Button();
			this.Panel1.SuspendLayout();
			((ISupportInitialize)this.PictureBox1).BeginInit();
			this.Panel2.SuspendLayout();
			this.TabControlClass1.SuspendLayout();
			this.TabPage1.SuspendLayout();
			((ISupportInitialize)this.TrackBar2).BeginInit();
			((ISupportInitialize)this.TrackBar1).BeginInit();
			this.TabPage2.SuspendLayout();
			this.TabPage3.SuspendLayout();
			this.TabPage4.SuspendLayout();
			this.TabPage5.SuspendLayout();
			this.TabPage6.SuspendLayout();
			((ISupportInitialize)this.PictureBox2).BeginInit();
			base.SuspendLayout();
			this.Timer1.Interval = 80;
			this.Timer3.Interval = 1;
			this.Timer4.Interval = 50;
			this.Timer5.Interval = 200;
			this.clickapply.BackColor = SystemColors.Window;
			this.clickapply.Font = new System.Drawing.Font("Tahoma", 9f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.clickapply.ForeColor = Color.Indigo;
			this.clickapply.Location = new Point(19, 17);
			this.clickapply.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.clickapply.Name = "clickapply";
			this.clickapply.Size = new System.Drawing.Size(74, 23);
			this.clickapply.TabIndex = 2;
			this.clickapply.Text = "Apply";
			this.clickapply.UseVisualStyleBackColor = false;
			this.Button2.BackColor = SystemColors.Window;
			this.Button2.Font = new System.Drawing.Font("Tahoma", 8.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.Button2.ForeColor = Color.Indigo;
			this.Button2.Location = new Point(264, 7);
			this.Button2.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.Button2.Name = "Button2";
			this.Button2.Size = new System.Drawing.Size(74, 23);
			this.Button2.TabIndex = 17;
			this.Button2.Text = "Dark Theme";
			this.Button2.UseVisualStyleBackColor = false;
			this.Button3.BackColor = SystemColors.Window;
			this.Button3.Font = new System.Drawing.Font("Tahoma", 8.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.Button3.ForeColor = Color.Indigo;
			this.Button3.Location = new Point(342, 7);
			this.Button3.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.Button3.Name = "Button3";
			this.Button3.Size = new System.Drawing.Size(70, 23);
			this.Button3.TabIndex = 18;
			this.Button3.Text = "Light Theme";
			this.Button3.UseVisualStyleBackColor = false;
			this.Button5.BackColor = SystemColors.Control;
			this.Button5.Font = new System.Drawing.Font("Tahoma", 8.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.Button5.ForeColor = Color.Indigo;
			this.Button5.Location = new Point(264, 31);
			this.Button5.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.Button5.Name = "Button5";
			this.Button5.Size = new System.Drawing.Size(74, 23);
			this.Button5.TabIndex = 20;
			this.Button5.Text = "All Normal";
			this.Button5.UseVisualStyleBackColor = false;
			this.Timer7.Interval = 75;
			this.Panel1.BackColor = Color.Transparent;
			this.Panel1.Controls.Add(this.PictureBox1);
			this.Panel1.Controls.Add(this.Label1);
			this.Panel1.Location = new Point(-4, -3);
			this.Panel1.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.Panel1.Name = "Panel1";
			this.Panel1.Size = new System.Drawing.Size(684, 57);
			this.Panel1.TabIndex = 22;
			this.PictureBox1.Image = Resources.icon;
			this.PictureBox1.Location = new Point(460, 4);
			this.PictureBox1.Name = "PictureBox1";
			this.PictureBox1.Size = new System.Drawing.Size(59, 60);
			this.PictureBox1.TabIndex = 17;
			this.PictureBox1.TabStop = false;
			this.Label1.AutoSize = true;
			this.Label1.Font = new System.Drawing.Font("Tahoma", 26.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.Label1.ForeColor = Color.Indigo;
			this.Label1.Location = new Point(159, 8);
			this.Label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.Label1.Name = "Label1";
			this.Label1.Size = new System.Drawing.Size(302, 42);
			this.Label1.TabIndex = 16;
			this.Label1.Text = "GRAPE CLICKER";
			this.Panel2.BackColor = Color.Transparent;
			this.Panel2.Controls.Add(this.Button1);
			this.Panel2.Controls.Add(this.FlatToggle41);
			this.Panel2.Controls.Add(this.Button6);
			this.Panel2.Controls.Add(this.Button3);
			this.Panel2.Controls.Add(this.Button2);
			this.Panel2.Controls.Add(this.Button5);
			this.Panel2.Controls.Add(this.clickapply);
			this.Panel2.Location = new Point(-4, 287);
			this.Panel2.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.Panel2.Name = "Panel2";
			this.Panel2.Size = new System.Drawing.Size(684, 61);
			this.Panel2.TabIndex = 23;
			this.Button6.BackColor = SystemColors.Window;
			this.Button6.Font = new System.Drawing.Font("Tahoma", 6.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.Button6.ForeColor = Color.Indigo;
			this.Button6.Location = new Point(342, 31);
			this.Button6.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.Button6.Name = "Button6";
			this.Button6.Size = new System.Drawing.Size(70, 23);
			this.Button6.TabIndex = 21;
			this.Button6.Text = "Color Cycle";
			this.Button6.UseVisualStyleBackColor = false;
			this.Timer9.Enabled = true;
			this.Timer9.Interval = 25;
			this.Timer10.Interval = 1;
			this.ofdsound.FileName = "ofdsound";
			this.Timer11.Enabled = true;
			this.Timer11.Interval = 1;
			this.Timer12.Enabled = true;
			this.Timer12.Interval = 1;
			this.Timer13.Interval = 400;
			this.Timer14.Interval = 160;
			this.Timer15.Interval = 1;
			this.Timer16.Enabled = true;
			this.NotifyIcon1.BalloonTipText = "Grape Clicker has been minimized to the system tray.";
			this.NotifyIcon1.BalloonTipTitle = "Grape Clicker";
			this.NotifyIcon1.Icon = (System.Drawing.Icon)componentResourceManager.GetObject("NotifyIcon1.Icon");
			this.NotifyIcon1.Text = "Grape Clicker";
			this.Timer17.Enabled = true;
			this.Timer17.Interval = 50;
			this.Timer18.Enabled = true;
			this.Timer18.Interval = 80;
			this.TabControlClass1.Controls.Add(this.TabPage1);
			this.TabControlClass1.Controls.Add(this.TabPage2);
			this.TabControlClass1.Controls.Add(this.TabPage3);
			this.TabControlClass1.Controls.Add(this.TabPage4);
			this.TabControlClass1.Controls.Add(this.TabPage5);
			this.TabControlClass1.Controls.Add(this.TabPage6);
			this.TabControlClass1.Font = new System.Drawing.Font("Tahoma", 9f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.TabControlClass1.FontColor = Color.White;
			this.TabControlClass1.ImageList = this.ImageList1;
			this.TabControlClass1.ItemSize = new System.Drawing.Size(100, 40);
			this.TabControlClass1.Location = new Point(-1, 52);
			this.TabControlClass1.Multiline = true;
			this.TabControlClass1.Name = "TabControlClass1";
			this.TabControlClass1.SelectedIndex = 0;
			this.TabControlClass1.Size = new System.Drawing.Size(681, 239);
			this.TabControlClass1.SizeMode = TabSizeMode.Fixed;
			this.TabControlClass1.TabColor = Color.Indigo;
			this.TabControlClass1.TabIndex = 17;
			this.TabControlClass1.UseLightTheme = false;
			this.TabPage1.BackColor = Color.FromArgb(42, 42, 42);
			this.TabPage1.Controls.Add(this.NsLabel13);
			this.TabPage1.Controls.Add(this.NsLabel14);
			this.TabPage1.Controls.Add(this.NsLabel21);
			this.TabPage1.Controls.Add(this.NsLabel19);
			this.TabPage1.Controls.Add(this.NsLabel18);
			this.TabPage1.Controls.Add(this.NsLabel17);
			this.TabPage1.Controls.Add(this.NsLabel16);
			this.TabPage1.Controls.Add(this.ComboBox8);
			this.TabPage1.Controls.Add(this.NsLabel15);
			this.TabPage1.Controls.Add(this.NsLabel12);
			this.TabPage1.Controls.Add(this.ComboBox13);
			this.TabPage1.Controls.Add(this.FlatToggle9);
			this.TabPage1.Controls.Add(this.FlatToggle8);
			this.TabPage1.Controls.Add(this.FlatToggle7);
			this.TabPage1.Controls.Add(this.FlatToggle6);
			this.TabPage1.Controls.Add(this.FlatToggle5);
			this.TabPage1.Controls.Add(this.FlatToggle4);
			this.TabPage1.Controls.Add(this.FlatToggle3);
			this.TabPage1.Controls.Add(this.FlatToggle2);
			this.TabPage1.Controls.Add(this.FlatToggle1);
			this.TabPage1.Controls.Add(this.Label16);
			this.TabPage1.Controls.Add(this.Label15);
			this.TabPage1.Controls.Add(this.ComboBox12);
			this.TabPage1.Controls.Add(this.Button7);
			this.TabPage1.Controls.Add(this.TrackBar2);
			this.TabPage1.Controls.Add(this.Label6);
			this.TabPage1.Controls.Add(this.Label4);
			this.TabPage1.Controls.Add(this.TrackBar1);
			this.TabPage1.Controls.Add(this.ComboBox1);
			this.TabPage1.Controls.Add(this.Label2);
			this.TabPage1.Controls.Add(this.Label3);
			this.TabPage1.Controls.Add(this.Label5);
			this.TabPage1.Controls.Add(this.ComboBox3);
			this.TabPage1.Controls.Add(this.ComboBox2);
			this.TabPage1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.TabPage1.ImageIndex = 3;
			this.TabPage1.Location = new Point(4, 44);
			this.TabPage1.Name = "TabPage1";
			this.TabPage1.Padding = new System.Windows.Forms.Padding(3);
			this.TabPage1.Size = new System.Drawing.Size(673, 191);
			this.TabPage1.TabIndex = 0;
			this.TabPage1.Text = "AutoClicker";
			this.NsLabel13.Font = new System.Drawing.Font("Tahoma", 14.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel13.Location = new Point(1, 148);
			this.NsLabel13.Name = "NsLabel13";
			this.NsLabel13.Size = new System.Drawing.Size(53, 27);
			this.NsLabel13.TabIndex = 131;
			this.NsLabel13.Text = "NsLabel13";
			this.NsLabel13.Value1 = "MIN";
			this.NsLabel13.Value2 = "";
			this.NsLabel14.Font = new System.Drawing.Font("Tahoma", 8.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel14.Location = new Point(396, 29);
			this.NsLabel14.Name = "NsLabel14";
			this.NsLabel14.Size = new System.Drawing.Size(86, 16);
			this.NsLabel14.TabIndex = 130;
			this.NsLabel14.Text = "NsLabel14";
			this.NsLabel14.Value1 = "SMOOTHNESS";
			this.NsLabel14.Value2 = "";
			this.NsLabel21.Font = new System.Drawing.Font("Tahoma", 8.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel21.Location = new Point(314, 29);
			this.NsLabel21.Name = "NsLabel21";
			this.NsLabel21.Size = new System.Drawing.Size(83, 16);
			this.NsLabel21.TabIndex = 128;
			this.NsLabel21.Text = "NsLabel21";
			this.NsLabel21.Value1 = "HORIZONTAL";
			this.NsLabel21.Value2 = "";
			this.NsLabel19.Font = new System.Drawing.Font("Tahoma", 14.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel19.Location = new Point(61, 148);
			this.NsLabel19.Name = "NsLabel19";
			this.NsLabel19.Size = new System.Drawing.Size(56, 27);
			this.NsLabel19.TabIndex = 126;
			this.NsLabel19.Text = "NsLabel19";
			this.NsLabel19.Value1 = "MAX";
			this.NsLabel19.Value2 = "";
			this.NsLabel18.Font = new System.Drawing.Font("Segoe UI", 18f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel18.Location = new Point(46, 120);
			this.NsLabel18.Name = "NsLabel18";
			this.NsLabel18.Size = new System.Drawing.Size(15, 27);
			this.NsLabel18.TabIndex = 125;
			this.NsLabel18.Text = "NsLabel18";
			this.NsLabel18.Value1 = ":";
			this.NsLabel18.Value2 = "";
			this.NsLabel17.Font = new System.Drawing.Font("Tahoma", 18f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel17.Location = new Point(3, 123);
			this.NsLabel17.Name = "NsLabel17";
			this.NsLabel17.Size = new System.Drawing.Size(39, 27);
			this.NsLabel17.TabIndex = 124;
			this.NsLabel17.Text = "NsLabel17";
			this.NsLabel17.Value1 = "10";
			this.NsLabel17.Value2 = "";
			this.NsLabel16.Font = new System.Drawing.Font("Tahoma", 18f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel16.Location = new Point(66, 123);
			this.NsLabel16.Name = "NsLabel16";
			this.NsLabel16.Size = new System.Drawing.Size(39, 27);
			this.NsLabel16.TabIndex = 123;
			this.NsLabel16.Text = "NsLabel16";
			this.NsLabel16.Value1 = "13";
			this.NsLabel16.Value2 = "";
			this.ComboBox8.BackColor = Color.Indigo;
			this.ComboBox8.ForeColor = Color.White;
			this.ComboBox8.FormattingEnabled = true;
			this.ComboBox8.Items.AddRange(new object[] { "None", "MB1", "MB2", "MB3", "MB4", "MB5", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12" });
			this.ComboBox8.Location = new Point(258, 8);
			this.ComboBox8.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox8.Name = "ComboBox8";
			this.ComboBox8.Size = new System.Drawing.Size(51, 21);
			this.ComboBox8.TabIndex = 122;
			this.ComboBox8.Text = "None";
			this.NsLabel15.Font = new System.Drawing.Font("Tahoma", 8.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel15.Location = new Point(254, 30);
			this.NsLabel15.Name = "NsLabel15";
			this.NsLabel15.Size = new System.Drawing.Size(61, 14);
			this.NsLabel15.TabIndex = 121;
			this.NsLabel15.Text = "NsLabel15";
			this.NsLabel15.Value1 = "VERTICAL";
			this.NsLabel15.Value2 = "";
			this.NsLabel12.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel12.Location = new Point(491, 31);
			this.NsLabel12.Name = "NsLabel12";
			this.NsLabel12.Size = new System.Drawing.Size(141, 23);
			this.NsLabel12.TabIndex = 117;
			this.NsLabel12.Text = "NsLabel12";
			this.NsLabel12.Value1 = "CLICK SOUND";
			this.NsLabel12.Value2 = " TYPE";
			this.ComboBox13.BackColor = Color.Indigo;
			this.ComboBox13.ForeColor = Color.White;
			this.ComboBox13.FormattingEnabled = true;
			this.ComboBox13.Items.AddRange(new object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
			this.ComboBox13.Location = new Point(417, 46);
			this.ComboBox13.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox13.Name = "ComboBox13";
			this.ComboBox13.Size = new System.Drawing.Size(36, 21);
			this.ComboBox13.TabIndex = 66;
			this.ComboBox13.Text = "1";
			this.FlatToggle9.BackColor = Color.Transparent;
			this.FlatToggle9.Checked = false;
			this.FlatToggle9.Cursor = Cursors.Hand;
			this.FlatToggle9.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle9.Location = new Point(488, 81);
			this.FlatToggle9.Name = "FlatToggle9";
			this.FlatToggle9.Options = FlatToggle._Options.Style8;
			this.FlatToggle9.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle9.TabIndex = 63;
			this.FlatToggle9.Text = "   ";
			this.FlatToggle8.BackColor = Color.Transparent;
			this.FlatToggle8.Checked = false;
			this.FlatToggle8.Cursor = Cursors.Hand;
			this.FlatToggle8.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle8.Location = new Point(488, 117);
			this.FlatToggle8.Name = "FlatToggle8";
			this.FlatToggle8.Options = FlatToggle._Options.Style9;
			this.FlatToggle8.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle8.TabIndex = 62;
			this.FlatToggle8.Text = "FlatToggle8";
			this.FlatToggle7.BackColor = Color.Transparent;
			this.FlatToggle7.Checked = false;
			this.FlatToggle7.Cursor = Cursors.Hand;
			this.FlatToggle7.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle7.Location = new Point(488, 153);
			this.FlatToggle7.Name = "FlatToggle7";
			this.FlatToggle7.Options = FlatToggle._Options.Style10;
			this.FlatToggle7.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle7.TabIndex = 61;
			this.FlatToggle7.Text = "FlatToggle7";
			this.FlatToggle6.BackColor = Color.Transparent;
			this.FlatToggle6.Checked = false;
			this.FlatToggle6.Cursor = Cursors.Hand;
			this.FlatToggle6.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle6.Location = new Point(488, 8);
			this.FlatToggle6.Name = "FlatToggle6";
			this.FlatToggle6.Options = FlatToggle._Options.Style7;
			this.FlatToggle6.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle6.TabIndex = 60;
			this.FlatToggle6.Text = "FlatToggle6";
			this.FlatToggle5.BackColor = Color.Transparent;
			this.FlatToggle5.Checked = false;
			this.FlatToggle5.Cursor = Cursors.Hand;
			this.FlatToggle5.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle5.Location = new Point(118, 153);
			this.FlatToggle5.Name = "FlatToggle5";
			this.FlatToggle5.Options = FlatToggle._Options.Style6;
			this.FlatToggle5.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle5.TabIndex = 59;
			this.FlatToggle5.Text = "FlatToggle5";
			this.FlatToggle4.BackColor = Color.Transparent;
			this.FlatToggle4.Checked = false;
			this.FlatToggle4.Cursor = Cursors.Hand;
			this.FlatToggle4.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle4.Location = new Point(118, 117);
			this.FlatToggle4.Name = "FlatToggle4";
			this.FlatToggle4.Options = FlatToggle._Options.Style4;
			this.FlatToggle4.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle4.TabIndex = 58;
			this.FlatToggle4.Text = "FlatToggle4";
			this.FlatToggle3.BackColor = Color.Transparent;
			this.FlatToggle3.Checked = false;
			this.FlatToggle3.Cursor = Cursors.Hand;
			this.FlatToggle3.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle3.Location = new Point(118, 81);
			this.FlatToggle3.Name = "FlatToggle3";
			this.FlatToggle3.Options = FlatToggle._Options.Style5;
			this.FlatToggle3.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle3.TabIndex = 57;
			this.FlatToggle3.Text = "FlatToggle3";
			this.FlatToggle2.BackColor = Color.Transparent;
			this.FlatToggle2.Checked = false;
			this.FlatToggle2.Cursor = Cursors.Hand;
			this.FlatToggle2.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle2.Location = new Point(118, 44);
			this.FlatToggle2.Name = "FlatToggle2";
			this.FlatToggle2.Options = FlatToggle._Options.Style3;
			this.FlatToggle2.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle2.TabIndex = 56;
			this.FlatToggle2.Text = "FlatToggle2";
			this.FlatToggle1.BackColor = Color.Transparent;
			this.FlatToggle1.Checked = false;
			this.FlatToggle1.Cursor = Cursors.Hand;
			this.FlatToggle1.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle1.Location = new Point(118, 8);
			this.FlatToggle1.Name = "FlatToggle1";
			this.FlatToggle1.Options = FlatToggle._Options.Style2;
			this.FlatToggle1.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle1.TabIndex = 55;
			this.FlatToggle1.Text = "FlatToggle1";
			this.Label16.AutoSize = true;
			this.Label16.Location = new Point(317, 29);
			this.Label16.Name = "Label16";
			this.Label16.Size = new System.Drawing.Size(54, 13);
			this.Label16.TabIndex = 54;
			this.Label16.Text = "Horizontal";
			this.Label15.AutoSize = true;
			this.Label15.Location = new Point(259, 29);
			this.Label15.Name = "Label15";
			this.Label15.Size = new System.Drawing.Size(42, 13);
			this.Label15.TabIndex = 53;
			this.Label15.Text = "Vertical";
			this.ComboBox12.BackColor = Color.Indigo;
			this.ComboBox12.ForeColor = Color.White;
			this.ComboBox12.FormattingEnabled = true;
			this.ComboBox12.Items.AddRange(new object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
			this.ComboBox12.Location = new Point(335, 46);
			this.ComboBox12.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox12.Name = "ComboBox12";
			this.ComboBox12.Size = new System.Drawing.Size(36, 21);
			this.ComboBox12.TabIndex = 42;
			this.ComboBox12.Text = "1";
			this.Button7.Location = new Point(630, 53);
			this.Button7.Name = "Button7";
			this.Button7.Size = new System.Drawing.Size(39, 23);
			this.Button7.TabIndex = 38;
			this.Button7.Text = "...";
			this.Button7.UseVisualStyleBackColor = true;
			this.TrackBar2.Location = new Point(68, 6);
			this.TrackBar2.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.TrackBar2.Maximum = 20;
			this.TrackBar2.Minimum = 6;
			this.TrackBar2.Name = "TrackBar2";
			this.TrackBar2.Orientation = Orientation.Vertical;
			this.TrackBar2.Size = new System.Drawing.Size(45, 118);
			this.TrackBar2.TabIndex = 23;
			this.TrackBar2.Value = 13;
			this.Label6.AutoSize = true;
			this.Label6.Font = new System.Drawing.Font("Calibri Light", 12.75f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.Label6.Location = new Point(66, 149);
			this.Label6.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.Label6.Name = "Label6";
			this.Label6.Size = new System.Drawing.Size(39, 21);
			this.Label6.TabIndex = 30;
			this.Label6.Text = "Max";
			this.Label4.AutoSize = true;
			this.Label4.Font = new System.Drawing.Font("Calibri Light", 18f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.Label4.Location = new Point(65, 123);
			this.Label4.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.Label4.Name = "Label4";
			this.Label4.Size = new System.Drawing.Size(37, 29);
			this.Label4.TabIndex = 26;
			this.Label4.Text = "13";
			this.TrackBar1.Location = new Point(8, 6);
			this.TrackBar1.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.TrackBar1.Maximum = 20;
			this.TrackBar1.Minimum = 6;
			this.TrackBar1.Name = "TrackBar1";
			this.TrackBar1.Orientation = Orientation.Vertical;
			this.TrackBar1.Size = new System.Drawing.Size(45, 118);
			this.TrackBar1.TabIndex = 22;
			this.TrackBar1.Value = 10;
			this.ComboBox1.BackColor = Color.Indigo;
			this.ComboBox1.ForeColor = Color.White;
			this.ComboBox1.FormattingEnabled = true;
			this.ComboBox1.Items.AddRange(new object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
			this.ComboBox1.Location = new Point(631, 82);
			this.ComboBox1.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox1.Name = "ComboBox1";
			this.ComboBox1.Size = new System.Drawing.Size(36, 21);
			this.ComboBox1.TabIndex = 12;
			this.ComboBox1.Text = "1";
			this.Label2.AutoSize = true;
			this.Label2.Font = new System.Drawing.Font("Calibri Light", 12.75f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.Label2.Location = new Point(3, 149);
			this.Label2.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.Label2.Name = "Label2";
			this.Label2.Size = new System.Drawing.Size(37, 21);
			this.Label2.TabIndex = 29;
			this.Label2.Text = "Min";
			this.Label3.AutoSize = true;
			this.Label3.Font = new System.Drawing.Font("Calibri Light", 18f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.Label3.Location = new Point(2, 123);
			this.Label3.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.Label3.Name = "Label3";
			this.Label3.Size = new System.Drawing.Size(37, 29);
			this.Label3.TabIndex = 25;
			this.Label3.Text = "10";
			this.Label5.AutoSize = true;
			this.Label5.Font = new System.Drawing.Font("Calibri Light", 18f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.Label5.Location = new Point(43, 121);
			this.Label5.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.Label5.Name = "Label5";
			this.Label5.Size = new System.Drawing.Size(19, 29);
			this.Label5.TabIndex = 28;
			this.Label5.Text = ":";
			this.ComboBox3.BackColor = Color.Indigo;
			this.ComboBox3.ForeColor = SystemColors.Control;
			this.ComboBox3.FormattingEnabled = true;
			this.ComboBox3.Items.AddRange(new object[] { "Default Sound", "Razer Deathadder", "Logitech G502", "Logitech GPro", "Logitech G303", "Logitech G303 (Different)", "Microsoft Mouse", "HP Mouse", "Non-Brand Mouse", "Custom Sound" });
			this.ComboBox3.Location = new Point(488, 54);
			this.ComboBox3.Name = "ComboBox3";
			this.ComboBox3.Size = new System.Drawing.Size(140, 21);
			this.ComboBox3.TabIndex = 32;
			this.ComboBox2.BackColor = Color.Indigo;
			this.ComboBox2.ForeColor = Color.White;
			this.ComboBox2.FormattingEnabled = true;
			this.ComboBox2.Items.AddRange(new object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
			this.ComboBox2.Location = new Point(263, 46);
			this.ComboBox2.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox2.Name = "ComboBox2";
			this.ComboBox2.Size = new System.Drawing.Size(36, 21);
			this.ComboBox2.TabIndex = 13;
			this.ComboBox2.Text = "1";
			this.TabPage2.BackColor = Color.FromArgb(42, 42, 42);
			this.TabPage2.Controls.Add(this.NsLabel2);
			this.TabPage2.Controls.Add(this.NsLabel1);
			this.TabPage2.Controls.Add(this.ComboBox14);
			this.TabPage2.Controls.Add(this.ComboBox16);
			this.TabPage2.Controls.Add(this.FlatToggle22);
			this.TabPage2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.TabPage2.ImageIndex = 1;
			this.TabPage2.Location = new Point(4, 44);
			this.TabPage2.Name = "TabPage2";
			this.TabPage2.Padding = new System.Windows.Forms.Padding(3);
			this.TabPage2.Size = new System.Drawing.Size(673, 191);
			this.TabPage2.TabIndex = 1;
			this.TabPage2.Text = "Misc";
			this.NsLabel2.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel2.Location = new Point(256, 100);
			this.NsLabel2.Name = "NsLabel2";
			this.NsLabel2.Size = new System.Drawing.Size(148, 23);
			this.NsLabel2.TabIndex = 109;
			this.NsLabel2.Text = "NsLabel2";
			this.NsLabel2.Value1 = "HIDE FROM ";
			this.NsLabel2.Value2 = " TASKBAR";
			this.NsLabel1.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel1.Location = new Point(292, 36);
			this.NsLabel1.Name = "NsLabel1";
			this.NsLabel1.Size = new System.Drawing.Size(75, 23);
			this.NsLabel1.TabIndex = 108;
			this.NsLabel1.Text = "NsLabel1";
			this.NsLabel1.Value1 = "CHAT";
			this.NsLabel1.Value2 = " KEY";
			this.ComboBox14.BackColor = Color.Indigo;
			this.ComboBox14.ForeColor = Color.White;
			this.ComboBox14.FormattingEnabled = true;
			this.ComboBox14.Items.AddRange(new object[] { "None", "MB1", "MB2", "MB3", "MB4", "MB5", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12" });
			this.ComboBox14.Location = new Point(300, 59);
			this.ComboBox14.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox14.Name = "ComboBox14";
			this.ComboBox14.Size = new System.Drawing.Size(51, 21);
			this.ComboBox14.TabIndex = 92;
			this.ComboBox14.Text = "T";
			this.ComboBox16.BackColor = Color.Indigo;
			this.ComboBox16.ForeColor = Color.White;
			this.ComboBox16.FormattingEnabled = true;
			this.ComboBox16.Items.AddRange(new object[] { "None", "MB1", "MB2", "MB3", "MB4", "MB5", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12" });
			this.ComboBox16.Location = new Point(300, 124);
			this.ComboBox16.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox16.Name = "ComboBox16";
			this.ComboBox16.Size = new System.Drawing.Size(51, 21);
			this.ComboBox16.TabIndex = 81;
			this.ComboBox16.Text = "None";
			this.FlatToggle22.BackColor = Color.Transparent;
			this.FlatToggle22.Checked = false;
			this.FlatToggle22.Cursor = Cursors.Hand;
			this.FlatToggle22.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle22.Location = new Point(246, 12);
			this.FlatToggle22.Name = "FlatToggle22";
			this.FlatToggle22.Options = Xh0kO1ZCmA.FlatToggle2._Options.Style14;
			this.FlatToggle22.Size = new System.Drawing.Size(167, 33);
			this.FlatToggle22.TabIndex = 76;
			this.FlatToggle22.Text = "FlatToggle22";
			this.TabPage3.BackColor = Color.FromArgb(42, 42, 42);
			this.TabPage3.Controls.Add(this.NsLabel23);
			this.TabPage3.Controls.Add(this.NsLabel22);
			this.TabPage3.Controls.Add(this.NsLabel20);
			this.TabPage3.Controls.Add(this.FlatTrackBar2);
			this.TabPage3.Controls.Add(this.NsLabel8);
			this.TabPage3.Controls.Add(this.FlatTrackBar1);
			this.TabPage3.Controls.Add(this.FlatToggle10);
			this.TabPage3.ImageIndex = 4;
			this.TabPage3.Location = new Point(4, 44);
			this.TabPage3.Name = "TabPage3";
			this.TabPage3.Size = new System.Drawing.Size(673, 191);
			this.TabPage3.TabIndex = 2;
			this.TabPage3.Text = "W-Tap";
			this.NsLabel23.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel23.Location = new Point(290, 96);
			this.NsLabel23.Name = "NsLabel23";
			this.NsLabel23.Size = new System.Drawing.Size(54, 23);
			this.NsLabel23.TabIndex = 116;
			this.NsLabel23.Text = "NsLabel23";
			this.NsLabel23.Value1 = "Default";
			this.NsLabel23.Value2 = "";
			this.NsLabel22.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel22.Location = new Point(290, 164);
			this.NsLabel22.Name = "NsLabel22";
			this.NsLabel22.Size = new System.Drawing.Size(54, 23);
			this.NsLabel22.TabIndex = 115;
			this.NsLabel22.Text = "NsLabel22";
			this.NsLabel22.Value1 = "Default";
			this.NsLabel22.Value2 = "";
			this.NsLabel20.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel20.Location = new Point(178, 121);
			this.NsLabel20.Name = "NsLabel20";
			this.NsLabel20.Size = new System.Drawing.Size(296, 23);
			this.NsLabel20.TabIndex = 114;
			this.NsLabel20.Text = "NsLabel20";
			this.NsLabel20.Value1 = "DELAY BETWEEN";
			this.NsLabel20.Value2 = " PRESS W AND RELEASE W";
			this.FlatTrackBar2.BackColor = Color.FromArgb(42, 42, 42);
			this.FlatTrackBar2.HatchColor = Color.FromArgb(75, 0, 130);
			this.FlatTrackBar2.Location = new Point(201, 143);
			this.FlatTrackBar2.Maximum = 3000;
			this.FlatTrackBar2.Minimum = 0;
			this.FlatTrackBar2.Name = "FlatTrackBar2";
			this.FlatTrackBar2.ShowValue = true;
			this.FlatTrackBar2.Size = new System.Drawing.Size(228, 49);
			this.FlatTrackBar2.Style = FlatTrackBar._Style.Slider;
			this.FlatTrackBar2.TabIndex = 113;
			this.FlatTrackBar2.Text = "FlatTrackBar2";
			this.FlatTrackBar2.TrackColor = Color.FromArgb(75, 0, 130);
			this.FlatTrackBar2.Value = 0;
			this.NsLabel8.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel8.Location = new Point(229, 48);
			this.NsLabel8.Name = "NsLabel8";
			this.NsLabel8.Size = new System.Drawing.Size(176, 23);
			this.NsLabel8.TabIndex = 112;
			this.NsLabel8.Text = "NsLabel8";
			this.NsLabel8.Value1 = "DELAY BETWEEN";
			this.NsLabel8.Value2 = " W-TAPS";
			this.FlatTrackBar1.BackColor = Color.FromArgb(42, 42, 42);
			this.FlatTrackBar1.HatchColor = Color.FromArgb(75, 0, 130);
			this.FlatTrackBar1.Location = new Point(201, 74);
			this.FlatTrackBar1.Maximum = 3000;
			this.FlatTrackBar1.Minimum = 0;
			this.FlatTrackBar1.Name = "FlatTrackBar1";
			this.FlatTrackBar1.ShowValue = true;
			this.FlatTrackBar1.Size = new System.Drawing.Size(228, 49);
			this.FlatTrackBar1.Style = FlatTrackBar._Style.Slider;
			this.FlatTrackBar1.TabIndex = 64;
			this.FlatTrackBar1.Text = "FlatTrackBar1";
			this.FlatTrackBar1.TrackColor = Color.FromArgb(75, 0, 130);
			this.FlatTrackBar1.Value = 0;
			this.FlatToggle10.BackColor = Color.Transparent;
			this.FlatToggle10.Checked = false;
			this.FlatToggle10.Cursor = Cursors.Hand;
			this.FlatToggle10.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle10.Location = new Point(246, 12);
			this.FlatToggle10.Name = "FlatToggle10";
			this.FlatToggle10.Options = FlatToggle._Options.Style11;
			this.FlatToggle10.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle10.TabIndex = 62;
			this.FlatToggle10.Text = "FlatToggle10";
			this.TabPage4.BackColor = Color.FromArgb(42, 42, 42);
			this.TabPage4.Controls.Add(this.NsLabel26);
			this.TabPage4.Controls.Add(this.ComboBox17);
			this.TabPage4.Controls.Add(this.FlatToggle13);
			this.TabPage4.Controls.Add(this.NsLabel7);
			this.TabPage4.Controls.Add(this.ComboBox11);
			this.TabPage4.Controls.Add(this.FlatToggle11);
			this.TabPage4.Controls.Add(this.NsLabel5);
			this.TabPage4.Controls.Add(this.NsLabel4);
			this.TabPage4.Controls.Add(this.NsLabel6);
			this.TabPage4.Controls.Add(this.ComboBox7);
			this.TabPage4.Controls.Add(this.NsLabel3);
			this.TabPage4.Controls.Add(this.ComboBox4);
			this.TabPage4.Controls.Add(this.ComboBox5);
			this.TabPage4.Controls.Add(this.ComboBox6);
			this.TabPage4.ImageIndex = 2;
			this.TabPage4.Location = new Point(4, 44);
			this.TabPage4.Name = "TabPage4";
			this.TabPage4.Size = new System.Drawing.Size(673, 191);
			this.TabPage4.TabIndex = 3;
			this.TabPage4.Text = "Throw Potion";
			this.NsLabel26.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel26.Location = new Point(41, 37);
			this.NsLabel26.Name = "NsLabel26";
			this.NsLabel26.Size = new System.Drawing.Size(86, 23);
			this.NsLabel26.TabIndex = 120;
			this.NsLabel26.Text = "NsLabel26";
			this.NsLabel26.Value1 = "DROP";
			this.NsLabel26.Value2 = " KEY";
			this.ComboBox17.BackColor = Color.Indigo;
			this.ComboBox17.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.ComboBox17.ForeColor = Color.White;
			this.ComboBox17.FormattingEnabled = true;
			this.ComboBox17.Items.AddRange(new object[] { "None", "MB1", "MB2", "MB3", "MB4", "MB5", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12" });
			this.ComboBox17.Location = new Point(51, 61);
			this.ComboBox17.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox17.Name = "ComboBox17";
			this.ComboBox17.Size = new System.Drawing.Size(51, 21);
			this.ComboBox17.TabIndex = 119;
			this.ComboBox17.Text = "Q";
			this.FlatToggle13.BackColor = Color.Transparent;
			this.FlatToggle13.Checked = false;
			this.FlatToggle13.Cursor = Cursors.Hand;
			this.FlatToggle13.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle13.Location = new Point(12, 12);
			this.FlatToggle13.Name = "FlatToggle13";
			this.FlatToggle13.Options = FlatToggle._Options.Style16;
			this.FlatToggle13.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle13.TabIndex = 118;
			this.FlatToggle13.Text = "FlatToggle13";
			this.NsLabel7.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel7.Location = new Point(277, 137);
			this.NsLabel7.Name = "NsLabel7";
			this.NsLabel7.Size = new System.Drawing.Size(93, 23);
			this.NsLabel7.TabIndex = 115;
			this.NsLabel7.Text = "NsLabel7";
			this.NsLabel7.Value1 = "SWORD";
			this.NsLabel7.Value2 = " SLOT";
			this.ComboBox11.BackColor = Color.Indigo;
			this.ComboBox11.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.ComboBox11.ForeColor = Color.White;
			this.ComboBox11.FormattingEnabled = true;
			this.ComboBox11.Items.AddRange(new object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" });
			this.ComboBox11.Location = new Point(301, 162);
			this.ComboBox11.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox11.Name = "ComboBox11";
			this.ComboBox11.Size = new System.Drawing.Size(36, 21);
			this.ComboBox11.TabIndex = 106;
			this.ComboBox11.Text = "1";
			this.FlatToggle11.BackColor = Color.Transparent;
			this.FlatToggle11.Checked = false;
			this.FlatToggle11.Cursor = Cursors.Hand;
			this.FlatToggle11.Font = new System.Drawing.Font("Segoe UI", 9.75f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.FlatToggle11.Location = new Point(246, 12);
			this.FlatToggle11.Name = "FlatToggle11";
			this.FlatToggle11.Options = FlatToggle._Options.Style12;
			this.FlatToggle11.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle11.TabIndex = 63;
			this.FlatToggle11.Text = "FlatToggle11";
			this.NsLabel5.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel5.Location = new Point(326, 41);
			this.NsLabel5.Name = "NsLabel5";
			this.NsLabel5.Size = new System.Drawing.Size(109, 23);
			this.NsLabel5.TabIndex = 112;
			this.NsLabel5.Text = "NsLabel5";
			this.NsLabel5.Value1 = "FIRST";
			this.NsLabel5.Value2 = " POTION";
			this.NsLabel4.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel4.Location = new Point(224, 41);
			this.NsLabel4.Name = "NsLabel4";
			this.NsLabel4.Size = new System.Drawing.Size(86, 23);
			this.NsLabel4.TabIndex = 111;
			this.NsLabel4.Text = "NsLabel4";
			this.NsLabel4.Value1 = "TOGGLE";
			this.NsLabel4.Value2 = " KEY";
			this.NsLabel6.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel6.Location = new Point(198, 87);
			this.NsLabel6.Name = "NsLabel6";
			this.NsLabel6.Size = new System.Drawing.Size(115, 23);
			this.NsLabel6.TabIndex = 114;
			this.NsLabel6.Text = "NsLabel6";
			this.NsLabel6.Value1 = "INVENTORY";
			this.NsLabel6.Value2 = " KEY";
			this.ComboBox7.BackColor = Color.Indigo;
			this.ComboBox7.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.ComboBox7.ForeColor = Color.White;
			this.ComboBox7.FormattingEnabled = true;
			this.ComboBox7.Items.AddRange(new object[] { "None", "MB1", "MB2", "MB3", "MB4", "MB5", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12" });
			this.ComboBox7.Location = new Point(259, 112);
			this.ComboBox7.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox7.Name = "ComboBox7";
			this.ComboBox7.Size = new System.Drawing.Size(51, 21);
			this.ComboBox7.TabIndex = 96;
			this.ComboBox7.Text = "E";
			this.NsLabel3.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel3.Location = new Point(326, 87);
			this.NsLabel3.Name = "NsLabel3";
			this.NsLabel3.Size = new System.Drawing.Size(98, 23);
			this.NsLabel3.TabIndex = 113;
			this.NsLabel3.Text = "NsLabel3";
			this.NsLabel3.Value1 = "LAST";
			this.NsLabel3.Value2 = " POTION";
			this.ComboBox4.BackColor = Color.Indigo;
			this.ComboBox4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.ComboBox4.ForeColor = Color.White;
			this.ComboBox4.FormattingEnabled = true;
			this.ComboBox4.Items.AddRange(new object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" });
			this.ComboBox4.Location = new Point(328, 64);
			this.ComboBox4.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox4.Name = "ComboBox4";
			this.ComboBox4.Size = new System.Drawing.Size(36, 21);
			this.ComboBox4.TabIndex = 101;
			this.ComboBox4.Text = "3";
			this.ComboBox5.BackColor = Color.Indigo;
			this.ComboBox5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.ComboBox5.ForeColor = Color.White;
			this.ComboBox5.FormattingEnabled = true;
			this.ComboBox5.Items.AddRange(new object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" });
			this.ComboBox5.Location = new Point(328, 112);
			this.ComboBox5.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox5.Name = "ComboBox5";
			this.ComboBox5.Size = new System.Drawing.Size(36, 21);
			this.ComboBox5.TabIndex = 102;
			this.ComboBox5.Text = "8";
			this.ComboBox6.BackColor = Color.Indigo;
			this.ComboBox6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.ComboBox6.ForeColor = Color.White;
			this.ComboBox6.FormattingEnabled = true;
			this.ComboBox6.Items.AddRange(new object[] { "None", "MB1", "MB2", "MB3", "MB4", "MB5", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12" });
			this.ComboBox6.Location = new Point(259, 64);
			this.ComboBox6.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox6.Name = "ComboBox6";
			this.ComboBox6.Size = new System.Drawing.Size(51, 21);
			this.ComboBox6.TabIndex = 95;
			this.ComboBox6.Text = "None";
			this.TabPage5.BackColor = Color.FromArgb(42, 42, 42);
			this.TabPage5.Controls.Add(this.NsLabel9);
			this.TabPage5.Controls.Add(this.NsLabel25);
			this.TabPage5.Controls.Add(this.ComboBox10);
			this.TabPage5.Controls.Add(this.NsLabel11);
			this.TabPage5.Controls.Add(this.NsLabel24);
			this.TabPage5.Controls.Add(this.ComboBox15);
			this.TabPage5.Controls.Add(this.FlatToggle12);
			this.TabPage5.Controls.Add(this.NsLabel10);
			this.TabPage5.Controls.Add(this.ComboBox9);
			this.TabPage5.Controls.Add(this.FlatTrackBar3);
			this.TabPage5.ImageIndex = 5;
			this.TabPage5.Location = new Point(4, 44);
			this.TabPage5.Name = "TabPage5";
			this.TabPage5.Size = new System.Drawing.Size(673, 191);
			this.TabPage5.TabIndex = 4;
			this.TabPage5.Text = "Rod Trick";
			this.NsLabel9.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel9.Location = new Point(371, 122);
			this.NsLabel9.Name = "NsLabel9";
			this.NsLabel9.Size = new System.Drawing.Size(93, 23);
			this.NsLabel9.TabIndex = 116;
			this.NsLabel9.Text = "NsLabel9";
			this.NsLabel9.Value1 = "SWORD";
			this.NsLabel9.Value2 = " SLOT";
			this.NsLabel25.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel25.Location = new Point(184, 41);
			this.NsLabel25.Name = "NsLabel25";
			this.NsLabel25.Size = new System.Drawing.Size(267, 23);
			this.NsLabel25.TabIndex = 117;
			this.NsLabel25.Text = "NsLabel25";
			this.NsLabel25.Value1 = "DELAY BEFORE SWITCHING TO";
			this.NsLabel25.Value2 = " SWORD";
			this.ComboBox10.BackColor = Color.Indigo;
			this.ComboBox10.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.ComboBox10.ForeColor = Color.White;
			this.ComboBox10.FormattingEnabled = true;
			this.ComboBox10.Items.AddRange(new object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" });
			this.ComboBox10.Location = new Point(399, 146);
			this.ComboBox10.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox10.Name = "ComboBox10";
			this.ComboBox10.Size = new System.Drawing.Size(36, 21);
			this.ComboBox10.TabIndex = 103;
			this.ComboBox10.Text = "1";
			this.NsLabel11.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel11.Location = new Point(283, 122);
			this.NsLabel11.Name = "NsLabel11";
			this.NsLabel11.Size = new System.Drawing.Size(93, 23);
			this.NsLabel11.TabIndex = 118;
			this.NsLabel11.Text = "NsLabel11";
			this.NsLabel11.Value1 = "ROD";
			this.NsLabel11.Value2 = " SLOT";
			this.NsLabel24.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel24.Location = new Point(287, 89);
			this.NsLabel24.Name = "NsLabel24";
			this.NsLabel24.Size = new System.Drawing.Size(54, 23);
			this.NsLabel24.TabIndex = 116;
			this.NsLabel24.Text = "NsLabel24";
			this.NsLabel24.Value1 = "Default";
			this.NsLabel24.Value2 = "";
			this.ComboBox15.BackColor = Color.Indigo;
			this.ComboBox15.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.ComboBox15.ForeColor = Color.White;
			this.ComboBox15.FormattingEnabled = true;
			this.ComboBox15.Items.AddRange(new object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" });
			this.ComboBox15.Location = new Point(298, 146);
			this.ComboBox15.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox15.Name = "ComboBox15";
			this.ComboBox15.Size = new System.Drawing.Size(36, 21);
			this.ComboBox15.TabIndex = 105;
			this.ComboBox15.Text = "2";
			this.FlatToggle12.BackColor = Color.Transparent;
			this.FlatToggle12.Checked = false;
			this.FlatToggle12.Cursor = Cursors.Hand;
			this.FlatToggle12.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle12.Location = new Point(246, 12);
			this.FlatToggle12.Name = "FlatToggle12";
			this.FlatToggle12.Options = FlatToggle._Options.Style13;
			this.FlatToggle12.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle12.TabIndex = 64;
			this.FlatToggle12.Text = "FlatToggle12";
			this.NsLabel10.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel10.Location = new Point(175, 122);
			this.NsLabel10.Name = "NsLabel10";
			this.NsLabel10.Size = new System.Drawing.Size(86, 23);
			this.NsLabel10.TabIndex = 117;
			this.NsLabel10.Text = "NsLabel10";
			this.NsLabel10.Value1 = "TOGGLE";
			this.NsLabel10.Value2 = " KEY";
			this.ComboBox9.BackColor = Color.Indigo;
			this.ComboBox9.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.ComboBox9.ForeColor = Color.White;
			this.ComboBox9.FormattingEnabled = true;
			this.ComboBox9.Items.AddRange(new object[] { "None", "MB1", "MB2", "MB3", "MB4", "MB5", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12" });
			this.ComboBox9.Location = new Point(191, 146);
			this.ComboBox9.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.ComboBox9.Name = "ComboBox9";
			this.ComboBox9.Size = new System.Drawing.Size(51, 21);
			this.ComboBox9.TabIndex = 99;
			this.ComboBox9.Text = "None";
			this.FlatTrackBar3.BackColor = Color.FromArgb(42, 42, 42);
			this.FlatTrackBar3.HatchColor = Color.FromArgb(75, 0, 130);
			this.FlatTrackBar3.Location = new Point(201, 67);
			this.FlatTrackBar3.Maximum = 2000;
			this.FlatTrackBar3.Minimum = 0;
			this.FlatTrackBar3.Name = "FlatTrackBar3";
			this.FlatTrackBar3.ShowValue = true;
			this.FlatTrackBar3.Size = new System.Drawing.Size(228, 49);
			this.FlatTrackBar3.Style = FlatTrackBar._Style.Slider;
			this.FlatTrackBar3.TabIndex = 114;
			this.FlatTrackBar3.Text = "FlatTrackBar3";
			this.FlatTrackBar3.TrackColor = Color.FromArgb(75, 0, 130);
			this.FlatTrackBar3.Value = 0;
			this.TabPage6.BackColor = Color.FromArgb(42, 42, 42);
			this.TabPage6.Controls.Add(this.NsLabel33);
			this.TabPage6.Controls.Add(this.NsLabel32);
			this.TabPage6.Controls.Add(this.NsLabel30);
			this.TabPage6.Controls.Add(this.FlatButton5);
			this.TabPage6.Controls.Add(this.NsLabel34);
			this.TabPage6.Controls.Add(this.FlatTrackBar4);
			this.TabPage6.Controls.Add(this.NsLabel31);
			this.TabPage6.Controls.Add(this.NsLabel29);
			this.TabPage6.Controls.Add(this.FlatToggle31);
			this.TabPage6.Controls.Add(this.FlatButton4);
			this.TabPage6.Controls.Add(this.FlatButton3);
			this.TabPage6.Controls.Add(this.PictureBox2);
			this.TabPage6.Controls.Add(this.NsLabel28);
			this.TabPage6.Controls.Add(this.ListBox1);
			this.TabPage6.Controls.Add(this.FlatButton2);
			this.TabPage6.Controls.Add(this.FlatButton1);
			this.TabPage6.Controls.Add(this.NsLabel27);
			this.TabPage6.ImageIndex = 6;
			this.TabPage6.Location = new Point(4, 44);
			this.TabPage6.Name = "TabPage6";
			this.TabPage6.Size = new System.Drawing.Size(673, 191);
			this.TabPage6.TabIndex = 5;
			this.TabPage6.Text = "Settings";
			this.NsLabel33.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel33.Location = new Point(492, 36);
			this.NsLabel33.Name = "NsLabel33";
			this.NsLabel33.Size = new System.Drawing.Size(150, 23);
			this.NsLabel33.TabIndex = 132;
			this.NsLabel33.Text = "NsLabel33";
			this.NsLabel33.Value1 = "MINIMUM CPS:";
			this.NsLabel33.Value2 = " N/A";
			this.NsLabel32.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel32.Location = new Point(492, 59);
			this.NsLabel32.Name = "NsLabel32";
			this.NsLabel32.Size = new System.Drawing.Size(150, 23);
			this.NsLabel32.TabIndex = 131;
			this.NsLabel32.Text = "NsLabel32";
			this.NsLabel32.Value1 = "MAXIMUM CPS:";
			this.NsLabel32.Value2 = " N/A";
			this.NsLabel30.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel30.Location = new Point(492, 14);
			this.NsLabel30.Name = "NsLabel30";
			this.NsLabel30.Size = new System.Drawing.Size(150, 23);
			this.NsLabel30.TabIndex = 130;
			this.NsLabel30.Text = "NsLabel30";
			this.NsLabel30.Value1 = "AVERAGE CPS:";
			this.NsLabel30.Value2 = " N/A";
			this.FlatButton5.BackColor = Color.Transparent;
			this.FlatButton5.BaseColor = Color.FromArgb(53, 53, 53);
			this.FlatButton5.Cursor = Cursors.Hand;
			this.FlatButton5.Font = new System.Drawing.Font("Tahoma", 11.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.FlatButton5.Location = new Point(261, 127);
			this.FlatButton5.Name = "FlatButton5";
			this.FlatButton5.Rounded = false;
			this.FlatButton5.Size = new System.Drawing.Size(126, 32);
			this.FlatButton5.TabIndex = 129;
			this.FlatButton5.Text = "Reset";
			this.FlatButton5.TextColor = Color.FromArgb(255, 255, 255);
			this.NsLabel34.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel34.Location = new Point(526, 123);
			this.NsLabel34.Name = "NsLabel34";
			this.NsLabel34.Size = new System.Drawing.Size(85, 23);
			this.NsLabel34.TabIndex = 128;
			this.NsLabel34.Text = "NsLabel34";
			this.NsLabel34.Value1 = "MAX";
			this.NsLabel34.Value2 = " DELAY";
			this.FlatTrackBar4.BackColor = Color.FromArgb(42, 42, 42);
			this.FlatTrackBar4.HatchColor = Color.FromArgb(75, 0, 130);
			this.FlatTrackBar4.Location = new Point(459, 140);
			this.FlatTrackBar4.Maximum = 1000;
			this.FlatTrackBar4.Minimum = 0;
			this.FlatTrackBar4.Name = "FlatTrackBar4";
			this.FlatTrackBar4.ShowValue = true;
			this.FlatTrackBar4.Size = new System.Drawing.Size(216, 34);
			this.FlatTrackBar4.Style = FlatTrackBar._Style.Slider;
			this.FlatTrackBar4.TabIndex = 127;
			this.FlatTrackBar4.Text = "FlatTrackBar4";
			this.FlatTrackBar4.TrackColor = Color.FromArgb(75, 0, 130);
			this.FlatTrackBar4.Value = 100;
			this.NsLabel31.Font = new System.Drawing.Font("Segoe UI", 11.25f, FontStyle.Bold);
			this.NsLabel31.Location = new Point(237, 1);
			this.NsLabel31.Name = "NsLabel31";
			this.NsLabel31.Size = new System.Drawing.Size(186, 36);
			this.NsLabel31.TabIndex = 123;
			this.NsLabel31.Text = "NsLabel31";
			this.NsLabel31.Value1 = "";
			this.NsLabel31.Value2 = "NOT RECORDING CLICKS";
			this.NsLabel29.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel29.Location = new Point(254, 65);
			this.NsLabel29.Name = "NsLabel29";
			this.NsLabel29.Size = new System.Drawing.Size(133, 23);
			this.NsLabel29.TabIndex = 122;
			this.NsLabel29.Text = "NsLabel29";
			this.NsLabel29.Value1 = "CLICK";
			this.NsLabel29.Value2 = " RECORDING";
			this.FlatToggle31.BackColor = Color.Transparent;
			this.FlatToggle31.Checked = false;
			this.FlatToggle31.Cursor = Cursors.Hand;
			this.FlatToggle31.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle31.Location = new Point(458, 94);
			this.FlatToggle31.Name = "FlatToggle31";
			this.FlatToggle31.Options = Xh0kO1ZCmA.FlatToggle3._Options.Style15;
			this.FlatToggle31.Size = new System.Drawing.Size(208, 33);
			this.FlatToggle31.TabIndex = 121;
			this.FlatToggle31.Text = "FlatToggle31";
			this.FlatButton4.BackColor = Color.Transparent;
			this.FlatButton4.BaseColor = Color.FromArgb(53, 53, 53);
			this.FlatButton4.Cursor = Cursors.Hand;
			this.FlatButton4.Font = new System.Drawing.Font("Tahoma", 11.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.FlatButton4.Location = new Point(327, 90);
			this.FlatButton4.Name = "FlatButton4";
			this.FlatButton4.Rounded = false;
			this.FlatButton4.Size = new System.Drawing.Size(126, 32);
			this.FlatButton4.TabIndex = 120;
			this.FlatButton4.Text = "Stop";
			this.FlatButton4.TextColor = Color.FromArgb(255, 255, 255);
			this.FlatButton3.BackColor = Color.Transparent;
			this.FlatButton3.BaseColor = Color.FromArgb(53, 53, 53);
			this.FlatButton3.Cursor = Cursors.Hand;
			this.FlatButton3.Font = new System.Drawing.Font("Tahoma", 11.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.FlatButton3.Location = new Point(197, 90);
			this.FlatButton3.Name = "FlatButton3";
			this.FlatButton3.Rounded = false;
			this.FlatButton3.Size = new System.Drawing.Size(126, 32);
			this.FlatButton3.TabIndex = 119;
			this.FlatButton3.Text = "Start";
			this.FlatButton3.TextColor = Color.FromArgb(255, 255, 255);
			this.PictureBox2.Image = (Image)componentResourceManager.GetObject("PictureBox2.Image");
			this.PictureBox2.Location = new Point(197, -4);
			this.PictureBox2.Name = "PictureBox2";
			this.PictureBox2.Size = new System.Drawing.Size(65, 64);
			this.PictureBox2.TabIndex = 118;
			this.PictureBox2.TabStop = false;
			this.PictureBox2.Visible = false;
			this.NsLabel28.Font = new System.Drawing.Font("Segoe UI", 11.25f, FontStyle.Bold);
			this.NsLabel28.Location = new Point(273, -1);
			this.NsLabel28.Name = "NsLabel28";
			this.NsLabel28.Size = new System.Drawing.Size(150, 40);
			this.NsLabel28.TabIndex = 117;
			this.NsLabel28.Text = "NsLabel28";
			this.NsLabel28.Value1 = "";
			this.NsLabel28.Value2 = "RECORDING CLICKS";
			this.NsLabel28.Visible = false;
			this.ListBox1.FormattingEnabled = true;
			this.ListBox1.ItemHeight = 14;
			this.ListBox1.Location = new Point(443, 20);
			this.ListBox1.Name = "ListBox1";
			this.ListBox1.Size = new System.Drawing.Size(210, 74);
			this.ListBox1.TabIndex = 116;
			this.ListBox1.Visible = false;
			this.FlatButton2.BackColor = Color.Transparent;
			this.FlatButton2.BaseColor = Color.FromArgb(53, 53, 53);
			this.FlatButton2.Cursor = Cursors.Hand;
			this.FlatButton2.Font = new System.Drawing.Font("Tahoma", 11.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.FlatButton2.Location = new Point(30, 29);
			this.FlatButton2.Name = "FlatButton2";
			this.FlatButton2.Rounded = false;
			this.FlatButton2.Size = new System.Drawing.Size(126, 32);
			this.FlatButton2.TabIndex = 115;
			this.FlatButton2.Text = "Save Settings";
			this.FlatButton2.TextColor = Color.FromArgb(255, 255, 255);
			this.FlatButton1.BackColor = Color.Transparent;
			this.FlatButton1.BaseColor = Color.FromArgb(53, 53, 53);
			this.FlatButton1.Cursor = Cursors.Hand;
			this.FlatButton1.Font = new System.Drawing.Font("Tahoma", 11.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.FlatButton1.Location = new Point(30, 67);
			this.FlatButton1.Name = "FlatButton1";
			this.FlatButton1.Rounded = false;
			this.FlatButton1.Size = new System.Drawing.Size(126, 32);
			this.FlatButton1.TabIndex = 114;
			this.FlatButton1.Text = "Load Settings";
			this.FlatButton1.TextColor = Color.FromArgb(255, 255, 255);
			this.NsLabel27.Font = new System.Drawing.Font("Tahoma", 9.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.NsLabel27.Location = new Point(153, 169);
			this.NsLabel27.Name = "NsLabel27";
			this.NsLabel27.Size = new System.Drawing.Size(384, 23);
			this.NsLabel27.TabIndex = 113;
			this.NsLabel27.Text = "NsLabel27";
			this.NsLabel27.Value1 = "THE SETTINGS GET DELETED IF YOU USE";
			this.NsLabel27.Value2 = " SELF DESTRUCT";
			this.ImageList1.ImageStream = (ImageListStreamer)componentResourceManager.GetObject("ImageList1.ImageStream");
			this.ImageList1.TransparentColor = Color.Transparent;
			this.ImageList1.Images.SetKeyName(0, "Cursor-Arrow-PNG-Picture.png");
			this.ImageList1.Images.SetKeyName(1, "Misc2.png");
			this.ImageList1.Images.SetKeyName(2, "red_potion_vector__1_by_greenmachine987-d9opoum.png");
			this.ImageList1.Images.SetKeyName(3, "clicker.png");
			this.ImageList1.Images.SetKeyName(4, "WKey2.png");
			this.ImageList1.Images.SetKeyName(5, "Rod Trick.png");
			this.ImageList1.Images.SetKeyName(6, "Settings2.png");
			this.Timer19.Interval = 1;
			this.Timer20.Interval = 1;
			this.Timer21.Interval = 1;
			this.ToolTip1.Tag = "Clicks with higher delay than the max delay set will be ignored.";
			this.FlatToggle41.BackColor = Color.Transparent;
			this.FlatToggle41.Checked = false;
			this.FlatToggle41.Cursor = Cursors.Hand;
			this.FlatToggle41.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.FlatToggle41.Location = new Point(536, 7);
			this.FlatToggle41.Name = "FlatToggle41";
			this.FlatToggle41.Options = Xh0kO1ZCmA.FlatToggle4._Options.Style17;
			this.FlatToggle41.Size = new System.Drawing.Size(135, 33);
			this.FlatToggle41.TabIndex = 132;
			this.FlatToggle41.Text = "FlatToggle41";
			this.Button1.BackColor = SystemColors.Window;
			this.Button1.Font = new System.Drawing.Font("Tahoma", 8.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.Button1.ForeColor = Color.Indigo;
			this.Button1.Location = new Point(559, 32);
			this.Button1.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			this.Button1.Name = "Button1";
			this.Button1.Size = new System.Drawing.Size(90, 23);
			this.Button1.TabIndex = 134;
			this.Button1.Text = "Self Destruct";
			this.Button1.UseVisualStyleBackColor = false;
			base.AutoScaleDimensions = new SizeF(6f, 13f);
			base.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.BackColor = SystemColors.Window;
			base.ClientSize = new System.Drawing.Size(679, 344);
			base.Controls.Add(this.TabControlClass1);
			base.Controls.Add(this.Panel2);
			base.Controls.Add(this.Panel1);
			this.ForeColor = SystemColors.ControlText;
			base.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
			base.Icon = (System.Drawing.Icon)componentResourceManager.GetObject("$this.Icon");
			base.Margin = new System.Windows.Forms.Padding(2, 3, 2, 3);
			base.Name = "Form1";
			base.StartPosition = FormStartPosition.CenterScreen;
			base.TransparencyKey = Color.Fuchsia;
			this.Panel1.ResumeLayout(false);
			this.Panel1.PerformLayout();
			((ISupportInitialize)this.PictureBox1).EndInit();
			this.Panel2.ResumeLayout(false);
			this.TabControlClass1.ResumeLayout(false);
			this.TabPage1.ResumeLayout(false);
			this.TabPage1.PerformLayout();
			((ISupportInitialize)this.TrackBar2).EndInit();
			((ISupportInitialize)this.TrackBar1).EndInit();
			this.TabPage2.ResumeLayout(false);
			this.TabPage3.ResumeLayout(false);
			this.TabPage4.ResumeLayout(false);
			this.TabPage5.ResumeLayout(false);
			this.TabPage6.ResumeLayout(false);
			((ISupportInitialize)this.PictureBox2).EndInit();
			base.ResumeLayout(false);
		}

		[DllImport("winmm", CharSet=CharSet.Auto, ExactSpelling=false, SetLastError=true)]
		private static extern int mciSendString(string lpstrCommand, string lpstrReturnString, ref int uReturnLength, IntPtr hwndCallBack);

		private void Melt(int Timeout)
		{
			ProcessStartInfo processStartInfo = new ProcessStartInfo("cmd.exe")
			{
				Arguments = string.Concat(new string[] { "/C ping 1.1.1.1 -n 1 -w ", Timeout.ToString(), " > Nul & Del \"C:\\Users\\", Environment.UserName, "\\AppData\\Local\\Temp\\", this.rndname.Substring(0, 5), ".exe\"" }),
				CreateNoWindow = true,
				ErrorDialog = false,
				WindowStyle = ProcessWindowStyle.Hidden
			};
			Process.Start(processStartInfo);
			Application.ExitThread();
		}

		private void Melt2(int Timeout)
		{
			ProcessStartInfo processStartInfo = new ProcessStartInfo("cmd.exe")
			{
				Arguments = string.Concat(new string[] { "/C ping 1.1.1.1 -n 1 -w ", Timeout.ToString(), " > Nul & Del \"C:\\Users\\", Environment.UserName, "\\AppData\\Local\\Temp\\EYrY9.ini\"" }),
				CreateNoWindow = true,
				ErrorDialog = false,
				WindowStyle = ProcessWindowStyle.Hidden
			};
			Process.Start(processStartInfo);
		}

		private void Melt3(int Timeout)
		{
			ProcessStartInfo processStartInfo = new ProcessStartInfo("cmd.exe")
			{
				Arguments = string.Concat(new string[] { "/C ping 1.1.1.1 -n 1 -w ", Timeout.ToString(), " > Nul & Del \"", this.rndname.Substring(6), "\"" }),
				CreateNoWindow = true,
				ErrorDialog = false,
				WindowStyle = ProcessWindowStyle.Hidden
			};
			Process.Start(processStartInfo);
		}

		private void memes()
		{
			Point position = System.Windows.Forms.Cursor.Position;
			position.X = checked(position.X + 120);
			System.Windows.Forms.Cursor.Position = position;
		}

		[DllImport("user32", CharSet=CharSet.Ansi, ExactSpelling=true, SetLastError=true)]
		public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int swextrainfo);

		public void MyDelay()
		{
			int i = 1;
			int num = (new Random()).Next(34400000, 51200000);
			for (i = 1; i <= num; i = checked(i + 1))
			{
				i = checked(i + 1);
			}
		}

		public void MyDelay2()
		{
			int i = 1;
			int num = (new Random()).Next(44300000, 55700000);
			for (i = 1; i <= num; i = checked(i + 1))
			{
				i = checked(i + 1);
			}
		}

		public void MyDelay3()
		{
			int i = 1;
			int num = (new Random()).Next(154300000, 195700000);
			for (i = 1; i <= num; i = checked(i + 1))
			{
				i = checked(i + 1);
			}
		}

		public void MyDelay4()
		{
			int i = 1;
			int num = (new Random()).Next(254300000, 285700000);
			for (i = 1; i <= num; i = checked(i + 1))
			{
				i = checked(i + 1);
			}
		}

		public void MyDelay5()
		{
			int i = 1;
			int num = (new Random()).Next(50000000, 65700000);
			for (i = 1; i <= num; i = checked(i + 1))
			{
				i = checked(i + 1);
			}
		}

		public void MyDelay6()
		{
			int i = 1;
			int num = (new Random()).Next(checked(this.RodMin * 1000000), checked(checked(this.RodMin * 1000000) + 31000000));
			for (i = 1; i <= num; i = checked(i + 1))
			{
				i = checked(i + 1);
			}
		}

		private void NewEvent(ref OneAction _action)
		{
			int integer = 0;
			IEnumerator enumerator = null;
			if (_action.Delay <= (long)this.FlatTrackBar4.Value)
			{
				this.ListBox1.Items.Add(_action.Delay);
				int num = 0;
				int count = this.ListBox1.Items.Count;
				int integer1 = Conversions.ToInteger(this.ListBox1.Items[0]);
				int num1 = Conversions.ToInteger(this.ListBox1.Items[0]);
				while (num < count)
				{
					if (Conversions.ToInteger(this.ListBox1.Items[num]) > integer1)
					{
						integer1 = Conversions.ToInteger(this.ListBox1.Items[num]);
					}
					if (Conversions.ToInteger(this.ListBox1.Items[num]) < num1)
					{
						num1 = Conversions.ToInteger(this.ListBox1.Items[num]);
					}
					num = checked(num + 1);
				}
				this.ClicksMax = integer1;
				this.ClicksMin = num1;
				try
				{
					enumerator = this.ListBox1.Items.GetEnumerator();
					while (enumerator.MoveNext())
					{
						string str = Conversions.ToString(enumerator.Current);
						integer = checked(integer + Conversions.ToInteger(str));
					}
				}
				finally
				{
					if (enumerator is IDisposable)
					{
						(enumerator as IDisposable).Dispose();
					}
				}
				integer = checked((int)Math.Round((double)integer / (double)this.ListBox1.Items.Count));
				NSLabel nsLabel30 = this.NsLabel30;
				double num2 = Math.Round(1000 / (double)integer, 2);
				nsLabel30.Value2 = string.Concat(" ", num2.ToString());
				NSLabel nsLabel33 = this.NsLabel33;
				num2 = Math.Round(1000 / (double)integer1, 2);
				nsLabel33.Value2 = string.Concat(" ", num2.ToString());
				NSLabel nsLabel32 = this.NsLabel32;
				num2 = Math.Round(800 / (double)num1, 2);
				nsLabel32.Value2 = string.Concat(" ", num2.ToString());
			}
			this.ListBox1.TopIndex = checked(this.ListBox1.Items.Count - 1);
		}

		private void NotifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
		{
			base.ShowInTaskbar = true;
			this.NotifyIcon1.Visible = false;
		}

		public object rnd2(int len)
		{
			string str = null;
			string str1 = null;
			str = string.Concat(str, "abcdefghijklmnopqrstuvwxyz");
			str = string.Concat(str, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			str = string.Concat(str, "0123456789");
			Random random = new Random();
			for (int i = 0; i < len; i = checked(i + 1))
			{
				str1 = string.Concat(str1, Conversions.ToString(str[random.Next(0, str.Length)]));
			}
			return str1;
		}

		[DllImport("kernel32", CharSet=CharSet.Ansi, ExactSpelling=true, SetLastError=true)]
		private static extern void Sleep(long dwMilliseconds);

		private string StringtoMD5(ref string Content)
		{
			MD5CryptoServiceProvider mD5CryptoServiceProvider = new MD5CryptoServiceProvider();
			byte[] bytes = Encoding.ASCII.GetBytes(Content);
			bytes = mD5CryptoServiceProvider.ComputeHash(bytes);
			string str = null;
			byte[] numArray = bytes;
			for (int i = 0; i < (int)numArray.Length; i = checked(i + 1))
			{
				byte num = numArray[i];
				str = string.Concat(str, num.ToString("x2"));
			}
			return str.ToUpper();
		}

		private async Task Timer1_Tick(object sender, EventArgs e)
		{
			this.rndmin = 14;
			this.rndmax = 26;
			this.rndint = this.r.Next(this.rndmin, this.rndmax);
			this.rndint2 = this.r.Next(this.rndmin, this.rndmax);
			if (this.rndint == this.Previous1)
			{
				int num = this.r.Next(-2, 2);
				ref int numPointer = ref this.rndint2;
				numPointer = checked(numPointer + num);
				if (this.rndint2 > this.Max)
				{
					num = this.r.Next(-1, -3);
				}
				if (this.rndint2 < this.Min)
				{
					num = this.r.Next(1, 3);
				}
				ref int numPointer1 = ref this.rndint;
				numPointer1 = checked(numPointer1 + num);
			}
			if (this.rndint == this.Previous2)
			{
				int num1 = this.r.Next(-2, 2);
				ref int numPointer2 = ref this.rndint2;
				numPointer2 = checked(numPointer2 + num1);
				if (this.rndint2 > this.Max)
				{
					num1 = this.r.Next(-1, -3);
				}
				if (this.rndint2 < this.Min)
				{
					num1 = this.r.Next(1, 3);
				}
				ref int numPointer3 = ref this.rndint;
				numPointer3 = checked(numPointer3 + num1);
			}
			if (this.rndint == this.Previous3)
			{
				int num2 = this.r.Next(-2, 2);
				ref int numPointer4 = ref this.rndint2;
				numPointer4 = checked(numPointer4 + num2);
				if (this.rndint2 > this.Max)
				{
					num2 = this.r.Next(-1, -3);
				}
				if (this.rndint2 < this.Min)
				{
					num2 = this.r.Next(1, 3);
				}
				ref int numPointer5 = ref this.rndint;
				numPointer5 = checked(numPointer5 + num2);
			}
			this.Previous1 = this.rndint;
			this.Previous2 = this.Previous1;
			this.Previous3 = this.Previous2;
			if (!this.FlatToggle1.Checked)
			{
				this.Timer1.Stop();
			}
			else if ((Form1.GetAsyncKeyState(Keys.LButton) & 32768) == 32768)
			{
				if (!this.BreakBlocks)
				{
					MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Left, System.Windows.Forms.Cursor.Position, true);
					await Task.Delay(this.rndint);
					MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Left, System.Windows.Forms.Cursor.Position, false);
				}
				else if ((Form1.GetAsyncKeyState(Keys.RButton) & 32768) != 32768)
				{
					MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Left, System.Windows.Forms.Cursor.Position, true);
					await Task.Delay(this.rndint);
					MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Left, System.Windows.Forms.Cursor.Position, false);
				}
			}
		}

		private void Timer10_Tick(object sender, EventArgs e)
		{
			int? min2;
			int? max2;
			bool? nullable;
			bool? nullable1;
			int? nullable2;
			bool? nullable3;
			int? nullable4;
			bool? nullable5;
			int? nullable6;
			bool? nullable7;
			int? nullable8;
			if (this.FlatToggle22.Checked)
			{
				if (!this.InChat && (Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox7.SelectedItem.ToString()]) & 32768) == 32768)
				{
					this.Min2 = new int?(Conversions.ToInteger(this.ComboBox4.Text));
				}
			}
			else if ((Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox7.SelectedItem.ToString()]) & 32768) == 32768)
			{
				this.Min2 = new int?(Conversions.ToInteger(this.ComboBox4.Text));
			}
			if (this.FlatToggle13.Checked)
			{
				if (this.FlatToggle22.Checked)
				{
					if (this.Min2.HasValue && !this.InChat)
					{
						if ((Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox6.SelectedItem.ToString()]) & 32768) == 32768)
						{
							InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.Min2.Value)], false);
							InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.Min2.Value)], true);
							this.MyDelay3();
							MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, true);
							this.MyDelay();
							MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, false);
							this.MyDelay3();
							InputHelper.SetKeyState(this.Hotkeys[this.ComboBox17.SelectedItem.ToString()], false);
							InputHelper.SetKeyState(this.Hotkeys[this.ComboBox17.SelectedItem.ToString()], true);
							this.MyDelay4();
							InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot2)], false);
							InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot2)], true);
							min2 = this.Min2;
							if (min2.HasValue)
							{
								nullable8 = new int?(checked(min2.GetValueOrDefault() + 1));
							}
							else
							{
								max2 = null;
								nullable8 = max2;
							}
							this.Min2 = nullable8;
						}
						min2 = this.Min2;
						max2 = this.Max2;
						if (min2.HasValue & max2.HasValue)
						{
							nullable7 = new bool?(min2.GetValueOrDefault() > max2.GetValueOrDefault());
						}
						else
						{
							nullable = null;
							nullable7 = nullable;
						}
						nullable = nullable7;
						if (nullable.GetValueOrDefault())
						{
							this.Min2 = null;
							return;
						}
					}
				}
				else if (this.Min2.HasValue)
				{
					if ((Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox6.SelectedItem.ToString()]) & 32768) == 32768)
					{
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.Min2.Value)], false);
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.Min2.Value)], true);
						this.MyDelay3();
						MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, true);
						this.MyDelay();
						MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, false);
						this.MyDelay4();
						InputHelper.SetKeyState(this.Hotkeys[this.ComboBox17.SelectedItem.ToString()], false);
						InputHelper.SetKeyState(this.Hotkeys[this.ComboBox17.SelectedItem.ToString()], true);
						this.MyDelay4();
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot2)], false);
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot2)], true);
						max2 = this.Min2;
						if (max2.HasValue)
						{
							nullable6 = new int?(checked(max2.GetValueOrDefault() + 1));
						}
						else
						{
							min2 = null;
							nullable6 = min2;
						}
						this.Min2 = nullable6;
					}
					max2 = this.Min2;
					min2 = this.Max2;
					if (max2.HasValue & min2.HasValue)
					{
						nullable5 = new bool?(max2.GetValueOrDefault() > min2.GetValueOrDefault());
					}
					else
					{
						nullable = null;
						nullable5 = nullable;
					}
					nullable = nullable5;
					if (nullable.GetValueOrDefault())
					{
						this.Min2 = null;
						return;
					}
				}
			}
			else if (this.FlatToggle22.Checked)
			{
				if (this.Min2.HasValue && !this.InChat)
				{
					if ((Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox6.SelectedItem.ToString()]) & 32768) == 32768)
					{
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.Min2.Value)], false);
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.Min2.Value)], true);
						this.MyDelay3();
						MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, true);
						this.MyDelay();
						MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, false);
						this.MyDelay4();
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot2)], false);
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot2)], true);
						min2 = this.Min2;
						if (min2.HasValue)
						{
							nullable4 = new int?(checked(min2.GetValueOrDefault() + 1));
						}
						else
						{
							max2 = null;
							nullable4 = max2;
						}
						this.Min2 = nullable4;
					}
					min2 = this.Min2;
					max2 = this.Max2;
					if (min2.HasValue & max2.HasValue)
					{
						nullable3 = new bool?(min2.GetValueOrDefault() > max2.GetValueOrDefault());
					}
					else
					{
						nullable = null;
						nullable3 = nullable;
					}
					nullable = nullable3;
					if (nullable.GetValueOrDefault())
					{
						this.Min2 = null;
						return;
					}
				}
			}
			else if (this.Min2.HasValue)
			{
				if ((Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox6.SelectedItem.ToString()]) & 32768) == 32768)
				{
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.Min2.Value)], false);
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.Min2.Value)], true);
					this.MyDelay3();
					MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, true);
					this.MyDelay();
					MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, false);
					this.MyDelay4();
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot2)], false);
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot2)], true);
					max2 = this.Min2;
					if (max2.HasValue)
					{
						nullable2 = new int?(checked(max2.GetValueOrDefault() + 1));
					}
					else
					{
						min2 = null;
						nullable2 = min2;
					}
					this.Min2 = nullable2;
				}
				max2 = this.Min2;
				min2 = this.Max2;
				if (max2.HasValue & min2.HasValue)
				{
					nullable1 = new bool?(max2.GetValueOrDefault() > min2.GetValueOrDefault());
				}
				else
				{
					nullable = null;
					nullable1 = nullable;
				}
				nullable = nullable1;
				if (nullable.GetValueOrDefault())
				{
					this.Min2 = null;
				}
			}
		}

		private void Timer11_Tick(object sender, EventArgs e)
		{
			if (this.FlatToggle22.Checked && !this.InChat && this.Hotkeys[this.ComboBox16.SelectedItem.ToString()] != Keys.LButton && (Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox16.SelectedItem.ToString()]) & 32768) == 32768)
			{
				if (!base.ShowInTaskbar)
				{
					base.ShowInTaskbar = true;
				}
				else
				{
					base.ShowInTaskbar = false;
				}
			}
			if (!this.FlatToggle22.Checked && this.Hotkeys[this.ComboBox16.SelectedItem.ToString()] != Keys.LButton && (Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox16.SelectedItem.ToString()]) & 32768) == 32768)
			{
				if (!base.ShowInTaskbar)
				{
					base.ShowInTaskbar = true;
				}
				else
				{
					base.ShowInTaskbar = false;
				}
			}
			if ((Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox14.SelectedItem.ToString()]) & 32768) == 32768 && !this.InChat)
			{
				this.wait2(10);
				this.InChat = true;
			}
			if ((Form1.GetAsyncKeyState(Keys.OemQuestion) & 32768) == 32768 && this.FlatToggle22.Checked && !this.InChat)
			{
				this.wait2(10);
				this.InChat = true;
			}
			if ((Form1.GetAsyncKeyState(Keys.Return) & 32768) == 32768 && this.FlatToggle22.Checked && this.InChat)
			{
				this.wait2(10);
				this.InChat = false;
			}
			if ((Form1.GetAsyncKeyState(Keys.Escape) & 32768) == 32768 && this.FlatToggle22.Checked && this.InChat)
			{
				this.wait2(10);
				this.InChat = false;
			}
			if (this.FlatToggle22.Checked && !this.InChat && (Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox8.SelectedItem.ToString()]) & 32768) == 32768)
			{
				if (this.FlatToggle1.Checked)
				{
					this.wait2(25);
					this.FlatToggle1.Checked = false;
					this.clickapply.PerformClick();
					base.Invalidate();
				}
				else
				{
					this.wait2(25);
					this.FlatToggle1.Checked = true;
					this.clickapply.PerformClick();
					base.Invalidate();
				}
			}
			if (!this.FlatToggle22.Checked && (Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox8.SelectedItem.ToString()]) & 32768) == 32768)
			{
				if (!this.FlatToggle1.Checked)
				{
					this.wait2(25);
					this.FlatToggle1.Checked = true;
					this.clickapply.PerformClick();
					base.Invalidate();
					return;
				}
				this.wait2(25);
				this.FlatToggle1.Checked = false;
				this.clickapply.PerformClick();
				base.Invalidate();
			}
		}

		private void Timer12_Tick(object sender, EventArgs e)
		{
			if (!this.FlatToggle22.Checked)
			{
				if (this.FlatToggle1.Checked)
				{
					this.ClickSoundJitterToggle = true;
					return;
				}
				this.ClickSoundJitterToggle = false;
			}
			else if (!this.InChat)
			{
				if (this.FlatToggle1.Checked)
				{
					this.ClickSoundJitterToggle = true;
					return;
				}
				this.ClickSoundJitterToggle = false;
				return;
			}
		}

		private async void Timer13_Tick(object sender, EventArgs e)
		{
			if (this.FlatTrackBar2.Value == 0 && (Form1.GetAsyncKeyState(Keys.W) & 32768) == 32768 && (Form1.GetAsyncKeyState(Keys.LButton) & 32768) == 32768)
			{
				InputHelper.SetKeyState(Keys.W, true);
				await Task.Delay(33);
				InputHelper.SetKeyState(Keys.W, false);
			}
			if (this.FlatTrackBar2.Value >= 1 && (Form1.GetAsyncKeyState(Keys.W) & 32768) == 32768 && (Form1.GetAsyncKeyState(Keys.LButton) & 32768) == 32768)
			{
				InputHelper.SetKeyState(Keys.W, true);
				await Task.Delay(this.r.Next(this.FlatTrackBar2.Value, checked(this.FlatTrackBar2.Value + 1)));
				InputHelper.SetKeyState(Keys.W, false);
			}
		}

		private async void Timer14_Tick(object sender, EventArgs e)
		{
			if (this.Timer1.Enabled && !this.BreakBlocks && (Form1.GetAsyncKeyState(Keys.RButton) & 32768) == 32768)
			{
				await Task.Delay(this.r.Next(35, 45));
				MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, true);
				await Task.Delay(this.r.Next(3, 8));
				MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, false);
				await Task.Delay(this.r.Next(102, 113));
			}
		}

		private async void Timer15_TickAsync(object sender, EventArgs e)
		{
			if (this.NsLabel24.Visible)
			{
				if (this.FlatToggle22.Checked)
				{
					if (!this.InChat && (Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox9.SelectedItem.ToString()]) & 32768) == 32768)
					{
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.RodSlot)], false);
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.RodSlot)], true);
						this.MyDelay3();
						MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, true);
						this.MyDelay();
						MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, false);
						this.RodMin = 654;
						this.MyDelay6();
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot)], false);
						InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot)], true);
					}
				}
				else if ((Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox9.SelectedItem.ToString()]) & 32768) == 32768)
				{
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.RodSlot)], false);
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.RodSlot)], true);
					this.MyDelay3();
					MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, true);
					this.MyDelay();
					MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, false);
					this.RodMin = 654;
					this.MyDelay6();
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot)], false);
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot)], true);
				}
			}
			else if (this.FlatToggle22.Checked)
			{
				if (!this.InChat && (Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox9.SelectedItem.ToString()]) & 32768) == 32768)
				{
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.RodSlot)], false);
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.RodSlot)], true);
					this.MyDelay3();
					MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, true);
					this.MyDelay();
					MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, false);
					this.RodMin = this.FlatTrackBar3.Value;
					this.MyDelay6();
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot)], false);
					InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot)], true);
				}
			}
			else if ((Form1.GetAsyncKeyState(this.Hotkeys[this.ComboBox9.SelectedItem.ToString()]) & 32768) == 32768)
			{
				InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.RodSlot)], false);
				InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.RodSlot)], true);
				this.MyDelay3();
				MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, true);
				this.MyDelay();
				MouseInputHelper.SendMouseClick(System.Windows.Forms.MouseButtons.Right, System.Windows.Forms.Cursor.Position, false);
				this.RodMin = this.FlatTrackBar3.Value;
				this.MyDelay6();
				InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot)], false);
				InputHelper.SetKeyState(this.Hotkeys[Conversions.ToString(this.SwordSlot)], true);
			}
		}

		private void Timer17_Tick(object sender, EventArgs e)
		{
			if (Process.GetProcessesByName("ProcessHacker").Count<Process>() > 0)
			{
				this.GUIReset();
				this.Label1.Text = null;
				this.Label2.Text = null;
				this.Label3.Text = null;
				this.Label4.Text = null;
				this.Label5.Text = null;
				this.Label6.Text = null;
				this.Label15.Text = null;
				this.Label16.Text = null;
				this.NsLabel1.Value1 = null;
				this.NsLabel1.Value2 = null;
				this.NsLabel2.Value1 = null;
				this.NsLabel2.Value2 = null;
				this.NsLabel3.Value1 = null;
				this.NsLabel3.Value2 = null;
				this.NsLabel4.Value1 = null;
				this.NsLabel4.Value2 = null;
				this.NsLabel5.Value1 = null;
				this.NsLabel5.Value2 = null;
				this.NsLabel6.Value1 = null;
				this.NsLabel6.Value2 = null;
				this.NsLabel7.Value1 = null;
				this.NsLabel7.Value2 = null;
				this.NsLabel9.Value1 = null;
				this.NsLabel9.Value2 = null;
				this.NsLabel10.Value1 = null;
				this.NsLabel10.Value2 = null;
				this.NsLabel11.Value1 = null;
				this.NsLabel11.Value2 = null;
				this.NsLabel12.Value1 = null;
				this.NsLabel12.Value2 = null;
				this.NsLabel13.Value1 = null;
				this.NsLabel13.Value2 = null;
				this.NsLabel14.Value1 = null;
				this.NsLabel14.Value2 = null;
				this.NsLabel15.Value1 = null;
				this.NsLabel15.Value2 = null;
				this.NsLabel16.Value1 = null;
				this.NsLabel16.Value2 = null;
				this.NsLabel17.Value1 = null;
				this.NsLabel17.Value2 = null;
				this.NsLabel18.Value1 = null;
				this.NsLabel18.Value2 = null;
				this.NsLabel19.Value1 = null;
				this.NsLabel19.Value2 = null;
				this.NsLabel21.Value1 = null;
				this.NsLabel21.Value2 = null;
				this.ComboBox1.Text = Conversions.ToString(1);
				this.ComboBox2.Text = Conversions.ToString(1);
				this.ComboBox3.Text = null;
				this.ComboBox4.Text = Conversions.ToString(1);
				this.ComboBox5.Text = Conversions.ToString(1);
				this.ComboBox6.Text = Conversions.ToString(1);
				this.ComboBox7.Text = Conversions.ToString(1);
				this.ComboBox8.Text = Conversions.ToString(1);
				this.ComboBox9.Text = Conversions.ToString(1);
				this.ComboBox10.Text = Conversions.ToString(1);
				this.ComboBox15.Text = Conversions.ToString(1);
				this.ComboBox12.Text = Conversions.ToString(1);
				this.ComboBox13.Text = Conversions.ToString(1);
				this.ComboBox14.Text = Conversions.ToString(1);
				this.ComboBox16.Text = Conversions.ToString(1);
				this.clickapply.Text = null;
				this.Button1.Text = null;
				this.Button2.Text = null;
				this.Button3.Text = null;
				this.Button5.Text = null;
				this.Button6.Text = null;
				this.Button7.Text = null;
				this.TabControlClass1.TabPages[0].Text = null;
				this.TabControlClass1.TabPages[1].Text = null;
				this.Melt(1);
			}
		}

		private void Timer19_Tick(object sender, EventArgs e)
		{
			base.Opacity = base.Opacity + 0.025;
			if (base.Opacity >= 1)
			{
				this.Timer19.Enabled = false;
				base.Opacity = 1;
			}
		}

		private void Timer2_Tick(object sender, EventArgs e)
		{
			System.Windows.Forms.Timer timer;
			Random random = new Random();
			int num = new int();
			int num1 = new int();
			int num2 = new int();
			int num3 = new int();
			int value = new int();
			int value1 = new int();
			int num4 = new int();
			int num5 = new int();
			int num6 = new int();
			if (this.FlatToggle31.Checked)
			{
				num2 = checked(random.Next(this.ClicksMin, this.ClicksMax) - 20);
				num3 = checked(random.Next(this.ClicksMin, this.ClicksMax) - 20);
			}
			else
			{
				value = this.TrackBar1.Value;
				value1 = this.TrackBar2.Value;
				num = random.Next(value, checked(value1 + 1));
				num1 = checked((int)Math.Round((double)num + (double)num * 0.45));
				num2 = checked((int)Math.Round(1000 / (double)num1));
				num3 = checked((int)Math.Round(1000 / (double)num1));
			}
			if (num2 == num4)
			{
				int num7 = random.Next(-5, 5);
				num3 = checked(num3 + num7);
				if ((double)num3 > 1000 / (double)value1 + (double)value1 * 0.45)
				{
					num7 = random.Next(-1, -5);
				}
				if ((double)num3 < 1000 / (double)value + (double)value * 0.45)
				{
					num7 = random.Next(1, 5);
				}
				num2 = checked(num2 + num7);
			}
			if (num2 == num5)
			{
				int num8 = random.Next(-5, 5);
				num3 = checked(num3 + num8);
				if ((double)num3 > 1000 / (double)value1 + (double)value1 * 0.45)
				{
					num8 = random.Next(-1, -5);
				}
				if ((double)num3 < 1000 / (double)value + (double)value * 0.45)
				{
					num8 = random.Next(1, 5);
				}
				num2 = checked(num2 + num8);
			}
			if (num2 == num6)
			{
				int num9 = random.Next(-5, 5);
				num3 = checked(num3 + num9);
				if ((double)num3 > 1000 / (double)value1 + (double)value1 * 0.45)
				{
					num9 = random.Next(-1, -5);
				}
				if ((double)num3 < 1000 / (double)value + (double)value * 0.45)
				{
					num9 = random.Next(1, 5);
				}
				num2 = checked(num2 + num9);
			}
			num4 = num2;
			num5 = num4;
			num6 = num5;
			if (!this.FlatToggle9.Checked)
			{
				if (this.FlatToggle8.Checked && !this.FlatToggle9.Checked)
				{
					VBMath.Randomize();
					switch (checked((int)Math.Round((double)Conversion.Int(7f * VBMath.Rnd()))))
					{
						case 1:
						{
							this.Timer1.Interval = num2;
							if (this.Timer1.Interval >= 100)
							{
								this.Timer5.Interval = 61;
								return;
							}
							this.Timer5.Interval = 60;
							return;
						}
						case 2:
						{
							this.Timer1.Interval = num2;
							if (this.Timer1.Interval >= 100)
							{
								this.Timer5.Interval = 61;
								return;
							}
							this.Timer5.Interval = 60;
							return;
						}
						case 3:
						{
							this.Timer1.Interval = num2;
							if (this.Timer1.Interval >= 100)
							{
								this.Timer5.Interval = 61;
								return;
							}
							this.Timer5.Interval = 60;
							return;
						}
						case 4:
						{
							this.Timer1.Interval = num2;
							if (this.Timer1.Interval >= 100)
							{
								this.Timer5.Interval = 61;
								return;
							}
							this.Timer5.Interval = 60;
							return;
						}
						case 5:
						{
							this.Timer1.Interval = num2;
							if (this.Timer1.Interval >= 100)
							{
								this.Timer5.Interval = 61;
								return;
							}
							this.Timer5.Interval = 60;
							return;
						}
						case 6:
						{
							VBMath.Randomize();
							switch (checked((int)Math.Round((double)Conversion.Int(21f * VBMath.Rnd()))))
							{
								case 1:
								{
									System.Windows.Forms.Timer timer1 = this.Timer1;
									timer = timer1;
									timer1.Interval = checked(timer.Interval + checked(20 + this.r.Next(0, 5)));
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 2:
								{
									System.Windows.Forms.Timer interval = this.Timer1;
									timer = interval;
									interval.Interval = checked(timer.Interval + checked(40 + this.r.Next(0, 5)));
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 3:
								{
									System.Windows.Forms.Timer timer11 = this.Timer1;
									timer = timer11;
									timer11.Interval = checked(timer.Interval + checked(60 + this.r.Next(0, 5)));
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 4:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 5:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 6:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 7:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 8:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 9:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 10:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 11:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 12:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 13:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 14:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 15:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 16:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 17:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 18:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 19:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								case 20:
								{
									this.Timer1.Interval = num2;
									if (this.Timer1.Interval >= 100)
									{
										this.Timer5.Interval = 61;
										return;
									}
									this.Timer5.Interval = 60;
									return;
								}
								default:
								{
									return;
								}
							}
							break;
						}
						default:
						{
							return;
						}
					}
				}
				VBMath.Randomize();
				switch (checked((int)Math.Round((double)Conversion.Int(6f * VBMath.Rnd()))))
				{
					case 1:
					{
						this.Timer1.Interval = num2;
						if (this.Timer1.Interval >= 100)
						{
							this.Timer5.Interval = 61;
							return;
						}
						this.Timer5.Interval = 60;
						return;
					}
					case 2:
					{
						this.Timer1.Interval = num2;
						if (this.Timer1.Interval >= 100)
						{
							this.Timer5.Interval = 61;
							return;
						}
						this.Timer5.Interval = 60;
						return;
					}
					case 3:
					{
						this.Timer1.Interval = num2;
						if (this.Timer1.Interval >= 100)
						{
							this.Timer5.Interval = 61;
							return;
						}
						this.Timer5.Interval = 60;
						return;
					}
					case 4:
					{
						this.Timer1.Interval = num2;
						if (this.Timer1.Interval >= 100)
						{
							this.Timer5.Interval = 61;
							return;
						}
						this.Timer5.Interval = 60;
						return;
					}
					case 5:
					{
						this.Timer1.Interval = num2;
						if (this.Timer1.Interval >= 100)
						{
							this.Timer5.Interval = 61;
							return;
						}
						this.Timer5.Interval = 60;
						break;
					}
					default:
					{
						return;
					}
				}
			}
			else
			{
				VBMath.Randomize();
				switch (checked((int)Math.Round((double)Conversion.Int(7f * VBMath.Rnd()))))
				{
					case 1:
					{
						this.Timer1.Interval = num2;
						if (this.Timer1.Interval >= 100)
						{
							this.Timer5.Interval = 61;
							return;
						}
						this.Timer5.Interval = 60;
						return;
					}
					case 2:
					{
						this.Timer1.Interval = num2;
						if (this.Timer1.Interval >= 100)
						{
							this.Timer5.Interval = 61;
							return;
						}
						this.Timer5.Interval = 60;
						return;
					}
					case 3:
					{
						this.Timer1.Interval = num2;
						if (this.Timer1.Interval >= 100)
						{
							this.Timer5.Interval = 61;
							return;
						}
						this.Timer5.Interval = 60;
						return;
					}
					case 4:
					{
						this.Timer1.Interval = num2;
						if (this.Timer1.Interval >= 100)
						{
							this.Timer5.Interval = 61;
							return;
						}
						this.Timer5.Interval = 60;
						return;
					}
					case 5:
					{
						this.Timer1.Interval = num2;
						if (this.Timer1.Interval >= 100)
						{
							this.Timer5.Interval = 61;
							return;
						}
						this.Timer5.Interval = 60;
						return;
					}
					case 6:
					{
						VBMath.Randomize();
						switch (checked((int)Math.Round((double)Conversion.Int(21f * VBMath.Rnd()))))
						{
							case 1:
							{
								if ((double)this.ayeeee2 == Conversions.ToDouble("1"))
								{
									System.Windows.Forms.Timer interval1 = this.Timer1;
									timer = interval1;
									interval1.Interval = checked(timer.Interval + checked(20 + this.r.Next(0, 5)));
									this.Timer5.Interval = 62;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("2"))
								{
									System.Windows.Forms.Timer timer12 = this.Timer1;
									timer = timer12;
									timer12.Interval = checked(timer.Interval + checked(40 + this.r.Next(0, 5)));
									this.Timer5.Interval = 62;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("3"))
								{
									System.Windows.Forms.Timer interval2 = this.Timer1;
									timer = interval2;
									interval2.Interval = checked(timer.Interval + checked(60 + this.r.Next(0, 5)));
									this.Timer5.Interval = 62;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("4"))
								{
									System.Windows.Forms.Timer timer2 = this.Timer1;
									timer = timer2;
									timer2.Interval = checked(timer.Interval + checked(80 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("5"))
								{
									System.Windows.Forms.Timer timer13 = this.Timer1;
									timer = timer13;
									timer13.Interval = checked(timer.Interval + checked(100 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("6"))
								{
									System.Windows.Forms.Timer interval3 = this.Timer1;
									timer = interval3;
									interval3.Interval = checked(timer.Interval + checked(120 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("7"))
								{
									System.Windows.Forms.Timer timer3 = this.Timer1;
									timer = timer3;
									timer3.Interval = checked(timer.Interval + checked(140 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("8"))
								{
									System.Windows.Forms.Timer timer14 = this.Timer1;
									timer = timer14;
									timer14.Interval = checked(timer.Interval + checked(160 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("9"))
								{
									System.Windows.Forms.Timer interval4 = this.Timer1;
									timer = interval4;
									interval4.Interval = checked(timer.Interval + checked(180 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 != Conversions.ToDouble("10"))
								{
									return;
								}
								System.Windows.Forms.Timer timer4 = this.Timer1;
								timer = timer4;
								timer4.Interval = checked(timer.Interval + checked(200 + this.r.Next(0, 5)));
								this.Timer5.Interval = 70;
								return;
							}
							case 2:
							{
								if ((double)this.ayeeee2 == Conversions.ToDouble("1"))
								{
									System.Windows.Forms.Timer timer15 = this.Timer1;
									timer = timer15;
									timer15.Interval = checked(timer.Interval + checked(20 + this.r.Next(0, 5)));
									this.Timer5.Interval = 62;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("2"))
								{
									System.Windows.Forms.Timer interval5 = this.Timer1;
									timer = interval5;
									interval5.Interval = checked(timer.Interval + checked(40 + this.r.Next(0, 5)));
									this.Timer5.Interval = 62;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("3"))
								{
									System.Windows.Forms.Timer timer5 = this.Timer1;
									timer = timer5;
									timer5.Interval = checked(timer.Interval + checked(60 + this.r.Next(0, 5)));
									this.Timer5.Interval = 62;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("4"))
								{
									System.Windows.Forms.Timer timer16 = this.Timer1;
									timer = timer16;
									timer16.Interval = checked(timer.Interval + checked(80 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("5"))
								{
									System.Windows.Forms.Timer interval6 = this.Timer1;
									timer = interval6;
									interval6.Interval = checked(timer.Interval + checked(100 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("6"))
								{
									System.Windows.Forms.Timer timer6 = this.Timer1;
									timer = timer6;
									timer6.Interval = checked(timer.Interval + checked(120 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("7"))
								{
									System.Windows.Forms.Timer timer17 = this.Timer1;
									timer = timer17;
									timer17.Interval = checked(timer.Interval + checked(140 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("8"))
								{
									System.Windows.Forms.Timer interval7 = this.Timer1;
									timer = interval7;
									interval7.Interval = checked(timer.Interval + checked(160 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("9"))
								{
									System.Windows.Forms.Timer timer7 = this.Timer1;
									timer = timer7;
									timer7.Interval = checked(timer.Interval + checked(180 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 != Conversions.ToDouble("10"))
								{
									return;
								}
								System.Windows.Forms.Timer timer18 = this.Timer1;
								timer = timer18;
								timer18.Interval = checked(timer.Interval + checked(200 + this.r.Next(0, 5)));
								this.Timer5.Interval = 70;
								return;
							}
							case 3:
							{
								if ((double)this.ayeeee2 == Conversions.ToDouble("1"))
								{
									System.Windows.Forms.Timer interval8 = this.Timer1;
									timer = interval8;
									interval8.Interval = checked(timer.Interval + checked(20 + this.r.Next(0, 5)));
									this.Timer5.Interval = 62;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("2"))
								{
									System.Windows.Forms.Timer timer8 = this.Timer1;
									timer = timer8;
									timer8.Interval = checked(timer.Interval + checked(40 + this.r.Next(0, 5)));
									this.Timer5.Interval = 62;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("3"))
								{
									System.Windows.Forms.Timer timer19 = this.Timer1;
									timer = timer19;
									timer19.Interval = checked(timer.Interval + checked(60 + this.r.Next(0, 5)));
									this.Timer5.Interval = 62;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("4"))
								{
									System.Windows.Forms.Timer interval9 = this.Timer1;
									timer = interval9;
									interval9.Interval = checked(timer.Interval + checked(80 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("5"))
								{
									System.Windows.Forms.Timer timer9 = this.Timer1;
									timer = timer9;
									timer9.Interval = checked(timer.Interval + checked(100 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("6"))
								{
									System.Windows.Forms.Timer timer110 = this.Timer1;
									timer = timer110;
									timer110.Interval = checked(timer.Interval + checked(120 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("7"))
								{
									System.Windows.Forms.Timer interval10 = this.Timer1;
									timer = interval10;
									interval10.Interval = checked(timer.Interval + checked(140 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("8"))
								{
									System.Windows.Forms.Timer timer10 = this.Timer1;
									timer = timer10;
									timer10.Interval = checked(timer.Interval + checked(160 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 == Conversions.ToDouble("9"))
								{
									System.Windows.Forms.Timer timer111 = this.Timer1;
									timer = timer111;
									timer111.Interval = checked(timer.Interval + checked(180 + this.r.Next(0, 5)));
									this.Timer5.Interval = 70;
									return;
								}
								if ((double)this.ayeeee2 != Conversions.ToDouble("10"))
								{
									return;
								}
								System.Windows.Forms.Timer interval11 = this.Timer1;
								timer = interval11;
								interval11.Interval = checked(timer.Interval + checked(200 + this.r.Next(0, 5)));
								this.Timer5.Interval = 70;
								return;
							}
							case 4:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 5:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 6:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 7:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 8:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 9:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 10:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 11:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 12:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 13:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 14:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 15:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 16:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 17:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 18:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 19:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							case 20:
							{
								this.Timer1.Interval = num2;
								if (this.Timer1.Interval >= 100)
								{
									this.Timer5.Interval = 61;
									return;
								}
								this.Timer5.Interval = 60;
								return;
							}
							default:
							{
								return;
							}
						}
						break;
					}
					default:
					{
						return;
					}
				}
			}
		}

		private void Timer20_Tick(object sender, EventArgs e)
		{
			base.Opacity = base.Opacity - 0.025;
			if (base.Opacity <= 0)
			{
				this.Timer20.Enabled = false;
				base.Opacity = 0;
				if (this.kbHook != null)
				{
					this.kbHook.Dispose();
				}
				this.Label1.Text = null;
				this.Label2.Text = null;
				this.Label3.Text = null;
				this.Label4.Text = null;
				this.Label5.Text = null;
				this.Label6.Text = null;
				this.Label15.Text = null;
				this.Label16.Text = null;
				this.NsLabel1.Value1 = null;
				this.NsLabel1.Value2 = null;
				this.NsLabel2.Value1 = null;
				this.NsLabel2.Value2 = null;
				this.NsLabel3.Value1 = null;
				this.NsLabel3.Value2 = null;
				this.NsLabel4.Value1 = null;
				this.NsLabel4.Value2 = null;
				this.NsLabel5.Value1 = null;
				this.NsLabel5.Value2 = null;
				this.NsLabel6.Value1 = null;
				this.NsLabel6.Value2 = null;
				this.NsLabel7.Value1 = null;
				this.NsLabel7.Value2 = null;
				this.NsLabel9.Value1 = null;
				this.NsLabel9.Value2 = null;
				this.NsLabel10.Value1 = null;
				this.NsLabel10.Value2 = null;
				this.NsLabel11.Value1 = null;
				this.NsLabel11.Value2 = null;
				this.NsLabel12.Value1 = null;
				this.NsLabel12.Value2 = null;
				this.NsLabel13.Value1 = null;
				this.NsLabel13.Value2 = null;
				this.NsLabel14.Value1 = null;
				this.NsLabel14.Value2 = null;
				this.NsLabel15.Value1 = null;
				this.NsLabel15.Value2 = null;
				this.NsLabel16.Value1 = null;
				this.NsLabel16.Value2 = null;
				this.NsLabel17.Value1 = null;
				this.NsLabel17.Value2 = null;
				this.NsLabel18.Value1 = null;
				this.NsLabel18.Value2 = null;
				this.NsLabel19.Value1 = null;
				this.NsLabel19.Value2 = null;
				this.NsLabel21.Value1 = null;
				this.NsLabel21.Value2 = null;
				this.ComboBox1.Text = Conversions.ToString(1);
				this.ComboBox2.Text = Conversions.ToString(1);
				this.ComboBox3.Text = null;
				this.ComboBox4.Text = Conversions.ToString(1);
				this.ComboBox5.Text = Conversions.ToString(1);
				this.ComboBox6.Text = Conversions.ToString(1);
				this.ComboBox7.Text = Conversions.ToString(1);
				this.ComboBox8.Text = Conversions.ToString(1);
				this.ComboBox9.Text = Conversions.ToString(1);
				this.ComboBox10.Text = Conversions.ToString(1);
				this.ComboBox15.Text = Conversions.ToString(1);
				this.ComboBox12.Text = Conversions.ToString(1);
				this.ComboBox13.Text = Conversions.ToString(1);
				this.ComboBox14.Text = Conversions.ToString(1);
				this.ComboBox16.Text = Conversions.ToString(1);
				this.clickapply.Text = null;
				this.Button1.Text = null;
				this.Button2.Text = null;
				this.Button3.Text = null;
				this.Button5.Text = null;
				this.Button6.Text = null;
				this.Button7.Text = null;
				this.TabControlClass1.TabPages[0].Text = null;
				this.TabControlClass1.TabPages[1].Text = null;
				this.TabControlClass1.TabPages[2].Text = null;
				this.TabControlClass1.TabPages[3].Text = null;
				this.TabControlClass1.TabPages[4].Text = null;
				this.TabControlClass1.TabPages[5].Text = null;
				this.Melt(1);
			}
		}

		public void Timer21_Tick(object sender, EventArgs e)
		{
			if (this.FlatToggleColor >= 73)
			{
				this.Timer21.Enabled = false;
				this.FlatToggleColor = 73;
				this.SavedFlatToggleColor = 73;
				this.FlatToggleFade = false;
				this.FlatToggle1.Invalidate();
				return;
			}
			this.SavedFlatToggleColor = this.FlatToggleColor;
			this.FlatToggleColor += 0.25;
			this.FlatToggleFade = true;
			this.FlatToggle1.Invalidate();
		}

		private void Timer3_Tick(object sender, EventArgs e)
		{
			if ((Form1.GetAsyncKeyState(Keys.LButton) & 32768) == 32768)
			{
				this.Timer1.Start();
				return;
			}
			this.Timer1.Stop();
		}

		private void Timer4_Tick(object sender, EventArgs e)
		{
			Point mousePosition;
			if (this.Timer1.Enabled && this.ClickSoundJitterToggle)
			{
				if (!this.BreakBlocks)
				{
					VBMath.Randomize();
					switch (checked((int)Math.Round((double)Conversion.Int(5f * VBMath.Rnd()))))
					{
						case 1:
						{
							mousePosition = Control.MousePosition;
							int num = checked((int)Math.Round((double)mousePosition.X + Math.Round(-1 * (double)this.ayeeeeV)));
							mousePosition = Control.MousePosition;
							System.Windows.Forms.Cursor.Position = new Point(num, checked((int)Math.Round((double)mousePosition.Y + Math.Round(-1 * (double)this.ayeeeeH))));
							return;
						}
						case 2:
						{
							mousePosition = Control.MousePosition;
							int num1 = checked((int)Math.Round((double)mousePosition.X - Math.Round(-1 * (double)this.ayeeeeV)));
							mousePosition = Control.MousePosition;
							System.Windows.Forms.Cursor.Position = new Point(num1, checked((int)Math.Round((double)mousePosition.Y - Math.Round(-1 * (double)this.ayeeeeH))));
							return;
						}
						case 3:
						{
							mousePosition = Control.MousePosition;
							int num2 = checked((int)Math.Round((double)mousePosition.X + Math.Round(-1 * (double)this.ayeeeeV)));
							mousePosition = Control.MousePosition;
							System.Windows.Forms.Cursor.Position = new Point(num2, checked((int)Math.Round((double)mousePosition.Y - Math.Round(-1 * (double)this.ayeeeeH))));
							return;
						}
						case 4:
						{
							mousePosition = Control.MousePosition;
							int num3 = checked((int)Math.Round((double)mousePosition.X - Math.Round(-1 * (double)this.ayeeeeV)));
							mousePosition = Control.MousePosition;
							System.Windows.Forms.Cursor.Position = new Point(num3, checked((int)Math.Round((double)mousePosition.Y + Math.Round(-1 * (double)this.ayeeeeH))));
							break;
						}
						default:
						{
							return;
						}
					}
				}
				else if ((Form1.GetAsyncKeyState(Keys.RButton) & 32768) != 32768)
				{
					VBMath.Randomize();
					switch (checked((int)Math.Round((double)Conversion.Int(5f * VBMath.Rnd()))))
					{
						case 1:
						{
							mousePosition = Control.MousePosition;
							int num4 = checked((int)Math.Round((double)mousePosition.X + Math.Round(-1 * (double)this.ayeeeeV)));
							mousePosition = Control.MousePosition;
							System.Windows.Forms.Cursor.Position = new Point(num4, checked((int)Math.Round((double)mousePosition.Y + Math.Round(-1 * (double)this.ayeeeeH))));
							return;
						}
						case 2:
						{
							mousePosition = Control.MousePosition;
							int num5 = checked((int)Math.Round((double)mousePosition.X - Math.Round(-1 * (double)this.ayeeeeV)));
							mousePosition = Control.MousePosition;
							System.Windows.Forms.Cursor.Position = new Point(num5, checked((int)Math.Round((double)mousePosition.Y - Math.Round(-1 * (double)this.ayeeeeH))));
							return;
						}
						case 3:
						{
							mousePosition = Control.MousePosition;
							int num6 = checked((int)Math.Round((double)mousePosition.X + Math.Round(-1 * (double)this.ayeeeeV)));
							mousePosition = Control.MousePosition;
							System.Windows.Forms.Cursor.Position = new Point(num6, checked((int)Math.Round((double)mousePosition.Y - Math.Round(-1 * (double)this.ayeeeeH))));
							return;
						}
						case 4:
						{
							mousePosition = Control.MousePosition;
							int num7 = checked((int)Math.Round((double)mousePosition.X - Math.Round(-1 * (double)this.ayeeeeV)));
							mousePosition = Control.MousePosition;
							System.Windows.Forms.Cursor.Position = new Point(num7, checked((int)Math.Round((double)mousePosition.Y + Math.Round(-1 * (double)this.ayeeeeH))));
							return;
						}
						default:
						{
							return;
						}
					}
				}
			}
		}

		private void Timer5_Tick(object sender, EventArgs e)
		{
			if (this.Timer1.Enabled && this.ClickSoundJitterToggle)
			{
				if (!this.BreakBlocks)
				{
					if (Operators.CompareString(this.ComboBox3.Text, "Default Sound", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.Untitled, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Logitech G502", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.test, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Logitech GPro", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.GPro, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Logitech G303", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.g303, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Logitech G303 (Different)", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.g3032, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Microsoft Mouse", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.microsoft, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "HP Mouse", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.hp, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Non-Brand Mouse", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.oldmouse, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Razer Deathadder", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.test2, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Custom Sound", false) == 0)
					{
						this.Timer5.Interval = this.audiolength;
						MyProject.Computer.Audio.Play(this.customsoundpath, AudioPlayMode.Background);
						return;
					}
					this.Timer5.Interval = 60;
				}
				else if ((Form1.GetAsyncKeyState(Keys.RButton) & 32768) != 32768)
				{
					if (Operators.CompareString(this.ComboBox3.Text, "Default Sound", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.Untitled, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Logitech G502", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.test, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Logitech GPro", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.GPro, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Logitech G303", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.g303, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Logitech G303 (Different)", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.g3032, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Microsoft Mouse", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.microsoft, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "HP Mouse", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.hp, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Non-Brand Mouse", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.oldmouse, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Razer Deathadder", false) == 0)
					{
						MyProject.Computer.Audio.Play(Resources.test2, AudioPlayMode.Background);
					}
					if (Operators.CompareString(this.ComboBox3.Text, "Custom Sound", false) != 0)
					{
						this.Timer5.Interval = 60;
						return;
					}
					this.Timer5.Interval = this.audiolength;
					MyProject.Computer.Audio.Play(this.customsoundpath, AudioPlayMode.Background);
					return;
				}
			}
		}

		private void Timer8_Tick(object sender, EventArgs e)
		{
			string caption = this.GetCaption();
			if (Operators.CompareString(this.makel, caption, false) != 0)
			{
				this.makel = caption;
				this.Timer8.Stop();
				if (!caption.Contains("Minecraft"))
				{
					this.Timer3.Enabled = false;
					this.Timer1.Enabled = false;
					this.Timer2.Enabled = false;
					this.Timer4.Enabled = false;
					this.Timer5.Enabled = false;
					this.Timer10.Enabled = false;
					this.Timer13.Enabled = false;
					this.Timer14.Enabled = false;
					this.Timer15.Enabled = false;
				}
				else
				{
					this.ayeeeeH = Conversions.ToInteger(this.ComboBox2.Text);
					this.ayeeeeV = Conversions.ToInteger(this.ComboBox12.Text);
					this.ayeeee2 = Conversions.ToInteger(this.ComboBox1.Text);
					if (this.FlatToggle8.Checked && this.FlatToggle9.Checked)
					{
						Interaction.MsgBox("Manual CPS Drops Needs To Be Turned Off For Smart CPS Drops To Work Properly", MsgBoxStyle.Information, this.Text);
						this.FlatToggle8.Checked = false;
						this.FlatToggle9.Checked = false;
					}
					if (!this.FlatToggle3.Checked)
					{
						this.Timer2.Stop();
					}
					else
					{
						this.Timer2.Start();
					}
					if (!this.FlatToggle1.Checked)
					{
						base.KeyPreview = false;
					}
					else
					{
						base.KeyPreview = true;
						this.Timer3.Start();
					}
					if (!this.FlatToggle2.Checked)
					{
						this.Timer4.Stop();
					}
					else
					{
						this.Timer4.Start();
					}
					if (!this.FlatToggle6.Checked)
					{
						this.Timer5.Stop();
					}
					else
					{
						this.Timer5.Start();
					}
					if (!this.FlatToggle4.Checked)
					{
						this.Timer8.Stop();
					}
					else
					{
						this.Timer8.Start();
					}
					if (!this.FlatToggle5.Checked)
					{
						this.BreakBlocks = false;
					}
					else
					{
						this.BreakBlocks = true;
					}
					if (!this.FlatToggle11.Checked)
					{
						this.Timer10.Stop();
					}
					else
					{
						this.Timer10.Start();
					}
					if (!this.FlatToggle10.Checked)
					{
						this.Timer13.Stop();
					}
					else
					{
						this.Timer13.Start();
					}
					if (!this.FlatToggle7.Checked)
					{
						this.Timer14.Stop();
					}
					else
					{
						this.Timer14.Start();
					}
					if (!this.FlatToggle12.Checked)
					{
						this.Timer15.Stop();
					}
					else
					{
						this.Timer15.Start();
					}
				}
				this.Timer8.Start();
			}
		}

		private void Timer9_Tick(object sender, EventArgs e)
		{
			if (this.TrackBar1.Value > this.TrackBar2.Value)
			{
				this.TrackBar1.Value = this.TrackBar2.Value;
				this.NsLabel17.Value1 = Conversions.ToString(this.TrackBar2.Value);
			}
		}

		private void TrackBar1_Scroll(object sender, EventArgs e)
		{
			this.NsLabel17.Value1 = Conversions.ToString(this.TrackBar1.Value);
		}

		private void TrackBar2_Scroll(object sender, EventArgs e)
		{
			this.NsLabel16.Value1 = Conversions.ToString(this.TrackBar2.Value);
		}

		private void wait(int seconds)
		{
			int num = checked(seconds * 100);
			for (int i = 0; i <= num; i = checked(i + 1))
			{
				Thread.Sleep(10);
				Application.DoEvents();
			}
		}

		private void wait2(int interval)
		{
			Stopwatch stopwatch = new Stopwatch();
			stopwatch.Start();
			while (stopwatch.ElapsedMilliseconds < (long)interval)
			{
				Application.DoEvents();
			}
			stopwatch.Stop();
		}

		private void wClient2_DownloadCompleted(object sender, AsyncCompletedEventArgs e)
		{
			this.DownloadDone2 = true;
		}

		public class clsComputerInfo
		{
			public clsComputerInfo()
			{
			}

			internal string GetMACAddress()
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator = null;
				ManagementObjectCollection instances = (new ManagementClass("Win32_NetworkAdapterConfiguration")).GetInstances();
				using (string empty = string.Empty)
				{
					enumerator = instances.GetEnumerator();
					while (enumerator.MoveNext())
					{
						ManagementObject current = (ManagementObject)enumerator.Current;
						if (empty.Equals(string.Empty))
						{
							if (Conversions.ToBoolean(current["IPEnabled"]))
							{
								empty = current["MacAddress"].ToString();
							}
							current.Dispose();
						}
						empty = empty.Replace(":", string.Empty);
					}
				}
				return empty;
			}

			internal string getMD5Hash(string strToHash)
			{
				MD5CryptoServiceProvider mD5CryptoServiceProvider = new MD5CryptoServiceProvider();
				byte[] bytes = Encoding.ASCII.GetBytes(strToHash);
				bytes = mD5CryptoServiceProvider.ComputeHash(bytes);
				string str = "";
				byte[] numArray = bytes;
				for (int i = 0; i < (int)numArray.Length; i = checked(i + 1))
				{
					byte num = numArray[i];
					str = string.Concat(str, num.ToString("x2"));
				}
				return str;
			}

			internal string GetMotherBoardID()
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator = null;
				string empty = string.Empty;
				using (ManagementObjectSearcher managementObjectSearcher = new ManagementObjectSearcher(new SelectQuery("Win32_BaseBoard")))
				{
					enumerator = managementObjectSearcher.Get().GetEnumerator();
					while (enumerator.MoveNext())
					{
						empty = ((ManagementObject)enumerator.Current)["product"].ToString();
					}
				}
				return empty;
			}

			internal string GetProcessorId()
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator = null;
				string empty = string.Empty;
				using (ManagementObjectSearcher managementObjectSearcher = new ManagementObjectSearcher(new SelectQuery("Win32_processor")))
				{
					enumerator = managementObjectSearcher.Get().GetEnumerator();
					while (enumerator.MoveNext())
					{
						empty = ((ManagementObject)enumerator.Current)["processorId"].ToString();
					}
				}
				return empty;
			}

			internal string GetVolumeSerial(string strDriveLetter = "C")
			{
				ManagementObject managementObject = new ManagementObject(string.Format("win32_logicaldisk.deviceid=\"{0}:\"", strDriveLetter));
				managementObject.Get();
				return managementObject["VolumeSerialNumber"].ToString();
			}
		}
	}
}