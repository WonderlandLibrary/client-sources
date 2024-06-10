using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;
using GHOSTBYTES.My;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using Microsoft.Win32;

namespace GHOSTBYTES
{
	// Token: 0x02000032 RID: 50
	[DesignerGenerated]
	public partial class Form2 : Form
	{
		// Token: 0x0600025D RID: 605 RVA: 0x0000B820 File Offset: 0x00009A20
		public Form2()
		{
			base.Load += this.Form2_Load;
			base.FormClosing += this.Form2_FormClosing;
			this.CurrentHK = Keys.Menu;
			this.HkStr = "Alt";
			this.PState = false;
			this.RandTime = false;
			this.RandCPS = false;
			this.PE = new AutoResetEvent(false);
			this.IsEnabled = false;
			this.FastplaceHK = Keys.Insert;
			this.FPStr = "Insert";
			this.FState = false;
			this.FastplaceEnabled = false;
			this.ChatDisable = false;
			this.ScriptHK = Keys.F10;
			this.TapHK = Keys.F9;
			this.MisplaceHK = Keys.Z;
			this.VelocityHK = Keys.L;
			this.StrafespeedHK = Keys.U;
			this.AimAssistHK = Keys.I;
			this.presstime = 500;
			this.taptime = 500;
			this.buttonpress = 4;
			this.tapkey = Keys.W;
			this.ActiveProcess = Form2.ProcessHelper.GetActiveProcess();
			this.InitializeComponent();
		}

		// Token: 0x170000CA RID: 202
		// (get) Token: 0x06000260 RID: 608 RVA: 0x00003F06 File Offset: 0x00002106
		// (set) Token: 0x06000261 RID: 609 RVA: 0x0001B768 File Offset: 0x00019968
		internal virtual FormSkin FormSkin1
		{
			[CompilerGenerated]
			get
			{
				return this.FormSkin1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FormSkin1_Click);
				FormSkin formSkin = this.FormSkin1;
				if (formSkin != null)
				{
					formSkin.Click -= value2;
				}
				this.FormSkin1 = value;
				formSkin = this.FormSkin1;
				if (formSkin != null)
				{
					formSkin.Click += value2;
				}
			}
		}

