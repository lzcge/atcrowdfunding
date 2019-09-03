package lzcge.crowdfunding.manager.controllor;

import lzcge.crowdfunding.entity.Role;
import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.manager.service.UserService;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.QueryIndexVo;
import lzcge.crowdfunding.vo.RoleListVo;
import lzcge.crowdfunding.vo.UserListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-07-28
 **/

@Controller
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 用户管理主页跳转
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex(){
		return "user/index";
	}

	/**
	 * 用户添加页面跳转
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "user/add";
	}

	/**
	 * 用户修改页面跳转
	 * @return
	 */
	@RequestMapping("/toUpdate")
	public String toUpdate(@RequestParam Integer id,Model model){
		User user = userService.selectById(id);
		model.addAttribute(user);
		return "user/update";
	}


	/**
	 * 用户分配角色
	 * @return
	 */
	@RequestMapping("/toAssignRole")
	public String toAssignRole(@RequestParam Integer id,Model model){
		//用户所具有的角色
		List<Role> assignRoleList = userService.selectRoleByUserId(id);
		List<Integer> assignRoleIds = assignRoleList.stream().map(Role::getId).collect(Collectors.toList());
		//所有角色
		List<Role> allRoleList = userService.selectAllRole();

		//用户不具有为非配的角色
		List<Role> unAssiginRoleList = new ArrayList<>();
		for (int i = 0; i < allRoleList.size(); i++) {
			if(!assignRoleIds.contains(allRoleList.get(i).getId()))
				unAssiginRoleList.add(allRoleList.get(i));
		}

		model.addAttribute("assignRoleList",assignRoleList);
		model.addAttribute("unAssiginRoleList",unAssiginRoleList);
		model.addAttribute("userId",id);
		return "user/assignRole";
	}



	/**
	 * 用户分配角色数据操作
	 * @return
	 */
	@PostMapping(value = "/doAssignRole",consumes = "application/json")
	@ResponseBody
	public JsonResult doAssignRole(@RequestBody RoleListVo roleListVo){
		JsonResult jsonResult = new JsonResult();
		try {
			//1  ：向这个用户添加某个角色   0：取消这个用户的某个角色
			if(roleListVo.getAssignStatus()==1)
				userService.saveUserAndRoleList(roleListVo);
			else if(roleListVo.getAssignStatus()==0)
				userService.deleteUserAndRoleList(roleListVo);
			jsonResult.setData(1);
			jsonResult.setInfo("success");
		}catch (Exception e){
			jsonResult.setInfo("error");
			e.printStackTrace();
		}
		System.out.println(roleListVo);
		return jsonResult;
	}




	/**
	 * 页面数据的异步加载
	 *
	 * @return
	 */
	@PostMapping("/index")
	@ResponseBody
	public JsonResult toIndex(QueryIndexVo queryIndexVo){

		JsonResult jsonResult = new JsonResult();
		try {
			Page page = userService.queryPage(queryIndexVo);
			jsonResult.setData(page);
			jsonResult.setInfo("success");
		}catch (Exception e){
			jsonResult.setInfo("error");
			e.printStackTrace();
		}
		return jsonResult;
	}

	/**
	 * 页面数据的异步加载
	 *
	 * @return
	 */
	@PostMapping(value = "/doAdd",consumes = "application/json")
	@ResponseBody
	public JsonResult doAdd(@RequestBody User user){
		JsonResult jsonResult = new JsonResult();
		try {
			int id = userService.insert(user);
			jsonResult.setInfo("success");
			jsonResult.setData(id);
		}catch (Exception e){
			jsonResult.setInfo("error");
			e.printStackTrace();
		}
		return jsonResult;
	}

	/**
	 * 修改用户
	 *
	 * @return
	 */
	@PostMapping(value = "/doUpdate",consumes = "application/json")
	@ResponseBody
	public JsonResult doUpdate(@RequestBody User user){
		JsonResult jsonResult = new JsonResult();
		try {
			userService.updateUser(user);
			jsonResult.setInfo("success");
			jsonResult.setData("success");
		}catch (Exception e){
			jsonResult.setInfo("error");
			e.printStackTrace();
		}
		return jsonResult;
	}

	/**
	 * 删除用户（单一删除）
	 *
	 * @return
	 */
	@PostMapping(value = "/doDelete")
	@ResponseBody
	public JsonResult doDelete( User user){
		JsonResult jsonResult = new JsonResult();
		try {
			Integer id = userService.deleteUser(user);
			jsonResult.setInfo("success");
			jsonResult.setData(id);
		}catch (Exception e){
			jsonResult.setInfo("error");
			e.printStackTrace();
		}
		return jsonResult;
	}



	/**
	 * 删除用户（批量删除）
	 *
	 * @return
	 */
	@PostMapping(value = "/doDeleteList",consumes = "application/json")
	@ResponseBody
	public JsonResult doDeleteList(@RequestBody UserListVo userListVo){
		JsonResult jsonResult = new JsonResult();
		try {
			userService.deleteUserList(userListVo.getUserList());
			jsonResult.setInfo("success");
			jsonResult.setData(1);
		}catch (Exception e){
			jsonResult.setInfo("error");
			e.printStackTrace();
		}
		return jsonResult;
	}
}
