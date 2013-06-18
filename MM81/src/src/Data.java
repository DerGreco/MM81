package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Data {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {		
		BufferedReader br=null;
		String str=null;
		StringTokenizer strtk=null;
		double[][][] scores=new double[10][10][10];
		int a,b,c;
		a=b=c=0;
		for (int i = 0; i < scores.length; i++) {
			a=b=c=0;
			int j=i+1;
			br=new BufferedReader(new FileReader("C:\\temp\\out.csv"));
			str=br.readLine();			
			while (str!=null) {
				strtk=new StringTokenizer(str, ",");				
				scores[a][b][c]=Double.parseDouble(strtk.nextToken());
				str=br.readLine();
				c++;
				if(c==10){
					c=0;
					b++;
					if(b==10){
						b=0;
						a++;
					}
				}
			}
		}
		double min=1000;
		int jmin,kmin;
		jmin=kmin=-1;
		for (int i = 0; i < scores.length; i++) {
			for (int j = 0; j < scores[i].length; j++) {
				for (int k = 0; k < scores[i][j].length; k++) {
					if(scores[i][j][k]<min){
						min=scores[i][j][k];
						jmin=j;
						kmin=k;
					}
				}
			}
			System.out.println("For seed "+(i+1)+", mass "+jmin+", radius "+kmin);
			min=1000;
			jmin=kmin=-1;
		}
	}

}
