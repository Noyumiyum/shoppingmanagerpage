package com.saeyan.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ProductVO {
	private Integer code;
	private String name;
	private Integer price;
	private String description;
	private String pictureUrl;
}
