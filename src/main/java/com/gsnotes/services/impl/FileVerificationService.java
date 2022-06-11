package com.gsnotes.services.impl;

import com.gsnotes.ExcelHandling.ExcelHandler;
import com.gsnotes.ExcelHandling.ExcelHandlerException;
import com.gsnotes.ExcelHandling.FileManagerHelper;
import com.gsnotes.bo.*;
import com.gsnotes.bo.Module;
import com.gsnotes.exceptions.*;
import com.gsnotes.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class FileVerificationService implements IFileVerificationService {

    private final List<String> CONTENT_TYPES = Arrays.asList("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel");
    private static final String TEMP_FOLDER = FileManagerHelper.getAbsolutePathProject() + "/TEMP/";
    private static final Path dir = Paths.get(TEMP_FOLDER);

    static {
        if(!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Autowired
    IEnseignantService enseignantService;
    @Autowired
    IEtudiantService etudiantService;
    @Autowired
    IInscriptionAnnuelleService inscriptionAnnuelleService;
    @Autowired
    IInscriptionModuleService inscriptionModuleService;


    // Primary function
    public Module verify(MultipartFile file, Long profId) throws IncoherentFileContentException, IncorrectFormulaException, GradeIntervalException, InvalidFileFormatException, IOException {

        // extension and contentFile
        if(!isValid(file)) {
            // throw format exception
            throw new InvalidFileFormatException("the file is either empty or the not an excel file");
        }

        try {
            uploadFile(file);
        } catch (IOException e) {
            throw new IOException("an IO error accured");
        }

        List<ArrayList<Object>> data = null;
        try {
            data = ExcelHandler.readFromExcel(TEMP_FOLDER + file.getOriginalFilename(), 0);
            showFileData(data);
        } catch (ExcelHandlerException e) {
            throw new InvalidFileFormatException("error reading the file");
        }

        Enseignant prof = enseignantService.getEnseignatById(profId);
        if(!isHeaderStructureCorrect(data)) {
            // throw exception
            System.out.println("header structure incorrect");
            // throw Invalid-format exception
            throw new InvalidFileFormatException("this file does not respect the wanted structure");
        }
        // check the correctness of the file header
        // throws exception
        Module currentModule = validateContent(data, prof);



        // students verification
        //saveStudents(data, currentModule, "normale");


        return currentModule;
    }



/*
    private void saveStudents(List<ArrayList<Object>> data, Module currentModule, String session) throws IncoherentFileContentException {

        // iterate over all students in the file
        Long id;
        List<InscriptionModule> listInscriptionModule = new ArrayList<>();
        List<Double> grades = new ArrayList<>();
        for(int i=4; i<data.size(); i++) {
            System.out.println("===========================" + i + " == " + data.get(i).get(0));
            id = ((Double) data.get(i).get(0)).longValue();
            System.out.println(id);
            Etudiant etd = etudiantService.getEtudiantById(id);
            InscriptionAnnuelle inscriptionAnnuelle = inscriptionAnnuelleService.getByEtdiantAndAnne(etd, sanitizeString((String) data.get(0).get(5)));
            if(inscriptionAnnuelle == null) {
                throw new IncoherentFileContentException("the student with id " + id + " not found in the module " + currentModule.getCode());
            }
            InscriptionModule inscriptionModule = inscriptionModuleService.getByInscriptionAnnuelleAndModule(inscriptionAnnuelle, currentModule);
            if(inscriptionModule == null) {
                throw new IncoherentFileContentException("the student with id " + id + " not found in the module " + currentModule.getCode());
            }
            listInscriptionModule.add(inscriptionModule);
            grades.add((Double) data.get(i).get(data.get(i).size() - 2));
        }

        // here all the students are good {subscribed in the module}
        int i=0;
        for(InscriptionModule inscriptionModule: listInscriptionModule) {
            if(areTheSame(session, "normale")) {
                inscriptionModule.setNoteSN(grades.get(i++));
            } else {
                inscriptionModule.setNoteSR(grades.get(i++));
            }
        }
    }
*/

    // verify the module, prof, session, elements names
    // structure of the file:
    private Module validateContent(List<ArrayList<Object>> data, Enseignant prof) throws IncoherentFileContentException, GradeIntervalException, IncorrectFormulaException {
        System.out.println("headerIsValid function running ...");

        String moduleNameFile = (String) data.get(0).get(1);
        String profNameFile = (String) data.get(1).get(1);
        String sessionFile = (String) data.get(0).get(3);
        String niveauAliasFile = (String) data.get(1).get(5);
        String yearFile = (String) data.get(0).get(5);

        if (!(areTheSame(profNameFile, prof.getNom() + prof.getPrenom()) || areTheSame(profNameFile, prof.getPrenom() + prof.getNom()))) {
            // the name of the prof is incorrect
            // throw an exception
            System.out.println("not the same prof name");
            throw new IncoherentFileContentException("seems like the prof name is not correct");
        }


        List<Module> modules = prof.getModules();
        System.out.println("Modules: " + modules);
        Module currentModule = null;
        for(Module module: modules) {
            if(areTheSame(module.getTitre(), moduleNameFile)){
                currentModule = module;
                System.out.println("=======Module found " + module);
                break;
            }
        }

        if(currentModule == null) {
            // the name of the module is incoherent
            // throw an exception
            throw new IncoherentFileContentException("seems like the module name is not correct");
        }

        // verifier l'annee
        //yearFile = sanitizeString(yearFile);


        // verify elements

        List<Element> currentElements = currentModule.getElements();
        System.out.println("elements =================================================........... " + currentElements);

        Niveau currentNiveau = currentModule.getNiveau();
        if(!areTheSame(currentNiveau.getAlias(), niveauAliasFile)) {
            throw new IncoherentFileContentException("seems like {niveau} is incorrect");
        }

        // make sure the number of elements is correct
        int elementsCountInFile = data.get(3).size() - 6;
        if(elementsCountInFile != currentElements.size()){
            System.out.println("Not the same number of elements of the module");
            throw new IncoherentFileContentException("seems like the number of the elements is not correct");

        }

        List<Element> orderedElements = new ArrayList<>();
        for(int i=4; i < 4 + elementsCountInFile; i++) {
            for (Element element : currentElements) {
                System.out.println("comparaison : " + (String) data.get(3).get(i) + " || " + element.getCode());
                if (areTheSame(element.getCode(), (String) data.get(3).get(i))) {
                    orderedElements.add(element);
                }
            }
        }
        System.out.println("elements =============" + orderedElements);
        if(orderedElements.size() != elementsCountInFile) {
            System.out.println("Wrong names of the elements");
            // throw error
            throw new IncoherentFileContentException("seems like some elements names are not correct");
        }


        // the elements are verified
        // now let's verify formulas for all students
        for(int i=4; i<data.size(); i++) {
            if(data.get(i).size() == 0)
                continue;
            double grade = (double) 0;
            double coefSum = (double) 0;
            System.out.print("Note[" + (i - 4) + "]: ");
            for(int j=4; j< 4 + elementsCountInFile; j++) {
                double note = (Double) data.get(i).get(j);
                System.out.print(note + " - ");
                // make sure grade in [0,20]
                if(note < 0 || note > 20) {
                    // throw new gradeIntervalException();
                    System.out.print(note + " n'est pas dans l'interval [0,20]");
                    throw new GradeIntervalException(note + " n'est pas dans l'interval [0,20]");
                }
                grade += orderedElements.get(j-4).getCurrentCoefficient() * note;
                coefSum += orderedElements.get(j-4).getCurrentCoefficient();
            }
            // error: formula not correct
            grade /= coefSum;
            if(Math.abs(grade - (Double) data.get(i).get(data.get(i).size() - 2)) > 0.01) {
                System.out.println("Formula not correct : Moyenne");
                throw new IncorrectFormulaException("the formula of the {Moyenne} is incorrect at line " + i);
            } else if (grade >= 12 && !areTheSame((String) data.get(i).get(data.get(i).size() - 1), "V")) {
                System.out.println("Formula not correct : Validation");
                throw new IncorrectFormulaException("the formula of the {Validation} is incorrect at line " + i);
            }


            System.out.println("[Content is valid]");
        }

        return currentModule;
    }

    private boolean isHeaderStructureCorrect(List<ArrayList<Object>> data) {
        if (data.get(0).size() != 6 || data.get(1).size() != 6) {
            //throw new FileHeaderErrorException("Header not valid format");
            return false;
        }

        // verify header structure
        String[][] perfectRow0_1 = new String[][] {{"Module", "Semestre", "Année"}, {"Enseignant", "Session", "Classe"}};
        String[] perfectRow2 = new String[] {"ID", "CNE", "NOM", "PRENOM"};

        for(int i=0; i<2; i++) {
            for(int j=0; j<3; j++) {
                if(!areTheSame(perfectRow0_1[i][j], (String) data.get(i).get(j*2))) {
                    // throw exception
                    System.out.println("incorrect format " + perfectRow0_1[i][j]);
                    return false;
                }
            }
        }
        for(int i=0; i<4; i++) {
            if(!areTheSame(perfectRow2[i], (String) data.get(3).get(i))) {
                // throw exception
                System.out.println("incorrect format : " + perfectRow2[i]);
                return false;
            }
        }
        if(!areTheSame((String) data.get(3).get(data.get(3).size() - 2), "Moyenne") || !areTheSame((String) data.get(3).get(data.get(3).size() - 1), "Validation")) {
            System.out.println("incorrect format : validation or moyeene");
            return false;
        }

        return true;
    }



    private boolean areTheSame(String s1, String s2) {
        return sanitizeString(s1).equals(sanitizeString(s2));
    }

    private void showFileData(List<ArrayList<Object>> data) {
        for (ArrayList<Object> row : data) {
            for (Object cell : row) {
                System.out.print(cell + "\t\t\t");
            }
            System.out.println();
        }
    }

    private void uploadFile(MultipartFile file) throws IOException {
        Path path = Paths.get(TEMP_FOLDER + file.getOriginalFilename());
        byte[] bytes = file.getBytes();
        Files.write(path, bytes);

        System.out.println("File uploaded Successfully");
    }


    // verify the type of the file
    private boolean isValid(MultipartFile file) {
        System.out.println("============================");
        System.out.println("content type = " + file.getContentType());
        System.out.println("original name = " + file.getOriginalFilename());
        System.out.println("size = " + file.getSize());
        System.out.println("resource = " + file.getResource());
        System.out.println("============================");


        if (file.isEmpty() || !CONTENT_TYPES.contains(file.getContentType())) {
            return false;
        }

        return true;
    }

    private String sanitizeString(String str) {
        return str.replace("\n", "").replace(" ", "").toLowerCase();
    }

}
