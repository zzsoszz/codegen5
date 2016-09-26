package dinamica.util;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public   class FileHelper {

	/**
	 * 删除一个文件
	 * 
	 * @param file
	 * @return
	 */
	public static  boolean delFile(File file) {
		if (file.isDirectory())
			return false;
		return file.delete();
	}

	
	
	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] getBytes(String filePath){
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	
	/**
	 * 根据byte数组，生成文件
	 */
	public static void getFile(byte[] bfile, String filePath,String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath+"\\"+fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除一个目录（忯以是鿞空目录）
	 * 
	 * @param dir
	 */
	public static  boolean delDir(File dir) {
		if (dir == null || !dir.exists() || dir.isFile()) {
			return false;
		}
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				delDir(file);// 递归
			}
		}
		dir.delete();
		return true;
	}

	/**
	 * 拷贿一个文件,srcFile溿文件，destFile目标文件
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static  boolean copyFileTo(File srcFile, File destFile) throws IOException {
		if (srcFile.isDirectory() || destFile.isDirectory())
			return false;// 判断是忦是文件
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		int readLen = 0;
		byte[] buf = new byte[1024];
		while ((readLen = fis.read(buf)) != -1) {
			fos.write(buf, 0, readLen);
		}
		fos.flush();
		fos.close();
		fis.close();
		return true;
	}

	
	public static  boolean saveStreamToFile(InputStream is, File destFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(destFile);
		int readLen = 0;
		byte[] buf = new byte[1024];
		while ((readLen = is.read(buf)) != -1) {
			//System.out.println(readLen);
			fos.write(buf, 0, readLen);
		}
		fos.flush();
		fos.close();
		is.close();
		return true;
	}
	
	public static void copyStream(InputStream in, OutputStream out, int bufferSize)
	throws IOException
	{

	   byte[] buf = new byte[bufferSize];
	   int len = 0;
	   while ((len = in.read(buf)) >= 0)
	   {
	      out.write(buf, 0, len);
	   }
	}
	
	/**
	 * 拷贿目录下的所有文件到指定目录
	 * 
	 * @param srcDir
	 * @param destDir
	 * @return
	 * @throws IOException
	 */
	public static  boolean copyFilesTo(File srcDir, File destDir) throws IOException {
		if (!srcDir.isDirectory() || !destDir.isDirectory())
			return false;// 判断是忦是目录
		if (!destDir.exists())
			return false;// 判断目标目录是忦存在
		File[] srcFiles = srcDir.listFiles();
		for (int i = 0; i < srcFiles.length; i++) {
			if (srcFiles[i].isFile()) {
				// 获得目标文件
				File destFile = new File(destDir.getPath() + "\\"+ srcFiles[i].getName());
				copyFileTo(srcFiles[i], destFile);
			} else if (srcFiles[i].isDirectory()) {
				File theDestDir = new File(destDir.getPath() + "\\"
						+ srcFiles[i].getName());
				copyFilesTo(srcFiles[i], theDestDir);
			}
		}
		return true;
	}

	/**
	 * 移动一个文件
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 * @throws IOException
	 */
	public static  boolean moveFileTo(File srcFile, File destFile) throws IOException {
		boolean iscopy = copyFileTo(srcFile, destFile);
		if (!iscopy)
			return false;
		delFile(srcFile);
		return true;
	}

	/**
	 * 移动目录下的所有文件到指定目录
	 * 
	 * @param srcDir
	 * @param destDir
	 * @return
	 * @throws IOException
	 */
	public static  boolean moveFilesTo(File srcDir, File destDir) throws IOException {
		if (!srcDir.isDirectory() || !destDir.isDirectory()) {
			return false;
		}
		File[] srcDirFiles = srcDir.listFiles();
		for (int i = 0; i < srcDirFiles.length; i++) {
			if (srcDirFiles[i].isFile()) {
				File oneDestFile = new File(destDir.getPath() + "\\"+ srcDirFiles[i].getName());
				moveFileTo(srcDirFiles[i], oneDestFile);
				delFile(srcDirFiles[i]);
			} else if (srcDirFiles[i].isDirectory()) {
				File oneDestFile = new File(destDir.getPath() + "\\"
						+ srcDirFiles[i].getName());
				moveFilesTo(srcDirFiles[i], oneDestFile);
				delDir(srcDirFiles[i]);
			}

		}
		return true;
	}
	
	
	public static    boolean  checkFile(File   file,   int   timeOut)   throws   InterruptedException,   IOException   
	{

		while(true)   
		{
					long now=System.currentTimeMillis();
					File   temp   =   new   File(file.getParent()   +   "\\"+now+file.getName());
					if(!file.exists())
					{
						return false;
					}
					else
					{
				    	if(file.renameTo(temp))
				    	{
			                temp.renameTo(file); 
			                //System.out.println( "do   file "+"线程:  "+Thread.currentThread().getName()+"文件"+file.getName());
			                return true;
			            }
			            else
			            {
			                //System.out.println( "waiting   for   release "+"线程:  "+Thread.currentThread().getName()+"文件"+file.getName()); 
			                Thread.sleep(timeOut); 
			            }
					}
	    }
	}
	
	
	
	
	
	
	  /** 
     * 新建目录 
     * @param folderPath String 如 c:/fqf 
     * @return boolean 
     */ 
   public static void newFolder(String folderPath) { 
       try { 
           String filePath = folderPath; 
           filePath = filePath.toString(); 
           File myFilePath = new File(filePath);
           if (!myFilePath.exists()) { 
               myFilePath.mkdir(); 
           } 
       } 
       catch (Exception e) { 
           //System.out.println("新建目录操作出错"); 
           e.printStackTrace(); 
       } 
   } 

   /** 
     * 新建文件 
     * @param filePathAndName String 文件路径及名称 如c:/fqf.txt 
     * @param fileContent String 文件内容 
     * @return boolean 
     */ 
   public static void newFile(String filePathAndName, String fileContent) { 

       try { 
           String filePath = filePathAndName; 
           filePath = filePath.toString(); 
           File myFilePath = new File(filePath); 
           if (!myFilePath.exists()) { 
               myFilePath.createNewFile(); 
           }
           FileWriter resultFile = new FileWriter(myFilePath); 
           PrintWriter myFile = new PrintWriter(resultFile); 
           String strContent = fileContent; 
           myFile.println(strContent); 
           resultFile.close(); 

       } 
       catch (Exception e) { 
           //System.out.println("新建目录操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 删除文件 
     * @param filePathAndName String 文件路径及名称 如c:/fqf.txt 
     * @param fileContent String 
     * @return boolean 
     */ 
   public static void delFile(String filePathAndName) { 
       try { 
           String filePath = filePathAndName; 
           filePath = filePath.toString(); 
           File myDelFile = new File(filePath);
           myDelFile.delete(); 

       } 
       catch (Exception e) { 
           //System.out.println("删除文件操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 删除文件夹 
     * @param filePathAndName String 文件夹路径及名称 如c:/fqf 
     * @param fileContent String 
     * @return boolean 
     */ 
   public static void delFolder(String folderPath) { 
       try { 
           delAllFile(folderPath); //删除完里面所有内容 
           String filePath = folderPath; 
           filePath = filePath.toString(); 
           File myFilePath = new File(filePath);
           myFilePath.delete(); //删除空文件夹 

       } 
       catch (Exception e) { 
           //System.out.println("删除文件夹操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 删除文件夹里面的所有文件 
     * @param path String 文件夹路径 如 c:/fqf 
     */ 
   public static void delAllFile(String path) { 
       File file = new File(path); 
       if (!file.exists()) { 
           return; 
       } 
       if (!file.isDirectory()) { 
           return; 
       } 
       String[] tempList = file.list(); 
       File temp = null; 
       for (int i = 0; i < tempList.length; i++) { 
           if (path.endsWith(File.separator)) { 
               temp = new File(path + tempList[i]); 
           } 
           else { 
               temp = new File(path + File.separator + tempList[i]); 
           } 
           if (temp.isFile()) { 
               temp.delete(); 
           } 
           if (temp.isDirectory()) { 
               delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件 
               delFolder(path+"/"+ tempList[i]);//再删除空文件夹 
           } 
       } 
   } 

   /** 
     * 复制单个文件 
     * @param oldPath String 原文件路径 如：c:/fqf.txt 
     * @param newPath String 复制后路径 如：f:/fqf.txt 
     * @return boolean 
     */ 
   public static void copyFile(String oldPath, String newPath) { 
       try { 
           int bytesum = 0; 
           int byteread = 0; 
           File oldfile = new File(oldPath); 
           if (oldfile.exists()) { //文件存在时 
               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
               FileOutputStream fs = new FileOutputStream(newPath); 
               byte[] buffer = new byte[1444]; 
               int length; 
               while ( (byteread = inStream.read(buffer)) != -1) { 
                   bytesum += byteread; //字节数 文件大小 
                   ////System.out.println(bytesum); 
                   fs.write(buffer, 0, byteread); 
               } 
               inStream.close(); 
           } 
       } 
       catch (Exception e) { 
           //System.out.println("复制单个文件操作出错"); 
           e.printStackTrace(); 

       } 

   }

   /** 
     * 复制整个文件夹内容 
     * @param oldPath String 原文件路径 如：c:/fqf 
     * @param newPath String 复制后路径 如：f:/fqf/ff 
     * @return boolean 
     */ 
   public static void copyFolder(String oldPath, String newPath) { 

       try { 
           (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
           File a=new File(oldPath); 
           String[] file=a.list(); 
           File temp=null; 
           for (int i = 0; i < file.length; i++) { 
               if(oldPath.endsWith(File.separator)){ 
                   temp=new File(oldPath+file[i]); 
               } 
               else{ 
                   temp=new File(oldPath+File.separator+file[i]); 
               } 

               if(temp.isFile()){ 
                   FileInputStream input = new FileInputStream(temp); 
                   FileOutputStream output = new FileOutputStream(newPath + "/" + 
                           (temp.getName()).toString()); 
                   byte[] b = new byte[1024 * 5]; 
                   int len; 
                   while ( (len = input.read(b)) != -1) { 
                       output.write(b, 0, len); 
                   } 
                   output.flush(); 
                   output.close(); 
                   input.close(); 
               } 
               if(temp.isDirectory()){//如果是子文件夹 
                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
               } 
           } 
       } 
       catch (Exception e) { 
           //System.out.println("复制整个文件夹内容操作出错"); 
           e.printStackTrace(); 

       } 

   } 

   /** 
     * 移动文件到指定目录 
     * @param oldPath String 如：c:/fqf.txt 
     * @param newPath String 如：d:/fqf.txt 
     */ 
   public static void moveFile(String oldPath, String newPath) { 
       copyFile(oldPath, newPath); 
       delFile(oldPath); 

   } 

   /** 
     * 移动文件到指定目录 
     * @param oldPath String 如：c:/fqf.txt 
     * @param newPath String 如：d:/fqf.txt 
 * @return 
     */ 
   public static boolean moveFolder(String oldPath, String newPath) { 
	   try{
		       copyFolder(oldPath, newPath); 
		       delFolder(oldPath);
	   }catch(Exception ex)
	   {
		   return false;
	   }
	   
       return true; 
   }
  
   
   
	/**
	 * A方法追加文件：使用RandomAccessFile
	 * 
	 * @param fileName
	 *            文件名
	 * @param content
	 *            追加的内容
	 */
	public static void appendMethodA(String fileName, String content) {
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * B方法追加文件：使用FileWriter
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void appendMethodB(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
	public static String getFileNameByPath(String filePath)
	{
		String fileName=null;
		String temp[] = filePath.replaceAll("\\\\", "/").split("/");
		if (temp.length > 1) 
		{
			fileName = temp[temp.length - 1];
		}
		return fileName;
	}
	

	
	public static String getFileDirPathByPath(String filePath) {
		int lastIndexOfDot = filePath.lastIndexOf(File.separator);
	    String dir = filePath.substring(0,lastIndexOfDot);
		return dir;
	}
	
	public static String getFileNameNoSuffixByPath(String filePath) {
		String fileName = null;
		String temp[] = filePath.replaceAll("\\\\", "/").split("/");
		if (temp.length > 1) {
			fileName = temp[temp.length - 1];
		}
		
		fileName=fileName.substring(0,fileName.lastIndexOf("."));
		return fileName;
	}
	
	
	
	
	
	public static void readFileByChars(String fileName) {
		File file = new File(fileName);
		Reader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				if (((char) tempchar) != 'r') {
					System.out.print((char) tempchar);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			while ((charread = reader.read(tempchars)) != -1) {
				if ((charread == tempchars.length)
						&& (tempchars[tempchars.length - 1] != 'r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == 'r') {
							continue;
						} else {
							System.out.print(tempchars[i]);
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				//System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void readFileByRandomAccess(String fileName) {
		RandomAccessFile randomFile = null;
		try {
			randomFile = new RandomAccessFile(fileName, "r");
			long fileLength = randomFile.length();
			int beginIndex = (fileLength > 4) ? 4 : 0;
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			while ((byteread = randomFile.read(bytes)) != -1) {
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	 public static String getFileContent(String fileName){
	        StringBuffer sb=new StringBuffer();
	        BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(fileName));
				 String str=null;  
				 while((str=br.readLine())!=null)  
				 {  
					 sb.append(str);
				 }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        return sb.toString();
	}
	
	public static String getFileContent(Class curclass,String filename)
	{
		return FileHelper.getFileContent(curclass.getResource("").getPath()+filename);
	}
	
	public static String getFilePath(Class curclass)
	{
		return curclass.getResource("").getPath();
	}
	
	private static void showAvailableBytes(InputStream in) {
	}

	
	public static String getClassesPath() {
		String path = FileHelper.class.getClassLoader().getResource("").getPath();
		return path;
	}
	
	public static void main(String[] args) {
		System.out.println(getFilePath(FileHelper.class));;
	}
	
	
	public String getWebClassesPath() {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		return path;
	}

	public String getWebInfPath() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF") + 8);
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}

	public String getWebRoot() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF/classes"));
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}
}
