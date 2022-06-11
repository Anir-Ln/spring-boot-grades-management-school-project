package com.gsnotes.dao;

import com.gsnotes.bo.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnseignantDao extends JpaRepository<Enseignant, Long> {

    Enseignant getEnseignantByIdUtilisateur(Long id);
}
