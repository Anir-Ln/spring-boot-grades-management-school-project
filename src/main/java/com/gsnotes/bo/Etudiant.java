package com.gsnotes.bo;


import java.util.*;

import javax.persistence.*;


/**
 * Represente un Etudiant.
 * 
 * Un enseignant est un cas spéciale de l'Etudiant
 * 
 * @author T. BOUDAA
 *
 */

@Entity
@Table(name = "etudiant")
@PrimaryKeyJoinColumn(name = "idEtudiant")
public class Etudiant extends Utilisateur {

	private String cne;

	private Date dateNaissance;

	@OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL , targetEntity = InscriptionAnnuelle.class)
	private List<InscriptionAnnuelle> inscriptions;


	public String getCne() {
		return cne;
	}

	public void setCne(String cne) {
		this.cne = cne;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(java.util.Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public List<InscriptionAnnuelle> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<InscriptionAnnuelle> inscriptions) {
		this.inscriptions = inscriptions;
	}

	@Override
	public String toString() {
		return "Etudiant [cne=" + cne + ", dateNaissance=" + dateNaissance + ", inscriptions=" + inscriptions + "]";
	}


	

}