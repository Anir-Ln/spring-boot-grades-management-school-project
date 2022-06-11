package com.gsnotes.bo;

import javax.persistence.*;

@Entity
@Table(name = "inscriptionmatiere")
public class InscriptionMatiere {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idInscriptionMatiere;

	private double noteSN;
	private double noteSR;
	private double noteFinale;
	private String validation;
	private String plusInfos;

	/** Coefficient */
	private double coefficient;

	@ManyToOne
	@JoinColumn(name = "idMatiere")
	private Element matiere;

	@ManyToOne
	@JoinColumn(name = "idInscription")
	private InscriptionAnnuelle inscriptionAnnuelle;

	public InscriptionMatiere() {
	}

	public Long getIdInscriptionMatiere() {
		return idInscriptionMatiere;
	}

	public void setIdInscriptionMatiere(Long idInscriptionMatiere) {
		this.idInscriptionMatiere = idInscriptionMatiere;
	}

	public double getNoteSN() {
		return noteSN;
	}

	public void setNoteSN(double noteSN) {
		this.noteSN = noteSN;
	}

	public double getNoteSR() {
		return noteSR;
	}

	public void setNoteSR(double noteSR) {
		this.noteSR = noteSR;
	}

	public double getNoteFinale() {
		return noteFinale;
	}

	public void setNoteFinale(double noteFinale) {
		this.noteFinale = noteFinale;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public String getPlusInfos() {
		return plusInfos;
	}

	public void setPlusInfos(String plusInfos) {
		this.plusInfos = plusInfos;
	}

	public Element getMatiere() {
		return matiere;
	}

	public void setMatiere(Element matiere) {
		this.matiere = matiere;
	}

	public InscriptionAnnuelle getInscriptionAnnuelle() {
		return inscriptionAnnuelle;
	}

	public void setInscriptionAnnuelle(InscriptionAnnuelle inscriptionAnnuelle) {
		this.inscriptionAnnuelle = inscriptionAnnuelle;
	}

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}

}
