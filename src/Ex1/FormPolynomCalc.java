package Ex1;

public class FormPolynomCalc {
	public static char[] OP = {'+', '-', '*'};

	private static boolean DEBUG_mode = false;
	    /**

	     * Check is the given String is of a proper Form shape.

	     * For example: 3, (-1.2), (3+4), (3*(4-1)), (((1.5))*((1.5*4)*(2.5*(4-2))))

	     * @param s

	     * @return

	     */

	    public static boolean isForm(String s) {

	        if(s==null || s.length()==0) {

	            return false;

	        }

	        if(isValidSimpleReal(s)){
	            return true;
	        }

	        String s1 = simplify(s);

	        if(s1.length()==s.length()) {return false;}

	        int i = mainOp(s1);

	        if(i==-1) {return false;}

	        String sl = s1.substring(0,i);

	        String sr = s1.substring(i+1);
	        return isForm(sl) && isForm(sr);

	    }



	    /**

	     * This static function returns the index of main operation (over *,-,+),

	     * in case of an invalid from returns -1;

	     * @param s: represents a form

	     * @return the index of the main oparatiopn (-1 if none).

	     */

	    public static int mainOp(String s) {

	        int ans = -1;

	        int c =0;

	        for(int i=0;i<s.length();i++) {

	            char ch = s.charAt(i);

	            if(ch=='(') {c++;}

	            if(ch==')') {c--;}

	            if(isOp(ch) && c==0) {

	                ans = i;

	            }

	        }

	        return ans;

	    }



	    /**

	     * Simple function of testing an mathematical operation return true for '*', '+', '-'

	     * @param c

	     * @return true for '*', '+', '-', else false

	     */

	    private static boolean isOp(char c) {

	        boolean ans = false;

	        for(int i=0;i<OP.length;i++) {

	            if (c == OP[i]) {

	                ans = true;

	            }

	        }

	        return ans;

	    }



	    /**

	     *

	     * @param s

	     * @return

	     */

	    public static boolean isValidSimpleReal(String s) {

	        boolean ans = false;

	        if(s!=null) {

	            int len = s.length();

	            String s1 = simplify(s);

	            while(s1!=null && s1.length()<len) {

	                len = s1.length();

	                s1 = simplify(s1);

	            }

	            try{

	                new Polynom(s1);

	                ans = true;

	            }

	            catch(Exception e) {;}

	        }

	        return ans;

	    }



	    /**

	     * Simplify a simpleForm "(4)" --> "4", "(((-2)))" --> "((-2))'

	     * @param s simple Form like String

	     * @return: a simplified String, or the same string of nothing to simplified.

	     */

	    private static String simplify(String s) {

	        int len = s.length();

	        if (s != null && len > 1) {

	            if (s.charAt(0) == '(' && s.charAt(len-1) == ')') {

	                //return simplify(s.substring(1, len-1)); // this leads to a bug - should not be recursive!

	                return s.substring(1, len-1);

	            }

	        }

	        return s;

	    }

	    public static void setDEBUG_mode(boolean t) {

	        DEBUG_mode = t;

	    }



	    public static boolean getDEBUG_mode() {return DEBUG_mode;}



	    /**

	     * Computes the real value of the given String if it is of shape such as: "3", "-1.2", "((-2.1))",

	     * else returns null;

	     * @param s

	     * @return

	     */

	    public static Polynom simpleRealValue(String s) {

	        Polynom ans = null;

	        if(s!=null) {

	            int len = s.length();

	            String s1 = simplify(s);

	            while(s1!=null && s1.length()<len) {

	                len = s1.length();

	                s1 = simplify(s1);

	            }

	            try{

	                ans = Polynom.init_from_string(s1); 

	            }

	            catch(Exception e) {;}

	        }

	        return ans;

	    }



	    /**

	     * Computes the real value of the given Form, return null if on an invalid shape.

	     * @param s: Form like String

	     * @return: a Double value (null in case of invalid input).

	     */

	    public static Polynom formValue(String s) {
	        if(s==null || s.length()==0) {

	            return null;

	        }

	        Polynom d = simpleRealValue(s);

	        if(d!=null){
	            return d;
	        }

	        String s1 = simplify(s);

	        if(s1.length()==s.length()) {return null;}

	        int i = mainOp(s1);

	        if(i==-1) {return null;}

	        char op = s1.charAt(i);

	        String sl = s1.substring(0,i);

	        String sr = s1.substring(i+1);

	        Polynom dl = formValue(sl);

	        Polynom dr = formValue(sr);

	        Polynom ans = comp(dl, op, dr);

	        return ans;

	    }



	    /**

	     * This is somewhat a better implementation - as we have only a single code for

	     * both computing and testing.

	     * @param s - a Form (represented as a String)

	     * @return

	     */

	    public static boolean isForm2(String s) {

	        Polynom d = formValue(s);

	        return d!=null;

	    }



	    /**

	     * Nothing to "write home about" - as simple as possible

	     * @param l: Double value (might be null)

	     * @param op: an operation ("*", "+", "-") else return null;

	     * @param r: Double value (might be null)

	     * @return: the value, or null if wrong input (null or invalid op).

	     */

	    private static Polynom comp(Polynom l, char op, Polynom r) {

	    	Polynom ans = null;

	        if(l==null || r ==null) {return ans;}

	        if(op=='*'){l.multiply(r); }

	        if(op=='+'){ l.add(r); }

	        if(op=='-'){ l.substract(r); }
	        ans=l;
	        return ans;

	    }


}
