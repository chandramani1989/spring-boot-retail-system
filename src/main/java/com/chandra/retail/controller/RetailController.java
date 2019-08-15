package com.chandra.retail.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chandra.retail.model.Items;
import com.chandra.retail.security.services.ItemsServices;
import com.chandra.retail.utill.ResourceNotFoundException;

/**
 * @author Chandramani Mishra
 *
 */
@RestController
@RequestMapping("/api/retail")
public class RetailController {

	@Autowired
	ItemsServices itemsServices;


	@GetMapping("/items") // all list by pagination
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	ResponseEntity<?> itemsPageable(@RequestParam("pageNo") int pageNo) {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Items> pagableItems = itemsServices.findAll(pageable);
		Map<String,Object> map = new HashMap<>();
		map.put("totalPages", pagableItems.getTotalPages());
		map.put("totalElement", pagableItems.getTotalElements());
		map.put("itemsList", pagableItems.getContent());
		map.put("pageSize", pagableItems.getSize());

		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/items/{id}")  // item by id
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Items> getEmployeeById(@PathVariable(value = "id") Integer itemId)
			throws ResourceNotFoundException {
		Items employee = itemsServices.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemId));
		return ResponseEntity.ok().body(employee);
	}
	
	@PostMapping("/items") // create item
	@PreAuthorize("hasRole('ADMIN')")
	public Items createItems(@Valid @RequestBody Items items) {
		return itemsServices.save(items);
	}
	
	@PutMapping("/items/{id}") // update item
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Items> updateItems(@PathVariable(value = "id") Integer itemId,
			@Valid @RequestBody Items itemObj) throws ResourceNotFoundException {
		Items items = itemsServices.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Items not found for this id :: " + itemId));

		items.setName(itemObj.getName());
		items.setPrice(itemObj.getPrice());
		items.setQuantity(itemObj.getQuantity());
		
		final Items updatedItems = itemsServices.save(items);
		return ResponseEntity.ok(updatedItems);
	}
	
	
}
