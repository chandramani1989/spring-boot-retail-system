package com.chandra.retail.security.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chandra.retail.model.Items;

/**
 * @author Chandramani Mishra
 *
 */
public interface ItemsServices {

	Page<Items> findAll(Pageable pageable);

	Items save(@Valid Items items);

	Optional<Items> findById(Integer itemId);

}
