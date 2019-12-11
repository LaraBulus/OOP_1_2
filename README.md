
# Ex1,2

# (Polynom)

# Codded by

# Athar hassan, Lara bulus

## **descreption**

This project represents monomial, polynomial with many functions and operations

### **Monom class**

Use a monom from the format of ax^b. **(a must be a (double) number**

**And b must be every (integer)number greater and equal to 0)**

Note: We assumed that the input is valid

ax^b, a, ax, x, x^b

The class monom supports some functions as

- add=adding two monoms if they have the same power
- derivative: computing the derivative of a monom
- f(x): compute the monom in a given number
- multiply: multiple between our monom and giving monom
- is equal: checks if the two monom have the same coeff and power
- is zero:checks if the coeff is zero(the monom is zero)

### **Polynom class**

Use a ploynom from the format of a\_x1^b + a\_x2^b + .... + axn^b. **(a must be a (double) number**

**And b must be every (integer)number greater and equal to 0)**

Note: We assumed that the input is valid

ax1^b + ax2^b + .... + axn^b, ((a1\_x^b1 + a2\_x^b2) \* (a3\_x^b3)) …

polynom (string):every input is valid(ax^b) with snd without &quot;\*&quot;

The class polynom supports some functions as:

- add - there is two options to add function add Monom/Polynom(using Polynom\_able) ,the monom function adds monom to our polynom and the polynom function adds polynom to our polynom.
- Derivative: compute the derivative of the polynom without changing the current polynom(it puts the derivative in a new polynom)
- f(x): computes the polynom in a given number
- multiply: multiples two polynoms
- subtract: subtracts two polynoms
- root
- area: computing the area of the polynom in a giving eps
- is equal: checks if two polynoms are equal
- is zero: checks if every coeff of the monoms in the polynom are 0
- polynom(string): every input is valid (ax^b) without &quot;\*&quot;

### **To show the polynom graph we use drawFunctions(…) function**
<br>
<img src="https://github.com/LaraBulus/OOP_1_2/blob/master/Functions_GUI.jpg",c_limit/so-logo-s.jpg" width="250">
<br>
