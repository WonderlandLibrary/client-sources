using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Windows.Forms;
using Xh0kO1ZCmA.My;

namespace Xh0kO1ZCmA
{
	public class TabControlClass : TabControl
	{
		private Color MainColor;

		private Color TextColor;

		private bool LightTheme;

		private GraphicsPath GP1;

		private GraphicsPath GP2;

		private GraphicsPath GP3;

		private GraphicsPath GP4;

		private Rectangle R1;

		private Rectangle R2;

		private Pen P1;

		private Pen P2;

		private Pen P3;

		private SolidBrush B1;

		private SolidBrush B2;

		private SolidBrush B3;

		private SolidBrush B4;

		private PathGradientBrush PB1;

		private TabPage TP1;

		private StringFormat SF1;

		private int Offset;

		private int ItemHeight;

		private GraphicsPath CreateRoundPath;

		private Rectangle CreateRoundRectangle;

		public Color FontColor
		{
			get
			{
				return this.TextColor;
			}
			set
			{
				this.TextColor = value;
				this.Refresh();
			}
		}

		public Color TabColor
		{
			get
			{
				return this.MainColor;
			}
			set
			{
				this.MainColor = value;
				this.Refresh();
			}
		}

		public bool UseLightTheme
		{
			get
			{
				return this.LightTheme;
			}
			set
			{
				this.LightTheme = value;
				this.Refresh();
			}
		}

		public TabControlClass()
		{
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.SizeMode = TabSizeMode.Fixed;
			base.ItemSize = new System.Drawing.Size(100, 40);
			base.Size = new System.Drawing.Size(400, 250);
			this.MainColor = Color.DeepPink;
			this.TextColor = Color.White;
			this.LightTheme = true;
			base.Alignment = TabAlignment.Top;
		}

		public GraphicsPath CreateRound(int x, int y, int width, int height, int slope)
		{
			this.CreateRoundRectangle = new Rectangle(x, y, width, height);
			return this.CreateRound(this.CreateRoundRectangle, slope);
		}

