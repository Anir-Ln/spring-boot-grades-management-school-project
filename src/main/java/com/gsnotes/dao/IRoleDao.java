package com.gsnotes.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gsnotes.bo.Role;

public interface IRoleDao extends JpaRepository<Role, Long> {

}
