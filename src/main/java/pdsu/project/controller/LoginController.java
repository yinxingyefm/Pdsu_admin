package pdsu.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import pdsu.dqw.interceptor.User;
import pdsu.dqw.util.JWTUtil;
import pdsu.dqw.util.ResponseData;
import pdsu.project.domain.ResFormat;
import pdsu.project.service.StudentInformationService;

@RestController
@RequestMapping("login")
public class LoginController {
	@Autowired
	private StudentInformationService studentInformationService;

	@PostMapping
    public ResponseEntity<ResFormat> login(@RequestBody User user) {
		System.out.println(user);
    	//模拟去查询数据库，看是否存在此用户
		System.out.println("login-------------------------");
		boolean login=false;
//		JSONObject jsStr = JSONObject.parseObject(user);
//		User u= (User) JSONObject.toJavaObject(jsStr,User.class);
//    	/*if(u.getName().equals("aa")&&u.getPassword().equals("aa")){
//    		login = true;
//    	}*/
		Map<String, Object> userInfo = studentInformationService.selectUserInfo(user.getUserName(), user.getPassword());
		Map<String,Object> res = new HashMap<>();

    	if(userInfo!=null){
			String token = JWTUtil.generToken("1", "Jersey-Security-Basic",userInfo.toString());
			System.out.println(token);
			res.put("token",token);
			res.put("userInfo",userInfo);
    	}else{
    		return ResponseEntity.ok(new ResFormat(110,"user cont found"));
    	}

//    	ResponseData responseData = ResponseData.ok();
//    	if(login) {
//    		//生成token
//    		Sring token = JWTUtil.generToken("1", "Jersey-Security-Basic",user);
//    		System.out.println("token:"+token);
//    		//向浏览器返回token，客户端受到此token后存入cookie中，或者h5的本地存储中
//    		responseData.putDataValue("token", token);
//    		//以及用户
//    		responseData.setObject(userInfo);
//    	}else {
//    		//用户或者密码错误
//    		responseData=ResponseData.customerError();
//    	}
		return ResponseEntity.ok(new ResFormat(0,"success",res));
    }
   

}
