using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using WindowsApplication1.My;

namespace WindowsApplication1
{
	// Token: 0x02000008 RID: 8
	[DesignerGenerated]
	public partial class Form1 : Form
	{
		// Token: 0x0600001F RID: 31 RVA: 0x0000EF2C File Offset: 0x0000D32C
		public Form1()
		{
			base.Load += this.Form1_Load;
			this.InitializeComponent();
		}

		// Token: 0x17000009 RID: 9
		// (get) Token: 0x06000022 RID: 34 RVA: 0x0000FFE8 File Offset: 0x0000E3E8
		// (set) Token: 0x06000023 RID: 35 RVA: 0x0000FFFC File Offset: 0x0000E3FC
		internal virtual GroupBox GroupBox1
		{
			get
			{
				return this._GroupBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._GroupBox1 = value;
			}
		}

		// Token: 0x1700000A RID: 10
		// (get) Token: 0x06000024 RID: 36 RVA: 0x00010008 File Offset: 0x0000E408
		// (set) Token: 0x06000025 RID: 37 RVA: 0x0001001C File Offset: 0x0000E41C
		internal virtual Label Label1
		{
			get
			{
				return this._Label1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Label1_Click);
				if (this._Label1 != null)
				{
					this._Label1.Click -= value2;
				}
				this._Label1 = value;
				if (this._Label1 != null)
				{
					this._Label1.Click += value2;
				}
			}
		}

