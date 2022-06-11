package com.gsnotes.dao;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInscriptionAnnuelleDao extends JpaRepository<InscriptionAnnuelle, Long> {
    public InscriptionAnnuelle getInscriptionAnnuelleByEtudiantAndAnnee(Etudiant etd, String annee);
}
