package visualization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import utility.StoreStringIntoFile;

import bean.Node;

/**
* @author : wuke
* @date   : 20171203 21:48:05
* Title   : GenEchartsJsonWithoutViedos
* Description : 
*/
public class GenEchartsJsonWithoutViedos {

	private static String KUS_PATH = "e:\\data\\my_kus\\4th-semester-without-videos\\nodes_4th_semester_kus 973.txt";
	private static String LINKS_PATH = "e:\\data\\my_kus\\4th-semester-without-videos\\links_4th_semester_kus 1141.txt";
	private static String SEMESTER_PATH = "e:\\data\\my_kus\\echarts\\webkit-dep-4th-semester-without-videos.json";
	
	public static void main(String[] args) {

		JSONArray nodesJsonArr = GenEchartsJsonWithoutViedos.genNodes(GenEchartsJsonWithoutViedos.KUS_PATH);
		JSONArray linksJsonArr = GenEchartsJsonWithoutViedos.genLinks(GenEchartsJsonWithoutViedos.LINKS_PATH);
		
		ArrayList<String> courses_names = new ArrayList<String>();
		courses_names.add("operating_system");
		courses_names.add("computer_network");
		courses_names.add("java");
		
		GenEchartsJsonWithoutViedos.genAll(nodesJsonArr, linksJsonArr, courses_names, GenEchartsJsonWithoutViedos.SEMESTER_PATH);
	}

	/**
	 * 从文件中读取所有知识点，转成 JSON 格式，存入 JSONArray
	 * @param kusPath
	 * @return
	 */
	private static JSONArray genNodes(String kusPath) {
		// 从文件中读取所有知识点
        ArrayList<String> kus = new ArrayList<String>();
		
		File file = new File(kusPath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String str = null;
			while ((str = reader.readLine()) != null) {
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
		
		// 将读取出来的知识点集合转成 JSON 格式
		JSONArray jsonArr = new JSONArray();
		JSONObject json = null;
		Node node = null;
		int i = 0;
		for (String s : kus) {
			if (i < 396) {
				node = new Node(s, 1, 0, "circle");
			} else if (i > 679) {
				node = new Node(s, 1, 2, "circle");
			} else {
				node = new Node(s, 1, 1, "circle");
			}
			json = (JSONObject) JSON.toJSON(node);
			jsonArr.add(json);
			
			i++;
		}
		
		return jsonArr;
	}
	
	/**
	 * 
	 * @param linksPath
	 * @return
	 */
	private static JSONArray genLinks(String linksPath) {
		JSONArray jsonArr = new JSONArray();
		JSONObject json_link = null;
		JSONObject json_normal = null;
		JSONObject json_color = null;
				
		File file = new File(linksPath);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String str = null;
			int tag = 0;
		    int tmp_tag = 0;
			int i = 0;
			while ((str = reader.readLine()) != null) {
				json_link = new JSONObject();
				json_normal = new JSONObject();
				json_color = new JSONObject();
				
				if (i < 405) {
					tag = Integer.parseInt(str.split(",")[0]) - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) - 1;
					json_color.put("color", "#ccc");
				} else if (404 < i && i < 726) {
					tag = Integer.parseInt(str.split(",")[0]) + 396 - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) + 396 - 1;
					json_color.put("color", "#ccc");
				} else if (725 < i && i < 1033) {
					tag = Integer.parseInt(str.split(",")[0]) + 680 - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) + 680 - 1;
					json_color.put("color", "#ccc");
				} else {
					tag = Integer.parseInt(str.split(",")[0]) - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) - 1;
					json_color.put("color", "#f00");
				}

				json_normal.put("normal", json_color);
				
				json_link.put("lineStyle", json_normal);
				json_link.put("source", tag);
				json_link.put("target", tmp_tag);
				
				jsonArr.add(json_link);

				i++;
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
	 * @param nodesJsonArr
	 * @param linksJsonArr
	 * @param courses_names
	 * @param path
	 */
	private static void genAll(JSONArray nodesJsonArr, JSONArray linksJsonArr, ArrayList<String> courses_names, String path) {
				
		JSONArray categoriesJsonArr = new JSONArray();
		for (String str : courses_names) {
			JSONObject course = new JSONObject();
			course.put("name", str);
			course.put("keyword", "{}");
			course.put("base", str);
	        categoriesJsonArr.add(course);
		}
		
		JSONObject all = new JSONObject();
		all.put("type", "force");
		all.put("categories", categoriesJsonArr);
		all.put("nodes", nodesJsonArr);
		all.put("links", linksJsonArr);
		
		StoreStringIntoFile.storeString(all.toString(), path, false);
	}
}
