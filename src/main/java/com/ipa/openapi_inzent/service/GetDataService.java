package com.ipa.openapi_inzent.service;

import com.ipa.openapi_inzent.dao.GetDataDao;
import com.ipa.openapi_inzent.model.GetDataDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDataService {
    private GetDataDao getDataDao;

    public GetDataService(GetDataDao getDataDao) {
        this.getDataDao = getDataDao;
    }

    public List<GetDataDTO> selectAll(String clientNum, String uri) {
        return getDataDao.selectAll(clientNum, uri);
    }

    public List<GetDataDTO> accountAll(String account, String clientNum, String uri) {
        return getDataDao.accountAll(account, clientNum , uri);
    }
}
