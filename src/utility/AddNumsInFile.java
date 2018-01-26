package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
* @author : wuke
* @date   : 20171207 22:35:57
* Title   : AddNumsInFile
* Description : 
*/
public class AddNumsInFile {
	
	public static void main(String[] args) {
		
		StringBuilder sb = new StringBuilder();
		
		File file = new File("e:\\data\\my_kus\\one\\links_between_4th_courses.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String str = null;
			String first = null;
			String second = null;
			int left = 0;
			int right = 0;
			while ((str = reader.readLine()) != null) {
				first = str.split(",")[0];
				second = str.split(",")[1];
				
				if(first.contains("+")) {
					left = Integer.parseInt(first.split("\\+")[0]) + Integer.parseInt(first.split("\\+")[1]);
					sb.append(left);
				} else {
					sb.append(first);
				}
				
				sb.append(",");
				
				if(second.contains("+")) {
					right = Integer.parseInt(second.split("\\+")[0]) + Integer.parseInt(second.split("\\+")[1]);
					sb.append(right);
				} else {
					sb.append(second);
				}
				
				sb.append("\r\n");
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
		
		StoreStringIntoFile.storeString(sb.toString(), "e:\\data\\my_kus\\one\\links_between_4th_courses 108.txt", false);
	}
}
