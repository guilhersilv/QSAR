//Autor: Swylmar
/**
 * 
 */
package web.processamento;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.chart.CartesianChartModel;

import org.primefaces.model.chart.LineChartSeries;

import pacoteOPS.OPS;
import pacoteOPS.OPSAutomatico;
import pacoteOPS.Outliers;
import pacoteOPS.TrataMatriz;
import pacoteOPS.Val_cruzada;
import pacoteOPS.Validacoes;
import Jama.Matrix;

import com.sun.xml.internal.ws.developer.Stateful;

/**
 * @author Jose
 * classe processemento dos dados 
 */
@Stateful
public class Processamento {
	
	private Matrix X;
	private Matrix y;
	private Matrix resultadosval;
	private Val_cruzada vc;
    private OPS ops;
    private OPSAutomatico opsaut;
    
    private String[] titulos; //uso para carregar os titulos
    
	int Mlin= 0; int NCol = 0;

	List<Double> coluna;
	List<ArrayList<Double>> matriz = new ArrayList<ArrayList<Double>>();
	List<Double> linha;
	
	double[][] parametro; //valores do calculo da validacao
	private int parsel = 0;	//passar o valor dentro do switch
	
	private int[] modelos;//modelos de ops
	private int vermodelos;
	double[][] opsMatriz;
	double[][] resultadosOutliers;
	
	
	
	/**
	 * @return the resultadosOutliers
	 */
	public double[][] getResultadosOutliers() {
		return resultadosOutliers;
	}
	/**
	 * @param resultadosOutliers the resultadosOutliers to set
	 */
	public void setResultadosOutliers(double[][] resultadosOutliers) {
		this.resultadosOutliers = resultadosOutliers;
	}
	/**
	 * @return the parsel
	 */
	public int getParsel() {
		return parsel;
	}
	/**
	 * @param parsel the parsel to set
	 */
	public void setParsel(int parsel) {
		this.parsel = parsel;
	}

