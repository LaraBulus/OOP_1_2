package Ex1;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Functions_GUI implements functions{

	private ArrayList<function> allFunctions;
	public static Color[] Colors = {Color.blue, Color.cyan,
			Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.PINK};

	public Functions_GUI() {
		this.allFunctions = new ArrayList<>();
	}


	@Override
	public boolean add(function arg0) {
		// TODO Auto-generated method stub
		return this.allFunctions.add(arg0);
	}


	@Override
	public boolean addAll(Collection<? extends function> arg0) {
		// TODO Auto-generated method stub
		return this.addAll(arg0);
	}


	@Override
	public void clear() {
		this.allFunctions.clear();
	}


	@Override
	public boolean contains(Object arg0) {
		return this.allFunctions.contains(arg0);
	}


	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return this.allFunctions.containsAll(arg0);
	}


	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.allFunctions.isEmpty();
	}


	@Override
	public Iterator<function> iterator() {
		// TODO Auto-generated method stub
		return this.allFunctions.iterator();
	}


	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return this.allFunctions.remove(arg0);
	}


	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return this.allFunctions.removeAll(arg0);
	}


	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return this.allFunctions.retainAll(arg0);
	}


	public function get(int i) {
		return this.allFunctions.get(i);
	}

	@Override
	//Return number of functions 
	public int size() {
		return this.allFunctions.size();
	}


	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return this.allFunctions.toArray();
	}


	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return this.allFunctions.toArray(arg0);
	}


	@Override
	//BufferReader code from: https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
	public void initFromFile(String file) throws IOException {
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();

			while (line != null) {
				String[] functions = line.split("\n");
				for (int i = 0; i < functions.length; i++) 
					this.allFunctions.add(new ComplexFunction().initFromString(functions[i]));
				line = br.readLine();
			}
		}	
	}


	@Override
	//Save all the functions in the ArrayList in text file.
	public void saveToFile(String file) throws IOException {
		String content = "";
		for (int i = 0; i < allFunctions.size(); i++) {
			content += allFunctions.get(i).toString() + "\n";
		}
		System.out.println(content);
		FileOutputStream fos = new FileOutputStream(file);
		DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
		outStream.writeUTF(content);
		outStream.close();
	}


	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		// rescale the coordinate system
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());

		StdDraw.setPenColor(Color.LIGHT_GRAY);
		//horizon lines
		for(double i= ry.get_min(); i<=ry.get_max();i++) {
			StdDraw.line(rx.get_min(), i, rx.get_max(), i);
			StdDraw.text(0.2,i+0.2,i+""); 
		}
		//vertical lines
		for(double j=rx.get_min(); j<=rx.get_max(); j++) {
			StdDraw.line(j, ry.get_min(), j, ry.get_max());
			StdDraw.text(j+0.2,0.2,j+""); 
		}

		//Drawing the base lines.	
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.005);
		// x line.
		StdDraw.line(rx.get_min(),0, rx.get_max(), 0);
		//y line.
		StdDraw.line(0, ry.get_min(), 0, ry.get_max());

		double step = (Math.abs(rx.get_min())+Math.abs(rx.get_max()))/resolution;
		for(int i=0; i<this.allFunctions.size();i++) {
			StdDraw.setPenColor(Colors[i%Colors.length]);
			for(double j =rx.get_min(); j< rx.get_max(); j=j+step) {
				StdDraw.line(j, allFunctions.get(i).f(j), j+step, allFunctions.get(i).f(j+step));
			}
		}
		//Save picture in OOP folder
		StdDraw.save("Functions_GUI.jpg");

	}


	@Override
	public void drawFunctions(String json_file) {
		//Default values
//		int width = 1000 , height = 600 ,resolution = 200;
//		double range_X [] = {-10 , 10};
//		double range_Y [] = {-5 , 15};

		JSONParser jsonParser = new JSONParser();

		try {

			Object obj = jsonParser.parse(new FileReader(json_file));
			JSONObject jsonObject = (JSONObject) obj;
			long width = (long) jsonObject.get("Width");
			long height =  (long) jsonObject.get("Height");
			JSONArray Jrx = (JSONArray) jsonObject.get("Range_X");
			JSONArray Jry = (JSONArray) jsonObject.get("Range_Y");
			String minX = Jrx.get(0).toString();
			String maxX = Jrx.get(1).toString();
			String minY = Jry.get(0).toString();
			String maxY = Jry.get(1).toString();
			Range rx = new Range(Integer.parseInt(minX), Integer.parseInt(maxX));
			Range ry = new Range(Integer.parseInt(minY), Integer.parseInt(maxY));
			long resolution = (long) jsonObject.get("Resolution");
			drawFunctions((int)width, (int)height, rx, ry, (int) resolution);

		}
		catch (Exception e) {
			e.printStackTrace();
		} 


	}


	public static void main(String[] args) throws IOException {
		//		Functions_GUI a = new Functions_GUI();
		//		a.allFunctions.add(new Polynom("3.1 +2.4x^2 -x^4"));
		//		a.saveToFile("/home/dor/file.txt");

		Functions_GUI test = new Functions_GUI();
		test.add(new Polynom("3 + 2x^2"));
		int w=1000, h=600, res=200;
		Range rx = new Range(-10,10);
		Range ry = new Range(-5,15);
		test.drawFunctions(w,h,rx,ry,res);
	}


}
