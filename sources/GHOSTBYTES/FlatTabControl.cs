using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x0200001D RID: 29
	internal class FlatTabControl : TabControl
	{
		// Token: 0x06000182 RID: 386 RVA: 0x000035F7 File Offset: 0x000017F7
		protected override void CreateHandle()
		{
			base.CreateHandle();
			base.Alignment = TabAlignment.Top;
		}

		// Token: 0x17000088 RID: 136
		// (get) Token: 0x06000183 RID: 387 RVA: 0x00003606 File Offset: 0x00001806
		// (set) Token: 0x06000184 RID: 388 RVA: 0x0000360E File Offset: 0x0000180E
		[Category("Colors")]
		public Color BaseColor
		{
			get
			{
				return this._BaseColor;
			}
			set
			{
				this._BaseColor = value;
			}
		}

		// Token: 0x17000089 RID: 137
		// (get) Token: 0x06000185 RID: 389 RVA: 0x00003617 File Offset: 0x00001817
		// (set) Token: 0x06000186 RID: 390 RVA: 0x0000361F File Offset: 0x0000181F
		[Category("Colors")]
		public Color ActiveColor
		{
			get
			{
				return this._ActiveColor;
			}
			set
			{
				this._ActiveColor = value;
			}
		}

		// Token: 0x06000187 RID: 391 RVA: 0x00007DC4 File Offset: 0x00005FC4
		public FlatTabControl()
		{
			this.BGColor = Color.FromArgb(50, 50, 50);
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._ActiveColor = Color.FromArgb(0, 170, 220);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(60, 70, 73);
			this.Font = new Font("Segoe UI", 10f);
			base.SizeMode = TabSizeMode.Fixed;
			base.ItemSize = new Size(120, 40);
		}

		// Token: 0x06000188 RID: 392 RVA: 0x00007E60 File Offset: 0x00006060
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			checked
			{
				this.W = base.Width - 1;
				this.H = base.Height - 1;
				Graphics graphics = Helpers.G;
				graphics.SmoothingMode = SmoothingMode.HighQuality;
				graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
				graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				graphics.Clear(this._BaseColor);
				try
				{
					base.SelectedTab.BackColor = this.BGColor;
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
						graphics.FillRectangle(new SolidBrush(this._BaseColor), rectangle2);
						graphics.FillRectangle(new SolidBrush(this._ActiveColor), rectangle2);
						if (base.ImageList != null)
						{
							try
							{
								if (base.ImageList.Images[base.TabPages[i].ImageIndex] != null)
								{
									graphics.DrawImage(base.ImageList.Images[base.TabPages[i].ImageIndex], new Point(rectangle2.Location.X + 8, rectangle2.Location.Y + 6));
									graphics.DrawString("      " + base.TabPages[i].Text, this.Font, Brushes.White, rectangle2, Helpers.CenterSF);
								}
								else
								{
									graphics.DrawString(base.TabPages[i].Text, this.Font, Brushes.White, rectangle2, Helpers.CenterSF);
								}
								goto IL_3FF;
							}
							catch (Exception ex2)
							{
								throw new Exception(ex2.Message);
							}
						}
						graphics.DrawString(base.TabPages[i].Text, this.Font, Brushes.White, rectangle2, Helpers.CenterSF);
					}
					else
					{
						graphics.FillRectangle(new SolidBrush(this._BaseColor), rectangle2);
						if (base.ImageList != null)
						{
							try
							{
								if (base.ImageList.Images[base.TabPages[i].ImageIndex] != null)
								{
									graphics.DrawImage(base.ImageList.Images[base.TabPages[i].ImageIndex], new Point(rectangle2.Location.X + 8, rectangle2.Location.Y + 6));
									graphics.DrawString("      " + base.TabPages[i].Text, this.Font, new SolidBrush(Color.White), rectangle2, new StringFormat
									{
										LineAlignment = StringAlignment.Center,
										Alignment = StringAlignment.Center
									});
								}
								else
								{
									graphics.DrawString(base.TabPages[i].Text, this.Font, new SolidBrush(Color.White), rectangle2, new StringFormat
									{
										LineAlignment = StringAlignment.Center,
										Alignment = StringAlignment.Center
									});
								}
								goto IL_3FF;
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
					IL_3FF:;
				}
				graphics = null;
				base.OnPaint(e);
				Helpers.G.Dispose();
				e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
				Helpers.B.Dispose();
			}
		}

		// Token: 0x04000075 RID: 117
		private int W;

		// Token: 0x04000076 RID: 118
		private int H;

		// Token: 0x04000077 RID: 119
		private Color BGColor;

		// Token: 0x04000078 RID: 120
		private Color _BaseColor;

		// Token: 0x04000079 RID: 121
		private Color _ActiveColor;
	}
}
