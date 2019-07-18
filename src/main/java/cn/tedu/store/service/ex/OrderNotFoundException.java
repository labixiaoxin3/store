package cn.tedu.store.service.ex;

/**
 *
 */
public class OrderNotFoundException extends ServiceException {

	private static final long serialVersionUID = 8856650980631064410L;

	public OrderNotFoundException() {
	}

	public OrderNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OrderNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrderNotFoundException(String message) {
		super(message);
	}

	public OrderNotFoundException(Throwable cause) {
		super(cause);
	}
}
