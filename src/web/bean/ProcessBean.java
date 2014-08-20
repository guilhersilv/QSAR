package web.bean;
/**
 * Import da classes e bibliotecas
 */

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.CartesianChartModel;

import org.primefaces.model.chart.LineChartSeries;



import web.processamento.Processamento;
import web.upload.Arquivos;





/**
 * Class de resposta pagina home.
 */
@ManagedBean(name="processingBean")
@SessionScoped
public class ProcessBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//////////

	private UploadedFile file;
	private String nomeExtensao;
	private List<String> listar = new ArrayList<String>();  //listar os aquivos enviados
	private String [] titulos;
	
	String arquivoY;
	String arquivoX;
	private int varLatentes;
    private int varRetirar;
	///////
 
    private String pre_valor; //valor selectMenu preprocessamento
    private String randomization ;  //para o selectMenu de validacao11
    private String rotuloTitulos; //titulos na tabela de validacao
    
    ////
    private int opsLatente; //valor dialogo para OPS
    private int opsModelo; //valor dialogo para OPS
    private int opsRetirar; //valor dialogo para OPS
    private int opsJanela; //valor dialogo para OPS
    private int opsIncremento; //valor dialogo para OPS
    private int opsPorcentagem; //valor dialogo para OPS
    private String opsVetor; //valor dialogo para OPS
    private String criterio; //valor dialogo para OPS
	private int opsQTA;
	private int opsAvPassos; //valor dialogo OPS Avançado
	private int leaveRepetir;
	private String leavemodelos;
	private int aleatorio;
	private int corteCorr;

	//graficos(charts) primefaces
		 private CartesianChartModel linearModel;


    /**
	 * @return the listar
	 */
	public List<String> getListar() {
		return listar;
	}
	/**
	 * @param listar the listar to set
	 */
	public void setListar(List<String> listar) {
		this.listar = listar;
	}
	

	private String lista ;

	
	
	/**
	 * Class de validacao do tipo.
	 * @throws IOException 
	 */
	public void transArquivos() throws IOException{
	
		Arquivos arqUpload = new Arquivos();
		arqUpload.setFile(file);
		nomeExtensao = getFile().getFileName();
		listar.add(nomeExtensao);
		arqUpload.transArquivos();
		listarQuatidade();
		System.out.println(listar);
		}
	/**
	 * @return the nomesArq
	 */
	
	/**
	 * @param lista the lista to set
	 */
	public List<String> getArquivoNome(){
		Arquivos arqUpload = new Arquivos();
		return new ArrayList<String>(arqUpload.getListar());

		}

	public ProcessBean(){
	
	}
	public void save() {
		addMessage("Data saved");
	}
	
	public void update() {
		
		
	}
	
	public String paginas(){
		
		return "/private/qsar";
		}
	public String pag(){
		
		return "/private/validacao";
		}
	public String ops(){
		
		return "/private/ops";
		}
	public String ops_avancado(){
		
		return "/private/ops_avancado";
		}
	
	public String leave_N_out(){
		
		return "/private/leave_N_out";
		}
	public String y_randomization(){
		
		return "/private/y_randomization";
	}
	
	public String corte_correlacao(){
		
		return "/private/corte_correlacao";
	}
	public String outliers(){
		return "/private/outliers";
	}
	//passar as menssagems 
	public void addMessage(String summary) {
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	




	/**
	 * @throws IOException 
	 * @função para ler o arquivo.
	 */		
	
public void listarQuatidade(){
	
	if(listar.size() == 1){
		 arquivoX = listar.get(0);
		//System.out.println("Nome do Arquivo X ("+ this.arquivoX +")");
	}
	if(listar.size() == 2){
		arquivoY = listar.get(1);
		//System.out.println("Nome do Arquivo Y ("+ this.arquivoY +")");
			
	}
	
	if(listar.size() > 2){
		listar.removeAll(getListar());
		listar.clear();
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Somente dois Arquivos",  null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
	 public void remover(String remover){
		 listar.remove(remover);
	 }

	 
	 public Object processX(){
			Processamento obj = new Processamento();
			
			
			return obj.MatrixArquivoX(arquivoX);
			
		
		}
	 public Object processY(){
			Processamento obj = new Processamento();
			 return obj.MatrixArquivoY(arquivoY);
		
		}
	 
	 public void listaNomesValidacao(){
			//colocar nomes na tabela na validacao
			HashMap<Integer, String> rotulo = new HashMap<Integer, String>();  
	        rotulo.put(0, new String("RMSECV"));  
	        rotulo.put(1, new String("RMSEC")); 
	        rotulo.put(2, new String("Rcv"));  
	        rotulo.put(3, new String("Rcal"));
	        rotulo.put(4, new String("Q²"));
	        rotulo.put(5, new String("R²"));	
	        rotulo.put(6, new String("PRESScv"));
	        rotulo.put(7, new String("PRESScal"));
	        rotulo.put(8, new String("Informação por VL"));
	        rotulo.put(9, new String("ycv"));
	        rotulo.put(10, new String("ycal"));
	        rotulo.put(11, new String("Vetor de Regressão Processado"));
	        rotulo.put(12, new String("Vetor de Regressão"));
	     		
	        if(randomization == null){
				
	        	 setRotuloTitulos(rotulo.get(0)); 
				}
			 else 
	        
				 setRotuloTitulos(rotulo.get(Integer.parseInt(randomization))); 
	    
	       /* for(Integer entry : rotulo.keySet()) {  
	           String p_index = rotulo.get(entry);  
	            System.out.println(p_index);  
	        } */ 
			
		}
		public void listaNomesOPS(){
			//colocar nomes na tabela na OPS
			HashMap<Integer, String> rotulo = new HashMap<Integer, String>();  
	        rotulo.put(1, new String("RMSECV"));  
	        rotulo.put(2, new String("Rcv")); 
	        rotulo.put(3, new String("Q²"));  
	        rotulo.put(4, new String("Spress"));
	        
	        if(criterio == null){
				
	        	 setRotuloTitulos(rotulo.get(0)); 
				}
			 else 
	        
				 setRotuloTitulos(rotulo.get(Integer.parseInt(criterio))); 
	    
		}
		public void listaNomesLeaveNout(){
			//colocar nomes na tabela na OPS
			HashMap<Integer, String> rotulo = new HashMap<Integer, String>();  
	        rotulo.put(1, new String("RMSECV"));  
	        rotulo.put(2, new String("Q²")); 
	       
	        
	        if(criterio == null){
				
	        	 setRotuloTitulos(rotulo.get(0)); 
				}
			 else 
	        
				 setRotuloTitulos(rotulo.get(Integer.parseInt(criterio))); 
	    
		}
	 public Object valiCruzada() throws IOException {
		
		 Processamento obj = new Processamento();
		 if(varLatentes == 0 || varRetirar == 0){
			addMessage("Insirar o valor inteiro");
			return null;
		}
		else
		 
		 listaNomesValidacao();
		
		
		 if(randomization == null){
			 obj.setParsel(0);
			
			}
		 else obj.setParsel(Integer.parseInt(randomization));
		
	 		 return  obj.ValiCruzada(Integer.parseInt(pre_valor),varLatentes,varRetirar,arquivoX,arquivoY);
		
	 }
	 
	public Object predilecao() throws NumberFormatException, IOException{
		Processamento obj = new Processamento();
		listaNomesOPS();
		obj.setVermodelos(opsQTA);
		return obj.PredilecaoOrdenar(Integer.parseInt(pre_valor), opsLatente, opsModelo, opsRetirar, opsJanela, Integer.parseInt(opsVetor), opsIncremento, opsPorcentagem, Integer.parseInt(criterio), arquivoX, arquivoY);
			
	}
	public Object predilecaoAvançado() throws NumberFormatException, IOException{
		Processamento obj = new Processamento();
		listaNomesOPS();
		obj.setVermodelos(opsQTA);
		return obj.PredilecaoOrdenarAvançado(Integer.parseInt(pre_valor), opsLatente, opsModelo, opsRetirar, opsJanela, Integer.parseInt(opsVetor), opsIncremento, opsPorcentagem, Integer.parseInt(criterio), opsAvPassos, arquivoX, arquivoY);
			
	}
	public Object leaveNout() throws NumberFormatException, IOException{
		Processamento obj = new Processamento();
		listaNomesLeaveNout();
		
		return obj.Leave_out(Integer.parseInt(pre_valor), varLatentes, varRetirar, leaveRepetir, Integer.parseInt(leavemodelos), arquivoX, arquivoY);
		
			
	}
	public Object yradomization() throws NumberFormatException, IOException{
		
		Processamento obj = new Processamento();
		
	
		return obj.Yradomization(aleatorio, varLatentes, varRetirar, Integer.parseInt(pre_valor), arquivoX, arquivoY);
		
			
	}
	public Object detecOutliers()throws NumberFormatException, IOException{
		Processamento obj = new Processamento();
		return obj.detec_Outliers(varLatentes, Integer.parseInt(pre_valor), arquivoX, arquivoY);
	}
	/**
	 * tornar a tabela editavel
	 */
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

	public Object createPieModel() throws IOException {  
		linearModel = new CartesianChartModel(); 
		 
		for(int i= 0;i<8;i++){
			Object amostra =  valiCruzada();
			 LineChartSeries val = new LineChartSeries(); 
			 val.setLabel("Validação"); 
			  val.set(amostra, i);
			
		        linearModel.addSeries(val);  
		       
		}
		
		
       
      
 


       
   
        
        return "/private/graficos";
        
	}
	/*para salvar documentos pdf
	 * 
	 * public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
	    pdf.open();
	    pdf.setPageSize(PageSize.A4);

	    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	    String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "prime_logo.png";

	    pdf.add(Image.getInstance(logo));
	}*/
	/**
	 * @return the lista
	 */
	public String getLista() {
		return lista;
	}
	/**
	 * @param lista the lista to set
	 */
	public void setLista(String lista) {
		this.lista = lista;
	}
	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	/**
	 * @return the nomeExtensao
	 */
	public String getNomeExtensao() {
		return nomeExtensao;
	}
	/**
	 * @param nomeExtensao the nomeExtensao to set
	 */
	public void setNomeExtensao(String nomeExtensao) {
		this.nomeExtensao = nomeExtensao;
	}

	/**
	 * @return the varLatentes
	 */
	public int getVarLatentes() {
		return varLatentes;
	}

	/**
	 * @param varLatentes the varLatentes to set
	 */
	public void setVarLatentes(int varLatentes) {
		this.varLatentes = varLatentes;
	}

	/**
	 * @return the varRetirar
	 */
	public int getVarRetirar() {
		return varRetirar;
	}

	/**
	 * @param varRetirar the varRetirar to set
	 */
	public void setVarRetirar(int varRetirar) {
		this.varRetirar = varRetirar;
	}

	/**
	 * @return the titulos
	 */
	public String [] getTitulos() {
		return titulos;
	}

	/**
	 * @param titulos the titulos to set
	 */
	public void setTitulos(String [] titulos) {
		this.titulos = titulos;
	}
	/**
	 * @return the pre_valor
	 */
	public String getPre_valor() {
		return pre_valor;
	}
	/**
	 * @param pre_valor the pre_valor to set
	 */
	public void setPre_valor(String pre_valor) {
		this.pre_valor = pre_valor;
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
	 * @return the rotuloTitulos
	 */
	public String getRotuloTitulos() {
		return rotuloTitulos;
	}
	/**
	 * @param rotuloTitulos the rotuloTitulos to set
	 */
	public void setRotuloTitulos(String rotuloTitulos) {
		this.rotuloTitulos = rotuloTitulos;
	}
	/**
	 * @return the opsLatente
	 */
	public int getOpsLatente() {
		return opsLatente;
	}
	/**
	 * @param opsLatente the opsLatente to set
	 */
	public void setOpsLatente(int opsLatente) {
		this.opsLatente = opsLatente;
	}
	/**
	 * @return the opsModelo
	 */
	public int getOpsModelo() {
		return opsModelo;
	}
	/**
	 * @param opsModelo the opsModelo to set
	 */
	public void setOpsModelo(int opsModelo) {
		this.opsModelo = opsModelo;
	}
	/**
	 * @return the opsRetirar
	 */
	public int getOpsRetirar() {
		return opsRetirar;
	}
	/**
	 * @param opsRetirar the opsRetirar to set
	 */
	public void setOpsRetirar(int opsRetirar) {
		this.opsRetirar = opsRetirar;
	}
	/**
	 * @return the opsJanela
	 */
	public int getOpsJanela() {
		return opsJanela;
	}
	/**
	 * @param opsJanela the opsJanela to set
	 */
	public void setOpsJanela(int opsJanela) {
		this.opsJanela = opsJanela;
	}
	/**
	 * @return the opsIncremento
	 */
	public int getOpsIncremento() {
		return opsIncremento;
	}
	/**
	 * @param opsIncremento the opsIncremento to set
	 */
	public void setOpsIncremento(int opsIncremento) {
		this.opsIncremento = opsIncremento;
	}
	/**
	 * @return the opsVetor
	 */
	public String getOpsVetor() {
		return opsVetor;
	}
	/**
	 * @param opsVetor the opsVetor to set
	 */
	public void setOpsVetor(String opsVetor) {
		this.opsVetor = opsVetor;
	}
	/**
	 * @return the criterio
	 */
	public String getCriterio() {
		return criterio;
	}
	/**
	 * @param criterio the criterio to set
	 */
	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}
	/**
	 * @return the opsPorcentagem
	 */
	public int getOpsPorcentagem() {
		return opsPorcentagem;
	}
	/**
	 * @param opsPorcentagem the opsPorcentagem to set
	 */
	public void setOpsPorcentagem(int opsPorcentagem) {
		this.opsPorcentagem = opsPorcentagem;
	}
	/**
	 * @return the opsQTA
	 */
	public int getOpsQTA() {
		return opsQTA;
	}
	/**
	 * @param opsQTA the opsQTA to set
	 */
	public void setOpsQTA(int opsQTA) {
		this.opsQTA = opsQTA;
	}
	/**
	 * @return the opsAvPassos
	 */
	public int getOpsAvPassos() {
		return opsAvPassos;
	}
	/**
	 * @param opsAvPassos the opsAvPassos to set
	 */
	public void setOpsAvPassos(int opsAvPassos) {
		this.opsAvPassos = opsAvPassos;
	}
	/**
	 * @return the leaveRepetir
	 */
	public int getLeaveRepetir() {
		return leaveRepetir;
	}
	/**
	 * @param leaveRepetir the leaveRepetir to set
	 */
	public void setLeaveRepetir(int leaveRepetir) {
		this.leaveRepetir = leaveRepetir;
	}
	/**
	 * @return the leavemodelos
	 */
	public String getLeavemodelos() {
		return leavemodelos;
	}
	/**
	 * @param leavemodelos the leavemodelos to set
	 */
	public void setLeavemodelos(String leavemodelos) {
		this.leavemodelos = leavemodelos;
	}
	/**
	 * @return the aleatorio
	 */
	public int getAleatorio() {
		return aleatorio;
	}
	/**
	 * @param aleatorio the aleatorio to set
	 */
	public void setAleatorio(int aleatorio) {
		this.aleatorio = aleatorio;
	}
	/**
	 * @return the corteCorr
	 */
	public int getCorteCorr() {
		return corteCorr;
	}
	/**
	 * @param corteCorr the corteCorr to set
	 */
	public void setCorteCorr(int corteCorr) {
		this.corteCorr = corteCorr;
	}
	
	
	/**
	 * @return the linearModel
	 */
	public CartesianChartModel getLinearModel() {
		return linearModel;
	}
	/**
	 * @param linearModel the linearModel to set
	 */
	public void setLinearModel(CartesianChartModel linearModel) {
		this.linearModel = linearModel;
	}


		


	


	


	
}
