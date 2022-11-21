package ru.qdts.training.streams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SalesRecLoader {
	public static List<SalesRec> load(String file) {
		ArrayList<SalesRec> list = new ArrayList<SalesRec>();
		String line;
		int counter=0;
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			System.out.println("SalesRecLoader: start reading file " + file);
			reader.readLine(); // Skip title line
			while((line = reader.readLine()) != null) {
				counter++;
				String[] filds = line.split(",");
				try {
				list.add(new SalesRec(filds[0], filds[1], filds[2], filds[3], filds[4].charAt(0),
					LocalDate.parse(filds[5],DateTimeFormatter.ofPattern("M/d/y")),
					Integer.parseInt(filds[6]),
					LocalDate.parse(filds[7],DateTimeFormatter.ofPattern("M/d/y")),
					Integer.parseInt(filds[8]), Float.parseFloat(filds[9]), Float.parseFloat(filds[10]),
					Float.parseFloat(filds[11]), Float.parseFloat(filds[12]), Float.parseFloat(filds[13])));
				} catch(DateTimeParseException e) {
					System.out.println("Line #" + counter);
					e.printStackTrace();
					continue;
				} catch(NumberFormatException e) {
					System.out.println("Line #" + counter);
					e.printStackTrace();
					continue;
				}
								
			}
			
			System.out.println(counter + " lines successfully loaded");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return Collections.unmodifiableList(list);
	}
}
