using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x0200004B RID: 75
internal class Control33 : TabControl
{
	// Token: 0x060003FD RID: 1021 RVA: 0x000130F8 File Offset: 0x000112F8
	protected virtual void CreateHandle()
	{
		base.CreateHandle();
		base.Alignment = TabAlignment.Top;
	}

	// Token: 0x1700013A RID: 314
	// (get) Token: 0x060003FE RID: 1022 RVA: 0x00013108 File Offset: 0x00011308
	// (set) Token: 0x060003FF RID: 1023 RVA: 0x00013120 File Offset: 0x00011320
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

	// Token: 0x1700013B RID: 315
	// (get) Token: 0x06000400 RID: 1024 RVA: 0x0001312C File Offset: 0x0001132C
	// (set) Token: 0x06000401 RID: 1025 RVA: 0x00013144 File Offset: 0x00011344
	[Category("Colors")]
	public Color Color_1
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

	// Token: 0x06000402 RID: 1026 RVA: 0x00013150 File Offset: 0x00011350
	public Control33()
	{
		this.color_0 = Color.FromArgb(60, 70, 73);
		this.color_1 = Color.FromArgb(45, 47, 49);
		this.color_2 = Class16.color_0;
		base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
		this.DoubleBuffered = true;
		this.BackColor = Color.FromArgb(60, 70, 73);
		this.Font = new Font("Segoe UI", 10f);
		base.SizeMode = TabSizeMode.Fixed;
		base.ItemSize = new Size(120, 40);
	}

	// Token: 0x06000403 RID: 1027 RVA: 0x000131E0 File Offset: 0x000113E0
	protected virtual void OnPaint(PaintEventArgs e)
	{
		Class16.bitmap_0 = new Bitmap(base.Width, base.Height);
		Class16.graphics_0 = Graphics.FromImage(Class16.bitmap_0);
		checked
		{
			this.int_0 = base.Width - 1;
			this.int_1 = base.Height - 1;
			Graphics graphics = Class16.graphics_0;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.Clear(this.color_1);
			try
			{
				base.SelectedTab.BackColor = this.color_0;
			}
			catch (Exception ex)
			{
			}
			int num = base.TabCount - 1;
			for (int i = 0; i <= num; i++)
			{
				Rectangle rectangle = new Rectangle(new Point(base.GetTabRect(i).Location.X + 2, base.GetTabRect(i).Location.Y), new Size(base.GetTabRect(i).Width, base.GetTabRect(i).Height));
				Rectangle rectangle2 = new Rectangle(rectangle.Location, new Size(rectangle.Width, rectangle.Height));
				if (i == base.SelectedIndex)
				{
					graphics.FillRectangle(new SolidBrush(this.color_1), rectangle2);
					graphics.FillRectangle(new SolidBrush(this.color_2), rectangle2);
					if (base.ImageList != null)
					{
						try
						{
							if (base.ImageList.Images[base.TabPages[i].ImageIndex] != null)
							{
								graphics.DrawImage(base.ImageList.Images[base.TabPages[i].ImageIndex], new Point(rectangle2.Location.X + 8, rectangle2.Location.Y + 6));
								graphics.DrawString("      " + base.TabPages[i].Text, this.Font, Brushes.White, rectangle2, Class16.stringFormat_1);
							}
							else
							{
								graphics.DrawString(base.TabPages[i].Text, this.Font, Brushes.White, rectangle2, Class16.stringFormat_1);
							}
							goto IL_40F;
						}
						catch (Exception ex2)
						{
							throw new Exception(ex2.Message);
						}
					}
					graphics.DrawString(base.TabPages[i].Text, this.Font, Brushes.White, rectangle2, Class16.stringFormat_1);
				}
				else
				{
					graphics.FillRectangle(new SolidBrush(this.color_1), rectangle2);
					if (base.ImageList != null)
					{
						try
						{
							if (base.ImageList.Images[base.TabPages[i].ImageIndex] == null)
							{
								graphics.DrawString(base.TabPages[i].Text, this.Font, new SolidBrush(Color.White), rectangle2, new StringFormat
								{
									LineAlignment = StringAlignment.Center,
									Alignment = StringAlignment.Center
								});
							}
							else
							{
								graphics.DrawImage(base.ImageList.Images[base.TabPages[i].ImageIndex], new Point(rectangle2.Location.X + 8, rectangle2.Location.Y + 6));
								graphics.DrawString("      " + base.TabPages[i].Text, this.Font, new SolidBrush(Color.White), rectangle2, new StringFormat
								{
									LineAlignment = StringAlignment.Center,
									Alignment = StringAlignment.Center
								});
							}
							goto IL_40F;
						}
						catch (Exception ex3)
						{
							throw new Exception(ex3.Message);
						}
					}
					graphics.DrawString(base.TabPages[i].Text, this.Font, new SolidBrush(Color.White), rectangle2, new StringFormat
					{
						LineAlignment = StringAlignment.Center,
						Alignment = StringAlignment.Center
					});
				}
				IL_40F:;
			}
			graphics = null;
			base.OnPaint(e);
			Class16.graphics_0.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Class16.bitmap_0, 0, 0);
			Class16.bitmap_0.Dispose();
		}
	}

	// Token: 0x0400021C RID: 540
	private int int_0;

	// Token: 0x0400021D RID: 541
	private int int_1;

	// Token: 0x0400021E RID: 542
	private Color color_0;

	// Token: 0x0400021F RID: 543
	private Color color_1;

	// Token: 0x04000220 RID: 544
	private Color color_2;
}
