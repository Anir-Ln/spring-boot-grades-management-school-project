package com.gsnotes.services.impl;

import com.gsnotes.bo.Etudiant;
import com.gsnotes.dao.IEtudiantDao;
import com.gsnotes.services.IEtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EtudiantService implements IEtudiantService {

    @Autowired
    IEtudiantDao etudiantDao;

    @Override
    public Etudiant getEtudiantById(Long id) {
        return etudiantDao.getEtudiantByIdUtilisateur(id);
    }


}
