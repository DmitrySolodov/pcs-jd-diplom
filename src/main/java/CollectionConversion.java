import java.util.Collection;

public interface CollectionConversion<T, V> {
    T convert(Collection<V> collection);
}
