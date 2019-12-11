package Ex1;

public class ComplexFunction implements complex_function{
	private function left, right;
	private Operation operation;


	//Empty Constructor
	public ComplexFunction() {

	}


	/**
	 * Constructor that get Operator as string and two functions, a and b
	 * @param string
	 * @param a
	 * @param b
	 */
	public ComplexFunction(String operation, function a, function b) {
		this.left = a;
		this.right = b;
		this.operation = getOperationByString(operation);
	}


	public ComplexFunction(function cf) {
		//If cf is ComplexFunction function type
		if((cf instanceof ComplexFunction)) {
			ComplexFunction f = (ComplexFunction)cf;
			this.left = f.left;
			this.right = f.right;
			this.operation = f.operation;
		}
		else {
			this.left = cf;
			this.right = null;
			this.operation = Operation.None;
		}
	}


	//Compute f(x)
	@Override
	public double f(double x) {
		switch (this.operation) {
		case Plus:
			return this.left.f(x) + this.right.f(x);

		case Times:
			return this.left.f(x) * this.right.f(x);

		case Divid:
			return this.left.f(x) / this.right.f(x);

		case Max:
			return Math.max(this.left.f(x), this.right.f(x));

		case Min:
			return Math.min(this.left.f(x), this.right.f(x));

		case Comp:
			return this.left.f(this.right.f(x));

		case None:
			return this.left.f(x);

		case Error:
			throw new ArithmeticException("Unknown Operation");
		}
		return 0;
	}


	//This function get string and check the function type(cf or polynom)  and send to
	//the appropriate constructor
	@Override
	public function initFromString(String s) {
		ComplexFunction cf = new ComplexFunction();
		s = s.replaceAll(" ", "");
		
		if(s.contains("("))
			cf = (ComplexFunction) initFromString(cf, s, "");
		else {
			cf.left = new Polynom(s); 
			cf.right = null;
			cf.operation = Operation.None;
		}
		return cf;
	}

	//This function get null cf, string function(without spaces and null operation.
	//The function goes all over the fun string by recreation and find all the function
	//input: "plus(div(+1.0x +1.0,mul(+1.0x -4.0, mul(+1.0x +3.0,+1.0x -2.0))),2.0)" --> String
	//output: Plus(2.0,Divid(1.0x + 1.0,Times(1.0x - 4.0,Times(1.0x + 3.0,1.0x - 2.0)))) --> CF object
	private function initFromString(ComplexFunction cf, String fun, String op) {

		int bracketLocation = fun.indexOf("("); //Index of first '('
		String operation = fun.substring(0, bracketLocation); //String of operation
		String bFunction = "", restFun = "";
		int k = 0;

		//If its function like this: div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0))
		//and the is more operators
		if((fun.charAt(bracketLocation+1) == '+' || fun.charAt(bracketLocation+1) == '-')
				&& (countChar(fun, '(') > 1)) {
			for (int i = bracketLocation+1; i < fun.length(); i++) {
				k++;
				if(fun.charAt(i) == ',') {
					bFunction = fun.substring(bracketLocation+1, bracketLocation+k); 
					break;
				}
			}
			restFun = fun.substring(bracketLocation + 1 +k , fun.length()-1);
		}

		//If its function like: div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0))
		else if((fun.charAt(bracketLocation+1) != '+' && fun.charAt(bracketLocation+1) != '-')
				&& (countChar(fun, '(') > 1)){
			for (int i = fun.length()-1; i > 0; i--) {
				k++;
				if(fun.charAt(i) == ',') {
					bFunction = fun.substring(i+1, fun.length()-1); 
					break;
				}
			}
			restFun = fun.substring(bracketLocation + 1 , fun.length()-k);
		}


		//If the is no more operators. like: mul(+1.0x +3.0,+1.0x -2.0)
		if(countChar(fun, '(') == 1) {
			restFun = fun.substring(bracketLocation+1, fun.length()-1);
			String[] arr = restFun.split(",", 2);
			cf = new ComplexFunction(operation, new Polynom(arr[0]), new Polynom(arr[1]));
		}
		else {
			cf = new ComplexFunction(operation, new Polynom(bFunction), initFromString(cf, restFun, op));	
		}

		return cf;
	}


