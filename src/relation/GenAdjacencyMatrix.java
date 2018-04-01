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
* Description : 有权无向图。共 1197 个节点，2211 条边
*/
public class GenAdjacencyMatrix {
	private static String AdjacencyMatrix = "e:\\data\\my_kus\\adjacency_matrix.txt";
	private static int NODES = 1197;
	private static int[] VIEDO_REVISION  = {1071, 1072, 1125, 1126, 1196};
	
	public static void main(String[] args) {
		double[][] result = GenAdjacencyMatrix.genAdjacencyMatrix(1, 1, 2, 0.5);
		
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < GenAdjacencyMatrix.NODES; i++) {
			for (int j = 0; j < GenAdjacencyMatrix.NODES; j++) {
				s.append(result[i][j] + " ");
			}
			s.append("\r\n");
		}
		
		StoreStringIntoFile.storeString(s.toString(), GenAdjacencyMatrix.AdjacencyMatrix, false);
		
		// 测试有没有空行
		/*int flag = 0;
		for (int i = 0; i < GenAdjacencyMatrix.NODES_NUM; i++) {
			for (int j = 0; j < GenAdjacencyMatrix.NODES_NUM; j++) {
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
	
	/**
	 * 生成带权邻接矩阵
	 * @param weight1 ku-ku 课程内 
	 * @param weight2 video-ku
	 * @param weight3 ku-ku 课程间
	 * @param weight4   总复习视频与知识点
	 * @return
	 */
	public static double[][] genAdjacencyMatrix(int weight1, int weight2, int weight3, double weight4) {
		double[][] result = new double[GenAdjacencyMatrix.NODES][GenAdjacencyMatrix.NODES];
		
		File file = new File(GenLinks.LINKS_PATH_FINAL);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String str = "";
			
			int i = 0;
			int from = 0;
			int to = 0;
			while ((str = br.readLine()) != null) {
				from = Integer.parseInt(str.split(",")[0]);
				to = Integer.parseInt(str.split(",")[1]);
				
				if (i < 1033) { //  ku-ku 课程内 
					result[from][to] = weight1;
					result[to][from] = weight1;
				} else if (1032 < i && i < 2103) { // video-ku
					result[from][to] = weight2;
					result[to][from] = weight2;
				} else { // ku-ku 课程间
					result[from][to] = weight3;
					result[to][from] = weight3;
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
		
		// 各课程中的个别“总复习”课程处理
		for (int i = 0; i < GenLinks.NODES_NUM[0]; i++) {
			result[VIEDO_REVISION[0]][i] = weight4;
			result[i][VIEDO_REVISION[0]] = weight4;
			
			result[VIEDO_REVISION[1]][i] = weight4;
			result[i][VIEDO_REVISION[1]] = weight4;
		}
		for (int i = GenLinks.NODES_NUM[0]; i < GenLinks.NODES_NUM[1]; i++) {
			result[VIEDO_REVISION[2]][i] = weight4;
			result[i][VIEDO_REVISION[2]] = weight4;
			
			result[VIEDO_REVISION[3]][i] = weight4;
			result[i][VIEDO_REVISION[3]] = weight4;
		}
		for (int i = GenLinks.NODES_NUM[1]; i < GenLinks.NODES_NUM[2]; i++) {
			result[VIEDO_REVISION[4]][i] = weight4;
			result[i][VIEDO_REVISION[4]] = weight4;
		}
		
		return result;
	}
}
