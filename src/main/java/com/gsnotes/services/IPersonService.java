package com.gsnotes.services;

import java.util.List;

import com.gsnotes.bo.Utilisateur;
import com.gsnotes.utils.export.ExcelExporter;

public interface IPersonService {

	public void addPerson(Utilisateur pPerson);

	public void updatePerson(Utilisateur pPerson);

	public List<Utilisateur> getAllPersons();

	public void deletePerson(Long id);

	public Utilisateur getPersonById(Long id);
	
	public Utilisateur getPersonByCin(String cin);
	
	public ExcelExporter preparePersonExport(List<Utilisateur> persons);
	
	

}
