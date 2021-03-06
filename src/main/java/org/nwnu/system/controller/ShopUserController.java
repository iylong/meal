package org.nwnu.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import org.nwnu.system.entity.ShopUser;
import org.nwnu.system.entity.SysRole;
import org.nwnu.system.entity.SysUser;
import org.nwnu.system.service.ShopUserService;
import org.nwnu.system.service.SysRoleService;
import org.nwnu.system.service.SysUserService;
import org.nwnu.base.controller.BaseController;//基础包
import org.nwnu.pub.util.StringUtil;//自定义字符串处理类，如果没有就取掉
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 餐厅管理员 前端控制器
 * </p>
 *
 * @author Answer
 * @since 2018-05-13
 */
@Controller
@RequestMapping("/ShopUser")
public class ShopUserController extends BaseController {
	@Autowired
	private ShopUserService this_ShopUserService;	
	@Autowired
	private SysRoleService sysRoleService;	
	@Autowired
	private SysUserService sysUserService;	
	
	/***
	 * 每个controller的首页
	 * 	 
	 */
	@RequestMapping(value = "/ShopUserIndex")
	public ModelAndView ShopUserIndex(ModelAndView modelAndView) {
		return modelAndView;
	}

	/***
	 * 列表
	 * 
	 * @param 
	 * @param page
	 *            起始页
	 * @param rows
	 *            页面大小 * @param sort 排序字段 * @param rows 排序顺序
	 * @return
	 */
	@RequestMapping(value = "/List", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetList(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int pagesizes) {
		EntityWrapper<ShopUser> wrapper=new EntityWrapper<ShopUser>();	
		//如果有查询条件，此处需要构造查询warpper
		//例如wrapper.eq();			
		List<ShopUser> ShopUserList=this_ShopUserService.selectPage(
				new Page<ShopUser>(page,pagesizes),
				wrapper.orderBy("id", false)//根据id倒序输出
				).getRecords();	
		for(ShopUser sp :ShopUserList){
			if (sp.getRolecode().equals("0004")&& sp.getUid() != null) {
				SysUser sdu=sysUserService.selectById(sp.getUid());
				if(sdu!=null){
					sp.setResUserName(sdu.getName());
				}
			}
			else  {
				ShopUser rdu=this_ShopUserService.selectById(sp.getUid());
				if(rdu!=null){
					sp.setResUserName(rdu.getName());
				}
			}
			if(sp.getRolecode()!=null){
		        SysRole sysRole=sysRoleService.selectOne(new EntityWrapper<SysRole>().eq("rolecode", sp.getRolecode()));
		        if(sysRole!=null){
					sp.setRolename(sysRole.getRolename());
		        }
			}
		}
		
		Map<String, Object> result = new HashMap<String, Object>();		
		int total=this_ShopUserService.selectList(wrapper).size();		
		result.put("total", total);
		result.put("data", ShopUserList);		
		return result;
	}

	/***
	 * 单页，如果是修改，显示内容及表单，如果是添加显示表单
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/ShopUserView", method = RequestMethod.GET)
	public ModelAndView view(ModelAndView modelAndView, ShopUser this_ShopUser, @RequestParam(value = "id", required = false) Integer id) {	
		List<SysRole> sysRoles=sysRoleService.selectList(new EntityWrapper<SysRole>()
				//.eq("rolecode","0004")
				.eq("status", "a"));
		if (id != null) {
            modelAndView.addObject("ShopUser", this_ShopUserService.selectById(id));
        }else
        {        	
        	modelAndView.addObject("ShopUser",this_ShopUser);
        }
		modelAndView.addObject("SysRoleList", sysRoles);
	     return modelAndView;
	}

	/***
	 * 保存，如果是新增，调用insert，如果是修改，调用updateById
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/Save", method = RequestMethod.POST)
	@ResponseBody
	public Object Save(ShopUser this_ShopUser,HttpSession session) {
	  		 	  		 	 	 
			  		 	 	 if(StringUtil.isEmpty(this_ShopUser.getName())){
		 	return renderError("姓名不能为空");		}
			  		 	 	 if(StringUtil.isEmpty(this_ShopUser.getPhone())){
		 	return renderError("手机号码不能为空");		}
			  		 	 	 
			  		 	 	 if(StringUtil.isEmpty(this_ShopUser.getRolecode())){
		 	return renderError("管理员角色不能为空");		}
			  		 	 	 if(StringUtil.isEmpty(this_ShopUser.getStatus())){
		 	return renderError("是否启用不能为空");		}
			  		 	 	 if(StringUtil.isEmpty(this_ShopUser.getRemake())){
		 	return renderError("描述不能为空");		}
			  		 	 	
			  	//   this_ShopUser.setOperator(((SysUser)session.getAttribute("loginedUser")).getName());
		
	   this_ShopUser.setUpdateDate(new Date());
	   if (this_ShopUser.getId() == null) {
            return this_ShopUserService.insert(this_ShopUser) ? renderSuccess("添加成功") : renderError("添加失败");
        } else {
            return this_ShopUserService.updateById(this_ShopUser) ? renderSuccess("修改成功") : renderError("修改失败");
        }		
	}

	/***
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/Delete")
	@ResponseBody
	public Object Delete(@RequestParam(value = "id", required = true) Integer id) {
		return this_ShopUserService.deleteById(id) ? renderSuccess("删除成功") : renderError("删除失败");
	}

	/***
	 * 根据ids批量删除，此方法根据前端需要
	 * 
	 * @param ids 逗号拼接项
	 * @return
	 */
	@RequestMapping("/BatchDelete")
	@ResponseBody
	public Object BatchDelete(@RequestParam(value = "ids", required = true) String ids) {
		List<Integer> idList=new ArrayList<Integer>();
		if(StringUtil.isNotEmpty(ids))
		{
			if (ids.contains("all,")) {
				ids=ids.replace("all,", "");//checkbox全选的时候带入，要去掉
			}			
			String[] lsStrings=ids.split(",");			
			for (String id : lsStrings) {
				idList.add(Integer.parseInt(id));
			}
			return this_ShopUserService.deleteBatchIds(idList) ? renderSuccess("删除成功") : renderError("删除失败");
		}else{
			return renderError("请选择需要删除的数据");
		}
		
	}
	
	
	
}
