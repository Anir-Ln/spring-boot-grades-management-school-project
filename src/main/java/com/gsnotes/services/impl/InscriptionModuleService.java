package com.gsnotes.services.impl;

import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.bo.Module;
import com.gsnotes.dao.IInscriptionModuleDao;
import com.gsnotes.services.IInscriptionModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InscriptionModuleService implements IInscriptionModuleService {
    @Autowired
    IInscriptionModuleDao inscriptionModuleDao;

    @Override
    public InscriptionModule getByInscriptionAnnuelleAndModule(InscriptionAnnuelle an, Module md) {
        return inscriptionModuleDao.getInscriptionModuleByInscriptionAnnuelleAndModule(an, md);
    }

    @Override
    public InscriptionModule save(InscriptionModule inscriptionModule) {
        return inscriptionModuleDao.save(inscriptionModule);
    }

}