		// Token: 0x1700000B RID: 11
		// (get) Token: 0x06000026 RID: 38 RVA: 0x00010068 File Offset: 0x0000E468
		// (set) Token: 0x06000027 RID: 39 RVA: 0x0001007C File Offset: 0x0000E47C
		internal virtual Button Button1
		{
			get
			{
				return this._Button1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button1_Click);
				if (this._Button1 != null)
				{
					this._Button1.Click -= value2;
				}
				this._Button1 = value;
				if (this._Button1 != null)
				{
					this._Button1.Click += value2;
				}
			}
		}

		// Token: 0x1700000C RID: 12
		// (get) Token: 0x06000028 RID: 40 RVA: 0x000100C8 File Offset: 0x0000E4C8
		// (set) Token: 0x06000029 RID: 41 RVA: 0x000100DC File Offset: 0x0000E4DC
		internal virtual Button Button2
		{
			get
			{
				return this._Button2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button2_Click);
				if (this._Button2 != null)
				{
					this._Button2.Click -= value2;
				}
				this._Button2 = value;
				if (this._Button2 != null)
				{
					this._Button2.Click += value2;
				}
			}
		}

		// Token: 0x1700000D RID: 13
		// (get) Token: 0x0600002A RID: 42 RVA: 0x00010128 File Offset: 0x0000E528
		// (set) Token: 0x0600002B RID: 43 RVA: 0x0001013C File Offset: 0x0000E53C
		internal virtual TrackBar TrackBar1
		{
			get
			{
				return this._TrackBar1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TrackBar1_Scroll);
				if (this._TrackBar1 != null)
				{
					this._TrackBar1.Scroll -= value2;
				}
				this._TrackBar1 = value;
				if (this._TrackBar1 != null)
				{
					this._TrackBar1.Scroll += value2;
				}
			}
		}

		// Token: 0x1700000E RID: 14
		// (get) Token: 0x0600002C RID: 44 RVA: 0x00010188 File Offset: 0x0000E588
		// (set) Token: 0x0600002D RID: 45 RVA: 0x0001019C File Offset: 0x0000E59C
		internal virtual GroupBox GroupBox2
		{
			get
			{
				return this._GroupBox2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.GroupBox2_Enter);
				if (this._GroupBox2 != null)
				{
					this._GroupBox2.Enter -= value2;
				}
				this._GroupBox2 = value;
				if (this._GroupBox2 != null)
				{
					this._GroupBox2.Enter += value2;
				}
			}
		}

		// Token: 0x1700000F RID: 15
		// (get) Token: 0x0600002E RID: 46 RVA: 0x000101E8 File Offset: 0x0000E5E8
		// (set) Token: 0x0600002F RID: 47 RVA: 0x000101FC File Offset: 0x0000E5FC
		internal virtual Label Label3
		{
			get
			{
				return this._Label3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label3 = value;
			}
		}

		// Token: 0x17000010 RID: 16
		// (get) Token: 0x06000030 RID: 48 RVA: 0x00010208 File Offset: 0x0000E608
		// (set) Token: 0x06000031 RID: 49 RVA: 0x0001021C File Offset: 0x0000E61C
		internal virtual ComboBox ComboBox1
		{
			get
			{
				return this._ComboBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ComboBox1_SelectedIndexChanged);
				if (this._ComboBox1 != null)
				{
					this._ComboBox1.SelectedIndexChanged -= value2;
				}
				this._ComboBox1 = value;
				if (this._ComboBox1 != null)
				{
					this._ComboBox1.SelectedIndexChanged += value2;
				}
			}
		}

		// Token: 0x17000011 RID: 17
		// (get) Token: 0x06000032 RID: 50 RVA: 0x00010268 File Offset: 0x0000E668
		// (set) Token: 0x06000033 RID: 51 RVA: 0x0001027C File Offset: 0x0000E67C
		internal virtual Label Label2
		{
			get
			{
				return this._Label2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label2 = value;
			}
		}

		// Token: 0x17000012 RID: 18
		// (get) Token: 0x06000034 RID: 52 RVA: 0x00010288 File Offset: 0x0000E688
		// (set) Token: 0x06000035 RID: 53 RVA: 0x0001029C File Offset: 0x0000E69C
		internal virtual GroupBox GroupBox3
		{
			get
			{
				return this._GroupBox3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._GroupBox3 = value;
			}
		}

		// Token: 0x17000013 RID: 19
		// (get) Token: 0x06000036 RID: 54 RVA: 0x000102A8 File Offset: 0x0000E6A8
		// (set) Token: 0x06000037 RID: 55 RVA: 0x000102BC File Offset: 0x0000E6BC
		internal virtual Label Label4
		{
			get
			{
				return this._Label4;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label4 = value;
			}
		}

		// Token: 0x17000014 RID: 20
		// (get) Token: 0x06000038 RID: 56 RVA: 0x000102C8 File Offset: 0x0000E6C8
		// (set) Token: 0x06000039 RID: 57 RVA: 0x000102DC File Offset: 0x0000E6DC
		internal virtual TrackBar TrackBar2
		{
			get
			{
				return this._TrackBar2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TrackBar2_Scroll);
				if (this._TrackBar2 != null)
				{
					this._TrackBar2.Scroll -= value2;
				}
				this._TrackBar2 = value;
				if (this._TrackBar2 != null)
				{
					this._TrackBar2.Scroll += value2;
				}
			}
		}

		// Token: 0x17000015 RID: 21
		// (get) Token: 0x0600003A RID: 58 RVA: 0x00010328 File Offset: 0x0000E728
		// (set) Token: 0x0600003B RID: 59 RVA: 0x0001033C File Offset: 0x0000E73C
		internal virtual CheckBox CheckBox1
		{
			get
			{
				return this._CheckBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.CheckBox1_CheckedChanged);
				if (this._CheckBox1 != null)
				{
					this._CheckBox1.CheckedChanged -= value2;
				}
				this._CheckBox1 = value;
				if (this._CheckBox1 != null)
				{
					this._CheckBox1.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x17000016 RID: 22
		// (get) Token: 0x0600003C RID: 60 RVA: 0x00010388 File Offset: 0x0000E788
		// (set) Token: 0x0600003D RID: 61 RVA: 0x0001039C File Offset: 0x0000E79C
		internal virtual Button Button3
		{
			get
			{
				return this._Button3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button3_Click);
				if (this._Button3 != null)
				{
					this._Button3.Click -= value2;
				}
				this._Button3 = value;
				if (this._Button3 != null)
				{
					this._Button3.Click += value2;
				}
			}
		}

		// Token: 0x17000017 RID: 23
		// (get) Token: 0x0600003E RID: 62 RVA: 0x000103E8 File Offset: 0x0000E7E8
		// (set) Token: 0x0600003F RID: 63 RVA: 0x000103FC File Offset: 0x0000E7FC
		internal virtual Button Button4
		{
			get
			{
				return this._Button4;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button4_Click);
				if (this._Button4 != null)
				{
					this._Button4.Click -= value2;
				}
				this._Button4 = value;
				if (this._Button4 != null)
				{
					this._Button4.Click += value2;
				}
			}
		}

		// Token: 0x17000018 RID: 24
		// (get) Token: 0x06000040 RID: 64 RVA: 0x00010448 File Offset: 0x0000E848
		// (set) Token: 0x06000041 RID: 65 RVA: 0x0001045C File Offset: 0x0000E85C
		internal virtual ProgressBar ProgressBar1
		{
			get
			{
				return this._ProgressBar1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ProgressBar1_Click);
				if (this._ProgressBar1 != null)
				{
					this._ProgressBar1.Click -= value2;
				}
				this._ProgressBar1 = value;
				if (this._ProgressBar1 != null)
				{
					this._ProgressBar1.Click += value2;
				}
			}
		}

		// Token: 0x17000019 RID: 25
		// (get) Token: 0x06000042 RID: 66 RVA: 0x000104A8 File Offset: 0x0000E8A8
		// (set) Token: 0x06000043 RID: 67 RVA: 0x000104BC File Offset: 0x0000E8BC
		internal virtual Timer Test
		{
			get
			{
				return this._Test;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Test_Tick);
				if (this._Test != null)
				{
					this._Test.Tick -= value2;
				}
				this._Test = value;
				if (this._Test != null)
				{
					this._Test.Tick += value2;
				}
			}
		}

		// Token: 0x1700001A RID: 26
		// (get) Token: 0x06000044 RID: 68 RVA: 0x00010508 File Offset: 0x0000E908
		// (set) Token: 0x06000045 RID: 69 RVA: 0x0001051C File Offset: 0x0000E91C
		internal virtual Timer Generateur
		{
			get
			{
				return this._Generateur;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Generateur_Tick);
				if (this._Generateur != null)
				{
					this._Generateur.Tick -= value2;
				}
				this._Generateur = value;
				if (this._Generateur != null)
				{
					this._Generateur.Tick += value2;
				}
			}
		}

		// Token: 0x1700001B RID: 27
		// (get) Token: 0x06000046 RID: 70 RVA: 0x00010568 File Offset: 0x0000E968
		// (set) Token: 0x06000047 RID: 71 RVA: 0x0001057C File Offset: 0x0000E97C
		internal virtual Button Button5
		{
			get
			{
				return this._Button5;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button5_Click);
				if (this._Button5 != null)
				{
					this._Button5.Click -= value2;
				}
				this._Button5 = value;
				if (this._Button5 != null)
				{
					this._Button5.Click += value2;
				}
			}
		}

		// Token: 0x1700001C RID: 28
		// (get) Token: 0x06000048 RID: 72 RVA: 0x000105C8 File Offset: 0x0000E9C8
		// (set) Token: 0x06000049 RID: 73 RVA: 0x000105DC File Offset: 0x0000E9DC
		internal virtual Button Button6
		{
			get
			{
				return this._Button6;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button6_Click);
				if (this._Button6 != null)
				{
					this._Button6.Click -= value2;
				}
				this._Button6 = value;
				if (this._Button6 != null)
				{
					this._Button6.Click += value2;
				}
			}
		}

		// Token: 0x1700001D RID: 29
		// (get) Token: 0x0600004A RID: 74 RVA: 0x00010628 File Offset: 0x0000EA28
		// (set) Token: 0x0600004B RID: 75 RVA: 0x0001063C File Offset: 0x0000EA3C
		internal virtual GroupBox GroupBox4
		{
			get
			{
				return this._GroupBox4;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._GroupBox4 = value;
			}
		}

		// Token: 0x1700001E RID: 30
		// (get) Token: 0x0600004C RID: 76 RVA: 0x00010648 File Offset: 0x0000EA48
		// (set) Token: 0x0600004D RID: 77 RVA: 0x0001065C File Offset: 0x0000EA5C
		internal virtual LinkLabel LinkLabel1
		{
			get
			{
				return this._LinkLabel1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				LinkLabelLinkClickedEventHandler value2 = new LinkLabelLinkClickedEventHandler(this.LinkLabel1_LinkClicked);
				if (this._LinkLabel1 != null)
				{
					this._LinkLabel1.LinkClicked -= value2;
				}
				this._LinkLabel1 = value;
				if (this._LinkLabel1 != null)
				{
					this._LinkLabel1.LinkClicked += value2;
				}
			}
		}

		// Token: 0x1700001F RID: 31
		// (get) Token: 0x0600004E RID: 78 RVA: 0x000106A8 File Offset: 0x0000EAA8
		// (set) Token: 0x0600004F RID: 79 RVA: 0x000106BC File Offset: 0x0000EABC
		internal virtual Label Label5
		{
			get
			{
				return this._Label5;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label5 = value;
			}
		}

		// Token: 0x17000020 RID: 32
		// (get) Token: 0x06000050 RID: 80 RVA: 0x000106C8 File Offset: 0x0000EAC8
		// (set) Token: 0x06000051 RID: 81 RVA: 0x000106DC File Offset: 0x0000EADC
		internal virtual LinkLabel LinkLabel2
		{
			get
			{
				return this._LinkLabel2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				LinkLabelLinkClickedEventHandler value2 = new LinkLabelLinkClickedEventHandler(this.LinkLabel2_LinkClicked);
				if (this._LinkLabel2 != null)
				{
					this._LinkLabel2.LinkClicked -= value2;
				}
				this._LinkLabel2 = value;
				if (this._LinkLabel2 != null)
				{
					this._LinkLabel2.LinkClicked += value2;
				}
			}
		}

		// Token: 0x17000021 RID: 33
		// (get) Token: 0x06000052 RID: 82 RVA: 0x00010728 File Offset: 0x0000EB28
		// (set) Token: 0x06000053 RID: 83 RVA: 0x0001073C File Offset: 0x0000EB3C
		internal virtual TextBox TextBox1
		{
			get
			{
				return this._TextBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TextBox1_TextChanged);
				if (this._TextBox1 != null)
				{
					this._TextBox1.TextChanged -= value2;
				}
				this._TextBox1 = value;
				if (this._TextBox1 != null)
				{
					this._TextBox1.TextChanged += value2;
				}
			}
		}

		// Token: 0x17000022 RID: 34
		// (get) Token: 0x06000054 RID: 84 RVA: 0x00010788 File Offset: 0x0000EB88
		// (set) Token: 0x06000055 RID: 85 RVA: 0x0001079C File Offset: 0x0000EB9C
		internal virtual Label Label6
		{
			get
			{
				return this._Label6;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label6 = value;
			}
		}

		// Token: 0x17000023 RID: 35
		// (get) Token: 0x06000056 RID: 86 RVA: 0x000107A8 File Offset: 0x0000EBA8
		// (set) Token: 0x06000057 RID: 87 RVA: 0x000107BC File Offset: 0x0000EBBC
		internal virtual GroupBox GroupBox5
		{
			get
			{
				return this._GroupBox5;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._GroupBox5 = value;
			}
		}

		// Token: 0x17000024 RID: 36
		// (get) Token: 0x06000058 RID: 88 RVA: 0x000107C8 File Offset: 0x0000EBC8
		// (set) Token: 0x06000059 RID: 89 RVA: 0x000107DC File Offset: 0x0000EBDC
		internal virtual PictureBox PictureBox1
		{
			get
			{
				return this._PictureBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._PictureBox1 = value;
			}
		}

		// Token: 0x0600005A RID: 90
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

		// Token: 0x0600005B RID: 91 RVA: 0x000107E8 File Offset: 0x0000EBE8
		private void CheckBox1_CheckedChanged(object sender, EventArgs e)
		{
			this.TopMost = this.CheckBox1.Checked;
		}

		// Token: 0x0600005C RID: 92 RVA: 0x000107FC File Offset: 0x0000EBFC
		private void TrackBar2_Scroll(object sender, EventArgs e)
		{
			this.Opacity = (double)this.TrackBar2.Value / 100.0;
			this.Label6.Text = Conversions.ToString(this.TrackBar2.Value) + "%";
		}

		// Token: 0x0600005D RID: 93 RVA: 0x0001084C File Offset: 0x0000EC4C
		private void TrackBar1_Scroll(object sender, EventArgs e)
		{
			this.Label1.Text = "Un clic tout les " + Conversions.ToString(this.TrackBar1.Value) + " millisecondes";
			this.TextBox1.Text = Conversions.ToString(this.TrackBar1.Value);
		}

		// Token: 0x0600005E RID: 94 RVA: 0x000108A0 File Offset: 0x0000ECA0
		private void Button1_Click(object sender, EventArgs e)
		{
			this.TrackBar1.Value = checked(this.TrackBar1.Value - 1);
			this.Label1.Text = "Un clic tout les " + Conversions.ToString(this.TrackBar1.Value) + " millisecondes";
		}

		// Token: 0x0600005F RID: 95 RVA: 0x000108F0 File Offset: 0x0000ECF0
		private void Button2_Click(object sender, EventArgs e)
		{
			this.TrackBar1.Value = checked(this.TrackBar1.Value + 1);
			this.Label1.Text = "Un clic tout les " + Conversions.ToString(this.TrackBar1.Value) + " millisecondes";
		}

		// Token: 0x06000060 RID: 96 RVA: 0x00010940 File Offset: 0x0000ED40
		private void ComboBox1_SelectedIndexChanged(object sender, EventArgs e)
		{
			this.Touche = this.ComboBox1.Text;
			this.Label3.Text = "Appuyez pour tester, ...";
			this.Label3.ForeColor = Color.Blue;
			this.Button3.Enabled = false;
			this.Test.Start();
		}

		// Token: 0x06000061 RID: 97 RVA: 0x00010998 File Offset: 0x0000ED98
		private void Test_Tick(object sender, EventArgs e)
		{
			object touche = this.Touche;
			if (Operators.ConditionalCompareObjectEqual(touche, "CTRL", false))
			{
				if (!MyProject.Computer.Keyboard.CtrlKeyDown)
				{
					return;
				}
			}
			else if (Operators.ConditionalCompareObjectEqual(touche, "ALT", false))
			{
				if (!MyProject.Computer.Keyboard.AltKeyDown)
				{
					return;
				}
			}
			else if (Operators.ConditionalCompareObjectEqual(touche, "SHIFT", false))
			{
				if (!MyProject.Computer.Keyboard.ShiftKeyDown)
				{
					return;
				}
			}
			else if (Operators.ConditionalCompareObjectEqual(touche, "NUM LOCK", false))
			{
				if (!MyProject.Computer.Keyboard.NumLock)
				{
					return;
				}
			}
			else if (!Operators.ConditionalCompareObjectEqual(touche, "CAPS LOCK", false) || !MyProject.Computer.Keyboard.CapsLock)
			{
				return;
			}
			this.Label3.Text = "Bravo, tu as Réussi !";
			this.Label3.ForeColor = Color.Lime;
			this.ComboBox1.Enabled = true;
			this.Button3.Enabled = true;
		}

		// Token: 0x06000062 RID: 98 RVA: 0x00010A90 File Offset: 0x0000EE90
		private void Button3_Click(object sender, EventArgs e)
		{
			this.Generateur.Interval = this.TrackBar1.Value;
			this.Generateur.Start();
			this.Button3.Visible = true;
			this.Button4.Visible = true;
			this.Button4.Enabled = true;
			this.ProgressBar1.Visible = true;
			this.GroupBox1.Enabled = false;
			this.GroupBox2.Enabled = false;
			this.GroupBox3.Enabled = false;
			this.Button3.Enabled = false;
		}

		// Token: 0x06000063 RID: 99 RVA: 0x00010B20 File Offset: 0x0000EF20
		private void Generateur_Tick(object sender, EventArgs e)
		{
			object touche = this.Touche;
			if (Operators.ConditionalCompareObjectEqual(touche, "CTRL", false))
			{
				if (MyProject.Computer.Keyboard.CtrlKeyDown)
				{
					Form1.mouse_event(2, 0, 0, 0, 0);
					Form1.mouse_event(4, 0, 0, 0, 0);
				}
			}
			else if (Operators.ConditionalCompareObjectEqual(touche, "ALT", false))
			{
				if (MyProject.Computer.Keyboard.AltKeyDown)
				{
					Form1.mouse_event(2, 0, 0, 0, 0);
					Form1.mouse_event(4, 0, 0, 0, 0);
				}
			}
			else if (Operators.ConditionalCompareObjectEqual(touche, "SHIFT", false))
			{
				if (MyProject.Computer.Keyboard.ShiftKeyDown)
				{
					Form1.mouse_event(2, 0, 0, 0, 0);
					Form1.mouse_event(4, 0, 0, 0, 0);
				}
			}
			else if (Operators.ConditionalCompareObjectEqual(touche, "NUM LOCK", false))
			{
				if (MyProject.Computer.Keyboard.NumLock)
				{
					Form1.mouse_event(2, 0, 0, 0, 0);
					Form1.mouse_event(4, 0, 0, 0, 0);
				}
			}
			else if (Operators.ConditionalCompareObjectEqual(touche, "CAPS LOCK", false) && MyProject.Computer.Keyboard.CapsLock)
			{
				Form1.mouse_event(2, 0, 0, 0, 0);
				Form1.mouse_event(4, 0, 0, 0, 0);
			}
		}

		// Token: 0x06000064 RID: 100 RVA: 0x00010C48 File Offset: 0x0000F048
		private void Button4_Click(object sender, EventArgs e)
		{
			this.Generateur.Stop();
			this.Button3.Visible = true;
			this.Button4.Visible = true;
			this.ProgressBar1.Visible = false;
			this.Button3.Enabled = true;
			this.GroupBox1.Enabled = true;
			this.GroupBox2.Enabled = true;
			this.GroupBox3.Enabled = true;
			this.Button4.Enabled = false;
		}

		// Token: 0x06000065 RID: 101 RVA: 0x00010CC0 File Offset: 0x0000F0C0
		private void Button5_Click(object sender, EventArgs e)
		{
			try
			{
				ColorDialog colorDialog = new ColorDialog();
				colorDialog.Color = this.BackColor;
				if (colorDialog.ShowDialog() == DialogResult.OK)
				{
					this.BackColor = colorDialog.Color;
				}
			}
			catch (Exception ex)
			{
			}
		}

		// Token: 0x06000066 RID: 102 RVA: 0x00010D14 File Offset: 0x0000F114
		private void Button6_Click(object sender, EventArgs e)
		{
			this.BackColor = Color.WhiteSmoke;
		}

		// Token: 0x06000067 RID: 103 RVA: 0x00010D2C File Offset: 0x0000F12C
		private void Label1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000068 RID: 104 RVA: 0x00010D30 File Offset: 0x0000F130
		private void LinkLabel2_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
		{
			Process.Start("https://www.paypal.com/fr/cgi-bin/webscr?cmd=_flow&SESSION=LGDL_x6Pbnhh9KIBZozol1pQcorTMEzp4yZBOPrm_-QnUbJ_yiYINmCZhgC&dispatch=5885d80a13c0db1f8e263663d3faee8d4e181b3aff599f99a338772351021e7d");
		}

		// Token: 0x06000069 RID: 105 RVA: 0x00010D40 File Offset: 0x0000F140
		private void TextBox1_TextChanged(object sender, EventArgs e)
		{
			try
			{
				this.Label1.Text = "Un clic tout les " + this.TextBox1.Text + " millisecondes";
				this.TrackBar1.Value = Conversions.ToInteger(this.TextBox1.Text);
			}
			catch (Exception ex)
			{
				Interaction.MsgBox("Erreur ! Veuillez mettre un chiffre entre 1 et 10000", MsgBoxStyle.Critical, "Erreur");
			}
			if (Operators.CompareString(this.TextBox1.Text, "0", false) == 0 | Operators.CompareString(this.TextBox1.Text, "", false) == 0)
			{
				this.TextBox1.Text = "1";
			}
		}

		// Token: 0x0600006A RID: 106 RVA: 0x00010E04 File Offset: 0x0000F204
		private void GroupBox2_Enter(object sender, EventArgs e)
		{
		}

		// Token: 0x0600006B RID: 107 RVA: 0x00010E08 File Offset: 0x0000F208
		private void LinkLabel1_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
		{
			MyProject.Forms.Form2.Show();
		}

		// Token: 0x0600006C RID: 108 RVA: 0x00010E1C File Offset: 0x0000F21C
		private void Form1_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x0600006D RID: 109 RVA: 0x00010E20 File Offset: 0x0000F220
		private void ProgressBar1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0400000B RID: 11
		[AccessedThroughProperty("GroupBox1")]
		private GroupBox _GroupBox1;

		// Token: 0x0400000C RID: 12
		[AccessedThroughProperty("Label1")]
		private Label _Label1;

		// Token: 0x0400000D RID: 13
		[AccessedThroughProperty("Button1")]
		private Button _Button1;

		// Token: 0x0400000E RID: 14
		[AccessedThroughProperty("Button2")]
		private Button _Button2;

		// Token: 0x0400000F RID: 15
		[AccessedThroughProperty("TrackBar1")]
		private TrackBar _TrackBar1;

		// Token: 0x04000010 RID: 16
		[AccessedThroughProperty("GroupBox2")]
		private GroupBox _GroupBox2;

		// Token: 0x04000011 RID: 17
		[AccessedThroughProperty("Label3")]
		private Label _Label3;

		// Token: 0x04000012 RID: 18
		[AccessedThroughProperty("ComboBox1")]
		private ComboBox _ComboBox1;

		// Token: 0x04000013 RID: 19
		[AccessedThroughProperty("Label2")]
		private Label _Label2;

		// Token: 0x04000014 RID: 20
		[AccessedThroughProperty("GroupBox3")]
		private GroupBox _GroupBox3;

		// Token: 0x04000015 RID: 21
		[AccessedThroughProperty("Label4")]
		private Label _Label4;

		// Token: 0x04000016 RID: 22
		[AccessedThroughProperty("TrackBar2")]
		private TrackBar _TrackBar2;

		// Token: 0x04000017 RID: 23
		[AccessedThroughProperty("CheckBox1")]
		private CheckBox _CheckBox1;

		// Token: 0x04000018 RID: 24
		[AccessedThroughProperty("Button3")]
		private Button _Button3;

		// Token: 0x04000019 RID: 25
		[AccessedThroughProperty("Button4")]
		private Button _Button4;

		// Token: 0x0400001A RID: 26
		[AccessedThroughProperty("ProgressBar1")]
		private ProgressBar _ProgressBar1;

		// Token: 0x0400001B RID: 27
		[AccessedThroughProperty("Test")]
		private Timer _Test;

		// Token: 0x0400001C RID: 28
		[AccessedThroughProperty("Generateur")]
		private Timer _Generateur;

		// Token: 0x0400001D RID: 29
		[AccessedThroughProperty("Button5")]
		private Button _Button5;

		// Token: 0x0400001E RID: 30
		[AccessedThroughProperty("Button6")]
		private Button _Button6;

		// Token: 0x0400001F RID: 31
		[AccessedThroughProperty("GroupBox4")]
		private GroupBox _GroupBox4;

		// Token: 0x04000020 RID: 32
		[AccessedThroughProperty("LinkLabel1")]
		private LinkLabel _LinkLabel1;

		// Token: 0x04000021 RID: 33
		[AccessedThroughProperty("Label5")]
		private Label _Label5;

		// Token: 0x04000022 RID: 34
		[AccessedThroughProperty("LinkLabel2")]
		private LinkLabel _LinkLabel2;

		// Token: 0x04000023 RID: 35
		[AccessedThroughProperty("TextBox1")]
		private TextBox _TextBox1;

		// Token: 0x04000024 RID: 36
		[AccessedThroughProperty("Label6")]
		private Label _Label6;

		// Token: 0x04000025 RID: 37
		[AccessedThroughProperty("GroupBox5")]
		private GroupBox _GroupBox5;

		// Token: 0x04000026 RID: 38
		[AccessedThroughProperty("PictureBox1")]
		private PictureBox _PictureBox1;

		// Token: 0x04000027 RID: 39
		public const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x04000028 RID: 40
		public const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x04000029 RID: 41
		private object Touche;
	}
}
