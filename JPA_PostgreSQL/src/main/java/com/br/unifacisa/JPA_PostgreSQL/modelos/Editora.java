package com.br.unifacisa.JPA_PostgreSQL.modelos;

import javax.persistence.*;

@Entity
@Table(name = "editora")

public class Editora {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "nome_editora",  nullable = false)
	private String nome;
	
	public Editora(){
		
	}
	
	public Editora(String nome){
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
