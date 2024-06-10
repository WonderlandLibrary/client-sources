using System.Windows.Forms;
using System.Threading;

namespace AnyDesk.GUI.Helpers
{
    public class Helper
    {
        public void Closing(int delay, Form form)
        {
            for (int i = 0; i < 10; i++)
            {
                try
                {
                    form.Opacity -= 0.1F;
                    Thread.Sleep(delay);
                } catch { }
            }
        }

        public void FadeOut(int delay, Form form)
        {
            for (int i = 0; i < 5; i++)
            {
                try
                {
                    form.Opacity -= 0.1F;
                    Thread.Sleep(delay);
                } catch { }
            }
        }

        public void FadeIn(int delay, Form form)
        {
            for (int i = 0; i < 5; i++)
            {
                try
                {
                    form.Opacity += 0.1F;
                    Thread.Sleep(delay);
                } catch { }
            }
        }
    }
}
