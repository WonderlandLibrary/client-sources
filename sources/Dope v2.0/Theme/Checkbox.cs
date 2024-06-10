using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static Dope.Theme.ThemeUtils;

namespace Dope.Theme
{
    class Checkbox : Control
    {
        private MouseState State = MouseState.None;
        private bool Checked_;

        public Checkbox()
        {
            SetStyle(ControlStyles.AllPaintingInWmPaint | ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.OptimizedDoubleBuffer, true);
            DoubleBuffered = true;
        }

        public bool Checked
        {
            get { return Checked_; }
            set
            {
                Checked_ = value;
                Invalidate();
            }
        }

        protected override void OnClick(EventArgs e)
        {
            Checked = !Checked;
            base.OnClick(e);
            Task.Run(async () =>
            {
                for (int i = 1; i < 5; i++)
                {
                    Location = new Point(Location.X, Location.Y + 1);
                    await Task.Delay(2);
                }
                for (int i = 1; i < 5; i++)
                {
                    Location = new Point(Location.X, Location.Y - 1);
                    await Task.Delay(2);
                }
            });
        }

        protected override void OnMouseDown(MouseEventArgs e)
        {
            base.OnMouseDown(e);
            State = MouseState.Down;
            Invalidate();
        }

        protected override void OnMouseUp(MouseEventArgs e)
        {
            base.OnMouseUp(e);
            State = MouseState.Over;
            Invalidate();
        }

        protected override void OnMouseEnter(EventArgs e)
        {
            base.OnMouseEnter(e);
            State = MouseState.Over;
            Invalidate();
        }

        protected override void OnMouseLeave(EventArgs e)
        {
            base.OnMouseLeave(e);
            State = MouseState.None;
            Invalidate();
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            ///basic setup
            Bitmap B = new Bitmap(Width, Height);
            Graphics G = Graphics.FromImage(B);
            G.SmoothingMode = SmoothingMode.HighQuality;
            G.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;

            ///CheckBox
            Point CheckP = new Point(2, (int)(Height / 2 - 7));
            GraphicsPath BaseBox = RoundRec(new Rectangle(CheckP.X, CheckP.Y,14,14), 2);

            if (Checked)
            {
                G.FillPath(new SolidBrush(Color.FromArgb(255, 220, 220, 220)), BaseBox);
                G.DrawPath(new Pen(new SolidBrush(BackColor), 2f), DrawCheck(new Rectangle(CheckP.X, CheckP.Y, 14, 14)));
            }
            else
            {
                G.DrawPath(new Pen(new SolidBrush(Color.FromArgb(255,220,220,220))), BaseBox);
            }

            ///Text drawing
            int TxtWidth = (int)G.MeasureString(Text, Font).Width + 2;
            G.DrawString(Text, Font, new SolidBrush(ForeColor), new Rectangle(CheckP.X + 21, 0, TxtWidth, Height), NearCenterSF);

            base.OnPaint(e);
            G.Dispose();
            e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
            e.Graphics.DrawImageUnscaled(B, 0, 0);
            B.Dispose();
        }
    }
}