		public GraphicsPath CreateRound(Rectangle r, int slope)
		{
			this.CreateRoundPath = new GraphicsPath(FillMode.Winding);
			this.CreateRoundPath.AddArc(r.X, r.Y, slope, slope, 180f, 90f);
			this.CreateRoundPath.AddArc(checked(r.Right - slope), r.Y, slope, slope, 270f, 90f);
			this.CreateRoundPath.AddArc(checked(r.Right - slope), checked(r.Bottom - slope), slope, slope, 0f, 90f);
			this.CreateRoundPath.AddArc(r.X, checked(r.Bottom - slope), slope, slope, 90f, 90f);
			this.CreateRoundPath.CloseFigure();
			return this.CreateRoundPath;
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			System.Drawing.Size itemSize;
			Rectangle rectangle;
			Rectangle rectangle1 = new Rectangle();
			Rectangle rectangle2;
			Rectangle tabRect;
			Point location;
			Rectangle rectangle3;
			Rectangle rectangle4;
			Rectangle rectangle5 = new Rectangle();
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphic = Graphics.FromImage(bitmap);
			if (MyProject.Forms.Form1.LightTheme)
			{
				try
				{
					base.SelectedTab.BackColor = Color.FromArgb(255, 255, 255, 255);
				}
				catch (Exception exception)
				{
					ProjectData.SetProjectError(exception);
					ProjectData.ClearProjectError();
				}
				graphic.Clear(Color.FromArgb(255, 255, 255, 255));
			}
			else
			{
				try
				{
					base.SelectedTab.BackColor = Color.FromArgb(255, 42, 42, 42);
				}
				catch (Exception exception1)
				{
					ProjectData.SetProjectError(exception1);
					ProjectData.ClearProjectError();
				}
				graphic.Clear(Color.FromArgb(255, 42, 42, 42));
			}
			if (!MyProject.Forms.Form1.LightTheme)
			{
				if (base.Alignment == TabAlignment.Top)
				{
					SolidBrush solidBrush = new SolidBrush(Color.FromArgb(255, 34, 34, 34));
					int width = base.Width;
					itemSize = base.ItemSize;
					graphic.FillRectangle(solidBrush, new Rectangle(0, 0, width, itemSize.Height));
				}
				else if (base.Alignment == TabAlignment.Bottom)
				{
					SolidBrush solidBrush1 = new SolidBrush(Color.FromArgb(255, 34, 34, 34));
					int height = base.Height;
					itemSize = base.ItemSize;
					int num = checked(height - itemSize.Height);
					int width1 = base.Width;
					itemSize = base.ItemSize;
					graphic.FillRectangle(solidBrush1, new Rectangle(0, num, width1, itemSize.Height));
				}
				else if (base.Alignment == TabAlignment.Left)
				{
					SolidBrush solidBrush2 = new SolidBrush(Color.FromArgb(255, 34, 34, 34));
					itemSize = base.ItemSize;
					graphic.FillRectangle(solidBrush2, new Rectangle(0, 0, itemSize.Height, base.Height));
				}
				else if (base.Alignment == TabAlignment.Right)
				{
					SolidBrush solidBrush3 = new SolidBrush(Color.FromArgb(255, 34, 34, 34));
					int num1 = base.Width;
					itemSize = base.ItemSize;
					int height1 = checked(num1 - itemSize.Height);
					itemSize = base.ItemSize;
					graphic.FillRectangle(solidBrush3, new Rectangle(height1, 0, itemSize.Height, base.Height));
				}
			}
			else if (base.Alignment == TabAlignment.Top)
			{
				SolidBrush solidBrush4 = new SolidBrush(Color.FromArgb(255, 220, 220, 220));
				int width2 = base.Width;
				itemSize = base.ItemSize;
				graphic.FillRectangle(solidBrush4, new Rectangle(0, 0, width2, itemSize.Height));
			}
			else if (base.Alignment == TabAlignment.Bottom)
			{
				SolidBrush solidBrush5 = new SolidBrush(Color.FromArgb(255, 230, 230, 230));
				int height2 = base.Height;
				itemSize = base.ItemSize;
				int num2 = checked(height2 - itemSize.Height);
				int width3 = base.Width;
				itemSize = base.ItemSize;
				graphic.FillRectangle(solidBrush5, new Rectangle(0, num2, width3, itemSize.Height));
			}
			else if (base.Alignment == TabAlignment.Left)
			{
				SolidBrush solidBrush6 = new SolidBrush(Color.FromArgb(255, 230, 230, 230));
				itemSize = base.ItemSize;
				graphic.FillRectangle(solidBrush6, new Rectangle(0, 0, itemSize.Height, base.Height));
			}
			else if (base.Alignment == TabAlignment.Right)
			{
				SolidBrush solidBrush7 = new SolidBrush(Color.FromArgb(255, 230, 230, 230));
				int num3 = base.Width;
				itemSize = base.ItemSize;
				int height3 = checked(num3 - itemSize.Height);
				itemSize = base.ItemSize;
				graphic.FillRectangle(solidBrush7, new Rectangle(height3, 0, itemSize.Height, base.Height));
			}
			if (!MyProject.Forms.Form1.LightTheme)
			{
				if (base.Alignment == TabAlignment.Top)
				{
					Pen pen = this.ToPen(this.MainColor);
					itemSize = base.ItemSize;
					Point point = new Point(0, checked(checked(itemSize.Height - 10) + 10));
					int width4 = base.Width;
					itemSize = base.ItemSize;
					graphic.DrawLine(pen, point, new Point(width4, checked(checked(itemSize.Height - 10) + 10)));
				}
				else if (base.Alignment == TabAlignment.Bottom)
				{
					Pen pen1 = this.ToPen(this.MainColor);
					int height4 = base.Height;
					itemSize = base.ItemSize;
					Point point1 = new Point(0, checked(height4 - itemSize.Height));
					int num4 = base.Width;
					int height5 = base.Height;
					itemSize = base.ItemSize;
					graphic.DrawLine(pen1, point1, new Point(num4, checked(height5 - itemSize.Height)));
				}
				else if (base.Alignment == TabAlignment.Left)
				{
					Pen pen2 = this.ToPen(this.MainColor);
					itemSize = base.ItemSize;
					Point point2 = new Point(itemSize.Height, 0);
					itemSize = base.ItemSize;
					graphic.DrawLine(pen2, point2, new Point(itemSize.Height, base.Height));
				}
				else if (base.Alignment == TabAlignment.Right)
				{
					Pen pen3 = this.ToPen(this.MainColor);
					int width5 = base.Width;
					itemSize = base.ItemSize;
					Point point3 = new Point(checked(width5 - itemSize.Height), 0);
					int num5 = base.Width;
					itemSize = base.ItemSize;
					graphic.DrawLine(pen3, point3, new Point(checked(num5 - itemSize.Height), base.Height));
				}
			}
			else if (base.Alignment == TabAlignment.Top)
			{
				Pen pen4 = this.ToPen(this.MainColor);
				itemSize = base.ItemSize;
				Point point4 = new Point(0, checked(checked(itemSize.Height - 10) + 10));
				int width6 = base.Width;
				itemSize = base.ItemSize;
				graphic.DrawLine(pen4, point4, new Point(width6, checked(checked(itemSize.Height - 10) + 10)));
			}
			else if (base.Alignment == TabAlignment.Bottom)
			{
				Pen pen5 = new Pen(Color.FromArgb(255, 234, 234, 234));
				int height6 = base.Height;
				itemSize = base.ItemSize;
				Point point5 = new Point(0, checked(height6 - itemSize.Height));
				int num6 = base.Width;
				int height7 = base.Height;
				itemSize = base.ItemSize;
				graphic.DrawLine(pen5, point5, new Point(num6, checked(height7 - itemSize.Height)));
			}
			else if (base.Alignment == TabAlignment.Left)
			{
				Pen pen6 = new Pen(Color.FromArgb(255, 234, 234, 234));
				itemSize = base.ItemSize;
				Point point6 = new Point(itemSize.Height, 0);
				itemSize = base.ItemSize;
				graphic.DrawLine(pen6, point6, new Point(itemSize.Height, base.Height));
			}
			else if (base.Alignment == TabAlignment.Right)
			{
				Pen pen7 = new Pen(Color.FromArgb(255, 234, 234, 234));
				int width7 = base.Width;
				itemSize = base.ItemSize;
				Point point7 = new Point(checked(width7 - itemSize.Height), 0);
				int num7 = base.Width;
				itemSize = base.ItemSize;
				graphic.DrawLine(pen7, point7, new Point(checked(num7 - itemSize.Height), base.Height));
			}
			int tabCount = checked(base.TabCount - 1);
			for (int i = 0; i <= tabCount; i = checked(i + 1))
			{
				if (i != base.SelectedIndex)
				{
					if (base.Alignment != TabAlignment.Left)
					{
						location = base.GetTabRect(i).Location;
						int x = location.X;
						location = base.GetTabRect(i).Location;
						int y = location.Y;
						int width8 = base.GetTabRect(i).Width;
						tabRect = base.GetTabRect(i);
						rectangle5 = new Rectangle(x, y, width8, checked(tabRect.Height - 2));
						location = base.GetTabRect(i).Location;
						int x1 = location.X;
						location = base.GetTabRect(i).Location;
						int y1 = location.Y;
						int num8 = base.GetTabRect(i).Width;
						tabRect = base.GetTabRect(i);
						rectangle4 = new Rectangle(x1, y1, num8, checked(tabRect.Height + 15));
						location = base.GetTabRect(i).Location;
						int x2 = location.X;
						location = base.GetTabRect(i).Location;
						int y2 = location.Y;
						int width9 = base.GetTabRect(i).Width;
						tabRect = base.GetTabRect(i);
						Rectangle rectangle6 = new Rectangle(x2, y2, width9, checked(tabRect.Height + 15));
						location = base.GetTabRect(i).Location;
						int x3 = checked(location.X + 1);
						location = base.GetTabRect(i).Location;
						int y3 = checked(location.Y + 1);
						int num9 = base.GetTabRect(i).Width;
						tabRect = base.GetTabRect(i);
						rectangle3 = new Rectangle(x3, y3, num9, checked(tabRect.Height + 15));
					}
					else
					{
						location = base.GetTabRect(i).Location;
						int x4 = location.X;
						location = base.GetTabRect(i).Location;
						int y4 = location.Y;
						tabRect = base.GetTabRect(i);
						int width10 = checked(tabRect.Width + 50);
						tabRect = base.GetTabRect(i);
						rectangle4 = new Rectangle(x4, y4, width10, checked(tabRect.Height - 50));
						location = base.GetTabRect(i).Location;
						int x5 = location.X;
						location = base.GetTabRect(i).Location;
						int y5 = location.Y;
						tabRect = base.GetTabRect(i);
						int num10 = checked(tabRect.Width + 50);
						tabRect = base.GetTabRect(i);
						Rectangle rectangle7 = new Rectangle(x5, y5, num10, checked(tabRect.Height - 50));
						location = base.GetTabRect(i).Location;
						int x6 = checked(location.X + 1);
						location = base.GetTabRect(i).Location;
						int y6 = checked(location.Y + 1);
						tabRect = base.GetTabRect(i);
						int width11 = checked(tabRect.Width + 50);
						tabRect = base.GetTabRect(i);
						rectangle3 = new Rectangle(x6, y6, width11, checked(tabRect.Height - 50));
					}
					if (MyProject.Forms.Form1.LightTheme)
					{
						graphic.FillRectangle(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), rectangle5);
					}
					else
					{
						graphic.FillRectangle(new SolidBrush(Color.FromArgb(255, 34, 34, 34)), rectangle5);
					}
					if (base.Alignment == TabAlignment.Top)
					{
						graphic.DrawLine(new Pen(this.MainColor), new Point(rectangle4.Left, rectangle4.Bottom), new Point(rectangle4.Right, rectangle4.Bottom));
					}
					else if (base.Alignment == TabAlignment.Bottom)
					{
						graphic.DrawLine(new Pen(this.MainColor), new Point(rectangle4.Left, rectangle4.Top), new Point(rectangle4.Right, rectangle4.Top));
					}
					else if (base.Alignment == TabAlignment.Left)
					{
						graphic.DrawLine(new Pen(this.MainColor), new Point(rectangle4.Left, rectangle4.Top), new Point(rectangle4.Left, rectangle4.Bottom));
					}
					else if (base.Alignment == TabAlignment.Right)
					{
						graphic.DrawLine(new Pen(this.MainColor), new Point(rectangle4.Left, rectangle4.Top), new Point(rectangle4.Left, rectangle4.Bottom));
					}
					if (!MyProject.Forms.Form1.LightTheme)
					{
						if (base.ImageList == null)
						{
							graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.Black, rectangle3, new StringFormat()
							{
								LineAlignment = StringAlignment.Center,
								Alignment = StringAlignment.Center
							});
							graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.White, rectangle4, new StringFormat()
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
									graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.Black, rectangle3, new StringFormat()
									{
										LineAlignment = StringAlignment.Center,
										Alignment = StringAlignment.Center
									});
									graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.White, rectangle4, new StringFormat()
									{
										LineAlignment = StringAlignment.Center,
										Alignment = StringAlignment.Center
									});
								}
								else
								{
									Image item = base.ImageList.Images[base.TabPages[i].ImageIndex];
									location = rectangle4.Location;
									int num11 = checked((int)Math.Round((double)location.X + 42.5));
									location = rectangle4.Location;
									graphic.DrawImage(item, new Point(num11, checked(location.Y + 2)));
									graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.Black, rectangle3, new StringFormat()
									{
										LineAlignment = StringAlignment.Center,
										Alignment = StringAlignment.Center
									});
									graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.White, rectangle4, new StringFormat()
									{
										LineAlignment = StringAlignment.Center,
										Alignment = StringAlignment.Center
									});
								}
							}
							catch (Exception exception2)
							{
								ProjectData.SetProjectError(exception2);
								graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.Black, rectangle3, new StringFormat()
								{
									LineAlignment = StringAlignment.Center,
									Alignment = StringAlignment.Center
								});
								graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.White, rectangle4, new StringFormat()
								{
									LineAlignment = StringAlignment.Center,
									Alignment = StringAlignment.Center
								});
								ProjectData.ClearProjectError();
							}
						}
					}
					else if (MyProject.Forms.Form1.LightTheme)
					{
						if (base.ImageList == null)
						{
							graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.Black, rectangle4, new StringFormat()
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
									graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.Black, rectangle4, new StringFormat()
									{
										LineAlignment = StringAlignment.Center,
										Alignment = StringAlignment.Center
									});
								}
								else
								{
									Image image = base.ImageList.Images[base.TabPages[i].ImageIndex];
									location = rectangle4.Location;
									int num12 = checked((int)Math.Round((double)location.X + 42.5));
									location = rectangle4.Location;
									graphic.DrawImage(image, new Point(num12, checked(location.Y + 2)));
									graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.Black, rectangle4, new StringFormat()
									{
										LineAlignment = StringAlignment.Center,
										Alignment = StringAlignment.Center
									});
								}
							}
							catch (Exception exception3)
							{
								ProjectData.SetProjectError(exception3);
								graphic.DrawString(base.TabPages[i].Text, this.Font, Brushes.Black, rectangle4, new StringFormat()
								{
									LineAlignment = StringAlignment.Center,
									Alignment = StringAlignment.Center
								});
								ProjectData.ClearProjectError();
							}
						}
					}
				}
				else
				{
					if (base.Alignment != TabAlignment.Left)
					{
						location = base.GetTabRect(i).Location;
						int x7 = location.X;
						location = base.GetTabRect(i).Location;
						int y7 = location.Y;
						int width12 = base.GetTabRect(i).Width;
						tabRect = base.GetTabRect(i);
						rectangle = new Rectangle(x7, y7, width12, checked(tabRect.Height - 2));
						location = base.GetTabRect(i).Location;
						int x8 = location.X;
						location = base.GetTabRect(i).Location;
						int y8 = location.Y;
						int width13 = base.GetTabRect(i).Width;
						tabRect = base.GetTabRect(i);
						rectangle1 = new Rectangle(x8, y8, width13, checked(tabRect.Height + 15));
						location = base.GetTabRect(i).Location;
						int x9 = checked(location.X + 1);
						location = base.GetTabRect(i).Location;
						int y9 = checked(location.Y + 1);
						int num13 = base.GetTabRect(i).Width;
						tabRect = base.GetTabRect(i);
						rectangle2 = new Rectangle(x9, y9, num13, checked(tabRect.Height + 15));
					}
					else
					{
						location = base.GetTabRect(i).Location;
						int x10 = location.X;
						location = base.GetTabRect(i).Location;
						int y10 = location.Y;
						tabRect = base.GetTabRect(i);
						int width14 = checked(tabRect.Width + 50);
						tabRect = base.GetTabRect(i);
						rectangle = new Rectangle(x10, y10, width14, checked(tabRect.Height - 50));
						location = base.GetTabRect(i).Location;
						int x11 = checked(location.X + 1);
						location = base.GetTabRect(i).Location;
						int y11 = checked(location.Y + 1);
						tabRect = base.GetTabRect(i);
						int num14 = checked(tabRect.Width + 50);
						tabRect = base.GetTabRect(i);
						rectangle2 = new Rectangle(x11, y11, num14, checked(tabRect.Height - 50));
					}
					ColorBlend colorBlend = new ColorBlend()
					{
						Colors = new Color[] { this.MainColor, this.MainColor, this.MainColor },
						Positions = new float[] { default(float), 0.5f, 1f }
					};
					LinearGradientBrush linearGradientBrush = new LinearGradientBrush(rectangle, Color.Black, Color.Black, 90f)
					{
						InterpolationColors = colorBlend
					};
					graphic.FillRectangle(linearGradientBrush, rectangle);
					if (base.ImageList == null)
					{
						graphic.DrawString(base.TabPages[i].Text, new System.Drawing.Font(this.Font.FontFamily, this.Font.Size, FontStyle.Bold), Brushes.Black, rectangle2, new StringFormat()
						{
							LineAlignment = StringAlignment.Center,
							Alignment = StringAlignment.Center
						});
						graphic.DrawString(base.TabPages[i].Text, new System.Drawing.Font(this.Font.FontFamily, this.Font.Size, FontStyle.Bold), this.ToBrush(this.TextColor), rectangle1, new StringFormat()
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
								graphic.DrawString(base.TabPages[i].Text, new System.Drawing.Font(this.Font.FontFamily, this.Font.Size, FontStyle.Bold), this.ToBrush(this.TextColor), rectangle1, new StringFormat()
								{
									LineAlignment = StringAlignment.Center,
									Alignment = StringAlignment.Center
								});
							}
							else
							{
								Image item1 = base.ImageList.Images[base.TabPages[i].ImageIndex];
								location = rectangle.Location;
								int num15 = checked((int)Math.Round((double)location.X + 42.5));
								location = rectangle.Location;
								graphic.DrawImage(item1, new Point(num15, checked(location.Y + 2)));
								graphic.DrawString(base.TabPages[i].Text, new System.Drawing.Font(this.Font.FontFamily, this.Font.Size, FontStyle.Bold), Brushes.Black, rectangle2, new StringFormat()
								{
									LineAlignment = StringAlignment.Center,
									Alignment = StringAlignment.Center
								});
								graphic.DrawString(base.TabPages[i].Text, new System.Drawing.Font(this.Font.FontFamily, this.Font.Size, FontStyle.Bold), this.ToBrush(this.TextColor), rectangle1, new StringFormat()
								{
									LineAlignment = StringAlignment.Center,
									Alignment = StringAlignment.Center
								});
							}
						}
						catch (Exception exception4)
						{
							ProjectData.SetProjectError(exception4);
							graphic.DrawString(base.TabPages[i].Text, new System.Drawing.Font(this.Font.FontFamily, this.Font.Size, FontStyle.Bold), Brushes.Black, rectangle2, new StringFormat()
							{
								LineAlignment = StringAlignment.Center,
								Alignment = StringAlignment.Center
							});
							graphic.DrawString(base.TabPages[i].Text, new System.Drawing.Font(this.Font.FontFamily, this.Font.Size, FontStyle.Bold), this.ToBrush(this.TextColor), rectangle1, new StringFormat()
							{
								LineAlignment = StringAlignment.Center,
								Alignment = StringAlignment.Center
							});
							ProjectData.ClearProjectError();
						}
					}
				}
			}
			NewLateBinding.LateCall(e.Graphics, null, "DrawImage", new object[] { bitmap.Clone(), 0, 0 }, null, null, null, true);
			graphic.Dispose();
			bitmap.Dispose();
		}

		public Bitmap RotateImage(ref Image image, PointF offset, decimal angle)
		{
			if (image == null)
			{
				throw new ArgumentNullException("image");
			}
			Bitmap bitmap = new Bitmap(image.Width, image.Height);
			bitmap.SetResolution(image.HorizontalResolution, image.VerticalResolution);
			Graphics graphic = Graphics.FromImage(bitmap);
			graphic.TranslateTransform(offset.X, offset.Y);
			graphic.RotateTransform(Convert.ToSingle(angle));
			graphic.TranslateTransform(-offset.X, -offset.Y);
			graphic.DrawImage(image, offset);
			return bitmap;
		}

		public Brush ToBrush(Color color)
		{
			return new SolidBrush(color);
		}

		public Pen ToPen(Color color)
		{
			return new Pen(color);
		}
	}
}