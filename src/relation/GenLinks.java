package relation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import utility.StoreStringIntoFile;

/**
* @author : wuke
* @date   : 20171221 09:16:53
* Title   : GenLinks
* Description : 按照知识点和视频统一编号，从 0 开始，共1197个；生成links，共2211条，可直接用于邻接矩阵生成的边
*/
public class GenLinks {
	
	private static String LINKS_PATH = "e:\\data\\my_kus\\links_4th_semester_kus_videos 2211.txt"; // 原始，每门课都各自从 1 开始编号
	public static String LINKS_PATH_FINAL = "e:\\data\\my_kus\\links_4th_semester.txt";
	public static int[] NODES_NUM = {396, 680, 973, 1073, 1127, 1197}; // {396, 284, 293, 100, 54, 70}
	private static int[] LINKS_NUM = {405, 726, 1033, 1455, 1775, 2103, 2211}; // {405, 321, 307, 422, 320, 328}
	
	public static void main(String[] args) {
		String result = GenLinks.genLinks();
		StoreStringIntoFile.storeString(result, LINKS_PATH_FINAL, false);
	}
	
	private static String genLinks() {
		String result = "";
		
		File file = new File(LINKS_PATH);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String str = null;
			String str1 = null;
			String str2 = null;
			int from = 0;
		    int to = 0;
			int i = 0; // 文件内容的第 i 行
			while ((str = reader.readLine()) != null) {
				str1 = str.split(",")[0];
				str2 = str.split(",")[1];
				
				 // ku-ku 课程内 
				if (i < LINKS_NUM[0]) { // OS
					from = Integer.parseInt(str1);
					to = Integer.parseInt(str2);
				} else if (LINKS_NUM[0] <= i && i < LINKS_NUM[1]) { // CN
					from = Integer.parseInt(str1) + NODES_NUM[0];
					to = Integer.parseInt(str2) + NODES_NUM[0];
				} else if (LINKS_NUM[1] <= i && i < LINKS_NUM[2]) { // JAVA
					from = Integer.parseInt(str1) + NODES_NUM[1];
					to = Integer.parseInt(str2) + NODES_NUM[1];
				}
				// video-ku
				else if (LINKS_NUM[2] <= i && i < LINKS_NUM[3]) { // OS
					from = Integer.parseInt(str1) + NODES_NUM[2];
					to = Integer.parseInt(str2);
				} else if (LINKS_NUM[3] <= i && i < LINKS_NUM[4]) { // CN
					from = Integer.parseInt(str1) + NODES_NUM[3];
					to = Integer.parseInt(str2) + NODES_NUM[0];
				} else if (LINKS_NUM[4] <= i && i < LINKS_NUM[5]) { // JAVA
					from = Integer.parseInt(str1) + NODES_NUM[4];
					to = Integer.parseInt(str2) + NODES_NUM[1];
				}
				// ku-ku 课程间 108个，已经按所有973个知识点统一编号过，无须特殊处理
				else { 
					from = Integer.parseInt(str1);
					to = Integer.parseInt(str2);
				}
				
				result += (from - 1) + "," + (to - 1) + "\r\n"; // 减一保证编号从 0 开始
	
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
		
		return result;
	}
}
