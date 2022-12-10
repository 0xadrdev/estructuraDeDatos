package practica6;

import static org.junit.Assert.fail;

import java.util.*;

public class BinaryExpressionTree {

	
	
	//cronstructor de árbol de expresiones a partir de una expresión notación potfijas completamente parentizada
			
	public static EDBinaryNode<Character> buildBinaryExpressionTree(String chain) {

		EDBinaryNode<Character> tree = null;
		if (chain == null || chain.length()==0)
			return tree;
		
		Stack<EDBinaryNode<Character>> pila = new Stack<>();
		boolean error = false;

		int i=0;

		while (i < chain.length() && !error) {
			Character c = chain.charAt(i);
			if (Character.isDigit(c)) {
				EDBinaryNode<Character> single = new EDBinaryNode<>(c);
				pila.push(single);
			}
			else if (isValidOperator(c)) {
				EDBinaryNode<Character> rightS=null, leftS=null;
				if (!pila.empty()) {
					rightS = pila.peek();
					pila.pop();
				}
				else error = true;  //wrong input chain
				if (!error) {
					if (!pila.empty()) {
						leftS = pila.peek();
						pila.pop();
					}
					else error = true; //wrong input chain
				}
				if (!error) {
					EDBinaryNode<Character> b = new EDBinaryNode<>(c,leftS,rightS);
					pila.push(b);
				}
			}
			else if (c!=' ') error = true; //wrong input chain, operator
			i++;  //next character in chain
		}
		if (!error) {
			if (!pila.empty()) {
				tree = pila.peek();
				pila.pop();
			}
			else error = true; //wrong input chain
		}
		if (!pila.empty()) error=true; //stack must be empty at the end

		if (error ) return null; 
		else return tree;
	}



	/**
	 * Determines whether the tree is extended or not, i.e., each node has either two or none siblings.
	 * @return <code>true</code> if extended.
	 */
	public static boolean isExtended(EDBinaryNode<Character> tree) {
		//EJERCICIO 1
		if (tree == null || tree.isLeaf()) {
			return true; 
		}
		return tree.hasLeft() && isExtended(tree.left()) && tree.hasRight() && isExtended(tree.right());
	}


    /** Given an expression tree, detemrines whether all the leaves contain digits and
	 * internal nodes only contain valid operators
     * @param tree the binary expression tree
     * @return <code>true</code> if digits are only on the leaves.
     */
	// EJERCICIO 2
	// Nodo hoja es un nodo sin descendientes, nodo terminal. 
	public static boolean digistsOnLeaves(EDBinaryNode<Character> tree) {
		if (tree == null) {
			return true;
		}
		if (tree.isLeaf()) {
			return tree.data() >= '0' && tree.data() <= '9';
		} else if (isValidOperator(tree.data())) {
			return digistsOnLeaves(tree.left()) && digistsOnLeaves(tree.right());
		}
		return false;
	}

	private static boolean isValidOperator(Character c) {
		if (c=='+' || c=='-' || c=='*' || c=='/' || c=='^')
			return true;
		else return false;
	}
			

    /** Given a binary expression tree, computes the result of executing the operations it contains.
     * @param tree a correct binary expression tree
     * @return The result of the evaluation.
     */
	public static float evaluate(EDBinaryNode<Character> tree) {	
		// EJERCICIO 3
		float resultado = 0; 
		if (tree.isLeaf()) {
			resultado = (float) tree.data() - '0';
		} else if (tree.data() == '+') {
			resultado = evaluate(tree.left()) + evaluate(tree.right());
		} else if (tree.data() == '-') {
			resultado = evaluate(tree.left()) - evaluate(tree.right());
		} else if (tree.data() == '^') {
			resultado = (float) Math.pow(evaluate(tree.left()), evaluate(tree.right()));
		} else if (tree.data() == '*') {
			resultado = evaluate(tree.left()) * evaluate(tree.right());
		} else if (tree.data() == '/') {
			resultado = evaluate(tree.left()) / evaluate(tree.right());
		} 
		return resultado;
	}
    /** Returns a list with the result of traversing the nodes of an expression tree in inOrder.
	 * The list must be completely parenthesised.
     * @param tree a correct binary expression tree
     * @return The list with the nodes of the tree in inorder
     */
	public static List<Character> asListInorder(EDBinaryNode<Character> tree) {
		// EJERCICIO 4
		// Se puede pasar una lista para no crear una cada vez que llamo al nodo. 
		List<Character> lista = new ArrayList<Character>(); 
		if (tree.isLeaf()) {    
			lista.add(tree.data());
		} else {  
			if (tree.hasLeft()) {
				lista.add('(');
				lista.addAll(asListInorder(tree.left()));
				lista.add(tree.data());
			}
			if (tree.hasRight()) {
				lista.addAll(asListInorder(tree.right()));
				lista.add(')');
			}
		}
		return lista; 
	}

