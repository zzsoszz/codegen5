package dinamica.util;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
//import org.springframework.util.ClassUtils;

public class DataUtils {
	
	 public static String basedir="/source/";
	 
	 public static String packagename="";

	 public static void main(String[] args) throws Exception {
			String json=FileUtils.readFileToString(new File(""), "GBK");
			dumpJsonToClass(json);
	}
	 
	public static void dumpJsonArrayToClass(String json,String packagename) throws Exception
	{
		JSONArray jsonarray =JSONArray.fromObject(json);
		if(jsonarray.size()>0)
		{
			JSONObject jsonobj=jsonarray.getJSONObject(0);
			DynaBean bean = (DynaBean) JSONObject.toBean( jsonobj );
		}
	}

	public static void dumpJsonToClass(String json) throws Exception
	{
		JSONObject jsonObject = JSONObject.fromObject(json);
		DynaBean bean = (DynaBean) JSONObject.toBean(jsonObject);
	}


//
//	public static void outputXml(DynaBean bean,String classname) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException
//	{
//		DynaProperty[] p = bean.getDynaClass().getDynaProperties();
//		StringBuffer sb=new StringBuffer("");
//		//sb.append("-------------------------------------------------------------------\n");
//		classname=classname==null?"":StringUtil.removeLastWith(StringUtil.upperCaseFirst(classname),"list");
//		if(packagename!=null && !"".equals(packagename))
//		{
//			sb.append("package "+packagename+";\n");
//		}
//		sb.append("import java.util.List;\n");
//		sb.append("public class "+classname+" {\n");
//		for(int i=0;i<p.length;i++)
//		{
//
//			if(ClassUtils.isPrimitiveOrWrapper(p[i].getType()) || p[i].getType().getName().equals("java.lang.String"))
//			{
//				sb.append("private "+p[i].getType().getSimpleName()+"  "+p[i].getName() +" ;\n");
//			}else if(List.class.isAssignableFrom(p[i].getType()))
//			{
//				List list=(List) PropertyUtils.getProperty(bean,p[i].getName());
//				String guessproperties = "String";
//				if(list!=null && list.size()>0)
//				{
//					guessproperties=p[i].getType().getSimpleName()+"<"+StringUtil.removeLastWith(StringUtil.upperCaseFirst(p[i].getName()),"list")+">  ";
//				}
//				sb.append("private "+guessproperties+" "+p[i].getName() +" ;\n");
//			}else if(p[i].getType().getName().equals("java.lang.Object"))
//			{
//				sb.append("private "+StringUtil.upperCaseFirst(p[i].getName())+"  "+p[i].getName() +" ;\n");
//			}
//		}
//		//geter seter
//		for(int i=0;i<p.length;i++)
//		{
//
//			if(p[i].getType().getName().equals("java.lang.Object"))
//			{
//				sb.append("public void set"+StringUtil.upperCaseFirst(p[i].getName())+"("+StringUtil.upperCaseFirst(p[i].getName())+"  "+StringUtil.lowerCaseFirst(p[i].getName()) +"){\n");
//				sb.append("     this."+p[i].getName()+" = "+StringUtil.lowerCaseFirst(p[i].getName()) +" ; \n" );
//				sb.append("}\n" );
//				sb.append("public "+StringUtil.upperCaseFirst(p[i].getName())+"  get"+StringUtil.upperCaseFirst(p[i].getName()) +"(){\n");
//				sb.append("    return this."+p[i].getName()+" ;\n" );
//				sb.append("}\n" );
//			}else if(List.class.isAssignableFrom(p[i].getType())){
//				List list=(List) PropertyUtils.getProperty(bean,p[i].getName());
//				String guessproperties = "String";
//				if(list!=null && list.size()>0)
//				{
//					guessproperties=p[i].getType().getSimpleName();
//				}
//				sb.append("public void set"+StringUtil.upperCaseFirst(p[i].getName())+"("+guessproperties+"  "+StringUtil.lowerCaseFirst(p[i].getName()) +"){\n");
//				sb.append("     this."+p[i].getName()+" = "+StringUtil.lowerCaseFirst(p[i].getName()) +" ; \n" );
//				sb.append("}\n" );
//				sb.append("public "+guessproperties+"  get"+StringUtil.upperCaseFirst(p[i].getName()) +"(){\n");
//				sb.append("    return this."+p[i].getName()+" ;\n" );
//				sb.append("}\n" );
//			}else{
//				sb.append("public void set"+StringUtil.upperCaseFirst(p[i].getName())+"("+p[i].getType().getSimpleName()+"  "+StringUtil.lowerCaseFirst(p[i].getName()) +"){\n");
//				sb.append("     this."+p[i].getName()+" = "+StringUtil.lowerCaseFirst(p[i].getName()) +" ; \n" );
//				sb.append("}\n" );
//				sb.append("public "+p[i].getType().getSimpleName()+"  get"+StringUtil.upperCaseFirst(p[i].getName()) +"(){\n");
//				sb.append("    return this."+p[i].getName()+" ;\n" );
//				sb.append("}\n" );
//			}
//		}
//
//		sb.append("}");
//		//System.out.println(sb.toString());
//		File javasrc=new File(basedir+packagename.replace(".","/")+"/"+StringUtil.upperCaseFirst(classname)+".java");
//		if(javasrc.exists())
//		{
//			javasrc.delete();
//		}
//		FileUtils.writeStringToFile(javasrc, sb.toString());
//
//		for(int i=0;i<p.length;i++)
//		{
//			if(ClassUtils.isPrimitiveOrWrapper(p[i].getType()) || p[i].getType().getName().equals("java.lang.String"))
//			{
//
//			}else if(List.class.isAssignableFrom(p[i].getType()))
//			{
//				List list=(List) PropertyUtils.getProperty(bean,p[i].getName());
//				if(list.size()>0)
//				{
//					outputXml((DynaBean) list.get(0),p[i].getName());
//				}
//			}else{
//				Object obj=(Object) PropertyUtils.getProperty(bean,p[i].getName());
//				outputXml((DynaBean)obj,p[i].getName());
//			}
//		}
//	}
//
//
//	public static void outputJson(DynaBean bean,String classname) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException
//	{
//		DynaProperty[] p = bean.getDynaClass().getDynaProperties();
//		StringBuffer sb=new StringBuffer("");
//		//sb.append("-------------------------------------------------------------------\n");
//		classname=classname==null?"":classname;
//		if(packagename!=null && !"".equals(packagename))
//		{
//			sb.append("package "+packagename+";\n");
//		}
//		sb.append("import java.util.List;\n");
//		sb.append("import org.codehaus.jackson.annotate.JsonProperty;\n");
//		sb.append("public class "+classname+" {\n");
//		for(int i=0;i<p.length;i++)
//		{
//			sb.append("@JsonProperty(value =\""+p[i].getName()+"\")\n");
//			sb.append("private "+p[i].getType().getSimpleName()+"  "+p[i].getName() +" ;\n");
//		}
//
//		//geter seter
//		for(int i=0;i<p.length;i++)
//		{
//			sb.append("public void set"+StringUtil.upperCaseFirst(p[i].getName())+"("+p[i].getType().getSimpleName()+"  "+StringUtil.lowerCaseFirst(p[i].getName()) +"){\n");
//			sb.append("     this."+p[i].getName()+" = "+StringUtil.lowerCaseFirst(p[i].getName()) +" ; \n" );
//			sb.append("}\n" );
//			sb.append("public "+p[i].getType().getSimpleName()+"  get"+StringUtil.upperCaseFirst(p[i].getName()) +"(){\n");
//			sb.append("    return this."+p[i].getName()+" ;\n" );
//			sb.append("}\n" );
//		}
//		sb.append("}");
//		//System.out.println(sb.toString());
//
//		File javasrc=new File(basedir+packagename.replace(".","/")+"/"+StringUtil.upperCaseFirst(classname)+".java");
//		if(javasrc.exists())
//		{
//			javasrc.delete();
//		}
//		FileUtils.writeStringToFile(javasrc, sb.toString());
//
//		for(int i=0;i<p.length;i++)
//		{
//			if(List.class.isAssignableFrom(p[i].getType()))
//			{
//				List list=(List) PropertyUtils.getProperty(bean,p[i].getName());
//				if(list==null)
//				{
//					//System.out.println("ddd");
//				}
//				if(list.size()>0)
//				{
//					outputJson((DynaBean) list.get(0),p[i].getName());
//				}
//			}
//		}
//	}

}
