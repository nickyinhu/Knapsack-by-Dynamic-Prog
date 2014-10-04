package DynamicKnapsack;

import java.io.*;
import java.util.*;

public class DynamicKnapsack {
	private static int[] values;
	private static int[] weights;
	private static int weightL;
	private static int n;
	public static void main(String[] args) throws Exception{
		String inputname = "dynamicknapsack_hyin_input_001.txt";
		FileIO(inputname);
	}
	public static void FileIO(String inputname) throws Exception{
		File input = new File(inputname);
		String outputname = inputname.replace("input", "output");
		Scanner scan = new Scanner(input); 
		PrintWriter output = new PrintWriter(outputname);
		String firstline = scan.nextLine();
		String[] set = firstline.split("\\s+");
		n = Integer.parseInt(set[0]);
		weightL = Integer.parseInt(set[1]);
		String secondline = scan.nextLine();
		String[] w = secondline.split("\\s+");
		weights = new int[n];
		for(int i =0;i<n;i++){
			weights[i] = Integer.parseInt(w[i]);
		}
		values = new int[n];
		String thirdline = scan.nextLine();
		String[]v = thirdline.split("\\s+");
		for(int i =0;i<n;i++){
			values[i] = Integer.parseInt(v[i]);
		}
		scan.close();
		System.out.println("Resulting table:");
		int[][] result = Knapsack();
		printC(result,output);
		System.out.println("Maximum capacity: "+weightL);
		Set<Integer> solution = retrieveData(result);
		if(!solution.isEmpty()){
			System.out.println("Optimal knapsack: "+solution);
			output.println("Optimal knapsack: "+solution);
			ArrayList<Integer> solutionValue = new ArrayList<Integer>();
			for(int indexS:solution){
				solutionValue.add(values[indexS-1]);
			}
			System.out.println("Optimal values: "+solutionValue);
			output.println("Optimal values: "+solutionValue);
			ArrayList<Integer> solutionWeight = new ArrayList<Integer>();
			for(int indexS:solution){
				solutionWeight.add(weights[indexS-1]);
			}
			System.out.println("Optimal weights: "+solutionWeight);
			output.println("Optimal weights: "+solutionWeight);
			int totalvalues = 0;
			for(int value:solutionValue){
				totalvalues+=value;
			}
			System.out.println("Optimal value taken: "+totalvalues);
			output.println("Optimal value taken: "+totalvalues);	
			int totalweight = 0;
			for(int weight:solutionWeight){
				totalweight+=weight;
			}
			System.out.println("Optimal weight taken: "+totalweight);
			output.println("Optimal weight taken: "+totalweight);
		}else{
			System.out.println("No items fit into the knapsack.");
			output.println("No items fit into the knapsack.");
		}
	}	
	public static int[][] Knapsack(){
		int[][] result = new int[n+1][weightL+1];
		for(int i =0; i<n+1;i++){
			for(int j=0;j<weightL+1;j++){
				if(i == 0 || j == 0){
					result[i][j] =0;
					continue;
				}
				if(j-weights[i-1]>=0){
					result[i][j] = Math.max(result[i-1][j], (values[i-1]+result[i-1][j-weights[i-1]]));
				}else{
					result[i][j] = result[i-1][j];
				}
			}
		}
		return result;
	}
    public static void printC(int[][] a, PrintWriter output) throws Exception{
        for (int i = 0; i < n+1; i++) {
        	System.out.print("|");
        	 output.print("|");
            for (int j = 0; j < weightL+1; j++) {
            	System.out.print(a[i][j]+"\t");
            	output.print(a[i][j]+"\t");
            }            
            System.out.println("|");
            output.println("|");
    	}
    }
    public static Set<Integer> retrieveData(int[][] result){
    	Set<Integer> subset = new HashSet<Integer>();
    	int i = n;
    	int j = weightL;
    	while(i>=1 && j>=1 && result[i][j]!=0){
    		while(result[i][j] == result[i-1][j]){
    			i--;
    		}
    		subset.add(i);
    		j=j-weights[i-1];
    		i--;
    	}
    	return subset;
    }
}
