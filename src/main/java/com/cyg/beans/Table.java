package com.cyg.beans;

import java.util.*;

public class Table {
	private String nom;
	private String[] colones_name;
	private String[] colones_type;
	

	public String[] getColones_name() {
		return colones_name;
	}

	public void setColones_name(String[] colones_name) {
		this.colones_name = colones_name;
	}

	public String[] getColones_type() {
		return colones_type;
	}

	public void setColones_type(String[] colones_type) {
		this.colones_type = colones_type;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
