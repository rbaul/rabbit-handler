package com.github.rbaul.rabbithandler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TestDto {
	private String id;
	
	private String name;
	
	private String familyName;
	
	private String address;
	
}