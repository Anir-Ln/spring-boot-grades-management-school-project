package com.gsnotes.services.impl;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.dao.IInscriptionAnnuelleDao;
import com.gsnotes.services.IInscriptionAnnuelleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InscriptionAnnuelleService implements IInscriptionAnnuelleService {
    @Autowired
    IInscriptionAnnuelleDao inscriptionAnnuelleDao;

    @Override
    public com.gsnotes.bo.InscriptionAnnuelle getByEtdiantAndAnne(Etudiant etd, String annee) {
        return inscriptionAnnuelleDao.getInscriptionAnnuelleByEtudiantAndAnnee(etd, annee);
    }
}
