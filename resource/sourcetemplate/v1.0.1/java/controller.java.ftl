package ${packageName}.controller;
import ${packageName}.bo.*;
import ${packageName}.model.*;
import com.bxtel.exception.*;
import com.bxtel.commons.Request;
import com.bxtel.commons.Response;
import com.bxtel.commons.SearchData;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;
import dinamica.util.DateHelper;
//${packageNameForHttp}/showadd.do
//${packageNameForHttp}/showdetail.do
//${packageNameForHttp}/showedit.do
//${packageNameForHttp}/showsearch.do
//${packageNameForHttp}/showpagelist.do
//${packageNameForHttp}/showlist.do
//${packageNameForHttp}/doadd.do
//${packageNameForHttp}/doupdate.do
//${packageNameForHttp}/dodelete.do


@Controller
@RequestMapping(value = "/${model.simpleName?lower_case}")
public class ${model.simpleName}Controller extends MultiActionController {
	    
    @Resource
	public ${model.simpleName}BO  bo;
    
    private static final Log logger = LogFactory.getLog(${model.simpleName}Controller.class);
    
    
     /*
     * search_LIKE_title
     * sort_DESC_title
     */
    @RequestMapping(value = "dosearch")
    @ResponseBody
    public Page<${model.simpleName}> dosearch(Integer page,Integer pagesize,HttpServletRequest request, HttpServletResponse response)  throws Exception, BusinessException {
    	Map<String, Object> search = Servlets.getParametersStartingWith(request, "search_");
    	Map<String, Object> sort = Servlets.getParametersStartingWith(request, "sort_");
    	return bo.search(search,sort,page,pagesize);
	}
    
    
    @RequestMapping(value = "dosearchforjson")
    @ResponseBody
    public Response<Page<${model.simpleName}>> dosearchforjson(@RequestBody Request<SearchData> req)  throws Exception, BusinessException {
    	Response<Page<${model.simpleName}>> resp=new Response<Page<${model.simpleName}>>();
    	try
    	{
    		Page<${model.simpleName}> page = bo.search(req.getData().getSearch(),req.getData().getSort(),req.getData().getPage(),req.getData().getPagesize());
        	resp.setData(page);
    	}catch(Exception ex)
		{
			resp.setReturncode("00000001"); 
			resp.setReturnmsg("系统异常!");
		}
    	return resp;
	}
	
}