/**
 * 
 */
package com.chandra.retail.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.chandra.retail.model.Items;

/**
 * @author Chandramani Mishra
 *
 */
@Repository
public interface ItemsRepository extends PagingAndSortingRepository<Items, Integer> {
	
	 //@Query("Select from  Items ")
	public Page<Items> findAll(Pageable pageable);
	
	//public Items findById(Integer itemId);

}
