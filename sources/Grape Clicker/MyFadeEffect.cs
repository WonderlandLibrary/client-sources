using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	public class MyFadeEffect : Control
	{
		private ImageAttributes ImgAtt;

		private bool StartFade;

		private float Alpha;

		private float SpeedFade;

		private Bitmap ImgHover;

		private Graphics GrHover;

		private Image _SetImage;

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

		internal virtual Timer TmrFade
		{
			get
			{
				return this._TmrFade;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.TmrFade_Tick);
				Timer timer = this._TmrFade;
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

		public MyFadeEffect()
		{
			this.ImgAtt = new ImageAttributes();
			this.Alpha = 0f;
			this.SpeedFade = 0f;
			this.TmrFade = new Timer()
			{
				Enabled = false,
				Interval = 1
			};
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new System.Drawing.Size(150, 50);
		}

		private void DrawFadeEffect(Graphics G)
		{
			this.SpeedFade = 0.11f;
			ColorMatrix colorMatrix = new ColorMatrix(new float[][] { new float[] { 1f, default(float), default(float), default(float), default(float) }, new float[] { default(float), 1f, default(float), default(float), default(float) }, new float[] { default(float), default(float), 1f, default(float), default(float) }, new float[] { default(float), default(float), default(float), this.Alpha, default(float) }, new float[] { default(float), default(float), default(float), default(float), 1f } });
			this.ImgAtt.SetColorMatrix(colorMatrix, ColorMatrixFlag.Default, ColorAdjustType.Bitmap);
			this.ImgHover = new Bitmap(base.Width, base.Height);
			this.GrHover = Graphics.FromImage(this.ImgHover);
			SolidBrush solidBrush = new SolidBrush(Color.FromArgb(150, Color.Blue));
			this.GrHover.FillRectangle(solidBrush, 0, 0, base.Width, base.Height);
			G.DrawImage(this.ImgHover, new Rectangle(0, 0, this.ImgHover.Width, this.ImgHover.Height), 0, 0, this.ImgHover.Width, this.ImgHover.Height, GraphicsUnit.Pixel, this.ImgAtt);
			this.ImgHover.Dispose();
			this.GrHover.Dispose();
		}

		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.StartFade = true;
			this.TmrFade.Enabled = true;
			base.Invalidate();
		}

		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.StartFade = false;
			this.TmrFade.Enabled = true;
			base.Invalidate();
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			e.Graphics.DrawRectangle(Pens.Red, 0, 0, checked(base.Width - 1), checked(base.Height - 1));
			TextRenderer.DrawText(e.Graphics, this.Text, this.Font, base.ClientRectangle, this.ForeColor);
		}

		protected override void OnPaintBackground(PaintEventArgs e)
		{
			base.OnPaintBackground(e);
			this.DrawFadeEffect(e.Graphics);
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
	}
}