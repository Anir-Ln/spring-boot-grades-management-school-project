package com.gsnotes.bo;



import javax.persistence.*;

import java.util.List;


/**
 * Represente un enseignant.
 * 
 * Un enseignant est un cas spéciale de l'Utilisateur
 * 
 * @author T. BOUDAA
 *
 */


@Entity
@Table(name="enseignant")
@PrimaryKeyJoinColumn(name = "idEnseignant")
public class Enseignant extends Utilisateur {


	
	private String specialite;

	@OneToMany(mappedBy = "enseignant" , cascade = CascadeType.ALL, targetEntity = Module.class)
	private List<Module> modules;

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}


    public List<Module> getModules() {
		return this.modules;
    }

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
}