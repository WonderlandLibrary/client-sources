using System.Diagnostics;

namespace GetInstance
{
    class GetMCInstance
    {
        private string title;
        public GetMCInstance(string title)
        {
            this.title = title;
        }

        public Process GetInstance()
        {
            Process[] processes = Process.GetProcessesByName("Javaw");
            foreach (var prc in processes)
            {
                if (prc.MainWindowTitle.Contains(title))
                    return prc;
            }
            return null;
        }
    }
}
