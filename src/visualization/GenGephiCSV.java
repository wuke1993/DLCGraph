package visualization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import utility.StoreStringIntoFile;

/**
* @author : wuke
* @date   : 20180103 21:28:55
* Title   : GenGephiCSV
* Description : 
*/
public class GenGephiCSV {
	private static String KUS_VIDEOS_PATH = "e:\\data\\my_kus\\nodes_4th_semester_kus_videos 1197.txt";
	private static String LINKS_PATH = "e:\\data\\my_kus\\links_4th_semester.txt";
	private static String NODES_PATH = "e:\\data\\my_kus\\gephi\\gephi_nodes_4th_semester.csv";
	private static String EDGES_PATH = "e:\\data\\my_kus\\gephi\\gephi_edges_4th_semester.csv";
	
	public static void main(String[] args) {
		StoreStringIntoFile.storeString(GenGephiCSV.genNodes(GenGephiCSV.KUS_VIDEOS_PATH), GenGephiCSV.NODES_PATH, false);
		StoreStringIntoFile.storeString(GenGephiCSV.genEdges(GenGephiCSV.LINKS_PATH), GenGephiCSV.EDGES_PATH, false);
	}

	public static String genNodes(String kusPath) {
		StringBuilder sb = new StringBuilder();
		sb.append("Id,Label,timeset,modularity_class" + "\n\r");
		
		int id = 0;
		String label = "";
		String timeset = ""; // 为空
		int modularity_class = 0;
		
		File file = null;
		BufferedReader reader = null;
		
		try {
			file = new File(kusPath);
			reader = new BufferedReader(new FileReader(file));
			while ((label = reader.readLine()) != null) {
				// if (id == 396 || id == 680 || id == 973 || id == 1073 || id == 1127) {
				if (id == 396 || id == 680 || id == 973) {
					modularity_class++;
				}
				
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
	
	public static String genEdges(String linksPath) {
		StringBuilder sb = new StringBuilder();
		sb.append("Source,Target,Type,Id,Label,timeset,Weight" + "\n\r");
		
		int source = 0;
		int target = 0;
		String type = "Undirected";
		int id = 0;
		String lable = ""; // 为空
		String timeset = ""; // 为空
		double weight = 1;
		
		File file = null;
		BufferedReader reader = null;
		
		try {
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
