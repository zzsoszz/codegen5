package dinamica.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qingtian.model.Field;
import com.qingtian.model.ScanModel;
import dinamica.classloader.ClassScan;
import dinamica.util.*;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class MainForJavaByModel {
	public static void main(String[] args) {
		gen_java("com.qingtian.model");
	}
	
	
    public  static void gen_java(String modelpackagename) {
    	try {
    		
    		com.qingtian.model.Model model=new com.qingtian.model.Model();
    		ArrayList<Field> fields=new ArrayList<Field>();
			fields.add(new Field("keyword","关键词","text",null,null,true,true,true,true,true,true));
			fields.add(new Field("id","编号","text",null,null,true,true,true,true,true,true));
			fields.add(new Field("mainTitle","主标题","text",null,null,true,true,true,true,true,true));
			fields.add(new Field("content","内容","text",null,null,true,true,true,true,true,true));
			fields.add(new Field("courseCover","封面","text",null,null,true,true,true,true,true,true));
			fields.add(new Field("courseType","课程类型","muliplyselect","courseTypeArray",null,true,true,true,true,true,true));
			fields.add(new Field("province","省份","muliplyselect","provinceArray",null,true,true,true,true,true,true));
			fields.add(new Field("city","城市","muliplyselect",null,"province",true,true,true,true,true,true));
			fields.add(new Field("createtime","时间","daterangepicker",null,null,true,true,true,true,true,true));
			fields.add(new Field("html","时间","html",null,null,true,true,true,true,true,true));
			model.setFields(fields);
			model.setTitle("微课堂");
			String basePackageName=modelpackagename.substring(0,modelpackagename.lastIndexOf("."));
			String basePackageNamePath=basePackageName.replaceAll("\\.","/");
			HashMap<String,Class> classNames = ClassScan.scanBasePackage(modelpackagename);
			Map<String ,Object> root = new HashMap<String ,Object>();   
	    	root.put("model",model);
//	    	root.put("packageName",basePackageName);
//	    	root.put("packageNameForHttp",basePackageNamePath);
//	    	root.put("setupdir",basePackageNamePath);
	        String baseDIR = FileHelper.getFilePath(MainForJavaByModel.class)+"../../sourcetemplate/v1.0.1";
	        //    鎵炬墿灞曞悕涓簍xt鐨勬枃浠�
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
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		
    	
//		try {
//			String basePackageName=modelpackagename.substring(0,modelpackagename.lastIndexOf("."));
//			String basePackageNamePath=basePackageName.replaceAll("\\.","/");
//			HashMap<String,Class> classNames = ClassScan.scanBasePackage(modelpackagename);
//			for(String key:classNames.keySet())
//			{
//				Class myclass=classNames.get(key);
//				Annotation has = myclass.getAnnotation(ScanModel.class);
//				if(has!=null)
//				{
//					System.out.println("key:"+key+"-class:"+myclass);
//					Map<String ,Object> root = new HashMap<String ,Object>();   
//			    	root.put("model",myclass);
//			    	root.put("packageName",basePackageName);//鐢熸垚java绫荤殑鍖呭悕
//			    	root.put("packageNameForHttp",basePackageNamePath);
//			    	root.put("setupdir",basePackageNamePath);//鐢熸垚action鐨勮矾寰�
//			        String baseDIR = FileHelper.getFilePath(MainForJavaByModel.class)+"../../sourcetemplate/v1.0.1";
//			        //    鎵炬墿灞曞悕涓簍xt鐨勬枃浠�
//	                System.out.println(baseDIR);
//			        String fileName = "*.ftl";
//			        List resultList = new ArrayList();
//			        FileSearcher.findFiles(baseDIR, fileName, resultList);    
//			        if (resultList.size() == 0) {   
//			        	
//			        } else {
//			            for (int i = 0; i < resultList.size(); i++) {   
//			                String dir=FileHelper.getFileDirPathByPath(resultList.get(i).toString());
//			                String outputdir=FileHelper.getFileDirPathByPath(resultList.get(i).toString())+"/"+basePackageNamePath;
//			                String templateFileName=FileHelper.getFileNameByPath((resultList.get(i).toString()));
//			                String targetFileName=FileHelper.getFileNameNoSuffixByPath(resultList.get(i).toString());
//			                System.out.println("templatedir:"+dir);
//			                System.out.println("outputdir:"+outputdir);
//			                System.out.println(templateFileName);
//			                System.out.println(targetFileName);
//			                MainForJavaByModel.output(dir,templateFileName,outputdir,targetFileName,"UTF-8", root);
//			            }
//			        }
//				}
//			}
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//		
//		
	}
    
    

    public static void analysisTemplate(String templateName,String templateEncoding, Map<?, ?> root) {
        try {

            Configuration config = new Configuration();

            File file = new File("templates");

            config.setDirectoryForTemplateLoading(file);

            config.setObjectWrapper(new DefaultObjectWrapper());

            Template template = config.getTemplate(templateName,
                    templateEncoding);

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
    
    
    

    public static void output(String templateDir,String templateName,String outputDir,String outputfile,String templateEncoding, Map<?, ?> root) {
        try {

            Configuration config = new Configuration();

            File file = new File(templateDir);

            config.setDirectoryForTemplateLoading(file);

            config.setObjectWrapper(new DefaultObjectWrapper());


            Template template = config.getTemplate(templateName,
                    templateEncoding);

            File outputDirfile = new File(outputDir);
            if(!outputDirfile.exists())
            {
            	boolean path=outputDirfile.mkdirs();
            	System.out.println("path:"+path+" "+outputDirfile.getAbsolutePath());
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
 