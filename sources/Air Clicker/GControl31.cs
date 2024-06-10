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

// Token: 0x0200009D RID: 157
public class GControl31 : Control
{
	// Token: 0x17000200 RID: 512
	// (get) Token: 0x06000722 RID: 1826 RVA: 0x00021D28 File Offset: 0x0001FF28
	// (set) Token: 0x06000723 RID: 1827 RVA: 0x00021D30 File Offset: 0x0001FF30
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

	// Token: 0x17000201 RID: 513
	// (get) Token: 0x06000724 RID: 1828 RVA: 0x00021D74 File Offset: 0x0001FF74
	// (set) Token: 0x06000725 RID: 1829 RVA: 0x00021D8C File Offset: 0x0001FF8C
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

	// Token: 0x17000202 RID: 514
	// (get) Token: 0x06000726 RID: 1830 RVA: 0x00021DBC File Offset: 0x0001FFBC
	// (set) Token: 0x06000727 RID: 1831 RVA: 0x00021DD4 File Offset: 0x0001FFD4
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

	// Token: 0x17000203 RID: 515
	// (get) Token: 0x06000728 RID: 1832 RVA: 0x00021DE0 File Offset: 0x0001FFE0
	// (set) Token: 0x06000729 RID: 1833 RVA: 0x00021DF8 File Offset: 0x0001FFF8
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

	// Token: 0x17000204 RID: 516
	// (get) Token: 0x0600072A RID: 1834 RVA: 0x00021E04 File Offset: 0x00020004
	// (set) Token: 0x0600072B RID: 1835 RVA: 0x00021E1C File Offset: 0x0002001C
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

	// Token: 0x17000205 RID: 517
	// (get) Token: 0x0600072C RID: 1836 RVA: 0x00021E28 File Offset: 0x00020028
	// (set) Token: 0x0600072D RID: 1837 RVA: 0x00021E40 File Offset: 0x00020040
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

	// Token: 0x17000206 RID: 518
	// (get) Token: 0x0600072E RID: 1838 RVA: 0x00021E4C File Offset: 0x0002004C
	// (set) Token: 0x0600072F RID: 1839 RVA: 0x00021E64 File Offset: 0x00020064
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

	// Token: 0x17000207 RID: 519
	// (get) Token: 0x06000730 RID: 1840 RVA: 0x00021E70 File Offset: 0x00020070
	public string String_1
	{
		get
		{
			return Conversions.ToString(this.ListBox_0.SelectedItem);
		}
	}

	// Token: 0x17000208 RID: 520
	// (get) Token: 0x06000731 RID: 1841 RVA: 0x00021E90 File Offset: 0x00020090
	public int Int32_0
	{
		get
		{
			return this.ListBox_0.SelectedIndex;
		}
	}

	// Token: 0x06000732 RID: 1842 RVA: 0x00021EAC File Offset: 0x000200AC
	public void method_0()
	{
		this.ListBox_0.Items.Clear();
	}

	// Token: 0x06000733 RID: 1843 RVA: 0x00021EC0 File Offset: 0x000200C0
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

	// Token: 0x06000734 RID: 1844 RVA: 0x00021F14 File Offset: 0x00020114
	protected virtual void OnCreateControl()
	{
		base.OnCreateControl();
		if (!base.Controls.Contains(this.ListBox_0))
		{
			base.Controls.Add(this.ListBox_0);
		}
	}

	// Token: 0x06000735 RID: 1845 RVA: 0x00021F44 File Offset: 0x00020144
	public void method_2(object[] object_0)
	{
		this.ListBox_0.Items.Remove("");
		this.ListBox_0.Items.AddRange(object_0);
	}

	// Token: 0x06000736 RID: 1846 RVA: 0x00021F6C File Offset: 0x0002016C
	public void method_3(object object_0)
	{
		this.ListBox_0.Items.Remove("");
		this.ListBox_0.Items.Add(RuntimeHelpers.GetObjectValue(object_0));
	}

	// Token: 0x06000737 RID: 1847 RVA: 0x00021F9C File Offset: 0x0002019C
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

	// Token: 0x06000738 RID: 1848 RVA: 0x00022184 File Offset: 0x00020384
	public GControl31()
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
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.ListBox_0.DrawMode = DrawMode.OwnerDrawFixed;
		this.ListBox_0.ScrollAlwaysVisible = false;
		this.ListBox_0.HorizontalScrollbar = false;
		this.ListBox_0.BorderStyle = BorderStyle.None;
		this.ListBox_0.BackColor = this.color_0;
		this.ListBox_0.Location = new Point(3, 3);
		this.ListBox_0.Font = new Font("Segoe UI", 8f);
		this.ListBox_0.ItemHeight = 20;
		this.ListBox_0.Items.Clear();
		this.ListBox_0.IntegralHeight = false;
		base.Size = new Size(130, 100);
	}

	// Token: 0x06000739 RID: 1849 RVA: 0x000222D0 File Offset: 0x000204D0
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
			this.ListBox_0.Size = new Size(base.Width - 6, base.Height - 5);
			graphics2.FillRectangle(new SolidBrush(this.color_0), rect);
			graphics2.DrawRectangle(new Pen(this.color_4, 3f), new Rectangle(0, 0, base.Width, base.Height - 1));
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}
	}

	// Token: 0x04000366 RID: 870
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("ListB")]
	private ListBox listBox_0;

	// Token: 0x04000367 RID: 871
	private string[] string_0;

	// Token: 0x04000368 RID: 872
	private Color color_0;

	// Token: 0x04000369 RID: 873
	private Color color_1;

	// Token: 0x0400036A RID: 874
	private Color color_2;

	// Token: 0x0400036B RID: 875
	private Color color_3;

	// Token: 0x0400036C RID: 876
	private Color color_4;
}