	private static<T> boolean isSymmetric(EDBinaryNode<T> tree1, EDBinaryNode<T> tree2) {
		if (tree1 == null && tree2 == null) {
			return true;
		}
		if (tree1 != null && tree2 != null && tree1.data() == tree2.data()) {
			return isSymmetric(tree1.left(), tree2.right()) && isSymmetric(tree1.right(), tree2.left());
		}
		return false;
	}

	/** isSymmetric (EDBinaryNode<T> )
	 *
	 * @param tree BinaryTree
	 * @return true if tree holds the symmetric property in content and in shape
	 */
	public static<T> boolean isSymmetric (EDBinaryNode<T> tree) {
		//EJERCICIO 5
		return isSymmetric(tree, tree);
	}

	
	public static<T> boolean areIdentical(EDBinaryNode<T> tree1, EDBinaryNode<T> tree2) {

		if (tree1 == null && tree2 == null) {
			return true;
		} 

		if (tree1 == null || tree2 == null) {
			return contains(tree1, tree2);
		}

		return tree1.data() == tree2.data() && areIdentical(tree1.left(), tree2.left()) && areIdentical(tree1.right(), tree2.right());
	}

	/** contains
	 * Checks if a binary tree subtree is contained in another binary tree, tree
	 * @param tree a binary tree
	 * @param subtree a binary tree
	 * @return true if subtree is contained in tree
	 */
	public static<T> boolean contains (EDBinaryNode<T> tree, EDBinaryNode<T> subtree) {
		//EJERCICIO 6
		// Si coincide la cabeza, miro la parte de abajo. 

		if (subtree == null) {
			return true;
		} 

		if (tree == null) {
			return false;
		}

		if (areIdentical(tree, subtree)) {
			return true;
		}

		return contains(tree.left(), subtree) || contains(tree.right(), subtree);
	}

	

	// methods needed for toString
	private static StringBuilder repeated(char c, int times) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++)
			sb.append(c);

		return sb;
	}

	private static int depth(EDBinaryNode<Character> node) {
		if (node == null)
			return 0;

		int ld = depth(node.left()) + 1;
		int rd = depth(node.right()) + 1;

		return ld > rd ? ld : rd;
	}

	private static int createSpaces(EDBinaryNode<Character> tree, List<String> offset, List<String> separator) {
		int depth = depth(tree);

		offset.clear();
		separator.clear();

		if (depth < 1)
			return 0;

		int pad = 1;
		for (int i = 0; i < depth; i++) {
			offset.add(0, repeated(' ', pad -1 ).toString());
			pad = 2*pad+1;
			separator.add(0, repeated(' ', pad).toString());
		}

		return depth;
	}

	public static String toString(EDBinaryNode<Character> tree) {
		StringBuilder resultado = new StringBuilder();
		Queue<EDBinaryNode<Character>> q = new LinkedList<>();
		List<String> margen = new LinkedList<>();
		List<String> separacion = new LinkedList<>();
		int altura =  createSpaces(tree, margen, separacion);

		if (altura == 0) {
			resultado.append("------------\n");
			resultado.append(" Empty tree\n");
			resultado.append("------------\n");
			return resultado.toString();
		}

		StringBuilder barra = repeated('-',   ((1 << (altura-1)) * 4) - 3);

		int nactual = 0;
		int cuenta = 1;
		resultado.append(barra).append('\n').append(margen.get(nactual));
		q.add(tree);

		while (nactual < altura) {
			EDBinaryNode<Character> n = q.remove();
			String dato = " ";
			if (n != null)
				dato = n.data().toString();

			resultado.append(dato);
			cuenta--;
			if (cuenta > 0)
				resultado.append(separacion.get(nactual));
			else {
				resultado.append('\n');
				nactual++;
				cuenta = 1 << nactual;

				if (nactual < altura)
					resultado.append(margen.get(nactual));
				else
					resultado.append(barra).append('\n');
			}

			if (nactual < altura ) {
				if (n == null) {
					q.add(null);
					q.add(null);
				} else {
					q.add(n.left());
					q.add(n.right());
				}
			}
		}
		return resultado.toString();
	}
}
