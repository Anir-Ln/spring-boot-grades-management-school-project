package com.gsnotes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Utilisateur;

public interface IUtilisateurDao extends JpaRepository<Utilisateur, Long> {
	public Utilisateur getUtilisateurByCin(String cin);

}
