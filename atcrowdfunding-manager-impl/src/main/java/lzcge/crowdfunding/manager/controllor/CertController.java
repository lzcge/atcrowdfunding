package lzcge.crowdfunding.manager.controllor;

import java.util.HashMap;
import java.util.Map;

import lzcge.crowdfunding.entity.Cert;
import lzcge.crowdfunding.manager.service.CertService;
import lzcge.crowdfunding.result.JsonResult;
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



@Controller
@RequestMapping("/cert")
public class CertController {

	@Autowired
	private CertService certService;
	

	@RequestMapping("/index")
	public String index() {
		return "cert/index";
	}
	
	@RequestMapping("/add")
	public String add() {
		return "cert/add";
	}
	
	@RequestMapping("/edit")
	public String edit( Integer id, Model model ) {
		
		// 根据主键查询资质信息
		Cert cert = certService.queryById(id);
		model.addAttribute("cert", cert);
		
		return "cert/edit";
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes( Data ds ) {
		JsonResult result = new JsonResult();
		
		try {
			int count = certService.deleteCerts(ds);
			if ( count == ds.getDatas().size() ) {
				result.setData(true);
			} else {
				result.setData(false);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete( Integer id ) {
		JsonResult result = new JsonResult();
		
		try {
			int count = certService.deleteCert(id);
			if ( count == 1 ) {
				result.setData(true);
			} else {
				result.setData(false);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update( Cert cert ) {
		JsonResult result = new JsonResult();
		
		try {
			int count = certService.updateCert(cert);
			if ( count == 1 ) {
				result.setData(true);
			} else {
				result.setData(false);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}
	
	/**
	 * 新增资质数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert( Cert cert ) {
		JsonResult result = new JsonResult();
		
		try {
			certService.insertCert(cert);
			result.setData(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}
	
	/**
	 * 分页查询资质数据
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
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pageno", pageno);
			paramMap.put("pagesize", pagesize);
			if ( StringUtil.isNotEmpty(pagetext) ) {
				//pagetext = pagetext.replaceAll("%", "\\\\%");
			}
			paramMap.put("pagetext", pagetext);
			
			// 分页查询数据
			Page<Cert> page = certService.pageQuery(paramMap);

			result.setInfo("success");
			result.setData(page);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
			result.setData(false);
		}
		
		return result;
	}
}
