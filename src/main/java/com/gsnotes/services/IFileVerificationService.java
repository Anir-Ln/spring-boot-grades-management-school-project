package com.gsnotes.services;

import com.gsnotes.bo.Module;
import com.gsnotes.exceptions.GradeIntervalException;
import com.gsnotes.exceptions.IncoherentFileContentException;
import com.gsnotes.exceptions.IncorrectFormulaException;
import com.gsnotes.exceptions.InvalidFileFormatException;
import com.gsnotes.web.models.UserAndAccountInfos;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileVerificationService {

    public Module verify(MultipartFile file, Long userId) throws IncoherentFileContentException, IncorrectFormulaException, GradeIntervalException, InvalidFileFormatException, IOException;


}
