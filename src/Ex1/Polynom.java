package Ex1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

import javax.management.RuntimeErrorException;

import Ex1.Monom;
/**
 * This class represents a Polynom with add, multipy functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	/**
	 * list of monoms
	 */

	private ArrayList<Monom> pol;
	private static final Monom_Comperator com = new Monom_Comperator();

	public double[] GetArrayY(int start,int end) {
		double[] ans=new double[end-start];
		int c=0;
		for (int i = start; i < end; i++) {
			ans[c]=f(i);
			c++;
		}
		return ans;
	}

	/**
	 * Empty Constructor   
	 */
	public Polynom() {
		//Initialize Array List of monoms 
		pol  = new  ArrayList<Monom>();
	}


	/**
	 *function that adds new monom to the polynom
	 */
	public Polynom(Polynom_able p1) {
		this(); //Constructor overloading
		Iterator<Monom> iter = p1.iteretor();
		while(iter.hasNext()) {
			Monom m = iter.next();
			this.add(new Monom(m));
		}
	}


	/**
	 * we assumed that there is a space between every polynom
	 *and there is a plus between every polynom 
	 * and the minus in the negative polynoms are attached to the coefficients
	 * function that change from string to integer
	 */
	public Polynom(String s) {
		this();
		Polynom th = null;
		if(s.contains("(")) {
			s="("+s+")";
			th=FormPolynomCalc.formValue(s);
		}
		else
			th = init_from_string(s);
		Iterator<Monom> iter = th.iteretor();
		while(iter.hasNext()) {
			this.add(iter.next());
		}
	}


	//function that returns the s 
	public String toString() {
		String s="";
		if(this.isZero())
			return "0";
		Iterator<Monom> v = this.iteretor();
		Monom m = v.next();
		s += m.toString();
		while(v.hasNext()) {
			m = v.next();
			if(m.get_coefficient()>0)
				s += " + " + m.toString();
			else if(m.get_coefficient()<0) {
				String r = "" + m.toString();
				r=r.substring(1);
				s+=" - " + r;
			}
		}
		return s;
	}


	@Override
	/**
	 * function that returns the value of the polynom in a random (x)
	 */
	public double f(double x) {
		//new iterator=this iterator
		Iterator<Monom> iterV = this.iteretor();
		double ans = 0;

		//if iterV has a next monom
		while (iterV.hasNext()) {
			Monom m = iterV.next();
			ans += m.f(x);
		}
		return ans;
	}


	@Override
	/**
	 * function that add two polynoms
	 */
	public void add(Polynom_able p1) {
		Iterator<Monom> iterV = p1.iteretor();
		while (iterV.hasNext()) {
			Monom m = iterV.next();
			this.add(m);
		}
	}


	/**
	 * function that delete monom from polynom
	 */ 
	public void substract(Monom m1) {
		boolean found_power = false;
		Iterator<Monom> v = this.iteretor();

		// check if there is power and the v has a next monom
		while (!found_power && v.hasNext()) {
			Monom c = v.next();

			//check if the power of c is simular to the power of m1
			if (c.get_power() == m1.get_power()) {
				c.substract(m1);
				found_power = true;
				if (c.get_coefficient() == 0)
					v.remove();
			}
		}

		if (!found_power) {
			Monom m2 = new Monom(m1.get_coefficient() * -1, m1.get_power());
			pol.add(m2);
			this.pol.sort(com);
		}
	}


	/**
	 *function that substracts two polynoms
	 */
	public void substract(Polynom_able p1) {
		if(this.equals(p1)) {
			pol.clear();
			//pol.add(new Monom(0,0));
			return;
		}
		else {
			Iterator<Monom> iterV = p1.iteretor();
			while (iterV.hasNext()) {
				Monom m = iterV.next();
				this.substract(m);
			}
		}
	}


	/** 
	 * function that add a monom to a polynom
	 */

	public void add(Monom m1) {
		boolean found_power = false;
		Iterator<Monom> v = this.iteretor();
		if(m1.get_coefficient()==0)
			return ;

		//check if v has a next monom
		while (!found_power && v.hasNext()) {
			Monom c = v.next();

			//check if power is equal
			if (c.get_power() == m1.get_power()) {
				c.add(m1);
				found_power = true;

				//check if c coefficient is zero if yes then remove the monom from the polynom
				if (c.get_coefficient() == 0)
					v.remove();
			}
		}

		//check if there is not a simular power
		if (!found_power) {
			// add the new monom to the polynom
			this.pol.add(m1);
			//sort the polynom by powers
			this.pol.sort(com);
		}
	}


	/**
	 * function that multiple two polynoms and returns the new polynom
	 */
	public static Polynom multiply(Polynom_able p0, Polynom_able p1) {
		Polynom ans = new Polynom();
		if(!p0.isZero() && !p1.isZero()) {
			Iterator<Monom> iter = p0.iteretor();
			while(iter.hasNext()) {
				Monom m = iter.next();
				Polynom p2 = new Polynom(p1);
				p2.multiply(m);
				ans.add(p2);
			}
		}
		return ans;
	}


	/**
	 * function that multiple two monoms
	 */

	public void multiply(Monom m1) {
		if(m1.get_coefficient()==0) {
			this.pol.clear();
		}
		else {
			Iterator<Monom> iter = this.iteretor();
			while(iter.hasNext()) {
				Monom m = iter.next();
				m.multipy(m1);
			}
		}
	}


	@Override
	/**
	 * function that multiple two polynoms
	 */
	public void multiply(Polynom_able p1) {
		Polynom p2 = multiply(this, p1);
		this.pol = p2.pol;
	}


	/**
	 *function that returns the size of the polynom
	 */
	public int size() {return this.pol.size();}


	@Override
	/**
	 *function that check if all the monoms in the two polynoms are equal
	 */

	public boolean equals (Object p1) {
		boolean ans = false;
		//if the size of the two polynoms is equal ans=true
		if(this.size() == ((Polynom)p1).size()) {
			ans = true;
			Iterator<Monom> iter0 = this.iteretor();
			Iterator<Monom> iter1 = ((Polynom) p1).iteretor();

			//if ans=true and iter0 has a next monom do
			while(ans && iter0.hasNext()) {
				Monom m0 = iter0.next();
				Monom m1 = iter1.next();

				//if m0 is not equal to m1 ans=false
				if(!m0.equals(m1)) {
					ans = false;
				}
			}
		}
		return ans;
	}


	/**
	 *function that check if the polynom is a zero polynom
	 */
	public boolean isZero() {
		if (this.pol.size() == 0) 
			return true;
		return false;
	}


	@Override
	/**
	 *function that returns the diffrence
	 */
	public double root(double x0, double x1, double eps) {
		double f0 = this.f(x0);
		double f1 = this.f(x1);
		if ((f0 * f1) > 0){
			throw new RuntimeException("ERROR: x1 and x0 are not opposite to one another.");
		}
		double length_x = Math.abs(x0 - x1);
		double length_y = Math.abs(f0 - f1);
		while (length_x > eps || length_y > eps) {
			double mid_x = (x0 + x1) / 2;
			double mid_y = this.f(mid_x);
			double value = f0 * mid_y;
			if (value < 0){
				x1 = mid_x;
				f1 = f(x1);
			}
			else if (value > 0){
				x0 = mid_x;
				f0 = f(x0);
			}
			else
				return mid_x;
			length_x = Math.abs(x0 - x1);
			length_y = Math.abs(f0 - f1);
		}
		return x1;
	}


	@Override
	/**
	 * deep copy
	 */
	public Polynom_able copy() {
		Polynom p1 = new Polynom();
		Iterator <Monom> v = this.iteretor();
		while(v.hasNext()) {
			Monom m1=v.next();
			p1.pol.add(new Monom(m1));
		}
		return p1;
	}


	@Override
	/**
	 * function that returns the derivative of a polynom
	 */
	public Polynom_able derivative() {
		Polynom p1=new Polynom();
		Iterator<Monom> v =this.iteretor();

		//if v has a next monom do
		while(v.hasNext()) {
			Monom m0=v.next();
			Monom m1=new Monom(m0);
			m1.derivative();
			p1.pol.add(m1);
		}
		return p1;
	}


	@Override
	/**
	 * function that returns the area of the polynom
	 */
	public double area(double x0, double x1, double eps) {
		if (x0 > x1){
			throw new RuntimeException("ERROR: Wrong values (x0 shoudl be less than x1");
		}

		double ar_under = 0; // Sum of each rectangle.
		double numOfRec = Math.abs((x1 - x0) / eps); // Number of rectangles calculated using eps.
		double mideps = eps / 2;

		for (int i = 1; i <= numOfRec; i++){
			double Area = eps *f(x0 + mideps);
			if (Area <= 0){
				ar_under += Area;
			}
			mideps += eps;
		}

		double under =0;
		double ar_above = 0; // Sum of each rectangle.
		numOfRec = Math.abs((x1 - x0) / eps); // Number of rectangles calculated using eps.
		mideps = eps / 2;

		for (int i = 1; i <= numOfRec; i++){
			double Area = eps * this.f(x0 + mideps);
			if (Area >= 0){
				ar_above += Area;
			}
			mideps += eps;
		}

		double above= ar_above;

		return above + under;
	}


	@Override
	/** 
	 * itrator function
	 */
	public Iterator<Monom> iteretor() {return this.pol.iterator();}


	public static Polynom init_from_string(String str) {
		int start = 0, end = 0;
		Polynom p=new Polynom();

		str = str.replaceAll(" ", "");
		str += "+";

		if(str.charAt(0) != '+' && str.charAt(0) != '-') str = "+" + str;

		String s="";

		for (int i = 0; i < str.length(); i++) {
			if(i == str.length()-1) break;

			if(str.charAt(i) == '+') {
				start= i + 1;

				for (int j = i+1; j < str.length(); j++) {
					if(str.charAt(j)=='+'||str.charAt(j)=='-') {
						end=j;
						break;
					}
					else if(j==str.length()) {
						end=str.length();
						break;
					}
				}
				
				s=str.substring(start,end);
				p.add(new Monom(s));
			}
			
			else if(str.charAt(i) == '-') {
				start=i;
				for (int j = i+1; j < str.length(); j++) {
					if(str.charAt(j)=='+'||str.charAt(j)=='-') {
						end=j;
						break;
					}
					else if(j==str.length()) {
						end=str.length();
						break;
					}
				}
				s=str.substring(start,end);
				p.add(new Monom(s));
			}
		}
		return p;
	}


	/**
	 * Initial Polynom object from string
	 */
	public function initFromString(String s) {
		return new Polynom(s);
	}
	
}


