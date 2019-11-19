package br.com.alunosJSF;

public class EdicaoPessoa {
	private String id;
	private long idBanco;
	private String nome;
	private String email;
	private String cpf;
	private String dataNascimento;
	private String telefone;
	private String telefone2;
	private Endereco endereco = new Endereco();
	
	public EdicaoPessoa() {
		
	}

	public EdicaoPessoa(String id, long idBanco, String nome, String email, String cpf, String dataNascimento, String telefone,
			String telefone2, String rua, String numero, String bairro, String cep, String complemento, String cidade,
			String estado) {
		this.id = id;
		this.idBanco = idBanco;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.telefone2 = telefone2;
		this.endereco.setRua(rua);
		this.setNumero(numero);
		this.setBairro(bairro);
		this.setCep(cep);
		this.setComplemento(complemento);
		this.setCidade(cidade);
		this.setEstado(estado);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public long getIdBanco() {
		return idBanco;
	}
	
	public void setIdbanco(long idBanco) {
		this.idBanco = idBanco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getRua() {
		return endereco.getRua();
	}

	public void setRua(String rua) {
		this.endereco.setRua(rua);
	}

	public String getNumero() {
		return endereco.getNumero();
	}

	public void setNumero(String numero) {
		this.endereco.setNumero(numero);
	}

	public String getBairro() {
		return endereco.getBairro();
	}

	public void setBairro(String bairro) {
		this.endereco.setBairro(bairro);
	}

	public String getCep() {
		return endereco.getCep();
	}

	public void setCep(String cep) {
		this.endereco.setCep(cep);
	}

	public String getComplemento() {
		return endereco.getComplemento();
	}

	public void setComplemento(String complemento) {
		this.endereco.setComplemento(complemento);
	}

	public String getCidade() {
		return endereco.getCidade();
	}

	public void setCidade(String cidade) {
		this.endereco.setCidade(cidade);
	}

	public String getEstado() {
		return endereco.getEstado();
	}

	public void setEstado(String estado) {
		this.endereco.setEstado(estado);
	}
}
