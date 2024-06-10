using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x02000052 RID: 82
internal class Control38 : Control
{
	// Token: 0x1700014F RID: 335
	// (get) Token: 0x06000458 RID: 1112 RVA: 0x00015414 File Offset: 0x00013614
	// (set) Token: 0x06000459 RID: 1113 RVA: 0x0001541C File Offset: 0x0001361C
	private virtual ListBox ListBox_0
	{
		[CompilerGenerated]
		get
		{
			return this.listBox_0;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			DrawItemEventHandler value2 = new DrawItemEventHandler(this.method_2);
			ListBox listBox = this.listBox_0;
			if (listBox != null)
			{
				listBox.DrawItem -= value2;
			}
			this.listBox_0 = value;
			listBox = this.listBox_0;
			if (listBox != null)
			{
				listBox.DrawItem += value2;
			}
		}
	}

	// Token: 0x17000150 RID: 336
	// (get) Token: 0x0600045A RID: 1114 RVA: 0x00015460 File Offset: 0x00013660
	// (set) Token: 0x0600045B RID: 1115 RVA: 0x00015478 File Offset: 0x00013678
	[Category("Options")]
	public string[] String_0
	{
		get
		{
			return this.string_0;
		}
		set
		{
			this.string_0 = value;
			this.ListBox_0.Items.Clear();
			this.ListBox_0.Items.AddRange(value);
			base.Invalidate();
		}
	}

	// Token: 0x17000151 RID: 337
	// (get) Token: 0x0600045C RID: 1116 RVA: 0x000154A8 File Offset: 0x000136A8
	// (set) Token: 0x0600045D RID: 1117 RVA: 0x000154C0 File Offset: 0x000136C0
	[Category("Colors")]
	public Color Color_0
	{
		get
		{
			return this.color_1;
		}
		set
		{
			this.color_1 = value;
		}
	}

	// Token: 0x17000152 RID: 338
	// (get) Token: 0x0600045E RID: 1118 RVA: 0x000154CC File Offset: 0x000136CC
	public string String_1
	{
		get
		{
			return Conversions.ToString(this.ListBox_0.SelectedItem);
		}
	}

	// Token: 0x17000153 RID: 339
	// (get) Token: 0x0600045F RID: 1119 RVA: 0x000154EC File Offset: 0x000136EC
	public int Int32_0
	{
		get
		{
			return this.ListBox_0.SelectedIndex;
		}
	}

	// Token: 0x06000460 RID: 1120 RVA: 0x00015508 File Offset: 0x00013708
	public void method_0()
	{
		this.ListBox_0.Items.Clear();
	}

	// Token: 0x06000461 RID: 1121 RVA: 0x0001551C File Offset: 0x0001371C
	public void method_1()
	{
		checked
		{
			int num = this.ListBox_0.SelectedItems.Count - 1;
			for (int i = num; i >= 0; i += -1)
			{
				this.ListBox_0.Items.Remove(RuntimeHelpers.GetObjectValue(this.ListBox_0.SelectedItems[i]));
			}
		}
	}

	// Token: 0x06000462 RID: 1122 RVA: 0x00015570 File Offset: 0x00013770
	public void method_2(object sender, DrawItemEventArgs e)
	{
		checked
		{
			if (e.Index >= 0)
			{
				e.DrawBackground();
				e.DrawFocusRectangle();
				e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
				e.Graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
				e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				if (Strings.InStr(e.State.ToString(), "Selected,", CompareMethod.Binary) <= 0)
				{
					e.Graphics.FillRectangle(new SolidBrush(Color.FromArgb(51, 53, 55)), new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height));
					e.Graphics.DrawString(" " + this.ListBox_0.Items[e.Index].ToString(), new Font("Segoe UI", 8f), Brushes.White, (float)e.Bounds.X, (float)(e.Bounds.Y + 2));
				}
				else
				{
					e.Graphics.FillRectangle(new SolidBrush(this.color_1), new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height));
					e.Graphics.DrawString(" " + this.ListBox_0.Items[e.Index].ToString(), new Font("Segoe UI", 8f), Brushes.White, (float)e.Bounds.X, (float)(e.Bounds.Y + 2));
				}
				e.Graphics.Dispose();
			}
		}
	}

	// Token: 0x06000463 RID: 1123 RVA: 0x00015774 File Offset: 0x00013974
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		if (!base.Controls.Contains(this.ListBox_0))
		{
			base.Controls.Add(this.ListBox_0);
		}
	}

	// Token: 0x06000464 RID: 1124 RVA: 0x000157A4 File Offset: 0x000139A4
	public void method_3(object[] object_0)
	{
		this.ListBox_0.Items.Remove("");
		this.ListBox_0.Items.AddRange(object_0);
	}

	// Token: 0x06000465 RID: 1125 RVA: 0x000157CC File Offset: 0x000139CC
	public void method_4(object object_0)
	{
		this.ListBox_0.Items.Remove("");
		this.ListBox_0.Items.Add(RuntimeHelpers.GetObjectValue(object_0));
	}

	// Token: 0x06000466 RID: 1126 RVA: 0x000157FC File Offset: 0x000139FC
	public Control38()
	{
		this.ListBox_0 = new ListBox();
		this.string_0 = new string[]
		{
			""
		};
		this.color_0 = Color.FromArgb(45, 47, 49);
		this.color_1 = Class16.color_0;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.ListBox_0.DrawMode = DrawMode.OwnerDrawFixed;
		this.ListBox_0.ScrollAlwaysVisible = false;
		this.ListBox_0.HorizontalScrollbar = false;
		this.ListBox_0.BorderStyle = BorderStyle.None;
		this.ListBox_0.BackColor = this.color_0;
		this.ListBox_0.ForeColor = Color.White;
		this.ListBox_0.Location = new Point(3, 3);
		this.ListBox_0.Font = new Font("Segoe UI", 8f);
		this.ListBox_0.ItemHeight = 20;
		this.ListBox_0.Items.Clear();
		this.ListBox_0.IntegralHeight = false;
		base.Size = new Size(131, 101);
		this.BackColor = this.color_0;
	}

	// Token: 0x06000467 RID: 1127 RVA: 0x00015924 File Offset: 0x00013B24
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		Rectangle rect = new Rectangle(0, 0, base.Width, base.Height);
		Graphics graphics_ = Class16.graphics_0;
		graphics_.SmoothingMode = SmoothingMode.HighQuality;
		graphics_.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics_.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics_.Clear(this.BackColor);
		this.ListBox_0.Size = checked(new Size(base.Width - 6, base.Height - 2));
		graphics_.FillRectangle(new SolidBrush(this.color_0), rect);
		base.OnPaint(e);
		Class16.graphics_0.Dispose();
		e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
		e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
		Class16.bitmap_0.Dispose();
	}

	// Token: 0x04000253 RID: 595
	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("ListBx")]
	private ListBox listBox_0;

	// Token: 0x04000254 RID: 596
	private string[] string_0;

	// Token: 0x04000255 RID: 597
	private Color color_0;

	// Token: 0x04000256 RID: 598
	private Color color_1;
}
