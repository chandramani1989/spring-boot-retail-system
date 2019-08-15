package com.chandra.retail.security.services;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chandra.retail.model.Items;
import com.chandra.retail.repository.ItemsRepository;
/**
 * @author Chandramani Mishra
 *
 */
@Service
public class ItemServiceImpl implements ItemsServices {
	
	@Autowired
	ItemsRepository itemsRepository;

	@Override
	public Page<Items> findAll(Pageable pageable) {
		
		return itemsRepository.findAll(pageable);
	}

	@Override
	public Items save(@Valid Items items) {
		
		return itemsRepository.save(items);
	}

	@Override
	public Optional<Items> findById(Integer itemId) {
		return itemsRepository.findById(itemId);
	}

}
