using System;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static Dope.Theme.ThemeUtils;

namespace Dope.Theme
{
    public class Switch : Control
    {
        private MouseState State = MouseState.None;
        private bool Checked_;

        public Switch()
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
                    Location = new Point(Location.X + 1, Location.Y);
                    await Task.Delay(2);
                }
                for (int i = 1; i < 5; i++)
                {
                    Location = new Point(Location.X - 1, Location.Y);
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
            ///Text drawing
            int TxtWidth = (int)G.MeasureString(Text, Font).Width + 2;
            G.DrawString(Text, Font, new SolidBrush(ForeColor), new Rectangle(2, 0, TxtWidth, Height), ThemeUtils.NearCenterSF);

            ///Switch
            Point basep = new Point(TxtWidth + 10, (int)(Height/2 - 10));
            GraphicsPath BaseSwitch = ThemeUtils.RoundRec(new Rectangle(basep.X, basep.Y + 3, 40, 14), 7);
            G.FillPath(new SolidBrush(Color.FromArgb(255,150,150,150)), BaseSwitch);

            Color Kob = (State == MouseState.Over) ? Color.FromArgb(255, 200, 200, 200) :
                Color.FromArgb(255, 220, 220, 220);

            G.FillEllipse(new SolidBrush(Kob),
                new RectangleF(Checked ? basep.X + 20 : basep.X, basep.Y, 20, 20));

            base.OnPaint(e);
            G.Dispose();
            e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
            e.Graphics.DrawImageUnscaled(B, 0, 0);
            B.Dispose();
        }
    }
}
