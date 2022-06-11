package com.gsnotes.services.impl;

import com.gsnotes.bo.Element;
import com.gsnotes.bo.Module;
import com.gsnotes.dao.IElementDao;
import com.gsnotes.services.IElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ElementService implements IElementService {

    @Autowired
    IElementDao elementDao;

    @Override
    public List<Element> getElementByModule(Module module) {
       return elementDao.getElementsByModule(module);
    }
}
