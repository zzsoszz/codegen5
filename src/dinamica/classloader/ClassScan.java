package dinamica.classloader;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ClassScan {

	/**
	 * 扫描给定的基包下的所有的类(包括子包)。
	 * 
	 * @param basePackageName
	 *            基包的名称
	 * @return 所有在基包下的类的全名
	 * @throws ClassNotFoundException 
	 */
	public static HashMap<String,Class> scanBasePackage(String basePackageName) throws ClassNotFoundException {
		String packageDirName = basePackageName.replace(".", "/");
		ClassLoader my = Thread.currentThread().getContextClassLoader();
		System.out.println(my);
		URL url = my.getResource(packageDirName);
		//System.out.println(url);
		File baseDir = new File(url.getFile());
		if (!baseDir.exists() || baseDir.isFile()) {
			throw new RuntimeException(basePackageName + "不是一个包名或者该包名不存在");
		}
		//MyClassLoader myother=new MyClassLoader(baseDir.getAbsolutePath());
		HashMap<String,Class> classNames = new HashMap<String,Class>();
		getAllClass(my,baseDir, basePackageName, classNames);
		return classNames;
	}
	
	
	
	/**
	 * 得到所有在parentFile目录下的class文件名称
	 * 
	 * @param parentFile
	 * @param classNames
	 * @param basePackageName
	 * @throws ClassNotFoundException 
	 */
	private static void getAllClass(ClassLoader my,File parentFile, String basePackageName,HashMap<String,Class> classNames) throws ClassNotFoundException {
		File[] files = parentFile.listFiles();
		for (File file : files) {
			String path = file.getPath();
			if (file.isFile()) {
				if (path.endsWith(".class")) {
					String calssname=basePackageName + "." + path.substring(path.lastIndexOf('\\') + 1, path.lastIndexOf('.'));
					Class<?> demo = my.loadClass(calssname);
					classNames.put(calssname,demo);
				}
			} else {
				basePackageName = basePackageName +"."+path.substring(path.lastIndexOf('\\') + 1);
				getAllClass(my,file, basePackageName, classNames);
			}
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		String basePackageName = "com";
		HashMap<String,Class> classNames = ClassScan.scanBasePackage(basePackageName);
		System.out.println(classNames);
		for(String key:classNames.keySet())
		{
			Class myclass=classNames.get(key);
			System.out.println("class:"+myclass.getName());
			Field[] aaa = myclass.getDeclaredFields();
			for(Field f:aaa)
			{
//    				getModifiers	
//					PUBLIC: 1
//					PRIVATE: 2
//					PROTECTED: 4
//					STATIC: 8
//					FINAL: 16
//					SYNCHRONIZED: 32
//					VOLATILE: 64
//					TRANSIENT: 128
//					NATIVE: 256
//					INTERFACE: 512
//					ABSTRACT: 1024
//					STRICT: 2048
				System.out.println("field:"+f.getType()+"  "+f.getName()+"  "+f.getModifiers());
			}
		}
	}
	
	
}



//1.支持扫描某个包下的所有类，进行处理。
//
//private static final String MSG_PATH = "com.test.msg.http";  
//private static final String RESOURCE_PATH = "classpath*:" + StringUtils.replace(MSG_PATH, ".", "/") + "/*.class";  
//private Map<String, String> funMap = new HashMap<String, String>();  
//public void init() throws ServiceException {  
//logger.info("注册消息初始化开始");  
//ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();  
//try {  
//Resource[] resources = resourcePatternResolver.getResources(RESOURCE_PATH);  
//MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);  
//for (Resource e : resources) {  
//MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(e);  
//String clazzName = ClassUtils.getClass(metadataReader.getClassMetadata().getClassName()).getSimpleName();;  
//if (StringUtils.endsWith(clazzName, "C2S_Msg")) {  
//String funcName = StringUtils.uncapitalize(StringUtils.substringBefore(clazzName, "_"));  
//funMap.put(clazzName, funcName);  
//}  
//}  
//  
//} catch (Exception e) {  
//throw new ServiceException(e);  
//}  
//logger.info("注册消息初始化完毕");  
//}  

