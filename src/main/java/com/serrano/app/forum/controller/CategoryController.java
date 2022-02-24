package com.serrano.app.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serrano.app.forum.dto.Categories;
import com.serrano.app.forum.dto.CategoryDTO;
import com.serrano.app.forum.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	private final CategoryService categoryService;
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PostMapping
	public ResponseEntity<Object> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		return new ResponseEntity<Object>(categoryService.create(categoryDTO), HttpStatus.OK);
	}
	
	@GetMapping(path = "")
	public ResponseEntity<Categories> findAll(){
		return new ResponseEntity<Categories>(categoryService.getAll(), HttpStatus.OK);
	}
}
