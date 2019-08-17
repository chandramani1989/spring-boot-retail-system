package com.chandra.retail;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.chandra.retail.controller.RetailController;
import com.chandra.retail.model.Items;
import com.chandra.retail.security.services.ItemsServices;
import com.chandra.retail.utill.ResourceNotFoundException;

public class RetailControllerTest {

	private MockMvc mockMvc;
	@Mock
	ItemsServices itemsServices;

	@InjectMocks
	RetailController retailController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(retailController).setMessageConverters(
				new MappingJackson2HttpMessageConverter(), new Jaxb2RootElementHttpMessageConverter()).build();
		ReflectionTestUtils.setField(retailController, "itemsServices", itemsServices);

	}

	@Test
	public void shouldReturnAllItemsPageable() {
		Pageable pageable = PageRequest.of(5, 10);
		List<Items> items = getAllItems();
		Page<Items> pagedItemsResponse = new PageImpl(items);
		Mockito.when(itemsServices.findAll(pageable)).thenReturn(pagedItemsResponse);

		ResponseEntity<?> resp = retailController.itemsPageable(5);;
		@SuppressWarnings("unchecked")
		Map<String,Object> map=(Map<String, Object>) resp.getBody();
		assertEquals(200,resp.getStatusCodeValue());
		assertEquals(1l,map.get("totalElement"));
		assertEquals(1,map.get("totalPages"));
	}

	@Test
	public void shouldReturnItemById() throws ResourceNotFoundException {
		Mockito.when(itemsServices.findById(2)).thenReturn(getItemById());
		Items item  = retailController.getItemById(2).getBody();	
		assertEquals("Item1",item.getName());
		System.out.println(item);
	}

	@Test
	public void shouldCreateItems() throws ResourceNotFoundException {
		Mockito.when(itemsServices.save(item())).thenReturn(item());
		ResponseEntity<Items> item  = retailController.createItems(item());
		assertEquals(200,item.getStatusCodeValue());
	}

	@Test
	public void shouldUpdateItems() throws ResourceNotFoundException {
		Mockito.when(itemsServices.findById(2)).thenReturn(getItemById());
		ResponseEntity<Items> item  = retailController.updateItems(2, item());
		assertEquals(200,item.getStatusCodeValue());
	}

	private List<Items> getAllItems() {
		List<Items> items=new  ArrayList<>();
		Items itm=new Items();

		itm.setAvailability(true);
		itm.setCreateDate(new Date());
		itm.setName("Item1");
		itm.setId(1);
		itm.setQuantity(5);
		items.add(itm);
		return items;

	}

	private Optional<Items> getItemById() {
		Items itm=new Items();
		itm.setAvailability(true);
		itm.setCreateDate(new Date());
		itm.setName("Item1");
		itm.setId(2);
		itm.setQuantity(5);
		Optional<Items> opt = Optional.of(itm);

		return opt;

	}

	private Items item() {
		Items itm=new Items();
		itm.setAvailability(true);
		itm.setCreateDate(new Date());
		itm.setName("Item1");
		itm.setQuantity(5);
		itm.setPrice(20d);
		return itm;

	}



}
