using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using WindowsApplication1.My.Resources;

namespace WindowsApplication1
{
	// Token: 0x02000009 RID: 9
	[DesignerGenerated]
	public partial class Form2 : Form
	{
		// Token: 0x0600006E RID: 110 RVA: 0x00010E24 File Offset: 0x0000F224
		public Form2()
		{
			this.InitializeComponent();
		}

		// Token: 0x17000025 RID: 37
		// (get) Token: 0x06000071 RID: 113 RVA: 0x00011A40 File Offset: 0x0000FE40
		// (set) Token: 0x06000072 RID: 114 RVA: 0x00011A54 File Offset: 0x0000FE54
		internal virtual TabPage TabPage3
		{
			get
			{
				return this._TabPage3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._TabPage3 = value;
			}
		}

		// Token: 0x17000026 RID: 38
		// (get) Token: 0x06000073 RID: 115 RVA: 0x00011A60 File Offset: 0x0000FE60
		// (set) Token: 0x06000074 RID: 116 RVA: 0x00011A74 File Offset: 0x0000FE74
		internal virtual Label Label11
		{
			get
			{
				return this._Label11;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Label11_Click);
				if (this._Label11 != null)
				{
					this._Label11.Click -= value2;
				}
				this._Label11 = value;
				if (this._Label11 != null)
				{
					this._Label11.Click += value2;
				}
			}
		}

		// Token: 0x17000027 RID: 39
		// (get) Token: 0x06000075 RID: 117 RVA: 0x00011AC0 File Offset: 0x0000FEC0
		// (set) Token: 0x06000076 RID: 118 RVA: 0x00011AD4 File Offset: 0x0000FED4
		internal virtual TabPage TabPage2
		{
			get
			{
				return this._TabPage2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._TabPage2 = value;
			}
		}

		// Token: 0x17000028 RID: 40
		// (get) Token: 0x06000077 RID: 119 RVA: 0x00011AE0 File Offset: 0x0000FEE0
		// (set) Token: 0x06000078 RID: 120 RVA: 0x00011AF4 File Offset: 0x0000FEF4
		internal virtual LinkLabel LinkLabel3
		{
			get
			{
				return this._LinkLabel3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				LinkLabelLinkClickedEventHandler value2 = new LinkLabelLinkClickedEventHandler(this.LinkLabel3_LinkClicked);
				if (this._LinkLabel3 != null)
				{
					this._LinkLabel3.LinkClicked -= value2;
				}
				this._LinkLabel3 = value;
				if (this._LinkLabel3 != null)
				{
					this._LinkLabel3.LinkClicked += value2;
				}
			}
		}

		// Token: 0x17000029 RID: 41
		// (get) Token: 0x06000079 RID: 121 RVA: 0x00011B40 File Offset: 0x0000FF40
		// (set) Token: 0x0600007A RID: 122 RVA: 0x00011B54 File Offset: 0x0000FF54
		internal virtual Label Label10
		{
			get
			{
				return this._Label10;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label10 = value;
			}
		}

		// Token: 0x1700002A RID: 42
		// (get) Token: 0x0600007B RID: 123 RVA: 0x00011B60 File Offset: 0x0000FF60
		// (set) Token: 0x0600007C RID: 124 RVA: 0x00011B74 File Offset: 0x0000FF74
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

		// Token: 0x1700002B RID: 43
		// (get) Token: 0x0600007D RID: 125 RVA: 0x00011BC0 File Offset: 0x0000FFC0
		// (set) Token: 0x0600007E RID: 126 RVA: 0x00011BD4 File Offset: 0x0000FFD4
		internal virtual Label Label9
		{
			get
			{
				return this._Label9;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label9 = value;
			}
		}

		// Token: 0x1700002C RID: 44
		// (get) Token: 0x0600007F RID: 127 RVA: 0x00011BE0 File Offset: 0x0000FFE0
		// (set) Token: 0x06000080 RID: 128 RVA: 0x00011BF4 File Offset: 0x0000FFF4
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

