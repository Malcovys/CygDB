package com.cyg.beans;

import java.util.List;

//import java.util.*;

public class Table {
	private String nom;
	private List<String> fields;
	private List<String> fields_type;
	private String[] colones_name;
	private String[] colones_type;
	private List<List<String>> lines;
	

	public List<List<String>> getLines() {
		return lines;
	}

	public void setLines(List<List<String>> lines) {
		this.lines = lines;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<String> getFields_type() {
		return fields_type;
	}

	public void setFields_type(List<String> fields_type) {
		this.fields_type = fields_type;
	}

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
