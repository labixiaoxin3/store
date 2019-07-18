package cn.tedu.store.service.ex;

public class ProductOutOfStockException extends ServiceException {

	private static final long serialVersionUID = 7705891984251550181L;

	public ProductOutOfStockException() {
	}

	public ProductOutOfStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProductOutOfStockException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductOutOfStockException(String message) {
		super(message);
	}

	public ProductOutOfStockException(Throwable cause) {
		super(cause);
	}
}
