package com.modul16.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.modul16.demo.models.Usuari;

public interface UsuarisDAO extends JpaRepository<Usuari, Integer>{


}
