package com.gsnotes.services;

import com.gsnotes.bo.InscriptionAnnuelle;
import com.gsnotes.bo.InscriptionModule;
import com.gsnotes.bo.Module;

public interface IInscriptionModuleService {
    public InscriptionModule getByInscriptionAnnuelleAndModule(InscriptionAnnuelle an, Module md);

    public InscriptionModule save(InscriptionModule inscriptionModule);
}
