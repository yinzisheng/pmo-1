package com.pmo.dashboard.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmo.dashboard.entity.User;
import com.pmo.dashboard.util.Utils;
import com.pom.dashboard.service.UserService;


/**
 * 用户相关
 * @author Devin
 * @since 2018-1-2
 * @version 1.0
 */
@Controller
@RequestMapping(value="user")
public class UserRelevantController {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
            .getLogger(UserRelevantController.class);
	
	@Resource
    private UserService userService;
	
	/**
	 * 添加页面
	 * @author Devin
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/addUserPage")
    public String index(final HttpServletRequest request,
            final HttpServletResponse response,Model model)
    {
        return "user/addUser";
    }
	
	/**
	 * 添加用户
	 * @author Devin
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addUser")
    @ResponseBody
    public boolean addUser(final HttpServletRequest request,
            final HttpServletResponse response)
    {
        
        String userId = Utils.getUUID(); 
        String eHr = request.getParameter("eHr");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String bu = request.getParameter("bu");
        String du = request.getParameter("du");
        
        User user = new User(userId,eHr,name,
                "123", type, bu, du);
        
        boolean resultFlag = userService.addUser(user);
        
        System.out.println("添加用户========================"+eHr+name+type+bu+du+resultFlag);
        
        return resultFlag;
    }
	
	/**
	 * 校验Ehr
	 * @author Devin
	 * @param eHr
	 * @return
	 */
	@RequestMapping("/checkEhr")
	@ResponseBody
	public String checkUser(String eHr){
		boolean result = true;
		User user = userService.checkUser(eHr);
		if(user!=null){
			result = false;
		}
		Map<String, Boolean> map = new HashMap<>();
        map.put("valid", result);
        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
	}

}