		// Token: 0x170000CB RID: 203
		// (get) Token: 0x06000262 RID: 610 RVA: 0x00003F0E File Offset: 0x0000210E
		// (set) Token: 0x06000263 RID: 611 RVA: 0x0001B7AC File Offset: 0x000199AC
		internal virtual FlatTabControl FlatTabControl1
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTabControl1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatTabControl1_Click);
				FlatTabControl flatTabControl = this.FlatTabControl1;
				if (flatTabControl != null)
				{
					flatTabControl.Click -= value2;
				}
				this.FlatTabControl1 = value;
				flatTabControl = this.FlatTabControl1;
				if (flatTabControl != null)
				{
					flatTabControl.Click += value2;
				}
			}
		}

		// Token: 0x170000CC RID: 204
		// (get) Token: 0x06000264 RID: 612 RVA: 0x00003F16 File Offset: 0x00002116
		// (set) Token: 0x06000265 RID: 613 RVA: 0x00003F1E File Offset: 0x0000211E
		internal virtual TabPage TabPage1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000CD RID: 205
		// (get) Token: 0x06000266 RID: 614 RVA: 0x00003F27 File Offset: 0x00002127
		// (set) Token: 0x06000267 RID: 615 RVA: 0x00003F2F File Offset: 0x0000212F
		internal virtual TabPage TabPage2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000CE RID: 206
		// (get) Token: 0x06000268 RID: 616 RVA: 0x00003F38 File Offset: 0x00002138
		// (set) Token: 0x06000269 RID: 617 RVA: 0x00003F40 File Offset: 0x00002140
		internal virtual TabPage TabPage3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000CF RID: 207
		// (get) Token: 0x0600026A RID: 618 RVA: 0x00003F49 File Offset: 0x00002149
		// (set) Token: 0x0600026B RID: 619 RVA: 0x0001B7F0 File Offset: 0x000199F0
		internal virtual TabPage TabPage4
		{
			[CompilerGenerated]
			get
			{
				return this.TabPage4;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TabPage4_Click);
				TabPage tabPage = this.TabPage4;
				if (tabPage != null)
				{
					tabPage.Click -= value2;
				}
				this.TabPage4 = value;
				tabPage = this.TabPage4;
				if (tabPage != null)
				{
					tabPage.Click += value2;
				}
			}
		}

		// Token: 0x170000D0 RID: 208
		// (get) Token: 0x0600026C RID: 620 RVA: 0x00003F51 File Offset: 0x00002151
		// (set) Token: 0x0600026D RID: 621 RVA: 0x0001B834 File Offset: 0x00019A34
		internal virtual FlatClose FlatClose1
		{
			[CompilerGenerated]
			get
			{
				return this.FlatClose1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatClose1_Click);
				FlatClose flatClose = this.FlatClose1;
				if (flatClose != null)
				{
					flatClose.Click -= value2;
				}
				this.FlatClose1 = value;
				flatClose = this.FlatClose1;
				if (flatClose != null)
				{
					flatClose.Click += value2;
				}
			}
		}

		// Token: 0x170000D1 RID: 209
		// (get) Token: 0x0600026E RID: 622 RVA: 0x00003F59 File Offset: 0x00002159
		// (set) Token: 0x0600026F RID: 623 RVA: 0x00003F61 File Offset: 0x00002161
		internal virtual FlatLabel FlatLabel6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000D2 RID: 210
		// (get) Token: 0x06000270 RID: 624 RVA: 0x00003F6A File Offset: 0x0000216A
		// (set) Token: 0x06000271 RID: 625 RVA: 0x00003F72 File Offset: 0x00002172
		internal virtual FlatLabel FlatLabel7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000D3 RID: 211
		// (get) Token: 0x06000272 RID: 626 RVA: 0x00003F7B File Offset: 0x0000217B
		// (set) Token: 0x06000273 RID: 627 RVA: 0x0001B878 File Offset: 0x00019A78
		internal virtual FlatTrackBar TrackEnd
		{
			[CompilerGenerated]
			get
			{
				return this.TrackEnd;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.TrackEnd_Scroll);
				FlatTrackBar.ScrollEventHandler obj2 = new FlatTrackBar.ScrollEventHandler(this.TrackEnd_Scroll_1);
				FlatTrackBar trackEnd = this.TrackEnd;
				if (trackEnd != null)
				{
					trackEnd.Scroll -= obj;
					trackEnd.Scroll -= obj2;
				}
				this.TrackEnd = value;
				trackEnd = this.TrackEnd;
				if (trackEnd != null)
				{
					trackEnd.Scroll += obj;
					trackEnd.Scroll += obj2;
				}
			}
		}

		// Token: 0x170000D4 RID: 212
		// (get) Token: 0x06000274 RID: 628 RVA: 0x00003F83 File Offset: 0x00002183
		// (set) Token: 0x06000275 RID: 629 RVA: 0x00003F8B File Offset: 0x0000218B
		internal virtual FlatLabel FlatLabel5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000D5 RID: 213
		// (get) Token: 0x06000276 RID: 630 RVA: 0x00003F94 File Offset: 0x00002194
		// (set) Token: 0x06000277 RID: 631 RVA: 0x0001B8D8 File Offset: 0x00019AD8
		internal virtual FlatLabel FlatLabel4
		{
			[CompilerGenerated]
			get
			{
				return this.FlatLabel4;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatLabel4_Click);
				FlatLabel flatLabel = this.FlatLabel4;
				if (flatLabel != null)
				{
					flatLabel.Click -= value2;
				}
				this.FlatLabel4 = value;
				flatLabel = this.FlatLabel4;
				if (flatLabel != null)
				{
					flatLabel.Click += value2;
				}
			}
		}

		// Token: 0x170000D6 RID: 214
		// (get) Token: 0x06000278 RID: 632 RVA: 0x00003F9C File Offset: 0x0000219C
		// (set) Token: 0x06000279 RID: 633 RVA: 0x00003FA4 File Offset: 0x000021A4
		internal virtual FlatLabel FlatLabel3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000D7 RID: 215
		// (get) Token: 0x0600027A RID: 634 RVA: 0x00003FAD File Offset: 0x000021AD
		// (set) Token: 0x0600027B RID: 635 RVA: 0x00003FB5 File Offset: 0x000021B5
		internal virtual FlatLabel FlatLabel2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000D8 RID: 216
		// (get) Token: 0x0600027C RID: 636 RVA: 0x00003FBE File Offset: 0x000021BE
		// (set) Token: 0x0600027D RID: 637 RVA: 0x00003FC6 File Offset: 0x000021C6
		internal virtual FlatLabel FlatLabel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000D9 RID: 217
		// (get) Token: 0x0600027E RID: 638 RVA: 0x00003FCF File Offset: 0x000021CF
		// (set) Token: 0x0600027F RID: 639 RVA: 0x0001B91C File Offset: 0x00019B1C
		internal virtual FlatTrackBar TrackStart
		{
			[CompilerGenerated]
			get
			{
				return this.TrackStart;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.TrackStart_Scroll);
				FlatTrackBar trackStart = this.TrackStart;
				if (trackStart != null)
				{
					trackStart.Scroll -= obj;
				}
				this.TrackStart = value;
				trackStart = this.TrackStart;
				if (trackStart != null)
				{
					trackStart.Scroll += obj;
				}
			}
		}

		// Token: 0x170000DA RID: 218
		// (get) Token: 0x06000280 RID: 640 RVA: 0x00003FD7 File Offset: 0x000021D7
		// (set) Token: 0x06000281 RID: 641 RVA: 0x00003FDF File Offset: 0x000021DF
		internal virtual FlatTabControl FlatTabControl2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000DB RID: 219
		// (get) Token: 0x06000282 RID: 642 RVA: 0x00003FE8 File Offset: 0x000021E8
		// (set) Token: 0x06000283 RID: 643 RVA: 0x0001B960 File Offset: 0x00019B60
		internal virtual TabPage TabPage5
		{
			[CompilerGenerated]
			get
			{
				return this.TabPage5;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TabPage5_Click);
				TabPage tabPage = this.TabPage5;
				if (tabPage != null)
				{
					tabPage.Click -= value2;
				}
				this.TabPage5 = value;
				tabPage = this.TabPage5;
				if (tabPage != null)
				{
					tabPage.Click += value2;
				}
			}
		}

		// Token: 0x170000DC RID: 220
		// (get) Token: 0x06000284 RID: 644 RVA: 0x00003FF0 File Offset: 0x000021F0
		// (set) Token: 0x06000285 RID: 645 RVA: 0x00003FF8 File Offset: 0x000021F8
		internal virtual FlatLabel FlatLabel8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000DD RID: 221
		// (get) Token: 0x06000286 RID: 646 RVA: 0x00004001 File Offset: 0x00002201
		// (set) Token: 0x06000287 RID: 647 RVA: 0x0001B9A4 File Offset: 0x00019BA4
		internal virtual FlatTrackBar FlatTrackBar1
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar1_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar1;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar1 = value;
				flatTrackBar = this.FlatTrackBar1;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x170000DE RID: 222
		// (get) Token: 0x06000288 RID: 648 RVA: 0x00004009 File Offset: 0x00002209
		// (set) Token: 0x06000289 RID: 649 RVA: 0x00004011 File Offset: 0x00002211
		internal virtual FlatLabel FlatLabel10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000DF RID: 223
		// (get) Token: 0x0600028A RID: 650 RVA: 0x0000401A File Offset: 0x0000221A
		// (set) Token: 0x0600028B RID: 651 RVA: 0x00004022 File Offset: 0x00002222
		internal virtual FlatLabel FlatLabel11 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000E0 RID: 224
		// (get) Token: 0x0600028C RID: 652 RVA: 0x0000402B File Offset: 0x0000222B
		// (set) Token: 0x0600028D RID: 653 RVA: 0x0001B9E8 File Offset: 0x00019BE8
		internal virtual FlatTrackBar FlatTrackBar2
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar2_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar2;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar2 = value;
				flatTrackBar = this.FlatTrackBar2;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x170000E1 RID: 225
		// (get) Token: 0x0600028E RID: 654 RVA: 0x00004033 File Offset: 0x00002233
		// (set) Token: 0x0600028F RID: 655 RVA: 0x0000403B File Offset: 0x0000223B
		internal virtual FlatLabel FlatLabel12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000E2 RID: 226
		// (get) Token: 0x06000290 RID: 656 RVA: 0x00004044 File Offset: 0x00002244
		// (set) Token: 0x06000291 RID: 657 RVA: 0x0000404C File Offset: 0x0000224C
		internal virtual FlatLabel FlatLabel13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000E3 RID: 227
		// (get) Token: 0x06000292 RID: 658 RVA: 0x00004055 File Offset: 0x00002255
		// (set) Token: 0x06000293 RID: 659 RVA: 0x0000405D File Offset: 0x0000225D
		internal virtual TabPage TabPage6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000E4 RID: 228
		// (get) Token: 0x06000294 RID: 660 RVA: 0x00004066 File Offset: 0x00002266
		// (set) Token: 0x06000295 RID: 661 RVA: 0x0000406E File Offset: 0x0000226E
		internal virtual FlatCheckBox FlatCheckBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000E5 RID: 229
		// (get) Token: 0x06000296 RID: 662 RVA: 0x00004077 File Offset: 0x00002277
		// (set) Token: 0x06000297 RID: 663 RVA: 0x0000407F File Offset: 0x0000227F
		internal virtual FlatCheckBox FlatCheckBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000E6 RID: 230
		// (get) Token: 0x06000298 RID: 664 RVA: 0x00004088 File Offset: 0x00002288
		// (set) Token: 0x06000299 RID: 665 RVA: 0x00004090 File Offset: 0x00002290
		internal virtual FlatCheckBox FlatCheckBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000E7 RID: 231
		// (get) Token: 0x0600029A RID: 666 RVA: 0x00004099 File Offset: 0x00002299
		// (set) Token: 0x0600029B RID: 667 RVA: 0x000040A1 File Offset: 0x000022A1
		internal virtual FlatCheckBox FlatCheckBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000E8 RID: 232
		// (get) Token: 0x0600029C RID: 668 RVA: 0x000040AA File Offset: 0x000022AA
		// (set) Token: 0x0600029D RID: 669 RVA: 0x000040B2 File Offset: 0x000022B2
		internal virtual FlatCheckBox FlatCheckBox5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000E9 RID: 233
		// (get) Token: 0x0600029E RID: 670 RVA: 0x000040BB File Offset: 0x000022BB
		// (set) Token: 0x0600029F RID: 671 RVA: 0x0001BA2C File Offset: 0x00019C2C
		internal virtual FlatCheckBox FlatCheckBox6
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox6;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox6_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox6;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox6 = value;
				flatCheckBox = this.FlatCheckBox6;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x170000EA RID: 234
		// (get) Token: 0x060002A0 RID: 672 RVA: 0x000040C3 File Offset: 0x000022C3
		// (set) Token: 0x060002A1 RID: 673 RVA: 0x0001BA70 File Offset: 0x00019C70
		internal virtual FlatComboBox FlatComboBox1
		{
			[CompilerGenerated]
			get
			{
				return this.FlatComboBox1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatComboBox1_SelectedIndexChanged);
				FlatComboBox flatComboBox = this.FlatComboBox1;
				if (flatComboBox != null)
				{
					flatComboBox.SelectedIndexChanged -= value2;
				}
				this.FlatComboBox1 = value;
				flatComboBox = this.FlatComboBox1;
				if (flatComboBox != null)
				{
					flatComboBox.SelectedIndexChanged += value2;
				}
			}
		}

		// Token: 0x170000EB RID: 235
		// (get) Token: 0x060002A2 RID: 674 RVA: 0x000040CB File Offset: 0x000022CB
		// (set) Token: 0x060002A3 RID: 675 RVA: 0x000040D3 File Offset: 0x000022D3
		internal virtual FlatLabel FlatLabel14 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000EC RID: 236
		// (get) Token: 0x060002A4 RID: 676 RVA: 0x000040DC File Offset: 0x000022DC
		// (set) Token: 0x060002A5 RID: 677 RVA: 0x000040E4 File Offset: 0x000022E4
		internal virtual FlatLabel FlatLabel9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000ED RID: 237
		// (get) Token: 0x060002A6 RID: 678 RVA: 0x000040ED File Offset: 0x000022ED
		// (set) Token: 0x060002A7 RID: 679 RVA: 0x0001BAB4 File Offset: 0x00019CB4
		internal virtual FlatTrackBar FlatTrackBar3
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar3;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar3_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar3;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar3 = value;
				flatTrackBar = this.FlatTrackBar3;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x170000EE RID: 238
		// (get) Token: 0x060002A8 RID: 680 RVA: 0x000040F5 File Offset: 0x000022F5
		// (set) Token: 0x060002A9 RID: 681 RVA: 0x000040FD File Offset: 0x000022FD
		internal virtual FlatLabel FlatLabel15 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000EF RID: 239
		// (get) Token: 0x060002AA RID: 682 RVA: 0x00004106 File Offset: 0x00002306
		// (set) Token: 0x060002AB RID: 683 RVA: 0x0001BAF8 File Offset: 0x00019CF8
		internal virtual FlatTrackBar FlatTrackBar4
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar4;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar4_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar4;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar4 = value;
				flatTrackBar = this.FlatTrackBar4;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x170000F0 RID: 240
		// (get) Token: 0x060002AC RID: 684 RVA: 0x0000410E File Offset: 0x0000230E
		// (set) Token: 0x060002AD RID: 685 RVA: 0x00004116 File Offset: 0x00002316
		internal virtual FlatLabel FlatLabel16 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000F1 RID: 241
		// (get) Token: 0x060002AE RID: 686 RVA: 0x0000411F File Offset: 0x0000231F
		// (set) Token: 0x060002AF RID: 687 RVA: 0x00004127 File Offset: 0x00002327
		internal virtual FlatCheckBox FlatCheckBox8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000F2 RID: 242
		// (get) Token: 0x060002B0 RID: 688 RVA: 0x00004130 File Offset: 0x00002330
		// (set) Token: 0x060002B1 RID: 689 RVA: 0x0001BB3C File Offset: 0x00019D3C
		internal virtual FlatCheckBox FlatCheckBox7
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox7;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox7_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox7;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox7 = value;
				flatCheckBox = this.FlatCheckBox7;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x170000F3 RID: 243
		// (get) Token: 0x060002B2 RID: 690 RVA: 0x00004138 File Offset: 0x00002338
		// (set) Token: 0x060002B3 RID: 691 RVA: 0x0001BB80 File Offset: 0x00019D80
		internal virtual FlatTabControl FlatTabControl3
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTabControl3;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatTabControl3_Click);
				FlatTabControl flatTabControl = this.FlatTabControl3;
				if (flatTabControl != null)
				{
					flatTabControl.Click -= value2;
				}
				this.FlatTabControl3 = value;
				flatTabControl = this.FlatTabControl3;
				if (flatTabControl != null)
				{
					flatTabControl.Click += value2;
				}
			}
		}

		// Token: 0x170000F4 RID: 244
		// (get) Token: 0x060002B4 RID: 692 RVA: 0x00004140 File Offset: 0x00002340
		// (set) Token: 0x060002B5 RID: 693 RVA: 0x0001BBC4 File Offset: 0x00019DC4
		internal virtual TabPage TabPage7
		{
			[CompilerGenerated]
			get
			{
				return this.TabPage7;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TabPage7_Click);
				TabPage tabPage = this.TabPage7;
				if (tabPage != null)
				{
					tabPage.Click -= value2;
				}
				this.TabPage7 = value;
				tabPage = this.TabPage7;
				if (tabPage != null)
				{
					tabPage.Click += value2;
				}
			}
		}

		// Token: 0x170000F5 RID: 245
		// (get) Token: 0x060002B6 RID: 694 RVA: 0x00004148 File Offset: 0x00002348
		// (set) Token: 0x060002B7 RID: 695 RVA: 0x00004150 File Offset: 0x00002350
		internal virtual ColorDialog ColorDialog1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000F6 RID: 246
		// (get) Token: 0x060002B8 RID: 696 RVA: 0x00004159 File Offset: 0x00002359
		// (set) Token: 0x060002B9 RID: 697 RVA: 0x00004161 File Offset: 0x00002361
		internal virtual Process Process1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000F7 RID: 247
		// (get) Token: 0x060002BA RID: 698 RVA: 0x0000416A File Offset: 0x0000236A
		// (set) Token: 0x060002BB RID: 699 RVA: 0x00004172 File Offset: 0x00002372
		internal virtual OpenFileDialog OpenFileDialog1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000F8 RID: 248
		// (get) Token: 0x060002BC RID: 700 RVA: 0x0000417B File Offset: 0x0000237B
		// (set) Token: 0x060002BD RID: 701 RVA: 0x0001BC08 File Offset: 0x00019E08
		internal virtual FlatCheckBox FlatCheckBox11
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox11;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox11_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox11;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox11 = value;
				flatCheckBox = this.FlatCheckBox11;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x170000F9 RID: 249
		// (get) Token: 0x060002BE RID: 702 RVA: 0x00004183 File Offset: 0x00002383
		// (set) Token: 0x060002BF RID: 703 RVA: 0x0000418B File Offset: 0x0000238B
		internal virtual FlatCheckBox FlatCheckBox10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000FA RID: 250
		// (get) Token: 0x060002C0 RID: 704 RVA: 0x00004194 File Offset: 0x00002394
		// (set) Token: 0x060002C1 RID: 705 RVA: 0x0000419C File Offset: 0x0000239C
		internal virtual FlatCheckBox FlatCheckBox9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000FB RID: 251
		// (get) Token: 0x060002C2 RID: 706 RVA: 0x000041A5 File Offset: 0x000023A5
		// (set) Token: 0x060002C3 RID: 707 RVA: 0x000041AD File Offset: 0x000023AD
		internal virtual FlatTabControl FlatTabControl4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000FC RID: 252
		// (get) Token: 0x060002C4 RID: 708 RVA: 0x000041B6 File Offset: 0x000023B6
		// (set) Token: 0x060002C5 RID: 709 RVA: 0x000041BE File Offset: 0x000023BE
		internal virtual TabPage TabPage8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000FD RID: 253
		// (get) Token: 0x060002C6 RID: 710 RVA: 0x000041C7 File Offset: 0x000023C7
		// (set) Token: 0x060002C7 RID: 711 RVA: 0x000041CF File Offset: 0x000023CF
		internal virtual TabPage TabPage9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000FE RID: 254
		// (get) Token: 0x060002C8 RID: 712 RVA: 0x000041D8 File Offset: 0x000023D8
		// (set) Token: 0x060002C9 RID: 713 RVA: 0x0001BC4C File Offset: 0x00019E4C
		internal virtual System.Windows.Forms.Timer Timer1
		{
			[CompilerGenerated]
			get
			{
				return this.Timer1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer1_Tick);
				System.Windows.Forms.Timer timer = this.Timer1;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this.Timer1 = value;
				timer = this.Timer1;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x170000FF RID: 255
		// (get) Token: 0x060002CA RID: 714 RVA: 0x000041E0 File Offset: 0x000023E0
		// (set) Token: 0x060002CB RID: 715 RVA: 0x0001BC90 File Offset: 0x00019E90
		internal virtual System.Windows.Forms.Timer Timer3
		{
			[CompilerGenerated]
			get
			{
				return this.Timer3;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer3_Tick);
				System.Windows.Forms.Timer timer = this.Timer3;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this.Timer3 = value;
				timer = this.Timer3;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x17000100 RID: 256
		// (get) Token: 0x060002CC RID: 716 RVA: 0x000041E8 File Offset: 0x000023E8
		// (set) Token: 0x060002CD RID: 717 RVA: 0x0001BCD4 File Offset: 0x00019ED4
		internal virtual FlatMini FlatMini1
		{
			[CompilerGenerated]
			get
			{
				return this.FlatMini1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatMini1_Click);
				FlatMini flatMini = this.FlatMini1;
				if (flatMini != null)
				{
					flatMini.Click -= value2;
				}
				this.FlatMini1 = value;
				flatMini = this.FlatMini1;
				if (flatMini != null)
				{
					flatMini.Click += value2;
				}
			}
		}

		// Token: 0x17000101 RID: 257
		// (get) Token: 0x060002CE RID: 718 RVA: 0x000041F0 File Offset: 0x000023F0
		// (set) Token: 0x060002CF RID: 719 RVA: 0x000041F8 File Offset: 0x000023F8
		internal virtual FlatLabel FlatLabel22 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000102 RID: 258
		// (get) Token: 0x060002D0 RID: 720 RVA: 0x00004201 File Offset: 0x00002401
		// (set) Token: 0x060002D1 RID: 721 RVA: 0x00004209 File Offset: 0x00002409
		internal virtual FlatLabel FlatLabel18 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000103 RID: 259
		// (get) Token: 0x060002D2 RID: 722 RVA: 0x00004212 File Offset: 0x00002412
		// (set) Token: 0x060002D3 RID: 723 RVA: 0x0001BD18 File Offset: 0x00019F18
		internal virtual FlatTrackBar FlatTrackBar5
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar5;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar5_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar5;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar5 = value;
				flatTrackBar = this.FlatTrackBar5;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000104 RID: 260
		// (get) Token: 0x060002D4 RID: 724 RVA: 0x0000421A File Offset: 0x0000241A
		// (set) Token: 0x060002D5 RID: 725 RVA: 0x0001BD5C File Offset: 0x00019F5C
		internal virtual FlatLabel FlatLabel19
		{
			[CompilerGenerated]
			get
			{
				return this.FlatLabel19;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatLabel19_Click);
				FlatLabel flatLabel = this.FlatLabel19;
				if (flatLabel != null)
				{
					flatLabel.Click -= value2;
				}
				this.FlatLabel19 = value;
				flatLabel = this.FlatLabel19;
				if (flatLabel != null)
				{
					flatLabel.Click += value2;
				}
			}
		}

		// Token: 0x17000105 RID: 261
		// (get) Token: 0x060002D6 RID: 726 RVA: 0x00004222 File Offset: 0x00002422
		// (set) Token: 0x060002D7 RID: 727 RVA: 0x0000422A File Offset: 0x0000242A
		internal virtual FlatLabel FlatLabel20 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000106 RID: 262
		// (get) Token: 0x060002D8 RID: 728 RVA: 0x00004233 File Offset: 0x00002433
		// (set) Token: 0x060002D9 RID: 729 RVA: 0x0001BDA0 File Offset: 0x00019FA0
		internal virtual FlatTrackBar FlatTrackBar6
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar6;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar6_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar6;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar6 = value;
				flatTrackBar = this.FlatTrackBar6;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000107 RID: 263
		// (get) Token: 0x060002DA RID: 730 RVA: 0x0000423B File Offset: 0x0000243B
		// (set) Token: 0x060002DB RID: 731 RVA: 0x00004243 File Offset: 0x00002443
		internal virtual FlatLabel FlatLabel21 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000108 RID: 264
		// (get) Token: 0x060002DC RID: 732 RVA: 0x0000424C File Offset: 0x0000244C
		// (set) Token: 0x060002DD RID: 733 RVA: 0x0001BDE4 File Offset: 0x00019FE4
		internal virtual Button Button1
		{
			[CompilerGenerated]
			get
			{
				return this.Button1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button1_Click);
				Button button = this.Button1;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button1 = value;
				button = this.Button1;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000109 RID: 265
		// (get) Token: 0x060002DE RID: 734 RVA: 0x00004254 File Offset: 0x00002454
		// (set) Token: 0x060002DF RID: 735 RVA: 0x0000425C File Offset: 0x0000245C
		internal virtual FlatLabel FlatLabel23 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700010A RID: 266
		// (get) Token: 0x060002E0 RID: 736 RVA: 0x00004265 File Offset: 0x00002465
		// (set) Token: 0x060002E1 RID: 737 RVA: 0x0000426D File Offset: 0x0000246D
		internal virtual FlatLabel FlatLabel24 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700010B RID: 267
		// (get) Token: 0x060002E2 RID: 738 RVA: 0x00004276 File Offset: 0x00002476
		// (set) Token: 0x060002E3 RID: 739 RVA: 0x0001BE28 File Offset: 0x0001A028
		internal virtual FlatTrackBar FlatTrackBar7
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar7;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar7_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar7;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar7 = value;
				flatTrackBar = this.FlatTrackBar7;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x1700010C RID: 268
		// (get) Token: 0x060002E4 RID: 740 RVA: 0x0000427E File Offset: 0x0000247E
		// (set) Token: 0x060002E5 RID: 741 RVA: 0x0001BE6C File Offset: 0x0001A06C
		internal virtual Button Button2
		{
			[CompilerGenerated]
			get
			{
				return this.Button2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button2_Click);
				Button button = this.Button2;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button2 = value;
				button = this.Button2;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x1700010D RID: 269
		// (get) Token: 0x060002E6 RID: 742 RVA: 0x00004286 File Offset: 0x00002486
		// (set) Token: 0x060002E7 RID: 743 RVA: 0x0001BEB0 File Offset: 0x0001A0B0
		internal virtual FlatCheckBox FlatCheckBox13
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox13;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox13_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox13;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox13 = value;
				flatCheckBox = this.FlatCheckBox13;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x1700010E RID: 270
		// (get) Token: 0x060002E8 RID: 744 RVA: 0x0000428E File Offset: 0x0000248E
		// (set) Token: 0x060002E9 RID: 745 RVA: 0x0001BEF4 File Offset: 0x0001A0F4
		internal virtual FlatCheckBox FlatCheckBox14
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox14;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox14_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox14;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox14 = value;
				flatCheckBox = this.FlatCheckBox14;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x1700010F RID: 271
		// (get) Token: 0x060002EA RID: 746 RVA: 0x00004296 File Offset: 0x00002496
		// (set) Token: 0x060002EB RID: 747 RVA: 0x0001BF38 File Offset: 0x0001A138
		internal virtual NotifyIcon NotifyIcon1
		{
			[CompilerGenerated]
			get
			{
				return this.NotifyIcon1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				MouseEventHandler value2 = new MouseEventHandler(this.NotifyIcon1_MouseDoubleClick);
				NotifyIcon notifyIcon = this.NotifyIcon1;
				if (notifyIcon != null)
				{
					notifyIcon.MouseDoubleClick -= value2;
				}
				this.NotifyIcon1 = value;
				notifyIcon = this.NotifyIcon1;
				if (notifyIcon != null)
				{
					notifyIcon.MouseDoubleClick += value2;
				}
			}
		}

		// Token: 0x17000110 RID: 272
		// (get) Token: 0x060002EC RID: 748 RVA: 0x0000429E File Offset: 0x0000249E
		// (set) Token: 0x060002ED RID: 749 RVA: 0x0001BF7C File Offset: 0x0001A17C
		internal virtual FlatCheckBox FlatCheckBox18
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox18;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox18_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox18;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox18 = value;
				flatCheckBox = this.FlatCheckBox18;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x17000111 RID: 273
		// (get) Token: 0x060002EE RID: 750 RVA: 0x000042A6 File Offset: 0x000024A6
		// (set) Token: 0x060002EF RID: 751 RVA: 0x000042AE File Offset: 0x000024AE
		internal virtual FlatCheckBox Aimassist { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000112 RID: 274
		// (get) Token: 0x060002F0 RID: 752 RVA: 0x000042B7 File Offset: 0x000024B7
		// (set) Token: 0x060002F1 RID: 753 RVA: 0x000042BF File Offset: 0x000024BF
		internal virtual FlatCheckBox FlatCheckBox16 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000113 RID: 275
		// (get) Token: 0x060002F2 RID: 754 RVA: 0x000042C8 File Offset: 0x000024C8
		// (set) Token: 0x060002F3 RID: 755 RVA: 0x000042D0 File Offset: 0x000024D0
		internal virtual FlatCheckBox FlatCheckBox15 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000114 RID: 276
		// (get) Token: 0x060002F4 RID: 756 RVA: 0x000042D9 File Offset: 0x000024D9
		// (set) Token: 0x060002F5 RID: 757 RVA: 0x000042E1 File Offset: 0x000024E1
		internal virtual ToolTip ToolTip1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000115 RID: 277
		// (get) Token: 0x060002F6 RID: 758 RVA: 0x000042EA File Offset: 0x000024EA
		// (set) Token: 0x060002F7 RID: 759 RVA: 0x000042F2 File Offset: 0x000024F2
		internal virtual FlatCheckBox FlatCheckBox20 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000116 RID: 278
		// (get) Token: 0x060002F8 RID: 760 RVA: 0x000042FB File Offset: 0x000024FB
		// (set) Token: 0x060002F9 RID: 761 RVA: 0x00004303 File Offset: 0x00002503
		internal virtual FlatCheckBox FlatCheckBox19 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000117 RID: 279
		// (get) Token: 0x060002FA RID: 762 RVA: 0x0000430C File Offset: 0x0000250C
		// (set) Token: 0x060002FB RID: 763 RVA: 0x0001BFC0 File Offset: 0x0001A1C0
		internal virtual TabPage TabPage10
		{
			[CompilerGenerated]
			get
			{
				return this.TabPage10;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TabPage10_Click);
				TabPage tabPage = this.TabPage10;
				if (tabPage != null)
				{
					tabPage.Click -= value2;
				}
				this.TabPage10 = value;
				tabPage = this.TabPage10;
				if (tabPage != null)
				{
					tabPage.Click += value2;
				}
			}
		}

		// Token: 0x17000118 RID: 280
		// (get) Token: 0x060002FC RID: 764 RVA: 0x00004314 File Offset: 0x00002514
		// (set) Token: 0x060002FD RID: 765 RVA: 0x0001C004 File Offset: 0x0001A204
		internal virtual FlatButton FlatButton5
		{
			[CompilerGenerated]
			get
			{
				return this.FlatButton5;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatButton5_Click);
				FlatButton flatButton = this.FlatButton5;
				if (flatButton != null)
				{
					flatButton.Click -= value2;
				}
				this.FlatButton5 = value;
				flatButton = this.FlatButton5;
				if (flatButton != null)
				{
					flatButton.Click += value2;
				}
			}
		}

		// Token: 0x17000119 RID: 281
		// (get) Token: 0x060002FE RID: 766 RVA: 0x0000431C File Offset: 0x0000251C
		// (set) Token: 0x060002FF RID: 767 RVA: 0x0001C048 File Offset: 0x0001A248
		internal virtual FlatButton FlatButton6
		{
			[CompilerGenerated]
			get
			{
				return this.FlatButton6;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatButton6_Click);
				FlatButton flatButton = this.FlatButton6;
				if (flatButton != null)
				{
					flatButton.Click -= value2;
				}
				this.FlatButton6 = value;
				flatButton = this.FlatButton6;
				if (flatButton != null)
				{
					flatButton.Click += value2;
				}
			}
		}

		// Token: 0x1700011A RID: 282
		// (get) Token: 0x06000300 RID: 768 RVA: 0x00004324 File Offset: 0x00002524
		// (set) Token: 0x06000301 RID: 769 RVA: 0x0000432C File Offset: 0x0000252C
		internal virtual FlatLabel FlatLabel25 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700011B RID: 283
		// (get) Token: 0x06000302 RID: 770 RVA: 0x00004335 File Offset: 0x00002535
		// (set) Token: 0x06000303 RID: 771 RVA: 0x0001C08C File Offset: 0x0001A28C
		internal virtual FlatButton FlatButton7
		{
			[CompilerGenerated]
			get
			{
				return this.FlatButton7;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatButton7_Click);
				FlatButton flatButton = this.FlatButton7;
				if (flatButton != null)
				{
					flatButton.Click -= value2;
				}
				this.FlatButton7 = value;
				flatButton = this.FlatButton7;
				if (flatButton != null)
				{
					flatButton.Click += value2;
				}
			}
		}

		// Token: 0x1700011C RID: 284
		// (get) Token: 0x06000304 RID: 772 RVA: 0x0000433D File Offset: 0x0000253D
		// (set) Token: 0x06000305 RID: 773 RVA: 0x00004345 File Offset: 0x00002545
		internal virtual FlatTextBox FlatTextBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700011D RID: 285
		// (get) Token: 0x06000306 RID: 774 RVA: 0x0000434E File Offset: 0x0000254E
		// (set) Token: 0x06000307 RID: 775 RVA: 0x0001C0D0 File Offset: 0x0001A2D0
		internal virtual System.Windows.Forms.Timer Timer2
		{
			[CompilerGenerated]
			get
			{
				return this.Timer2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer2_Tick);
				System.Windows.Forms.Timer timer = this.Timer2;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this.Timer2 = value;
				timer = this.Timer2;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x1700011E RID: 286
		// (get) Token: 0x06000308 RID: 776 RVA: 0x00004356 File Offset: 0x00002556
		// (set) Token: 0x06000309 RID: 777 RVA: 0x0001C114 File Offset: 0x0001A314
		internal virtual System.Windows.Forms.Timer CheckEvents
		{
			[CompilerGenerated]
			get
			{
				return this.CheckEvents;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.CheckEvents_Tick);
				System.Windows.Forms.Timer checkEvents = this.CheckEvents;
				if (checkEvents != null)
				{
					checkEvents.Tick -= value2;
				}
				this.CheckEvents = value;
				checkEvents = this.CheckEvents;
				if (checkEvents != null)
				{
					checkEvents.Tick += value2;
				}
			}
		}

		// Token: 0x1700011F RID: 287
		// (get) Token: 0x0600030A RID: 778 RVA: 0x0000435E File Offset: 0x0000255E
		// (set) Token: 0x0600030B RID: 779 RVA: 0x0001C158 File Offset: 0x0001A358
		internal virtual Button Button8
		{
			[CompilerGenerated]
			get
			{
				return this.Button8;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button8_Click);
				Button button = this.Button8;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button8 = value;
				button = this.Button8;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000120 RID: 288
		// (get) Token: 0x0600030C RID: 780 RVA: 0x00004366 File Offset: 0x00002566
		// (set) Token: 0x0600030D RID: 781 RVA: 0x0000436E File Offset: 0x0000256E
		internal virtual FlatLabel FlatLabel26 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000121 RID: 289
		// (get) Token: 0x0600030E RID: 782 RVA: 0x00004377 File Offset: 0x00002577
		// (set) Token: 0x0600030F RID: 783 RVA: 0x0001C19C File Offset: 0x0001A39C
		internal virtual Button Button7
		{
			[CompilerGenerated]
			get
			{
				return this.Button7;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button7_Click);
				Button button = this.Button7;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button7 = value;
				button = this.Button7;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000122 RID: 290
		// (get) Token: 0x06000310 RID: 784 RVA: 0x0000437F File Offset: 0x0000257F
		// (set) Token: 0x06000311 RID: 785 RVA: 0x00004387 File Offset: 0x00002587
		internal virtual FlatLabel FlatLabel27 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000123 RID: 291
		// (get) Token: 0x06000312 RID: 786 RVA: 0x00004390 File Offset: 0x00002590
		// (set) Token: 0x06000313 RID: 787 RVA: 0x00004398 File Offset: 0x00002598
		internal virtual FlatComboBox FlatComboBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000124 RID: 292
		// (get) Token: 0x06000314 RID: 788 RVA: 0x000043A1 File Offset: 0x000025A1
		// (set) Token: 0x06000315 RID: 789 RVA: 0x000043A9 File Offset: 0x000025A9
		internal virtual FlatLabel FlatLabel29 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000125 RID: 293
		// (get) Token: 0x06000316 RID: 790 RVA: 0x000043B2 File Offset: 0x000025B2
		// (set) Token: 0x06000317 RID: 791 RVA: 0x000043BA File Offset: 0x000025BA
		internal virtual FlatLabel FlatLabel28 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000126 RID: 294
		// (get) Token: 0x06000318 RID: 792 RVA: 0x000043C3 File Offset: 0x000025C3
		// (set) Token: 0x06000319 RID: 793 RVA: 0x0001C1E0 File Offset: 0x0001A3E0
		internal virtual Button Button10
		{
			[CompilerGenerated]
			get
			{
				return this.Button10;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button10_Click);
				Button button = this.Button10;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button10 = value;
				button = this.Button10;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000127 RID: 295
		// (get) Token: 0x0600031A RID: 794 RVA: 0x000043CB File Offset: 0x000025CB
		// (set) Token: 0x0600031B RID: 795 RVA: 0x0001C224 File Offset: 0x0001A424
		internal virtual Button Button9
		{
			[CompilerGenerated]
			get
			{
				return this.Button9;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button9_Click);
				Button button = this.Button9;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button9 = value;
				button = this.Button9;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000128 RID: 296
		// (get) Token: 0x0600031C RID: 796 RVA: 0x000043D3 File Offset: 0x000025D3
		// (set) Token: 0x0600031D RID: 797 RVA: 0x000043DB File Offset: 0x000025DB
		internal virtual FlatLabel FlatLabel31 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000129 RID: 297
		// (get) Token: 0x0600031E RID: 798 RVA: 0x000043E4 File Offset: 0x000025E4
		// (set) Token: 0x0600031F RID: 799 RVA: 0x0001C268 File Offset: 0x0001A468
		internal virtual FlatLabel FlatLabel30
		{
			[CompilerGenerated]
			get
			{
				return this.FlatLabel30;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatLabel30_Click);
				FlatLabel flatLabel = this.FlatLabel30;
				if (flatLabel != null)
				{
					flatLabel.Click -= value2;
				}
				this.FlatLabel30 = value;
				flatLabel = this.FlatLabel30;
				if (flatLabel != null)
				{
					flatLabel.Click += value2;
				}
			}
		}

		// Token: 0x1700012A RID: 298
		// (get) Token: 0x06000320 RID: 800 RVA: 0x000043EC File Offset: 0x000025EC
		// (set) Token: 0x06000321 RID: 801 RVA: 0x000043F4 File Offset: 0x000025F4
		internal virtual FlatLabel FlatLabel32 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700012B RID: 299
		// (get) Token: 0x06000322 RID: 802 RVA: 0x000043FD File Offset: 0x000025FD
		// (set) Token: 0x06000323 RID: 803 RVA: 0x00004405 File Offset: 0x00002605
		internal virtual FlatTabControl FlatTabControl5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700012C RID: 300
		// (get) Token: 0x06000324 RID: 804 RVA: 0x0000440E File Offset: 0x0000260E
		// (set) Token: 0x06000325 RID: 805 RVA: 0x00004416 File Offset: 0x00002616
		internal virtual TabPage TabPage11 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700012D RID: 301
		// (get) Token: 0x06000326 RID: 806 RVA: 0x0000441F File Offset: 0x0000261F
		// (set) Token: 0x06000327 RID: 807 RVA: 0x00004427 File Offset: 0x00002627
		internal virtual FlatLabel FlatLabel33 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700012E RID: 302
		// (get) Token: 0x06000328 RID: 808 RVA: 0x00004430 File Offset: 0x00002630
		// (set) Token: 0x06000329 RID: 809 RVA: 0x00004438 File Offset: 0x00002638
		internal virtual FlatLabel FlatLabel34 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700012F RID: 303
		// (get) Token: 0x0600032A RID: 810 RVA: 0x00004441 File Offset: 0x00002641
		// (set) Token: 0x0600032B RID: 811 RVA: 0x0001C2AC File Offset: 0x0001A4AC
		internal virtual FlatLabel FlatLabel35
		{
			[CompilerGenerated]
			get
			{
				return this.FlatLabel35;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatLabel35_Click);
				FlatLabel flatLabel = this.FlatLabel35;
				if (flatLabel != null)
				{
					flatLabel.Click -= value2;
				}
				this.FlatLabel35 = value;
				flatLabel = this.FlatLabel35;
				if (flatLabel != null)
				{
					flatLabel.Click += value2;
				}
			}
		}

		// Token: 0x17000130 RID: 304
		// (get) Token: 0x0600032C RID: 812 RVA: 0x00004449 File Offset: 0x00002649
		// (set) Token: 0x0600032D RID: 813 RVA: 0x0001C2F0 File Offset: 0x0001A4F0
		internal virtual FlatTrackBar FlatTrackBar8
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar8;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar8_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar8;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar8 = value;
				flatTrackBar = this.FlatTrackBar8;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000131 RID: 305
		// (get) Token: 0x0600032E RID: 814 RVA: 0x00004451 File Offset: 0x00002651
		// (set) Token: 0x0600032F RID: 815 RVA: 0x0001C334 File Offset: 0x0001A534
		internal virtual Button Button11
		{
			[CompilerGenerated]
			get
			{
				return this.Button11;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button11_Click);
				Button button = this.Button11;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button11 = value;
				button = this.Button11;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000132 RID: 306
		// (get) Token: 0x06000330 RID: 816 RVA: 0x00004459 File Offset: 0x00002659
		// (set) Token: 0x06000331 RID: 817 RVA: 0x00004461 File Offset: 0x00002661
		internal virtual FlatLabel FlatLabel36 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000133 RID: 307
		// (get) Token: 0x06000332 RID: 818 RVA: 0x0000446A File Offset: 0x0000266A
		// (set) Token: 0x06000333 RID: 819 RVA: 0x00004472 File Offset: 0x00002672
		internal virtual FlatComboBox FlatComboBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000134 RID: 308
		// (get) Token: 0x06000334 RID: 820 RVA: 0x0000447B File Offset: 0x0000267B
		// (set) Token: 0x06000335 RID: 821 RVA: 0x0001C378 File Offset: 0x0001A578
		internal virtual System.Windows.Forms.Timer Timer4
		{
			[CompilerGenerated]
			get
			{
				return this.Timer4;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer4_Tick);
				System.Windows.Forms.Timer timer = this.Timer4;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this.Timer4 = value;
				timer = this.Timer4;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x17000135 RID: 309
		// (get) Token: 0x06000336 RID: 822 RVA: 0x00004483 File Offset: 0x00002683
		// (set) Token: 0x06000337 RID: 823 RVA: 0x0001C3BC File Offset: 0x0001A5BC
		internal virtual System.Windows.Forms.Timer FastplaceEvents
		{
			[CompilerGenerated]
			get
			{
				return this.FastplaceEvents;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FastplaceEvents_Tick);
				System.Windows.Forms.Timer fastplaceEvents = this.FastplaceEvents;
				if (fastplaceEvents != null)
				{
					fastplaceEvents.Tick -= value2;
				}
				this.FastplaceEvents = value;
				fastplaceEvents = this.FastplaceEvents;
				if (fastplaceEvents != null)
				{
					fastplaceEvents.Tick += value2;
				}
			}
		}

		// Token: 0x17000136 RID: 310
		// (get) Token: 0x06000338 RID: 824 RVA: 0x0000448B File Offset: 0x0000268B
		// (set) Token: 0x06000339 RID: 825 RVA: 0x0001C400 File Offset: 0x0001A600
		internal virtual Button Button12
		{
			[CompilerGenerated]
			get
			{
				return this.Button12;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button12_Click);
				Button button = this.Button12;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button12 = value;
				button = this.Button12;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000137 RID: 311
		// (get) Token: 0x0600033A RID: 826 RVA: 0x00004493 File Offset: 0x00002693
		// (set) Token: 0x0600033B RID: 827 RVA: 0x0000449B File Offset: 0x0000269B
		internal virtual TabPage TabPage12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000138 RID: 312
		// (get) Token: 0x0600033C RID: 828 RVA: 0x000044A4 File Offset: 0x000026A4
		// (set) Token: 0x0600033D RID: 829 RVA: 0x000044AC File Offset: 0x000026AC
		internal virtual FlatCheckBox FlatCheckBox21 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000139 RID: 313
		// (get) Token: 0x0600033E RID: 830 RVA: 0x000044B5 File Offset: 0x000026B5
		// (set) Token: 0x0600033F RID: 831 RVA: 0x000044BD File Offset: 0x000026BD
		internal virtual FlatLabel FlatLabel41 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700013A RID: 314
		// (get) Token: 0x06000340 RID: 832 RVA: 0x000044C6 File Offset: 0x000026C6
		// (set) Token: 0x06000341 RID: 833 RVA: 0x0001C444 File Offset: 0x0001A644
		internal virtual FlatLabel FlatLabel39
		{
			[CompilerGenerated]
			get
			{
				return this.FlatLabel39;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatLabel39_Click);
				FlatLabel flatLabel = this.FlatLabel39;
				if (flatLabel != null)
				{
					flatLabel.Click -= value2;
				}
				this.FlatLabel39 = value;
				flatLabel = this.FlatLabel39;
				if (flatLabel != null)
				{
					flatLabel.Click += value2;
				}
			}
		}

		// Token: 0x1700013B RID: 315
		// (get) Token: 0x06000342 RID: 834 RVA: 0x000044CE File Offset: 0x000026CE
		// (set) Token: 0x06000343 RID: 835 RVA: 0x000044D6 File Offset: 0x000026D6
		internal virtual FlatLabel FlatLabel17 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700013C RID: 316
		// (get) Token: 0x06000344 RID: 836 RVA: 0x000044DF File Offset: 0x000026DF
		// (set) Token: 0x06000345 RID: 837 RVA: 0x000044E7 File Offset: 0x000026E7
		internal virtual FlatLabel FlatLabel37 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700013D RID: 317
		// (get) Token: 0x06000346 RID: 838 RVA: 0x000044F0 File Offset: 0x000026F0
		// (set) Token: 0x06000347 RID: 839 RVA: 0x000044F8 File Offset: 0x000026F8
		internal virtual FlatLabel FlatLabel38 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700013E RID: 318
		// (get) Token: 0x06000348 RID: 840 RVA: 0x00004501 File Offset: 0x00002701
		// (set) Token: 0x06000349 RID: 841 RVA: 0x00004509 File Offset: 0x00002709
		internal virtual FlatLabel FlatLabel40 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700013F RID: 319
		// (get) Token: 0x0600034A RID: 842 RVA: 0x00004512 File Offset: 0x00002712
		// (set) Token: 0x0600034B RID: 843 RVA: 0x0001C488 File Offset: 0x0001A688
		internal virtual FlatTrackBar FlatTrackBar10
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar10;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar10_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar10;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar10 = value;
				flatTrackBar = this.FlatTrackBar10;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000140 RID: 320
		// (get) Token: 0x0600034C RID: 844 RVA: 0x0000451A File Offset: 0x0000271A
		// (set) Token: 0x0600034D RID: 845 RVA: 0x00004522 File Offset: 0x00002722
		internal virtual FlatLabel FlatLabel43 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000141 RID: 321
		// (get) Token: 0x0600034E RID: 846 RVA: 0x0000452B File Offset: 0x0000272B
		// (set) Token: 0x0600034F RID: 847 RVA: 0x0001C4CC File Offset: 0x0001A6CC
		internal virtual FlatLabel FlatLabel42
		{
			[CompilerGenerated]
			get
			{
				return this.FlatLabel42;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatLabel42_Click);
				FlatLabel flatLabel = this.FlatLabel42;
				if (flatLabel != null)
				{
					flatLabel.Click -= value2;
				}
				this.FlatLabel42 = value;
				flatLabel = this.FlatLabel42;
				if (flatLabel != null)
				{
					flatLabel.Click += value2;
				}
			}
		}

		// Token: 0x17000142 RID: 322
		// (get) Token: 0x06000350 RID: 848 RVA: 0x00004533 File Offset: 0x00002733
		// (set) Token: 0x06000351 RID: 849 RVA: 0x0000453B File Offset: 0x0000273B
		internal virtual TabPage TabPage14 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000143 RID: 323
		// (get) Token: 0x06000352 RID: 850 RVA: 0x00004544 File Offset: 0x00002744
		// (set) Token: 0x06000353 RID: 851 RVA: 0x0001C510 File Offset: 0x0001A710
		internal virtual Button Button13
		{
			[CompilerGenerated]
			get
			{
				return this.Button13;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button13_Click);
				Button button = this.Button13;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button13 = value;
				button = this.Button13;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000144 RID: 324
		// (get) Token: 0x06000354 RID: 852 RVA: 0x0000454C File Offset: 0x0000274C
		// (set) Token: 0x06000355 RID: 853 RVA: 0x00004554 File Offset: 0x00002754
		internal virtual FlatLabel FlatLabel44 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000145 RID: 325
		// (get) Token: 0x06000356 RID: 854 RVA: 0x0000455D File Offset: 0x0000275D
		// (set) Token: 0x06000357 RID: 855 RVA: 0x00004565 File Offset: 0x00002765
		internal virtual FlatComboBox FlatComboBox5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000146 RID: 326
		// (get) Token: 0x06000358 RID: 856 RVA: 0x0000456E File Offset: 0x0000276E
		// (set) Token: 0x06000359 RID: 857 RVA: 0x00004576 File Offset: 0x00002776
		internal virtual TabPage TabPage15 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000147 RID: 327
		// (get) Token: 0x0600035A RID: 858 RVA: 0x0000457F File Offset: 0x0000277F
		// (set) Token: 0x0600035B RID: 859 RVA: 0x00004587 File Offset: 0x00002787
		internal virtual FlatLabel FlatLabel45 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000148 RID: 328
		// (get) Token: 0x0600035C RID: 860 RVA: 0x00004590 File Offset: 0x00002790
		// (set) Token: 0x0600035D RID: 861 RVA: 0x00004598 File Offset: 0x00002798
		internal virtual FlatLabel FlatLabel46 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000149 RID: 329
		// (get) Token: 0x0600035E RID: 862 RVA: 0x000045A1 File Offset: 0x000027A1
		// (set) Token: 0x0600035F RID: 863 RVA: 0x000045A9 File Offset: 0x000027A9
		internal virtual FlatLabel FlatLabel47 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700014A RID: 330
		// (get) Token: 0x06000360 RID: 864 RVA: 0x000045B2 File Offset: 0x000027B2
		// (set) Token: 0x06000361 RID: 865 RVA: 0x0001C554 File Offset: 0x0001A754
		internal virtual FlatTrackBar FlatTrackBar9
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar9;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar9_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar9;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar9 = value;
				flatTrackBar = this.FlatTrackBar9;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x1700014B RID: 331
		// (get) Token: 0x06000362 RID: 866 RVA: 0x000045BA File Offset: 0x000027BA
		// (set) Token: 0x06000363 RID: 867 RVA: 0x0001C598 File Offset: 0x0001A798
		internal virtual FlatCheckBox FlatCheckBox23
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox23;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox23_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox23;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox23 = value;
				flatCheckBox = this.FlatCheckBox23;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x1700014C RID: 332
		// (get) Token: 0x06000364 RID: 868 RVA: 0x000045C2 File Offset: 0x000027C2
		// (set) Token: 0x06000365 RID: 869 RVA: 0x0001C5DC File Offset: 0x0001A7DC
		internal virtual FlatCheckBox FlatCheckBox22
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox22;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox22_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox22;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox22 = value;
				flatCheckBox = this.FlatCheckBox22;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x1700014D RID: 333
		// (get) Token: 0x06000366 RID: 870 RVA: 0x000045CA File Offset: 0x000027CA
		// (set) Token: 0x06000367 RID: 871 RVA: 0x000045D2 File Offset: 0x000027D2
		internal virtual FlatCheckBox FlatCheckBox24 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700014E RID: 334
		// (get) Token: 0x06000368 RID: 872 RVA: 0x000045DB File Offset: 0x000027DB
		// (set) Token: 0x06000369 RID: 873 RVA: 0x0001C620 File Offset: 0x0001A820
		internal virtual FlatCheckBox FlatCheckBox25
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox25;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox25_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox25;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox25 = value;
				flatCheckBox = this.FlatCheckBox25;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x1700014F RID: 335
		// (get) Token: 0x0600036A RID: 874 RVA: 0x000045E3 File Offset: 0x000027E3
		// (set) Token: 0x0600036B RID: 875 RVA: 0x000045EB File Offset: 0x000027EB
		internal virtual FlatLabel FlatLabel48 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000150 RID: 336
		// (get) Token: 0x0600036C RID: 876 RVA: 0x000045F4 File Offset: 0x000027F4
		// (set) Token: 0x0600036D RID: 877 RVA: 0x0001C664 File Offset: 0x0001A864
		internal virtual FlatCheckBox FlatCheckBox27
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox27;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox27_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox27;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox27 = value;
				flatCheckBox = this.FlatCheckBox27;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x17000151 RID: 337
		// (get) Token: 0x0600036E RID: 878 RVA: 0x000045FC File Offset: 0x000027FC
		// (set) Token: 0x0600036F RID: 879 RVA: 0x00004604 File Offset: 0x00002804
		internal virtual FlatCheckBox FlatCheckBox26 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000152 RID: 338
		// (get) Token: 0x06000370 RID: 880 RVA: 0x0000460D File Offset: 0x0000280D
		// (set) Token: 0x06000371 RID: 881 RVA: 0x00004615 File Offset: 0x00002815
		internal virtual FlatLabel FlatLabel49 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000153 RID: 339
		// (get) Token: 0x06000372 RID: 882 RVA: 0x0000461E File Offset: 0x0000281E
		// (set) Token: 0x06000373 RID: 883 RVA: 0x00004626 File Offset: 0x00002826
		internal virtual FlatNumeric FlatNumeric1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000154 RID: 340
		// (get) Token: 0x06000374 RID: 884 RVA: 0x0000462F File Offset: 0x0000282F
		// (set) Token: 0x06000375 RID: 885 RVA: 0x00004637 File Offset: 0x00002837
		internal virtual FlatTabControl FlatTabControl7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000155 RID: 341
		// (get) Token: 0x06000376 RID: 886 RVA: 0x00004640 File Offset: 0x00002840
		// (set) Token: 0x06000377 RID: 887 RVA: 0x00004648 File Offset: 0x00002848
		internal virtual TabPage TabPage18 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000156 RID: 342
		// (get) Token: 0x06000378 RID: 888 RVA: 0x00004651 File Offset: 0x00002851
		// (set) Token: 0x06000379 RID: 889 RVA: 0x00004659 File Offset: 0x00002859
		internal virtual TabPage TabPage19 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000157 RID: 343
		// (get) Token: 0x0600037A RID: 890 RVA: 0x00004662 File Offset: 0x00002862
		// (set) Token: 0x0600037B RID: 891 RVA: 0x0000466A File Offset: 0x0000286A
		internal virtual FlatLabel FlatLabel50 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000158 RID: 344
		// (get) Token: 0x0600037C RID: 892 RVA: 0x00004673 File Offset: 0x00002873
		// (set) Token: 0x0600037D RID: 893 RVA: 0x0000467B File Offset: 0x0000287B
		internal virtual FlatLabel FlatLabel51 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000159 RID: 345
		// (get) Token: 0x0600037E RID: 894 RVA: 0x00004684 File Offset: 0x00002884
		// (set) Token: 0x0600037F RID: 895 RVA: 0x0000468C File Offset: 0x0000288C
		internal virtual FlatLabel FlatLabel52 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700015A RID: 346
		// (get) Token: 0x06000380 RID: 896 RVA: 0x00004695 File Offset: 0x00002895
		// (set) Token: 0x06000381 RID: 897 RVA: 0x0000469D File Offset: 0x0000289D
		internal virtual FlatLabel FlatLabel53 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700015B RID: 347
		// (get) Token: 0x06000382 RID: 898 RVA: 0x000046A6 File Offset: 0x000028A6
		// (set) Token: 0x06000383 RID: 899 RVA: 0x0001C6A8 File Offset: 0x0001A8A8
		internal virtual FlatTrackBar FlatTrackBar11
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar11;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar11_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar11;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar11 = value;
				flatTrackBar = this.FlatTrackBar11;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x1700015C RID: 348
		// (get) Token: 0x06000384 RID: 900 RVA: 0x000046AE File Offset: 0x000028AE
		// (set) Token: 0x06000385 RID: 901 RVA: 0x000046B6 File Offset: 0x000028B6
		internal virtual FlatLabel FlatLabel57 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700015D RID: 349
		// (get) Token: 0x06000386 RID: 902 RVA: 0x000046BF File Offset: 0x000028BF
		// (set) Token: 0x06000387 RID: 903 RVA: 0x000046C7 File Offset: 0x000028C7
		internal virtual FlatLabel FlatLabel58 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700015E RID: 350
		// (get) Token: 0x06000388 RID: 904 RVA: 0x000046D0 File Offset: 0x000028D0
		// (set) Token: 0x06000389 RID: 905 RVA: 0x000046D8 File Offset: 0x000028D8
		internal virtual FlatLabel FlatLabel59 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700015F RID: 351
		// (get) Token: 0x0600038A RID: 906 RVA: 0x000046E1 File Offset: 0x000028E1
		// (set) Token: 0x0600038B RID: 907 RVA: 0x0001C6EC File Offset: 0x0001A8EC
		internal virtual FlatTrackBar FlatTrackBar12
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar12;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar12_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar12;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar12 = value;
				flatTrackBar = this.FlatTrackBar12;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000160 RID: 352
		// (get) Token: 0x0600038C RID: 908 RVA: 0x000046E9 File Offset: 0x000028E9
		// (set) Token: 0x0600038D RID: 909 RVA: 0x000046F1 File Offset: 0x000028F1
		internal virtual FlatLabel FlatLabel61 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000161 RID: 353
		// (get) Token: 0x0600038E RID: 910 RVA: 0x000046FA File Offset: 0x000028FA
		// (set) Token: 0x0600038F RID: 911 RVA: 0x00004702 File Offset: 0x00002902
		internal virtual FlatNumeric FlatNumeric2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000162 RID: 354
		// (get) Token: 0x06000390 RID: 912 RVA: 0x0000470B File Offset: 0x0000290B
		// (set) Token: 0x06000391 RID: 913 RVA: 0x00004713 File Offset: 0x00002913
		internal virtual FlatLabel FlatLabel60 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000163 RID: 355
		// (get) Token: 0x06000392 RID: 914 RVA: 0x0000471C File Offset: 0x0000291C
		// (set) Token: 0x06000393 RID: 915 RVA: 0x00004724 File Offset: 0x00002924
		internal virtual FlatCheckBox FlatCheckBox30 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000164 RID: 356
		// (get) Token: 0x06000394 RID: 916 RVA: 0x0000472D File Offset: 0x0000292D
		// (set) Token: 0x06000395 RID: 917 RVA: 0x0001C730 File Offset: 0x0001A930
		internal virtual FlatCheckBox FlatCheckBox29
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox29;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox29_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox29;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox29 = value;
				flatCheckBox = this.FlatCheckBox29;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x17000165 RID: 357
		// (get) Token: 0x06000396 RID: 918 RVA: 0x00004735 File Offset: 0x00002935
		// (set) Token: 0x06000397 RID: 919 RVA: 0x0000473D File Offset: 0x0000293D
		internal virtual FlatCheckBox FlatCheckBox28 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000166 RID: 358
		// (get) Token: 0x06000398 RID: 920 RVA: 0x00004746 File Offset: 0x00002946
		// (set) Token: 0x06000399 RID: 921 RVA: 0x0000474E File Offset: 0x0000294E
		internal virtual FlatLabel FlatLabel62 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000167 RID: 359
		// (get) Token: 0x0600039A RID: 922 RVA: 0x00004757 File Offset: 0x00002957
		// (set) Token: 0x0600039B RID: 923 RVA: 0x0000475F File Offset: 0x0000295F
		internal virtual FlatNumeric FlatNumeric3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000168 RID: 360
		// (get) Token: 0x0600039C RID: 924 RVA: 0x00004768 File Offset: 0x00002968
		// (set) Token: 0x0600039D RID: 925 RVA: 0x0001C774 File Offset: 0x0001A974
		internal virtual System.Windows.Forms.Timer Tap
		{
			[CompilerGenerated]
			get
			{
				return this.Tap;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Tap_Tick);
				System.Windows.Forms.Timer tap = this.Tap;
				if (tap != null)
				{
					tap.Tick -= value2;
				}
				this.Tap = value;
				tap = this.Tap;
				if (tap != null)
				{
					tap.Tick += value2;
				}
			}
		}

		// Token: 0x17000169 RID: 361
		// (get) Token: 0x0600039E RID: 926 RVA: 0x00004770 File Offset: 0x00002970
		// (set) Token: 0x0600039F RID: 927 RVA: 0x00004778 File Offset: 0x00002978
		internal virtual FlatCheckBox FlatCheckBox32 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700016A RID: 362
		// (get) Token: 0x060003A0 RID: 928 RVA: 0x00004781 File Offset: 0x00002981
		// (set) Token: 0x060003A1 RID: 929 RVA: 0x0001C7B8 File Offset: 0x0001A9B8
		internal virtual FlatButton FlatButton9
		{
			[CompilerGenerated]
			get
			{
				return this.FlatButton9;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatButton9_Click_1);
				FlatButton flatButton = this.FlatButton9;
				if (flatButton != null)
				{
					flatButton.Click -= value2;
				}
				this.FlatButton9 = value;
				flatButton = this.FlatButton9;
				if (flatButton != null)
				{
					flatButton.Click += value2;
				}
			}
		}

		// Token: 0x1700016B RID: 363
		// (get) Token: 0x060003A2 RID: 930 RVA: 0x00004789 File Offset: 0x00002989
		// (set) Token: 0x060003A3 RID: 931 RVA: 0x0001C7FC File Offset: 0x0001A9FC
		internal virtual FlatButton FlatButton10
		{
			[CompilerGenerated]
			get
			{
				return this.FlatButton10;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatButton10_Click);
				FlatButton flatButton = this.FlatButton10;
				if (flatButton != null)
				{
					flatButton.Click -= value2;
				}
				this.FlatButton10 = value;
				flatButton = this.FlatButton10;
				if (flatButton != null)
				{
					flatButton.Click += value2;
				}
			}
		}

		// Token: 0x1700016C RID: 364
		// (get) Token: 0x060003A4 RID: 932 RVA: 0x00004791 File Offset: 0x00002991
		// (set) Token: 0x060003A5 RID: 933 RVA: 0x0001C840 File Offset: 0x0001AA40
		internal virtual FlatButton FlatButton11
		{
			[CompilerGenerated]
			get
			{
				return this.FlatButton11;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatButton11_Click);
				FlatButton flatButton = this.FlatButton11;
				if (flatButton != null)
				{
					flatButton.Click -= value2;
				}
				this.FlatButton11 = value;
				flatButton = this.FlatButton11;
				if (flatButton != null)
				{
					flatButton.Click += value2;
				}
			}
		}

		// Token: 0x1700016D RID: 365
		// (get) Token: 0x060003A6 RID: 934 RVA: 0x00004799 File Offset: 0x00002999
		// (set) Token: 0x060003A7 RID: 935 RVA: 0x000047A1 File Offset: 0x000029A1
		internal virtual FlatCheckBox FlatCheckBox31 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700016E RID: 366
		// (get) Token: 0x060003A8 RID: 936 RVA: 0x000047AA File Offset: 0x000029AA
		// (set) Token: 0x060003A9 RID: 937 RVA: 0x0001C884 File Offset: 0x0001AA84
		internal virtual Button Button14
		{
			[CompilerGenerated]
			get
			{
				return this.Button14;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button14_Click);
				Button button = this.Button14;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button14 = value;
				button = this.Button14;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x1700016F RID: 367
		// (get) Token: 0x060003AA RID: 938 RVA: 0x000047B2 File Offset: 0x000029B2
		// (set) Token: 0x060003AB RID: 939 RVA: 0x000047BA File Offset: 0x000029BA
		internal virtual FlatCheckBox FlatCheckBox35 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000170 RID: 368
		// (get) Token: 0x060003AC RID: 940 RVA: 0x000047C3 File Offset: 0x000029C3
		// (set) Token: 0x060003AD RID: 941 RVA: 0x000047CB File Offset: 0x000029CB
		internal virtual FlatCheckBox FlatCheckBox34 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000171 RID: 369
		// (get) Token: 0x060003AE RID: 942 RVA: 0x000047D4 File Offset: 0x000029D4
		// (set) Token: 0x060003AF RID: 943 RVA: 0x000047DC File Offset: 0x000029DC
		internal virtual FlatCheckBox FlatCheckBox33 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000172 RID: 370
		// (get) Token: 0x060003B0 RID: 944 RVA: 0x000047E5 File Offset: 0x000029E5
		// (set) Token: 0x060003B1 RID: 945 RVA: 0x000047ED File Offset: 0x000029ED
		internal virtual TabPage TabPage13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000173 RID: 371
		// (get) Token: 0x060003B2 RID: 946 RVA: 0x000047F6 File Offset: 0x000029F6
		// (set) Token: 0x060003B3 RID: 947 RVA: 0x000047FE File Offset: 0x000029FE
		internal virtual FlatCheckBox FlatCheckBox38 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000174 RID: 372
		// (get) Token: 0x060003B4 RID: 948 RVA: 0x00004807 File Offset: 0x00002A07
		// (set) Token: 0x060003B5 RID: 949 RVA: 0x0000480F File Offset: 0x00002A0F
		internal virtual FlatCheckBox FlatCheckBox37 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000175 RID: 373
		// (get) Token: 0x060003B6 RID: 950 RVA: 0x00004818 File Offset: 0x00002A18
		// (set) Token: 0x060003B7 RID: 951 RVA: 0x00004820 File Offset: 0x00002A20
		internal virtual FlatCheckBox FlatCheckBox36 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000176 RID: 374
		// (get) Token: 0x060003B8 RID: 952 RVA: 0x00004829 File Offset: 0x00002A29
		// (set) Token: 0x060003B9 RID: 953 RVA: 0x00004831 File Offset: 0x00002A31
		internal virtual FlatCheckBox FlatCheckBox39 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000177 RID: 375
		// (get) Token: 0x060003BA RID: 954 RVA: 0x0000483A File Offset: 0x00002A3A
		// (set) Token: 0x060003BB RID: 955 RVA: 0x00004842 File Offset: 0x00002A42
		internal virtual FlatCheckBox FlatCheckBox40 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000178 RID: 376
		// (get) Token: 0x060003BC RID: 956 RVA: 0x0000484B File Offset: 0x00002A4B
		// (set) Token: 0x060003BD RID: 957 RVA: 0x00004853 File Offset: 0x00002A53
		internal virtual FlatLabel FlatLabel55 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000179 RID: 377
		// (get) Token: 0x060003BE RID: 958 RVA: 0x0000485C File Offset: 0x00002A5C
		// (set) Token: 0x060003BF RID: 959 RVA: 0x00004864 File Offset: 0x00002A64
		internal virtual FlatComboBox FlatComboBox6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700017A RID: 378
		// (get) Token: 0x060003C0 RID: 960 RVA: 0x0000486D File Offset: 0x00002A6D
		// (set) Token: 0x060003C1 RID: 961 RVA: 0x00004875 File Offset: 0x00002A75
		internal virtual FlatLabel FlatLabel63 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700017B RID: 379
		// (get) Token: 0x060003C2 RID: 962 RVA: 0x0000487E File Offset: 0x00002A7E
		// (set) Token: 0x060003C3 RID: 963 RVA: 0x0001C8C8 File Offset: 0x0001AAC8
		internal virtual Button Button15
		{
			[CompilerGenerated]
			get
			{
				return this.Button15;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button15_Click);
				Button button = this.Button15;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button15 = value;
				button = this.Button15;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x1700017C RID: 380
		// (get) Token: 0x060003C4 RID: 964 RVA: 0x00004886 File Offset: 0x00002A86
		// (set) Token: 0x060003C5 RID: 965 RVA: 0x0000488E File Offset: 0x00002A8E
		internal virtual TabPage TabPage16 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700017D RID: 381
		// (get) Token: 0x060003C6 RID: 966 RVA: 0x00004897 File Offset: 0x00002A97
		// (set) Token: 0x060003C7 RID: 967 RVA: 0x0000489F File Offset: 0x00002A9F
		internal virtual FlatTabControl FlatTabControl6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700017E RID: 382
		// (get) Token: 0x060003C8 RID: 968 RVA: 0x000048A8 File Offset: 0x00002AA8
		// (set) Token: 0x060003C9 RID: 969 RVA: 0x0001C90C File Offset: 0x0001AB0C
		internal virtual TabPage TabPage17
		{
			[CompilerGenerated]
			get
			{
				return this.TabPage17;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TabPage17_Click);
				TabPage tabPage = this.TabPage17;
				if (tabPage != null)
				{
					tabPage.Click -= value2;
				}
				this.TabPage17 = value;
				tabPage = this.TabPage17;
				if (tabPage != null)
				{
					tabPage.Click += value2;
				}
			}
		}

		// Token: 0x1700017F RID: 383
		// (get) Token: 0x060003CA RID: 970 RVA: 0x000048B0 File Offset: 0x00002AB0
		// (set) Token: 0x060003CB RID: 971 RVA: 0x000048B8 File Offset: 0x00002AB8
		internal virtual TabPage TabPage20 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000180 RID: 384
		// (get) Token: 0x060003CC RID: 972 RVA: 0x000048C1 File Offset: 0x00002AC1
		// (set) Token: 0x060003CD RID: 973 RVA: 0x000048C9 File Offset: 0x00002AC9
		internal virtual FlatLabel FlatLabel56 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000181 RID: 385
		// (get) Token: 0x060003CE RID: 974 RVA: 0x000048D2 File Offset: 0x00002AD2
		// (set) Token: 0x060003CF RID: 975 RVA: 0x0001C950 File Offset: 0x0001AB50
		internal virtual FlatLabel FlatLabel64
		{
			[CompilerGenerated]
			get
			{
				return this.FlatLabel64;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatLabel64_Click);
				FlatLabel flatLabel = this.FlatLabel64;
				if (flatLabel != null)
				{
					flatLabel.Click -= value2;
				}
				this.FlatLabel64 = value;
				flatLabel = this.FlatLabel64;
				if (flatLabel != null)
				{
					flatLabel.Click += value2;
				}
			}
		}

		// Token: 0x17000182 RID: 386
		// (get) Token: 0x060003D0 RID: 976 RVA: 0x000048DA File Offset: 0x00002ADA
		// (set) Token: 0x060003D1 RID: 977 RVA: 0x000048E2 File Offset: 0x00002AE2
		internal virtual FlatLabel FlatLabel68 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000183 RID: 387
		// (get) Token: 0x060003D2 RID: 978 RVA: 0x000048EB File Offset: 0x00002AEB
		// (set) Token: 0x060003D3 RID: 979 RVA: 0x000048F3 File Offset: 0x00002AF3
		internal virtual FlatLabel FlatLabel69 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000184 RID: 388
		// (get) Token: 0x060003D4 RID: 980 RVA: 0x000048FC File Offset: 0x00002AFC
		// (set) Token: 0x060003D5 RID: 981 RVA: 0x00004904 File Offset: 0x00002B04
		internal virtual FlatLabel FlatLabel70 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000185 RID: 389
		// (get) Token: 0x060003D6 RID: 982 RVA: 0x0000490D File Offset: 0x00002B0D
		// (set) Token: 0x060003D7 RID: 983 RVA: 0x0001C994 File Offset: 0x0001AB94
		internal virtual FlatTrackBar FlatTrackBar14
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar14;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar14_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar14;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar14 = value;
				flatTrackBar = this.FlatTrackBar14;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000186 RID: 390
		// (get) Token: 0x060003D8 RID: 984 RVA: 0x00004915 File Offset: 0x00002B15
		// (set) Token: 0x060003D9 RID: 985 RVA: 0x0000491D File Offset: 0x00002B1D
		internal virtual FlatLabel FlatLabel65 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000187 RID: 391
		// (get) Token: 0x060003DA RID: 986 RVA: 0x00004926 File Offset: 0x00002B26
		// (set) Token: 0x060003DB RID: 987 RVA: 0x0000492E File Offset: 0x00002B2E
		internal virtual FlatLabel FlatLabel66 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000188 RID: 392
		// (get) Token: 0x060003DC RID: 988 RVA: 0x00004937 File Offset: 0x00002B37
		// (set) Token: 0x060003DD RID: 989 RVA: 0x0000493F File Offset: 0x00002B3F
		internal virtual FlatLabel FlatLabel67 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000189 RID: 393
		// (get) Token: 0x060003DE RID: 990 RVA: 0x00004948 File Offset: 0x00002B48
		// (set) Token: 0x060003DF RID: 991 RVA: 0x0001C9D8 File Offset: 0x0001ABD8
		internal virtual FlatTrackBar FlatTrackBar13
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar13;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar13_Scroll_1);
				FlatTrackBar flatTrackBar = this.FlatTrackBar13;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar13 = value;
				flatTrackBar = this.FlatTrackBar13;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x1700018A RID: 394
		// (get) Token: 0x060003E0 RID: 992 RVA: 0x00004950 File Offset: 0x00002B50
		// (set) Token: 0x060003E1 RID: 993 RVA: 0x0001CA1C File Offset: 0x0001AC1C
		internal virtual Button Button16
		{
			[CompilerGenerated]
			get
			{
				return this.Button16;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button16_Click);
				Button button = this.Button16;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button16 = value;
				button = this.Button16;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x1700018B RID: 395
		// (get) Token: 0x060003E2 RID: 994 RVA: 0x00004958 File Offset: 0x00002B58
		// (set) Token: 0x060003E3 RID: 995 RVA: 0x00004960 File Offset: 0x00002B60
		internal virtual FlatLabel FlatLabel71 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700018C RID: 396
		// (get) Token: 0x060003E4 RID: 996 RVA: 0x00004969 File Offset: 0x00002B69
		// (set) Token: 0x060003E5 RID: 997 RVA: 0x00004971 File Offset: 0x00002B71
		internal virtual FlatComboBox FlatComboBox7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700018D RID: 397
		// (get) Token: 0x060003E6 RID: 998 RVA: 0x0000497A File Offset: 0x00002B7A
		// (set) Token: 0x060003E7 RID: 999 RVA: 0x00004982 File Offset: 0x00002B82
		internal virtual FlatLabel FlatLabel72 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700018E RID: 398
		// (get) Token: 0x060003E8 RID: 1000 RVA: 0x0000498B File Offset: 0x00002B8B
		// (set) Token: 0x060003E9 RID: 1001 RVA: 0x00004993 File Offset: 0x00002B93
		internal virtual Button Button17 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700018F RID: 399
		// (get) Token: 0x060003EA RID: 1002 RVA: 0x0000499C File Offset: 0x00002B9C
		// (set) Token: 0x060003EB RID: 1003 RVA: 0x0001CA60 File Offset: 0x0001AC60
		internal virtual Button Button6
		{
			[CompilerGenerated]
			get
			{
				return this.Button6;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button6_Click_1);
				Button button = this.Button6;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button6 = value;
				button = this.Button6;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000190 RID: 400
		// (get) Token: 0x060003EC RID: 1004 RVA: 0x000049A4 File Offset: 0x00002BA4
		// (set) Token: 0x060003ED RID: 1005 RVA: 0x000049AC File Offset: 0x00002BAC
		internal virtual TextBox FlatTextbox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000191 RID: 401
		// (get) Token: 0x060003EE RID: 1006 RVA: 0x000049B5 File Offset: 0x00002BB5
		// (set) Token: 0x060003EF RID: 1007 RVA: 0x000049BD File Offset: 0x00002BBD
		internal virtual TabPage TabPage22 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000192 RID: 402
		// (get) Token: 0x060003F0 RID: 1008 RVA: 0x000049C6 File Offset: 0x00002BC6
		// (set) Token: 0x060003F1 RID: 1009 RVA: 0x000049CE File Offset: 0x00002BCE
		internal virtual FlatTabControl FlatTabControl8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000193 RID: 403
		// (get) Token: 0x060003F2 RID: 1010 RVA: 0x000049D7 File Offset: 0x00002BD7
		// (set) Token: 0x060003F3 RID: 1011 RVA: 0x000049DF File Offset: 0x00002BDF
		internal virtual TabPage TabPage23 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000194 RID: 404
		// (get) Token: 0x060003F4 RID: 1012 RVA: 0x000049E8 File Offset: 0x00002BE8
		// (set) Token: 0x060003F5 RID: 1013 RVA: 0x0001CAA4 File Offset: 0x0001ACA4
		internal virtual Button Button21
		{
			[CompilerGenerated]
			get
			{
				return this.Button21;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button21_Click);
				Button button = this.Button21;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button21 = value;
				button = this.Button21;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000195 RID: 405
		// (get) Token: 0x060003F6 RID: 1014 RVA: 0x000049F0 File Offset: 0x00002BF0
		// (set) Token: 0x060003F7 RID: 1015 RVA: 0x000049F8 File Offset: 0x00002BF8
		internal virtual FlatLabel FlatLabel73 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000196 RID: 406
		// (get) Token: 0x060003F8 RID: 1016 RVA: 0x00004A01 File Offset: 0x00002C01
		// (set) Token: 0x060003F9 RID: 1017 RVA: 0x00004A09 File Offset: 0x00002C09
		internal virtual FlatComboBox FlatComboBox8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000197 RID: 407
		// (get) Token: 0x060003FA RID: 1018 RVA: 0x00004A12 File Offset: 0x00002C12
		// (set) Token: 0x060003FB RID: 1019 RVA: 0x00004A1A File Offset: 0x00002C1A
		internal virtual FlatLabel FlatLabel74 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000198 RID: 408
		// (get) Token: 0x060003FC RID: 1020 RVA: 0x00004A23 File Offset: 0x00002C23
		// (set) Token: 0x060003FD RID: 1021 RVA: 0x00004A2B File Offset: 0x00002C2B
		internal virtual FlatLabel FlatLabel75 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000199 RID: 409
		// (get) Token: 0x060003FE RID: 1022 RVA: 0x00004A34 File Offset: 0x00002C34
		// (set) Token: 0x060003FF RID: 1023 RVA: 0x00004A3C File Offset: 0x00002C3C
		internal virtual FlatLabel FlatLabel76 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700019A RID: 410
		// (get) Token: 0x06000400 RID: 1024 RVA: 0x00004A45 File Offset: 0x00002C45
		// (set) Token: 0x06000401 RID: 1025 RVA: 0x0001CAE8 File Offset: 0x0001ACE8
		internal virtual FlatTrackBar FlatTrackBar15
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar15;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar15_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar15;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar15 = value;
				flatTrackBar = this.FlatTrackBar15;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x1700019B RID: 411
		// (get) Token: 0x06000402 RID: 1026 RVA: 0x00004A4D File Offset: 0x00002C4D
		// (set) Token: 0x06000403 RID: 1027 RVA: 0x00004A55 File Offset: 0x00002C55
		internal virtual FlatLabel FlatLabel77 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700019C RID: 412
		// (get) Token: 0x06000404 RID: 1028 RVA: 0x00004A5E File Offset: 0x00002C5E
		// (set) Token: 0x06000405 RID: 1029 RVA: 0x00004A66 File Offset: 0x00002C66
		internal virtual FlatLabel FlatLabel78 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700019D RID: 413
		// (get) Token: 0x06000406 RID: 1030 RVA: 0x00004A6F File Offset: 0x00002C6F
		// (set) Token: 0x06000407 RID: 1031 RVA: 0x00004A77 File Offset: 0x00002C77
		internal virtual FlatCheckBox FlatCheckBox17 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700019E RID: 414
		// (get) Token: 0x06000408 RID: 1032 RVA: 0x00004A80 File Offset: 0x00002C80
		// (set) Token: 0x06000409 RID: 1033 RVA: 0x0001CB2C File Offset: 0x0001AD2C
		internal virtual System.Windows.Forms.Timer TempDisable
		{
			[CompilerGenerated]
			get
			{
				return this.TempDisable;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TempDisable_Tick);
				System.Windows.Forms.Timer tempDisable = this.TempDisable;
				if (tempDisable != null)
				{
					tempDisable.Tick -= value2;
				}
				this.TempDisable = value;
				tempDisable = this.TempDisable;
				if (tempDisable != null)
				{
					tempDisable.Tick += value2;
				}
			}
		}

		// Token: 0x1700019F RID: 415
		// (get) Token: 0x0600040A RID: 1034 RVA: 0x00004A88 File Offset: 0x00002C88
		// (set) Token: 0x0600040B RID: 1035 RVA: 0x0001CB70 File Offset: 0x0001AD70
		internal virtual FlatCheckBox FlatCheckBox41
		{
			[CompilerGenerated]
			get
			{
				return this.FlatCheckBox41;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox41_CheckedChanged);
				FlatCheckBox flatCheckBox = this.FlatCheckBox41;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this.FlatCheckBox41 = value;
				flatCheckBox = this.FlatCheckBox41;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x170001A0 RID: 416
		// (get) Token: 0x0600040C RID: 1036 RVA: 0x00004A90 File Offset: 0x00002C90
		// (set) Token: 0x0600040D RID: 1037 RVA: 0x0001CBB4 File Offset: 0x0001ADB4
		internal virtual System.Windows.Forms.Timer Timer5
		{
			[CompilerGenerated]
			get
			{
				return this.Timer5;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer5_Tick);
				System.Windows.Forms.Timer timer = this.Timer5;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this.Timer5 = value;
				timer = this.Timer5;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x170001A1 RID: 417
		// (get) Token: 0x0600040E RID: 1038 RVA: 0x00004A98 File Offset: 0x00002C98
		// (set) Token: 0x0600040F RID: 1039 RVA: 0x0001CBF8 File Offset: 0x0001ADF8
		internal virtual System.Windows.Forms.Timer ScriptEvents
		{
			[CompilerGenerated]
			get
			{
				return this.ScriptEvents;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ScriptEvents_Tick);
				System.Windows.Forms.Timer scriptEvents = this.ScriptEvents;
				if (scriptEvents != null)
				{
					scriptEvents.Tick -= value2;
				}
				this.ScriptEvents = value;
				scriptEvents = this.ScriptEvents;
				if (scriptEvents != null)
				{
					scriptEvents.Tick += value2;
				}
			}
		}

		// Token: 0x170001A2 RID: 418
		// (get) Token: 0x06000410 RID: 1040 RVA: 0x00004AA0 File Offset: 0x00002CA0
		// (set) Token: 0x06000411 RID: 1041 RVA: 0x00004AA8 File Offset: 0x00002CA8
		internal virtual FlatTabControl FlatTabControl9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001A3 RID: 419
		// (get) Token: 0x06000412 RID: 1042 RVA: 0x00004AB1 File Offset: 0x00002CB1
		// (set) Token: 0x06000413 RID: 1043 RVA: 0x00004AB9 File Offset: 0x00002CB9
		internal virtual TabPage TabPage24 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001A4 RID: 420
		// (get) Token: 0x06000414 RID: 1044 RVA: 0x00004AC2 File Offset: 0x00002CC2
		// (set) Token: 0x06000415 RID: 1045 RVA: 0x00004ACA File Offset: 0x00002CCA
		internal virtual FlatTabControl FlatTabControl10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001A5 RID: 421
		// (get) Token: 0x06000416 RID: 1046 RVA: 0x00004AD3 File Offset: 0x00002CD3
		// (set) Token: 0x06000417 RID: 1047 RVA: 0x0001CC3C File Offset: 0x0001AE3C
		internal virtual TabPage TabPage31
		{
			[CompilerGenerated]
			get
			{
				return this.TabPage31;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TabPage31_Click);
				TabPage tabPage = this.TabPage31;
				if (tabPage != null)
				{
					tabPage.Click -= value2;
				}
				this.TabPage31 = value;
				tabPage = this.TabPage31;
				if (tabPage != null)
				{
					tabPage.Click += value2;
				}
			}
		}

		// Token: 0x170001A6 RID: 422
		// (get) Token: 0x06000418 RID: 1048 RVA: 0x00004ADB File Offset: 0x00002CDB
		// (set) Token: 0x06000419 RID: 1049 RVA: 0x00004AE3 File Offset: 0x00002CE3
		internal virtual TabPage TabPage32 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001A7 RID: 423
		// (get) Token: 0x0600041A RID: 1050 RVA: 0x00004AEC File Offset: 0x00002CEC
		// (set) Token: 0x0600041B RID: 1051 RVA: 0x0001CC80 File Offset: 0x0001AE80
		internal virtual TabPage TabPage33
		{
			[CompilerGenerated]
			get
			{
				return this.TabPage33;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TabPage33_Click);
				TabPage tabPage = this.TabPage33;
				if (tabPage != null)
				{
					tabPage.Click -= value2;
				}
				this.TabPage33 = value;
				tabPage = this.TabPage33;
				if (tabPage != null)
				{
					tabPage.Click += value2;
				}
			}
		}

		// Token: 0x170001A8 RID: 424
		// (get) Token: 0x0600041C RID: 1052 RVA: 0x00004AF4 File Offset: 0x00002CF4
		// (set) Token: 0x0600041D RID: 1053 RVA: 0x00004AFC File Offset: 0x00002CFC
		internal virtual TabPage TabPage27 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001A9 RID: 425
		// (get) Token: 0x0600041E RID: 1054 RVA: 0x00004B05 File Offset: 0x00002D05
		// (set) Token: 0x0600041F RID: 1055 RVA: 0x0001CCC4 File Offset: 0x0001AEC4
		internal virtual Button Button22
		{
			[CompilerGenerated]
			get
			{
				return this.Button22;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button22_Click_1);
				Button button = this.Button22;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button22 = value;
				button = this.Button22;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170001AA RID: 426
		// (get) Token: 0x06000420 RID: 1056 RVA: 0x00004B0D File Offset: 0x00002D0D
		// (set) Token: 0x06000421 RID: 1057 RVA: 0x00004B15 File Offset: 0x00002D15
		internal virtual FlatLabel FlatLabel82 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001AB RID: 427
		// (get) Token: 0x06000422 RID: 1058 RVA: 0x00004B1E File Offset: 0x00002D1E
		// (set) Token: 0x06000423 RID: 1059 RVA: 0x0001CD08 File Offset: 0x0001AF08
		internal virtual FlatComboBox FlatComboBox9
		{
			[CompilerGenerated]
			get
			{
				return this.FlatComboBox9;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatComboBox9_SelectedIndexChanged);
				FlatComboBox flatComboBox = this.FlatComboBox9;
				if (flatComboBox != null)
				{
					flatComboBox.SelectedIndexChanged -= value2;
				}
				this.FlatComboBox9 = value;
				flatComboBox = this.FlatComboBox9;
				if (flatComboBox != null)
				{
					flatComboBox.SelectedIndexChanged += value2;
				}
			}
		}

		// Token: 0x170001AC RID: 428
		// (get) Token: 0x06000424 RID: 1060 RVA: 0x00004B26 File Offset: 0x00002D26
		// (set) Token: 0x06000425 RID: 1061 RVA: 0x00004B2E File Offset: 0x00002D2E
		internal virtual FlatLabel FlatLabel79 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001AD RID: 429
		// (get) Token: 0x06000426 RID: 1062 RVA: 0x00004B37 File Offset: 0x00002D37
		// (set) Token: 0x06000427 RID: 1063 RVA: 0x0001CD4C File Offset: 0x0001AF4C
		internal virtual FlatTrackBar FlatTrackBar16
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar16;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar16_Scroll_1);
				FlatTrackBar flatTrackBar = this.FlatTrackBar16;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar16 = value;
				flatTrackBar = this.FlatTrackBar16;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x170001AE RID: 430
		// (get) Token: 0x06000428 RID: 1064 RVA: 0x00004B3F File Offset: 0x00002D3F
		// (set) Token: 0x06000429 RID: 1065 RVA: 0x00004B47 File Offset: 0x00002D47
		internal virtual FlatLabel FlatLabel81 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001AF RID: 431
		// (get) Token: 0x0600042A RID: 1066 RVA: 0x00004B50 File Offset: 0x00002D50
		// (set) Token: 0x0600042B RID: 1067 RVA: 0x00004B58 File Offset: 0x00002D58
		internal virtual FlatLabel FlatLabel80 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001B0 RID: 432
		// (get) Token: 0x0600042C RID: 1068 RVA: 0x00004B61 File Offset: 0x00002D61
		// (set) Token: 0x0600042D RID: 1069 RVA: 0x0001CD90 File Offset: 0x0001AF90
		internal virtual Button Button23
		{
			[CompilerGenerated]
			get
			{
				return this.Button23;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button23_Click_1);
				Button button = this.Button23;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button23 = value;
				button = this.Button23;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170001B1 RID: 433
		// (get) Token: 0x0600042E RID: 1070 RVA: 0x00004B69 File Offset: 0x00002D69
		// (set) Token: 0x0600042F RID: 1071 RVA: 0x0001CDD4 File Offset: 0x0001AFD4
		internal virtual Button Button25
		{
			[CompilerGenerated]
			get
			{
				return this.Button25;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button25_Click);
				Button button = this.Button25;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button25 = value;
				button = this.Button25;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170001B2 RID: 434
		// (get) Token: 0x06000430 RID: 1072 RVA: 0x00004B71 File Offset: 0x00002D71
		// (set) Token: 0x06000431 RID: 1073 RVA: 0x0001CE18 File Offset: 0x0001B018
		internal virtual Button Button24
		{
			[CompilerGenerated]
			get
			{
				return this.Button24;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button24_Click);
				Button button = this.Button24;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button24 = value;
				button = this.Button24;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170001B3 RID: 435
		// (get) Token: 0x06000432 RID: 1074 RVA: 0x00004B79 File Offset: 0x00002D79
		// (set) Token: 0x06000433 RID: 1075 RVA: 0x00004B81 File Offset: 0x00002D81
		internal virtual FlatLabel FlatLabel83 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001B4 RID: 436
		// (get) Token: 0x06000434 RID: 1076 RVA: 0x00004B8A File Offset: 0x00002D8A
		// (set) Token: 0x06000435 RID: 1077 RVA: 0x00004B92 File Offset: 0x00002D92
		internal virtual FlatTrackBar FlatTrackBar17 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001B5 RID: 437
		// (get) Token: 0x06000436 RID: 1078 RVA: 0x00004B9B File Offset: 0x00002D9B
		// (set) Token: 0x06000437 RID: 1079 RVA: 0x00004BA3 File Offset: 0x00002DA3
		internal virtual FlatLabel FlatLabel84 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001B6 RID: 438
		// (get) Token: 0x06000438 RID: 1080 RVA: 0x00004BAC File Offset: 0x00002DAC
		// (set) Token: 0x06000439 RID: 1081 RVA: 0x00004BB4 File Offset: 0x00002DB4
		internal virtual FlatLabel FlatLabel85 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001B7 RID: 439
		// (get) Token: 0x0600043A RID: 1082 RVA: 0x00004BBD File Offset: 0x00002DBD
		// (set) Token: 0x0600043B RID: 1083 RVA: 0x00004BC5 File Offset: 0x00002DC5
		internal virtual FlatLabel FlatLabel86 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001B8 RID: 440
		// (get) Token: 0x0600043C RID: 1084 RVA: 0x00004BCE File Offset: 0x00002DCE
		// (set) Token: 0x0600043D RID: 1085 RVA: 0x0001CE5C File Offset: 0x0001B05C
		internal virtual FlatTrackBar FlatTrackBar18
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar18;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar18_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar18;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar18 = value;
				flatTrackBar = this.FlatTrackBar18;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x170001B9 RID: 441
		// (get) Token: 0x0600043E RID: 1086 RVA: 0x00004BD6 File Offset: 0x00002DD6
		// (set) Token: 0x0600043F RID: 1087 RVA: 0x00004BDE File Offset: 0x00002DDE
		internal virtual FlatLabel FlatLabel87 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001BA RID: 442
		// (get) Token: 0x06000440 RID: 1088 RVA: 0x00004BE7 File Offset: 0x00002DE7
		// (set) Token: 0x06000441 RID: 1089 RVA: 0x00004BEF File Offset: 0x00002DEF
		internal virtual FlatLabel FlatLabel88 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001BB RID: 443
		// (get) Token: 0x06000442 RID: 1090 RVA: 0x00004BF8 File Offset: 0x00002DF8
		// (set) Token: 0x06000443 RID: 1091 RVA: 0x00004C00 File Offset: 0x00002E00
		internal virtual FlatComboBox FlatComboBox10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001BC RID: 444
		// (get) Token: 0x06000444 RID: 1092 RVA: 0x00004C09 File Offset: 0x00002E09
		// (set) Token: 0x06000445 RID: 1093 RVA: 0x0001CEA0 File Offset: 0x0001B0A0
		internal virtual System.Windows.Forms.Timer Timer6
		{
			[CompilerGenerated]
			get
			{
				return this.Timer6;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer6_Tick);
				System.Windows.Forms.Timer timer = this.Timer6;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this.Timer6 = value;
				timer = this.Timer6;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x170001BD RID: 445
		// (get) Token: 0x06000446 RID: 1094 RVA: 0x00004C11 File Offset: 0x00002E11
		// (set) Token: 0x06000447 RID: 1095 RVA: 0x0001CEE4 File Offset: 0x0001B0E4
		internal virtual System.Windows.Forms.Timer TapEvents
		{
			[CompilerGenerated]
			get
			{
				return this.TapEvents;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TapEvents_Tick);
				System.Windows.Forms.Timer tapEvents = this.TapEvents;
				if (tapEvents != null)
				{
					tapEvents.Tick -= value2;
				}
				this.TapEvents = value;
				tapEvents = this.TapEvents;
				if (tapEvents != null)
				{
					tapEvents.Tick += value2;
				}
			}
		}

		// Token: 0x170001BE RID: 446
		// (get) Token: 0x06000448 RID: 1096 RVA: 0x00004C19 File Offset: 0x00002E19
		// (set) Token: 0x06000449 RID: 1097 RVA: 0x00004C21 File Offset: 0x00002E21
		internal virtual FlatLabel FlatLabel89 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001BF RID: 447
		// (get) Token: 0x0600044A RID: 1098 RVA: 0x00004C2A File Offset: 0x00002E2A
		// (set) Token: 0x0600044B RID: 1099 RVA: 0x0001CF28 File Offset: 0x0001B128
		internal virtual FlatTrackBar FlatTrackBar19
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTrackBar19;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar19_Scroll);
				FlatTrackBar flatTrackBar = this.FlatTrackBar19;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this.FlatTrackBar19 = value;
				flatTrackBar = this.FlatTrackBar19;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x170001C0 RID: 448
		// (get) Token: 0x0600044C RID: 1100 RVA: 0x00004C32 File Offset: 0x00002E32
		// (set) Token: 0x0600044D RID: 1101 RVA: 0x00004C3A File Offset: 0x00002E3A
		internal virtual FlatLabel FlatLabel90 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001C1 RID: 449
		// (get) Token: 0x0600044E RID: 1102 RVA: 0x00004C43 File Offset: 0x00002E43
		// (set) Token: 0x0600044F RID: 1103 RVA: 0x00004C4B File Offset: 0x00002E4B
		internal virtual FlatLabel FlatLabel91 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001C2 RID: 450
		// (get) Token: 0x06000450 RID: 1104 RVA: 0x00004C54 File Offset: 0x00002E54
		// (set) Token: 0x06000451 RID: 1105 RVA: 0x0001CF6C File Offset: 0x0001B16C
		internal virtual CheckBox CheckBox4
		{
			[CompilerGenerated]
			get
			{
				return this.CheckBox4;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.CheckBox4_CheckedChanged);
				CheckBox checkBox = this.CheckBox4;
				if (checkBox != null)
				{
					checkBox.CheckedChanged -= value2;
				}
				this.CheckBox4 = value;
				checkBox = this.CheckBox4;
				if (checkBox != null)
				{
					checkBox.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x170001C3 RID: 451
		// (get) Token: 0x06000452 RID: 1106 RVA: 0x00004C5C File Offset: 0x00002E5C
		// (set) Token: 0x06000453 RID: 1107 RVA: 0x0001CFB0 File Offset: 0x0001B1B0
		internal virtual CheckBox CheckBox3
		{
			[CompilerGenerated]
			get
			{
				return this.CheckBox3;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.CheckBox3_CheckedChanged);
				CheckBox checkBox = this.CheckBox3;
				if (checkBox != null)
				{
					checkBox.CheckedChanged -= value2;
				}
				this.CheckBox3 = value;
				checkBox = this.CheckBox3;
				if (checkBox != null)
				{
					checkBox.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x170001C4 RID: 452
		// (get) Token: 0x06000454 RID: 1108 RVA: 0x00004C64 File Offset: 0x00002E64
		// (set) Token: 0x06000455 RID: 1109 RVA: 0x0001CFF4 File Offset: 0x0001B1F4
		internal virtual CheckBox CheckBox2
		{
			[CompilerGenerated]
			get
			{
				return this.CheckBox2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.CheckBox2_CheckedChanged);
				CheckBox checkBox = this.CheckBox2;
				if (checkBox != null)
				{
					checkBox.CheckedChanged -= value2;
				}
				this.CheckBox2 = value;
				checkBox = this.CheckBox2;
				if (checkBox != null)
				{
					checkBox.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x170001C5 RID: 453
		// (get) Token: 0x06000456 RID: 1110 RVA: 0x00004C6C File Offset: 0x00002E6C
		// (set) Token: 0x06000457 RID: 1111 RVA: 0x00004C74 File Offset: 0x00002E74
		internal virtual FlatLabel FlatLabel92 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001C6 RID: 454
		// (get) Token: 0x06000458 RID: 1112 RVA: 0x00004C7D File Offset: 0x00002E7D
		// (set) Token: 0x06000459 RID: 1113 RVA: 0x0001D038 File Offset: 0x0001B238
		internal virtual CheckBox CheckBox1
		{
			[CompilerGenerated]
			get
			{
				return this.CheckBox1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.CheckBox1_CheckedChanged);
				CheckBox checkBox = this.CheckBox1;
				if (checkBox != null)
				{
					checkBox.CheckedChanged -= value2;
				}
				this.CheckBox1 = value;
				checkBox = this.CheckBox1;
				if (checkBox != null)
				{
					checkBox.CheckedChanged += value2;
				}
			}
		}

		// Token: 0x170001C7 RID: 455
		// (get) Token: 0x0600045A RID: 1114 RVA: 0x00004C85 File Offset: 0x00002E85
		// (set) Token: 0x0600045B RID: 1115 RVA: 0x00004C8D File Offset: 0x00002E8D
		internal virtual FlatCheckBox FlatCheckBox46 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001C8 RID: 456
		// (get) Token: 0x0600045C RID: 1116 RVA: 0x00004C96 File Offset: 0x00002E96
		// (set) Token: 0x0600045D RID: 1117 RVA: 0x00004C9E File Offset: 0x00002E9E
		internal virtual FlatCheckBox FlatCheckBox45 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001C9 RID: 457
		// (get) Token: 0x0600045E RID: 1118 RVA: 0x00004CA7 File Offset: 0x00002EA7
		// (set) Token: 0x0600045F RID: 1119 RVA: 0x00004CAF File Offset: 0x00002EAF
		internal virtual FlatCheckBox FlatCheckBox44 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001CA RID: 458
		// (get) Token: 0x06000460 RID: 1120 RVA: 0x00004CB8 File Offset: 0x00002EB8
		// (set) Token: 0x06000461 RID: 1121 RVA: 0x00004CC0 File Offset: 0x00002EC0
		internal virtual FlatCheckBox FlatCheckBox43 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001CB RID: 459
		// (get) Token: 0x06000462 RID: 1122 RVA: 0x00004CC9 File Offset: 0x00002EC9
		// (set) Token: 0x06000463 RID: 1123 RVA: 0x00004CD1 File Offset: 0x00002ED1
		internal virtual FlatCheckBox FlatCheckBox42 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001CC RID: 460
		// (get) Token: 0x06000464 RID: 1124 RVA: 0x00004CDA File Offset: 0x00002EDA
		// (set) Token: 0x06000465 RID: 1125 RVA: 0x00004CE2 File Offset: 0x00002EE2
		internal virtual FlatLabel FlatLabel93 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001CD RID: 461
		// (get) Token: 0x06000466 RID: 1126 RVA: 0x00004CEB File Offset: 0x00002EEB
		// (set) Token: 0x06000467 RID: 1127 RVA: 0x00004CF3 File Offset: 0x00002EF3
		internal virtual FlatLabel FlatLabel94 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001CE RID: 462
		// (get) Token: 0x06000468 RID: 1128 RVA: 0x00004CFC File Offset: 0x00002EFC
		// (set) Token: 0x06000469 RID: 1129 RVA: 0x00004D04 File Offset: 0x00002F04
		internal virtual FlatLabel FlatLabel95 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001CF RID: 463
		// (get) Token: 0x0600046A RID: 1130 RVA: 0x00004D0D File Offset: 0x00002F0D
		// (set) Token: 0x0600046B RID: 1131 RVA: 0x0001D07C File Offset: 0x0001B27C
		internal virtual Button Button18
		{
			[CompilerGenerated]
			get
			{
				return this.Button18;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button18_Click_1);
				Button button = this.Button18;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button18 = value;
				button = this.Button18;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170001D0 RID: 464
		// (get) Token: 0x0600046C RID: 1132 RVA: 0x00004D15 File Offset: 0x00002F15
		// (set) Token: 0x0600046D RID: 1133 RVA: 0x0001D0C0 File Offset: 0x0001B2C0
		internal virtual FlatTextBox Textbox2
		{
			[CompilerGenerated]
			get
			{
				return this.Textbox2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatTextBox1_TextChanged);
				FlatTextBox textbox = this.Textbox2;
				if (textbox != null)
				{
					textbox.TextChanged -= value2;
				}
				this.Textbox2 = value;
				textbox = this.Textbox2;
				if (textbox != null)
				{
					textbox.TextChanged += value2;
				}
			}
		}

		// Token: 0x170001D1 RID: 465
		// (get) Token: 0x0600046E RID: 1134 RVA: 0x00004D1D File Offset: 0x00002F1D
		// (set) Token: 0x0600046F RID: 1135 RVA: 0x00004D25 File Offset: 0x00002F25
		internal virtual FlatCheckBox FlatCheckBox12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001D2 RID: 466
		// (get) Token: 0x06000470 RID: 1136 RVA: 0x00004D2E File Offset: 0x00002F2E
		// (set) Token: 0x06000471 RID: 1137 RVA: 0x00004D36 File Offset: 0x00002F36
		internal virtual FlatCheckBox FlatCheckBox47 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001D3 RID: 467
		// (get) Token: 0x06000472 RID: 1138 RVA: 0x00004D3F File Offset: 0x00002F3F
		// (set) Token: 0x06000473 RID: 1139 RVA: 0x00004D47 File Offset: 0x00002F47
		internal virtual FlatCheckBox FlatCheckBox48 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001D4 RID: 468
		// (get) Token: 0x06000474 RID: 1140 RVA: 0x00004D50 File Offset: 0x00002F50
		// (set) Token: 0x06000475 RID: 1141 RVA: 0x00004D58 File Offset: 0x00002F58
		internal virtual FlatCheckBox FlatCheckBox49 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001D5 RID: 469
		// (get) Token: 0x06000476 RID: 1142 RVA: 0x00004D61 File Offset: 0x00002F61
		// (set) Token: 0x06000477 RID: 1143 RVA: 0x0001D104 File Offset: 0x0001B304
		internal virtual Button Button28
		{
			[CompilerGenerated]
			get
			{
				return this.Button28;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button28_Click);
				Button button = this.Button28;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button28 = value;
				button = this.Button28;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170001D6 RID: 470
		// (get) Token: 0x06000478 RID: 1144 RVA: 0x00004D69 File Offset: 0x00002F69
		// (set) Token: 0x06000479 RID: 1145 RVA: 0x0001D148 File Offset: 0x0001B348
		internal virtual Button Button27
		{
			[CompilerGenerated]
			get
			{
				return this.Button27;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button27_Click);
				Button button = this.Button27;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button27 = value;
				button = this.Button27;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170001D7 RID: 471
		// (get) Token: 0x0600047A RID: 1146 RVA: 0x00004D71 File Offset: 0x00002F71
		// (set) Token: 0x0600047B RID: 1147 RVA: 0x00004D79 File Offset: 0x00002F79
		internal virtual FlatTextBox Textbox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001D8 RID: 472
		// (get) Token: 0x0600047C RID: 1148 RVA: 0x00004D82 File Offset: 0x00002F82
		// (set) Token: 0x0600047D RID: 1149 RVA: 0x00004D8A File Offset: 0x00002F8A
		internal virtual FlatTextBox Textbox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001D9 RID: 473
		// (get) Token: 0x0600047E RID: 1150 RVA: 0x00004D93 File Offset: 0x00002F93
		// (set) Token: 0x0600047F RID: 1151 RVA: 0x0001D18C File Offset: 0x0001B38C
		internal virtual System.Windows.Forms.Timer MisplaceEvents
		{
			[CompilerGenerated]
			get
			{
				return this.MisplaceEvents;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.MisplaceEvents_Tick);
				System.Windows.Forms.Timer misplaceEvents = this.MisplaceEvents;
				if (misplaceEvents != null)
				{
					misplaceEvents.Tick -= value2;
				}
				this.MisplaceEvents = value;
				misplaceEvents = this.MisplaceEvents;
				if (misplaceEvents != null)
				{
					misplaceEvents.Tick += value2;
				}
			}
		}

		// Token: 0x170001DA RID: 474
		// (get) Token: 0x06000480 RID: 1152 RVA: 0x00004D9B File Offset: 0x00002F9B
		// (set) Token: 0x06000481 RID: 1153 RVA: 0x0001D1D0 File Offset: 0x0001B3D0
		internal virtual System.Windows.Forms.Timer VelocityEvents
		{
			[CompilerGenerated]
			get
			{
				return this.VelocityEvents;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.VelocityEvents_Tick);
				System.Windows.Forms.Timer velocityEvents = this.VelocityEvents;
				if (velocityEvents != null)
				{
					velocityEvents.Tick -= value2;
				}
				this.VelocityEvents = value;
				velocityEvents = this.VelocityEvents;
				if (velocityEvents != null)
				{
					velocityEvents.Tick += value2;
				}
			}
		}

		// Token: 0x170001DB RID: 475
		// (get) Token: 0x06000482 RID: 1154 RVA: 0x00004DA3 File Offset: 0x00002FA3
		// (set) Token: 0x06000483 RID: 1155 RVA: 0x0001D214 File Offset: 0x0001B414
		internal virtual System.Windows.Forms.Timer AimAssistEvents
		{
			[CompilerGenerated]
			get
			{
				return this.AimAssistEvents;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.AimAssistEvents_Tick);
				System.Windows.Forms.Timer aimAssistEvents = this.AimAssistEvents;
				if (aimAssistEvents != null)
				{
					aimAssistEvents.Tick -= value2;
				}
				this.AimAssistEvents = value;
				aimAssistEvents = this.AimAssistEvents;
				if (aimAssistEvents != null)
				{
					aimAssistEvents.Tick += value2;
				}
			}
		}

		// Token: 0x170001DC RID: 476
		// (get) Token: 0x06000484 RID: 1156 RVA: 0x00004DAB File Offset: 0x00002FAB
		// (set) Token: 0x06000485 RID: 1157 RVA: 0x0001D258 File Offset: 0x0001B458
		internal virtual System.Windows.Forms.Timer StrafespeedEvents
		{
			[CompilerGenerated]
			get
			{
				return this.StrafespeedEvents;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.StrafespeedEvents_Tick);
				System.Windows.Forms.Timer strafespeedEvents = this.StrafespeedEvents;
				if (strafespeedEvents != null)
				{
					strafespeedEvents.Tick -= value2;
				}
				this.StrafespeedEvents = value;
				strafespeedEvents = this.StrafespeedEvents;
				if (strafespeedEvents != null)
				{
					strafespeedEvents.Tick += value2;
				}
			}
		}

		// Token: 0x170001DD RID: 477
		// (get) Token: 0x06000486 RID: 1158 RVA: 0x00004DB3 File Offset: 0x00002FB3
		// (set) Token: 0x06000487 RID: 1159 RVA: 0x0001D29C File Offset: 0x0001B49C
		internal virtual System.Windows.Forms.Timer ArraylistEvents
		{
			[CompilerGenerated]
			get
			{
				return this.ArraylistEvents;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ArraylistEvents_Tick);
				System.Windows.Forms.Timer arraylistEvents = this.ArraylistEvents;
				if (arraylistEvents != null)
				{
					arraylistEvents.Tick -= value2;
				}
				this.ArraylistEvents = value;
				arraylistEvents = this.ArraylistEvents;
				if (arraylistEvents != null)
				{
					arraylistEvents.Tick += value2;
				}
			}
		}

		// Token: 0x170001DE RID: 478
		// (get) Token: 0x06000488 RID: 1160 RVA: 0x00004DBB File Offset: 0x00002FBB
		// (set) Token: 0x06000489 RID: 1161 RVA: 0x0001D2E0 File Offset: 0x0001B4E0
		internal virtual Button Button19
		{
			[CompilerGenerated]
			get
			{
				return this.Button19;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button19_Click_2);
				Button button = this.Button19;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button19 = value;
				button = this.Button19;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170001DF RID: 479
		// (get) Token: 0x0600048A RID: 1162 RVA: 0x00004DC3 File Offset: 0x00002FC3
		// (set) Token: 0x0600048B RID: 1163 RVA: 0x0001D324 File Offset: 0x0001B524
		internal virtual Button Button29
		{
			[CompilerGenerated]
			get
			{
				return this.Button29;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button29_Click);
				Button button = this.Button29;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button29 = value;
				button = this.Button29;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170001E0 RID: 480
		// (get) Token: 0x0600048C RID: 1164 RVA: 0x00004DCB File Offset: 0x00002FCB
		// (set) Token: 0x0600048D RID: 1165 RVA: 0x0001D368 File Offset: 0x0001B568
		internal virtual Button Button30
		{
			[CompilerGenerated]
			get
			{
				return this.Button30;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button30_Click);
				Button button = this.Button30;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button30 = value;
				button = this.Button30;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x170001E1 RID: 481
		// (get) Token: 0x0600048E RID: 1166 RVA: 0x00004DD3 File Offset: 0x00002FD3
		// (set) Token: 0x0600048F RID: 1167 RVA: 0x00004DDB File Offset: 0x00002FDB
		internal virtual FlatCheckBox FlatCheckBox51 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001E2 RID: 482
		// (get) Token: 0x06000490 RID: 1168 RVA: 0x00004DE4 File Offset: 0x00002FE4
		// (set) Token: 0x06000491 RID: 1169 RVA: 0x00004DEC File Offset: 0x00002FEC
		internal virtual FlatCheckBox FlatCheckBox50 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x06000492 RID: 1170
		[DllImport("user32.dll")]
		private static extern void keybd_event(byte bVk, byte bScan, uint dwFlags, uint dwExtraInfo);

		// Token: 0x06000493 RID: 1171
		[DllImport("user32.dll")]
		private static extern void mouse_event(uint dwFlags, int dx, int dy, int dwData, uint dwExtraInfo);

		// Token: 0x06000494 RID: 1172
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern int GetAsyncKeyState(long vkey);

		// Token: 0x06000495 RID: 1173
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

		// Token: 0x06000496 RID: 1174
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
		private static extern bool apimouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

		// Token: 0x06000497 RID: 1175 RVA: 0x0001D3AC File Offset: 0x0001B5AC
		private void TrackStart_Scroll(object sender)
		{
			int value = this.TrackStart.Value;
			value.ToString("00");
			string.Format("{0:d2}", value);
			if (this.TrackStart.Value > this.TrackEnd.Value)
			{
				this.TrackStart.Value = this.TrackEnd.Value;
			}
			this.FlatLabel4.Text = Conversions.ToString(this.TrackStart.Value);
		}

		// Token: 0x06000498 RID: 1176 RVA: 0x0001D42C File Offset: 0x0001B62C
		private void TrackEnd_Scroll(object sender)
		{
			int value = this.TrackEnd.Value;
			value.ToString("00");
			string.Format("{0:d2}", value);
			if (this.TrackEnd.Value < this.TrackStart.Value)
			{
				this.TrackEnd.Value = this.TrackStart.Value;
			}
			this.FlatLabel6.Text = Conversions.ToString(this.TrackEnd.Value);
		}

		// Token: 0x06000499 RID: 1177 RVA: 0x00004DF5 File Offset: 0x00002FF5
		private void FlatTrackBar1_Scroll(object sender)
		{
			this.FlatLabel11.Text = Conversions.ToString(this.FlatTrackBar1.Value);
		}

		// Token: 0x0600049A RID: 1178 RVA: 0x00004E12 File Offset: 0x00003012
		private void FlatTrackBar2_Scroll(object sender)
		{
			this.FlatLabel10.Text = Conversions.ToString(this.FlatTrackBar2.Value);
		}

		// Token: 0x0600049B RID: 1179 RVA: 0x00003E9A File Offset: 0x0000209A
		private void TabPage5_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600049C RID: 1180 RVA: 0x00004E2F File Offset: 0x0000302F
		private void FlatTrackBar3_Scroll(object sender)
		{
			this.FlatLabel9.Text = Conversions.ToString(this.FlatTrackBar3.Value);
		}

		// Token: 0x0600049D RID: 1181 RVA: 0x0001D4AC File Offset: 0x0001B6AC
		private void Form2_Load(object sender, EventArgs e)
		{
			Process.Start("cmd.exe");
			Thread.Sleep(750);
			Process.GetProcessesByName("cmd")[0].Kill();
			this.FlatTrackBar6.Value = 70;
			this.FlatComboBox9.SelectedItem = "F9";
			this.FlatComboBox10.SelectedItem = "F10";
			this.FlatComboBox5.SelectedItem = "L";
			this.FlatComboBox4.SelectedItem = "V";
			this.FlatComboBox4.SelectedItem = "Insert";
			this.FlatComboBox3.SelectedItem = "Alt";
			this.FlatComboBox1.SelectedItem = "Z";
			this.FlatComboBox6.Enabled = true;
			this.Text = "GHOSTBYTES";
			this.CheckEvents.Interval = 230;
			this.FlatTrackBar13.Value = SystemInformation.MouseSpeed;
			this.FlatComboBox1.SelectedItem = this.FlatComboBox1.Text;
			this.FlatComboBox3.SelectedItem = this.FlatComboBox3.Text;
			this.FlatComboBox4.SelectedItem = this.FlatComboBox4.Text;
			this.FlatComboBox6.SelectedItem = this.FlatComboBox6.Text;
			this.FlatComboBox7.SelectedItem = this.FlatComboBox7.Text;
			this.TempDisable.Enabled = true;
		}

		// Token: 0x0600049E RID: 1182 RVA: 0x00004E4C File Offset: 0x0000304C
		private void FlatTrackBar4_Scroll(object sender)
		{
			this.FlatLabel16.Text = Conversions.ToString(this.FlatTrackBar4.Value);
		}

		// Token: 0x0600049F RID: 1183 RVA: 0x00004E69 File Offset: 0x00003069
		private void FlatCheckBox7_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox7.Checked)
			{
				this.FlatTrackBar4.Enabled = true;
			}
			if (!this.FlatCheckBox7.Checked)
			{
				this.FlatTrackBar4.Enabled = false;
				this.FlatTrackBar4.Value = 0;
			}
		}

		// Token: 0x060004A0 RID: 1184 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatButton1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004A1 RID: 1185 RVA: 0x00003E9A File Offset: 0x0000209A
		private void TabPage4_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004A2 RID: 1186 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatTabControl3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004A3 RID: 1187 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatTabControl1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004A4 RID: 1188 RVA: 0x00004EA9 File Offset: 0x000030A9
		private void FlatCheckBox11_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox11.Checked)
			{
				base.ShowInTaskbar = false;
			}
			if (!this.FlatCheckBox11.Checked)
			{
				base.ShowInTaskbar = true;
			}
		}

		// Token: 0x060004A5 RID: 1189 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatButton2_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004A6 RID: 1190 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatButton3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004A7 RID: 1191 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatCheckBox12_CheckedChanged(object sender)
		{
		}

		// Token: 0x060004A8 RID: 1192 RVA: 0x00003E9A File Offset: 0x0000209A
		private void MinClick_Scroll(object sender)
		{
		}

		// Token: 0x060004A9 RID: 1193 RVA: 0x00003E9A File Offset: 0x0000209A
		private void MaxClick_Scroll(object sender)
		{
		}

		// Token: 0x060004AA RID: 1194 RVA: 0x0001D610 File Offset: 0x0001B810
		private void Timer1_Tick(object sender, EventArgs e)
		{
			if (!this.FlatCheckBox32.Checked)
			{
				Form2.mouse_event(2, 0, 0, 0, 0);
				Thread.Sleep(this.buttonpress);
				Form2.mouse_event(4, 0, 0, 0, 0);
			}
			if (this.FlatCheckBox32.Checked && Control.MouseButtons == MouseButtons.Left)
			{
				Form2.mouse_event(2, 0, 0, 0, 0);
				Form2.mouse_event(4, 0, 0, 0, 0);
			}
			if (this.Aimassist.Checked)
			{
				Registry.CurrentUser.OpenSubKey("Control Panel\\\\Mouse", true).SetValue("MouseSpeed", this.FlatTrackBar13.Value);
			}
			if (this.RandCPS)
			{
				this.GetRandCPS();
			}
		}

		// Token: 0x060004AB RID: 1195
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "GetAsyncKeyState", ExactSpelling = true, SetLastError = true)]
		private static extern int GetKeyPress(int key);

		// Token: 0x060004AC RID: 1196 RVA: 0x00003E9A File Offset: 0x0000209A
		private void Timer3_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x060004AD RID: 1197 RVA: 0x0001D6BC File Offset: 0x0001B8BC
		private void FlatTrackBar6_Scroll(object sender)
		{
			if (this.FlatTrackBar6.Value > this.FlatTrackBar5.Value)
			{
				this.FlatTrackBar6.Value = this.FlatTrackBar5.Value;
			}
			if (this.FlatTrackBar6.Value < 1)
			{
				this.FlatTrackBar6.Value = 70;
			}
			if (this.FlatCheckBox16.Checked && this.FlatTrackBar6.Value < 60)
			{
				this.FlatTrackBar6.Value = 60;
			}
			this.FlatLabel19.Text = Conversions.ToString(this.FlatTrackBar6.Value);
			this.Timer1.Interval = this.FlatTrackBar6.Value;
		}

		// Token: 0x060004AE RID: 1198 RVA: 0x0001D76C File Offset: 0x0001B96C
		private void FlatTrackBar5_Scroll(object sender)
		{
			if (this.FlatTrackBar5.Value < this.FlatTrackBar6.Value)
			{
				this.FlatTrackBar5.Value = this.FlatTrackBar6.Value;
			}
			this.FlatLabel20.Text = Conversions.ToString(this.FlatTrackBar5.Value);
		}

		// Token: 0x060004AF RID: 1199 RVA: 0x0001D7C4 File Offset: 0x0001B9C4
		private void Button1_Click(object sender, EventArgs e)
		{
			if (!this.PState)
			{
				this.PState = true;
				this.Button1.Text = "Toggle Off";
				MyProject.Forms.Arraylist.AutoclickerOn.ForeColor = Color.DodgerBlue;
			}
			else if (this.FlatCheckBox14.Checked)
			{
				this.NotifyIcon1.BalloonTipText = "Autoclicker was successfully disabled.";
				this.NotifyIcon1.BalloonTipTitle = "Autoclicker";
				this.NotifyIcon1.Visible = true;
				this.NotifyIcon1.ShowBalloonTip(0);
				MyProject.Forms.Arraylist.AutoclickerOn.ForeColor = Color.White;
			}
			else
			{
				this.PState = false;
				this.Button1.Text = "Toggle On";
			}
			if (!this.PState & this.FlatCheckBox14.Checked)
			{
				this.PState = true;
				this.Button1.Text = "Toggle Off";
				this.NotifyIcon1.BalloonTipText = "Autoclicker was successfully enabled.";
				this.NotifyIcon1.BalloonTipTitle = "Autoclicker";
				this.NotifyIcon1.Visible = true;
				this.NotifyIcon1.ShowBalloonTip(0);
				MyProject.Forms.Arraylist.AutoclickerOn.ForeColor = Color.DodgerBlue;
			}
		}

		// Token: 0x060004B0 RID: 1200 RVA: 0x00004ED3 File Offset: 0x000030D3
		private void FlatTrackBar7_Scroll(object sender)
		{
			this.FlatLabel24.Text = Conversions.ToString(this.FlatTrackBar7.Value);
		}

		// Token: 0x060004B1 RID: 1201 RVA: 0x00004EF0 File Offset: 0x000030F0
		private void Button2_Click(object sender, EventArgs e)
		{
			Process.Start("cmd.exe");
			Thread.Sleep(700);
			Process.GetProcessesByName("cmd")[0].Kill();
			this.buttonpress = this.FlatTrackBar7.Value;
		}

		// Token: 0x060004B2 RID: 1202 RVA: 0x00003E9A File Offset: 0x0000209A
		private void Button3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004B3 RID: 1203 RVA: 0x0001D904 File Offset: 0x0001BB04
		private void FlatCheckBox13_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox13.Checked)
			{
				Interaction.MsgBox("A small presstime might cause detectability issues.", MsgBoxStyle.Information, "Information");
				Process.Start("cmd.exe");
				Thread.Sleep(700);
				Process.GetProcessesByName("cmd")[0].Kill();
				this.Button2.Enabled = true;
			}
			if (!this.FlatCheckBox13.Checked)
			{
				this.Button2.Enabled = false;
			}
		}

		// Token: 0x060004B4 RID: 1204 RVA: 0x00004F29 File Offset: 0x00003129
		private void NotifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
		{
			base.Show();
		}

		// Token: 0x060004B5 RID: 1205 RVA: 0x00004F31 File Offset: 0x00003131
		private void Button4_Click(object sender, EventArgs e)
		{
			Thread.Sleep(1000);
			Interaction.MsgBox("Scripts cleared successfully!", MsgBoxStyle.Information, "Success");
		}

		// Token: 0x060004B6 RID: 1206 RVA: 0x00004F4F File Offset: 0x0000314F
		private void FlatButton6_Click(object sender, EventArgs e)
		{
			this.OpenFileDialog1.Filter = "Clicksound | *wav";
			if (this.OpenFileDialog1.ShowDialog() == DialogResult.OK)
			{
				this.FlatTextbox2.Text = this.OpenFileDialog1.FileName;
			}
		}

		// Token: 0x060004B7 RID: 1207 RVA: 0x0001D97C File Offset: 0x0001BB7C
		private void FlatButton5_Click(object sender, EventArgs e)
		{
			if (Strings.InStr(this.FlatTextbox2.Text, ":", CompareMethod.Binary) != 0)
			{
				MyProject.Computer.Audio.Play(this.FlatTextbox2.Text, AudioPlayMode.BackgroundLoop);
				return;
			}
			Interaction.MsgBox("Invalid Audio File", MsgBoxStyle.Critical, "Error");
		}

		// Token: 0x060004B8 RID: 1208 RVA: 0x00004F85 File Offset: 0x00003185
		private void FlatButton7_Click(object sender, EventArgs e)
		{
			MyProject.Computer.Audio.Stop();
		}

		// Token: 0x060004B9 RID: 1209 RVA: 0x0001D9D4 File Offset: 0x0001BBD4
		private int GetRandCPS()
		{
			Random random = new Random();
			int num = random.Next(40, 70);
			int num2 = random.Next(1, 10);
			int num3 = random.Next(1, 30);
			checked
			{
				if (num3 > 25)
				{
					System.Windows.Forms.Timer timer;
					(timer = this.Timer1).Interval = timer.Interval + num;
				}
				else if (num3 < 25)
				{
					System.Windows.Forms.Timer timer;
					(timer = this.Timer1).Interval = timer.Interval - num2;
				}
				return num;
			}
		}

		// Token: 0x060004BA RID: 1210 RVA: 0x0001DA44 File Offset: 0x0001BC44
		private int GetRandomInterval()
		{
			Random random = new Random();
			int num = random.Next(1000, 15000);
			int num2 = random.Next(5000, 20000);
			int num3 = random.Next(1, 9);
			checked
			{
				num = (int)Math.Round(unchecked((double)num + 250.0));
				num2 = (int)Math.Round(unchecked((double)num2 + 200.0));
				int num4 = num3;
				if (num4 > 4)
				{
					this.Timer2.Interval = num;
				}
				else if (num4 < 4)
				{
					this.Timer2.Interval = num2;
				}
				return num;
			}
		}

		// Token: 0x060004BB RID: 1211 RVA: 0x00004F96 File Offset: 0x00003196
		private void Timer2_Tick(object sender, EventArgs e)
		{
			this.Timer1.Enabled = false;
			this.PE.WaitOne(230);
			this.Timer1.Enabled = true;
			this.GetRandomInterval();
		}

		// Token: 0x060004BC RID: 1212 RVA: 0x00004FC8 File Offset: 0x000031C8
		private void CheckControls()
		{
			if (this.FlatCheckBox16.Checked)
			{
				this.RandCPS = true;
				return;
			}
			this.RandCPS = false;
		}

		// Token: 0x060004BD RID: 1213 RVA: 0x0001DAD8 File Offset: 0x0001BCD8
		private void CheckEvents_Tick(object sender, EventArgs e)
		{
			if (!this.ChatDisable && !this.PState)
			{
				bool flag = Form2.GetAsyncKeyState((long)this.CurrentHK) != 0;
				this.CheckControls();
				if (!this.IsEnabled & this.FlatCheckBox14.Checked)
				{
					if (flag)
					{
						this.IsEnabled = true;
						this.Button1.Text = "Toggle Off";
						this.NotifyIcon1.BalloonTipText = "Autoclicker was successfully enabled.";
						this.NotifyIcon1.BalloonTipTitle = "Autoclicker";
						this.NotifyIcon1.Visible = true;
						this.NotifyIcon1.ShowBalloonTip(0);
						MyProject.Computer.Audio.Play(this.FlatTextbox2.Text, AudioPlayMode.BackgroundLoop);
						MyProject.Forms.Arraylist.AutoclickerOn.ForeColor = Color.DodgerBlue;
						if (this.RandTime)
						{
							this.GetRandomInterval();
							this.Timer2.Enabled = true;
						}
						this.Timer1.Enabled = true;
					}
				}
				else if ((this.IsEnabled & this.FlatCheckBox14.Checked) && flag)
				{
					this.IsEnabled = false;
					this.Button1.Text = "Toggle On";
					MyProject.Computer.Audio.Stop();
					this.Timer2.Enabled = false;
					this.Timer1.Enabled = false;
					this.NotifyIcon1.BalloonTipText = "Autoclicker was successfully disabled.";
					this.NotifyIcon1.BalloonTipTitle = "Autoclicker";
					this.NotifyIcon1.Visible = true;
					this.NotifyIcon1.ShowBalloonTip(0);
					MyProject.Forms.Arraylist.AutoclickerOn.ForeColor = Color.White;
				}
				if (!this.IsEnabled & !this.FlatCheckBox14.Checked)
				{
					if (flag)
					{
						this.IsEnabled = true;
						this.Button1.Text = "Toggle Off";
						MyProject.Forms.Arraylist.AutoclickerOn.ForeColor = Color.DodgerBlue;
						MyProject.Computer.Audio.Play(this.FlatTextbox2.Text, AudioPlayMode.BackgroundLoop);
						if (this.RandTime)
						{
							this.GetRandomInterval();
							this.Timer2.Enabled = true;
						}
						this.Timer1.Enabled = true;
						return;
					}
				}
				else if ((this.IsEnabled & !this.FlatCheckBox14.Checked) && flag)
				{
					this.IsEnabled = false;
					this.Button1.Text = "Toggle On";
					MyProject.Computer.Audio.Stop();
					this.Timer2.Enabled = false;
					this.Timer1.Enabled = false;
					MyProject.Forms.Arraylist.AutoclickerOn.ForeColor = Color.White;
				}
			}
		}

		// Token: 0x060004BE RID: 1214 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatComboBox1_SelectedIndexChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060004BF RID: 1215 RVA: 0x0001DD90 File Offset: 0x0001BF90
		private void Button8_Click(object sender, EventArgs e)
		{
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "F9", false))
			{
				this.MisplaceHK = Keys.F9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "F10", false))
			{
				this.MisplaceHK = Keys.F10;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "R", false))
			{
				this.MisplaceHK = Keys.R;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "V", false))
			{
				this.MisplaceHK = Keys.V;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "N", false))
			{
				this.MisplaceHK = Keys.N;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "Alt", false))
			{
				this.MisplaceHK = Keys.Menu;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "Shift", false))
			{
				this.MisplaceHK = Keys.Shift;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "Insert", false))
			{
				this.MisplaceHK = Keys.Insert;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "A", false))
			{
				this.MisplaceHK = Keys.A;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "B", false))
			{
				this.MisplaceHK = Keys.B;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "C", false))
			{
				this.MisplaceHK = Keys.C;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "D", false))
			{
				this.MisplaceHK = Keys.D;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "E", false))
			{
				this.MisplaceHK = Keys.E;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "F", false))
			{
				this.MisplaceHK = Keys.F;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "G", false))
			{
				this.MisplaceHK = Keys.G;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "H", false))
			{
				this.MisplaceHK = Keys.H;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "I", false))
			{
				this.MisplaceHK = Keys.I;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "J", false))
			{
				this.MisplaceHK = Keys.J;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "K", false))
			{
				this.MisplaceHK = Keys.K;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "L", false))
			{
				this.MisplaceHK = Keys.L;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "M", false))
			{
				this.MisplaceHK = Keys.M;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "O", false))
			{
				this.MisplaceHK = Keys.O;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "P", false))
			{
				this.MisplaceHK = Keys.P;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "Q", false))
			{
				this.MisplaceHK = Keys.Q;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "S", false))
			{
				this.MisplaceHK = Keys.S;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "T", false))
			{
				this.MisplaceHK = Keys.T;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "U", false))
			{
				this.MisplaceHK = Keys.U;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "W", false))
			{
				this.MisplaceHK = Keys.W;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "X", false))
			{
				this.MisplaceHK = Keys.X;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "Y", false))
			{
				this.MisplaceHK = Keys.Y;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "Z", false))
			{
				this.MisplaceHK = Keys.Z;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "0", false))
			{
				this.MisplaceHK = Keys.D0;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "1", false))
			{
				this.MisplaceHK = Keys.D1;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "2", false))
			{
				this.MisplaceHK = Keys.D2;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "3", false))
			{
				this.MisplaceHK = Keys.D3;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "4", false))
			{
				this.MisplaceHK = Keys.D4;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "5", false))
			{
				this.MisplaceHK = Keys.D5;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "6", false))
			{
				this.MisplaceHK = Keys.D6;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "7", false))
			{
				this.MisplaceHK = Keys.D7;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "8", false))
			{
				this.MisplaceHK = Keys.D8;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox1.SelectedItem, "9", false))
			{
				this.MisplaceHK = Keys.D9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			base.Invalidate();
		}

		// Token: 0x060004C0 RID: 1216 RVA: 0x0001E5C8 File Offset: 0x0001C7C8
		private void Button7_Click(object sender, EventArgs e)
		{
			this.FlatComboBox3.Text = Conversions.ToString(this.FlatComboBox3.SelectedItem);
			this.HkStr = Conversions.ToString(this.FlatComboBox3.SelectedItem);
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "F9", false))
			{
				this.CurrentHK = Keys.F9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "F10", false))
			{
				this.CurrentHK = Keys.F10;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "R", false))
			{
				this.CurrentHK = Keys.R;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "V", false))
			{
				this.CurrentHK = Keys.V;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "N", false))
			{
				this.CurrentHK = Keys.N;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "Alt", false))
			{
				this.CurrentHK = Keys.Menu;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "Shift", false))
			{
				this.CurrentHK = Keys.Shift;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "Insert", false))
			{
				this.CurrentHK = Keys.Insert;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "A", false))
			{
				this.CurrentHK = Keys.A;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "B", false))
			{
				this.CurrentHK = Keys.B;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "C", false))
			{
				this.CurrentHK = Keys.C;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "D", false))
			{
				this.CurrentHK = Keys.D;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "E", false))
			{
				this.CurrentHK = Keys.E;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "F", false))
			{
				this.CurrentHK = Keys.F;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "G", false))
			{
				this.CurrentHK = Keys.G;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "H", false))
			{
				this.CurrentHK = Keys.H;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "I", false))
			{
				this.CurrentHK = Keys.I;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "J", false))
			{
				this.CurrentHK = Keys.J;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "K", false))
			{
				this.CurrentHK = Keys.K;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "L", false))
			{
				this.CurrentHK = Keys.L;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "M", false))
			{
				this.CurrentHK = Keys.M;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "O", false))
			{
				this.CurrentHK = Keys.O;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "P", false))
			{
				this.CurrentHK = Keys.P;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "Q", false))
			{
				this.CurrentHK = Keys.Q;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "S", false))
			{
				this.CurrentHK = Keys.S;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "T", false))
			{
				this.CurrentHK = Keys.T;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "U", false))
			{
				this.CurrentHK = Keys.U;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "W", false))
			{
				this.CurrentHK = Keys.W;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "X", false))
			{
				this.CurrentHK = Keys.X;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "Y", false))
			{
				this.CurrentHK = Keys.Y;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "Z", false))
			{
				this.CurrentHK = Keys.Z;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "0", false))
			{
				this.CurrentHK = Keys.D0;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "1", false))
			{
				this.CurrentHK = Keys.D1;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "2", false))
			{
				this.CurrentHK = Keys.D2;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "3", false))
			{
				this.CurrentHK = Keys.D3;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "4", false))
			{
				this.CurrentHK = Keys.D4;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "5", false))
			{
				this.CurrentHK = Keys.D5;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "6", false))
			{
				this.CurrentHK = Keys.D6;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "7", false))
			{
				this.CurrentHK = Keys.D7;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "8", false))
			{
				this.CurrentHK = Keys.D8;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox3.SelectedItem, "9", false))
			{
				this.CurrentHK = Keys.D9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			base.Invalidate();
		}

		// Token: 0x060004C1 RID: 1217 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatLabel19_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004C2 RID: 1218 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatTextBox4_MouseHover(object sender, EventArgs e)
		{
		}

		// Token: 0x060004C3 RID: 1219 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatTextBox5_MouseHover(object sender, EventArgs e)
		{
		}

		// Token: 0x060004C4 RID: 1220 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatLabel30_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060004C5 RID: 1221 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatTextBox4_TextChanged_1(object sender, EventArgs e)
		{
		}

		// Token: 0x060004C6 RID: 1222 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatTextBox5_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060004C7 RID: 1223 RVA: 0x00004FE6 File Offset: 0x000031E6
		[MethodImpl(MethodImplOptions.NoInlining | MethodImplOptions.NoOptimization)]
		private void FlatClose1_Click(object sender, EventArgs e)
		{
			ProjectData.EndApp();
		}

		// Token: 0x060004C8 RID: 1224 RVA: 0x00004FED File Offset: 0x000031ED
		private void FlatMini1_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x060004C9 RID: 1225 RVA: 0x00003E9A File Offset: 0x0000209A
		private void Button6_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004CA RID: 1226 RVA: 0x00004FF6 File Offset: 0x000031F6
		private void Button10_Click(object sender, EventArgs e)
		{
			Thread.Sleep(800);
			Interaction.MsgBox("Recording Saved Successfully!", MsgBoxStyle.Information, "Success");
		}

		// Token: 0x060004CB RID: 1227 RVA: 0x00005014 File Offset: 0x00003214
		private void FlatLabel30_Click(object sender, EventArgs e)
		{
			Process.Start("https://github.com/blackeaglefna/soundpack");
		}

		// Token: 0x060004CC RID: 1228 RVA: 0x0001EE30 File Offset: 0x0001D030
		private void FlatCheckBox18_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox18.Checked & Operators.CompareString(this.FlatLabel32.Text, "RecordOn", false) == 0)
			{
				Interaction.MsgBox("You are using recorded clicks now.", MsgBoxStyle.OkOnly, null);
				this.FlatTrackBar6.Enabled = false;
				this.FlatTrackBar5.Enabled = false;
				if (Operators.CompareString(this.FlatLabel32.Text, "RecordOf", false) == 0)
				{
					Interaction.MsgBox("No recording found. Please record your clicks before proceeding.", MsgBoxStyle.Critical, "Error");
					this.FlatCheckBox18.Checked = false;
				}
			}
			if (!this.FlatCheckBox18.Checked)
			{
				this.FlatTrackBar6.Enabled = true;
				this.FlatTrackBar5.Enabled = true;
			}
		}

		// Token: 0x060004CD RID: 1229 RVA: 0x00005021 File Offset: 0x00003221
		private void Button9_Click(object sender, EventArgs e)
		{
			this.FlatLabel32.Text = "RecordOn";
		}

		// Token: 0x060004CE RID: 1230 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatLabel35_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004CF RID: 1231 RVA: 0x00005033 File Offset: 0x00003233
		private void FlatTrackBar8_Scroll(object sender)
		{
			this.FlatLabel35.Text = Conversions.ToString(this.FlatTrackBar8.Value);
			if (this.FlatTrackBar8.Value < 1)
			{
				this.FlatTrackBar8.Value = 1;
			}
		}

		// Token: 0x060004D0 RID: 1232 RVA: 0x0001EEE4 File Offset: 0x0001D0E4
		private void Button11_Click(object sender, EventArgs e)
		{
			this.FlatComboBox4.Text = Conversions.ToString(this.FlatComboBox4.SelectedItem);
			this.FPStr = Conversions.ToString(this.FlatComboBox4.SelectedItem);
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "F9", false))
			{
				this.FastplaceHK = Keys.F9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "F10", false))
			{
				this.FastplaceHK = Keys.F10;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "R", false))
			{
				this.FastplaceHK = Keys.R;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "V", false))
			{
				this.FastplaceHK = Keys.V;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "N", false))
			{
				this.FastplaceHK = Keys.N;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "Alt", false))
			{
				this.FastplaceHK = Keys.Menu;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "Shift", false))
			{
				this.FastplaceHK = Keys.Shift;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "Insert", false))
			{
				this.FastplaceHK = Keys.Insert;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "A", false))
			{
				this.FastplaceHK = Keys.A;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "B", false))
			{
				this.FastplaceHK = Keys.B;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "C", false))
			{
				this.FastplaceHK = Keys.C;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "D", false))
			{
				this.FastplaceHK = Keys.D;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "E", false))
			{
				this.FastplaceHK = Keys.E;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "F", false))
			{
				this.FastplaceHK = Keys.F;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "G", false))
			{
				this.FastplaceHK = Keys.G;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "H", false))
			{
				this.FastplaceHK = Keys.H;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "I", false))
			{
				this.FastplaceHK = Keys.I;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "J", false))
			{
				this.FastplaceHK = Keys.J;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "K", false))
			{
				this.FastplaceHK = Keys.K;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "L", false))
			{
				this.FastplaceHK = Keys.L;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "M", false))
			{
				this.FastplaceHK = Keys.M;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "O", false))
			{
				this.FastplaceHK = Keys.O;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "P", false))
			{
				this.FastplaceHK = Keys.P;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "Q", false))
			{
				this.FastplaceHK = Keys.Q;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "S", false))
			{
				this.FastplaceHK = Keys.S;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "T", false))
			{
				this.FastplaceHK = Keys.T;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "U", false))
			{
				this.FastplaceHK = Keys.U;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "W", false))
			{
				this.FastplaceHK = Keys.W;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "X", false))
			{
				this.FastplaceHK = Keys.X;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "Y", false))
			{
				this.FastplaceHK = Keys.Y;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "Z", false))
			{
				this.FastplaceHK = Keys.Z;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "0", false))
			{
				this.FastplaceHK = Keys.D0;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "1", false))
			{
				this.FastplaceHK = Keys.D1;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "2", false))
			{
				this.FastplaceHK = Keys.D2;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "3", false))
			{
				this.FastplaceHK = Keys.D3;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "4", false))
			{
				this.FastplaceHK = Keys.D4;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "5", false))
			{
				this.FastplaceHK = Keys.D5;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "6", false))
			{
				this.FastplaceHK = Keys.D6;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "7", false))
			{
				this.FastplaceHK = Keys.D7;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "8", false))
			{
				this.FastplaceHK = Keys.D8;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox4.SelectedItem, "9", false))
			{
				this.FastplaceHK = Keys.D9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
		}

		// Token: 0x060004D1 RID: 1233 RVA: 0x0000506A File Offset: 0x0000326A
		private void Timer4_Tick(object sender, EventArgs e)
		{
			this.Timer4.Interval = this.FlatTrackBar8.Value;
			if (Control.MouseButtons == MouseButtons.Right)
			{
				Form2.apimouse_event(16, 0, 0, 0, 0);
				Form2.apimouse_event(8, 0, 0, 0, 0);
			}
		}

		// Token: 0x060004D2 RID: 1234 RVA: 0x0001F744 File Offset: 0x0001D944
		private void Button12_Click(object sender, EventArgs e)
		{
			checked
			{
				this.toggle++;
				if (this.toggle == 1)
				{
					this.Timer4.Start();
					this.Button12.Text = "Toggle Off";
					MyProject.Forms.Arraylist.FastplaceOn.ForeColor = Color.DodgerBlue;
				}
				else
				{
					this.Timer4.Stop();
					this.toggle = 0;
					this.Button12.Text = "Toggle On";
					MyProject.Forms.Arraylist.FastplaceOn.ForeColor = Color.White;
				}
				if (this.toggle == 1 & this.FlatCheckBox21.Checked)
				{
					this.Timer4.Start();
					this.Button12.Text = "Toggle Off";
					this.NotifyIcon1.BalloonTipText = "Fastplace was successfully enabled.";
					this.NotifyIcon1.BalloonTipTitle = "Fastplace";
					this.NotifyIcon1.Visible = true;
					this.NotifyIcon1.ShowBalloonTip(0);
					MyProject.Forms.Arraylist.FastplaceOn.ForeColor = Color.DodgerBlue;
				}
				if (this.toggle == 0 & this.FlatCheckBox21.Checked)
				{
					this.Timer4.Stop();
					this.Button12.Text = "Toggle On";
					this.NotifyIcon1.BalloonTipText = "Fastplace was successfully disabled.";
					this.NotifyIcon1.BalloonTipTitle = "Fastplace";
					this.NotifyIcon1.Visible = true;
					this.NotifyIcon1.ShowBalloonTip(0);
					MyProject.Forms.Arraylist.FastplaceOn.ForeColor = Color.White;
				}
			}
		}

		// Token: 0x060004D3 RID: 1235 RVA: 0x0001F8E0 File Offset: 0x0001DAE0
		private void FastplaceEvents_Tick(object sender, EventArgs e)
		{
			checked
			{
				if (!this.ChatDisable && Form2.GetKeyPress((int)this.FastplaceHK) != 0)
				{
					this.toggle++;
					if (this.toggle == 1)
					{
						this.Timer4.Start();
						this.Button12.Text = "Toggle Off";
						MyProject.Forms.Arraylist.FastplaceOn.ForeColor = Color.DodgerBlue;
					}
					else
					{
						this.Timer4.Stop();
						this.toggle = 0;
						this.Button12.Text = "Toggle On";
						MyProject.Forms.Arraylist.FastplaceOn.ForeColor = Color.White;
					}
					if (this.toggle == 1 & this.FlatCheckBox21.Checked)
					{
						this.Timer4.Start();
						this.Button12.Text = "Toggle Off";
						this.NotifyIcon1.BalloonTipText = "Fastplace was successfully enabled.";
						this.NotifyIcon1.BalloonTipTitle = "Fastplace";
						this.NotifyIcon1.Visible = true;
						this.NotifyIcon1.ShowBalloonTip(0);
						MyProject.Forms.Arraylist.FastplaceOn.ForeColor = Color.DodgerBlue;
					}
					if (this.toggle == 0 & this.FlatCheckBox21.Checked)
					{
						this.Timer4.Stop();
						this.Button12.Text = "Toggle On";
						this.NotifyIcon1.BalloonTipText = "Fastplace was successfully disabled.";
						this.NotifyIcon1.BalloonTipTitle = "Fastplace";
						this.NotifyIcon1.Visible = true;
						this.NotifyIcon1.ShowBalloonTip(0);
						MyProject.Forms.Arraylist.FastplaceOn.ForeColor = Color.White;
					}
				}
			}
		}

		// Token: 0x060004D4 RID: 1236 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatLabel39_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004D5 RID: 1237 RVA: 0x0001FA9C File Offset: 0x0001DC9C
		private void FlatTrackBar10_Scroll(object sender)
		{
			int num = 100;
			this.FlatLabel39.Text = Conversions.ToString(checked(num - this.FlatTrackBar10.Value));
			this.FlatLabel40.Text = Conversions.ToString(this.FlatTrackBar10.Value);
		}

		// Token: 0x060004D6 RID: 1238 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatLabel42_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004D7 RID: 1239 RVA: 0x0001FAE4 File Offset: 0x0001DCE4
		private void Button13_Click(object sender, EventArgs e)
		{
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "A", false))
			{
				this.VelocityHK = Keys.A;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "B", false))
			{
				this.VelocityHK = Keys.B;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "C", false))
			{
				this.VelocityHK = Keys.C;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "D", false))
			{
				this.VelocityHK = Keys.D;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "E", false))
			{
				this.VelocityHK = Keys.E;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "F", false))
			{
				this.VelocityHK = Keys.F;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "G", false))
			{
				this.VelocityHK = Keys.G;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "H", false))
			{
				this.VelocityHK = Keys.H;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "I", false))
			{
				this.VelocityHK = Keys.I;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "J", false))
			{
				this.VelocityHK = Keys.J;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "K", false))
			{
				this.VelocityHK = Keys.K;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "L", false))
			{
				this.VelocityHK = Keys.L;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "M", false))
			{
				this.VelocityHK = Keys.M;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "O", false))
			{
				this.VelocityHK = Keys.O;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "P", false))
			{
				this.VelocityHK = Keys.P;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "Q", false))
			{
				this.VelocityHK = Keys.Q;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "S", false))
			{
				this.VelocityHK = Keys.S;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "T", false))
			{
				this.VelocityHK = Keys.T;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "U", false))
			{
				this.VelocityHK = Keys.U;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "W", false))
			{
				this.VelocityHK = Keys.W;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "X", false))
			{
				this.VelocityHK = Keys.X;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "Y", false))
			{
				this.VelocityHK = Keys.Y;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "Z", false))
			{
				this.VelocityHK = Keys.Z;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "0", false))
			{
				this.VelocityHK = Keys.D0;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "1", false))
			{
				this.VelocityHK = Keys.D1;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "2", false))
			{
				this.VelocityHK = Keys.D2;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "3", false))
			{
				this.VelocityHK = Keys.D3;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "4", false))
			{
				this.VelocityHK = Keys.D4;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "5", false))
			{
				this.VelocityHK = Keys.D5;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "6", false))
			{
				this.VelocityHK = Keys.D6;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "7", false))
			{
				this.VelocityHK = Keys.D7;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "8", false))
			{
				this.VelocityHK = Keys.D8;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox5.SelectedItem, "9", false))
			{
				this.VelocityHK = Keys.D9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
		}

		// Token: 0x060004D8 RID: 1240 RVA: 0x000050A5 File Offset: 0x000032A5
		private void FlatTrackBar9_Scroll(object sender)
		{
			this.FlatLabel47.Text = Conversions.ToString(this.FlatTrackBar9.Value);
		}

		// Token: 0x060004D9 RID: 1241 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatCheckBox22_CheckedChanged(object sender)
		{
		}

		// Token: 0x060004DA RID: 1242 RVA: 0x000050C2 File Offset: 0x000032C2
		private void FlatCheckBox23_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox23.Checked)
			{
				this.FlatTrackBar10.Enabled = false;
			}
			if (!this.FlatCheckBox23.Checked)
			{
				this.FlatTrackBar10.Enabled = true;
			}
		}

		// Token: 0x060004DB RID: 1243 RVA: 0x000050F6 File Offset: 0x000032F6
		private void FlatCheckBox27_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox27.Checked)
			{
				this.FlatNumeric1.Enabled = true;
			}
			if (!this.FlatCheckBox27.Checked)
			{
				this.FlatNumeric1.Enabled = false;
			}
		}

		// Token: 0x060004DC RID: 1244 RVA: 0x0000512A File Offset: 0x0000332A
		private void FlatTrackBar11_Scroll(object sender)
		{
			this.FlatLabel53.Text = Conversions.ToString(this.FlatTrackBar11.Value);
		}

		// Token: 0x060004DD RID: 1245 RVA: 0x00005147 File Offset: 0x00003347
		private void FlatCheckBox25_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox25.Checked)
			{
				this.FlatTrackBar11.Enabled = true;
			}
			if (!this.FlatCheckBox25.Checked)
			{
				this.FlatTrackBar11.Enabled = false;
			}
		}

		// Token: 0x060004DE RID: 1246 RVA: 0x00005014 File Offset: 0x00003214
		private void FlatLabel55_Click(object sender, EventArgs e)
		{
			Process.Start("https://github.com/blackeaglefna/soundpack");
		}

		// Token: 0x060004DF RID: 1247 RVA: 0x00003E9A File Offset: 0x0000209A
		private void TabPage10_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004E0 RID: 1248 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatButton8_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004E1 RID: 1249 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatButton3_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x060004E2 RID: 1250 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatButton2_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x060004E3 RID: 1251 RVA: 0x0000517B File Offset: 0x0000337B
		private void FlatTrackBar12_Scroll(object sender)
		{
			this.FlatLabel59.Text = Conversions.ToString(this.FlatTrackBar12.Value);
		}

		// Token: 0x060004E4 RID: 1252 RVA: 0x00005198 File Offset: 0x00003398
		private void Button5_Click(object sender, EventArgs e)
		{
			Interaction.MsgBox("Setting synchronized.", MsgBoxStyle.Information, "Success");
		}

		// Token: 0x060004E5 RID: 1253 RVA: 0x000051AC File Offset: 0x000033AC
		private void FlatCheckBox6_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox6.Checked)
			{
				this.FlatNumeric2.Enabled = true;
			}
			if (!this.FlatCheckBox6.Checked)
			{
				this.FlatNumeric2.Enabled = false;
			}
		}

		// Token: 0x060004E6 RID: 1254 RVA: 0x000051E0 File Offset: 0x000033E0
		private void FlatCheckBox29_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox29.Checked)
			{
				this.FlatNumeric3.Enabled = true;
			}
			if (!this.FlatCheckBox29.Checked)
			{
				this.FlatNumeric3.Enabled = false;
			}
		}

		// Token: 0x060004E7 RID: 1255 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatButton9_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004E8 RID: 1256 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatTextBox2_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060004E9 RID: 1257 RVA: 0x00004F85 File Offset: 0x00003185
		private void FlatButton1_Click_1(object sender, EventArgs e)
		{
			MyProject.Computer.Audio.Stop();
		}

		// Token: 0x060004EA RID: 1258 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatCheckBox33_CheckedChanged(object sender)
		{
		}

		// Token: 0x060004EB RID: 1259 RVA: 0x00005214 File Offset: 0x00003414
		private void FlatButton9_Click_1(object sender, EventArgs e)
		{
			base.Hide();
		}

		// Token: 0x060004EC RID: 1260 RVA: 0x00004FE6 File Offset: 0x000031E6
		[MethodImpl(MethodImplOptions.NoInlining | MethodImplOptions.NoOptimization)]
		private void FlatButton10_Click(object sender, EventArgs e)
		{
			ProjectData.EndApp();
		}

		// Token: 0x060004ED RID: 1261 RVA: 0x0000521C File Offset: 0x0000341C
		private void FlatButton11_Click(object sender, EventArgs e)
		{
			Thread.Sleep(3000);
			base.Hide();
			Thread.Sleep(500);
			base.Show();
			Interaction.MsgBox("Cache cleared successfully.", MsgBoxStyle.Information, "Success");
		}

		// Token: 0x060004EE RID: 1262 RVA: 0x00020164 File Offset: 0x0001E364
		[MethodImpl(MethodImplOptions.NoInlining | MethodImplOptions.NoOptimization)]
		private void Button14_Click(object sender, EventArgs e)
		{
			if (!this.FlatCheckBox33.Checked)
			{
				Thread.Sleep(2000);
				ProjectData.EndApp();
			}
			if (this.FlatCheckBox33.Checked)
			{
				Process.Start(new ProcessStartInfo
				{
					Arguments = "/C choice /C Y /N /D Y /T 3 & Del " + Application.ExecutablePath,
					FileName = "cmd.exe"
				});
				Thread.Sleep(2000);
				ProjectData.EndApp();
			}
		}

		// Token: 0x060004EF RID: 1263 RVA: 0x000201D4 File Offset: 0x0001E3D4
		private string getmyname(string path)
		{
			string result;
			if (path.IndexOf('\\') == -1)
			{
				result = string.Empty;
			}
			else
			{
				result = path.Substring(checked(path.LastIndexOf('\\') + 1));
			}
			return result;
		}

		// Token: 0x060004F0 RID: 1264 RVA: 0x00020208 File Offset: 0x0001E408
		private void Tap_Tick(object sender, EventArgs e)
		{
			if (base.ShowInTaskbar && Form2.GetKeyPress(36) != 0)
			{
				base.Hide();
				base.ShowInTaskbar = false;
				this.FlatCheckBox11.Checked = true;
			}
			if (!base.ShowInTaskbar && Form2.GetKeyPress(36) != 0)
			{
				base.Show();
				base.ShowInTaskbar = true;
				this.FlatCheckBox11.Checked = false;
			}
		}

		// Token: 0x060004F1 RID: 1265 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatTextBox4_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060004F2 RID: 1266 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatCheckBox14_CheckedChanged(object sender)
		{
		}

		// Token: 0x060004F3 RID: 1267 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatLabel4_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004F4 RID: 1268 RVA: 0x00003E9A File Offset: 0x0000209A
		private void TrackEnd_Scroll_1(object sender)
		{
		}

		// Token: 0x060004F5 RID: 1269 RVA: 0x00005214 File Offset: 0x00003414
		private void Button15_Click(object sender, EventArgs e)
		{
			base.Hide();
		}

		// Token: 0x060004F6 RID: 1270
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "SystemParametersInfoA", ExactSpelling = true, SetLastError = true)]
		private static extern int SystemParametersInfo(int uAction, int uParam, ref object lpvParam, int fuWinIni);

		// Token: 0x060004F7 RID: 1271 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatTrackBar13_Scroll(object sender)
		{
		}

		// Token: 0x060004F8 RID: 1272 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatLabel64_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004F9 RID: 1273 RVA: 0x00020270 File Offset: 0x0001E470
		private void FlatTrackBar13_Scroll_1(object sender)
		{
			if (this.FlatTrackBar13.Value > this.FlatTrackBar14.Value)
			{
				this.FlatTrackBar13.Value = this.FlatTrackBar14.Value;
			}
			this.FlatLabel67.Text = Conversions.ToString(this.FlatTrackBar13.Value);
		}

		// Token: 0x060004FA RID: 1274 RVA: 0x000202C8 File Offset: 0x0001E4C8
		private void FlatTrackBar14_Scroll(object sender)
		{
			if (this.FlatTrackBar14.Value < this.FlatTrackBar13.Value)
			{
				this.FlatTrackBar14.Value = this.FlatTrackBar13.Value;
			}
			this.FlatLabel70.Text = Conversions.ToString(this.FlatTrackBar14.Value);
		}

		// Token: 0x060004FB RID: 1275 RVA: 0x00003E9A File Offset: 0x0000209A
		private void TabPage7_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004FC RID: 1276 RVA: 0x00003E9A File Offset: 0x0000209A
		private void TabPage17_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004FD RID: 1277 RVA: 0x00020320 File Offset: 0x0001E520
		private void Button16_Click(object sender, EventArgs e)
		{
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "A", false))
			{
				this.AimAssistHK = Keys.A;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "B", false))
			{
				this.AimAssistHK = Keys.B;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "C", false))
			{
				this.AimAssistHK = Keys.C;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "D", false))
			{
				this.AimAssistHK = Keys.D;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "E", false))
			{
				this.AimAssistHK = Keys.E;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "F", false))
			{
				this.AimAssistHK = Keys.F;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "G", false))
			{
				this.AimAssistHK = Keys.G;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "H", false))
			{
				this.AimAssistHK = Keys.H;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "I", false))
			{
				this.AimAssistHK = Keys.I;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "J", false))
			{
				this.AimAssistHK = Keys.J;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "K", false))
			{
				this.AimAssistHK = Keys.K;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "L", false))
			{
				this.AimAssistHK = Keys.L;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "M", false))
			{
				this.AimAssistHK = Keys.M;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "O", false))
			{
				this.AimAssistHK = Keys.O;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "P", false))
			{
				this.AimAssistHK = Keys.P;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "Q", false))
			{
				this.AimAssistHK = Keys.Q;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "S", false))
			{
				this.AimAssistHK = Keys.S;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "T", false))
			{
				this.AimAssistHK = Keys.T;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "U", false))
			{
				this.AimAssistHK = Keys.U;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "W", false))
			{
				this.AimAssistHK = Keys.W;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "X", false))
			{
				this.AimAssistHK = Keys.X;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "Y", false))
			{
				this.AimAssistHK = Keys.Y;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "Z", false))
			{
				this.AimAssistHK = Keys.Z;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "0", false))
			{
				this.AimAssistHK = Keys.D0;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "1", false))
			{
				this.AimAssistHK = Keys.D1;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "2", false))
			{
				this.AimAssistHK = Keys.D2;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "3", false))
			{
				this.AimAssistHK = Keys.D3;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "4", false))
			{
				this.AimAssistHK = Keys.D4;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "5", false))
			{
				this.AimAssistHK = Keys.D5;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "6", false))
			{
				this.AimAssistHK = Keys.D6;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "7", false))
			{
				this.AimAssistHK = Keys.D7;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "8", false))
			{
				this.AimAssistHK = Keys.D8;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox7.SelectedItem, "9", false))
			{
				this.AimAssistHK = Keys.D9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
		}

		// Token: 0x060004FE RID: 1278 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FormSkin1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060004FF RID: 1279 RVA: 0x00003E9A File Offset: 0x0000209A
		private void Button19_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000500 RID: 1280 RVA: 0x00005250 File Offset: 0x00003450
		private void Button19_Click_1(object sender, EventArgs e)
		{
			Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
		}

		// Token: 0x06000501 RID: 1281 RVA: 0x00005264 File Offset: 0x00003464
		private void Button6_Click_1(object sender, EventArgs e)
		{
			Interaction.MsgBox("Simulation edited.", MsgBoxStyle.Information, "Success");
			this.FlatComboBox6.Text = Conversions.ToString(this.FlatComboBox6.SelectedItem);
		}

		// Token: 0x06000502 RID: 1282 RVA: 0x00003E9A File Offset: 0x0000209A
		private void Button20_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000503 RID: 1283 RVA: 0x00003E9A File Offset: 0x0000209A
		private void Button18_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000504 RID: 1284 RVA: 0x00003E9A File Offset: 0x0000209A
		private void Form2_FormClosing(object sender, FormClosingEventArgs e)
		{
		}

		// Token: 0x06000505 RID: 1285 RVA: 0x00005293 File Offset: 0x00003493
		private void FlatTrackBar15_Scroll(object sender)
		{
			this.FlatLabel76.Text = Conversions.ToString(this.FlatTrackBar15.Value);
		}

		// Token: 0x06000506 RID: 1286 RVA: 0x000209A0 File Offset: 0x0001EBA0
		private void Button21_Click(object sender, EventArgs e)
		{
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "A", false))
			{
				this.StrafespeedHK = Keys.A;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "B", false))
			{
				this.StrafespeedHK = Keys.B;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "C", false))
			{
				this.StrafespeedHK = Keys.C;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "D", false))
			{
				this.StrafespeedHK = Keys.D;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "E", false))
			{
				this.StrafespeedHK = Keys.E;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "F", false))
			{
				this.StrafespeedHK = Keys.F;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "G", false))
			{
				this.StrafespeedHK = Keys.G;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "H", false))
			{
				this.StrafespeedHK = Keys.H;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "I", false))
			{
				this.StrafespeedHK = Keys.I;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "J", false))
			{
				this.StrafespeedHK = Keys.J;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "K", false))
			{
				this.StrafespeedHK = Keys.K;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "L", false))
			{
				this.StrafespeedHK = Keys.L;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "M", false))
			{
				this.StrafespeedHK = Keys.M;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "O", false))
			{
				this.StrafespeedHK = Keys.O;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "P", false))
			{
				this.StrafespeedHK = Keys.P;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "Q", false))
			{
				this.StrafespeedHK = Keys.Q;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "S", false))
			{
				this.StrafespeedHK = Keys.S;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "T", false))
			{
				this.StrafespeedHK = Keys.T;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "U", false))
			{
				this.StrafespeedHK = Keys.U;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "W", false))
			{
				this.StrafespeedHK = Keys.W;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "X", false))
			{
				this.StrafespeedHK = Keys.X;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "Y", false))
			{
				this.StrafespeedHK = Keys.Y;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "Z", false))
			{
				this.StrafespeedHK = Keys.Z;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "0", false))
			{
				this.StrafespeedHK = Keys.D0;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "1", false))
			{
				this.StrafespeedHK = Keys.D1;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "2", false))
			{
				this.StrafespeedHK = Keys.D2;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "3", false))
			{
				this.StrafespeedHK = Keys.D3;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "4", false))
			{
				this.StrafespeedHK = Keys.D4;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "5", false))
			{
				this.StrafespeedHK = Keys.D5;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "6", false))
			{
				this.StrafespeedHK = Keys.D6;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "7", false))
			{
				this.StrafespeedHK = Keys.D7;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "8", false))
			{
				this.StrafespeedHK = Keys.D8;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox8.SelectedItem, "9", false))
			{
				this.StrafespeedHK = Keys.D9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
		}

		// Token: 0x06000507 RID: 1287 RVA: 0x000052B0 File Offset: 0x000034B0
		private void FlatCheckBox41_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox41.Checked)
			{
				this.ChatDisable = true;
			}
			if (!this.FlatCheckBox41.Checked)
			{
				this.ChatDisable = false;
			}
		}

		// Token: 0x06000508 RID: 1288 RVA: 0x00021020 File Offset: 0x0001F220
		private void TempDisable_Tick(object sender, EventArgs e)
		{
			if (Form2.GetKeyPress(84) != 0)
			{
				this.ChatDisable = true;
				this.FlatCheckBox41.Checked = true;
			}
			if (Form2.GetKeyPress(27) != 0)
			{
				this.ChatDisable = false;
				this.FlatCheckBox41.Checked = false;
			}
			if (Form2.GetKeyPress(13) != 0)
			{
				this.ChatDisable = false;
				this.FlatCheckBox41.Checked = false;
			}
		}

		// Token: 0x06000509 RID: 1289 RVA: 0x000052DA File Offset: 0x000034DA
		private void FlatTrackBar16_Scroll(object sender)
		{
			this.Timer5.Interval = this.FlatTrackBar16.Value;
		}

		// Token: 0x0600050A RID: 1290 RVA: 0x00003E9A File Offset: 0x0000209A
		private void Button22_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600050B RID: 1291 RVA: 0x0002108C File Offset: 0x0001F28C
		private void ScriptEvents_Tick(object sender, EventArgs e)
		{
			checked
			{
				if (!this.ChatDisable && Form2.GetKeyPress((int)this.ScriptHK) != 0)
				{
					this.script++;
					if (this.script == 1)
					{
						this.Timer5.Start();
						this.Button23.Text = "Toggle Off";
						return;
					}
					this.Timer5.Stop();
					this.script = 0;
					this.Button23.Text = "Toggle On";
				}
			}
		}

		// Token: 0x0600050C RID: 1292 RVA: 0x000052F2 File Offset: 0x000034F2
		private void Timer5_Tick(object sender, EventArgs e)
		{
			InputHelper.SetKeyState(Keys.LShiftKey, false);
			Thread.Sleep(this.presstime);
			InputHelper.SetKeyState(Keys.LShiftKey, true);
		}

		// Token: 0x0600050D RID: 1293 RVA: 0x00003E9A File Offset: 0x0000209A
		private void Button23_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600050E RID: 1294 RVA: 0x00003E9A File Offset: 0x0000209A
		private void TabPage31_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600050F RID: 1295 RVA: 0x00021108 File Offset: 0x0001F308
		private void Button22_Click_1(object sender, EventArgs e)
		{
			this.FlatComboBox9.Text = Conversions.ToString(this.FlatComboBox9.SelectedItem);
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "F9", false))
			{
				this.ScriptHK = Keys.F9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "F10", false))
			{
				this.ScriptHK = Keys.F10;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "R", false))
			{
				this.ScriptHK = Keys.R;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "V", false))
			{
				this.ScriptHK = Keys.V;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "N", false))
			{
				this.ScriptHK = Keys.N;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "Alt", false))
			{
				this.ScriptHK = Keys.Menu;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "Shift", false))
			{
				this.ScriptHK = Keys.Shift;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "Insert", false))
			{
				this.ScriptHK = Keys.Insert;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "A", false))
			{
				this.ScriptHK = Keys.A;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "B", false))
			{
				this.ScriptHK = Keys.B;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "C", false))
			{
				this.ScriptHK = Keys.C;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "D", false))
			{
				this.ScriptHK = Keys.D;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "E", false))
			{
				this.ScriptHK = Keys.E;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "F", false))
			{
				this.ScriptHK = Keys.F;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "G", false))
			{
				this.ScriptHK = Keys.G;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "H", false))
			{
				this.ScriptHK = Keys.H;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "I", false))
			{
				this.ScriptHK = Keys.I;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "J", false))
			{
				this.ScriptHK = Keys.J;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "K", false))
			{
				this.ScriptHK = Keys.K;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "L", false))
			{
				this.ScriptHK = Keys.L;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "M", false))
			{
				this.ScriptHK = Keys.M;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "O", false))
			{
				this.ScriptHK = Keys.O;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "P", false))
			{
				this.ScriptHK = Keys.P;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "Q", false))
			{
				this.ScriptHK = Keys.Q;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "S", false))
			{
				this.ScriptHK = Keys.S;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "T", false))
			{
				this.ScriptHK = Keys.T;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "U", false))
			{
				this.ScriptHK = Keys.U;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "W", false))
			{
				this.ScriptHK = Keys.W;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "X", false))
			{
				this.ScriptHK = Keys.X;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "Y", false))
			{
				this.ScriptHK = Keys.Y;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "Z", false))
			{
				this.ScriptHK = Keys.Z;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "0", false))
			{
				this.ScriptHK = Keys.D0;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "1", false))
			{
				this.ScriptHK = Keys.D1;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "2", false))
			{
				this.ScriptHK = Keys.D2;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "3", false))
			{
				this.ScriptHK = Keys.D3;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "4", false))
			{
				this.ScriptHK = Keys.D4;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "5", false))
			{
				this.ScriptHK = Keys.D5;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "6", false))
			{
				this.ScriptHK = Keys.D6;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "7", false))
			{
				this.ScriptHK = Keys.D7;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "8", false))
			{
				this.ScriptHK = Keys.D8;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox9.SelectedItem, "9", false))
			{
				this.ScriptHK = Keys.D9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			base.Invalidate();
		}

		// Token: 0x06000510 RID: 1296 RVA: 0x00021958 File Offset: 0x0001FB58
		private void Button23_Click_1(object sender, EventArgs e)
		{
			checked
			{
				this.tapper++;
				if (this.tapper == 1)
				{
					this.Timer6.Start();
					this.Button23.Text = "Toggle Off";
					return;
				}
				this.Timer6.Stop();
				this.tapper = 0;
				this.Button23.Text = "Toggle On";
			}
		}

		// Token: 0x06000511 RID: 1297 RVA: 0x00005315 File Offset: 0x00003515
		private void FlatTrackBar16_Scroll_1(object sender)
		{
			this.Timer5.Interval = this.FlatTrackBar16.Value;
			this.FlatLabel80.Text = Conversions.ToString(this.FlatTrackBar16.Value);
		}

		// Token: 0x06000512 RID: 1298 RVA: 0x00005348 File Offset: 0x00003548
		private void FlatTrackBar18_Scroll(object sender)
		{
			this.presstime = this.FlatTrackBar18.Value;
			this.FlatLabel88.Text = Conversions.ToString(this.FlatTrackBar18.Value);
		}

		// Token: 0x06000513 RID: 1299 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatComboBox9_SelectedIndexChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000514 RID: 1300 RVA: 0x000219BC File Offset: 0x0001FBBC
		private void Button25_Click(object sender, EventArgs e)
		{
			this.FlatComboBox10.Text = Conversions.ToString(this.FlatComboBox10.SelectedItem);
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "F9", false))
			{
				this.TapHK = Keys.F9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "F10", false))
			{
				this.TapHK = Keys.F10;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "R", false))
			{
				this.TapHK = Keys.R;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "V", false))
			{
				this.TapHK = Keys.V;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "N", false))
			{
				this.TapHK = Keys.N;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "Alt", false))
			{
				this.TapHK = Keys.Menu;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "Shift", false))
			{
				this.TapHK = Keys.Shift;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			else if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "Insert", false))
			{
				this.TapHK = Keys.Insert;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "A", false))
			{
				this.TapHK = Keys.A;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "B", false))
			{
				this.TapHK = Keys.B;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "C", false))
			{
				this.TapHK = Keys.C;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "D", false))
			{
				this.TapHK = Keys.D;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "E", false))
			{
				this.TapHK = Keys.E;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "F", false))
			{
				this.TapHK = Keys.F;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "G", false))
			{
				this.TapHK = Keys.G;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "H", false))
			{
				this.TapHK = Keys.H;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "I", false))
			{
				this.TapHK = Keys.I;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "J", false))
			{
				this.TapHK = Keys.J;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "K", false))
			{
				this.TapHK = Keys.K;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "L", false))
			{
				this.TapHK = Keys.L;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "M", false))
			{
				this.TapHK = Keys.M;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "O", false))
			{
				this.TapHK = Keys.O;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "P", false))
			{
				this.TapHK = Keys.P;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "Q", false))
			{
				this.TapHK = Keys.Q;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "S", false))
			{
				this.TapHK = Keys.S;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "T", false))
			{
				this.TapHK = Keys.T;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "U", false))
			{
				this.TapHK = Keys.U;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "W", false))
			{
				this.TapHK = Keys.W;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "X", false))
			{
				this.TapHK = Keys.X;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "Y", false))
			{
				this.TapHK = Keys.Y;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "Z", false))
			{
				this.TapHK = Keys.Z;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "0", false))
			{
				this.TapHK = Keys.D0;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "1", false))
			{
				this.TapHK = Keys.D1;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "2", false))
			{
				this.TapHK = Keys.D2;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "3", false))
			{
				this.TapHK = Keys.D3;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "4", false))
			{
				this.TapHK = Keys.D4;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "5", false))
			{
				this.TapHK = Keys.D5;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "6", false))
			{
				this.TapHK = Keys.D6;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "7", false))
			{
				this.TapHK = Keys.D7;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "8", false))
			{
				this.TapHK = Keys.D8;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			if (Operators.ConditionalCompareObjectEqual(this.FlatComboBox10.SelectedItem, "9", false))
			{
				this.TapHK = Keys.D9;
				Interaction.MsgBox("Keybind synchronized.", MsgBoxStyle.Information, "Success");
			}
			base.Invalidate();
		}

		// Token: 0x06000515 RID: 1301 RVA: 0x0002220C File Offset: 0x0002040C
		private void TapEvents_Tick(object sender, EventArgs e)
		{
			checked
			{
				if (!this.ChatDisable && Form2.GetKeyPress((int)this.TapHK) != 0)
				{
					this.tapper++;
					if (this.tapper == 1)
					{
						this.Timer6.Start();
						this.Button23.Text = "Toggle Off";
						return;
					}
					this.Timer6.Stop();
					this.tapper = 0;
					this.Button23.Text = "Toggle On";
				}
			}
		}

		// Token: 0x06000516 RID: 1302 RVA: 0x00005376 File Offset: 0x00003576
		private void Timer6_Tick(object sender, EventArgs e)
		{
			InputHelper.SetKeyState(this.tapkey, false);
			Thread.Sleep(this.presstime);
			InputHelper.SetKeyState(this.tapkey, true);
		}

		// Token: 0x06000517 RID: 1303 RVA: 0x00003E9A File Offset: 0x0000209A
		private void TabPage33_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000518 RID: 1304 RVA: 0x0000539B File Offset: 0x0000359B
		private void FlatTrackBar19_Scroll(object sender)
		{
			this.FlatLabel91.Text = Conversions.ToString(this.FlatTrackBar19.Value);
			this.taptime = this.FlatTrackBar19.Value;
		}

		// Token: 0x06000519 RID: 1305 RVA: 0x000053C9 File Offset: 0x000035C9
		private void CheckBox1_CheckedChanged(object sender, EventArgs e)
		{
			if (this.CheckBox1.Checked)
			{
				this.tapkey = Keys.W;
				return;
			}
			this.tapkey = Keys.None;
		}

		// Token: 0x0600051A RID: 1306 RVA: 0x00022288 File Offset: 0x00020488
		private void CheckBox3_CheckedChanged(object sender, EventArgs e)
		{
			if (this.CheckBox3.Checked)
			{
				this.tapkey = Keys.A;
				this.CheckBox1.Checked = false;
				this.CheckBox2.Checked = false;
				this.CheckBox4.Checked = false;
				return;
			}
			this.tapkey = Keys.None;
		}

		// Token: 0x0600051B RID: 1307 RVA: 0x000222D8 File Offset: 0x000204D8
		private void CheckBox2_CheckedChanged(object sender, EventArgs e)
		{
			if (this.CheckBox2.Checked)
			{
				this.tapkey = Keys.S;
				this.CheckBox1.Checked = false;
				this.CheckBox3.Checked = false;
				this.CheckBox4.Checked = false;
				return;
			}
			this.tapkey = Keys.None;
		}

		// Token: 0x0600051C RID: 1308 RVA: 0x00022328 File Offset: 0x00020528
		private void CheckBox4_CheckedChanged(object sender, EventArgs e)
		{
			if (this.CheckBox4.Checked)
			{
				this.tapkey = Keys.D;
				this.CheckBox1.Checked = false;
				this.CheckBox3.Checked = false;
				this.CheckBox2.Checked = false;
				return;
			}
			this.tapkey = Keys.None;
		}

		// Token: 0x0600051D RID: 1309 RVA: 0x00022378 File Offset: 0x00020578
		private void Button24_Click(object sender, EventArgs e)
		{
			checked
			{
				this.script++;
				if (this.script == 1)
				{
					this.Timer5.Start();
					this.Button24.Text = "Toggle Off";
					return;
				}
				this.Timer5.Stop();
				this.script = 0;
				this.Button24.Text = "Toggle On";
			}
		}

		// Token: 0x0600051E RID: 1310 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatTextBox1_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x0600051F RID: 1311 RVA: 0x000223DC File Offset: 0x000205DC
		private void MisplaceEvents_Tick(object sender, EventArgs e)
		{
			checked
			{
				if (Form2.GetKeyPress((int)this.MisplaceHK) != 0)
				{
					this.MisplaceArraylist++;
					if (this.MisplaceArraylist == 1)
					{
						MyProject.Forms.Arraylist.MisplaceOn.ForeColor = Color.DodgerBlue;
						return;
					}
					MyProject.Forms.Arraylist.MisplaceOn.ForeColor = Color.White;
					this.MisplaceArraylist = 0;
				}
			}
		}

		// Token: 0x06000520 RID: 1312 RVA: 0x0002244C File Offset: 0x0002064C
		private void VelocityEvents_Tick(object sender, EventArgs e)
		{
			checked
			{
				if (Form2.GetKeyPress((int)this.VelocityHK) != 0)
				{
					this.VelocityArraylist++;
					if (this.VelocityArraylist == 1)
					{
						MyProject.Forms.Arraylist.VelocityOn.ForeColor = Color.DodgerBlue;
						this.Button12.Text = "Toggle Off";
						return;
					}
					MyProject.Forms.Arraylist.VelocityOn.ForeColor = Color.White;
					this.VelocityArraylist = 0;
					this.Button12.Text = "Toggle On";
				}
			}
		}

		// Token: 0x06000521 RID: 1313 RVA: 0x000224DC File Offset: 0x000206DC
		private void AimAssistEvents_Tick(object sender, EventArgs e)
		{
			checked
			{
				if (Form2.GetKeyPress((int)this.AimAssistHK) != 0)
				{
					this.AimassistArraylist++;
					if (this.AimassistArraylist == 1)
					{
						MyProject.Forms.Arraylist.AimAssistOn.ForeColor = Color.DodgerBlue;
						this.Button29.Text = "Toggle Off";
						return;
					}
					MyProject.Forms.Arraylist.AimAssistOn.ForeColor = Color.White;
					this.AimassistArraylist = 0;
					this.Button29.Text = "Toggle On";
				}
			}
		}

		// Token: 0x06000522 RID: 1314 RVA: 0x0002256C File Offset: 0x0002076C
		private void StrafespeedEvents_Tick(object sender, EventArgs e)
		{
			checked
			{
				if (Form2.GetKeyPress((int)this.StrafespeedHK) != 0)
				{
					this.Strafespeedarraylist++;
					if (this.Strafespeedarraylist == 1)
					{
						MyProject.Forms.Arraylist.StrafespeedOn.ForeColor = Color.DodgerBlue;
						this.Button30.Text = "Toggle Off";
						return;
					}
					MyProject.Forms.Arraylist.StrafespeedOn.ForeColor = Color.White;
					this.Strafespeedarraylist = 0;
					this.Button30.Text = "Toggle On";
				}
			}
		}

		// Token: 0x06000523 RID: 1315 RVA: 0x000225FC File Offset: 0x000207FC
		private void ArraylistEvents_Tick(object sender, EventArgs e)
		{
			checked
			{
				if (Form2.GetKeyPress(117) != 0)
				{
					this.Arraylistcheck++;
					if (this.Arraylistcheck == 1)
					{
						MyProject.Forms.Arraylist.Show();
						return;
					}
					MyProject.Forms.Arraylist.Hide();
					this.Arraylistcheck = 0;
				}
			}
		}

		// Token: 0x06000524 RID: 1316 RVA: 0x00022654 File Offset: 0x00020854
		private void Button19_Click_2(object sender, EventArgs e)
		{
			checked
			{
				this.VelocityArraylist++;
				if (this.VelocityArraylist == 1)
				{
					this.Timer4.Start();
					this.Button19.Text = "Toggle Off";
					MyProject.Forms.Arraylist.VelocityOn.ForeColor = Color.DodgerBlue;
					return;
				}
				this.Timer4.Stop();
				this.VelocityArraylist = 0;
				this.Button19.Text = "Toggle On";
				MyProject.Forms.Arraylist.VelocityOn.ForeColor = Color.White;
			}
		}

		// Token: 0x06000525 RID: 1317 RVA: 0x000226E8 File Offset: 0x000208E8
		private void Button29_Click(object sender, EventArgs e)
		{
			checked
			{
				this.AimassistArraylist++;
				if (this.AimassistArraylist == 1)
				{
					this.Timer4.Start();
					this.Button29.Text = "Toggle Off";
					MyProject.Forms.Arraylist.AimAssistOn.ForeColor = Color.DodgerBlue;
					return;
				}
				this.Timer4.Stop();
				this.AimassistArraylist = 0;
				this.Button29.Text = "Toggle On";
				MyProject.Forms.Arraylist.AimAssistOn.ForeColor = Color.White;
			}
		}

		// Token: 0x06000526 RID: 1318 RVA: 0x0002277C File Offset: 0x0002097C
		private void Button30_Click(object sender, EventArgs e)
		{
			checked
			{
				this.Strafespeedarraylist++;
				if (this.Strafespeedarraylist == 1)
				{
					this.Timer4.Start();
					this.Button30.Text = "Toggle Off";
					MyProject.Forms.Arraylist.StrafespeedOn.ForeColor = Color.DodgerBlue;
					return;
				}
				this.Timer4.Stop();
				this.Strafespeedarraylist = 0;
				this.Button30.Text = "Toggle On";
				MyProject.Forms.Arraylist.StrafespeedOn.ForeColor = Color.White;
			}
		}

		// Token: 0x06000527 RID: 1319 RVA: 0x00022810 File Offset: 0x00020A10
		private void Button18_Click_1(object sender, EventArgs e)
		{
			string text = "0123456789ABCDEFGHIJKLMNOP";
			int i = 0;
			Random random = new Random();
			this.Textbox2.Text = "";
			checked
			{
				while (i < 16)
				{
					string value = Conversions.ToString(random.Next(0, text.Length));
					this.Textbox2.Text = this.Textbox2.Text + Conversions.ToString(text[Conversions.ToInteger(value)]);
					i++;
				}
				string text2 = "1234756980DFOIKBCAPRSWXZYTLMUNKJE";
				int j = 0;
				Random random2 = new Random();
				this.Textbox3.Text = "";
				while (j < 12)
				{
					string value2 = Conversions.ToString(random2.Next(0, text2.Length));
					this.Textbox3.Text = this.Textbox3.Text + Conversions.ToString(text2[Conversions.ToInteger(value2)]);
					j++;
				}
				string text3 = "0123456789";
				int k = 0;
				Random random3 = new Random();
				this.Textbox4.Text = "";
				while (k < 16)
				{
					string value3 = Conversions.ToString(random3.Next(0, text3.Length));
					this.Textbox4.Text = this.Textbox4.Text + Conversions.ToString(text3[Conversions.ToInteger(value3)]);
					k++;
				}
			}
		}

		// Token: 0x06000528 RID: 1320 RVA: 0x00004FE6 File Offset: 0x000031E6
		[MethodImpl(MethodImplOptions.NoInlining | MethodImplOptions.NoOptimization)]
		private void Button28_Click(object sender, EventArgs e)
		{
			ProjectData.EndApp();
		}

		// Token: 0x06000529 RID: 1321 RVA: 0x000053E8 File Offset: 0x000035E8
		private void Button27_Click(object sender, EventArgs e)
		{
			Process.Start("cmd.exe");
			Thread.Sleep(700);
			Process.GetProcessesByName("cmd")[0].Kill();
		}

		// Token: 0x04000204 RID: 516
		private const int KEYEVENTF_KEYDOWN = 0;

		// Token: 0x04000205 RID: 517
		private const int KEYEVENTF_KEYUP = 2;

		// Token: 0x04000206 RID: 518
		private Keys CurrentHK;

		// Token: 0x04000207 RID: 519
		private string HkStr;

		// Token: 0x04000208 RID: 520
		private bool PState;

		// Token: 0x04000209 RID: 521
		private bool RandTime;

		// Token: 0x0400020A RID: 522
		private bool RandCPS;

		// Token: 0x0400020B RID: 523
		private AutoResetEvent PE;

		// Token: 0x0400020C RID: 524
		private bool IsEnabled;

		// Token: 0x0400020D RID: 525
		private Keys FastplaceHK;

		// Token: 0x0400020E RID: 526
		private string FPStr;

		// Token: 0x0400020F RID: 527
		private bool FState;

		// Token: 0x04000210 RID: 528
		private bool FastplaceEnabled;

		// Token: 0x04000211 RID: 529
		private bool ChatDisable;

		// Token: 0x04000212 RID: 530
		private Keys ScriptHK;

		// Token: 0x04000213 RID: 531
		private Keys TapHK;

		// Token: 0x04000214 RID: 532
		private Keys MisplaceHK;

		// Token: 0x04000215 RID: 533
		private Keys VelocityHK;

		// Token: 0x04000216 RID: 534
		private Keys StrafespeedHK;

		// Token: 0x04000217 RID: 535
		private Keys AimAssistHK;

		// Token: 0x04000218 RID: 536
		public const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x04000219 RID: 537
		public const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x0400021A RID: 538
		public const int MOUSEEVENTF_MIDDLEDOWN = 32;

		// Token: 0x0400021B RID: 539
		public const int MOUSEEVENTF_MIDDLEUP = 64;

		// Token: 0x0400021C RID: 540
		public const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x0400021D RID: 541
		public const int MOUSEEVENTF_RIGHTUP = 16;

		// Token: 0x0400021E RID: 542
		public const int MOUSEEVENTF_MOVE = 1;

		// Token: 0x0400021F RID: 543
		private int toggle;

		// Token: 0x04000220 RID: 544
		private int script;

		// Token: 0x04000221 RID: 545
		private int presstime;

		// Token: 0x04000222 RID: 546
		private int taptime;

		// Token: 0x04000223 RID: 547
		private int buttonpress;

		// Token: 0x04000224 RID: 548
		private int tapper;

		// Token: 0x04000225 RID: 549
		private Keys tapkey;

		// Token: 0x04000226 RID: 550
		private Process ActiveProcess;

		// Token: 0x04000227 RID: 551
		private int MisplaceArraylist;

		// Token: 0x04000228 RID: 552
		private int AutoclickerArraylist;

		// Token: 0x04000229 RID: 553
		private int FastplaceArraylist;

		// Token: 0x0400022A RID: 554
		private int VelocityArraylist;

		// Token: 0x0400022B RID: 555
		private int AimassistArraylist;

		// Token: 0x0400022C RID: 556
		private int Strafespeedarraylist;

		// Token: 0x0400022D RID: 557
		private int Arraylistcheck;

		// Token: 0x0400022E RID: 558
		private const int SPI_SETMOUSESPEED = 113;

		// Token: 0x02000033 RID: 51
		public sealed class ProcessHelper
		{
			// Token: 0x0600052A RID: 1322 RVA: 0x00002123 File Offset: 0x00000323
			private ProcessHelper()
			{
			}

			// Token: 0x0600052B RID: 1323
			[DllImport("user32.dll", SetLastError = true)]
			private static extern IntPtr GetForegroundWindow();

			// Token: 0x0600052C RID: 1324
			[DllImport("user32.dll", SetLastError = true)]
			private static extern int GetWindowThreadProcessId(IntPtr hWnd, ref uint lpdwProcessId);

			// Token: 0x0600052D RID: 1325 RVA: 0x00022980 File Offset: 0x00020B80
			public static Process GetActiveProcess()
			{
				IntPtr foregroundWindow = Form2.ProcessHelper.GetForegroundWindow();
				Process result;
				if (foregroundWindow == IntPtr.Zero)
				{
					result = null;
				}
				else
				{
					uint num = 0U;
					Form2.ProcessHelper.GetWindowThreadProcessId(foregroundWindow, ref num);
					if ((ulong)num == 0UL)
					{
						result = null;
					}
					else
					{
						result = Process.GetProcessById(checked((int)num));
					}
				}
				return result;
			}
		}
	}
}
