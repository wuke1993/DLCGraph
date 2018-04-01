package relation;

import Jama.Matrix;
import utility.StoreStringIntoFile;

public class ILN
{
	private static String AdjacencyMatrix = "e:\\data\\my_kus\\relationship_videos.txt";
	private double[][] weightArray; // 邻接矩阵
	private double[][] aArray; // clone of weightArray
	private double[][] dArray; // 度矩阵
	private double vg;
	private double[][] lArray; // 拉普拉斯矩阵
	private double[][] lppArray;// 拉普拉斯矩阵伪逆
	private double[][] disArray; // the average commute time
	private int verticeSize;

	public static void main(String[] args) {
		ILN obj = new ILN();
		double[][] result = obj.getDisArray();
		
		StringBuilder s = new StringBuilder();
		for (int i = 973; i < 1197; i++) {
			for (int j = 973; j < 1197; j++) {
				s.append(result[i][j] + "  ");
			}
			s.append("\r\n");
		}
		
		StoreStringIntoFile.storeString(s.toString(), ILN.AdjacencyMatrix, false);
	}
	
	ILN() {
		weightArray = GenAdjacencyMatrix.genAdjacencyMatrix(1, 1, 2, 0.5);
		verticeSize = weightArray.length;
		
		cacualteDistanceArray();
	}

	private void cacualteDistanceArray() {
		init();
		caculateArrays();
	}

	private void init() {
		aArray = new double[verticeSize][verticeSize];		
		dArray = new double[verticeSize][verticeSize];		
		lArray = new double[verticeSize][verticeSize];		
		lppArray = new double[verticeSize][verticeSize];		
		disArray = new double[verticeSize][verticeSize];
	}

	private void caculateArrays() {
		caculateAArray();
		caculateDArray();
		caculateVg();
		caculateLArray();
		caculateLppArray();
		caculateSArray();
	}

	private void caculateAArray() {
		aArray = clone(weightArray);
	}

	private void caculateDArray() {
		for (int i = 0; i < verticeSize; i++) {
			for (int j = 0; j < verticeSize; j++) {
				if (aArray[i][j] != 0) {
					dArray[i][i]++;
				}
				// dArray[i][i] += aArray[i][j];
			}
		}
	}

	private void caculateVg() { // 度矩阵的对角线和
		vg = 0;
		for (int i = 0; i < verticeSize; i++) {
			vg += dArray[i][i];
		}

	}

	private void caculateLArray() {
		Matrix dMatrix = new Matrix(clone(dArray));
		Matrix aMatrix = new Matrix(clone(aArray));
		Matrix lMatrix = dMatrix.minus(aMatrix);
		lArray = lMatrix.getArray();
	}

	private void caculateLppArray() {
		Matrix lMatrix = new Matrix(clone(lArray));
		Matrix eMatrix = new Matrix(verticeSize, 1, 1.0);
		Matrix etMatrix = new Matrix(1, verticeSize, 1.0);
		Matrix lppMatrix = lMatrix.minus(eMatrix.times(etMatrix).times(1.0 / verticeSize)).inverse().plus(eMatrix.times(etMatrix).times(1.0 / verticeSize));
		lppArray = lppMatrix.getArray();
	}

	private void caculateSArray() {
		for (int i = 0; i < verticeSize; i++)
		{
			for (int j = 0; j < verticeSize; j++)
			{
				// disArray[i][j] = vg * (lppArray[i][i] + lppArray[j][j] - 2 * lppArray[i][j]);
				disArray[i][j] = lppArray[i][i] + lppArray[j][j] - 2 * lppArray[i][j];
			}
		}

	}

	private double[][] clone(double[][] array) {
		int xLength = array.length;
		int yLength = array[0].length;
		double[][] newArray = new double[xLength][yLength];
		for (int i = 0; i < xLength; i++) {
			for (int j = 0; j < yLength; j++) {
				newArray[i][j] = array[i][j];
			}
		}
		return newArray;
	}

	public double[][] getDisArray() {
		return disArray;
	}
	
}
