package com.account.facade;

import com.account.model.Result;

/**
 * Internal Facade
 * 
 * @author king
 * @date 2022/05/07 13:51
 **/
public interface AccountInternalFacade {

    /**
     * getAccount
     *
     * @date 2022/05/07 13:58
     * @return com.stori.sofa.model.Result<java.lang.String>
     */
    public Result<String> getAccount();
}
