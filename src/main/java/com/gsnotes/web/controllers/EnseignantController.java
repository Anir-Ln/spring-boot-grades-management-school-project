package com.gsnotes.web.controllers;


import com.gsnotes.ExcelHandling.ExcelHandler;
import com.gsnotes.ExcelHandling.ExcelHandlerException;
import com.gsnotes.ExcelHandling.FileManagerHelper;
import com.gsnotes.bo.*;
import com.gsnotes.bo.Module;
import com.gsnotes.exceptions.GradeIntervalException;
import com.gsnotes.exceptions.IncoherentFileContentException;
import com.gsnotes.exceptions.IncorrectFormulaException;
import com.gsnotes.exceptions.InvalidFileFormatException;
import com.gsnotes.services.*;
import com.gsnotes.web.models.UserAndAccountInfos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.gsnotes.web.controllers.LoginController.getUserAndAccountInfos;

@Controller
@RequestMapping("/prof")
public class EnseignantController {

    @Autowired
    IEnseignantService enseignantService;

    @Autowired
    IElementService elementService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private IFileVerificationService fileVerificationService;
    @Autowired
    private IEtudiantService etudiantService;
    @Autowired
    private IInscriptionAnnuelleService inscriptionAnnuelleService;
    @Autowired
    private IInscriptionModuleService inscriptionModuleService;


    private UserAndAccountInfos getUserAccount() {
        // On vérifie si les infos de l'utilisateur sont déjà dans la session
        return getUserAndAccountInfos(httpSession);
    }


    @PostMapping("/import")
    public String processModulesNotes(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) throws InvalidFileFormatException, IncorrectFormulaException, IncoherentFileContentException, GradeIntervalException, IOException, ExcelHandlerException {
        Module currentModule = fileVerificationService.verify(file, getUserAccount().getIdPersonne());
        if(currentModule == null) {
            System.out.println("verifing ...........");
            return "/prof/userHomePage";
        }

        List<ArrayList<Object>> data = ExcelHandler.readFromExcel(FileManagerHelper.getAbsolutePathProject() + "/TEMP/" + file.getOriginalFilename(), 0);
        String session = ((String)data.get(1).get(3)).toLowerCase();

        Long id;
        List<InscriptionModule> listInscriptionModule = new ArrayList<>();
        List<Double> grades = new ArrayList<>();
        for(int i=4; i<data.size(); i++) {
            if(data.get(i).size() == 0)
                continue;
            id = ((Double) data.get(i).get(0)).longValue();
            Etudiant etd = etudiantService.getEtudiantById(id);
            InscriptionAnnuelle inscriptionAnnuelle = inscriptionAnnuelleService.getByEtdiantAndAnne(etd, ((String) data.get(0).get(5)).trim());
//            if(inscriptionAnnuelle == null) {
//                throw new IncoherentFileContentException("the student with id " + id + " not found in the module " + currentModule.getCode());
//            }
            InscriptionModule inscriptionModule = inscriptionModuleService.getByInscriptionAnnuelleAndModule(inscriptionAnnuelle, currentModule);
            if(inscriptionModule == null) {
                throw new IncoherentFileContentException("the student with id " + id + " not found in the module " + currentModule.getCode());
            }
            listInscriptionModule.add(inscriptionModule);
            grades.add((Double) data.get(i).get(data.get(i).size() - 2));
        }

        System.out.println("=====SAVING=====");
        System.out.println("inscriptionModule : " + listInscriptionModule);
        System.out.println("grades : " + grades.size());

        // already put notes
        double grade = session.equals("normale") ? listInscriptionModule.get(0).getNoteSN() : listInscriptionModule.get(0).getNoteSR();
        if(grade != -1) {
            httpSession.setAttribute("listInscriptionModule", listInscriptionModule);
            httpSession.setAttribute("grades", grades);
            httpSession.setAttribute("session", session);
            return "prof/areyousure";
        }
        // here all the students are good {subscribed in the module}
        int i=0;
        for(InscriptionModule inscriptionModule: listInscriptionModule) {
            if(session.equals("normale")) {
                inscriptionModule.setNoteSN(grades.get(i++));
            } else {
                inscriptionModule.setNoteSR(grades.get(i++));
            }
            inscriptionModuleService.save(inscriptionModule);
        }

        model.addAttribute("message", "the grades were successfuly uploaded");

        return "/prof/userHomePage";
    }


    @GetMapping("/import")
    public String processImport(@RequestParam(value="sure", required=false) Integer sure, Model model) {
        if(sure == null)
            return "/prof/userHomePage";
        if(sure == 1) {
            List<InscriptionModule> listInscriptionModule = (List<InscriptionModule>) httpSession.getAttribute("listInscriptionModule");
            List<Double> grades = (List<Double>) httpSession.getAttribute("grades");
            String session = (String) httpSession.getAttribute("session");
            int i=0;
            for(InscriptionModule inscriptionModule: listInscriptionModule) {
                if (session.equals("normale")) {
                    inscriptionModule.setNoteSN(grades.get(i++));
                } else {
                    inscriptionModule.setNoteSR(grades.get(i++));
                }
                inscriptionModuleService.save(inscriptionModule);
            }
        }
        model.addAttribute("message", "the grades were successfuly uploaded");
        return "/prof/userHomePage";
    }
}
