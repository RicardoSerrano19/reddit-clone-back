package com.serrano.app.forum.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serrano.app.forum.domain.Category;
import com.serrano.app.forum.dto.Categories;
import com.serrano.app.forum.dto.CategoryDTO;
import com.serrano.app.forum.dto.ServiceResponse;
import com.serrano.app.forum.exception.CategoryNotFoundException;
import com.serrano.app.forum.exception.CustomApiException;
import com.serrano.app.forum.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService {

	private final CategoryRepository categoryRepo;
	private final ModelMapper mapper;

	@Autowired
	public CategoryService(CategoryRepository categoryRepo, ModelMapper mapper) {
		this.categoryRepo = categoryRepo;   
		this.mapper = mapper;
	}
	
	@PostConstruct
	private void init(){
		TypeMap<Category, CategoryDTO> typeMap = mapper.createTypeMap(Category.class, CategoryDTO.class);
	    Converter<Collection<?> ,Integer> collectionToSize = c -> c.getSource().size();
	    typeMap.addMappings(
	    		ma -> ma.using(collectionToSize).map(Category::getPosts, CategoryDTO::setNumberOfPosts)    
	    ); 
	}
	
	public ServiceResponse create(CategoryDTO categoryDTO) {
		Category category = mapper.map(categoryDTO, Category.class);
		try {
			categoryRepo.save(category);
			CategoryDTO responseDTO = mapper.map(category, CategoryDTO.class);
			return new ServiceResponse("Category registration succesfully", HttpStatus.CREATED, responseDTO);			
		}catch(Exception ex) {
			throw new CustomApiException("Something went wrong when try to create category", 
					new ArrayList<String>(Arrays.asList(ex.getMessage())) );
		}
	}
	
	@Transactional(readOnly = true)
	public Categories getAll() {
        List<Category> entity = categoryRepo.findAll();																																																																																																																																																																																																																																																																																																																																																																																																														
        List<CategoryDTO> categoriesDTO = mapper.map(entity, new TypeToken<List<CategoryDTO>>(){}.getType());
		Categories categories = new Categories(categoriesDTO);
		return categories;
	}
	
	public CategoryDTO findById(Long id) {
		Category category = findUser(id);
		CategoryDTO categoryDTO = mapper.map(category, CategoryDTO.class);
		return categoryDTO;
	}
	
	@Transactional(readOnly = true)
	private Category findUser(Long id) {
		Category category = categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
		return category;
	}
	
}
