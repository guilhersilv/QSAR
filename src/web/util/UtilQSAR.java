package web.util;

import pacoteOPS.TrataMatriz;
import Jama.Matrix;

public class UtilQSAR {
	
	public static double JANSIZE = 4;
	public static Matrix cdda (Matrix x, Matrix y, double cut, Integer cdda_cut){
		
		Matrix x2 = TrataMatriz.autoescala(x);
		Matrix y2 = TrataMatriz.autoescala(y);
//		double [] c = new double[];
//		if(c<cut){
//			x = x.getMatrix(1, x.getRowDimension(), 1, x.getColumnDimension());			
//		}
//		C = abs(corr(X2,y2));
//		Math.abs(TrataMatriz.correlograma(x2, y2));
//		names(:,C < cut)=[];
		
		//Distribution cut-off
		Integer m = x.getRowDimension();
		Integer n = x.getColumnDimension();
		y2 = normalization(y);
		
		Matrix xn = x;
	    xn = normalization(x);
	    
	    int cX2 = x2.getColumnDimension();
	    int lX2 = x2.getRowDimension();
	    int cY2 = y2.getColumnDimension();
	    int lY2 = y2.getRowDimension();
	    double[][] matrizXn = xn.getArray();
	    
	    //	 X2=[y2 Xn]; linha 26
	    for (int j = cY2; j < cX2; j++)
	    {
	      for (int i = lY2; i < lX2; i++) {
	        y2.set(i, j, matrizXn[i][j]);
	      }	      
	    }
	    
	    //F = ones(m,2^jansize,n+1);
	    double[][] matrizY2 = y2.getArray();
	    double jan = 1/16;
	    double[][][] f = new double[m][16][n+1];
		  for (int z = 0; z < (n+1); z++){	
	    	for (int j = 0; j < 16; j++)
		    {
		      for (int i = 0; i < m; i++) {
		    	  double g = 0;
		    	  if(z == 0){
		    		  g = 1;
		    	  }else{
		    		  g=z;
		    	  }
		    	  if((matrizY2[i][j] >= ((g-1)*jan)) && (matrizY2[i][j]< (g*jan)) || ((matrizY2[i][j]==(g*jan)) && (matrizY2[i][j]==1))){
		    		  f[i][j][z] = 1;  
		    	  }else{
		    		  f[i][j][z] = 0;
		    	  }
		      }	      
		    }
		}
		//Error comparasions
		double[][] scoreForw = ones(m,16);
		double[][] scoreBack = ones(m,16);
		double[][] scoreOk = ones(m,16);
		
		//Best = (sum(F(:,:,1)));
		double[] best = new double[n+1];
		for (int j = 0; j < (n+1); j++)
	    {
			double soma = 0;
	    	for (int i = 0; i < m; i++) {
	    		soma += f[i][j][1];
	    	}
	    	best[j] = soma;
	    }
				
		for (int j = 0; j < (n+1); j++)
		{
			int fliplr = n+1;
			for (int i = 0; i < m; i++) {
				double[] somaF = new double [n+1];
				for(int k = 0 ; k < (n+1); k++){
					double somaParcial = 0; 
					for(int w = 0; w < m; w++){
						somaParcial += f[w][k][i];
					}
					somaF[k] = somaParcial;
				}
				scoreForw [i][j] = somaF[i] - best[i];
				scoreBack [i][j] = somaF[fliplr] - best[i];
				fliplr --;	

			}			
		}
		
		for (int j = 0; j < n; j++)
		{
			double somaScoreBack = 0;
			double somaScoreForw = 0;
			for (int i = 0; i < m; i++) {
				somaScoreBack +=scoreBack[i][j];
				somaScoreForw +=scoreForw[i][j];
			}
			if(somaScoreForw < somaScoreBack){
				for (int i = 0; i < m; i++) {
					scoreOk[i][j] = scoreForw[i][j];
					
				}
			}else{
				for (int i = 0; i < m; i++) {
					scoreOk[i][j] = scoreBack[i][j];					
				}
			}			
		}
		
		double[] param = new double[n];
		for(int j = 0; j< m ; j++){
			
			double somaOk = 0;
			for(int i = 0 ; i<n; i++){
				somaOk += scoreOk[i][2];
			}
			param[j] = (1- somaOk/(2*(m-2)));
		}
//		Ending Step
		

	      
		return null;
	}
	
	/**
	 * Cria uma matriz n por n de 1's
	 * @param n - tamanho da matriz
	 * @return matrizUm - a matriz n por n
	 */
	public static double[][] ones (int n){
		
		double[][] matrizUm = new double[n][n];
		
		for (int j = 0; j < n; j++)
	    {
	      for (int i = 0; i < n; i++) {
	        
	    	  matrizUm[i][j] = 1;
	        
	      }	      
	    }
		return matrizUm;
	}
	/**
	 * Cria uma matriz n por n de 1's
	 * @param n - tamanho da matriz
	 * @return matrizUm - a matriz n por n
	 */
	public static double[][] ones (int n, int m){
		
		double[][] matrizUm = new double[n][n];
		
		for (int j = 0; j < m; j++)
		{
			for (int i = 0; i < n; i++) {
				
				matrizUm[i][j] = 1;
				
			}	      
		}
		return matrizUm;
	}
	
	public static double obterMaiorValorMatriz(Matrix A){
		int n = A.getColumnDimension();
	    int m = A.getRowDimension();
	    double[][] X = A.getArray();
	    double max = X[0][0];
	    for (int j = 0; j < n; j++)
	    {
	      for (int i = 0; i < m; i++) {
	        if (X[i][j] > max)
	        {
	        	max = X[i][j];	       
	        }
	      }	      
	    }
		return max;
	}
	
	public static double obterMenorValorMatriz(Matrix A){
		int n = A.getColumnDimension();
	    int m = A.getRowDimension();
	    
	    double[][] X = A.getArray();
	    double min = X[0][0];
	    for (int j = 0; j < n; j++)
	    {
	      for (int i = 0; i < m; i++) {
	        if (X[i][j] < min)
	        {
	        	min = (int)X[i][j];	       
	        }
	      }	      
	    }
		return min;
	}
	
	public static Matrix normalization(Matrix A){
		double xmin = obterMenorValorMatriz(A);
		double xmax = obterMaiorValorMatriz(A);
		int n = A.getColumnDimension();
	    int m = A.getRowDimension();
		double[][] X = A.getArray();
		
		if(xmin == xmax){
			//xnorm = nan(size(x));			
		}else{
		  for (int j = 0; j < n; j++)
		    {
		      for (int i = 0; i < m; i++) {
		    	  //xnorm = (x-xmin)./(xmax-xmin);
		    	  double k = (X[i][j] - xmin)/(xmax - xmin);
		    	  A.set(i, j, k);
		      }	      
		    }
		}

		return A;
	}
}
