package lzcge.crowdfunding.manager.controllor;

import lzcge.crowdfunding.entity.Permission;
import lzcge.crowdfunding.entity.Role;
import lzcge.crowdfunding.manager.service.PermissionService;
import lzcge.crowdfunding.manager.service.RoleService;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;
import lzcge.crowdfunding.vo.QueryIndexVo;
import lzcge.crowdfunding.vo.RoleListVo;
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
 * @create: 2019-08-25
 **/
@Controller
@RequestMapping("/role")
@CrossOrigin
public class RoleController {


	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	/**
	 * 角色主界面跳转
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex(){
		return "role/index";
	}

	/**
	 * 角色主界面跳转
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "role/add";
	}


	/**
	 * 角色更新
	 * @return
	 */
	@RequestMapping("/toUpdate")
	public String toUpdate(@RequestParam  Integer id, Model model){
		Role role = roleService.selectByRoleId(id);
		model.addAttribute("role",role);
		return "role/update";
	}

	/**
	 * 分配权限
	 * @return
	 */
	@RequestMapping("/assignPermission")
	public String assiginPermission(@RequestParam  Integer roleId, Model model){
		return "role/assignPermission";
	}




	/**
	 * 异步加载角色数据
	 * @param queryIndexVo
	 * @return
	 */
	@PostMapping("/doIndex")
	@ResponseBody
	public JsonResult doIndex(QueryIndexVo queryIndexVo){
		JsonResult jsonResult = new JsonResult();
		try {
			Page page = roleService.queryPage(queryIndexVo);
			jsonResult.setInfo("success");
			jsonResult.setData(page);
		}catch (Exception e){
			e.printStackTrace();
			jsonResult.setInfo("error");
		}

		return jsonResult;
	}


	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	@PostMapping(value="/doAdd",consumes = "application/json")
	@ResponseBody
	public JsonResult doAdd(@RequestBody Role role){
		JsonResult jsonResult = new JsonResult();
		int roleId = -1;
		try {
			roleId = roleService.insert(role);
			jsonResult.setInfo("success");
		}catch (Exception e){
			e.printStackTrace();
			jsonResult.setInfo("error");
		}
		jsonResult.setData(roleId);
		return jsonResult;

	}


	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	@PostMapping(value="/doUpdate",consumes = "application/json")
	@ResponseBody
	public JsonResult doUpdate(@RequestBody Role role){
		JsonResult jsonResult = new JsonResult();
		try {
			roleService.update(role);
			jsonResult.setInfo("success");
		}catch (Exception e){
			e.printStackTrace();
			jsonResult.setInfo("error");
		}
		jsonResult.setData(1);
		return jsonResult;

	}


	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	@PostMapping(value="/doDelete")
	@ResponseBody
	public JsonResult doDelete(Role role){
		JsonResult jsonResult = new JsonResult();
		try {
			roleService.deleteRole(role);
			jsonResult.setInfo("success");
		}catch (Exception e){
			e.printStackTrace();
			jsonResult.setInfo("error");
		}
		jsonResult.setData(1);
		return jsonResult;

	}


	/**
	 * 批量删除角色
	 * @param roleListVo
	 * @return
	 */
	@PostMapping(value="/doDeleteList",consumes = "application/json")
	@ResponseBody
	public JsonResult doDeleteList(@RequestBody RoleListVo roleListVo){
		JsonResult jsonResult = new JsonResult();
		try {
			roleService.deleteRoleList(roleListVo.getRoleList());
			jsonResult.setInfo("success");
		}catch (Exception e){
			e.printStackTrace();
			jsonResult.setInfo("error");
		}
		jsonResult.setData(1);
		return jsonResult;

	}



	/**
	 * 异步获取权限树数据
	 * @return
	 */
	@PostMapping("/loadPermissionDate")
	@ResponseBody
	public Object loadPermissionDate(@RequestParam Integer roleId){

		List<Permission> root = new ArrayList<>();
		try {
			//查询出所有权限
			List<Permission> permissionList = permissionService.selectAll();
			//查询出当前角色已经拥有的权限
			List<Integer> permissionIdsForRoleList = permissionService.selectPermissionIdsForRole(roleId);
			Map<Integer,Permission> permissionMap = new HashMap<>();
			for (int i = 0; i < permissionList.size(); i++) {
				Permission permission = permissionList.get(i);
				permissionMap.put(permission.getId(),permission);
				//如果被分配就设置checked为true，用于回显
				if(permissionIdsForRoleList.contains(permission.getId())){
					permission.setChecked(true);
				}
			}

			for (int i = 0; i < permissionList.size(); i++) {
				Permission permission = permissionList.get(i);  //假设为子菜单
				if(permission.getPid()==null) root.add(permission);
				else{
					Permission parent = permissionMap.get(permission.getPid());
					parent.getChildren().add(permission);
				}
			}

		}catch (Exception e){
			e.printStackTrace();
		}

		return root;
	}


	/**
	 * 分配权限
	 * @return
	 */
	@PostMapping("/doAssignPermission")
	@ResponseBody
	public JsonResult doAssignPermission(Integer roleid, Data datas){
		JsonResult jsonResult = new JsonResult();
		try {
			permissionService.deleteRoleAndPermission(roleid);

			permissionService.saveRoleAndPermission(roleid,datas);

			jsonResult.setInfo("success");
		}catch (Exception e){
			jsonResult.setInfo("error");
			e.printStackTrace();
		}
		jsonResult.setData(1);

		return jsonResult;
	}



}
