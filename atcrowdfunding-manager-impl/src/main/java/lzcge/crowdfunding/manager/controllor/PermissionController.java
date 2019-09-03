package lzcge.crowdfunding.manager.controllor;

import lzcge.crowdfunding.entity.Permission;
import lzcge.crowdfunding.manager.service.PermissionService;
import lzcge.crowdfunding.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-28
 **/
@Controller
@RequestMapping("/permission")
@CrossOrigin
public class PermissionController {

	@Autowired
	private PermissionService permissionService;


	@RequestMapping("/toIndex")
	public String toIndex(){
		return "permission/index";
	}

	@RequestMapping("/toAdd")
	public String toAdd(){
		return "permission/add";
	}

	@RequestMapping("/toUpdate")
	public String toUpdate(@RequestParam Integer id, Model model){
		model.addAttribute("permission",permissionService.selectByid(id));
		return "permission/update";
	}


	/**
	 * 异步获取权限树
	 * @return
	 */
	@PostMapping("/loadData")
	@ResponseBody
	public JsonResult loadData(){

		JsonResult jsonResult = new JsonResult();
		List<Permission> root = new ArrayList<>();
		try {
			List<Permission> permissionList = permissionService.selectAll();
			Map<Integer,Permission> permissionMap = new HashMap<>();
			for (int i = 0; i < permissionList.size(); i++) {
				Permission permission = permissionList.get(i);
				permissionMap.put(permission.getId(),permission);
			}

			for (int i = 0; i < permissionList.size(); i++) {
				Permission permission = permissionList.get(i);  //假设为子菜单
				if(permission.getPid()==null) root.add(permission);
				else{
					Permission parent = permissionMap.get(permission.getPid());
					parent.getChildren().add(permission);
				}
			}
//			jsonResult.setData(root);

			jsonResult.setInfo("success");
		}catch (Exception e){
			e.printStackTrace();
			jsonResult.setInfo("error");
		}
		jsonResult.setData(root);

		return jsonResult;
	}



	/**
	 * 添加权限
	 * @return
	 */
	@PostMapping(value = "/doAdd",consumes = "application/json")
	@ResponseBody
	public JsonResult doAdd(@RequestBody Permission permission){

		JsonResult jsonResult = new JsonResult();
		try {
			permission.setIcon("glyphicon glyphicon-check");
			int count = permissionService.insert(permission);
			jsonResult.setData(count);
			jsonResult.setInfo("success");
		}catch (Exception e){
			e.printStackTrace();
			jsonResult.setInfo("error");
		}

		return jsonResult;
	}

	/**
	 * 删除权限
	 * @return
	 */
	@PostMapping(value = "/doDelete")
	@ResponseBody
	public JsonResult doDelete( Permission permission){

		JsonResult jsonResult = new JsonResult();
		try {
			int count = permissionService.delete(permission);
			jsonResult.setData(count);
			jsonResult.setInfo("success");
		}catch (Exception e){
			e.printStackTrace();
			jsonResult.setInfo("error");
		}

		return jsonResult;
	}

	/**
	 * 修改权限
	 * @return
	 */
	@PostMapping(value = "/doUpdate",consumes = "application/json")
	@ResponseBody
	public JsonResult doUpdate(@RequestBody Permission permission){

		JsonResult jsonResult = new JsonResult();
		try {
			int count = permissionService.update(permission);
			jsonResult.setData(count);
			jsonResult.setInfo("success");
		}catch (Exception e){
			e.printStackTrace();
			jsonResult.setInfo("error");
		}

		return jsonResult;
	}
}
