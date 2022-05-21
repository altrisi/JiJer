package altrisi.dev.jijer;

public class Utils {
	@FunctionalInterface
	public static interface ThrowingSupplier<T, E extends Exception> {
		T execute() throws E;
	}
	
	public static <T, E extends Exception> T shouldntHappen(ThrowingSupplier<T, E> function) {
		try {
			return function.execute();
		} catch (Exception e) {
			if (e instanceof RuntimeException re)
				throw re;
			throw new IllegalStateException("Unexpected exception!", e);
		}
	}
}
