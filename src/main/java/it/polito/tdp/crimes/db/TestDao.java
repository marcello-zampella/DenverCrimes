package it.polito.tdp.crimes.db;

import java.time.LocalDate;
import java.time.Month;

import it.polito.tdp.crimes.model.Event;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		Month mese=Month.FEBRUARY;
		System.out.println(dao.getContatoreCrimini("menacing-felony-w-weap", "aggravated-assault", mese));
	}

}
