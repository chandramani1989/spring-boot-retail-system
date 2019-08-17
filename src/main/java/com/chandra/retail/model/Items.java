package com.chandra.retail.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * @author Chandramani Mishra
 *
 */
@Entity
@Table(name = "Items")
public class Items implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 517160714494831707L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull(message = "Item name is required.")
    @Basic(optional = false)
	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@NotNull(message = "Item price is required.")
	@Column(name = "price", nullable = false)
	private Double price;
	
	@NotNull(message = "Item quantity is required.")
	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Create_Date", nullable = false)
	private Date createDate = new Date();
	
	@Transient
	private boolean availability = true;
	
	

	public Items() {
		
	}

	public Items(@NotNull(message = "Item name is required.") String name,
			@NotNull(message = "Item price is required.") Double price,
			@NotNull(message = "Item quantity is required.") Integer quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
}