	private String randomization;  //para o selectMenu de validacao
	public Processamento(){
	
	}
/*
 * classe para leitura de arquivo com pacote  pacoteOPS.OPS;
 */
	public Object MatrixArquivoX(String arquivoX){
		      
		File yourFile = new File("C:\\temp\\" + arquivoX);
		String suchFile = yourFile.getAbsolutePath();

	     //  verifica se arquivo existe.
	     if (yourFile.exists())
	     {
	     	try {
	     		//TrataMatriz do pacote OPS e coloca na matriz
				X = TrataMatriz.lerarquivo(suchFile);
				 titulos = TrataMatriz.titulos(suchFile);
			} catch (FileNotFoundException e) {
				System.out.println("Arquivo não encontrado!" +e.getMessage());
				e.printStackTrace();
			 } 
	     	 System.out.println(titulos);
	           Mlin = X.getRowDimension();// Retorna o número de linhas na matriz.
	           NCol = X.getColumnDimension();//Retorna o número de colunas na matriz
	           
		  		/*for (int i = 0; i < Mlin; i++) {
		    	 linha = new ArrayList<Double>();
		    	 for(int j= 0; j< NCol; j++){0
		    		 //Adiciona os valores na lista de Object array
		    		 linha.add(X.get(i, j));  
		    	 }
		    	 matriz.add((ArrayList<Double>) linha);
		     }
		  			*/
	           
	           for (int j = 0; j < NCol; j++)
	           {
			    	 coluna = new ArrayList<Double>();
			    	 for(int i= 0; i< Mlin; i++)
			    	   
			    	 {
			    		 //Adiciona os valores na lista de Object array
			    		 coluna.add(X.get(i, j)); 
			    		
			    	 }
			    	 matriz.add((ArrayList<Double>) coluna);
			     }
		  		
		  		}
	
	  
	 return matriz;
	 }
	
	
	public Object MatrixArquivoY(String arquivoY){
		File yourFiley = new File("C:\\temp\\" + arquivoY);
	    String suchFiley = yourFiley.getAbsolutePath();

	    
	     if (yourFiley.exists())
	     {
	     	try {
				y = TrataMatriz.lerarquivo(suchFiley);
				 titulos = TrataMatriz.titulos(suchFiley);
			} catch (FileNotFoundException e) {
				System.out.println("Arquivo não encontrado!" +e.getMessage());
				e.printStackTrace();
			}
	     	
	           Mlin = y.getRowDimension();// Retorna o número de linhas na matriz.
	           NCol = y.getColumnDimension();//Retorna o número de colunas na matriz
	           int colunas[] = new int[NCol];
	           for (int i=0;i<colunas.length;i++)
	               colunas[i] = i+1;   
	           TrataMatriz.salvarcomtitulos(arquivoY, y, colunas, titulos);
	           for(int i= 0; i< Mlin; i++)
	           {
			    	 coluna = new ArrayList<Double>();
			    	 for (int j = 0; j < NCol; j++)
			    	 {
			    		 //Adiciona os valores na lista de Object array
			    		 coluna.add(y.get(i, j));  
			    	 }
			    	 matriz.add((ArrayList<Double>) coluna);
			     }
	           

	     }
	     return matriz;
	}


// num e pre_valor
	public Object ValiCruzada(Integer num, int varLatentes, int varRetirar,String arquivoX, String arquivoY) throws IOException{
		
		File yourFiley = new File("C:\\temp\\" + arquivoY);
		String suchFiley = yourFiley.getAbsolutePath();
		
		if(varLatentes == 0 ||varRetirar == 0){
			return null;
		}
		else
		
	  
	    y = TrataMatriz.lerarquivo(suchFiley);
	    File yourFile = new File("C:\\temp\\" + arquivoX);
		String suchFile = yourFile.getAbsolutePath();
		X = TrataMatriz.lerarquivo(suchFile);
	
		vc = new Val_cruzada(X, y, varLatentes, num, varRetirar);
		
		
		
	switch (parsel) {
	   
		case 0: //RMSECV
	         
			parametro = vc.getRmsecv().getArray();
	        
	  			linha = new ArrayList<Double>();
	  			for(int i = 0;i< parametro.length; i++){
	  				
				  linha.add(parametro[i][0]);
				
		
				 // System.out.println("contador!" +numAmostra);
			
	  			}
	  			
	  			break;
	  			
	    case 1: //RMSEC
	        parametro = vc.getRmsec().getArray();
	       
	        linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
				
				 linha.add(parametro[i][0]);
		
			 }
			
	        break;
	   
	    case 2: //Rcv
            parametro = vc.getRcv().getArray();
          
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
				 linha.add(parametro[i][0]);
				 }
         
            break;
	   
	    case 3: //Rcal
            parametro = vc.getRcal().getArray();
           
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
								 
