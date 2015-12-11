package pl.jcommerce.java8.methodReferences;

@FunctionalInterface
public interface ReferenceToFunctionWithTwoParams<T, T1, R> {

	public R execute(T t, T1 t1);
}
