using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Net;
using System.Net.NetworkInformation;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Speech.Recognition;
using System.Speech.Recognition.SrgsGrammar;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x020000E8 RID: 232
[DesignerGenerated]
public partial class skeet : Form
{
	// Token: 0x0600099F RID: 2463 RVA: 0x0002D228 File Offset: 0x0002B428
	public skeet()
	{
		base.MouseMove += this.skeet_MouseMove;
		base.Load += this.skeet_Load;
		base.Closing += this.skeet_Closing;
		this.int_0 = 0;
		this.string_4 = "";
		this.string_5 = "";
		this.string_6 = "";
		this.string_7 = "";
		this.string_8 = "";
		this.string_9 = "";
		this.string_10 = "Enabled";
		this.int_1 = 0;
		this.string_11 = "";
		this.string_12 = "";
		this.string_13 = "";
		this.string_14 = "";
		this.string_15 = "";
		this.string_16 = "";
		this.string_17 = "";
		this.string_18 = "";
		this.keys_0 = Keys.V;
		this.string_19 = "no";
		this.object_0 = 0;
		this.bool_0 = false;
		this.SpeechRecognitionEngine_0 = new SpeechRecognitionEngine();
		this.int_2 = 255;
		this.random_0 = new Random();
		this.int_5 = 0;
		this.bool_6 = false;
		this.bool_7 = false;
		this.string_31 = Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + "\\AnyDesk.exe";
		this.bitmap_0 = new Bitmap(1, 1);
		this.graphics_0 = Graphics.FromImage(this.bitmap_0);
		this.bool_8 = true;
		this.bool_9 = false;
		this.bool_10 = false;
		this.bool_11 = false;
		this.color_0 = Color.FromArgb(1, 93, 163);
		this.int_15 = 5;
		this.int_16 = 25;
		this.bool_12 = false;
		this.int_17 = 105;
		this.bool_13 = true;
		this.int_18 = 0;
		this.int_19 = 0;
		this.bool_14 = true;
		this.int_20 = 0;
		this.int_21 = 0;
		this.int_22 = 0;
		this.int_23 = 0;
		this.int_24 = 0;
		this.GClass2_0 = new GClass2();
		this.bool_15 = false;
		this.InitializeComponent();
	}

	// Token: 0x060009A0 RID: 2464 RVA: 0x0002D458 File Offset: 0x0002B658
	[DebuggerNonUserCode]
	protected virtual void Dispose(bool disposing)
	{
		try
		{
			if (disposing && this.icontainer_0 != null)
			{
				this.icontainer_0.Dispose();
			}
		}
		finally
		{
			base.Dispose(disposing);
		}
	}

