package com.gsnotes.dao;

import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.bo.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInscriptionModuleDao extends JpaRepository<InscriptionModule, Long> {
    public InscriptionModule getInscriptionModuleByInscriptionAnnuelleAndModule(InscriptionAnnuelle an, Module md);
    public InscriptionModule save(InscriptionModule inscriptionModule);
}
