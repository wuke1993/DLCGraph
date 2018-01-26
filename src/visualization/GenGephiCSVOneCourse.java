package visualization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import utility.StoreStringIntoFile;

/**
* @author : wuke
* @date   : 20180103 15:47:55
* Title   : GenGephiCSVOneCourse
* Description : 
*/
public class GenGephiCSVOneCourse {
	private static String KUS_PATH = "e:\\data\\my_kus\\one\\kus_8_Java 293.txt";
	private static String VIDEOS_PATH = "e:\\data\\my_kus\\one\\videos_8_Java 70.txt";
	private static String LINKS_PATH = "e:\\data\\my_kus\\one\\links_8_Java 307.txt";
	private static String LINKS_V_K_PATH = "e:\\data\\my_kus\\one\\links_videos_kus_8_Java 328.txt";
	private static String NODES_PATH = "e:\\data\\my_kus\\gephi\\gephi_nodes_Java.csv";
	private static String EDGES_PATH = "e:\\data\\my_kus\\gephi\\gephi_edges_Java.csv";
	
	public static void main(String[] args) {
		StoreStringIntoFile.storeString(GenGephiCSVOneCourse.genNodes(GenGephiCSVOneCourse.KUS_PATH, GenGephiCSVOneCourse.VIDEOS_PATH), GenGephiCSVOneCourse.NODES_PATH, false);
		StoreStringIntoFile.storeString(GenGephiCSVOneCourse.genEdges(GenGephiCSVOneCourse.LINKS_PATH, GenGephiCSVOneCourse.LINKS_V_K_PATH), GenGephiCSVOneCourse.EDGES_PATH, false);
	}

	/**
	 * 
	 * @param kusPath
	 * @param videosPath
	 * @return
	 */
	public static String genNodes(String kusPath, String videosPath) {
		StringBuilder sb = new StringBuilder();
		sb.append("Id,Label,timeset,modularity_class" + "\n\r");
		
		int id = 1;
		String label = "";
		String timeset = ""; // 为空
		int modularity_class = 0;
		
		File file = null;
		BufferedReader reader = null;
		
		try {
			// 知识点
			file = new File(kusPath);
			reader = new BufferedReader(new FileReader(file));
			while ((label = reader.readLine()) != null) {
				sb.append(id + ",").append(label + ",").append(timeset + ",").append(modularity_class).append("\n\r");
				
				id++;
			}
			reader.close();
			
			// 视频
			modularity_class = 1;
			file = new File(videosPath);
			reader = new BufferedReader(new FileReader(file));
			while ((label = reader.readLine()) != null) {
				sb.append(id + ",").append(label + ",").append(timeset + ",").append(modularity_class).append("\n\r");
				
				id++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @param linksPath
	 * @param links_v_k_Path
	 * @return
	 */
	public static String genEdges(String linksPath, String links_v_k_Path) {
		StringBuilder sb = new StringBuilder();
		sb.append("Source,Target,Type,Id,Label,timeset,Weight" + "\n\r");
		
		int source = 0;
		int target = 0;
		String type = "Undirected";
		int id = 0;
		String lable = ""; // 为空
		String timeset = ""; // 为空
		double weight = 1.1;
		
		File file = null;
		BufferedReader reader = null;
		
		try {
			// 知识点-知识点
			file = new File(linksPath);
			reader = new BufferedReader(new FileReader(file));
			String str = "";
			while ((str = reader.readLine()) != null) {
				source = Integer.parseInt(str.split(",")[0]);
				target = Integer.parseInt(str.split(",")[1]);
				
				sb.append(source + ",").append(target + ",").append(type + ",").append(id + ",").append(lable + ",")
						.append(timeset + ",").append(weight).append("\n\r");
				
				id++;
			}
			reader.close();
			
			// 视频-知识点
			weight = 1;
			file = new File(links_v_k_Path);
			reader = new BufferedReader(new FileReader(file));
			while ((str = reader.readLine()) != null) {
				source = Integer.parseInt(str.split(",")[0]) + 293;
				target = Integer.parseInt(str.split(",")[1]);
				
				sb.append(source + ",").append(target + ",").append(type + ",").append(id + ",").append(lable + ",")
						.append(timeset + ",").append(weight).append("\n\r");
				
				id++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}
}
