package edu.miracosta.cs113;

import java.util.*;

/**
 * Implementation of a Doubly Linked List of List of generic objects
 * @param <E> Generic parameter
 */
public class DoubleLinkedList<E> implements List<E> {
	
	///////  NODE ///////////////////////////////////////////////

	/**
	 * Inner Class of Nodes f generic objects to use with a Doubly Linked List
	 * @param <E> Generic parameter
	 */
	private static class Node<E> {
		
		private Node<E> previous ;
		private Node<E> next ;
		private E data; 
		
		// Constructor
		// changes
		int j = 0 ;

		/**
		 * Constructor that accepts a generic data item
		 * @param dataItem  Generic data
		 */
		private Node(E dataItem) {
			data = dataItem ;
			next = null ;
			previous = null ;
		}

		/**
		 * Constructor, empty
		 */
		private Node() {
			data = null ;
			next = null ;
			previous = null ;
		}

		/**
		 * Full constructor for Node
		 * @param dataItem  Data for the node
		 * @param nextRef   Position of the next node
		 * @param previousRef  Position of the last node returned
		 */
		private Node(E dataItem, Node<E> nextRef, Node<E> previousRef) {
			data = dataItem ;
			next = nextRef ;
			previous = previousRef ;
		}
		
		
		
	} // end Node class  //////////////////////////
	
	
	//////// LIST_ITERATOR //////////////////////

	/**
	 * Doublly Linked List Iterator, implements ListIterator using generic parameter
	 */
	private class DoubleListIterator implements ListIterator<E>{
		private Node<E> nextItem ;
		private Node<E> lastItemReturned ;
		private int index ;


		/**
		 * Double List Link Iterator constructor
		 * @param i  index of iterator to begin at
		 */
		public DoubleListIterator(int i) {
			if (i < 0 || i > size()) {
				throw new IndexOutOfBoundsException() ;
			}
			
			lastItemReturned = null ; // pointer's last item has not been returned, yet
			
			// special last item case
			if (i == size()) {
				index = size ;
				nextItem = null ;
			} 
			else {
				nextItem = head ; // pointer is at beginning
				for (index = 0 ; index < i ; index++) {
					nextItem = nextItem.next ;
				}
			}
		}

		/**
		 * Empty constructor for list iterator
		 */
		public DoubleListIterator() {
			lastItemReturned = null ; // pointer's last item has not been returned, yet
			nextItem = head ; // pointer is at beginning
			for (index = 0 ; index < size() ; index++) {
				nextItem = nextItem.next ;
			}
		}

		@Override
		public void add(E e) {
			// empty list
			if (head == null) {
				head = new Node<E>(e) ;
				tail = head ;
			}
			// add to head
			else if (nextItem == head) {
				Node<E> newNode = new Node<E>(e) ;
				newNode.next = nextItem ;
				nextItem.previous = newNode ;
				head = newNode ;
			}
			// add to tail
			else if (nextItem == null) {
				Node<E> newNode = new Node<E>(e) ;
				tail.next = newNode ;
				newNode.previous = tail ;
				tail = newNode ;
			}
			else {  // add to middle
				Node<E> newNode = new Node<E>(e) ;
				newNode.previous = nextItem.previous ;
				nextItem.previous.next = newNode ;
				newNode.next = nextItem ;
				nextItem.previous = newNode ;
			}
			size++ ;
			index++ ;
			lastItemReturned = null ;
			
		}

		@Override
		public boolean hasNext()		{
			return nextItem != null ;		
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException() ;
			}
			
			lastItemReturned = nextItem ;
			nextItem = nextItem.next ;
			index++ ;		
			return lastItemReturned.data ;
		}

		@Override
		public boolean hasPrevious() {
		     if (head == null) { // empty
                return false ;
            }
            else if  (nextItem == null ) { // at the end
                return true ;
            }
            else if (nextItem.previous == null) { // at the head
                return false ;
            }

            else {
                return true;
            }
		}
		
		@Override
		public E previous() {
			if (!hasPrevious()) { //head
				throw new NoSuchElementException() ;
			}

			if (nextItem == null) {//tail
				nextItem = tail ;
			} 
			else {
				nextItem = nextItem.previous ;
			}
			lastItemReturned = nextItem ;
			index-- ;
			System.out.println("in previous. last item return is " + lastItemReturned.data.toString());
			return lastItemReturned.data ;
		}

		@Override
		public int previousIndex() {
			return (index-1) ;
		}

		@Override
		public int nextIndex() {
			return index ;
		}

		@Override
		public void remove() {
			if (head == null) { // empty list
				throw new IllegalStateException() ;
			}
			else if (lastItemReturned == null) { // no step over
				throw new IllegalStateException() ;
			}
			else if (lastItemReturned.previous == null && nextItem == null) {
				head = null ;
				tail = null ;
				size = 0 ;
				index = 0 ;
			}
			else if (lastItemReturned.previous == null) {
				nextItem.previous = head ;
				head = nextItem ;
				size-- ;
				index-- ;
			}
			else if (nextItem == null) { // tail
				lastItemReturned.previous.next = null ;
				tail = lastItemReturned.previous ;
				index-- ;
				size-- ;
			}
			else {
				lastItemReturned.previous.next = nextItem ;
				nextItem.previous = lastItemReturned.previous ;
				size-- ;
				index-- ;	System.out.println("In the remove");

			}
			//lastItemReturned = null ;


		}

