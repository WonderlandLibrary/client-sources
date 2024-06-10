using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using Action_Installer.My;
using Action_Installer.My.Resources;
using gTrackBar;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

namespace Action_Installer
{
	// Token: 0x02000002 RID: 2
	[DesignerGenerated]
	public partial class frmAutoclicker : Form
	{
		// Token: 0x06000001 RID: 1 RVA: 0x00002050 File Offset: 0x00000250
		public frmAutoclicker()
		{
			this.hasSimulatedUp = true;
			this.hasSimulatedDown = true;
			this.newSound = false;
			this.soundToPlay = "";
			this.fd = new OpenFileDialog();
			this.mH = new mH.mH();
			this.exitSub = false;
			this.isSimulatingClick = false;
			this.r = new Random();
			this.InitializeComponent();
		}

		// Token: 0x17000001 RID: 1
		// (get) Token: 0x06000002 RID: 2 RVA: 0x000020BB File Offset: 0x000002BB
		// (set) Token: 0x06000003 RID: 3 RVA: 0x000020C8 File Offset: 0x000002C8
		public virtual mH.mH mH
		{
			[CompilerGenerated]
			get
			{
				return this._mH;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				mH.mH.mldEventHandler obj = new mH.mH.mldEventHandler(this.mh_ld);
				mH.mH.mluEventHandler obj2 = new mH.mH.mluEventHandler(this.mh_lu);
				mH.mH mH = this._mH;
				if (mH != null)
				{
					mH.mld -= obj;
					mH.mlu -= obj2;
				}
				this._mH = value;
				mH = this._mH;
				if (mH != null)
				{
					mH.mld += obj;
					mH.mlu += obj2;
				}
			}
		}

		// Token: 0x06000004 RID: 4
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

