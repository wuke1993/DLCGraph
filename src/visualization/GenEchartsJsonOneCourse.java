package visualization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import bean.Node;
import utility.StoreStringIntoFile;

/**
* @author : wuke
* @date   : 20171204 14:56:59
* Title   : GenEchartsJsonOneCourse
* Description : TODO 加上视频
*/
public class GenEchartsJsonOneCourse {
	private static String COURSE_NAME = "Java";
	private static String KUS_PATH = "e:\\data\\my_kus\\one\\kus_8_Java 293.txt";
	private static String LINKS_PATH = "e:\\data\\my_kus\\one\\links_8_Java 307.txt";
	private static String RESULT_PATH = "e:\\data\\my_kus\\echarts\\webkit-dep-4th-semester-Java.json";
	
	public static void main(String[] args) {
		JSONArray nodesJsonArr = GenEchartsJsonOneCourse.genNodes(GenEchartsJsonOneCourse.KUS_PATH);
		JSONArray edgesJsonArr = GenEchartsJsonOneCourse.genEdges(GenEchartsJsonOneCourse.LINKS_PATH);
		
		GenEchartsJsonOneCourse.genAll(GenEchartsJsonOneCourse.COURSE_NAME, nodesJsonArr, edgesJsonArr, GenEchartsJsonOneCourse.RESULT_PATH);
	}
	
    private static JSONArray genNodes(String kusPath) {
		
        ArrayList<String> kus = new ArrayList<String>();
		
		File file = new File(kusPath);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String str = null;
			while((str = reader.readLine()) != null) {
				kus.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		JSONArray jsonArr = new JSONArray();
		
		JSONObject json = null;
		Node node = null;
		for(String s : kus) {
			
			node = new Node(s, 1, 0, "circle");
			
			json = (JSONObject) JSON.toJSON(node);
			jsonArr.add(json);
		}
		
		return jsonArr;
	}
    
    private static JSONArray genEdges(String linksPath) {
		JSONArray jsonArr = new JSONArray();
		JSONObject json = null;
		
		File file = new File(linksPath);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String str = null;
			int tag = 0;
		    int tmp_tag = 0;
			while((str = reader.readLine()) != null) {
				
				tag = Integer.parseInt(str.split(",")[0]) - 1;
				tmp_tag = Integer.parseInt(str.split(",")[1]) - 1;
				
				json = new JSONObject();
				json.put("source", tag);
				json.put("target", tmp_tag);
				
				jsonArr.add(json);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return jsonArr;
	}
    
    /**
     * 
     * @param courseName
     * @param nodesJsonArr
     * @param linksJsonArr
     * @param storePath
     */
    private static void genAll(String courseName, JSONArray nodesJsonArr, JSONArray edgesJsonArr, String storePath) {
		JSONObject course = new JSONObject();
		course.put("name", courseName);
		course.put("keyword", "{}");
		course.put("base", courseName);
		
		JSONArray categoriesJsonArr = new JSONArray();
		categoriesJsonArr.add(course);
		
		JSONObject all = new JSONObject();
		all.put("type", "force");
		all.put("categories", categoriesJsonArr);
		all.put("nodes", nodesJsonArr);
		all.put("links", edgesJsonArr);
		
		StoreStringIntoFile.storeString(all.toString(), storePath, false);
	}
}