				 linha.add(parametro[i][0]);
				 }
            break;
            
        case 4: //Q2
            parametro = vc.getQ2().getArray();
           
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
								 
				 linha.add(parametro[i][0]);
						 
			 }
            break;
            
        case 5: //R2
            parametro = vc.getR2().getArray();
           
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
				
				 linha.add(parametro[i][0]);
		
			 }
           
            break;
            
        case 6: //Presscv
            parametro = vc.getPresscv().getArray();
           
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
				
				 linha.add(parametro[i][0]);
		
			 }
            break;
            
        case 7: //Presscal
            parametro = vc.getPresscal().getArray();
           
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
				
				 linha.add(parametro[i][0]);
			 }
            break;
            
        case 8: //Informação
            parametro = vc.getInf().getArray();
           
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
				
				 linha.add(parametro[i][0]);
		
			 }
		      break;
		      
        case 9: //Ycv
        	parametro = vc.getYcv().getArray();
        	
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
					 
				 linha.add(parametro[i][0]);
		
				 System.out.println(parametro[i][0]);
			 }
            break;
            
        case 10: //Ycal
            //parametro = new double[2][y.getRowDimension()];
        	parametro = vc.getYcal().getArray();
            
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
				
				 
				 linha.add(parametro[i][0]);
		
				 System.out.println(parametro[i][0]);
			 }
            break;
            
        case 11: //Vetor de regressao processado
            parametro = new double[2][y.getRowDimension()];
        	parametro = vc.getB().getArray();
           
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
				
				 
				 linha.add(parametro[i][0]);
		
				 System.out.println(parametro[i][0]);
			 }
            break;
           
        case 12: //Vetor de regressao
            parametro = new double[2][y.getRowDimension()];
        	parametro = vc.getBreal().getArray();
          
            linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
				
				 
				 linha.add(parametro[i][0]);
		
				 System.out.println(parametro[i][0]);
			 }
            break;
	    default:
	        parametro = new double[2][y.getRowDimension()];
	        linha = new ArrayList<Double>();
			 for(int i = 0;i< parametro.length; i++){
				
				 
				 linha.add(parametro[i][0]);
		
				 System.out.println(parametro[i][0]);
			 }
	     
	        break;
	}

		
		
			 return linha;
			}  