	// Token: 0x1700029B RID: 667
	// (get) Token: 0x060009A2 RID: 2466 RVA: 0x00038A14 File Offset: 0x00036C14
	// (set) Token: 0x060009A3 RID: 2467 RVA: 0x00038A1C File Offset: 0x00036C1C
	internal virtual Control13 NsGroupBox3
	{
		[CompilerGenerated]
		get
		{
			return this._NsGroupBox3;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_38);
			MouseEventHandler value3 = new MouseEventHandler(this.method_39);
			Control13 nsGroupBox = this._NsGroupBox3;
			if (nsGroupBox != null)
			{
				nsGroupBox.Click -= value2;
				nsGroupBox.MouseMove -= value3;
			}
			this._NsGroupBox3 = value;
			nsGroupBox = this._NsGroupBox3;
			if (nsGroupBox != null)
			{
				nsGroupBox.Click += value2;
				nsGroupBox.MouseMove += value3;
			}
		}
	}

	// Token: 0x1700029C RID: 668
	// (get) Token: 0x060009A4 RID: 2468 RVA: 0x00038A7C File Offset: 0x00036C7C
	// (set) Token: 0x060009A5 RID: 2469 RVA: 0x00038A84 File Offset: 0x00036C84
	internal virtual GControl7 FaderGroupBox8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700029D RID: 669
	// (get) Token: 0x060009A6 RID: 2470 RVA: 0x00038A90 File Offset: 0x00036C90
	// (set) Token: 0x060009A7 RID: 2471 RVA: 0x00038A98 File Offset: 0x00036C98
	internal virtual Class20 FlatLabel46 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700029E RID: 670
	// (get) Token: 0x060009A8 RID: 2472 RVA: 0x00038AA4 File Offset: 0x00036CA4
	// (set) Token: 0x060009A9 RID: 2473 RVA: 0x00038AAC File Offset: 0x00036CAC
	internal virtual PictureBox PictureBox15 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700029F RID: 671
	// (get) Token: 0x060009AA RID: 2474 RVA: 0x00038AB8 File Offset: 0x00036CB8
	// (set) Token: 0x060009AB RID: 2475 RVA: 0x00038AC0 File Offset: 0x00036CC0
	internal virtual Class20 FlatLabel47 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002A0 RID: 672
	// (get) Token: 0x060009AC RID: 2476 RVA: 0x00038ACC File Offset: 0x00036CCC
	// (set) Token: 0x060009AD RID: 2477 RVA: 0x00038AD4 File Offset: 0x00036CD4
	internal virtual Class20 FlatLabel36 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002A1 RID: 673
	// (get) Token: 0x060009AE RID: 2478 RVA: 0x00038AE0 File Offset: 0x00036CE0
	// (set) Token: 0x060009AF RID: 2479 RVA: 0x00038AE8 File Offset: 0x00036CE8
	internal virtual Class20 FlatLabel48 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002A2 RID: 674
	// (get) Token: 0x060009B0 RID: 2480 RVA: 0x00038AF4 File Offset: 0x00036CF4
	// (set) Token: 0x060009B1 RID: 2481 RVA: 0x00038AFC File Offset: 0x00036CFC
	internal virtual Control28 FlatButton9
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton9;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_67);
			Control28 flatButton = this._FlatButton9;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton9 = value;
			flatButton = this._FlatButton9;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002A3 RID: 675
	// (get) Token: 0x060009B2 RID: 2482 RVA: 0x00038B40 File Offset: 0x00036D40
	// (set) Token: 0x060009B3 RID: 2483 RVA: 0x00038B48 File Offset: 0x00036D48
	internal virtual Class20 FlatLabel49 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002A4 RID: 676
	// (get) Token: 0x060009B4 RID: 2484 RVA: 0x00038B54 File Offset: 0x00036D54
	// (set) Token: 0x060009B5 RID: 2485 RVA: 0x00038B5C File Offset: 0x00036D5C
	internal virtual Class20 FlatLabel34 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002A5 RID: 677
	// (get) Token: 0x060009B6 RID: 2486 RVA: 0x00038B68 File Offset: 0x00036D68
	// (set) Token: 0x060009B7 RID: 2487 RVA: 0x00038B70 File Offset: 0x00036D70
	internal virtual Class20 FlatLabel33 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002A6 RID: 678
	// (get) Token: 0x060009B8 RID: 2488 RVA: 0x00038B7C File Offset: 0x00036D7C
	// (set) Token: 0x060009B9 RID: 2489 RVA: 0x00038B84 File Offset: 0x00036D84
	internal virtual GControl7 FaderGroupBox7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002A7 RID: 679
	// (get) Token: 0x060009BA RID: 2490 RVA: 0x00038B90 File Offset: 0x00036D90
	// (set) Token: 0x060009BB RID: 2491 RVA: 0x00038B98 File Offset: 0x00036D98
	internal virtual Class20 FlatLabel54 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002A8 RID: 680
	// (get) Token: 0x060009BC RID: 2492 RVA: 0x00038BA4 File Offset: 0x00036DA4
	// (set) Token: 0x060009BD RID: 2493 RVA: 0x00038BAC File Offset: 0x00036DAC
	internal virtual Class20 FlatLabel55 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002A9 RID: 681
	// (get) Token: 0x060009BE RID: 2494 RVA: 0x00038BB8 File Offset: 0x00036DB8
	// (set) Token: 0x060009BF RID: 2495 RVA: 0x00038BC0 File Offset: 0x00036DC0
	internal virtual PictureBox PictureBox17 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002AA RID: 682
	// (get) Token: 0x060009C0 RID: 2496 RVA: 0x00038BCC File Offset: 0x00036DCC
	// (set) Token: 0x060009C1 RID: 2497 RVA: 0x00038BD4 File Offset: 0x00036DD4
	internal virtual Class20 FlatLabel44 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002AB RID: 683
	// (get) Token: 0x060009C2 RID: 2498 RVA: 0x00038BE0 File Offset: 0x00036DE0
	// (set) Token: 0x060009C3 RID: 2499 RVA: 0x00038BE8 File Offset: 0x00036DE8
	internal virtual Class20 FlatLabel56 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002AC RID: 684
	// (get) Token: 0x060009C4 RID: 2500 RVA: 0x00038BF4 File Offset: 0x00036DF4
	// (set) Token: 0x060009C5 RID: 2501 RVA: 0x00038BFC File Offset: 0x00036DFC
	internal virtual Class20 FlatLabel43 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002AD RID: 685
	// (get) Token: 0x060009C6 RID: 2502 RVA: 0x00038C08 File Offset: 0x00036E08
	// (set) Token: 0x060009C7 RID: 2503 RVA: 0x00038C10 File Offset: 0x00036E10
	internal virtual Class20 FlatLabel57 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002AE RID: 686
	// (get) Token: 0x060009C8 RID: 2504 RVA: 0x00038C1C File Offset: 0x00036E1C
	// (set) Token: 0x060009C9 RID: 2505 RVA: 0x00038C24 File Offset: 0x00036E24
	internal virtual Control28 FlatButton11
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton11;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_65);
			Control28 flatButton = this._FlatButton11;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton11 = value;
			flatButton = this._FlatButton11;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002AF RID: 687
	// (get) Token: 0x060009CA RID: 2506 RVA: 0x00038C68 File Offset: 0x00036E68
	// (set) Token: 0x060009CB RID: 2507 RVA: 0x00038C70 File Offset: 0x00036E70
	internal virtual Class20 FlatLabel42 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002B0 RID: 688
	// (get) Token: 0x060009CC RID: 2508 RVA: 0x00038C7C File Offset: 0x00036E7C
	// (set) Token: 0x060009CD RID: 2509 RVA: 0x00038C84 File Offset: 0x00036E84
	internal virtual GControl7 FaderGroupBox6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002B1 RID: 689
	// (get) Token: 0x060009CE RID: 2510 RVA: 0x00038C90 File Offset: 0x00036E90
	// (set) Token: 0x060009CF RID: 2511 RVA: 0x00038C98 File Offset: 0x00036E98
	internal virtual Class20 FlatLabel13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002B2 RID: 690
	// (get) Token: 0x060009D0 RID: 2512 RVA: 0x00038CA4 File Offset: 0x00036EA4
	// (set) Token: 0x060009D1 RID: 2513 RVA: 0x00038CAC File Offset: 0x00036EAC
	internal virtual PictureBox PictureBox9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002B3 RID: 691
	// (get) Token: 0x060009D2 RID: 2514 RVA: 0x00038CB8 File Offset: 0x00036EB8
	// (set) Token: 0x060009D3 RID: 2515 RVA: 0x00038CC0 File Offset: 0x00036EC0
	internal virtual Class20 FlatLabel14 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002B4 RID: 692
	// (get) Token: 0x060009D4 RID: 2516 RVA: 0x00038CCC File Offset: 0x00036ECC
	// (set) Token: 0x060009D5 RID: 2517 RVA: 0x00038CD4 File Offset: 0x00036ED4
	internal virtual Class20 FlatLabel16 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002B5 RID: 693
	// (get) Token: 0x060009D6 RID: 2518 RVA: 0x00038CE0 File Offset: 0x00036EE0
	// (set) Token: 0x060009D7 RID: 2519 RVA: 0x00038CE8 File Offset: 0x00036EE8
	internal virtual Class20 FlatLabel15 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002B6 RID: 694
	// (get) Token: 0x060009D8 RID: 2520 RVA: 0x00038CF4 File Offset: 0x00036EF4
	// (set) Token: 0x060009D9 RID: 2521 RVA: 0x00038CFC File Offset: 0x00036EFC
	internal virtual Control28 FlatButton3
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton3;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_53);
			Control28 flatButton = this._FlatButton3;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton3 = value;
			flatButton = this._FlatButton3;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002B7 RID: 695
	// (get) Token: 0x060009DA RID: 2522 RVA: 0x00038D40 File Offset: 0x00036F40
	// (set) Token: 0x060009DB RID: 2523 RVA: 0x00038D48 File Offset: 0x00036F48
	internal virtual Class20 FlatLabel22 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002B8 RID: 696
	// (get) Token: 0x060009DC RID: 2524 RVA: 0x00038D54 File Offset: 0x00036F54
	// (set) Token: 0x060009DD RID: 2525 RVA: 0x00038D5C File Offset: 0x00036F5C
	internal virtual Class20 FlatLabel20 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002B9 RID: 697
	// (get) Token: 0x060009DE RID: 2526 RVA: 0x00038D68 File Offset: 0x00036F68
	// (set) Token: 0x060009DF RID: 2527 RVA: 0x00038D70 File Offset: 0x00036F70
	internal virtual Class20 FlatLabel19 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002BA RID: 698
	// (get) Token: 0x060009E0 RID: 2528 RVA: 0x00038D7C File Offset: 0x00036F7C
	// (set) Token: 0x060009E1 RID: 2529 RVA: 0x00038D84 File Offset: 0x00036F84
	internal virtual GControl7 FaderGroupBox5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002BB RID: 699
	// (get) Token: 0x060009E2 RID: 2530 RVA: 0x00038D90 File Offset: 0x00036F90
	// (set) Token: 0x060009E3 RID: 2531 RVA: 0x00038D98 File Offset: 0x00036F98
	internal virtual Class20 FlatLabel9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002BC RID: 700
	// (get) Token: 0x060009E4 RID: 2532 RVA: 0x00038DA4 File Offset: 0x00036FA4
	// (set) Token: 0x060009E5 RID: 2533 RVA: 0x00038DAC File Offset: 0x00036FAC
	internal virtual PictureBox PictureBox10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002BD RID: 701
	// (get) Token: 0x060009E6 RID: 2534 RVA: 0x00038DB8 File Offset: 0x00036FB8
	// (set) Token: 0x060009E7 RID: 2535 RVA: 0x00038DC0 File Offset: 0x00036FC0
	internal virtual Class20 FlatLabel11 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002BE RID: 702
	// (get) Token: 0x060009E8 RID: 2536 RVA: 0x00038DCC File Offset: 0x00036FCC
	// (set) Token: 0x060009E9 RID: 2537 RVA: 0x00038DD4 File Offset: 0x00036FD4
	internal virtual Class20 FlatLabel18 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002BF RID: 703
	// (get) Token: 0x060009EA RID: 2538 RVA: 0x00038DE0 File Offset: 0x00036FE0
	// (set) Token: 0x060009EB RID: 2539 RVA: 0x00038DE8 File Offset: 0x00036FE8
	internal virtual Class20 FlatLabel35 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002C0 RID: 704
	// (get) Token: 0x060009EC RID: 2540 RVA: 0x00038DF4 File Offset: 0x00036FF4
	// (set) Token: 0x060009ED RID: 2541 RVA: 0x00038DFC File Offset: 0x00036FFC
	internal virtual Control28 FlatButton4
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton4;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_54);
			Control28 flatButton = this._FlatButton4;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton4 = value;
			flatButton = this._FlatButton4;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002C1 RID: 705
	// (get) Token: 0x060009EE RID: 2542 RVA: 0x00038E40 File Offset: 0x00037040
	// (set) Token: 0x060009EF RID: 2543 RVA: 0x00038E48 File Offset: 0x00037048
	internal virtual Class20 FlatLabel37 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002C2 RID: 706
	// (get) Token: 0x060009F0 RID: 2544 RVA: 0x00038E54 File Offset: 0x00037054
	// (set) Token: 0x060009F1 RID: 2545 RVA: 0x00038E5C File Offset: 0x0003705C
	internal virtual Class20 FlatLabel32 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002C3 RID: 707
	// (get) Token: 0x060009F2 RID: 2546 RVA: 0x00038E68 File Offset: 0x00037068
	// (set) Token: 0x060009F3 RID: 2547 RVA: 0x00038E70 File Offset: 0x00037070
	internal virtual Class20 FlatLabel31 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002C4 RID: 708
	// (get) Token: 0x060009F4 RID: 2548 RVA: 0x00038E7C File Offset: 0x0003707C
	// (set) Token: 0x060009F5 RID: 2549 RVA: 0x00038E84 File Offset: 0x00037084
	internal virtual GControl7 FaderGroupBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002C5 RID: 709
	// (get) Token: 0x060009F6 RID: 2550 RVA: 0x00038E90 File Offset: 0x00037090
	// (set) Token: 0x060009F7 RID: 2551 RVA: 0x00038E98 File Offset: 0x00037098
	internal virtual Class20 FlatLabel23 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002C6 RID: 710
	// (get) Token: 0x060009F8 RID: 2552 RVA: 0x00038EA4 File Offset: 0x000370A4
	// (set) Token: 0x060009F9 RID: 2553 RVA: 0x00038EAC File Offset: 0x000370AC
	internal virtual PictureBox PictureBox8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002C7 RID: 711
	// (get) Token: 0x060009FA RID: 2554 RVA: 0x00038EB8 File Offset: 0x000370B8
	// (set) Token: 0x060009FB RID: 2555 RVA: 0x00038EC0 File Offset: 0x000370C0
	internal virtual Class20 FlatLabel24 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002C8 RID: 712
	// (get) Token: 0x060009FC RID: 2556 RVA: 0x00038ECC File Offset: 0x000370CC
	// (set) Token: 0x060009FD RID: 2557 RVA: 0x00038ED4 File Offset: 0x000370D4
	internal virtual Class20 FlatLabel17 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002C9 RID: 713
	// (get) Token: 0x060009FE RID: 2558 RVA: 0x00038EE0 File Offset: 0x000370E0
	// (set) Token: 0x060009FF RID: 2559 RVA: 0x00038EE8 File Offset: 0x000370E8
	internal virtual Class20 FlatLabel41 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002CA RID: 714
	// (get) Token: 0x06000A00 RID: 2560 RVA: 0x00038EF4 File Offset: 0x000370F4
	// (set) Token: 0x06000A01 RID: 2561 RVA: 0x00038EFC File Offset: 0x000370FC
	internal virtual Class20 FlatLabel21 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002CB RID: 715
	// (get) Token: 0x06000A02 RID: 2562 RVA: 0x00038F08 File Offset: 0x00037108
	// (set) Token: 0x06000A03 RID: 2563 RVA: 0x00038F10 File Offset: 0x00037110
	internal virtual Class20 FlatLabel45 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002CC RID: 716
	// (get) Token: 0x06000A04 RID: 2564 RVA: 0x00038F1C File Offset: 0x0003711C
	// (set) Token: 0x06000A05 RID: 2565 RVA: 0x00038F24 File Offset: 0x00037124
	internal virtual Control28 FlatButton5
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton5;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_52);
			Control28 flatButton = this._FlatButton5;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton5 = value;
			flatButton = this._FlatButton5;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002CD RID: 717
	// (get) Token: 0x06000A06 RID: 2566 RVA: 0x00038F68 File Offset: 0x00037168
	// (set) Token: 0x06000A07 RID: 2567 RVA: 0x00038F70 File Offset: 0x00037170
	internal virtual Class20 FlatLabel30 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002CE RID: 718
	// (get) Token: 0x06000A08 RID: 2568 RVA: 0x00038F7C File Offset: 0x0003717C
	// (set) Token: 0x06000A09 RID: 2569 RVA: 0x00038F84 File Offset: 0x00037184
	internal virtual Control28 FlatButton27 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002CF RID: 719
	// (get) Token: 0x06000A0A RID: 2570 RVA: 0x00038F90 File Offset: 0x00037190
	// (set) Token: 0x06000A0B RID: 2571 RVA: 0x00038F98 File Offset: 0x00037198
	internal virtual Control28 FlatButton28 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002D0 RID: 720
	// (get) Token: 0x06000A0C RID: 2572 RVA: 0x00038FA4 File Offset: 0x000371A4
	// (set) Token: 0x06000A0D RID: 2573 RVA: 0x00038FAC File Offset: 0x000371AC
	internal virtual Control28 FlatButton29 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002D1 RID: 721
	// (get) Token: 0x06000A0E RID: 2574 RVA: 0x00038FB8 File Offset: 0x000371B8
	// (set) Token: 0x06000A0F RID: 2575 RVA: 0x00038FC0 File Offset: 0x000371C0
	internal virtual Control28 FlatButton26 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002D2 RID: 722
	// (get) Token: 0x06000A10 RID: 2576 RVA: 0x00038FCC File Offset: 0x000371CC
	// (set) Token: 0x06000A11 RID: 2577 RVA: 0x00038FD4 File Offset: 0x000371D4
	internal virtual Control28 FlatButton30 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002D3 RID: 723
	// (get) Token: 0x06000A12 RID: 2578 RVA: 0x00038FE0 File Offset: 0x000371E0
	// (set) Token: 0x06000A13 RID: 2579 RVA: 0x00038FE8 File Offset: 0x000371E8
	internal virtual System.Windows.Forms.Timer Timer_0
	{
		[CompilerGenerated]
		get
		{
			return this.timer_0;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_81);
			System.Windows.Forms.Timer timer = this.timer_0;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_0 = value;
			timer = this.timer_0;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x170002D4 RID: 724
	// (get) Token: 0x06000A14 RID: 2580 RVA: 0x0003902C File Offset: 0x0003722C
	// (set) Token: 0x06000A15 RID: 2581 RVA: 0x00039034 File Offset: 0x00037234
	internal virtual GControl7 FaderGroupBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002D5 RID: 725
	// (get) Token: 0x06000A16 RID: 2582 RVA: 0x00039040 File Offset: 0x00037240
	// (set) Token: 0x06000A17 RID: 2583 RVA: 0x00039048 File Offset: 0x00037248
	internal virtual System.Windows.Forms.Timer Timer_1
	{
		[CompilerGenerated]
		get
		{
			return this.timer_1;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_78);
			System.Windows.Forms.Timer timer = this.timer_1;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_1 = value;
			timer = this.timer_1;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x170002D6 RID: 726
	// (get) Token: 0x06000A18 RID: 2584 RVA: 0x0003908C File Offset: 0x0003728C
	// (set) Token: 0x06000A19 RID: 2585 RVA: 0x00039094 File Offset: 0x00037294
	internal virtual System.Windows.Forms.Timer Timer_2
	{
		[CompilerGenerated]
		get
		{
			return this.timer_2;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_73);
			System.Windows.Forms.Timer timer = this.timer_2;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_2 = value;
			timer = this.timer_2;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x170002D7 RID: 727
	// (get) Token: 0x06000A1A RID: 2586 RVA: 0x000390D8 File Offset: 0x000372D8
	// (set) Token: 0x06000A1B RID: 2587 RVA: 0x000390E0 File Offset: 0x000372E0
	internal virtual System.Windows.Forms.Timer Timer_3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002D8 RID: 728
	// (get) Token: 0x06000A1C RID: 2588 RVA: 0x000390EC File Offset: 0x000372EC
	// (set) Token: 0x06000A1D RID: 2589 RVA: 0x000390F4 File Offset: 0x000372F4
	internal virtual System.Windows.Forms.Timer Timer_4
	{
		[CompilerGenerated]
		get
		{
			return this.timer_4;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_62);
			System.Windows.Forms.Timer timer = this.timer_4;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_4 = value;
			timer = this.timer_4;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x170002D9 RID: 729
	// (get) Token: 0x06000A1E RID: 2590 RVA: 0x00039138 File Offset: 0x00037338
	// (set) Token: 0x06000A1F RID: 2591 RVA: 0x00039140 File Offset: 0x00037340
	internal virtual System.Windows.Forms.Timer Timer_5
	{
		[CompilerGenerated]
		get
		{
			return this.timer_5;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_127);
			System.Windows.Forms.Timer timer = this.timer_5;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_5 = value;
			timer = this.timer_5;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x170002DA RID: 730
	// (get) Token: 0x06000A20 RID: 2592 RVA: 0x00039184 File Offset: 0x00037384
	// (set) Token: 0x06000A21 RID: 2593 RVA: 0x0003918C File Offset: 0x0003738C
	internal virtual System.Windows.Forms.Timer Timer_6
	{
		[CompilerGenerated]
		get
		{
			return this.timer_6;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_48);
			System.Windows.Forms.Timer timer = this.timer_6;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_6 = value;
			timer = this.timer_6;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x170002DB RID: 731
	// (get) Token: 0x06000A22 RID: 2594 RVA: 0x000391D0 File Offset: 0x000373D0
	// (set) Token: 0x06000A23 RID: 2595 RVA: 0x000391D8 File Offset: 0x000373D8
	internal virtual System.Windows.Forms.Timer Timer_7
	{
		[CompilerGenerated]
		get
		{
			return this.timer_7;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_13);
			EventHandler value3 = new EventHandler(this.method_36);
			System.Windows.Forms.Timer timer = this.timer_7;
			if (timer != null)
			{
				timer.Disposed -= value2;
				timer.Tick -= value3;
			}
			this.timer_7 = value;
			timer = this.timer_7;
			if (timer != null)
			{
				timer.Disposed += value2;
				timer.Tick += value3;
			}
		}
	}

	// Token: 0x170002DC RID: 732
	// (get) Token: 0x06000A24 RID: 2596 RVA: 0x00039238 File Offset: 0x00037438
	// (set) Token: 0x06000A25 RID: 2597 RVA: 0x00039240 File Offset: 0x00037440
	internal virtual System.Windows.Forms.Timer Timer_8
	{
		[CompilerGenerated]
		get
		{
			return this.timer_8;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_8);
			System.Windows.Forms.Timer timer = this.timer_8;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_8 = value;
			timer = this.timer_8;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x170002DD RID: 733
	// (get) Token: 0x06000A26 RID: 2598 RVA: 0x00039284 File Offset: 0x00037484
	// (set) Token: 0x06000A27 RID: 2599 RVA: 0x0003928C File Offset: 0x0003748C
	internal virtual System.Windows.Forms.Timer Timer_9
	{
		[CompilerGenerated]
		get
		{
			return this.timer_9;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_49);
			System.Windows.Forms.Timer timer = this.timer_9;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_9 = value;
			timer = this.timer_9;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x170002DE RID: 734
	// (get) Token: 0x06000A28 RID: 2600 RVA: 0x000392D0 File Offset: 0x000374D0
	// (set) Token: 0x06000A29 RID: 2601 RVA: 0x000392D8 File Offset: 0x000374D8
	internal virtual Class20 FlatLabel12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002DF RID: 735
	// (get) Token: 0x06000A2A RID: 2602 RVA: 0x000392E4 File Offset: 0x000374E4
	// (set) Token: 0x06000A2B RID: 2603 RVA: 0x000392EC File Offset: 0x000374EC
	internal virtual PictureBox PictureBox14
	{
		[CompilerGenerated]
		get
		{
			return this._PictureBox14;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_68);
			PictureBox pictureBox = this._PictureBox14;
			if (pictureBox != null)
			{
				pictureBox.Click -= value2;
			}
			this._PictureBox14 = value;
			pictureBox = this._PictureBox14;
			if (pictureBox != null)
			{
				pictureBox.Click += value2;
			}
		}
	}

	// Token: 0x170002E0 RID: 736
	// (get) Token: 0x06000A2C RID: 2604 RVA: 0x00039330 File Offset: 0x00037530
	// (set) Token: 0x06000A2D RID: 2605 RVA: 0x00039338 File Offset: 0x00037538
	internal virtual GControl7 FaderGroupBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002E1 RID: 737
	// (get) Token: 0x06000A2E RID: 2606 RVA: 0x00039344 File Offset: 0x00037544
	// (set) Token: 0x06000A2F RID: 2607 RVA: 0x0003934C File Offset: 0x0003754C
	internal virtual Control28 FlatButton24
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton24;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_124);
			Control28 flatButton = this._FlatButton24;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton24 = value;
			flatButton = this._FlatButton24;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002E2 RID: 738
	// (get) Token: 0x06000A30 RID: 2608 RVA: 0x00039390 File Offset: 0x00037590
	// (set) Token: 0x06000A31 RID: 2609 RVA: 0x00039398 File Offset: 0x00037598
	internal virtual Control28 FlatButton25
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton25;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_116);
			Control28 flatButton = this._FlatButton25;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton25 = value;
			flatButton = this._FlatButton25;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002E3 RID: 739
	// (get) Token: 0x06000A32 RID: 2610 RVA: 0x000393DC File Offset: 0x000375DC
	// (set) Token: 0x06000A33 RID: 2611 RVA: 0x000393E4 File Offset: 0x000375E4
	internal virtual Control28 FlatButton22
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton22;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_123);
			Control28 flatButton = this._FlatButton22;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton22 = value;
			flatButton = this._FlatButton22;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002E4 RID: 740
	// (get) Token: 0x06000A34 RID: 2612 RVA: 0x00039428 File Offset: 0x00037628
	// (set) Token: 0x06000A35 RID: 2613 RVA: 0x00039430 File Offset: 0x00037630
	internal virtual Control28 FlatButton23
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton23;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_114);
			Control28 flatButton = this._FlatButton23;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton23 = value;
			flatButton = this._FlatButton23;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002E5 RID: 741
	// (get) Token: 0x06000A36 RID: 2614 RVA: 0x00039474 File Offset: 0x00037674
	// (set) Token: 0x06000A37 RID: 2615 RVA: 0x0003947C File Offset: 0x0003767C
	internal virtual Control28 FlatButton20
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton20;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_77);
			Control28 flatButton = this._FlatButton20;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton20 = value;
			flatButton = this._FlatButton20;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002E6 RID: 742
	// (get) Token: 0x06000A38 RID: 2616 RVA: 0x000394C0 File Offset: 0x000376C0
	// (set) Token: 0x06000A39 RID: 2617 RVA: 0x000394C8 File Offset: 0x000376C8
	internal virtual Control28 FlatButton21
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton21;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_115);
			Control28 flatButton = this._FlatButton21;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton21 = value;
			flatButton = this._FlatButton21;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002E7 RID: 743
	// (get) Token: 0x06000A3A RID: 2618 RVA: 0x0003950C File Offset: 0x0003770C
	// (set) Token: 0x06000A3B RID: 2619 RVA: 0x00039514 File Offset: 0x00037714
	internal virtual Control28 FlatButton18
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton18;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_76);
			Control28 flatButton = this._FlatButton18;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton18 = value;
			flatButton = this._FlatButton18;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002E8 RID: 744
	// (get) Token: 0x06000A3C RID: 2620 RVA: 0x00039558 File Offset: 0x00037758
	// (set) Token: 0x06000A3D RID: 2621 RVA: 0x00039560 File Offset: 0x00037760
	internal virtual Control28 FlatButton19
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton19;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_113);
			Control28 flatButton = this._FlatButton19;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton19 = value;
			flatButton = this._FlatButton19;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002E9 RID: 745
	// (get) Token: 0x06000A3E RID: 2622 RVA: 0x000395A4 File Offset: 0x000377A4
	// (set) Token: 0x06000A3F RID: 2623 RVA: 0x000395AC File Offset: 0x000377AC
	internal virtual Control28 FlatButton16
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton16;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_111);
			Control28 flatButton = this._FlatButton16;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton16 = value;
			flatButton = this._FlatButton16;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002EA RID: 746
	// (get) Token: 0x06000A40 RID: 2624 RVA: 0x000395F0 File Offset: 0x000377F0
	// (set) Token: 0x06000A41 RID: 2625 RVA: 0x000395F8 File Offset: 0x000377F8
	internal virtual Control28 FlatButton17
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton17;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_109);
			Control28 flatButton = this._FlatButton17;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton17 = value;
			flatButton = this._FlatButton17;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002EB RID: 747
	// (get) Token: 0x06000A42 RID: 2626 RVA: 0x0003963C File Offset: 0x0003783C
	// (set) Token: 0x06000A43 RID: 2627 RVA: 0x00039644 File Offset: 0x00037844
	internal virtual Control28 FlatButton14
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton14;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_106);
			Control28 flatButton = this._FlatButton14;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton14 = value;
			flatButton = this._FlatButton14;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002EC RID: 748
	// (get) Token: 0x06000A44 RID: 2628 RVA: 0x00039688 File Offset: 0x00037888
	// (set) Token: 0x06000A45 RID: 2629 RVA: 0x00039690 File Offset: 0x00037890
	internal virtual Control28 FlatButton15
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton15;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_104);
			Control28 flatButton = this._FlatButton15;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton15 = value;
			flatButton = this._FlatButton15;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002ED RID: 749
	// (get) Token: 0x06000A46 RID: 2630 RVA: 0x000396D4 File Offset: 0x000378D4
	// (set) Token: 0x06000A47 RID: 2631 RVA: 0x000396DC File Offset: 0x000378DC
	internal virtual Control28 FlatButton13
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton13;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_75);
			Control28 flatButton = this._FlatButton13;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton13 = value;
			flatButton = this._FlatButton13;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002EE RID: 750
	// (get) Token: 0x06000A48 RID: 2632 RVA: 0x00039720 File Offset: 0x00037920
	// (set) Token: 0x06000A49 RID: 2633 RVA: 0x00039728 File Offset: 0x00037928
	internal virtual Control28 FlatButton12
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton12;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_74);
			Control28 flatButton = this._FlatButton12;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton12 = value;
			flatButton = this._FlatButton12;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x170002EF RID: 751
	// (get) Token: 0x06000A4A RID: 2634 RVA: 0x0003976C File Offset: 0x0003796C
	// (set) Token: 0x06000A4B RID: 2635 RVA: 0x00039774 File Offset: 0x00037974
	internal virtual GControl7 FaderGroupBox12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002F0 RID: 752
	// (get) Token: 0x06000A4C RID: 2636 RVA: 0x00039780 File Offset: 0x00037980
	// (set) Token: 0x06000A4D RID: 2637 RVA: 0x00039788 File Offset: 0x00037988
	internal virtual Label Label11 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002F1 RID: 753
	// (get) Token: 0x06000A4E RID: 2638 RVA: 0x00039794 File Offset: 0x00037994
	// (set) Token: 0x06000A4F RID: 2639 RVA: 0x0003979C File Offset: 0x0003799C
	internal virtual GClass10 ThirteenComboBox5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002F2 RID: 754
	// (get) Token: 0x06000A50 RID: 2640 RVA: 0x000397A8 File Offset: 0x000379A8
	// (set) Token: 0x06000A51 RID: 2641 RVA: 0x000397B0 File Offset: 0x000379B0
	internal virtual Control31 FlatCheckBox15 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002F3 RID: 755
	// (get) Token: 0x06000A52 RID: 2642 RVA: 0x000397BC File Offset: 0x000379BC
	// (set) Token: 0x06000A53 RID: 2643 RVA: 0x000397C4 File Offset: 0x000379C4
	internal virtual Control31 FlatCheckBox16 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002F4 RID: 756
	// (get) Token: 0x06000A54 RID: 2644 RVA: 0x000397D0 File Offset: 0x000379D0
	// (set) Token: 0x06000A55 RID: 2645 RVA: 0x000397D8 File Offset: 0x000379D8
	internal virtual GClass10 ThirteenComboBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002F5 RID: 757
	// (get) Token: 0x06000A56 RID: 2646 RVA: 0x000397E4 File Offset: 0x000379E4
	// (set) Token: 0x06000A57 RID: 2647 RVA: 0x000397EC File Offset: 0x000379EC
	internal virtual Class20 FlatLabel27 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002F6 RID: 758
	// (get) Token: 0x06000A58 RID: 2648 RVA: 0x000397F8 File Offset: 0x000379F8
	// (set) Token: 0x06000A59 RID: 2649 RVA: 0x00039800 File Offset: 0x00037A00
	internal virtual Class20 FlatLabel28 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002F7 RID: 759
	// (get) Token: 0x06000A5A RID: 2650 RVA: 0x0003980C File Offset: 0x00037A0C
	// (set) Token: 0x06000A5B RID: 2651 RVA: 0x00039814 File Offset: 0x00037A14
	internal virtual GControl7 FaderGroupBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002F8 RID: 760
	// (get) Token: 0x06000A5C RID: 2652 RVA: 0x00039820 File Offset: 0x00037A20
	// (set) Token: 0x06000A5D RID: 2653 RVA: 0x00039828 File Offset: 0x00037A28
	internal virtual Label Label9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002F9 RID: 761
	// (get) Token: 0x06000A5E RID: 2654 RVA: 0x00039834 File Offset: 0x00037A34
	// (set) Token: 0x06000A5F RID: 2655 RVA: 0x0003983C File Offset: 0x00037A3C
	internal virtual Control39 FlatTrackBar4
	{
		[CompilerGenerated]
		get
		{
			return this._FlatTrackBar4;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control39.Delegate13 value2 = new Control39.Delegate13(this.method_5);
			Control39 flatTrackBar = this._FlatTrackBar4;
			if (flatTrackBar != null)
			{
				flatTrackBar.Event_0 -= value2;
			}
			this._FlatTrackBar4 = value;
			flatTrackBar = this._FlatTrackBar4;
			if (flatTrackBar != null)
			{
				flatTrackBar.Event_0 += value2;
			}
		}
	}

	// Token: 0x170002FA RID: 762
	// (get) Token: 0x06000A60 RID: 2656 RVA: 0x00039880 File Offset: 0x00037A80
	// (set) Token: 0x06000A61 RID: 2657 RVA: 0x00039888 File Offset: 0x00037A88
	internal virtual Control39 MinimumCPS
	{
		[CompilerGenerated]
		get
		{
			return this._MinimumCPS;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control39.Delegate13 value2 = new Control39.Delegate13(this.method_10);
			Control39 minimumCPS = this._MinimumCPS;
			if (minimumCPS != null)
			{
				minimumCPS.Event_0 -= value2;
			}
			this._MinimumCPS = value;
			minimumCPS = this._MinimumCPS;
			if (minimumCPS != null)
			{
				minimumCPS.Event_0 += value2;
			}
		}
	}

	// Token: 0x170002FB RID: 763
	// (get) Token: 0x06000A62 RID: 2658 RVA: 0x000398CC File Offset: 0x00037ACC
	// (set) Token: 0x06000A63 RID: 2659 RVA: 0x000398D4 File Offset: 0x00037AD4
	internal virtual Control13 NsGroupBox5
	{
		[CompilerGenerated]
		get
		{
			return this._NsGroupBox5;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_6);
			Control13 nsGroupBox = this._NsGroupBox5;
			if (nsGroupBox != null)
			{
				nsGroupBox.Click -= value2;
			}
			this._NsGroupBox5 = value;
			nsGroupBox = this._NsGroupBox5;
			if (nsGroupBox != null)
			{
				nsGroupBox.Click += value2;
			}
		}
	}

	// Token: 0x170002FC RID: 764
	// (get) Token: 0x06000A64 RID: 2660 RVA: 0x00039918 File Offset: 0x00037B18
	// (set) Token: 0x06000A65 RID: 2661 RVA: 0x00039920 File Offset: 0x00037B20
	internal virtual PictureBox PictureBox7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002FD RID: 765
	// (get) Token: 0x06000A66 RID: 2662 RVA: 0x0003992C File Offset: 0x00037B2C
	// (set) Token: 0x06000A67 RID: 2663 RVA: 0x00039934 File Offset: 0x00037B34
	internal virtual Control39 MaximumCPS
	{
		[CompilerGenerated]
		get
		{
			return this._MaximumCPS;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control39.Delegate13 value2 = new Control39.Delegate13(this.method_11);
			Control39 maximumCPS = this._MaximumCPS;
			if (maximumCPS != null)
			{
				maximumCPS.Event_0 -= value2;
			}
			this._MaximumCPS = value;
			maximumCPS = this._MaximumCPS;
			if (maximumCPS != null)
			{
				maximumCPS.Event_0 += value2;
			}
		}
	}

	// Token: 0x170002FE RID: 766
	// (get) Token: 0x06000A68 RID: 2664 RVA: 0x00039978 File Offset: 0x00037B78
	// (set) Token: 0x06000A69 RID: 2665 RVA: 0x00039980 File Offset: 0x00037B80
	internal virtual Label Label6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x170002FF RID: 767
	// (get) Token: 0x06000A6A RID: 2666 RVA: 0x0003998C File Offset: 0x00037B8C
	// (set) Token: 0x06000A6B RID: 2667 RVA: 0x00039994 File Offset: 0x00037B94
	internal virtual Label Label5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000300 RID: 768
	// (get) Token: 0x06000A6C RID: 2668 RVA: 0x000399A0 File Offset: 0x00037BA0
	// (set) Token: 0x06000A6D RID: 2669 RVA: 0x000399A8 File Offset: 0x00037BA8
	internal virtual Control31 FlatCheckBox6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000301 RID: 769
	// (get) Token: 0x06000A6E RID: 2670 RVA: 0x000399B4 File Offset: 0x00037BB4
	// (set) Token: 0x06000A6F RID: 2671 RVA: 0x000399BC File Offset: 0x00037BBC
	internal virtual Control31 FlatCheckBox7
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox7;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_44);
			Control31 flatCheckBox = this._FlatCheckBox7;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox7 = value;
			flatCheckBox = this._FlatCheckBox7;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000302 RID: 770
	// (get) Token: 0x06000A70 RID: 2672 RVA: 0x00039A00 File Offset: 0x00037C00
	// (set) Token: 0x06000A71 RID: 2673 RVA: 0x00039A08 File Offset: 0x00037C08
	internal virtual Control31 FlatCheckBox5
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox5;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_22);
			Control31 flatCheckBox = this._FlatCheckBox5;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox5 = value;
			flatCheckBox = this._FlatCheckBox5;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000303 RID: 771
	// (get) Token: 0x06000A72 RID: 2674 RVA: 0x00039A4C File Offset: 0x00037C4C
	// (set) Token: 0x06000A73 RID: 2675 RVA: 0x00039A54 File Offset: 0x00037C54
	internal virtual GClass10 ThirteenComboBox1
	{
		[CompilerGenerated]
		get
		{
			return this._ThirteenComboBox1;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_7);
			GClass10 thirteenComboBox = this._ThirteenComboBox1;
			if (thirteenComboBox != null)
			{
				thirteenComboBox.SelectedIndexChanged -= value2;
			}
			this._ThirteenComboBox1 = value;
			thirteenComboBox = this._ThirteenComboBox1;
			if (thirteenComboBox != null)
			{
				thirteenComboBox.SelectedIndexChanged += value2;
			}
		}
	}

	// Token: 0x17000304 RID: 772
	// (get) Token: 0x06000A74 RID: 2676 RVA: 0x00039A98 File Offset: 0x00037C98
	// (set) Token: 0x06000A75 RID: 2677 RVA: 0x00039AA0 File Offset: 0x00037CA0
	internal virtual Control31 FlatCheckBox8
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox8;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_47);
			Control31 flatCheckBox = this._FlatCheckBox8;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox8 = value;
			flatCheckBox = this._FlatCheckBox8;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000305 RID: 773
	// (get) Token: 0x06000A76 RID: 2678 RVA: 0x00039AE4 File Offset: 0x00037CE4
	// (set) Token: 0x06000A77 RID: 2679 RVA: 0x00039AEC File Offset: 0x00037CEC
	internal virtual Control13 NsGroupBox1
	{
		[CompilerGenerated]
		get
		{
			return this._NsGroupBox1;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			MouseEventHandler value2 = new MouseEventHandler(this.method_37);
			Control13 nsGroupBox = this._NsGroupBox1;
			if (nsGroupBox != null)
			{
				nsGroupBox.MouseMove -= value2;
			}
			this._NsGroupBox1 = value;
			nsGroupBox = this._NsGroupBox1;
			if (nsGroupBox != null)
			{
				nsGroupBox.MouseMove += value2;
			}
		}
	}

	// Token: 0x17000306 RID: 774
	// (get) Token: 0x06000A78 RID: 2680 RVA: 0x00039B30 File Offset: 0x00037D30
	// (set) Token: 0x06000A79 RID: 2681 RVA: 0x00039B38 File Offset: 0x00037D38
	internal virtual Control31 FlatCheckBox9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000307 RID: 775
	// (get) Token: 0x06000A7A RID: 2682 RVA: 0x00039B44 File Offset: 0x00037D44
	// (set) Token: 0x06000A7B RID: 2683 RVA: 0x00039B4C File Offset: 0x00037D4C
	internal virtual GClass10 ThirteenComboBox6
	{
		[CompilerGenerated]
		get
		{
			return this._ThirteenComboBox6;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_70);
			GClass10 thirteenComboBox = this._ThirteenComboBox6;
			if (thirteenComboBox != null)
			{
				thirteenComboBox.SelectedIndexChanged -= value2;
			}
			this._ThirteenComboBox6 = value;
			thirteenComboBox = this._ThirteenComboBox6;
			if (thirteenComboBox != null)
			{
				thirteenComboBox.SelectedIndexChanged += value2;
			}
		}
	}

	// Token: 0x17000308 RID: 776
	// (get) Token: 0x06000A7C RID: 2684 RVA: 0x00039B90 File Offset: 0x00037D90
	// (set) Token: 0x06000A7D RID: 2685 RVA: 0x00039B98 File Offset: 0x00037D98
	internal virtual Label Label1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000309 RID: 777
	// (get) Token: 0x06000A7E RID: 2686 RVA: 0x00039BA4 File Offset: 0x00037DA4
	// (set) Token: 0x06000A7F RID: 2687 RVA: 0x00039BAC File Offset: 0x00037DAC
	internal virtual Control27 FlatGroupBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700030A RID: 778
	// (get) Token: 0x06000A80 RID: 2688 RVA: 0x00039BB8 File Offset: 0x00037DB8
	// (set) Token: 0x06000A81 RID: 2689 RVA: 0x00039BC0 File Offset: 0x00037DC0
	internal virtual GControl7 FaderGroupBox11 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700030B RID: 779
	// (get) Token: 0x06000A82 RID: 2690 RVA: 0x00039BCC File Offset: 0x00037DCC
	// (set) Token: 0x06000A83 RID: 2691 RVA: 0x00039BD4 File Offset: 0x00037DD4
	internal virtual Class20 FlatLabel29 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700030C RID: 780
	// (get) Token: 0x06000A84 RID: 2692 RVA: 0x00039BE0 File Offset: 0x00037DE0
	// (set) Token: 0x06000A85 RID: 2693 RVA: 0x00039BE8 File Offset: 0x00037DE8
	internal virtual Control28 FlatButton6
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton6;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_61);
			Control28 flatButton = this._FlatButton6;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton6 = value;
			flatButton = this._FlatButton6;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x1700030D RID: 781
	// (get) Token: 0x06000A86 RID: 2694 RVA: 0x00039C2C File Offset: 0x00037E2C
	// (set) Token: 0x06000A87 RID: 2695 RVA: 0x00039C34 File Offset: 0x00037E34
	internal virtual Control28 FlatButton7
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton7;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_60);
			Control28 flatButton = this._FlatButton7;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton7 = value;
			flatButton = this._FlatButton7;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x1700030E RID: 782
	// (get) Token: 0x06000A88 RID: 2696 RVA: 0x00039C78 File Offset: 0x00037E78
	// (set) Token: 0x06000A89 RID: 2697 RVA: 0x00039C80 File Offset: 0x00037E80
	internal virtual Control28 FlatButton8
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton8;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_59);
			Control28 flatButton = this._FlatButton8;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton8 = value;
			flatButton = this._FlatButton8;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x1700030F RID: 783
	// (get) Token: 0x06000A8A RID: 2698 RVA: 0x00039CC4 File Offset: 0x00037EC4
	// (set) Token: 0x06000A8B RID: 2699 RVA: 0x00039CCC File Offset: 0x00037ECC
	internal virtual Control13 NsGroupBox2
	{
		[CompilerGenerated]
		get
		{
			return this._NsGroupBox2;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_17);
			MouseEventHandler value3 = new MouseEventHandler(this.method_40);
			Control13 nsGroupBox = this._NsGroupBox2;
			if (nsGroupBox != null)
			{
				nsGroupBox.Click -= value2;
				nsGroupBox.MouseMove -= value3;
			}
			this._NsGroupBox2 = value;
			nsGroupBox = this._NsGroupBox2;
			if (nsGroupBox != null)
			{
				nsGroupBox.Click += value2;
				nsGroupBox.MouseMove += value3;
			}
		}
	}

	// Token: 0x17000310 RID: 784
	// (get) Token: 0x06000A8C RID: 2700 RVA: 0x00039D2C File Offset: 0x00037F2C
	// (set) Token: 0x06000A8D RID: 2701 RVA: 0x00039D34 File Offset: 0x00037F34
	internal virtual GControl7 FaderGroupBox10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000311 RID: 785
	// (get) Token: 0x06000A8E RID: 2702 RVA: 0x00039D40 File Offset: 0x00037F40
	// (set) Token: 0x06000A8F RID: 2703 RVA: 0x00039D48 File Offset: 0x00037F48
	internal virtual PictureBox PictureBox12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000312 RID: 786
	// (get) Token: 0x06000A90 RID: 2704 RVA: 0x00039D54 File Offset: 0x00037F54
	// (set) Token: 0x06000A91 RID: 2705 RVA: 0x00039D5C File Offset: 0x00037F5C
	internal virtual Control39 FlatTrackBar1
	{
		[CompilerGenerated]
		get
		{
			return this._FlatTrackBar1;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control39.Delegate13 value2 = new Control39.Delegate13(this.method_14);
			Control39 flatTrackBar = this._FlatTrackBar1;
			if (flatTrackBar != null)
			{
				flatTrackBar.Event_0 -= value2;
			}
			this._FlatTrackBar1 = value;
			flatTrackBar = this._FlatTrackBar1;
			if (flatTrackBar != null)
			{
				flatTrackBar.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000313 RID: 787
	// (get) Token: 0x06000A92 RID: 2706 RVA: 0x00039DA0 File Offset: 0x00037FA0
	// (set) Token: 0x06000A93 RID: 2707 RVA: 0x00039DA8 File Offset: 0x00037FA8
	internal virtual Control28 FlatButton2
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton2;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_15);
			Control28 flatButton = this._FlatButton2;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton2 = value;
			flatButton = this._FlatButton2;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x17000314 RID: 788
	// (get) Token: 0x06000A94 RID: 2708 RVA: 0x00039DEC File Offset: 0x00037FEC
	// (set) Token: 0x06000A95 RID: 2709 RVA: 0x00039DF4 File Offset: 0x00037FF4
	internal virtual Class20 FlatLabel2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000315 RID: 789
	// (get) Token: 0x06000A96 RID: 2710 RVA: 0x00039E00 File Offset: 0x00038000
	// (set) Token: 0x06000A97 RID: 2711 RVA: 0x00039E08 File Offset: 0x00038008
	internal virtual PictureBox PictureBox11
	{
		[CompilerGenerated]
		get
		{
			return this._PictureBox11;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			MouseEventHandler value2 = new MouseEventHandler(this.method_19);
			EventHandler value3 = new EventHandler(this.method_43);
			PictureBox pictureBox = this._PictureBox11;
			if (pictureBox != null)
			{
				pictureBox.MouseDown -= value2;
				pictureBox.Click -= value3;
			}
			this._PictureBox11 = value;
			pictureBox = this._PictureBox11;
			if (pictureBox != null)
			{
				pictureBox.MouseDown += value2;
				pictureBox.Click += value3;
			}
		}
	}

	// Token: 0x17000316 RID: 790
	// (get) Token: 0x06000A98 RID: 2712 RVA: 0x00039E68 File Offset: 0x00038068
	// (set) Token: 0x06000A99 RID: 2713 RVA: 0x00039E70 File Offset: 0x00038070
	internal virtual PictureBox PictureBox13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000317 RID: 791
	// (get) Token: 0x06000A9A RID: 2714 RVA: 0x00039E7C File Offset: 0x0003807C
	// (set) Token: 0x06000A9B RID: 2715 RVA: 0x00039E84 File Offset: 0x00038084
	internal virtual Class20 FlatLabel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000318 RID: 792
	// (get) Token: 0x06000A9C RID: 2716 RVA: 0x00039E90 File Offset: 0x00038090
	// (set) Token: 0x06000A9D RID: 2717 RVA: 0x00039E98 File Offset: 0x00038098
	internal virtual GControl7 FaderGroupBox9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000319 RID: 793
	// (get) Token: 0x06000A9E RID: 2718 RVA: 0x00039EA4 File Offset: 0x000380A4
	// (set) Token: 0x06000A9F RID: 2719 RVA: 0x00039EAC File Offset: 0x000380AC
	internal virtual Control31 FlatCheckBox12
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox12;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_63);
			Control31 flatCheckBox = this._FlatCheckBox12;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox12 = value;
			flatCheckBox = this._FlatCheckBox12;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x1700031A RID: 794
	// (get) Token: 0x06000AA0 RID: 2720 RVA: 0x00039EF0 File Offset: 0x000380F0
	// (set) Token: 0x06000AA1 RID: 2721 RVA: 0x00039EF8 File Offset: 0x000380F8
	internal virtual Control31 FlatCheckBox10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700031B RID: 795
	// (get) Token: 0x06000AA2 RID: 2722 RVA: 0x00039F04 File Offset: 0x00038104
	// (set) Token: 0x06000AA3 RID: 2723 RVA: 0x00039F0C File Offset: 0x0003810C
	internal virtual Class20 FlatLabel10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700031C RID: 796
	// (get) Token: 0x06000AA4 RID: 2724 RVA: 0x00039F18 File Offset: 0x00038118
	// (set) Token: 0x06000AA5 RID: 2725 RVA: 0x00039F20 File Offset: 0x00038120
	internal virtual Control31 FlatCheckBox11
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox11;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_71);
			Control31 flatCheckBox = this._FlatCheckBox11;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox11 = value;
			flatCheckBox = this._FlatCheckBox11;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x1700031D RID: 797
	// (get) Token: 0x06000AA6 RID: 2726 RVA: 0x00039F64 File Offset: 0x00038164
	// (set) Token: 0x06000AA7 RID: 2727 RVA: 0x00039F6C File Offset: 0x0003816C
	internal virtual Control28 FlatButton1
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton1;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_51);
			Control28 flatButton = this._FlatButton1;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton1 = value;
			flatButton = this._FlatButton1;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x1700031E RID: 798
	// (get) Token: 0x06000AA8 RID: 2728 RVA: 0x00039FB0 File Offset: 0x000381B0
	// (set) Token: 0x06000AA9 RID: 2729 RVA: 0x00039FB8 File Offset: 0x000381B8
	internal virtual Control13 NsGroupBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700031F RID: 799
	// (get) Token: 0x06000AAA RID: 2730 RVA: 0x00039FC4 File Offset: 0x000381C4
	// (set) Token: 0x06000AAB RID: 2731 RVA: 0x00039FCC File Offset: 0x000381CC
	internal virtual Control13 NsGroupBox7
	{
		[CompilerGenerated]
		get
		{
			return this._NsGroupBox7;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_41);
			MouseEventHandler value3 = new MouseEventHandler(this.method_42);
			Control13 nsGroupBox = this._NsGroupBox7;
			if (nsGroupBox != null)
			{
				nsGroupBox.Click -= value2;
				nsGroupBox.MouseMove -= value3;
			}
			this._NsGroupBox7 = value;
			nsGroupBox = this._NsGroupBox7;
			if (nsGroupBox != null)
			{
				nsGroupBox.Click += value2;
				nsGroupBox.MouseMove += value3;
			}
		}
	}

	// Token: 0x17000320 RID: 800
	// (get) Token: 0x06000AAC RID: 2732 RVA: 0x0003A02C File Offset: 0x0003822C
	// (set) Token: 0x06000AAD RID: 2733 RVA: 0x0003A034 File Offset: 0x00038234
	internal virtual Control31 FlatCheckBox20
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox20;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_79);
			Control31 flatCheckBox = this._FlatCheckBox20;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox20 = value;
			flatCheckBox = this._FlatCheckBox20;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000321 RID: 801
	// (get) Token: 0x06000AAE RID: 2734 RVA: 0x0003A078 File Offset: 0x00038278
	// (set) Token: 0x06000AAF RID: 2735 RVA: 0x0003A080 File Offset: 0x00038280
	internal virtual Control31 FlatCheckBox2
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox2;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_80);
			Control31 flatCheckBox = this._FlatCheckBox2;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox2 = value;
			flatCheckBox = this._FlatCheckBox2;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000322 RID: 802
	// (get) Token: 0x06000AB0 RID: 2736 RVA: 0x0003A0C4 File Offset: 0x000382C4
	// (set) Token: 0x06000AB1 RID: 2737 RVA: 0x0003A0CC File Offset: 0x000382CC
	internal virtual Control31 FlatCheckBox19
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox19;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_69);
			Control31 flatCheckBox = this._FlatCheckBox19;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox19 = value;
			flatCheckBox = this._FlatCheckBox19;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000323 RID: 803
	// (get) Token: 0x06000AB2 RID: 2738 RVA: 0x0003A110 File Offset: 0x00038310
	// (set) Token: 0x06000AB3 RID: 2739 RVA: 0x0003A118 File Offset: 0x00038318
	internal virtual Control31 FlatCheckBox17
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox17;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_58);
			Control31 flatCheckBox = this._FlatCheckBox17;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox17 = value;
			flatCheckBox = this._FlatCheckBox17;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000324 RID: 804
	// (get) Token: 0x06000AB4 RID: 2740 RVA: 0x0003A15C File Offset: 0x0003835C
	// (set) Token: 0x06000AB5 RID: 2741 RVA: 0x0003A164 File Offset: 0x00038364
	internal virtual Control31 FlatCheckBox18
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox18;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_57);
			Control31 flatCheckBox = this._FlatCheckBox18;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox18 = value;
			flatCheckBox = this._FlatCheckBox18;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000325 RID: 805
	// (get) Token: 0x06000AB6 RID: 2742 RVA: 0x0003A1A8 File Offset: 0x000383A8
	// (set) Token: 0x06000AB7 RID: 2743 RVA: 0x0003A1B0 File Offset: 0x000383B0
	internal virtual Control31 FlatCheckBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000326 RID: 806
	// (get) Token: 0x06000AB8 RID: 2744 RVA: 0x0003A1BC File Offset: 0x000383BC
	// (set) Token: 0x06000AB9 RID: 2745 RVA: 0x0003A1C4 File Offset: 0x000383C4
	internal virtual GControl7 FaderGroupBox13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000327 RID: 807
	// (get) Token: 0x06000ABA RID: 2746 RVA: 0x0003A1D0 File Offset: 0x000383D0
	// (set) Token: 0x06000ABB RID: 2747 RVA: 0x0003A1D8 File Offset: 0x000383D8
	internal virtual Control31 FlatCheckBox21
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox21;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_83);
			Control31 flatCheckBox = this._FlatCheckBox21;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox21 = value;
			flatCheckBox = this._FlatCheckBox21;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000328 RID: 808
	// (get) Token: 0x06000ABC RID: 2748 RVA: 0x0003A21C File Offset: 0x0003841C
	// (set) Token: 0x06000ABD RID: 2749 RVA: 0x0003A224 File Offset: 0x00038424
	internal virtual ToolTip ToolTip_0
	{
		[CompilerGenerated]
		get
		{
			return this.toolTip_0;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			PopupEventHandler value2 = new PopupEventHandler(this.method_82);
			ToolTip toolTip = this.toolTip_0;
			if (toolTip != null)
			{
				toolTip.Popup -= value2;
			}
			this.toolTip_0 = value;
			toolTip = this.toolTip_0;
			if (toolTip != null)
			{
				toolTip.Popup += value2;
			}
		}
	}

	// Token: 0x17000329 RID: 809
	// (get) Token: 0x06000ABE RID: 2750 RVA: 0x0003A268 File Offset: 0x00038468
	// (set) Token: 0x06000ABF RID: 2751 RVA: 0x0003A270 File Offset: 0x00038470
	internal virtual PictureBox PictureBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700032A RID: 810
	// (get) Token: 0x06000AC0 RID: 2752 RVA: 0x0003A27C File Offset: 0x0003847C
	// (set) Token: 0x06000AC1 RID: 2753 RVA: 0x0003A284 File Offset: 0x00038484
	internal virtual Control28 FlatButton39
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton39;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_84);
			Control28 flatButton = this._FlatButton39;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton39 = value;
			flatButton = this._FlatButton39;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x1700032B RID: 811
	// (get) Token: 0x06000AC2 RID: 2754 RVA: 0x0003A2C8 File Offset: 0x000384C8
	// (set) Token: 0x06000AC3 RID: 2755 RVA: 0x0003A2D0 File Offset: 0x000384D0
	internal virtual Control28 FlatButton40
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton40;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_86);
			Control28 flatButton = this._FlatButton40;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton40 = value;
			flatButton = this._FlatButton40;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x1700032C RID: 812
	// (get) Token: 0x06000AC4 RID: 2756 RVA: 0x0003A314 File Offset: 0x00038514
	// (set) Token: 0x06000AC5 RID: 2757 RVA: 0x0003A31C File Offset: 0x0003851C
	internal virtual Control28 FlatButton37
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton37;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_85);
			Control28 flatButton = this._FlatButton37;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton37 = value;
			flatButton = this._FlatButton37;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x1700032D RID: 813
	// (get) Token: 0x06000AC6 RID: 2758 RVA: 0x0003A360 File Offset: 0x00038560
	// (set) Token: 0x06000AC7 RID: 2759 RVA: 0x0003A368 File Offset: 0x00038568
	internal virtual Control28 FlatButton38
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton38;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_112);
			Control28 flatButton = this._FlatButton38;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton38 = value;
			flatButton = this._FlatButton38;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x1700032E RID: 814
	// (get) Token: 0x06000AC8 RID: 2760 RVA: 0x0003A3AC File Offset: 0x000385AC
	// (set) Token: 0x06000AC9 RID: 2761 RVA: 0x0003A3B4 File Offset: 0x000385B4
	internal virtual GControl7 FaderGroupBox14 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700032F RID: 815
	// (get) Token: 0x06000ACA RID: 2762 RVA: 0x0003A3C0 File Offset: 0x000385C0
	// (set) Token: 0x06000ACB RID: 2763 RVA: 0x0003A3C8 File Offset: 0x000385C8
	internal virtual Control39 FlatTrackBar2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000330 RID: 816
	// (get) Token: 0x06000ACC RID: 2764 RVA: 0x0003A3D4 File Offset: 0x000385D4
	// (set) Token: 0x06000ACD RID: 2765 RVA: 0x0003A3DC File Offset: 0x000385DC
	internal virtual Label Label7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000331 RID: 817
	// (get) Token: 0x06000ACE RID: 2766 RVA: 0x0003A3E8 File Offset: 0x000385E8
	// (set) Token: 0x06000ACF RID: 2767 RVA: 0x0003A3F0 File Offset: 0x000385F0
	internal virtual Label Label4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000332 RID: 818
	// (get) Token: 0x06000AD0 RID: 2768 RVA: 0x0003A3FC File Offset: 0x000385FC
	// (set) Token: 0x06000AD1 RID: 2769 RVA: 0x0003A404 File Offset: 0x00038604
	internal virtual Control31 FlatCheckBox24 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000333 RID: 819
	// (get) Token: 0x06000AD2 RID: 2770 RVA: 0x0003A410 File Offset: 0x00038610
	// (set) Token: 0x06000AD3 RID: 2771 RVA: 0x0003A418 File Offset: 0x00038618
	internal virtual Label Label3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000334 RID: 820
	// (get) Token: 0x06000AD4 RID: 2772 RVA: 0x0003A424 File Offset: 0x00038624
	// (set) Token: 0x06000AD5 RID: 2773 RVA: 0x0003A42C File Offset: 0x0003862C
	internal virtual Label Label2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000335 RID: 821
	// (get) Token: 0x06000AD6 RID: 2774 RVA: 0x0003A438 File Offset: 0x00038638
	// (set) Token: 0x06000AD7 RID: 2775 RVA: 0x0003A440 File Offset: 0x00038640
	internal virtual GControl7 FaderGroupBox15 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000336 RID: 822
	// (get) Token: 0x06000AD8 RID: 2776 RVA: 0x0003A44C File Offset: 0x0003864C
	// (set) Token: 0x06000AD9 RID: 2777 RVA: 0x0003A454 File Offset: 0x00038654
	internal virtual Label Label12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000337 RID: 823
	// (get) Token: 0x06000ADA RID: 2778 RVA: 0x0003A460 File Offset: 0x00038660
	// (set) Token: 0x06000ADB RID: 2779 RVA: 0x0003A468 File Offset: 0x00038668
	internal virtual Class20 FlatLabel5
	{
		[CompilerGenerated]
		get
		{
			return this._FlatLabel5;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_87);
			Class20 flatLabel = this._FlatLabel5;
			if (flatLabel != null)
			{
				flatLabel.Click -= value2;
			}
			this._FlatLabel5 = value;
			flatLabel = this._FlatLabel5;
			if (flatLabel != null)
			{
				flatLabel.Click += value2;
			}
		}
	}

	// Token: 0x17000338 RID: 824
	// (get) Token: 0x06000ADC RID: 2780 RVA: 0x0003A4AC File Offset: 0x000386AC
	// (set) Token: 0x06000ADD RID: 2781 RVA: 0x0003A4B4 File Offset: 0x000386B4
	internal virtual Class20 FlatLabel8
	{
		[CompilerGenerated]
		get
		{
			return this._FlatLabel8;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_96);
			Class20 flatLabel = this._FlatLabel8;
			if (flatLabel != null)
			{
				flatLabel.Click -= value2;
			}
			this._FlatLabel8 = value;
			flatLabel = this._FlatLabel8;
			if (flatLabel != null)
			{
				flatLabel.Click += value2;
			}
		}
	}

	// Token: 0x17000339 RID: 825
	// (get) Token: 0x06000ADE RID: 2782 RVA: 0x0003A4F8 File Offset: 0x000386F8
	// (set) Token: 0x06000ADF RID: 2783 RVA: 0x0003A500 File Offset: 0x00038700
	internal virtual Class20 FlatLabel38
	{
		[CompilerGenerated]
		get
		{
			return this._FlatLabel38;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_98);
			Class20 flatLabel = this._FlatLabel38;
			if (flatLabel != null)
			{
				flatLabel.Click -= value2;
			}
			this._FlatLabel38 = value;
			flatLabel = this._FlatLabel38;
			if (flatLabel != null)
			{
				flatLabel.Click += value2;
			}
		}
	}

	// Token: 0x1700033A RID: 826
	// (get) Token: 0x06000AE0 RID: 2784 RVA: 0x0003A544 File Offset: 0x00038744
	// (set) Token: 0x06000AE1 RID: 2785 RVA: 0x0003A54C File Offset: 0x0003874C
	internal virtual Class20 FlatLabel40
	{
		[CompilerGenerated]
		get
		{
			return this._FlatLabel40;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_100);
			Class20 flatLabel = this._FlatLabel40;
			if (flatLabel != null)
			{
				flatLabel.Click -= value2;
			}
			this._FlatLabel40 = value;
			flatLabel = this._FlatLabel40;
			if (flatLabel != null)
			{
				flatLabel.Click += value2;
			}
		}
	}

	// Token: 0x1700033B RID: 827
	// (get) Token: 0x06000AE2 RID: 2786 RVA: 0x0003A590 File Offset: 0x00038790
	// (set) Token: 0x06000AE3 RID: 2787 RVA: 0x0003A598 File Offset: 0x00038798
	internal virtual Class20 FlatLabel6
	{
		[CompilerGenerated]
		get
		{
			return this._FlatLabel6;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_94);
			Class20 flatLabel = this._FlatLabel6;
			if (flatLabel != null)
			{
				flatLabel.Click -= value2;
			}
			this._FlatLabel6 = value;
			flatLabel = this._FlatLabel6;
			if (flatLabel != null)
			{
				flatLabel.Click += value2;
			}
		}
	}

	// Token: 0x1700033C RID: 828
	// (get) Token: 0x06000AE4 RID: 2788 RVA: 0x0003A5DC File Offset: 0x000387DC
	// (set) Token: 0x06000AE5 RID: 2789 RVA: 0x0003A5E4 File Offset: 0x000387E4
	internal virtual PictureBox PictureBox2
	{
		[CompilerGenerated]
		get
		{
			return this._PictureBox2;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_88);
			PictureBox pictureBox = this._PictureBox2;
			if (pictureBox != null)
			{
				pictureBox.Click -= value2;
			}
			this._PictureBox2 = value;
			pictureBox = this._PictureBox2;
			if (pictureBox != null)
			{
				pictureBox.Click += value2;
			}
		}
	}

	// Token: 0x1700033D RID: 829
	// (get) Token: 0x06000AE6 RID: 2790 RVA: 0x0003A628 File Offset: 0x00038828
	// (set) Token: 0x06000AE7 RID: 2791 RVA: 0x0003A630 File Offset: 0x00038830
	internal virtual PictureBox PictureBox3
	{
		[CompilerGenerated]
		get
		{
			return this._PictureBox3;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_89);
			PictureBox pictureBox = this._PictureBox3;
			if (pictureBox != null)
			{
				pictureBox.Click -= value2;
			}
			this._PictureBox3 = value;
			pictureBox = this._PictureBox3;
			if (pictureBox != null)
			{
				pictureBox.Click += value2;
			}
		}
	}

	// Token: 0x1700033E RID: 830
	// (get) Token: 0x06000AE8 RID: 2792 RVA: 0x0003A674 File Offset: 0x00038874
	// (set) Token: 0x06000AE9 RID: 2793 RVA: 0x0003A67C File Offset: 0x0003887C
	internal virtual PictureBox PictureBox4
	{
		[CompilerGenerated]
		get
		{
			return this._PictureBox4;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_90);
			PictureBox pictureBox = this._PictureBox4;
			if (pictureBox != null)
			{
				pictureBox.Click -= value2;
			}
			this._PictureBox4 = value;
			pictureBox = this._PictureBox4;
			if (pictureBox != null)
			{
				pictureBox.Click += value2;
			}
		}
	}

	// Token: 0x1700033F RID: 831
	// (get) Token: 0x06000AEA RID: 2794 RVA: 0x0003A6C0 File Offset: 0x000388C0
	// (set) Token: 0x06000AEB RID: 2795 RVA: 0x0003A6C8 File Offset: 0x000388C8
	internal virtual PictureBox PictureBox5
	{
		[CompilerGenerated]
		get
		{
			return this._PictureBox5;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_91);
			PictureBox pictureBox = this._PictureBox5;
			if (pictureBox != null)
			{
				pictureBox.Click -= value2;
			}
			this._PictureBox5 = value;
			pictureBox = this._PictureBox5;
			if (pictureBox != null)
			{
				pictureBox.Click += value2;
			}
		}
	}

	// Token: 0x17000340 RID: 832
	// (get) Token: 0x06000AEC RID: 2796 RVA: 0x0003A70C File Offset: 0x0003890C
	// (set) Token: 0x06000AED RID: 2797 RVA: 0x0003A714 File Offset: 0x00038914
	internal virtual PictureBox PictureBox6
	{
		[CompilerGenerated]
		get
		{
			return this._PictureBox6;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_92);
			PictureBox pictureBox = this._PictureBox6;
			if (pictureBox != null)
			{
				pictureBox.Click -= value2;
			}
			this._PictureBox6 = value;
			pictureBox = this._PictureBox6;
			if (pictureBox != null)
			{
				pictureBox.Click += value2;
			}
		}
	}

	// Token: 0x17000341 RID: 833
	// (get) Token: 0x06000AEE RID: 2798 RVA: 0x0003A758 File Offset: 0x00038958
	// (set) Token: 0x06000AEF RID: 2799 RVA: 0x0003A760 File Offset: 0x00038960
	internal virtual Control31 FlatCheckBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000342 RID: 834
	// (get) Token: 0x06000AF0 RID: 2800 RVA: 0x0003A76C File Offset: 0x0003896C
	// (set) Token: 0x06000AF1 RID: 2801 RVA: 0x0003A774 File Offset: 0x00038974
	internal virtual GControl7 FaderGroupBox16 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000343 RID: 835
	// (get) Token: 0x06000AF2 RID: 2802 RVA: 0x0003A780 File Offset: 0x00038980
	// (set) Token: 0x06000AF3 RID: 2803 RVA: 0x0003A788 File Offset: 0x00038988
	internal virtual Control28 FlatButton10
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton10;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_103);
			Control28 flatButton = this._FlatButton10;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton10 = value;
			flatButton = this._FlatButton10;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x17000344 RID: 836
	// (get) Token: 0x06000AF4 RID: 2804 RVA: 0x0003A7CC File Offset: 0x000389CC
	// (set) Token: 0x06000AF5 RID: 2805 RVA: 0x0003A7D4 File Offset: 0x000389D4
	internal virtual System.Windows.Forms.Timer Timer_10
	{
		[CompilerGenerated]
		get
		{
			return this.timer_10;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_105);
			System.Windows.Forms.Timer timer = this.timer_10;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_10 = value;
			timer = this.timer_10;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000345 RID: 837
	// (get) Token: 0x06000AF6 RID: 2806 RVA: 0x0003A818 File Offset: 0x00038A18
	// (set) Token: 0x06000AF7 RID: 2807 RVA: 0x0003A820 File Offset: 0x00038A20
	internal virtual GControl7 FaderGroupBox17 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000346 RID: 838
	// (get) Token: 0x06000AF8 RID: 2808 RVA: 0x0003A82C File Offset: 0x00038A2C
	// (set) Token: 0x06000AF9 RID: 2809 RVA: 0x0003A834 File Offset: 0x00038A34
	internal virtual Label Label10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000347 RID: 839
	// (get) Token: 0x06000AFA RID: 2810 RVA: 0x0003A840 File Offset: 0x00038A40
	// (set) Token: 0x06000AFB RID: 2811 RVA: 0x0003A848 File Offset: 0x00038A48
	internal virtual Control39 FlatTrackBar3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000348 RID: 840
	// (get) Token: 0x06000AFC RID: 2812 RVA: 0x0003A854 File Offset: 0x00038A54
	// (set) Token: 0x06000AFD RID: 2813 RVA: 0x0003A85C File Offset: 0x00038A5C
	internal virtual Control39 FlatTrackBar5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000349 RID: 841
	// (get) Token: 0x06000AFE RID: 2814 RVA: 0x0003A868 File Offset: 0x00038A68
	// (set) Token: 0x06000AFF RID: 2815 RVA: 0x0003A870 File Offset: 0x00038A70
	internal virtual Control31 FlatCheckBox23
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox23;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_107);
			Control31 flatCheckBox = this._FlatCheckBox23;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox23 = value;
			flatCheckBox = this._FlatCheckBox23;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x1700034A RID: 842
	// (get) Token: 0x06000B00 RID: 2816 RVA: 0x0003A8B4 File Offset: 0x00038AB4
	// (set) Token: 0x06000B01 RID: 2817 RVA: 0x0003A8BC File Offset: 0x00038ABC
	internal virtual Control31 FlatCheckBox26
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox26;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_108);
			Control31 flatCheckBox = this._FlatCheckBox26;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox26 = value;
			flatCheckBox = this._FlatCheckBox26;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x1700034B RID: 843
	// (get) Token: 0x06000B02 RID: 2818 RVA: 0x0003A900 File Offset: 0x00038B00
	// (set) Token: 0x06000B03 RID: 2819 RVA: 0x0003A908 File Offset: 0x00038B08
	internal virtual System.Windows.Forms.Timer Timer_11
	{
		[CompilerGenerated]
		get
		{
			return this.timer_11;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_110);
			System.Windows.Forms.Timer timer = this.timer_11;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_11 = value;
			timer = this.timer_11;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x1700034C RID: 844
	// (get) Token: 0x06000B04 RID: 2820 RVA: 0x0003A94C File Offset: 0x00038B4C
	// (set) Token: 0x06000B05 RID: 2821 RVA: 0x0003A954 File Offset: 0x00038B54
	internal virtual GControl7 FaderGroupBox18 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700034D RID: 845
	// (get) Token: 0x06000B06 RID: 2822 RVA: 0x0003A960 File Offset: 0x00038B60
	// (set) Token: 0x06000B07 RID: 2823 RVA: 0x0003A968 File Offset: 0x00038B68
	internal virtual Label Label13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700034E RID: 846
	// (get) Token: 0x06000B08 RID: 2824 RVA: 0x0003A974 File Offset: 0x00038B74
	// (set) Token: 0x06000B09 RID: 2825 RVA: 0x0003A97C File Offset: 0x00038B7C
	internal virtual Control39 FlatTrackBar6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700034F RID: 847
	// (get) Token: 0x06000B0A RID: 2826 RVA: 0x0003A988 File Offset: 0x00038B88
	// (set) Token: 0x06000B0B RID: 2827 RVA: 0x0003A990 File Offset: 0x00038B90
	internal virtual Control39 FlatTrackBar7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000350 RID: 848
	// (get) Token: 0x06000B0C RID: 2828 RVA: 0x0003A99C File Offset: 0x00038B9C
	// (set) Token: 0x06000B0D RID: 2829 RVA: 0x0003A9A4 File Offset: 0x00038BA4
	internal virtual Control31 FlatCheckBox22 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000351 RID: 849
	// (get) Token: 0x06000B0E RID: 2830 RVA: 0x0003A9B0 File Offset: 0x00038BB0
	// (set) Token: 0x06000B0F RID: 2831 RVA: 0x0003A9B8 File Offset: 0x00038BB8
	internal virtual Control31 FlatCheckBox25 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000352 RID: 850
	// (get) Token: 0x06000B10 RID: 2832 RVA: 0x0003A9C4 File Offset: 0x00038BC4
	// (set) Token: 0x06000B11 RID: 2833 RVA: 0x0003A9CC File Offset: 0x00038BCC
	internal virtual GControl24 FlatTextBox6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000353 RID: 851
	// (get) Token: 0x06000B12 RID: 2834 RVA: 0x0003A9D8 File Offset: 0x00038BD8
	// (set) Token: 0x06000B13 RID: 2835 RVA: 0x0003A9E0 File Offset: 0x00038BE0
	internal virtual System.Windows.Forms.Timer Timer_12
	{
		[CompilerGenerated]
		get
		{
			return this.timer_12;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_118);
			System.Windows.Forms.Timer timer = this.timer_12;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_12 = value;
			timer = this.timer_12;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000354 RID: 852
	// (get) Token: 0x06000B14 RID: 2836 RVA: 0x0003AA24 File Offset: 0x00038C24
	// (set) Token: 0x06000B15 RID: 2837 RVA: 0x0003AA2C File Offset: 0x00038C2C
	internal virtual Control31 FlatCheckBox27
	{
		[CompilerGenerated]
		get
		{
			return this._FlatCheckBox27;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			Control31.Delegate12 value2 = new Control31.Delegate12(this.method_139);
			Control31 flatCheckBox = this._FlatCheckBox27;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 -= value2;
			}
			this._FlatCheckBox27 = value;
			flatCheckBox = this._FlatCheckBox27;
			if (flatCheckBox != null)
			{
				flatCheckBox.Event_0 += value2;
			}
		}
	}

	// Token: 0x17000355 RID: 853
	// (get) Token: 0x06000B16 RID: 2838 RVA: 0x0003AA70 File Offset: 0x00038C70
	// (set) Token: 0x06000B17 RID: 2839 RVA: 0x0003AA78 File Offset: 0x00038C78
	internal virtual System.Windows.Forms.Timer Timer_13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000356 RID: 854
	// (get) Token: 0x06000B18 RID: 2840 RVA: 0x0003AA84 File Offset: 0x00038C84
	// (set) Token: 0x06000B19 RID: 2841 RVA: 0x0003AA8C File Offset: 0x00038C8C
	internal virtual Control39 FlatTrackBar8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000357 RID: 855
	// (get) Token: 0x06000B1A RID: 2842 RVA: 0x0003AA98 File Offset: 0x00038C98
	// (set) Token: 0x06000B1B RID: 2843 RVA: 0x0003AAA0 File Offset: 0x00038CA0
	internal virtual System.Windows.Forms.Timer Timer_14
	{
		[CompilerGenerated]
		get
		{
			return this.timer_14;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_117);
			System.Windows.Forms.Timer timer = this.timer_14;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_14 = value;
			timer = this.timer_14;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000358 RID: 856
	// (get) Token: 0x06000B1C RID: 2844 RVA: 0x0003AAE4 File Offset: 0x00038CE4
	// (set) Token: 0x06000B1D RID: 2845 RVA: 0x0003AAEC File Offset: 0x00038CEC
	internal virtual GClass10 ThirteenComboBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000359 RID: 857
	// (get) Token: 0x06000B1E RID: 2846 RVA: 0x0003AAF8 File Offset: 0x00038CF8
	// (set) Token: 0x06000B1F RID: 2847 RVA: 0x0003AB00 File Offset: 0x00038D00
	internal virtual Class20 FlatLabel26 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700035A RID: 858
	// (get) Token: 0x06000B20 RID: 2848 RVA: 0x0003AB0C File Offset: 0x00038D0C
	// (set) Token: 0x06000B21 RID: 2849 RVA: 0x0003AB14 File Offset: 0x00038D14
	internal virtual System.Windows.Forms.Timer Timer_15
	{
		[CompilerGenerated]
		get
		{
			return this.timer_15;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_119);
			System.Windows.Forms.Timer timer = this.timer_15;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_15 = value;
			timer = this.timer_15;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x1700035B RID: 859
	// (get) Token: 0x06000B22 RID: 2850 RVA: 0x0003AB58 File Offset: 0x00038D58
	// (set) Token: 0x06000B23 RID: 2851 RVA: 0x0003AB60 File Offset: 0x00038D60
	internal virtual System.Windows.Forms.Timer Timer_16
	{
		[CompilerGenerated]
		get
		{
			return this.timer_16;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_120);
			System.Windows.Forms.Timer timer = this.timer_16;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_16 = value;
			timer = this.timer_16;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x1700035C RID: 860
	// (get) Token: 0x06000B24 RID: 2852 RVA: 0x0003ABA4 File Offset: 0x00038DA4
	// (set) Token: 0x06000B25 RID: 2853 RVA: 0x0003ABAC File Offset: 0x00038DAC
	internal virtual GControl7 FaderGroupBox19 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700035D RID: 861
	// (get) Token: 0x06000B26 RID: 2854 RVA: 0x0003ABB8 File Offset: 0x00038DB8
	// (set) Token: 0x06000B27 RID: 2855 RVA: 0x0003ABC0 File Offset: 0x00038DC0
	internal virtual Label Label8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700035E RID: 862
	// (get) Token: 0x06000B28 RID: 2856 RVA: 0x0003ABCC File Offset: 0x00038DCC
	// (set) Token: 0x06000B29 RID: 2857 RVA: 0x0003ABD4 File Offset: 0x00038DD4
	internal virtual GClass10 ThirteenComboBox9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700035F RID: 863
	// (get) Token: 0x06000B2A RID: 2858 RVA: 0x0003ABE0 File Offset: 0x00038DE0
	// (set) Token: 0x06000B2B RID: 2859 RVA: 0x0003ABE8 File Offset: 0x00038DE8
	internal virtual Class20 FlatLabel51 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000360 RID: 864
	// (get) Token: 0x06000B2C RID: 2860 RVA: 0x0003ABF4 File Offset: 0x00038DF4
	// (set) Token: 0x06000B2D RID: 2861 RVA: 0x0003ABFC File Offset: 0x00038DFC
	internal virtual GClass10 ThirteenComboBox10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000361 RID: 865
	// (get) Token: 0x06000B2E RID: 2862 RVA: 0x0003AC08 File Offset: 0x00038E08
	// (set) Token: 0x06000B2F RID: 2863 RVA: 0x0003AC10 File Offset: 0x00038E10
	internal virtual Class20 FlatLabel52 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000362 RID: 866
	// (get) Token: 0x06000B30 RID: 2864 RVA: 0x0003AC1C File Offset: 0x00038E1C
	// (set) Token: 0x06000B31 RID: 2865 RVA: 0x0003AC24 File Offset: 0x00038E24
	internal virtual System.Windows.Forms.Timer Timer_17
	{
		[CompilerGenerated]
		get
		{
			return this.timer_17;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_121);
			System.Windows.Forms.Timer timer = this.timer_17;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_17 = value;
			timer = this.timer_17;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000363 RID: 867
	// (get) Token: 0x06000B32 RID: 2866 RVA: 0x0003AC68 File Offset: 0x00038E68
	// (set) Token: 0x06000B33 RID: 2867 RVA: 0x0003AC70 File Offset: 0x00038E70
	internal virtual System.Windows.Forms.Timer Timer_18
	{
		[CompilerGenerated]
		get
		{
			return this.timer_18;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_122);
			System.Windows.Forms.Timer timer = this.timer_18;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_18 = value;
			timer = this.timer_18;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000364 RID: 868
	// (get) Token: 0x06000B34 RID: 2868 RVA: 0x0003ACB4 File Offset: 0x00038EB4
	// (set) Token: 0x06000B35 RID: 2869 RVA: 0x0003ACBC File Offset: 0x00038EBC
	internal virtual GControl7 FaderGroupBox20 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000365 RID: 869
	// (get) Token: 0x06000B36 RID: 2870 RVA: 0x0003ACC8 File Offset: 0x00038EC8
	// (set) Token: 0x06000B37 RID: 2871 RVA: 0x0003ACD0 File Offset: 0x00038ED0
	internal virtual GControl24 LogInNormalTextBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000366 RID: 870
	// (get) Token: 0x06000B38 RID: 2872 RVA: 0x0003ACDC File Offset: 0x00038EDC
	// (set) Token: 0x06000B39 RID: 2873 RVA: 0x0003ACE4 File Offset: 0x00038EE4
	internal virtual Label Label14 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000367 RID: 871
	// (get) Token: 0x06000B3A RID: 2874 RVA: 0x0003ACF0 File Offset: 0x00038EF0
	// (set) Token: 0x06000B3B RID: 2875 RVA: 0x0003ACF8 File Offset: 0x00038EF8
	internal virtual Control31 FlatCheckBox29 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000368 RID: 872
	// (get) Token: 0x06000B3C RID: 2876 RVA: 0x0003AD04 File Offset: 0x00038F04
	// (set) Token: 0x06000B3D RID: 2877 RVA: 0x0003AD0C File Offset: 0x00038F0C
	internal virtual Control31 FlatCheckBox14 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000369 RID: 873
	// (get) Token: 0x06000B3E RID: 2878 RVA: 0x0003AD18 File Offset: 0x00038F18
	// (set) Token: 0x06000B3F RID: 2879 RVA: 0x0003AD20 File Offset: 0x00038F20
	internal virtual GControl24 LogInNormalTextBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700036A RID: 874
	// (get) Token: 0x06000B40 RID: 2880 RVA: 0x0003AD2C File Offset: 0x00038F2C
	// (set) Token: 0x06000B41 RID: 2881 RVA: 0x0003AD34 File Offset: 0x00038F34
	internal virtual Control31 FlatCheckBox13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700036B RID: 875
	// (get) Token: 0x06000B42 RID: 2882 RVA: 0x0003AD40 File Offset: 0x00038F40
	// (set) Token: 0x06000B43 RID: 2883 RVA: 0x0003AD48 File Offset: 0x00038F48
	internal virtual GControl24 LogInNormalTextBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700036C RID: 876
	// (get) Token: 0x06000B44 RID: 2884 RVA: 0x0003AD54 File Offset: 0x00038F54
	// (set) Token: 0x06000B45 RID: 2885 RVA: 0x0003AD5C File Offset: 0x00038F5C
	internal virtual Control31 FlatCheckBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700036D RID: 877
	// (get) Token: 0x06000B46 RID: 2886 RVA: 0x0003AD68 File Offset: 0x00038F68
	// (set) Token: 0x06000B47 RID: 2887 RVA: 0x0003AD70 File Offset: 0x00038F70
	internal virtual System.Windows.Forms.Timer Timer_19
	{
		[CompilerGenerated]
		get
		{
			return this.timer_19;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_125);
			System.Windows.Forms.Timer timer = this.timer_19;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_19 = value;
			timer = this.timer_19;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x1700036E RID: 878
	// (get) Token: 0x06000B48 RID: 2888 RVA: 0x0003ADB4 File Offset: 0x00038FB4
	// (set) Token: 0x06000B49 RID: 2889 RVA: 0x0003ADBC File Offset: 0x00038FBC
	internal virtual Control28 FlatButton31
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton31;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_126);
			Control28 flatButton = this._FlatButton31;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton31 = value;
			flatButton = this._FlatButton31;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x1700036F RID: 879
	// (get) Token: 0x06000B4A RID: 2890 RVA: 0x0003AE00 File Offset: 0x00039000
	// (set) Token: 0x06000B4B RID: 2891 RVA: 0x0003AE08 File Offset: 0x00039008
	internal virtual Control28 FlatButton32
	{
		[CompilerGenerated]
		get
		{
			return this._FlatButton32;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_128);
			Control28 flatButton = this._FlatButton32;
			if (flatButton != null)
			{
				flatButton.Click -= value2;
			}
			this._FlatButton32 = value;
			flatButton = this._FlatButton32;
			if (flatButton != null)
			{
				flatButton.Click += value2;
			}
		}
	}

	// Token: 0x17000370 RID: 880
	// (get) Token: 0x06000B4C RID: 2892 RVA: 0x0003AE4C File Offset: 0x0003904C
	// (set) Token: 0x06000B4D RID: 2893 RVA: 0x0003AE54 File Offset: 0x00039054
	internal virtual System.Windows.Forms.Timer Timer_20
	{
		[CompilerGenerated]
		get
		{
			return this.timer_20;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_129);
			System.Windows.Forms.Timer timer = this.timer_20;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_20 = value;
			timer = this.timer_20;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000371 RID: 881
	// (get) Token: 0x06000B4E RID: 2894 RVA: 0x0003AE98 File Offset: 0x00039098
	// (set) Token: 0x06000B4F RID: 2895 RVA: 0x0003AEA0 File Offset: 0x000390A0
	internal virtual System.Windows.Forms.Timer Timer_21
	{
		[CompilerGenerated]
		get
		{
			return this.timer_21;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_130);
			System.Windows.Forms.Timer timer = this.timer_21;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_21 = value;
			timer = this.timer_21;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000372 RID: 882
	// (get) Token: 0x06000B50 RID: 2896 RVA: 0x0003AEE4 File Offset: 0x000390E4
	// (set) Token: 0x06000B51 RID: 2897 RVA: 0x0003AEEC File Offset: 0x000390EC
	internal virtual GControl24 FlatTextBox7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000373 RID: 883
	// (get) Token: 0x06000B52 RID: 2898 RVA: 0x0003AEF8 File Offset: 0x000390F8
	// (set) Token: 0x06000B53 RID: 2899 RVA: 0x0003AF00 File Offset: 0x00039100
	internal virtual GControl24 FlatTextBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000374 RID: 884
	// (get) Token: 0x06000B54 RID: 2900 RVA: 0x0003AF0C File Offset: 0x0003910C
	// (set) Token: 0x06000B55 RID: 2901 RVA: 0x0003AF14 File Offset: 0x00039114
	internal virtual GControl24 FlatTextBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000375 RID: 885
	// (get) Token: 0x06000B56 RID: 2902 RVA: 0x0003AF20 File Offset: 0x00039120
	// (set) Token: 0x06000B57 RID: 2903 RVA: 0x0003AF28 File Offset: 0x00039128
	internal virtual GControl24 FlatTextBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000376 RID: 886
	// (get) Token: 0x06000B58 RID: 2904 RVA: 0x0003AF34 File Offset: 0x00039134
	// (set) Token: 0x06000B59 RID: 2905 RVA: 0x0003AF3C File Offset: 0x0003913C
	internal virtual GControl24 FlatTextBox5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000377 RID: 887
	// (get) Token: 0x06000B5A RID: 2906 RVA: 0x0003AF48 File Offset: 0x00039148
	// (set) Token: 0x06000B5B RID: 2907 RVA: 0x0003AF50 File Offset: 0x00039150
	internal virtual GControl24 FlatTextBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000378 RID: 888
	// (get) Token: 0x06000B5C RID: 2908 RVA: 0x0003AF5C File Offset: 0x0003915C
	// (set) Token: 0x06000B5D RID: 2909 RVA: 0x0003AF64 File Offset: 0x00039164
	internal virtual Class20 FlatLabel53 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000379 RID: 889
	// (get) Token: 0x06000B5E RID: 2910 RVA: 0x0003AF70 File Offset: 0x00039170
	// (set) Token: 0x06000B5F RID: 2911 RVA: 0x0003AF78 File Offset: 0x00039178
	internal virtual Class20 FlatLabel3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700037A RID: 890
	// (get) Token: 0x06000B60 RID: 2912 RVA: 0x0003AF84 File Offset: 0x00039184
	// (set) Token: 0x06000B61 RID: 2913 RVA: 0x0003AF8C File Offset: 0x0003918C
	internal virtual System.Windows.Forms.Timer Timer_22
	{
		[CompilerGenerated]
		get
		{
			return this.timer_22;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_131);
			System.Windows.Forms.Timer timer = this.timer_22;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_22 = value;
			timer = this.timer_22;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x1700037B RID: 891
	// (get) Token: 0x06000B62 RID: 2914 RVA: 0x0003AFD0 File Offset: 0x000391D0
	// (set) Token: 0x06000B63 RID: 2915 RVA: 0x0003AFD8 File Offset: 0x000391D8
	internal virtual System.Windows.Forms.Timer Timer_23
	{
		[CompilerGenerated]
		get
		{
			return this.timer_23;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_132);
			System.Windows.Forms.Timer timer = this.timer_23;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_23 = value;
			timer = this.timer_23;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x1700037C RID: 892
	// (get) Token: 0x06000B64 RID: 2916 RVA: 0x0003B01C File Offset: 0x0003921C
	// (set) Token: 0x06000B65 RID: 2917 RVA: 0x0003B024 File Offset: 0x00039224
	internal virtual System.Windows.Forms.Timer Timer_24
	{
		[CompilerGenerated]
		get
		{
			return this.timer_24;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_133);
			System.Windows.Forms.Timer timer = this.timer_24;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_24 = value;
			timer = this.timer_24;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x1700037D RID: 893
	// (get) Token: 0x06000B66 RID: 2918 RVA: 0x0003B068 File Offset: 0x00039268
	// (set) Token: 0x06000B67 RID: 2919 RVA: 0x0003B070 File Offset: 0x00039270
	internal virtual System.Windows.Forms.Timer Timer_25
	{
		[CompilerGenerated]
		get
		{
			return this.timer_25;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_134);
			System.Windows.Forms.Timer timer = this.timer_25;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_25 = value;
			timer = this.timer_25;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x1700037E RID: 894
	// (get) Token: 0x06000B68 RID: 2920 RVA: 0x0003B0B4 File Offset: 0x000392B4
	// (set) Token: 0x06000B69 RID: 2921 RVA: 0x0003B0BC File Offset: 0x000392BC
	internal virtual System.Windows.Forms.Timer Timer_26
	{
		[CompilerGenerated]
		get
		{
			return this.timer_26;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_135);
			System.Windows.Forms.Timer timer = this.timer_26;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_26 = value;
			timer = this.timer_26;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x1700037F RID: 895
	// (get) Token: 0x06000B6A RID: 2922 RVA: 0x0003B100 File Offset: 0x00039300
	// (set) Token: 0x06000B6B RID: 2923 RVA: 0x0003B108 File Offset: 0x00039308
	internal virtual System.Windows.Forms.Timer Timer_27
	{
		[CompilerGenerated]
		get
		{
			return this.timer_27;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_136);
			System.Windows.Forms.Timer timer = this.timer_27;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_27 = value;
			timer = this.timer_27;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000380 RID: 896
	// (get) Token: 0x06000B6C RID: 2924 RVA: 0x0003B14C File Offset: 0x0003934C
	// (set) Token: 0x06000B6D RID: 2925 RVA: 0x0003B154 File Offset: 0x00039354
	internal virtual System.Windows.Forms.Timer Timer_28
	{
		[CompilerGenerated]
		get
		{
			return this.timer_28;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_137);
			System.Windows.Forms.Timer timer = this.timer_28;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_28 = value;
			timer = this.timer_28;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000381 RID: 897
	// (get) Token: 0x06000B6E RID: 2926 RVA: 0x0003B198 File Offset: 0x00039398
	// (set) Token: 0x06000B6F RID: 2927 RVA: 0x0003B1A0 File Offset: 0x000393A0
	internal virtual System.Windows.Forms.Timer Timer_29
	{
		[CompilerGenerated]
		get
		{
			return this.timer_29;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_138);
			System.Windows.Forms.Timer timer = this.timer_29;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_29 = value;
			timer = this.timer_29;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000382 RID: 898
	// (get) Token: 0x06000B70 RID: 2928 RVA: 0x0003B1E4 File Offset: 0x000393E4
	// (set) Token: 0x06000B71 RID: 2929 RVA: 0x0003B1EC File Offset: 0x000393EC
	internal virtual System.Windows.Forms.Timer Timer_30
	{
		[CompilerGenerated]
		get
		{
			return this.timer_30;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_140);
			System.Windows.Forms.Timer timer = this.timer_30;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_30 = value;
			timer = this.timer_30;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000383 RID: 899
	// (get) Token: 0x06000B72 RID: 2930 RVA: 0x0003B230 File Offset: 0x00039430
	// (set) Token: 0x06000B73 RID: 2931 RVA: 0x0003B238 File Offset: 0x00039438
	internal virtual PictureBox PictureBox16 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000384 RID: 900
	// (get) Token: 0x06000B74 RID: 2932 RVA: 0x0003B244 File Offset: 0x00039444
	// (set) Token: 0x06000B75 RID: 2933 RVA: 0x0003B24C File Offset: 0x0003944C
	internal virtual NotifyIcon NotifyIcon_0 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000385 RID: 901
	// (get) Token: 0x06000B76 RID: 2934 RVA: 0x0003B258 File Offset: 0x00039458
	// (set) Token: 0x06000B77 RID: 2935 RVA: 0x0003B260 File Offset: 0x00039460
	internal virtual PictureBox PictureBox18 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000386 RID: 902
	// (get) Token: 0x06000B78 RID: 2936 RVA: 0x0003B26C File Offset: 0x0003946C
	// (set) Token: 0x06000B79 RID: 2937 RVA: 0x0003B274 File Offset: 0x00039474
	internal virtual PictureBox PictureBox19 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000387 RID: 903
	// (get) Token: 0x06000B7A RID: 2938 RVA: 0x0003B280 File Offset: 0x00039480
	// (set) Token: 0x06000B7B RID: 2939 RVA: 0x0003B288 File Offset: 0x00039488
	internal virtual PictureBox PictureBox20 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000388 RID: 904
	// (get) Token: 0x06000B7C RID: 2940 RVA: 0x0003B294 File Offset: 0x00039494
	// (set) Token: 0x06000B7D RID: 2941 RVA: 0x0003B29C File Offset: 0x0003949C
	internal virtual PictureBox PictureBox21 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000389 RID: 905
	// (get) Token: 0x06000B7E RID: 2942 RVA: 0x0003B2A8 File Offset: 0x000394A8
	// (set) Token: 0x06000B7F RID: 2943 RVA: 0x0003B2B0 File Offset: 0x000394B0
	internal virtual PictureBox PictureBox22 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x06000B80 RID: 2944
	[DllImport("kernel32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern int WriteProcessMemory(int int_29, int int_30, ref int int_31, int int_32, ref int int_33);

	// Token: 0x1700038A RID: 906
	// (get) Token: 0x06000B81 RID: 2945 RVA: 0x0003B2BC File Offset: 0x000394BC
	// (set) Token: 0x06000B82 RID: 2946 RVA: 0x0003B2C4 File Offset: 0x000394C4
	private virtual SpeechRecognitionEngine SpeechRecognitionEngine_0
	{
		[CompilerGenerated]
		get
		{
			return this.speechRecognitionEngine_0;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler<RecognizeCompletedEventArgs> value2 = new EventHandler<RecognizeCompletedEventArgs>(this.method_2);
			EventHandler<SpeechRecognizedEventArgs> value3 = new EventHandler<SpeechRecognizedEventArgs>(this.method_3);
			SpeechRecognitionEngine speechRecognitionEngine = this.speechRecognitionEngine_0;
			if (speechRecognitionEngine != null)
			{
				speechRecognitionEngine.RecognizeCompleted -= value2;
				speechRecognitionEngine.SpeechRecognized -= value3;
			}
			this.speechRecognitionEngine_0 = value;
			speechRecognitionEngine = this.speechRecognitionEngine_0;
			if (speechRecognitionEngine != null)
			{
				speechRecognitionEngine.RecognizeCompleted += value2;
				speechRecognitionEngine.SpeechRecognized += value3;
			}
		}
	}

	// Token: 0x06000B83 RID: 2947
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern int GetAsyncKeyState(int int_29);

	// Token: 0x06000B84 RID: 2948
	[DllImport("user32.dll", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern bool mouse_event(int int_29, int int_30, int int_31, int int_32, int int_33);

	// Token: 0x06000B85 RID: 2949
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern int GetMessageExtraInfo();

	// Token: 0x06000B86 RID: 2950
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern long SetCursorPos(long long_0, long long_1);

	// Token: 0x06000B87 RID: 2951
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern long GetCursorPos(ref skeet.GStruct2 gstruct2_0);

	// Token: 0x06000B88 RID: 2952
	[DllImport("user32.dll", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	public static extern int SendMessageA(int int_29, int int_30, int int_31, [MarshalAs(UnmanagedType.VBByRefStr)] ref string string_33);

	// Token: 0x06000B89 RID: 2953
	[DllImport("user32.dll", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern void keybd_event(byte byte_0, byte byte_1, int int_29, int int_30);

	// Token: 0x06000B8A RID: 2954
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	public static extern int MapVirtualKeyA(int int_29, int int_30);

	// Token: 0x06000B8B RID: 2955
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern long GetActiveWindow();

	// Token: 0x06000B8C RID: 2956
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern int GetKeyState(long long_0);

	// Token: 0x06000B8D RID: 2957
	[DllImport("user32.dll", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
	public static extern bool mouse_event_1(int int_29, int int_30, int int_31, int int_32, int int_33);

	// Token: 0x06000B8E RID: 2958
	[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "GetAsyncKeyState", ExactSpelling = true, SetLastError = true)]
	private static extern int GetAsyncKeyState_1(int int_29);

	// Token: 0x1700038B RID: 907
	// (get) Token: 0x06000B8F RID: 2959 RVA: 0x0003B324 File Offset: 0x00039524
	// (set) Token: 0x06000B90 RID: 2960 RVA: 0x0003B32C File Offset: 0x0003952C
	private virtual GClass2 GClass2_0
	{
		[CompilerGenerated]
		get
		{
			return this.gclass2_0;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			GClass2.GDelegate1 value2 = new GClass2.GDelegate1(this.method_23);
			GClass2.GDelegate2 value3 = new GClass2.GDelegate2(this.method_24);
			GClass2 gclass = this.gclass2_0;
			if (gclass != null)
			{
				gclass.Event_1 -= value2;
				gclass.Event_2 -= value3;
			}
			this.gclass2_0 = value;
			gclass = this.gclass2_0;
			if (gclass != null)
			{
				gclass.Event_1 += value2;
				gclass.Event_2 += value3;
			}
		}
	}

	// Token: 0x06000B91 RID: 2961
	[DllImport("user32.dll", EntryPoint = "GetAsyncKeyState")]
	private static extern short GetAsyncKeyState_2(Keys keys_1);

	// Token: 0x06000B92 RID: 2962
	[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
	private static extern IntPtr GetForegroundWindow();

	// Token: 0x06000B93 RID: 2963
	[DllImport("user32", CharSet = CharSet.Auto, SetLastError = true)]
	private static extern int GetWindowText(IntPtr intptr_0, StringBuilder stringBuilder_0, int int_29);

	// Token: 0x06000B94 RID: 2964 RVA: 0x0003B38C File Offset: 0x0003958C
	private string method_0()
	{
		StringBuilder stringBuilder = new StringBuilder(256);
		IntPtr foregroundWindow = skeet.GetForegroundWindow();
		skeet.GetWindowText(foregroundWindow, stringBuilder, stringBuilder.Capacity);
		return stringBuilder.ToString();
	}

	// Token: 0x06000B95 RID: 2965
	[DllImport("user32.dll")]
	private static extern IntPtr FindWindowW([MarshalAs(UnmanagedType.LPTStr)] string string_33, [MarshalAs(UnmanagedType.LPTStr)] string string_34);

	// Token: 0x06000B96 RID: 2966
	[DllImport("user32.dll")]
	[return: MarshalAs(UnmanagedType.Bool)]
	private static extern bool GetWindowRect(IntPtr intptr_0, ref skeet.Struct2 struct2_0);

	// Token: 0x06000B97 RID: 2967 RVA: 0x0003B3C0 File Offset: 0x000395C0
	private void skeet_MouseMove(object sender, MouseEventArgs e)
	{
		if (e.Button != MouseButtons.Left)
		{
			this.point_0 = e.Location;
		}
		else
		{
			base.Location += checked(new Size(e.X - this.point_0.X, e.Y - this.point_0.Y));
		}
	}

	// Token: 0x06000B98 RID: 2968 RVA: 0x0003B424 File Offset: 0x00039624
	public object method_1()
	{
		NetworkInterface[] allNetworkInterfaces = NetworkInterface.GetAllNetworkInterfaces();
		return allNetworkInterfaces[0].GetPhysicalAddress().ToString();
	}

	// Token: 0x06000B99 RID: 2969 RVA: 0x0003B448 File Offset: 0x00039648
	private async void skeet_Load(object sender, EventArgs e)
	{
		skeet.Class28.smethod_0(this.PictureBox16, Color.FromArgb(60, 60, 60));
		skeet.Class28.smethod_1(this.FlatCheckBox27);
		skeet.Class28.smethod_2(this.Timer_23);
		skeet.Class28.smethod_3(this.Timer_23, 1);
		this.string_0 = skeet.Class28.smethod_5(skeet.Class28.smethod_4(), "https://pastebin.com/raw/X4qxLemx");
		this.string_1 = "8cVveuB3dpMlUq1R685D38BNmX4sQOK9xTrcDdzCYwhWg5G1Ws9MomCfDOTaVOBDkXhBpnHsaaRHnvBS1kBo1wYPDLtgJTivJbDRS2Ygg5g1rlCyz97Yg4HSBVWLuzBVF1lJJgSm7f5fTwSQrRUstHWcmUsmx7sEBtAJTquP4bdANEpjB7P2GqR0b0UQgZT6jjqrr5TEOFmOljeWzTqBERHqmCfkEQx6LquiK4VjdF1hbuD6Opie19kxlfMoNITwmh4NIHAlcyClL87psDM3L65ZH6fBRqH8FK9tWeCqMZ1NTD1yIdKfIliVMYCwXd36GCbecUAniPAEOuzyxOcyAMG6itUG28ksTPPvBvgVP40BuaRnriGasisP1ZPnIqrv2HOp9tu6aOyyTMmWYWBROGmdhwetFP1M33ruNqtnqgURbhSecquw3IwD6WhQgemR5tfxWaTT4eKKuJelI3gpDmTb0C9P5MddMIrxNZWH0Mcjfew0Fve8wBR7ciCElsFtTDwjjviKIoBPUUfWWjZrT1K0oScz7oKN4v5iVqDPImuZye4GjSyTsCQ7VTjzhOO4Ugsv8XObF83wDIl6FGrQ9VCS46gw3NYAXocAIPkk8K8zkCmreUAgoghQETUAYIJ4csbWm9ixDdUwBdnuTTkgDlKXcWxWp7P9c4fVBXiJ8bGI5YSVUIICv8VsYB1GFRJ9ghDOmqW3zt5wsro5twcOsyNm3iuI0vFdSF0GuUPTuWL0svkFaqlSVE68QC9k5uSDahKNgPvhDGUe1rf8QNXBLLwIG8h5tcun";
		skeet.Class28.smethod_2(this.Timer_22);
		skeet.Class28.smethod_6(this.FlatTrackBar8);
		this.method_102();
		skeet.Class28.smethod_6(this.FaderGroupBox17);
		skeet.Class28.smethod_6(this.FaderGroupBox18);
		skeet.Class28.smethod_6(this.FaderGroupBox19);
		skeet.Class28.smethod_6(this.FaderGroupBox20);
		if (!(skeet.Class28.smethod_7(this.PictureBox2) == this.color_0))
		{
			skeet.Class28.smethod_0(this.PictureBox2, skeet.Class28.smethod_7(this.PictureBox12));
			skeet.Class28.smethod_0(this.FlatLabel5, skeet.Class28.smethod_7(this.PictureBox12));
			skeet.Class28.smethod_0(this.PictureBox18, skeet.Class28.smethod_7(this.PictureBox12));
			skeet.Class28.smethod_0(this.PictureBox3, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.FlatLabel6, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.PictureBox19, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.PictureBox4, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.FlatLabel8, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.PictureBox20, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.PictureBox5, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.FlatLabel38, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.PictureBox21, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.PictureBox6, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.FlatLabel40, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_0(this.PictureBox22, Color.FromArgb(10, 10, 10));
			skeet.Class28.smethod_1(this.NsGroupBox1);
			skeet.Class28.smethod_6(this.NsGroupBox7);
			skeet.Class28.smethod_6(this.NsGroupBox2);
			skeet.Class28.smethod_6(this.NsGroupBox3);
			skeet.Class28.smethod_6(this.NsGroupBox4);
		}
		this.GClass2_0.method_1();
		skeet.Class28.smethod_6(this.PictureBox1);
		string text = skeet.Class28.smethod_12(skeet.Class28.smethod_11(skeet.Class28.smethod_10(skeet.Class28.smethod_9(skeet.Class28.smethod_8("userprofile"), "\\desktop\\"), this.method_1())));
		if (!skeet.Class28.smethod_13(text))
		{
			skeet.Class28.smethod_15(skeet.Class28.smethod_14(text));
		}
		skeet.Class28.smethod_6(this.FaderGroupBox15);
		skeet.Class28.smethod_6(this.FaderGroupBox14);
		skeet.Class28.smethod_6(this.FaderGroupBox12);
		skeet.Class28.smethod_16(this.ThirteenComboBox6, "V");
		skeet.Class28.smethod_6(this.FaderGroupBox3);
		skeet.Class28.smethod_2(this.Timer_9);
		skeet.Class28.smethod_2(this.Timer_4);
		skeet.Class28.smethod_17(this.ThirteenComboBox3, "1");
		skeet.Class28.smethod_17(this.ThirteenComboBox4, "2");
		skeet.Class28.smethod_17(this.ThirteenComboBox5, "9");
		skeet.Class28.smethod_2(this.Timer_3);
		skeet.Class28.smethod_18(this.SpeechRecognitionEngine_0);
		SrgsDocument srgsDocument_ = skeet.Class28.smethod_19();
		SrgsRule srgsRule = skeet.Class28.smethod_20("fdp");
		SrgsOneOf srgsElement_ = skeet.Class28.smethod_21(new string[]
		{
			"Main",
			"Settings",
			"Misc",
			"Servers",
			"Destruct"
		});
		skeet.Class28.smethod_22(srgsRule, srgsElement_);
		skeet.Class28.smethod_23(srgsDocument_).Add(srgsRule);
		skeet.Class28.smethod_24(srgsDocument_, srgsRule);
		skeet.Class28.smethod_26(this.SpeechRecognitionEngine_0, skeet.Class28.smethod_25(srgsDocument_));
		skeet.Class28.smethod_27(this.SpeechRecognitionEngine_0);
		skeet.Class28.smethod_1(this.NsGroupBox1);
		skeet.Class28.smethod_6(this.NsGroupBox2);
		skeet.Class28.smethod_6(this.NsGroupBox3);
		skeet.Class28.smethod_2(this.Timer_8);
		skeet.Class28.smethod_29(skeet.Class28.smethod_28(this.ThirteenComboBox1), new object[]
		{
			"Default Sound",
			"Razer Deathadder",
			"Logitech G502",
			"Logitech GPro",
			"Logitech G303",
			"Microsoft Mouse",
			"HP Mouse",
			"Non-Brand Mouse"
		});
		Process[] array = skeet.Class28.smethod_30("explorer");
		foreach (Process process_ in array)
		{
			skeet.Class28.smethod_31(process_);
		}
		await skeet.Class28.smethod_32(100);
		Process.Start("explorer");
	}

	// Token: 0x06000B9A RID: 2970 RVA: 0x0003B490 File Offset: 0x00039690
	private void method_2(object sender, RecognizeCompletedEventArgs e)
	{
		this.SpeechRecognitionEngine_0.RecognizeAsync();
	}

	// Token: 0x06000B9B RID: 2971 RVA: 0x0003B4A0 File Offset: 0x000396A0
	private void method_3(object sender, SpeechRecognizedEventArgs e)
	{
		new SrgsOneOf(new string[]
		{
			"Main",
			"Settings",
			"Misc",
			"Servers",
			"Destruct",
			this.LogInNormalTextBox1.System.Windows.Forms.Control.Text
		});
		if (Operators.CompareString(this.FlatLabel12.Text, "Listening...", false) == 0)
		{
			string text = e.Result.Text;
			if (Operators.CompareString(text, this.LogInNormalTextBox1.System.Windows.Forms.Control.Text, false) == 0 && this.FlatCheckBox29.Boolean_0)
			{
				this.FlatButton12.Color_0 = this.PictureBox12.BackColor;
				this.Timer_7.Start();
			}
			if (this.FlatCheckBox14.Boolean_0)
			{
				string text2 = e.Result.Text;
				if (Operators.CompareString(text2, "Main", false) != 0)
				{
					if (Operators.CompareString(text2, "Misc", false) != 0)
					{
						if (Operators.CompareString(text2, "Settings", false) != 0)
						{
							if (Operators.CompareString(text2, "Servers", false) != 0)
							{
								if (Operators.CompareString(text2, "Destruct", false) == 0 && !(this.PictureBox5.BackColor == this.color_0))
								{
									this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
									this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
									this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
									this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
									this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
									this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
									this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
									this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
									this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
									this.PictureBox5.BackColor = this.PictureBox12.BackColor;
									this.FlatLabel38.BackColor = this.PictureBox12.BackColor;
									this.PictureBox21.BackColor = this.PictureBox12.BackColor;
									this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
									this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
									this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
									this.NsGroupBox1.Hide();
									this.NsGroupBox7.Hide();
									this.NsGroupBox2.Hide();
									this.NsGroupBox3.Hide();
									this.NsGroupBox4.Show();
								}
							}
							else if (!(this.PictureBox6.BackColor == this.color_0))
							{
								this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
								this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
								this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
								this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
								this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
								this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
								this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
								this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
								this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
								this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
								this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
								this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
								this.PictureBox6.BackColor = this.PictureBox12.BackColor;
								this.FlatLabel40.BackColor = this.PictureBox12.BackColor;
								this.PictureBox22.BackColor = this.PictureBox12.BackColor;
								this.NsGroupBox7.Hide();
								this.NsGroupBox2.Hide();
								this.NsGroupBox1.Hide();
								this.NsGroupBox3.Show();
								this.NsGroupBox4.Hide();
							}
						}
						else if (!(this.PictureBox3.BackColor == this.color_0))
						{
							this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
							this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
							this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
							this.PictureBox3.BackColor = this.PictureBox12.BackColor;
							this.FlatLabel6.BackColor = this.PictureBox12.BackColor;
							this.PictureBox19.BackColor = this.PictureBox12.BackColor;
							this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
							this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
							this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
							this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
							this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
							this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
							this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
							this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
							this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
							this.NsGroupBox7.Hide();
							this.NsGroupBox3.Hide();
							this.NsGroupBox1.Hide();
							this.NsGroupBox2.Show();
							this.NsGroupBox4.Hide();
						}
					}
					else if (!(this.PictureBox4.BackColor == this.color_0))
					{
						this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
						this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
						this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
						this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
						this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
						this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
						this.PictureBox4.BackColor = this.PictureBox12.BackColor;
						this.FlatLabel8.BackColor = this.PictureBox12.BackColor;
						this.PictureBox20.BackColor = this.PictureBox12.BackColor;
						this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
						this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
						this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
						this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
						this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
						this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
						this.NsGroupBox7.Show();
						this.NsGroupBox3.Hide();
						this.NsGroupBox1.Hide();
						this.NsGroupBox2.Hide();
						this.NsGroupBox4.Hide();
					}
				}
				else if (!(this.PictureBox2.BackColor == this.color_0))
				{
					this.PictureBox2.BackColor = this.PictureBox12.BackColor;
					this.FlatLabel5.BackColor = this.PictureBox12.BackColor;
					this.PictureBox18.BackColor = this.PictureBox12.BackColor;
					this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
					this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
					this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
					this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
					this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
					this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
					this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
					this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
					this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
					this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
					this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
					this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
					this.NsGroupBox1.Show();
					this.NsGroupBox7.Hide();
					this.NsGroupBox2.Hide();
					this.NsGroupBox3.Hide();
					this.NsGroupBox4.Hide();
				}
			}
		}
	}

	// Token: 0x06000B9C RID: 2972 RVA: 0x0003BDF0 File Offset: 0x00039FF0
	private void method_4(object sender, EventArgs e)
	{
		base.Close();
	}

	// Token: 0x06000B9D RID: 2973 RVA: 0x0003BDF8 File Offset: 0x00039FF8
	private void method_5(object object_1)
	{
		checked
		{
			this.PictureBox7.Size = new Size(this.FlatTrackBar4.Int32_2 + 10, this.FlatTrackBar4.Int32_2 + 10);
			this.PictureBox7.Location = new Point((int)Math.Round(unchecked(45.0 - (double)this.FlatTrackBar4.Int32_2 / 2.0)), (int)Math.Round(unchecked(45.0 - (double)this.FlatTrackBar4.Int32_2 / 2.0)));
		}
	}

	// Token: 0x06000B9E RID: 2974 RVA: 0x0003BE90 File Offset: 0x0003A090
	private void method_6(object sender, EventArgs e)
	{
	}

	// Token: 0x06000B9F RID: 2975 RVA: 0x0003BE94 File Offset: 0x0003A094
	private void method_7(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BA0 RID: 2976 RVA: 0x0003BE98 File Offset: 0x0003A098
	private void method_8(object sender, EventArgs e)
	{
		if (this.MinimumCPS.Int32_2 > this.MaximumCPS.Int32_2)
		{
			this.MinimumCPS.Int32_2 = this.MaximumCPS.Int32_2;
		}
		if (this.FlatTrackBar3.Int32_2 > this.FlatTrackBar5.Int32_2)
		{
			this.FlatTrackBar3.Int32_2 = this.FlatTrackBar5.Int32_2;
		}
		if (this.FlatTrackBar6.Int32_2 > this.FlatTrackBar7.Int32_2)
		{
			this.FlatTrackBar6.Int32_2 = this.FlatTrackBar7.Int32_2;
		}
		IntPtr intPtr = skeet.FindWindowW(null, "CosmicClient ALPHA 1.3.10");
		if (intPtr != IntPtr.Zero)
		{
			skeet.Struct2 @struct = default(skeet.Struct2);
			skeet.GetWindowRect(intPtr, ref @struct);
		}
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
	}

	// Token: 0x06000BA1 RID: 2977 RVA: 0x0003C20C File Offset: 0x0003A40C
	private void method_9(object sender, EventArgs e)
	{
		base.Close();
	}

	// Token: 0x06000BA2 RID: 2978 RVA: 0x0003C214 File Offset: 0x0003A414
	private void method_10(object object_1)
	{
		this.Label5.Text = Conversions.ToString(this.MinimumCPS.Int32_2);
	}

	// Token: 0x06000BA3 RID: 2979 RVA: 0x0003C234 File Offset: 0x0003A434
	private void method_11(object object_1)
	{
		this.Label6.Text = Conversions.ToString(this.MaximumCPS.Int32_2);
	}

	// Token: 0x06000BA4 RID: 2980 RVA: 0x0003C254 File Offset: 0x0003A454
	private void method_12(object object_1)
	{
		checked
		{
			this.int_3++;
			if (this.int_3 != 1)
			{
				this.Timer_7.Stop();
				this.int_3 = 0;
			}
			else
			{
				this.Timer_7.Start();
			}
			if (this.FlatButton12.Color_0 == this.color_0)
			{
				Class1.Class2_0.Arraylist_0.FlatListBox1.method_4("Autoclicker");
			}
		}
	}

	// Token: 0x06000BA5 RID: 2981 RVA: 0x0003C2CC File Offset: 0x0003A4CC
	private void method_13(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BA6 RID: 2982 RVA: 0x0003C2D0 File Offset: 0x0003A4D0
	private void method_14(object object_1)
	{
		base.Opacity = (double)this.FlatTrackBar1.Int32_2 / 100.0;
	}

	// Token: 0x06000BA7 RID: 2983 RVA: 0x0003C2F0 File Offset: 0x0003A4F0
	private void method_15(object sender, EventArgs e)
	{
		this.FlatButton5.Color_0 = this.PictureBox12.BackColor;
		this.FlatButton3.Color_0 = this.PictureBox12.BackColor;
		this.FlatButton4.Color_0 = this.PictureBox12.BackColor;
		this.FlatButton6.Color_0 = this.PictureBox12.BackColor;
		this.FlatButton7.Color_0 = this.PictureBox12.BackColor;
		this.FlatButton8.Color_0 = this.PictureBox12.BackColor;
		this.FlatButton11.Color_0 = this.PictureBox12.BackColor;
		this.FlatButton9.Color_0 = this.PictureBox12.BackColor;
		this.FlatButton6.Hide();
		this.FlatButton7.Hide();
		this.FlatButton8.Hide();
		this.FlatButton6.Show();
		this.FlatButton7.Show();
		this.FlatButton8.Show();
		this.FlatTrackBar1.Color_0 = this.PictureBox12.BackColor;
		this.FlatTrackBar1.Color_1 = this.PictureBox12.BackColor;
		this.MinimumCPS.Color_0 = this.PictureBox12.BackColor;
		this.MaximumCPS.Color_0 = this.PictureBox12.BackColor;
		if (!this.FlatCheckBox21.Boolean_0)
		{
			this.MinimumCPS.Color_1 = this.PictureBox12.BackColor;
		}
		if (!this.FlatCheckBox21.Boolean_0)
		{
			this.MaximumCPS.Color_1 = this.PictureBox12.BackColor;
		}
		if (this.PictureBox2.BackColor == this.color_0)
		{
			this.PictureBox2.BackColor = this.PictureBox12.BackColor;
			this.FlatLabel5.BackColor = this.PictureBox12.BackColor;
			this.PictureBox18.BackColor = this.PictureBox12.BackColor;
		}
		if (this.PictureBox3.BackColor == this.color_0)
		{
			this.PictureBox3.BackColor = this.PictureBox12.BackColor;
			this.FlatLabel6.BackColor = this.PictureBox12.BackColor;
			this.PictureBox19.BackColor = this.PictureBox12.BackColor;
		}
		if (this.PictureBox4.BackColor == this.color_0)
		{
			this.PictureBox4.BackColor = this.PictureBox12.BackColor;
			this.PictureBox20.BackColor = this.PictureBox12.BackColor;
			this.FlatLabel8.BackColor = this.PictureBox12.BackColor;
		}
		if (this.PictureBox5.BackColor == this.color_0)
		{
			this.PictureBox5.BackColor = this.PictureBox12.BackColor;
			this.FlatLabel38.BackColor = this.PictureBox12.BackColor;
			this.PictureBox21.BackColor = this.PictureBox12.BackColor;
		}
		if (this.PictureBox6.BackColor == this.color_0)
		{
			this.PictureBox6.BackColor = this.PictureBox12.BackColor;
			this.FlatLabel40.BackColor = this.PictureBox12.BackColor;
			this.PictureBox22.BackColor = this.PictureBox12.BackColor;
		}
		if (this.FlatButton12.Color_0 == this.color_0)
		{
			this.FlatButton12.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton12.Refresh();
		}
		if (this.FlatButton13.Color_0 == this.color_0)
		{
			this.FlatButton13.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton13.Refresh();
		}
		if (this.FlatButton15.Color_0 == this.color_0)
		{
			this.FlatButton15.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton15.Refresh();
		}
		if (this.FlatButton14.Color_0 == this.color_0)
		{
			this.FlatButton14.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton14.Refresh();
		}
		if (this.FlatButton17.Color_0 == this.color_0)
		{
			this.FlatButton17.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton17.Refresh();
		}
		if (this.FlatButton16.Color_0 == this.color_0)
		{
			this.FlatButton16.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton16.Refresh();
		}
		if (this.FlatButton18.Color_0 == this.color_0)
		{
			this.FlatButton18.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton18.Refresh();
		}
		if (this.FlatButton20.Color_0 == this.color_0)
		{
			this.FlatButton20.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton20.Refresh();
		}
		if (this.FlatButton22.Color_0 == this.color_0)
		{
			this.FlatButton22.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton22.Refresh();
		}
		if (this.FlatButton24.Color_0 == this.color_0)
		{
			this.FlatButton24.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton24.Refresh();
		}
		if (this.FlatButton38.Color_0 == this.color_0)
		{
			this.FlatButton38.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton38.Refresh();
		}
		if (this.FlatButton37.Color_0 == this.color_0)
		{
			this.FlatButton37.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton37.Refresh();
		}
		if (this.FlatButton40.Color_0 == this.color_0)
		{
			this.FlatButton40.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton40.Refresh();
		}
		if (this.FlatButton39.Color_0 == this.color_0)
		{
			this.FlatButton39.Color_0 = this.PictureBox12.BackColor;
			this.FlatButton39.Refresh();
		}
		this.color_0 = this.PictureBox12.BackColor;
		checked
		{
			this.MinimumCPS.Int32_2 = this.MinimumCPS.Int32_2 - 1;
			this.MinimumCPS.Int32_2 = this.MinimumCPS.Int32_2 + 1;
			this.FlatButton2.Color_0 = this.PictureBox12.BackColor;
			this.FlatTrackBar1.Int32_2 = this.FlatTrackBar1.Int32_2 - 1;
			this.FlatTrackBar1.Int32_2 = this.FlatTrackBar1.Int32_2 + 1;
			this.MaximumCPS.Int32_2 = this.MaximumCPS.Int32_2 - 1;
			this.MaximumCPS.Int32_2 = this.MaximumCPS.Int32_2 + 1;
			this.FlatTrackBar4.Color_0 = this.PictureBox12.BackColor;
			this.FlatTrackBar4.Color_1 = this.PictureBox12.BackColor;
			this.FlatTrackBar4.Int32_2 = this.FlatTrackBar4.Int32_2 - 1;
			this.FlatTrackBar4.Int32_2 = this.FlatTrackBar4.Int32_2 + 1;
			this.ThirteenComboBox1.Color_0 = this.PictureBox12.BackColor;
			this.FlatCheckBox1.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox2.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox3.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox5.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox4.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox7.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox6.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox8.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox9.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox10.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox11.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox12.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox13.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox14.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox15.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox16.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox17.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox18.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox19.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox20.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox21.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox22.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox23.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox24.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox25.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox26.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox27.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox29.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox27.Color_1 = this.PictureBox12.BackColor;
			this.FlatTrackBar1.Color_0 = this.PictureBox12.BackColor;
			this.FlatTrackBar1.Color_1 = this.PictureBox12.BackColor;
			this.FlatTrackBar2.Color_0 = this.PictureBox12.BackColor;
			this.FlatTrackBar2.Color_1 = this.PictureBox12.BackColor;
			this.FlatTrackBar3.Color_0 = this.PictureBox12.BackColor;
			this.FlatTrackBar3.Color_1 = this.PictureBox12.BackColor;
			this.FlatTrackBar4.Color_0 = this.PictureBox12.BackColor;
			this.FlatTrackBar4.Color_1 = this.PictureBox12.BackColor;
			this.FlatTrackBar5.Color_0 = this.PictureBox12.BackColor;
			this.FlatTrackBar5.Color_1 = this.PictureBox12.BackColor;
			this.FlatTrackBar6.Color_0 = this.PictureBox12.BackColor;
			this.FlatTrackBar6.Color_1 = this.PictureBox12.BackColor;
			this.FlatTrackBar7.Color_0 = this.PictureBox12.BackColor;
			this.FlatTrackBar7.Color_1 = this.PictureBox12.BackColor;
			this.FlatTrackBar8.Color_0 = this.PictureBox12.BackColor;
			this.FlatTrackBar8.Color_1 = this.PictureBox12.BackColor;
			this.FlatTrackBar1.Refresh();
			this.FlatTrackBar2.Refresh();
			this.FlatTrackBar3.Refresh();
			this.FlatTrackBar4.Refresh();
			this.FlatTrackBar5.Refresh();
			this.FlatTrackBar6.Refresh();
			this.FlatTrackBar7.Refresh();
			this.FlatTrackBar8.Refresh();
			this.ThirteenComboBox1.Color_0 = this.PictureBox12.BackColor;
			this.ThirteenComboBox3.Color_0 = this.PictureBox12.BackColor;
			this.ThirteenComboBox4.Color_0 = this.PictureBox12.BackColor;
			this.ThirteenComboBox5.Color_0 = this.PictureBox12.BackColor;
			this.ThirteenComboBox6.Color_0 = this.PictureBox12.BackColor;
			this.ThirteenComboBox9.Color_0 = this.PictureBox12.BackColor;
			this.ThirteenComboBox10.Color_0 = this.PictureBox12.BackColor;
			this.FlatCheckBox2.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox4.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox5.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox6.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox7.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox8.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox9.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox10.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox11.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox12.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox15.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox16.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox17.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox18.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox19.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox20.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox9.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox8.Color_1 = this.PictureBox12.BackColor;
			this.FlatCheckBox12.Color_1 = this.PictureBox12.BackColor;
			this.FlatButton1.Refresh();
			this.FlatButton2.Refresh();
			this.FlatButton3.Refresh();
			this.FlatButton4.Refresh();
			this.FlatButton5.Refresh();
			this.FlatButton6.Refresh();
			this.FlatButton7.Refresh();
			this.FlatButton8.Refresh();
			this.FlatButton9.Refresh();
			this.FlatButton11.Refresh();
			this.FlatButton12.Refresh();
			this.FlatButton13.Refresh();
			this.FlatButton14.Refresh();
			this.FlatButton15.Refresh();
			this.FlatButton16.Refresh();
			this.FlatButton17.Refresh();
			this.FlatButton18.Refresh();
			this.FlatButton19.Refresh();
			this.FlatButton20.Refresh();
			this.FlatButton21.Refresh();
			this.FlatButton22.Refresh();
			this.FlatButton23.Refresh();
			this.FlatButton24.Refresh();
			this.FlatButton25.Refresh();
			this.FlatButton26.Refresh();
			this.FlatButton27.Refresh();
			this.FlatButton28.Refresh();
			this.FlatButton29.Refresh();
			this.FlatButton30.Refresh();
		}
	}

	// Token: 0x06000BA8 RID: 2984 RVA: 0x0003D248 File Offset: 0x0003B448
	private void method_16(object object_1)
	{
		if (this.FlatCheckBox4.Boolean_0)
		{
			Class1.Class2_0.Arraylist_0.Show();
		}
		else
		{
			Class1.Class2_0.Arraylist_0.Hide();
		}
	}

	// Token: 0x06000BA9 RID: 2985 RVA: 0x0003D278 File Offset: 0x0003B478
	private void method_17(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BAA RID: 2986 RVA: 0x0003D27C File Offset: 0x0003B47C
	private void method_18(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BAB RID: 2987 RVA: 0x0003D280 File Offset: 0x0003B480
	private void method_19(object sender, MouseEventArgs e)
	{
		this.graphics_0.CopyFromScreen(Cursor.Position, Point.Empty, this.bitmap_0.Size);
		this.PictureBox13.BackColor = this.bitmap_0.GetPixel(0, 0);
		this.PictureBox12.BackColor = this.bitmap_0.GetPixel(0, 0);
		this.FlatTextBox1.System.Windows.Forms.Control.Text = Conversions.ToString(this.bitmap_0.GetPixel(0, 0).R);
		this.FlatTextBox2.System.Windows.Forms.Control.Text = Conversions.ToString(this.bitmap_0.GetPixel(0, 0).G);
		this.FlatTextBox3.System.Windows.Forms.Control.Text = Conversions.ToString(this.bitmap_0.GetPixel(0, 0).B);
		this.FlatTextBox4.System.Windows.Forms.Control.Text = ColorTranslator.ToHtml(this.bitmap_0.GetPixel(0, 0));
		this.FlatTextBox5.System.Windows.Forms.Control.Text = string.Concat(new string[]
		{
			Conversions.ToString(this.bitmap_0.GetPixel(0, 0).R),
			",",
			Conversions.ToString(this.bitmap_0.GetPixel(0, 0).G),
			",",
			Conversions.ToString(this.bitmap_0.GetPixel(0, 0).B)
		});
		this.method_27(50.0);
		Cursor.Clip = Cursor.Clip;
		Cursor.Current = Cursors.Cross;
		if (e.Button == MouseButtons.Left)
		{
			this.PictureBox13.Location = checked(new Point(e.X + 34, e.Y + 77));
		}
	}

	// Token: 0x06000BAC RID: 2988 RVA: 0x0003D43C File Offset: 0x0003B63C
	private void method_20(object sender, EventArgs e)
	{
		this.Timer_6.Stop();
	}

	// Token: 0x06000BAD RID: 2989 RVA: 0x0003D44C File Offset: 0x0003B64C
	private void method_21(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BAE RID: 2990 RVA: 0x0003D450 File Offset: 0x0003B650
	private void method_22(object object_1)
	{
	}

	// Token: 0x06000BAF RID: 2991 RVA: 0x0003D454 File Offset: 0x0003B654
	private void method_23(Point point_5)
	{
		new Thread(new ThreadStart(this.method_26));
		this.bool_5 = true;
		this.bool_6 = true;
		Thread thread = new Thread(new ThreadStart(this.method_25));
		if (this.Timer_7.Enabled & this.FlatButton38.Color_0 == this.color_0)
		{
			if (thread.IsAlive)
			{
				thread.Join();
			}
			else
			{
				thread.Start();
			}
		}
		else
		{
			thread.Abort();
		}
	}

	// Token: 0x06000BB0 RID: 2992 RVA: 0x0003D4D8 File Offset: 0x0003B6D8
	private void method_24(Point point_5)
	{
		this.bool_5 = false;
		if (!this.bool_7)
		{
			this.bool_6 = false;
		}
		this.bool_7 = false;
	}

	// Token: 0x06000BB1 RID: 2993 RVA: 0x0003D4FC File Offset: 0x0003B6FC
	public void method_25()
	{
		if (this.method_0().StartsWith(this.FlatTextBox6.System.Windows.Forms.Control.Text))
		{
			VBMath.Randomize();
			skeet.GStruct2 gstruct;
			skeet.GetCursorPos(ref gstruct);
			Cursor.Position = checked(new Point(gstruct.int_0 + this.random_0.Next(0 - this.FlatTrackBar4.Int32_2, this.FlatTrackBar4.Int32_2), gstruct.int_1 + this.random_0.Next(0 - this.FlatTrackBar4.Int32_2, this.FlatTrackBar4.Int32_2)));
		}
	}

	// Token: 0x06000BB2 RID: 2994 RVA: 0x0003D58C File Offset: 0x0003B78C
	public void method_26()
	{
		VBMath.Randomize();
		skeet.mouse_event(2, 0, 0, 2, skeet.GetMessageExtraInfo());
		Thread.Sleep(this.random_0.Next(50, 70));
		this.bool_7 = true;
		skeet.mouse_event(4, 0, 0, 2, skeet.GetMessageExtraInfo());
		Thread.Sleep(this.random_0.Next(50, 70));
	}

	// Token: 0x06000BB3 RID: 2995 RVA: 0x0003D5EC File Offset: 0x0003B7EC
	public void method_27(double double_0)
	{
		DateAndTime.Now.AddSeconds(1.1574074074074073E-05);
		DateTime t = DateAndTime.Now.AddSeconds(1.1574074074074073E-05).AddSeconds(double_0);
		while (DateTime.Compare(DateAndTime.Now, t) <= 0)
		{
			Application.DoEvents();
		}
	}

	// Token: 0x06000BB4 RID: 2996 RVA: 0x0003D648 File Offset: 0x0003B848
	public void method_28()
	{
		Class1.Class0_0.Audio.Play(Class5.UnmanagedMemoryStream_15, AudioPlayMode.Background);
	}

	// Token: 0x06000BB5 RID: 2997 RVA: 0x0003D660 File Offset: 0x0003B860
	public void method_29()
	{
		Class1.Class0_0.Audio.Play(Class5.UnmanagedMemoryStream_14, AudioPlayMode.Background);
	}

	// Token: 0x06000BB6 RID: 2998 RVA: 0x0003D678 File Offset: 0x0003B878
	public void method_30()
	{
		Class1.Class0_0.Audio.Play(Class5.UnmanagedMemoryStream_16, AudioPlayMode.Background);
	}

	// Token: 0x06000BB7 RID: 2999 RVA: 0x0003D690 File Offset: 0x0003B890
	public void method_31()
	{
		Class1.Class0_0.Audio.Play(Class5.UnmanagedMemoryStream_5, AudioPlayMode.Background);
	}

	// Token: 0x06000BB8 RID: 3000 RVA: 0x0003D6A8 File Offset: 0x0003B8A8
	public void method_32()
	{
		Class1.Class0_0.Audio.Play(Class5.UnmanagedMemoryStream_3, AudioPlayMode.Background);
	}

	// Token: 0x06000BB9 RID: 3001 RVA: 0x0003D6C0 File Offset: 0x0003B8C0
	public void method_33()
	{
		Class1.Class0_0.Audio.Play(Class5.UnmanagedMemoryStream_7, AudioPlayMode.Background);
	}

	// Token: 0x06000BBA RID: 3002 RVA: 0x0003D6D8 File Offset: 0x0003B8D8
	public void method_34()
	{
		Class1.Class0_0.Audio.Play(Class5.UnmanagedMemoryStream_6, AudioPlayMode.Background);
	}

	// Token: 0x06000BBB RID: 3003 RVA: 0x0003D6F0 File Offset: 0x0003B8F0
	public void method_35()
	{
		Class1.Class0_0.Audio.Play(Class5.UnmanagedMemoryStream_9, AudioPlayMode.Background);
	}

	// Token: 0x06000BBC RID: 3004 RVA: 0x0003D708 File Offset: 0x0003B908
	private void method_36(object sender, EventArgs e)
	{
		checked
		{
			int maxValue = (int)Math.Round(1000.0 / unchecked((double)this.MinimumCPS.Int32_2 + (double)this.MaximumCPS.Int32_2 * 0.2));
			int minValue = (int)Math.Round(1000.0 / unchecked((double)this.MinimumCPS.Int32_2 + (double)this.MaximumCPS.Int32_2 * 0.48));
			Thread thread = new Thread(new ThreadStart(this.method_26));
			Thread thread2 = new Thread(new ThreadStart(this.method_28));
			Thread thread3 = new Thread(new ThreadStart(this.method_29));
			Thread thread4 = new Thread(new ThreadStart(this.method_31));
			Thread thread5 = new Thread(new ThreadStart(this.method_30));
			Thread thread6 = new Thread(new ThreadStart(this.method_32));
			Thread thread7 = new Thread(new ThreadStart(this.method_33));
			Thread thread8 = new Thread(new ThreadStart(this.method_34));
			Thread thread9 = new Thread(new ThreadStart(this.method_35));
			this.Timer_7.Interval = 100;
			if (!this.FlatCheckBox21.Boolean_0 && this.MinimumCPS.Enabled)
			{
				this.Timer_7.Interval = this.random_0.Next(minValue, maxValue);
			}
			MouseButtons mouseButtons = Control.MouseButtons;
			if (mouseButtons != MouseButtons.Right)
			{
				if (!this.FlatCheckBox8.Boolean_0)
				{
					if (!this.bool_6)
					{
						thread.Abort();
					}
					else if (!thread.IsAlive)
					{
						if (this.FlatButton40.Color_0 == this.color_0)
						{
							if (Operators.CompareString(this.ThirteenComboBox1.Text, "Default Sound", false) == 0)
							{
								thread5.Start();
							}
							if (Operators.CompareString(this.ThirteenComboBox1.Text, "Logitech G502", false) == 0)
							{
								thread3.Start();
							}
							if (Operators.CompareString(this.ThirteenComboBox1.Text, "Logitech GPro", false) == 0)
							{
								thread4.Start();
							}
							if (Operators.CompareString(this.ThirteenComboBox1.Text, "Logitech G303", false) == 0)
							{
								thread6.Start();
							}
							if (Operators.CompareString(this.ThirteenComboBox1.Text, "Microsoft Mouse", false) == 0)
							{
								thread7.Start();
							}
							if (Operators.CompareString(this.ThirteenComboBox1.Text, "HP Mouse", false) == 0)
							{
								thread8.Start();
							}
							if (Operators.CompareString(this.ThirteenComboBox1.Text, "Non-Brand Mouse", false) == 0)
							{
								thread9.Start();
							}
							if (Operators.CompareString(this.ThirteenComboBox1.Text, "Razer Deathadder", false) == 0)
							{
								thread2.Start();
							}
						}
						if (this.FlatCheckBox5.Boolean_0)
						{
							MouseButtons mouseButtons2 = Control.MouseButtons;
							if (mouseButtons2 != MouseButtons.Right)
							{
								thread.Start();
							}
						}
						else
						{
							thread.Start();
						}
					}
					else
					{
						thread.Join();
					}
				}
				else if (this.method_0().StartsWith(this.FlatTextBox6.System.Windows.Forms.Control.Text))
				{
					if (this.bool_6)
					{
						if (!thread.IsAlive)
						{
							if (this.FlatButton40.Color_0 == this.color_0)
							{
								if (Operators.CompareString(this.ThirteenComboBox1.Text, "Default Sound", false) == 0)
								{
									thread5.Start();
								}
								if (Operators.CompareString(this.ThirteenComboBox1.Text, "Logitech G502", false) == 0)
								{
									thread3.Start();
								}
								if (Operators.CompareString(this.ThirteenComboBox1.Text, "Logitech GPro", false) == 0)
								{
									thread4.Start();
								}
								if (Operators.CompareString(this.ThirteenComboBox1.Text, "Logitech G303", false) == 0)
								{
									thread6.Start();
								}
								if (Operators.CompareString(this.ThirteenComboBox1.Text, "Microsoft Mouse", false) == 0)
								{
									thread7.Start();
								}
								if (Operators.CompareString(this.ThirteenComboBox1.Text, "HP Mouse", false) == 0)
								{
									thread8.Start();
								}
								if (Operators.CompareString(this.ThirteenComboBox1.Text, "Non-Brand Mouse", false) == 0)
								{
									thread9.Start();
								}
								if (Operators.CompareString(this.ThirteenComboBox1.Text, "Razer Deathadder", false) == 0)
								{
									thread2.Start();
								}
							}
							if (!this.FlatCheckBox5.Boolean_0)
							{
								thread.Start();
							}
							else
							{
								MouseButtons mouseButtons3 = Control.MouseButtons;
								if (mouseButtons3 != MouseButtons.Right)
								{
									thread.Start();
								}
							}
						}
						else
						{
							thread.Join();
						}
					}
					else
					{
						thread.Abort();
					}
				}
			}
		}
	}

	// Token: 0x06000BBD RID: 3005 RVA: 0x0003DBB4 File Offset: 0x0003BDB4
	private void method_37(object sender, MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Left)
		{
			base.Location += checked(new Size(e.X - this.point_1.X, e.Y - this.point_1.Y));
		}
		else
		{
			this.point_1 = e.Location;
		}
	}

	// Token: 0x06000BBE RID: 3006 RVA: 0x0003DC18 File Offset: 0x0003BE18
	private void method_38(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BBF RID: 3007 RVA: 0x0003DC1C File Offset: 0x0003BE1C
	private void method_39(object sender, MouseEventArgs e)
	{
		if (e.Button != MouseButtons.Left)
		{
			this.point_2 = e.Location;
		}
		else
		{
			base.Location += checked(new Size(e.X - this.point_2.X, e.Y - this.point_2.Y));
		}
	}

	// Token: 0x06000BC0 RID: 3008 RVA: 0x0003DC80 File Offset: 0x0003BE80
	private void method_40(object sender, MouseEventArgs e)
	{
		if (e.Button == MouseButtons.Left)
		{
			base.Location += checked(new Size(e.X - this.point_3.X, e.Y - this.point_3.Y));
		}
		else
		{
			this.point_3 = e.Location;
		}
	}

	// Token: 0x06000BC1 RID: 3009 RVA: 0x0003DCE4 File Offset: 0x0003BEE4
	private void method_41(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BC2 RID: 3010 RVA: 0x0003DCE8 File Offset: 0x0003BEE8
	private void method_42(object sender, MouseEventArgs e)
	{
		if (e.Button != MouseButtons.Left)
		{
			this.point_4 = e.Location;
		}
		else
		{
			base.Location += checked(new Size(e.X - this.point_4.X, e.Y - this.point_4.Y));
		}
	}

	// Token: 0x06000BC3 RID: 3011 RVA: 0x0003DD4C File Offset: 0x0003BF4C
	private void method_43(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BC4 RID: 3012 RVA: 0x0003DD50 File Offset: 0x0003BF50
	private void method_44(object object_1)
	{
		Interaction.MsgBox("Warning: With that feature turned off, you have more chances of triggering servers AntiCheats!", MsgBoxStyle.Information, null);
	}

	// Token: 0x06000BC5 RID: 3013 RVA: 0x0003DD60 File Offset: 0x0003BF60
	private void method_45(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BC6 RID: 3014 RVA: 0x0003DD64 File Offset: 0x0003BF64
	private void method_46(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BC7 RID: 3015 RVA: 0x0003DD68 File Offset: 0x0003BF68
	private void method_47(object object_1)
	{
		this.FlatCheckBox8.Boolean_0 = true;
	}

	// Token: 0x06000BC8 RID: 3016 RVA: 0x0003DD78 File Offset: 0x0003BF78
	private void method_48(object sender, EventArgs e)
	{
		checked
		{
			if (this.int_2 < 255)
			{
				this.int_2++;
				this.PictureBox14.BackColor = Color.FromArgb(255, this.int_2, this.int_2);
			}
			else
			{
				this.Timer_9.Start();
				this.Timer_6.Stop();
			}
		}
	}

	// Token: 0x06000BC9 RID: 3017 RVA: 0x0003DDDC File Offset: 0x0003BFDC
	private void method_49(object sender, EventArgs e)
	{
		checked
		{
			if (this.int_2 <= 30)
			{
				this.Timer_6.Start();
				this.Timer_9.Stop();
			}
			else
			{
				this.int_2--;
				this.PictureBox14.BackColor = Color.FromArgb(255, this.int_2, this.int_2);
			}
		}
	}

	// Token: 0x06000BCA RID: 3018 RVA: 0x0003DE3C File Offset: 0x0003C03C
	private void method_50(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BCB RID: 3019 RVA: 0x0003DE40 File Offset: 0x0003C040
	private void method_51(object sender, EventArgs e)
	{
		this.Timer_30.Stop();
		this.Timer_1.Start();
	}

	// Token: 0x06000BCC RID: 3020 RVA: 0x0003DE58 File Offset: 0x0003C058
	private void method_52(object sender, EventArgs e)
	{
		Interaction.MsgBox("Settings successfully loaded!", MsgBoxStyle.Information, null);
		this.FlatCheckBox6.Boolean_0 = true;
		this.FlatCheckBox7.Boolean_0 = true;
		this.MaximumCPS.Int32_2 = 12;
		this.MinimumCPS.Int32_2 = 9;
		this.Label6.Text = Conversions.ToString(12);
		this.Label5.Text = Conversions.ToString(9);
	}

	// Token: 0x06000BCD RID: 3021 RVA: 0x0003DECC File Offset: 0x0003C0CC
	private void method_53(object sender, EventArgs e)
	{
		Interaction.MsgBox("Settings successfully loaded!", MsgBoxStyle.Information, null);
		this.FlatCheckBox6.Boolean_0 = true;
		this.FlatCheckBox7.Boolean_0 = true;
		this.MaximumCPS.Int32_2 = 13;
		this.MinimumCPS.Int32_2 = 8;
		this.Label6.Text = Conversions.ToString(13);
		this.Label5.Text = Conversions.ToString(8);
	}

	// Token: 0x06000BCE RID: 3022 RVA: 0x0003DF3C File Offset: 0x0003C13C
	private void method_54(object sender, EventArgs e)
	{
		Interaction.MsgBox("Settings successfully loaded!", MsgBoxStyle.Information, null);
		this.FlatCheckBox6.Boolean_0 = true;
		this.FlatCheckBox7.Boolean_0 = true;
		this.MaximumCPS.Int32_2 = 14;
		this.MinimumCPS.Int32_2 = 11;
		this.Label6.Text = Conversions.ToString(14);
		this.Label5.Text = Conversions.ToString(11);
	}

	// Token: 0x06000BCF RID: 3023 RVA: 0x0003DFB0 File Offset: 0x0003C1B0
	private void method_55(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BD0 RID: 3024 RVA: 0x0003DFB4 File Offset: 0x0003C1B4
	private void method_56(object sender, EventArgs e)
	{
		Interaction.MsgBox("Under Developement.", MsgBoxStyle.OkOnly, null);
	}

	// Token: 0x06000BD1 RID: 3025 RVA: 0x0003DFC4 File Offset: 0x0003C1C4
	private void method_57(object object_1)
	{
		if (this.FlatCheckBox18.Boolean_0)
		{
			base.TopMost = true;
		}
		else
		{
			base.TopMost = false;
		}
	}

	// Token: 0x06000BD2 RID: 3026 RVA: 0x0003DFE4 File Offset: 0x0003C1E4
	private void method_58(object object_1)
	{
		if (this.FlatCheckBox17.Boolean_0)
		{
			base.ShowInTaskbar = false;
		}
		else
		{
			base.ShowInTaskbar = true;
		}
	}

	// Token: 0x06000BD3 RID: 3027 RVA: 0x0003E004 File Offset: 0x0003C204
	[MethodImpl(MethodImplOptions.NoOptimization)]
	private void method_59(object sender, EventArgs e)
	{
		OpenFileDialog openFileDialog = new OpenFileDialog();
		openFileDialog.InitialDirectory = "c:\\";
		openFileDialog.Filter = "Air Files (**)|";
		openFileDialog.FilterIndex = 2;
		openFileDialog.RestoreDirectory = true;
		Thread.Sleep(1000);
		if (openFileDialog.ShowDialog() == DialogResult.OK)
		{
			if (Operators.CompareString(FileSystem.Dir(openFileDialog.FileName, FileAttribute.Normal), "", false) == 0)
			{
				Interaction.MsgBox("File Not Found", MsgBoxStyle.Critical, null);
			}
			else
			{
				Interaction.MsgBox(" Imported " + openFileDialog.FileName, MsgBoxStyle.Information, null);
			}
			this.FlatTextBox7.System.Windows.Forms.Control.Text = openFileDialog.FileName;
			string text = File.ReadAllText(this.FlatTextBox7.System.Windows.Forms.Control.Text);
			if (text.Contains("minval:1"))
			{
				this.MinimumCPS.Int32_2 = 1;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:2"))
			{
				this.MinimumCPS.Int32_2 = 2;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:3"))
			{
				this.MinimumCPS.Int32_2 = 3;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:4"))
			{
				this.MinimumCPS.Int32_2 = 4;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:5"))
			{
				this.MinimumCPS.Int32_2 = 5;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:6"))
			{
				this.MinimumCPS.Int32_2 = 6;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:7"))
			{
				this.MinimumCPS.Int32_2 = 7;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:8"))
			{
				this.MinimumCPS.Int32_2 = 8;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:9"))
			{
				this.MinimumCPS.Int32_2 = 9;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:10"))
			{
				this.MinimumCPS.Int32_2 = 10;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:11"))
			{
				this.MinimumCPS.Int32_2 = 11;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:12"))
			{
				this.MinimumCPS.Int32_2 = 12;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:13"))
			{
				this.MinimumCPS.Int32_2 = 13;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:14"))
			{
				this.MinimumCPS.Int32_2 = 14;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:15"))
			{
				this.MinimumCPS.Int32_2 = 15;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:16"))
			{
				this.MinimumCPS.Int32_2 = 16;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:17"))
			{
				this.MinimumCPS.Int32_2 = 17;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:18"))
			{
				this.MinimumCPS.Int32_2 = 18;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:19"))
			{
				this.MinimumCPS.Int32_2 = 19;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("minval:20"))
			{
				this.MinimumCPS.Int32_2 = 20;
				this.MinimumCPS.Refresh();
			}
			if (text.Contains("maxval:1"))
			{
				this.MaximumCPS.Int32_2 = 1;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:2"))
			{
				this.MaximumCPS.Int32_2 = 2;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:3"))
			{
				this.MaximumCPS.Int32_2 = 3;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:4"))
			{
				this.MaximumCPS.Int32_2 = 4;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:5"))
			{
				this.MaximumCPS.Int32_2 = 5;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:6"))
			{
				this.MaximumCPS.Int32_2 = 6;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:7"))
			{
				this.MaximumCPS.Int32_2 = 7;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:8"))
			{
				this.MinimumCPS.Int32_2 = 8;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:9"))
			{
				this.MaximumCPS.Int32_2 = 9;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:10"))
			{
				this.MaximumCPS.Int32_2 = 10;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:11"))
			{
				this.MaximumCPS.Int32_2 = 11;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:12"))
			{
				this.MaximumCPS.Int32_2 = 12;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:13"))
			{
				this.MaximumCPS.Int32_2 = 13;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:14"))
			{
				this.MaximumCPS.Int32_2 = 14;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:15"))
			{
				this.MaximumCPS.Int32_2 = 15;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:16"))
			{
				this.MaximumCPS.Int32_2 = 16;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:17"))
			{
				this.MaximumCPS.Int32_2 = 17;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:18"))
			{
				this.MaximumCPS.Int32_2 = 18;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("maxval:19"))
			{
				this.MaximumCPS.Int32_2 = 19;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("minval:20"))
			{
				this.MaximumCPS.Int32_2 = 20;
				this.MaximumCPS.Refresh();
			}
			if (text.Contains("inv.nn"))
			{
				this.FlatCheckBox5.Boolean_0 = false;
			}
			if (text.Contains("inv.ui"))
			{
				this.FlatCheckBox5.Boolean_0 = true;
			}
			if (!text.Contains("jit.nn"))
			{
			}
			if (!text.Contains("jit.ui"))
			{
			}
			if (text.Contains("rndm.nn"))
			{
				this.FlatCheckBox6.Boolean_0 = false;
			}
			if (text.Contains("rndm.ui"))
			{
				this.FlatCheckBox6.Boolean_0 = true;
			}
			if (text.Contains("smrt.nn"))
			{
				this.FlatCheckBox7.Boolean_0 = false;
			}
			if (text.Contains("smrt.ui"))
			{
				this.FlatCheckBox7.Boolean_0 = true;
			}
			if (text.Contains("pin.nn"))
			{
				this.FlatCheckBox4.Boolean_0 = false;
			}
			if (text.Contains("pin.ui"))
			{
				this.FlatCheckBox4.Boolean_0 = true;
			}
			if (text.Contains("ovr.ui"))
			{
				this.FlatCheckBox2.Boolean_0 = true;
			}
			if (text.Contains("ovr.nn"))
			{
				this.FlatCheckBox2.Boolean_0 = false;
			}
			if (text.Contains("cachcach.ui"))
			{
				this.FlatCheckBox17.Boolean_0 = true;
			}
			if (text.Contains("cachcach.nn"))
			{
				this.FlatCheckBox17.Boolean_0 = false;
			}
			if (text.Contains("cpsdis.ui"))
			{
				this.FlatCheckBox20.Boolean_0 = true;
			}
			if (text.Contains("cpsdis.nn"))
			{
				this.FlatCheckBox20.Boolean_0 = false;
			}
			if (text.Contains("pce.ui"))
			{
				this.FlatCheckBox19.Boolean_0 = true;
			}
			if (text.Contains("pce.nn"))
			{
				this.FlatCheckBox19.Boolean_0 = false;
			}
			if (text.Contains("top.ui"))
			{
				this.FlatCheckBox18.Boolean_0 = true;
			}
			if (text.Contains("top.nn"))
			{
				this.FlatCheckBox18.Boolean_0 = false;
			}
		}
	}

	// Token: 0x06000BD4 RID: 3028 RVA: 0x0003E850 File Offset: 0x0003CA50
	private void method_60(object sender, EventArgs e)
	{
		this.MinimumCPS.Int32_2 = 8;
		this.MinimumCPS.Refresh();
		this.MaximumCPS.Int32_2 = 11;
		this.MaximumCPS.Refresh();
		this.FlatCheckBox5.Boolean_0 = false;
		this.FlatCheckBox6.Boolean_0 = true;
		this.FlatCheckBox7.Boolean_0 = true;
		this.FlatCheckBox8.Boolean_0 = true;
		this.FlatTrackBar4.Int32_2 = 40;
		Thread.Sleep(2000);
		Interaction.MsgBox("Settings have been restored", MsgBoxStyle.OkOnly, null);
	}

	// Token: 0x06000BD5 RID: 3029 RVA: 0x0003E8E0 File Offset: 0x0003CAE0
	private void method_61(object sender, EventArgs e)
	{
		Interaction.MsgBox("DO NOT CHANGE THE LOCATION OR  the settings file name if you care about bypassing screenshares", MsgBoxStyle.OkOnly, null);
		string path = Conversions.ToString(Operators.ConcatenateObject(Interaction.Environ("USERPROFILE") + "\\desktop\\", this.method_1()));
		StreamWriter streamWriter = new StreamWriter(path);
		if (this.MinimumCPS.Int32_2 == 1)
		{
			streamWriter.Write("minval:1");
		}
		if (this.MinimumCPS.Int32_2 == 2)
		{
			streamWriter.Write("minval:2");
		}
		if (this.MinimumCPS.Int32_2 == 3)
		{
			streamWriter.Write("minval:3");
		}
		if (this.MinimumCPS.Int32_2 == 4)
		{
			streamWriter.Write("minval:4");
			streamWriter.Close();
		}
		if (this.MinimumCPS.Int32_2 == 5)
		{
			streamWriter.Write("minval:5");
		}
		if (this.MinimumCPS.Int32_2 == 6)
		{
			streamWriter.Write("minval:6");
		}
		if (this.MinimumCPS.Int32_2 == 7)
		{
			streamWriter.Write("minval:7");
		}
		if (this.MinimumCPS.Int32_2 == 8)
		{
			streamWriter.Write("minval:8");
		}
		if (this.MinimumCPS.Int32_2 == 9)
		{
			streamWriter.Write("minval:9");
		}
		if (this.MinimumCPS.Int32_2 == 10)
		{
			streamWriter.Write("minval:10");
		}
		if (this.MinimumCPS.Int32_2 == 11)
		{
			streamWriter.Write("minval:11");
		}
		if (this.MinimumCPS.Int32_2 == 12)
		{
			streamWriter.Write("minval:12");
		}
		if (this.MinimumCPS.Int32_2 == 13)
		{
			streamWriter.Write("minval:13");
		}
		if (this.MinimumCPS.Int32_2 == 14)
		{
			streamWriter.Write("minval:14");
			streamWriter.Close();
		}
		if (this.MinimumCPS.Int32_2 == 15)
		{
			streamWriter.Write("minval:15");
		}
		if (this.MinimumCPS.Int32_2 == 16)
		{
			streamWriter.Write("minval:16");
		}
		if (this.MinimumCPS.Int32_2 == 17)
		{
			streamWriter.Write("minval:17");
		}
		if (this.MinimumCPS.Int32_2 == 18)
		{
			streamWriter.Write("minval:18");
		}
		if (this.MinimumCPS.Int32_2 == 19)
		{
			streamWriter.Write("minval:19");
		}
		if (this.MinimumCPS.Int32_2 == 20)
		{
			streamWriter.Write("minval:20");
		}
		if (this.MaximumCPS.Int32_2 == 1)
		{
			streamWriter.Write("maxval:1");
		}
		if (this.MaximumCPS.Int32_2 == 2)
		{
			streamWriter.Write("maxval:2");
		}
		if (this.MaximumCPS.Int32_2 == 3)
		{
			streamWriter.Write("maxval:3");
		}
		if (this.MaximumCPS.Int32_2 == 4)
		{
			streamWriter.Write("maxval:4");
			streamWriter.Close();
		}
		if (this.MaximumCPS.Int32_2 == 5)
		{
			streamWriter.Write("maxval:5");
		}
		if (this.MaximumCPS.Int32_2 == 6)
		{
			streamWriter.Write("maxval:6");
		}
		if (this.MaximumCPS.Int32_2 == 7)
		{
			streamWriter.Write("maxval:7");
		}
		if (this.MaximumCPS.Int32_2 == 8)
		{
			streamWriter.Write("maxval:8");
		}
		if (this.MaximumCPS.Int32_2 == 9)
		{
			streamWriter.Write("maxval:9");
		}
		if (this.MaximumCPS.Int32_2 == 10)
		{
			streamWriter.Write("maxval:10");
		}
		if (this.MaximumCPS.Int32_2 == 11)
		{
			streamWriter.Write("maxval:11");
		}
		if (this.MaximumCPS.Int32_2 == 12)
		{
			streamWriter.Write("maxval:12");
		}
		if (this.MaximumCPS.Int32_2 == 13)
		{
			streamWriter.Write("maxval:13");
		}
		if (this.MaximumCPS.Int32_2 == 14)
		{
			streamWriter.Write("maxval:14");
			streamWriter.Close();
		}
		if (this.MaximumCPS.Int32_2 == 15)
		{
			streamWriter.Write("maxval:15");
		}
		if (this.MaximumCPS.Int32_2 == 16)
		{
			streamWriter.Write("maxval:16");
		}
		if (this.MaximumCPS.Int32_2 == 17)
		{
			streamWriter.Write("maxval:17");
		}
		if (this.MaximumCPS.Int32_2 == 18)
		{
			streamWriter.Write("maxval:18");
		}
		if (this.MinimumCPS.Int32_2 == 19)
		{
			streamWriter.Write("maxval:19");
		}
		if (this.MaximumCPS.Int32_2 == 20)
		{
			streamWriter.Write("maxval:20");
		}
		if (!this.FlatCheckBox5.Boolean_0)
		{
			streamWriter.Write("inv.nn");
		}
		if (this.FlatCheckBox5.Boolean_0)
		{
			streamWriter.Write("inv.ui");
		}
		if (!this.FlatCheckBox6.Boolean_0)
		{
			streamWriter.Write("rndm.nn");
		}
		if (this.FlatCheckBox6.Boolean_0)
		{
			streamWriter.Write("rndm.ui");
		}
		if (!this.FlatCheckBox7.Boolean_0)
		{
			streamWriter.Write("smrt.nn");
		}
		if (this.FlatCheckBox7.Boolean_0)
		{
			streamWriter.Write("smrt.ui");
		}
		if (!this.FlatCheckBox4.Boolean_0)
		{
			streamWriter.Write("pin.nn");
		}
		if (this.FlatCheckBox4.Boolean_0)
		{
			streamWriter.Write("pin.ui");
		}
		if (this.FlatCheckBox2.Boolean_0)
		{
			streamWriter.Write("ovr.ui");
		}
		if (!this.FlatCheckBox2.Boolean_0)
		{
			streamWriter.Write("ovr.nn");
		}
		if (this.FlatCheckBox17.Boolean_0)
		{
			streamWriter.Write("cachcach.ui");
		}
		if (!this.FlatCheckBox17.Boolean_0)
		{
			streamWriter.Write("cachcach.nn");
		}
		if (this.FlatCheckBox20.Boolean_0)
		{
			streamWriter.Write("cpsdis.ui");
		}
		if (!this.FlatCheckBox20.Boolean_0)
		{
			streamWriter.Write("cpsdis.nn");
		}
		if (this.FlatCheckBox19.Boolean_0)
		{
			streamWriter.Write("pce.ui");
		}
		if (!this.FlatCheckBox19.Boolean_0)
		{
			streamWriter.Write("pce.nn");
		}
		if (this.FlatCheckBox18.Boolean_0)
		{
			streamWriter.Write("top.ui");
		}
		if (!this.FlatCheckBox18.Boolean_0)
		{
			streamWriter.Write("top.nn");
		}
		streamWriter.Close();
	}

	// Token: 0x06000BD6 RID: 3030 RVA: 0x0003EF60 File Offset: 0x0003D160
	private void method_62(object sender, EventArgs e)
	{
		if (skeet.GetAsyncKeyState_1(65) != 0)
		{
		}
	}

	// Token: 0x06000BD7 RID: 3031 RVA: 0x0003EF70 File Offset: 0x0003D170
	private void method_63(object object_1)
	{
	}

	// Token: 0x06000BD8 RID: 3032 RVA: 0x0003EF74 File Offset: 0x0003D174
	private void method_64(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BD9 RID: 3033 RVA: 0x0003EF78 File Offset: 0x0003D178
	private void method_65(object sender, EventArgs e)
	{
		Interaction.MsgBox("Settings successfully loaded!", MsgBoxStyle.Information, null);
		this.FlatCheckBox6.Boolean_0 = true;
		this.FlatCheckBox7.Boolean_0 = true;
		this.MaximumCPS.Int32_2 = 12;
		this.MinimumCPS.Int32_2 = 9;
		this.Label6.Text = Conversions.ToString(12);
		this.Label5.Text = Conversions.ToString(9);
	}

	// Token: 0x06000BDA RID: 3034 RVA: 0x0003EFEC File Offset: 0x0003D1EC
	private void method_66(object sender, EventArgs e)
	{
		Interaction.MsgBox("Settings successfully loaded!", MsgBoxStyle.Information, null);
		this.FlatCheckBox6.Boolean_0 = true;
		this.FlatCheckBox7.Boolean_0 = true;
		this.MaximumCPS.Int32_2 = 14;
		this.MinimumCPS.Int32_2 = 11;
		this.Label6.Text = Conversions.ToString(14);
		this.Label5.Text = Conversions.ToString(11);
	}

	// Token: 0x06000BDB RID: 3035 RVA: 0x0003F060 File Offset: 0x0003D260
	private void method_67(object sender, EventArgs e)
	{
		Interaction.MsgBox("Settings successfully loaded!", MsgBoxStyle.Information, null);
		this.FlatCheckBox6.Boolean_0 = true;
		this.FlatCheckBox7.Boolean_0 = true;
		this.MaximumCPS.Int32_2 = 13;
		this.MinimumCPS.Int32_2 = 9;
		this.Label6.Text = Conversions.ToString(13);
		this.Label5.Text = Conversions.ToString(9);
	}

	// Token: 0x06000BDC RID: 3036 RVA: 0x0003F0D4 File Offset: 0x0003D2D4
	private void method_68(object sender, EventArgs e)
	{
	}

	// Token: 0x06000BDD RID: 3037 RVA: 0x0003F0D8 File Offset: 0x0003D2D8
	private void skeet_Closing(object sender, CancelEventArgs e)
	{
		this.Timer_1.Start();
		e.Cancel = true;
	}

	// Token: 0x06000BDE RID: 3038 RVA: 0x0003F0EC File Offset: 0x0003D2EC
	private void method_69(object object_1)
	{
		Interaction.MsgBox("Warning: With that feature turned off, you will have more chances of getting banned In a screenshare!", MsgBoxStyle.Information, null);
	}

	// Token: 0x06000BDF RID: 3039 RVA: 0x0003F0FC File Offset: 0x0003D2FC
	private void method_70(object sender, EventArgs e)
	{
		if (Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "a", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "z", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "e", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "r", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "t", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "y", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "u", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "i", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "o", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "p", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "q", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "s", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "d", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "f", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "g", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "h", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "j", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "k", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "l", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "m", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "w", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "x", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "c", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "v", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "b", false) || Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox6.SelectedItem, "n", false))
		{
		}
	}

	// Token: 0x06000BE0 RID: 3040 RVA: 0x0003F3B8 File Offset: 0x0003D5B8
	private void method_71(object object_1)
	{
	}

	// Token: 0x06000BE1 RID: 3041 RVA: 0x0003F3BC File Offset: 0x0003D5BC
	private void method_72(object sender, EventArgs e)
	{
		this.Timer_2.Start();
	}

	// Token: 0x06000BE2 RID: 3042 RVA: 0x0003F3CC File Offset: 0x0003D5CC
	private async void method_73(object sender, EventArgs e)
	{
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (skeet.GetAsyncKeyState_2(Keys.Escape) != 0)
		{
			this.string_10 = "Enabled";
		}
		if (skeet.GetAsyncKeyState_2(Keys.Return) != 0)
		{
			this.string_10 = "Enabled";
		}
		if (skeet.Class29.smethod_0(this.string_9, "", false) != 0 && skeet.GetAsyncKeyState_2(dictionary[this.string_9]) != 0)
		{
			this.string_10 = "Disabled";
		}
		if (!(skeet.Class29.smethod_0(this.string_10, "Disabled", false) == 0 & this.FlatCheckBox3.Boolean_0))
		{
			TaskAwaiter taskAwaiter2;
			if (skeet.Class29.smethod_0(this.string_11, "", false) != 0 && skeet.GetAsyncKeyState_2(dictionary[this.string_11]) != 0)
			{
				if (this.FlatButton12.Color_0 == this.color_0)
				{
					this.FlatButton12.Color_0 = Color.FromArgb(38, 38, 38);
					skeet.Class29.smethod_1(this.Timer_7);
					skeet.Class29.smethod_2(this.FlatButton12);
					skeet.Class29.smethod_1(this.Timer_2);
					TaskAwaiter taskAwaiter = skeet.Class29.smethod_4(skeet.Class29.smethod_3(500));
					if (!taskAwaiter.IsCompleted)
					{
						await taskAwaiter;
						taskAwaiter = taskAwaiter2;
						taskAwaiter2 = default(TaskAwaiter);
					}
					taskAwaiter.GetResult();
					taskAwaiter = default(TaskAwaiter);
					this.Timer_2.Start();
				}
				else
				{
					this.FlatButton12.Color_0 = this.PictureBox12.BackColor;
					this.Timer_7.Start();
					this.FlatButton12.Refresh();
					this.Timer_2.Stop();
					TaskAwaiter taskAwaiter3 = Task.Delay(500).GetAwaiter();
					if (!taskAwaiter3.IsCompleted)
					{
						await taskAwaiter3;
						taskAwaiter3 = taskAwaiter2;
						taskAwaiter2 = default(TaskAwaiter);
					}
					taskAwaiter3.GetResult();
					taskAwaiter3 = default(TaskAwaiter);
					this.Timer_2.Start();
				}
			}
			if (Operators.CompareString(this.string_13, "", false) != 0 && skeet.GetAsyncKeyState_2(dictionary[this.string_13]) != 0)
			{
				if (!(this.FlatButton15.Color_0 == this.color_0))
				{
					this.FlatButton15.Color_0 = this.PictureBox12.BackColor;
					this.Timer_10.Start();
					this.FlatButton15.Refresh();
					this.Timer_2.Stop();
					TaskAwaiter taskAwaiter4 = Task.Delay(500).GetAwaiter();
					if (!taskAwaiter4.IsCompleted)
					{
						await taskAwaiter4;
						taskAwaiter4 = taskAwaiter2;
						taskAwaiter2 = default(TaskAwaiter);
					}
					taskAwaiter4.GetResult();
					taskAwaiter4 = default(TaskAwaiter);
					this.Timer_2.Start();
				}
				else
				{
					this.FlatButton15.Color_0 = Color.FromArgb(38, 38, 38);
					this.Timer_10.Stop();
					this.FlatButton15.Refresh();
					this.Timer_2.Stop();
					await Task.Delay(500);
					this.Timer_2.Start();
				}
			}
			if (Operators.CompareString(this.string_14, "", false) != 0 && skeet.GetAsyncKeyState_2(dictionary[this.string_14]) != 0)
			{
				if (this.FlatButton17.Color_0 == this.color_0)
				{
					this.FlatButton17.Color_0 = Color.FromArgb(38, 38, 38);
					this.Timer_11.Stop();
					this.FlatButton17.Refresh();
					this.Timer_2.Stop();
					TaskAwaiter taskAwaiter5 = Task.Delay(500).GetAwaiter();
					if (!taskAwaiter5.IsCompleted)
					{
						await taskAwaiter5;
						taskAwaiter5 = taskAwaiter2;
						taskAwaiter2 = default(TaskAwaiter);
					}
					taskAwaiter5.GetResult();
					taskAwaiter5 = default(TaskAwaiter);
					this.Timer_2.Start();
				}
				else
				{
					this.FlatButton17.Color_0 = this.PictureBox12.BackColor;
					this.Timer_11.Start();
					this.FlatButton17.Refresh();
					this.Timer_2.Stop();
					TaskAwaiter taskAwaiter6 = Task.Delay(500).GetAwaiter();
					if (!taskAwaiter6.IsCompleted)
					{
						await taskAwaiter6;
						taskAwaiter6 = taskAwaiter2;
						taskAwaiter2 = default(TaskAwaiter);
					}
					taskAwaiter6.GetResult();
					taskAwaiter6 = default(TaskAwaiter);
					this.Timer_2.Start();
				}
			}
			if (Operators.CompareString(this.string_12, "", false) != 0)
			{
				if (skeet.GetAsyncKeyState_2(dictionary[this.string_12]) != 0)
				{
					this.Timer_12.Start();
				}
				if (skeet.GetAsyncKeyState_2(dictionary[this.string_8]) != 0 && this.FlatCheckBox15.Boolean_0)
				{
					this.int_5 = 0;
				}
			}
			if (Operators.CompareString(this.string_15, "", false) != 0 && skeet.GetAsyncKeyState_2(dictionary[this.string_15]) != 0)
			{
				this.Timer_18.Start();
			}
			if (Operators.CompareString(this.string_15, "", false) != 0 && skeet.GetAsyncKeyState_2(dictionary[this.string_15]) != 0)
			{
				this.Timer_18.Start();
			}
			if (Operators.CompareString(this.string_16, "", false) != 0)
			{
				if (skeet.GetAsyncKeyState_2(dictionary[this.string_16]) != 0)
				{
					this.bool_0 = true;
					this.FlatLabel12.Text = "Listening...";
					this.FlatLabel12.ForeColor = Color.Green;
					this.FlatButton23.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton23.Refresh();
				}
				else
				{
					this.bool_0 = false;
					this.FlatLabel12.Text = "Air V0.1";
					this.FlatLabel12.ForeColor = Color.Silver;
					this.FlatButton23.Color_0 = Color.FromArgb(38, 38, 38);
					this.FlatButton23.Refresh();
				}
			}
			if (Operators.CompareString(this.string_17, "", false) != 0 && skeet.GetAsyncKeyState_2(dictionary[this.string_17]) != 0)
			{
				if (!this.Visible)
				{
					this.Show();
					this.Timer_2.Stop();
					TaskAwaiter taskAwaiter7 = Task.Delay(500).GetAwaiter();
					if (!taskAwaiter7.IsCompleted)
					{
						await taskAwaiter7;
						taskAwaiter7 = taskAwaiter2;
						taskAwaiter2 = default(TaskAwaiter);
					}
					taskAwaiter7.GetResult();
					taskAwaiter7 = default(TaskAwaiter);
					this.Timer_2.Start();
				}
				else
				{
					this.Hide();
					this.Timer_2.Stop();
					await Task.Delay(500);
					this.Timer_2.Start();
				}
			}
		}
	}

	// Token: 0x06000BE3 RID: 3043 RVA: 0x0003F414 File Offset: 0x0003D614
	private void method_74(object sender, EventArgs e)
	{
		if (skeet.GetAsyncKeyState_2(Keys.LShiftKey) != 0)
		{
			this.FlatButton12.Text = ">...<";
			this.Timer_0.Start();
			this.FlatButton12.Refresh();
		}
		else if (!(this.FlatButton12.Color_0 == this.color_0))
		{
			this.FlatButton12.Color_0 = this.PictureBox12.BackColor;
			this.Timer_7.Start();
		}
		else
		{
			this.FlatButton12.Color_0 = Color.FromArgb(38, 38, 38);
			this.Timer_7.Stop();
		}
	}

	// Token: 0x06000BE4 RID: 3044 RVA: 0x0003F4B4 File Offset: 0x0003D6B4
	private void method_75(object sender, EventArgs e)
	{
		if (Operators.CompareString(this.FlatButton13.Text, "<", false) == 0)
		{
			this.FlatButton13.Text = ">";
			this.FlatButton13.Color_0 = this.PictureBox12.BackColor;
			this.FaderGroupBox3.Show();
			this.FlatButton18.Text = "<";
			this.FlatButton18.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox12.Hide();
			this.FlatButton39.Text = "<";
			this.FlatButton39.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox14.Hide();
			this.FlatButton14.Text = "<";
			this.FlatButton14.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox17.Hide();
			this.FlatButton16.Text = "<";
			this.FlatButton16.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox18.Hide();
			this.FlatButton20.Text = "<";
			this.FlatButton20.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox19.Hide();
			this.FlatButton37.Text = "<";
			this.FlatButton37.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox15.Hide();
			this.FlatButton22.Text = "<";
			this.FlatButton22.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox20.Hide();
		}
		else
		{
			this.FlatButton13.Text = "<";
			this.FlatButton13.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox3.Hide();
		}
		this.FlatButton22.Refresh();
		this.FlatButton20.Refresh();
		this.FlatButton16.Refresh();
		this.FlatButton14.Refresh();
		this.FlatButton39.Refresh();
		this.FlatButton13.Refresh();
		this.FlatButton37.Refresh();
		this.FlatButton18.Refresh();
	}

	// Token: 0x06000BE5 RID: 3045 RVA: 0x0003F6F4 File Offset: 0x0003D8F4
	private void method_76(object sender, EventArgs e)
	{
		if (Operators.CompareString(this.FlatButton18.Text, "<", false) == 0)
		{
			this.FlatButton18.Text = ">";
			this.FlatButton18.Color_0 = this.PictureBox12.BackColor;
			this.FaderGroupBox12.Show();
			this.FlatButton13.Text = "<";
			this.FlatButton13.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox3.Hide();
			this.FlatButton39.Text = "<";
			this.FlatButton39.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox14.Hide();
			this.FlatButton37.Text = "<";
			this.FlatButton37.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox15.Hide();
			this.FlatButton14.Text = "<";
			this.FlatButton14.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox17.Hide();
			this.FlatButton16.Text = "<";
			this.FlatButton16.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox18.Hide();
			this.FlatButton20.Text = "<";
			this.FlatButton20.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox19.Hide();
			this.FlatButton22.Text = "<";
			this.FlatButton22.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox20.Hide();
		}
		else
		{
			this.FlatButton18.Text = "<";
			this.FlatButton18.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox12.Hide();
		}
		this.FlatButton22.Refresh();
		this.FlatButton20.Refresh();
		this.FlatButton16.Refresh();
		this.FlatButton14.Refresh();
		this.FlatButton39.Refresh();
		this.FlatButton13.Refresh();
		this.FlatButton37.Refresh();
		this.FlatButton18.Refresh();
	}

	// Token: 0x06000BE6 RID: 3046 RVA: 0x0003F934 File Offset: 0x0003DB34
	private void method_77(object sender, EventArgs e)
	{
		if (Operators.CompareString(this.FlatButton20.Text, "<", false) == 0)
		{
			this.FlatButton20.Text = ">";
			this.FlatButton20.Color_0 = this.PictureBox12.BackColor;
			this.FaderGroupBox19.Show();
			this.FlatButton13.Text = "<";
			this.FlatButton13.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox3.Hide();
			this.FlatButton39.Text = "<";
			this.FlatButton39.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox14.Hide();
			this.FlatButton37.Text = "<";
			this.FlatButton37.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox15.Hide();
			this.FlatButton14.Text = "<";
			this.FlatButton14.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox17.Hide();
			this.FlatButton16.Text = "<";
			this.FlatButton16.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox18.Hide();
			this.FlatButton18.Text = "<";
			this.FlatButton18.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox12.Hide();
			this.FlatButton22.Text = "<";
			this.FlatButton22.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox20.Hide();
		}
		else
		{
			this.FlatButton20.Text = "<";
			this.FlatButton20.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox19.Hide();
		}
		this.FlatButton22.Refresh();
		this.FlatButton20.Refresh();
		this.FlatButton16.Refresh();
		this.FlatButton14.Refresh();
		this.FlatButton39.Refresh();
		this.FlatButton13.Refresh();
		this.FlatButton37.Refresh();
		this.FlatButton18.Refresh();
	}

	// Token: 0x06000BE7 RID: 3047 RVA: 0x0003FB74 File Offset: 0x0003DD74
	private void method_78(object sender, EventArgs e)
	{
		this.Timer_30.Stop();
		base.Opacity -= 0.1;
		if (base.Opacity == 0.0)
		{
			string path = Environment.GetCommandLineArgs()[0];
			string fileName = Path.GetFileName(path);
			this.FlatButton1.Dispose();
			this.FlatButton2.Dispose();
			this.FlatButton3.Dispose();
			this.FlatButton4.Dispose();
			this.FlatButton5.Dispose();
			this.FlatButton6.Dispose();
			this.FlatButton7.Dispose();
			this.FlatButton8.Dispose();
			this.FlatButton9.Dispose();
			this.FlatButton10.Dispose();
			this.FlatButton11.Dispose();
			this.FlatButton12.Dispose();
			this.FlatButton13.Dispose();
			this.FlatButton14.Dispose();
			this.FlatButton15.Dispose();
			this.FlatButton16.Dispose();
			this.FlatButton17.Dispose();
			this.FlatButton18.Dispose();
			this.FlatButton19.Dispose();
			this.FlatButton20.Dispose();
			this.FlatButton21.Dispose();
			this.FlatButton22.Dispose();
			this.FlatButton23.Dispose();
			this.FlatButton24.Dispose();
			this.FlatButton25.Dispose();
			this.FlatButton26.Dispose();
			this.FlatButton27.Dispose();
			this.FlatButton28.Dispose();
			this.FlatButton29.Dispose();
			this.FlatButton30.Dispose();
			this.FlatButton31.Dispose();
			this.FlatButton32.Dispose();
			this.Label1.Dispose();
			this.Label2.Dispose();
			this.Label3.Dispose();
			this.Label4.Dispose();
			this.Label5.Dispose();
			this.Label6.Dispose();
			this.Label7.Dispose();
			this.Label8.Dispose();
			this.Label10.Dispose();
			this.Label9.Dispose();
			this.Label11.Dispose();
			this.Label13.Dispose();
			this.Label12.Dispose();
			this.Label14.Dispose();
			this.FlatLabel1.Dispose();
			this.FlatLabel2.Dispose();
			this.PictureBox19.Dispose();
			this.FlatLabel8.Dispose();
			this.FlatLabel9.Dispose();
			this.PictureBox18.Dispose();
			this.FlatLabel6.Dispose();
			this.FlatLabel5.Dispose();
			this.FlatLabel9.Dispose();
			this.FlatLabel10.Dispose();
			this.FlatLabel11.Dispose();
			this.FlatLabel12.Dispose();
			this.FlatLabel13.Dispose();
			this.FlatLabel14.Dispose();
			this.FlatLabel15.Dispose();
			this.FlatLabel16.Dispose();
			this.FlatLabel17.Dispose();
			this.FlatLabel18.Dispose();
			this.FlatLabel19.Dispose();
			this.FlatLabel20.Dispose();
			this.FlatLabel21.Dispose();
			this.FlatLabel22.Dispose();
			this.FlatLabel23.Dispose();
			this.FlatLabel24.Dispose();
			this.PictureBox20.Dispose();
			this.FlatLabel26.Dispose();
			this.FlatLabel27.Dispose();
			this.FlatLabel28.Dispose();
			this.FlatLabel29.Dispose();
			this.FlatLabel30.Dispose();
			this.FlatLabel31.Dispose();
			this.FlatLabel32.Dispose();
			this.FlatLabel33.Dispose();
			this.FlatLabel34.Dispose();
			this.FlatLabel35.Dispose();
			this.FlatLabel36.Dispose();
			this.FlatLabel37.Dispose();
			this.PictureBox21.Dispose();
			this.FlatLabel40.Dispose();
			this.FlatLabel38.Dispose();
			this.PictureBox1.Dispose();
			this.PictureBox2.Dispose();
			this.PictureBox3.Dispose();
			this.PictureBox4.Dispose();
			this.PictureBox5.Dispose();
			this.PictureBox6.Dispose();
			this.PictureBox7.Dispose();
			this.PictureBox8.Dispose();
			this.PictureBox9.Dispose();
			this.FlatLabel51.Dispose();
			this.PictureBox22.Dispose();
			this.FlatLabel52.Dispose();
			this.FlatLabel54.Dispose();
			this.FlatLabel55.Dispose();
			this.FlatLabel56.Dispose();
			this.FlatLabel57.Dispose();
			this.FlatCheckBox1.Dispose();
			this.FlatCheckBox2.Dispose();
			this.FlatCheckBox3.Dispose();
			this.FlatCheckBox4.Dispose();
			this.FlatCheckBox13.Dispose();
			this.FlatCheckBox14.Dispose();
			this.FlatCheckBox5.Dispose();
			this.FlatCheckBox6.Dispose();
			this.FlatCheckBox7.Dispose();
			this.FlatCheckBox8.Dispose();
			this.FlatCheckBox9.Dispose();
			this.FlatCheckBox10.Dispose();
			this.FlatCheckBox11.Dispose();
			this.FlatCheckBox12.Dispose();
			this.FlatCheckBox15.Dispose();
			this.FlatCheckBox16.Dispose();
			this.FlatCheckBox17.Dispose();
			this.FlatCheckBox18.Dispose();
			this.FlatCheckBox19.Dispose();
			this.FlatCheckBox20.Dispose();
			this.FlatCheckBox22.Dispose();
			this.FlatCheckBox19.Dispose();
			this.FlatButton12.Dispose();
			this.FlatButton15.Dispose();
			this.FlatButton17.Dispose();
			this.FlatButton19.Dispose();
			this.FlatButton21.Dispose();
			this.FlatButton23.Dispose();
			this.FlatButton25.Dispose();
			this.FlatCheckBox24.Dispose();
			this.FlatCheckBox26.Dispose();
			this.FlatCheckBox27.Dispose();
			this.FlatCheckBox29.Dispose();
			this.FlatCheckBox29.Dispose();
			this.FlatLabel12.Dispose();
			this.Label5.Dispose();
			this.Label6.Dispose();
			this.FlatCheckBox5.Dispose();
			this.FlatCheckBox6.Dispose();
			this.FlatCheckBox7.Dispose();
			this.Label3.Dispose();
			this.ThirteenComboBox1.Dispose();
			this.FlatCheckBox8.Dispose();
			this.FlatTextBox6.Dispose();
			this.FlatCheckBox9.Dispose();
			this.Label2.Dispose();
			this.FlatLabel17.Dispose();
			this.FlatLabel21.Dispose();
			this.FlatLabel30.Dispose();
			this.FlatLabel24.Dispose();
			this.FlatLabel23.Dispose();
			this.PictureBox1.Dispose();
			this.PictureBox5.Dispose();
			this.FlatButton5.Dispose();
			this.FlatButton3.Dispose();
			this.FlatButton4.Dispose();
			this.FlatButton11.Dispose();
			this.FlatButton9.Dispose();
			this.FlatLabel16.Dispose();
			this.FlatLabel14.Dispose();
			this.FlatLabel13.Dispose();
			this.FlatLabel15.Dispose();
			this.FlatLabel22.Dispose();
			this.FlatLabel19.Dispose();
			this.FlatLabel20.Dispose();
			this.FlatLabel18.Dispose();
			this.FlatLabel11.Dispose();
			this.FlatLabel9.Dispose();
			this.FlatLabel37.Dispose();
			this.FlatLabel35.Dispose();
			this.FlatLabel32.Dispose();
			this.FlatLabel31.Dispose();
			this.FlatLabel55.Dispose();
			this.FlatLabel54.Dispose();
			this.FlatLabel56.Dispose();
			this.FlatLabel57.Dispose();
			this.FlatLabel36.Dispose();
			this.FlatLabel33.Dispose();
			this.FlatLabel34.Dispose();
			this.FlatCheckBox10.Dispose();
			this.FlatCheckBox11.Dispose();
			this.FlatCheckBox12.Dispose();
			this.FlatButton1.Dispose();
			this.FlatLabel10.Dispose();
			this.FlatLabel1.Dispose();
			this.FlatLabel2.Dispose();
			this.FlatCheckBox4.Dispose();
			this.FlatButton2.Dispose();
			this.FlatButton6.Dispose();
			this.FlatButton7.Dispose();
			this.FlatButton8.Dispose();
			this.FlatLabel29.Dispose();
			this.FlatTextBox7.Dispose();
			this.FlatCheckBox17.Dispose();
			this.FlatCheckBox18.Dispose();
			this.FlatCheckBox15.Dispose();
			this.FlatCheckBox16.Dispose();
			this.FlatLabel26.Dispose();
			this.FlatLabel27.Dispose();
			this.FlatLabel28.Dispose();
			this.ThirteenComboBox3.Dispose();
			this.ThirteenComboBox4.Dispose();
			this.ThirteenComboBox5.Dispose();
			if (this.FlatCheckBox10.Boolean_0)
			{
				Process.Start(new ProcessStartInfo
				{
					Arguments = "/C choice /C Y /N /D Y /T 3 & Del \"" + Application.ExecutablePath + "\"",
					WindowStyle = ProcessWindowStyle.Hidden,
					CreateNoWindow = true,
					FileName = "cmd.exe"
				});
				Application.Exit();
			}
			if (this.FlatCheckBox11.Boolean_0)
			{
				Process.Start(new ProcessStartInfo
				{
					Arguments = "/C choice /C Y /N /D Y /T 3 & del C:\\Windows\\prefetch\\*\"" + fileName + "\"*/s/q",
					WindowStyle = ProcessWindowStyle.Hidden,
					CreateNoWindow = true,
					FileName = "cmd.exe"
				});
			}
			this.method_145();
			Application.Exit();
		}
	}

	// Token: 0x06000BE8 RID: 3048 RVA: 0x0004053C File Offset: 0x0003E73C
	private void method_79(object object_1)
	{
		if (this.FlatCheckBox20.Boolean_0)
		{
			Class1.Class2_0.Cpsdisplay_0.Show();
		}
		else
		{
			Class1.Class2_0.Cpsdisplay_0.Close();
		}
	}

	// Token: 0x06000BE9 RID: 3049 RVA: 0x0004056C File Offset: 0x0003E76C
	private void method_80(object object_1)
	{
		if (this.FlatCheckBox2.Boolean_0)
		{
			Class1.Class2_0.Overlay_0.Close();
		}
		else
		{
			Class1.Class2_0.Overlay_0.Close();
		}
	}

	// Token: 0x06000BEA RID: 3050 RVA: 0x0004059C File Offset: 0x0003E79C
	private void method_81(object sender, EventArgs e)
	{
		this.Timer_2.Start();
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (skeet.GetAsyncKeyState_2(Keys.A) != 0)
		{
			this.FlatButton12.Text = "[A] Autoclicker";
			this.string_11 = "A";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.B) != 0)
		{
			this.FlatButton12.Text = "[B] Autoclicker";
			this.string_11 = "B";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.C) != 0)
		{
			this.FlatButton12.Text = "[C] Autoclicker";
			this.string_11 = "C";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.D) != 0)
		{
			this.FlatButton12.Text = "[D] Autoclicker";
			this.string_11 = "D";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.E) != 0)
		{
			this.FlatButton12.Text = "[E] Autoclicker";
			this.string_11 = "E";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.F) != 0)
		{
			this.FlatButton12.Text = "[F] Autoclicker";
			this.string_11 = "F";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.G) != 0)
		{
			this.FlatButton12.Text = "[G] Autoclicker";
			this.string_11 = "G";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.H) != 0)
		{
			this.FlatButton12.Text = "[H] Autoclicker";
			this.string_11 = "H";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.I) != 0)
		{
			this.FlatButton12.Text = "[I] Autoclicker";
			this.string_11 = "I";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.J) != 0)
		{
			this.FlatButton12.Text = "[J] Autoclicker";
			this.string_11 = "J";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.K) != 0)
		{
			this.FlatButton12.Text = "[K] Autoclicker";
			this.string_11 = "K";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.L) != 0)
		{
			this.FlatButton12.Text = "[L] Autoclicker";
			this.string_11 = "L";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.M) != 0)
		{
			this.FlatButton12.Text = "[M] Autoclicker";
			this.string_11 = "M";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.N) != 0)
		{
			this.FlatButton12.Text = "[N] Autoclicker";
			this.string_11 = "N";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.O) != 0)
		{
			this.FlatButton12.Text = "[O] Autoclicker";
			this.string_11 = "O";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.P) != 0)
		{
			this.FlatButton12.Text = "[P] Autoclicker";
			this.string_11 = "P";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Q) != 0)
		{
			this.FlatButton12.Text = "[Q] Autoclicker";
			this.string_11 = "Q";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.R) != 0)
		{
			this.FlatButton12.Text = "[R] Autoclicker";
			this.string_11 = "R";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.S) != 0)
		{
			this.FlatButton12.Text = "[S] Autoclicker";
			this.string_11 = "S";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.T) != 0)
		{
			this.FlatButton12.Text = "[T] Autoclicker";
			this.string_11 = "T";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.U) != 0)
		{
			this.FlatButton12.Text = "[U] Autoclicker";
			this.string_11 = "U";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.V) != 0)
		{
			this.FlatButton12.Text = "[V] Autoclicker";
			this.string_11 = "V";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.X) != 0)
		{
			this.FlatButton12.Text = "[X] Autoclicker";
			this.string_11 = "X";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Y) != 0)
		{
			this.FlatButton12.Text = "[Y] Autoclicker";
			this.string_11 = "Y";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Z) != 0)
		{
			this.FlatButton12.Text = "[Z] Autoclicker";
			this.string_11 = "Z";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.W) != 0)
		{
			this.FlatButton12.Text = "[W] Autoclicker";
			this.string_11 = "W";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Escape) != 0)
		{
			this.FlatButton12.Text = "Autoclicker";
			this.string_11 = "";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState(1048576) != 0)
		{
			this.FlatButton12.Text = "Autoclicker";
			this.string_11 = "";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
		if (skeet.GetAsyncKeyState(2097152) != 0)
		{
			this.FlatButton12.Text = "Autoclicker";
			this.string_11 = "";
			this.FlatButton12.Refresh();
			this.Timer_0.Stop();
		}
	}

	// Token: 0x06000BEB RID: 3051 RVA: 0x00040F60 File Offset: 0x0003F160
	private void method_82(object sender, PopupEventArgs e)
	{
	}

	// Token: 0x06000BEC RID: 3052 RVA: 0x00040F64 File Offset: 0x0003F164
	private void method_83(object object_1)
	{
		if (this.FlatCheckBox21.Boolean_0)
		{
			this.MinimumCPS.Enabled = false;
			this.MaximumCPS.Enabled = false;
			this.MaximumCPS.Color_1 = Color.FromArgb(45, 47, 49);
			this.MinimumCPS.Color_1 = Color.FromArgb(45, 47, 49);
			this.MinimumCPS.Refresh();
			this.MaximumCPS.Refresh();
			this.PictureBox1.Show();
		}
		else
		{
			this.MinimumCPS.Enabled = true;
			this.MaximumCPS.Enabled = true;
			this.MaximumCPS.Color_1 = this.PictureBox12.BackColor;
			this.MinimumCPS.Color_1 = this.PictureBox12.BackColor;
			this.MinimumCPS.Refresh();
			this.MaximumCPS.Refresh();
			this.PictureBox1.Hide();
		}
	}

	// Token: 0x06000BED RID: 3053 RVA: 0x0004104C File Offset: 0x0003F24C
	private void method_84(object sender, EventArgs e)
	{
		if (Operators.CompareString(this.FlatButton39.Text, "<", false) == 0)
		{
			this.FlatButton39.Text = ">";
			this.FlatButton39.Color_0 = this.PictureBox12.BackColor;
			this.FaderGroupBox14.Show();
			this.FlatButton18.Text = "<";
			this.FlatButton18.Color_0 = Color.FromArgb(38, 38, 38);
			this.FlatButton13.Text = "<";
			this.FlatButton13.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox12.Hide();
			this.FaderGroupBox3.Hide();
			this.FlatButton37.Text = "<";
			this.FlatButton37.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox15.Hide();
			this.FlatButton14.Text = "<";
			this.FlatButton14.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox17.Hide();
			this.FlatButton16.Text = "<";
			this.FlatButton16.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox18.Hide();
			this.FlatButton20.Text = "<";
			this.FlatButton20.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox19.Hide();
			this.FlatButton22.Text = "<";
			this.FlatButton22.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox20.Hide();
		}
		else
		{
			this.FlatButton39.Text = "<";
			this.FlatButton39.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox14.Hide();
		}
		this.FlatButton22.Refresh();
		this.FlatButton20.Refresh();
		this.FlatButton16.Refresh();
		this.FlatButton14.Refresh();
		this.FlatButton39.Refresh();
		this.FlatButton13.Refresh();
		this.FlatButton37.Refresh();
		this.FlatButton18.Refresh();
	}

	// Token: 0x06000BEE RID: 3054 RVA: 0x0004128C File Offset: 0x0003F48C
	private void method_85(object sender, EventArgs e)
	{
		if (Operators.CompareString(this.FlatButton37.Text, "<", false) != 0)
		{
			this.FlatButton37.Text = "<";
			this.FlatButton37.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox15.Hide();
		}
		else
		{
			this.FlatButton37.Text = ">";
			this.FlatButton37.Color_0 = this.PictureBox12.BackColor;
			this.FaderGroupBox15.Show();
			this.FlatButton18.Text = "<";
			this.FlatButton18.Color_0 = Color.FromArgb(38, 38, 38);
			this.FlatButton13.Text = "<";
			this.FlatButton13.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox12.Hide();
			this.FaderGroupBox3.Hide();
			this.FlatButton39.Text = "<";
			this.FlatButton39.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox14.Hide();
			this.FlatButton14.Text = "<";
			this.FlatButton14.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox17.Hide();
			this.FlatButton16.Text = "<";
			this.FlatButton16.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox18.Hide();
			this.FlatButton20.Text = "<";
			this.FlatButton20.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox19.Hide();
			this.FlatButton22.Text = "<";
			this.FlatButton22.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox20.Hide();
		}
		this.FlatButton22.Refresh();
		this.FlatButton20.Refresh();
		this.FlatButton16.Refresh();
		this.FlatButton14.Refresh();
		this.FlatButton39.Refresh();
		this.FlatButton13.Refresh();
		this.FlatButton37.Refresh();
		this.FlatButton18.Refresh();
	}

	// Token: 0x06000BEF RID: 3055 RVA: 0x000414CC File Offset: 0x0003F6CC
	private void method_86(object sender, EventArgs e)
	{
		if (this.FlatButton40.Color_0 == this.color_0)
		{
			this.FlatButton40.Color_0 = Color.FromArgb(38, 38, 38);
		}
		else
		{
			this.FlatButton40.Color_0 = this.PictureBox12.BackColor;
		}
	}

	// Token: 0x06000BF0 RID: 3056 RVA: 0x00041520 File Offset: 0x0003F720
	private void method_87(object sender, EventArgs e)
	{
		if (!(this.PictureBox2.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = this.color_0;
			this.FlatLabel5.BackColor = this.color_0;
			this.PictureBox18.BackColor = this.color_0;
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox1.Show();
			this.NsGroupBox7.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox3.Hide();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BF1 RID: 3057 RVA: 0x000416BC File Offset: 0x0003F8BC
	private void method_88(object sender, EventArgs e)
	{
		if (!(this.PictureBox2.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = this.color_0;
			this.FlatLabel5.BackColor = this.color_0;
			this.PictureBox18.BackColor = this.color_0;
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox1.Show();
			this.NsGroupBox7.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox3.Hide();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BF2 RID: 3058 RVA: 0x00041858 File Offset: 0x0003FA58
	private void method_89(object sender, EventArgs e)
	{
		if (!(this.PictureBox3.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = this.color_0;
			this.FlatLabel6.BackColor = this.color_0;
			this.PictureBox19.BackColor = this.color_0;
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox7.Hide();
			this.NsGroupBox3.Hide();
			this.NsGroupBox1.Hide();
			this.NsGroupBox2.Show();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BF3 RID: 3059 RVA: 0x000419F4 File Offset: 0x0003FBF4
	private void method_90(object sender, EventArgs e)
	{
		if (!(this.PictureBox4.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = this.color_0;
			this.FlatLabel8.BackColor = this.color_0;
			this.PictureBox20.BackColor = this.color_0;
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox7.Show();
			this.NsGroupBox3.Hide();
			this.NsGroupBox1.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BF4 RID: 3060 RVA: 0x00041B90 File Offset: 0x0003FD90
	private void method_91(object sender, EventArgs e)
	{
		if (!(this.PictureBox5.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = this.color_0;
			this.FlatLabel38.BackColor = this.color_0;
			this.PictureBox21.BackColor = this.color_0;
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox1.Hide();
			this.NsGroupBox7.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox3.Hide();
			this.NsGroupBox4.Show();
		}
	}

	// Token: 0x06000BF5 RID: 3061 RVA: 0x00041D2C File Offset: 0x0003FF2C
	private void method_92(object sender, EventArgs e)
	{
		if (!(this.PictureBox6.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = this.color_0;
			this.FlatLabel40.BackColor = this.color_0;
			this.PictureBox22.BackColor = this.color_0;
			this.NsGroupBox7.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox1.Hide();
			this.NsGroupBox3.Show();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BF6 RID: 3062 RVA: 0x00041EC8 File Offset: 0x000400C8
	private void method_93(object sender, EventArgs e)
	{
		if (!(this.PictureBox2.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = this.color_0;
			this.FlatLabel5.BackColor = this.color_0;
			this.PictureBox18.BackColor = this.color_0;
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox1.Show();
			this.NsGroupBox7.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox3.Hide();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BF7 RID: 3063 RVA: 0x00042064 File Offset: 0x00040264
	private void method_94(object sender, EventArgs e)
	{
		if (!(this.PictureBox3.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = this.color_0;
			this.FlatLabel6.BackColor = this.color_0;
			this.PictureBox19.BackColor = this.color_0;
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox7.Hide();
			this.NsGroupBox3.Hide();
			this.NsGroupBox1.Hide();
			this.NsGroupBox2.Show();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BF8 RID: 3064 RVA: 0x00042200 File Offset: 0x00040400
	private void method_95(object sender, EventArgs e)
	{
		if (!(this.PictureBox3.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = this.color_0;
			this.FlatLabel6.BackColor = this.color_0;
			this.PictureBox19.BackColor = this.color_0;
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox7.Hide();
			this.NsGroupBox3.Hide();
			this.NsGroupBox1.Hide();
			this.NsGroupBox2.Show();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BF9 RID: 3065 RVA: 0x0004239C File Offset: 0x0004059C
	private void method_96(object sender, EventArgs e)
	{
		if (!(this.PictureBox4.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = this.color_0;
			this.FlatLabel8.BackColor = this.color_0;
			this.PictureBox20.BackColor = this.color_0;
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox7.Show();
			this.NsGroupBox3.Hide();
			this.NsGroupBox1.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BFA RID: 3066 RVA: 0x00042538 File Offset: 0x00040738
	private void method_97(object sender, EventArgs e)
	{
		if (!(this.PictureBox4.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = this.color_0;
			this.FlatLabel8.BackColor = this.color_0;
			this.PictureBox20.BackColor = this.color_0;
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox7.Show();
			this.NsGroupBox3.Hide();
			this.NsGroupBox1.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BFB RID: 3067 RVA: 0x000426D4 File Offset: 0x000408D4
	private void method_98(object sender, EventArgs e)
	{
		if (!(this.PictureBox5.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = this.color_0;
			this.FlatLabel38.BackColor = this.color_0;
			this.PictureBox21.BackColor = this.color_0;
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox1.Hide();
			this.NsGroupBox7.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox3.Hide();
			this.NsGroupBox4.Show();
		}
	}

	// Token: 0x06000BFC RID: 3068 RVA: 0x00042870 File Offset: 0x00040A70
	private void method_99(object sender, EventArgs e)
	{
		if (!(this.PictureBox5.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = this.color_0;
			this.FlatLabel38.BackColor = this.color_0;
			this.PictureBox21.BackColor = this.color_0;
			this.PictureBox6.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel40.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox22.BackColor = Color.FromArgb(10, 10, 10);
			this.NsGroupBox1.Hide();
			this.NsGroupBox7.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox3.Hide();
			this.NsGroupBox4.Show();
		}
	}

	// Token: 0x06000BFD RID: 3069 RVA: 0x00042A0C File Offset: 0x00040C0C
	private void method_100(object sender, EventArgs e)
	{
		if (!(this.PictureBox6.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = this.color_0;
			this.FlatLabel40.BackColor = this.color_0;
			this.PictureBox22.BackColor = this.color_0;
			this.NsGroupBox7.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox1.Hide();
			this.NsGroupBox3.Show();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BFE RID: 3070 RVA: 0x00042BA8 File Offset: 0x00040DA8
	private void method_101(object sender, EventArgs e)
	{
		if (!(this.PictureBox6.BackColor == this.color_0))
		{
			this.PictureBox2.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel5.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox18.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox3.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel6.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox19.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox4.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel8.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox20.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox5.BackColor = Color.FromArgb(10, 10, 10);
			this.FlatLabel38.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox21.BackColor = Color.FromArgb(10, 10, 10);
			this.PictureBox6.BackColor = this.color_0;
			this.FlatLabel40.BackColor = this.color_0;
			this.PictureBox22.BackColor = this.color_0;
			this.NsGroupBox7.Hide();
			this.NsGroupBox2.Hide();
			this.NsGroupBox1.Hide();
			this.NsGroupBox3.Show();
			this.NsGroupBox4.Hide();
		}
	}

	// Token: 0x06000BFF RID: 3071 RVA: 0x00042D44 File Offset: 0x00040F44
	public void method_102()
	{
		string path = Interaction.Environ("USERPROFILE") + "\\AppData\\Roaming\\.minecraft\\options.txt";
		string text = File.ReadAllText(path);
		if (text.Contains("key_key.drop:16"))
		{
			this.string_4 = "q";
		}
		if (text.Contains("key_key.drop:17"))
		{
			this.string_4 = "w";
		}
		if (text.Contains("key_key.drop:18"))
		{
			this.string_4 = "e";
		}
		if (text.Contains("key_key.drop:19"))
		{
			this.string_4 = "r";
		}
		if (text.Contains("key_key.drop:20"))
		{
			this.string_4 = "t";
		}
		if (text.Contains("key_key.drop:21"))
		{
			this.string_4 = "y";
		}
		if (text.Contains("key_key.drop:22"))
		{
			this.string_4 = "u";
		}
		if (text.Contains("key_key.drop:23"))
		{
			this.string_4 = "i";
		}
		if (text.Contains("key_key.drop:24"))
		{
			this.string_4 = "o";
		}
		if (text.Contains("key_key.drop:25"))
		{
			this.string_4 = "p";
		}
		if (text.Contains("key_key.drop:30"))
		{
			this.string_4 = "a";
		}
		if (text.Contains("key_key.drop:31"))
		{
			this.string_4 = "s";
		}
		if (text.Contains("key_key.drop:32"))
		{
			this.string_4 = "d";
		}
		if (text.Contains("key_key.drop:33"))
		{
			this.string_4 = "f";
		}
		if (text.Contains("key_key.drop:34"))
		{
			this.string_4 = "g";
		}
		if (text.Contains("key_key.drop:35"))
		{
			this.string_4 = "h";
		}
		if (text.Contains("key_key.drop:36"))
		{
			this.string_4 = "j";
		}
		if (text.Contains("key_key.drop:37"))
		{
			this.string_4 = "k";
		}
		if (text.Contains("key_key.drop:38"))
		{
			this.string_4 = "l";
		}
		if (text.Contains("key_key.drop:44"))
		{
			this.string_4 = "z";
		}
		if (text.Contains("key_key.drop:45"))
		{
			this.string_4 = "x";
		}
		if (text.Contains("key_key.drop:46"))
		{
			this.string_4 = "c";
		}
		if (text.Contains("key_key.drop:47"))
		{
			this.string_4 = "v";
		}
		if (text.Contains("key_key.drop:48"))
		{
			this.string_4 = "b";
		}
		if (text.Contains("key_key.drop:49"))
		{
			this.string_4 = "n";
		}
		if (text.Contains("key_key.drop:50"))
		{
			this.string_4 = "m";
		}
		if (text.Contains("key_key.drop:15"))
		{
			this.string_4 = "tab";
		}
		if (text.Contains("key_key.drop:29"))
		{
			this.string_4 = "leftctrl";
		}
		if (text.Contains("key_key.drop:49"))
		{
			this.string_4 = "leftshift";
		}
		if (text.Contains("key_key.drop:54"))
		{
			this.string_4 = "rightshift";
		}
		if (text.Contains("key_key.drop:56"))
		{
			this.string_4 = "leftalt";
		}
		if (text.Contains("key_key.drop:98"))
		{
			this.string_4 = "rightalt";
		}
		if (text.Contains("key_key.drop:99"))
		{
			this.string_4 = "rightctrl";
		}
		if (text.Contains("key_key.forward:16"))
		{
			this.string_5 = "q";
		}
		if (text.Contains("key_key.forward:17"))
		{
			this.string_5 = "w";
		}
		if (text.Contains("key_key.forward:18"))
		{
			this.string_5 = "e";
		}
		if (text.Contains("key_key.forward:19"))
		{
			this.string_5 = "r";
		}
		if (text.Contains("key_key.forward:20"))
		{
			this.string_5 = "t";
		}
		if (text.Contains("key_key.forward:21"))
		{
			this.string_5 = "y";
		}
		if (text.Contains("key_key.forward:22"))
		{
			this.string_5 = "u";
		}
		if (text.Contains("key_key.forward:23"))
		{
			this.string_5 = "i";
		}
		if (text.Contains("key_key.forward:24"))
		{
			this.string_5 = "o";
		}
		if (text.Contains("key_key.forward:25"))
		{
			this.string_5 = "p";
		}
		if (text.Contains("key_key.forward:30"))
		{
			this.string_5 = "a";
		}
		if (text.Contains("key_key.forward:31"))
		{
			this.string_5 = "s";
		}
		if (text.Contains("key_key.forward:32"))
		{
			this.string_5 = "d";
		}
		if (text.Contains("key_key.forward:33"))
		{
			this.string_5 = "f";
		}
		if (text.Contains("key_key.forward:34"))
		{
			this.string_5 = "g";
		}
		if (text.Contains("key_key.forward:35"))
		{
			this.string_5 = "h";
		}
		if (text.Contains("key_key.forward:36"))
		{
			this.string_5 = "j";
		}
		if (text.Contains("key_key.forward:37"))
		{
			this.string_5 = "k";
		}
		if (text.Contains("key_key.forward:38"))
		{
			this.string_5 = "l";
		}
		if (text.Contains("key_key.forward:44"))
		{
			this.string_5 = "z";
		}
		if (text.Contains("key_key.forward:45"))
		{
			this.string_5 = "x";
		}
		if (text.Contains("key_key.forward:46"))
		{
			this.string_5 = "c";
		}
		if (text.Contains("key_key.forward:47"))
		{
			this.string_5 = "v";
		}
		if (text.Contains("key_key.forward:48"))
		{
			this.string_5 = "b";
		}
		if (text.Contains("key_key.forward:49"))
		{
			this.string_5 = "n";
		}
		if (text.Contains("key_key.forward:50"))
		{
			this.string_5 = "m";
		}
		if (text.Contains("key_key.forward:15"))
		{
			this.string_5 = "tab";
		}
		if (text.Contains("key_key.forward:29"))
		{
			this.string_5 = "leftctrl";
		}
		if (text.Contains("key_key.forward:49"))
		{
			this.string_5 = "leftshift";
		}
		if (text.Contains("key_key.forward:54"))
		{
			this.string_5 = "rightshift";
		}
		if (text.Contains("key_key.forward:56"))
		{
			this.string_5 = "leftalt";
		}
		if (text.Contains("key_key.forward:98"))
		{
			this.string_5 = "rightalt";
		}
		if (text.Contains("key_key.forward:99"))
		{
			this.string_5 = "rightctrl";
		}
		if (text.Contains("key_key.sprint:16"))
		{
			this.string_7 = "q";
		}
		if (text.Contains("key_key.sprint:17"))
		{
			this.string_7 = "w";
		}
		if (text.Contains("key_key.sprint:18"))
		{
			this.string_7 = "e";
		}
		if (text.Contains("key_key.sprint:19"))
		{
			this.string_7 = "r";
		}
		if (text.Contains("key_key.sprint:20"))
		{
			this.string_7 = "t";
		}
		if (text.Contains("key_key.sprint:21"))
		{
			this.string_7 = "y";
		}
		if (text.Contains("key_key.sprint:22"))
		{
			this.string_7 = "u";
		}
		if (text.Contains("key_key.sprint:23"))
		{
			this.string_7 = "i";
		}
		if (text.Contains("key_key.sprint:24"))
		{
			this.string_7 = "o";
		}
		if (text.Contains("key_key.sprint:25"))
		{
			this.string_7 = "p";
		}
		if (text.Contains("key_key.sprint:30"))
		{
			this.string_7 = "a";
		}
		if (text.Contains("key_key.sprint:31"))
		{
			this.string_7 = "s";
		}
		if (text.Contains("key_key.sprint:32"))
		{
			this.string_7 = "d";
		}
		if (text.Contains("key_key.sprint:33"))
		{
			this.string_7 = "f";
		}
		if (text.Contains("key_key.sprint:34"))
		{
			this.string_7 = "g";
		}
		if (text.Contains("key_key.sprint:35"))
		{
			this.string_7 = "h";
		}
		if (text.Contains("key_key.sprint:36"))
		{
			this.string_7 = "j";
		}
		if (text.Contains("key_key.sprint:37"))
		{
			this.string_7 = "k";
		}
		if (text.Contains("key_key.sprint:38"))
		{
			this.string_7 = "l";
		}
		if (text.Contains("key_key.sprint:44"))
		{
			this.string_7 = "z";
		}
		if (text.Contains("key_key.sprint:45"))
		{
			this.string_7 = "x";
		}
		if (text.Contains("key_key.sprint:46"))
		{
			this.string_7 = "c";
		}
		if (text.Contains("key_key.sprint:47"))
		{
			this.string_7 = "v";
		}
		if (text.Contains("key_key.sprint:48"))
		{
			this.string_7 = "b";
		}
		if (text.Contains("key_key.sprint:49"))
		{
			this.string_7 = "n";
		}
		if (text.Contains("key_key.sprint:50"))
		{
			this.string_7 = "m";
		}
		if (text.Contains("key_key.sprint:15"))
		{
			this.string_7 = "tab";
		}
		if (text.Contains("key_key.sprint:29"))
		{
			this.string_7 = "leftctrl";
		}
		if (text.Contains("key_key.sprint:49"))
		{
			this.string_7 = "leftshift";
		}
		if (text.Contains("key_key.sprint:54"))
		{
			this.string_7 = "rightshift";
		}
		if (text.Contains("key_key.sprint:56"))
		{
			this.string_7 = "leftalt";
		}
		if (text.Contains("key_key.sprint:98"))
		{
			this.string_7 = "rightalt";
		}
		if (text.Contains("key_key.sprint:99"))
		{
			this.string_7 = "rightctrl";
		}
		if (text.Contains("key_key.inventory:16"))
		{
			this.string_8 = "q";
		}
		if (text.Contains("key_key.inventory:17"))
		{
			this.string_8 = "w";
		}
		if (text.Contains("key_key.inventory:18"))
		{
			this.string_8 = "e";
		}
		if (text.Contains("key_key.inventory:19"))
		{
			this.string_8 = "r";
		}
		if (text.Contains("key_key.inventory:20"))
		{
			this.string_8 = "t";
		}
		if (text.Contains("key_key.inventory:21"))
		{
			this.string_8 = "y";
		}
		if (text.Contains("key_key.inventory:22"))
		{
			this.string_8 = "u";
		}
		if (text.Contains("key_key.inventory:23"))
		{
			this.string_8 = "i";
		}
		if (text.Contains("key_key.inventory:24"))
		{
			this.string_8 = "o";
		}
		if (text.Contains("key_key.inventory:25"))
		{
			this.string_8 = "p";
		}
		if (text.Contains("key_key.inventory:30"))
		{
			this.string_8 = "a";
		}
		if (text.Contains("key_key.inventory:31"))
		{
			this.string_8 = "s";
		}
		if (text.Contains("key_key.inventory:32"))
		{
			this.string_8 = "d";
		}
		if (text.Contains("key_key.inventory:33"))
		{
			this.string_8 = "f";
		}
		if (text.Contains("key_key.inventory:34"))
		{
			this.string_8 = "g";
		}
		if (text.Contains("key_key.inventory:35"))
		{
			this.string_8 = "h";
		}
		if (text.Contains("key_key.inventory:36"))
		{
			this.string_8 = "j";
		}
		if (text.Contains("key_key.inventory:37"))
		{
			this.string_8 = "k";
		}
		if (text.Contains("key_key.inventory:38"))
		{
			this.string_8 = "l";
		}
		if (text.Contains("key_key.inventory:44"))
		{
			this.string_8 = "z";
		}
		if (text.Contains("key_key.inventory:45"))
		{
			this.string_8 = "x";
		}
		if (text.Contains("key_key.inventory:46"))
		{
			this.string_8 = "c";
		}
		if (text.Contains("key_key.inventory:47"))
		{
			this.string_8 = "v";
		}
		if (text.Contains("key_key.inventory:48"))
		{
			this.string_8 = "b";
		}
		if (text.Contains("key_key.inventory:49"))
		{
			this.string_8 = "n";
		}
		if (text.Contains("key_key.inventory:50"))
		{
			this.string_8 = "m";
		}
		if (text.Contains("key_key.inventory:15"))
		{
			this.string_8 = "tab";
		}
		if (text.Contains("key_key.inventory:29"))
		{
			this.string_8 = "leftctrl";
		}
		if (text.Contains("key_key.inventory:49"))
		{
			this.string_8 = "leftshift";
		}
		if (text.Contains("key_key.inventory:54"))
		{
			this.string_8 = "rightshift";
		}
		if (text.Contains("key_key.inventory:56"))
		{
			this.string_8 = "leftalt";
		}
		if (text.Contains("key_key.inventory:98"))
		{
			this.string_8 = "rightalt";
		}
		if (text.Contains("key_key.inventory:99"))
		{
			this.string_8 = "rightctrl";
		}
		if (text.Contains("key_key.chat:16"))
		{
			this.string_9 = "q";
		}
		if (text.Contains("key_key.chat:17"))
		{
			this.string_9 = "w";
		}
		if (text.Contains("key_key.chat:18"))
		{
			this.string_9 = "e";
		}
		if (text.Contains("key_key.chat:19"))
		{
			this.string_9 = "r";
		}
		if (text.Contains("key_key.chat:20"))
		{
			this.string_9 = "t";
		}
		if (text.Contains("key_key.chat:21"))
		{
			this.string_9 = "y";
		}
		if (text.Contains("key_key.chat:22"))
		{
			this.string_9 = "u";
		}
		if (text.Contains("key_key.chat:23"))
		{
			this.string_9 = "i";
		}
		if (text.Contains("key_key.chat:24"))
		{
			this.string_9 = "o";
		}
		if (text.Contains("key_key.chat:25"))
		{
			this.string_9 = "p";
		}
		if (text.Contains("key_key.chat:30"))
		{
			this.string_9 = "a";
		}
		if (text.Contains("key_key.chat:31"))
		{
			this.string_9 = "s";
		}
		if (text.Contains("key_key.chat:32"))
		{
			this.string_9 = "d";
		}
		if (text.Contains("key_key.chat:33"))
		{
			this.string_9 = "f";
		}
		if (text.Contains("key_key.chat:34"))
		{
			this.string_9 = "g";
		}
		if (text.Contains("key_key.chat:35"))
		{
			this.string_9 = "h";
		}
		if (text.Contains("key_key.chat:36"))
		{
			this.string_9 = "j";
		}
		if (text.Contains("key_key.chat:37"))
		{
			this.string_9 = "k";
		}
		if (text.Contains("key_key.chat:38"))
		{
			this.string_9 = "l";
		}
		if (text.Contains("key_key.chat:44"))
		{
			this.string_9 = "z";
		}
		if (text.Contains("key_key.chat:45"))
		{
			this.string_9 = "x";
		}
		if (text.Contains("key_key.chat:46"))
		{
			this.string_9 = "c";
		}
		if (text.Contains("key_key.chat:47"))
		{
			this.string_9 = "v";
		}
		if (text.Contains("key_key.chat:48"))
		{
			this.string_9 = "b";
		}
		if (text.Contains("key_key.chat:49"))
		{
			this.string_9 = "n";
		}
		if (text.Contains("key_key.chat:50"))
		{
			this.string_9 = "m";
		}
		if (text.Contains("key_key.chat:15"))
		{
			this.string_9 = "tab";
		}
		if (text.Contains("key_key.chat:29"))
		{
			this.string_9 = "leftctrl";
		}
		if (text.Contains("key_key.chat:49"))
		{
			this.string_9 = "leftshift";
		}
		if (text.Contains("key_key.chat:54"))
		{
			this.string_9 = "rightshift";
		}
		if (text.Contains("key_key.chat:56"))
		{
			this.string_9 = "leftalt";
		}
		if (text.Contains("key_key.chat:98"))
		{
			this.string_9 = "rightalt";
		}
		if (text.Contains("key_key.chat:99"))
		{
			this.string_9 = "rightctrl";
		}
		if (text.Contains("key_key.back:16"))
		{
			this.string_6 = "q";
		}
		if (text.Contains("key_key.back:17"))
		{
			this.string_6 = "w";
		}
		if (text.Contains("key_key.back:18"))
		{
			this.string_6 = "e";
		}
		if (text.Contains("key_key.back:19"))
		{
			this.string_6 = "r";
		}
		if (text.Contains("key_key.back:20"))
		{
			this.string_6 = "t";
		}
		if (text.Contains("key_key.back:21"))
		{
			this.string_6 = "y";
		}
		if (text.Contains("key_key.back:22"))
		{
			this.string_6 = "u";
		}
		if (text.Contains("key_key.back:23"))
		{
			this.string_6 = "i";
		}
		if (text.Contains("key_key.back:24"))
		{
			this.string_6 = "o";
		}
		if (text.Contains("key_key.back:25"))
		{
			this.string_6 = "p";
		}
		if (text.Contains("key_key.back:30"))
		{
			this.string_6 = "a";
		}
		if (text.Contains("key_key.back:31"))
		{
			this.string_6 = "s";
		}
		if (text.Contains("key_key.back:32"))
		{
			this.string_6 = "d";
		}
		if (text.Contains("key_key.back:33"))
		{
			this.string_6 = "f";
		}
		if (text.Contains("key_key.back:34"))
		{
			this.string_6 = "g";
		}
		if (text.Contains("key_key.back:35"))
		{
			this.string_6 = "h";
		}
		if (text.Contains("key_key.back:36"))
		{
			this.string_6 = "j";
		}
		if (text.Contains("key_key.back:37"))
		{
			this.string_6 = "k";
		}
		if (text.Contains("key_key.back:38"))
		{
			this.string_6 = "l";
		}
		if (text.Contains("key_key.back:44"))
		{
			this.string_6 = "z";
		}
		if (text.Contains("key_key.back:45"))
		{
			this.string_6 = "x";
		}
		if (text.Contains("key_key.back:46"))
		{
			this.string_6 = "c";
		}
		if (text.Contains("key_key.back:47"))
		{
			this.string_6 = "v";
		}
		if (text.Contains("key_key.back:48"))
		{
			this.string_6 = "b";
		}
		if (text.Contains("key_key.back:49"))
		{
			this.string_6 = "n";
		}
		if (text.Contains("key_key.back:50"))
		{
			this.string_6 = "m";
		}
		if (text.Contains("key_key.back:15"))
		{
			this.string_6 = "tab";
		}
		if (text.Contains("key_key.back:29"))
		{
			this.string_6 = "leftctrl";
		}
		if (text.Contains("key_key.back:49"))
		{
			this.string_6 = "leftshift";
		}
		if (text.Contains("key_key.back:54"))
		{
			this.string_6 = "rightshift";
		}
		if (text.Contains("key_key.back:56"))
		{
			this.string_6 = "leftalt";
		}
		if (text.Contains("key_key.back:98"))
		{
			this.string_6 = "rightalt";
		}
		if (text.Contains("key_key.back:99"))
		{
			this.string_6 = "rightctrl";
		}
	}

	// Token: 0x06000C00 RID: 3072 RVA: 0x00044000 File Offset: 0x00042200
	private void method_103(object sender, EventArgs e)
	{
		this.method_102();
		Interaction.MsgBox(string.Concat(new string[]
		{
			"Drop: ",
			this.string_4,
			"\r\nForward: ",
			this.string_5,
			"\r\nSprint: ",
			this.string_7,
			"\r\nInventory: ",
			this.string_8,
			"\r\nChat: ",
			this.string_9,
			"\r\nBackward: ",
			this.string_6
		}), MsgBoxStyle.OkOnly, null);
	}

	// Token: 0x06000C01 RID: 3073 RVA: 0x00044090 File Offset: 0x00042290
	private void method_104(object sender, EventArgs e)
	{
		if (skeet.GetAsyncKeyState_2(Keys.LShiftKey) != 0)
		{
			this.FlatButton15.Text = ">...<";
			this.Timer_15.Start();
			this.FlatButton15.Refresh();
		}
		else if (this.FlatButton15.Color_0 == this.color_0)
		{
			this.FlatButton15.Color_0 = Color.FromArgb(38, 38, 38);
			this.Timer_10.Stop();
		}
		else
		{
			this.FlatButton15.Color_0 = this.PictureBox12.BackColor;
			this.Timer_10.Start();
		}
	}

	// Token: 0x06000C02 RID: 3074 RVA: 0x00044130 File Offset: 0x00042330
	private void method_105(object sender, EventArgs e)
	{
		this.Timer_10.Interval = this.random_0.Next(this.FlatTrackBar3.Int32_2, this.FlatTrackBar5.Int32_2);
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		if ((skeet.GetAsyncKeyState_2(dictionary[this.string_5]) & ((-((this.Timer_7.Enabled > false) ? 1 : 0)) ? 1 : 0) & ((-((this.bool_6 > false) ? 1 : 0)) ? 1 : 0)) != 0)
		{
			GClass12.smethod_3(dictionary[this.string_5], true);
			this.method_27(0.025);
			GClass12.smethod_3(dictionary[this.string_5], false);
		}
	}

	// Token: 0x06000C03 RID: 3075 RVA: 0x00044478 File Offset: 0x00042678
	private void method_106(object sender, EventArgs e)
	{
		if (Operators.CompareString(this.FlatButton14.Text, "<", false) == 0)
		{
			this.FlatButton13.Text = "<";
			this.FlatButton13.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox3.Hide();
			this.FlatButton18.Text = "<";
			this.FlatButton18.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox12.Hide();
			this.FlatButton39.Text = "<";
			this.FlatButton39.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox14.Hide();
			this.FlatButton16.Text = "<";
			this.FlatButton16.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox18.Hide();
			this.FlatButton20.Text = "<";
			this.FlatButton20.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox19.Hide();
			this.FlatButton37.Text = "<";
			this.FlatButton37.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox15.Hide();
			this.FlatButton14.Text = ">";
			this.FlatButton14.Color_0 = this.PictureBox12.BackColor;
			this.FaderGroupBox17.Show();
			this.FlatButton22.Text = "<";
			this.FlatButton22.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox20.Hide();
		}
		else
		{
			this.FlatButton14.Text = "<";
			this.FlatButton14.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox17.Hide();
		}
		this.FlatButton22.Refresh();
		this.FlatButton20.Refresh();
		this.FlatButton16.Refresh();
		this.FlatButton14.Refresh();
		this.FlatButton39.Refresh();
		this.FlatButton13.Refresh();
		this.FlatButton37.Refresh();
		this.FlatButton18.Refresh();
	}

	// Token: 0x06000C04 RID: 3076 RVA: 0x000446B8 File Offset: 0x000428B8
	private void method_107(object object_1)
	{
		this.FlatCheckBox23.Boolean_0 = true;
	}

	// Token: 0x06000C05 RID: 3077 RVA: 0x000446C8 File Offset: 0x000428C8
	private void method_108(object object_1)
	{
		this.FlatCheckBox26.Boolean_0 = true;
	}

	// Token: 0x06000C06 RID: 3078 RVA: 0x000446D8 File Offset: 0x000428D8
	private void method_109(object sender, EventArgs e)
	{
		if (skeet.GetAsyncKeyState_2(Keys.LShiftKey) != 0)
		{
			this.FlatButton17.Text = ">...<";
			this.Timer_16.Start();
			this.FlatButton17.Refresh();
		}
		else if (!(this.FlatButton17.Color_0 == this.color_0))
		{
			this.FlatButton17.Color_0 = this.PictureBox12.BackColor;
			this.Timer_11.Start();
		}
		else
		{
			this.FlatButton17.Color_0 = Color.FromArgb(38, 38, 38);
			this.Timer_11.Stop();
		}
	}

	// Token: 0x06000C07 RID: 3079 RVA: 0x00044778 File Offset: 0x00042978
	private void method_110(object sender, EventArgs e)
	{
		this.Timer_11.Interval = this.random_0.Next(this.FlatTrackBar6.Int32_2, this.FlatTrackBar7.Int32_2);
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		if ((skeet.GetAsyncKeyState_2(dictionary[this.string_5]) & ((-((this.Timer_7.Enabled > false) ? 1 : 0)) ? 1 : 0) & ((-((this.bool_6 > false) ? 1 : 0)) ? 1 : 0)) != 0)
		{
			GClass12.smethod_3(dictionary[this.string_6], false);
			this.method_27(0.025);
			GClass12.smethod_3(dictionary[this.string_6], true);
		}
	}

	// Token: 0x06000C08 RID: 3080 RVA: 0x00044AC0 File Offset: 0x00042CC0
	private void method_111(object sender, EventArgs e)
	{
		if (Operators.CompareString(this.FlatButton16.Text, "<", false) == 0)
		{
			this.FlatButton13.Text = "<";
			this.FlatButton13.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox3.Hide();
			this.FlatButton18.Text = "<";
			this.FlatButton18.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox12.Hide();
			this.FlatButton39.Text = "<";
			this.FlatButton39.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox14.Hide();
			this.FlatButton20.Text = "<";
			this.FlatButton20.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox19.Hide();
			this.FlatButton37.Text = "<";
			this.FlatButton37.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox15.Hide();
			this.FlatButton14.Text = "<";
			this.FlatButton14.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox17.Hide();
			this.FlatButton16.Text = ">";
			this.FlatButton16.Color_0 = this.PictureBox12.BackColor;
			this.FaderGroupBox18.Show();
			this.FlatButton22.Text = "<";
			this.FlatButton22.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox20.Hide();
		}
		else
		{
			this.FlatButton16.Text = "<";
			this.FlatButton16.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox18.Hide();
		}
		this.FlatButton22.Refresh();
		this.FlatButton20.Refresh();
		this.FlatButton16.Refresh();
		this.FlatButton14.Refresh();
		this.FlatButton39.Refresh();
		this.FlatButton13.Refresh();
		this.FlatButton37.Refresh();
		this.FlatButton18.Refresh();
	}

	// Token: 0x06000C09 RID: 3081 RVA: 0x00044D00 File Offset: 0x00042F00
	private void method_112(object sender, EventArgs e)
	{
		if (!(this.FlatButton38.Color_0 == this.color_0))
		{
			this.FlatButton38.Color_0 = this.PictureBox12.BackColor;
		}
		else
		{
			this.FlatButton38.Color_0 = Color.FromArgb(38, 38, 38);
		}
	}

	// Token: 0x06000C0A RID: 3082 RVA: 0x00044D54 File Offset: 0x00042F54
	private async void method_113(object sender, EventArgs e)
	{
		if (skeet.GetAsyncKeyState_2(Keys.LShiftKey) != 0)
		{
			skeet.Class30.smethod_0(this.FlatButton19, ">...<");
			skeet.Class30.smethod_1(this.Timer_14);
			skeet.Class30.smethod_2(this.FlatButton19);
		}
		else
		{
			string text = skeet.Class30.smethod_3(this.FlatButton19);
			skeet.Class30.smethod_0(this.FlatButton19, "Must use bind.");
			skeet.Class30.smethod_2(this.FlatButton19);
			await skeet.Class30.smethod_4(1000);
			this.FlatButton19.Text = text;
			this.FlatButton19.Refresh();
		}
	}

	// Token: 0x06000C0B RID: 3083 RVA: 0x00044D9C File Offset: 0x00042F9C
	private async void method_114(object sender, EventArgs e)
	{
		if (skeet.GetAsyncKeyState_2(Keys.LShiftKey) != 0)
		{
			skeet.Class31.smethod_0(this.FlatButton23, ">...<");
			skeet.Class31.smethod_1(this.Timer_19);
			skeet.Class31.smethod_2(this.FlatButton23);
		}
		else
		{
			string text = skeet.Class31.smethod_3(this.FlatButton23);
			skeet.Class31.smethod_0(this.FlatButton23, "Must use bind.");
			skeet.Class31.smethod_2(this.FlatButton23);
			await skeet.Class31.smethod_4(1000);
			this.FlatButton23.Text = text;
			this.FlatButton23.Refresh();
		}
	}

	// Token: 0x06000C0C RID: 3084 RVA: 0x00044DE4 File Offset: 0x00042FE4
	private async void method_115(object sender, EventArgs e)
	{
		if (skeet.GetAsyncKeyState_2(Keys.LShiftKey) == 0)
		{
			string text = skeet.Class32.smethod_3(this.FlatButton21);
			skeet.Class32.smethod_0(this.FlatButton21, "Must use bind.");
			skeet.Class32.smethod_2(this.FlatButton21);
			TaskAwaiter taskAwaiter = skeet.Class32.smethod_5(skeet.Class32.smethod_4(1000));
			if (!taskAwaiter.IsCompleted)
			{
				await taskAwaiter;
				TaskAwaiter taskAwaiter2;
				taskAwaiter = taskAwaiter2;
				taskAwaiter2 = default(TaskAwaiter);
			}
			taskAwaiter.GetResult();
			taskAwaiter = default(TaskAwaiter);
			this.FlatButton21.Text = text;
			this.FlatButton21.Refresh();
		}
		else
		{
			skeet.Class32.smethod_0(this.FlatButton21, ">...<");
			skeet.Class32.smethod_1(this.Timer_17);
			skeet.Class32.smethod_2(this.FlatButton21);
		}
	}

	// Token: 0x06000C0D RID: 3085 RVA: 0x00044E2C File Offset: 0x0004302C
	private async void method_116(object sender, EventArgs e)
	{
		string text = skeet.Class33.smethod_0(this.FlatButton25);
		skeet.Class33.smethod_1(this.FlatButton25, "Must use bind.");
		skeet.Class33.smethod_2(this.FlatButton25);
		await skeet.Class33.smethod_3(1000);
		this.FlatButton25.Text = text;
		this.FlatButton25.Refresh();
	}

	// Token: 0x06000C0E RID: 3086 RVA: 0x00044E74 File Offset: 0x00043074
	private void method_117(object sender, EventArgs e)
	{
		this.Timer_2.Start();
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (skeet.GetAsyncKeyState_2(Keys.A) != 0)
		{
			this.FlatButton19.Text = "[A] ThrowPot";
			this.string_12 = "A";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.B) != 0)
		{
			this.FlatButton19.Text = "[B] ThrowPot";
			this.string_12 = "B";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.C) != 0)
		{
			this.FlatButton19.Text = "[C] ThrowPot";
			this.string_12 = "C";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.D) != 0)
		{
			this.FlatButton19.Text = "[D] ThrowPot";
			this.string_12 = "D";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.E) != 0)
		{
			this.FlatButton19.Text = "[E] ThrowPot";
			this.string_12 = "E";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.F) != 0)
		{
			this.FlatButton19.Text = "[F] ThrowPot";
			this.string_12 = "F";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.G) != 0)
		{
			this.FlatButton19.Text = "[G] ThrowPot";
			this.string_12 = "G";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.H) != 0)
		{
			this.FlatButton19.Text = "[H] ThrowPot";
			this.string_12 = "H";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.I) != 0)
		{
			this.FlatButton19.Text = "[I] ThrowPot";
			this.string_12 = "I";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.J) != 0)
		{
			this.FlatButton19.Text = "[J] ThrowPot";
			this.string_12 = "J";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.K) != 0)
		{
			this.FlatButton19.Text = "[K] ThrowPot";
			this.string_12 = "K";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.L) != 0)
		{
			this.FlatButton19.Text = "[L] ThrowPot";
			this.string_12 = "L";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.M) != 0)
		{
			this.FlatButton19.Text = "[M] ThrowPot";
			this.string_12 = "M";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.N) != 0)
		{
			this.FlatButton19.Text = "[N] ThrowPot";
			this.string_12 = "N";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.O) != 0)
		{
			this.FlatButton19.Text = "[O] ThrowPot";
			this.string_12 = "O";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.P) != 0)
		{
			this.FlatButton19.Text = "[P] ThrowPot";
			this.string_12 = "P";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Q) != 0)
		{
			this.FlatButton19.Text = "[Q] ThrowPot";
			this.string_12 = "Q";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.R) != 0)
		{
			this.FlatButton19.Text = "[R] ThrowPot";
			this.string_12 = "R";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.S) != 0)
		{
			this.FlatButton19.Text = "[S] ThrowPot";
			this.string_12 = "S";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.T) != 0)
		{
			this.FlatButton19.Text = "[T] ThrowPot";
			this.string_12 = "T";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.U) != 0)
		{
			this.FlatButton19.Text = "[U] ThrowPot";
			this.string_12 = "U";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.V) != 0)
		{
			this.FlatButton19.Text = "[V] ThrowPot";
			this.string_12 = "V";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.X) != 0)
		{
			this.FlatButton19.Text = "[X] ThrowPot";
			this.string_12 = "X";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Y) != 0)
		{
			this.FlatButton19.Text = "[Y] ThrowPot";
			this.string_12 = "Y";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Z) != 0)
		{
			this.FlatButton19.Text = "[Z] ThrowPot";
			this.string_12 = "Z";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.W) != 0)
		{
			this.FlatButton19.Text = "[W] ThrowPot";
			this.string_12 = "W";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Escape) != 0)
		{
			this.FlatButton19.Text = "ThrowPot";
			this.string_12 = "";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState(1048576) != 0)
		{
			this.FlatButton19.Text = "ThrowPot";
			this.string_12 = "";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
		if (skeet.GetAsyncKeyState(2097152) != 0)
		{
			this.FlatButton19.Text = "ThrowPot";
			this.string_12 = "";
			this.FlatButton19.Refresh();
			this.Timer_14.Stop();
		}
	}

	// Token: 0x06000C0F RID: 3087 RVA: 0x00045838 File Offset: 0x00043A38
	private async void method_118(object sender, EventArgs e)
	{
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (!skeet.Class34.smethod_0(this.method_0(), this.FlatTextBox6.System.Windows.Forms.Control.Text))
		{
			this.Timer_12.Stop();
		}
		else if (this.int_1 == 0)
		{
			if (this.int_5 == 0)
			{
				this.int_5 = skeet.Class34.smethod_2(skeet.Class34.smethod_1(this.ThirteenComboBox4));
			}
			this.int_1 = 1;
			if (this.int_5 == 1)
			{
				GClass12.smethod_3(Keys.D1, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D1, true);
			}
			if (this.int_5 == 2)
			{
				GClass12.smethod_3(Keys.D2, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D2, true);
			}
			if (this.int_5 == 3)
			{
				GClass12.smethod_3(Keys.D3, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D3, true);
			}
			if (this.int_5 == 4)
			{
				GClass12.smethod_3(Keys.D4, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D4, true);
			}
			if (this.int_5 == 5)
			{
				GClass12.smethod_3(Keys.D5, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D5, true);
			}
			if (this.int_5 == 6)
			{
				GClass12.smethod_3(Keys.D6, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D6, true);
			}
			if (this.int_5 == 7)
			{
				GClass12.smethod_3(Keys.D7, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D7, true);
			}
			if (this.int_5 == 8)
			{
				GClass12.smethod_3(Keys.D8, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D8, true);
			}
			if (this.int_5 == 9)
			{
				GClass12.smethod_3(Keys.D9, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D9, true);
			}
			if (this.int_5 == 10)
			{
				GClass12.smethod_3(Keys.D0, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D0, true);
			}
			this.method_27(0.03);
			skeet.mouse_event(8, 0, 0, 0, 0);
			this.method_27(0.05);
			skeet.mouse_event(16, 0, 0, 0, 0);
			this.method_27(0.05);
			if (this.FlatCheckBox16.Boolean_0)
			{
				GClass12.smethod_3(dictionary[this.string_4], false);
				this.method_27(0.025);
				GClass12.smethod_3(dictionary[this.string_4], true);
			}
			this.method_27(0.005);
			if (skeet.Class34.smethod_3(skeet.Class34.smethod_1(this.ThirteenComboBox3), "1", false) == 0)
			{
				GClass12.smethod_3(Keys.D1, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D1, true);
			}
			if (skeet.Class34.smethod_3(skeet.Class34.smethod_1(this.ThirteenComboBox3), "2", false) == 0)
			{
				GClass12.smethod_3(Keys.D2, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D2, true);
			}
			if (skeet.Class34.smethod_3(skeet.Class34.smethod_1(this.ThirteenComboBox3), "3", false) == 0)
			{
				GClass12.smethod_3(Keys.D3, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D3, true);
			}
			if (skeet.Class34.smethod_3(skeet.Class34.smethod_1(this.ThirteenComboBox3), "4", false) == 0)
			{
				GClass12.smethod_3(Keys.D4, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D4, true);
			}
			if (skeet.Class34.smethod_3(skeet.Class34.smethod_1(this.ThirteenComboBox3), "5", false) == 0)
			{
				GClass12.smethod_3(Keys.D5, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D5, true);
			}
			if (skeet.Class34.smethod_3(skeet.Class34.smethod_1(this.ThirteenComboBox3), "6", false) == 0)
			{
				GClass12.smethod_3(Keys.D6, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D6, true);
			}
			if (skeet.Class34.smethod_3(skeet.Class34.smethod_1(this.ThirteenComboBox3), "7", false) == 0)
			{
				GClass12.smethod_3(Keys.D7, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D7, true);
			}
			if (skeet.Class34.smethod_3(skeet.Class34.smethod_1(this.ThirteenComboBox3), "8", false) == 0)
			{
				GClass12.smethod_3(Keys.D8, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D8, true);
			}
			if (skeet.Class34.smethod_3(skeet.Class34.smethod_1(this.ThirteenComboBox3), "9", false) == 0)
			{
				GClass12.smethod_3(Keys.D9, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D9, true);
			}
			checked
			{
				this.int_5++;
			}
			if ((double)this.int_5 == skeet.Class34.smethod_4(skeet.Class34.smethod_1(this.ThirteenComboBox5)) + 1.0)
			{
				this.int_5 = skeet.Class34.smethod_2(skeet.Class34.smethod_1(this.ThirteenComboBox4));
			}
			await skeet.Class34.smethod_5(100);
			this.int_1 = 0;
			this.Timer_12.Stop();
		}
	}

	// Token: 0x06000C10 RID: 3088 RVA: 0x00045880 File Offset: 0x00043A80
	private void method_119(object sender, EventArgs e)
	{
		this.Timer_2.Start();
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (skeet.GetAsyncKeyState_2(Keys.A) != 0)
		{
			this.FlatButton15.Text = "[A] WTap";
			this.string_13 = "A";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.B) != 0)
		{
			this.FlatButton15.Text = "[B] WTap";
			this.string_13 = "B";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.C) != 0)
		{
			this.FlatButton15.Text = "[C] WTap";
			this.string_13 = "C";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.D) != 0)
		{
			this.FlatButton15.Text = "[D] WTap";
			this.string_13 = "D";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.E) != 0)
		{
			this.FlatButton15.Text = "[E] WTap";
			this.string_13 = "E";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.F) != 0)
		{
			this.FlatButton15.Text = "[F] WTap";
			this.string_13 = "F";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.G) != 0)
		{
			this.FlatButton15.Text = "[G] WTap";
			this.string_13 = "G";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.H) != 0)
		{
			this.FlatButton15.Text = "[H] WTap";
			this.string_13 = "H";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.I) != 0)
		{
			this.FlatButton15.Text = "[I] WTap";
			this.string_13 = "I";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.J) != 0)
		{
			this.FlatButton15.Text = "[J] WTap";
			this.string_13 = "J";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.K) != 0)
		{
			this.FlatButton15.Text = "[K] WTap";
			this.string_13 = "K";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.L) != 0)
		{
			this.FlatButton15.Text = "[L] WTap";
			this.string_13 = "L";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.M) != 0)
		{
			this.FlatButton15.Text = "[M] WTap";
			this.string_13 = "M";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.N) != 0)
		{
			this.FlatButton15.Text = "[N] WTap";
			this.string_13 = "N";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.O) != 0)
		{
			this.FlatButton15.Text = "[O] WTap";
			this.string_13 = "O";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.P) != 0)
		{
			this.FlatButton15.Text = "[P] WTap";
			this.string_13 = "P";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Q) != 0)
		{
			this.FlatButton15.Text = "[Q] WTap";
			this.string_13 = "Q";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.R) != 0)
		{
			this.FlatButton15.Text = "[R] WTap";
			this.string_13 = "R";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.S) != 0)
		{
			this.FlatButton15.Text = "[S] WTap";
			this.string_13 = "S";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.T) != 0)
		{
			this.FlatButton15.Text = "[T] WTap";
			this.string_13 = "T";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.U) != 0)
		{
			this.FlatButton15.Text = "[U] WTap";
			this.string_13 = "U";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.V) != 0)
		{
			this.FlatButton15.Text = "[V] WTap";
			this.string_13 = "V";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.X) != 0)
		{
			this.FlatButton15.Text = "[X] WTap";
			this.string_13 = "X";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Y) != 0)
		{
			this.FlatButton15.Text = "[Y] WTap";
			this.string_13 = "Y";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Z) != 0)
		{
			this.FlatButton15.Text = "[Z] WTap";
			this.string_13 = "Z";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.W) != 0)
		{
			this.FlatButton15.Text = "[W] WTap";
			this.string_13 = "W";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Escape) != 0)
		{
			this.FlatButton15.Text = "WTap";
			this.string_13 = "";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState(1048576) != 0)
		{
			this.FlatButton15.Text = "WTap";
			this.string_13 = "";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
		if (skeet.GetAsyncKeyState(2097152) != 0)
		{
			this.FlatButton15.Text = "WTap";
			this.string_13 = "";
			this.FlatButton15.Refresh();
			this.Timer_15.Stop();
		}
	}

	// Token: 0x06000C11 RID: 3089 RVA: 0x00046244 File Offset: 0x00044444
	private void method_120(object sender, EventArgs e)
	{
		this.Timer_2.Start();
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (skeet.GetAsyncKeyState_2(Keys.A) != 0)
		{
			this.FlatButton17.Text = "[A] STap";
			this.string_14 = "A";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.B) != 0)
		{
			this.FlatButton17.Text = "[B] STap";
			this.string_14 = "B";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.C) != 0)
		{
			this.FlatButton17.Text = "[C] STap";
			this.string_14 = "C";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.D) != 0)
		{
			this.FlatButton17.Text = "[D] STap";
			this.string_14 = "D";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.E) != 0)
		{
			this.FlatButton17.Text = "[E] STap";
			this.string_14 = "E";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.F) != 0)
		{
			this.FlatButton17.Text = "[F] STap";
			this.string_14 = "F";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.G) != 0)
		{
			this.FlatButton17.Text = "[G] STap";
			this.string_14 = "G";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.H) != 0)
		{
			this.FlatButton17.Text = "[H] STap";
			this.string_14 = "H";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.I) != 0)
		{
			this.FlatButton17.Text = "[I] STap";
			this.string_14 = "I";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.J) != 0)
		{
			this.FlatButton17.Text = "[J] STap";
			this.string_14 = "J";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.K) != 0)
		{
			this.FlatButton17.Text = "[K] STap";
			this.string_14 = "K";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.L) != 0)
		{
			this.FlatButton17.Text = "[L] STap";
			this.string_14 = "L";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.M) != 0)
		{
			this.FlatButton17.Text = "[M] STap";
			this.string_14 = "M";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.N) != 0)
		{
			this.FlatButton17.Text = "[N] STap";
			this.string_14 = "N";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.O) != 0)
		{
			this.FlatButton17.Text = "[O] STap";
			this.string_14 = "O";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.P) != 0)
		{
			this.FlatButton17.Text = "[P] STap";
			this.string_14 = "P";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Q) != 0)
		{
			this.FlatButton17.Text = "[Q] STap";
			this.string_14 = "Q";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.R) != 0)
		{
			this.FlatButton17.Text = "[R] STap";
			this.string_14 = "R";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.S) != 0)
		{
			this.FlatButton17.Text = "[S] STap";
			this.string_14 = "S";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.T) != 0)
		{
			this.FlatButton17.Text = "[T] STap";
			this.string_14 = "T";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.U) != 0)
		{
			this.FlatButton17.Text = "[U] STap";
			this.string_14 = "U";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.V) != 0)
		{
			this.FlatButton17.Text = "[V] STap";
			this.string_14 = "V";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.X) != 0)
		{
			this.FlatButton17.Text = "[X] STap";
			this.string_14 = "X";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Y) != 0)
		{
			this.FlatButton17.Text = "[Y] STap";
			this.string_14 = "Y";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Z) != 0)
		{
			this.FlatButton17.Text = "[Z] STap";
			this.string_14 = "Z";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.W) != 0)
		{
			this.FlatButton17.Text = "[W] STap";
			this.string_14 = "W";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Escape) != 0)
		{
			this.FlatButton17.Text = "STap";
			this.string_14 = "";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState(1048576) != 0)
		{
			this.FlatButton17.Text = "STap";
			this.string_14 = "";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
		if (skeet.GetAsyncKeyState(2097152) != 0)
		{
			this.FlatButton17.Text = "STap";
			this.string_14 = "";
			this.FlatButton17.Refresh();
			this.Timer_16.Stop();
		}
	}

	// Token: 0x06000C12 RID: 3090 RVA: 0x00046C08 File Offset: 0x00044E08
	private void method_121(object sender, EventArgs e)
	{
		this.Timer_2.Start();
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (skeet.GetAsyncKeyState_2(Keys.A) != 0)
		{
			this.FlatButton21.Text = "[A] ThrowPearl";
			this.string_15 = "A";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.B) != 0)
		{
			this.FlatButton21.Text = "[B] ThrowPearl";
			this.string_15 = "B";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.C) != 0)
		{
			this.FlatButton21.Text = "[C] ThrowPearl";
			this.string_15 = "C";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.D) != 0)
		{
			this.FlatButton21.Text = "[D] ThrowPearl";
			this.string_15 = "D";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.E) != 0)
		{
			this.FlatButton21.Text = "[E] ThrowPearl";
			this.string_15 = "E";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.F) != 0)
		{
			this.FlatButton21.Text = "[F] ThrowPearl";
			this.string_15 = "F";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.G) != 0)
		{
			this.FlatButton21.Text = "[G] ThrowPearl";
			this.string_15 = "G";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.H) != 0)
		{
			this.FlatButton21.Text = "[H] ThrowPearl";
			this.string_15 = "H";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.I) != 0)
		{
			this.FlatButton21.Text = "[I] ThrowPearl";
			this.string_15 = "I";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.J) != 0)
		{
			this.FlatButton21.Text = "[J] ThrowPearl";
			this.string_15 = "J";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.K) != 0)
		{
			this.FlatButton21.Text = "[K] ThrowPearl";
			this.string_15 = "K";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.L) != 0)
		{
			this.FlatButton21.Text = "[L] ThrowPearl";
			this.string_15 = "L";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.M) != 0)
		{
			this.FlatButton21.Text = "[M] ThrowPearl";
			this.string_15 = "M";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.N) != 0)
		{
			this.FlatButton21.Text = "[N] ThrowPearl";
			this.string_15 = "N";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.O) != 0)
		{
			this.FlatButton21.Text = "[O] ThrowPearl";
			this.string_15 = "O";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.P) != 0)
		{
			this.FlatButton21.Text = "[P] ThrowPearl";
			this.string_15 = "P";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Q) != 0)
		{
			this.FlatButton21.Text = "[Q] ThrowPearl";
			this.string_15 = "Q";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.R) != 0)
		{
			this.FlatButton21.Text = "[R] ThrowPearl";
			this.string_15 = "R";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.S) != 0)
		{
			this.FlatButton21.Text = "[S] ThrowPearl";
			this.string_15 = "S";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.T) != 0)
		{
			this.FlatButton21.Text = "[T] ThrowPearl";
			this.string_15 = "T";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.U) != 0)
		{
			this.FlatButton21.Text = "[U] ThrowPearl";
			this.string_15 = "U";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.V) != 0)
		{
			this.FlatButton21.Text = "[V] ThrowPearl";
			this.string_15 = "V";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.X) != 0)
		{
			this.FlatButton21.Text = "[X] ThrowPearl";
			this.string_15 = "X";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Y) != 0)
		{
			this.FlatButton21.Text = "[Y] ThrowPearl";
			this.string_15 = "Y";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Z) != 0)
		{
			this.FlatButton21.Text = "[Z] ThrowPearl";
			this.string_15 = "Z";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.W) != 0)
		{
			this.FlatButton21.Text = "[W] ThrowPearl";
			this.string_15 = "W";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Escape) != 0)
		{
			this.FlatButton21.Text = "ThrowPearl";
			this.string_15 = "";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState(1048576) != 0)
		{
			this.FlatButton21.Text = "ThrowPearl";
			this.string_15 = "";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
		if (skeet.GetAsyncKeyState(2097152) != 0)
		{
			this.FlatButton21.Text = "ThrowPearl";
			this.string_15 = "";
			this.FlatButton21.Refresh();
			this.Timer_17.Stop();
		}
	}

	// Token: 0x06000C13 RID: 3091 RVA: 0x000475CC File Offset: 0x000457CC
	private async void method_122(object sender, EventArgs e)
	{
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (!skeet.Class35.smethod_0(this.method_0(), this.FlatTextBox6.System.Windows.Forms.Control.Text))
		{
			this.Timer_18.Stop();
		}
		else if (this.int_1 == 0)
		{
			this.int_1 = 1;
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox10), "1", false) == 0)
			{
				GClass12.smethod_3(Keys.D1, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D1, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox10), "2", false) == 0)
			{
				GClass12.smethod_3(Keys.D2, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D2, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox10), "3", false) == 0)
			{
				GClass12.smethod_3(Keys.D3, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D3, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox10), "4", false) == 0)
			{
				GClass12.smethod_3(Keys.D4, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D4, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox10), "5", false) == 0)
			{
				GClass12.smethod_3(Keys.D5, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D5, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox10), "6", false) == 0)
			{
				GClass12.smethod_3(Keys.D6, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D6, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox10), "7", false) == 0)
			{
				GClass12.smethod_3(Keys.D7, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D7, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox10), "8", false) == 0)
			{
				GClass12.smethod_3(Keys.D8, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D8, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox10), "9", false) == 0)
			{
				GClass12.smethod_3(Keys.D9, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D9, true);
			}
			this.method_27(0.03);
			skeet.mouse_event(8, 0, 0, 0, 0);
			this.method_27(0.05);
			skeet.mouse_event(16, 0, 0, 0, 0);
			this.method_27(0.05);
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox9), "1", false) == 0)
			{
				GClass12.smethod_3(Keys.D1, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D1, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox9), "2", false) == 0)
			{
				GClass12.smethod_3(Keys.D2, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D2, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox9), "3", false) == 0)
			{
				GClass12.smethod_3(Keys.D3, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D3, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox9), "4", false) == 0)
			{
				GClass12.smethod_3(Keys.D4, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D4, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox9), "5", false) == 0)
			{
				GClass12.smethod_3(Keys.D5, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D5, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox9), "6", false) == 0)
			{
				GClass12.smethod_3(Keys.D6, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D6, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox9), "7", false) == 0)
			{
				GClass12.smethod_3(Keys.D7, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D7, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox9), "8", false) == 0)
			{
				GClass12.smethod_3(Keys.D8, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D8, true);
			}
			if (skeet.Class35.smethod_2(skeet.Class35.smethod_1(this.ThirteenComboBox9), "9", false) == 0)
			{
				GClass12.smethod_3(Keys.D9, false);
				this.method_27(0.025);
				GClass12.smethod_3(Keys.D9, true);
			}
			await skeet.Class35.smethod_3(200);
			this.int_1 = 0;
			this.Timer_18.Stop();
		}
	}

	// Token: 0x06000C14 RID: 3092 RVA: 0x00047614 File Offset: 0x00045814
	private void method_123(object sender, EventArgs e)
	{
		if (Operators.CompareString(this.FlatButton22.Text, "<", false) != 0)
		{
			this.FlatButton22.Text = "<";
			this.FlatButton22.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox20.Hide();
		}
		else
		{
			this.FlatButton13.Text = "<";
			this.FlatButton13.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox3.Hide();
			this.FlatButton18.Text = "<";
			this.FlatButton18.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox12.Hide();
			this.FlatButton39.Text = "<";
			this.FlatButton39.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox14.Hide();
			this.FlatButton20.Text = "<";
			this.FlatButton20.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox19.Hide();
			this.FlatButton37.Text = "<";
			this.FlatButton37.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox15.Hide();
			this.FlatButton14.Text = "<";
			this.FlatButton14.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox17.Hide();
			this.FlatButton16.Text = "<";
			this.FlatButton16.Color_0 = Color.FromArgb(38, 38, 38);
			this.FaderGroupBox18.Hide();
			this.FlatButton22.Text = ">";
			this.FlatButton22.Color_0 = this.PictureBox12.BackColor;
			this.FaderGroupBox20.Show();
		}
		this.FlatButton22.Refresh();
		this.FlatButton20.Refresh();
		this.FlatButton16.Refresh();
		this.FlatButton14.Refresh();
		this.FlatButton39.Refresh();
		this.FlatButton13.Refresh();
		this.FlatButton37.Refresh();
		this.FlatButton18.Refresh();
	}

	// Token: 0x06000C15 RID: 3093 RVA: 0x00047854 File Offset: 0x00045A54
	private void method_124(object sender, EventArgs e)
	{
	}

	// Token: 0x06000C16 RID: 3094 RVA: 0x00047858 File Offset: 0x00045A58
	private void method_125(object sender, EventArgs e)
	{
		this.Timer_2.Start();
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (skeet.GetAsyncKeyState_2(Keys.A) != 0)
		{
			this.FlatButton23.Text = "[A] Voice Control";
			this.string_16 = "A";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.B) != 0)
		{
			this.FlatButton23.Text = "[B] Voice Control";
			this.string_16 = "B";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.C) != 0)
		{
			this.FlatButton23.Text = "[C] Voice Control";
			this.string_16 = "C";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.D) != 0)
		{
			this.FlatButton23.Text = "[D] Voice Control";
			this.string_16 = "D";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.E) != 0)
		{
			this.FlatButton23.Text = "[E] Voice Control";
			this.string_16 = "E";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.F) != 0)
		{
			this.FlatButton23.Text = "[F] Voice Control";
			this.string_16 = "F";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.G) != 0)
		{
			this.FlatButton23.Text = "[G] Voice Control";
			this.string_16 = "G";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.H) != 0)
		{
			this.FlatButton23.Text = "[H] Voice Control";
			this.string_16 = "H";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.I) != 0)
		{
			this.FlatButton23.Text = "[I] Voice Control";
			this.string_16 = "I";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.J) != 0)
		{
			this.FlatButton23.Text = "[J] Voice Control";
			this.string_16 = "J";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.K) != 0)
		{
			this.FlatButton23.Text = "[K] Voice Control";
			this.string_16 = "K";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.L) != 0)
		{
			this.FlatButton23.Text = "[L] Voice Control";
			this.string_16 = "L";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.M) != 0)
		{
			this.FlatButton23.Text = "[M] Voice Control";
			this.string_16 = "M";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.N) != 0)
		{
			this.FlatButton23.Text = "[N] Voice Control";
			this.string_16 = "N";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.O) != 0)
		{
			this.FlatButton23.Text = "[O] Voice Control";
			this.string_16 = "O";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.P) != 0)
		{
			this.FlatButton23.Text = "[P] Voice Control";
			this.string_16 = "P";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Q) != 0)
		{
			this.FlatButton23.Text = "[Q] Voice Control";
			this.string_16 = "Q";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.R) != 0)
		{
			this.FlatButton23.Text = "[R] Voice Control";
			this.string_16 = "R";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.S) != 0)
		{
			this.FlatButton23.Text = "[S] Voice Control";
			this.string_16 = "S";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.T) != 0)
		{
			this.FlatButton23.Text = "[T] Voice Control";
			this.string_16 = "T";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.U) != 0)
		{
			this.FlatButton23.Text = "[U] Voice Control";
			this.string_16 = "U";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.V) != 0)
		{
			this.FlatButton23.Text = "[V] Voice Control";
			this.string_16 = "V";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.X) != 0)
		{
			this.FlatButton23.Text = "[X] Voice Control";
			this.string_16 = "X";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Y) != 0)
		{
			this.FlatButton23.Text = "[Y] Voice Control";
			this.string_16 = "Y";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Z) != 0)
		{
			this.FlatButton23.Text = "[Z] Voice Control";
			this.string_16 = "Z";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.W) != 0)
		{
			this.FlatButton23.Text = "[W] Voice Control";
			this.string_16 = "W";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Escape) != 0)
		{
			this.FlatButton23.Text = "Voice Control";
			this.string_16 = "";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState(1048576) != 0)
		{
			this.FlatButton23.Text = "Voice Control";
			this.string_16 = "";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
		if (skeet.GetAsyncKeyState(2097152) != 0)
		{
			this.FlatButton23.Text = "Voice Control";
			this.string_16 = "";
			this.FlatButton23.Refresh();
			this.Timer_19.Stop();
		}
	}

	// Token: 0x06000C17 RID: 3095 RVA: 0x0004821C File Offset: 0x0004641C
	private async void method_126(object sender, EventArgs e)
	{
		if (skeet.GetAsyncKeyState_2(Keys.LShiftKey) != 0)
		{
			skeet.Class36.smethod_0(this.FlatButton31, ">...<");
			skeet.Class36.smethod_1(this.Timer_5);
			skeet.Class36.smethod_2(this.FlatButton31);
		}
		else
		{
			string text = skeet.Class36.smethod_3(this.FlatButton31);
			skeet.Class36.smethod_0(this.FlatButton31, "Must use bind.");
			skeet.Class36.smethod_2(this.FlatButton31);
			await skeet.Class36.smethod_4(1000);
			this.FlatButton31.Text = text;
			this.FlatButton31.Refresh();
		}
	}

	// Token: 0x06000C18 RID: 3096 RVA: 0x00048264 File Offset: 0x00046464
	private void method_127(object sender, EventArgs e)
	{
		this.Timer_2.Start();
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (skeet.GetAsyncKeyState_2(Keys.A) != 0)
		{
			this.FlatButton31.Text = "[A] Hide";
			this.string_17 = "A";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.B) != 0)
		{
			this.FlatButton31.Text = "[B] Hide";
			this.string_17 = "B";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.C) != 0)
		{
			this.FlatButton31.Text = "[C] Hide";
			this.string_17 = "C";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.D) != 0)
		{
			this.FlatButton31.Text = "[D] Hide";
			this.string_17 = "D";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.E) != 0)
		{
			this.FlatButton31.Text = "[E] Hide";
			this.string_17 = "E";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.F) != 0)
		{
			this.FlatButton31.Text = "[F] Hide";
			this.string_17 = "F";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.G) != 0)
		{
			this.FlatButton31.Text = "[G] Hide";
			this.string_17 = "G";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.H) != 0)
		{
			this.FlatButton31.Text = "[H] Hide";
			this.string_17 = "H";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.I) != 0)
		{
			this.FlatButton31.Text = "[I] Hide";
			this.string_17 = "I";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.J) != 0)
		{
			this.FlatButton31.Text = "[J] Hide";
			this.string_17 = "J";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.K) != 0)
		{
			this.FlatButton31.Text = "[K] Hide";
			this.string_17 = "K";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.L) != 0)
		{
			this.FlatButton31.Text = "[L] Hide";
			this.string_17 = "L";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.M) != 0)
		{
			this.FlatButton31.Text = "[M] Hide";
			this.string_17 = "M";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.N) != 0)
		{
			this.FlatButton31.Text = "[N] Hide";
			this.string_17 = "N";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.O) != 0)
		{
			this.FlatButton31.Text = "[O] Hide";
			this.string_17 = "O";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.P) != 0)
		{
			this.FlatButton31.Text = "[P] Hide";
			this.string_17 = "P";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Q) != 0)
		{
			this.FlatButton31.Text = "[Q] Hide";
			this.string_17 = "Q";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.R) != 0)
		{
			this.FlatButton31.Text = "[R] Hide";
			this.string_17 = "R";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.S) != 0)
		{
			this.FlatButton31.Text = "[S] Hide";
			this.string_17 = "S";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.T) != 0)
		{
			this.FlatButton31.Text = "[T] Hide";
			this.string_17 = "T";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.U) != 0)
		{
			this.FlatButton31.Text = "[U] Hide";
			this.string_17 = "U";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.V) != 0)
		{
			this.FlatButton31.Text = "[V] Hide";
			this.string_17 = "V";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.X) != 0)
		{
			this.FlatButton31.Text = "[X] Hide";
			this.string_17 = "X";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Y) != 0)
		{
			this.FlatButton31.Text = "[Y] Hide";
			this.string_17 = "Y";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Z) != 0)
		{
			this.FlatButton31.Text = "[Z] Hide";
			this.string_17 = "Z";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.W) != 0)
		{
			this.FlatButton31.Text = "[W] Hide";
			this.string_17 = "W";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Escape) != 0)
		{
			this.FlatButton31.Text = "Hide";
			this.string_17 = "";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState(1048576) != 0)
		{
			this.FlatButton31.Text = "Hide";
			this.string_17 = "";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
		if (skeet.GetAsyncKeyState(2097152) != 0)
		{
			this.FlatButton31.Text = "Hide";
			this.string_17 = "";
			this.FlatButton31.Refresh();
			this.Timer_5.Stop();
		}
	}

	// Token: 0x06000C19 RID: 3097 RVA: 0x00048C28 File Offset: 0x00046E28
	private void method_128(object sender, EventArgs e)
	{
		if (skeet.GetAsyncKeyState_2(Keys.LShiftKey) == 0)
		{
			this.Timer_1.Start();
		}
		else
		{
			this.FlatButton32.Text = ">...<";
			this.Timer_20.Start();
			this.FlatButton32.Refresh();
		}
	}

	// Token: 0x06000C1A RID: 3098 RVA: 0x00048C78 File Offset: 0x00046E78
	private async void method_129(object sender, EventArgs e)
	{
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (skeet.GetAsyncKeyState_2(Keys.A) != 0)
		{
			skeet.Class37.smethod_0(this.FlatButton32, "[A] Destruct");
			this.string_18 = "A";
			skeet.Class37.smethod_1(this.FlatButton32);
			skeet.Class37.smethod_2(this.Timer_20);
			await skeet.Class37.smethod_3(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.B) != 0)
		{
			this.FlatButton32.Text = "[B] Destruct";
			this.string_18 = "B";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.C) != 0)
		{
			this.FlatButton32.Text = "[C] Destruct";
			this.string_18 = "C";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.D) != 0)
		{
			this.FlatButton32.Text = "[D] Destruct";
			this.string_18 = "D";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.E) != 0)
		{
			this.FlatButton32.Text = "[E] Destruct";
			this.string_18 = "E";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.F) != 0)
		{
			this.FlatButton32.Text = "[F] Destruct";
			this.string_18 = "F";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.G) != 0)
		{
			this.FlatButton32.Text = "[G] Destruct";
			this.string_18 = "G";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.H) != 0)
		{
			this.FlatButton32.Text = "[H] Destruct";
			this.string_18 = "H";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.I) != 0)
		{
			this.FlatButton32.Text = "[I] Destruct";
			this.string_18 = "I";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.J) != 0)
		{
			this.FlatButton32.Text = "[J] Destruct";
			this.string_18 = "J";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.K) != 0)
		{
			this.FlatButton32.Text = "[K] Destruct";
			this.string_18 = "K";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.L) != 0)
		{
			this.FlatButton32.Text = "[L] Destruct";
			this.string_18 = "L";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.M) != 0)
		{
			this.FlatButton32.Text = "[M] Destruct";
			this.string_18 = "M";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.N) != 0)
		{
			this.FlatButton32.Text = "[N] Destruct";
			this.string_18 = "N";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.O) != 0)
		{
			this.FlatButton32.Text = "[O] Destruct";
			this.string_18 = "O";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.P) != 0)
		{
			this.FlatButton32.Text = "[P] Destruct";
			this.string_18 = "P";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Q) != 0)
		{
			this.FlatButton32.Text = "[Q] Destruct";
			this.string_18 = "Q";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.R) != 0)
		{
			this.FlatButton32.Text = "[R] Destruct";
			this.string_18 = "R";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.S) != 0)
		{
			this.FlatButton32.Text = "[S] Destruct";
			this.string_18 = "S";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.T) != 0)
		{
			this.FlatButton32.Text = "[T] Destruct";
			this.string_18 = "T";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.U) != 0)
		{
			this.FlatButton32.Text = "[U] Destruct";
			this.string_18 = "U";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.V) != 0)
		{
			this.FlatButton32.Text = "[V] Destruct";
			this.string_18 = "V";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.X) != 0)
		{
			this.FlatButton32.Text = "[X] Destruct";
			this.string_18 = "X";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Y) != 0)
		{
			this.FlatButton32.Text = "[Y] Destruct";
			this.string_18 = "Y";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Z) != 0)
		{
			this.FlatButton32.Text = "[Z] Destruct";
			this.string_18 = "Z";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.W) != 0)
		{
			this.FlatButton32.Text = "[W] Destruct";
			this.string_18 = "W";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Start();
		}
		if (skeet.GetAsyncKeyState_2(Keys.Escape) != 0)
		{
			this.FlatButton32.Text = "Destruct";
			this.string_18 = "";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Stop();
		}
		if (skeet.GetAsyncKeyState(1048576) != 0)
		{
			this.FlatButton32.Text = "Destruct";
			this.string_18 = "";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Stop();
		}
		if (skeet.GetAsyncKeyState(2097152) != 0)
		{
			this.FlatButton32.Text = "Destruct";
			this.string_18 = "";
			this.FlatButton32.Refresh();
			this.Timer_20.Stop();
			await Task.Delay(200);
			this.Timer_21.Stop();
		}
	}

	// Token: 0x06000C1B RID: 3099 RVA: 0x00048CC0 File Offset: 0x00046EC0
	private void method_130(object sender, EventArgs e)
	{
		Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
		dictionary.Add("A", Keys.A);
		dictionary.Add("B", Keys.B);
		dictionary.Add("C", Keys.C);
		dictionary.Add("D", Keys.D);
		dictionary.Add("E", Keys.E);
		dictionary.Add("F", Keys.F);
		dictionary.Add("G", Keys.G);
		dictionary.Add("H", Keys.H);
		dictionary.Add("I", Keys.I);
		dictionary.Add("J", Keys.J);
		dictionary.Add("K", Keys.K);
		dictionary.Add("L", Keys.L);
		dictionary.Add("M", Keys.M);
		dictionary.Add("N", Keys.N);
		dictionary.Add("O", Keys.O);
		dictionary.Add("P", Keys.P);
		dictionary.Add("Q", Keys.Q);
		dictionary.Add("R", Keys.R);
		dictionary.Add("S", Keys.S);
		dictionary.Add("T", Keys.T);
		dictionary.Add("U", Keys.U);
		dictionary.Add("V", Keys.V);
		dictionary.Add("W", Keys.W);
		dictionary.Add("X", Keys.X);
		dictionary.Add("Y", Keys.Y);
		dictionary.Add("Z", Keys.Z);
		dictionary.Add("a", Keys.A);
		dictionary.Add("b", Keys.B);
		dictionary.Add("c", Keys.C);
		dictionary.Add("d", Keys.D);
		dictionary.Add("e", Keys.E);
		dictionary.Add("f", Keys.F);
		dictionary.Add("g", Keys.G);
		dictionary.Add("h", Keys.H);
		dictionary.Add("i", Keys.I);
		dictionary.Add("j", Keys.J);
		dictionary.Add("k", Keys.K);
		dictionary.Add("l", Keys.L);
		dictionary.Add("m", Keys.M);
		dictionary.Add("n", Keys.N);
		dictionary.Add("o", Keys.O);
		dictionary.Add("p", Keys.P);
		dictionary.Add("q", Keys.Q);
		dictionary.Add("r", Keys.R);
		dictionary.Add("s", Keys.S);
		dictionary.Add("t", Keys.T);
		dictionary.Add("u", Keys.U);
		dictionary.Add("v", Keys.V);
		dictionary.Add("w", Keys.W);
		dictionary.Add("x", Keys.X);
		dictionary.Add("y", Keys.Y);
		dictionary.Add("z", Keys.Z);
		dictionary.Add("R-SHIFT", Keys.RShiftKey);
		if (Operators.CompareString(this.string_18, "", false) != 0 && skeet.GetAsyncKeyState_2(dictionary[this.string_18]) != 0)
		{
			this.Timer_1.Start();
		}
	}

	// Token: 0x06000C1C RID: 3100 RVA: 0x00048FC0 File Offset: 0x000471C0
	private void method_131(object sender, EventArgs e)
	{
		if (Operators.CompareString("lolk", "lolk", false) != 0)
		{
			Application.Exit();
		}
	}

	// Token: 0x06000C1D RID: 3101 RVA: 0x00048FDC File Offset: 0x000471DC
	private void method_132(object sender, EventArgs e)
	{
		checked
		{
			if (this.int_18 != 1)
			{
				this.int_15++;
			}
			else
			{
				this.int_20 = 0;
				this.int_19 = 0;
				this.Timer_24.Start();
				this.Timer_23.Stop();
			}
			if (this.int_15 > 255)
			{
				this.int_18 = 1;
				this.int_15 = 255;
			}
			this.PictureBox16.BackColor = Color.FromArgb(this.int_15, this.int_16, this.int_17);
		}
	}

	// Token: 0x06000C1E RID: 3102 RVA: 0x0004906C File Offset: 0x0004726C
	private void method_133(object sender, EventArgs e)
	{
		checked
		{
			if (this.int_19 == 1)
			{
				this.int_20 = 0;
				this.int_18 = 0;
				this.Timer_25.Start();
				this.Timer_24.Stop();
			}
			else
			{
				this.int_17--;
			}
			if (this.int_17 < 0)
			{
				this.int_19 = 1;
				this.int_17 = 0;
			}
			this.PictureBox16.BackColor = Color.FromArgb(this.int_15, this.int_16, this.int_17);
		}
	}

	// Token: 0x06000C1F RID: 3103 RVA: 0x000490F4 File Offset: 0x000472F4
	private void method_134(object sender, EventArgs e)
	{
		this.int_19 = 0;
		this.int_18 = 0;
		checked
		{
			if (this.int_20 == 1)
			{
				this.Timer_26.Start();
				this.Timer_25.Stop();
				this.int_18 = 0;
			}
			else
			{
				this.int_16++;
			}
			if (this.int_16 > 255)
			{
				this.int_20 = 1;
				this.int_16 = 255;
			}
			this.PictureBox16.BackColor = Color.FromArgb(this.int_15, this.int_16, this.int_17);
		}
	}

	// Token: 0x06000C20 RID: 3104 RVA: 0x0004918C File Offset: 0x0004738C
	private void method_135(object sender, EventArgs e)
	{
		this.int_20 = 0;
		this.int_19 = 0;
		checked
		{
			if (this.int_18 == 1)
			{
				this.Timer_26.Stop();
				this.Timer_27.Start();
				this.int_19 = 0;
			}
			else
			{
				this.int_15--;
			}
			if (this.int_15 < 0)
			{
				this.int_18 = 1;
				this.int_15 = 0;
			}
			this.PictureBox16.BackColor = Color.FromArgb(this.int_15, this.int_16, this.int_17);
		}
	}

	// Token: 0x06000C21 RID: 3105 RVA: 0x0004921C File Offset: 0x0004741C
	private void method_136(object sender, EventArgs e)
	{
		this.int_20 = 0;
		this.int_18 = 0;
		checked
		{
			if (this.int_19 == 1)
			{
				this.Timer_28.Start();
				this.Timer_27.Stop();
				this.int_20 = 0;
			}
			else
			{
				this.int_17++;
			}
			if (this.int_17 > 255)
			{
				this.int_19 = 1;
				this.int_17 = 255;
			}
			this.PictureBox16.BackColor = Color.FromArgb(this.int_15, this.int_16, this.int_17);
		}
	}

	// Token: 0x06000C22 RID: 3106 RVA: 0x000492B4 File Offset: 0x000474B4
	private void method_137(object sender, EventArgs e)
	{
		this.int_19 = 0;
		checked
		{
			if (this.int_20 != 1)
			{
				this.int_16--;
			}
			else
			{
				this.Timer_23.Start();
				this.Timer_28.Stop();
				this.int_18 = 0;
			}
			if (this.int_16 < 0)
			{
				this.int_20 = 1;
				this.int_16 = 0;
			}
			this.PictureBox16.BackColor = Color.FromArgb(this.int_15, this.int_16, this.int_17);
		}
	}

	// Token: 0x06000C23 RID: 3107 RVA: 0x0004933C File Offset: 0x0004753C
	private void method_138(object sender, EventArgs e)
	{
		if (this.int_0 == 1)
		{
			this.PictureBox10.BackColor = Color.FromArgb(this.int_15, this.int_16, this.int_17);
		}
	}

	// Token: 0x06000C24 RID: 3108 RVA: 0x0004936C File Offset: 0x0004756C
	private void method_139(object object_1)
	{
		checked
		{
			if (!this.FlatCheckBox27.Boolean_0)
			{
				this.PictureBox12.Show();
				this.FlatButton2.Color_0 = this.color_0;
				this.Timer_30.Stop();
				this.FlatButton2.Refresh();
				this.FlatButton5.Color_0 = this.PictureBox12.BackColor;
				this.FlatButton3.Color_0 = this.PictureBox12.BackColor;
				this.FlatButton4.Color_0 = this.PictureBox12.BackColor;
				this.FlatButton6.Color_0 = this.PictureBox12.BackColor;
				this.FlatButton7.Color_0 = this.PictureBox12.BackColor;
				this.FlatButton8.Color_0 = this.PictureBox12.BackColor;
				this.FlatButton11.Color_0 = this.PictureBox12.BackColor;
				this.FlatButton9.Color_0 = this.PictureBox12.BackColor;
				this.FlatButton6.Hide();
				this.FlatButton7.Hide();
				this.FlatButton8.Hide();
				this.FlatButton6.Show();
				this.FlatButton7.Show();
				this.FlatButton8.Show();
				this.FlatTrackBar1.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar1.Color_1 = this.PictureBox12.BackColor;
				this.MinimumCPS.Color_0 = this.PictureBox12.BackColor;
				this.MaximumCPS.Color_0 = this.PictureBox12.BackColor;
				if (!this.FlatCheckBox21.Boolean_0)
				{
					this.MinimumCPS.Color_1 = this.PictureBox12.BackColor;
				}
				if (!this.FlatCheckBox21.Boolean_0)
				{
					this.MaximumCPS.Color_1 = this.PictureBox12.BackColor;
				}
				if (this.PictureBox2.BackColor == this.color_0)
				{
					this.PictureBox2.BackColor = this.PictureBox12.BackColor;
					this.FlatLabel5.BackColor = this.PictureBox12.BackColor;
					this.PictureBox18.BackColor = this.PictureBox12.BackColor;
				}
				if (this.PictureBox3.BackColor == this.color_0)
				{
					this.PictureBox3.BackColor = this.PictureBox12.BackColor;
					this.FlatLabel6.BackColor = this.PictureBox12.BackColor;
					this.PictureBox19.BackColor = this.PictureBox12.BackColor;
				}
				if (this.PictureBox4.BackColor == this.color_0)
				{
					this.PictureBox4.BackColor = this.PictureBox12.BackColor;
					this.PictureBox20.BackColor = this.PictureBox12.BackColor;
					this.FlatLabel8.BackColor = this.PictureBox12.BackColor;
				}
				if (this.PictureBox5.BackColor == this.color_0)
				{
					this.PictureBox5.BackColor = this.PictureBox12.BackColor;
					this.FlatLabel38.BackColor = this.PictureBox12.BackColor;
					this.PictureBox21.BackColor = this.PictureBox12.BackColor;
				}
				if (this.PictureBox6.BackColor == this.color_0)
				{
					this.PictureBox6.BackColor = this.PictureBox12.BackColor;
					this.FlatLabel40.BackColor = this.PictureBox12.BackColor;
					this.PictureBox22.BackColor = this.PictureBox12.BackColor;
				}
				if (this.FlatButton12.Color_0 == this.color_0)
				{
					this.FlatButton12.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton12.Refresh();
				}
				if (this.FlatButton13.Color_0 == this.color_0)
				{
					this.FlatButton13.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton13.Refresh();
				}
				if (this.FlatButton15.Color_0 == this.color_0)
				{
					this.FlatButton15.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton15.Refresh();
				}
				if (this.FlatButton14.Color_0 == this.color_0)
				{
					this.FlatButton14.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton14.Refresh();
				}
				if (this.FlatButton17.Color_0 == this.color_0)
				{
					this.FlatButton17.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton17.Refresh();
				}
				if (this.FlatButton16.Color_0 == this.color_0)
				{
					this.FlatButton16.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton16.Refresh();
				}
				if (this.FlatButton18.Color_0 == this.color_0)
				{
					this.FlatButton18.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton18.Refresh();
				}
				if (this.FlatButton20.Color_0 == this.color_0)
				{
					this.FlatButton20.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton20.Refresh();
				}
				if (this.FlatButton22.Color_0 == this.color_0)
				{
					this.FlatButton22.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton22.Refresh();
				}
				if (this.FlatButton24.Color_0 == this.color_0)
				{
					this.FlatButton24.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton24.Refresh();
				}
				if (this.FlatButton38.Color_0 == this.color_0)
				{
					this.FlatButton38.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton38.Refresh();
				}
				if (this.FlatButton37.Color_0 == this.color_0)
				{
					this.FlatButton37.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton37.Refresh();
				}
				if (this.FlatButton40.Color_0 == this.color_0)
				{
					this.FlatButton40.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton40.Refresh();
				}
				if (this.FlatButton39.Color_0 == this.color_0)
				{
					this.FlatButton39.Color_0 = this.PictureBox12.BackColor;
					this.FlatButton39.Refresh();
				}
				this.color_0 = this.PictureBox12.BackColor;
				this.MinimumCPS.Int32_2 = this.MinimumCPS.Int32_2 - 1;
				this.MinimumCPS.Int32_2 = this.MinimumCPS.Int32_2 + 1;
				this.FlatButton2.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar1.Int32_2 = this.FlatTrackBar1.Int32_2 - 1;
				this.FlatTrackBar1.Int32_2 = this.FlatTrackBar1.Int32_2 + 1;
				this.MaximumCPS.Int32_2 = this.MaximumCPS.Int32_2 - 1;
				this.MaximumCPS.Int32_2 = this.MaximumCPS.Int32_2 + 1;
				this.FlatTrackBar4.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar4.Color_1 = this.PictureBox12.BackColor;
				this.FlatTrackBar4.Int32_2 = this.FlatTrackBar4.Int32_2 - 1;
				this.FlatTrackBar4.Int32_2 = this.FlatTrackBar4.Int32_2 + 1;
				this.ThirteenComboBox1.Color_0 = this.PictureBox12.BackColor;
				this.FlatCheckBox1.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox2.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox3.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox5.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox4.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox7.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox6.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox8.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox9.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox10.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox11.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox12.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox13.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox14.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox15.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox16.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox17.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox18.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox19.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox20.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox21.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox22.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox23.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox24.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox25.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox26.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox27.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox29.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox27.Color_1 = this.PictureBox12.BackColor;
				this.FlatTrackBar1.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar1.Color_1 = this.PictureBox12.BackColor;
				this.FlatTrackBar2.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar2.Color_1 = this.PictureBox12.BackColor;
				this.FlatTrackBar3.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar3.Color_1 = this.PictureBox12.BackColor;
				this.FlatTrackBar4.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar4.Color_1 = this.PictureBox12.BackColor;
				this.FlatTrackBar5.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar5.Color_1 = this.PictureBox12.BackColor;
				this.FlatTrackBar6.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar6.Color_1 = this.PictureBox12.BackColor;
				this.FlatTrackBar7.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar7.Color_1 = this.PictureBox12.BackColor;
				this.FlatTrackBar8.Color_0 = this.PictureBox12.BackColor;
				this.FlatTrackBar8.Color_1 = this.PictureBox12.BackColor;
				this.FlatTrackBar1.Refresh();
				this.FlatTrackBar2.Refresh();
				this.FlatTrackBar3.Refresh();
				this.FlatTrackBar4.Refresh();
				this.FlatTrackBar5.Refresh();
				this.FlatTrackBar6.Refresh();
				this.FlatTrackBar7.Refresh();
				this.FlatTrackBar8.Refresh();
				this.ThirteenComboBox1.Color_0 = this.PictureBox12.BackColor;
				this.ThirteenComboBox3.Color_0 = this.PictureBox12.BackColor;
				this.ThirteenComboBox4.Color_0 = this.PictureBox12.BackColor;
				this.ThirteenComboBox5.Color_0 = this.PictureBox12.BackColor;
				this.ThirteenComboBox6.Color_0 = this.PictureBox12.BackColor;
				this.ThirteenComboBox9.Color_0 = this.PictureBox12.BackColor;
				this.ThirteenComboBox10.Color_0 = this.PictureBox12.BackColor;
				this.FlatCheckBox2.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox4.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox5.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox6.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox7.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox8.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox9.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox10.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox11.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox12.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox15.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox16.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox17.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox18.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox19.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox20.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox9.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox8.Color_1 = this.PictureBox12.BackColor;
				this.FlatCheckBox12.Color_1 = this.PictureBox12.BackColor;
				this.FlatButton1.Refresh();
				this.FlatButton2.Refresh();
				this.FlatButton3.Refresh();
				this.FlatButton4.Refresh();
				this.FlatButton5.Refresh();
				this.FlatButton6.Refresh();
				this.FlatButton7.Refresh();
				this.FlatButton8.Refresh();
				this.FlatButton9.Refresh();
				this.FlatButton11.Refresh();
				this.FlatButton12.Refresh();
				this.FlatButton13.Refresh();
				this.FlatButton14.Refresh();
				this.FlatButton15.Refresh();
				this.FlatButton16.Refresh();
				this.FlatButton17.Refresh();
				this.FlatButton18.Refresh();
				this.FlatButton19.Refresh();
				this.FlatButton20.Refresh();
				this.FlatButton21.Refresh();
				this.FlatButton22.Refresh();
				this.FlatButton23.Refresh();
				this.FlatButton24.Refresh();
				this.FlatButton25.Refresh();
				this.FlatButton26.Refresh();
				this.FlatButton27.Refresh();
				this.FlatButton28.Refresh();
				this.FlatButton29.Refresh();
				this.FlatButton30.Refresh();
			}
			else
			{
				this.PictureBox12.Hide();
				this.FlatButton2.Color_0 = Color.DimGray;
				this.Timer_30.Start();
			}
		}
	}

	// Token: 0x06000C25 RID: 3109 RVA: 0x0004A330 File Offset: 0x00048530
	private void method_140(object sender, EventArgs e)
	{
		checked
		{
			if (this.FlatCheckBox27.Boolean_0)
			{
				this.method_144();
				this.FlatButton5.Color_0 = this.PictureBox16.BackColor;
				this.FlatButton3.Color_0 = this.PictureBox16.BackColor;
				this.FlatButton4.Color_0 = this.PictureBox16.BackColor;
				this.FlatButton6.Color_0 = this.PictureBox16.BackColor;
				this.FlatButton7.Color_0 = this.PictureBox16.BackColor;
				this.FlatButton8.Color_0 = this.PictureBox16.BackColor;
				this.FlatButton11.Color_0 = this.PictureBox16.BackColor;
				this.FlatButton9.Color_0 = this.PictureBox16.BackColor;
				this.FlatButton6.Hide();
				this.FlatButton7.Hide();
				this.FlatButton8.Hide();
				this.FlatButton6.Show();
				this.FlatButton7.Show();
				this.FlatButton8.Show();
				this.FlatTrackBar1.Color_0 = this.PictureBox16.BackColor;
				this.FlatTrackBar1.Color_1 = this.PictureBox16.BackColor;
				this.MinimumCPS.Color_0 = this.PictureBox16.BackColor;
				this.MaximumCPS.Color_0 = this.PictureBox16.BackColor;
				this.color_0 = this.PictureBox16.BackColor;
				this.MinimumCPS.Refresh();
				this.FlatButton2.Color_0 = this.PictureBox16.BackColor;
				this.FlatTrackBar1.Int32_2 = this.FlatTrackBar1.Int32_2 - 1;
				this.FlatTrackBar1.Int32_2 = this.FlatTrackBar1.Int32_2 + 1;
				this.MaximumCPS.Refresh();
				this.FlatTrackBar4.Color_0 = this.PictureBox16.BackColor;
				this.FlatTrackBar4.Color_1 = this.PictureBox16.BackColor;
				this.FlatTrackBar4.Int32_2 = this.FlatTrackBar4.Int32_2 - 1;
				this.FlatTrackBar4.Int32_2 = this.FlatTrackBar4.Int32_2 + 1;
				this.ThirteenComboBox1.Color_0 = this.PictureBox16.BackColor;
			}
		}
	}

	// Token: 0x06000C26 RID: 3110 RVA: 0x0004A57C File Offset: 0x0004877C
	public void method_141()
	{
		this.ThirteenComboBox1.Color_0 = this.PictureBox16.BackColor;
		this.ThirteenComboBox3.Color_0 = this.PictureBox16.BackColor;
		this.ThirteenComboBox4.Color_0 = this.PictureBox16.BackColor;
		this.ThirteenComboBox5.Color_0 = this.PictureBox16.BackColor;
		this.ThirteenComboBox6.Color_0 = this.PictureBox16.BackColor;
		this.ThirteenComboBox9.Color_0 = this.PictureBox16.BackColor;
		this.ThirteenComboBox10.Color_0 = this.PictureBox16.BackColor;
		this.FlatCheckBox2.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox4.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox5.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox6.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox7.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox8.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox9.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox10.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox11.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox12.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox15.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox16.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox17.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox18.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox19.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox20.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox9.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox8.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox12.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox1.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox2.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox3.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox5.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox4.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox7.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox6.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox8.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox9.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox10.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox11.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox12.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox13.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox14.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox15.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox16.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox17.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox18.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox19.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox20.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox21.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox22.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox23.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox24.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox25.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox26.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox27.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox29.Color_1 = this.PictureBox16.BackColor;
		this.FlatCheckBox27.Color_1 = this.PictureBox16.BackColor;
		this.FlatTrackBar1.Color_0 = this.PictureBox16.BackColor;
		this.FlatTrackBar1.Color_1 = this.PictureBox16.BackColor;
		this.FlatTrackBar2.Color_0 = this.PictureBox16.BackColor;
		this.FlatTrackBar2.Color_1 = this.PictureBox16.BackColor;
		this.FlatTrackBar3.Color_0 = this.PictureBox16.BackColor;
		this.FlatTrackBar3.Color_1 = this.PictureBox16.BackColor;
		this.FlatTrackBar4.Color_0 = this.PictureBox16.BackColor;
		this.FlatTrackBar4.Color_1 = this.PictureBox16.BackColor;
		this.FlatTrackBar5.Color_0 = this.PictureBox16.BackColor;
		this.FlatTrackBar5.Color_1 = this.PictureBox16.BackColor;
		this.FlatTrackBar6.Color_0 = this.PictureBox16.BackColor;
		this.FlatTrackBar6.Color_1 = this.PictureBox16.BackColor;
		this.FlatTrackBar7.Color_0 = this.PictureBox16.BackColor;
		this.FlatTrackBar7.Color_1 = this.PictureBox16.BackColor;
		this.FlatTrackBar8.Color_0 = this.PictureBox16.BackColor;
		this.FlatTrackBar8.Color_1 = this.PictureBox16.BackColor;
		this.method_142();
	}

	// Token: 0x06000C27 RID: 3111 RVA: 0x0004ABAC File Offset: 0x00048DAC
	public void method_142()
	{
		this.FlatCheckBox2.Refresh();
		this.FlatCheckBox4.Refresh();
		this.FlatCheckBox5.Refresh();
		this.FlatCheckBox6.Refresh();
		this.FlatCheckBox7.Refresh();
		this.FlatCheckBox8.Refresh();
		this.FlatCheckBox9.Refresh();
		this.FlatCheckBox10.Refresh();
		this.FlatCheckBox11.Refresh();
		this.FlatCheckBox12.Refresh();
		this.FlatCheckBox15.Refresh();
		this.FlatCheckBox16.Refresh();
		this.FlatCheckBox17.Refresh();
		this.FlatCheckBox18.Refresh();
		this.FlatCheckBox19.Refresh();
		this.FlatCheckBox20.Refresh();
		this.FlatCheckBox9.Refresh();
		this.FlatCheckBox8.Refresh();
		this.FlatCheckBox12.Refresh();
		this.FlatButton1.Refresh();
		this.FlatButton2.Refresh();
		this.FlatButton3.Refresh();
		this.FlatButton4.Refresh();
		this.FlatButton5.Refresh();
		this.FlatButton6.Refresh();
		this.FlatButton7.Refresh();
		this.FlatButton8.Refresh();
		this.FlatButton9.Refresh();
		this.FlatButton11.Refresh();
		this.FlatButton12.Refresh();
		this.FlatButton13.Refresh();
		this.FlatButton14.Refresh();
		this.FlatButton15.Refresh();
		this.FlatButton16.Refresh();
		this.FlatButton17.Refresh();
		this.FlatButton18.Refresh();
		this.FlatButton19.Refresh();
		this.FlatButton20.Refresh();
		this.FlatButton21.Refresh();
		this.FlatButton22.Refresh();
		this.FlatButton23.Refresh();
		this.FlatButton24.Refresh();
		this.FlatButton25.Refresh();
		this.FlatButton26.Refresh();
		this.FlatButton27.Refresh();
		this.FlatButton28.Refresh();
		this.FlatButton29.Refresh();
		this.FlatButton30.Refresh();
		this.FlatTrackBar1.Refresh();
		this.FlatTrackBar2.Refresh();
		this.FlatTrackBar3.Refresh();
		this.FlatTrackBar4.Refresh();
		this.FlatTrackBar5.Refresh();
		this.FlatTrackBar6.Refresh();
		this.FlatTrackBar7.Refresh();
		this.FlatTrackBar8.Refresh();
		this.ThirteenComboBox1.Refresh();
		this.ThirteenComboBox3.Refresh();
		this.ThirteenComboBox4.Refresh();
		this.ThirteenComboBox5.Refresh();
		this.ThirteenComboBox6.Refresh();
		this.ThirteenComboBox9.Refresh();
		this.ThirteenComboBox10.Refresh();
	}

	// Token: 0x06000C28 RID: 3112 RVA: 0x0004AE70 File Offset: 0x00049070
	public void method_143()
	{
		if (!(this.FlatButton12.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton12.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton12.Refresh();
		}
		if (!(this.FlatButton13.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton13.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton13.Refresh();
		}
		if (!(this.FlatButton15.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton15.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton15.Refresh();
		}
		if (!(this.FlatButton14.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton14.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton14.Refresh();
		}
		if (!(this.FlatButton17.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton17.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton17.Refresh();
		}
		if (!(this.FlatButton16.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton16.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton16.Refresh();
		}
		if (!(this.FlatButton18.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton18.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton18.Refresh();
		}
		if (!(this.FlatButton20.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton20.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton20.Refresh();
		}
		if (!(this.FlatButton22.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton22.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton22.Refresh();
		}
		if (!(this.FlatButton24.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton24.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton24.Refresh();
		}
		if (!(this.FlatButton38.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton38.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton38.Refresh();
		}
		if (!(this.FlatButton37.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton37.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton37.Refresh();
		}
		if (!(this.FlatButton40.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton40.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton40.Refresh();
		}
		if (!(this.FlatButton39.Color_0 == Color.FromArgb(38, 38, 38)))
		{
			this.FlatButton39.Color_0 = this.PictureBox16.BackColor;
			this.FlatButton39.Refresh();
		}
		this.method_141();
	}

	// Token: 0x06000C29 RID: 3113 RVA: 0x0004B1E8 File Offset: 0x000493E8
	public void method_144()
	{
		if (!this.FlatCheckBox21.Boolean_0)
		{
			this.MinimumCPS.Color_1 = this.PictureBox16.BackColor;
		}
		if (!this.FlatCheckBox21.Boolean_0)
		{
			this.MaximumCPS.Color_1 = this.PictureBox16.BackColor;
		}
		if (this.FlatCheckBox27.Boolean_0)
		{
			if (!(this.PictureBox2.BackColor == Color.FromArgb(10, 10, 10)))
			{
				this.PictureBox2.BackColor = this.PictureBox16.BackColor;
				this.FlatLabel5.BackColor = this.PictureBox16.BackColor;
				this.PictureBox18.BackColor = this.PictureBox16.BackColor;
			}
			if (!(this.PictureBox3.BackColor == Color.FromArgb(10, 10, 10)))
			{
				this.PictureBox3.BackColor = this.PictureBox16.BackColor;
				this.FlatLabel6.BackColor = this.PictureBox16.BackColor;
				this.PictureBox19.BackColor = this.PictureBox16.BackColor;
			}
			if (!(this.PictureBox4.BackColor == Color.FromArgb(10, 10, 10)))
			{
				this.PictureBox4.BackColor = this.PictureBox16.BackColor;
				this.PictureBox20.BackColor = this.PictureBox16.BackColor;
				this.FlatLabel8.BackColor = this.PictureBox16.BackColor;
			}
			if (!(this.PictureBox5.BackColor == Color.FromArgb(10, 10, 10)))
			{
				this.PictureBox5.BackColor = this.PictureBox16.BackColor;
				this.FlatLabel38.BackColor = this.PictureBox16.BackColor;
				this.PictureBox21.BackColor = this.PictureBox16.BackColor;
			}
			if (!(this.PictureBox6.BackColor == Color.FromArgb(10, 10, 10)))
			{
				this.PictureBox6.BackColor = this.PictureBox16.BackColor;
				this.FlatLabel40.BackColor = this.PictureBox16.BackColor;
				this.PictureBox22.BackColor = this.PictureBox16.BackColor;
			}
		}
		this.method_143();
	}

	// Token: 0x06000C2A RID: 3114 RVA: 0x0004B42C File Offset: 0x0004962C
	void method_145()
	{
		base.Dispose();
	}

	// Token: 0x040004E6 RID: 1254
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("BindClcl")]
	private System.Windows.Forms.Timer timer_0;

	// Token: 0x040004E8 RID: 1256
	[AccessedThroughProperty("poof")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_1;

	// Token: 0x040004E9 RID: 1257
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("Enableclcker")]
	private System.Windows.Forms.Timer timer_2;

	// Token: 0x040004EA RID: 1258
	[CompilerGenerated]
	[AccessedThroughProperty("Timer5")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private System.Windows.Forms.Timer timer_3;

	// Token: 0x040004EB RID: 1259
	[CompilerGenerated]
	[AccessedThroughProperty("Timer3")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private System.Windows.Forms.Timer timer_4;

	// Token: 0x040004EC RID: 1260
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("BindHide")]
	private System.Windows.Forms.Timer timer_5;

	// Token: 0x040004ED RID: 1261
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("Timer4")]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_6;

	// Token: 0x040004EE RID: 1262
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("Autoclicker")]
	private System.Windows.Forms.Timer timer_7;

	// Token: 0x040004EF RID: 1263
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("Timer2")]
	private System.Windows.Forms.Timer timer_8;

	// Token: 0x040004F0 RID: 1264
	[CompilerGenerated]
	[AccessedThroughProperty("Timer1")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private System.Windows.Forms.Timer timer_9;

	// Token: 0x0400053B RID: 1339
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("ToolTip1")]
	private ToolTip toolTip_0;

	// Token: 0x04000557 RID: 1367
	[AccessedThroughProperty("WTap")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_10;

	// Token: 0x0400055E RID: 1374
	[AccessedThroughProperty("STap")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_11;

	// Token: 0x04000566 RID: 1382
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("ThrowPot")]
	private System.Windows.Forms.Timer timer_12;

	// Token: 0x04000568 RID: 1384
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("Rainbowww")]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_13;

	// Token: 0x0400056A RID: 1386
	[CompilerGenerated]
	[AccessedThroughProperty("BindPot")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private System.Windows.Forms.Timer timer_14;

	// Token: 0x0400056D RID: 1389
	[CompilerGenerated]
	[AccessedThroughProperty("WBind")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private System.Windows.Forms.Timer timer_15;

	// Token: 0x0400056E RID: 1390
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("SBind")]
	private System.Windows.Forms.Timer timer_16;

	// Token: 0x04000575 RID: 1397
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("PearlBind")]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_17;

	// Token: 0x04000576 RID: 1398
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("ThrowPearl")]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_18;

	// Token: 0x04000580 RID: 1408
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("BindVoice")]
	private System.Windows.Forms.Timer timer_19;

	// Token: 0x04000583 RID: 1411
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("BindDestruct")]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_20;

	// Token: 0x04000584 RID: 1412
	[AccessedThroughProperty("Enabledets")]
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private System.Windows.Forms.Timer timer_21;

	// Token: 0x0400058D RID: 1421
	[AccessedThroughProperty("Auth")]
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private System.Windows.Forms.Timer timer_22;

	// Token: 0x0400058E RID: 1422
	[AccessedThroughProperty("ez1")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_23;

	// Token: 0x0400058F RID: 1423
	[AccessedThroughProperty("ez2")]
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private System.Windows.Forms.Timer timer_24;

	// Token: 0x04000590 RID: 1424
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("ez3")]
	private System.Windows.Forms.Timer timer_25;

	// Token: 0x04000591 RID: 1425
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("ez4")]
	private System.Windows.Forms.Timer timer_26;

	// Token: 0x04000592 RID: 1426
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("ez5")]
	private System.Windows.Forms.Timer timer_27;

	// Token: 0x04000593 RID: 1427
	[AccessedThroughProperty("ez6")]
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private System.Windows.Forms.Timer timer_28;

	// Token: 0x04000594 RID: 1428
	[AccessedThroughProperty("ezfinal")]
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private System.Windows.Forms.Timer timer_29;

	// Token: 0x04000595 RID: 1429
	[AccessedThroughProperty("looprainbow")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_30;

	// Token: 0x04000597 RID: 1431
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("NotifyIcon1")]
	[CompilerGenerated]
	private NotifyIcon notifyIcon_0;

	// Token: 0x0400059D RID: 1437
	private string string_0;

	// Token: 0x0400059E RID: 1438
	private string string_1;

	// Token: 0x0400059F RID: 1439
	private string string_2;

	// Token: 0x040005A0 RID: 1440
	private WebClient webClient_0;

	// Token: 0x040005A1 RID: 1441
	private string string_3;

	// Token: 0x040005A2 RID: 1442
	private int int_0;

	// Token: 0x040005A3 RID: 1443
	private string string_4;

	// Token: 0x040005A4 RID: 1444
	private string string_5;

	// Token: 0x040005A5 RID: 1445
	private string string_6;

	// Token: 0x040005A6 RID: 1446
	private string string_7;

	// Token: 0x040005A7 RID: 1447
	private string string_8;

	// Token: 0x040005A8 RID: 1448
	private string string_9;

	// Token: 0x040005A9 RID: 1449
	private string string_10;

	// Token: 0x040005AA RID: 1450
	private int int_1;

	// Token: 0x040005AB RID: 1451
	private string string_11;

	// Token: 0x040005AC RID: 1452
	private string string_12;

	// Token: 0x040005AD RID: 1453
	private string string_13;

	// Token: 0x040005AE RID: 1454
	private string string_14;

	// Token: 0x040005AF RID: 1455
	private string string_15;

	// Token: 0x040005B0 RID: 1456
	private string string_16;

	// Token: 0x040005B1 RID: 1457
	private string string_17;

	// Token: 0x040005B2 RID: 1458
	private string string_18;

	// Token: 0x040005B3 RID: 1459
	private Keys keys_0;

	// Token: 0x040005B4 RID: 1460
	private string string_19;

	// Token: 0x040005B5 RID: 1461
	private object object_0;

	// Token: 0x040005B6 RID: 1462
	private bool bool_0;

	// Token: 0x040005B7 RID: 1463
	[AccessedThroughProperty("reg")]
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private SpeechRecognitionEngine speechRecognitionEngine_0;

	// Token: 0x040005B8 RID: 1464
	private bool bool_1;

	// Token: 0x040005B9 RID: 1465
	private bool bool_2;

	// Token: 0x040005BA RID: 1466
	private int int_2;

	// Token: 0x040005BB RID: 1467
	private string string_20;

	// Token: 0x040005BC RID: 1468
	private int int_3;

	// Token: 0x040005BD RID: 1469
	private int int_4;

	// Token: 0x040005BE RID: 1470
	private Random random_0;

	// Token: 0x040005BF RID: 1471
	private int int_5;

	// Token: 0x040005C0 RID: 1472
	private string string_21;

	// Token: 0x040005C1 RID: 1473
	private string string_22;

	// Token: 0x040005C2 RID: 1474
	private string string_23;

	// Token: 0x040005C3 RID: 1475
	private string string_24;

	// Token: 0x040005C4 RID: 1476
	private string string_25;

	// Token: 0x040005C5 RID: 1477
	private string string_26;

	// Token: 0x040005C6 RID: 1478
	private string string_27;

	// Token: 0x040005C7 RID: 1479
	private string string_28;

	// Token: 0x040005C8 RID: 1480
	private string string_29;

	// Token: 0x040005C9 RID: 1481
	private string string_30;

	// Token: 0x040005CA RID: 1482
	public const int int_6 = 2;

	// Token: 0x040005CB RID: 1483
	public const int int_7 = 4;

	// Token: 0x040005CC RID: 1484
	public const int int_8 = 32;

	// Token: 0x040005CD RID: 1485
	public const int int_9 = 64;

	// Token: 0x040005CE RID: 1486
	public const int int_10 = 8;

	// Token: 0x040005CF RID: 1487
	public const int int_11 = 16;

	// Token: 0x040005D0 RID: 1488
	public const int int_12 = 1;

	// Token: 0x040005D1 RID: 1489
	public bool bool_3;

	// Token: 0x040005D2 RID: 1490
	public bool bool_4;

	// Token: 0x040005D3 RID: 1491
	public DateTime dateTime_0;

	// Token: 0x040005D4 RID: 1492
	public int int_13;

	// Token: 0x040005D5 RID: 1493
	private bool bool_5;

	// Token: 0x040005D6 RID: 1494
	private bool bool_6;

	// Token: 0x040005D7 RID: 1495
	private bool bool_7;

	// Token: 0x040005D8 RID: 1496
	private string string_31;

	// Token: 0x040005D9 RID: 1497
	private Bitmap bitmap_0;

	// Token: 0x040005DA RID: 1498
	private Graphics graphics_0;

	// Token: 0x040005DB RID: 1499
	private const int int_14 = 1;

	// Token: 0x040005DC RID: 1500
	private const string string_32 = "Heydings Icons; 12pt";

	// Token: 0x040005DD RID: 1501
	private bool bool_8;

	// Token: 0x040005DE RID: 1502
	private bool bool_9;

	// Token: 0x040005DF RID: 1503
	private bool bool_10;

	// Token: 0x040005E0 RID: 1504
	private bool bool_11;

	// Token: 0x040005E1 RID: 1505
	private Color color_0;

	// Token: 0x040005E2 RID: 1506
	private int int_15;

	// Token: 0x040005E3 RID: 1507
	private int int_16;

	// Token: 0x040005E4 RID: 1508
	private bool bool_12;

	// Token: 0x040005E5 RID: 1509
	private int int_17;

	// Token: 0x040005E6 RID: 1510
	private bool bool_13;

	// Token: 0x040005E7 RID: 1511
	private int int_18;

	// Token: 0x040005E8 RID: 1512
	private int int_19;

	// Token: 0x040005E9 RID: 1513
	private bool bool_14;

	// Token: 0x040005EA RID: 1514
	private int int_20;

	// Token: 0x040005EB RID: 1515
	private int int_21;

	// Token: 0x040005EC RID: 1516
	private int int_22;

	// Token: 0x040005ED RID: 1517
	private int int_23;

	// Token: 0x040005EE RID: 1518
	private int int_24;

	// Token: 0x040005EF RID: 1519
	private int int_25;

	// Token: 0x040005F0 RID: 1520
	private int int_26;

	// Token: 0x040005F1 RID: 1521
	private int int_27;

	// Token: 0x040005F2 RID: 1522
	[CompilerGenerated]
	[AccessedThroughProperty("mHook")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private GClass2 gclass2_0;

	// Token: 0x040005F3 RID: 1523
	private int int_28;

	// Token: 0x040005F4 RID: 1524
	private bool bool_15;

	// Token: 0x040005F5 RID: 1525
	private Point point_0;

	// Token: 0x040005F6 RID: 1526
	private Point point_1;

	// Token: 0x040005F7 RID: 1527
	private Point point_2;

	// Token: 0x040005F8 RID: 1528
	private Point point_3;

	// Token: 0x040005F9 RID: 1529
	private Point point_4;

	// Token: 0x020000E9 RID: 233
	public struct GStruct2
	{
		// Token: 0x040005FA RID: 1530
		public int int_0;

		// Token: 0x040005FB RID: 1531
		public int int_1;
	}

	// Token: 0x020000EA RID: 234
	private struct Struct2
	{
		// Token: 0x040005FC RID: 1532
		public int int_0;

		// Token: 0x040005FD RID: 1533
		public int int_1;

		// Token: 0x040005FE RID: 1534
		public int int_2;

		// Token: 0x040005FF RID: 1535
		public int int_3;
	}
}
