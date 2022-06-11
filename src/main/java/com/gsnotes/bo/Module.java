package com.gsnotes.bo;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="module")
public class Module {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idModule;

	private String titre;

	private String code;


	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL, targetEntity = Element.class)
	private List<Element> elements;

	@ManyToOne
	@JoinColumn(name = "idNiveau")
	private Niveau niveau;

	@ManyToOne
	@JoinColumn(name = "idEnseignant")
	private Enseignant enseignant;

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public Long getIdModule() {
		return idModule;
	}

	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		return "Module{" +
				"idModule=" + idModule +
				", titre='" + titre + '\'' +
				", code='" + code + '\'' +
				", elements=" + elements +
				", niveau=" + niveau +
				'}';
	}
}