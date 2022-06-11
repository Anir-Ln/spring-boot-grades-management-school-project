package com.gsnotes.services.impl;

import com.gsnotes.bo.Enseignant;
import com.gsnotes.dao.IEnseignantDao;
import com.gsnotes.services.IEnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EnseignantService implements IEnseignantService {

    @Autowired
    IEnseignantDao enseignantDao;

    @Override
    public Enseignant getEnseignatById(Long id) {
       return enseignantDao.getEnseignantByIdUtilisateur(id);
    }
}
