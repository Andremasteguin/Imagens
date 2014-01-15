package br.com.caelum.imagens.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;

import br.com.caelum.imagens.Dao.DAO;
import br.com.caelum.imagens.modelo.Foto;
import br.com.caelum.imagens.modelo.Produto;

@SessionScoped
@ManagedBean
public class ProdutoBean implements Serializable {
	private List<Produto> produtos;
	private Produto produto = new Produto();
	private Produto produtoSelecionado = new Produto();
	
	private List<Foto> fotos;
	private Foto foto = new Foto();
	
	public void salvaProduto(){
		DAO<Produto> dao = new DAO<Produto>(Produto.class);
		dao.adiciona(produto);
		FacesContext.getCurrentInstance().addMessage(
				null,new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Produto adicionado","Produto adicionado"));
		
	}
	public void salvaFoto(){
		DAO<Foto> dao = new DAO<Foto>(Foto.class);
		dao.adiciona(foto);
		FacesContext.getCurrentInstance().addMessage(
				null, new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Foto adicionada","Foto adicionada"));
	}
	public void processFileUpload(FileUploadEvent uploadEvent){//Método que será chamado pela view e será responsável pelo que deve ser feito com o 
		//arquivo, neste caso estamos definindo de qual produto é a foto.
		foto.setProduto(produtoSelecionado);
		foto.setImagem(uploadEvent.getFile().getContents());
		
	}
	public void listaFotosProduto(){//Método que será responsável por gerar a imagem para ser exibida na tela.
		DAO<Foto> dao = new DAO<Foto>(Foto.class);
		dao.buscaPorId(produtoSelecionado.getId());
		ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		File folder = new File(context.getRealPath("/temp"));
		if (!folder.exists()){
			folder.mkdir();
			
		for(Foto foto : fotos){
			String nomeArquivo = foto.getId() + ".jpeg";
			String arquivo = context.getRealPath("/temp") + File.separator + nomeArquivo;
			
			criaArquivo(foto.getImagem(),arquivo);
		}
		}
	}
	public void criaArquivo(byte[] bytes,String arquivo){// Método responsável por criar uma pasta temporária no 
		//servidor para guardar as imagens para serem exibidas na tela.

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			
			fos.flush();
			fos.close();
		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}catch (IOException ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		
	}
	public List<Produto> getProdutos() {
		return produtos;
	}
	public Produto getProduto() {
		return produto;
	}
	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}
	public List<Foto> getFotos() {
		return fotos;
	}
	public Foto getFoto() {
		return foto;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}
	public void setFoto(Foto foto) {
		this.foto = foto;
	}
	

}
