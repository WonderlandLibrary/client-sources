using Microsoft.VisualBasic.CompilerServices;
using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatTabControl : TabControl
	{
		private int W;

		private int H;

		private Color BGColor;

		private Color _BaseColor;

		private Color _ActiveColor;

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

		public FlatTabControl()
		{
			this.BGColor = Color.FromArgb(60, 70, 73);
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._ActiveColor = Helpers._FlatColor;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(60, 70, 73);
			this.Font = new System.Drawing.Font("Segoe UI", 10f);
			base.SizeMode = TabSizeMode.Fixed;
			base.ItemSize = new System.Drawing.Size(120, 40);
		}

		protected override void CreateHandle()
		{
			base.CreateHandle();
			base.Alignment = TabAlignment.Top;
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = checked(base.Width - 1);
			this.H = checked(base.Height - 1);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this._BaseColor);
			try
			{
				base.SelectedTab.BackColor = this.BGColor;
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				ProjectData.ClearProjectError();
			}
			int tabCount = checked(base.TabCount - 1);
			for (int i = 0; i <= tabCount; i = checked(i + 1))
			{
				Point location = base.GetTabRect(i).Location;
				int x = checked(location.X + 2);
				location = base.GetTabRect(i).Location;
				Point point = new Point(x, location.Y);
				int width = base.GetTabRect(i).Width;
				Rectangle tabRect = base.GetTabRect(i);
				Rectangle rectangle = new Rectangle(point, new System.Drawing.Size(width, tabRect.Height));
				Rectangle rectangle1 = new Rectangle(rectangle.Location, new System.Drawing.Size(rectangle.Width, rectangle.Height));
				if (i != base.SelectedIndex)
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rectangle1);
					if (base.ImageList == null)
					{
						g.DrawString(base.TabPages[i].Text, this.Font, new SolidBrush(Color.White), rectangle1, new StringFormat()
						{
							LineAlignment = StringAlignment.Center,
							Alignment = StringAlignment.Center
						});
					}
					else
					{
						try
						{
							if (base.ImageList.Images[base.TabPages[i].ImageIndex] == null)
							{
								g.DrawString(base.TabPages[i].Text, this.Font, new SolidBrush(Color.White), rectangle1, new StringFormat()
								{
									LineAlignment = StringAlignment.Center,
									Alignment = StringAlignment.Center
								});
							}
							else
							{
								Image item = base.ImageList.Images[base.TabPages[i].ImageIndex];
								location = rectangle1.Location;
								int num = checked(location.X + 8);
								location = rectangle1.Location;
								g.DrawImage(item, new Point(num, checked(location.Y + 6)));
								g.DrawString(string.Concat("      ", base.TabPages[i].Text), this.Font, new SolidBrush(Color.White), rectangle1, new StringFormat()
								{
									LineAlignment = StringAlignment.Center,
									Alignment = StringAlignment.Center
								});
							}
						}
						catch (Exception exception1)
						{
							ProjectData.SetProjectError(exception1);
							throw new Exception(exception1.Message);
						}
					}
				}
				else
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rectangle1);
					g.FillRectangle(new SolidBrush(this._ActiveColor), rectangle1);
					if (base.ImageList == null)
					{
						g.DrawString(base.TabPages[i].Text, this.Font, Brushes.White, rectangle1, Helpers.CenterSF);
					}
					else
					{
						try
						{
							if (base.ImageList.Images[base.TabPages[i].ImageIndex] == null)
							{
								g.DrawString(base.TabPages[i].Text, this.Font, Brushes.White, rectangle1, Helpers.CenterSF);
							}
							else
							{
								Image image = base.ImageList.Images[base.TabPages[i].ImageIndex];
								location = rectangle1.Location;
								int x1 = checked(location.X + 8);
								location = rectangle1.Location;
								g.DrawImage(image, new Point(x1, checked(location.Y + 6)));
								g.DrawString(string.Concat("      ", base.TabPages[i].Text), this.Font, Brushes.White, rectangle1, Helpers.CenterSF);
							}
						}
						catch (Exception exception2)
						{
							ProjectData.SetProjectError(exception2);
							throw new Exception(exception2.Message);
						}
					}
				}
			}
			g = null;
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}
	}
}