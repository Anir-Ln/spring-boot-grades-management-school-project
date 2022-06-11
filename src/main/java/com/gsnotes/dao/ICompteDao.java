package com.gsnotes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Compte;

public interface ICompteDao extends JpaRepository<Compte, Long> {
	public Compte getCompteByLogin(String username);

}
