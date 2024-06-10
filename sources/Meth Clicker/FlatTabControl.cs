using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000022 RID: 34
	internal class FlatTabControl : TabControl
	{
		// Token: 0x06000288 RID: 648 RVA: 0x000123AF File Offset: 0x000105AF
		protected override void CreateHandle()
		{
			base.CreateHandle();
			base.Alignment = TabAlignment.Top;
		}

		// Token: 0x170000BF RID: 191
		// (get) Token: 0x06000289 RID: 649 RVA: 0x000123BE File Offset: 0x000105BE
		// (set) Token: 0x0600028A RID: 650 RVA: 0x000123C6 File Offset: 0x000105C6
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

		// Token: 0x170000C0 RID: 192
		// (get) Token: 0x0600028B RID: 651 RVA: 0x000123CF File Offset: 0x000105CF
		// (set) Token: 0x0600028C RID: 652 RVA: 0x000123D7 File Offset: 0x000105D7
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

		// Token: 0x0600028D RID: 653 RVA: 0x000123E0 File Offset: 0x000105E0
		public FlatTabControl()
		{
			this.BGColor = Color.FromArgb(50, 50, 50);
			this._BaseColor = Color.FromArgb(50, 50, 50);
			this._ActiveColor = Color.FromArgb(30, 144, 255);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(50, 50, 50);
			this.Font = new Font("Segoe UI", 10f);
			base.SizeMode = TabSizeMode.Normal;
			base.ItemSize = new Size(50, 20);
		}

		// Token: 0x0600028E RID: 654 RVA: 0x0001247C File Offset: 0x0001067C
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
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

		// Token: 0x0400014B RID: 331
		private int W;

		// Token: 0x0400014C RID: 332
		private int H;

		// Token: 0x0400014D RID: 333
		private Color BGColor;

		// Token: 0x0400014E RID: 334
		private Color _BaseColor;

		// Token: 0x0400014F RID: 335
		private Color _ActiveColor;
	}
}
