package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.function;

class ComplexFunctionTest {



	@Test
	void initFromStringTest() {
		ComplexFunction cf = new ComplexFunction();
		String currect1 = "2x + 3 + x^2";
		String currect2 = "-2x^2 -4x";
		int err = 0;
		cf.initFromString(currect1);
		cf.initFromString(currect1);

		String bad1 = "pls(x,5)";
		String bad2 = "Max(x 5)";
		try {
			cf.initFromString(bad1);
		} catch (Exception e) {
			System.out.println(e); err++;
		}

		try {
			cf.initFromString(bad2);
		} catch (Exception e) {
			System.out.println(e); err++;
		}
		
		assertEquals(2, err);

	}

	@Test
	void copyTest() {
		ComplexFunction cf = new ComplexFunction();
		String str = "plus(div(4x^2,3+x),-2)";
		function c = cf.initFromString(str);
		function cf1 = c.copy();
		assertEquals(cf1.toString(), c.toString());
	}

	
	@Test
	void plusTest() {
		function p1= new Polynom("x^2+x^4");
		function p2= new Polynom("x+5");
		function p3= new Polynom("x^2+x^2");
		String exp="Plus(Times(1.0x^4 + 1.0x^2,1.0x + 5.0),2.0x^2)";
		ComplexFunction f1= new ComplexFunction("mul", p1, p2);
		f1.plus(p3);
		assertEquals(f1.toString(), exp.toString());
	}
	
	
	@Test
	void mulTest() {
		function p1= new Polynom("x^2+x^4");
		function p2= new Polynom("x+5");
		function p3= new Polynom("x^2+x^2");
		String exp="Times(Times(1.0x^4 + 1.0x^2,1.0x + 5.0),2.0x^2)";
		ComplexFunction f1= new ComplexFunction("mul", p1, p2);
		f1.mul(p3);
		assertEquals(f1.toString(), exp.toString());
	}
	
	
	@Test
	void divTest() {
		function p1= new Polynom("x^2+x^4");
		function p2= new Polynom("x+5");
		function p3= new Polynom("x^2+x^2");
		String exp="Divid(Times(1.0x^4 + 1.0x^2,1.0x + 5.0),2.0x^2)";
		ComplexFunction f1= new ComplexFunction("mul", p1, p2);
		f1.div(p3);
		assertEquals(f1.toString(), exp.toString());
	}
	
	
	@Test
	void maxTest() {
		function p1= new Polynom("x^2+x^4");
		function p2= new Polynom("x+5");
		function p3= new Polynom("x^2+x^2");
		String exp="Max(Times(1.0x^4 + 1.0x^2,1.0x + 5.0),2.0x^2)";
		ComplexFunction f1= new ComplexFunction("mul", p1, p2);
		f1.max(p3);
		assertEquals(f1.toString(), exp.toString());
	}
	
	
	@Test
	void minTest() {
		function p1= new Polynom("x^2+x^4");
		function p2= new Polynom("x+5");
		function p3= new Polynom("x^2+x^2");
		String exp="Min(Times(1.0x^4 + 1.0x^2,1.0x + 5.0),2.0x^2)";
		ComplexFunction f1= new ComplexFunction("mul", p1, p2);
		f1.min(p3);
		assertEquals(f1.toString(), exp.toString());
	}
	
	
	@Test
	void compTest() {
		function p1= new Polynom("x^2+x^4");
		function p2= new Polynom("x+5");
		function p3= new Polynom("x^2+x^2");
		String exp="Comp(Times(1.0x^4 + 1.0x^2,1.0x + 5.0),2.0x^2)";
		ComplexFunction f1= new ComplexFunction("mul", p1, p2);
		f1.comp(p3);
		assertEquals(f1.toString(), exp.toString());
	}
	
	@Test
	void getLeftTest() {
		function p1= new Polynom("x^2+x^4");
		function p2= new Polynom("x+5");
		String exp="1.0x^4 + 1.0x^2";
		ComplexFunction f1= new ComplexFunction("mul", p1, p2);
		assertEquals(f1.left().toString(), exp.toString());
	}
	
	
	@Test
	void getRightTest() {
		function p1= new Polynom("x^2+x^4");
		function p2= new Polynom("x+5");
		String exp="1.0x + 5.0";
		ComplexFunction f1= new ComplexFunction("mul", p1, p2);
		assertEquals(f1.right().toString(), exp.toString());
	}
	
	void getOpTest() {
		function p1= new Polynom("x^2+x^4");
		function p2= new Polynom("x+5");
		String exp="Mul";
		ComplexFunction f1= new ComplexFunction("mul", p1, p2);
		Operation op = f1.getOp();
		assertEquals(op.toString(), exp.toString());
	}
}
