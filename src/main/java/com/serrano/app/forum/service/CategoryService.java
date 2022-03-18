package com.serrano.app.forum.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serrano.app.forum.domain.Category;
import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.dto.Categories;
import com.serrano.app.forum.dto.CategoryDTO;
import com.serrano.app.forum.dto.ServiceResponse;
import com.serrano.app.forum.exception.CategoryNotFoundException;
import com.serrano.app.forum.exception.CustomApiException;
import com.serrano.app.forum.mapper.CategoryMapper;
import com.serrano.app.forum.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService {

	private final CategoryRepository categoryRepo;
	private final CategoryMapper mapper;
	private final AuthService authService;

	@Autowired
	public CategoryService(CategoryRepository categoryRepo, CategoryMapper mapper, AuthService authService) {
		this.categoryRepo = categoryRepo;   
		this.mapper = mapper;
		this.authService = authService;

	}
	
	
	public ServiceResponse create(CategoryDTO categoryDTO) {
		User user = authService.getCurrentUser();
		Category category = mapper.mapToEntity(categoryDTO, user);
		try {
			Category categorySaved = categoryRepo.save(category);
			log.info(categorySaved.toString());
			CategoryDTO responseDTO = mapper.mapToDTO(categorySaved);
			return new ServiceResponse("Category registration succesfully", HttpStatus.CREATED, responseDTO);			
		}catch(Exception ex) {
			throw new CustomApiException("Something went wrong when try to create category", 
					new ArrayList<String>(Arrays.asList(ex.getMessage())) );
		}
	}
	
	@Transactional(readOnly = true)
	public Categories getAll() {
        List<Category> entity = categoryRepo.findAll();
        List<CategoryDTO> categoriesDTO = entity.stream()
        		.map(c -> mapper.mapToDTO(c))
        		.collect(Collectors.toList());
		Categories categories = new Categories(categoriesDTO);
		return categories;
	}
	
	public CategoryDTO findById(Long id) {
		Category category = findUser(id);
		log.info(category.toString());
		log.info(category.getPosts().toString());

		CategoryDTO categoryDTO = mapper.mapToDTO(category);
		return categoryDTO;
	}
	
	@Transactional(readOnly = true)
	private Category findUser(Long id) {
		Category category = categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
		return category;
	}
	
}
