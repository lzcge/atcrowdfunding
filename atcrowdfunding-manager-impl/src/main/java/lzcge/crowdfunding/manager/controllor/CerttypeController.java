package lzcge.crowdfunding.manager.controllor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lzcge.crowdfunding.entity.Cert;
import lzcge.crowdfunding.manager.service.CertService;
import lzcge.crowdfunding.manager.service.CerttypeService;
import lzcge.crowdfunding.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/certtype")
public class CerttypeController {

	@Autowired
	private CerttypeService certtypeService;
	
	@Autowired
	private CertService certService ;
	
	@RequestMapping("/index")
	public String index(Map<String, Object> map){
		//查询所有资质
		List<Cert> queryAllCert = certService.queryAllCert();		
		map.put("allCert", queryAllCert);
		
		
		//查询资质与账户类型之间关系(表示之前给账户类型分配过资质)
		List<Map<String,Object>> certAccttypeList =  certtypeService.queryCertAccttype();
		map.put("certAccttypeList", certAccttypeList);
		
		return "certtype/index";
	}
	
	
	@ResponseBody
	@RequestMapping("/insertAcctTypeCert")
	public Object insertAcctTypeCert( Integer certid, String accttype ) {
		JsonResult result = new JsonResult();
		
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("certid", certid);
			paramMap.put("accttype", accttype);
			
			int count = certtypeService.insertAcctTypeCert(paramMap);
			result.setData(count==1);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}

	@ResponseBody
	@RequestMapping("/deleteAcctTypeCert")
	public Object deleteAcctTypeCert( Integer certid, String accttype ) {
		JsonResult result = new JsonResult();
		
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("certid", certid);
			paramMap.put("accttype", accttype);
			
			int count = certtypeService.deleteAcctTypeCert(paramMap);
			result.setData(count==1);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}

}
