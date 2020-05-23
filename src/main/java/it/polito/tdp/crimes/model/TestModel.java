package it.polito.tdp.crimes.model;

import java.util.LinkedList;

public class TestModel {

	public static void main(String[] args) {
		LinkedList<String> c=new LinkedList<String>();
		c.add("a");
		c.add("b");
		LinkedList t=(LinkedList) c.clone();
		c.removeLast();
		System.out.println(c+"\n"+t);
		
	}

}
