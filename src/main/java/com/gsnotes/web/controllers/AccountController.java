package com.gsnotes.web.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gsnotes.bo.Compte;
import com.gsnotes.services.ICompteService;
import com.gsnotes.services.IPersonService;
import com.gsnotes.utils.export.ExcelExporter;
import com.gsnotes.web.models.AccountModel;

/**
 * 
 * @author Tarik BOUDAA
 *
 */

@Controller
@RequestMapping("/admin") // Très important car, dans notre Spring security config les URL qui commencent par ADMIN
							// sont dédiées à l'admin. Toutes les URL dédinies dans ce controleur définissent des
							// fonctionnalités accessibles uniquement à l'administrateur
public class AccountController {

	@Autowired
	private ICompteService userService; // On obtient par injection automatique le service

	@Autowired
	private IPersonService personService; // On obtient par injection automatique le service
	
	//Cette méthode initialise le formulaire de création de compte
	@RequestMapping(value = "createAccountForm/{idPerson}", method = RequestMethod.GET)
	public String createAccountForm(@PathVariable int idPerson, Model model) {

		
		//On crée le model 
		AccountModel accountModel = new AccountModel(Long.valueOf(idPerson));

		//On enregistre le modèle pour le passer à la vue
		model.addAttribute("accountModel", accountModel);

		//On ajoute la liste des roles dans le modele 
		model.addAttribute("roleList", userService.getAllRoles());
		
		//On ajoute également la liste des comptes dans le modèle
		model.addAttribute("accountList", userService.getAllAccounts());

		
		//On affiche la vue
		return "admin/formAccount";
	}

	
	//Affiche la liste des comptes
	@GetMapping("manageAccounts")
	public String manageAccounts(@ModelAttribute("accountModel") AccountModel accountModel, Model model) {

		model.addAttribute("accountList", userService.getAllAccounts());

		return "admin/accountList";
	}
	
	@GetMapping("createAccounts")
	public String createAccounts(@ModelAttribute("accountModel") AccountModel accountModel, Model model) {


		model.addAttribute("personList", personService.getAllPersons());

		return "admin/accountCreation";
	}
	
	
	//Cette méthode permet de créer un comote
	@PostMapping("addAccount")
	public String addAccount(@ModelAttribute("accountModel") AccountModel accountModel, Model model) {

		
		//La création du compte est implémentée au niveau service
		//Il suffit de passer l'id du role et l'id de personne
		//à la couche service
		String password = userService.createUser(accountModel.getRoleId(), accountModel.getPersonId());

		//On affiche le mot de passe dans la vue 
		accountModel.setPassword(password);
		
		//On affiche également la liste des comptes dans la vue
		model.addAttribute("accountList", userService.getAllAccounts());

		
		//On affiche la vue 
		return "/admin/accountList";

	}
	
	
	//export des données du compte
	@GetMapping("exportAccounts")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=accounts_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Compte> comptes = userService.getAllAccounts();

		ExcelExporter excelExporter = userService.prepareCompteExport(comptes);

		excelExporter.export(response);
	}
	

}
