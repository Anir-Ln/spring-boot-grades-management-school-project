package com.gsnotes.web.controllers;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

import com.gsnotes.ExcelHandling.ExcelHandler;
import com.gsnotes.ExcelHandling.FileManagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gsnotes.bo.Compte;
import com.gsnotes.bo.UserPrincipal;
import com.gsnotes.bo.Utilisateur;
import com.gsnotes.web.models.UserAndAccountInfos;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


@Controller
public class LoginController {

	@Autowired
	private HttpSession httpSession;

	/**
	 * Récupère les données de l'utilisateur connecté du contexte de securité et le
	 * stocke dans un objet personnalisé à enregistrer dans la session http
	 * 
	 * @return
	 */
	private UserAndAccountInfos getUserAccount() {
		// On vérifie si les infors de l'utilisateur sont déjà dans la session
		return getUserAndAccountInfos(httpSession);

	}

	static UserAndAccountInfos getUserAndAccountInfos(HttpSession httpSession) {
		UserAndAccountInfos userInfo = (UserAndAccountInfos) httpSession.getAttribute("userInfo");

		if (userInfo == null) {

			// On obtient l'objet representant le compte connecté (Objet UserPrincipal
			// implémentant UserDetails)
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// On cast vers notre objet UserPrincipal
			Compte userAccount = ((UserPrincipal) principal).getUser();

			Utilisateur u = userAccount.getProprietaire();

			String roleName = userAccount.getRole().getNomRole();

			userInfo = new UserAndAccountInfos(u.getIdUtilisateur(), userAccount.getIdCompte(), userAccount.getLogin(),
					u.getNom(), u.getPrenom(), u.getEmail(), roleName);

			// On le stocke dans la session
			httpSession.setAttribute("userInfo", userInfo);
		}

		return userInfo;
	}

	@GetMapping("/showMyLoginPage")
	public String showLoginForm() {

		return "loginForm";
	}

	@GetMapping("/access-denied")
	public String showAccessDenied() {

		return "access-denied";

	}

	@GetMapping("/student/showHome")
	public String showStudentHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);

		return "student/userHomePage";

	}

	@GetMapping("/prof/showHome")
	public String showProfHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "prof/userHomePage";
	}



	@GetMapping("/cadreadmin/showHome")
	public String showCadreAdminHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "cadreadmin/userHomePage";

	}

	@GetMapping("/biblio/showHome")
	public String showBiblioHomePage(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "biblio/userHomePage";

	}

	@GetMapping("/admin/showAdminHome")
	public String showAdminHome(Model m) {

		UserAndAccountInfos infoUser = getUserAccount();
		m.addAttribute("userInfos", infoUser);
		return "admin/adminHome";

	}

}