		@Override
		public void set(E e) {
		    if (lastItemReturned == null) {
		        throw new IllegalStateException() ;
            }
            Node<E> newNode = new Node<E>(e) ;
            lastItemReturned.data = newNode.data ;
        }

		
		
		
	} // end ListIterator class ///////////////////////////
	
	
	// DLL Instance variable
    private Node<E> head = null ;
    private Node<E> tail = null ;
    private int size = 0 ;
    
    
    // Constructors
	@Override
    public DoubleListIterator listIterator() {
        return new DoubleListIterator() ;
    }

    @Override
    public DoubleListIterator iterator() {
		return new DoubleListIterator() ;
	}

	@Override
	public Object[] toArray() {
		return new Object[0];
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	/**
	 * Creates a new listIterator
	 * @param index  Index where to begin iterator
	 * @return  listIterator
	 */
	public DoubleListIterator listIterator(int index) {

        return new DoubleListIterator(index) ;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return null;
	}


	/**
	 * Constructor for Doubly Linked List
	 */
	public DoubleLinkedList() {
		tail = null ;
        head = null ; 
        size = 0 ;        
    }

    @Override
    public boolean add(E e) {
		listIterator().add(e);			
		return true ;
	}

	@Override
	public void add(int index, E obj) {
		listIterator(index).add(obj) ;
	}

	@Override
    public E get(int index) {
        DoubleListIterator mover = new DoubleListIterator(index) ;
        if (!(mover.hasNext())) {
            throw new IndexOutOfBoundsException() ;
        }
		return mover.next();
	}

	@Override
	public int size() {
		return size ;
	}

	@Override
	public boolean isEmpty() {
			return (head == null) ;
		
	}

	@Override
	public void clear() {
		head = null;
		tail = head ;
		size = 0 ;
	}

	@Override
	public E remove(int index) {
        if ( index < 0  || index >= size()) {
            throw new IndexOutOfBoundsException() ;
        }
        else {

			DoubleListIterator mover = new DoubleListIterator(index);
			if (!(mover.hasNext())) {
				throw new IllegalStateException();
			}

			E temp = mover.next();
			mover.remove();
			return temp;
		}
		
	}
	@Override
	public boolean remove(Object o) {
		int sizeNow = size ;
		int indexNow = indexOf(o) ;
		if (indexNow < 0 || indexNow >= size()) {
			return false ;
		}
		DoubleListIterator mover = new DoubleListIterator(indexNow) ;
		if (mover.hasNext()) {
			mover.next();
		}
		else {
			throw new IndexOutOfBoundsException();
		}
		mover.remove() ;
		if (sizeNow == (size() -1)) {
			return false ;
		}
		else {
			return true ;
		}
		
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean contains(Object o) {	
		if (indexOf(o) > 0 ) {
			return true ;
		}
		return false ;
	}

	@Override
	public E set(int index, E e) {
        if (index > size()-1) {
            throw new IndexOutOfBoundsException() ;
        }
		DoubleListIterator mover = new DoubleListIterator(index) ;
		Node<E> oldInfo = new Node<E>() ;

		mover.next() ;

		oldInfo.data = mover.lastItemReturned.data;
		mover.set(e);

		return oldInfo.data ;
	}

	@Override
	public String toString() {
		String returnString = "[" ;
		Node position = head ;
		
		if (head == null) {
			returnString += "" ;
		}
		else {
			while (position != null) {

				returnString +=  position.data ;
				position = position.next  ;
                returnString += ", " ;
			}
			returnString = returnString.substring(0, returnString.length() - 2) ;
			
		}
		returnString += "]" ;
		return returnString ;
	}

	@Override
	public int indexOf(Object o) {
		int count = 0 ;
		if (o == null) {
			return -1 ;
		}
		Node position = head ;
		while (position != null) {
			if (o.equals(position.data)) {
				return count ;
			}
			count++ ;
			position = position.next ;
		}
		return -1 ;
		
	}
	@Override
	public int lastIndexOf(Object o) {
		int count = 0 ;
		if (o == null) {
			return -1 ;
		}
		Node position = tail ;
		while (position != null) {
			if (o.equals(position.data)) {
				return (size - count - 1) ;
			}
			count++ ;
			position = position.previous ;
		}
		return -1 ;
		
	}

	@Override
	public boolean equals(Object o) {
        if (o == null ) {
            return false ;
        }
        if (!(o instanceof LinkedList)) {
			return false ;
		}
		
        List anotherList = (LinkedList) o ;
        if (size() != anotherList.size()) {
            return false ;
        }
        Node<E> position = head ;
        while (position != null) {
            if (! (anotherList.contains(position.data))) {
                return false ;
            }
            position = position.next ;                   
        }      
        return true ;                                    
    }

	

} // end class
