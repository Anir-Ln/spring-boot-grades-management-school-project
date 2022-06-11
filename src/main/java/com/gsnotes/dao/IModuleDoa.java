package com.gsnotes.dao;

import com.gsnotes.bo.Element;
import com.gsnotes.bo.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IModuleDoa extends JpaRepository<Module, Long> {

}
