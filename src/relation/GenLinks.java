package relation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import utility.StoreStringIntoFile;

/**
* @author : wuke
* @date   : 20171221 09:16:53
* Title   : GenLinks
* Description : 按照知识点和视频统一编号，共2211个，对 links 进行处理，生成可直接用于邻接矩阵生成的边
*/
public class GenLinks {
	
	private static String LINKS_PATH = "e:\\data\\my_kus\\links_4th_semester_kus_videos 2211.txt";
	private static String LINKS_PATH_FINAL = "e:\\data\\my_kus\\links_4th_semester.txt";

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
			int tag = 0;
		    int tmp_tag = 0;
			int i = 0;
			while ((str = reader.readLine()) != null) {
				
				if (i < 405) { // ku-ku 课程内
					tag = Integer.parseInt(str.split(",")[0]) - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) - 1;
				} else if (404 < i && i < 726) {
					tag = Integer.parseInt(str.split(",")[0]) + 396 - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) + 396 - 1;
				} else if (725 < i && i < 1033) {
					tag = Integer.parseInt(str.split(",")[0]) + 680 - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) + 680 - 1;
				} else if (1032 < i && i < 1455) { // video-ku
					tag = Integer.parseInt(str.split(",")[0]) + 973 - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) - 1;
				} else if (1454 < i && i < 1775) {
					tag = Integer.parseInt(str.split(",")[0]) + 1073 - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) + 396 - 1;
				} else if (1774 < i && i < 2103) {
					tag = Integer.parseInt(str.split(",")[0]) + 1127 - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) + 680 - 1;
				} else { // ku-ku 课程间 108个
					tag = Integer.parseInt(str.split(",")[0]) - 1;
					tmp_tag = Integer.parseInt(str.split(",")[1]) - 1;
				}
				
				result += tag + "," + tmp_tag + "\r\n";
	
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
