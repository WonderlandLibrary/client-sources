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

// Token: 0x0200009E RID: 158
public class GControl32 : Control
{
	// Token: 0x17000209 RID: 521
	// (get) Token: 0x0600073A RID: 1850 RVA: 0x000223BC File Offset: 0x000205BC
	// (set) Token: 0x0600073B RID: 1851 RVA: 0x000223C4 File Offset: 0x000205C4
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
			DrawItemEventHandler value2 = new DrawItemEventHandler(this.method_4);
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

	// Token: 0x1700020A RID: 522
	// (get) Token: 0x0600073C RID: 1852 RVA: 0x00022408 File Offset: 0x00020608
	// (set) Token: 0x0600073D RID: 1853 RVA: 0x00022420 File Offset: 0x00020620
	[Category("Control")]
	public Font Font_0
	{
		get
		{
			return this.font_0;
		}
		set
		{
			this.font_0 = value;
		}
	}

	// Token: 0x1700020B RID: 523
	// (get) Token: 0x0600073E RID: 1854 RVA: 0x0002242C File Offset: 0x0002062C
	// (set) Token: 0x0600073F RID: 1855 RVA: 0x00022444 File Offset: 0x00020644
	[Category("Control")]
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

	// Token: 0x1700020C RID: 524
	// (get) Token: 0x06000740 RID: 1856 RVA: 0x00022474 File Offset: 0x00020674
	// (set) Token: 0x06000741 RID: 1857 RVA: 0x0002248C File Offset: 0x0002068C
	[Category("Colours")]
	public Color Color_0
	{
		get
		{
			return this.color_4;
		}
		set
		{
			this.color_4 = value;
		}
	}

	// Token: 0x1700020D RID: 525
	// (get) Token: 0x06000742 RID: 1858 RVA: 0x00022498 File Offset: 0x00020698
	// (set) Token: 0x06000743 RID: 1859 RVA: 0x000224B0 File Offset: 0x000206B0
	[Category("Colours")]
	public Color Color_1
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

	// Token: 0x1700020E RID: 526
	// (get) Token: 0x06000744 RID: 1860 RVA: 0x000224BC File Offset: 0x000206BC
	// (set) Token: 0x06000745 RID: 1861 RVA: 0x000224D4 File Offset: 0x000206D4
	[Category("Colours")]
	public Color Color_2
	{
		get
		{
			return this.color_0;
		}
		set
		{
			this.color_0 = value;
		}
	}

	// Token: 0x1700020F RID: 527
	// (get) Token: 0x06000746 RID: 1862 RVA: 0x000224E0 File Offset: 0x000206E0
	// (set) Token: 0x06000747 RID: 1863 RVA: 0x000224F8 File Offset: 0x000206F8
	[Category("Colours")]
	public Color Color_3
	{
		get
		{
			return this.color_2;
		}
		set
		{
			this.color_2 = value;
		}
	}

	// Token: 0x17000210 RID: 528
	// (get) Token: 0x06000748 RID: 1864 RVA: 0x00022504 File Offset: 0x00020704
	// (set) Token: 0x06000749 RID: 1865 RVA: 0x0002251C File Offset: 0x0002071C
	[Category("Colours")]
	public Color Color_4
	{
		get
		{
			return this.color_3;
		}
		set
		{
			this.color_3 = value;
		}
	}

	// Token: 0x17000211 RID: 529
	// (get) Token: 0x0600074A RID: 1866 RVA: 0x00022528 File Offset: 0x00020728
	public string String_1
	{
		get
		{
			return Conversions.ToString(this.ListBox_0.SelectedItem);
		}
	}

	// Token: 0x17000212 RID: 530
	// (get) Token: 0x0600074B RID: 1867 RVA: 0x00022548 File Offset: 0x00020748
	public int Int32_0
	{
		get
		{
			return this.ListBox_0.SelectedIndex;
		}
	}

	// Token: 0x0600074C RID: 1868 RVA: 0x00022564 File Offset: 0x00020764
	public void method_0()
	{
		this.ListBox_0.Items.Clear();
	}

	// Token: 0x0600074D RID: 1869 RVA: 0x00022578 File Offset: 0x00020778
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

