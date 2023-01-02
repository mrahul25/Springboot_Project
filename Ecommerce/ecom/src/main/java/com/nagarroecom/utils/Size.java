package com.nagarroecom.utils;

public enum Size {
	S,M,L,XL,XXL;
	
	Size size;
	
	private void setSize(String s) {
		this.size = size.valueOf(s.toUpperCase());
	}
}