	//Check how many char c there is in str
	private int countChar(String str, char c){
		int count = 0;

		for(int i=0; i < str.length(); i++){
			if(str.charAt(i) == c)
				count++;
		}
		return count;
	}


	@Override
	/**
	 * Copy Complex Function to new Complex Function.
	 */
	public function copy() {
		return new ComplexFunction(getStringByOperayion(this.operation), this.left, this.right);
	}


	@Override
	/**
	 * Get function type and will be added to this f1 our function
	 */
	public void plus(function f1) {
		this.left = this.copy();
		this.right = f1;
		this.operation = Operation.Plus;

	}


	@Override
	public void mul(function f1) {
		this.left = this.copy();
		this.right = f1;
		this.operation = Operation.Times;
	}


	@Override
	public void div(function f1) {
		this.left = this.copy();
		this.right = f1;
		this.operation = Operation.Divid;
	}


	@Override
	public void max(function f1) {
		this.left = this.copy();
		this.right = f1;
		this.operation = Operation.Max;
	}


	@Override
	public void min(function f1) {
		this.left = this.copy();
		this.right = f1;
		this.operation = Operation.Min;
	}


	@Override
	public void comp(function f1) {
		this.left = this.copy();
		this.right = f1;
		this.operation = Operation.Comp;
	}


	@Override
	//Return aFun
	public function left() {
		return this.left;
	}


	@Override
	//Return bFun
	public function right() {
		return this.right;
	}


	@Override
	//Return operation type
	public Operation getOp() {
		return this.operation;
	}


	private Operation getOperationByString(String string) {
		switch (string.toLowerCase()) {
		case "plus":
			return Operation.Plus;
		case "mul":
			return Operation.Times;
		case "div":
			return Operation.Divid;
		case "max":
			return Operation.Max;
		case "min":
			return Operation.Min;
		case "comp":
			return Operation.Comp;
		case "none":
			return Operation.None;
		case "error":
			return Operation.Error;

		default:
			throw new ArithmeticException("Operation doesn't exist");
		}
	}


	private String getStringByOperayion(Operation op) {
		switch (op) {
		case Plus:
			return "plus";
		case Times:
			return "mul";
		case Divid:
			return "div";
		case Max:
			return "max";
		case Min:
			return "min";
		case Comp:
			return "comp";
		case None:
			return "none";
		case Error:
			return "error";
		default:
			throw new ArithmeticException("Operation doesn't exist");
		}
	}


	public String toString() {
		return  this.operation+"(" + this.left + "," + this.right + ")";
	}


	public static void main(String[] args) {
		String s1 = "None(3.1 +2.4x^2 -x^4 , 5 +2x -3.3x +0.1x^5)";
		String s2 = "5 +2x -3.3x +0.1x^5";
		String[] s3 = {"x +3","x -2", "x -4"};
		String s4 = "plus(div(+1.0x +1.0,mul(+1.0x -4.0, mul(+1.0x +3.0,+1.0x -2.0))),2.0)";
		ComplexFunction cf3 = new ComplexFunction();
		function cf5 = cf3.initFromString(s4);
		//cf3.initFromString(s2);
		//System.out.println(cf5.toString());
		
		function p1= new Polynom("x^2+x^4");
		function p2= new Polynom("x+5");
		function p3= new Polynom("x^2+x^2");
		String exp="Plus(mul(1.0x^3+1.0x^2,1.0x^1+1.0),1.0x^3+1.0x^2)";
		ComplexFunction f1= new ComplexFunction("mul", p1, p2);
		f1.comp(p3);
		System.out.println(f1.toString());
	}
}
