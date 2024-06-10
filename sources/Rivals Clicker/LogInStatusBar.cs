using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x0200002A RID: 42
	public class LogInStatusBar : Control
	{
		// Token: 0x170000FD RID: 253
		// (get) Token: 0x060002C2 RID: 706 RVA: 0x0000FCA4 File Offset: 0x0000DEA4
		// (set) Token: 0x060002C3 RID: 707 RVA: 0x000033E9 File Offset: 0x000015E9
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._BaseColour;
			}
			set
			{
				this._BaseColour = value;
			}
		}

		// Token: 0x170000FE RID: 254
		// (get) Token: 0x060002C4 RID: 708 RVA: 0x0000FCBC File Offset: 0x0000DEBC
		// (set) Token: 0x060002C5 RID: 709 RVA: 0x000033F3 File Offset: 0x000015F3
		[Category("Colours")]
		public Color BorderColour
		{
			get
			{
				return this._BorderColour;
			}
			set
			{
				this._BorderColour = value;
			}
		}

		// Token: 0x170000FF RID: 255
		// (get) Token: 0x060002C6 RID: 710 RVA: 0x0000FCD4 File Offset: 0x0000DED4
		// (set) Token: 0x060002C7 RID: 711 RVA: 0x000033FD File Offset: 0x000015FD
		[Category("Colours")]
		public Color TextColour
		{
			get
			{
				return this._TextColour;
			}
			set
			{
				this._TextColour = value;
			}
		}

		// Token: 0x17000100 RID: 256
		// (get) Token: 0x060002C8 RID: 712 RVA: 0x0000FCEC File Offset: 0x0000DEEC
		// (set) Token: 0x060002C9 RID: 713 RVA: 0x00003407 File Offset: 0x00001607
		[Category("Control")]
		public LogInStatusBar.Alignments Alignment
		{
			get
			{
				return this._Alignment;
			}
			set
			{
				this._Alignment = value;
			}
		}

		// Token: 0x17000101 RID: 257
		// (get) Token: 0x060002CA RID: 714 RVA: 0x0000FD04 File Offset: 0x0000DF04
		// (set) Token: 0x060002CB RID: 715 RVA: 0x00003411 File Offset: 0x00001611
		[Category("Control")]
		public LogInStatusBar.LinesCount LinesToShow
		{
			get
			{
				return this._LinesToShow;
			}
			set
			{
				this._LinesToShow = value;
			}
		}

		// Token: 0x17000102 RID: 258
		// (get) Token: 0x060002CC RID: 716 RVA: 0x0000FD1C File Offset: 0x0000DF1C
		// (set) Token: 0x060002CD RID: 717 RVA: 0x0000341B File Offset: 0x0000161B
		public bool ShowBorder
		{
			get
			{
				return this._ShowBorder;
			}
			set
			{
				this._ShowBorder = value;
			}
		}

		// Token: 0x060002CE RID: 718 RVA: 0x00003425 File Offset: 0x00001625
		protected override void CreateHandle()
		{
			base.CreateHandle();
			this.Dock = DockStyle.Bottom;
		}

		// Token: 0x060002CF RID: 719 RVA: 0x00002BD3 File Offset: 0x00000DD3
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x17000103 RID: 259
		// (get) Token: 0x060002D0 RID: 720 RVA: 0x0000FD34 File Offset: 0x0000DF34
		// (set) Token: 0x060002D1 RID: 721 RVA: 0x00003437 File Offset: 0x00001637
		[Category("Colours")]
		public Color RectangleColor
		{
			get
			{
				return this._RectColour;
			}
			set
			{
				this._RectColour = value;
			}
		}

		// Token: 0x17000104 RID: 260
		// (get) Token: 0x060002D2 RID: 722 RVA: 0x0000FD4C File Offset: 0x0000DF4C
		// (set) Token: 0x060002D3 RID: 723 RVA: 0x00003441 File Offset: 0x00001641
		public bool ShowLine
		{
			get
			{
				return this._ShowLine;
			}
			set
			{
				this._ShowLine = value;
			}
		}

		// Token: 0x060002D4 RID: 724 RVA: 0x0000FD64 File Offset: 0x0000DF64
		public LogInStatusBar()
		{
			this._BaseColour = Color.FromArgb(42, 42, 42);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._TextColour = Color.White;
			this._RectColour = Color.FromArgb(21, 117, 149);
			this._ShowLine = true;
			this._LinesToShow = LogInStatusBar.LinesCount.One;
			this._Alignment = LogInStatusBar.Alignments.Left;
			this._ShowBorder = true;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Font = new Font("Segoe UI", 9f);
			this.ForeColor = Color.White;
			base.Size = new Size(base.Width, 20);
		}

		// Token: 0x060002D5 RID: 725 RVA: 0x0000FE24 File Offset: 0x0000E024
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			Rectangle Base = new Rectangle(0, 0, base.Width, base.Height);
			Graphics graphics = G;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.Clear(this.BaseColour);
			graphics.FillRectangle(new SolidBrush(this.BaseColour), Base);
			bool showLine = this._ShowLine;
			checked
			{
				if (showLine)
				{
					LogInStatusBar.LinesCount linesToShow = this._LinesToShow;
					if (linesToShow != LogInStatusBar.LinesCount.One)
					{
						if (linesToShow == LogInStatusBar.LinesCount.Two)
						{
							bool flag = this._Alignment == LogInStatusBar.Alignments.Left;
							if (flag)
							{
								graphics.DrawString(this.Text, this.Font, new SolidBrush(this._TextColour), new Rectangle(22, 2, base.Width, base.Height), new StringFormat
								{
									Alignment = StringAlignment.Near,
									LineAlignment = StringAlignment.Near
								});
							}
							else
							{
								bool flag2 = this._Alignment == LogInStatusBar.Alignments.Center;
								if (flag2)
								{
									graphics.DrawString(this.Text, this.Font, new SolidBrush(this._TextColour), new Rectangle(0, 0, base.Width, base.Height), new StringFormat
									{
										Alignment = StringAlignment.Center,
										LineAlignment = StringAlignment.Center
									});
								}
								else
								{
									graphics.DrawString(this.Text, this.Font, new SolidBrush(this._TextColour), new Rectangle(0, 0, base.Width - 22, base.Height), new StringFormat
									{
										Alignment = StringAlignment.Far,
										LineAlignment = StringAlignment.Center
									});
								}
							}
							graphics.FillRectangle(new SolidBrush(this._RectColour), new Rectangle(5, 9, 14, 3));
							graphics.FillRectangle(new SolidBrush(this._RectColour), new Rectangle(base.Width - 20, 9, 14, 3));
						}
					}
					else
					{
						bool flag3 = this._Alignment == LogInStatusBar.Alignments.Left;
						if (flag3)
						{
							graphics.DrawString(this.Text, this.Font, new SolidBrush(this._TextColour), new Rectangle(22, 2, base.Width, base.Height), new StringFormat
							{
								Alignment = StringAlignment.Near,
								LineAlignment = StringAlignment.Near
							});
						}
						else
						{
							bool flag4 = this._Alignment == LogInStatusBar.Alignments.Center;
							if (flag4)
							{
								graphics.DrawString(this.Text, this.Font, new SolidBrush(this._TextColour), new Rectangle(0, 0, base.Width, base.Height), new StringFormat
								{
									Alignment = StringAlignment.Center,
									LineAlignment = StringAlignment.Center
								});
							}
							else
							{
								graphics.DrawString(this.Text, this.Font, new SolidBrush(this._TextColour), new Rectangle(0, 0, base.Width - 5, base.Height), new StringFormat
								{
									Alignment = StringAlignment.Far,
									LineAlignment = StringAlignment.Center
								});
							}
						}
						graphics.FillRectangle(new SolidBrush(this._RectColour), new Rectangle(5, 9, 14, 3));
					}
				}
				else
				{
					graphics.DrawString(this.Text, this.Font, Brushes.White, new Rectangle(5, 2, base.Width, base.Height), new StringFormat
					{
						Alignment = StringAlignment.Near,
						LineAlignment = StringAlignment.Near
					});
				}
				bool showBorder = this._ShowBorder;
				if (showBorder)
				{
					graphics.DrawLine(new Pen(this._BorderColour, 2f), new Point(0, 0), new Point(base.Width, 0));
				}
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x04000111 RID: 273
		private Color _BaseColour;

		// Token: 0x04000112 RID: 274
		private Color _BorderColour;

		// Token: 0x04000113 RID: 275
		private Color _TextColour;

		// Token: 0x04000114 RID: 276
		private Color _RectColour;

		// Token: 0x04000115 RID: 277
		private bool _ShowLine;

		// Token: 0x04000116 RID: 278
		private LogInStatusBar.LinesCount _LinesToShow;

		// Token: 0x04000117 RID: 279
		private LogInStatusBar.Alignments _Alignment;

		// Token: 0x04000118 RID: 280
		private bool _ShowBorder;

		// Token: 0x0200002B RID: 43
		public enum LinesCount
		{
			// Token: 0x0400011A RID: 282
			One = 1,
			// Token: 0x0400011B RID: 283
			Two
		}

		// Token: 0x0200002C RID: 44
		public enum Alignments
		{
			// Token: 0x0400011D RID: 285
			Left,
			// Token: 0x0400011E RID: 286
			Center,
			// Token: 0x0400011F RID: 287
			Right
		}
	}
}
