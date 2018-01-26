package relation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import utility.StoreStringIntoFile;

/**
* @author : wuke
* @date   : 20171221 09:51:47
* Title   : GenAdjacencyMatrix
* Description : 
*/
public class GenAdjacencyMatrix {

	private static String LINKS_PATH_FINAL = "e:\\data\\my_kus\\links_4th_semester.txt";
	private static String AdjacencyMatrix = "e:\\data\\my_kus\\adjacency_matrix.txt";
	
	public static void main(String[] args) {

		double[][] result = GenAdjacencyMatrix.genAdjacencyMatrix();
		
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 1197; i++) {
			for (int j = 0; j < 1197; j++) {
				s.append(result[i][j] + " ");
			}
			s.append("\r\n");
		}
		
		StoreStringIntoFile.storeString(s.toString(), GenAdjacencyMatrix.AdjacencyMatrix, false);
		
		// 测试有没有空行
		/*int flag = 0;
		for (int i = 0; i < 1197; i++) {
			for (int j = 0; j < 1197; j++) {
				if (result[i][j] != 0) {
					flag = 1;
				}
			}
			if (flag == 0) {
				System.out.println(i);
			}
			flag = 0;
		}*/
	}

	public static double[][] genAdjacencyMatrix() {
		double[][] result = new double[1197][1197];
		
		File file = new File(LINKS_PATH_FINAL);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String str = "";
			
			int i = 0;
			int node1 = 0;
			int node2 = 0;
			while ((str = br.readLine()) != null) {
				node1 = Integer.parseInt(str.split(",")[0]);
				node2 = Integer.parseInt(str.split(",")[1]);
				
				if (i < 1033) {
					result[node1][node2] = 1;
					result[node2][node1] = 1;
				} else if (1032 < i && i < 2103) {
					result[node1][node2] = 1;
					result[node2][node1] = 1;
				} else {
					result[node1][node2] = 1;
					result[node2][node1] = 1;
				}
				
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < 396; i++) {
			result[1071][i] = 1;
			result[i][1071] = 1;
			result[1072][i] = 1;
			result[i][1072] = 1;
		}
		for (int i = 396; i < 680; i++) {
			result[1125][i] = 1;
			result[i][1125] = 1;
			result[1126][i] = 1;
			result[i][1126] = 1;
		}
		for (int i = 680; i < 973; i++) {
			result[1196][i] = 1;
			result[i][1196] = 1;
		}
		
		return result;
	}
}
