package com.chandra.retail.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	private static final Logger logger = LoggerFactory.getLogger(AuthRestAPIs.class);

	@GetMapping("/items") // all list by pagination
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> itemsPageable(@RequestParam("pageNo") int pageNo) {
		logger.info("inside api retail get all items -> Message: {}");
		int pageSize = 10;
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Items> pagableItems = itemsServices.findAll(pageable);
		List<Items> listItem = pagableItems.getContent();
		listItem.forEach(e -> {
			if(e.getQuantity() <=0) {
				e.setAvailability(false);
			}
		});
		Map<String,Object> itemsDetailMap = new HashMap<>();
		itemsDetailMap.put("totalPages", pagableItems.getTotalPages());
		itemsDetailMap.put("totalElement", pagableItems.getTotalElements());
		itemsDetailMap.put("itemsList", pagableItems.getContent());
		itemsDetailMap.put("pageSize", pagableItems.getSize());

		return ResponseEntity.ok(itemsDetailMap);
	}

	@GetMapping("/items/{id}")  // item by id
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Items> getItemById(@PathVariable(value = "id") Integer itemId)
			throws ResourceNotFoundException {
		logger.info("inside api retail get by id item -> Message: {}");
		Items item = itemsServices.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + itemId));
		return ResponseEntity.ok().body(item);
	}

	@PostMapping("/items") // create item
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Items> createItems(@Valid @RequestBody Items items) {
		Items item =itemsServices.save(items);
		return ResponseEntity.ok().body(item);
	}


	@PutMapping("/items/{id}") // update item
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Items> updateItems(@PathVariable(value = "id") Integer itemId,
			@Valid @RequestBody Items itemObj) throws ResourceNotFoundException {
		logger.info("inside update api retail by id item -> Message: {}");
		Items items = itemsServices.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Items not found for this id :: " + itemId));

		items.setName(itemObj.getName());
		items.setPrice(itemObj.getPrice());
		items.setQuantity(itemObj.getQuantity());

		final Items updatedItems = itemsServices.save(items);
		return ResponseEntity.ok(updatedItems);
	}

	@DeleteMapping("/items/{id}") // delete items
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Boolean> deleteItems(@PathVariable(value = "id") Integer itemId)
			throws ResourceNotFoundException {
		logger.info("inside delete api retail by id item -> Message: {}");
		Items items = itemsServices.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Items not found for this id :: " + itemId));

		itemsServices.delete(items);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}



}
