package model;

import java.util.ArrayList;

public class BRTree<K extends Comparable<K>,V> extends AVLTree<K,V>{
	BRNode<K,V> root;
	@Override
	public void insert(K key, V value) {
		BRNode<K,V> node = new BRNode<K,V>(key,value);
		
		if(root == null) {
			root = node;
			root.setColor(Color.BLACK);
		} else {
			node.setColor(Color.RED);
			insertBR(node, root);
			autoBalanceBR(node);
		}

	}
	protected void insertBR(BRNode<K, V> node, BRNode<K, V> current) {
		
		if(node.compareTo(current)<=-1) {
			if(current.getLeft() == null) {
				current.setLeft(node);
				node.setDad(current);
				return;
			}
			insertBR(node,current.getLeft());
		} else if(node.compareTo(current)>=1) {
			if(current.getRight() == null) {
				current.setRight(node);
				node.setDad(current);
				return;
			}
			insertBR(node,current.getRight());
		} else {
			throw new IllegalArgumentException("The key is already in the tree");
		}
	}
	
	public void autoBalanceBR(BRNode<K, V> current) {
		
		if(current == null) {
			return;
		} if(current.getDad() == null) {
			return;
		} 
		if(current.getDad().getLeft() != null) {
			if(current == current.getDad().getLeft()) {
				current.getDad().setLeft(balanceBR(current));
			} 
		} if(current.getDad()!=null &&current.getDad().getRight() != null) {
			if(current == current.getDad().getRight()) {
				current.getDad().setRight(balanceBR(current));
			}
		}
 

		
		autoBalanceBR(current.getDad());
	}
	
	public BRNode<K, V> search(K key) {
		return searchBR(root, key);
	}

	// Recursivo
	public BRNode<K, V> searchBR(BRNode<K, V> node, K key) {
		// Caso base
		if (node == null) {
			return null;
		}

		if (key == node.getKey()) {
			return node;
		}
		// Procedimiento recursivo
		if (key.compareTo(node.getKey())>1) {
			return searchBR(node.getLeft(), key);
		} else {
			return searchBR(node.getRight(), key);
		}

	}
	
	public ArrayList<V> searchAll(String name) {
		ArrayList<V> names = new ArrayList<V>();
		return searchAll(root, name,names);
	}
	
	private ArrayList<V> searchAll(BRNode<K, V> current, String name, ArrayList<V> names) {
		
		if (current != null) {
		
			searchAll(current.getLeft(),name,names);
			Person p= (Person)current.getValue();
			if( p.getName().equals(name))
				names.add((V)p);
			searchAll(current.getRight(),name,names);
		}
		return names;
	}
	
	public void inorder(AVLNode<K, V> AVLNode) {
		// Caso base
		if (AVLNode == null) {
			return;
		}
		// Recursivo

		inorder(AVLNode.getLeft());
		System.out.println(AVLNode.getKey());
		inorder(AVLNode.getRight());
	}
	public BRNode<K, V> balanceBR(BRNode<K, V> node) {
		
		//System.out.println("Key: "+node.getKey()+" Balance: "+nodeBalance);
		if(node==null) {
			return null;
		}
		
		if(node==root) {
			root.setColor(Color.BLACK);
			return node;
		}
		
		BRNode<K, V> dad = node.getDad();
		BRNode<K, V> uncle = node.getUncle();
		
		if(dad.getColor()==Color.RED) {
			if(node.getUncle()!=null&&uncle.getColor()==Color.RED) {
				dad.setColor(Color.BLACK);
				uncle.setColor(Color.BLACK);
				dad.getDad().setColor(Color.RED);
				balanceBR(dad.getDad());
			}else {
				if(dad.getRight()==node) {
					leftRotateBR(dad.getDad());
					dad.setColor(Color.BLACK);
					balanceBR(dad.getLeft());
					if(dad.getDad()!=null)
					rightRotateBR(dad.getDad());
				}
				else {
					rightRotateBR(dad.getDad());
					dad.setColor(Color.BLACK);
					balanceBR(dad.getRight());
					if(dad.getDad()!=null)
					leftRotateBR(dad.getDad());
				}
			}
		}
		
		return node;
	}
	
protected BRNode<K, V> leftRotateBR(BRNode<K, V> node) {
		
	BRNode<K, V> right = node.getRight();
		
		if(node == root) {
			root = right;
			root.setColor(Color.BLACK);
		}
		
		if(right==null) {
			return null;
		}
		
		
		node.setRight(right.getLeft());
		right.setLeft(node);
		if(node.getDad()!=null) {
			BRNode<K, V> dad = node.getDad();
			
			if(dad.getRight()==node) {
				dad.setRight(right);
			}else {
				dad.setLeft(right);
			}
		}
		right.setDad(node.getDad());
		node.setDad(right);
		node.setColor(Color.RED);
		
		return right;
	}
	
	protected BRNode<K, V> rightRotateBR(BRNode<K, V> node) {
		
		BRNode<K, V> left = node.getLeft();
		
		if(node == root) {
			root = left;
			root.setColor(Color.BLACK);
		}
		if(left==null) {
			return null;
		}
		
		node.setLeft(left.getRight());
		left.setRight(node);
		if(node.getDad()!=null) {
			BRNode<K, V> dad = node.getDad();
			
			if(dad.getRight()==node) {
				dad.setRight(left);
			}else {
				dad.setLeft(left);
			}
		}
			left.setDad(node.getDad());
			node.setDad(left);
			node.setColor(Color.RED);
		
		
		return left;
	}
	
	@Override
	public void print() {
		printBR(root,1);
	}
	
	protected void printBR(BRNode<K, V> root, int space)
	{
	    // Base case
	    if (root == null)
	        return;
	 
	    // Increase distance between levels
	    space += 10;
	 
	    // Process right child first
	    printBR(root.getRight(), space);
	 
	    // Print current node after space
	    // count
	    System.out.print("\n");
	    for (int i = 10; i < space; i++)
	        System.out.print(" ");
	    System.out.print(root.getKey()+" "+root.getColor()+" " +root.getValue() + "\n");
	 
	    // Process left child
	    printBR(root.getLeft(), space);
	}
}
