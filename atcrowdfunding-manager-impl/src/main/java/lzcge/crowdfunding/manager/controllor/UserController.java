package lzcge.crowdfunding.manager.controllor;

import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.manager.service.UserService;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.QueryUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
	 * 页面数据的异步加载
	 *
	 * @return
	 */
	@PostMapping("/index")
	@ResponseBody
	public JsonResult toIndex(QueryUserVo queryUserVo){

		JsonResult jsonResult = new JsonResult();
		try {
			Page page = userService.queryPage(queryUserVo);
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
}