public Object PredilecaoOrdenar(Integer pre_valor, int opsLatente, int opsModelo, int opsRetirar, int opsJanela, Integer opsVetor,
		int opsIncremento,int porcentagem, Integer criterio, String arquivoX, String arquivoY) throws IOException{
		
		
		
	  	File yourFile = new File("C:\\temp\\" + arquivoX);
		String suchFile = yourFile.getAbsolutePath();
		X = TrataMatriz.lerarquivo(suchFile);
		
		File yourFiley = new File("C:\\temp\\" + arquivoY);
	    String suchFiley = yourFiley.getAbsolutePath();
	    y = TrataMatriz.lerarquivo(suchFiley);
	    
	  
	  
	    opsMatriz = new double[vermodelos][3];
		ops = new OPS(X, y, opsLatente, opsModelo, pre_valor, opsRetirar, opsVetor, opsJanela, opsIncremento,
						porcentagem, criterio);
		
	     modelos = new int[ops.getMelhoresModelos(criterio).length];
	     modelos = ops.getMelhoresModelos(criterio);
	
        for (int i=0; i < vermodelos; i++)
        {
        	 opsMatriz[i][0] = ops.getParametros()[modelos[i]];
        	 opsMatriz[i][1] = ops.getNvar()[modelos[i]];
        	 opsMatriz[i][2] = ops.getVLs()[modelos[i]];
        	//linha.add(ops.getParametros()[modelos[i]]);
            System.out.println("matriz getParametros"+  opsMatriz[i][0]);
        	System.out.println("matriz getNvar"+  opsMatriz[i][1]);
        	System.out.println("matriz getVLs()"+  opsMatriz[i][2]);
        
        	
        }
		
		return  opsMatriz;
}
public Object PredilecaoOrdenarAvançado(Integer pre_valor, int opsLatente, int opsModelo, int opsRetirar, int opsJanela, Integer opsVetor,
		int opsIncremento,int porcentagem, Integer criterio, int opsAvPassos, String arquivoX, String arquivoY) throws IOException{
		
		
		
	  	File yourFile = new File("C:\\temp\\" + arquivoX);
		String suchFile = yourFile.getAbsolutePath();
		X = TrataMatriz.lerarquivo(suchFile);
		
		File yourFiley = new File("C:\\temp\\" + arquivoY);
	    String suchFiley = yourFiley.getAbsolutePath();
	    y = TrataMatriz.lerarquivo(suchFiley);
	    
	  
	  
	    opsMatriz = new double[vermodelos][5];
		
		opsaut = new OPSAutomatico(X, y, opsLatente,  opsModelo, pre_valor, opsRetirar, opsVetor, opsJanela, opsIncremento, porcentagem, criterio, opsAvPassos);
	
	     
		modelos = new int[opsaut.getMelhoresModelos(criterio).length];
        modelos = opsaut.getMelhoresModelos(criterio);
	
        for (int i=0; i < vermodelos; i++)
        {
        	opsMatriz[i][0] = opsaut.getParametro().get(modelos[i]);
        	opsMatriz[i][1] = opsaut.getNvar().get(modelos[i]);
        	opsMatriz[i][2] = opsaut.getVLMod().get(modelos[i]);
        	opsMatriz[i][3] = opsaut.getVLOPS().get(modelos[i]);
        	opsMatriz[i][4] = opsaut.getVetor().get(modelos[i]);
        	//linha.add(ops.getParametros()[modelos[i]]);
            System.out.println("matriz opsaut.getParametro"+  opsMatriz[i][0]);
        	System.out.println("matriz opsaut.getNvar"+  opsMatriz[i][1]);
        	System.out.println("matriz opsaut.getVLMod()"+  opsMatriz[i][2]);
        	System.out.println("matriz opsaut.getVLOPS())"+  opsMatriz[i][3]);
        	System.out.println("matriz opsaut.getVetor()"+  opsMatriz[i][4]);
        
        	
        }
		
		return  opsMatriz;
}
public Object Leave_out(Integer pre_valor, int vLlatente ,int maxRetirar, int leaveRepetir, Integer leavemodelos, String arquivoX, String arquivoY) throws IOException{
		
		
		
	  	File yourFile = new File("C:\\temp\\" + arquivoX);
		String suchFile = yourFile.getAbsolutePath();
		X = TrataMatriz.lerarquivo(suchFile);
		
		File yourFiley = new File("C:\\temp\\" + arquivoY);
	    String suchFiley = yourFiley.getAbsolutePath();
	    y = TrataMatriz.lerarquivo(suchFiley);
	    
	    Matrix Xv;
	    Xv = X;
	    Xv = opsaut.getXsel(X, y, pre_valor, opsaut.getNvar().get(modelos[leavemodelos-1]), opsaut.getVLOPS().get(modelos[leavemodelos-1]), opsaut.getVetor().get(modelos[leavemodelos-1]));
	    resultadosval = Validacoes.leaveNOut(Xv, y, vLlatente, pre_valor, maxRetirar, leaveRepetir, 2);
	   
	    System.out.println("Leave_out " + resultadosval);
	    return resultadosval.getArray();
}
public Object Yradomization(int aleatorio, int varLatentes, int varRetirar, Integer pre_valor, String arquivoX, String arquivoY) throws IOException{
	
		File yourFile = new File("C:\\temp\\" + arquivoX);
		String suchFile = yourFile.getAbsolutePath();
		X = TrataMatriz.lerarquivo(suchFile);
		
		File yourFiley = new File("C:\\temp\\" + arquivoY);
	    String suchFiley = yourFiley.getAbsolutePath();
	    y = TrataMatriz.lerarquivo(suchFiley);
	    
	    Matrix Xv;
	    Xv = X;
	   // Xv = ops.getXsel(ops.getNvar()[modelos[aleatorio-1]]);
	    Xv = opsaut.getXsel(X, y, pre_valor, opsaut.getNvar().get(modelos[aleatorio-1]), opsaut.getVLOPS().get(modelos[aleatorio-1]), opsaut.getVetor().get(modelos[aleatorio-1]));
	    
	    resultadosval = Validacoes.yrandomization(Xv, y, varLatentes, pre_valor, varRetirar, aleatorio);
		
	    return  resultadosval.getArray();
}
public Object detec_Outliers(int nVLOutliers, Integer pre_valor, String arquivoX, String arquivoY) throws IOException{
	
	File yourFile = new File("C:\\temp\\" + arquivoX);
	String suchFile = yourFile.getAbsolutePath();
	X = TrataMatriz.lerarquivo(suchFile);
	
	File yourFiley = new File("C:\\temp\\" + arquivoY);
    String suchFiley = yourFiley.getAbsolutePath();
    y = TrataMatriz.lerarquivo(suchFiley);
    
	double[] leverage = new double[X.getRowDimension()];
    double[] rstd = new double[X.getRowDimension()];
    
try{
	  
      Outliers outliers = new Outliers(X,y,nVLOutliers,pre_valor);
      leverage = outliers.getLeverage();
      rstd = outliers.getRstd();
      resultadosOutliers = new double[rstd.length][2];
      for (int i=0;i<rstd.length;i++) {
          resultadosOutliers[i][0] = leverage[i];
          resultadosOutliers[i][1] = rstd[i];
          //linha.add(resultadosOutliers[i][0]  = leverage[i]);
      }
	
}catch (ArrayIndexOutOfBoundsException  arrayIndexOutOfBoundsException )
{
	System.out.println("Erro ao tentar acessar elemento de uma matriz!" );
	
}
	return resultadosOutliers;
}

