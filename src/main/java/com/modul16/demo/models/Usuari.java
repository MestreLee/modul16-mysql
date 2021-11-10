package com.modul16.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuaris")
public class Usuari {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nom", nullable=false)
	private String nom;
	
	private int tirades;
	private int guanyades;
	private double promig;
	
	public Usuari(int id, String nom, int tirades, int guanyades, double promig) {
		super();
		this.id = id;
		this.nom = nom;
		this.tirades = tirades;
		this.guanyades = guanyades;
		this.promig = promig;
	}
	
	public int getGuanyades() {
		return guanyades;
	}

	public void setGuanyades(int guanyades) {
		this.guanyades = guanyades;
	}

	public Usuari() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getTirades() {
		return tirades;
	}
	public void setTirades(int tirades) {
		this.tirades = tirades;
	}
	public void incrTirades() {
		this.setTirades(this.getTirades() + 1);
	}
	public void incrGuanyades() {
		this.setGuanyades(this.getGuanyades() + 1);
	}
	
	public double getPromig() {
		return this.promig;
	}
	
	public void setPromig() {
		
		if (this.tirades != 0) {
			if(this.guanyades != 0) {
				this.promig =  (float)this.guanyades / (float)this.tirades * 100;
			}
		}
		
	}

	@Override
	public String toString() {
		return "Usuari [id=" + id + ", nom=" + nom + ", promig=" + promig + "]" + "\n";
	}
	
	
	
}
