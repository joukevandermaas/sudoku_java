/*
 * RemoveOnlyList.java
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RemoveOnlyList<T> implements List<T> {
	private ArrayList<T> list;
	
	public RemoveOnlyList(T[] values) {
		list = new ArrayList<T>();
		for(T i : values)
			list.add(i);
	}
	
	public String toString() {
		return list.toString();
	}
	
	@Override
	public boolean add(T arg0) {
		return false;
	}

	@Override
	public void add(int arg0, T arg1) {
		throw new IllegalStateException("Not implemented");
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		return false;
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return list.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return list.containsAll(arg0);
	}

	@Override
	public T get(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return list.indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return list.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<T> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int arg0) {
		return list.listIterator(arg0);
	}

	@Override
	public boolean remove(Object arg0) {
		return list.remove(arg0);
	}

	@Override
	public T remove(int arg0) {
		return list.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return list.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return list.retainAll(arg0);
	}

	@Override
	public T set(int arg0, T arg1) {
		return null;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public List<T> subList(int arg0, int arg1) {
		return list.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] arg0) {
		return list.toArray(arg0);
	}

}
