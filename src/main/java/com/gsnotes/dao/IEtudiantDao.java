package com.gsnotes.dao;

import com.gsnotes.bo.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEtudiantDao extends JpaRepository<Etudiant, Long> {
    public Etudiant getEtudiantByIdUtilisateur(Long id);
}