		// Token: 0x1700002D RID: 45
		// (get) Token: 0x06000081 RID: 129 RVA: 0x00011C40 File Offset: 0x00010040
		// (set) Token: 0x06000082 RID: 130 RVA: 0x00011C54 File Offset: 0x00010054
		internal virtual Label Label8
		{
			get
			{
				return this._Label8;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label8 = value;
			}
		}

		// Token: 0x1700002E RID: 46
		// (get) Token: 0x06000083 RID: 131 RVA: 0x00011C60 File Offset: 0x00010060
		// (set) Token: 0x06000084 RID: 132 RVA: 0x00011C74 File Offset: 0x00010074
		internal virtual TabPage TabPage1
		{
			get
			{
				return this._TabPage1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._TabPage1 = value;
			}
		}

		// Token: 0x1700002F RID: 47
		// (get) Token: 0x06000085 RID: 133 RVA: 0x00011C80 File Offset: 0x00010080
		// (set) Token: 0x06000086 RID: 134 RVA: 0x00011C94 File Offset: 0x00010094
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

		// Token: 0x17000030 RID: 48
		// (get) Token: 0x06000087 RID: 135 RVA: 0x00011CA0 File Offset: 0x000100A0
		// (set) Token: 0x06000088 RID: 136 RVA: 0x00011CB4 File Offset: 0x000100B4
		internal virtual Label Label7
		{
			get
			{
				return this._Label7;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label7 = value;
			}
		}

		// Token: 0x17000031 RID: 49
		// (get) Token: 0x06000089 RID: 137 RVA: 0x00011CC0 File Offset: 0x000100C0
		// (set) Token: 0x0600008A RID: 138 RVA: 0x00011CD4 File Offset: 0x000100D4
		internal virtual Label Label1
		{
			get
			{
				return this._Label1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label1 = value;
			}
		}

		// Token: 0x17000032 RID: 50
		// (get) Token: 0x0600008B RID: 139 RVA: 0x00011CE0 File Offset: 0x000100E0
		// (set) Token: 0x0600008C RID: 140 RVA: 0x00011CF4 File Offset: 0x000100F4
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

