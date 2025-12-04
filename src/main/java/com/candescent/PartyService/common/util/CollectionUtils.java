package com.candescent.PartyService.common.util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Collection manipulation utilities.
 */
public final class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /** Checks if collection is null or empty. */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /** Checks if collection is not empty. */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /** Checks if map is null or empty. */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /** Checks if map is not empty. */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /** Returns collection size or 0 if null. */
    public static int size(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    /** Returns map size or 0 if null. */
    public static int size(Map<?, ?> map) {
        return map == null ? 0 : map.size();
    }

    /** Returns empty list if input is null. */
    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    /** Returns empty set if input is null. */
    public static <T> Set<T> emptyIfNull(Set<T> set) {
        return set == null ? Collections.emptySet() : set;
    }

    /** Returns empty map if input is null. */
    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> map) {
        return map == null ? Collections.emptyMap() : map;
    }

    /** Gets first element of list. */
    public static <T> Optional<T> getFirst(List<T> list) {
        if (isEmpty(list)) {
            return Optional.empty();
        }
        return Optional.ofNullable(list.get(0));
    }

    /** Gets last element of list. */
    public static <T> Optional<T> getLast(List<T> list) {
        if (isEmpty(list)) {
            return Optional.empty();
        }
        return Optional.ofNullable(list.get(list.size() - 1));
    }

    /** Gets element at index safely. */
    public static <T> Optional<T> getAt(List<T> list, int index) {
        if (isEmpty(list) || index < 0 || index >= list.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(list.get(index));
    }

    /** Converts collection to list. */
    public static <T> List<T> toList(Collection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(collection);
    }

    /** Converts collection to set. */
    public static <T> Set<T> toSet(Collection<T> collection) {
        if (collection == null) {
            return new HashSet<>();
        }
        return new HashSet<>(collection);
    }

    /** Filters collection by predicate. */
    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection) || predicate == null) {
            return new ArrayList<>();
        }
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /** Maps collection using mapper function. */
    public static <T, R> List<R> map(Collection<T> collection, Function<T, R> mapper) {
        if (isEmpty(collection) || mapper == null) {
            return new ArrayList<>();
        }
        return collection.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /** Converts list to map using key extractor. */
    public static <K, V> Map<K, V> toMap(List<V> list, Function<V, K> keyExtractor) {
        if (isEmpty(list) || keyExtractor == null) {
            return new HashMap<>();
        }
        return list.stream()
                .collect(Collectors.toMap(keyExtractor, Function.identity(), (v1, v2) -> v1));
    }

    /** Converts list to map using key and value extractors. */
    public static <T, K, V> Map<K, V> toMap(List<T> list, Function<T, K> keyExtractor, Function<T, V> valueExtractor) {
        if (isEmpty(list) || keyExtractor == null || valueExtractor == null) {
            return new HashMap<>();
        }
        return list.stream()
                .collect(Collectors.toMap(keyExtractor, valueExtractor, (v1, v2) -> v1));
    }

    /** Groups elements by classifier. */
    public static <T, K> Map<K, List<T>> groupBy(Collection<T> collection, Function<T, K> classifier) {
        if (isEmpty(collection) || classifier == null) {
            return new HashMap<>();
        }
        return collection.stream()
                .collect(Collectors.groupingBy(classifier));
    }

    /** Partitions list into sublists of specified size. */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (isEmpty(list) || size <= 0) {
            return new ArrayList<>();
        }
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }

    /** Removes duplicates preserving order. */
    public static <T> List<T> distinct(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    /** Flattens collection of collections. */
    public static <T> List<T> flatten(Collection<? extends Collection<T>> collections) {
        if (isEmpty(collections)) {
            return new ArrayList<>();
        }
        return collections.stream()
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
