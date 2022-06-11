package com.gsnotes.bo;

import java.util.*;

import javax.persistence.*;

/**
 * Represente une inscription annuelle.
 * <p>
 * Une inscription annuelle est composée de plusieurs inscriptions dans les
 * matières
 *
 * @author T. BOUDAA
 */

@Entity
@Table(name = "inscriptionannuelle")
public class InscriptionAnnuelle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInscription;

    private String annee;

    private int etat;

    private String type;

    private int rang;

    private String validation;

    private String mention;

    private String plusInfos;

    /**
     * Permet de stocker les notes des matières
     */
    @OneToMany(mappedBy = "inscriptionAnnuelle", cascade = CascadeType.ALL, targetEntity = InscriptionMatiere.class)
    private Set<InscriptionMatiere> inscriptionMatieres;


    /**
     * Permet de stocker les notes des matières
     */
    @OneToMany(mappedBy = "inscriptionAnnuelle", cascade = CascadeType.ALL, targetEntity = InscriptionModule.class)
    private Set<InscriptionModule> inscriptionModules;

    @ManyToOne
    @JoinColumn(name = "idNiveau")
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "idEtudiant")
    private Etudiant etudiant;

    public Long getIdInscription() {
        return idInscription;
    }

    public void setIdInscription(Long idInscription) {
        this.idInscription = idInscription;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Set<InscriptionMatiere> getInscriptionMatieres() {
        return inscriptionMatieres;
    }

    public void setInscriptionMatieres(Set<InscriptionMatiere> inscriptionMatieres) {
        this.inscriptionMatieres = inscriptionMatieres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<InscriptionModule> getInscriptionModules() {
        return inscriptionModules;
    }

    public void setInscriptionModules(Set<InscriptionModule> inscriptionModules) {
        this.inscriptionModules = inscriptionModules;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public String getPlusInfos() {
        return plusInfos;
    }

    public void setPlusInfos(String plusInfos) {
        this.plusInfos = plusInfos;
    }

}