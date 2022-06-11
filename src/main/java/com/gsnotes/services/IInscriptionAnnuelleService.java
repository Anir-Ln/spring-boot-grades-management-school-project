package com.gsnotes.services;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.bo.InscriptionAnnuelle;

public interface IInscriptionAnnuelleService {
    public InscriptionAnnuelle getByEtdiantAndAnne(Etudiant etd, String annee);
}
