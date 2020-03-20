package lzcge.crowdfunding.manager.controllor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;



import lzcge.crowdfunding.entity.Advert;
import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.manager.service.AdvertService;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Const;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.util.StringUtil;
import lzcge.crowdfunding.vo.Data;
import lzcge.crowdfunding.vo.QueryIndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/advert")
public class AdvertController {

	@Autowired
	private AdvertService advertService;
	

	@RequestMapping("/index")
	public String index() {
		return "advert/index";
	}
	
	@RequestMapping("/add")
	public String add() {
		return "advert/add";
	}
	
	@RequestMapping("/edit")
	public String edit( Integer id, Model model ) {
		
		// 根据主键查询资质信息
		Advert advert = advertService.queryById(id);
		model.addAttribute("advert", advert);
		
		return "advert/edit";
	}
	
	@ResponseBody
	@RequestMapping("/batchDelete")
	public Object batchDelete( Data ds ) {
		JsonResult result = new JsonResult();
		
		try {
			int count = advertService.deleteAdverts(ds);
			if ( count == ds.getDatas().size() ) {
				result.setData(200);
				result.setInfo("success");
			} else {
				result.setInfo("false");
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete( Integer id ) {
		JsonResult result = new JsonResult();
		
		try {
			int count = advertService.deleteAdvert(id);
			if ( count == 1 ) {
				result.setData(200);
				result.setInfo("success");
			} else {
				result.setInfo("false");
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}


		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update( Advert advert ) {
		JsonResult result = new JsonResult();
		
		try {
			int count = advertService.updateAdvert(advert);
			if ( count == 1 ) {
				result.setData(200);
				result.setInfo("success");
			} else {
				result.setInfo("false");
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}
		
		return result;
	}
	
	/**
	 * 新增资质数据
	 * @return
	 */
	@ResponseBody
	@PostMapping(value = "/doAdd")
	public Object doAdd(HttpServletRequest request, Advert advert , HttpSession session) {
		JsonResult result = new JsonResult();
		
		
		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;
			//获取上传文件（广告图片）
			MultipartFile mfile = mreq.getFile("advpic");

			//重命名
			String name = mfile.getOriginalFilename();//java.jpg
			String extname = name.substring(name.lastIndexOf(".")); // .jpg
			
			String iconpath = UUID.randomUUID().toString()+extname; //232243343.jpg
			
			ServletContext servletContext = session.getServletContext();
			String realpath = servletContext.getRealPath("/pics");
			
			String path =realpath+ "\\adv\\"+iconpath;

			File file = new File(path);

			try {

				mfile.transferTo(file);
			}catch (IOException e){
				e.printStackTrace();
				result.setInfo("文件上传失败");
			}

			User user = (User)session.getAttribute(Const.LOGIN_USER);
			advert.setUserid(user.getId());
			advert.setStatus("1");
			advert.setIconpath(iconpath);

			//插入返回id
			int count = advertService.insertAdvert(advert);
			//result.setInfo(count==1);
			result.setData(200);
			result.setInfo("success");
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}
		
		return result;
	}
	
	
	

	
	/**
	 * 分页查询资质数据
	 * @advert pageno
	 * @advert pagesize
	 * @return
	 */
	@ResponseBody
	@PostMapping("/pageQuery")
	public Object pageQuery(QueryIndexVo queryIndexVo) {
		String pagetext = queryIndexVo.getQueryText();
		Integer pageno = queryIndexVo.getPageNo();
		Integer pagesize = queryIndexVo.getPageSize();
		JsonResult result = new JsonResult();
		
		try {
			// 查询资质数据
			Map<String, Object> advertMap = new HashMap<String, Object>();
			advertMap.put("pageno", pageno);
			advertMap.put("pagesize", pagesize);
			if ( StringUtil.isNotEmpty(pagetext) ) {
				pagetext = pagetext.replaceAll("%", "\\\\%");
			}
			advertMap.put("pagetext", pagetext);
			
			// 分页查询
			Page<Advert> page = advertService.pageQuery(advertMap);
			//result.setPage(page);
			result.setData(page);
			result.setInfo("success");
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}
		
		return result;
	}
}
