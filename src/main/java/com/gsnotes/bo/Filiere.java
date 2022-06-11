package com.gsnotes.bo;


import java.util.*;

import javax.persistence.*;


/**
 * Represente une filière.
 * 
 * 
 * @author T. BOUDAA
 *
 */

@Entity
@Table(name = "filiere")
public class Filiere {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFiliere;

	private String titreFiliere;

	private String codeFiliere;

	private int anneeaccreditation;

	private int anneeFinaccreditation;

	@OneToMany(mappedBy = "filiere" ,  cascade = CascadeType.ALL, targetEntity = Niveau.class)
	private Set<Niveau> niveaux;


	public Long getIdFiliere() {
		return idFiliere;
	}

	public void setIdFiliere(Long idFiliere) {
		this.idFiliere = idFiliere;
	}

	public String getTitreFiliere() {
		return titreFiliere;
	}

	public void setTitreFiliere(String titreFiliere) {
		this.titreFiliere = titreFiliere;
	}

	public String getCodeFiliere() {
		return codeFiliere;
	}

	public void setCodeFiliere(String codeFiliere) {
		this.codeFiliere = codeFiliere;
	}

	public int getAnneeaccreditation() {
		return anneeaccreditation;
	}

	public void setAnneeaccreditation(int anneeaccreditation) {
		this.anneeaccreditation = anneeaccreditation;
	}

	public int getAnneeFinaccreditation() {
		return anneeFinaccreditation;
	}

	public void setAnneeFinaccreditation(int anneeFinaccreditation) {
		this.anneeFinaccreditation = anneeFinaccreditation;
	}

	public Set<Niveau> getNiveaux() {
		return niveaux;
	}

	public void setNiveaux(Set<Niveau> niveaux) {
		this.niveaux = niveaux;
	}

	
	
}