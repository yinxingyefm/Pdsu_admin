package pdsu.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pdsu.dqw.util.ResponseData;
import pdsu.project.domain.AllSchoolNumInArea;
import pdsu.project.domain.AllTeacherAndStudentOutSideOrInSide;
import pdsu.project.domain.DorInformation;
import pdsu.project.domain.OutSideInfo;
import pdsu.project.domain.OutSideInfoForSomeOneDay;
import pdsu.project.domain.RecordedInformation;
import pdsu.project.domain.TempInformation;
import pdsu.project.domain.TempTempForToDay;
import pdsu.project.domain.UnusualInformation;
import pdsu.project.service.AllTeacherAndStudentOutSideOrInSideService;
import pdsu.project.service.StudentInformationService;
import pdsu.project.service.TempInfoService;
import pdsu.project.service.TempTempForToDayService;
import pdsu.project.service.ViewAllSchoolAreaInformationService;

@RestController
@RequestMapping("/")
public class DataController {
	@Autowired
	private AllTeacherAndStudentOutSideOrInSideService outService;
	@Autowired
	private ViewAllSchoolAreaInformationService areaService;
	@Autowired
	private StudentInformationService studentInformationService;
	@Autowired
	private TempInfoService tempInfoService;
	@Autowired
	private TempTempForToDayService weekTempService;

	//统计外出情况
	@GetMapping("outside/{role}")
	public ResponseData outside(@PathVariable("role") String role){
		ResponseData responseData = ResponseData.ok();
		AllTeacherAndStudentOutSideOrInSide data = outService.Information(role);
		responseData.setObject(data);
		return responseData;
	}
	//统计每天各地区变化
	@GetMapping("china/{id}")
	public ResponseData stuAndTerAreaInformation(@PathVariable("id") int id){
		ResponseData responseData = ResponseData.ok();
		List<AllSchoolNumInArea> information = areaService.StudentAndTeacherAreaInformation(id);
		responseData.setObject(information);
		return responseData;
	}
	//统计师生在各个地区一星期的人数变化
	@GetMapping("population/{id}")
	public ResponseData stuAndterAreaForWeek(@PathVariable("id") int id){
		ResponseData responseData = ResponseData.ok();
		Map<String, Object> data = areaService.StudentAndTeacherAreaInformationForWeek(id);
		responseData.setObject(data);
		return responseData;
	}
	//通过班级名称查询班级信息
	@GetMapping("classinfo/{className}")
	public ResponseData selectClassInfoByClassName(@PathVariable("className") String className){
		ResponseData responseData = ResponseData.ok();
		Map<String, Object> data = studentInformationService.selectClassInfoByClassName(className);
		responseData.setObject(data);
		return responseData;
	}
	//根据宿舍号查询宿舍成员信息
	@GetMapping("dormitoryInfo/{dormitoryNum}")
	public ResponseData selectDorInfo(@PathVariable("dormitoryNum")   int dormitoryNum){
		ResponseData responseData = ResponseData.ok();
	    DorInformation data = studentInformationService.selectDorInfo(dormitoryNum);
		responseData.setObject(data);
		return responseData;
	}
	//统计一周正常体温变化
		@GetMapping("temp/line/{role}")
		public ResponseData tempForOneDayForRole(@PathVariable("role") String role) throws Exception{
			ResponseData responseData = ResponseData.ok();
			System.out.println("role:"+role);
			 TempTempForToDay data = weekTempService.selectAWeekforRole(role);
			if(data!=null){
				responseData.setObject(data);
				return responseData;
			}else{
				return responseData;
			}
		   
			
			
		}
	//获取指定时间师生外出人员信息
		@RequestMapping("outside/{role}/{time}")
		public ResponseData detailOutMag(@PathVariable("role") String role,@PathVariable("time") String time){
			System.out.println("role:"+role+"time:"+time);
			ResponseData responseData = ResponseData.ok();
			ArrayList<OutSideInfoForSomeOneDay> forTimeAndRole = outService.forTimeAndRole(role, time);
			responseData.setObject(forTimeAndRole);
			return responseData;
		}
	//统计师生今日的温度 	
		@GetMapping("temp/{role}")
		public ResponseData TodayTemMag(@PathVariable("role") String role){
			System.out.println("role:"+role);
			ResponseData responseData = ResponseData.ok();
			List<TempInformation> tempForOneDayForRole = tempInfoService.tempForOneDayForRole(role);
			responseData.setObject(tempForOneDayForRole);
			return responseData;
		}
   //统计录入情况/enter/:{role}/:{grade}
		@RequestMapping("enter/{role}")
		public ResponseData ReportMag(@PathVariable("role") String role) throws Exception{
			System.out.println("role:"+role);
			ResponseData responseData = ResponseData.ok();
			ArrayList<RecordedInformation> information = tempInfoService.RecordedInformation(role);
			responseData.setObject(information);
			return responseData;
		}
	//获取指定时间师生体温异常/unusual/:{role}/:{time}
		@RequestMapping("unusual/{role}/{time}")
		public ResponseData detailTimMag(@PathVariable("role") String role,@PathVariable("time") String time){
			System.out.println("role:"+role+"time:"+time);
			ResponseData responseData = ResponseData.ok();
			List<UnusualInformation> unmalForTime = outService.unmalForTime(role, time);
			responseData.setObject(unmalForTime);
			return responseData;
		}
     //查看所有人员/userList
		@GetMapping("userList")
		public ResponseData userList(){
			ResponseData responseData = ResponseData.ok();
			List<Map<String,Object>> all = studentInformationService.findAll();
			responseData.setObject(all);
			return responseData;
		}
//*更新用户/user/:{id}
		@PutMapping("user/{id}")
		public ResponseData updateUser(@PathVariable("id") Integer id){
			ResponseData responseData = ResponseData.ok();
			System.out.println("更新人员信息："+id);
			return responseData;
		}
		//根据id查看用户 /user/:{id}
		@GetMapping("user/{id}")
		public ResponseData getUserbyId(@PathVariable("id") Integer id){
			ResponseData responseData = ResponseData.ok();
			System.out.println("查看人员信息："+id);
			Map<String, Object> map = studentInformationService.selectById(id);
			responseData.setObject(map);
			return responseData;
		}
		//根据学号或工号查找用户/user/:{num}
		@GetMapping("user/num/{num}")
		public ResponseData getUserbyNum(@PathVariable("num") String num){
			ResponseData responseData = ResponseData.ok();
			Map<String, Object> map = studentInformationService.selectByNum(num);
			responseData.setObject(map);
			return responseData;
		}
		//获取指定师生每天外出地点/outSide/:{role}/:{num}
				@RequestMapping(value="outSide/{role}/{num}")
				public ResponseData OneOutAdr(@PathVariable("role") String role,@PathVariable("num") String num){
					System.out.println("role:"+role+"num:"+num);
					ResponseData responseData = ResponseData.ok();
					List<OutSideInfo> allOutsideInformation = outService.AllOutsideInformation(role, num);
					responseData.setObject(allOutsideInformation);
					return responseData;
				}
	   //copyMap		
				public  void mapCopy(Map paramsMap, Map resultMap) {
				    if (resultMap == null) resultMap = new HashMap();
				    Iterator it = paramsMap.entrySet().iterator();
				    while (it.hasNext()) {
				        Map.Entry entry = (Map.Entry) it.next();
				        Object key = entry.getKey();
				        resultMap.put(key, paramsMap.get(key) != null ? paramsMap.get(key) : "");

				    }
				}
}

















