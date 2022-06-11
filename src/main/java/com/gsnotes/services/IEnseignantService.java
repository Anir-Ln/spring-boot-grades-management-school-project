package com.gsnotes.services;

import com.gsnotes.bo.Enseignant;
import org.springframework.stereotype.Service;

public interface IEnseignantService {

    public Enseignant getEnseignatById(Long id);
}
