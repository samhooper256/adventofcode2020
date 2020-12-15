package utils;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author Sam Hooper
 *
 */
public final class Graphs {
	
	private Graphs() {}
	
	/**
	 * <p>Returns a {@link Set} containing all objects that appear as either the first or the second element of one of the {@code T[]}s in the given
	 * {@link Collection}. If any element in the given {@code Collection} is {@code null} or is not an array of length 2, <b>all behavior is undefined</b>.</p>
	 * @param <T>
	 * @param edges
	 */
	public static <T, S extends Set<T>> S inferNodes(final Collection<T[]> edges, Supplier<S> setFactory) {
		S set = setFactory.get();
		for(T[] edge : edges) {
			set.add(edge[0]);
			set.add(edge[1]);
		}
		return set;
	}
	
	/**
	 * <p>Returns a {@link Set} containing all objects that appear as either the first or the second element of one of the {@code T[]}s in the given
	 * {@link Collection}. If any element in the given {@code Collection} is {@code null} or is not an array of length 2, <b>all behavior is undefined</b>.</p>
	 * @param <T>
	 * @param edges
	 */
	public static <T> Set<T> inferNodes(final Collection<T[]> edges) {
		return inferNodes(edges, HashSet::new);
	}
	
	/**
	 * <p>Returns an adjacency {@link Map} for the given directed graph. The adjacency {@code Map} maps each node to a {@link Collection} (produced by the given
	 * {@link Supplier}) containing all of its direct successors. This method accepts the {@code nodes} and {@code edges} of the graph as parameters.</p>
	 * 
	 * <p>Only objects in {@code nodes} will be keys in the returned {@code Map}. If an object is in {@code edges} (as an element of one of the arrays) but not in
	 * {@code nodes}, <b>all behavior is undefined<b>. An object may be in {@code nodes} but not in {@code edges}; in that case, that object will be mapped
	 * to an empty {@code Collection} supplied by the given {@code Supplier}.</p>
	 * @param <T> The type of the nodes in the graph
	 * @param <M> The type of the adjacency {@link Map} to be returned
	 * @param <C> The {@link Collection} type that will be the values of the returned map.
	 * @param edges a <code>Collection&ltT[]&gt</code> containing arrays of length-2, where the first element in an array is the tail of
	 * the edge (the node from which the edge is going) and the second element is the head of the edge (the node to which the edge is directed).
	 * @param nodes a {@code Collection<T>} containing the nodes in the graph. The returned map will contain the unique values of {@code nodes} as its keys.
	 * @param mapFactory The factory to generate the returned {@code Map}.
	 * @param collectionFactory The factory to generate the {@code Collections} that will be the values of the {@code Map}.
	 * @return
	 */
	public static <T, M extends Map<T, C>, C extends Collection<T>> M adjMapDirected(
			final Collection<T[]> edges, final Collection<T> nodes, final Supplier<M> mapFactory, Supplier<C> collectionFactory) {
		M map = mapFactory.get();
		for(T node : nodes)
			map.put(node, collectionFactory.get());
		for(T[] edge : edges)
			map.get(edge[0]).add(edge[1]);
		return map;
	}
	
	public static <T> Map<T, Collection<T>> radjMapDirected(final Collection<T[]> edges, final Collection<T> nodes) {
		return radjMapDirected(edges, nodes, HashMap::new, ArrayList::new);
	}
	
	
	public static <T, M extends Map<T, C>, C extends Collection<T>> M radjMapDirected(
			final Collection<T[]> edges, final Collection<T> nodes, final Supplier<M> mapFactory, Supplier<C> collectionFactory) {
		M map = mapFactory.get();
		for(T node : nodes)
			map.put(node, collectionFactory.get());
		for(T[] edge : edges)
			map.get(edge[1]).add(edge[0]);
		return map;
	}
	
	
}
