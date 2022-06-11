package com.gsnotes.bo;

import javax.persistence.Entity;

import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Represente un cadre qui travaille à l'administration
 * 
 * @author T. BOUDAA
 *
 */

@Entity
@Table(name="cadreadministrateur")
@PrimaryKeyJoinColumn(name = "idCardreAdmin")
public class CadreAdministrateur extends Utilisateur {

	private String grade;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}