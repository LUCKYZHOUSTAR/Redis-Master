package com.fengzhuang;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -3191526578333403868L;
	/** 错误码 */
    protected String errcode="-1";
    
    public BusinessException(String errcode, String errmsg){ 
    	super(errmsg);
        this.errcode = errcode;
    }
    
    public BusinessException(String errcode, String errmsg, Throwable cause){ 
    	super(errmsg, cause);
        this.errcode = errcode;
    }

	public String getErrcode() {
		return errcode;
	}
    

	
}
