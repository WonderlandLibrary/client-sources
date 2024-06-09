using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;
using Xh0kO1ZCmA.My;

namespace Xh0kO1ZCmA
{
	[DefaultEvent("CheckedChanged")]
	internal class FlatToggle3 : Control
	{
		private int W;

		private int H;

		private FlatToggle3._Options O;

		private bool _Checked;

		private MouseState State;

		private Color BaseColor;

		private Color BaseColor2;

		private Color BaseColor3;

		private Color BaseColorRed;

		private Color BGColor;

		private Color ToggleColor;

		private Color TextColor;

		private ImageAttributes ImgAtt;

		private bool StartFade;

		private float Alpha;

		private float SpeedFade;

		private Bitmap ImgHover;

		private Graphics GrHover;

		private Image _SetImage;

		[Category("Options")]
		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
			}
		}

		[Category("Options")]
		public FlatToggle3._Options Options
		{
			get
			{
				return this.O;
			}
			set
			{
				this.O = value;
			}
		}

		private Image SetImage
		{
			get
			{
				return this._SetImage;
			}
			set
			{
				this._SetImage = value;
				base.Invalidate();
			}
		}

		internal virtual System.Windows.Forms.Timer TmrFade
		{
			get
			{
				return this._TmrFade;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.TmrFade_Tick);
				System.Windows.Forms.Timer timer = this._TmrFade;
				if (timer != null)
				{
					timer.Tick -= eventHandler;
				}
				this._TmrFade = value;
				timer = this._TmrFade;
				if (timer != null)
				{
					timer.Tick += eventHandler;
				}
			}
		}

		public FlatToggle3()
		{
			this._Checked = false;
			this.State = MouseState.None;
			this.BaseColor = Helpers._FlatColor;
			this.BaseColor2 = Color.FromArgb(255, 53, 53, 53);
			this.BaseColor3 = Color.FromArgb(255, 220, 220, 220);
			this.BaseColorRed = Color.FromArgb(220, 85, 96);
			this.BGColor = Color.FromArgb(84, 85, 86);
			this.ToggleColor = Color.FromArgb(45, 47, 49);
			this.TextColor = Color.FromArgb(243, 243, 243);
			this.ImgAtt = new ImageAttributes();
			this.Alpha = 0f;
			this.SpeedFade = 0f;
			this.TmrFade = new System.Windows.Forms.Timer()
			{
				Enabled = false,
				Interval = 1
			};
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			base.Size = new System.Drawing.Size(44, checked(base.Height + 1));
			this.Cursor = Cursors.Hand;
			this.Font = new System.Drawing.Font("Segoe UI", 10f);
			base.Size = new System.Drawing.Size(76, 33);
		}

		private void DrawFadeEffect(Graphics G)
		{
			this.SpeedFade = 0.11f;
			ColorMatrix colorMatrix = new ColorMatrix(new float[][] { new float[] { 1f, default(float), default(float), default(float), default(float) }, new float[] { default(float), 1f, default(float), default(float), default(float) }, new float[] { default(float), default(float), 1f, default(float), default(float) }, new float[] { default(float), default(float), default(float), this.Alpha, default(float) }, new float[] { default(float), default(float), default(float), default(float), 1f } });
			this.ImgAtt.SetColorMatrix(colorMatrix, ColorMatrixFlag.Default, ColorAdjustType.Bitmap);
			this.ImgHover = new Bitmap(base.Width, base.Height);
			this.GrHover = Graphics.FromImage(this.ImgHover);
			SolidBrush solidBrush = new SolidBrush(Color.FromArgb(255, Color.FromArgb(255, 200, 200, 200)));
			solidBrush = (MyProject.Forms.Form1.LightTheme ? new SolidBrush(Color.FromArgb(255, Color.FromArgb(255, 200, 200, 200))) : new SolidBrush(Color.FromArgb(255, Color.FromArgb(255, 73, 73, 73))));
			this.GrHover.FillRectangle(solidBrush, 0, 0, base.Width, base.Height);
			G.DrawImage(this.ImgHover, new Rectangle(0, 0, this.ImgHover.Width, this.ImgHover.Height), 0, 0, this.ImgHover.Width, this.ImgHover.Height, GraphicsUnit.Pixel, this.ImgAtt);
			this.ImgHover.Dispose();
			this.GrHover.Dispose();
		}

		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			this._Checked = !this._Checked;
			FlatToggle3.CheckedChangedEventHandler checkedChangedEventHandler = this.CheckedChanged;
			if (checkedChangedEventHandler != null)
			{
				checkedChangedEventHandler(this);
			}
		}

		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			MyProject.Forms.Form1.Timer21.Enabled = true;
			this.StartFade = true;
			this.TmrFade.Enabled = true;
			this.State = MouseState.Over;
			base.Invalidate();
			base.Invalidate();
		}

		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			MyProject.Forms.Form1.Timer21.Enabled = false;
			this.StartFade = false;
			this.TmrFade.Enabled = true;
			this.State = MouseState.None;
			base.Invalidate();
			base.Invalidate();
		}

		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, checked(base.Height - 10));
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = checked(base.Width + 30);
			this.H = checked(base.Height - 10);
			GraphicsPath graphicsPath = new GraphicsPath();
			GraphicsPath graphicsPath1 = new GraphicsPath();
			Rectangle rectangle = new Rectangle(0, 0, this.W, this.H);
			Rectangle rectangle1 = new Rectangle(this.W / 2, 0, 38, this.H);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			switch (this.O)
			{
				case FlatToggle3._Options.Style1:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 6);
					graphicsPath1 = Helpers.RoundRec(rectangle1, 6);
					g.FillPath(new SolidBrush(this.BGColor), graphicsPath);
					g.FillPath(new SolidBrush(this.ToggleColor), graphicsPath1);
					g.DrawString("OFF", this.Font, new SolidBrush(this.BGColor), new Rectangle(19, 1, this.W, this.H), Helpers.CenterSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 6);
					graphicsPath1 = Helpers.RoundRec(new Rectangle(this.W / 2, 0, 38, this.H), 6);
					g.FillPath(new SolidBrush(this.ToggleColor), graphicsPath);
					g.FillPath(new SolidBrush(this.BaseColor), graphicsPath1);
					g.DrawString("ON", this.Font, new SolidBrush(this.BaseColor), new Rectangle(8, 7, this.W, this.H), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style2:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Click", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Click", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style3:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Jitter", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Jitter", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style4:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("MC Only", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("MC Only", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style5:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Randomize", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Randomize", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style6:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Break Blocks", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Break Blocks", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style7:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Click Sounds", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Click Sounds", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style8:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("CPS Drops", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("CPS Drops", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style9:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Smart CPS Drops", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Smart CPS Drops", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style10:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Block Hit", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Block Hit", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style11:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("W-Tap", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("W-Tap", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style12:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Throw Pot", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Throw Pot", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style13:
				{
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Rod Trick", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					if (!this.Checked)
					{
						break;
					}
					graphicsPath = Helpers.RoundRec(rectangle, 1);
					if (MyProject.Forms.Form1.LightTheme)
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), graphicsPath1);
						this.TextColor = Color.Indigo;
					}
					else
					{
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath);
						g.FillPath(new SolidBrush(Color.FromArgb(255, 53, 53, 53)), graphicsPath1);
						this.TextColor = Color.White;
					}
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
					g.DrawString("Rod Trick", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 50)), 5, base.Width, base.Height), Helpers.NearSF);
					break;
				}
				case FlatToggle3._Options.Style14:
				{
					if (MyProject.Forms.Form1.LightTheme)
					{
						graphicsPath = Helpers.RoundRec(rectangle, 1);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.Indigo;
						}
						else
						{
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.White;
						}
						g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Ignore Binds In Chat", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 65)), 5, base.Width, base.Height), Helpers.NearSF);
						if (!this.Checked)
						{
							break;
						}
						graphicsPath = Helpers.RoundRec(rectangle, 1);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.Indigo;
						}
						else
						{
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.White;
						}
						g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Ignore Binds In Chat", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 65)), 5, base.Width, base.Height), Helpers.NearSF);
						break;
					}
					else
					{
						graphicsPath = Helpers.RoundRec(rectangle, 1);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.Indigo;
						}
						else
						{
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.White;
						}
						g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Ignore Binds In Chat", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(Color.Black), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 64)), 6, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Ignore Binds In Chat", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 65)), 5, base.Width, base.Height), Helpers.NearSF);
						if (!this.Checked)
						{
							break;
						}
						graphicsPath = Helpers.RoundRec(rectangle, 1);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.Indigo;
						}
						else
						{
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.White;
						}
						g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Ignore Binds In Chat", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(Color.Black), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 64)), 6, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Ignore Binds In Chat", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 65)), 5, base.Width, base.Height), Helpers.NearSF);
						break;
					}
				}
				case FlatToggle3._Options.Style15:
				{
					if (MyProject.Forms.Form1.LightTheme)
					{
						graphicsPath = Helpers.RoundRec(rectangle, 1);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.Indigo;
						}
						else
						{
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.White;
						}
						g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Simulate Humanized Clicks", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 80)), 5, base.Width, base.Height), Helpers.NearSF);
						if (!this.Checked)
						{
							break;
						}
						graphicsPath = Helpers.RoundRec(rectangle, 1);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.Indigo;
						}
						else
						{
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.White;
						}
						g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Simulate Humanized Clicks", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 80)), 5, base.Width, base.Height), Helpers.NearSF);
						break;
					}
					else
					{
						graphicsPath = Helpers.RoundRec(rectangle, 1);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.Indigo;
						}
						else
						{
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.White;
						}
						g.DrawString("r", new System.Drawing.Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(7, 6, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Simulate Humanized Clicks", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(Color.Black), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 79)), 6, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Simulate Humanized Clicks", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 80)), 5, base.Width, base.Height), Helpers.NearSF);
						if (!this.Checked)
						{
							break;
						}
						graphicsPath = Helpers.RoundRec(rectangle, 1);
						if (MyProject.Forms.Form1.LightTheme)
						{
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor3), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.Indigo;
						}
						else
						{
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath);
							g.FillPath(new SolidBrush(this.BaseColor2), graphicsPath1);
							this.DrawFadeEffect(Helpers.G);
							this.TextColor = Color.White;
						}
						g.DrawString("ü", new System.Drawing.Font("Wingdings", 12f), new SolidBrush(this.TextColor), new Rectangle(6, 4, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Simulate Humanized Clicks", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(Color.Black), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 79)), 6, base.Width, base.Height), Helpers.NearSF);
						g.DrawString("Simulate Humanized Clicks", new System.Drawing.Font("Myriad Pro", 10f), new SolidBrush(this.TextColor), new Rectangle(checked((int)Math.Round((double)this.W / 2 - 80)), 5, base.Width, base.Height), Helpers.NearSF);
						break;
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

		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
		}

		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		private void TmrFade_Tick(object sender, EventArgs e)
		{
			if (!this.StartFade)
			{
				this.Alpha -= this.SpeedFade;
			}
			else
			{
				this.Alpha += this.SpeedFade;
			}
			this.SetImage = this.ImgHover;
			if (this.Alpha >= 1f)
			{
				this.Alpha = 1f;
				this.TmrFade.Enabled = false;
				return;
			}
			if (this.Alpha <= 0f)
			{
				this.Alpha = 0f;
				this.TmrFade.Enabled = false;
			}
		}

		public event FlatToggle3.CheckedChangedEventHandler CheckedChanged;

		[Flags]
		public enum _Options
		{
			Style1,
			Style2,
			Style3,
			Style4,
			Style5,
			Style6,
			Style7,
			Style8,
			Style9,
			Style10,
			Style11,
			Style12,
			Style13,
			Style14,
			Style15
		}

		public delegate void CheckedChangedEventHandler(object sender);
	}
}