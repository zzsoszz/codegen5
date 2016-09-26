package dinamica.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dinamica.classloader.ClassScan;
import dinamica.util.*;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class MainForJavaByModel {
	public static void main(String[] args) {
//		gen_java("com.bxtel.cases.model");
//		gen_java("com.bxtel.company.model");
		gen_java("com.bxtel.knowledge.model");
	}
	
    public  static void gen_java(String modelpackagename) {
		try {
			
			String basePackageName=modelpackagename.substring(0,modelpackagename.lastIndexOf("."));
			String basePackageNamePath=basePackageName.replaceAll("\\.","/");
			HashMap<String,Class> classNames = ClassScan.scanBasePackage(modelpackagename);
			for(String key:classNames.keySet())
			{
				Class myclass=classNames.get(key);
				System.out.println("key:"+key+"-class:"+myclass);
				Map<String ,Object> root = new HashMap<String ,Object>();   
		    	root.put("model",myclass);
		    	root.put("packageName",basePackageName);//生成java类的包名
		    	root.put("packageNameForHttp",basePackageNamePath);
		    	root.put("setupdir",basePackageNamePath);//生成action的路径
		        String baseDIR = FileHelper.getFilePath(MainForJavaByModel.class)+"../../sourcetemplate/v1.0.1";
		        //    找扩展名为txt的文件
                System.out.println(baseDIR);
		        String fileName = "*.ftl";
		        List resultList = new ArrayList();
		        FileSearcher.findFiles(baseDIR, fileName, resultList);    
		        if (resultList.size() == 0) {   
		        	
		        } else {
		            for (int i = 0; i < resultList.size(); i++) {   
		                String dir=FileHelper.getFileDirPathByPath(resultList.get(i).toString());
		                String outputdir=FileHelper.getFileDirPathByPath(resultList.get(i).toString())+"/"+basePackageNamePath;
		                String templateFileName=FileHelper.getFileNameByPath((resultList.get(i).toString()));
		                String targetFileName=FileHelper.getFileNameNoSuffixByPath(resultList.get(i).toString());
		                System.out.println("templatedir:"+dir);
		                System.out.println("outputdir:"+outputdir);
		                System.out.println(templateFileName);
		                System.out.println(targetFileName);
		                MainForJavaByModel.output(dir,templateFileName,outputdir,targetFileName,"UTF-8", root);
		            }
		        }
		        
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
    
    
    /**
     * 
     * @param templateName
     *            模板文件名称
     * @param templateEncoding
     *            模板文件的编码方式
     * @param root
     *            数据模型根对象
     */
    public static void analysisTemplate(String templateName,String templateEncoding, Map<?, ?> root) {
        try {
            /**
             * 创建Configuration对象
             */
            Configuration config = new Configuration();
            /**
             * 指定模板路径
             */
            File file = new File("templates");
            /**
             * 设置要解析的模板所在的目录，并加载模板文件
             */
            config.setDirectoryForTemplateLoading(file);
            /**
             * 设置包装器，并将对象包装为数据模型
             */
            config.setObjectWrapper(new DefaultObjectWrapper());

            /**
             * 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
             */
            Template template = config.getTemplate(templateName,
                    templateEncoding);
            /**
             * 合并数据模型与模板
             */
            Writer out = new OutputStreamWriter(System.out);
            template.process(root, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

     }
    
    
    
    /**
     * 
     * @param templateName
     *            模板文件名称
     * @param templateEncoding
     *            模板文件的编码方式
     * @param root
     *            数据模型根对象
     */
    public static void output(String templateDir,String templateName,String outputDir,String outputfile,String templateEncoding, Map<?, ?> root) {
        try {
            /**
             * 创建Configuration对象
             */
            Configuration config = new Configuration();
            /**
             * 指定模板路径
             */
            File file = new File(templateDir);
            /**
             * 设置要解析的模板所在的目录，并加载模板文件
             */
            config.setDirectoryForTemplateLoading(file);
            /**
             * 设置包装器，并将对象包装为数据模型
             */
            config.setObjectWrapper(new DefaultObjectWrapper());

            /**
             * 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
             */
            Template template = config.getTemplate(templateName,
                    templateEncoding);
            /**
             * 合并数据模型与模板
             */
            File outputDirfile = new File(outputDir);
            if(!outputDirfile.exists())
            {
            	boolean path=outputDirfile.mkdirs();
            	System.out.println("创建文件夹："+path+" "+outputDirfile.getAbsolutePath());
            }
            Writer out = new OutputStreamWriter(new FileOutputStream(outputDirfile.getAbsoluteFile()+File.separator+outputfile),templateEncoding);
            template.process(root, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        catch (TemplateException e) {
            e.printStackTrace();
        }

     }
    
    
    
 }
 