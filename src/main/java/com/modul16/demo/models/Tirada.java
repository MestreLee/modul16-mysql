package com.modul16.demo.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tirades")
public class Tirada {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int valor1;
	private int valor2;
	private int valortotal;
	private boolean victoria;
	
	
	@Column(name="usuariid", nullable=false, length=30)
	private int usuariid;
	
	
	public Tirada(int id, int valor1, int valor2, int valortotal, boolean victoria, int usuariid) {
		super();
		this.id = id;
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.valortotal = valortotal;
		//this.dataentrada = new Timestamp(System.currentTimeMillis());
		this.victoria = victoria;
		this.usuariid = usuariid;
	}
	
	public Tirada() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValor1() {
		return valor1;
	}

	public void setValor1(int valor1) {
		this.valor1 = valor1;
	}

	public int getValor2() {
		return valor2;
	}

	public void setValor2(int valor2) {
		this.valor2 = valor2;
	}

	public int getValortotal() {
		return valortotal;
	}

	public void setValortotal(int valortotal) {
		this.valortotal = valortotal;
	}

	public boolean isVictoria() {
		return victoria;
	}

	public void setVictoria(boolean victoria) {
		this.victoria = victoria;
	}

	public int getUsuariid() {
		return usuariid;
	}

	public void setUsuariid(int usuariid) {
		this.usuariid = usuariid;
	}
	
}
