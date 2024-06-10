using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using Muphy.My;
using Siticone.UI.WinForms;

namespace Muphy
{
	// Token: 0x0200000D RID: 13
	[DesignerGenerated]
	public partial class client : Form
	{
		// Token: 0x06000078 RID: 120 RVA: 0x00002523 File Offset: 0x00000723
		public client()
		{
			base.Load += this.client_Load;
			this.InitializeComponent();
		}

		// Token: 0x1700003A RID: 58
		// (get) Token: 0x0600007B RID: 123 RVA: 0x00002543 File Offset: 0x00000743
		// (set) Token: 0x0600007C RID: 124 RVA: 0x0000254B File Offset: 0x0000074B
		internal virtual Panel Panel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700003B RID: 59
		// (get) Token: 0x0600007D RID: 125 RVA: 0x00002554 File Offset: 0x00000754
		// (set) Token: 0x0600007E RID: 126 RVA: 0x00007344 File Offset: 0x00005544
		internal virtual SiticoneControlBox SiticoneControlBox2
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneControlBox2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneControlBox2_Click);
				SiticoneControlBox siticoneControlBox = this._SiticoneControlBox2;
				if (siticoneControlBox != null)
				{
					siticoneControlBox.Click -= value2;
				}
				this._SiticoneControlBox2 = value;
				siticoneControlBox = this._SiticoneControlBox2;
				if (siticoneControlBox != null)
				{
					siticoneControlBox.Click += value2;
				}
			}
		}

		// Token: 0x1700003C RID: 60
		// (get) Token: 0x0600007F RID: 127 RVA: 0x0000255C File Offset: 0x0000075C
		// (set) Token: 0x06000080 RID: 128 RVA: 0x00002564 File Offset: 0x00000764
		internal virtual SiticoneControlBox SiticoneControlBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700003D RID: 61
		// (get) Token: 0x06000081 RID: 129 RVA: 0x0000256D File Offset: 0x0000076D
		// (set) Token: 0x06000082 RID: 130 RVA: 0x00002575 File Offset: 0x00000775
		internal virtual SiticoneDragControl SiticoneDragControl1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700003E RID: 62
		// (get) Token: 0x06000083 RID: 131 RVA: 0x0000257E File Offset: 0x0000077E
		// (set) Token: 0x06000084 RID: 132 RVA: 0x00002586 File Offset: 0x00000786
		internal virtual SiticoneDragControl SiticoneDragControl2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700003F RID: 63
		// (get) Token: 0x06000085 RID: 133 RVA: 0x0000258F File Offset: 0x0000078F
		// (set) Token: 0x06000086 RID: 134 RVA: 0x00002597 File Offset: 0x00000797
		internal virtual SiticoneDragControl SiticoneDragControl3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000040 RID: 64
		// (get) Token: 0x06000087 RID: 135 RVA: 0x000025A0 File Offset: 0x000007A0
		// (set) Token: 0x06000088 RID: 136 RVA: 0x00007388 File Offset: 0x00005588
		internal virtual TrackBar TrackBar2
		{
			[CompilerGenerated]
			get
			{
				return this._TrackBar2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TrackBar2_Scroll);
				TrackBar trackBar = this._TrackBar2;
				if (trackBar != null)
				{
					trackBar.Scroll -= value2;
				}
				this._TrackBar2 = value;
				trackBar = this._TrackBar2;
				if (trackBar != null)
				{
					trackBar.Scroll += value2;
				}
			}
		}

		// Token: 0x17000041 RID: 65
		// (get) Token: 0x06000089 RID: 137 RVA: 0x000025A8 File Offset: 0x000007A8
		// (set) Token: 0x0600008A RID: 138 RVA: 0x000025B0 File Offset: 0x000007B0
		internal virtual Label Label2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000042 RID: 66
		// (get) Token: 0x0600008B RID: 139 RVA: 0x000025B9 File Offset: 0x000007B9
		// (set) Token: 0x0600008C RID: 140 RVA: 0x000025C1 File Offset: 0x000007C1
		internal virtual Label Label1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000043 RID: 67
		// (get) Token: 0x0600008D RID: 141 RVA: 0x000025CA File Offset: 0x000007CA
		// (set) Token: 0x0600008E RID: 142 RVA: 0x000073CC File Offset: 0x000055CC
		internal virtual SiticoneButton SiticoneButton1
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneButton1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button1_Click);
				SiticoneButton siticoneButton = this._SiticoneButton1;
				if (siticoneButton != null)
				{
					siticoneButton.Click -= value2;
				}
				this._SiticoneButton1 = value;
				siticoneButton = this._SiticoneButton1;
				if (siticoneButton != null)
				{
					siticoneButton.Click += value2;
				}
			}
		}

		// Token: 0x17000044 RID: 68
		// (get) Token: 0x0600008F RID: 143 RVA: 0x000025D2 File Offset: 0x000007D2
		// (set) Token: 0x06000090 RID: 144 RVA: 0x000025DA File Offset: 0x000007DA
		internal virtual Label Label4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000045 RID: 69
		// (get) Token: 0x06000091 RID: 145 RVA: 0x000025E3 File Offset: 0x000007E3
		// (set) Token: 0x06000092 RID: 146 RVA: 0x000025EB File Offset: 0x000007EB
		internal virtual Label Label3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000046 RID: 70
		// (get) Token: 0x06000093 RID: 147 RVA: 0x000025F4 File Offset: 0x000007F4
		// (set) Token: 0x06000094 RID: 148 RVA: 0x00007410 File Offset: 0x00005610
		internal virtual Timer Timer1
		{
			[CompilerGenerated]
			get
			{
				return this._Timer1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer1_Tick);
				Timer timer = this._Timer1;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this._Timer1 = value;
				timer = this._Timer1;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x17000047 RID: 71
		// (get) Token: 0x06000095 RID: 149 RVA: 0x000025FC File Offset: 0x000007FC
		// (set) Token: 0x06000096 RID: 150 RVA: 0x00007454 File Offset: 0x00005654
		internal virtual SiticoneCheckBox SiticoneCheckBox1
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneCheckBox1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneCheckBox1_CheckedChanged);
				SiticoneCheckBox siticoneCheckBox = this._SiticoneCheckBox1;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged -= value2;
				}
				this._SiticoneCheckBox1 = value;
				siticoneCheckBox = this._SiticoneCheckBox1;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x17000048 RID: 72
		// (get) Token: 0x06000097 RID: 151 RVA: 0x00002604 File Offset: 0x00000804
		// (set) Token: 0x06000098 RID: 152 RVA: 0x00007498 File Offset: 0x00005698
		internal virtual Timer Timer2
		{
			[CompilerGenerated]
			get
			{
				return this._Timer2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer2_Tick);
				Timer timer = this._Timer2;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this._Timer2 = value;
				timer = this._Timer2;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x17000049 RID: 73
		// (get) Token: 0x06000099 RID: 153 RVA: 0x0000260C File Offset: 0x0000080C
		// (set) Token: 0x0600009A RID: 154 RVA: 0x000074DC File Offset: 0x000056DC
		internal virtual SiticoneCheckBox SiticoneCheckBox2
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneCheckBox2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneCheckBox2_CheckedChanged);
				SiticoneCheckBox siticoneCheckBox = this._SiticoneCheckBox2;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged -= value2;
				}
				this._SiticoneCheckBox2 = value;
				siticoneCheckBox = this._SiticoneCheckBox2;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x1700004A RID: 74
		// (get) Token: 0x0600009B RID: 155 RVA: 0x00002614 File Offset: 0x00000814
		// (set) Token: 0x0600009C RID: 156 RVA: 0x0000261C File Offset: 0x0000081C
		internal virtual SiticoneElipse SiticoneElipse1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004B RID: 75
		// (get) Token: 0x0600009D RID: 157 RVA: 0x00002625 File Offset: 0x00000825
		// (set) Token: 0x0600009E RID: 158 RVA: 0x0000262D File Offset: 0x0000082D
		internal virtual SiticoneElipse SiticoneElipse2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004C RID: 76
		// (get) Token: 0x0600009F RID: 159 RVA: 0x00002636 File Offset: 0x00000836
		// (set) Token: 0x060000A0 RID: 160 RVA: 0x0000263E File Offset: 0x0000083E
		internal virtual SiticoneElipse SiticoneElipse3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004D RID: 77
		// (get) Token: 0x060000A1 RID: 161 RVA: 0x00002647 File Offset: 0x00000847
		// (set) Token: 0x060000A2 RID: 162 RVA: 0x0000264F File Offset: 0x0000084F
		internal virtual SiticoneElipse SiticoneElipse4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004E RID: 78
		// (get) Token: 0x060000A3 RID: 163 RVA: 0x00002658 File Offset: 0x00000858
		// (set) Token: 0x060000A4 RID: 164 RVA: 0x00002660 File Offset: 0x00000860
		internal virtual SiticoneElipse SiticoneElipse5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004F RID: 79
		// (get) Token: 0x060000A5 RID: 165 RVA: 0x00002669 File Offset: 0x00000869
		// (set) Token: 0x060000A6 RID: 166 RVA: 0x00002671 File Offset: 0x00000871
		internal virtual SiticoneElipse SiticoneElipse6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000050 RID: 80
		// (get) Token: 0x060000A7 RID: 167 RVA: 0x0000267A File Offset: 0x0000087A
		// (set) Token: 0x060000A8 RID: 168 RVA: 0x00002682 File Offset: 0x00000882
		internal virtual Label Label6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000051 RID: 81
		// (get) Token: 0x060000A9 RID: 169 RVA: 0x0000268B File Offset: 0x0000088B
		// (set) Token: 0x060000AA RID: 170 RVA: 0x00002693 File Offset: 0x00000893
		internal virtual SiticoneGroupBox SiticoneGroupBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000052 RID: 82
		// (get) Token: 0x060000AB RID: 171 RVA: 0x0000269C File Offset: 0x0000089C
		// (set) Token: 0x060000AC RID: 172 RVA: 0x00007520 File Offset: 0x00005720
		internal virtual TrackBar TrackBar1
		{
			[CompilerGenerated]
			get
			{
				return this._TrackBar1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TrackBar1_Scroll_1);
				TrackBar trackBar = this._TrackBar1;
				if (trackBar != null)
				{
					trackBar.Scroll -= value2;
				}
				this._TrackBar1 = value;
				trackBar = this._TrackBar1;
				if (trackBar != null)
				{
					trackBar.Scroll += value2;
				}
			}
		}

		// Token: 0x17000053 RID: 83
		// (get) Token: 0x060000AD RID: 173 RVA: 0x000026A4 File Offset: 0x000008A4
		// (set) Token: 0x060000AE RID: 174 RVA: 0x000026AC File Offset: 0x000008AC
		internal virtual Label Label7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000054 RID: 84
		// (get) Token: 0x060000AF RID: 175 RVA: 0x000026B5 File Offset: 0x000008B5
		// (set) Token: 0x060000B0 RID: 176 RVA: 0x000026BD File Offset: 0x000008BD
		internal virtual SiticoneGroupBox SiticoneGroupBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000055 RID: 85
		// (get) Token: 0x060000B1 RID: 177 RVA: 0x000026C6 File Offset: 0x000008C6
		// (set) Token: 0x060000B2 RID: 178 RVA: 0x00007564 File Offset: 0x00005764
		internal virtual SiticoneGroupBox SiticoneGroupBox3
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneGroupBox3;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneGroupBox3_Click);
				SiticoneGroupBox siticoneGroupBox = this._SiticoneGroupBox3;
				if (siticoneGroupBox != null)
				{
					siticoneGroupBox.Click -= value2;
				}
				this._SiticoneGroupBox3 = value;
				siticoneGroupBox = this._SiticoneGroupBox3;
				if (siticoneGroupBox != null)
				{
					siticoneGroupBox.Click += value2;
				}
			}
		}

		// Token: 0x17000056 RID: 86
		// (get) Token: 0x060000B3 RID: 179 RVA: 0x000026CE File Offset: 0x000008CE
		// (set) Token: 0x060000B4 RID: 180 RVA: 0x000026D6 File Offset: 0x000008D6
		internal virtual Label Label8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000057 RID: 87
		// (get) Token: 0x060000B5 RID: 181 RVA: 0x000026DF File Offset: 0x000008DF
		// (set) Token: 0x060000B6 RID: 182 RVA: 0x000026E7 File Offset: 0x000008E7
		internal virtual TextBox BLUCLR { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000058 RID: 88
		// (get) Token: 0x060000B7 RID: 183 RVA: 0x000026F0 File Offset: 0x000008F0
		// (set) Token: 0x060000B8 RID: 184 RVA: 0x000026F8 File Offset: 0x000008F8
		internal virtual TextBox TextBox5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000059 RID: 89
		// (get) Token: 0x060000B9 RID: 185 RVA: 0x00002701 File Offset: 0x00000901
		// (set) Token: 0x060000BA RID: 186 RVA: 0x00002709 File Offset: 0x00000909
		internal virtual TextBox GRNCLR { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005A RID: 90
		// (get) Token: 0x060000BB RID: 187 RVA: 0x00002712 File Offset: 0x00000912
		// (set) Token: 0x060000BC RID: 188 RVA: 0x0000271A File Offset: 0x0000091A
		internal virtual TextBox TextBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005B RID: 91
		// (get) Token: 0x060000BD RID: 189 RVA: 0x00002723 File Offset: 0x00000923
		// (set) Token: 0x060000BE RID: 190 RVA: 0x0000272B File Offset: 0x0000092B
		internal virtual TextBox RDCLR { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005C RID: 92
		// (get) Token: 0x060000BF RID: 191 RVA: 0x00002734 File Offset: 0x00000934
		// (set) Token: 0x060000C0 RID: 192 RVA: 0x0000273C File Offset: 0x0000093C
		internal virtual TextBox TextBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005D RID: 93
		// (get) Token: 0x060000C1 RID: 193 RVA: 0x00002745 File Offset: 0x00000945
		// (set) Token: 0x060000C2 RID: 194 RVA: 0x0000274D File Offset: 0x0000094D
		internal virtual TrackBar TBBlue { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005E RID: 94
		// (get) Token: 0x060000C3 RID: 195 RVA: 0x00002756 File Offset: 0x00000956
		// (set) Token: 0x060000C4 RID: 196 RVA: 0x0000275E File Offset: 0x0000095E
		internal virtual TrackBar TBGreen { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005F RID: 95
		// (get) Token: 0x060000C5 RID: 197 RVA: 0x00002767 File Offset: 0x00000967
		// (set) Token: 0x060000C6 RID: 198 RVA: 0x0000276F File Offset: 0x0000096F
		internal virtual TrackBar TBRed { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000060 RID: 96
		// (get) Token: 0x060000C7 RID: 199 RVA: 0x00002778 File Offset: 0x00000978
		// (set) Token: 0x060000C8 RID: 200 RVA: 0x000075A8 File Offset: 0x000057A8
		internal virtual Timer TMRGen
		{
			[CompilerGenerated]
			get
			{
				return this._TMRGen;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TMRGen_Tick);
				Timer tmrgen = this._TMRGen;
				if (tmrgen != null)
				{
					tmrgen.Tick -= value2;
				}
				this._TMRGen = value;
				tmrgen = this._TMRGen;
				if (tmrgen != null)
				{
					tmrgen.Tick += value2;
				}
			}
		}

		// Token: 0x17000061 RID: 97
		// (get) Token: 0x060000C9 RID: 201 RVA: 0x00002780 File Offset: 0x00000980
		// (set) Token: 0x060000CA RID: 202 RVA: 0x00002788 File Offset: 0x00000988
		internal virtual SiticoneGroupBox SiticoneGroupBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000062 RID: 98
		// (get) Token: 0x060000CB RID: 203 RVA: 0x00002791 File Offset: 0x00000991
		// (set) Token: 0x060000CC RID: 204 RVA: 0x00002799 File Offset: 0x00000999
		internal virtual Label Label11 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000063 RID: 99
		// (get) Token: 0x060000CD RID: 205 RVA: 0x000027A2 File Offset: 0x000009A2
		// (set) Token: 0x060000CE RID: 206 RVA: 0x000027AA File Offset: 0x000009AA
		internal virtual Label Label9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000064 RID: 100
		// (get) Token: 0x060000CF RID: 207 RVA: 0x000027B3 File Offset: 0x000009B3
		// (set) Token: 0x060000D0 RID: 208 RVA: 0x000027BB File Offset: 0x000009BB
		internal virtual Panel Panel2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000065 RID: 101
		// (get) Token: 0x060000D1 RID: 209 RVA: 0x000027C4 File Offset: 0x000009C4
		// (set) Token: 0x060000D2 RID: 210 RVA: 0x000075EC File Offset: 0x000057EC
		internal virtual LinkLabel LinkLabel2
		{
			[CompilerGenerated]
			get
			{
				return this._LinkLabel2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				LinkLabelLinkClickedEventHandler value2 = new LinkLabelLinkClickedEventHandler(this.LinkLabel2_LinkClicked);
				LinkLabel linkLabel = this._LinkLabel2;
				if (linkLabel != null)
				{
					linkLabel.LinkClicked -= value2;
				}
				this._LinkLabel2 = value;
				linkLabel = this._LinkLabel2;
				if (linkLabel != null)
				{
					linkLabel.LinkClicked += value2;
				}
			}
		}

		// Token: 0x17000066 RID: 102
		// (get) Token: 0x060000D3 RID: 211 RVA: 0x000027CC File Offset: 0x000009CC
		// (set) Token: 0x060000D4 RID: 212 RVA: 0x00007630 File Offset: 0x00005830
		internal virtual LinkLabel LinkLabel1
		{
			[CompilerGenerated]
			get
			{
				return this._LinkLabel1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				LinkLabelLinkClickedEventHandler value2 = new LinkLabelLinkClickedEventHandler(this.LinkLabel1_LinkClicked);
				LinkLabel linkLabel = this._LinkLabel1;
				if (linkLabel != null)
				{
					linkLabel.LinkClicked -= value2;
				}
				this._LinkLabel1 = value;
				linkLabel = this._LinkLabel1;
				if (linkLabel != null)
				{
					linkLabel.LinkClicked += value2;
				}
			}
		}

		// Token: 0x17000067 RID: 103
		// (get) Token: 0x060000D5 RID: 213 RVA: 0x000027D4 File Offset: 0x000009D4
		// (set) Token: 0x060000D6 RID: 214 RVA: 0x000027DC File Offset: 0x000009DC
		internal virtual Label Label14 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000068 RID: 104
		// (get) Token: 0x060000D7 RID: 215 RVA: 0x000027E5 File Offset: 0x000009E5
		// (set) Token: 0x060000D8 RID: 216 RVA: 0x000027ED File Offset: 0x000009ED
		internal virtual Label Label13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000069 RID: 105
		// (get) Token: 0x060000D9 RID: 217 RVA: 0x000027F6 File Offset: 0x000009F6
		// (set) Token: 0x060000DA RID: 218 RVA: 0x000027FE File Offset: 0x000009FE
		internal virtual Label Label15 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700006A RID: 106
		// (get) Token: 0x060000DB RID: 219 RVA: 0x00002807 File Offset: 0x00000A07
		// (set) Token: 0x060000DC RID: 220 RVA: 0x0000280F File Offset: 0x00000A0F
		internal virtual Label Label5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700006B RID: 107
		// (get) Token: 0x060000DD RID: 221 RVA: 0x00002818 File Offset: 0x00000A18
		// (set) Token: 0x060000DE RID: 222 RVA: 0x00007674 File Offset: 0x00005874
		internal virtual SiticoneCheckBox SiticoneCheckBox6
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneCheckBox6;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneCheckBox6_CheckedChanged);
				SiticoneCheckBox siticoneCheckBox = this._SiticoneCheckBox6;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged -= value2;
				}
				this._SiticoneCheckBox6 = value;
				siticoneCheckBox = this._SiticoneCheckBox6;
				if (siticoneCheckBox != null)
				{
					siticoneCheckBox.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x1700006C RID: 108
		// (get) Token: 0x060000DF RID: 223 RVA: 0x00002820 File Offset: 0x00000A20
		// (set) Token: 0x060000E0 RID: 224 RVA: 0x00002828 File Offset: 0x00000A28
		internal virtual Label Label10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700006D RID: 109
		// (get) Token: 0x060000E1 RID: 225 RVA: 0x00002831 File Offset: 0x00000A31
		// (set) Token: 0x060000E2 RID: 226 RVA: 0x00002839 File Offset: 0x00000A39
		internal virtual SiticoneGroupBox SiticoneGroupBox5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700006E RID: 110
		// (get) Token: 0x060000E3 RID: 227 RVA: 0x00002842 File Offset: 0x00000A42
		// (set) Token: 0x060000E4 RID: 228 RVA: 0x000076B8 File Offset: 0x000058B8
		internal virtual SiticoneCheckBox normal
		{
			[CompilerGenerated]
			get
			{
				return this._normal;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.normal_CheckedChanged);
				SiticoneCheckBox normal = this._normal;
				if (normal != null)
				{
					normal.CheckedChanged -= value2;
				}
				this._normal = value;
				normal = this._normal;
				if (normal != null)
				{
					normal.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x1700006F RID: 111
		// (get) Token: 0x060000E5 RID: 229 RVA: 0x0000284A File Offset: 0x00000A4A
		// (set) Token: 0x060000E6 RID: 230 RVA: 0x000076FC File Offset: 0x000058FC
		internal virtual SiticoneCheckBox butterfly
		{
			[CompilerGenerated]
			get
			{
				return this._butterfly;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.butterfly_CheckedChanged);
				SiticoneCheckBox butterfly = this._butterfly;
				if (butterfly != null)
				{
					butterfly.CheckedChanged -= value2;
				}
				this._butterfly = value;
				butterfly = this._butterfly;
				if (butterfly != null)
				{
					butterfly.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x17000070 RID: 112
		// (get) Token: 0x060000E7 RID: 231 RVA: 0x00002852 File Offset: 0x00000A52
		// (set) Token: 0x060000E8 RID: 232 RVA: 0x00007740 File Offset: 0x00005940
		internal virtual SiticoneCheckBox blatant
		{
			[CompilerGenerated]
			get
			{
				return this._blatant;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.blatant_CheckedChanged);
				EventHandler value3 = new EventHandler(this.blatant_CheckedChanged_1);
				SiticoneCheckBox blatant = this._blatant;
				if (blatant != null)
				{
					blatant.CheckedChanged -= value2;
					blatant.CheckedChanged -= value3;
				}
				this._blatant = value;
				blatant = this._blatant;
				if (blatant != null)
				{
					blatant.CheckedChanged += value2;
					blatant.CheckedChanged += value3;
				}
			}
		}

		// Token: 0x17000071 RID: 113
		// (get) Token: 0x060000E9 RID: 233 RVA: 0x0000285A File Offset: 0x00000A5A
		// (set) Token: 0x060000EA RID: 234 RVA: 0x00002862 File Offset: 0x00000A62
		internal virtual Label slc { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000072 RID: 114
		// (get) Token: 0x060000EB RID: 235 RVA: 0x0000286B File Offset: 0x00000A6B
		// (set) Token: 0x060000EC RID: 236 RVA: 0x000077A0 File Offset: 0x000059A0
		internal virtual SiticoneButton sclick
		{
			[CompilerGenerated]
			get
			{
				return this._sclick;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.sclick_Click);
				SiticoneButton sclick = this._sclick;
				if (sclick != null)
				{
					sclick.Click -= value2;
				}
				this._sclick = value;
				sclick = this._sclick;
				if (sclick != null)
				{
					sclick.Click += value2;
				}
			}
		}

		// Token: 0x17000073 RID: 115
		// (get) Token: 0x060000ED RID: 237 RVA: 0x00002873 File Offset: 0x00000A73
		// (set) Token: 0x060000EE RID: 238 RVA: 0x000077E4 File Offset: 0x000059E4
		internal virtual SiticoneButton qtap
		{
			[CompilerGenerated]
			get
			{
				return this._qtap;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.qtap_Click);
				SiticoneButton qtap = this._qtap;
				if (qtap != null)
				{
					qtap.Click -= value2;
				}
				this._qtap = value;
				qtap = this._qtap;
				if (qtap != null)
				{
					qtap.Click += value2;
				}
			}
		}

		// Token: 0x17000074 RID: 116
		// (get) Token: 0x060000EF RID: 239 RVA: 0x0000287B File Offset: 0x00000A7B
		// (set) Token: 0x060000F0 RID: 240 RVA: 0x00007828 File Offset: 0x00005A28
		internal virtual Label Label16
		{
			[CompilerGenerated]
			get
			{
				return this._Label16;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Label16_Click);
				Label label = this._Label16;
				if (label != null)
				{
					label.Click -= value2;
				}
				this._Label16 = value;
				label = this._Label16;
				if (label != null)
				{
					label.Click += value2;
				}
			}
		}

		// Token: 0x17000075 RID: 117
		// (get) Token: 0x060000F1 RID: 241 RVA: 0x00002883 File Offset: 0x00000A83
		// (set) Token: 0x060000F2 RID: 242 RVA: 0x0000786C File Offset: 0x00005A6C
		internal virtual Timer gwi
		{
			[CompilerGenerated]
			get
			{
				return this._gwi;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.gwi_Tick);
				Timer gwi = this._gwi;
				if (gwi != null)
				{
					gwi.Tick -= value2;
				}
				this._gwi = value;
				gwi = this._gwi;
				if (gwi != null)
				{
					gwi.Tick += value2;
				}
			}
		}

		// Token: 0x17000076 RID: 118
		// (get) Token: 0x060000F3 RID: 243 RVA: 0x0000288B File Offset: 0x00000A8B
		// (set) Token: 0x060000F4 RID: 244 RVA: 0x000078B0 File Offset: 0x00005AB0
		internal virtual SiticoneButton SiticoneButton2
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneButton2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneButton2_Click_1);
				SiticoneButton siticoneButton = this._SiticoneButton2;
				if (siticoneButton != null)
				{
					siticoneButton.Click -= value2;
				}
				this._SiticoneButton2 = value;
				siticoneButton = this._SiticoneButton2;
				if (siticoneButton != null)
				{
					siticoneButton.Click += value2;
				}
			}
		}

		// Token: 0x17000077 RID: 119
		// (get) Token: 0x060000F5 RID: 245 RVA: 0x00002893 File Offset: 0x00000A93
		// (set) Token: 0x060000F6 RID: 246 RVA: 0x0000289B File Offset: 0x00000A9B
		internal virtual SiticoneGroupBox qtape { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000078 RID: 120
		// (get) Token: 0x060000F7 RID: 247 RVA: 0x000028A4 File Offset: 0x00000AA4
		// (set) Token: 0x060000F8 RID: 248 RVA: 0x000028AC File Offset: 0x00000AAC
		internal virtual SiticoneGroupBox sound { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000079 RID: 121
		// (get) Token: 0x060000F9 RID: 249 RVA: 0x000028B5 File Offset: 0x00000AB5
		// (set) Token: 0x060000FA RID: 250 RVA: 0x000028BD File Offset: 0x00000ABD
		internal virtual SiticoneGroupBox user { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x060000FB RID: 251
		[DllImport("user32.dll")]
		private static extern short GetAsyncKeyState(int vKey);

		// Token: 0x060000FC RID: 252
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern bool mouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

		// Token: 0x060000FD RID: 253 RVA: 0x000028C6 File Offset: 0x00000AC6
		private void TrackBar1_Scroll(object sender, EventArgs e)
		{
			this.Label2.Text = Conversions.ToString(this.TrackBar1.Value);
		}

		// Token: 0x060000FE RID: 254 RVA: 0x000028E3 File Offset: 0x00000AE3
		private void TrackBar2_Scroll(object sender, EventArgs e)
		{
			this.Label1.Text = Conversions.ToString(this.TrackBar2.Value);
		}

		// Token: 0x060000FF RID: 255 RVA: 0x000078F4 File Offset: 0x00005AF4
		private void Timer1_Tick(object sender, EventArgs e)
		{
			VBMath.Randomize();
			Random random = new Random();
			checked
			{
				int minValue = (int)Math.Round(1000.0 / (double)this.TrackBar1.Value);
				int maxValue = (int)Math.Round(1000.0 / (double)this.TrackBar2.Value);
				this.Timer1.Interval = random.Next(minValue, maxValue);
				if (Control.MouseButtons == MouseButtons.Left)
				{
					client.mouse_event(4, 0, 0, 0, 0);
					client.mouse_event(2, 0, 0, 0, 0);
				}
			}
		}

		// Token: 0x06000100 RID: 256 RVA: 0x00007980 File Offset: 0x00005B80
		private void Button1_Click(object sender, EventArgs e)
		{
			checked
			{
				this.toggle++;
				if (this.toggle == 1)
				{
					this.Timer1.Start();
					this.SiticoneButton1.Text = "UnToggle";
					Interaction.MsgBox("''Im minecraft gamer now \ud83d\ude0e\ud83d\udc4d''", MsgBoxStyle.OkOnly, null);
				}
				else
				{
					this.Timer1.Stop();
					this.toggle = 0;
					this.SiticoneButton1.Text = "Toggle";
				}
			}
		}

		// Token: 0x06000101 RID: 257 RVA: 0x000079F4 File Offset: 0x00005BF4
		private void SiticoneCheckBox1_CheckedChanged(object sender, EventArgs e)
		{
			if (this.SiticoneCheckBox1.Checked)
			{
				base.ShowInTaskbar = false;
				base.MinimizeBox = false;
				this.SiticoneControlBox1.Hide();
			}
			else
			{
				base.ShowInTaskbar = true;
				base.MinimizeBox = true;
				this.SiticoneControlBox1.Show();
			}
		}

		// Token: 0x06000102 RID: 258 RVA: 0x00002900 File Offset: 0x00000B00
		private void SiticoneControlBox2_Click(object sender, EventArgs e)
		{
			Application.Exit();
		}

		// Token: 0x06000103 RID: 259 RVA: 0x00002907 File Offset: 0x00000B07
		private void SiticoneButton3_Click(object sender, EventArgs e)
		{
			Interaction.MsgBox("Coming Soon", MsgBoxStyle.OkOnly, null);
		}

		// Token: 0x06000104 RID: 260 RVA: 0x00002916 File Offset: 0x00000B16
		private void Button4_Click(object sender, EventArgs e)
		{
			Process.Start("https://discord.gg/YEhpqRB");
		}

		// Token: 0x06000105 RID: 261 RVA: 0x00002521 File Offset: 0x00000721
		private void TrackBar6_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000106 RID: 262 RVA: 0x00007A44 File Offset: 0x00005C44
		private void Timer2_Tick(object sender, EventArgs e)
		{
			try
			{
				if (client.GetAsyncKeyState(115) != 0)
				{
					this.Timer1.Start();
					this.SiticoneButton1.Text = "UnToggle";
				}
				else if (client.GetAsyncKeyState(88) != 0)
				{
					this.Timer1.Stop();
					this.SiticoneButton1.Text = "Toggle";
				}
			}
			catch (Exception ex)
			{
			}
		}

		// Token: 0x06000107 RID: 263 RVA: 0x00007AC4 File Offset: 0x00005CC4
		private void SiticoneCheckBox2_CheckedChanged(object sender, EventArgs e)
		{
			if (this.SiticoneCheckBox2.Checked)
			{
				this.Timer2.Start();
			}
			else
			{
				this.Timer2.Stop();
			}
		}

		// Token: 0x06000108 RID: 264 RVA: 0x00002923 File Offset: 0x00000B23
		private void Button2_Click(object sender, EventArgs e)
		{
			Interaction.MsgBox("KeyBind added, Reach Added, New Design added, Self Destruct in development...", MsgBoxStyle.OkOnly, null);
		}

		// Token: 0x06000109 RID: 265 RVA: 0x00002932 File Offset: 0x00000B32
		private void Button3_Click(object sender, EventArgs e)
		{
			Process.Start("https://www.youtube.com/channel/UC5_CvhQCny6RR2y-xo-5MXA?view_as=subscriber");
		}

		// Token: 0x0600010A RID: 266 RVA: 0x0000293F File Offset: 0x00000B3F
		private void Button1_Click_1(object sender, EventArgs e)
		{
			Interaction.MsgBox("Move the dll for other directory to app no open, and now it is so hard to detect and application cant open :D!", MsgBoxStyle.OkOnly, null);
		}

		// Token: 0x0600010B RID: 267 RVA: 0x0000294E File Offset: 0x00000B4E
		private void Button5_Click(object sender, EventArgs e)
		{
			Interaction.MsgBox("Need help? Join in discord!", MsgBoxStyle.OkOnly, null);
		}

		// Token: 0x0600010C RID: 268 RVA: 0x0000295D File Offset: 0x00000B5D
		private void sdButton_Click(object sender, EventArgs e)
		{
			base.Hide();
			MyProject.Forms.self.Show();
		}

		// Token: 0x0600010D RID: 269 RVA: 0x000028C6 File Offset: 0x00000AC6
		private void TrackBar1_Scroll_1(object sender, EventArgs e)
		{
			this.Label2.Text = Conversions.ToString(this.TrackBar1.Value);
		}

		// Token: 0x0600010E RID: 270 RVA: 0x00007AF8 File Offset: 0x00005CF8
		private void TMRGen_Tick(object sender, EventArgs e)
		{
			this.RDCLR.Text = Conversions.ToString(this.TBRed.Value);
			this.GRNCLR.Text = Conversions.ToString(this.TBGreen.Value);
			this.BLUCLR.Text = Conversions.ToString(this.TBBlue.Value);
			int red = Conversions.ToInteger(this.RDCLR.Text);
			int green = Conversions.ToInteger(this.GRNCLR.Text);
			int blue = Conversions.ToInteger(this.BLUCLR.Text);
			this.Panel1.BackColor = Color.FromArgb(red, green, blue);
			this.SiticoneButton1.FillColor = Color.FromArgb(red, green, blue);
			this.SiticoneButton2.FillColor = Color.FromArgb(red, green, blue);
			this.Label5.ForeColor = Color.FromArgb(red, green, blue);
			this.SiticoneCheckBox1.ForeColor = Color.FromArgb(red, green, blue);
			this.SiticoneCheckBox2.CheckMarkColor = Color.FromArgb(red, green, blue);
			this.SiticoneCheckBox6.CheckMarkColor = Color.FromArgb(red, green, blue);
			this.SiticoneCheckBox1.CheckMarkColor = Color.FromArgb(red, green, blue);
			this.SiticoneCheckBox6.ForeColor = Color.FromArgb(red, green, blue);
			this.butterfly.ForeColor = Color.FromArgb(red, green, blue);
			this.butterfly.CheckMarkColor = Color.FromArgb(red, green, blue);
			this.normal.CheckMarkColor = Color.FromArgb(red, green, blue);
			this.normal.ForeColor = Color.FromArgb(red, green, blue);
			this.blatant.ForeColor = Color.FromArgb(red, green, blue);
			this.blatant.CheckMarkColor = Color.FromArgb(red, green, blue);
			this.qtap.FillColor = Color.FromArgb(red, green, blue);
			this.sclick.FillColor = Color.FromArgb(red, green, blue);
		}

		// Token: 0x0600010F RID: 271 RVA: 0x00002521 File Offset: 0x00000721
		private void client_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x06000110 RID: 272 RVA: 0x00002974 File Offset: 0x00000B74
		private void LinkLabel1_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
		{
			Process.Start("https://discord.gg/NaTJrJr");
		}

		// Token: 0x06000111 RID: 273 RVA: 0x00002521 File Offset: 0x00000721
		private void toggleright_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000112 RID: 274 RVA: 0x00002521 File Offset: 0x00000721
		private void SiticoneGroupBox3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000113 RID: 275 RVA: 0x00007CCC File Offset: 0x00005ECC
		private void blatant_CheckedChanged(object sender, EventArgs e)
		{
			if (this.blatant.Checked)
			{
				this.TrackBar1.Maximum = 70;
				this.TrackBar2.Maximum = 70;
				this.butterfly.Hide();
				this.normal.Hide();
				this.slc.Text = "Blatant Mode";
			}
			else
			{
				this.TrackBar1.Maximum = 0;
				this.TrackBar2.Maximum = 0;
				this.butterfly.Show();
				this.normal.Show();
				this.TrackBar1.Value = Conversions.ToInteger("0");
				this.TrackBar2.Value = Conversions.ToInteger("0");
				this.Label1.Text = "0";
				this.Label2.Text = "0";
				this.slc.Text = "Select One Mode";
			}
		}

		// Token: 0x06000114 RID: 276 RVA: 0x00007DB4 File Offset: 0x00005FB4
		private void butterfly_CheckedChanged(object sender, EventArgs e)
		{
			if (this.butterfly.Checked)
			{
				this.TrackBar1.Maximum = 50;
				this.TrackBar2.Maximum = 50;
				this.blatant.Hide();
				this.normal.Hide();
				this.slc.Text = "Butterfly Mode";
			}
			else
			{
				this.TrackBar1.Maximum = 0;
				this.TrackBar2.Maximum = 0;
				this.blatant.Show();
				this.normal.Show();
				this.Label1.Text = "0";
				this.Label2.Text = "0";
				this.slc.Text = "Select One Mode";
				this.TrackBar1.Value = Conversions.ToInteger("0");
				this.TrackBar2.Value = Conversions.ToInteger("0");
			}
		}

		// Token: 0x06000115 RID: 277 RVA: 0x00007E9C File Offset: 0x0000609C
		private void normal_CheckedChanged(object sender, EventArgs e)
		{
			if (this.normal.Checked)
			{
				this.TrackBar1.Maximum = 25;
				this.TrackBar2.Maximum = 25;
				this.blatant.Hide();
				this.butterfly.Hide();
				this.slc.Text = "Normal Mode";
			}
			else
			{
				this.slc.Text = "Select One Mode";
				this.TrackBar1.Maximum = 0;
				this.TrackBar2.Maximum = 0;
				this.blatant.Show();
				this.Label1.Text = "0";
				this.Label2.Text = "0";
				this.butterfly.Show();
				this.TrackBar1.Value = Conversions.ToInteger("0");
				this.TrackBar2.Value = Conversions.ToInteger("0");
			}
		}

		// Token: 0x06000116 RID: 278 RVA: 0x00002521 File Offset: 0x00000721
		private void blatant_CheckedChanged_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000117 RID: 279 RVA: 0x00002981 File Offset: 0x00000B81
		private void SiticoneCheckBox6_CheckedChanged(object sender, EventArgs e)
		{
			base.Hide();
		}

		// Token: 0x06000118 RID: 280 RVA: 0x00002989 File Offset: 0x00000B89
		private void qtap_Click(object sender, EventArgs e)
		{
			MyProject.Forms.qtapy.Show();
		}

		// Token: 0x06000119 RID: 281 RVA: 0x0000299A File Offset: 0x00000B9A
		private void sclick_Click(object sender, EventArgs e)
		{
			MyProject.Forms.soundform.Show();
		}

		// Token: 0x0600011A RID: 282 RVA: 0x00002521 File Offset: 0x00000721
		private void SiticoneButton2_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600011B RID: 283 RVA: 0x00002521 File Offset: 0x00000721
		private void gwi_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x0600011C RID: 284 RVA: 0x000029AB File Offset: 0x00000BAB
		private void SiticoneButton2_Click_1(object sender, EventArgs e)
		{
			Interaction.MsgBox("BUY FISHWARE", MsgBoxStyle.OkOnly, null);
		}

		// Token: 0x0600011D RID: 285 RVA: 0x00002916 File Offset: 0x00000B16
		private void LinkLabel2_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
		{
			Process.Start("https://discord.gg/YEhpqRB");
		}

		// Token: 0x0600011E RID: 286 RVA: 0x00002521 File Offset: 0x00000721
		private void Label16_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0400006D RID: 109
		public const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x0400006E RID: 110
		public const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x0400006F RID: 111
		public const int MOUSEEVENTF_MIDDLEDOWN = 32;

		// Token: 0x04000070 RID: 112
		public const int MOUSEEVENTF_MIDDLEUP = 64;

		// Token: 0x04000071 RID: 113
		public const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x04000072 RID: 114
		public const int MOUSEEVENTF_RIGHTUP = 16;

		// Token: 0x04000073 RID: 115
		public const int MOUSEEVENTF_MOVE = 1;

		// Token: 0x04000074 RID: 116
		private int toggle;
	}
}
