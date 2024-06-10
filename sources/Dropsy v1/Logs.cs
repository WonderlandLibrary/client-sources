using System;
using System.IO;

// Token: 0x02000008 RID: 8
internal class Logs
{
	// Token: 0x06000014 RID: 20 RVA: 0x0000263C File Offset: 0x00000A3C
	public static void WriteLog(string strLog)
	{
		string text = Directory.GetCurrentDirectory() + "\\";
		text = text + "Log-" + DateTime.Today.ToString("MM-dd-yyyy") + ".txt";
		FileInfo fileInfo = new FileInfo(text);
		DirectoryInfo directoryInfo = new DirectoryInfo(fileInfo.DirectoryName);
		if (!directoryInfo.Exists)
		{
			directoryInfo.Create();
		}
		FileStream stream;
		if (!fileInfo.Exists)
		{
			stream = fileInfo.Create();
		}
		else
		{
			stream = new FileStream(text, FileMode.Append);
		}
		StreamWriter streamWriter = new StreamWriter(stream);
		streamWriter.WriteLine("# >>  " + strLog);
		streamWriter.WriteLine("#-------------------------------------------------------------------------");
		streamWriter.Close();
	}

	// Token: 0x06000015 RID: 21 RVA: 0x000026E4 File Offset: 0x00000AE4
	public static void DeleteLog()
	{
		FileInfo fileInfo = new FileInfo(Directory.GetCurrentDirectory() + "\\Log-" + DateTime.Today.ToString("MM-dd-yyyy") + ".txt");
		DirectoryInfo directoryInfo = new DirectoryInfo(fileInfo.DirectoryName);
		if (!directoryInfo.Exists)
		{
			directoryInfo.Delete();
		}
		if (fileInfo.Exists)
		{
			fileInfo.Delete();
		}
	}
}
