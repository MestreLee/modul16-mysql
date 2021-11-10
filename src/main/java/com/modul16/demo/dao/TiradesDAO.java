package com.modul16.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.modul16.demo.models.Tirada;

public interface TiradesDAO extends JpaRepository<Tirada,Integer>{

	List<Tirada> findByusuariid(int usuariID);

	@Transactional
	void deleteAllByusuariid(int usuariID);
	

}
