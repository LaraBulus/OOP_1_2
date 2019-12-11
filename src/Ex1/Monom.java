
package Ex1;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.RuntimeErrorException;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}

	private static DecimalFormat df = new DecimalFormat("#.#####");
	private double _coefficient; 
	private int _power;


	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}


	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}


	public Monom (String s) {
		if (!isStringCorrect(s)) {
			System.out.println(s);
			throw new ArithmeticException(
					"Invalid format for Monom . Could created by instance of Monom Class or Polynom Class");
		}
		else {
			Monom th = init_from_String(s);
			this.set_coefficient(th._coefficient);
			this.set_power(th._power);
		}
	}


	/**
	 * 
	 * @return
	 */
	public double get_coefficient() {
		return this._coefficient;
	}


	public int get_power() {
		return this._power;
	}


	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}


	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 


	public boolean isZero() {return this.get_coefficient() == 0;}


	public void add(Monom m) {
		if(this.get_power()==m.get_power()) {
			this.set_coefficient(this.get_coefficient()+m.get_coefficient());
			this.set_coefficient(Double.parseDouble(df.format(get_coefficient())));
		}
		else {
			throw new RuntimeException("Error! You try to Add two Monoms with diffierent power!");
		}
	}

	
	public void multipy(Monom d) {
		this.set_coefficient(this.get_coefficient()*d.get_coefficient());
		this.set_coefficient(Double.parseDouble(df.format(get_coefficient())));
		this.set_power(this.get_power()+d.get_power());
	}

	
	public void substract(Monom m) {
		if(this.get_power()==m.get_power()) {
			this.set_coefficient(this.get_coefficient()-m.get_coefficient());
		}
		else {
			throw new RuntimeException("Error! You try to Add two Monoms with diffierent power!");
		}
	}


	/**
	 * function that returns s
	 */
	public String toString() {

		if(this._coefficient == 0)

			return "0";

		else if(this._power == 0)

			return "" + this._coefficient ;

		else if(this._power == 1)

			return "" + this._coefficient + "x";

		return "" + this._coefficient + "x^" + this._power;
	}


	public boolean equals(Monom m1) {
		if(this._coefficient==m1._coefficient&&this._power==m1._power)
			return true;
		return false;
	}

	
	private void set_coefficient(double a){
		this._coefficient = a;
	}


	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}


	private static Monom getNewZeroMonom() {return new Monom(ZERO);}


	//Check if there is a illegal characters
	//This code from: 
	//https://stackoverflow.com/questions/14635391/java-function-to-return-if-string-contains-illegal-characters
	private boolean isStringCorrect(String s) {
		String patternStr = "(^[-+]?([0-9]*\\.[0-9]+|[0-9]+|)([x](\\^[0-9]+)?)?)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();	
	}


	/**
	 * function that changes the monom from string to double
	 */
	private static Monom init_from_String(String s) {           
		if (s == null) throw new RuntimeException("error can not recive null");

		double coefficient = 0;
		int pow = 0;
		String in = s.toLowerCase();

		int inx = in.indexOf("x");
		//If there is no x
		if (inx < 0) coefficient = Double.parseDouble(in);     

		//If there is a x 
		else {
			String c = in.substring(0 , inx);   

			if(c.contains("*")) c = c.substring(0, c.length()-1);
			//There is no coefficient
			else if(c.length()==0)
				c="1";
			//Negative x
			else if(c.length()==1&&c.contains("-"))
				c="-1";

			coefficient = Double.parseDouble(c);  

			//Check if there is a power//
			int inp = in.indexOf("^");   
			//If there is no power
			if (inp < 0) pow = 1;
			//There is a power
			else {
				String v = in.substring(inp+1);
				pow = Integer.parseInt(v);
			}
		}
		return new Monom(coefficient,pow);
	}


	/**
	 * Initial Monom object from string
	 */
	public function initFromString(String s) {
		return new Monom(s);
	}


	/**
	 * Copy Monom object
	 */
	public function copy() {
		return new Monom(this);
	}


}