	// Token: 0x0600074E RID: 1870 RVA: 0x000225CC File Offset: 0x000207CC
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		if (!base.Controls.Contains(this.ListBox_0))
		{
			base.Controls.Add(this.ListBox_0);
		}
	}

	// Token: 0x0600074F RID: 1871 RVA: 0x000225FC File Offset: 0x000207FC
	public void method_2(object[] object_0)
	{
		this.ListBox_0.Items.Remove("");
		this.ListBox_0.Items.AddRange(object_0);
	}

	// Token: 0x06000750 RID: 1872 RVA: 0x00022624 File Offset: 0x00020824
	public void method_3(object object_0)
	{
		this.ListBox_0.Items.Remove("");
		this.ListBox_0.Items.Add(RuntimeHelpers.GetObjectValue(object_0));
	}

	// Token: 0x06000751 RID: 1873 RVA: 0x00022654 File Offset: 0x00020854
	public void method_4(object sender, DrawItemEventArgs e)
	{
		checked
		{
			if (e.Index >= 0)
			{
				e.DrawBackground();
				e.DrawFocusRectangle();
				Graphics graphics = e.Graphics;
				graphics.SmoothingMode = SmoothingMode.HighQuality;
				graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				if (Strings.InStr(e.State.ToString(), "Selected,", CompareMethod.Binary) > 0)
				{
					graphics.FillRectangle(new SolidBrush(this.color_1), new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height - 1));
					graphics.DrawString(" " + this.ListBox_0.Items[e.Index].ToString(), new Font("Segoe UI", 9f, FontStyle.Bold), new SolidBrush(this.color_3), (float)e.Bounds.X, (float)(e.Bounds.Y + 2));
				}
				else
				{
					graphics.FillRectangle(new SolidBrush(this.color_2), new Rectangle(e.Bounds.X, e.Bounds.Y, e.Bounds.Width, e.Bounds.Height));
					graphics.DrawString(" " + this.ListBox_0.Items[e.Index].ToString(), new Font("Segoe UI", 8f), new SolidBrush(this.color_3), (float)e.Bounds.X, (float)(e.Bounds.Y + 2));
				}
				graphics.Dispose();
			}
		}
	}

	// Token: 0x06000752 RID: 1874 RVA: 0x0002283C File Offset: 0x00020A3C
	public GControl32()
	{
		this.ListBox_0 = new ListBox();
		this.string_0 = new string[]
		{
			""
		};
		this.color_0 = Color.FromArgb(42, 42, 42);
		this.color_1 = Color.FromArgb(55, 55, 55);
		this.color_2 = Color.FromArgb(47, 47, 47);
		this.color_3 = Color.FromArgb(255, 255, 255);
		this.color_4 = Color.FromArgb(35, 35, 35);
		this.font_0 = new Font("Segeo UI", 10f, FontStyle.Bold);
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.ListBox_0.DrawMode = DrawMode.OwnerDrawFixed;
		this.ListBox_0.ScrollAlwaysVisible = false;
		this.ListBox_0.HorizontalScrollbar = false;
		this.ListBox_0.BorderStyle = BorderStyle.None;
		this.ListBox_0.BackColor = this.Color_2;
		this.ListBox_0.Location = new Point(3, 28);
		this.ListBox_0.Font = new Font("Segoe UI", 8f);
		this.ListBox_0.ItemHeight = 20;
		this.ListBox_0.Items.Clear();
		this.ListBox_0.IntegralHeight = false;
		base.Size = new Size(130, 100);
	}

	// Token: 0x06000753 RID: 1875 RVA: 0x000229A0 File Offset: 0x00020BA0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Bitmap bitmap = new Bitmap(base.Width, base.Height);
		Graphics graphics = Graphics.FromImage(bitmap);
		Rectangle rect = new Rectangle(0, 0, base.Width, base.Height);
		Graphics graphics2 = graphics;
		graphics2.SmoothingMode = SmoothingMode.HighQuality;
		graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
		graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		graphics2.Clear(this.BackColor);
		checked
		{
			this.ListBox_0.Size = new Size(base.Width - 6, base.Height - 30);
			graphics2.FillRectangle(new SolidBrush(this.Color_2), rect);
			graphics2.DrawRectangle(new Pen(this.color_4, 3f), new Rectangle(0, 0, base.Width, base.Height - 1));
			graphics2.DrawLine(new Pen(this.color_4, 2f), new Point(0, 27), new Point(base.Width - 1, 27));
			graphics2.DrawString(this.Text, this.font_0, new SolidBrush(this.color_3), new Rectangle(2, 5, base.Width - 5, 20), new StringFormat
			{
				Alignment = StringAlignment.Center,
				LineAlignment = StringAlignment.Center
			});
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x0400036D RID: 877
	[CompilerGenerated]
	[AccessedThroughProperty("ListB")]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	private ListBox listBox_0;

	// Token: 0x0400036E RID: 878
	private string[] string_0;

	// Token: 0x0400036F RID: 879
	private Color color_0;

	// Token: 0x04000370 RID: 880
	private Color color_1;

	// Token: 0x04000371 RID: 881
	private Color color_2;

	// Token: 0x04000372 RID: 882
	private Color color_3;

	// Token: 0x04000373 RID: 883
	private Color color_4;

	// Token: 0x04000374 RID: 884
	private Font font_0;
}
