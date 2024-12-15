package com.algaworks.festa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.festa.model.Convidado;
import com.algaworks.festa.repository.Convidados;


@Controller
public class ConvidadosController {
	
	@Autowired
	private Convidados convidados;

	@GetMapping("/convidados")
	public ModelAndView Listar() {
		ModelAndView modelAndView = new ModelAndView("ListaConvidados");
		
		modelAndView.addObject("convidados",convidados.findAll());
		modelAndView.addObject(new Convidado());
		
		return modelAndView;
	}
	
	@PostMapping("/convidados")
	public String Salvar(Convidado convidado) {
		convidados.save(convidado);
		return "redirect:/convidados";
		
	}
	
	
}