		// Token: 0x17000033 RID: 51
		// (get) Token: 0x0600008D RID: 141 RVA: 0x00011D00 File Offset: 0x00010100
		// (set) Token: 0x0600008E RID: 142 RVA: 0x00011D14 File Offset: 0x00010114
		internal virtual Label Label3
		{
			get
			{
				return this._Label3;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Label3_Click);
				if (this._Label3 != null)
				{
					this._Label3.Click -= value2;
				}
				this._Label3 = value;
				if (this._Label3 != null)
				{
					this._Label3.Click += value2;
				}
			}
		}

		// Token: 0x17000034 RID: 52
		// (get) Token: 0x0600008F RID: 143 RVA: 0x00011D60 File Offset: 0x00010160
		// (set) Token: 0x06000090 RID: 144 RVA: 0x00011D74 File Offset: 0x00010174
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

		// Token: 0x17000035 RID: 53
		// (get) Token: 0x06000091 RID: 145 RVA: 0x00011D80 File Offset: 0x00010180
		// (set) Token: 0x06000092 RID: 146 RVA: 0x00011D94 File Offset: 0x00010194
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

		// Token: 0x17000036 RID: 54
		// (get) Token: 0x06000093 RID: 147 RVA: 0x00011DA0 File Offset: 0x000101A0
		// (set) Token: 0x06000094 RID: 148 RVA: 0x00011DB4 File Offset: 0x000101B4
		internal virtual TabControl TabControl1
		{
			get
			{
				return this._TabControl1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._TabControl1 = value;
			}
		}

		// Token: 0x17000037 RID: 55
		// (get) Token: 0x06000095 RID: 149 RVA: 0x00011DC0 File Offset: 0x000101C0
		// (set) Token: 0x06000096 RID: 150 RVA: 0x00011DD4 File Offset: 0x000101D4
		internal virtual PictureBox PictureBox1
		{
			get
			{
				return this._PictureBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.PictureBox1_Click);
				if (this._PictureBox1 != null)
				{
					this._PictureBox1.Click -= value2;
				}
				this._PictureBox1 = value;
				if (this._PictureBox1 != null)
				{
					this._PictureBox1.Click += value2;
				}
			}
		}

		// Token: 0x06000097 RID: 151 RVA: 0x00011E20 File Offset: 0x00010220
		private void Label3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000098 RID: 152 RVA: 0x00011E24 File Offset: 0x00010224
		private void LinkLabel1_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
		{
			Process.Start("http://www.youtube.com/user/TheArmax2001");
		}

		// Token: 0x06000099 RID: 153 RVA: 0x00011E34 File Offset: 0x00010234
		private void LinkLabel2_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
		{
			Process.Start("http://www.youtube.com/user/HeyBindidon");
		}

		// Token: 0x0600009A RID: 154 RVA: 0x00011E44 File Offset: 0x00010244
		private void LinkLabel3_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
		{
			Process.Start("http://www.youtube.com/user/chai1310b");
		}

		// Token: 0x0600009B RID: 155 RVA: 0x00011E54 File Offset: 0x00010254
		private void Label11_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600009C RID: 156 RVA: 0x00011E58 File Offset: 0x00010258
		private void PictureBox1_Click(object sender, EventArgs e)
		{
			Interaction.MsgBox("Merciiiiiiiiiiiiiii <3", MsgBoxStyle.Exclamation, "Merci");
		}

		// Token: 0x0400002B RID: 43
		[AccessedThroughProperty("TabPage3")]
		private TabPage _TabPage3;

		// Token: 0x0400002C RID: 44
		[AccessedThroughProperty("Label11")]
		private Label _Label11;

		// Token: 0x0400002D RID: 45
		[AccessedThroughProperty("TabPage2")]
		private TabPage _TabPage2;

		// Token: 0x0400002E RID: 46
		[AccessedThroughProperty("LinkLabel3")]
		private LinkLabel _LinkLabel3;

		// Token: 0x0400002F RID: 47
		[AccessedThroughProperty("Label10")]
		private Label _Label10;

		// Token: 0x04000030 RID: 48
		[AccessedThroughProperty("LinkLabel2")]
		private LinkLabel _LinkLabel2;

		// Token: 0x04000031 RID: 49
		[AccessedThroughProperty("Label9")]
		private Label _Label9;

		// Token: 0x04000032 RID: 50
		[AccessedThroughProperty("LinkLabel1")]
		private LinkLabel _LinkLabel1;

		// Token: 0x04000033 RID: 51
		[AccessedThroughProperty("Label8")]
		private Label _Label8;

		// Token: 0x04000034 RID: 52
		[AccessedThroughProperty("TabPage1")]
		private TabPage _TabPage1;

		// Token: 0x04000035 RID: 53
		[AccessedThroughProperty("Label2")]
		private Label _Label2;

		// Token: 0x04000036 RID: 54
		[AccessedThroughProperty("Label7")]
		private Label _Label7;

		// Token: 0x04000037 RID: 55
		[AccessedThroughProperty("Label1")]
		private Label _Label1;

		// Token: 0x04000038 RID: 56
		[AccessedThroughProperty("Label6")]
		private Label _Label6;

		// Token: 0x04000039 RID: 57
		[AccessedThroughProperty("Label3")]
		private Label _Label3;

		// Token: 0x0400003A RID: 58
		[AccessedThroughProperty("Label5")]
		private Label _Label5;

		// Token: 0x0400003B RID: 59
		[AccessedThroughProperty("Label4")]
		private Label _Label4;

		// Token: 0x0400003C RID: 60
		[AccessedThroughProperty("TabControl1")]
		private TabControl _TabControl1;

		// Token: 0x0400003D RID: 61
		[AccessedThroughProperty("PictureBox1")]
		private PictureBox _PictureBox1;
	}
}
