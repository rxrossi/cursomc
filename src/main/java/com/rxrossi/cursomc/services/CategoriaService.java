package com.rxrossi.cursomc.services;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rxrossi.cursomc.domain.Categoria;
import com.rxrossi.cursomc.repositories.CategoriaRepository;
import com.rxrossi.cursomc.services.exceptions.ObjectNotFoundException;
import com.rxrossi.cursomc.services.exceptions.ConstraintException;



@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		return repo.save(obj);
	}


	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) {
			throw new ConstraintException("Não é possível excluir uma categoria que possui produtos");
		}
	}
}