		// Token: 0x06000005 RID: 5
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x06000006 RID: 6
		[DllImport("user32", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowText(IntPtr hWnd, StringBuilder lpString, int cch);

		// Token: 0x06000007 RID: 7 RVA: 0x00002128 File Offset: 0x00000328
		private void trbCPS_ValueChanged(object sender, EventArgs e)
		{
			this.lblCPS.Text = "CPS: " + this.trbCPS.Value.ToString();
		}

		// Token: 0x06000008 RID: 8 RVA: 0x00002160 File Offset: 0x00000360
		private void trbRandomization_ValueChanged(object sender, EventArgs e)
		{
			this.lblRand.Text = "Rand: " + this.trbRandomization.Value.ToString();
		}

		// Token: 0x06000009 RID: 9 RVA: 0x00002198 File Offset: 0x00000398
		private string GetCaption()
		{
			StringBuilder stringBuilder = new StringBuilder(256);
			IntPtr foregroundWindow = frmAutoclicker.GetForegroundWindow();
			frmAutoclicker.GetWindowText(foregroundWindow, stringBuilder, stringBuilder.Capacity);
			return stringBuilder.ToString();
		}

		// Token: 0x0600000A RID: 10 RVA: 0x000021D0 File Offset: 0x000003D0
		private void tmrMouseDown_Tick(object sender, EventArgs e)
		{
			bool flag = this.hasSimulatedUp;
			if (flag)
			{
				this.hasSimulatedUp = false;
				this.hasSimulatedDown = true;
				bool flag2 = !this.exitSub;
				if (flag2)
				{
					bool @checked = this.chkJitter.Checked;
					if (@checked)
					{
						Debug.WriteLine("Simulating Jitter");
						Cursor.Position = checked(new Point(Control.MousePosition.X + this.r.Next((int)Math.Round(unchecked(-1.0 * Conversions.ToDouble(this.txtPixels.Text))), Conversions.ToInteger(this.txtPixels.Text)), Control.MousePosition.Y + this.r.Next((int)Math.Round(unchecked(-1.0 * Conversions.ToDouble(this.txtPixels.Text))), Conversions.ToInteger(this.txtPixels.Text))));
					}
					Debug.WriteLine("Simulating down");
					this.isSimulatingClick = true;
					frmAutoclicker.mouse_event(2, 0, 0, 0, 0);
					this.isSimulatingClick = false;
				}
			}
		}

		// Token: 0x0600000B RID: 11 RVA: 0x000022F0 File Offset: 0x000004F0
		private void tmrMouseUp_Tick(object sender, EventArgs e)
		{
			bool flag = this.hasSimulatedDown & !this.exitSub;
			if (flag)
			{
				this.hasSimulatedDown = false;
				this.hasSimulatedUp = true;
				Debug.WriteLine("Simulating up");
				this.isSimulatingClick = true;
				frmAutoclicker.mouse_event(4, 0, 0, 0, 0);
				this.isSimulatingClick = false;
			}
			bool flag2 = this.exitSub;
			if (flag2)
			{
				Debug.WriteLine("Stopping...");
				this.tmrMouseUp.Stop();
				this.tmrMouseDown.Stop();
				this.tmrRandomize.Stop();
			}
		}

		// Token: 0x0600000C RID: 12 RVA: 0x00002384 File Offset: 0x00000584
		private void tmrRandomize_Tick(object sender, EventArgs e)
		{
			Debug.WriteLine("Randomizing...");
			VBMath.Randomize();
			bool flag = this.r.Next(1, 20) == 1 & this.chkRandomStops.Checked;
			checked
			{
				if (flag)
				{
					this.tmrMouseDown.Interval = (int)Math.Round(850.0 / (double)this.r.Next(5, 8));
					this.tmrMouseUp.Interval = this.tmrMouseDown.Interval;
				}
				else
				{
					this.tmrMouseDown.Interval = (int)Math.Round(862.5 / (double)this.r.Next(this.trbCPS.Value - this.trbRandomization.Value, this.trbCPS.Value + this.trbRandomization.Value));
					this.tmrMouseUp.Interval = this.tmrMouseDown.Interval;
				}
			}
		}

		// Token: 0x0600000D RID: 13 RVA: 0x00002478 File Offset: 0x00000678
		private void mh_ld()
		{
			bool flag = !this.isSimulatingClick;
			if (flag)
			{
				this.hasSimulatedDown = true;
				this.hasSimulatedUp = true;
				bool @checked = this.chkOnlyClickOnMinecraft.Checked;
				if (@checked)
				{
					bool flag2 = this.GetCaption().Contains("Minecraft") | this.GetCaption().Contains("Forge");
					if (flag2)
					{
						bool flag3 = this.chkPlaySound.Checked & !this.newSound;
						if (flag3)
						{
							MyProject.Computer.Audio.Play(Resources.clicks, AudioPlayMode.Background);
						}
						else
						{
							bool flag4 = this.chkPlaySound.Checked & this.newSound;
							if (flag4)
							{
								MyProject.Computer.Audio.Play(this.soundToPlay, AudioPlayMode.Background);
							}
						}
						Debug.WriteLine("Real Down Detected");
						this.exitSub = false;
						this.tmrRandomize.Start();
						this.tmrMouseDown.Start();
						DateTime now = DateAndTime.Now;
						while (DateAndTime.Now.Subtract(now).Milliseconds < this.r.Next(30, 50))
						{
						}
						this.isSimulatingClick = true;
						frmAutoclicker.mouse_event(4, 0, 0, 0, 0);
						this.isSimulatingClick = false;
						this.tmrMouseUp.Start();
					}
				}
				else
				{
					bool flag5 = this.chkPlaySound.Checked & !this.newSound;
					if (flag5)
					{
						MyProject.Computer.Audio.Play(Resources.clicks, AudioPlayMode.Background);
					}
					else
					{
						bool flag6 = this.chkPlaySound.Checked & this.newSound;
						if (flag6)
						{
							MyProject.Computer.Audio.Play(this.soundToPlay, AudioPlayMode.Background);
						}
					}
					Debug.WriteLine("Real Down Detected");
					this.exitSub = false;
					this.tmrRandomize.Start();
					this.tmrMouseDown.Start();
					DateTime now2 = DateAndTime.Now;
					while (DateAndTime.Now.Subtract(now2).Milliseconds < this.r.Next(30, 50))
					{
					}
					this.tmrMouseUp.Start();
				}
			}
		}

		// Token: 0x0600000E RID: 14 RVA: 0x000026AC File Offset: 0x000008AC
		private void mh_lu()
		{
			bool flag = !this.isSimulatingClick;
			if (flag)
			{
				Debug.WriteLine("Real Up Detected");
				this.exitSub = true;
				MyProject.Computer.Audio.Stop();
			}
		}

		// Token: 0x0600000F RID: 15 RVA: 0x000026EC File Offset: 0x000008EC
		private void btnChangeAudio_Click(object sender, EventArgs e)
		{
			this.fd = new OpenFileDialog();
			this.fd.Title = "Open File Dialog";
			this.fd.InitialDirectory = "C:\\";
			this.fd.Filter = "WAV files | *.wav";
			this.fd.FilterIndex = 2;
			this.fd.RestoreDirectory = true;
			bool flag = this.fd.ShowDialog() == DialogResult.OK;
			if (flag)
			{
				this.newSound = true;
				this.soundToPlay = this.fd.FileName.ToString();
			}
			else
			{
				this.newSound = false;
			}
		}

		// Token: 0x17000002 RID: 2
		// (get) Token: 0x06000012 RID: 18 RVA: 0x000035A9 File Offset: 0x000017A9
		// (set) Token: 0x06000013 RID: 19 RVA: 0x000035B4 File Offset: 0x000017B4
		internal virtual gTrackBar trbCPS
		{
			[CompilerGenerated]
			get
			{
				return this._trbCPS;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				gTrackBar.ValueChangedEventHandler valueChangedEventHandler = new gTrackBar.ValueChangedEventHandler(this.trbCPS_ValueChanged);
				gTrackBar trbCPS = this._trbCPS;
				if (trbCPS != null)
				{
					trbCPS.ValueChanged -= valueChangedEventHandler;
				}
				this._trbCPS = value;
				trbCPS = this._trbCPS;
				if (trbCPS != null)
				{
					trbCPS.ValueChanged += valueChangedEventHandler;
				}
			}
		}

		// Token: 0x17000003 RID: 3
		// (get) Token: 0x06000014 RID: 20 RVA: 0x000035F7 File Offset: 0x000017F7
		// (set) Token: 0x06000015 RID: 21 RVA: 0x00003601 File Offset: 0x00001801
		internal virtual Label lblCPS { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000004 RID: 4
		// (get) Token: 0x06000016 RID: 22 RVA: 0x0000360A File Offset: 0x0000180A
		// (set) Token: 0x06000017 RID: 23 RVA: 0x00003614 File Offset: 0x00001814
		internal virtual Label lblRand { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000005 RID: 5
		// (get) Token: 0x06000018 RID: 24 RVA: 0x0000361D File Offset: 0x0000181D
		// (set) Token: 0x06000019 RID: 25 RVA: 0x00003628 File Offset: 0x00001828
		internal virtual gTrackBar trbRandomization
		{
			[CompilerGenerated]
			get
			{
				return this._trbRandomization;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				gTrackBar.ValueChangedEventHandler valueChangedEventHandler = new gTrackBar.ValueChangedEventHandler(this.trbRandomization_ValueChanged);
				gTrackBar trbRandomization = this._trbRandomization;
				if (trbRandomization != null)
				{
					trbRandomization.ValueChanged -= valueChangedEventHandler;
				}
				this._trbRandomization = value;
				trbRandomization = this._trbRandomization;
				if (trbRandomization != null)
				{
					trbRandomization.ValueChanged += valueChangedEventHandler;
				}
			}
		}

		// Token: 0x17000006 RID: 6
		// (get) Token: 0x0600001A RID: 26 RVA: 0x0000366B File Offset: 0x0000186B
		// (set) Token: 0x0600001B RID: 27 RVA: 0x00003675 File Offset: 0x00001875
		internal virtual GroupBox grpOptions { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000007 RID: 7
		// (get) Token: 0x0600001C RID: 28 RVA: 0x0000367E File Offset: 0x0000187E
		// (set) Token: 0x0600001D RID: 29 RVA: 0x00003688 File Offset: 0x00001888
		internal virtual Timer tmrMouseDown
		{
			[CompilerGenerated]
			get
			{
				return this._tmrMouseDown;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.tmrMouseDown_Tick);
				Timer tmrMouseDown = this._tmrMouseDown;
				if (tmrMouseDown != null)
				{
					tmrMouseDown.Tick -= value2;
				}
				this._tmrMouseDown = value;
				tmrMouseDown = this._tmrMouseDown;
				if (tmrMouseDown != null)
				{
					tmrMouseDown.Tick += value2;
				}
			}
		}

		// Token: 0x17000008 RID: 8
		// (get) Token: 0x0600001E RID: 30 RVA: 0x000036CB File Offset: 0x000018CB
		// (set) Token: 0x0600001F RID: 31 RVA: 0x000036D8 File Offset: 0x000018D8
		internal virtual Timer tmrMouseUp
		{
			[CompilerGenerated]
			get
			{
				return this._tmrMouseUp;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.tmrMouseUp_Tick);
				Timer tmrMouseUp = this._tmrMouseUp;
				if (tmrMouseUp != null)
				{
					tmrMouseUp.Tick -= value2;
				}
				this._tmrMouseUp = value;
				tmrMouseUp = this._tmrMouseUp;
				if (tmrMouseUp != null)
				{
					tmrMouseUp.Tick += value2;
				}
			}
		}

		// Token: 0x17000009 RID: 9
		// (get) Token: 0x06000020 RID: 32 RVA: 0x0000371B File Offset: 0x0000191B
		// (set) Token: 0x06000021 RID: 33 RVA: 0x00003728 File Offset: 0x00001928
		internal virtual Timer tmrRandomize
		{
			[CompilerGenerated]
			get
			{
				return this._tmrRandomize;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.tmrRandomize_Tick);
				Timer tmrRandomize = this._tmrRandomize;
				if (tmrRandomize != null)
				{
					tmrRandomize.Tick -= value2;
				}
				this._tmrRandomize = value;
				tmrRandomize = this._tmrRandomize;
				if (tmrRandomize != null)
				{
					tmrRandomize.Tick += value2;
				}
			}
		}

		// Token: 0x1700000A RID: 10
		// (get) Token: 0x06000022 RID: 34 RVA: 0x0000376B File Offset: 0x0000196B
		// (set) Token: 0x06000023 RID: 35 RVA: 0x00003775 File Offset: 0x00001975
		internal virtual CheckBox chkOnlyClickOnMinecraft { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700000B RID: 11
		// (get) Token: 0x06000024 RID: 36 RVA: 0x0000377E File Offset: 0x0000197E
		// (set) Token: 0x06000025 RID: 37 RVA: 0x00003788 File Offset: 0x00001988
		internal virtual CheckBox chkPlaySound { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700000C RID: 12
		// (get) Token: 0x06000026 RID: 38 RVA: 0x00003791 File Offset: 0x00001991
		// (set) Token: 0x06000027 RID: 39 RVA: 0x0000379B File Offset: 0x0000199B
		internal virtual CheckBox chkRandomStops { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700000D RID: 13
		// (get) Token: 0x06000028 RID: 40 RVA: 0x000037A4 File Offset: 0x000019A4
		// (set) Token: 0x06000029 RID: 41 RVA: 0x000037AE File Offset: 0x000019AE
		internal virtual TextBox txtPixels { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700000E RID: 14
		// (get) Token: 0x0600002A RID: 42 RVA: 0x000037B7 File Offset: 0x000019B7
		// (set) Token: 0x0600002B RID: 43 RVA: 0x000037C1 File Offset: 0x000019C1
		internal virtual CheckBox chkJitter { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700000F RID: 15
		// (get) Token: 0x0600002C RID: 44 RVA: 0x000037CA File Offset: 0x000019CA
		// (set) Token: 0x0600002D RID: 45 RVA: 0x000037D4 File Offset: 0x000019D4
		internal virtual Button btnChangeAudio
		{
			[CompilerGenerated]
			get
			{
				return this._btnChangeAudio;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.btnChangeAudio_Click);
				Button btnChangeAudio = this._btnChangeAudio;
				if (btnChangeAudio != null)
				{
					btnChangeAudio.Click -= value2;
				}
				this._btnChangeAudio = value;
				btnChangeAudio = this._btnChangeAudio;
				if (btnChangeAudio != null)
				{
					btnChangeAudio.Click += value2;
				}
			}
		}

		// Token: 0x17000010 RID: 16
		// (get) Token: 0x0600002E RID: 46 RVA: 0x00003817 File Offset: 0x00001A17
		// (set) Token: 0x0600002F RID: 47 RVA: 0x00003821 File Offset: 0x00001A21
		internal virtual Label lblZekra { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x04000001 RID: 1
		private bool hasSimulatedUp;

		// Token: 0x04000002 RID: 2
		private bool hasSimulatedDown;

		// Token: 0x04000003 RID: 3
		private bool newSound;

		// Token: 0x04000004 RID: 4
		private string soundToPlay;

		// Token: 0x04000005 RID: 5
		private OpenFileDialog fd;

		// Token: 0x04000007 RID: 7
		private bool exitSub;

		// Token: 0x04000008 RID: 8
		private bool isSimulatingClick;

		// Token: 0x04000009 RID: 9
		public const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x0400000A RID: 10
		public const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x0400000B RID: 11
		private Random r;
	}
}
