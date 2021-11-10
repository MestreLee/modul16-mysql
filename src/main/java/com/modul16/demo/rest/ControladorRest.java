package com.modul16.demo.rest;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.modul16.demo.dao.UsuarisDAO;
import com.modul16.demo.dao.TiradesDAO;
import com.modul16.demo.models.Usuari;
import com.modul16.demo.models.Tirada;


@RestController
@RequestMapping("")
public class ControladorRest {
	
	@Autowired
	UsuarisDAO usuarisDAO;
	
	@Autowired
	TiradesDAO tiradesDAO;

	//veure tots els usuaris
	@GetMapping("/usuaris")
	public ResponseEntity<List<Usuari>> getAllUsuaris(){
		List<Usuari> usuaris = usuarisDAO.findAll();
		return ResponseEntity.ok(usuaris);	
	}
	
	//veure totes les tirades
	@GetMapping("/tirades")
	public ResponseEntity<List<Tirada>> getAllTirades(){
		List<Tirada> tirades = tiradesDAO.findAll();
		return ResponseEntity.ok(tirades);
	}
	
	//veure les tirades que pertanyen a un usuari en concret
	@GetMapping("/usuaris/{usuariID}/tirades")
	public ResponseEntity<List<Tirada>> getTirades(@PathVariable("usuariID") int usuariID){
		List<Tirada> tirades = tiradesDAO.findByusuariid(usuariID);
		return ResponseEntity.ok(tirades);
	}
	
	//veure el ranking de jugadors
	@GetMapping("/usuaris/ranking")
	public ResponseEntity<String> getRanking(){
		
		String usuarispromig = "";
		List<Usuari> usuaris = usuarisDAO.findAll();
		List<Usuari> sortedUsuaris = usuaris.stream().sorted(Comparator.comparing(Usuari::getPromig).reversed()).collect(Collectors.toList());
		for(Usuari user:sortedUsuaris) {
			usuarispromig = usuarispromig + user.toString();
		}
		return ResponseEntity.ok(usuarispromig);	
	}
	
	//afegir un usuari nou
	@PostMapping("/usuaris")
	public ResponseEntity<Usuari> crearUsuari(@RequestBody Usuari usuari){
		Usuari newUsuari = usuarisDAO.save(usuari);
		return ResponseEntity.ok(newUsuari);
	}
	
	//un usuari fa una tirada nova
	@PostMapping("/usuaris/{usuariID}/tirades")
	public ResponseEntity<Tirada> crearTirada(@PathVariable("usuariID") int usuariID){
		Optional<Usuari> optionalUsuari = usuarisDAO.findById(usuariID);
		if(optionalUsuari.isPresent()) {
			Tirada newTirada = new Tirada();
			newTirada.setUsuariid(usuariID);
			//tirada 1
			int dau = 0;
			int randomNum = 0;
		    while(dau == 0){
		    	randomNum = 0;
		    	randomNum = (int)(Math.random() * 6);  // 0 to 6
		        if(randomNum != 0){
		        	dau = 1;
		        	newTirada.setValor1(randomNum);
		        }
		    }
			
			//tirada 2
		    dau = 0;
		    while(dau == 0){
		    	randomNum = 0;
		    	randomNum = (int)(Math.random() * 6);  // 0 to 6
		        if(randomNum != 0){
		        	dau = 1;
		        	newTirada.setValor2(randomNum);
		        }
		    }
			//tirada 1 + tirada 2
			newTirada.setValortotal(newTirada.getValor1() + newTirada.getValor2());
			//if valorTotal == 7, true
			newTirada.setVictoria((newTirada.getValortotal() == 7)? true : false);
			//sumar valors a l'usuari
			
			Usuari updateUsuari = optionalUsuari.get();
			updateUsuari.incrTirades();
			if(newTirada.isVictoria()) updateUsuari.incrGuanyades();
			updateUsuari.setPromig();
			usuarisDAO.save(updateUsuari);
			tiradesDAO.save(newTirada);
			return ResponseEntity.ok(newTirada);
		}else {
			return ResponseEntity.noContent().build();
		}

	}	
	
	//usuari amb el millor promig
	@GetMapping("/usuaris/ranking/winner")
	public ResponseEntity<Usuari> usuariWinner(){
		List<Usuari> usuaris = usuarisDAO.findAll();
		List<Usuari> sortedUsuaris = usuaris.stream().sorted(Comparator.comparing(Usuari::getPromig).reversed()).collect(Collectors.toList());
		return ResponseEntity.ok(sortedUsuaris.get(0));
	}
	
	//usuari amb el pitjor promig
	@GetMapping("/usuaris/ranking/loser")
	public ResponseEntity<Usuari> usuariLoser(){
		List<Usuari> usuaris = usuarisDAO.findAll();
		List<Usuari> sortedUsuaris = usuaris.stream().sorted(Comparator.comparing(Usuari::getPromig)).collect(Collectors.toList());
		return ResponseEntity.ok(sortedUsuaris.get(0));
	}
	
	//modificar el nom d'un usuari en concret segons la seva id
	@PutMapping(value="/usuaris/{usuariID}")
	public ResponseEntity<Usuari> updateUsuari(@PathVariable("usuariID") int usuariID, @RequestBody Usuari usuari){
		Optional<Usuari> optionalUsuari = usuarisDAO.findById(usuariID);
		if(optionalUsuari.isPresent()) {
			Usuari updateUsuari = optionalUsuari.get();
			updateUsuari.setNom(usuari.getNom());
			usuarisDAO.save(updateUsuari);
			return ResponseEntity.ok(updateUsuari);
		}else {
			return ResponseEntity.noContent().build();
		}
	}	
	
	//eliminar un usuari en concret segons la seva id
	@DeleteMapping(value="/usuaris/{usuariID}")
	public ResponseEntity<Void> deleteUsuari(@PathVariable("usuariID") int usuariID){
		usuarisDAO.deleteById(usuariID);
		return ResponseEntity.ok(null);
	}
	
	
	//eliminar totes les tirades d'un usuari
	@DeleteMapping(value="/usuaris/{usuariID}/tirades")
	public ResponseEntity<Void> deleteAllTiradesByusuariid(@PathVariable("usuariID") int usuariID){
		tiradesDAO.deleteAllByusuariid(usuariID);
		return ResponseEntity.ok(null);
	}

}