/**
 * funcao graficos
 */
public void charts(Object grafico,  CartesianChartModel linearModel) {  
	
    linearModel = new CartesianChartModel();  

    LineChartSeries series1 = new LineChartSeries();  
    series1.setLabel("Series 1");  
    series1.setMarkerStyle("diamond");  
   
    series1.set(grafico, 10);
    
    linearModel.addSeries(series1);  

 
}  
	/**
	 * @return the randomization
	 */
	public String getRandomization() {
		return randomization;
	}
	/**
	 * @param randomization the randomization to set
	 */
	public void setRandomization(String randomization) {
		this.randomization = randomization;
	}
	/**
	 * @return the x
	 */
	public Matrix getX() {
		return X;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(Matrix x) {
		X = x;
	}
	/**
	 * @return the y
	 */
	public Matrix getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(Matrix y) {
		this.y = y;
	}

	
	/**
	 * @return the vc
	 */
	public Val_cruzada getVc() {
		return vc;
	}
	/**
	 * @param vc the vc to set
	 */
	public void setVc(Val_cruzada vc) {
		this.vc = vc;
	}
	/**
	 * @return the ops
	 */
	public OPS getOps() {
		return ops;
	}

	/**
	 * @param ops the ops to set
	 */
	public void setOps(OPS ops) {
		this.ops = ops;
	}
	/**
	 * @return the opsaut
	 */
	public OPSAutomatico getOpsaut() {
		return opsaut;
	}
	/**
	 * @param opsaut the opsaut to set
	 */
	public void setOpsaut(OPSAutomatico opsaut) {
		this.opsaut = opsaut;
	}
	
	
	/**
	 * @return the titulos
	 */
	public String[] getTitulos() {
		return titulos;
	}
	/**
	 * @param titulos the titulos to set
	 */
	public void setTitulos(String[] titulos) {
		this.titulos = titulos;
	}
	/**
	 * @return the modelos
	 */
	public int[] getModelos() {
		return modelos;
	}
	/**
	 * @param modelos the modelos to set
	 */
	public void setModelos(int[] modelos) {
		this.modelos = modelos;
	}
	/**
	 * @return the vermodelos
	 */
	public int getVermodelos() {
		return vermodelos;
	}
	/**
	 * @param vermodelos the vermodelos to set
	 */
	public void setVermodelos(int vermodelos) {
		this.vermodelos = vermodelos;
	}
	/**
	 * @return the resultadosval
	 */
	public Matrix getResultadosval() {
		return resultadosval;
	}
	/**
	 * @param resultadosval the resultadosval to set
	 */
	public void setResultadosval(Matrix resultadosval) {
		this.resultadosval = resultadosval;
	}

	
	
	


}
