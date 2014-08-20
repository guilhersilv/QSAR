package web.upload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import web.bean.ProcessBean;

@SessionScoped
public class Arquivos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3459308999593943595L;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	////////////////  
	private String destination = "C:\\temp\\";
	private UploadedFile file;
	private String nomeExtensao;
	private List<String> listar = new ArrayList<String>();  
	private String arquivoX;
	private String arquivoY;
	/////////////////////////
	
	/**
	 * @return the arquivoX
	 */
	public String getArquivoX() {
		return arquivoX;
	}


	/**
	 * @param arquivoX the arquivoX to set
	 */
	public void setArquivoX(String arquivoX) {
		this.arquivoX = arquivoX;
	}


	/**
	 * @return the arquivoY
	 */
	public String getArquivoY() {
		return arquivoY;
	}


	/**
	 * @param arquivoY the arquivoY to set
	 */
	public void setArquivoY(String arquivoY) {
		this.arquivoY = arquivoY;
	}


	public Arquivos(){
		
		
	}


	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}


	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
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


	



	/**
	 * @return 
	 * 
	 */
public void TransferFile(String fileName, InputStream in){
		
		try{
			OutputStream out = new FileOutputStream(destination + fileName);
			int reader = 0;
			byte[] bytes = new byte[(int)getFile().getSize()];
			while((reader = in.read(bytes)) != -1){
				out.write(bytes, 0, reader);
			}
			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	} 
	   
public void transArquivos() throws IOException{
	String validar;
	if(getFile()!= null){
		
		nomeExtensao = getFile().getFileName();
			
				//System.out.println(listar);
			
		
		if(nomeExtensao != null){
			validar = nomeExtensao.substring(nomeExtensao.indexOf(".")+1);
			}
		else{ validar = "null";}
		
		if(validar.equals("txt")){
			
	try {
		TransferFile(getFile().getFileName(), getFile().getInputstream());
		
	} catch (IOException e) {
		Logger.getLogger(ProcessBean.class.getName()).log(Level.SEVERE, null, e);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Sucesso", getFile().getFileName()));
		e.printStackTrace();
	}
	}else{
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("ext Incorreta"));
	}
}else{
	FacesContext context = FacesContext.getCurrentInstance();
	context.addMessage(null, new FacesMessage("Por favor Selecionar Arquivos"));
}
	
	}     
	
/**
 * @leitura de Arquivos
 */
public String arquivos(String arquivoX) throws IOException{
	
	File arquivo = new File("C:\\temp\\"+ arquivoX);
	
    StringBuilder conteudo = new StringBuilder();
    BufferedReader reader = null ;
    
    try {

        reader = new BufferedReader(new FileReader(arquivo));
        String text;
        System.out.println("Arquivo encontradomsm " +  arquivo);
        // repete enquanto existir linhas
        while ((text = reader.readLine()) != null) {
            conteudo.append(text).append(System.getProperty("line.separator"));
     
        }
        //lista1.add(conteudo.toString());
        //System.out.println(conteudo.toString());
        reader.close();
       
       
    } catch (FileNotFoundException e) {
        System.out.println("Arquivo não encontrado!");
    } catch (IOException e) {
        System.out.println("Falha ao tentar ler arquivo! "+e.getMessage());
    }
   return conteudo.toString();
	
    
}  
public String arquivos2(String arquivoY) throws IOException{
 

 	File arquivo2 = new File("C:\\temp\\"+ arquivoY);

    StringBuilder conteudo = new StringBuilder();
    BufferedReader reader2 = null ;
    
    try {

        reader2 = new BufferedReader(new FileReader(arquivo2));
        String text2;
        System.out.println("Arquivo encontrado = sim " +  arquivo2);
        // repete enquanto existir linhas
        while ((text2 = reader2.readLine()) != null) {
            conteudo.append(text2).append(System.getProperty("line.separator"));
            
        }
        //lista2.add(conteudo.toString());
        //System.out.println(conteudo.toString());
        reader2.close();
       
       
    } catch (FileNotFoundException e) {
        System.out.println("Arquivo não encontrado!");
    } catch (IOException e) {
        System.out.println("Falha ao tentar ler arquivo! "+e.getMessage());
    }
   //return lista2.toString();
    return conteudo.toString();
    
}  
 
}	  


