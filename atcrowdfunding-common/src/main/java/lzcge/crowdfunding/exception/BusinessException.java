package lzcge.crowdfunding.exception;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException{

	public BusinessException(){
		super();
	}

	public BusinessException(String ExceptionMessage){
		super(ExceptionMessage);
	}
}
